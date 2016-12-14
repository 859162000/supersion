package zxptsystem.service.imps;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.AutoDTO_QY_BLXDZCCL_JC;
import zxptsystem.dto.AutoDTO_QY_DBXX_JC;
import zxptsystem.dto.AutoDTO_QY_DKXX_JC;
import zxptsystem.dto.AutoDTO_QY_DKYWXX;
import zxptsystem.dto.AutoDTO_QY_JJXX;
import zxptsystem.dto.AutoDTO_QY_RZYWXX;
import framework.helper.FrameworkFactory;
import framework.interfaces.ActionJumpResult;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.imps.BaseService;
import framework.services.interfaces.MessageResult;

public class PositionJumpService extends BaseService{
	@SuppressWarnings("unchecked")
	public void initSuccessResult() throws Exception {

		String[] idLIst = (String[])RequestManager.getIdList();
		MessageResult messageResult = new MessageResult();
		
		// 处理跳转信息
		ActionJumpResult actionJumpResult = new ActionJumpResult();
		String actionName = RequestManager.getActionName();
		String dtoName = "";

		if(idLIst == null || idLIst.length !=1){
			messageResult.setSuccess(false);
			messageResult.setMessage("请只勾选一条数据");
			this.setServiceResult(messageResult);
			if(actionName.indexOf("@")>-1){
				actionName = actionName.split("@")[1];
			}
			if(actionName.startsWith("PositionJump")){
				actionName = actionName.replace("PositionJumpJC", "").replace("PositionJump", "");
			}
			
			SessionManager.setFirstActionName(RequestManager.getTName(), actionName);
			return;
		}
		String tName = RequestManager.getTOject().getClass().getName().replace("_Condition", "").replace(".condition", "");
		ServletActionContext.getContext().getSession().put("JumpMXLevel", tName+idLIst[0]);
		
		
		// 基础之间跳转
		if(actionName.indexOf("PositionJumpJC")>-1){
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			actionName = actionName.replace("PositionJumpJC", "");
			actionName = actionName.substring(0, actionName.indexOf("-"));
			
			if(actionName.indexOf("@")>-1){
				String[] actionNameArr = actionName.split("@");
				actionName = actionNameArr[1];
				dtoName = actionNameArr[0];
			}
			else if(tName.equals(AutoDTO_QY_DBXX_JC.class.getName())){
				AutoDTO_QY_DBXX_JC autoDTO_QY_DBXX_JC = (AutoDTO_QY_DBXX_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DBXX_JC.class.getName(), idLIst[0] ,null});
				String XDYWZL = autoDTO_QY_DBXX_JC.getXDYWZL();
				if(XDYWZL.equals("1")){
					dtoName = "zxptsystem.dto.AutoDTO_QY_DKYW_JC";
				}
				else if(XDYWZL.equals("2")){
					dtoName = "zxptsystem.dto.AutoDTO_QY_BLYW_JC";
				}
				else if(XDYWZL.equals("4")){
					dtoName = "zxptsystem.dto.AutoDTO_QY_MYRZ_JC";
				}
				else if(XDYWZL.equals("5")){
					dtoName = "zxptsystem.dto.AutoDTO_QY_XYZYW_JC";
				}
				else if(XDYWZL.equals("6")){
					dtoName = "zxptsystem.dto.AutoDTO_QY_BHYW_JC";
				}
				else if(XDYWZL.equals("7")){
					dtoName = "zxptsystem.dto.AutoDTO_QY_YHCDHP_JC";
				}
				else{
					messageResult.setSuccess(false);
					messageResult.setMessage("不能跳转至信贷业务种类对应表");
					this.setServiceResult(messageResult);
					SessionManager.setFirstActionName(RequestManager.getTName(), actionName);
					return;
				}
			}
			else if(tName.equals(AutoDTO_QY_DKXX_JC.class.getName())){
				AutoDTO_QY_DKXX_JC autoDTO_QY_DKXX_JC = (AutoDTO_QY_DKXX_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DKXX_JC.class.getName(), idLIst[0] ,null});
				String DKZL = null;
				Set<AutoDTO_QY_DKYWXX> XDYWZLSet = (Set<AutoDTO_QY_DKYWXX>) autoDTO_QY_DKXX_JC.getOneToMany_QY_DKYWXX();
				if(XDYWZLSet.size()<0 || XDYWZLSet.size()>1){
					messageResult.setSuccess(false);
					messageResult.setMessage("垫款业务信息对应"+XDYWZLSet.size()+"个种类，不能精确跳转");
					this.setServiceResult(messageResult);
					SessionManager.setFirstActionName(RequestManager.getTName(), actionName);
					return;
				}
				Iterator<AutoDTO_QY_DKYWXX> it = XDYWZLSet.iterator();
				while(it.hasNext()){
					DKZL = ((AutoDTO_QY_DKYWXX)it.next()).getDKZL();
				}
				if(DKZL!=null){
					if(DKZL.equals("1")){
						dtoName = "zxptsystem.dto.AutoDTO_QY_BHYW_JC";
					}
					else if(DKZL.equals("2")){
						dtoName = "zxptsystem.dto.AutoDTO_QY_YHCDHP_JC";
					}
					else if(DKZL.equals("3")){
						dtoName = "zxptsystem.dto.AutoDTO_QY_XYZYW_JC";
					}
					else if(DKZL.equals("4")){
						dtoName = "zxptsystem.dto.AutoDTO_QY_BLYW_JC";
					}
					else{
						messageResult.setSuccess(false);
						messageResult.setMessage("不能跳转至垫款种类对应表");
						this.setServiceResult(messageResult);
						SessionManager.setFirstActionName(RequestManager.getTName(), actionName);
						return;
					}
				}
			}
			else if(tName.equals(AutoDTO_QY_BLXDZCCL_JC.class.getName())){
				AutoDTO_QY_BLXDZCCL_JC autoDTO_QY_BLXDZCCL_JC = (AutoDTO_QY_BLXDZCCL_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_BLXDZCCL_JC.class.getName(), idLIst[0] ,null});
				String YWBH = autoDTO_QY_BLXDZCCL_JC.getYWBH();
				String JRJGDM = autoDTO_QY_BLXDZCCL_JC.getJRJGDM();
				
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(AutoDTO_QY_DKXX_JC.class);
				detachedCriteria1.add(Restrictions.eq("JRJGDM", JRJGDM));
				detachedCriteria1.add(Restrictions.eq("DKYWHM", YWBH));
				List<AutoDTO_QY_DKXX_JC> autoDTO_QY_DKXX_JCList = (List<AutoDTO_QY_DKXX_JC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria1,null});
				
				
				DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(AutoDTO_QY_JJXX.class);
				detachedCriteria2.add(Restrictions.eq("JJBH", YWBH));
				List<AutoDTO_QY_JJXX> autoDTO_QY_JJXXList = (List<AutoDTO_QY_JJXX>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria2,null});
				
				
				DetachedCriteria detachedCriteria3 = DetachedCriteria.forClass(AutoDTO_QY_RZYWXX.class);
				detachedCriteria3.add(Restrictions.eq("RZYWBH", YWBH));
				List<AutoDTO_QY_RZYWXX> autoDTO_QY_RZYWXXList = (List<AutoDTO_QY_RZYWXX>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria3,null});
				
				int YWBHCount =  0;
				if(autoDTO_QY_JJXXList!=null && autoDTO_QY_JJXXList.size()>0){
					dtoName = "zxptsystem.dto.AutoDTO_QY_DKYW_JC";
					YWBHCount++;
				}
				if(autoDTO_QY_DKXX_JCList!=null && autoDTO_QY_DKXX_JCList.size()>0){
						dtoName = "zxptsystem.dto.AutoDTO_QY_DKXX_JC";
						YWBHCount++;
					}
				if(autoDTO_QY_RZYWXXList!=null && autoDTO_QY_RZYWXXList.size()>0){
					dtoName = "zxptsystem.dto.AutoDTO_QY_MYRZ_JC";
					YWBHCount++;
				}
				
				if(YWBHCount<=0 || YWBHCount>1){
					messageResult.setSuccess(false);
					messageResult.setMessage("贷款业务的业务编号对应"+YWBHCount+"张业务表，不能精确跳转");
					this.setServiceResult(messageResult);
					SessionManager.setFirstActionName(RequestManager.getTName(), actionName+"-"+RequestManager.getTName());
					return;
				}
			}
		}else{ 
			// 明细之间跳转
			actionName = actionName.replace("PositionJump", "");
			actionName = actionName.substring(0, actionName.indexOf("-"));
			if(actionName.indexOf("@")>-1){
				String[] actionNameArr = actionName.split("@");
				actionName = actionNameArr[1];
				dtoName = actionNameArr[0];
			}
		}

		String windowId = RequestManager.getWindowId();	
		actionName = actionName+"-"+dtoName+"?WindowId="+windowId;
		actionJumpResult.setActionName(actionName);
		actionJumpResult.setActionNamespace(RequestManager.getNamespace());
		this.setServiceResult(actionJumpResult);
	}
}
