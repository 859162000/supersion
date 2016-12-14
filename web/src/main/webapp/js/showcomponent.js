function getGridDataIndex(gridid)
{
	var gridIdPrefix="Grid_Task";
	var dataIndex=parseInt(gridid.substr(gridIdPrefix.length));
	return dataIndex;
}
function insertRow(gridid,dtoName){
	var line = 0;
	var isThCounts = 0;
	var dataIndex=getGridDataIndex(gridid);
	var curhead=headName[dataIndex-1];
	var trCount = 0;
	var widthArr = new Array();// 记录各个表头TD宽度的数组
	$("#"+gridid+" tr").first().children().each(function(){
		isThCounts ++;
		widthArr.push($(this).width());
	});
	
	// 获取每个明细对应的table中表头中的链接字段的数目
	var linkedCount = parseInt($("#"+gridid+"_linkedCount").val())+2;
	isThCounts -= linkedCount;
	
	trCount = $("#"+gridid+" tr").length;
	if(trCount > 1){
		line = trCount - 1;
	}
	var idName = "serviceParam.makeNew["+(line+1)+"]."+$("#"+gridid+" tr:eq(1)").children("td:eq(0)").children("input:eq(1)").val();
	
	var isThCount = "";
	
	isThCount += "<tr name='insert-canNotEdit' class='JS_Grid_Data_Row' style='font-size: 12px; font-family: 微软雅黑;' Rows_Type='DataRow'>"+
		"<td id='chckeboxList' class='JS_Grid_Data_Row_Cell' style='overflow:visible;'>"+
		"<input name='idList' class='Grid_CheckBox' type='checkbox'></input>"+
		"<input name='' class='dataId' type='hidden' value='' /></td>"+
		"<td id='chckeboxList' class='JS_Grid_Data_Row_Cell'>"+$("#"+gridid+" tr").length+"</td>"
	var linkedCount = parseInt($("#"+gridid+"_linkedCount").val());
	for(var i = 0; i < linkedCount; i++){
		isThCount += "<td></td>";
	}
	
	var readOnly = "";//是否是只读属性
	for(var i=0;i<isThCounts;i++){
	    var f=curhead[i];
	    readOnly = f.readOnly;
	    if(!readOnly){
		    var name=f.postFieldName;
		   	var splitIndex=name.indexOf(".");
		    var f1=name.substring(0,splitIndex);
		    var f2=name.substring(splitIndex);
		    var newName="serviceParam."+f1+".makeNew["+line+"]"+f2;
		    //var newName="serviceParam."+f1+f2;
		    if(f.singleTag=="selectField")
		    {
		        var select="<td style='width:"+widthArr[i+linkedCount]+"px;' align='center'><select name='"+newName+"' style='width:90%'>";
		        var tag=f.tag;
				select += "<option value=''>---请选择---</option>";
		        
		        for(var kv in tag)
		        {
		        select+="<option value='"+kv+"'>"+tag[kv]+"</option>";
		        }
				select+="</select></td>";
				isThCount+=select;
		    }
		    else if(f.singleTag=="dateField")
		    {
		    	isThCount+= "<td style='"+widthArr[i+linkedCount]+"px;' align='center'><input type='text' name='"+newName+"' style='width:90%;' onfocus='WdatePicker({doubleCalendar:true,dateFmt:\"yyyy-MM-dd\"})' /></td>";
		    }
		    else if(f.singleTag=="dateFieldMoth")
		    {
		    	isThCount+= "<td style='width:"+widthArr[i+linkedCount]+"px;' align='center'><input type='text' name='"+newName+"' style='width:90%;' onfocus='WdatePicker({doubleCalendar:true,dateFmt:\"yyyyMM\"})' /></td>";
		    }
		    else if(f.singleTag=="dateFieldNoSlash")
		    {
		    	isThCount+= "<td style='width:"+widthArr[i+linkedCount]+"px;' align='center'><input type='text' name='"+newName+"' style='width:90%;' onfocus='WdatePicker({doubleCalendar:true,dateFmt:\"yyyyMMdd\"})' /></td>";
		    }
		    else if(f.singleTag=="dateFieldHMS")
		    {
		    	isThCount+= "<td style='width:"+widthArr[i+linkedCount]+"px;' align='center'><input type='text' name='"+newName+"' style='width:90%;' onfocus='WdatePicker({doubleCalendar:true,dateFmt:\"yyyy-MM-dd H:m:s\"})' /></td>";
		    }
		    else if(f.singleTag=="modelField")
		    {
		    	isThCount+= "<td style='width:"+widthArr[i+linkedCount]+"px;' align='center'><input type='text' readOnly='true' name='"+newName+"' style='width:60%;' /><input style='width:20%;' type='button' onclick=\"showListModelWindow('"+newName+"','"+f.tag+"')\" url='<%=basePath%>"+f.tag+"' value='...' /></td>";
		    }
		    else
		    {
		    	isThCount+= "<td style='width:"+widthArr[i+linkedCount]+"px;' align='center'><input type='text' name='"+newName+"' style='width:90%;'/></td>";
		    }
	    }else{
	    	isThCount+= "<td style='width:"+widthArr[i+linkedCount]+"px;' align='center'></td>";
	    }
			
	}
	isThCount += "</tr>"
	$("#"+gridid).append(isThCount);
		
	// 当前table所在的panel的垂直滚动条定位到底部
	var scrollHeight = $("#"+gridid).height() - $("#"+gridid).parent().height();
	$("#"+gridid).parent().scrollTop(scrollHeight);
	
	line = 0;
}
function edit(gridid,dtoName){
	var checkedCount = 0;// 选中的数据量
	var dataIndex=getGridDataIndex(gridid);
	var curhead=headName[dataIndex-1];
	var headIndex = 0;// 当前标题字段位置
	var nodeContent = "";// 需要拼接的节点内容
	var lineNo = 0;// 当前的行号
	checkedCount = $("#"+gridid+" tr[name='canEdit'] #chckeboxList input:checked[name='idList']").length;
	
	if(checkedCount == 0){
		alert("编辑失败，原因如下：\r\n未选中数据行\r\n当前选中行中存在新增行\r\n当前选中行中有处于编辑状态的行");
		return;
	}else{
		var f="";
		var name="";
		var splitIndex="";
		var f1="";
		var f2="";
		var newName="";
		var tag = "";
		var readOnly = "";//是否是只读属性
		var currVal = "";//下拉列表选中的值
		var idName = "";
		var idVal = "";
		var idFieldNameFlag = "false";
		var idFieldValFlag = "false";
		var splitIndex = 0;
		
		// 获取每个明细对应的table中表头中的链接字段的数目
		var linkedCount = parseInt($("#"+gridid+"_linkedCount").val())+2;
		var tdIndex = linkedCount;
		
		$("#"+gridid+" tr:gt(0)").each(function(){
			if($(this).attr("name") == "canEdit"){// 判断当前行是否可以编辑
				$(this).children().slice(tdIndex).each(function(){// 遍历每一个TD
					
					if($(this).parent().children().first().children().first().attr("name") == "idList" &&
						$(this).parent().children().first().children().first().is(':checked')){
						lineNo = $.trim($(this).parent().children("td:eq(1)").text());// 行号
						
						if(idFieldValFlag == "false"){
							idName = $("#"+gridid+" tr:eq("+lineNo+")").children("td:eq(0)").children("input:eq(1)").attr("value");
							idVal = $("#"+gridid+" tr:eq("+lineNo+")").children("td:eq(0)").children("input:eq(2)").attr("value");
							$("#"+gridid+" tr:eq("+lineNo+")").children("td:eq(0)").children("input:eq(1)").attr("value",idVal);
							idFieldValFlag = "true"
						}
						
						if(idFieldNameFlag == "false"){
							splitIndex = idName.indexOf(".");
							idName = "serviceParam."+idName.substring(0,splitIndex).substring(0,idName.substring(0,splitIndex).indexOf("["))+".makeNew["+(lineNo-1)+"]"+idName.substring(splitIndex);
							$("#"+gridid+" tr:eq("+lineNo+")").children("td:eq(0)").children("input:eq(1)").attr("name",idName);
							idFieldNameFlag = "true"
						}
						
						f=curhead[headIndex];
						readOnly = f.readOnly;
						if(!readOnly){
						    name=f.postFieldName;
						    splitIndex=name.indexOf(".");
						    f1=name.substring(0,splitIndex);
						  	f2=name.substring(splitIndex);
						    newName="serviceParam."+f1+".makeNew["+(lineNo-1)+"]"+f2;
						    
						    currVal = $.trim($(this).text());
						    
						    if(f.singleTag=="selectField")
						    {	
						    	currVal = $.trim($(this).children().first().val());
						    	nodeContent = "<select name='"+newName+"' style='width:90%;'>";
						        tag=f.tag;
						        nodeContent += "<option value=''>---请选择---</option>";
						        for(var kv in tag)
						        {
						        	if(kv == currVal){
						        		nodeContent+="<option value='"+kv+"' selected='selected'>"+tag[kv]+"</option>";
						        	}else{
						        		nodeContent+="<option value='"+kv+"'>"+tag[kv]+"</option>";
						        	}
						        }
								nodeContent+="</select>";
						    }
						    else if(f.singleTag=="dateField")
						    {
						    	nodeContent = "<input type='text' name='"+newName+"' value='"+currVal+"' style='width:90%;' onfocus='WdatePicker({doubleCalendar:true,dateFmt:\"yyyy-MM-dd\"})'/>";
						    }
						    else if(f.singleTag=="dateFieldHMS")
						    {
						    	nodeContent = "<input type='text' name='"+newName+"' value='"+currVal+"' style='width:90%;' onfocus='WdatePicker({doubleCalendar:true,dateFmt:\"yyyy-MM-dd H:m:s\"})'/>";
						    }
						    else if(f.singleTag=="dateFieldMonth")
						    {
						    	nodeContent = "<input type='text' name='"+newName+"' value='"+currVal+"' style='width:90%;' onfocus='WdatePicker({doubleCalendar:true,dateFmt:\"yyyyMM\"})'/>";
						    }
						    else if(f.singleTag=="dateFieldNoSlash")
						    {
						    	nodeContent = "<input name='"+newName+"' value='"+currVal+"' style='width:90%;' onfocus='WdatePicker({doubleCalendar:true,dateFmt:\"yyyyMMdd\"})'/>";
						    }
						    else if(f.singleTag=="modelField")
						    {	
						    	nodeContent ="<div style='width:90%;'><input type='text' readOnly='true' name='"+newName+"' value='"+currVal+"' style='width:80%;' /><input type='button' style='width:20%;' onclick=\"showListModelWindow('"+newName+"','"+f.tag+"')\" value='...' url='<%=basePath%>"+f.tag+"' /></div>";
						    }
						    else
						    {
						    	nodeContent = "<input type='text' name='"+newName+"' value='"+currVal+"' style='width:90%;'/>";
						    }
						    $(this).text("");
						    $(this).append(nodeContent);
						}
					    
					    headIndex++;
						$(this).parent().attr("name","canNotEdit");
					}// if判断结束
					
					
				});// 遍历每一个TD结束
				
				idFieldValFlag == "false"
				idFieldNameFlag = "false";
				headIndex = 0;
				
			}// 判断当前行是否可以编辑结束
			
		});// 行便利结束
		
	}// 选中数据判断结束
}

