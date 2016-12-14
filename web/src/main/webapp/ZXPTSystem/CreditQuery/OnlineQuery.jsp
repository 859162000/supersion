<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="coresystem.dto.UserInfo"%>
<%@page import="framework.security.SecurityContext"%>
<%
String path = request.getContextPath();
String id=request.getParameter("id");
zxptsystem.helper.download.OnLineQuery  onlineQuery=new zxptsystem.helper.download.OnLineQuery();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String,String> map =onlineQuery.OnLineQuery(id);
String loginUrl=map.get("ip")+"/logon.do?orgCode="+map.get("orgCode")+"&userid="+map.get("userId")+"&password="+map.get("pwd");

String redirectUrl=map.get("ip")+"/detail_qt_new.do?loancard="+map.get("strInCode")+"&crccode="+map.get("crcCode")+"&attribute=1&borrnatucode=1&loancard="+map.get("strInCode")+"&curtype=00101&somePage=";
String sessionFactory=framework.security.SecurityContext.getInstance().getLoginInfo().getSessionFactory();
UserInfo user = (UserInfo)SecurityContext.getInstance().getLoginInfo().getTag();
zxptsystem.service.imps.CreditReportLogHelper.LogQyInfo(id,"Intranet_QY_Online",map.get("userId"),sessionFactory,user);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>在线查询功能</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
		#iframe{border:1px solid silver;width:1px;height:1px;padding:2px;}     
		object{border:1px solid silver;width:1px;height:1px;}
	</style> 
	<script type="text/javascript">
		function btnClick(){
			var ifra=document.getElementById("iframe");       
			ifra.innerHTML='<object id="obj1" type="text/html" data="<%=loginUrl%>"></object>';     
			setTimeout('document.getElementById("two").click()',500);		
		} 
	</script>
  </head>
  
  <body onload="btnClick()">       
	<a id="two" href="<%=redirectUrl%>" target="_self">
	123
	</a>
	<div id="iframe" ><p>使用Object实现Iframe功能</p>这里显示你打开的页面内容</div>
</body>
</html>
