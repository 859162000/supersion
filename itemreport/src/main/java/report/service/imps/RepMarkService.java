package report.service.imps;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.dto.RptInfo;

import com.opensymphony.xwork2.ActionContext;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseObjectDaoResultService;

public class RepMarkService extends BaseObjectDaoResultService{
	
	private String strRptCode;
	@Override
	public void initSuccessResult() throws Exception {
		
     	HttpServletRequest request=ServletActionContext.getRequest();
		strRptCode = request.getParameter("strRptCode").toString();
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		RptInfo rptInfo = (RptInfo) dao.paramObjectResultExecute(new Object[]{RptInfo.class.getName(),strRptCode,null});
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		String path = ServletActionContext.getRequest().getContextPath();
	    String basePath = ServletActionContext.getRequest().getScheme()
        +"://"+ServletActionContext.getRequest().getServerName()+":"
        +ServletActionContext.getRequest().getServerPort()+path+"/";
		response.setContentType("text/html");
		StringBuilder html = new StringBuilder("<html><head><title>填报说明</title>");
		html.append("<link rel='stylesheet' type='text/css' href='"+basePath+"css/themes/default/easyui.css'>");
		html.append("<script type='text/javascript' src='" + basePath + "js/jquery/jquery.1.7.js'></script>");
		html.append("<script type='text/javascript' src='"+basePath+"js/easyui/jquery.easyui.min.js'></script>");
		html.append("</head><body align=center>");
		if(rptInfo != null && StringUtils.isNotBlank(rptInfo.getRemark())){
			html.append("<textarea rows=30 style='width:100%' readonly=true>");
			html.append(rptInfo.getRemark());
			html.append("</textarea>");
		}
		else
			html.append("<h2 style='text-align:center;' >无</h2>");
		html.append("</body>");
		html.append("</html>");
		ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
		out.print(html.toString());
		out.close();
	}
	
}
