package framework.services.procese;

import java.lang.reflect.Field;

import javax.persistence.ManyToOne;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowContext;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;

public class ShowModelListProceses implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		if(serviceResult instanceof ShowSaveOrUpdate){
			ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
			for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
				if(showFieldValue.getShowField().getSingleTag().equals("modelField")){
					Field  field = ReflectOperation.getReflectField(Class.forName(RequestManager.getTName()), showFieldValue.getShowField().getFieldName());
					String tName = RequestManager.getTName();
					String modelDtoTName = "";
					
					if(field.isAnnotationPresent(ManyToOne.class))
					{
						modelDtoTName=field.getType().getName();
					}			
					else
					{
						if(("zxptsystem.dto.QYZXCreditReportInfo".equals(tName)||
								"zxptsystem.dto.GRZXCreditReportInfo".equals(tName))&&
								field.getName().equals("strDocName"))
						{						
							modelDtoTName="zxptsystem.dto.EIS_AuthorizationDocumentInfo";						
						}					
					}
					String url = "framework/ShowModelList-" + modelDtoTName + ".action?relation=";				
					String key = RequestManager.getTName() + "," + modelDtoTName;	
					if(null!= ShowContext.getInstance().getShowEntityMap().get("ShowModelListEntity")
							&& ShowContext.getInstance().getShowEntityMap().get("ShowModelListEntity").containsKey(key)){
						url = url + ShowContext.getInstance().getShowEntityMap().get("ShowModelListEntity").get(key);
					}else{
						if(("zxptsystem.dto.QYZXCreditReportInfo".equals(tName)||
								"zxptsystem.dto.GRZXCreditReportInfo".equals(tName))&&
								field.getName().equals("strDocName"))
						{						
							url = url +"strDocId.id-0@strDocName-6";						
						}
						else
						{
							url = url +showFieldValue.getShowField().getFieldName()+"."+showFieldValue.getShowField().getFieldTargetPrimaryKey()+ "-0";
						}
					}
					showFieldValue.setTag(url);				
				}
			}	
		}else if(serviceResult instanceof ShowList){
			ShowList showList = (ShowList)serviceResult;
			for(ShowFieldCondition obj:showList.getShowCondition()){
				if(obj.getSingleTag().equals("modelField")){
					Field  field = ReflectOperation.getReflectField(Class.forName(RequestManager.getTName()), obj.getFieldName());
					String modelDtoTName = "";
					if(field.isAnnotationPresent(ManyToOne.class))
					{
						modelDtoTName=field.getType().getName();
					}			
					String url = "framework/ShowModelList-" + modelDtoTName + ".action?relation=";				
					String key = RequestManager.getTName() + "," + modelDtoTName;	
					if(null!= ShowContext.getInstance().getShowEntityMap().get("ShowModelListEntity")
							&& ShowContext.getInstance().getShowEntityMap().get("ShowModelListEntity").containsKey(key)){
						url = url + ShowContext.getInstance().getShowEntityMap().get("ShowModelListEntity").get(key);
					}else{
						url=url+obj.getFieldName()+"."+obj.getTarget()+ "-0";
					}
					obj.setTag(url);		
				}
			}
		}		
		return serviceResult;
	}

}
