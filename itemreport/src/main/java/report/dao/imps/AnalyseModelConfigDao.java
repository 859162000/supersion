package report.dao.imps;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.analyse.Rep_AnalyseModel;
import report.dto.analyse.Rep_AnalyseModel_Currs;
import report.dto.analyse.Rep_AnalyseModel_Dimension1;
import report.dto.analyse.Rep_AnalyseModel_Dimension2;
import report.dto.analyse.Rep_AnalyseModel_ItemPros;
import report.dto.analyse.Rep_AnalyseModel_Items;
import report.dto.analyse.Rep_AnalyseModel_Orgs;
import framework.dao.imps.BaseVoidResultDao;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;

public class AnalyseModelConfigDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		Rep_AnalyseModel analyseModel = (Rep_AnalyseModel) RequestManager.getTOject();
		//根据外键清除关联表数据
		List<Field> oneTomanyFilds = ReflectOperation.getOneToManyField(Rep_AnalyseModel.class);
		for(Field fk : oneTomanyFilds){
			DetachedCriteria dc = DetachedCriteria.forEntityName(ReflectOperation.getDtoName(fk));
			dc.add(Restrictions.eq("model", analyseModel));
			List<Object> listObject=this.getHibernateTemplate().findByCriteria(dc);
			if(listObject.size()>0)
			{
				this.getHibernateTemplate().deleteAll(listObject);
			}
		}
		this.getHibernateTemplate().flush();
		this.getHibernateTemplate().clear();
		//02 指标
		for(String itemId : analyseModel.getItemIds()){
			if(StringUtils.isNotBlank(itemId))
				analyseModel.getItems().add(new Rep_AnalyseModel_Items(analyseModel,itemId));
		}
		
		//03 机构
		for(String orgId : analyseModel.getOrgIds()){
			if(StringUtils.isNotBlank(orgId))
				analyseModel.getOrgs().add(new Rep_AnalyseModel_Orgs(analyseModel,orgId));
		}
		
		//04  币种
		for(String currId : analyseModel.getCurrIds()){
			if(StringUtils.isNotBlank(currId))
				analyseModel.getCurrs().add(new Rep_AnalyseModel_Currs(analyseModel,currId));
		}
		
		//05 指标属性
		for(String itemProId : analyseModel.getItemProIds()){
			if(StringUtils.isNotBlank(itemProId))
				analyseModel.getItemPros().add(new Rep_AnalyseModel_ItemPros(analyseModel,itemProId));
		}
		
		//06 扩展维度1
		for(String dimension1Type : analyseModel.getDimension1Types()){
			if(StringUtils.isNotBlank(dimension1Type))
				analyseModel.getDimension1s().add(new Rep_AnalyseModel_Dimension1(analyseModel,dimension1Type));
		}
		
		//07 扩展维度2
		for(String dimension2Type : analyseModel.getDimension2Types()){
			if(StringUtils.isNotBlank(dimension2Type))
				analyseModel.getDimension2s().add(new Rep_AnalyseModel_Dimension2(analyseModel,dimension2Type));
		}
		
		this.getHibernateTemplate().update(analyseModel);
	}

}
