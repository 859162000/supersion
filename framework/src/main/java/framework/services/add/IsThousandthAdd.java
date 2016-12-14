package framework.services.add;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.apache.commons.lang.xwork.StringUtils;
import framework.helper.ReflectOperation;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IAdd;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class IsThousandthAdd extends BaseConstructor implements IAdd{

	public IsThousandthAdd(){
		super();
	}
	
	public IsThousandthAdd(Object baseObject){
		super(baseObject);
	}
	
	public void Add() throws Exception {
		
		Object tObject = this.getBaseObject();
		Class<?> type = Class.forName(this.getBaseObject().getClass().getName());
		ShowInstance showInstance = ReflectOperation.getShowInstance(this.getBaseObject().getClass().getName(), ShowParamManager.getShowInstanceName());
		
		for(ShowField showField : showInstance.getShowFieldList()){
			Field field = ReflectOperation.getReflectField(type,showField.getFieldName());
			Object objValue = ReflectOperation.getFieldValue(tObject, field.getName());
			try{
				if(showField.isThousandth()){
					if(objValue!=null){
						if(!StringUtils.isBlank(objValue.toString())){
							objValue=objValue.toString().replace(",","");
							ReflectOperation.setFieldValue(tObject,showField.getFieldName(),objValue);
						}
					}
				}
				if(showField.getReportUnitCode()!=0){
					if(showField.getReportUnitCode()==1){//元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								BigDecimal UnitConst = new BigDecimal("1");
								objValue=bdCurrencyValue.multiply(UnitConst);
							}
						}
					}
					else if(showField.getReportUnitCode()==2){//十元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								objValue=bdCurrencyValue.multiply( new BigDecimal("10"));
							}
						}
					}
					else if(showField.getReportUnitCode()==3){//百元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								objValue=bdCurrencyValue.multiply(new BigDecimal("100"));
							}
						}
					}
					else if(showField.getReportUnitCode()==4){//千元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								objValue=bdCurrencyValue.multiply(new BigDecimal("1000"));
							}
						}
					}
					else if(showField.getReportUnitCode()==5){//万元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								objValue=bdCurrencyValue.multiply(new BigDecimal("10000"));
							}
						}
					}
					else if(showField.getReportUnitCode()==6){//十万元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								objValue=bdCurrencyValue.multiply(new BigDecimal("100000"));
							}
						}
					}
					else if(showField.getReportUnitCode()==7){//百万元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								objValue=bdCurrencyValue.multiply(new BigDecimal("1000000"));
							}
						}
					}
					else if(showField.getReportUnitCode()==8){//千万元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								objValue=bdCurrencyValue.multiply(new BigDecimal("10000000"));
							}
						}
					}
					else if(showField.getReportUnitCode()==9){//亿元
						if(objValue!=null){
							if(!StringUtils.isBlank(objValue.toString())){
								BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
								objValue=bdCurrencyValue.multiply( new BigDecimal("100000000"));
							}
						}
					}
					if(objValue != null){
						ReflectOperation.setFieldValue(tObject,showField.getFieldName(),objValue);
					}
				}
			}catch(Exception ex){
				ReflectOperation.setFieldValue(tObject,showField.getFieldName(),objValue);
			}
			
			if(showField.isEncrypt()){
				if(objValue!=null){
					if(!StringUtils.isBlank(objValue.toString())){
						objValue=objValue.toString().replace(",","");
						ReflectOperation.setFieldValue(tObject,showField.getFieldName(),objValue);
					}
				}
			}
			
		}
	}
}
