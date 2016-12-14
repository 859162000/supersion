package framework.services.check;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.services.interfaces.MessageResult.ErrorField;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class ComponentAllCheck extends BaseConstructor implements ICheck {

	public ComponentAllCheck() {
		super();
	}

	public ComponentAllCheck(Object baseObject) {
		super(baseObject);
	}

	private List<String> getCheckList(String dtoName) {
		List<String> checkList = null;
		if (LogicParamManager.getDtoCheckMap() != null) {
			checkList = LogicParamManager.getDtoCheckMap().get(dtoName);
		} else {
			checkList = LogicParamManager.getDefaultComponentCheckClassList();
		}

		if (checkList == null) {
			checkList = new ArrayList<String>();
		}
		return checkList;

	}

	private MessageResult messageResult = new MessageResult();
	private MessageResult currentResult = null;
	private List<ErrorField> errorFieldList = new ArrayList<ErrorField>();
	private Boolean isSetErrorDtoName = false;

	public MessageResult Check() throws Exception {

		Object tObject = RequestManager.getTOject();
		String tName = RequestManager.getTName();
		ShowInstance showInstance = getShowInstance(tName);
		List<ICheck> checkList = null;
		
		if (tObject != null) {
			checkList = createCheckClass(tName);
			for (ICheck check : checkList) {
				if(BaseConstructor.class.isInstance(check))
				{
					((BaseConstructor)check).setBaseObject(tObject);
				}
				
				currentResult = check.Check();

				this.setAllErrorFieldList(this.getBaseObject());
			}
			this.isSetErrorDtoName = false;

			List<Field> fieldList = getAllShowField(showInstance, tName);
			if (fieldList != null && fieldList.size() > 0) {
				Object fieldValue = null;
				String detailDtoName = null;
				for (Field f : fieldList) {
					fieldValue = ReflectOperation.getFieldValue(tObject, f.getName());
					if(f.isAnnotationPresent(OneToMany.class)){
						detailDtoName = ReflectOperation.getGenericClass(f.getGenericType()).getName();
						checkList = createCheckClass(detailDtoName);
						checkListFieldInfo(fieldValue, detailDtoName, checkList);
					}else if(f.isAnnotationPresent(JoinColumn.class)){
						detailDtoName = ReflectOperation.getDtoName(f);
						checkList = createCheckClass(detailDtoName);
						checkCardFieldInfo(fieldValue, detailDtoName, checkList);
					}
				}
			}
			this.setAllMessageResult();
		}
		return messageResult;
	}

	/**
	 * 
	 * 设置所有的出错信息
	 * 
	 * @param errorObject
	 *            当前出现错误的DTO对象
	 * @throws Exception
	 */
	private void setAllErrorFieldList(Object errorObject) throws Exception {
		if (currentResult != null
				&& currentResult.getErrorFieldList().size() > 0
				&& !currentResult.isSuccess() && errorObject != null) {
			if (!isSetErrorDtoName) {
				String DTODes = errorObject.getClass().getAnnotation(
						IEntity.class).description();
				String keyName = null;
				String keyNameFieldValue = null;
				Field keyNameField = ReflectOperation
						.getKeyNameField(errorObject.getClass());
				IColumn ic = keyNameField.getAnnotation(IColumn.class);
				if (keyNameField != null && ic != null) {
					keyName = ic.description();
					keyNameFieldValue = ReflectOperation.getFieldValue(
							errorObject, keyNameField.getName()).toString();
					if (!StringUtils.isBlank(keyName)
							&& !StringUtils.isBlank(keyNameFieldValue)) {
						errorFieldList.add(new ErrorField("", "", "选项卡\""
								+ DTODes + "\"中，" + keyName + "为"
								+ keyNameFieldValue + "的数据出现错误："));
					} else {
						errorFieldList.add(new ErrorField("", "", "选项卡\""
								+ DTODes + "\"中的数据出现错误："));
					}
				} else {
					errorFieldList.add(new ErrorField("", "", "选项卡\"" + DTODes
							+ "\"中的数据出现错误："));
				}
			}
			if (currentResult.getErrorFieldList() != null
					&& currentResult.getErrorFieldList().size() > 0) {
				errorFieldList.addAll(currentResult.getErrorFieldList());
			}
			isSetErrorDtoName = true;
			currentResult = null;
		}
	}

	/**
	 * 
	 * 设置总体的校验信息
	 */
	private MessageResult setAllMessageResult() {
		if (errorFieldList != null && errorFieldList.size() > 0) {
			messageResult.setErrorFieldList(errorFieldList);
			messageResult.setSuccess(false);
		}
		return messageResult;
	}

	/**
	 * 
	 * 获取当前DTO展示到界面上的所有非空字段
	 * 
	 * @param object
	 *            基础段对应的DTO
	 * @param showInstance
	 *            基础段的显示实例
	 * @param tName
	 *            基础段的DTO名称
	 * @return List<Field> getAllNotNullShowField 所有的值非空的字段
	 * @throws Exception
	 */
	private List<Field> getAllShowField(ShowInstance showInstance, String tName)
			throws Exception {
		List<Field> allShowFieldList = new ArrayList<Field>();

		List<Field> listFieldList = ReflectOperation.getComponentDetailList(
				tName, showInstance.getFieldListWithShowList());
		List<Field> cardLieldList = ReflectOperation.getComponentCardList(
				tName, showInstance.getFieldListWithShowCard());

		allShowFieldList.addAll(listFieldList);
		allShowFieldList.addAll(cardLieldList);

		return allShowFieldList;
	}

	/**
	 * 
	 * 获取显示实例
	 * 
	 * @param tName
	 *            基础段DTO对应的名称
	 * @return ShowInstance 基础段DTO对应的显示实例
	 * @throws Exception
	 */
	private ShowInstance getShowInstance(String tName) throws Exception {
		String showInstanceName = ShowParamManager.getShowInstanceName();
		ShowInstance showInstance = ReflectOperation.getShowInstance(tName,
				showInstanceName);
		return showInstance;
	}

	/**
	 * 
	 * 校验以列表显示的字段的信息
	 * 
	 * @param listFieldList
	 *            基础段中以列表展示的字段的List
	 * @param detailDtoName
	 *            基础段中以列表展示的字段对应的DTO的名称
	 * @throws Exception
	 */
	private void checkListFieldInfo(Object fieldValue, String detailDtoName, List<ICheck> checkList) throws Exception {
		if(checkList != null){
			for(Object fiedlVal : (Collection<?>)fieldValue){
				for(ICheck check : checkList){
					if(BaseConstructor.class.isInstance(check))
					{
						((BaseConstructor)check).setBaseObject(fiedlVal);
					}
					currentResult = check.Check();
					
					setAllErrorFieldList(fiedlVal);
				}
				this.isSetErrorDtoName = false;
			}
		}
	}

	/**
	 * 
	 * 校验以卡片形式展示的字段的信息
	 * 
	 * @param cardFieldList
	 *            基础段中以卡片展示的字段的List
	 * @param detailDtoName
	 *            基础段中以卡片展示的字段对应的DTO的名称
	 * @throws Exception
	 */
	private void checkCardFieldInfo(Object fieldValue, String detailDtoName, List<ICheck> checkList) throws Exception {
		if (checkList != null) {
			for (ICheck check : checkList) {
				if(BaseConstructor.class.isInstance(check))
				{
					((BaseConstructor)check).setBaseObject(fieldValue);
				}
				currentResult = check.Check();
				
				this.setAllErrorFieldList(fieldValue);
			}
			this.isSetErrorDtoName = false;
		}
	}

	/**
	 * 
	 * 创建当前的字段对应的校验类
	 * 
	 * @param field
	 *            基础段中对应字段的值对象
	 * @param detailDtoName
	 *            基础段中对应字段相应的DTO的名称
	 * @return
	 * @throws Exception
	 */
	private List<ICheck> createCheckClass(String dtoName) throws Exception {
		List<String> checkList = getCheckList(dtoName);
		List<ICheck> checkClassList = new ArrayList<ICheck>();
		ICheck check = null;
		if (checkList != null && checkList.size() > 0) {
			
			for (String strCheckClass : checkList) {
				check = (ICheck) FrameworkFactory.CreateClass(strCheckClass);
				if (check != null) {
					checkClassList.add(check);
				}
			}
		}
		return checkClassList;
	}

}
