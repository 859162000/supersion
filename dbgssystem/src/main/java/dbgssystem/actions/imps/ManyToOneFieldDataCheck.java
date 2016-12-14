package dbgssystem.actions.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.struts2.ServletActionContext;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.services.interfaces.MessageResult.ErrorField;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class ManyToOneFieldDataCheck extends BaseConstructor implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		IParamObjectResultExecute SingleObjectFindByIdDao =	(IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		
		ShowInstance showInstance =null;
		if(ServletActionContext.getContext() == null){
			showInstance = ReflectOperation.getDefaultShowInstance(this.getBaseObject().getClass().getName());
		}
		else{
			showInstance = ReflectOperation.getShowInstance(this.getBaseObject().getClass().getName(), ShowParamManager.getShowInstanceName());
		}
		
		List<Field> manyToOneField=getManyToOneField(this.getBaseObject());
		for(Field field:manyToOneField){
			Object val = ReflectOperation.getFieldValue(this.getBaseObject(), field.getName());
			IColumn column = field.getAnnotation(IColumn.class);
			JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
			
			if(null==val){
				if((null!=column && !column.isNullable()) || (null!=joinColumn && !joinColumn.nullable())){
					messageResult.setSuccess(false);
					String msg=":不能为空!";
					for(ShowField showField : showInstance.getShowFieldList()){
						if(showField.getFieldName().equals(field.getName())){
							msg=showField.getFieldShowName()+msg;
							break;
						}
					}
					ErrorField e = new ErrorField(field.getName(), "red", msg);					
					messageResult.getErrorFieldList().add(e);
				}
			}else{
				Object foreignObj= ReflectOperation.getFieldValue(this.getBaseObject(),field.getName());
				
				RequestManager.setTName(foreignObj.getClass().getName());
				RequestManager.setId(ReflectOperation.getPrimaryKeyValue(foreignObj));
				Object tObject = SingleObjectFindByIdDao.paramObjectResultExecute(null);
				if(tObject==null){
					messageResult.setSuccess(false);
					ErrorField e = new ErrorField(field.getName(), "red", "对象外键约束("+field.getName()+")错误！");	
					messageResult.getErrorFieldList().add(e);
				}
			}
		}
		
		return messageResult;
	}
	public ManyToOneFieldDataCheck(Object baseObject){
		super(baseObject);
	}
	public ManyToOneFieldDataCheck(){
		super();
	}
	private List<Field> getManyToOneField(Object obj){
		List<Field> manyToOneField = new ArrayList<Field>();		
		Field[] fieldList = ReflectOperation.getReflectFields(obj.getClass());
		for(int i=0;i<fieldList.length;i++){
			if(fieldList[i].isAnnotationPresent(ManyToOne.class)){
				manyToOneField.add(fieldList[i]);
			}
		}
		return manyToOneField;
	}
}
