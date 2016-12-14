/**
 * Transino Windows - V1.0
 * Date:2014-05-16 Author:Majun
 */
/**
 *声明ObjectWindows静态变量
 */
 //存储目前窗体个数
 ObjectWindows.WindowsCount = 0;
 //存储目前窗体ID的堆栈
 ObjectWindows.WindowsArray = new Array();
 //定义一个时钟
 ObjectWindows.Windowstimer = null;
/**
 *ObjectWindows主函数
 * 参数描述
 * strFatherID:窗体父级控件id
 * strWindwosID:窗体id,需要是唯一的值
 * Height:窗体高度
 * Width:窗体宽度
 * strUrl:窗体内打开的网页地址 
 * strWindowTitle:窗体的标题
 * IframeScrolling:窗体是否现实滚动条,这里指的不是窗体中打开页面的滚动条，而是显示网页iframe的滚动条[yes/no]
 * strWindowsHtmlType:窗体内打开页面的类型,例如，你打开报表页面时，需要传入Report，这样在任务栏中
 * Isanimate:窗体在进行缩放时，是否使用动画[目前不作为参数]
 * 会将超过两的报表窗口进行合并
 */
function ObjectWindows(strFatherID,strWindwosID,Height,Width,strUrl,strWindowTitle,
                        IframeScrolling,strWindowsHtmlType)
{
    this.strFatherID = strFatherID;
    this.strWindwosID = strWindwosID;
    this.Height = Height;
    this.Width = Width;
    this.strUrl = strUrl;
    this.strWindowTitle = strWindowTitle;
    this.IframeScrolling = IframeScrolling;
    this.strWindowsHtmlType = strWindowsHtmlType;
    this.Isanimate = true;
    this.DifferenceHOfWindows = 50;
    this.DifferenceWOfWindows = 18;
    //判断窗体是否存在
    if(this.WindowsExist()) return;
    
    //构建窗体结构
    this.CreateWindowsFrame();
    
    //绑定窗体关闭按钮事件
    this.BindWindowsCloseButtonEvent();
    
    //绑定窗体最小化按钮事件
    this.BindWindowsMinButtonEvent();
    
    //绑定窗体最大化按钮事件
    this.BindWindowsMaxButtonEvent();
    
    //绑定窗体拖动事件
    this.BindWindowsMoveEvent();
    
    //如果双击窗体在最大化和正常之间切换
    //如果单击窗体置顶
    this.BindWindowsTitleEvent();
    
    //绑定窗体置顶事件
    //即当什么情况下窗口会置顶
    this.BindWindowsToTopEvent();
    
    //绑定可以拖拽窗体大小的事件
    this.BindWindowsResizeEvent();
    
    //绑定合并任务栏相关事件
    this.BindTaskBarMergerEvent();
    
    //绑定任务栏相关事件
    this.BindTaskBarEvent();
    
    //设置窗体样式
    this.SetWindowsStyle();

    //阻止冒泡
    this.StopPropagationEvent();
    
    //窗口置顶
    ObjectWindows.WindowsToTop(this.strWindwosID);
    return this;
  
}
//判断窗体是否存在,如果窗体存在,函数返回ture，并将窗口置顶,否则返回false;
ObjectWindows.prototype.WindowsExist = function()
{
    var Bool_WindowsExist = false;
    //判断窗体是否存在,如果length>0 说明存在
    if ($("#" + this.strWindwosID).length > 0) 
    {
        //如果当前窗口处于隐藏状态,那么将窗口显示出来，并且将窗口置顶
        //负责直接将窗口置顶
	    if ($("#" + this.strWindwosID).attr("Windowhidden") == "Hidden") 
	    {
	        //获取窗口隐藏前的高度、宽度和位置,这些信息都存在这个窗体对应的任务栏中
		    var Windowwidth = $("#" + this.strWindwosID + "_TaskBar").attr("Windowwidth");
		    var Windowheight = $("#" + this.strWindwosID + "_TaskBar").attr("Windowheight");
		    var Windowleft = $("#" + this.strWindwosID + "_TaskBar").attr("Windowleft");
		    var Windowtop = $("#" + this.strWindwosID + "_TaskBar").attr("Windowtop");
		    
		    //进行窗体还原
		    $("#" + this.strWindwosID).animate({
			    opacity: '1',
			    width: Windowwidth,
			    height: Windowheight,
			    left: Windowleft,
			    top: Windowtop
		    },
		    "slow");
	    }
	    //窗口置顶
		ObjectWindows.WindowsToTop(this.strWindwosID);
		Bool_WindowsExist = true;
    }
    else
    {
        Bool_WindowsExist = false;
    }
	return Bool_WindowsExist;
}
/*
 * 创建窗体框架
 */
