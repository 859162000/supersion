package report.service.add;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.opensymphony.util.GUID;

import coresystem.dto.InstInfo;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;

import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IAdd;

public class ReportComponentAdd extends BaseConstructor implements IAdd{

	public ReportComponentAdd(){
		super();
	}
	
	public ReportComponentAdd(Object baseObject){
		super(baseObject);
	}
	public void Add() throws Exception {
		
		ReflectOperation.setFieldValue(this.getBaseObject(), "dtDate", TypeParse.parseDate("2014-12-31"));
		InstInfo instInfo = new InstInfo();
		instInfo.setStrInstCode("0000");
		ReflectOperation.setFieldValue(this.getBaseObject(), "autoID", GUID.generateGUID());
		ReflectOperation.setFieldValue(this.getBaseObject(), "instInfo", instInfo);
		ReflectOperation.setFieldValue(this.getBaseObject(), "RPTVerifyType", "1");
		ReflectOperation.setFieldValue(this.getBaseObject(), "RPTCheckType", "2");
		ReflectOperation.setFieldValue(this.getBaseObject(), "RPTSendType", "1");
		ReflectOperation.setFieldValue(this.getBaseObject(), "RPTSubmitStatus", "1");
		ReflectOperation.setFieldValue(this.getBaseObject(), "RPTFeedbackType", "1");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ReflectOperation.setFieldValue(this.getBaseObject(),"lastUpdateDate",simpleDateFormat.format(new Date()));
		
		List<Field> fieldList=ReflectOperation.getOneToManyField(this.getBaseObject().getClass().getName());
		for (Field field : fieldList) {
		    Collection<?> collection = (Collection<?>)ReflectOperation.getFieldValue(this.getBaseObject(), field.getName());
		    for(Object object : collection){
				ReflectOperation.setFieldValue(object, "RPTCheckType", "2");
				ReflectOperation.setFieldValue(object, "RPTSendType", "1");
				ReflectOperation.setFieldValue(object, "RPTFeedbackType", "1");
				ReflectOperation.setFieldValue(object, "FOREIGNID", this.getBaseObject());
		    }
		}
	}

}
