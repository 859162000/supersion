package zxptsystem.service.procese;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.ServletActionContext;
import zxptsystem.dto.AutoDTO_QY_BHYW_JC;
import zxptsystem.dto.AutoDTO_QY_BLXDZCCL_JC;
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
import zxptsystem.dto.View_JG_JGJBXX;
import zxptsystem.dto.View_QY_JKRGKXX;

import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowContext;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;
/**
 * 跳转如新增界面时，填充默认数据
 * 1、据左边定位数据
 * 2、据所选机构所关联的金融机构代码
 * 3、据快捷跳转定位关联字段（查询中的关联字段数据）
 * @author Administrator
 *
 */
public class addPositionValueProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception{
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;

		String viewDTOName = View_QY_JKRGKXX.class.getName();
		IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		String id = "qyzx_strVersion";
		SystemParam qyzxVersionObj = (SystemParam)byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", id , null});
		if (qyzxVersionObj != null && qyzxVersionObj.getStrParamValue().equals("2.2")) {
			viewDTOName = View_JG_JGJBXX.class.getName();
		}

		Map<String, String> positionMap = ShowContext.getInstance().getShowEntityMap().get(RequestManager.getTName()+"-Position");
		Object JKRGKPosition = ServletActionContext.getContext().getSession().get(viewDTOName+".JKRGKPosition");
		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
			String fieldName = showFieldValue.getShowField().getFieldName();
			if(positionMap != null){
				for (Entry<String, String> entry : positionMap.entrySet()) {
					Object fieldValue = ServletActionContext.getContext().getSession().get(entry.getKey());
					if(fieldValue != null && !fieldValue.equals(viewDTOName) && fieldName.equals(entry.getValue())){
						if(entry.getKey().startsWith(viewDTOName)){
							if(JKRGKPosition != null && JKRGKPosition.equals("true")){
								showFieldValue.setFieldValue(fieldValue.toString());
								break;
							}
						}else{
							showFieldValue.setFieldValue(fieldValue.toString());
						}
						break;
					}
				}
			}
		}
		
		// 填充查询框中的关联字段值
		Object tNameObject = RequestManager.getTName();
		if(tNameObject!=null){
			String tName = tNameObject.toString();
			List<String> targetFieldName = new ArrayList<String>();
			Object fieldValue = "";
			
			if(tName.startsWith(AutoDTO_QY_DKYW_ZQXX.class.getName())){
				targetFieldName.add("JJBH");
			}
			else if(tName.startsWith(AutoDTO_QY_HKXX.class.getName())){
				targetFieldName.add("JJBH");
			}
			else if(tName.startsWith(AutoDTO_QY_JJXX.class.getName())){
				targetFieldName.add("JJBH");
			}

			else if(tName.startsWith(AutoDTO_QY_MYRZ_ZQXX.class.getName())){
				targetFieldName.add("RZYWBH");
			}
			else if(tName.startsWith(AutoDTO_QY_RZYWXX.class.getName())){
				targetFieldName.add("RZYWBH");
			}
			else if(tName.startsWith(AutoDTO_QY_RZYWHKXX.class.getName())){
				targetFieldName.add("RZYWBH");
			}

			else if(tName.startsWith(AutoDTO_QY_DBXX_JC.class.getName())){
				targetFieldName.add("ZHTBH");
				targetFieldName.add("XDYWZL");
				if(JKRGKPosition == null || !JKRGKPosition.equals("true")){
					targetFieldName.add("DKKBM");
				}
			}
			else if(tName.startsWith(AutoDTO_QY_DKYW_JC.class.getName())){
				targetFieldName.add("DKHTHM");
				if(JKRGKPosition == null || !JKRGKPosition.equals("true")){
					targetFieldName.add("DKKBM");
				}
			}
			else if(tName.startsWith(AutoDTO_QY_BLYW_JC.class.getName())){
				targetFieldName.add("BLXYBH");
			}
			else if(tName.startsWith(AutoDTO_QY_MYRZ_JC.class.getName())){
				targetFieldName.add("RZXYBH");
				if(JKRGKPosition == null || !JKRGKPosition.equals("true")){
					targetFieldName.add("DKKBM");
				}
			}
			else if(tName.startsWith(AutoDTO_QY_XYZYW_JC.class.getName())){
				targetFieldName.add("XYZHM");
			}
			else if(tName.startsWith(AutoDTO_QY_BHYW_JC.class.getName())){
				targetFieldName.add("BHHM");
			}
			else if(tName.startsWith(AutoDTO_QY_YHCDHP_JC.class.getName())){
				targetFieldName.add("HPHM");
			}
			else if(tName.startsWith(AutoDTO_QY_BLXDZCCL_JC.class.getName())){
				targetFieldName.add("YWBH");
				if(JKRGKPosition == null || !JKRGKPosition.equals("true")){
					targetFieldName.add("DKKBM");
				}
			}
			else if(tName.startsWith(AutoDTO_QY_DKXX_JC.class.getName())){
				targetFieldName.add("DKYWHM");
			}

			Object tObject_Condition = SessionManager.getTCondition(tName);
			if(targetFieldName != null && tObject_Condition != null){
				for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
					String fieldName = showFieldValue.getShowField().getFieldName();
					for (int i = 0; i < targetFieldName.size(); i++) {
						if(fieldName.equals(targetFieldName.get(i))){
							fieldValue = ReflectOperation.getFieldValue(tObject_Condition, targetFieldName.get(i));
							showFieldValue.setFieldValue(fieldValue);
							break;
						}
					}
				}
			}
		}

		return serviceResult;
	}
}