ObjectWindows.prototype.CreateWindowsFrame = function() {
    //判断WindowsHtmlType类型，如果传入的是Report,那么将Windows的WindowsHtmlType属性设置为Report,
    //否则设置为Other。
    var strWindowsHtmlType_Attr = "WindowsHtmlType='Other'";
    if (this.strWindowsHtmlType == "Report") {
        strWindowsHtmlType_Attr = "WindowsHtmlType='Report'";
    }
    //拼接窗口体结构
    var strInsertHtml = "";
    strInsertHtml += "<div id='" + this.strWindwosID + "' class='Windows_Frame_Div' style='position:absolute;width:" + this.Width + "px;  height:" + (this.Height + 20) + "px;' WindowsType='Windows' " + strWindowsHtmlType_Attr + " WindowTitle = '" + this.strWindowTitle + "'>";
    strInsertHtml += "<div id='" + this.strWindwosID + "_WH" + "' class='Windows_R_Size'></div>";
    strInsertHtml += "<div class ='active_lm' DivType='Frame_lm' Frame_Line='TRUE'></div>";
    strInsertHtml += "<div class ='active_tm' DivType='Frame_tm' Frame_Line='TRUE'></div>";
    strInsertHtml += "<div class ='active_tl' DivType='Frame_tl' Frame_Line='TRUE'></div>";
    strInsertHtml += "<div class ='active_tr' DivType='Frame_tr' Frame_Line='TRUE'></div>";
    strInsertHtml += "<div class ='active_bl' DivType='Frame_bl' Frame_Line='TRUE'></div>";
    strInsertHtml += "<div class ='active_br' DivType='Frame_br' Frame_Line='TRUE'></div>";
    strInsertHtml += "<div class ='active_rm' DivType='Frame_rm' Frame_Line='TRUE'></div>";
    strInsertHtml += "<div class ='active_bm' DivType='Frame_bm' Frame_Line='TRUE'></div>";
    //strInsertHtml+= "<div id='"+strWindwosID+"_Tile"+"' class='Windows_Title'><div style='float:left;'>"+strWindowTitle+"</div><div id ='"+strWindwosID+"_Close'style='float:right;margin-right:30px;width:40px;height:20px;color:red; line-height:30px;cursor:pointer;'>关闭</div></div>";
    strInsertHtml += "<div id='" + this.strWindwosID + "_Tile" + "' onselectstart='return false;'  class='Windows_Title'>";
    /*var strTmpWindwosID = "TaskModelInstWindows";
    if(this.strWindwosID.substring(this.strWindwosID.length - strTmpWindwosID.length) == strTmpWindwosID){
    	strInsertHtml+= "   <div id='WindowTitleId' style='float:left;margin-left:10px;'>"+this.strWindowTitle+"</div>";
    }
    if(this.strWindwosID.substring(this.strWindwosID.length - strTmpWindwosID.length) == strTmpWindwosID 
    		|| this.strWindwosID.substring(this.strWindwosID.length - "TaskModelInstSHWindows".length) == "TaskModelInstSHWindows"
    		|| this.strWindwosID.substring(this.strWindwosID.length - "TaskModelInstCKWindows".length) == "TaskModelInstCKWindows"){
    	strInsertHtml+= "   <div id='WindowTitleId' style='float:left;margin-left:10px;'>"+this.strWindowTitle+"</div>";
    }*/
    var strTmpTaskModelInstWindwosID = "TaskModelInstWindows";
    var strTmpTaskModelInstSHWindwosID = "TaskModelInstSHWindows";
    var strTmpTaskModelInstCKWindwosID = "TaskModelInstCKWindows";
    if(this.strWindwosID.substring(this.strWindwosID.length - strTmpTaskModelInstWindwosID.length) == strTmpTaskModelInstWindwosID){
    	strInsertHtml+= "   <div id='TaskModelInstWindowId' style='float:left;margin-left:10px;'>"+this.strWindowTitle+"</div>";
    }
    else if(this.strWindwosID.substring(this.strWindwosID.length - strTmpTaskModelInstSHWindwosID.length) == strTmpTaskModelInstSHWindwosID){
    	strInsertHtml+= "   <div id='TaskModelInstSHWindowId' style='float:left;margin-left:10px;'>"+this.strWindowTitle+"</div>";
    }
    else if(this.strWindwosID.substring(this.strWindwosID.length - strTmpTaskModelInstCKWindwosID.length) == strTmpTaskModelInstCKWindwosID){
    	strInsertHtml+= "   <div id='TaskModelInstCKWindowId' style='float:left;margin-left:10px;'>"+this.strWindowTitle+"</div>";
    }
    else{
    	strInsertHtml+= "   <div style='float:left;margin-left:10px;'>"+this.strWindowTitle+"</div>";
    }
    strInsertHtml += "   <div id ='" + this.strWindwosID + "_Close' class='WindowMaxMinClosed WindowClosed'></div>";
    strInsertHtml += "   <div id ='" + this.strWindwosID + "_Max'  class='WindowMaxMinClosed WindowMax'></div>";
    strInsertHtml += "   <div id ='" + this.strWindwosID + "_Min' class='WindowMaxMinClosed WindowMin'></div>";
    strInsertHtml += "</div>";
    /*strInsertHtml+= "<iframe id='"+this.strWindwosID+"_Iframe"+"'  frameborder='0' style='width:100%;height:"+(this.Height-30)+"px; background-color:White;'  src='"+this.strUrl+"' scrolling='"+this.IframeScrolling+"' ></iframe>";*/
    //strInsertHtml += "<div class='Div_Iframe_Frame' style='height:" + (this.Height - 40) + "px;width:" + (this.Width - 18) + "px' >";
    strInsertHtml += "   <iframe id='" + this.strWindwosID + "_Iframe' class='Div_Iframe'  frameborder='0' style= 'height:" + (this.Height - this.DifferenceHOfWindows) + "px;width:" + (this.Width - this.DifferenceWOfWindows) + "px' src='" + this.strUrl + "' scrolling='" + this.IframeScrolling + "'></iframe>";
    //strInsertHtml+= "</div>";
    strInsertHtml += "<div id='" + this.strWindwosID + "_Move" + "' class='WindowMove_Cover'  WindowsType='Move_Div' WindowsHeight='" + (this.Height - 60) + "'></div>";

    //strInsertHtml += "<div id='" + this.strWindwosID + "_Bottom_Div" + "' class='Windows_Bottom_Div'><img id='" + this.strWindwosID + "_WH" + "' class='Windows_R_Size' src='images/windows/JiaoDorp_Temp.png'/></div>";
    strInsertHtml += "</div>";

    //将窗体插入到制定的容器中
    $("#" + this.strFatherID).append(strInsertHtml);

    //将这个窗体添加到堆栈队列中
    ObjectWindows.WindowsArrayInsert(this.strWindwosID);

    //设置窗口的位置
    var top = ($(window).height() - this.Height) / 2 + ObjectWindows.WindowsCount * 40;
    var left = ($(window).width() - this.Width) / 2 + ObjectWindows.WindowsCount * 40;
    $("#" + this.strWindwosID).css({ "top": top, "left": left });


    //调整窗体边框
    ObjectWindows.ChangeWindowsFrameWH(this.strWindwosID, this.Width, this.Height +20);
    
    //窗体个数统计加1
    ObjectWindows.WindowsCount++;
}
/*
 * 绑定窗体关闭按钮事件
 * 如果点击(Click)关闭按钮,那么关闭窗体
 */
ObjectWindows.prototype.BindWindowsCloseButtonEvent = function()
{
    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
    $("#"+WindowsTmpId+"_Close").click(function(){
        //关闭窗口
        ObjectWindows.CloseWindows(WindowsTmpId);
    });
}
/*
 * 静态函数
 * 关闭窗口
 */
ObjectWindows.CloseWindows = function(strWindowsID)
{
    //删除窗体
    $("#" + strWindowsID).remove();
            
    //删除任务栏
    $("#" + strWindowsID+"_TaskBar").remove();
            
    //删除快捷菜单
    $("#" + strWindowsID+"_QucikBar").remove();
            
    //窗体个数统计减1
    ObjectWindows.WindowsCount --;
    //将窗体从堆栈中删除
    ObjectWindows.WindowsArrayDelete(strWindowsID);
            
    //重置合并任务栏
    ObjectWindows.RebuildTaskBarMerger();
    
    //快捷工具条重新布局
    CreateReportQuickBar.AutoLayout();
}
/*
 * 绑定窗体最小化按钮事件
 * 如果点击(Click)最小化按钮,那么将窗体最小化
 */