function onDetailHeadOpAlertClick(gridid,actionNamespace, actionName,showView,name){
	 var gnl=confirm("确定要"+name+"操作吗?");
     if (gnl==true){
    	var curPageInput=$("#"+gridid+"_CurrentPage");
    	var dataUrlInput=$("#"+gridid+"_PageUrl");
     	if(actionName.indexOf("Show")>-1 && showView == true){
	            if(Now_Mode=="Ico"){
	            	Now_Mode="Table";
	            }else if(Now_Mode == "Table"){
	            	Now_Mode="Ico";
	            }else if(Now_Mode==""){
	            	Now_Mode="Ico";
	            }
         }
     	var url=basePath + actionNamespace + "/" + actionName;
     	if(url ==dataUrlInput.val()){
     		
     		url = url+ "&currentPage=" + curPageInput.val()+"&Now_ModeValue="+Now_Mode;
     	}else{
     		url =url+ "&Now_ModeValue="+Now_Mode;
     	}
     	var id=$("#id").val();
    	var type=$("#type").val();
    	var form=$("<form id='formdetail'> </form>");
    	form.append("<input type='hidden' name='id' value='"+id+"'/>")
    	form.append("<input type='hidden' name='type' value='"+type+"'/>")
    	$("#"+gridid+" tr[name='canNotEdit'] #chckeboxList input:checked[name='idList']").each(function(){
		form.append("<input name='idList' value='"+$(this).val()+"'>");
		});
		$("#"+gridid+" tr[name='canEdit'] #chckeboxList input:checked[name='idList']").each(function(){
			form.append("<input name='idList' value='"+$(this).val()+"'>");
		});
    	form.attr("action",url);
    	form.attr("method","post");
    	form.appendTo("body").submit().remove();
     }else{ 
     	return;  
     }  
}



