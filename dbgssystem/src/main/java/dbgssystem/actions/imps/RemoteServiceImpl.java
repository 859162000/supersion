package dbgssystem.actions.imps;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.UserInfo;
import dbgssystem.dto.condition.DBGSDownload_Condition;
import dbgssystem.helper.DateUitl;
import framework.helper.AESSecretKey;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.security.LoginInfo;
import framework.services.imps.BaseService;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;

public class RemoteServiceImpl extends BaseRmiService{

	private static final long serialVersionUID = 1L;
	
	//private static Map<String,String[]> DtoConfig;
	
//	static{
//		DtoConfig=GetDtoConfig();
//	}
	public RemoteServiceImpl() throws RemoteException {
		super();
	}
	private static Map<String,String[]> GetDtoConfig(){
		//key为dto类名，value[0]为保存beanid，value[1]为更新beanid
		Map<String,String[]> map=new HashMap<String,String[]>();
		//担保合同基础段
		map.put("dbgssystem.dto.AutoDTO_DB_DBXX_JC", new String[]{"autoDTO_DB_DBXX_JCSaveLevelAUTODTOService","autoDTO_DB_DBXX_JCUpdateLevelAUTODTOService"});
		//担保合同
		map.put("dbgssystem.dto.AutoDTO_DB_DBHTXX", new String[]{"singleObjectSaveLevelAutoDTOMXService","singleObjectUpdateLevelAutoDTOMXService"});
		//被担保人
		map.put("dbgssystem.dto.AutoDTO_DB_BDBRXX", new String[]{"autoDTO_DB_BDBRXXSaveLevelAutoDTOMXService","autoDTO_DB_BDBRXXUpdateLevelAutoDTOMXService"});
		//债权人
		map.put("dbgssystem.dto.AutoDTO_DB_ZQRJZHTXX", new String[]{"autoDTO_DB_ZQRJZHTXXSaveLevelAutoDTOMXService","autoDTO_DB_ZQRJZHTXXUpdateLevelAutoDTOMXService"});
		//反担保人
		map.put("dbgssystem.dto.AutoDTO_DB_FDBRXX", new String[]{"autoDTO_DB_FDBRXXSaveLevelAutoDTOMXService","autoDTO_DB_FDBRXXUpdateLevelAutoDTOMXService"});
		//实际在保
		map.put("dbgssystem.dto.AutoDTO_DB_SJZBZRXX", new String[]{"singleObjectSaveLevelAutoDTOMXService","singleObjectUpdateLevelAutoDTOMXService"});
		//代偿概况
		map.put("dbgssystem.dto.AutoDTO_DB_DCGKXX", new String[]{"singleObjectSaveLevelAutoDTOMXService","singleObjectUpdateLevelAutoDTOMXService"});
		//代偿明细
		map.put("dbgssystem.dto.AutoDTO_DB_DCMXXX", new String[]{"singleObjectSaveLevelAutoDTOMXService","singleObjectUpdateLevelAutoDTOMXService"});
		//保费概况
		map.put("dbgssystem.dto.AutoDTO_DB_BFJNGKXX", new String[]{"singleObjectSaveLevelAutoDTOMXService","singleObjectUpdateLevelAutoDTOMXService"});
		//保费明细
		map.put("dbgssystem.dto.AutoDTO_DB_BFJNMXXX", new String[]{"singleObjectSaveLevelAutoDTOMXService","singleObjectUpdateLevelAutoDTOMXService"});
		//追偿明细 
		map.put("dbgssystem.dto.AutoDTO_DB_ZCMXXX", new String[]{"singleObjectSaveLevelAutoDTOMXService","singleObjectUpdateLevelAutoDTOMXService"});
		return map;
	}
	
	private MessageResult packageCheck(List<Object> list){
		MessageResult messageResult = new MessageResult();
		if(null==list){
			messageResult.setSuccess(false);
			messageResult.setMessage("参数不能为Null!");
			return messageResult;
		}
		for(Object obj:list){
			if(null!= obj && !obj.getClass().getPackage().getName().startsWith("dbgssystem.dto")){
				messageResult.setSuccess(false);
				messageResult.setMessage("对象包名不匹配,接口包名被限制为dbgssystem.dto.*");
			}
		}
		
		return messageResult;
	}
	
