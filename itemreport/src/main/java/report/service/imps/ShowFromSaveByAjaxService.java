package report.service.imps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.CalculationRule;
import report.dto.CheckRule;
import report.dto.ItemRuleDetail;
import report.dto.ItemsCalculation;
import report.service.expression.ExpressionContextKey;
import report.service.expression.interfaces.IExpressionBuilder;
import report.service.expression.interfaces.IExpressionCalculater;
import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseDService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

public class ShowFromSaveByAjaxService extends BaseDService {
	static String WHERE_CONDTION_FIELD_SPIT="$";
	static String WHERE_CONDTION_FIELD_COLUMN_SPIT="#";
	static String WHERE_CONDTION_FIELD_COLUMN_TYPE_SPIT="|";
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		MessageResult messageResult = new MessageResult();
		String type=RequestManager.getTName();
		String vs=((Map<String,Object>)ServletActionContext.getContext().get("request")).get("vs").toString();
		String id= ServletActionContext.getContext().getSession().get("CalculationRuleID").toString();
		List<Object> itemsCalculationList= new ArrayList<Object>();
		
		Object[] obj = (Object[])RequestManager.getTOject();
		int index=1;
		Object cr ;
		if(Class.forName(type).newInstance() instanceof ItemsCalculation){
			cr = new CalculationRule();
		}else{
			cr = new CheckRule();
		}
		ReflectOperation.setFieldValue(cr, "autoCalculationRuleID", id);
		
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(cr.getClass());
		detachedCriteria1.add(Restrictions.eq("autoCalculationRuleID",id));
		
		cr= ((List<Object>)dao.paramObjectResultExecute(new Object[]{detachedCriteria1, null})).get(0);
		for(Object o : obj){
			java.util.HashMap<String,String> map= (java.util.HashMap<String,String>)o;
			Object ic = Class.forName(type).newInstance();
			String strItemType=map.get("type").toString();
			ReflectOperation.setFieldValue(ic, "strItemType", strItemType);
			ReflectOperation.setFieldValue(ic, "calculationRule", cr);
			
			if(strItemType.equals("item")){
				String str = "Time="+map.get("time")+"&"+"ItemCode="+map.get("itemCode")+"&"+"Extend1="+map.get("extend1")+"&"
				+"Extend2="+map.get("extend2")+"&"+"dtDate="+map.get("dtDate")+"&"+"instInfo="+map.get("instInfo")+"&"
				+"Curr="+map.get("curr")+"&"+"Property="+map.get("property")+"&"+"Freq="+map.get("freq");
	
				ReflectOperation.setFieldValue(ic, "strItemValue", str);
			}else if(strItemType.equals("symbol")){
				ReflectOperation.setFieldValue(ic, "strItemValue", map.get("op"));
			}else if(strItemType.equals("const")){
				ReflectOperation.setFieldValue(ic, "strItemValue", map.get("constv"));
			}else if(strItemType.equals("sql")){
				ReflectOperation.setFieldValue(ic, "strItemValue", map.get("sqlContent"));
			}else if(strItemType.equals("detail")){
				///////////////////////////
				Map<String,String> fieldType=ShowContext.getInstance().getShowEntityMap().get("fieldType");
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
				detachedCriteria.add(Restrictions.eq("strTableName",map.get("acctableName")));
				ReportModel_Table reportModel_Table = ((List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null})).get(0);
				
				detachedCriteria = DetachedCriteria.forClass(ReportModel_Field.class);
				detachedCriteria.add(Restrictions.eq("reportModel_Table",reportModel_Table));
				List<ReportModel_Field> fieldList=(List<ReportModel_Field>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				Map<String,String> fieldTypeId = new HashMap<String,String>();
				for(ReportModel_Field reportModel_Field:fieldList){
					fieldTypeId.put(reportModel_Field.getStrFieldName(), reportModel_Field.getStrFieldType());
				}
				
				String whereCondition = map.get("WhereCondition");
				String[] rows = whereCondition.split("\\"+WHERE_CONDTION_FIELD_SPIT);
				String[] newList= new String[rows.length];
				int i=0;
				for(String row:rows){
					String[] cells = row.split(WHERE_CONDTION_FIELD_COLUMN_SPIT);
					if(!cells[0].equals("-1") && cells[0].indexOf(WHERE_CONDTION_FIELD_COLUMN_TYPE_SPIT)<0){
						cells[0]+=WHERE_CONDTION_FIELD_COLUMN_TYPE_SPIT+fieldType.get(fieldTypeId.get(cells[0].toString())).toString();
					}
					newList[i]=StringUtils.join(cells, WHERE_CONDTION_FIELD_COLUMN_SPIT);
					i++;
				}
				///////////////////////////
				
				String str="{"+map.get("acctableName")+":"+map.get("CalcType")+"("+map.get("CalcField")+")["+StringUtils.join(newList,WHERE_CONDTION_FIELD_SPIT)+"]}";
				ReflectOperation.setFieldValue(ic, "strItemValue", str);
			}
			ReflectOperation.setFieldValue(ic, "intOrder", index);
			itemsCalculationList.add(ic);
			index++;
		}
		
		List<ItemRuleDetail> itemRuleDetailList = CovertToExpression(itemsCalculationList);	
		IExpressionBuilder builder=(IExpressionBuilder)FrameworkFactory.CreateBean("jsExpressionBuilder");
		Map<String,Object> context=new HashMap<String,Object>();
		context.put(ExpressionContextKey.DATA_PARAM_KEY,itemRuleDetailList);
		builder.setContext(context);
		String exp=builder.build();
		//validate
		IExpressionCalculater calculater2=(IExpressionCalculater)FrameworkFactory.CreateBean("jsExpCheckCal");
		try
		{
			calculater2.calculate(exp);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			messageResult.setMessage("校验失败:"+ex.getMessage());
			messageResult.setSuccess(false);
		}
		//save
		try{
			if(messageResult.isSuccess() && vs.equals("S")){
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(type));
				detachedCriteria.add(Restrictions.eq("calculationRule",  cr));
				IParamVoidResultExecute delDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
				delDao.paramVoidResultExecute(new Object[]{detachedCriteria, null});
							    	
				IParamVoidResultExecute insertDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				insertDao.paramVoidResultExecute(new Object[]{itemsCalculationList,null});	
				ReflectOperation.getFieldValue(cr, "strExpression");	//延迟加载的字段，需要先获取一次，update语句里的更新字段中才能包含此延迟加载字段
				ReflectOperation.setFieldValue(cr, "strExpression", exp);
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{cr,null});
			}
		}
		catch(Exception ex1){
			messageResult.setMessage("保存失败:"+ex1.getMessage());
			messageResult.setSuccess(false);
		}
		this.setServiceResult(messageResult);
	}

	private List<ItemRuleDetail> CovertToExpression(List<Object> itemsCalculationList){
		List<ItemRuleDetail> itemRuleDetailList = new ArrayList<ItemRuleDetail>();
		for(Object o :itemsCalculationList){
			try {
				ItemRuleDetail ird= new ItemRuleDetail(ReflectOperation.getFieldValue(o, "strItemType").toString()
						,ReflectOperation.getFieldValue(o, "strItemValue").toString()
						,ReflectOperation.getFieldValue(o, "intOrder").toString());
				itemRuleDetailList.add(ird);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return itemRuleDetailList;
	}
}
