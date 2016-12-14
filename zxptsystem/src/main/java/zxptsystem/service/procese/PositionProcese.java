package zxptsystem.service.procese;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowHeaderName;
import framework.show.ShowInstance;
import framework.show.ShowList;

public class PositionProcese implements IProcese {

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {

		boolean isShowNameList = false;
		String isNewLine = "<br>"; 
		String positionMessage = "";
		String INSTINFO = "INSTINFO";
		Boolean boo = false;
		ShowList showList=(ShowList)serviceResult;
		
		String tName = RequestManager.getTName().replace("_Condition", "");
		List<Field> fieldList= ReflectOperation.getColumnFieldList(tName);
		

		for (int i = 0; i < fieldList.size(); i++) {
			
			String fieldName = fieldList.get(i).getName();
			
			if(fieldName.equals(INSTINFO)){
				
				Object instInfoCode = ((Map<String, Object>)ServletActionContext.getContext().getSession()).get(RequestManager.getActionName()+"instinfo");
				Object positionFieldValue = ServletActionContext.getContext().getSession().get(tName+"."+fieldName);
				
				if(positionFieldValue!=null && !instInfoCode.equals(positionFieldValue)){
					positionMessage = "";
					boo = true;
					break;
				}
			}
		}
		
		if(boo){
			for (int i = 0; i < fieldList.size(); i++) {
				String fieldName = fieldList.get(i).getName();
				ServletActionContext.getContext().getSession().remove(tName+"."+fieldName);
			}
		}
		
		for (int i = 0; i < fieldList.size(); i++) {
			
			if(fieldList.get(i).getName().equals(INSTINFO) || boo){
				continue;
			}
			
			String fieldName = fieldList.get(i).getName();
			
			ShowInstance showInstance = ReflectOperation.getShowInstance(tName, ShowParamManager.getShowInstanceName());
			List<ShowField> showFieldList = showInstance.getShowFieldList();
			
			Object positionFieldValue = ServletActionContext.getContext().getSession().get(tName+"."+fieldName);
			
			
			if(positionFieldValue != null && !positionFieldValue.equals("")){
				
				if(isShowNameList){
					
					List<ShowHeaderName> showHeaderNameList = showList.getShowNameList();
					
					for (int j = 0; j < showHeaderNameList.size(); j++) {
						
						String postFieldName = showList.getShowNameList().get(j).getPostFieldName();
						
						if(postFieldName != null && postFieldName.equals("."+fieldName) ){
							
							positionMessage = positionMessage+showList.getShowNameList().get(j).getShowName()+"："+positionFieldValue.toString()+isNewLine;
							break;
						}
					}
				}
				else{
					
					for (int j = 0; j < showFieldList.size(); j++) {
						
						String postFieldName = showFieldList.get(j).getFieldName();
						
						if(postFieldName != null && postFieldName.equals(fieldName)){
							
							positionMessage = positionMessage+showFieldList.get(j).getFieldShowName()+"："+positionFieldValue.toString()+isNewLine;
							break;
						}
					}
				}
			}
		}
		
		if(!positionMessage.equals("")){
			positionMessage = positionMessage.substring(0, positionMessage.length()-(isNewLine.length()));
		}

		((Map<String,Object>)ServletActionContext.getContext().get("request")).put("position_view", positionMessage);
		return serviceResult;
	}

}
