<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base href="<%=basePath%>"></base>
	<title>沪农商行村镇银行征信管理平台</title>
    <link href="css/Comm.css" rel="stylesheet" type="text/css" />
    <link href="css/IndexCss.css" rel="stylesheet" type="text/css" />
	<link href="css/NewLogin.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath %>js/Shortcutkeys.js" type="text/javascript" ></script>
    <script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/jquery.cookie.js" type="text/javascript"></script>
    
	<script type="text/javascript">
		$(document).ready(function(){
			if(!(document.cookie || navigator.cookieEnabled))
			{
				alert("请将cookie设置为打开，否则'记住密码'功能无法使用。");
			}
			
			$("#Rem_UserName, #Rem_PassWord").click(function(){
				if($("#Rem_UserName").is(":checked")){
			        $.cookie("UserName", $("#UserName").val(), { expires: 7 });
				}else{
					$.cookie("UserName", '', { expires: -1 });
				}
				if($("#Rem_PassWord").is(":checked")){
			        $.cookie("PassWord", $("#PassWord").val(), { expires: 7 });
				}else{
					$.cookie("PassWord", '', { expires: -1 });
				}
				$.cookie("Rem_UserName", $("#Rem_UserName").is(":checked"), { expires: 7 });
				$.cookie("Rem_PassWord", $("#Rem_PassWord").is(":checked"), { expires: 7 });
			})
			
			$("#UserName,#PassWord").blur(function(){
				if($.cookie("Rem_UserName") == "true") {
					$.cookie("UserName", $("#UserName").val(), { expires: 7 });
				}
				if($.cookie("Rem_UserName") == "true") {
					$.cookie("PassWord", $("#PassWord").val(), { expires: 7 });
				}
			})
			
			if($.cookie("Rem_UserName") == "true") {
				$("#UserName").val($.cookie("UserName"));
				$("#Rem_UserName").attr("checked", true);
			}
			if($.cookie("Rem_PassWord") == "true") {
		        $("#PassWord").val($.cookie("PassWord"));
				$("#Rem_PassWord").attr("checked", true);
			}
			
			$("#sut").click(function(){
				if($("#UserName").val()!="" && $("#PassWord").val()!=""){
				  	$("#form").submit();
				}else{
					IsTextNull('登录');
				}
			});
			
			function IsTextNull(nameOne){
				if($("#UserName").val()=="" && $("#PassWord").val()!=""){
					alert(nameOne+"失败！\n请输入用户名");
				}else if($("#UserName").val()!="" && $("#PassWord").val()==""){
					alert(nameOne+"失败！\n请输入密码");
				}else if($("#UserName").val()=="" && $("#PassWord").val()==""){
					alert(nameOne+"失败！\n请输入密码,\n请输入用户名");
				}
			}
		});
	
		$(document).keypress(function(e){
	         if(e.which==13 && ($("#UserName").val()!="" && $("#PassWord").val()!="")){
		         $("#form").submit();
		     }
	         if(e.which == 27)return false;
		});

        function AutiSize() {
            var Login_Main_Frame_MarginTop = ($(window).height() - 30 - 70 - 450) / 2 - 40;
            var LoginMain_Left = ($(window).width()-$(".LoginMain").width())/2;

            if (Login_Main_Frame_MarginTop <= 0)
                Login_Main_Frame_MarginTop = 0;
 
            $("#Login_Main_Frame").css("marginTop", Login_Main_Frame_MarginTop);
            $(".LoginMain").css("left",LoginMain_Left);
        }
	</script>    

