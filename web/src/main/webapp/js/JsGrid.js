/*
Create a new Grid
Object_Css - HeadRowCss,HeadCellCss,DataRowCss,DataCellCss
Object_Column - ColumnWidth,ColumnName,ColumnCode,DropList:true/false,DropType:"Normal"/"othertype"
*/
function CreateJsGrid(FatherDivID,HeadWidth,Object_Column,Object_Css,HeadFixed)
{
    this.HeadWidth = HeadWidth;
    this.FatherDivID = FatherDivID;
    this.Object_Column = Object_Column;
    this.Object_Css = Object_Css;
    
    var InsertHeadHtml = "";
    InsertHeadHtml+="<div class='"+Object_Css.HeadRowCss+"'  style='width:"+HeadWidth+"px;'>";
    InsertHeadHtml+="<div class='"+Object_Css.HeadCellCss+"' style='width:20px;'><input id='"+FatherDivID+"_Check' style='margin-top:6px;' type='checkbox'/></div>";
    for(var index = 0 ; index < Object_Column.length; index ++)
    {
        var Col = Object_Column[index];
        var Drop = "";
        if(Col.DropList)
            Drop = "<img id='"+Col.ColumnCode+"_Img' src='images/grid/payment-card.png' DropList='yes' DropType='"+Col.DropType+"' FatherDivID='"+Col.ColumnCode+"' style='margin-top:8px;float:left;margin-left:5px' />";
        
        InsertHeadHtml+="<div id='"+Col.ColumnCode+"_Head' class='"+Object_Css.HeadCellCss+"' style='width:"+Col.ColumnWidth+"px;' ColumnCode='"+Col.ColumnCode+"'>"+ Drop +Col.ColumnName +"</div>";
       
    }
    InsertHeadHtml+="</div>";
    
    if(HeadFixed)
        InsertHeadHtml+="<div class='JS_Grid_Head_Row_Fixed_Null_Rows'></div>";
    
    
    $("#"+FatherDivID).append(InsertHeadHtml);
    this.SetLastRowSpace(10);
    
    var This_tmp = this;
    $("#"+FatherDivID+"_Check").click(function(){
         This_tmp.CheckedAllRows(this);
     });
     //设置选择列时候的样式，但是感觉不好看，先屏蔽掉
    /*
    $("."+Object_Css.HeadCellCss).mouseover(function(){
       
         $(this).attr("ColumnCode");
         $("[ColumnCode="+$(this).attr("ColumnCode")+"]").css({"box-shadow":"0 5px 30px Black","background-color":"#F7F7F7"});
         
     });
    $("."+Object_Css.HeadCellCss).mouseleave(function(){
       
         $(this).attr("ColumnCode");
         $("[ColumnCode="+$(this).attr("ColumnCode")+"]").css({"box-shadow":"","background-color":""});
         
     });*/
    //alert(InsertHeadHtml);
    this.LoadDropDownList();
    return this;
}
//Set the height of last line to the bottom 
CreateJsGrid.prototype.SetLastRowSpace = function(Heigth)
{
    var InsertHeadHtml = "";
    $("#"+ this.FatherDivID).children('#Last_Div').remove();
    $("#"+ this.FatherDivID).children('#Last_Br').remove();
    InsertHeadHtml+="<div id ='Last_Div' style='width:100%; height:"+Heigth+"px;float:left;'></div>";
    InsertHeadHtml+="<br id='Last_Br' style='clear:both;height:0px;'/>";
    $("#"+this.FatherDivID).append(InsertHeadHtml);
}


