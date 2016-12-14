package report.service.imps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import report.dto.ItemData;
import report.dto.analyse.Rep_AnalyseModel;
import report.dto.analyse.Rep_AnalyseModel_Currs;
import report.dto.analyse.Rep_AnalyseModel_Dimension1;
import report.dto.analyse.Rep_AnalyseModel_Dimension2;
import report.dto.analyse.Rep_AnalyseModel_ItemPros;
import report.dto.analyse.Rep_AnalyseModel_Items;
import report.dto.analyse.Rep_AnalyseModel_Orgs;
import report.dto.analyse.Rep_ItemWarning;
import report.dto.analyse.Rep_ItemWarningResult;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.MessageResult;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：RepItemWarningService</P>
 * *********************************<br>
 * <P>类描述：预警分析</P>
 * 创建人：王川<br>
 * 创建时间：2016-9-2 下午5:29:00<br>
 * 修改人：王川<br>
 * 修改时间：2016-9-2 下午5:29:00<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class RepItemWarningService extends RepModelAnalyseService implements IParamObjectResultExecute{
	
	@Override
	public Object paramObjectResultExecute(Object param) throws Exception {
		
		Object[] objects=(Object[]) param;
		String[] strWaringModelIds=null;
		Map<String,Object> paramValueMap=null; 
		if(objects.length>=2)
		{
			if(objects[0] !=null ){
				strWaringModelIds = (objects[0]+"").split(",");
			}
			if(objects[1] !=null){ 
				paramValueMap=(Map<String,Object>)objects[1];
			}
			return paramObjectResultExecute(strWaringModelIds,paramValueMap);
		}
		throw new Exception("缺少模型");
	}

	private Object paramObjectResultExecute(String[] strWaringModelIds, Map<String, Object> paramValueMap) throws Exception {
		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		//1、查找预警模型 进行分析
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Rep_AnalyseModel.class);
		detachedCriteria.add(Restrictions.in("uuid", strWaringModelIds));
		detachedCriteria.add(Restrictions.eq("modelType", "1"));
		
		List<Rep_AnalyseModel> modelList = (List<Rep_AnalyseModel>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		for(Rep_AnalyseModel model:modelList){
			try{
				modelWaring(model);
			}catch(Exception e){
				throw new Exception("预警模型"+model.getModelName()+"["+model.getUuid()+"] 异常：",e);
			}
		}
		return new MessageResult("预警完成");
	}
	
	/**
	 * <p>方法描述: 模型预警</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param model
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-9-5 下午3:03:27</p>
	 */
	private void modelWaring(Rep_AnalyseModel model) throws Exception{
		//TODO 频度所引起的问题
		List<Rep_ItemWarningResult> waringRsList = new ArrayList<Rep_ItemWarningResult>();
		Set<String> inDate = new HashSet<String>();
		
		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		//1、查找预警模型 进行分析
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Rep_AnalyseModel_Items.class);
		detachedCriteria.add(Property.forName("model.uuid").eq(model.getUuid()));
		List<Rep_AnalyseModel_Items> items = (List<Rep_AnalyseModel_Items>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		//指标
		for(Rep_AnalyseModel_Items item : items){
			model.getItemIds().add(item.getItemInfo().getStrItemCode());
		}
		
		detachedCriteria = DetachedCriteria.forClass(Rep_AnalyseModel_Orgs.class);
		detachedCriteria.add(Property.forName("model.uuid").eq(model.getUuid()));
		List<Rep_AnalyseModel_Orgs> orgs = (List<Rep_AnalyseModel_Orgs>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		//机构
		for(Rep_AnalyseModel_Orgs org : orgs){
			model.getOrgIds().add(org.getInstInfo().getStrInstCode());
		}
		
		detachedCriteria = DetachedCriteria.forClass(Rep_AnalyseModel_Currs.class);
		detachedCriteria.add(Property.forName("model.uuid").eq(model.getUuid()));
		List<Rep_AnalyseModel_Currs> currs = (List<Rep_AnalyseModel_Currs>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		//币种
		for(Rep_AnalyseModel_Currs curr : currs){
			model.getCurrIds().add(curr.getCurrencyType().getStrCurrencyCode());
		}
		
		detachedCriteria = DetachedCriteria.forClass(Rep_AnalyseModel_ItemPros.class);
		detachedCriteria.add(Property.forName("model.uuid").eq(model.getUuid()));
		List<Rep_AnalyseModel_ItemPros> itemPros = (List<Rep_AnalyseModel_ItemPros>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		//指标属性
		for(Rep_AnalyseModel_ItemPros itemPro :itemPros){
			model.getItemProIds().add(itemPro.getItemProperty().getStrPropertyCode());
		}
		
		detachedCriteria = DetachedCriteria.forClass(Rep_AnalyseModel_Dimension1.class);
		detachedCriteria.add(Property.forName("model.uuid").eq(model.getUuid()));
		List<Rep_AnalyseModel_Dimension1> dimension1s = (List<Rep_AnalyseModel_Dimension1>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		//扩展维度1
		for(Rep_AnalyseModel_Dimension1 dimension1 : dimension1s){
			model.getDimension1Types().add(dimension1.getStrExtendDimension1());
		}
		
		detachedCriteria = DetachedCriteria.forClass(Rep_AnalyseModel_Dimension2.class);
		detachedCriteria.add(Property.forName("model.uuid").eq(model.getUuid()));
		List<Rep_AnalyseModel_Dimension2> dimension2s = (List<Rep_AnalyseModel_Dimension2>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		//扩展维度2
		for(Rep_AnalyseModel_Dimension2 dimension2 : dimension2s){
			model.getDimension2Types().add(dimension2.getStrExtendDimension2());
		}
		
		List<ItemData> itemDataList = getData(model);
		Map<String, Rep_ItemWarning> wnMap = warningListToMap(getItemWarning(model));
		//预警指标
		for(ItemData itemData : itemDataList){
			Rep_ItemWarning itemWn = wnMap.get(itemData.getItemInfo().getStrItemCode());
			if(itemWn != null){
				Rep_ItemWarningResult waringRs = new Rep_ItemWarningResult(itemData);
				inDate.add(waringRs.getDtDate());
				waringRs.setWarningRule(itemWn);
				waringRs.setModel(model);
				//指标默认正常[绿色]
				String warningVal = "0";
				//这儿有可能会出现异常
				Double value = Double.parseDouble(itemData.getStrValue());
				//正向指标处理 (越大越好)
				if("Z".equals(itemWn.getItemType())){
					//黄色触发值
					if(value<=itemWn.getCritical()||value<=itemWn.getCriticalUp())
						warningVal = "1";
					//红色预警
					if(value<=itemWn.getWorstUp())
						warningVal = "2";
				}
				//负向指标处理 (越小越好)
				if("F".equals(itemWn.getItemType())){
					//黄色触发值
					if(value>=itemWn.getCritical()||value>=itemWn.getCriticalDown())
						warningVal = "1";
					//红色预警
					if(value>=itemWn.getWorstDown())
						warningVal = "2";
				}
				//区间指标处理 (在最优区间越好)
				if("Q".equals(itemWn.getItemType())){
					//安全区
					if(value>=itemWn.getBestDown()&&value<=itemWn.getBestUp()){
						warningVal = "0";
					}
					//黄色预警区
					else if(value>=itemWn.getCriticalDown()&&value<=itemWn.getCriticalUp()){
						warningVal = "1";
					}
					//红色预警区
					else 
						warningVal = "2";
				}
				waringRs.setWarningVal(warningVal);
				waringRsList.add(waringRs);
			}
		}
		if(!waringRsList.isEmpty()){
			//根据模型id和时间删除历史预警数据
			IParamVoidResultExecute deleteDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(Rep_ItemWarningResult.class);
			detachedCriteria.add(Property.forName("model.uuid").eq(model.getUuid()));
			detachedCriteria.add(Restrictions.in("dtDate", inDate.toArray()));
			deleteDao.paramVoidResultExecute(new Object[] {detachedCriteria, null });
			//插入新数据
			IParamVoidResultExecute saveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveAllDao");
			saveDao.paramVoidResultExecute(new Object[] {waringRsList, null });
		}
	}
}