/*
function showListModelWindow(name,url){
	url= "<%=basePath%>"+url;
	if(url.indexOf("zxptsystem.dto.EIS_AuthorizationDocumentInfo")>0){
		if(document.getElementById("serviceParam.strCustomerID.strCustomerID").value==""){
			alert("请选择授权客户名称！");
			return;
		}
		var type="";
		type=window.location.href.split(".action")[0].split("-")[1];
		
		url+="&lastType="+type+"&strC="+document.getElementById("serviceParam.strCustomerID.strCustomerID").value;
	}
	var retValue = window.showModalDialog(url,'',"dialogWidth=800px;dialogHeight=500px");
	if(typeof(retValue) != "undefined"){
		$.each(retValue,function(i,item){
			var kv= item.split("=");
			if(kv[1].substring(kv[1].length-1,kv[1].length)=="-"){
				$("input[name='"+name+"']").val(kv[1].substring(0,kv[1].length-1));
			}else{
				$("input[name='"+name+"']").val(kv[1]);
			}
		});
	}
}*/

function selectAll(currGridid, hostGridid){
	if($("#"+currGridid+" tr:eq(0) td:eq(0) input[id='Grid_Task_Check'][type='checkbox']").is(":checked")){
		$("#"+hostGridid+" tr:gt(0) td input[type='checkbox']").each(function(){
			$(this).attr('checked',true);
		});
	}else{
		$("#"+hostGridid+" tr:gt(0) td input[type='checkbox']").each(function(){
			$(this).attr('checked',false);
		});
	}
}