ObjectWindows.prototype.BindWindowsMinButtonEvent = function()
{
    //获取当前窗口是否执行动画效果
    var ThisWindowsanimate = this.Isanimate;
    
    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
    $("#"+WindowsTmpId+"_Min").click(function(){
        //读取当前窗口位置和大小信息
        var Windowwidth = $("#" + WindowsTmpId).width();
        var Windowheight = $("#" + WindowsTmpId).height();
        var Windowleft = $("#" + WindowsTmpId).css("left");
        var Windowtop = $("#" + WindowsTmpId).css("top");
        
        //将信息存储到对应的任务栏按钮上
        $("#"+WindowsTmpId+"_TaskBar").attr("Windowwidth",Windowwidth);
        $("#"+WindowsTmpId+"_TaskBar").attr("Windowheight",Windowheight);
        $("#"+WindowsTmpId+"_TaskBar").attr("Windowleft",Windowleft);
        $("#"+WindowsTmpId+"_TaskBar").attr("Windowtop",Windowtop);
        
        //执行最小化操作
        //判断是否执行动画
        if(ThisWindowsanimate)
            $("#" + WindowsTmpId).animate({opacity:'0',width:'0',height:'0',left:Windowleft,top:$(window).height()},"slow");
        else
            $("#" + WindowsTmpId).css({opacity:'0',width:'0',height:'0',left:Windowleft,top:$(window).height()});
        
        //设置窗体的Windowhidden属性为Hidden,此属性主要是判断目前窗体是否最小化
        $("#" + WindowsTmpId).attr("Windowhidden","Hidden");
    });
}
/*
 * 绑定窗体最大化按钮事件
 * 如果点击(Click)最大化按钮,那么将窗体最大化或者还原
 */
ObjectWindows.prototype.BindWindowsMaxButtonEvent = function() {
    //获取当前窗口是否执行动画效果
    var ThisWindowsanimate = this.Isanimate;

    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
    var DifferenceHOfWindows_tmp = this.DifferenceHOfWindows;
    var DifferenceWOfWindows_tmp = this.DifferenceWOfWindows;

    $("#" + WindowsTmpId + "_Max").click(function() {
        //判断目前的窗口是否处于最大化状态
        if ($("#" + WindowsTmpId).attr("WindowStat") == "Max") {
            //如果窗体是最大化,那么就意味着我需要把他还原到最大化之前大小和位置
            //那么这些信息都是存储在最大化按钮的属性里
            //因此，我们需要从按钮的属性中读取最大化之前大小和位置信息
            var Windowwidth = $(this).attr("Windowwidth");
            var Windowheight = $(this).attr("Windowheight");
            var Windowleft = $(this).attr("Windowleft");
            var Windowtop = $(this).attr("Windowtop");
            var Iframeheight = $(this).attr("Iframeheight");


            //执行还原操作
            //判断是否执行动画
            if (ThisWindowsanimate) {
                //首先将窗体内的Iframe控件进行还原
                $("#" + WindowsTmpId + "_Iframe").animate({ height: Iframeheight, width: Windowwidth - DifferenceWOfWindows_tmp }, "slow");

                //还原窗体本身
                $("#" + WindowsTmpId).animate({ width: Windowwidth, height: Windowheight, left: Windowleft, top: Windowtop }, "slow", function() { ObjectWindows.ChangeWindowsFrameWH(WindowsTmpId, Windowwidth, Windowheight); });
                //调整窗体边框

            }
            else {
                //首先将窗体内的Iframe控件进行还原
                $("#" + WindowsTmpId + "_Iframe").css({ height: Iframeheight });
                //$(".Div_Iframe_Frame").css({ height: Iframeheight });
                //还原窗体本身
                $("#" + WindowsTmpId).css({ width: Windowwidth, height: Windowheight, left: Windowleft, top: Windowtop });

                //调整窗体边框
                ObjectWindows.ChangeWindowsFrameWH(WindowsTmpId, Windowwidth, Windowheight);
            }




            //更新窗体的状态,到按钮的WindowStat属性里面
            $("#" + WindowsTmpId).attr("WindowStat", "Normal");

            //切换最大化按钮的背景图片
            $(this).css("background-image", "url(images/windows/btn-reg.png)");
        }
        else {
            //如果目前窗口是正确形态，那么我们需要将其最大化
            //首先将目前窗体的位置和大小信息读出来
            var Windowwidth = $("#" + WindowsTmpId).width();
            var Windowheight = $("#" + WindowsTmpId).height();
            var Windowleft = $("#" + WindowsTmpId).css("left");
            var Windowtop = $("#" + WindowsTmpId).css("top");
            //var Iframeheight = $(".Div_Iframe_Frame").height();
            var Iframeheight = $("#" + WindowsTmpId + "_Iframe").height();

            //然后将位置和大小信息存储到按钮的属性中
            $(this).attr("Windowwidth", Windowwidth);
            $(this).attr("Windowheight", Windowheight);
            $(this).attr("Windowleft", Windowleft);
            $(this).attr("Windowtop", Windowtop);
            $(this).attr("Iframeheight", Iframeheight);

            //计算目前屏幕的宽度和高度
            //这里-30的原因是窗体最大化后，不能覆盖下面的任务栏，30就是任务栏的高度
            var Setheight = $(window).height() - 30;

            var Setwidth = $(window).width();

            //执行最大化
            $("#" + WindowsTmpId + "_Iframe").css("height", Setheight - DifferenceHOfWindows_tmp - 20);
            $("#" + WindowsTmpId + "_Iframe").css("width", Setwidth - DifferenceWOfWindows_tmp);
            //$(".Div_Iframe_Frame").css("height", Setheight - 30);

            //判断是否执行动画
            if (ThisWindowsanimate)
                $("#" + WindowsTmpId).animate({ width: Setwidth, height: Setheight, left: '0', top: '0' }, "slow");
            else
                $("#" + WindowsTmpId).css({ width: Setwidth, height: Setheight, left: '0', top: '0' });

            //调整窗体边框
            ObjectWindows.ChangeWindowsFrameWH(WindowsTmpId, Setwidth, Setheight);

            //更新窗体的状态,到按钮的WindowStat属性里面
            $("#" + WindowsTmpId).attr("WindowStat", "Max");

            //切换最大化按钮的背景图片
            $(this).css("background-image", "url(images/windows/btn-max.png)");

            //将窗口置顶
            ObjectWindows.WindowsToTop(WindowsTmpId);
        }
    });
}
/*
 * 设置窗体可以被鼠标拖动
 * 当用户在窗口标题头部分按下鼠标后，窗体可以跟随鼠标移动
 * 这里面折中处理以下mousedown和dblclick的问题,这个问题解决起来非常的纠结
 * 本身为了使窗体托拽更为流畅,当用户在窗体上mousedown是,我会在窗体上面加一个
 * DIV，当鼠标移动时，DIV跟着移动，但是客户需要加一个dblclick窗口最大或者最小
 * 的操作，dblclick=mousedown + mouseup +mousedown + mouseup,因为mousedown会
 * 生存一个DIV，当第一个mousedown后，所有操作都点到了新增的DIV上,
 * 所以窗体根本就捕获不到db事件,而其db事件本身包括mousedown，所以
 * 系统根本就没法判断这个down是mousedown移动，还是db中的一步。
 * 为了解决这个问题，我想了很多办法，都是可以实现，但是效果都不好。
 * 1、不要新加入的DIV层，但是这样在托拽得时候会卡。
 * 2、使用clearTimeout和setTimeout，即当mousedown的时候使用setTimeout，让进入到db
 * 时clearTimeout。
 * 3、当mousedown时，所有正常执行，但是新增的div不会置顶，
 * 需要等待200毫秒，这段时间就是跟db预留的时间.为了使效果看起来更好，新增的DIV
 * 我会做一个透明度由浅到深的变化，这样会把非置顶的这段时间度过。
 **/
