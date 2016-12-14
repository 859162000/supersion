package dbgssystem.service.check;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class DBGSLogicPrimaryKeyCheck  extends BaseConstructor implements ICheck{

	public DBGSLogicPrimaryKeyCheck(){
		super();
	}
	
	public DBGSLogicPrimaryKeyCheck(Object baseObject){
		super(baseObject);
	}

	public MessageResult Check() throws Exception {
		MessageResult messageResult=new MessageResult();
		
		if(LogicParamManager.getLogicPrimaryKey()!=null){
			
			Object tObject=this.getBaseObject();
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(tObject.getClass());
			String FieldName="";
			String tName = this.getBaseObject().getClass().getName();
			String showInstanceName = ShowParamManager.getShowInstanceName();
			ShowInstance showInstance = ReflectOperation.getShowInstance(tName, showInstanceName);
			
			for (String field : LogicParamManager.getLogicPrimaryKey()) {
				detachedCriteria.add(Restrictions.eq(field, ReflectOperation.getFieldValue(tObject, field)));
				
				for (ShowField showField : showInstance.getShowFieldList()) {
					if(showField.getFieldName().equals(field)){
						if(field.equals("FOREIGNID")){
							String currentLevelLevel = SessionManager.getCurrentLevel();
							String autoID=SessionManager.getLevelIdValue(currentLevelLevel);
							IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
							Object obj = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"dbgssystem.dto.AutoDTO_DB_DBXX_JC", autoID, null});
							
							FieldName+=showField.getFieldShowName()+"："+ReflectOperation.getFieldValue(obj, "DBYWBH")+"，";
						}else{
							FieldName+=showField.getFieldShowName()+"："+ReflectOperation.getFieldValue(tObject, field)+"，";
						}
						break;
					}
				}
			}
			if(FieldName.length()>0){
				FieldName=FieldName.substring(0,FieldName.length()-1);
			}
			List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			if(ReflectOperation.getPrimaryKeyValue(tObject).equals("")){
				if(objectList.size()>0){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add(FieldName+"， 已存在该条数据");
				}
			}else{
				if(objectList.size()>0){
					for (Object object : objectList) {
						if(!ReflectOperation.getPrimaryKeyValue(object).equals(ReflectOperation.getPrimaryKeyValue(tObject))){
							if(objectList.size()>0){
								messageResult.setSuccess(false);
								messageResult.getMessageSet().add(FieldName+"， 已存在该条数据");
							}
						}
					}
				}
			}
		}
		return messageResult;
	}

}
