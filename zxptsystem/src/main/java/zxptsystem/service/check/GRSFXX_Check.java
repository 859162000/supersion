package zxptsystem.service.check;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import zxptsystem.dto.AutoDTO_GR_GRSFXX;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;
/**
 * 个人-身份信息段
 * 当证件类型为身份证时，对证件号码与性别之间的校验
 * @author Transino
 *
 */
public class GRSFXX_Check implements ICheckWithParam {
	
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		Object valueXB=mapObject.get("XB");
		Object valueZJLX=mapObject.get("POZJLX");
		Object valueZJHM=mapObject.get("POZJHM");

		String fieldZXLXShowName= ((Field) ReflectOperation.getFieldByName(AutoDTO_GR_GRSFXX.class, "POZJLX")).getAnnotation(IColumn.class).description().toString();
		String fieldZXHMShowName=((Field) ReflectOperation.getFieldByName(AutoDTO_GR_GRSFXX.class, "POZJHM")).getAnnotation(IColumn.class).description().toString();
		
		//判段用户的性别和出生日期
		if(valueZJLX !=null && valueZJHM!=null &&  !StringUtils.isBlank(valueZJLX.toString()) &&  !StringUtils.isBlank(valueZJHM.toString())){
			if(valueXB !=null &&  (valueXB.equals("1") || valueXB.equals("2"))){
				if(valueZJLX.equals("0") && (valueZJHM.toString().length()==15 || valueZJHM.toString().length()==18)){
					int intsex=0;
					if(valueZJHM.toString().length()==15){
						intsex=TypeParse.parseInt(valueZJHM.toString().substring(14)).intValue();
					}
					else{
						intsex=TypeParse.parseInt(valueZJHM.toString().substring(16,17)).intValue();
					}
					if(valueXB.equals("2") && intsex%2 !=0){
						
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("个人身份信息段中"+fieldZXLXShowName+"为'0-身份证'且"+fieldZXHMShowName+"为"+valueZJHM.toString().length()+"位时，"+fieldZXHMShowName+"的性别与个人身份信息段选择的性别女不一致");
					}
					else if(valueXB.equals("1") && intsex%2 ==0){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("个人身份信息段中"+fieldZXLXShowName+"为'0-身份证'且"+fieldZXHMShowName+"为"+valueZJHM.toString().length()+"位时，"+fieldZXHMShowName+"的性别与个人身份信息段所选择的性别男不一致");
					}
				}
			}
		}
	
		return messageResult;
		
	}

}
