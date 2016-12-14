package zxptsystem.service.check;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.AutoDTO_QY_DBXX_JC;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;

/* 
 * 担保合同信息段 ，自然人保证合同信息段
 * 1、同一基础段下&保证合同编号相同，当保证担保形式为单人担保时，（1） 不可存在多个担保人
 * 2、同一基础段下，当保证担保形式为多人联保时，（1）每个担保人的保证金额必须相同 （2）每个担保人的币种必须相同
 */
public class QY_DBYW_BZDBXSCheck implements ICheckWithParam {
	
	@SuppressWarnings("unchecked")
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		
		String strDtoName="";
		if(mapObject.containsKey("BZRDDKKBM")){
			strDtoName="zxptsystem.dto.AutoDTO_QY_DBHTXX";
		}else{
			strDtoName="zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX";
		}
		MessageResult messageResult = new MessageResult();
		
		String strBZHTBH= "" ;
		String strBZDBXS= ""; 
		String strBZRMC= "";   
		String strBZJE= "";    
		String strBZ= ""; 
		
		if(mapObject.get("BZHTBH")!=null){
			strBZHTBH=mapObject.get("BZHTBH").toString();
		}
		if(mapObject.get("BZDBXS")!=null){
			strBZDBXS=mapObject.get("BZDBXS").toString();
		}
		if(mapObject.get("BZRMC")!=null){
			strBZRMC=mapObject.get("BZRMC").toString();
		}
		if(mapObject.get("BZJE")!=null){
			strBZJE=mapObject.get("BZJE").toString();
		}
		if(mapObject.get("BZ")!=null){
			strBZ=mapObject.get("BZ").toString();
		}
		
		Object objforeinId = mapObject.get("FOREIGNID");
		
		IParamObjectResultExecute singleObjectFindByIdDao =(IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoDTO_QY_DBXX_JC autoDTO_QY_DBXX_JC = (AutoDTO_QY_DBXX_JC) singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DBXX_JC.class.getName(),objforeinId,null});
		
		Object strBZRDDKKBM="";
		if(strDtoName.equals("zxptsystem.dto.AutoDTO_QY_DBHTXX")){
			strBZRDDKKBM=mapObject.get("BZRDDKKBM");
		}
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria=null;
		if(strDtoName.equals("zxptsystem.dto.AutoDTO_QY_DBHTXX")){
			detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DBHTXX"));
		}else if(strDtoName.equals("zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX")){
			detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX"));
		}
		if(mapObject.get("AUTOID")!=null){
			detachedCriteria.add(Restrictions.ne("autoID", mapObject.get("AUTOID")));
		}
		detachedCriteria.add(Restrictions.eq("FOREIGNID", autoDTO_QY_DBXX_JC));
		detachedCriteria.add(Restrictions.eq("BZHTBH", strBZHTBH)); //保证合同编号相同
		List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(objectList.size()>0){
			for (Object object : objectList) {
				if(strBZDBXS.equals("1")){
					if(strDtoName.equals("zxptsystem.dto.AutoDTO_QY_DBHTXX")){
						if(ReflectOperation.getFieldValue(object, "BZRDDKKBM")!=null && !ReflectOperation.getFieldValue(object, "BZRDDKKBM").toString().equals(strBZRDDKKBM)){
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("同一基础段下，当保证担保形式为单人担保时，保证人贷款卡编码必须相同");
						}
					}
					if(ReflectOperation.getFieldValue(object, "BZRMC")!=null && !ReflectOperation.getFieldValue(object, "BZRMC").toString().equals(strBZRMC)){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("同一基础段下，当保证担保形式为单人担保时，不可存在多个担保人");
					}else{
						List<Object> objectDBHTXXList=getObjectList(strDtoName, autoDTO_QY_DBXX_JC, strBZHTBH, strBZDBXS, strBZRMC);
						if(objectDBHTXXList.size()>0){
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("同一基础段下，当保证担保形式为单人担保时，不可存在多个担保人");
						}
					}
				
			   }else if(strBZDBXS.equals("2")){
					if(ReflectOperation.getFieldValue(object, "BZ")!=null && !ReflectOperation.getFieldValue(object, "BZ").toString().equals(strBZ)){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("同一基础段下，当保证担保形式为多人联保时，每个担保人的币种必须相同");
					}else{
						List<Object> objectDBHTXXList=getObjectList(strDtoName, autoDTO_QY_DBXX_JC, strBZHTBH, strBZDBXS, strBZ);
						if(objectDBHTXXList.size()>0){
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("同一基础段下，当保证担保形式为多人联保时，每个担保人的币种必须相同");
						}
					}
					
					if(ReflectOperation.getFieldValue(object, "BZJE")!=null && !ReflectOperation.getFieldValue(object, "BZJE").toString().equals(strBZJE)){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("同一基础段下，当保证担保形式为多人联保时，每个担保人的保证金额必须相同");
					}else{
						List<Object> objectDBHTXXList=getObjectList(strDtoName, autoDTO_QY_DBXX_JC, strBZHTBH, strBZDBXS, strBZJE);
						if(objectDBHTXXList.size()>0){
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("同一基础段下，当保证担保形式为多人联保时，每个担保人的保证金额必须相同");
						}
					}
			   }
		    }
	    }
		return messageResult;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object> getObjectList(String strDtoName,Object objforeinId,String strBZHTBH,String strBZDBXS,String str) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria=null;
		if(strDtoName.equals("zxptsystem.dto.AutoDTO_QY_DBHTXX")){
			detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX"));
		}else if(strDtoName.equals("zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX")){
			detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DBHTXX"));
		}
		detachedCriteria.add(Restrictions.eq("FOREIGNID", objforeinId));
		detachedCriteria.add(Restrictions.eq("BZHTBH", strBZHTBH));
		detachedCriteria.add(Restrictions.eq("BZDBXS", strBZDBXS));
		detachedCriteria.add(Restrictions.eq("BZRMC", str));
		List<Object> objectDBHTXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		return objectDBHTXXList;
	}
}
