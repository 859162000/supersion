package extend.service.check;

import java.lang.reflect.Field;

import javax.persistence.Column;


import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.services.interfaces.MessageResult.ErrorField;
import framework.show.ShowField;
import framework.show.ShowInstance;
import extend.dto.*;

public class OracleFieldNameLengthCheck implements ICheck {
	
	public MessageResult Check() throws Exception
	{
		Object tObject = RequestManager.getTOject();
		ShowInstance showInstance = ReflectOperation.getShowInstance(tObject.getClass().getName(), ShowParamManager.getShowInstanceName());
		MessageResult messageResult = new MessageResult();
		Field[] fields = ReflectOperation.getReflectFields(tObject.getClass());
		for(int i=0;i<fields.length;i++){
			if(fields[i].getName().equals("strFieldName"))
			{
				try
				{
					ReportModel_Field reportModel_field=(ReportModel_Field)RequestManager.getTOject();
					String id=reportModel_field.getReportModel_Table().getAutoTableID();
					
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					id=((ReportModel_Table)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{ReportModel_Table.class.getName(),id, null})).getDataSource().getAutoDataSourceID();
					
					
					singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					AutoETL_DataSource autoETL_DataSource=(AutoETL_DataSource)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_DataSource.class.getName(),id, null});
					
					if(autoETL_DataSource.getStrDatabaseType().equals("1"))
					{
						return messageResult;
					}
				}
				catch(Exception ex)
				{
					AutoETL_ViewField autoETL_ViewField=(AutoETL_ViewField)RequestManager.getTOject();
					String id=autoETL_ViewField.getAutoETL_View().getAutoViewID();
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					id=((AutoETL_View)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_View.class.getName(),id, null})).getDataSource().getAutoDataSourceID();
					singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					AutoETL_DataSource autoETL_DataSource=(AutoETL_DataSource)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_DataSource.class.getName(),id, null});
					if(autoETL_DataSource.getStrDatabaseType().equals("1"))
					{
						return messageResult;
					}
				}
				Object value = ReflectOperation.getFieldValue(tObject, fields[i].getName());
				ShowField showField = null;
				for(ShowField tempShowField : showInstance.getShowFieldList()){
					if(tempShowField.getFieldName().equals(fields[i].getName())){
						showField = tempShowField;
						break;
					}
				}
				if(fields[i].getType().equals(String.class)){
					if(fields[i].isAnnotationPresent(Column.class)){
						if(30 < value.toString().length()){
							messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "长度应小于等于30"));
						}
					}
				}
			}
		}
		
		for(ErrorField errorField : messageResult.getErrorFieldList()){
			if(errorField.getColor().equals(MessageResult.COLORRED)){
				messageResult.setSuccess(false);
				break;
			}
		}
		return messageResult;
	}
}
