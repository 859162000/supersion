<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="framework.helper.HttpUtils"%>
<%@page import="java.util.*"%>
<%@page import="framework.show.ShowNavigationComponent" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base href="<%=basePath%>"></base>
    <title>统一监管报送平台 V3.0</title>
    <link href="css/DeskTopCss.css" rel="stylesheet" type="text/css" />
    <link href="css/Comm.css" rel="stylesheet" type="text/css" />
    <link href="css/WindowsTaskBarCss.css" rel="stylesheet" type="text/css" />
    <link href="css/ReportQuickBarCss.css" rel="stylesheet" type="text/css" />
    <link href="css/WindowsCss.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath %>js/Comml.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/Windows.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/Shortcutkeys.js" type="text/javascript" ></script>
    <script src="<%=basePath %>js/ReportQuickBar.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/IcoMeum_DeskTop.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/jquery/jquery.roundabout.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/jquery/jquery.roundabout-shapes.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/WindowsDesk.js" type="text/javascript"></script>
    <style>
    	.Ico_Css:hover
		{
			background-color: #F7F7F7;
			box-shadow: 0 0px 5px Black;
			border-radius: 2px;
		}
    </style>
    <script type="text/javascript">
        function stop()
        { 
            return false; 
        }
        $(document).bind("selectstart",function(){return false;});
        $(document).bind("contextmenu",function(){return false;});
        function SetWindowzIndex(ClickWindowID)
        {
            ObjectWindows.WindowsToTop(ClickWindowID);
        }
        function RemoveWindows(strWindwosID)
        {
            $("#"+strWindwosID).remove();
            $("#"+strWindwosID +"_TaskBar").remove();
            ObjectWindows.WindowsCount--;
        }
        function AddNewReportQuickBar(strReportId,strReportName)
        {
            new CreateReportQuickBar(strReportId,strReportName);
        }
        function AutiSize()
        {
            DeskMeum.SetDeskMeumLayout();
        }

        function CreateDeskIco(Tmp_Object)
        {
	         <%
				  List<ShowNavigationComponent> list1=HttpUtils.GetShowNavigationComponent();
				  for(ShowNavigationComponent showNavigationComponent:list1){
				  	if(showNavigationComponent.getParentId().equals("DeskTop")){				  	
				  	%>
				  	Tmp_Object.CreateNewDeskMeum('<%=showNavigationComponent.getId()%>','<%=showNavigationComponent.getName()%>',''
				  	,'<%=showNavigationComponent.getIco()%>','<%=showNavigationComponent.getUrl()%>');
				  	<%}
				  }
			 %>
        }
        function getWindowsHeight(strUrl){
            if(strUrl.indexOf("coresystem.dto.ActionExcute") > -1){
                return <s:property value="@framework.helper.ReflectOperation@getShowInstance('coresystem.dto.ActionExcute','1').getWindowHeight()" />;
            }
            else{
                return $(window).height()-100;
            }
    	}
    	var msgFlashId=0;
    	$(document).ready(function(){
    		hasUserNeedDoingThing();
		 	setInterval("hasUserNeedDoingThing()",300000);
		});
		function hasUserNeedDoingThing()
		{
			var url="<%=basePath %>/coresystem/HasUserNeedDoThing.action";
			$.ajax({
				type:"GET",
	            url:url,
	            data:"rid="+new Date(),
	            datatype: "json",
	            success:function(data){
					if(data==true)
					{
						if(msgFlashId==0)
						{
							msgFlashId=setInterval("msgAlert()",500);
						}
						
					}
	            }
			});
		}
		function openDoingTask()
		{
		    if(msgFlashId!=0)
		    {
		    	clearInterval(msgFlashId);
		    	msgFlashId=0;
		    	$("#doingtask").hide();
		    	var url="<%=basePath %>/coresystem/ShowUserNeedDoThing-coresystem.dto.ShowUserNeedDoThing.action";
		    	var windowId="ShowUserNeedDoThing";
		    	var windowName="待办事项";
		    	new window.parent.ObjectWindows("Main_Body", windowId, 520, 1000, url, windowName, "yes");
		    }
			
		}
		function msgAlert()
		{
			$("#doingtask").toggle();
			
		}
    	function getWindowsWidth(strUrl){
    		if(strUrl.indexOf("coresystem.dto.ActionExcute") > -1){
                return <s:property value="@framework.helper.ReflectOperation@getShowInstance('coresystem.dto.ActionExcute','1').getWindowWidth()" />;
            }
            else{
                return 1000;
            }
    	}
		
    	function logoutOrExit(obj){
	        var strType = $(obj).attr("type_It");
	        if(strType == "logout"){
				var one=window.location.pathname.split("/");
	         	window.location.href = window.location.protocol+"//"+window.location.host+"/"+one[1]+"/coresystem/Logout-coresystem.dto.UserInfo.action";
	        }else if(strType == "exit"){
	         	self.opener="2131232131";
	         	self.close();
	        }
    	}
    	
    </script>
</head>
<body id="Main_Body" scroll="no" onResize="AutiSize();" ondragstart="return false;">
    <div id="backgroundImage"> 
        <img id='dva1' src="images/wall_page/bg.jpg" /> 
    </div>
    <div id="TaskBarLine_Frame"></div>
    <div id="TaskBarLine">
        <img id="TaskBarRight" src="images/taskbar/TaskBarRight.png" style="height:30px; float:right;"  WindowsStaut='Show'/>
        
    	<div title="注销" class="TaskBar" style="float: right;" type_It="logout" onclick="javascript:logoutOrExit(this);">注销</div>
        <div title="退出平台" class="TaskBar" style="float: right;" type_It="exit" onclick="javascript:logoutOrExit(this);">退出平台</div>
        <div id="doingtask"  class="MsgBtn" style="float: right;display:none;" type_It="logout" onclick="javascript:openDoingTask();">新任务</div>
	</div>
	
</body>
</html>