ObjectWindows.prototype.BindWindowsMoveEvent = function() {
    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
    $("#" + this.strWindwosID).mousedown(function(event) {
    	
        //当用户鼠标按下时,计算鼠标距离控件上和左边的距离
        event = event || window.event;
        var X_OffSet = parseInt(event.originalEvent.offsetX || event.originalEvent.layerX || 0);
        var Y_OffSet = parseInt(event.originalEvent.offsetY || event.originalEvent.layerY || 0);
        ObjectWindows.Windowstimer && clearTimeout(ObjectWindows.Windowstimer);
        //设置延时
        ObjectWindows.Windowstimer = setTimeout(function() {
            //获取但前窗口的大小和位置
            var Windowswidth = parseInt($("#" + WindowsTmpId).css("width"));
            var Windowsheight = parseInt($("#" + WindowsTmpId).css("height"));
            var Windowstop = parseInt($("#" + WindowsTmpId).css("top"));
            var Windowsleft = parseInt($("#" + WindowsTmpId).css("left"));

            //窗口置顶
            ObjectWindows.WindowsToTop(WindowsTmpId);

            //构建一个和窗体同样大小的div
            var strInsertHtml = "<div class='WindowMove_Div' id='" + WindowsTmpId + "_Move_Frame" + "' style='width:" + Windowswidth + "px; height:" + Windowsheight + "px; top:" + Windowstop + "px; left:" + Windowsleft + "px;'></div>";
            
            //绑定可以移动的事件
            var WindowsMove = new ObjectWindows.Drag_NewDiv(WindowsTmpId + "_Move_Frame", WindowsTmpId, strInsertHtml, X_OffSet, Y_OffSet);
        }, 0); //原来是200，为了用户体验现在修改为0
    });
}
/*
 * 窗口可以跟随鼠标移动的主方法
 * 此方法可以使窗体移动，当用户移动鼠标的时候，系统重新生成一个类似于母窗体大小的DIV，此DIV跟随
 * 用户鼠标移动，当鼠标太起后，母窗体瞬移到DIV的位置。因为系统自动生成的DIV没有其他子元素，所以不会特别的卡。
 * 应该在用户承受范围内，如果用户使用的IE9以上的版本，那么效果会比较好。
 */
ObjectWindows.Drag_NewDiv = function(id, strWindowID, strMove_Frame, X_OffSet, Y_OffSet) {
    //创建临时层
    $("body").append(strMove_Frame);

    //设置参数
    var el = document.getElementById(id),
	options = arguments[1] || {},
	container = options.container || document.documentElement,
	limit = false || options.limit,
	lockX = false || options.lockX,
	lockY = false || options.lockY;
    el.style.position = "absolute";

    var drag = function(e) {
        var myDate = new Date();
        var t = myDate.getSeconds();
        e = e || window.event;
        el.style.cursor = "pointer"; ! +"\v1" ? document.selection.empty() : window.getSelection().removeAllRanges();
        var _left = e.clientX - el.offset_x,
		_top = e.clientY - el.offset_y;
        el.style.left = _left + "px";
        el.style.top = _top + "px";
        //alert(t-myDate.getSeconds());
    }
    var WhenUserUpMouse = function() {
        //窗体置顶
        ObjectWindows.WindowsToTop(strWindowID);

        //调整窗体透明度
        $("#" + strWindowID).css("opacity", "1");

        //设置所有窗体覆盖层高度 
        $("[WindowsType='Move_Div']").css("height", "0%");

        //窗口置顶
        ObjectWindows.WindowsToTop(strWindowID);

        //设置窗体位置
        $("#" + strWindowID).css({ "left": el.style.left, "top": el.style.top });

        //移除覆盖层
        $("#" + id).remove();

    }
    var dragend = function() {

        WhenUserUpMouse();
        //取消事件
        document.body.onmouseup = null;
        document.body.onmousemove = null;
    }
    var WhenUserDownMouse = function() {
        //设置临时层透明度
    	$("#" + el.getAttribute("id")).css("opacity", "0.2");

        //设置临时曾层级
        $("#" + strWindowID + "_Move_Frame").css("zIndex", "10000");

        //设置窗体透明度
        $("#" + strWindowID).css("opacity", "1");

        //调整所有窗体覆盖层高度
        $("[WindowsType='Move_Div']").each(function() {
            //$(this).css("height",$(this).attr("WindowsHeight"));
            $(this).css("height", '100%');
        });
    }

    //设置临时层层级
    Drag.z = 999;

    //获取鼠标相对于临时层上和左的位置
    el.offset_x = X_OffSet;
    el.offset_y = Y_OffSet;
    WhenUserDownMouse();

    //绑定事件
    document.body.onmousemove = drag;
    document.body.onmouseup = dragend;

}
/*
 * 绑定窗体Title单击和双击事件
 * 如果是双击事件，那么根据目前窗体的形态（最大或者正常）,进行缩放。
 * 如果当前是正常，那么双击后窗体最大化，否则相反.
 * 如果是单击事件，那么窗体置顶
 */
ObjectWindows.prototype.BindWindowsTitleEvent = function()
{
    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
    
    //这是双键事件
    $("#"+WindowsTmpId+"_Tile").dblclick(function() {
    
        //如果窗体捕获了双击事件，那么停止相应mousedown事件
        clearTimeout(ObjectWindows.Windowstimer);
        //调用窗体最大化按钮事件
        $("#"+WindowsTmpId+"_Max").click();
    });
    $("#"+WindowsTmpId+"_Tile").click(function() {
    
        //如果窗体捕获了双击事件，那么停止相应mousedown事件
        clearTimeout(ObjectWindows.Windowstimer);
        
        //窗体置顶
        ObjectWindows.WindowsToTop(WindowsTmpId);
    });
}
/*
 * 阻止相关元素的冒泡事件
 * 简单解释一个“冒泡”,也就是如果两个叠放在一起的透明DIV,如果都绑定了click事件，那么
 * 当你点击上层DIV时，不但会触发其自身的click事件，同时也会触发下面Div的Click事件
 */
