package framework.services.procese;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.ManyToOne;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.ExceptionLog;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowHeaderName;
import framework.show.ShowInstance;
import framework.show.ShowList;
import framework.show.ShowValue;

public class ShowListForComponentDetailProcese implements IProcese{



	@Override
	public Object Procese(Object serviceResult) throws Exception {
		if(serviceResult!=null && ShowList.class.isInstance(serviceResult))
		{
			ShowList showList=(ShowList)serviceResult;
			String selectAction=showList.getSelectActionName()+".action?type="+RequestManager.getTypeName()+"&id="+RequestManager.getLevelIdValue();
			showList.setSelectActionName(selectAction);
			String dtoName=RequestManager.getTName();
			String typeName=RequestManager.getTypeName();
			
			List<Field> fieldList=ReflectOperation.getOneToManyField(typeName);
			Field masterJoinField=null;
			for(Field field:fieldList)
			{
				Class<?> fieldType=ReflectOperation.getGenericClass(field.getGenericType());
				if(dtoName.equals(fieldType.getName()))
				{
					masterJoinField=field;
					break;
				}
			}
			if(masterJoinField!=null)
			{
				for(List<ShowValue> showValueList : showList.getValueTable()){
					for(ShowValue showValue : showValueList){
						showValue.setPostFieldName(masterJoinField.getName() + showValue.getPostFieldName());
					}
				}
				
				for(ShowHeaderName showHeaderName : showList.getShowNameList()){
					showHeaderName.setPostFieldName(masterJoinField.getName() + showHeaderName.getPostFieldName());
					if(ApplicationManager.getModelField().equals(showHeaderName.getSingleTag()))
					{
						String postFielName=showHeaderName.getPostFieldName();
						if(!StringUtils.isBlank(postFielName))
						{
							showHeaderName.setTag(getModelFieldTag(dtoName,postFielName.substring(1)));
						}
						
					}
				}
			}
			
			
		}
		return serviceResult;
		
	}
	private String getModelFieldTag(String tName,String fieldName)
	{
		
		String  url="";
		try {
			Field field = ReflectOperation.getReflectField(Class.forName(tName),fieldName);
			String modelDtoTName = "";
			if(field.isAnnotationPresent(ManyToOne.class)){
				modelDtoTName=field.getType().getName();
			}			
			
			url = "framework/ShowModelList-" + modelDtoTName + ".action?relation=";				
			String key = tName + "," + modelDtoTName;	
			if(ShowContext.getInstance().getShowEntityMap().get("ShowModelListEntity").containsKey(key)){
				url = url + ShowContext.getInstance().getShowEntityMap().get("ShowModelListEntity").get(key);
			}		
		
		} catch (Exception e) {
		   ExceptionLog.CreateLog(e);
		}
		
		
			
		return url;
	}
}