function onMasterOpClick(actionNamespace,actionName){
	var form=$("#form1");
	var data=form.serialize();
	var url=basePath + actionNamespace + "/" + actionName + ".action";
	$.post(url,data,function(data,status){
		//alert("Data: " + data + "\nStatus: " + status);
		if(data.success==true)
		{
			alert("操作成功");
			if(typeof(data.forwardAction)!="undefined")
			{
				form.attr("action",data.forwardAction);
				//form.attr("method","get");
				form.empty();
				form.submit();
			}
		}
		else
		{
			if(typeof(data.message)!="undefined")
			{
				closeMessager();
				var msg = data.message.replace(/\\r\\n/g, '<br/>');
				makeCheckMeassge(msg);
			}
			else
			{
				closeMessager();
				makeCheckMeassge("系统异常");
			}
		}
	});
	
}

/**
 * 展示校验结果（失败）的信息
 * @param msg 校验结果信息
 */
function makeCheckMeassge(msg){
	var options = {  
		title: "消息",  
		msg: msg,  
		showType: 'slide', 
		width:400,
		height:300,
		collapsible:true,
		timeout: 0  
	}; 
	$.messager.show(options);
}

function closeMessager(){
	 var msgWindow = $("div.window");
	 if(msgWindow.length > 0){
		 msgWindow.remove();
	 }
}

function onDetailHeadOpClick(gridid,actionNamespace,actionName){
	var id=$("#id").val();
	var type=$("#type").val();
	var url=basePath + actionNamespace + "/" + actionName + ".action";
	var form=$("<form id='formdetail'> </form>");
	form.append("<input type='hidden' name='id' value='"+id+"'/>")
	form.append("<input type='hidden' name='type' value='"+type+"'/>")
	$("#"+gridid+" tr[name='canNotEdit'] #chckeboxList input:checked[name='idList']").each(function(){
		form.append("<input name='idList' value='"+$(this).val()+"'>");
	});
	$("#"+gridid+" tr[name='canEdit'] #chckeboxList input:checked[name='idList']").each(function(){
		form.append("<input name='idList' value='"+$(this).val()+"'>");
	});
	form.attr("action",url);
	form.attr("method","post");
	form.appendTo("body").submit().remove();
	//document.form1.submit();
}

function onLinkClick(url){
	if(url.indexOf("?") > -1){
		url += "&windowId=" + document.getElementById('windowId').value; 
	}
	else{
		url += "?windowId=" + document.getElementById('windowId').value; 
	}
	window.location.href=url;
}

function onOpenLinkWindows(url, windowId, index, name){
	if(name==null){
		name=="";
	}
	if(window.name==""){
		new window.parent.ObjectWindows("Main_Body", windowId, 520, 1000, url, name+index, "yes");
	}
	else if(window.parent.name==""){
		new window.parent.parent.ObjectWindows("Main_Body", windowId, 520, 1000, url, name+index, "yes");
	}
	else if(window.parent.parent.name==""){
		new window.parent.parent.parent.ObjectWindows("Main_Body", windowId, 520, 1000, url, name+index, "yes");
	}
}

function onOpenModelWindows(url, windowId, name){
	windowId = windowId.replace(/-/g, "").replace(/\./g, "");
	//目前不支持查询条件，暂时屏蔽url = getCondition(url);
	window.showModalDialog(url, "", "dialogWidth=700px;dialogHeight=500px;");
}

function onOpenCommonWindows(url, windowId, name){
	windowId = windowId.replace(/-/g, "").replace(/\./g, "");
	//目前不支持查询条件，暂时屏蔽url = getCondition(url);
	onOpenLinkWindows(url, windowId, "", name);
	showOrHideModel(windowId, name);
}

