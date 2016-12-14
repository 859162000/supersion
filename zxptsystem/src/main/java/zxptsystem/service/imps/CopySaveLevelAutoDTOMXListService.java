package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskModelInst;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;
import framework.security.SecurityContext;
import framework.services.interfaces.IAdd;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

/**
 * 
 * @description <p>批量复制明细段数据(删除报文)</P>
 * @author Liutao & xiajieli
 */
public class CopySaveLevelAutoDTOMXListService implements IObjectResultExecute {
	
	private IAdd add = null;
	private ICheck check = null;
	private IProcese procese = null;
	private ITranslate translate = null;
	private String errorMessage;
	private Object serviceResult;
	private Object serviceObject;
	private String successMessage;
	private String defaultDaoBeanId;
	Object saveJCObject = null;// 实际需要保存的基础段数据
	IParamVoidResultExecute baseVoidDao = null;
	IParamObjectResultExecute findByIdDao = null;
	IParamObjectResultExecute findByCriteriaDao = null;

	@Override
	public Object objectResultExecute() throws Exception {
		initSuccessResult();
		return this.getServiceResult();
	}

	public void initSuccessResult() {
		MessageResult messageResult = new MessageResult();
		MessageResult messageResultRel = new MessageResult();
		try {
			initDao();
			String[] idList = (String[]) RequestManager.getIdList();
			if (idList != null && idList.length > 0) {
				Class<?> clazz = RequestManager.getTOject().getClass();
				String dtoName = clazz.getName();
				if(dtoName.indexOf("_Condition") > -1) {
					dtoName = dtoName.substring(0, dtoName.indexOf("_Condition")).replace("condition.", "").trim();
					clazz = Class.forName(dtoName);
				}
				
				// 查看当前选中的明细段是否存在未回执成功的数据
				DetachedCriteria dc = DetachedCriteria.forClass(clazz);
				dc.add(Restrictions.in(ReflectOperation.getPrimaryKeyField(clazz).getName(), idList));
				dc.add(Restrictions.eq("RPTFeedbackType", "2"));//只对回执成功的数据进行操作
				List<Object> mxList = (List<Object>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
				if(mxList != null && mxList.size() == idList.length) {
					messageResult  = saveJCObject(mxList.get(0)); //保存基础
					
					if(messageResult.isSuccess()) {
					    String dtoNames=ShowContext.getInstance().getShowEntityMap().get("RelevantDtoName").get(dtoName);//关联多个dto
					    String extend2="";
					    Object FOREIGNID=null;
					    
					    if(ReflectOperation.getFieldValue(mxList.get(0), "FOREIGNID")!=null){
					    	FOREIGNID=ReflectOperation.getFieldValue(mxList.get(0), "FOREIGNID");
					    }
					    for (Object objMX : mxList) {
					    	if(ReflectOperation.getFieldValue(objMX, "extend2")!=null){
						    	extend2=ReflectOperation.getFieldValue(objMX, "extend2").toString();
						    }
						    
						    String[] dtoNameArr=new String[]{};
						    if(dtoNames.indexOf(",")>-1){//存在多个关联的dto
						    	dtoNameArr=dtoNames.split(",");
						    	for (String name : dtoNameArr) {
						    		List<Object> relMXList=GetMXListByCriteria(name, extend2,FOREIGNID,dc);//得到明细列表
									if(relMXList.size()>0){
										messageResultRel = saveMXObjectList(relMXList);
									}
								}
						    }else{//存在一个关联的dto
						    	List<Object> relMXList=GetMXListByCriteria(dtoNames, extend2,FOREIGNID,dc);//得到明细列表
						    	if(relMXList.size()>0){
									messageResultRel = saveMXObjectList(relMXList);
								}
						    }
						}
					    
					    messageResult = saveMXObjectList(mxList);
						if(messageResult.isSuccess() && messageResultRel.isSuccess()) {
							messageResult.setMessage(this.successMessage);
						}
					    
					}
					this.setServiceResult(messageResult);
				} else {
					messageResult.setSuccess(false);
					messageResult.setMessage(this.errorMessage+"\\r\\n当前选中的明细段存在未回执成功的数据，请重试");
					this.setServiceResult(messageResult);
				}
			} else {
				messageResult.setSuccess(false);
				messageResult.setMessage("请至少勾选一条数据");
				this.setServiceResult(messageResult);
			}
		} catch (Exception e) {
			messageResult.setSuccess(false);
			messageResult.setMessage(this.errorMessage + "\\r\\n系统异常，请联系管理员");
			this.setServiceResult(messageResult);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <P>保存基础段数据</P>
	 * @author Liutao
	 */
	private MessageResult saveJCObject(Object mxObject) throws Exception {
		MessageResult messageResult = new MessageResult();
		Field foreinKeyJCField = getForeignField(mxObject.getClass());
		copyJCObject(mxObject, foreinKeyJCField);
		// 查看当前的基础段是否有ID（如果有：则数据库中已经存在信息操作记录类型为删除状态的数据）
		if(saveJCObject != null) {
			Field pkField = ReflectOperation.getPrimaryKeyField(saveJCObject.getClass());
			if(StringUtils.isBlank(ReflectOperation.getFieldValue(saveJCObject, pkField.getName()).toString())) {
				RequestManager.setTOject(saveJCObject);
				setNeedParams(true);
				
				Object addList = ReflectOperation.getFieldValue(this.serviceObject, "defaultAddClassList");
				if(addList != null) {
					for(String str : (List<String>) addList) {
						add = (IAdd) FrameworkFactory.CreateClass(str);
						add.Add();
					}
				}
				
				Object checkList = ReflectOperation.getFieldValue(this.serviceObject, "defaultCheckClassList");
				if(checkList != null) {
					MessageResult curMessageResult = null;
					for(String str : (List<String>) checkList) {
						check = (ICheck) FrameworkFactory.CreateClass(str);
						curMessageResult = check.Check();
						if(!curMessageResult.isSuccess()) {
							messageResult = curMessageResult;
							break;
						}
					}
				}
				
				if(messageResult.isSuccess()) {
					Object translateList = ReflectOperation.getFieldValue(this.serviceObject, "defaultTranslateClassList");
					if(translateList != null) {
						for(String str : (List<String>) translateList) {
							translate = (ITranslate) FrameworkFactory.CreateClass(str);
							translate.Translate();
						}
					}
				
					this.baseVoidDao.paramVoidResultExecute(null);
					//设置基础段的状态
					setJCORMXStatus(RequestManager.getTOject(), true);
					
					Object proceseList = ReflectOperation.getFieldValue(this.serviceObject, "defaultProceseClassList");
					if(proceseList != null) {
						String oldTName = RequestManager.getTName();
						String[] idList = (String[]) RequestManager.getIdList();
						String oldCurrentLevel = SessionManager.getCurrentLevel();
						String oldPreviousLevel = SessionManager.getPreviousLevel();
						String oldPreviousLevelTName = SessionManager.getPreviousLevelTName();
						
						RequestManager.setIdList(null);
						RequestManager.setTName(saveJCObject.getClass().getName());
						
						SessionManager.setCurrentLevel("AUTODTO");
						SessionManager.setPreviousLevel(SessionManager.getCurrentLevel(), "report.dto.TaskModelInst-0");
						SessionManager.setLevelTName(SessionManager.getPreviousLevel(), TaskModelInst.class.getName());
						
						
						for(String str : (List<String>) proceseList) {
							procese = (IProcese) FrameworkFactory.CreateClass(str);
							this.setServiceResult(procese.Procese(this.serviceResult));
						}
						
						RequestManager.setIdList(idList);
						RequestManager.setTName(oldTName);

						SessionManager.setCurrentLevel(oldCurrentLevel);
						SessionManager.setPreviousLevel(SessionManager.getCurrentLevel(), oldPreviousLevel);
						SessionManager.setLevelTName(SessionManager.getPreviousLevel(), oldPreviousLevelTName);
					}
				} else {
					if(!StringUtils.isBlank(messageResult.getMessage())) {
						messageResult.setMessage("复制基础段失败"+messageResult.getMessage());
					} else {
						messageResult.setMessage("复制基础段失败");
					}
					messageResult.AlertTranslate();
					this.setServiceResult(messageResult);
				}
			} else {
				//设置基础段的状态
				setJCORMXStatus(saveJCObject, false);
			}
		} else {
			messageResult.setSuccess(false);
			messageResult.setMessage("当前选中明细段对应的基础段不存在");
			this.setServiceResult(messageResult);
		}
		return messageResult;
	}
	
	/**
	 * 
	 * <P>保存明细段段数据</P>
	 * @author Liutao
	 */
	private MessageResult saveMXObjectList(List<Object> mxList) throws Exception {
		Object addList = null;
		Object checkList = null;
		Object translateList = null;
		Object proceseList = null;
		Object saveMXObject = null;
		MessageResult messageResult = new MessageResult();
		for(Object mxObject : mxList) {
			saveMXObject = mxObject.getClass().newInstance();
			ReflectOperation.CopyFieldValue(mxObject, saveMXObject);
			setExtendOrForeignKeyFieldValue(saveMXObject);
			RequestManager.setTOject(saveMXObject);
			String oldCurrentLevel = SessionManager.getCurrentLevel();
			SessionManager.setCurrentLevel(null);// 置空当前层级
			initService(false, mxObject.getClass());
			setNeedParams(false);
			
			addList = ReflectOperation.getFieldValue(this.serviceObject, "defaultAddClassList");
			if(addList != null) {
				for(String str : (List<String>) addList) {
					add = (IAdd) FrameworkFactory.CreateClass(str);
					add.Add();
				}
			}
			
			checkList = ReflectOperation.getFieldValue(this.serviceObject, "defaultCheckClassList");
			if(checkList != null) {
				MessageResult curMessageResult = null;
				for(String str : (List<String>) checkList) {
					check = (ICheck) FrameworkFactory.CreateClass(str);
					curMessageResult = check.Check();
					if(!curMessageResult.isSuccess()) {
						messageResult = curMessageResult;
						break;
					}
				}
			}
			
			if(messageResult.isSuccess()) {
				translateList = ReflectOperation.getFieldValue(this.serviceObject, "defaultTranslateClassList");
				if(translateList != null) {
					for(String str : (List<String>) translateList) {
						translate = (ITranslate) FrameworkFactory.CreateClass(str);
						translate.Translate();
					}
				}
				
				setExtendOrForeignKeyFieldValue(RequestManager.getTOject());
				this.baseVoidDao.paramVoidResultExecute(null);
				//设置明细段的状态
				setJCORMXStatus(RequestManager.getTOject(), false);
				
				proceseList = ReflectOperation.getFieldValue(this.serviceObject, "defaultProceseClassList");
				if(proceseList != null) {
					SessionManager.setCurrentLevel(oldCurrentLevel);// 还原当前层级
					for(String str : (List<String>) proceseList) {
						procese = (IProcese) FrameworkFactory.CreateClass(str);
						this.setServiceResult(procese.Procese(this.serviceResult));
					}
				}
			} else {
				if(!StringUtils.isBlank(messageResult.getMessage())) {
					messageResult.setMessage("复制明细段失败,"+messageResult.getMessage());
				} else {
					messageResult.setMessage("复制明细段失败");
				}
				messageResult.AlertTranslate();
				this.setServiceResult(messageResult);
				break;
			}
		}
		return messageResult;
	}
	
	/**
	 * 
	 * <P>复制基础段对象</P>
	 * @author Liutao
	 */
	private void copyJCObject(Object mxObject, Field foreinKeyJCField) throws Exception {
		Object oldJCObject = ReflectOperation.getFieldValue(mxObject, foreinKeyJCField.getName());
		if(oldJCObject != null) {
			initService(true, oldJCObject.getClass());
			String xxjlczlx = ReflectOperation.getFieldValue(oldJCObject, "XXJLCZLX").toString();
			if(!"4".equals(xxjlczlx)) {
				String[] logicKeyList = (String[]) ReflectOperation.getFieldValue(this.serviceObject, "logicPrimaryKey");
				if(logicKeyList != null && logicKeyList.length > 0) {
					boolean hasNoNullConditionFlag = true;
					DetachedCriteria dc = DetachedCriteria.forClass(oldJCObject.getClass());
					for(String logicKey : logicKeyList) {
						if(!StringUtils.isBlank(logicKey)) {
							if(!"XXJLCZLX".equals(logicKey)) {
								dc.add(Restrictions.eq(logicKey, ReflectOperation.getFieldValue(oldJCObject, logicKey)));
							}
						} else {
							hasNoNullConditionFlag = false;
							break;
						}
					}
					if(hasNoNullConditionFlag) {
						dc.add(Restrictions.eq("XXJLCZLX", "4"));
						List<Object> objectList = (List<Object>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
						if(objectList != null && objectList.size() > 0) {
							saveJCObject = objectList.get(0);
						} else {
							saveJCObject = oldJCObject.getClass().newInstance();
							ReflectOperation.CopyFieldValue(oldJCObject, saveJCObject);
							ReflectOperation.setFieldValue(saveJCObject, "XXJLCZLX", "4");
							ReflectOperation.setFieldValue(saveJCObject, ReflectOperation.getPrimaryKeyField(saveJCObject.getClass()).getName(), "");
						}
					}
				}
			} else {
				saveJCObject = oldJCObject;
			}
		}
		
		if(saveJCObject != null) {
			if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()) {
				ReflectOperation.setFieldValue(saveJCObject, "operationUser", SecurityContext.getInstance().getLoginInfo().getTag());
			} else {
				Field operationUser = ReflectOperation.getReflectField(saveJCObject.getClass(), "operationUser");
				ReflectOperation.setFieldNullValue(saveJCObject, "operationUser", operationUser.getType());
			}
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ReflectOperation.setFieldValue(saveJCObject, "lastUpdateDate", TypeParse.parseTimestamp(sdf.format(date)));
			
			setExtendOrForeignKeyFieldValue(saveJCObject);
		}
	}
	
	/**
	 * 
	 * <P>设置扩展字段的值</P>
	 * @author Liutao
	 */
	private void setExtendOrForeignKeyFieldValue(Object object) throws Exception {
		if(object != null) {
			Field[] fields = ReflectOperation.getReflectFields(object.getClass());
			List<String> extendNameList = new ArrayList<String>();
			extendNameList.add("extend1");
			extendNameList.add("extend2");
			extendNameList.add("extend3");
			extendNameList.add("extend4");
			extendNameList.add("extend5");
			for(Field field : fields) {
				if(extendNameList.contains(field.getName())) {
					ReflectOperation.setFieldValue(object, field.getName(), "");
				}
			}
			
			if(!object.getClass().equals(saveJCObject.getClass())) {
				List<Field> fieldList = ReflectOperation.getJoinColumnFieldList(object.getClass());
				for(Field field : fieldList) {
					if(field.getType().equals(saveJCObject.getClass())) {
						ReflectOperation.setFieldValue(object, field.getName(), saveJCObject);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * <P>设置基础段或者明细段的各个状态字段</br>
	 * 因为可能存在再Spring中配置的默认值与实际需要的状态不符合的情况
	 * </P>
	 * @param object 需要进行状态修改的基础段或者明细段对象
	 * @param insertJCFlag 是否是新增的基础段（true：新增的基础段，false：原有就存在的基础段）
	 * @author Liutao
	 */
	private void setJCORMXStatus(Object object, boolean insertJCFlag) throws Exception {
		if(object.getClass().equals(saveJCObject.getClass())) {// 是否是基础段
			if(insertJCFlag) {// 数据库中原本就不存在此基础段，即新增的基础段
				ReflectOperation.setFieldValue(object, "RPTSendType", "1");
				ReflectOperation.setFieldValue(object, "RPTCheckType", "2");
				ReflectOperation.setFieldValue(object, "RPTVerifyType", "1");
				ReflectOperation.setFieldValue(object, "RPTSubmitStatus", "1");
				ReflectOperation.setFieldValue(object, "RPTFeedbackType", "1");
			} else {
				ReflectOperation.setFieldValue(object, "RPTCheckType", "2");
				ReflectOperation.setFieldValue(object, "RPTVerifyType", "1");
				ReflectOperation.setFieldValue(object, "RPTSubmitStatus", "1");
				
				if("2".equals(ReflectOperation.getFieldValue(object, "RPTSendType").toString())) {
					ReflectOperation.setFieldValue(object, "RPTSendType", "5");
				} else {
					ReflectOperation.setFieldValue(object, "RPTSendType", "1");
				}
				
				if("2".equals(ReflectOperation.getFieldValue(object, "RPTFeedbackType").toString())) {
					ReflectOperation.setFieldValue(object, "RPTFeedbackType", "4");
				} else {
					ReflectOperation.setFieldValue(object, "RPTFeedbackType", "1");
				}
			}
		} else {
			ReflectOperation.setFieldValue(object, "RPTSendType", "1");
			ReflectOperation.setFieldValue(object, "RPTCheckType", "2");
			ReflectOperation.setFieldValue(object, "RPTFeedbackType", "1");
		}
		
		IParamVoidResultExecute updateDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
		updateDao.paramVoidResultExecute(new Object[] {object, null});
	}
	
	private void initDao() throws Exception{
	 	this.baseVoidDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean(this.defaultDaoBeanId);
	 	findByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
	 	findByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	}

	/**
	 * 
	 * <P>设置所需参数信息</P>
	 * @author Liutao
	 */
	private void setNeedParams(boolean jcFlag) throws Exception {
		Method method = ReflectOperation.getReflectMethod(this.serviceObject.getClass(), "init");
		method.invoke(this.serviceObject);
	}
	
	/**
	 * 
	 * <P>
	 * 获取基础段或者明细段复制保存的service
	 * </P>
	 * 
	 * @author Liutao
	 */
	private void initService(boolean jcFlag, Class<?> clazz) throws Exception {
		String action = "";
		if (jcFlag) {
			action = "CopySaveLevelAUTODTO";
		} else {
			action = "MXCopySaveLevelAutoDTOMX";
		}
		String serviceBeanId = TActionRule.getServiceBeanName(clazz.getName(), action);
		this.serviceObject = FrameworkFactory.CreateBean(serviceBeanId);
		if(this.serviceObject == null) {
			if(jcFlag) {
				this.serviceObject = FrameworkFactory.CreateBean("singleObjectCopySaveLevelAUTODTOService");
			} else {
				this.serviceObject = FrameworkFactory.CreateBean("singleObjectMXCopySaveLevelAutoDTOMXService");
			}
		}
	}

	/**
	 * 
	 * <P>
	 * 获取外键基础段对象
	 * </P>
	 * 
	 * @author Liutao
	 */
	private Field getForeignField(Class<?> clazz) throws Exception {
		Field foreinKeyJCField = null;
		List<Field> joinColumnFieldList = ReflectOperation.getJoinColumnFieldList(clazz);// 明细段外键字段数组
		List<Field> oneToManyFieldList = null;// 基础段一对多字段
		// 获取明细段的外键基础段
		if (joinColumnFieldList != null) {
			Entity en = null;
			Type[] types = null;
			Method method = null;
			String methodName = null;
			for (Field field : joinColumnFieldList) {
				en = field.getType().getAnnotation(Entity.class);
				if (en != null) {
					oneToManyFieldList = ReflectOperation.getOneToManyField(field.getType());
					if (oneToManyFieldList != null) {
						for (Field field2 : oneToManyFieldList) {
							methodName = "set"+ field2.getName().substring(0, 1).toUpperCase()+field2.getName().substring(1);
							method = ReflectOperation.getReflectMethod(field.getType(), methodName, Set.class);
							if (method != null) {
								types = method.getGenericParameterTypes();
								if (types != null && types.length > 0) {
									if (ReflectOperation.getGenericClass(types[0]).equals(clazz)) {
										foreinKeyJCField = field;
										break;
									}
								}
							}
						}
					}
				}

				if (foreinKeyJCField != null) {
					break;
				}
			}
		}

		return foreinKeyJCField;
	}
	
	/**
	 * 根据条件得到dto明细列表
	 * @param dtoName dto名称
	 * @param extend2 业务发生日期
	 * @param FOREIGNID 外键对象
	 * @param dc 条件
	 * @return
	 * @throws Exception
	 */
	public List<Object> GetMXListByCriteria(String dtoName,String extend2,Object FOREIGNID, DetachedCriteria dc) throws Exception{
		dc = DetachedCriteria.forClass(Class.forName(dtoName));
		dc.add(Restrictions.eq("RPTFeedbackType", "2"));
		dc.add(Restrictions.eq("extend2", extend2));
		dc.add(Restrictions.eq("FOREIGNID", FOREIGNID));
		List<Object> relMXList = (List<Object>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
		return relMXList;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getDefaultDaoBeanId() {
		return defaultDaoBeanId;
	}

	public void setDefaultDaoBeanId(String defaultDaoBeanId) {
		this.defaultDaoBeanId = defaultDaoBeanId;
	}

	public Object getServiceResult() {
		return serviceResult;
	}

	public void setServiceResult(Object serviceResult) {
		this.serviceResult = serviceResult;
	}

}
