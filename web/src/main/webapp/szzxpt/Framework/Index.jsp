<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base href="<%=basePath%>"></base>
    <title>深圳银行征信管理平台</title>
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
            //$("[WindowsType='Windows']").css("zIndex","999");
            //$("#" + ClickWindowID).css("zIndex","1000");
            ObjectWindows.WindowsToTop(ClickWindowID);
        }
        function RemoveWindows(strWindwosID)
        {
            //$("[WindowsType='Windows']").css("zIndex","999");
            //$("#" + ClickWindowID).css("zIndex","1000");
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
	         <s:iterator value="#session.showNavigation_ShowNavigationComponent" id="vt">
	     	    if('<s:property value="parentId" />' == 'DeskTop'){
	     	    	Tmp_Object.CreateNewDeskMeum('<s:property value="id" />','<s:property value="name" />','','<s:property value="ico" />','<s:property value="url" />');
	     	    }
	         </s:iterator>
        }
        function getWindowsHeight(strUrl){
            if(strUrl.indexOf("coresystem.dto.ActionExcute") > -1){
                return <s:property value="@framework.helper.ReflectOperation@getShowInstance('coresystem.dto.ActionExcute','1').getWindowHeight()" />;
            }
            else{
                return $(window).height()-100;
            }
    	}

    	function getWindowsWidth(strUrl){
    		if(strUrl.indexOf("coresystem.dto.ActionExcute") > -1){
                return <s:property value="@framework.helper.ReflectOperation@getShowInstance('coresystem.dto.ActionExcute','1').getWindowWidth()" />;
            }
            else{
                return 1000;
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
    </div>
</body>
</html>