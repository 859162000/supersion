package szzxpt.service.add;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import szzxpt.dto.YWDATA;
import szzxpt.dto.condition.YWDATA_Condition;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class ShowYWDATAConditionAdd implements IAdd {

	public void Add() throws Exception {
		YWDATA_Condition yWDATA_Condition = (YWDATA_Condition)RequestManager.getTOject();

		if(yWDATA_Condition != null){
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(YWDATA.class);
			detachedCriteria.addOrder(Order.desc("DATTIME"));
			List<YWDATA> yWDATAList = (List<YWDATA>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			String DTTime="";
			if(yWDATAList.size()>0){
				DTTime=yWDATAList.get(0).getDATTIME().toString();
				if(StringUtils.isBlank(yWDATA_Condition.getStartDate())){
					yWDATA_Condition.setStartDate(DTTime);
				}
				if(StringUtils.isBlank(yWDATA_Condition.getEndDate())){
					yWDATA_Condition.setEndDate(DTTime);
				}
			}
			
		}
	}

}
