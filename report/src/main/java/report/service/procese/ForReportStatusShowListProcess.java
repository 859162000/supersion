package report.service.procese;

import java.util.List;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowInstanceValue;

public class ForReportStatusShowListProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		String tName = RequestManager.getTName();
		String showInstanceName = ShowParamManager.getShowInstanceName();
		
		ShowInstance showInstance = ReflectOperation.getShowInstance(tName, showInstanceName);
		ShowInstanceValue showInstanceValue = new ShowInstanceValue(showInstance);
		
		List<ShowFieldValue> showFieldValueList = showInstanceValue.getShowFieldValueList();
		for(ShowFieldValue showFieldValue :showFieldValueList){
			if(showFieldValue.getShowField().getFieldName().equals("RPTFeedbackType") || 
					showFieldValue.getShowField().getFieldName().equals("RPTSubmitStatus") || 
					showFieldValue.getShowField().getFieldName().equals("RPTVerifyType")|| 
					showFieldValue.getShowField().getFieldName().equals("RPTSendType")|| 
					showFieldValue.getShowField().getFieldName().equals("RPTCheckType") ||
					showFieldValue.getShowField().getFieldName().equals("instInfo") ||
					showFieldValue.getShowField().getFieldName().equals("operationUser")){
				showFieldValue.getShowField().setSingleTag(ApplicationManager.getSingleTagSelect());
			}
			else if(
					showFieldValue.getShowField().getFieldName().equals("FOREIGNID") ||
					showFieldValue.getShowField().getFieldName().equals("XZXH") ||
					showFieldValue.getShowField().getFieldName().equals("extend1") ||
					showFieldValue.getShowField().getFieldName().equals("extend2") ||
					showFieldValue.getShowField().getFieldName().equals("extend3") ||
					showFieldValue.getShowField().getFieldName().equals("extend4") ||
					showFieldValue.getShowField().getFieldName().equals("extend5") 
					){
					showFieldValue.getShowField().setListVisible(false);
			}
			if(RequestManager.getActionName().contains("ShowListLevelAutoDTOMX") || RequestManager.getActionName().contains("SHShowListLevelAUTODTOSHMX")){
				String tempTableName=tName.substring(tName.indexOf("AutoDTO_")+8);
				if(!tempTableName.startsWith("GR_") && !tempTableName.startsWith("DB_")){
					if(showFieldValue.getShowField().getFieldName().equals("extend2")){
						showFieldValue.getShowField().setListVisible(true);
						showFieldValue.getShowField().setFieldShowName("业务发生日期");
					}
				}else if(tempTableName.startsWith("DB_")){
					if(showFieldValue.getShowField().getFieldName().equals("extend2")){
						showFieldValue.getShowField().setListVisible(true);
						showFieldValue.getShowField().setFieldShowName("数据报告日期");
					}
				}
			}
		}
		return serviceResult;
	}

}
