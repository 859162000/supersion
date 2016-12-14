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
		<title>ShowList</title>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/JSGrid.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/ShowListCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
		<link href="css/RiskShowCss.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath %>js/Comml.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/JsGrid.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/ShowList.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript" ></script>
		<script type="text/javascript">
		
			var boo = false;
			function onOperationClick(){
				if(boo){
					alert("请勿重复提交,耐心等待!");
					return false;
				}
				var time = document.getElementById("time").value;
				if(time=="" || time==null){
					alert("数据时间不能为空!");
					return false;
				}
				
				var actionName=document.getElementById("tt").value;
				if(actionName=="" || actionName==null){
					alert("校验实例不能为空!");
					return false;
				}
				
				boo = true;
				document.form1.action = "<%=basePath %>/framework" + "/" + actionName + ".action";
				document.form1.submit();
				return true;
			}
		</script>

	</head>
	<body ondragstart="return false;">
		<form name="form1" method="post">
			<div class="Menu_Frame_Div">
				<a class="Menu_Frame_Div_Cell" onclick="onOperationClick();">预校验</a>
				<a class="Menu_Frame_Div_Cell" onclick="window.location.href='<%=basePath%>/framework/Download-coresystem.dto.UserInfo.action'">校验结果下载</a>
			</div>
			<div id="SelectDivFrame" style="width: 100%; height:40px;border-bottom:1px solid #0060A6;border-left: 0px solid #dadada;font-size: 12px; font-family: 微软雅黑;float: left;background-color:White;padding-top:2px;" >
				<div class="Select_Menu_Control_Frame_Div">
					<div class="Title" class="Font_css_normal_Condtion">数据时间</div>
					<div  class="Control_Frame">
						<s:textfield cssClass="Select_Menu_TextBox" name="dtDate" id="time" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"/>
					</div>
				</div>
				
				<div class="Select_Menu_Control_Frame_Div" style="width: 340px;">
					<div class="Title" class="Font_css_normal_Condtion">机构信息</div>
					<div  class="Control_Frame">
						<select name="strInstCode" id="strInstCode" class="Select_Menu_SelectBox" style="width:250px;_width:240px;">
							<option>--请选择--</option>
							<s:iterator value="serviceResult.get(1)" id="sr">
								<option value="<s:property value="%{#sr.key}" />"><s:property value="%{#sr.value}" /></option>
							</s:iterator>
						</select>
					</div>
				</div>
				
				<div class="Select_Menu_Control_Frame_Div" style="width: 340px;">
					<div class="Title" class="Font_css_normal_Condtion">检验实例</div>
					<div  class="Control_Frame">
						<select id="tt" class="Select_Menu_SelectBox" style="width:250px;_width:240px;">
							<s:iterator value="serviceResult.get(0)" id="sr">
								<option value="ReportCheck-<s:property value="%{#sr.key}" />"><s:property value="%{#sr.value}" /></option>
							</s:iterator>
						</select>
					</div>
				</div>
			</div>
		</form>
	</body>
		
</html>
