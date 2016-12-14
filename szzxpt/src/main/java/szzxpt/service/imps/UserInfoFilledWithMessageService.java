package szzxpt.service.imps;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import szzxpt.dto.RoleInfoCopy;



import com.opensymphony.xwork2.ActionContext;
import coresystem.dto.RoleInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseService;

public class UserInfoFilledWithMessageService  extends BaseService {

	@SuppressWarnings("unchecked")
	public void initSuccessResult() throws Exception {
		// TODO Auto-generated method stub

		HttpServletRequest request=(HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		//1.获取当前的主要个人的基本信息
		Object strInstCode=request.getParameter("strInstCode");
		if(strInstCode==null){
			strInstCode="";
		}
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RoleInfo.class);
		detachedCriteria.add(Restrictions.ilike("strRoleCode", strInstCode.toString(), MatchMode.START));
		List<RoleInfo> roleInfoList=(List<RoleInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		List<RoleInfoCopy> roleInfoCopylist=new ArrayList<RoleInfoCopy>();
		if(roleInfoList.size()==0){
			detachedCriteria = DetachedCriteria.forClass(RoleInfo.class);
			roleInfoList=(List<RoleInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		}
		for(RoleInfo role : roleInfoList){
			RoleInfoCopy copy=new RoleInfoCopy();
			copy.setStrRoleCode(role.getStrRoleCode());
			copy.setStrRoleName(role.getStrRoleName());
			roleInfoCopylist.add(copy);
		}
		
		JSONArray jsonArray = JSONArray.fromObject(roleInfoCopylist);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
	     //写入到前台
	    response.getWriter().write(jsonArray.toString());
	}
}
