$(document).ready(function () {
    //$("#UserHead").css("opacity","0.8");
    //$("#UserName").css("opacity","0.8");
    //$("#PassWord").css("opacity","0.8");
    $(".LoginFrame_Div").css("opacity", "0.7");
    $("#BankLogo").css("opacity", "0.8");
    $(".Des_Div").css("opacity", "0.5");
    //$("#backgroundImage").css("opacity", "0.2");
    var top = ($(window).height() - $(".LoginFrame_Div").height()) / 2 - 50;
    var left = ($(window).width() - $(".LoginFrame_Div").width()) / 2;
    $(".LoginFrame_Div").css({
        "top" : top,
        "left" : left
    });
    
    //$("#UserName").val("请输入用户名");
    //$("#PassWord").attr("type","text");
    //$("#UserName").css("color","#C0C0C0");
    $("#UserHead").error(function(){
        $("#UserHead").attr("src","images/index/UserHead.png");
    });
    $("#UserName").blur(function(){
        $("#UserHead").attr("src","images/userhead/"+$("#UserName").val()+".png");
    });
    $("#UserName").focus(function(){
        $("#UserHead").attr("src","images/userhead/"+$("#UserName").val()+".png");
    });
    $("#UserName").keyup(function(){
        $("#UserHead").attr("src","images/userhead/"+$("#UserName").val()+".png");
    });
});