ObjectWindows.prototype.StopPropagationEvent = function()
{

    //阻止底边 - mousedown冒泡
    $("#"+this.strWindwosID+"_Bottom_Div").mousedown(function(event) {
        event.stopPropagation();
    });
    
    //阻止关闭按钮 - mousedown冒泡
    $("#"+this.strWindwosID+"_Close").mousedown(function(event){
        event.stopPropagation();
    });
    
    //阻止最大化按钮 - mousedown冒泡
    $("#"+this.strWindwosID+"_Max").mousedown(function(event){
        event.stopPropagation();
    });
    
    //阻止最小化按钮 - mousedown冒泡
    $("#"+this.strWindwosID+"_Min").mousedown(function(event){
        event.stopPropagation();
    });
    
    //阻止移动DIV - mousedown冒泡
    $("#"+this.strWindwosID+"_Move").mousedown(function(event){
        event.stopPropagation();
    });
    
    //阻止移动DIV - click 冒泡
    $("#"+this.strWindwosID+"_Move").click(function(event){
        event.stopPropagation();
    });
    
    //阻止移动css 为TaskBar的DIV - mousedown 冒泡
    $(".TaskBar").mousedown(function(event) {
            event.stopPropagation();
    });
    
    //阻止冒泡 Windows边框图片
    $("[Frame_Line='TRUE']'").mousedown(function(event) {
        event.stopPropagation();
    });
    //阻止冒泡 拖拽窗体大小
    $(".Windows_R_Size").mousedown(function(event) {
        event.stopPropagation();
    });
}
/*
 * 绑定窗体置顶事件
 * 即当什么情况下，窗体会被置顶。
 * 目前只有两种情况(除Title各类事件外)
 * 1、就是当点击窗体下边框时
 * 2、就是当点击窗体Move_Div时.
 */
ObjectWindows.prototype.BindWindowsToTopEvent = function()
{
    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
    
    //当用户click某个窗体时，这个窗体置顶
    $("#"+this.strWindwosID+"_Move").click(function(event){
        ObjectWindows.WindowsToTop(WindowsTmpId);
    });
    
    //当点击窗体下边时，将窗体置顶
    $("#"+this.strWindwosID+"_Bottom_Div").click(function(){
        ObjectWindows.WindowsToTop(WindowsTmpId);
    });
}
/*
 * 绑定窗口可变大小事件
 * 当点击右下的三角标后，可以拖拽窗体大小。
 */
ObjectWindows.prototype.BindWindowsResizeEvent = function()
{
    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
    var DifferenceHOfWindows_tmp = this.DifferenceHOfWindows;
    var DifferenceWOfWindows_tmp = this.DifferenceWOfWindows;
    $("#"+this.strWindwosID+"_WH").mousedown(function(event) {
    
        //当鼠标按下时，首先获取当前窗口的大小和位置
    	var Windowswidth = parseInt($("#"+WindowsTmpId).css("width"));
	    var Windowsheight = parseInt($("#"+WindowsTmpId).css("height"));
	    var Windowstop = parseInt($("#"+WindowsTmpId).css("top"));
	    var Windowsleft =parseInt($("#"+WindowsTmpId).css("left"));
        
        //然后构建一个与窗体大小和位置一样的DIV
	    var strInsertHtml = "<div class='WindowSize_Div' id='"+WindowsTmpId+"_Size_Frame"+"' style='width:"+Windowswidth+"px; height:"+Windowsheight+"px; top:"+Windowstop+"px; left:"+Windowsleft+"px;'></div>";
	    
	    //将这个DIv插入到body中
	    $("body").append(strInsertHtml);
	    
	    //然后将窗口置顶
        ObjectWindows.WindowsToTop(WindowsTmpId);
        
        //绑定可以拖拽大小的事件
        ObjectWindows.BindResize(WindowsTmpId, WindowsTmpId + "_WH", event, DifferenceHOfWindows_tmp, DifferenceWOfWindows_tmp);
    });
}
/*
 * 窗体可以拖拽大小的主事件
 * 此方法可以使窗体改变，当用户移动鼠标的时候，系统重新生成一个类似于母窗体大小的DIV，此DIV跟随
 * 用户鼠标移动，当鼠标太起后，母窗体会改变大小。因为系统自动生成的DIV没有其他子元素，所以不会特别的卡。
 * 应该在用户承受范围内，如果用户使用的IE9以上的版本，那么效果会比较好。
 */
ObjectWindows.BindResize = function(strWindowsId, ResizeDiv, event, DifferenceHOfWindows, DifferenceWOfWindows) {
    //初始化参数 
    var el = document.getElementById(ResizeDiv);
    var els = document.getElementById(strWindowsId).style;
    var Iframe = document.getElementById(strWindowsId + "_Iframe").style;
    var WindowSize = document.getElementById(strWindowsId + "_Size_Frame").style;
    //鼠标的 X 和 Y 轴坐标 
    x = 0;
    y = 0;
    //按下元素后，计算当前鼠标与对象计算后的坐标 
    x = event.clientX - $("#" + strWindowsId + "_Size_Frame").width();
    y = event.clientY - $("#" + strWindowsId + "_Size_Frame").height();
    //在支持 setCapture 做些东东 
    el.setCapture ? (
    //捕捉焦点 
	el.setCapture(),
    //设置事件 
	el.onmousemove = function(ev) {
	    mouseMove(ev || event);
	},
	el.onmouseup = mouseUp) : (
    //绑定事件 
	$(document.body).bind("mousemove", mouseMove).bind("mouseup", mouseUp));
    //防止默认事件发生 
    event.preventDefault();
    $(document.body).bind("mousemove", mouseMove).bind("mouseup", mouseUp);

    //移动事件 
    function mouseMove(e) {

        //这里的判断主要是控制窗体最小不能小于200X200
        if (e.clientX - x > 200) {
            WindowSize.width = e.clientX - x + 'px';
        }
        else {
            WindowSize.width = '200px';
        }

        if (e.clientY - y > 200) {
            WindowSize.height = e.clientY - y + 'px';
        }
        else {
            WindowSize.height = '200px';
        }
    }
    //停止事件 
    function mouseUp() {
        WhenUserUpMouse();
        //在支持 releaseCapture 做些东东 
        el.releaseCapture ? (
        //释放焦点 
		el.releaseCapture(),
        //移除事件 
		el.onmousemove = el.onmouseup = null) : (
        //卸载事件 
		$(document.body).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp));
        $(document.body).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp);
    }
    //当鼠标抬起后
    function WhenUserUpMouse() {
        //这是窗体的透明度
        //$("[WindowsType='Windows']").css("opacity","1");
        $("#" + strWindowsId).css("opacity", "1");

        //调整所有窗体覆盖层高度
        $("[WindowsType='Move_Div']").css("height", "0%");

        //设置Iframe高度
        Iframe.height = parseInt(WindowSize.height) - DifferenceHOfWindows - 20 + 'px';
        Iframe.width = parseInt(WindowSize.width) - DifferenceWOfWindows + 'px';
        //$(".Div_Iframe_Frame").css({ "height": parseInt(WindowSize.height) - 60 });

        //设置窗体大小
        $("#" + strWindowsId).css({ "width": WindowSize.width, "height": WindowSize.height });

        ObjectWindows.ChangeWindowsFrameWH(strWindowsId,WindowSize.width, WindowSize.height);

        //移除临时层
        $("#" + strWindowsId + "_Size_Frame").remove();

        //窗口置顶
        ObjectWindows.WindowsToTop(strWindowsId);
    }
    //当鼠标按下时
    function WhenUserDownMouse() {
        //调整缩放层的层级
        $("#" + strWindowsId + "_Size_Frame").css("zIndex", "10000");

        //将调整DIv的透明度调整
        $("#" + strWindowsId + "_Size_Frame").css("opacity", "0.2");

        //调整窗体透明度
        $("#" + strWindowsId).css("opacity", "1");

        //调整其它窗体的覆盖层
        $("[WindowsType='Move_Div']").each(function() {
            //$(this).css("height",$(this).attr("WindowsHeight"));
            $(this).css("height", "100%");
        });

    }
    WhenUserDownMouse();
}
/*
 * 绑定合并任务栏相关事件
 */