//add a new line to grid
CreateJsGrid.prototype.AddNewRow = function(Object_Row,objectId)
{
    var InsertHeadHtml = "";
    InsertHeadHtml+="<div class='"+this.Object_Css.DataRowCss+"'  style='width:"+this.HeadWidth+"px;' Rows_Type='DataRow'>";
    InsertHeadHtml+="<div class='"+this.Object_Css.DataCellCss+"' style='width:20px;'><input name='idList' value='"+objectId+"' style='margin-top:6px;' type='checkbox' /></div>";
    for(var index = 0 ; index < this.Object_Column.length; index ++)
    {
        var Col = this.Object_Column[index];
        var Row = Object_Row[index];
        InsertHeadHtml+="<div class='"+this.Object_Css.DataCellCss+"' style='width:"+Col.ColumnWidth+"px;' ColumnCode='"+Col.ColumnCode+"' title='"+Row+"'>"+Row+"</div>";
    }
    InsertHeadHtml+="</div>";
    $("#"+this.FatherDivID).append(InsertHeadHtml);
    this.SetLastRowSpace(10);
    
    var FatherDivID_tmp = this.FatherDivID;
    $("#"+this.FatherDivID).children("[Rows_Type='DataRow']").unbind();
    $("#"+this.FatherDivID).children("[Rows_Type='DataRow']").click(function(){
         if(!$(this).hasClass("JS_Grid_Data_Row_Select"))
            $("#"+FatherDivID_tmp).children("[Rows_Type='DataRow']").removeClass("JS_Grid_Data_Row_Select");
         //$(this).addClass("JS_Grid_Data_Row_Select");
         $(this).toggleClass("JS_Grid_Data_Row_Select");
     });
    /*
    $("#"+this.FatherDivID).children("[Rows_Type='DataRow']").mouseover(function(){
          $(this).css("background-color","#0066FF");
          $(this).css("color","White");
     });
    $("#"+this.FatherDivID).children("[Rows_Type='DataRow']").mouseout(function(){
          $(this).css("background-color","White");
          $(this).css("color","#000000");
    });*/
}
//remove the checked rows from grid
CreateJsGrid.prototype.DeleteCheckedRows = function()
{
    //.parent().parent().remove();
    var HeadCheckID = this.FatherDivID+"_Check";
    $("#"+this.FatherDivID).children("div").children("div").children(":checkbox").each(function(){
        if($(this).attr("checked") && this.id != HeadCheckID)
        {
            $(this).parent().parent().remove();
        }
    });
}
//checked all rows in this gird
CreateJsGrid.prototype.CheckedAllRows = function(HeadCheck)
{
    if($(HeadCheck).attr("checked"))
    {
        $("#"+this.FatherDivID).children("div").children("div").children(":checkbox").attr("checked",'true');
    }
    else
    {
        $("#"+this.FatherDivID).children("div").children("div").children(":checkbox").removeAttr("checked"); 
    }
} 
function ReturnDownDiv(strType)
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
    if(strType == "Normal")
    {
        strDivHtml += "<div class='LikeDownListRow'><input type='radio' class='ReportColDown_Radio' /><div class='ReportColDown_Txt'>等于</div><input type='text' class='ReportColDown_Text TextBox' /></div>";
        strDivHtml += "<div class='LikeDownListRow'><input type='radio' class='ReportColDown_Radio' /><div class='ReportColDown_Txt'>包括</div><input type='text' class='ReportColDown_Text TextBox' /></div>";
        strDivHtml += "<div class='LikeDownListRow'><input type='radio' class='ReportColDown_Radio' /><div class='ReportColDown_Txt'>不等于</div><input type='text' class='ReportColDown_Text TextBox' /></div>";
        strDivHtml += "<div class='LikeDownListRow'><input type='radio' class='ReportColDown_Radio' /><div class='ReportColDown_Txt'>大于</div><input type='text' class='ReportColDown_Text TextBox' /></div>";
        strDivHtml += "<div class='LikeDownListRow'><input type='radio' class='ReportColDown_Radio' /><div class='ReportColDown_Txt'>小于</div><input type='text' class='ReportColDown_Text TextBox' /></div>";
    }
    else
    {
        for(var index = 0 ; index < AarryTmp.length; index ++ )
        {

            strDivHtml += "<div class='LikeDownListRow'><input style='margin-right:5px;margin-top:5px; float:left;' type='checkbox'/>"+AarryTmp[index]+"</div>";
        }
    }
	//strDivHtml += "<div style='width:100%; height:30px;float:left;'>aaaaaa</div>";
	strDivHtml += "<br style='clear:both;'/>";

    return strDivHtml;

}
CreateJsGrid.prototype.LoadDropDownList = function()
{
    //focus
    $("[DropList='yes']").click(function() {
    
        var strThisID = $(this).attr("id");
        var strFatherDivID = $(this).attr("FatherDivID");
        
        if($("#" + strThisID + "_Down").length > 0)
        {
            $(".ReportColDown_Div").remove();
            return;
        }
        else
        {
            $(".ReportColDown_Div").remove();
        }
       
        //alert($(this).offset("top"));
	    var intLeft = $(this).offset().left;//getAbsoluteLeft(strThisID);
	    var intTop =  $(this).offset().top + 37;//getAbsoluteTop(strThisID) + $(this).height() + 10;
	    //var intWidth = $(this).css("width");//getElementWidth(strThisID);
	    var strInsertHtml_Down = "";
	    strInsertHtml_Down += "<div id='" + strThisID + "_Down' class = 'ReportColDown_Div'>";
	    strInsertHtml_Down += ReturnDownDiv($(this).attr("DropType"));
	    //strInsertHtml_Down += "<div style='width:100%; height:0px;float:left;'></div>";
	    //strInsertHtml_Down += "<br style='clear:both;'/>";
	    strInsertHtml_Down += "</div>";
        //alert(strThisID);
        //"[ColumnCode='"+strThisID+"']"
         //alert(strInsertHtml_Down);
	    $("#"+strFatherDivID+"_Head").append(strInsertHtml_Down);
	   //$("body").append(strInsertHtml_Down);
        /*
        $("#" + strThisID + "_Down").css({
		    "left": intLeft,
		    "top": intTop,
		    "width": intWidth
	    });*/
	    $("#" + strThisID + "_Down").css({
		    "left":0,
		    "top": 2,
		    "width": 200
	    });
	    /*$(".LikeDownListRow").click(function(){
            //alert($(this).val());
	        $("#"+strThisID).val($(this).text());
	        $("#" + strThisID + "_Down").remove();
	    });*/
    });

}
CreateJsGrid.AutoLo = function(GridId) {
    //var width = $("#div").width();
    //var height = $("#div").height();

    $(".JS_Grid_Head_Row_Cell").each(function(index) {
        $(".JS_Grid_Data_Row").each(function(index) { 
            
            });
    });
}