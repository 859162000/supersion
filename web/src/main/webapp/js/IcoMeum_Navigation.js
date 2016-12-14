
function ObjectDeskMeum_Navigation(strFatherID,strMeumNodeName,LayoutType,strFatherMeum,strMeumName,Bool_Desk_N)
{
    this.strFatherID = strFatherID;
    this.strMeumNodeName = strMeumNodeName;
    
    if(strFatherMeum == "" || strFatherMeum == null)
        this.strFatherMeum = "DESKTOP";
    else
        this.strFatherMeum = strFatherMeum;
        
    if(strMeumName == "" || strMeumName == null)
        this.strMeumName = "桌面";
    else
        this.strMeumName = strMeumName;
        
    if(LayoutType == "" || LayoutType == null)
        this.LayoutType = "Left";
    else
        this.LayoutType = LayoutType;

    $("#" +strFatherID).children().remove();
    //创建导航图标
    this.CreateNavigationMeum();
    
    return this;
}
/**
  * 创建导航图标
  */
ObjectDeskMeum_Navigation.prototype.CreateNavigationMeum = function()
{
 
    var Tmp_Object = this;
    var strMeumNodeName_tmp = this.strMeumNodeName;
    
    CreateNavigation(Tmp_Object,strMeumNodeName_tmp);
    
	//绑定导航页面图标事件
    Tmp_Object.BindNavigationEvent();
    //设置菜单布局
    Tmp_Object.SetDeskMeumLayout();

    //创建导航条菜单
    if(this.strMeumNodeName.toUpperCase() != "DESK_ICO"){
    	ObjectDeskMeum_Navigation.InsertNavigationEvent(this.strMeumNodeName,this.strMeumName,this.strFatherMeum,this.strFatherID);
    }
    $("#DeskMeum").hide();
}
/**
  * 设置菜单的布局
  */
ObjectDeskMeum_Navigation.prototype.SetDeskMeumLayout = function(strLayoutType)
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
ObjectDeskMeum_Navigation.prototype.DeskMeumLeftLayout = function()
{
    
    //设置控件的位置属性
    $("#" + this.strFatherID).children(".Desk_Ico_Div").css({"position":"absolute","float":""});
    
    //获取级容器的宽度和高度，以及元素数量
    var FatherWidth = $("#"+this.strFatherID).width();
    var FatherHeight = $("#"+this.strFatherID).height() - 100;
    var IcoNums = $("#" + this.strFatherID).children(".Desk_Ico_Div").length;
    
    
    if(IcoNums <= 0)
        return;
        
    //正常一个ICO元素的高度和宽度为90 90
    var index_div = 0;
    var Left = 0;
    var Top = 0;
    $("#" + this.strFatherID).children(".Desk_Ico_Div").each(function(){
        if((index_div * 90) > FatherHeight)
        {
            Left += 90;
            Top = 0;
            index_div = 0;
        }
        $(this).css({"top": Top,"left":Left})
        index_div ++;
        Top += 90;
    });
}
/**
  * 菜单的自动布局(靠上)
  */
ObjectDeskMeum_Navigation.prototype.DeskMeumTopLayout = function()
{
    $("#" + this.strFatherID).children(".Desk_Ico_Div").css({"position":"relative","float":"left","left":0,"top":0});
}
/**
  * 菜单的自动布局(靠右)
  */
ObjectDeskMeum_Navigation.prototype.DeskMeumRightLayout = function()
{
    //设置控件的位置属性
    $("#" + this.strFatherID).children(".Desk_Ico_Div").css({"position":"absolute","float":""});
    
    //获取级容器的宽度和高度，以及元素数量
    var FatherWidth = $("#"+this.strFatherID).width();
    var FatherHeight = $("#"+this.strFatherID).height() - 100;
    var IcoNums = $("#" + this.strFatherID).children(".Desk_Ico_Div").length;
    
    if(IcoNums <= 0)
        return;
        
    //正常一个ICO元素的高度和宽度为90 90
    var index_div = 0;
    var Left = FatherWidth - 90;
    var Top = 0;
    $("#" + this.strFatherID).children(".Desk_Ico_Div").each(function(){
        if((index_div * 90) > FatherHeight)
        {
            Left -= 90;
            Top = 0;
            index_div = 0;
        }
        $(this).css({"top": Top,"left":Left})
        index_div ++;
        Top += 90;
    });
}
/**
  * 创建一个导航页面菜单
  */
ObjectDeskMeum_Navigation.prototype.CreateNewNavigationMeum = function(strICOID,strIcoName,FunctionType,strIcoImgName,Ico_Src,ico_des)
{
    $("#" + strICOID).remove();
    var strIcoDes = "功能菜单项";
    if(FunctionType.toUpperCase() == "PATH")
        strIcoDes = "导航菜单项";
    var strInsertHtml = "";
    strInsertHtml+= "<div id='"+strICOID+"' class='Ico_Css Ico_Css:hover' OneRow='"+strIcoName+"' TwoRow='"+FunctionType+"' ThreeRow='"+Ico_Src+"' FatherMeum='"+this.strMeumNodeName+"'>";
    strInsertHtml+= "<img src='../images/icomeum/"+strIcoImgName+"'/>";
    strInsertHtml+= "<div style='margin-top:10px;font-size:12px;font-family:微软雅黑;' title ='"+strIcoName+"'>"+strIcoName+"</div>";
    strInsertHtml+= "<div title ='"+strIcoDes+"' style='font-size:12px;font-family:微软雅黑;'>"+strIcoDes+"</div>";
    strInsertHtml+= "<div title ='"+ico_des+"' style='font-size:12px;font-family:微软雅黑;' >"+ico_des+"</div>";
    strInsertHtml+= "</div>";
    $("#"+this.strFatherID).append(strInsertHtml);
}
/**
  * 绑定导航图标事件
  */