function onOpenNewWindows(url){
	//目前不支持查询条件，暂时屏蔽url = getCondition(url);
	window.open(url);
}

function getCondition(url){

	var from = $("#form1")[0];
	
    for ( var i = 0; i < from.elements.length; i++) {
    	if(from.elements(i).name != null && from.elements(i).name.indexOf("serviceParam")>-1 && from.elements(i).value != null && from.elements(i).value!=""){
        	if(url.indexOf("?")<0){
        		url += "?";
        	}else{
        		url += "&";
        	}
    		url = url+from.elements(i).name+"="+from.elements(i).value;
    	}
	}
	
	return url;
}

function onOperationClickBtn(actionNamespace,actionName,id){
		document.form1.action = basePath + actionNamespace + "/" + actionName + ".action?id=" + id;
		document.form1.submit();
}



function showListDivWidthBig(strss,acionStr,val0,val1,window1){
	window.location.href = strss + "/" + acionStr+".action?id="+val0+"&type="+val1+"&windowId="+window1;
}
function skip(gridid,namespace,actionname,object){
	 var num =  $(object).prev(":text").val();
    if(isNaN(num) || num.indexOf(".")>-1 || num.indexOf("-")>-1 || num == 0){
        alert("数字非法");
        return false;
    }
   
    var countString = $(object).parent().siblings("div").eq(1).html();
    var count = parseInt(countString.substring(countString.indexOf(":")+1));
    if(num > count){
        alert("超出总共页数");
        return false;
    }
    onPage(gridid,namespace,actionname,num);
   
}
function onPage(gridid,actionNamespace,actionName,page){
    var currentPage=1;
	if(page != null){
		currentPage = page;
	}
	var url=basePath+ actionNamespace + "/" + actionName + "&currentPage=" + currentPage;
	$.get(url,function(data,status){
		//alert("Data: " + data + "\nStatus: " + status);
		createGrid(gridid,data);
	});
	
	
}
function refreshGrid(gridid)
{
	var curPageInput=$("#"+gridid+"_CurrentPage");
	var dataUrlInput=$("#"+gridid+"_PageUrl");
	var url=basePath+dataUrlInput.val()+"&currentPage=" + curPageInput.val()+"&"+new Date();
	$.get(url,function(data,status){
		//alert("Data: " + data + "\nStatus: " + status);
		createGrid(gridid,data);
	});
}

function createGrid(gridid,data)
{
	if(typeof(data.showPage)!="undefined")
	{
		var grid=$("#"+gridid);
		grid.find("tr").filter("[rows_type='DataRow']").remove();
		var rowNum=1;
		createPage(gridid,data)
		var vt=data.valueTable;
		
		for(row in vt)
		{
			createRow(grid,vt[row],data.linkedList[row],rowNum,data.showPage.currentPage);
			rowNum=rowNum+1;
		}
	}
	else
	{
		alert("加载数据错误");
	}
	
	
}
function createPage(gridid,griddata)
{
	var pagediv=$("#"+gridid+"_Page");
	var curPageInput=$("#"+gridid+"_CurrentPage");
	curPageInput.val(griddata.showPage.currentPage);
	pagediv.find("div").remove();
	if(griddata.showPage.showPage)
	{
		pagediv.append("<div>总条数："+griddata.showPage.totalCount+"</div>");
		pagediv.append("<div>总共页："+griddata.showPage.totalPage+"</div>");
		pagediv.append("<div>当前页："+griddata.showPage.currentPage+"</div>");
		pagediv.append("<div>每页条数："+griddata.showPage.pageSize+"</div>");
		pagediv.append("<div onclick=\"javascript:onPage('"+gridid+"','"+griddata.namespace+"','"+griddata.selectActionName+"',1)\">首页</div>");
		if(griddata.showPage.showPrePage)
		{
			pagediv.append("<div onclick=\"javascript:onPage('"+gridid+"','"+griddata.namespace+"','"+griddata.selectActionName+"',"+(griddata.showPage.currentPage-1)+")\">上一页</div>");
		}
		if(griddata.showPage.showNextPage)
		{
			pagediv.append("<div onclick=\"javascript:onPage('"+gridid+"','"+griddata.namespace+"','"+griddata.selectActionName+"',"+(griddata.showPage.currentPage+1)+")\">下一页</div>");
		}
		pagediv.append("<div onclick=\"javascript:onPage('"+gridid+"','"+griddata.namespace+"','"+griddata.selectActionName+"',"+griddata.showPage.totalPage+")\">尾页</div>");
		pagediv.append("<div> <input type='text' style='width: 28px;' /> <a onclick=\"javascript:skip('"+gridid+"','"+griddata.namespace+"','"+griddata.selectActionName+"',this)\">跳转</a></div>");
		
	}
	else
	{
		pagediv.append("<div>总共页：1</div>");
		pagediv.append("<div>当前页：1</div>");
		
	}
	
}
function createLinkedCol(grid,tr,coldata,rowdata,currentPage)
{
	var td=$("<td title='"+coldata.operationName+"' class='JS_Grid_Data_Row_CellLink' columncode='ColA'></td>");
	var width="100px";
	if(coldata.width!=null && coldata.width!="")
	{
		width=coldata.width+"px";
	}
	td.css("width",width);
	createLinkButton(grid,tr, td, coldata, rowdata,currentPage)
}

