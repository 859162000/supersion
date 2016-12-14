package report.service.imps;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.CalculationExampleInfo;
import report.dto.CalculationRule;
import report.dto.CheckInstance;
import report.dto.CheckInstanceRule;
import report.dto.CheckRule;
import report.dto.CurrencyType;
import report.dto.ExampleInfoRule;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;

public class AjaxRequestInstanceService extends BaseService {

	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		String resultId="";
		String instanceId= ServletActionContext.getContext().getSession().get("instanceId").toString();
		String itemCode= ServletActionContext.getContext().getSession().get("itemCode").toString();
		String currCode = ServletActionContext.getContext().getSession().get("Cur").toString();
		String propCode = ServletActionContext.getContext().getSession().get("Prop").toString();
		String freq = ServletActionContext.getContext().getSession().get("Freq").toString();
		String extendDimensino1 = ServletActionContext.getContext().getSession().get("Kz1").toString();
		String extendDimensino2 = ServletActionContext.getContext().getSession().get("Kz2").toString();
		
		
		Object type = Class.forName(RequestManager.getTName()).newInstance();
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(RequestManager.getTName()));
		ItemInfo ii = new ItemInfo();
		ii.setStrItemCode(itemCode);
		
		CurrencyType currt = new CurrencyType();
		currt.setStrCurrencyCode(currCode);
		
		ItemProperty ipt = new ItemProperty();
		ipt.setStrPropertyCode(propCode);
		
		
		detachedCriteria.add(Restrictions.eq("itemInfo",ii));
		detachedCriteria.add(Restrictions.eq("currencyType",currt));
		detachedCriteria.add(Restrictions.eq("itemProperty",ipt));
		if(StringUtils.isNotBlank(freq))
			detachedCriteria.add(Restrictions.eq("strFreq",freq));
		if(StringUtils.isNotBlank(extendDimensino1))
			detachedCriteria.add(Restrictions.eq("strExtendDimension1",extendDimensino1));
		if(StringUtils.isNotBlank(extendDimensino2))
			detachedCriteria.add(Restrictions.eq("strExtendDimension2",extendDimensino2));
		
		List<Object> calculationRuleList= (List<Object>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		boolean isExists=false;
		if(type instanceof CalculationRule){
			
			
			CalculationExampleInfo calculationExampleInfo =new CalculationExampleInfo();
			calculationExampleInfo.setStrCalculationName(instanceId);
			
			for(Object obj : calculationRuleList){
				CalculationRule crt = (CalculationRule)obj;

				detachedCriteria = DetachedCriteria.forClass(ExampleInfoRule.class);
				
				detachedCriteria.add(Restrictions.eq("calculationRule", crt));
				detachedCriteria.add(Restrictions.eq("calculationExampleInfo", calculationExampleInfo));
				
				List<ExampleInfoRule> exampleInfoRuleList= (List<ExampleInfoRule>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
				if(exampleInfoRuleList.size()>0){
					isExists=true;
					resultId=ReflectOperation.getFieldValue(crt, "autoCalculationRuleID").toString();
					break;
				}
			}
			
			if(!isExists){
				CalculationRule newCr= new CalculationRule();
				CurrencyType ct = new CurrencyType();
				ct.setStrCurrencyCode(ServletActionContext.getContext().getSession().get("Cur").toString());
				newCr.setCurrencyType(ct);
				newCr.setEnddate("2050-01-01");				
				newCr.setIntOrder("0");
				newCr.setStrFreq(ServletActionContext.getContext().getSession().get("Freq").toString());
				newCr.setItemInfo(ii);
				ItemProperty ip = new ItemProperty();
				ip.setStrPropertyCode(ServletActionContext.getContext().getSession().get("Prop").toString());
				newCr.setItemProperty(ip);
				newCr.setStartdate("2000-01-01");
				newCr.setStrCalculationRuleName(ii.getStrItemCode());
				newCr.setStrDescription(null);
				newCr.setStrExtendDimension1(ServletActionContext.getContext().getSession().get("Kz1").toString());
				newCr.setStrExtendDimension2(ServletActionContext.getContext().getSession().get("Kz2").toString());
				
				ExampleInfoRule eir = new ExampleInfoRule();
				eir.setCalculationExampleInfo(calculationExampleInfo);
				eir.setCalculationRule(newCr);
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{newCr,null});
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{eir,null});
				
				resultId=newCr.getAutoCalculationRuleID();
			}
		}else if(type instanceof CheckRule){
			CheckInstance calculationExampleInfo =new CheckInstance();
			calculationExampleInfo.setInstanceName(instanceId);
			
			for(Object cr : calculationRuleList){
				detachedCriteria = DetachedCriteria.forClass(CheckInstanceRule.class);
				
				detachedCriteria.add(Restrictions.eq("checkRule.autoCalculationRuleID", ReflectOperation.getFieldValue(cr, "autoCalculationRuleID").toString()));
				detachedCriteria.add(Restrictions.eq("autoCheckInstanceID.instanceName", instanceId));
				
				List<CheckInstanceRule> exampleInfoRuleList= (List<CheckInstanceRule>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
				if(exampleInfoRuleList.size()>0){
					isExists=true;
					resultId=ReflectOperation.getFieldValue(cr, "autoCalculationRuleID").toString();
					break;
				}
			}
			
			if(!isExists){
				CheckRule newCr= new CheckRule();
				CurrencyType ct = new CurrencyType();
				ct.setStrCurrencyCode(ServletActionContext.getContext().getSession().get("Cur").toString());
				newCr.setCurrencyType(ct);
				newCr.setEnddate("2050-01-01");				
				newCr.setIntOrder("0");
				newCr.setItemInfo(ii);
				newCr.setCompareType("=");//比较类型默认为=
				newCr.setStrFreq(ServletActionContext.getContext().getSession().get("Freq").toString());
				ItemProperty ip = new ItemProperty();
				ip.setStrPropertyCode(ServletActionContext.getContext().getSession().get("Prop").toString());
				newCr.setItemProperty(ip);
				newCr.setStartdate("2000-01-01");
				newCr.setStrCalculationRuleName(ii.getStrItemCode());
				newCr.setStrDescription(null);
				newCr.setStrExtendDimension1(ServletActionContext.getContext().getSession().get("Kz1").toString());
				newCr.setStrExtendDimension2(ServletActionContext.getContext().getSession().get("Kz2").toString());
				
				CheckInstanceRule eir = new CheckInstanceRule();
				eir.setAutoCheckInstanceID(calculationExampleInfo);
				eir.setCheckRule(newCr);
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{newCr,null});
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{eir,null});
				
				resultId=newCr.getAutoCalculationRuleID();
			}
		}
		
		this.setServiceResult(resultId);
	}

}
