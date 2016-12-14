
function InsertNewTaskIco(strFatherID,strTaskID,strSuitIco,strTaskStatus,strSuitName,strTaskName,strTaskData)
{
    $("#" + strTaskID).remove();
    var strInsertHtml = "";
    strInsertHtml += "<div id='"+strTaskID+"' class='Task_Ico_Div'>";
    strInsertHtml += "    <img src='../images/suit/"+strSuitIco+".ico' style='width:60px;' />";
    strInsertHtml += "    <div title='"+strSuitName+"' style='margin-top:6px'> ";
    strInsertHtml += "        <img src='../images/taskstaut/"+strTaskStatus+".png' style='float:right; margin-right:10px' />";
    strInsertHtml += "        "+strSuitName+"";
    strInsertHtml += "    </div>";
    strInsertHtml += "    <div title='"+strTaskName+"'>"+strTaskName+"</div>";
    strInsertHtml += "    <div title='"+strTaskData+"'>";
    strInsertHtml += "        <img src='../images/taskstaut/progress.png' style='float:right; margin-right:10px; margin-top:4px;' />";
    strInsertHtml += "       "+strTaskData+"";
    strInsertHtml += "    </div>";
    strInsertHtml += "</div>";
    $("#"+strFatherID).append(strInsertHtml);
}
function InsertSuitIco(strFatherID,strSuitID,strSuitIco,strSuitName)
{
    $("#" + strSuitID).remove();
    var strInsertHtml = "";
    strInsertHtml +="<div id='"+strSuitID+"' class='Suit_Ico_Select_Div' SuitType='Select'>";
    strInsertHtml +="    <img src='../images/suit/"+strSuitIco+".ico' style='width:60px;' />";
    strInsertHtml +="    <div>"+strSuitName+"</div>";
    strInsertHtml +="    <input id='"+strSuitID+"_Checkbox' type='checkbox' style='border-style: none' />";
    strInsertHtml +="</div>";
    $("#"+strFatherID).append(strInsertHtml);
    
    //阻止冒泡
    $("#"+strSuitID).click(function(event) {
        event.stopPropagation();
    });
    $("#"+strSuitID).css("marginLeft",100);
    $("#"+strSuitID).animate({"marginLeft":10},"slow");
}
 