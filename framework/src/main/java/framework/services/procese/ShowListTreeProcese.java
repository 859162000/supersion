package framework.services.procese;

import java.lang.reflect.Field;

import javax.persistence.JoinColumn;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;

public class ShowListTreeProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {

		ShowSaveOrUpdate showSaveOrUpdate = null;
		ShowList showList = null;
		if(serviceResult.getClass().equals(ShowSaveOrUpdate.class)){
			showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		}
		else if(serviceResult.getClass().equals(ShowList.class)){
			showList = (ShowList)serviceResult;
		}
		
		String type = SessionManager.getTreeTypeName(RequestManager.getTName());
		if(type != null){
			Field[] fields = ReflectOperation.getReflectFields(Class.forName(RequestManager.getTName()));
			for(int j=0;j<fields.length;j++){
				if(fields[j].isAnnotationPresent(JoinColumn.class)){
					if(isExsitType(fields[j].getType(),type)){
						if(showSaveOrUpdate != null){
							for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
								if(showFieldValue.getShowField().getFieldName().equals(fields[j].getName())){
									
									JoinColumn joinColumn = fields[j].getAnnotation(JoinColumn.class);
									if(joinColumn.nullable()){
										IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
										Object object = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{RequestManager.getTName(),SessionManager.getTreeIdValue(RequestManager.getTName()),null});
										String fieldName = null;
										for(int k=0;k<fields.length;k++){
											if(!fields[k].getName().equals(fields[j].getName()) && fields[k].getType().equals(fields[j].getType())){
												fieldName = fields[k].getName();
												break;
											}
										}
										Object objectValue = null;
										if(fieldName == null){
											objectValue = object;
										}
										else{
											if(object != null){
												objectValue = ReflectOperation.getFieldValue(object, fieldName);
											}
										}
										if(objectValue != null){
											showFieldValue.setFieldValue(ReflectOperation.getPrimaryKeyValue(objectValue));
										}
									}
									
									break;
								}
							}
						}
						else if(showList != null){
							for(ShowFieldCondition showFieldCondition : showList.getShowCondition()){
								if(showFieldCondition.getFieldName().equals(fields[j].getName())){
									showList.getShowCondition().remove(showFieldCondition);
									break;
								}
							}
						}
					}
				}
			}
		}
		
		if(showSaveOrUpdate != null){
			return showSaveOrUpdate;
		}
		else if(showList != null){
			return showList;
		}
		else{
			return serviceResult;
		}
	}
	
	private boolean isExsitType(Class<?> typeClass,String type) throws ClassNotFoundException{
		if(type.indexOf(",") == -1){
			if(typeClass.equals(Class.forName(type))){
				return true;
			}
		}
		else{
			String[] types= type.split(",");
			for(int i=0;i<types.length;i++){
				if(typeClass.equals(Class.forName(types[i]))){
					return true;
				}
			}
		}
		
		return false;
	}
}
