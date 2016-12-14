package zxptsystem.service.add;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.AutoDTO_GR_GRXX_JC;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class AutoDTO_GR_GRXX_JCAdd implements IAdd{

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void Add() throws Exception {
		Object object = RequestManager.getTOject();
		Object ValueZHYYZXXTS = ReflectOperation.getFieldValue(object, "ZHYYZXXTS");
		Object ValueHKZT = ReflectOperation.getFieldValue(object, "HKZT");
		Object ValueHKZT_24 = ReflectOperation.getFieldValue(object, "HKZT_24");
		Object ValueJRJGDM = ReflectOperation.getFieldValue(object, "JRJGDM");
		Object ValueYWH = ReflectOperation.getFieldValue(object, "YWH");
		Object ValueJSYHKRQ = ReflectOperation.getFieldValue(object, "JSYHKRQ");
		Object ValueKHRQ = ReflectOperation.getFieldValue(object, "KHRQ");
		if(ValueZHYYZXXTS != null && ValueHKZT_24 != null && ValueJRJGDM != null && ValueYWH != null){
			Date dateKHRQ=TypeParse.parseDate(ValueKHRQ.toString());
			Date dateJSYHKRQ=TypeParse.parseDate(ValueJSYHKRQ.toString());
			if(dateKHRQ!=null && dateJSYHKRQ!=null && dateKHRQ.getYear() == dateJSYHKRQ.getYear() && dateKHRQ.getMonth() == dateJSYHKRQ.getMonth()){
				ValueHKZT = "///////////////////////" + ValueHKZT_24.toString();
			}
			else{
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_GR_GRXX_JC.class);
				detachedCriteria.add(Restrictions.eq("JRJGDM", ValueJRJGDM));
				detachedCriteria.add(Restrictions.eq("YWH", ValueYWH));
				if(ValueJSYHKRQ !=null && !StringUtils.isBlank(ValueJSYHKRQ.toString())){
					Date dtbyLASTJSYHKRQ=TypeParse.parseDate(ValueJSYHKRQ.toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(dtbyLASTJSYHKRQ);
					int year = cal.get(Calendar.YEAR);
					int month=cal.get(Calendar.MONTH);
					Calendar cal1 = Calendar.getInstance();
					cal1.set(year, month, 1);
					Date dateman=cal1.getTime();
					detachedCriteria.add(Restrictions.lt("JSYHKRQ", TypeParse.parseString(dateman, "yyyyMMdd")));
				}
				detachedCriteria.addOrder(Order.desc("JSYHKRQ"));
				List<AutoDTO_GR_GRXX_JC> autoDTO_GR_GRXX_JCList = (List<AutoDTO_GR_GRXX_JC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				Date dtValueJSYHKRQ = TypeParse.parseDate(ValueJSYHKRQ.toString());
				Date dtKHRQ = TypeParse.parseDate(ValueKHRQ.toString());
				if(dtValueJSYHKRQ!=null && dtKHRQ!=null){
					int NumberMonth = (dtValueJSYHKRQ.getYear() - dtKHRQ.getYear()) * 12 + (dtValueJSYHKRQ.getMonth() - dtKHRQ.getMonth());
					if(autoDTO_GR_GRXX_JCList == null || autoDTO_GR_GRXX_JCList.size() == 0){
						if(ValueHKZT==null || StringUtils.isBlank(ValueHKZT.toString())){
							if(NumberMonth >= 23){
								ValueHKZT = "#######################" + ValueHKZT_24.toString();
							}
							else if(NumberMonth >= 0 && NumberMonth < 23){
								ValueHKZT = "";
								for(int i=23;i>0;i--){
									if(i > NumberMonth){
										ValueHKZT = ValueHKZT.toString() + "/";
									}
									else{
										ValueHKZT = ValueHKZT.toString() + "#";
									}
								}
								ValueHKZT=ValueHKZT.toString()+ValueHKZT_24.toString();
							}
						}
					}
					else{
						Date dtJSYHKRQ = TypeParse.parseDate(autoDTO_GR_GRXX_JCList.get(0).getJSYHKRQ().toString());
						int JudgementMonth = (dtValueJSYHKRQ.getYear() - dtJSYHKRQ.getYear()) * 12 + (dtValueJSYHKRQ.getMonth() - dtJSYHKRQ.getMonth());
						if(JudgementMonth == 1){
							ValueHKZT = autoDTO_GR_GRXX_JCList.get(0).getHKZT().toString().substring(1) + ValueHKZT_24.toString();
						}
						else{
							ValueHKZT = autoDTO_GR_GRXX_JCList.get(0).getHKZT().toString().substring(JudgementMonth);
							for(int i = JudgementMonth;i>1;i--){
								ValueHKZT = ValueHKZT.toString() + "#";
							}
							ValueHKZT = ValueHKZT.toString()+ValueHKZT_24.toString();
						}
					}
				}
			}
			if(ValueHKZT !=null){
				ReflectOperation.setFieldValue(object, "HKZT", ValueHKZT);
			}
		}	
	}
}

		
		
		
