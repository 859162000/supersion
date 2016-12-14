package dbgssystem.service.check;

import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class CheckDBJCReport implements ICheck{

	public MessageResult Check() throws Exception {
		
		MessageResult messageResult=new MessageResult();
		String tName=RequestManager.getTName();
		String logicPrimaryKey = ShowContext.getInstance().getShowEntityMap().get("DBGSSystemLogicPrimaryKey").get(tName);

		if(logicPrimaryKey!=null){
			String[] logicPrimaryKeys=logicPrimaryKey.split(",");
			String showInstanceName = ShowParamManager.getShowInstanceName();
			ShowInstance showInstance = ReflectOperation.getShowInstance(tName, showInstanceName);
			
			//excle与excle比较
			String FieldNameForExcle="";
			List<Object> insertDataList = (List<Object>)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("insertDataList");
			for (int i = 0; i < insertDataList.size()-1; i++) {
				boolean objectIsSame = false;
				for (int j = i+1; j < insertDataList.size(); j++) {
					boolean logicIsSame = true;
					for (String str : logicPrimaryKeys) {
						if(!ReflectOperation.getFieldValue(insertDataList.get(i), str).equals(ReflectOperation.getFieldValue(insertDataList.get(j), str))){
							logicIsSame = false;
							break;
						}
					}
					if(logicIsSame){
						objectIsSame = true;
						// 添加错误提示内容
						for (String str : logicPrimaryKeys) {
							for (ShowField showField : showInstance.getShowFieldList()) {
								if(showField.getFieldName().equals(str)){
									FieldNameForExcle+=showField.getFieldShowName()+"："+ReflectOperation.getFieldValue(insertDataList.get(i), str)+"，";
									break;
								}
							}
						}
						break;
					}
				}
				if(objectIsSame){
					if(FieldNameForExcle.length()>0){
						FieldNameForExcle=FieldNameForExcle.substring(0,FieldNameForExcle.length()-1);
					}
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("Excle中："+FieldNameForExcle+"， 逻辑主键重复");
					break;
				}
			}
			for (int i = 0; i < insertDataList.size(); i++) {
				//excle与数据库比较
				String FieldName="";
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RequestManager.getTOject().getClass());
				
				for (String field : logicPrimaryKeys) {
					detachedCriteria.add(Restrictions.eq(field, ReflectOperation.getFieldValue(insertDataList.get(i), field)));
					
					for (ShowField showField : showInstance.getShowFieldList()) {
						if(showField.getFieldName().equals(field)){
							FieldName+=showField.getFieldShowName()+"："+ReflectOperation.getFieldValue(insertDataList.get(i), field)+"，";
							break;
						}
					}
				}
				List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(objectList.size()>0){
					if(FieldName.length()>0){
						FieldName=FieldName.substring(0,FieldName.length()-1);
					}
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("excle与数据库中："+FieldName+"，逻辑主键重复");
				}
	
			}
		}

		return messageResult;
	}
}
