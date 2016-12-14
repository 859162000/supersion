//获取控件左绝对位置
function getAbsoluteLeft(objectId) 
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
//获取控件上绝对位置
function getAbsoluteTop(objectId) 
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

//获取控件宽度
function getElementWidth(objectId) 
{
    x = document.getElementById(objectId);
    return x.offsetWidth;
}
//获取控件高度
function getElementHeight(objectId) 
{
    x = document.getElementById(objectId);
    return x.offsetHeight;
}
/*
window.onload = function() {
	var p = document.getElementById("parent");
	new Drag("drag1", {
		container: p,
		limit: true,
		lockX: true
	});
	new Drag("drag2", {
		container: p,
		limit: true,
		lockY: true
	});
	new Drag("drag3", {
		container: p,
		limit: true
	});
	new Drag("drag4", {
		container: p,
		limit: true
	});
	new Drag("drag5", {
		container: p,
		limit: true
	});
	new Drag("drag6", {
		container: p,
		limit: true
	});
            }
*/
var getCoords = function(el) {
	var box = el.getBoundingClientRect(),
	doc = el.ownerDocument,
	body = doc.body,
	html = doc.documentElement,
	clientTop = html.clientTop || body.clientTop || 0,
	clientLeft = html.clientLeft || body.clientLeft || 0,
	top = box.top + (self.pageYOffset || html.scrollTop || body.scrollTop) - clientTop,
	left = box.left + (self.pageXOffset || html.scrollLeft || body.scrollLeft) - clientLeft
	return {
		'top': top,
		'left': left
	};
};
///此方法可以使窗体移动，当用户移动鼠标的时候，窗体跟着移动，这样会比较卡，特别是在IE8上，所以
///不是特别推荐这个方法
var Drag = function(id) {

	var el = document.getElementById(id),
	options = arguments[1] || {},
	container = options.container || document.documentElement,
	limit = false || options.limit,
	lockX = false || options.lockX,
	lockY = false || options.lockY;
	//el.style.position = "absolute";

	var drag = function(e) {
		e = e || window.event;
		el.style.cursor = "pointer"; ! +"\v1" ? document.selection.empty() : window.getSelection().removeAllRanges();
		var _left = e.clientX - el.offset_x,
		_top = e.clientY - el.offset_y;
		if (limit) {
			var _right = _left + el.offsetWidth,
			_bottom = _top + el.offsetHeight,
			_cCoords = getCoords(container),
			_cLeft = _cCoords.left,
			_cTop = _cCoords.top,
			_cRight = _cLeft + container.clientWidth,
			_cBottom = _cTop + container.clientHeight;
			_left = Math.max(_left, _cLeft);
			_top = Math.max(_top, _cTop);
			if (_right > _cRight) {
				_left = _cRight - el.offsetWidth;
			}
			if (_bottom > _cBottom) {
				_top = _cBottom - el.offsetHeight;
			}
		}
		if (lockX) {
			_left = el.lockX;
		}
		if (lockY) {
			_top = el.lockY;
		}
		el.style.left = _left + "px";
		el.style.top = _top + "px";
		//el.innerHTML = parseInt(el.style.left, 10) + " x " + parseInt(el.style.top, 10);
	}
    var WhenUserUpMouse = function()
    {
	    //$("#" + el.getAttribute("id") +"_Move").css("height","0");
	    //$("[WindowsType='Windows']").css("opacity","1");
	    //$("[WindowsType='Move_Div']").css("height","0");
    }
	var dragend = function() {
        WhenUserUpMouse();
		document.onmouseup = null;
		document.onmousemove = null;
	}
    var WhenUserDownMouse = function()
    {
		//$("#" + el.getAttribute("id") +"_Move").css("height",el.style.height);
		/*$("[WindowsType='Windows']").css("opacity","0.1");
		$("[WindowsType='Move_Div']").each(function(){
		    //alert($(this).attr("id"));
		    $(this).css("height",$(this).attr("WindowsHeight"));
		});*/

		//$("#" + el.getAttribute("id")).css("opacity","0.4");
    }
	var dragstart = function(e) {
		e = e || window.event;
		WhenUserDownMouse();
		if (lockX) {
			el.lockX = getCoords(el).left;
		}
		if (lockY) {
			el.lockY = getCoords(el).top;
		}
		if (/a/ [ - 1] == 'a') {
	
			el.offset_x = e.layerX;
			el.offset_y = e.layerY;
		} else {
            /*var InsertTHML = "<div id='XXXXXX' style='background-color:#5579BA; width:200px; height:200px; position:absolute;'></div>";
            var elll = document.getElementById("XXXXXX");
            $("body").append(InsertTHML);*/
			el.offset_x = e.offsetX;
			el.offset_y = e.offsetY;
		}
		document.onmouseup = dragend;
		document.onmousemove = drag;
		el.style.zIndex = ++Drag.z;
		return false;
	}
	var InsertNewDivFrame = function()
	{

    }
	Drag.z = 999;
	el.onmousedown = dragstart;
}
function InsertNewICO(strFatherID,strICOID,strICOPath,strOneRow,strTwoRow,strThreeRow,checkVale)
{
    $("#" + strICOID).remove();
    var strInsertHtml = "";
    strInsertHtml+= "<div id='"+strICOID+"' rows_type='View' class='Ico_Css Ico_Css:hover' OneRow='"+strOneRow+"' TwoRow='"+strTwoRow+"' ThreeRow='"+strThreeRow+"' >";
    strInsertHtml+= "<input name='idList' style='margin-top: 6px;display:none;' type='checkbox' value='"+checkVale+"'>";
    strInsertHtml+= "<img src='"+strICOPath+"'/>";
    strInsertHtml+= "<div style='margin-top:10px;' title ='"+strOneRow+"'>"+strOneRow+"</div>";
    strInsertHtml+= "<div title ='"+strTwoRow+"'>"+strTwoRow+"</div>";
    strInsertHtml+= "<div title ='"+strThreeRow+"'>"+strThreeRow+"</div>";
    strInsertHtml+= "</div>";
    $("#"+strFatherID).append(strInsertHtml);
}
function BlockadeWindow()
{
    var strInsertHtml = "";
    strInsertHtml+= "<div id='BlockadeWindow_Div' style='background-color:#CCCCCC; width:100%;  height:100%; position:absolute; left:0px; top:0px;'>";
    $("body").append(strInsertHtml);
    $("#BlockadeWindow_Div").css("opacity","0.5");
    
}
function RemoveBlockadeWindow()
{
    $("#BlockadeWindow_Div").remove();
}
///此方法可以改变窗体，当用户拖拽窗体时，会比较卡，特别是在IE8上，所以
///不是特别推荐这个方法，另外，一般子窗体内的页面都有重绘事件，当窗口变化时，会更卡。
function BindResize(ResizeDiv,strWindwosID){
	//初始化参数 
	var el = document.getElementById(ResizeDiv);
	var els = document.getElementById(strWindwosID).style;
	var Iframe = document.getElementById(strWindwosID+"_Iframe").style; 
	//鼠标的 X 和 Y 轴坐标 
	x = 0;
	y = 0;
	//邪恶的食指 
	$(el).mousedown(function(e) {
		//按下元素后，计算当前鼠标与对象计算后的坐标 
		x = e.clientX - el.offsetWidth - $("#" +strWindwosID).width();
		y = e.clientY - el.offsetHeight- $("#" +strWindwosID).height();
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
		$(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp));
		//防止默认事件发生 
		e.preventDefault();
		
	});
	//移动事件 
	function mouseMove(e) {
		if(e.clientX - x > 200)
		    els.width = e.clientX - x + 'px';
		    
		if(e.clientY - y > 200)
		{
		    els.height = e.clientY - y + 'px';
		    Iframe.height = e.clientY - y - 50 + 'px';
		}
	}
	//停止事件 
	function mouseUp() {
		//在支持 releaseCapture 做些东东 
		el.releaseCapture ? (
		//释放焦点 
		el.releaseCapture(),
		//移除事件 
		el.onmousemove = el.onmouseup = null) : (
		//卸载事件 
		$(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp));
	}
}
function BindResizeThree(SqlitDiv,LeftDiv,RightDiv){
	//初始化参数
	var el = document.getElementById(SqlitDiv);
	var LeftDiv_Object = document.getElementById(LeftDiv).style;
	var RightDiv_Object = document.getElementById(RightDiv).style;
	var RightDiv_Object_Width = $("#" +RightDiv).width();
	//鼠标的 X 和 Y 轴坐标 
	LeftDiv_x = 0;
	RightDiv_x = 0;
	//邪恶的食指 
	$(el).mousedown(function(e) {
		//按下元素后，计算当前鼠标与对象计算后的坐标 
		LeftDiv_x = e.clientX - el.offsetWidth - $("#" +LeftDiv).width();
		RightDiv_x = e.clientX - el.offsetWidth + $("#" +RightDiv).width();
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
		$(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp));
		//防止默认事件发生 
		e.preventDefault();
	});
	//移动事件 
	function mouseMove(e) {
	     if((e.clientX - LeftDiv_x) < 50 || (RightDiv_x - e.clientX) < 50)
	        return;
		 LeftDiv_Object.width = e.clientX - LeftDiv_x + 'px';
		 // RightDiv_Object.left = e.clientX + 'px';
		 RightDiv_Object.left= el.style.left = (e.clientX+1) + 'px';
		 //alert((RightDiv_x - e.clientX) + 'px');
		 RightDiv_Object.width = (RightDiv_x - e.clientX) + 'px';
	}
	//停止事件 
	function mouseUp() {
		//在支持 releaseCapture 做些东东 
		el.releaseCapture ? (
		//释放焦点 
		el.releaseCapture(),
		//移除事件 
		el.onmousemove = el.onmouseup = null) : (
		//卸载事件 
		$(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp));
	}
}