function createLinkButton(grid,tr, td, coldata, rowdata,currentPage){
	var aTag = "";
	var aTagContent = "";
	var linkDiv = "";
	var windowId = $("#windowId").val();
	
	var gridId = $(grid).attr("id");
	
	if(coldata.imageUrl=="" || coldata.imageUrl==null)
	{
		aTagContent=coldata.operationName;
	}
	else
	{
		aTagContent="<img src='"+coldata.imageUrl+"'/>";
	}
	
	if(coldata.operationType == "Upload"){
		linkDiv = "<div title='"+coldata.operationName+"'>";
		aTag =linkDiv+ "<a href=\"javascript:onUpload('"+gridId+"','"+coldata.operationNamespace+"','"+coldata.operationAction+"','"+rowdata[0].value+"');\" >";
		aTag = aTag + aTagContent;
		aTag = aTag + "</a>";
		aTag =  aTag + "</div>";
		
	}
	else if(coldata.operationType == "TypeHidden"){
		aTag = "<a href='"+coldata.operationNamespace+"/"+coldata.operationAction+".action?id="+rowdata[0].value+"&type="+rowdata[1].value+"&windowId="+windowId+"'" +
				"cssStyle='display:inline-block;color:"+coldata.color+";'>";
		aTag = aTag + aTagContent;
		aTag = aTag + "</a>";
	
	}
	else if(coldata.operationType == "Pop"){
		aTag = "<a href='"+basePath+coldata.operationNamespace+"/"+coldata.operationAction+".action?id="+rowdata[0].value+"&type="+rowdata[1].value+"&windowId="+windowId+"'" +
			"cssStyle='display:inline-block;color:"+coldata.color+";' target='_blank'>";
		
		aTag = aTag + aTagContent;
		aTag = aTag + "</a>";
		
	}
	else if(coldata.operationType == "Post"){
		aTag = "<a href=\"javascript:onOpForDetail('"+gridId+"','"+coldata.operationNamespace+"','"+coldata.operationAction+"','"
		+rowdata[0].value+"','"+rowdata[1].value+"','"+windowId+"');\" style='color:"+coldata.color+"'>";
		aTag = aTag + aTagContent;
		
		
	}
	else if(coldata.operationType == "GetConfirm"){
//		linkDiv = "<div title='"+coldata.operationName+"' class='JS_Grid_Data_Row_CellLink' columncode='ColA'></div>"
		aTag = "<a href=\"javascript:ajaxAlertOpForDetail('"+gridId+"','"+coldata.operationNamespace+"','"+coldata.operationAction+"','"
			+rowdata[0].value+"','"+rowdata[1].value+"','"+windowId+"');\" style='color:"+coldata.color+"'>";
		aTag = aTag + aTagContent;
		aTag = aTag + "<a>";
		
	}
	else if(coldata.operationType == "DivWidthBig"){
		aTag = "<div onclick=\"javascript:showListDivWidthBig('"+coldata.operationNamespace+"','"+coldata.operationAction+"','"+rowdata[0].value+"','"+rowdata[1].value+"','"+windowId+"');\" " +
				"style=\"width: 75px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; color: #0000FF; text-decoration: underline; display: inline-block;\">"+aTagContent+"</div>";
		
	}
	else if(coldata.operationType == "OpenWin"){
		var lineIndex = parseInt($("#"+gridId+" tr:gt(0)").length)+1;
		aTag = "<a href=\"javascript:onOpenLinkWindows('"+basePath+coldata.operationNamespace+"/"+coldata.operationAction+".action?id="+rowdata[0].value
			+"&type="+rowdata[1].value+"','"+currentPage+rowdata[0].value+"','_"+lineIndex+"','"+coldata.operationName+"');\">";
		aTag = aTag + aTagContent;
		aTag = aTag + "</a>";
		
	}else{
		//"javascript:ajaxOpForDetail('Grid_Task<s:property value="#sll.index+1" />','<s:property value='%{#ll.operationNamespace}'/>','<s:property value='%{#ll.operationAction}'/>','<s:property value='%{#vt.get(0).value}'/>','<s:property value='%{#vt.get(1).value}'/>','<s:property value='%{#request.windowId}'/>')"
		aTag = "<a href=\"javascript:ajaxOpForDetail('"+gridId+"','"+coldata.operationNamespace+"','"+coldata.operationAction+"','"+rowdata[0].value+"','"+rowdata[1].value+"','"+windowId+"');\">";
		aTag = aTag + aTagContent;
		aTag = aTag + "</a>";
		
	}
	td.append(aTag);
	tr.append(td);
}


