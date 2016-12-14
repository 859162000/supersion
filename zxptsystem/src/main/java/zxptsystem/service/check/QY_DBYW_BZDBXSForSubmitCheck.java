package zxptsystem.service.check;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskModelInst;
import zxptsystem.dto.AutoDTO_QY_DBHTXX;
import zxptsystem.dto.AutoDTO_QY_DBXX_JC;
import zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX;
import coresystem.dto.InstInfo;
import extend.dto.SystemCodeValue;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
/*
 * 提交时校验
 *担保合同信息，自然人保证信息
 *同一基础段下&保证合同编号相同，当保证担保形式是多人联保或多人分保时，至少与两个保证人
 */
public class QY_DBYW_BZDBXSForSubmitCheck implements ICheck,IParamObjectResultExecute{

	public MessageResult Check() throws Exception {
		return (MessageResult) paramObjectResultExecute(RequestManager.getTOject());
	}
	
	@SuppressWarnings("unchecked")
	public Object paramObjectResultExecute(Object param) throws Exception {
		
		MessageResult messageResult = new MessageResult();
		String[] idList=(String[])RequestManager.getIdList();
		
		boolean isContain=false;
		if(RequestManager.getActionName().contains("RptSubmitStatusUpdateFieldLevelAUTODTO")){
			for (String strId : idList) {
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				AutoDTO_QY_DBXX_JC  autoDTO_QY_DBXX_JC= (AutoDTO_QY_DBXX_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DBXX_JC.class.getName(),strId,null});
				
				if(autoDTO_QY_DBXX_JC!=null && autoDTO_QY_DBXX_JC.getRPTSubmitStatus()!=null && autoDTO_QY_DBXX_JC.getRPTCheckType()!=null){
					if(autoDTO_QY_DBXX_JC.getRPTSubmitStatus().equals("1") && autoDTO_QY_DBXX_JC.getRPTCheckType().equals("2")){
						String message=GetErrorMessage(messageResult,autoDTO_QY_DBXX_JC,isContain);
						if(!message.equals("")){
							isContain=true;
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add(message);
						}
					}
				}
			}
		}else if(RequestManager.getActionName().contains("RptSubmitStatusALLUpdateField")){
			for (String strId : idList) {
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				TaskModelInst  taskModelInst= (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),strId,null});