var divResize = function() {
	var totalHeight = $("html").height();
	var topHeight = $("#top").height();
	$("#menu").height(totalHeight - topHeight);
	$("#rightbar").height(totalHeight - topHeight);
}
//$(function() {
	//divResize();
	//$(window).resize(divResize);

	//bindResize(document.getElementById('rightbar'));
//});


/*向某个页面元素中插入报表项,试用于不定长报表页面*/
/*悬停函数设置*/
var mytimer = null;
function ReportCellTips(strReportID,strCellID,strFatherID)
{
    if (mytimer == -1) return;

    var strInsertHtml = "";
	var intLeft = $("#" + strCellID).offset().left;//getAbsoluteLeft(strThisID);
	var intTop =  $("#" + strCellID).offset().top + 40;//getAbsoluteTop(strThisID) + $(this).height() + 10;
    /*var intLeft = getAbsoluteLeft(strCellID);
    var intTop = getAbsoluteTop(strCellID) + $("#" +strCellID).height() + 20;*/
    var intWidth = $("#" + strCellID).css("width");// getElementWidth(strCellID);
    strInsertHtml += "<div id='"+strCellID+"_Tips' class = 'ReportCellTips_Div'>";
    strInsertHtml += "这个是"+strCellID+"元素提示框，向某个页面元素中插入报表项,试用于不定长报表页面，程序可以根据实际的情况动态加载这个提示。如果用户希望修改这个提示，那么可通过系统其他功能修改。";
    strInsertHtml += "<div style='width:100%; height:0px;float:left;'></div>";
    strInsertHtml += "<br style='clear:both;'/>";
    strInsertHtml += "</div>";

    //$("#" +strCellID).append(strInsertHtml);
    $("#"+strFatherID).append(strInsertHtml);
    
    $("#" +strCellID+"_Tips").css({"left":intLeft,"top":intTop,"width":intWidth});
    //$("#" +strCellID+"_Tips").css({"left":0,"top":5,"width":intWidth});
    //$("#" +strCellID+"_Tips").animate({opacity:'0',width:'0',height:'0',left:e.clientX,top:$(window).height()},"slow");
    $("#" +strCellID+"_Tips").dblclick(function(){
        $(this).remove();
    });
    clearTimeout(mytimer);
    mytimer = null;
}
function InsertReportDataListCell(strFatherID,strCellID,strCellDes,strCellContType,IsNULL,IsLikeDown,LikeDownType)
{
    $("#" + strCellID).remove();
    //LikeDown
    var strInsertHtml = "";
    var strTipsHtml ="";
    var strattr = "";
    
    var strClass = "TextBox";
    if(IsLikeDown)
    {
        strClass += " LikeDown";
        strattr= "LikeDown='yes' LikeDownType='"+LikeDownType+"' ";
        
    }
    if(IsNULL)
    {
        strTipsHtml = "<div class='Tips'>*</div>"
    }
    strInsertHtml+= "<div id='"+strCellID+"' class='DataCell_Div'>";
    strInsertHtml+= "    <div class='DataCell_Des_Div'>" + strTipsHtml + strCellDes +":</div>";
    strInsertHtml+= "    <div class='DataCell_Cont_Div'>";
    if(strCellContType =="text")
        strInsertHtml+= "        <input class='"+strClass+"' id='"+strCellID+"_Control' FatherDivID='"+strCellID+"' type='text' style='width:180px; height:26px;line-height:26px; ' "+strattr+" />";
    else if(strCellContType =="select")
        strInsertHtml+= "<select id='Select1' class='"+strClass+"' id='Text1' type='text' FatherDivID='"+strCellID+"' style='width:185px; height:30px;line-height:26px;' "+strattr+"><option></option></select>";
    else
        strInsertHtml+= "        <input class='"+strClass+"' id=id='"+strCellID+"_Control' FatherDivID='"+strCellID+"' type='text'  style='width:180px; height:26px;line-height:26px;' "+strattr+" />";
    strInsertHtml+= "    </div>";
    strInsertHtml+= "</div>";
    $("#"+strFatherID).append(strInsertHtml);
    
    
    /*这里开始设置鼠标在DIV上悬停时出现提示信息,悬停时间为2秒*/
    $("#"+strCellID).mouseover(function(){
       mytimer =  window.setTimeout("ReportCellTips('123','"+strCellID+"','"+strFatherID+"')", 2000);
    });
    $("#"+strCellID).mouseout(function(){
        clearTimeout(mytimer); 
        $("#" + $(this).attr("id") + "_Tips").remove();
        mytimer = -1;     
    });
}
function ReturnDownDiv(strType,stren,BindDivId)
{

    var strDivHtml = "";
    var AarryTmp = "";
    
    if(strType == "FiveLv")
    {
        AarryTmp = FiveLv;
    }
    if(strType == "Currency")
    {
        AarryTmp = Currency;
    }
    
    if(stren !="")
    {
        for(var index = 0 ; index < AarryTmp.length; index ++ )
        {
                //alert(makePy(FiveLv[index]));
                //alert(stren.toUpperCase());
          var strReturn = makePy(AarryTmp[index]).join('|');
         
          if(strReturn.indexOf(stren.toUpperCase()) >=0 || AarryTmp[index].indexOf(stren) >=0)
          {
               strDivHtml += "<div class='LikeDownListRow'>"+AarryTmp[index]+"</div>";
          }
        }
    }
    if(stren =="" || strDivHtml=="")
    {
        for(var index = 0 ; index < AarryTmp.length; index ++ )
        {
            strDivHtml += "<div class='LikeDownListRow'>"+AarryTmp[index]+"</div>";
        }
    }
	//strDivHtml += "<div style='width:100%; height:30px;float:left;'>aaaaaa</div>";
	strDivHtml += "<br style='clear:both;'/>";

    return strDivHtml;

}
function LoadLikeDownList()
{
    //focus
    $("[LikeDown='yes']").click(function() {
    
        var strThisID = $(this).attr("id");
        
        if($("#" + strThisID + "_Down").length > 0)
        {
            $(".ReportCellLikeDown_Div").remove();
            return;
        }
        else
        {
            $(".ReportCellLikeDown_Div").remove();
        }
        
        //alert($(this).offset("top"));
	    var intLeft = $(this).offset().left;//getAbsoluteLeft(strThisID);
	    var intTop =  $(this).offset().top + 37;//getAbsoluteTop(strThisID) + $(this).height() + 10;
	    var intWidth = $(this).css("width");//getElementWidth(strThisID);
	    var strInsertHtml_Down = "";
	    strInsertHtml_Down += "<div id='" + strThisID + "_Down' class = 'ReportCellLikeDown_Div'>";
	    strInsertHtml_Down += ReturnDownDiv($(this).attr("LikeDownType"),"",strThisID);
	    //strInsertHtml_Down += "<div style='width:100%; height:0px;float:left;'></div>";
	    //strInsertHtml_Down += "<br style='clear:both;'/>";
	    strInsertHtml_Down += "</div>";
       
	    $("#" + $(this).attr("FatherDivID")).append(strInsertHtml_Down);
        /*
        $("#" + strThisID + "_Down").css({
		    "left": intLeft,
		    "top": intTop,
		    "width": intWidth
	    });*/
	    $("#" + strThisID + "_Down").css({
		    "left":155,
		    "top": 5,
		    "width": intWidth
	    });
	    $(".LikeDownListRow").click(function(){
            //alert($(this).val());
	        $("#"+strThisID).val($(this).text());
	        $("#" + strThisID + "_Down").remove();
	    });
    });
    $("[LikeDown='yes']").blur(function() {
        //var strThisID = $(this).attr("id");
        //$("#" + strThisID + "_Down").remove();
    });
    $("[LikeDown='yes']").keyup(function() {
        var strThisID = $(this).attr("id");
        if($("#" +strThisID + "_Down").length > 0) 
        {
           var UserInput = $(this).val();
           var strHtmlDown = "";
           
           strHtmlDown = ReturnDownDiv($(this).attr("LikeDownType"),UserInput,strThisID);
           
           $("#" + strThisID + "_Down").children().remove();
           $("#" + strThisID + "_Down").append(strHtmlDown);
          // $("#" + strThisID + "_Down").css("height","auto");
	        $(".LikeDownListRow").click(function(){
	            
	            $("#"+strThisID).val($(this).text());
	            $("#" + strThisID + "_Down").remove();
	        });
        }
    });
}
function getParameter(param) {
    var query = window.location.search;
    var iLen = param.length;
    var iStart = query.indexOf(param);
    
    if (iStart == -1)
        return "";
    
    iStart += iLen + 1;
    
    var iEnd = query.indexOf("&", iStart);
    if (iEnd == -1)
        return query.substring(iStart);
    
    return query.substring(iStart, iEnd);
}