ObjectWindows.prototype.BindTaskBarMergerEvent = function()
{
    //获取当前窗口是否执行动画效果
    var ThisWindowsanimate = this.Isanimate;
    
    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
    
    /*
    判断当前WindowsHtmlType='Report'有多少个,WindowsHtmlType='Report'说明当前窗体中打开的是
    报表填报页面，如果当前数量<=1,那么任务栏中就显示当前报表的名称，如果>1那么就将其合并,任务栏
    中显示共用名称,后面加上目前报表页面的数量
    */
    if($("[WindowsHtmlType='Report']").length > 1 )
    {
        $("#"+this.strWindwosID +"_TaskBar").remove();
        $("#Report_TaskBar").remove();
        
        strInsertHtml = "<div id='"+this.strWindwosID +"_TaskBar"+"' class='TaskBar' WindowsID='"+this.strWindwosID+"' onselectstart='javascript:return false;'>"+this.strWindowTitle+"</div>";
        $("#TaskBarLine").append(strInsertHtml);
        
        $("[WindowsHtmlType='Report']").each(function(){
            var ThisWindowsID = $(this).attr("id");
            $("#"+ThisWindowsID +"_TaskBar").hide();
        });
        var ReportWindowsNum = $("[WindowsHtmlType='Report']").length;
        strInsertHtml = "<div id='Report_TaskBar"+"' class='TaskBar' onselectstart='javascript:return false;'>"+"报表填报页面[" + ReportWindowsNum + "]</div>";
        $("#TaskBarLine").append(strInsertHtml);
    }
    else
    {
        
        $("#"+this.strWindwosID +"_TaskBar").remove();
        strInsertHtml = "<div id='"+this.strWindwosID +"_TaskBar"+"' class='TaskBar' WindowsID='"+this.strWindwosID+"' onselectstart='javascript:return false;' title='" + this.strWindowTitle + "'>"+this.strWindowTitle+"</div>";
        $("#TaskBarLine").append(strInsertHtml);
    }
    
    //设置合并任务栏事件
    $("#Report_TaskBar").mousedown(function(event) {
    
        //清除菜单
        $("#TaskBarRightMeum").remove();
        var InsertHTML = "";
        
        //如果没有合并，则直接返回
        if($("[WindowsHtmlType='Report']").length <= 0)
            return;
            
        //如果已经达到了合并的情况，那么就构建合并菜单
        InsertHTML += "  <div id='TaskBarRightMeum' class='Report_Merge_MEUM'>";
        $("[WindowsHtmlType='Report']").each(function (){
            InsertHTML += "  <div type_It='ShowWindows' WindowsID ='" + $(this).attr("id") + "'><img type_It='Close' WindowsID ='" + $(this).attr("id") + "' src='images/rightmeum/TaskBar/cancel.png' /><div onselectstart='javascript:return false;' title='" + $(this).attr("WindowTitle") + "'>" + $(this).attr("WindowTitle") + "</div></div>";
        });
        InsertHTML += "<br style='clear:both;'/>";
        InsertHTML += "  </div>";
        
        //将合并的菜单插入body
        $("body").append(InsertHTML);

        //设置CSS
        $(".SHOW_INFO_CSS").css("color", "Black");

        //设置菜单的位置和层级
        $("#TaskBarRightMeum").css("left", ObjectWindows.getAbsoluteLeft("Report_TaskBar"));
        $("#TaskBarRightMeum").css("z-index", 10000);
    
        //处理菜单项的事件
        $("[type_It]").unbind();
        $("[type_It]").click(function ()
        {
            var strType = $(this).attr("type_It");
            switch (strType)
            {
                //如果点的是X，那么就关闭窗口
                case "Close":
                    //删除菜单项
                    $("#" + $(this).attr("WindowsID")).remove();
                    
                    //关闭窗口
                    ObjectWindows.CloseWindows( $(this).attr("WindowsID"));
                    break;
                case "ShowWindows":
                    //显示窗体
                    ObjectWindows.WindowsShow($(this).attr("WindowsID"),ThisWindowsanimate);
                    break;
                default:
                    alert($(this).attr("type_It") + "  " + User_Choose_ID);
            }
            
            //移除菜单
            $("#TaskBarRightMeum").remove();
        });
    });
}
/*
 * 绑定任务栏相关事件
 */
