package framework.services.procese;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ShowUpdateProcese extends ShowSaveProcese{


	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {

		if(serviceResult == null){
			return super.Procese(serviceResult);
		}
		
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)super.Procese(null);
		
		Class<?> type = serviceResult.getClass();
		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();
		
		for(ShowFieldValue showFieldValue : showFieldValueList){
			ShowField showField = showFieldValue.getShowField();
			
			if(LogicParamManager.getServiceResult() == null){
				showFieldValue.setReadOnly(showField.isUpdateReadOnly());
			}

			Field field = ReflectOperation.getReflectField(type,showField.getFieldName());
			if(field != null){
				Object value = ReflectOperation.getFieldValue(serviceResult, field.getName());
				if(value != null){
					if(ReflectOperation.isBaseType(value)){
						if(value.getClass().equals(java.util.Date.class) || value.getClass().equals(java.sql.Date.class)){
							showFieldValue.setFieldValue(TypeParse.parseString((java.util.Date)value, "yyyy-MM-dd"));
						}
						else{
							try{
								if(showField.isThousandth()){ 
									if(!StringUtils.isBlank(value.toString())){
										BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
										value=bdCurrencyValue.toString();
									}
								}
								if(showField.getReportUnitCode()!=0){
									if(showField.getReportUnitCode()==1){//元
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("1"));
										}
									}
									else if(showField.getReportUnitCode()==2){//十元
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("10"));
										}
									}
									else if(showField.getReportUnitCode()==3){//百元
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("100"));
										}
									}
									else if(showField.getReportUnitCode()==4){//千元
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("1000"));
										}
									}
									else if(showField.getReportUnitCode()==5){//万元
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("10000"));
										}
									}
									else if(showField.getReportUnitCode()==6){//十万
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("100000"));
										}
									}
									else if(showField.getReportUnitCode()==7){//百万
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("1000000"));
										}
									}
									else if(showField.getReportUnitCode()==8){//千万
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("10000000"));
										}
									}
									else if(showField.getReportUnitCode()==9){//亿
										if(!StringUtils.isBlank(value.toString())){
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value=bdCurrencyValue.divide(new BigDecimal("100000000"));
										}
									}
								}
								showFieldValue.setFieldValue(value.toString());
							}catch(Exception ex){
								showFieldValue.setFieldValue(value.toString());
							}
							
						}
					}
					else{
						if(showField.getSingleTag().equals(ApplicationManager.getSingleTagMultipleSelect())){
							Field idListField = ReflectOperation.getIdListField(serviceResult.getClass().getName(), field);
							IColumn idListFieldColumn = idListField.getAnnotation(IColumn.class);
							Class<?> tType = ReflectOperation.getGenericClass(field.getGenericType());
							Field targetField = ReflectOperation.getFieldByName(tType,idListFieldColumn.mappedBy());
							Field PrimaryKeyField = ReflectOperation.getPrimaryKeyField(targetField.getType());
							Set<Object> objectSet = (Set<Object>)value;
							String[] values = new String[objectSet.size()];
							int i = 0;
							for(Object object : objectSet){
								Object value1 = ReflectOperation.getFieldValue(object,targetField.getName());
								Object value2 = ReflectOperation.getFieldValue(value1,PrimaryKeyField.getName());
								values[i] = value2.toString();
								i++;
							}
							showFieldValue.setFieldValue(values);
						}
						else if(showField.getSingleTag().equals(ApplicationManager.getSingleTagSelect()) 
								|| showField.getSingleTag().equals(ApplicationManager.getSingleTagHidden())
								|| showField.getSingleTag().equals(ApplicationManager.getModelField())){
							Object value1 = null;
							if(showField.getFieldTargetPrimaryKey()!=null){
								value1 = ReflectOperation.getFieldValue(value,showField.getFieldTargetPrimaryKey());
							}
							if(value1 != null){
								showFieldValue.setFieldValue(value1.toString());
							}
						}
					}
				}
			}
		}
		
		showSaveOrUpdate.setId(ReflectOperation.getPrimaryKeyValue(serviceResult).toString());
		
		if(LogicParamManager.getServiceResult() != null){
			Object paramServiceResult = LogicParamManager.getServiceResult();
			if(paramServiceResult.getClass().equals(MessageResult.class)){
				MessageResult messageResult = (MessageResult)paramServiceResult;
				
				for(ErrorField errorField : messageResult.getErrorFieldList()){
					for(ShowFieldValue showFieldValue : showFieldValueList){
						if(errorField.getFieldName()!=null && showFieldValue.getShowField().getFieldName()!=null){
							if(errorField.getFieldName().equals(showFieldValue.getShowField().getFieldName())){
								showFieldValue.setShowColor(errorField.getColor());
								
								String message = errorField.getMessage();
								if(message.startsWith(showFieldValue.getShowField().getFieldShowName())){
									if(message.length() >= showFieldValue.getShowField().getFieldShowName().length() + 2){
										//message = message.substring(showFieldValue.getShowField().getFieldShowName().length() + 1);
										message = message.replaceFirst(showFieldValue.getShowField().getFieldShowName(),"");
										message=message.replace(":", "").replace("：", "");
										if(message.getBytes().length==24){
											message= "." + message;
										}
									}
								}
 								showFieldValue.setCheckMessage(message);
								break;
							}
						}
						
					}
				}
			}
		}
		
		return showSaveOrUpdate;
	}

}
