package framework.services.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;

public class SingleObjectUpdateFieldService extends BaseVoidDaoResultService{
	
	private String judgeFieldName;
	private String fieldName;
	private String fieldValue;
	
	private Map<String,String> updateValueMap;
	
	private List<String> updateFieldCheckClassList;
	
	private List<String> updateFieldTranslateClassList;
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public List<String> getUpdateFieldCheckClassList() {
		return updateFieldCheckClassList;
	}
	public void setUpdateFieldCheckClassList(List<String> updateFieldCheckClassList) {
		this.updateFieldCheckClassList = updateFieldCheckClassList;
	}
	
	private boolean getCheckResult(Object object) throws Exception{
		RequestManager.setTOject(object);
		boolean isTrue = true;
		for(String updateFieldCheckClass : updateFieldCheckClassList){
			ICheck check = (ICheck)FrameworkFactory.CreateClass(updateFieldCheckClass);
			MessageResult currentResult = check.Check();
			if(!currentResult.isSuccess()){
				isTrue = false;
				break;
			}
		}
		return isTrue;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void initSuccessResult() throws Exception {
		if(judgeFieldName == null){
			judgeFieldName = fieldName;
		}
		this.initDao();
		if(this.getBaseVoidDao() != null) {
			
			DetachedCriteria detachedCriteria = null;
			
			List<Boolean> checkResultList = new ArrayList<Boolean>();
			if(updateFieldCheckClassList == null){
				if(updateFieldTranslateClassList != null){
					for(String str : updateFieldTranslateClassList){
						ITranslate translate = (ITranslate)FrameworkFactory.CreateClass(str);
						translate.Translate();
					}
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					detachedCriteria = LogicParamManager.getDetachedCriteria();
					List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					for(int i=0;i<objectList.size();i++){
						checkResultList.add(true);
					}
				}
				else if(RequestManager.getIdList() != null){
					Object[] idList = (Object[]) RequestManager.getIdList();
					for(int i=0;i<idList.length;i++){
						checkResultList.add(true);
					}
				}
				else{
					checkResultList.add(true);
				}
			}
			else{
				if(updateFieldTranslateClassList != null){
					for(String str : updateFieldTranslateClassList){
						ITranslate translate = (ITranslate)FrameworkFactory.CreateClass(str);
						translate.Translate();
					}
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					detachedCriteria = LogicParamManager.getDetachedCriteria();
					List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					for(Object object : objectList){
						 checkResultList.add(getCheckResult(object));
					}
				}
				else if(RequestManager.getIdList() != null){
					Object[] idList = (Object[]) RequestManager.getIdList();
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					for(int i=0;i<idList.length;i++){
						Object object = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{RequestManager.getTName(),idList[i],null});
	                    checkResultList.add(getCheckResult(object));
					}
				}
				else if(RequestManager.getId() != null){
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
                    Object object = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{RequestManager.getTName(),RequestManager.getId(),null});
                    checkResultList.add(getCheckResult(object));
				}
				else{
					checkResultList.add(getCheckResult(RequestManager.getTOject()));
				}
			}
			
			this.getBaseVoidDao().paramVoidResultExecute(new Object[]{RequestManager.getTName(),judgeFieldName,fieldName,fieldValue,RequestManager.getIdList(),
					RequestManager.getId(),RequestManager.getTOject(),checkResultList,detachedCriteria,updateValueMap,null});
		}
		this.setServiceResult(new MessageResult(this.getSuccessMessage()));
	}

	public void setJudgeFieldName(String judgeFieldName) {
		this.judgeFieldName = judgeFieldName;
	}
	public String getJudgeFieldName() {
		return judgeFieldName;
	}
	public void setUpdateFieldTranslateClassList(
			List<String> updateFieldTranslateClassList) {
		this.updateFieldTranslateClassList = updateFieldTranslateClassList;
	}
	public List<String> getUpdateFieldTranslateClassList() {
		return updateFieldTranslateClassList;
	}
	public void setUpdateValueMap(Map<String,String> updateValueMap) {
		this.updateValueMap = updateValueMap;
	}
	public Map<String,String> getUpdateValueMap() {
		return updateValueMap;
	}
}
