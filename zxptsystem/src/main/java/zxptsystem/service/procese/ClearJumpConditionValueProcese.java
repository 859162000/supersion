package zxptsystem.service.procese;

import java.util.ArrayList;
import java.util.List;

import zxptsystem.dto.AutoDTO_QY_BHYW_JC;
import zxptsystem.dto.AutoDTO_QY_BLYW_JC;
import zxptsystem.dto.AutoDTO_QY_DBXX_JC;
import zxptsystem.dto.AutoDTO_QY_DKXX_JC;
import zxptsystem.dto.AutoDTO_QY_DKYW_JC;
import zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX;
import zxptsystem.dto.AutoDTO_QY_HKXX;
import zxptsystem.dto.AutoDTO_QY_JJXX;
import zxptsystem.dto.AutoDTO_QY_MYRZ_JC;
import zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX;
import zxptsystem.dto.AutoDTO_QY_RZYWHKXX;
import zxptsystem.dto.AutoDTO_QY_RZYWXX;
import zxptsystem.dto.AutoDTO_QY_XYZYW_JC;
import zxptsystem.dto.AutoDTO_QY_YHCDHP_JC;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;

public class ClearJumpConditionValueProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception{
		Object tNameObject = RequestManager.getTName();
		if(tNameObject!=null){
			List<String> fieldName = new ArrayList<String>();
			String tName = tNameObject.toString();
			Object tObject_Condition = SessionManager.getTCondition(tName);
			if(tName.equals(AutoDTO_QY_DKYW_ZQXX.class.getName())){
				fieldName.add("JJBH");
			}
			else if(tName.equals(AutoDTO_QY_HKXX.class.getName())){
				fieldName.add("JJBH");
			}
			else if(tName.equals(AutoDTO_QY_JJXX.class.getName())){
				fieldName.add("JJBH");
			}

			else if(tName.equals(AutoDTO_QY_MYRZ_ZQXX.class.getName())){
				fieldName.add("RZYWBH");
			}
			else if(tName.equals(AutoDTO_QY_RZYWXX.class.getName())){
				fieldName.add("RZYWBH");
			}
			else if(tName.equals(AutoDTO_QY_RZYWHKXX.class.getName())){
				fieldName.add("RZYWBH");
			}
			
			else if(tName.equals(AutoDTO_QY_DBXX_JC.class.getName())){
				fieldName.add("ZHTBH");
				fieldName.add("XDYWZL");
				fieldName.add("DKKBM");
				fieldName.add("JRJGDM");
			}
			else if(tName.equals(AutoDTO_QY_DKYW_JC.class.getName())){
				fieldName.add("DKHTHM");
				fieldName.add("DKKBM");
				fieldName.add("JRJGDM");
			}
			else if(tName.equals(AutoDTO_QY_BLYW_JC.class.getName())){
				fieldName.add("BLXYBH");
				fieldName.add("JRJGDM");
			}
			else if(tName.equals(AutoDTO_QY_MYRZ_JC.class.getName())){
				fieldName.add("RZXYBH");
				fieldName.add("DKKBM");
				fieldName.add("JRJGDM");
			}
			else if(tName.equals(AutoDTO_QY_XYZYW_JC.class.getName())){
				fieldName.add("XYZHM");
				fieldName.add("JRJGDM");
			}
			else if(tName.equals(AutoDTO_QY_BHYW_JC.class.getName())){
				fieldName.add("BHHM");
				fieldName.add("JRJGDM");
			}
			else if(tName.equals(AutoDTO_QY_YHCDHP_JC.class.getName())){
				fieldName.add("HPHM");
				fieldName.add("JRJGDM");
			}
			else if(tName.startsWith(AutoDTO_QY_DKXX_JC.class.getName())){
				fieldName.add("DKYWHM");
				fieldName.add("JRJGDM");
			}
			
			if(fieldName != null && tObject_Condition != null){
				for (int i = 0; i < fieldName.size(); i++) {
					ReflectOperation.setFieldValue(tObject_Condition, fieldName.get(i), "");
				}
				SessionManager.setTCondition(tObject_Condition, tName);
			}
		}
		return serviceResult;
	}
}