	private MessageResult validate(String str){
		MessageResult messageResult = new MessageResult();
		if(null==str){
			messageResult.setSuccess(false);
			messageResult.setMessage("验证字符串不能为NULL!");
		}else{
			String plainText= AESSecretKey.DecryString(str, null);
			if(!plainText.equals("1234567890")){
				messageResult.setSuccess(false);
				messageResult.setMessage("验证字符串不正确!");
			}
			try{
				init();
			}catch(Exception ex)
			{
				messageResult.setSuccess(false);
				messageResult.setMessage(ex.getMessage());
			}
		}
		
		return messageResult;
	}
	@SuppressWarnings("unchecked")
	@Override
	public MessageResult saveOrUpdate(Object object,String val_str) {
		//找到servive中的check类进行校验
		//针对明细，自己做基础检查
		//尽量使用一个http session

		MessageResult messageResult = new MessageResult();
		messageResult = validate(val_str);
		if(!messageResult.isSuccess()){
			return messageResult;
		}
		//LogicParamManager.setDefaultCheckInstance("extend.dto.ReportModel_Table.strCheckInstance");
		List<Object> list =null; 
		
		if(object instanceof Collection){
			list= (ArrayList<Object>)object;
		}else{
			list=new ArrayList<Object>();
			list.add(object);
		}
		
		messageResult = packageCheck(list);		
		if(!messageResult.isSuccess()){
			return messageResult;
		}
		
		List<Object> saveList= new ArrayList<Object>();
		List<Object> updateList= new ArrayList<Object>();
		
		IParamObjectResultExecute SingleObjectFindByIdDao =	(IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		try {						
			String beanId ="";
			BaseService baseService;

			int index=0;
			for(Object obj:list){
				String autoId=(String)ReflectOperation.getFieldValue(obj, "autoID");
				if(null ==autoId || "".equals(autoId)){
					continue;
					
				}
				index++;
				ReflectOperation.setFieldValue(obj, "RPTCheckType", "2");
				if(obj instanceof dbgssystem.dto.AutoDTO_DB_DBXX_JC){
					ReflectOperation.setFieldValue(obj, "RPTSubmitStatus", "2");
					ReflectOperation.setFieldValue(obj, "RPTVerifyType", "2");
					ReflectOperation.setFieldValue(obj, "RPTVerifyType", "2");
					ReflectOperation.setFieldValue(obj, "lastUpdateDate", DateUitl.dateToString());
				}
				
				ReflectOperation.setFieldValue(obj, "RPTSendType", "1");
				ReflectOperation.setFieldValue(obj, "RPTFeedbackType", "1");
				
				RequestManager.setTName(obj.getClass().getName());
				RequestManager.setTOject(obj);
				RequestManager.setId(ReflectOperation.getFieldValue(obj, "autoID"));

				Object tObject = SingleObjectFindByIdDao.paramObjectResultExecute(null);
				if(null == tObject){//无数据
					saveList.add(obj);
					beanId = RunRmiServiceAction.DtoConfig.get(obj.getClass().getName())[0];
				}else{//有数据
					/*ReflectOperation.CopyFieldValue(obj, tObject);
					updateList.add(tObject);*/
					updateList.add(obj);
					beanId = RunRmiServiceAction.DtoConfig.get(obj.getClass().getName())[1];
				}
				
				baseService = (BaseService)FrameworkFactory.CreateBean(beanId);
				List<String> checkList = baseService.getDefaultCheckClassList();
				if(null== checkList){
					checkList = new ArrayList<String>();					
				}
				//checkList.add("dbgssystem.actions.imps.ManyToOneFieldDataCheck");
				
				for(String chk:checkList){
					if(chk.equals("framework.services.check.PrimaryKeyRepeatCheck") || chk.equals("dbgssystem.service.check.CheckForDBGS")){
						continue;
					}
					Object chkObj = FrameworkFactory.CreateClass(chk);
					if(chkObj instanceof BaseConstructor){
						Constructor<?> cons = Class.forName(chk).getConstructor(Object.class);
						chkObj = cons.newInstance(obj);
					}
					LogicParamManager.setDefaultCheckInstance("extend.dto.ReportModel_Table.strCheckInstance");
					MessageResult currResult = ((ICheck)chkObj).Check();
					if(!currResult.isSuccess()){
						for(ErrorField err:currResult.getErrorFieldList()){
							err.setFieldName(obj.getClass().getName()
									+"%"+ReflectOperation.getPrimaryKeyValue(obj)+"%"
									+err.getFieldName());
							messageResult.getErrorFieldList().add(err);
						}
						for(String err:currResult.getMessageList()){
							ErrorField errorField = new ErrorField(obj.getClass().getName()
									+"%"+ReflectOperation.getPrimaryKeyValue(obj), "red", err);
							messageResult.getErrorFieldList().add(errorField);
						}
						for(String err:currResult.getMessageSet()){
							ErrorField errorField = new ErrorField(obj.getClass().getName()
									+"%"+ReflectOperation.getPrimaryKeyValue(obj), "red", err);
							messageResult.getErrorFieldList().add(errorField);
						}
					}
				}
			}
			
			if(messageResult.getErrorFieldList().size() > 0){
				messageResult.setSuccess(false);
				messageResult.setMessage("数据校验失败!");
			}

			if(messageResult.isSuccess()){
				List<Object> jcObjectList = new ArrayList<Object>();
				List<Object> mxObjectList = new ArrayList<Object>();
				for(Object o : saveList){
					if(o instanceof dbgssystem.dto.AutoDTO_DB_DBXX_JC){
						jcObjectList.add(o);
					}
					else{
						mxObjectList.add(o);
					}
				}
				
				/*for(Object o : mxObjectList){
					Object jc = ReflectOperation.getFieldValue(o, "FOREIGNID");
					for(Object obj : jcObjectList){
						if(ReflectOperation.getPrimaryKeyValue(jc).equals(ReflectOperation.getPrimaryKeyValue(obj))){
							ReflectOperation.setFieldValue(o, "FOREIGNID", obj);
							break;
						}
					}
					
				}*/
				if(jcObjectList.size() > 0){
					IParamVoidResultExecute singleObjectSaveAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
					singleObjectSaveAllDao.paramVoidResultExecute(new Object[]{jcObjectList,null});
				}

				if(mxObjectList.size() > 0){
					IParamVoidResultExecute singleObjectSaveAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
					singleObjectSaveAllDao.paramVoidResultExecute(new Object[]{mxObjectList,null});
				}
				if(updateList.size()>0){
					IParamVoidResultExecute singleObjectUpdateListDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					singleObjectUpdateListDao.paramVoidResultExecute(new Object[] {updateList, null});
				}
			}else{
				return messageResult;
			}
		} catch (Exception e) {
			messageResult.setSuccess(false);
			//messageResult.setMessage("");
			String a=" ";
			StackTraceElement[] se = e.getStackTrace();
            for (int i = 0; i < se.length; i++) {
                a=a.concat(se[i].toString());
            }
			messageResult.setMessage(a);
		}
		return messageResult;
	}
	
	@Override
	public Object download(DBGSDownload_Condition condition,String val_str) {

		MessageResult messageResult = validate(val_str);
		if(!messageResult.isSuccess()){
			return messageResult;
		}
		
		//注重逻辑
		ActionContext.getContext().getSession().put("strReportType", condition.getStrReportType());
		ActionContext.getContext().getSession().put("strJRJGCode", condition.getStrJRJGCode());
		ActionContext.getContext().getSession().put("SJBGRQ", condition.getSJBGRQ());
		
		IObjectResultExecute baseService = (IObjectResultExecute)FrameworkFactory.CreateBean("downLoadDBGSReportService");
		
		try {
			//return baseService.objectResultExecute();
			Object obj = baseService.objectResultExecute();
			if(obj instanceof framework.services.interfaces.DownloadResult){
				framework.services.interfaces.DownloadResult dd = (framework.services.interfaces.DownloadResult)obj;
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
				byte[] buff = new byte[1024]; 
				int rc = 0; 
				while((rc=dd.getInputStream().read(buff)) !=-1) {
					swapStream.write(buff,0,rc);
			    }				
				DownloadResult dr = new DownloadResult();
				dr.setBufferSize(dd.getBufferSize());
				dr.setBytes(swapStream.toByteArray());
				dr.setContentDisposition(dd.getContentDisposition());
				dr.setContentType(dd.getContentType());
				dr.setSuccess(dd.isSuccess());
				obj=dr;
			}
			return obj;

		} catch (Exception e) {
			messageResult = new MessageResult();
			messageResult.setSuccess(false);
			messageResult.setMessage(e.getMessage());
			return messageResult;
		}
	}

	@Override
	public MessageResult upload(String fileUrl,String fileName,String val_str) {

		MessageResult messageResult = validate(val_str);
		if(!messageResult.isSuccess()){
			return messageResult;
		}
				
		File file = new File(fileUrl);
		if(!file.exists()){
			messageResult.setSuccess(false);
			messageResult.setMessage(fileUrl+"文件不存在!");
			return messageResult;
		}
		
		RequestManager.setUploadFile(file);
		Map<String,String> checkParamMap=new HashMap<String,String>();
		checkParamMap.put("uploadFileFileName", file.getName());
		RequestManager.setReportCheckParam(checkParamMap);
		
		IObjectResultExecute baseService = (IObjectResultExecute)FrameworkFactory.CreateBean("dBGSCJFKBWUploadFileService");
		try {
			messageResult = (MessageResult)baseService.objectResultExecute();
			if(messageResult.isSuccess()){
				messageResult.setMessage("{'fileName': '1034256587623463457554645765”, 'contents': [{ 'autoID': '123324546522365411', 'date': '业务发生日期', 'msg': '错误信息' }, { 'autoID': '123324546522365422', 'date': '业务发生日期', 'msg': '错误信息' },{ 'autoID': '123324546522365437', 'date': '业务发生日期', 'msg': '错误信息” }]}");
			}
		} catch (Exception e) {
			messageResult.setSuccess(false);
			messageResult.setMessage(e.getMessage());
		}

		return messageResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MessageResult delete(Object object, String val_str) throws Exception {
		//自己玩儿
		MessageResult messageResult = validate(val_str);
		if(!messageResult.isSuccess()){
			return messageResult;
		}
		
		List<Object> list = new ArrayList<Object>();
		list.add(object);
		
		messageResult = packageCheck(list);
		if(!messageResult.isSuccess()){
			return messageResult;
		}
		
		IParamVoidResultExecute singleObjectDeleteByIdDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectDeleteByIdDao");
		
		try{
			Object jcObj=null;
			if(!(object instanceof dbgssystem.dto.AutoDTO_DB_DBXX_JC)){
				String id = ReflectOperation.getPrimaryKeyValue(object).toString();
				jcObj = ReflectOperation.getFieldValue(object, "FOREIGNID");			
				singleObjectDeleteByIdDao.paramVoidResultExecute(new Object[]{object.getClass().getName(),id,null});
			}else{
				jcObj = object;
			}
			
			List<Field> fields = ReflectOperation.getOneToManyField(jcObj.getClass());
			String jcId = ReflectOperation.getPrimaryKeyValue(jcObj).toString();
			
			if(object==jcObj){			
				for(Field field:fields){
					singleObjectDeleteByIdDao.paramVoidResultExecute(
							new Object[]{
									ReflectOperation.getGenericClass(field.getGenericType()).getName()
									,jcId
									,null}
							);
				}
			}else{
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria;
				boolean isExistData=false;
				for(Field field:fields){
					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					detachedCriteria.add(Restrictions.eq("FOREIGNID", jcObj));
					Object result = singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					if(null!=result && ((List<Object>)result).size()>0){
						isExistData=true;
						break;
					}
				}
				if(!isExistData){
					singleObjectDeleteByIdDao.paramVoidResultExecute(new Object[]{jcId.getClass().getName(),jcId,null});
				}
			}
		}catch(Exception ex){
			messageResult.setSuccess(false);
			messageResult.setMessage(ex.getMessage());
		}
		return messageResult;
	}

	private void init() {
		Map<String,Object> contextMap=new HashMap<String,Object>();
		Map<String,Object> requestMap=new HashMap<String,Object>();	
		Map<String,Object> sessionMap=new HashMap<String,Object>();			
		Map<String,Object> applicationMap=new HashMap<String,Object>();			
		contextMap.put("request", requestMap);
		ActionContext actionContext=new ActionContext(contextMap);
		actionContext.setSession(sessionMap);
		actionContext.setApplication(applicationMap);
		ServletActionContext.setContext(actionContext);

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setAdministrator(true);
		UserInfo user = new UserInfo();
		user.setStrUserCode("admin");
		loginInfo.setTag(user);
		SessionManager.setLoginInfo(loginInfo);
	}
}
