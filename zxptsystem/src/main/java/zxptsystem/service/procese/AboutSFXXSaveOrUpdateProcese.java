package zxptsystem.service.procese;

import java.lang.reflect.Field;
import java.util.List;
import zxptsystem.dto.UserModel;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class AboutSFXXSaveOrUpdateProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception{
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		Object valueZJHM=null;
		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
			if(showFieldValue.getShowField().getFieldName().equals("FOREIGNID")){
				valueZJHM=ReflectOperation.getFieldValue(showFieldValue.getFieldValue(), "ZJHM");
				break;
			}
		}
		//从UserModel里查询出对应的数据
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		UserModel userModel  = (UserModel)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{UserModel.class.getName(),valueZJHM,null}); 
		List<Field> fieldList=ReflectOperation.getColumnFieldList(UserModel.class);
		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
			for(Field field : fieldList){
				if(showFieldValue.getShowField().getFieldName().equals(field.getName())){
					Object value=ReflectOperation.getFieldValue(userModel, field.getName());
					Object showValue=showFieldValue.getFieldValue();
					if(showValue ==null &&value!=null){
						showFieldValue.setFieldValue(value);
					}
					break;
				}
			}
		}

		return serviceResult;
	}
}
