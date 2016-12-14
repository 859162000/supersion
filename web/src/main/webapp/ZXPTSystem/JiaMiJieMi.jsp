<%@ page contentType="text/html;charset=UTF-8" import="java.util.*" import="framework.show.ShowContext"  language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"></base>
		<title>RportPrepCheck</title>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/JSGrid.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/ShowListCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath %>js/Comml.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/JsGrid.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/ShowList.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript" ></script>
		<script type="text/javascript">
			function onOperationClick(){
				if(form1.uploadFile.value==null || form1.uploadFile.value==""){
					alert("请选择文件!");
		        }else{
					form1.submit();
				}
			}
		</script>
	</head>
	<body ondragstart="return false;">
		<form name="form1" enctype="multipart/form-data" method="post" action="<%=basePath %>/framework/Jiajiemi.action">
			<div id="Menu_Frame_Div" class="Menu_Frame_Div">
				<s:file id="uploadFile" name="uploadFile" style="float:left;"></s:file>
				<a class="Menu_Frame_Div_Cell" href="javascript:onOperationClick()" style="float:left;">处理 </a>
			</div>
		</form>
	</body>
</html>