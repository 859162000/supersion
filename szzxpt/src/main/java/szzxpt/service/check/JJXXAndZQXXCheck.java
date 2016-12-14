package szzxpt.service.check;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import zxptsystem.dto.AutoDTO_QY_DKYW_JC;
import zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX;
import zxptsystem.dto.AutoDTO_QY_JJXX;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class JJXXAndZQXXCheck implements ICheck{
	@SuppressWarnings("unchecked")
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		String tName=RequestManager.getTName();		
		if(tName.equals(AutoDTO_QY_DKYW_JC.class.getName())) {
			String [] idList=(String [])RequestManager.getIdList();
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_QY_DKYW_JC.class);
			detachedCriteria.add(Restrictions.in(ReflectOperation.getPrimaryKeyField(AutoDTO_QY_DKYW_JC.class.getName()).getName(), idList));
			List<AutoDTO_QY_DKYW_JC> dkywjcList=(List<AutoDTO_QY_DKYW_JC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			JJXXAndZQXX(dkywjcList,singleObjectFindByCriteriaDao,detachedCriteria,messageResult);			
		}
		return messageResult;	
	}
	@SuppressWarnings("unchecked")
	protected void JJXXAndZQXX(List<AutoDTO_QY_DKYW_JC> dkywjcList,IParamObjectResultExecute singleObjectFindByCriteriaDao, DetachedCriteria detachedCriteria,MessageResult messageResult) throws Exception {
		if(dkywjcList.size()>0){			
			for(AutoDTO_QY_DKYW_JC autoDTO_QY_DKYW_JC : dkywjcList) {
				detachedCriteria = DetachedCriteria.forClass(AutoDTO_QY_JJXX.class);
				detachedCriteria.add(Restrictions.ge("FOREIGNID", autoDTO_QY_DKYW_JC));
				List<AutoDTO_QY_JJXX> JJXXlist = (List<AutoDTO_QY_JJXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
				if(JJXXlist.size()>0) {
					for(AutoDTO_QY_JJXX autoDTO_QY_JJXX :JJXXlist) {
						if(autoDTO_QY_JJXX.getZQBZ().equals("1")) {
							detachedCriteria = DetachedCriteria.forClass(AutoDTO_QY_DKYW_ZQXX.class);
							detachedCriteria.add(Restrictions.ge("FOREIGNID", autoDTO_QY_DKYW_JC));
							detachedCriteria.add(Restrictions.ge("JJBH",autoDTO_QY_JJXX.getJJBH()));
							List<AutoDTO_QY_DKYW_ZQXX> ZQXXlist=(List<AutoDTO_QY_DKYW_ZQXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
							if(ZQXXlist.size()<=0) {
								messageResult.getMessageSet().add("基础段为(金融机构代码:"+autoDTO_QY_DKYW_JC.getJRJGDM()+",贷款卡编码:"+autoDTO_QY_DKYW_JC.getDKKBM()+",业务发生日期:"+autoDTO_QY_DKYW_JC.getYWFSRQ()+")的贷款业务信息报文中,当借据信息段(借据编号:"+autoDTO_QY_JJXX.getJJBH()+")中展期标志为 1-是 时,必须录入展期标志段(借据编号:"+autoDTO_QY_JJXX.getJJBH()+")展期信息");
								messageResult.setSuccess(false);
								messageResult.AlertTranslate();
							}
						}
					}
				}
			}
		}
	}
}
