package zxptsystem.service.check;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;

/**
 * 042特殊交易段中“发生日期”>=基础段中“开户日期”，“发生日期”<=当前系统日期
 * @author xiajieli
 *
 */
public class GR_TSJY_Check implements ICheckWithParam {
	
	@SuppressWarnings("unchecked")
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		MessageResult messageResult = new MessageResult();
		try{
			Date KHRQ = null;//开户日期
			Date FSRQ = null;//发生日期
			String FOREIGNID="";
			
			if(mapObject.get("FSRQ")!=null && !mapObject.get("FSRQ").equals("")){
				FSRQ=TypeParse.parseDate(mapObject.get("FSRQ").toString());
			}
		
			if(mapObject.get("FOREIGNID")!=null && !mapObject.get("FOREIGNID").equals("")){
				FOREIGNID=mapObject.get("FOREIGNID").toString();
			}
			
			//查询基础段
	    	 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GR_GRXX_JC"));
			 detachedCriteria.add(Restrictions.eq("autoID",FOREIGNID));
			 List<Object> objectJC = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			 
			 if(objectJC.size()>0){
				 if(ReflectOperation.getFieldValue(objectJC.get(0), "KHRQ")!=null){
					 KHRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objectJC.get(0), "KHRQ").toString());
				}
				 
				if(FSRQ!=null && KHRQ!=null && FSRQ.before(KHRQ)){
					 messageResult.setSuccess(false);
					 messageResult.getMessageSet().add("特殊交易段中“发生日期”>=基础段中“开户日期”");
				}
			 }
			 
			 if(FSRQ!=null && FSRQ.after(TypeParse.parseDate(HelpTool.getSystemCurrentDate()))){
				 messageResult.setSuccess(false);
				 messageResult.getMessageSet().add("“发生日期”应小于等于系统当前日期:" + HelpTool.getSystemCurrentDate());
			}
			
		}catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			throw new Exception(ex);
		}
		
		return messageResult;
		
	}


}
