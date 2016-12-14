function ObjectDeskMeum_DeskTop(strFatherID,strMeumNodeName,LayoutType)
{
    this.strFatherID = strFatherID;
    this.strMeumNodeName = strMeumNodeName;
        
    if(LayoutType == "" || LayoutType == null)
        this.LayoutType = "Left";
    else
        this.LayoutType = LayoutType;
        
    //创建菜单
    this.CreateDeskMeum();
    
    return this;
}
/**
  * 创建桌面图标
  */
ObjectDeskMeum_DeskTop.prototype.CreateDeskMeum = function()
{
    var Tmp_Object = this;
    
    CreateDeskIco(this);
  	//设置菜单布局
  	Tmp_Object.BindEvent();
  	//绑定事件
  	Tmp_Object.SetDeskMeumLayout();
}
/**
  * 设置菜单的布局
  */
ObjectDeskMeum_DeskTop.prototype.SetDeskMeumLayout = function(strLayoutType)
{
    if(strLayoutType != "" && strLayoutType != null)
        this.LayoutType = strLayoutType;
        
    switch (this.LayoutType) {
    case "Left":
        this.DeskMeumLeftLayout();
        break;
    case "Right":
        this.DeskMeumRightLayout();
        break;
    case "Top":
        this.DeskMeumTopLayout();
        break;
    default:
        this.DeskMeumLeftLayout();
    }
}
/**
  * 设置菜单的布局
  */
ObjectDeskMeum_DeskTop.prototype.SetDeskMeumLayout = function(strLayoutType)
{
    if(strLayoutType != "" && strLayoutType != null)
        this.LayoutType = strLayoutType;
        
    switch (this.LayoutType) {
    case "Left":
        this.DeskMeumLeftLayout();
        break;
    case "Right":
        this.DeskMeumRightLayout();
        break;
    case "Top":
        this.DeskMeumTopLayout();
        break;
    default:
        this.DeskMeumLeftLayout();
    }
}
/**
  * 菜单的自动布局(靠左)
  */
ObjectDeskMeum_DeskTop.prototype.DeskMeumLeftLayout = function()
{

    //设置控件的位置属性
    $("#" + this.strFatherID).children(".Desk_Ico_Div").css({"position":"absolute"});
    $("#" + this.strFatherID).children(".Desk_Ico_Div").css({
        "position":"absolute",
        "float":"",
        "margin-left":0,
        "margin-top":0,
        "margin-bottom":0
        });
    
    //获取级容器的宽度和高度，以及元素数量
    var FatherWidth = $("#"+this.strFatherID).width();
    var FatherHeight = $("#"+this.strFatherID).height() - 50;
    var IcoNums = $("#" + this.strFatherID).children(".Desk_Ico_Div").length;
    
    
    if(IcoNums <= 0)
        return;
        
    //正常一个ICO元素的高度和宽度为80 80,框架110 110
    var index_div = 1;
    var Left = 0;
    var Top = 0;
    $("#" + this.strFatherID).children(".Desk_Ico_Div").each(function(){
        if((index_div * 110) > FatherHeight)
        {
            Left += 110;
            Top = 0;
            index_div = 1;
        }
        $(this).css({"top": Top + 30,"left":Left + 30});
        index_div ++;
        Top += 110;
    });
}
/**
  * 菜单的自动布局(靠上)
  */
ObjectDeskMeum_DeskTop.prototype.DeskMeumTopLayout = function()
{
    
    $("#" + this.strFatherID).children(".Desk_Ico_Div").css({
        "position":"relative",
        "float":"left",
        "left":0,
        "top":0,
        "margin-left":30,
        "margin-top":20,
        "margin-bottom":10
       });
}
/**
  * 菜单的自动布局(靠右)
  */
