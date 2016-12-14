package zxptsystem.service.check;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import zxptsystem.dto.AutoDTO_QY_GJGLRQK;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;
/**
 * 企业-高级管理人情况
 * 当证件类型为身份证时，对证件号码与性别之间的校验
 * @author Transino
 *
 */
public class GJGLRQK_Check implements ICheckWithParam {
	
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		Object valueXB=mapObject.get("XB");
		Object vakueGGRYCSRQ=mapObject.get("GGRYCSRQ");
		Object valueZJLX=mapObject.get("ZJLX");
		Object valueZJHM=mapObject.get("ZJHM");

		String fieldZXLXShowName=((Field) ReflectOperation.getFieldByName(AutoDTO_QY_GJGLRQK.class, "ZJLX")).getAnnotation(IColumn.class).description().toString();
		String fieldZXHMShowName=((Field) ReflectOperation.getFieldByName(AutoDTO_QY_GJGLRQK.class, "ZJHM")).getAnnotation(IColumn.class).description().toString();
		
		//1.处理类型
		if(valueZJLX !=null && valueZJHM!=null &&  !StringUtils.isBlank(valueZJLX.toString()) &&  !StringUtils.isBlank(valueZJHM.toString())){
			if(valueZJLX !=null && valueZJHM !=null &&valueZJLX.equals("0") && (valueZJHM.toString().length()==15 || valueZJHM.toString().length()==18)){
				int intsex=0;
				Object objbirth=null;
				if(valueZJHM.toString().length()==15){
					intsex=TypeParse.parseInt(valueZJHM.toString().substring(14)).intValue();
					objbirth="19"+valueZJHM.toString().substring(6,12);
				}
				else{
					intsex=TypeParse.parseInt(valueZJHM.toString().substring(16,17)).intValue();
					objbirth=valueZJHM.toString().substring(6,14);
				}
				if(valueXB !=null && (valueXB.equals("1") || valueXB.equals("2"))){
					if(valueXB.equals("2") && intsex%2 !=0){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("高级管理人情况段中"+fieldZXLXShowName+"为'0-身份证'且"+fieldZXHMShowName+"为"+valueZJHM.toString().length()+"位时，"+fieldZXHMShowName+"的性别与所选择的性别女不一致");
					}
					else if(valueXB.equals("1") && intsex%2 ==0){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("高级管理人情况段中"+fieldZXLXShowName+"为'0-身份证'且"+fieldZXHMShowName+"为"+valueZJHM.toString().length()+"位时，"+fieldZXHMShowName+"的性别与所选择的性别男不一致");
					}
				}
				if(vakueGGRYCSRQ !=null && !objbirth.equals(vakueGGRYCSRQ)){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("高级管理人情况段中"+fieldZXLXShowName+"为'0-身份证'且"+fieldZXHMShowName+"为"+valueZJHM.toString().length()+"位时，"+fieldZXHMShowName+"的出生日期("+objbirth+")与所选择的高管人员出生日期("+vakueGGRYCSRQ+")不一致");
				}
			}
		}
		
		return messageResult;
		
	}

}