function createRow(grid,rowdata,linkedOps,rowNum,currentPage)
{
	
	var tr=$("<tr name='canEdit' class='JS_Grid_Data_Row'" +
			" style='font-size: 12px; font-family: 微软雅黑;'"+
			"Rows_Type='DataRow'></tr>");
	tr.append("<td id='chckeboxList' class='JS_Grid_Data_Row_Cell' style='width:35px;'>"+
			"<input name='idList' class='Grid_CheckBox' type='checkbox' value='"+rowdata[0].value+"'/>" +
		    "<input name='' type='hidden' id='id"+rowNum+"' value='"+rowdata[0].postFieldName+"' />"+
		    "<input name='' class='dataId' type='hidden' id='id' value='"+rowdata[0].value+"' /></td>");
	tr.append("<td id='chckeboxList' style='width:20px;' class='JS_Grid_Data_Row_Cell'>"+rowNum+"</td>");
	
	
	var linkHeadCount = parseInt($("#"+grid.attr("id")+"_linkedCount").val());
	if(linkHeadCount > 0 && linkedOps.length > 0 && linkHeadCount == linkedOps.length){
		for(col in linkedOps)
		{	
			createLinkedCol(grid,tr,linkedOps[col],rowdata,currentPage)
		}
	}
	
	
	var colNum=2;
	var colLen=rowdata.length;
	for(;colNum<colLen;)
	{
		createDataCol(tr,rowdata[colNum],rowNum,colNum);
		colNum=colNum+1;
	}
	grid.append(tr);
}
function createDataCol(tr,coldata,rowNum,colNum)
{
	//style="width: <s:property value="(width==null || width=='')?100:(width+20)"/>px;"
	var width="100px";
	if(coldata.width!=null && coldata.width=="")
	{
		width=coldata.width+"px";
	}
	tr.append("<td title='"+coldata.value+"' " +
				"class='JS_Grid_Data_Row_Cell' columncode='Col2' " +
				"titleField='"+coldata.titleField +"' "+
				"splitField='"+coldata.splitField +"' "+
				"style='width:"+width+";'"+
				"><div class='TD_Div_Nowrap'>"+(coldata.value==null?"":coldata.value)+"&nbsp</div></td>"
				);
	
}
function eidtStatusCheck(gridid)
{
	var inEditing=false;

	if($("#"+gridid+" tr[name='canNotEdit'] #chckeboxList input:checked[name='idList']").length > 0){
		inEditing=true;
		
	}
	else if($("#"+gridid+" tr[name='insert-canNotEdit'] #chckeboxList input:checked[name='idList']").length > 0){
		inEditing=true;
		/*if(confirm("提示\r\n当前选中的数据中存在新增的数据行\r\n是否"+name+"当前选中的新增数据行")){
			$("#Grid_Task"+index+" tr[name='insert-canNotEdit'] #chckeboxList input:checked[name='idList']").each(function(){
				$(this).parents("tr").remove();
			});
		}*/
	}
	return inEditing;
}
function ajaxOp(gridid,actionNamespace,actionName,opName)
{
	if(eidtStatusCheck(gridid))
	{
		var r=confirm("存在新增或编辑状态的数据,是否进行该操作");
		if(r==false)
		{
			return;
		}
		else
		{
			//todo：删除新增及编辑数据	
		}
	}
	
	// 获取各个状态下选中的数据的数目
	// 新增状态的数据数目
	var insertDataCount = $("#"+gridid+" tr[name='insert-canNotEdit'] #chckeboxList input:checked[name='idList']").length;
	// 编辑状态的数据数目
	var editDataCount = $("#"+gridid+" tr[name='canNotEdit'] #chckeboxList input:checked[name='idList']").length;
	// 初始化状态（即：原本就有）的数据数目
	var initDataCount = $("#"+gridid+" tr[name='canEdit'] #chckeboxList input:checked[name='idList']").length;
	
	// 判断是否只是选中了新增的数据
	if( insertDataCount > 0 && editDataCount == 0 && initDataCount == 0 ){
		$("#"+gridid+" tr[name='insert-canNotEdit'] #chckeboxList input:checked[name='idList']").each(function(){
			$(this).parent().parent().remove();
		});
	}else{
		url=basePath+actionNamespace+"/" + actionName +".action";
		var form=$("<form method='post'></form>");
		form.attr("action",url);
		$("#"+gridid+" tr[name='canNotEdit'] #chckeboxList input:checked[name='idList']").each(function(){
			form.append("<input name='idList' value='"+$(this).val()+"'>");
		});
		$("#"+gridid+" tr[name='canEdit'] #chckeboxList input:checked[name='idList']").each(function(){
			form.append("<input name='idList' value='"+$(this).val()+"'>");
		});
		var id=$("#id").val();
		var type=$("#type").val();
		form.append("<input type='hidden' name='id' value='"+id+"'/>")
		form.append("<input type='hidden' name='type' value='"+type+"'/>")
		var data=form.serialize();
		$.post(url,data,function(data,status){
			//alert("Data: " + data + "\nStatus: " + status);
			if(data.success==true)
			{
				alert("操作成功");
			}
			else
			{
				alert(data.message);
			}
			refreshGrid(gridid);
		});
	}
	
	
	
}
function onOpForDetail(gridid,namespace,actionname,id,type,windowid)
{
	var url=basePath+namespace+"/"+actionname+".action";
	var form=$("<form name='detailget' target='_self' method='get'> </form>");
	form.append("<input name='id' type='hidden' value='"+id+"'/>");
	form.append("<input name='type' type='hidden' value='"+type+"'/>");
	form.append("<input name='windowId' type='hidden' value='"+windowid+"'/>");
	form.attr("action",url);
	form.appendTo("body").submit();
	
	
}
function ajaxOpForDetail(gridid,namespace,actionname,id,type,windowid)
{
	var url=basePath+namespace+"/"+actionname+".action?id="+id+"&type="+type+"&windowId="+windowid;
	$.get(url,function(data,status){
		//alert("Data: " + data + "\nStatus: " + status);
		if(data.success==true)
		{
			alert("操作成功");
		}
		else
		{
			alert(data.message);
		}
		refreshGrid(gridid);
	});
}
function ajaxAlertOpForDetail(gridid,namespace,actionname,id,type,windowid,opName)
{
	var r=confirm("是否要进行"+opName+"操作");
	if(r)
	{
		ajaxOpForDetail(gridid,url);
	}
}
function ajaxAlertOp(gridid,actionNamespace,actionName,opName)
{
	var r=confirm("是否要进行"+opName+"操作");
	if(r)
	{
		ajaxOp(gridid,actionNamespace,actionName,opName);
	}
}

