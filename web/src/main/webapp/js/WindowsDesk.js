var DeskMeum = null;
$(document).ready(function () {
    
    DeskMeum = new ObjectDeskMeum_DeskTop("Main_Body", "desk_ico", "Left");
    $("#TaskBarLine").mousedown(function (event) {
        event.stopPropagation();
    });
    
    $("#TaskBarLine_Frame").css("opacity", "0.6");
    
    $("#Main_Body").click(function () {
        $(".RIGRH_MEUM").remove();
    });
    
    var address = window.location.pathname;
	var one=address.split("/");
	var two=window.location.protocol+"//"+window.location.host+"/"+one[1]+"/";
    
    $("#Main_Body").mousedown(function (event) {
    	
        if (event.which == 3) {
            $("#RightMeum").remove();
            var InsertHTML = "";
            InsertHTML += "  <div id='RightMeum' class='RIGRH_MEUM' onselectstart='javascript:return false;'>";
            InsertHTML += "  <div type_It='Ico_Left'><img src='"+two+"images/rightmeum/desk/left.png' /><a>图标靠左排列</a></div>";
            InsertHTML += "  <div type_It='Ico_Right'><img src='"+two+"images/rightmeum/desk/right.png' /><a>图标靠右排列</a></div>";
            InsertHTML += "  <div type_It='Ico_Top'><img src='"+two+"images/rightmeum/desk/up.png' /><a>图标靠上排列</a></div>";
            InsertHTML += "  <div type_It='Refresh'><img src='"+two+"images/rightmeum/usermanagement/pwd.png' /><a>刷新桌面</a></div>";
            InsertHTML += "  <div type_It='logout'><img src='"+two+"images/rightmeum/refresh.png' /><a>注销</a></div>";
            InsertHTML += "  <div type_It='exit'><img src='"+two+"images/rightmeum/refresh.png' /><a>退出平台</a></div>";
            /*InsertHTML += "  <div type_It='@@@@@@'><img src='images/rightmeum/usermanagement/px.png' /><a>@!#$%%^&*&*</a></div>";*/
            InsertHTML += "<br style='clear:both;'/>";
            InsertHTML += "  </div>";
            $("body").append(InsertHTML);
            var User_Choose_ID = this.id;
            $(".SHOW_INFO_CSS").css("color", "Black");
            //$("#RightMeum").css("height",0);
            $("#RightMeum").show();
            //$("#RightMeum").animate({height:(20+5)*6+5},"fast");
            //alert(event.pageY);
            $("#RightMeum").css("top", event.pageY);
            $("#RightMeum").css("left", event.pageX);
            $("#RightMeum").css("z-index", 1000);
            
            $("[type_It]").unbind();
            $("[type_It]").click(function () {
                var strType = $(this).attr("type_It");
                switch (strType) {
                    //float:left;
                    //margin-left:auto;margin-right:2px
                case "Ico_Left":
                    DeskMeum.SetDeskMeumLayout("Left");
                    break;
                case "Ico_Right":
                    DeskMeum.SetDeskMeumLayout("Right");
                    break;
                case "Ico_Top":
                    DeskMeum.SetDeskMeumLayout("Top");
                    Up_Sort = 'id';
                    break;
                case "logout":
                	window.location.href=window.location.protocol+"//"+window.location.host+"/"+one[1]+"/coresystem/Logout-coresystem.dto.UserInfo.action";
                    //ReplaceDeskBackground();
                    break;
                case "exit":
                	self.opener="2131232131";
                	self.close();
                	break;
                case "Refresh":
                    location.reload();
                    break;
                default:
                    alert($(this).attr("type_It") + "  " + User_Choose_ID);
                }
                $("#RightMeum").remove();
            });
        }
    });
    
    
    $("#TaskBarRight").mouseover(function(){
        $(this).attr("src",two+"images/taskbar/TaskBarRightOver.png");
    });
    $("#TaskBarRight").mouseleave(function(){
        $(this).attr("src",two+"images/taskbar/TaskBarRight.png");
    });
    $("#TaskBarRight").click(function(){
        var strWindowsStaut = $(this).attr("WindowsStaut");
        if(strWindowsStaut =="Show")
        {
            $("[WindowsType='Windows']").each(function(){
                ObjectWindows.WindowsHidden($(this).attr("id"),true);
            });
            $(this).attr("WindowsStaut","Hidden");
        }
        else
        {
            $("[WindowsType='Windows']").each(function(){
                
                ObjectWindows.WindowsShow($(this).attr("id"),true);
            });
            $(this).attr("WindowsStaut","Show");  
        }
    });
});