				if(taskModelInst.getReportModel_Table().getStrTableName().equals("QY_DBXX_JC")){
					InstInfo instInfo=taskModelInst.getInstInfo();
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DBXX_JC"));
					detachedCriteria.add(Restrictions.eq("instInfo", instInfo));
					detachedCriteria.add(Restrictions.eq("RPTSubmitStatus", "1"));
					detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
					List<AutoDTO_QY_DBXX_JC> autoDTO_QY_DBXX_JCList = (List<AutoDTO_QY_DBXX_JC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					for (AutoDTO_QY_DBXX_JC autoDTO_QY_DBXX_JC : autoDTO_QY_DBXX_JCList) {
						String message=GetErrorMessage(messageResult,autoDTO_QY_DBXX_JC,isContain);
						if(!message.equals("")){
							isContain=true;
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add(message);
						}
					}
				}
			}
			
		}
		
		return messageResult;
	}

	@SuppressWarnings("unchecked")
	private String GetErrorMessage(MessageResult messageResult,AutoDTO_QY_DBXX_JC  autoDTO_QY_DBXX_JC,boolean isContain) throws Exception {
		String message="";
		String strXDYWZLCode="";
		String strZHTBH="";
		if(ReflectOperation.getFieldValue(autoDTO_QY_DBXX_JC, "XDYWZL")!=null){
			strXDYWZLCode=(String)ReflectOperation.getFieldValue(autoDTO_QY_DBXX_JC, "XDYWZL");
		}
		if(ReflectOperation.getFieldValue(autoDTO_QY_DBXX_JC, "ZHTBH")!=null){
			strZHTBH=(String)ReflectOperation.getFieldValue(autoDTO_QY_DBXX_JC, "ZHTBH");
		}
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.SystemCodeValue"));
		detachedCriteria.add(Restrictions.eq("strCode", strXDYWZLCode));
		detachedCriteria.add(Restrictions.eq("codeSet.strCodeID", "XDYWZL"));
		List<SystemCodeValue> systemCodeValueList = (List<SystemCodeValue>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		String strXDYWZL="";
		if(systemCodeValueList.size()==1){
			strXDYWZL=systemCodeValueList.get(0).getStrValue();	
		}
		
		List<Field> oneToManyFieldList = ReflectOperation.getOneToManyField(autoDTO_QY_DBXX_JC.getClass());
		
		HashMap<String, HashMap<String, Integer>> dataCount = new HashMap<String, HashMap<String, Integer>>();
		for (int i = 0; i < oneToManyFieldList.size(); i++) {
			if(ReflectOperation.getGenericClass(oneToManyFieldList.get(i).getGenericType()).getName().equals(AutoDTO_QY_DBHTXX.class.getName())){
				Set<zxptsystem.dto.AutoDTO_QY_DBHTXX> autoDTO_QY_DBHTXXSet = autoDTO_QY_DBXX_JC.getOneToMany_QY_DBHTXX();
				Iterator<zxptsystem.dto.AutoDTO_QY_DBHTXX> it = autoDTO_QY_DBHTXXSet.iterator();
				while (it.hasNext()) {
					AutoDTO_QY_DBHTXX autoDTO_QY_DBHTXX = it.next();
					String BZDBXS = autoDTO_QY_DBHTXX.getBZDBXS();
					if(BZDBXS.equals("2") || BZDBXS.equals("3")){
						if(dataCount.containsKey(autoDTO_QY_DBHTXX.getBZHTBH())){
							HashMap<String, Integer> tempData = dataCount.get(autoDTO_QY_DBHTXX.getBZHTBH());
							if(tempData.get(autoDTO_QY_DBHTXX.getBZRDDKKBM()) == null){
								tempData.put(autoDTO_QY_DBHTXX.getBZRDDKKBM(), 1);
							}
						}else{
							HashMap<String, Integer> tempData = new HashMap<String, Integer>();
							tempData.put(autoDTO_QY_DBHTXX.getBZRDDKKBM(), 1);
							dataCount.put(autoDTO_QY_DBHTXX.getBZHTBH(), tempData);
						}
					}
					
				}
			}
			if(ReflectOperation.getGenericClass(oneToManyFieldList.get(i).getGenericType()).getName().equals(AutoDTO_QY_ZRRBZHTXX.class.getName())){
				Set<zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX> autoDTO_QY_ZRRBZHTXXSet = autoDTO_QY_DBXX_JC.getOneToMany_QY_ZRRBZHTXX();
				Iterator<zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX> it = autoDTO_QY_ZRRBZHTXXSet.iterator();
				while (it.hasNext()) {
					AutoDTO_QY_ZRRBZHTXX autoDTO_QY_ZRRBZHTXX = it.next();
					String BZDBXS = autoDTO_QY_ZRRBZHTXX.getBZDBXS();
					if(BZDBXS.equals("2") || BZDBXS.equals("3")){
						if(dataCount.containsKey(autoDTO_QY_ZRRBZHTXX.getBZHTBH())){
							HashMap<String, Integer> tempData = dataCount.get(autoDTO_QY_ZRRBZHTXX.getBZHTBH());
							if(tempData.get(autoDTO_QY_ZRRBZHTXX.getZJHM()) == null){
								tempData.put(autoDTO_QY_ZRRBZHTXX.getZJHM(), 1);
							}
						}else{
							HashMap<String, Integer> tempData = new HashMap<String, Integer>();
							tempData.put(autoDTO_QY_ZRRBZHTXX.getZJHM(), 1);
							dataCount.put(autoDTO_QY_ZRRBZHTXX.getBZHTBH(), tempData);
						}
					}
					
				}
			}
		}
		
		HashMap<String, Integer> dataMessageCount = new HashMap<String, Integer>();
		Iterator it = dataCount.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Iterator iter = ((HashMap<String, Integer>) entry.getValue()).entrySet().iterator();
			Integer count = 0;
			while (iter.hasNext()) {
				Map.Entry entry2 = (Map.Entry) iter.next();
				count = count + (Integer) entry2.getValue();
			}
			dataMessageCount.put(entry.getKey().toString(), count);
		}
		

		Iterator itMessage = dataMessageCount.entrySet().iterator();
		boolean isContainTitle=false;
		while (itMessage.hasNext()) {
			Map.Entry entry = (Map.Entry) itMessage.next();
			if((Integer)entry.getValue() < 2){
				if(!isContain){
					if(!isContainTitle){
						isContainTitle=true;
						message+="\\r\\n同一基础段下，若保证担保形式为多人联保或多人分保时，担保人不可少于两个";
						message+="\\r\\n-------------------------------------------------------------------";
						message+="\\r\\n信贷业务种类                                                      主合同编号                    ";
					}
				}
				if(!message.contains("\\r\\n"+strXDYWZL+"                                                      "+strZHTBH+"")){
					message+="\\r\\n"+strXDYWZL+"                                                      "+strZHTBH+"";
				}
			}
			
		}

		return message;
	}
		
}
