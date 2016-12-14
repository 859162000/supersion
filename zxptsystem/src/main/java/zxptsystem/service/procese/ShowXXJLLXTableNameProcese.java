package zxptsystem.service.procese;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowValue;

public class ShowXXJLLXTableNameProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		
		if(RequestManager.getActionName().contains("FKBWShowInputUpdate") || RequestManager.getActionName().contains("FKBWShowCheckUpdateLevelFKBWAutoDTO")){
			Object tObject=RequestManager.getTOject();
			ShowSaveOrUpdate showSaveOrUpdate=(ShowSaveOrUpdate)serviceResult;
			for (ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()) {
				boolean boo = false;
				if(showFieldValue.getShowField().getFieldName().equals("XXJLLX")){
					
	        		Object xxjllx = ReflectOperation.getFieldValue(tObject,"XXJLLX");
	        		String operationName=GetOperationNames(xxjllx);
					
					for (ShowOperation showOperation : showSaveOrUpdate.getOperationList()) {
						if(showOperation.getOperationName().equals("报表类型")){
							showOperation.setOperationName(operationName);
							boo = true;
							break;
						}
					}
				}
				if(boo){
					break;
				}
			}
		}else{
			ShowList showList=(ShowList)serviceResult;
			
			for (int i = 0; i < showList.getValueTable().size(); i++) {
				List<ShowValue> showValueList = showList.getValueTable().get(i);
					
				String id = showValueList.get(0).getValue();
				String dtoName = RequestManager.getTName();
				
        		Object object =singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{dtoName, id, null});
        		Object xxjllx = ReflectOperation.getFieldValue(object,"XXJLLX");
        		String operationName=GetOperationNames(xxjllx);
        		
        		String operationValue = "";
        		for (Map.Entry<String, String> operation : ShowParamManager.getLinkedMap().entrySet()) {
        			if(operation.getKey().equals("报表类型")){
        				operationValue = operation.getValue();
        			}
        		}
				String[] operationValues = operationValue.split(",");
				String[] condtions=new String[]{};
				if(operationValues.length>=3){
					condtions = operationValues[3].split("\\|");
				}
				
				String strColor = "";
				
				boolean boo = false;
				List<ShowOperation> operationList = showList.getLinkedList().get(i);

				for (ShowOperation showOperation : operationList) {
					if(showOperation.getOperationName().equals("报表类型")){
						showOperation.setOperationName(operationName);
						
						String fieldName = "";
                        if(xxjllx == null || xxjllx.equals("")){
							continue;
						}
						else if(xxjllx.equals("03")){
							fieldName = "OneToMany_QY_02ZCFZB";
						}
						else if(xxjllx.equals("04")){
							fieldName = "OneToMany_QY_02LRJLRFPB";
						}
						else if(xxjllx.equals("05")){
							fieldName = "OneToMany_QY_02XJLLB";
						}
						else if(xxjllx.equals("43")){
							fieldName = "OneToMany_QY_07ZCFZB";
						}
						else if(xxjllx.equals("44")){
							fieldName = "OneToMany_QY_07LRJLRFPB";
						}
						else if(xxjllx.equals("45")){
							fieldName = "OneToMany_QY_07XJLLB";
						}
						else if(xxjllx.equals("46")){
							fieldName = "OneToMany_QY_SYDWZCFZB";
						}
						else if(xxjllx.equals("47")){
							fieldName = "OneToMany_QY_SYDWSRZCB";
						}
						

						Collection<?> objectValue = (Collection<?>) ReflectOperation.getFieldValue(object,fieldName);

						if (objectValue.size() != 0) {
							for (int j = 0; j < condtions.length; j++) {
								boolean isMarth = true; // red*RPTCheckType:1%2@RPTSendType:2
														// 不匹配，isMarth =
														// false，blue*RPTCheckType:2@RPTSendType:2
														// 匹配上后，isMarth的值不变，还是false
								String[] str = condtions[j].split("\\*");
								String strLogic = str[1];
								String[] strLogicFieldValues = strLogic.split("@");
								for (String strLogicFieldValue : strLogicFieldValues) {
									String strLogicField = strLogicFieldValue.split(":")[0];
									String[] strLogicValues = strLogicFieldValue.split(":")[1].split("%");

									boolean isFieldMarch = false;
									for (Object o : objectValue) {
										isMarth = true;
										String configValue = ReflectOperation.getFieldValue(o, strLogicField)
												.toString();
										for (String strLogicValue : strLogicValues) {
											if (configValue.equals(strLogicValue)) {
												isFieldMarch = true;
												break;
											}
										}
										if (isFieldMarch) {
											break;
										}
									}
									if (!isFieldMarch) {
										isMarth = false;
									}

									if (!isMarth) {
										break;
									}
								}
								if (isMarth) {
									strColor = str[0];
									showOperation.setColor(strColor);
									break;
								}
							}
						}
					
						
						boo = true;
						break;
					}
					if(boo){
						break;
					}
				}
				
			}
		}
		
		return serviceResult;
	}

	private String GetOperationNames(Object xxjllx) {
		String operationName = "";
		
		if(xxjllx==null || xxjllx.equals("")){
			operationName = "报表类型";
		}else if(xxjllx.equals("03")){
			operationName = "资产负债表";
		}
		else if(xxjllx.equals("04")){
			operationName = "利润及利润分配表";
		}
		else if(xxjllx.equals("05")){
			operationName = "现金流量表";
		}
		else if(xxjllx.equals("43")){
			operationName = "07版资产负债表";
		}
		else if(xxjllx.equals("44")){
			operationName = "07版利润及利润分配表";
		}
		else if(xxjllx.equals("45")){
			operationName = "07版现金流量表";
		}
		else if(xxjllx.equals("46")){
			operationName = "事业单位资产负债表";
		}
		else if(xxjllx.equals("47")){
			operationName = "事业单位收入支出表";
		}
		return operationName;
	}

}