ObjectDeskMeum_Navigation.prototype.BindNavigationEvent = function()
{
    var strFatherID_tmp = this.strFatherID;
    $("#" + this.strFatherID).children(".Ico_Css").click(function () {
    	
        var strId = $(this).attr("id");
        var strName = $(this).attr("OneRow");
        var strUrl = $(this).attr("ThreeRow");
        var Function_Type = $(this).attr("TwoRow");
        var strFatherMeum = $(this).attr("FatherMeum");
        if(Function_Type == "path")
        {
            //ObjectDeskMeum.InsertNavigationEvent(strId,strName,strFatherMeum);
            new ObjectDeskMeum_Navigation(strFatherID_tmp,strId,"Left",strId,strName);
        } 
        else{
        	var address = window.location.pathname;
        	var one=address.split("/");
        	var two=window.location.protocol+"//"+window.location.host+"/"+one[1]+"/";
            
        	if(strUrl.indexOf(".jsp")>-1)
        		strUrl = two + strUrl;
        	else
        		strUrl = two + strUrl + ".action";
            
            var heigth = getWindowsHeight(strUrl);
        	var width = getWindowsWidth(strUrl);
        	strUrl += "?windowId=" +　strId;
            new window.parent.ObjectWindows("Main_Body", strId + "Windows", heigth, width, strUrl, strName, "no");
        }
    });
}

/**
  * 添加导航条菜单
  */
ObjectDeskMeum_Navigation.InsertNavigationEvent = function(strMeumID,strMeumName,strFatherMeum,MeumDivFrameID)
{       
	    //$("#Top_Bar").children("[FatherMeum='"+ strFatherMeum +"']").remove();
	    //$("#Top_Bar").children("#" + strMeumID + "_Meum").remove();
	    $("#Top_Bar").children("[Div_Type='Navigation']:gt(0)").remove();
	    
	    var navigationNameHtml = getNavigationNameHtml(strMeumID);
	    var navigationNameHtmls = navigationNameHtml.split(",");

	    for(var i=0;i<navigationNameHtmls.length;i++){
	    	
	    	var strId = navigationNameHtmls[i].split("|")[0];
	    	var strName = navigationNameHtmls[i].split("|")[0];
	    	
	    	 var strInsertHtml = "";
	         strInsertHtml += "<div id='"+i+"_Meum' strId = '"+strId+"' strName = '"+strName+"' FatherMeum='"+strName+"' Div_Type='Navigation' class='NavigationClick'>";
	         strInsertHtml += "<img src='../images/navigationbar/MD-play.png'/>";
	         strInsertHtml += "<div>"+navigationNameHtmls[i].split("|")[1]+"</div>";
	         strInsertHtml += "</div>";
	         $("#Top_Bar").append(strInsertHtml);
	    }  
	    $("#" + 0 +"_Meum").click(function(){
	           new ObjectDeskMeum_Navigation("Grid_Task",navigationNameHtmls[0].split("|")[0],"Left",navigationNameHtmls[0].split("|")[0],navigationNameHtmls[0].split("|")[1]);
	    });
	    $("#" + 1 +"_Meum").click(function(){
	           new ObjectDeskMeum_Navigation("Grid_Task",navigationNameHtmls[1].split("|")[0],"Left",navigationNameHtmls[1].split("|")[0],navigationNameHtmls[1].split("|")[1]);
	    });
	    $("#" + 2 +"_Meum").click(function(){
	           new ObjectDeskMeum_Navigation("Grid_Task",navigationNameHtmls[2].split("|")[0],"Left",navigationNameHtmls[2].split("|")[0],navigationNameHtmls[2].split("|")[1]);
	    });
	    $("#" + 3 +"_Meum").click(function(){
	           new ObjectDeskMeum_Navigation("Grid_Task",navigationNameHtmls[3].split("|")[0],"Left",navigationNameHtmls[3].split("|")[0],navigationNameHtmls[3].split("|")[1]);
	    });
	    $("#" + 4 +"_Meum").click(function(){
	           new ObjectDeskMeum_Navigation("Grid_Task",navigationNameHtmls[4].split("|")[0],"Left",navigationNameHtmls[4].split("|")[0],navigationNameHtmls[4].split("|")[1]);
	    });
	    $("#" + 5 +"_Meum").click(function(){
	           new ObjectDeskMeum_Navigation("Grid_Task",navigationNameHtmls[5].split("|")[0],"Left",navigationNameHtmls[5].split("|")[0],navigationNameHtmls[5].split("|")[1]);
	    });
	    $("#" + 6 +"_Meum").click(function(){
	           new ObjectDeskMeum_Navigation("Grid_Task",navigationNameHtmls[6].split("|")[0],"Left",navigationNameHtmls[6].split("|")[0],navigationNameHtmls[6].split("|")[1]);
	    });
	    $("#" + 7 +"_Meum").click(function(){
	           new ObjectDeskMeum_Navigation("Grid_Task",navigationNameHtmls[7].split("|")[0],"Left",navigationNameHtmls[7].split("|")[0],navigationNameHtmls[7].split("|")[1]);
	    });
}