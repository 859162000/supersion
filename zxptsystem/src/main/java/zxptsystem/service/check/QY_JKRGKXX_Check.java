package zxptsystem.service.check;

import java.util.Date;
import java.util.Map;

import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.TypeParse;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;

/**
 * 借款人概况信息段:借款人成立年份<=当前年份
 * @author xiajieli
 *
 */
public class QY_JKRGKXX_Check implements ICheckWithParam {
	
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		MessageResult messageResult = new MessageResult();
		try{
			String JKRCLNF = null;//本单位工作起始年份
			
			if(mapObject.get("JKRCLNF")!=null && !mapObject.get("JKRCLNF").equals("")){
				JKRCLNF=mapObject.get("JKRCLNF").toString();
			}
			
			 if(JKRCLNF!=null &&  TypeParse.parseInt(JKRCLNF) > TypeParse.parseInt(HelpTool.getSystemCurrentYear())){
				 messageResult.setSuccess(false);
				 messageResult.getMessageSet().add("“借款人成立年份”应小于等于当前年份:" + TypeParse.parseInt(HelpTool.getSystemCurrentYear()));
			}
			
		}catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			throw new Exception(ex);
		}
		
		return messageResult;
		
	}


}