function ReplaceDeskBackground()
{
    var SelectDivTop = parseInt(($(window).height() - 420 - 40)/2);
    var SelectDivLeft =200/2;
    var SelectDivWidth =parseInt( $(window).width() - 200);
    $("#ReplaceDeskBackgroundDiv").remove();
    var strInsertHtml = "";
    strInsertHtml += "<div id='ReplaceDeskBackgroundDiv' class='ReplaceDeskBackgroundDiv_Frame'>"
    for(var index=1; index <=33 ; index ++ )
    {
        strInsertHtml += "    <div><img src='images/wall_page/"+index+".jpg'/></div>";
    }

    strInsertHtml += "</div>";
    $("body").append(strInsertHtml);
   // alert(SelectDivLeft);
    $("#ReplaceDeskBackgroundDiv").css({"top":SelectDivTop,"left":SelectDivLeft,"width":SelectDivWidth});
    
    $("#ReplaceDeskBackgroundDiv").children("div").children("img").click(function(){
        var BackGUrl = $(this).attr("src");
        $("#backgroundImage").children("img").attr("src",BackGUrl);
    });
    $("#ReplaceDeskBackgroundDiv").children("div").children("img").dblclick(function(){
        var BackGUrl = $(this).attr("src");
        $("#backgroundImage").children("img").attr("src",BackGUrl);
         $("#ReplaceDeskBackgroundDiv").remove();
    });
}