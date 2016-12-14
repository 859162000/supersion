function CreateReportQuickBar(strWindowsID,strWindowsName)
{
    //设置自身属性
    this.strWindowsID = strWindowsID;
    this.strWindowsName = strWindowsName;
    this.ReportQuickBarID = this.strWindowsID + "_QucikBar";
    var WindowHeight = $(window).height() - 40;
    
    //如果当前页面中存在这个元素,那么直接范围
    if ($("#" + this.ReportQuickBarID).length > 0)
        return;
        
    //拼接快捷菜单
    var strInsertHtml = "";
    strInsertHtml +=" <div id='"+this.ReportQuickBarID+"' class='ReportQuickBarDiv' ReportBar='yes' ReportID='"+this.strWindowsID+"' onselectstart='return false;' title='"+strWindowsName+"'>";
    strInsertHtml += strWindowsName;
    strInsertHtml +=" </div>";
    
    //插入快捷菜单
    $("body").append(strInsertHtml);
    
    //设置菜单的起始高度
    $("#" + this.ReportQuickBarID).css("top",WindowHeight)
    
    //绑定菜单相关事件
    this.BindEvent();
    
    //从新动态布局
    CreateReportQuickBar.AutoLayout();
    return this;

}
//动态布局,此函数为静态函数
CreateReportQuickBar.AutoLayout = function()
{
    //获取窗口高度-任务栏高度
    var WindowHeight = $(window).height() - 40;
    
    //判断有几个快捷菜单
    var intReportBars = $("[ReportBar='yes']").length;
    var AllHeight = intReportBars * (30 + 5);
    
    //计算第一个菜单的高度
    var FirstBarTop = WindowHeight/4;// (WindowHeight -  AllHeight)/2;
	$("[ReportBar='yes']").each(function(){
        
        //动态设置高度
        $(this).animate({"top":FirstBarTop},"slow");
        FirstBarTop = FirstBarTop + 35;
	});
}
//绑定菜单事件
CreateReportQuickBar.prototype.BindEvent = function()
{
    var strWindowsID_Tmp = this.strWindowsID;
    
    //当鼠标在菜单上方
    $("#" + this.ReportQuickBarID).mouseover(function(){
        $(this).animate({"right":0},"slow");
        $(this).css("zIndex",10000);
    });
    
    //当鼠标离开菜单上方
    $("#" + this.ReportQuickBarID).mouseleave(function(){
        var ReportID = $(this).attr("id");
        setTimeout(function ()
        {
            $("#" +ReportID).animate({"right":-100},"slow",function(){
                $(this).css("zIndex",0);
            });
        },1000);
    });
    
    //当鼠标双击
    $("#" + this.ReportQuickBarID).dblclick(function(){
        //关闭窗口
        ObjectWindows.CloseWindows(strWindowsID_Tmp);
    });
    
    //当鼠标单击
    $("#" + this.ReportQuickBarID).click(function(){
        //调用菜单对于窗体对应任务栏的Click事件,这样的写法主要是为了剩代码
        $("#"+strWindowsID_Tmp+"_TaskBar").click();
    });

    //阻止mousedown冒泡
    $("#" + this.ReportQuickBarID).mousedown(function(event){
        event.stopPropagation();
    });
    
}