function onUpload(gridid,namespace,actionName,dataId) {
	// 上传方法
	var id=$("#id").val();
	var type=$("#type").val();
	var url=basePath+namespace+"/"+actionName+".action";
	if(dataId!=null && dataId!="" && typeof(dataId)!="undefined")
	{
		url=url+"?id="+dataId;
	}
	$.upload({
			// 上传地址
			url: url, 
			// 文件域名字
			fileName: 'uploadFile', 
			// 其他表单数据
			params: {id: id,type:type},
			// 上传完成后, 返回json, text
			dataType: 'json',
			// 上传之前回调,return true表示可继续上传
			onSend: function() {
					return true;
			},
			// 上传之后回调
			onComplate: function(data) {
				if(typeof(data.message)!="undefined" && data.message!=""&&data.message!=null)
				{
					alert(data.message)
				}
				
				refreshGrid(gridid);
				
				
			}
	});
	
}

/**
 * table的内容滚动时，固定table的头部信息
 */
function scrollGrid_Table(){
	$("table").each(function(){
		$(this).parent().scroll(function(){
			if($(this).children().first().attr("id").indexOf("_head") < 0){
				$("#"+$(this).children().first().attr("id")+"_head").parent().scrollLeft($(this).scrollLeft());
				$(this).scrollLeft($("#"+$(this).children().first().attr("id")+"_head").parent().scrollLeft());
			}
		});
	});
}

function changeOptionsTitle(name){ 
 	var options = document.getElementById(name).options;
    for (var i=0;i<options.length;i++){ 
        var text = options[i].text;
        options[i].title = text;
    }
}

function AutiSize()
{
	$("#DataDiv").height($(window).height()-30-$("#ShowErrMes").height());
}