ObjectDeskMeum_DeskTop.prototype.DeskMeumRightLayout = function()
{
    //设置控件的位置属性
    $("#" + this.strFatherID).children(".Desk_Ico_Div").css({"position":"absolute"});
    $("#" + this.strFatherID).children(".Desk_Ico_Div").css({
        "position":"absolute",
        "float":"",
        "margin-top":0,
        "margin-left":0,
        "margin-bottom":0
    });
    
    //获取级容器的宽度和高度，以及元素数量
    var FatherWidth = $("#"+this.strFatherID).width();
    var FatherHeight = $("#"+this.strFatherID).height() - 50;
    var IcoNums = $("#" + this.strFatherID).children(".Desk_Ico_Div").length;
    
    if(IcoNums <= 0)
        return;
        
    //正常一个ICO元素的高度和宽度为80 80 ,框架110 110
    var index_div = 1;
    var Left = FatherWidth - 110;
    var Top = 0;
    $("#" + this.strFatherID).children(".Desk_Ico_Div").each(function(){
        if((index_div * 110) > FatherHeight)
        {
            Left -= 110;
            Top = 1;
            index_div = 1;
        }
        $(this).css({"top": Top + 30,"left":Left - 30})
        index_div ++;
        Top += 110;
    });
}
/**
  * 创建一个桌面菜单prototype
  */
ObjectDeskMeum_DeskTop.prototype.CreateNewDeskMeum = function(strICOID,strIcoName,FunctionType,strIcoImgName,Ico_Src)
{
	var address = window.location.pathname;
	var one=address.split("/");
	var two=window.location.protocol+"//"+window.location.host+"/"+one[1]+"/";
    $("#" + strICOID).remove();
    var strInsertHtml = "";
    strInsertHtml+= "<div id='"+strICOID+"' class='Desk_Ico_Div' onselectstart='return false;' Function_Type='"+FunctionType+"' IMGLink='"+two+Ico_Src+"' IMGName='"+strIcoName+"' MeumName='"+strIcoName+"'>";
    strInsertHtml+= "    <!--div class='Desk_Ico_Div_Select'></div-->";
    strInsertHtml+= "    <img src='"+two+"images/icomeum/"+strIcoImgName+"' style='width:60px;' />";
    strInsertHtml+= "    <div style='font-size: 12px;'>"+strIcoName+"</div>";
    strInsertHtml+= "</div>";
    //$("#"+this.strFatherID).append(strInsertHtml);
    $("#"+this.strFatherID).append(strInsertHtml);
}
/**
  * 绑定桌面图标事件
  */
ObjectDeskMeum_DeskTop.prototype.BindEvent = function()
{
    /*$("#" + this.strFatherID).children(".Desk_Ico_Div").mouseover(function () {
        $(this).children("div").css("color", "Black");
        $(this).css("background-color", "#0066ff");
    });
    $("#" + this.strFatherID).children(".Desk_Ico_Div").mouseout(function () {
        $(this).children("div").css("color", "White");
        $(this).css("background-color", "");
    });*/

    $(".Desk_Ico_Div").click(function () {
    	
        var strId = $(this).attr("id");
        var strName = $(this).attr("IMGName");
        var strUrl = $(this).attr("IMGLink");

        var heigth = getWindowsHeight(strUrl);
    	var width = getWindowsWidth(strUrl);
        
        if(strUrl.indexOf("NavigationPage.jsp") > -1){
        	//ObjectWindows.CloseWindows("NavigationWindows");
        	new ObjectWindows("Main_Body", "NavigationWindows", heigth, width,strUrl + "?OpenMeum=" + strId +"&MeumName=" + strName, "导航窗口", "no");
        }
        else{
        	if(strUrl.indexOf(".jsp")>-1)
        		strUrl += "?windowId=" +　strId;
        	else
        		strUrl += ".action"+"?windowId=" +　strId;

        	 new ObjectWindows("Main_Body", strId + "Windows", heigth, width, strUrl, strName, "no");
        }
          
    });
}