ObjectWindows.prototype.BindTaskBarEvent = function()
{
    //获取当前窗口是否执行动画效果
    var ThisWindowsanimate = this.Isanimate;
    
    //获取当前窗口的ID,因为这里的this会冲突
    var WindowsTmpId = this.strWindwosID;
   
    //设置任务栏右键菜单事件
    $("#"+this.strWindwosID+"_TaskBar").mousedown(function(event) {
	    if (event.which == 3) {
	    
	        //清除菜单
		    $("#TaskBarRightMeum").remove();
		    var InsertHTML = "";
		    
		    //构建菜单，
		    //一个关闭,一个最小化,一个显示
		    InsertHTML += "  <div id='TaskBarRightMeum' class='TASK_RIGRH_MEUM'>";
		    InsertHTML += "  <div type_It='Close'><img src='images/rightmeum/TaskBar/cancel.png' /><a onselectstart='javascript:return false;'>关闭</a></div>";
		   /* InsertHTML += "  <div type_It='Hidden'><img src='images/rightmeum/TaskBar/Hidden.png' /><a onselectstart='javascript:return false;'>最小化</a></div>";
		    InsertHTML += "  <div type_It='Show'><img src='images/rightmeum/TaskBar/Show.png' /><a onselectstart='javascript:return false;'>显示</a></div>";*/
		    InsertHTML += "  <br style='clear:both;'/>";
		    InsertHTML += "  </div>";
		    
		    //插入body
		    $("body").append(InsertHTML);
		    
		    //设置菜单出现的位置
		    $(".SHOW_INFO_CSS").css("color", "Black");
		    //$("#TaskBarRightMeum").css("top", event.pageY - 50);
		    $("#TaskBarRightMeum").css("bottom", 30);
		    $("#TaskBarRightMeum").css("left", event.pageX);
		    $("#TaskBarRightMeum").css("z-index", 10000);
            
            //绑定菜单的事件
		    $("[type_It]").unbind();
		    $("[type_It]").click(function() {
			    var strType = $(this).attr("type_It");
			    switch (strType)
			    {
			        //如果类型是关闭
			        case "Close":
                        //关闭窗口
                        ObjectWindows.CloseWindows(WindowsTmpId);
				        break;
			        //如果类型是最小化
			        case "Hidden":
                        //最小化窗口
                        ObjectWindows.WindowsHidden(WindowsTmpId);
				        break;
			        //如果类型是显示
			        case "Show":
                        //显示窗口
                        ObjectWindows.WindowsShow(WindowsTmpId);
				        break;  
			        default:
				        alert($(this).attr("type_It") + "  " + User_Choose_ID);
			    }
			    //移除菜单
			    $("#TaskBarRightMeum").remove();
		    });
	    } 
	    //鼠标点击在body元素消除TaskBarRightMeum
    	$("#Main_Body").click(function(event){
    		$("#TaskBarRightMeum").remove();
    	});
    });
    
    //设置任务栏单击事件
    $("#"+this.strWindwosID+"_TaskBar").click(function(e){
    
        //从任务栏属性中获取其对应的窗体ID
        var WindowID = $(this).attr("WindowsID");
        
        //根据窗体的属性，来判断目前窗体的状态
        if($("#" + WindowID).attr("Windowhidden") == "Hidden")
        {
            //如果窗口处于隐藏状态，那么将会把他打开
            //设置目前打开的状态正在进行中
            ObjectWindows.WindowsShow(WindowID,ThisWindowsanimate);
        }
        else if($("#" + WindowID).attr("Windowhidden") == "Show")
        {
            //如果目前窗口没有处于隐藏状态
            //设置目前打开的状态正在进行中
            ObjectWindows.WindowsHidden(WindowID,ThisWindowsanimate);
        }
    });
}
/*
 * 静态函数
 * 调整窗体边框长度和宽度
 */
ObjectWindows.ChangeWindowsFrameWH = function(WindowID,Windowwidth, Windowheight) {
    $("#" + WindowID).children("[DivType = 'Frame_tm']").css({ "width": parseInt(Windowwidth) - 18 });
    $("#" + WindowID).children("[DivType = 'Frame_bm']").css({ "width": parseInt(Windowwidth) - 18 });
    $("#" + WindowID).children("[DivType = 'Frame_lm']").css({ "height": parseInt(Windowheight) - 18 });
    $("#" + WindowID).children("[DivType = 'Frame_rm']").css({ "height": parseInt(Windowheight) - 18 });
}
/*
 * 静态函数
 * 将窗体隐藏，并且置顶
 */
ObjectWindows.WindowsHidden = function(strWindows_Tmp,Windowsanimate)
{
    //如果目前窗口没有处于隐藏状态
    //设置目前打开的状态正在进行中
    $("#" + strWindows_Tmp).attr("Windowhidden","DO");
            
    //判断，如果有窗体最大化，而且选择任务对应的窗体不是最大化，而且窗体的层级不是1000
    if($("[WindowStat='Max']").length >0 
    && $("#"+strWindows_Tmp).attr("WindowStat") !="Max" 
    && $("#"+strWindows_Tmp).css("zIndex") !="1000")
    {   
        //将窗体置顶 
        ObjectWindows.WindowsToTop(strWindows_Tmp);
        return;
    }
            
    //获取窗体的大小和位置
    var Windowwidth = $("#" + strWindows_Tmp).width();
    var Windowheight = $("#" + strWindows_Tmp).height();
    var Windowleft = $("#" + strWindows_Tmp).css("left");
    var Windowtop = $("#" + strWindows_Tmp).css("top");
            
    //将信息保存在任务栏属性中
    $("#"+strWindows_Tmp+"_TaskBar").attr("Windowwidth",Windowwidth);
    $("#"+strWindows_Tmp+"_TaskBar").attr("Windowheight",Windowheight);
    $("#"+strWindows_Tmp+"_TaskBar").attr("Windowleft",Windowleft);
    $("#"+strWindows_Tmp+"_TaskBar").attr("Windowtop",Windowtop);
    
    //获取任务栏的决对位置，即窗体将要缩放的位置
    var WindowHiddenLeft = ObjectWindows.getAbsoluteLeft($("#"+strWindows_Tmp+"_TaskBar").attr("id"));
    
    //执行隐藏,执行完成后重置Windowhidden状态
    //判断是否执行动画
    if(Windowsanimate)
    {
        $("#" + strWindows_Tmp).animate({opacity:'0',width:'0',height:'0',left:WindowHiddenLeft,top:$(window).height()},"slow",function(){$("#" + strWindows_Tmp).attr("Windowhidden","Hidden");});
    }
    else
    {
       $("#" + strWindows_Tmp).css({opacity:'0',width:'0',height:'0',left:WindowHiddenLeft,top:$(window).height()});
       $("#" + strWindows_Tmp).attr("Windowhidden","Hidden");
    }
            
}
/*
 * 静态函数
 * 将窗体显示，并且置顶
 */