</head>
<body scroll="no" onload ="AutiSize();" onResize="AutiSize();" ondragstart="return false;">
	<s:form id="form" action="Login-coresystem.dto.UserInfo" namespace="/coresystem"  autocomplete ="off">
 <div id="backgroundImage"> 
        <img id='dva1' src="images/login/gde.jpg" /> 
    </div>
    <div class="Login_Head_Frame">
        <div class="Login_Head_Frame_Cell">
            <div class="Login_Head_Frame_Cell_Cell_Left">欢迎您登录统一监管报送平台!</div>
            <div class="Login_Head_Frame_Cell_Cell_Left"></div>
            <div class="Login_Head_Frame_Cell_Cell_Right">系统帮助</div>
            <div class="Login_Head_Frame_Cell_Split"></div>
            <div class="Login_Head_Frame_Cell_Cell_Right">问题提交</div>
        </div>
    </div>
    <div id="Login_Main_Frame" >
        <div class="Login_Title">
            <div class="Com_Logo"></div>
            <div class="Comp_Name">北京时代正邦科技有限公司</div>
            <div class="ProductsName">统一监管报送平台</div>
        </div>
        <div class="LoginMain LoginMain_BG"></div>
        <div class="LoginMain">
            <div class="Login_Images_Frame">
<!--                 <img src="images/login/gg.png"/> -->
            </div>
            <div class="Login_Div_BG"></div>
            <div class="Login_Div" >
                <div  class="Login_Div_Title">账号登录</div>
                <div  class="Login_Div_LoginName">用户名</div>
                <div class="Login_Div_LoginName_PassWord_Frame">
					<input id="UserName" value="" type="text" name="serviceParam.strUserCode" class="TextBox_NotBorder_Login Login_Div_LoginName_Txt" style=" background-image:url('images/login/user-icon.png');" />
                </div>
                <div  class="Login_Div_LoginPassWord">密码</div>
                <div class="Login_Div_LoginName_PassWord_Frame">
					<input id="PassWord" value="" type="password" name="serviceParam.strPassword" class="TextBox_NotBorder_Login Login_Div_LoginPassWord_Txt" style="background-image:url('images/login/pass-icon.png');" />
                </div>
                <div class="Login_Div_Shortcut">
                    <input class="Login_Div_Shortcut_CheckBox_UserName" id="Rem_UserName" type="checkbox"/> 
                    <div class="Login_Div_Shortcut_CheckBox_UserName_Name">记住账号</div>
                    <input class="Login_Div_Shortcut_CheckBox_PassWord" id="Rem_PassWord" type="checkbox"/> 
                    <div class="Login_Div_Shortcut_CheckBox_PassWord_Name">记住密码</div>
<!--                     <div class="Login_Div_Shortcut_Forget">忘记密码？</div> -->
                </div>
                <div class="Login_Div_LoginButton_Frame">
					<div id="sut" class="Login_Div_LoginButton" >登&nbsp;&nbsp;录</div>
                </div>
            </div>
        </div>
    </div>
    <div class="Login_Bottom_Frame">
        <div class="Login_Bottom_Frame_First_Line">
            <div class="Login_Bottom_Frame_First_Line_Company">北京时代正邦科技有限公司</div>
            <div class="Login_Bottom_Frame_First_Line_Cell_Right">联系我们</div>
            <div class="Login_Bottom_Frame_First_Line_Split"></div>
            <div class="Login_Bottom_Frame_First_Line_Cell_Right">微信关注</div>
            <div class="Login_Bottom_Frame_First_Line_Split"></div>
            <div class="Login_Bottom_Frame_First_Line_Cell_Right">官方网站</div>
            <img class="Login_Bottom_Frame_First_Line_ERWEI" src="images/login/api_tmp.png" />
        </div>
        <div class="Login_Bottom_Frame_Second_Line">
            <div class="Login_Bottom_Frame_Second_Line_Add">公司地址:北京市海淀区上地信息路1号上地国际创业园1号楼16层</div>
            <div class="Login_Bottom_Frame_Second_Line_Tel">联系电话:010-62119336</div>
        </div>
       <div class="Login_Bottom_Frame_Third_Line">
            <div class="Login_Bottom_Frame_Third_Line_Copyright">Copyright &copy; 2013-2014 Transino.com  their respective owners. E-mail:Service@Transino.Net</div>
        </div>
    </div>
    </s:form>
</body>
</html>


