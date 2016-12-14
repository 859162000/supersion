package zxptsystem.service.check;

import java.util.Date;
import java.util.Map;

import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.TypeParse;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;

/**
 * 职业信息段:本单位工作起始年份<=当前年份
 * @author xiajieli
 *
 */
public class GR_ZYXX_Check implements ICheckWithParam {
	
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		MessageResult messageResult = new MessageResult();
		try{
			String BDWGZQSNF = null;//本单位工作起始年份
			
			if(mapObject.get("BDWGZQSNF")!=null && !mapObject.get("BDWGZQSNF").equals("")){
				BDWGZQSNF=mapObject.get("BDWGZQSNF").toString();
			}

			 if(BDWGZQSNF!=null && TypeParse.parseInt(BDWGZQSNF) > TypeParse.parseInt(HelpTool.getSystemCurrentYear())){
				 messageResult.setSuccess(false);
				 messageResult.getMessageSet().add("“本单位工作起始年份”应小于等于当前年份:" + TypeParse.parseInt(HelpTool.getSystemCurrentYear()));
			}
			
		}catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			throw new Exception(ex);
		}
		
		return messageResult;
		
	}


}