ObjectWindows.WindowsShow = function(strWindows_Tmp,Windowsanimate)
{
    //如果窗口处于隐藏状态，那么将会把他打开
    //设置目前打开的状态正在进行中
    $("#" + strWindows_Tmp).attr("Windowhidden","DO");
            
    //获取窗口之前的大小和位置
    var Windowwidth = $("#"+strWindows_Tmp+"_TaskBar").attr("Windowwidth");
    var Windowheight = $("#"+strWindows_Tmp+"_TaskBar").attr("Windowheight");
    var Windowleft = $("#"+strWindows_Tmp+"_TaskBar").attr("Windowleft");
    var Windowtop = $("#"+strWindows_Tmp+"_TaskBar").attr("Windowtop");
            
    //执行还原,执行完成后重置Windowhidden状态
    //判断是否执行动画
    if(Windowsanimate)
    {
        $("#" + strWindows_Tmp).animate({opacity:'1',width:Windowwidth,height:Windowheight,left:Windowleft,top:Windowtop},"slow",function(){$("#" + strWindows_Tmp).attr("Windowhidden","Show"); });
    }
    else
    {
        $("#" + strWindows_Tmp).css({opacity:'1',width:Windowwidth,height:Windowheight,left:Windowleft,top:Windowtop});
        $("#" + strWindows_Tmp).attr("Windowhidden","Show");
    }
            
    //窗体置顶
    ObjectWindows.WindowsToTop(strWindows_Tmp);
}
/*
 * 静态函数
 * 重置合并菜单项内容
 */
ObjectWindows.RebuildTaskBarMerger = function()
{
    $("#Report_TaskBar").text("报表填报页面[" + $("[WindowsHtmlType='Report']").length + "]");
    //如果当前合并菜单项内容不足，那么就切换到正常模式
    if($("[WindowsHtmlType='Report']").length <= 1)
    {
        //删除合并菜单项
        $("#Report_TaskBar").remove();
        //任务栏显示出来
        $(".TaskBar").show();
    }
}
/*
 * 设置窗体样式
 */
ObjectWindows.prototype.SetWindowsStyle = function()
{
    //设置窗体状态
    $("#" + this.strWindwosID).attr("Windowhidden","Show");
    
    //新打开的窗口置顶
    ObjectWindows.WindowszIndex();
}
/*
 * 静态函数
 * 设置窗口的层级关系
 */
ObjectWindows.WindowszIndex = function()
{
   var zIndex = 1000; 
   for(var index = 0 ; index <  ObjectWindows.WindowsArray.length ; index++)
   {
        $("#"+ObjectWindows.WindowsArray[index]).css("zIndex",zIndex);
        zIndex--;
   }
   $("[WindowsType='Move_Div']").each(function(){
        //$(this).css("height",$(this).attr("WindowsHeight"));
        $(this).css("height",'100%');
   });
   
   if(ObjectWindows.WindowsArray.length > 0)
   {
        $("#"+ObjectWindows.WindowsArray[0]+"_Move").css("height",0);
   }
   
   
}
/*
 * 静态函数
 * 从堆栈中移除一个窗体
 */
ObjectWindows.ChangeDivFrameCss = function(strWindowID)
{
    $(".active_tl").addClass("inactive_tl");
    $(".active_tr").addClass("inactive_tr");
    $(".active_bl").addClass("inactive_bl");
    $(".active_br").addClass("inactive_br");
    $(".active_lm").addClass("inactive_lm");
    $(".active_tm").addClass("inactive_tm");
    $(".active_rm").addClass("inactive_rm");
    $(".active_bm").addClass("inactive_bm");
    
    $(".active_tl").removeClass("active_tl");
    $(".active_tr").removeClass("active_tr");
    $(".active_bl").removeClass("active_bl");
    $(".active_br").removeClass("active_br");
    $(".active_lm").removeClass("active_lm");
    $(".active_tm").removeClass("active_tm");
    $(".active_rm").removeClass("active_rm");
    $(".active_bm").removeClass("active_bm");
    /*
    $("[DivType='Frame_tl']").addClass('inactive_tl');
    $("[DivType='Frame_tr']").addClass('inactive_tr');
    $("[DivType='Frame_bl']").addClass('inactive_bl');
    $("[DivType='Frame_br']").addClass('inactive_br');
    $("[DivType='Frame_lm']").addClass('inactive_lm');
    $("[DivType='Frame_tm']").addClass('inactive_tm');
    $("[DivType='Frame_rm']").addClass('inactive_rm');
    $("[DivType='Frame_bm']").addClass('inactive_bm');
    */
    $("#" + strWindowID).children("[DivType='Frame_tl']").addClass('active_tl');
    $("#" + strWindowID).children("[DivType='Frame_tr']").addClass('active_tr');
    $("#" + strWindowID).children("[DivType='Frame_bl']").addClass('active_bl');
    $("#" + strWindowID).children("[DivType='Frame_br']").addClass('active_br');
    $("#" + strWindowID).children("[DivType='Frame_lm']").addClass('active_lm');
    $("#" + strWindowID).children("[DivType='Frame_tm']").addClass('active_tm');
    $("#" + strWindowID).children("[DivType='Frame_rm']").addClass('active_rm');
    $("#" + strWindowID).children("[DivType='Frame_bm']").addClass('active_bm');
    
}
/*
 * 静态函数
 * 从堆栈中移除一个窗体
 */
ObjectWindows.WindowsArrayDelete = function(strWindowID)
{
   for(var index = 0 ; index <  ObjectWindows.WindowsArray.length ; index++)
   {
        if(ObjectWindows.WindowsArray[index] == strWindowID)
        {
            ObjectWindows.WindowsArray.splice(index,1);
            break;
        }
   }
}
/*
 * 静态函数
 * 向堆栈中加入一个窗体
 */
ObjectWindows.WindowsArrayInsert = function(strWindowID)
{
    ObjectWindows.WindowsArray.unshift(strWindowID);
}
/*
 * 静态函数
 * 将窗体置顶
 */
ObjectWindows.WindowsToTop = function(strWindowID)
{
    ObjectWindows.WindowsArrayDelete(strWindowID);
    ObjectWindows.WindowsArrayInsert(strWindowID);
    ObjectWindows.WindowszIndex();
    ObjectWindows.ChangeDivFrameCss(strWindowID);
}
/*
 * 静态函数
 * 获取控件左绝对位置
 */
ObjectWindows.getAbsoluteLeft = function(objectId) 
{
    o = document.getElementById(objectId);
    oLeft = o.offsetLeft;
    while(o.offsetParent!=null) 
    { 
        oParent = o.offsetParent;
        oLeft += oParent.offsetLeft;
        o = oParent;
    }
    return oLeft;
}
/*
 * 静态函数
 * 获取控件上绝对位置
 */
ObjectWindows.getAbsoluteTop = function(objectId) 
{
    o = document.getElementById(objectId);
    oTop = o.offsetTop;
    while(o.offsetParent!=null)
    {  
        oParent = o.offsetParent; 
        oTop += oParent.offsetTop;  // Add parent top position
        o = oParent;
    }
    return oTop;
}
/*
 * 静态函数
 * 获取控件宽度
 */
ObjectWindows.getElementWidth = function(objectId) 
{
    x = document.getElementById(objectId);
    return x.offsetWidth;
}