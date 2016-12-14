$(document).ready(function(){
	
	$('#target').dblclick(function(){  
		bound();

	})
	
	/**
	 * description: 获取默认选中的明细表的字段信息
	 * updateDate: 2015-11-03 AM
	 */
	selectTable($("#tableName")[0]);
	
	    $("#suit").val($("#currSuitValue").val());
	    $("#inputHelper").val("请输入指标代码");
		$("#inputHelper").css("color", "gray");	
		document.all.WebOffice.ShowToolBar = false; // 隐藏weboffice自带工具栏
		$('a[name=initExcel]').click(function(){
			WebOffice_NotifyCtrlReady($("#path").val());
		});
		$('a[name=savedoc]').click(function(){saveFile();});
		$('a[name=saveas]').click(function(){
			document.all.WebOffice.Save();
		});
		$('a[name=bound]').click(function(){bound();});
		$('a[name=unionbound]').click(function(){unionbound();});
		$('a.color1').click(function(){
			$(this).addClass("color").siblings().removeClass("color");
			$(".actionarea").children(":eq("+$(this).index()+")").css("display","block").siblings().css("display","none");
		});
		$('a[name=print]').click(function(){
			document.all.WebOffice.PrintDoc('1');
		});
		$('a[name=open]').click(function(){
			document.all.WebOffice.LoadOriginalFile("open","xls");
			document.getElementById("WebOffice").GetDocumentObject().Application.SendKeys("{F2}");
		});
		$('a[name=close]').click(function(){
			document.alel.WebOffice.CloseDoc(0);
		});	
		$('#btnCheckMapping').click(function(){			
			var calcInstName1=$("#checkInstName").val();
			if(calcInstName1==''){
				alert('请先设置校验实例名！');
				return false;
			}
			
			var xlDoc = document.getElementById("WebOffice").GetDocumentObject();
			var area = xlDoc.Application.Selection;
			var xlSht = xlDoc.ActiveSheet;
			var message = "";
			if (area == null) {
				message = "请选择单元格!";
			}
			
			var item ="";
			var bz ="";
			var sx ="";
			var rq ="";
			var pd ="";
			var jg ="";
			var kz1 ="";
			var kz2 ="";
			var qs ="";
			var kbj ="";
			if(xlSht.Cells(area.Row, area.Column).Comment !=null){
				var comment = xlSht.Cells(area.Row, area.Column).Comment.Text();
				var items = comment.split("\n");
				$.each(items,function(i,itemss){
					var pair = itemss.split(":");
					if(pair.length==2){
						if(pair[0]=="代码"){
							item=pair[1];
						}else if(pair[0]=="币种"){
							bz=pair[1];
						}else if(pair[0]=="属性"){
							sx=pair[1];
						}else if(pair[0]=="日期"){
							rq=pair[1];
						}else if(pair[0]=="频度"){
							pd=pair[1];
						}else if(pair[0]=="机构"){
							jg=pair[1];
						}else if(pair[0]=="扩展1"){
							kz1=pair[1];
						}else if(pair[0]=="扩展2"){
							kz2=pair[1];
						}else if(pair[0]=="期数"){
							qs=pair[1];
						}else if(pair[0]=="可编辑"){
							kbj=pair[1];
						}
					}
				})
			}
			
			if(item==""){
				alert('请先选择指标！');
				return false;
			}
			
			var CalculationRuleID='';
			var u1=$("#basePath").val() + "ajax/GetInstanceIdJsonInfo-report.dto.CheckRule.action?"+new Date();

			$.ajax({
				type:"GET",
	            url:u1,
	            data:"instanceId="+calcInstName1+"&itemCode="+item+"&bz="+bz+"&sx="+sx+"&rq="+rq+"&pd="+pd+"&jg="+jg+"&kz1="+kz1+"&kz2="+kz2+"&qs="+qs+"&kbj="+kbj+"&rid="+new Date(),
	            datatype: "json",
	            success:function(data){
				var url=$("#basePath").val()+'/report/ShowCalcRuleCfg-report.dto.ItemsCheck.action?id='+data+'&type=&windowId=CalculationRule&time=' + new Date();
				window.showModalDialog(url,'校验规则配置',"dialogWidth=1000px;dialogHeight=550px");
	            },
				error:function(data){
	            	alert(data.responseText);
				}
			});
		});	
		$('#btnItemMapping').click(function(){
			var calcInstName1=$("#calcInstName").val();
			if(calcInstName1==''){
				alert('请先设置计算实例名！');
				return false;
			}
			
			var xlDoc = document.getElementById("WebOffice").GetDocumentObject();
			var area = xlDoc.Application.Selection;
			var xlSht = xlDoc.ActiveSheet;
			var message = "";
			if (area == null) {
				message = "请选择单元格!";
			}
			
			var item ="";
			var bz ="";
			var sx ="";
			var rq ="";
			var pd ="";
			var jg ="";
			var kz1 ="";
			var kz2 ="";
			var qs ="";
			var kbj ="";
			if(xlSht.Cells(area.Row, area.Column).Comment !=null){
				var comment = xlSht.Cells(area.Row, area.Column).Comment.Text();
				var items = comment.split("\n");
				$.each(items,function(i,itemss){
					var pair = itemss.split(":");
					if(pair.length==2){
						if(pair[0]=="代码"){
							item=pair[1];
						}else if(pair[0]=="币种"){
							bz=pair[1];
						}else if(pair[0]=="属性"){
							sx=pair[1];
						}else if(pair[0]=="日期"){
							rq=pair[1];
						}else if(pair[0]=="频度"){
							pd=pair[1];
						}else if(pair[0]=="机构"){
							jg=pair[1];
						}else if(pair[0]=="扩展1"){
							kz1=pair[1];
						}else if(pair[0]=="扩展2"){
							kz2=pair[1];
						}else if(pair[0]=="期数"){
							qs=pair[1];
						}else if(pair[0]=="可编辑"){
							kbj=pair[1];
						}
					}
				})
			}			

			if(item==""){
				alert('请先选择指标！');
				return false;
			}
			var CalculationRuleID='';
			var u1=$("#basePath").val() + "ajax/GetInstanceIdJsonInfo-report.dto.CalculationRule.action?"+new Date();

			$.ajax({
				type:"GET",
	            url:u1,
	            data:"instanceId="+calcInstName1+"&itemCode="+item+"&bz="+bz+"&sx="+sx+"&rq="+rq+"&pd="+pd+"&jg="+jg+"&kz1="+kz1+"&kz2="+kz2+"&qs="+qs+"&kbj="+kbj+"&rid="+new Date(),
	            datatype: "json",
	            success:function(data){
				var url=$("#basePath").val()+'/report/ShowCalcRuleCfg-report.dto.ItemsCalculation.action?id='+data+'&type=&windowId=CalculationRule&time=' + new Date();
				window.showModalDialog(url,'计算规则配置',"dialogWidth=1000px;dialogHeight=550px");
	            },
				error:function(data){
	            	alert(data.responseText);
				}
			});
		});	
		
		$("#inputHelper").focus(function(){
			 $("#inputHelper").val("");

	   
		}).blur(function(){
			if($("#inputHelper").val() == ""){
				$("#inputHelper").val("请输入指标代码");
			}
	   
		}).keyup(function(){	
			var str = $(this).val();
			if($.trim(str)!=''&& str !="请输入指标代码"){
				var u=$("#basePath").val() + "ajax/GetJsonInfo-report.dto.ItemInfo.action?"+new Date();
				$.ajax({
					type:"GET",
		            url:u,
		            data:"itemCode="+str+"&suit="+$("#suit").val(),
		            datatype: "json",
		            beforeSend:function(){},
		            success:function(data){
		            	
		            	$("#target").empty();
		            	var dataObj=eval("("+data+")");
		            	var keyArr= new Array();
		            	var dataArr=new Array();
		            	$.each(dataObj,function(i,item){		            		
		            		keyArr.push(i);
		            		dataArr[i]=item;
		            	});
		            	keyArr.sort();
		            	$.each(keyArr,function(index,key){
		            		$("#target").append("<option value='"+key+"'>"+key+"|"+dataArr[key]+"</option>");
		            	});
		            },
					error:function(data){alert(data.responseText);}
				});
			}
		});
		//$(window).beforeunload(function(){
		//	document.all.WebOffice.Close();
		//});
  	});

function WebOffice_NotifyCtrlReady(url){
	if(url!=''){
		url=$("#basePath").val()+url;
	}
	var encodeurl=encodeURI(url);
	
	var flag=document.all.WebOffice.LoadOriginalFile(encodeurl,"xls");
	document.getElementById("WebOffice").GetDocumentObject().Application.SendKeys("{F2}");
	if(flag==0)
	{
		alert("打开模板失败");
	}
}
//////////////////////////////
var fieldArr = new Array();
	function selectTable(AutoTableID){
 	var AutoTableIDValue = ((AutoTableID.value).split(";"))[0];
	var xmlhttp;
	if(window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
		if(xmlhttp.overrideMimeType){
			xmlhttp.overrideMimeType("text/xml");
		}
	}else if(window.ActiveXObject){
		var activexml = ["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];
		for ( var i = 0; i < activexml.length; i++) {
			try{
				var xmlhttp = new ActiveXObject(activexml[i]);
				break;
			}catch(e){
				continue;		
			}
		}	
	}
	var url = "report/Select-showFields.action?AutoTableID="+AutoTableIDValue;
	xmlhttp.open("POST",url,true);
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState == 4){
			if(xmlhttp.status == 200){
				var result = xmlhttp.responseText;
				var fieldCtl = document.getElementById('fieldName');
				fieldCtl.options.length=0;	
				if(result != ""){
					var fieldsByte = new Array();
					var field = new Array();
					fieldsByte = result.split(";");
					height = (fieldsByte.length)*20;
					for ( var i = 0; i < fieldsByte.length-1; i++) {
						field = fieldsByte[i].split(",");
						fieldCtl.options.add(new Option(field[0],field[1]));
						fieldArr.push(field[0]);
					}
				}
			}
		}
	}
	xmlhttp.send();
}
var ItemInfoArr = new Array();
	function selectTarget(suitID){ // 主题变化时，向服务器查询出其指标
    $("#inputHelper").val("");
    $("#target").empty();
	var suitIDValue = ((suitID.value).split(";"))[0];
	var xmlhttp;
	
	if(window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
		if(xmlhttp.overrideMimeType){
			xmlhttp.overrideMimeType("text/xml");
		}
	}
	else if(window.ActiveXObject){
		var activexml = ["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];
		for ( var i = 0; i < activexml.length; i++) {
			try{
				var xmlhttp = new ActiveXObject(activexml[i]);
				break;
			}catch(e){
				continue;		
			}
		}	
	}
	
	var url = "report/Select-showFields.action?SuitID="+suitIDValue + "&TmpName=" + $("#TmpName").val();
	xmlhttp.open("POST",url,true); // 请求
	xmlhttp.onreadystatechange = function() { // 响应
		if(xmlhttp.readyState == 4) {
			if(xmlhttp.status == 200) {
				var result = xmlhttp.responseText;
				var fieldCtl = document.getElementById('target');
				fieldCtl.options.length=0;	
				if(result != "") {
					var fieldsByte = new Array();
					var field = new Array();
					fieldsByte = result.split(";");
					height = (fieldsByte.length)*20;
			
	  				ItemInfoArr.length = 0; // 清空
					for ( var i = 0; i < fieldsByte.length-1; i++) {
						field = fieldsByte[i].split(",");
						fieldCtl.options.add(new Option(field[1],field[0]));
						ItemInfoArr.push(fieldsByte[i]);
					}							
				}
			}
		}
	}
	xmlhttp.send();
}

function bound(){
	var xlDoc = document.getElementById("WebOffice").GetDocumentObject();
	var area = xlDoc.Application.Selection;
	var xlSht = xlDoc.ActiveSheet;
	var message = "";
	if (area == null) {
		message = "请选择单元格!";
	}
	var boo = $(".item").is(':hidden');
	if(boo){
		var tableName = document.getElementById("tableName").value.split(";")[1];
		var fieldNames = document.getElementById("fieldName").options;		
		var fieldName = new Array();
		var len = 0;
		for ( var i = 0; i < fieldNames.length; i++) {
			if(fieldNames[i] != null && fieldNames[i].selected == true){
				fieldName[len] = fieldNames[i];
				len++;
			}
		}
		
		if(tableName == null || tableName == ""){
			message += "请选择表;\r\n";
		}
		if(fieldName == null || fieldName.length<1){
			message += "请选择字段;";
		}
	}else{
		var currencyType = document.getElementById("currencyType").value;
		var itemproperty = document.getElementById("itemproperty").value;		
		var time = document.getElementById("time").value;
		var info = document.getElementById("info").value;
		var dimension1 = document.getElementById("dimension1").value;
		var dimension2 = document.getElementById("dimension2").value;
		var issue = document.getElementById("issue").value;
		var freq = document.getElementById("freqCategory").value;
		var isEditAble='2';
		if($("input[name=isEditAble]").is(':checked')) {
			isEditAble='1';
		}
		var suit = document.getElementById("suit").value;
		var targets = document.getElementById("target").options;		
		var isHorizontal = document.getElementById("checkbox1").checked;
		var itemcolor = document.getElementById("itemcolor").value;
		var target = new Array();
		var len = 0;
		for ( var i = 0; i < targets.length; i++) {
			if(targets[i] != null && targets[i].selected == true){
				target[len] = targets[i];
				len++;
			}
		}		
		if(suit == "" || suit == null){
			message += "请选择主题;\r\n";
		}
		if(target == null || target == ""){
			message += "请选择指标;\r\n";
		}
		if(currencyType == "" || currencyType == null){
			message += "请选择币种;\r\n";
		}
		if(itemproperty == "" || itemproperty == null){
			message += "请选择属性;\r\n";
		}
	}
	
	if(message != "") {
		alert(message);
	} else {
		if(boo) { // 表字段绑定
			var column = area.Column;
			var row = area.Row;
			
			for ( var i = 0; i < fieldName.length; i++) {
				var comment = "[字段]\n表名:"+tableName+"\n字段:"+fieldName[i].value+"";
				var curCellComment = xlSht.Cells(row, column).Comment;// .getCellComment();
				if(column == 1 && curCellComment != null){ // 第一列时，保留上一行表结尾标志
					comment = "[表结束]"+comment;		
				}
				xlSht.Cells(row, column).ClearComments();
				xlSht.Cells(row, column).AddComment(comment);
				xlSht.Cells(row, column).Comment.Shape.TextFrame.AutoSize = true;
				//xlSht.Cells(row, column).Value2 = " "; // 防止取得此cell为null
				
				// 下一行首列设置结束标志
				if(i == 0) {
					var nextLineComment = xlSht.Cells(row+1, 1).Comment;
					var newLineString = "";
					if(nextLineComment != null) { // && nextLineComment != "undefined"
						newLineString = "[表结束]" + nextLineComment.Text().replace("[表结束]", "");
					}else{
						newLineString = "[表结束]";
					}
					xlSht.Cells(row+1, 1).ClearComments();
					xlSht.Cells(row+1, 1).AddComment(newLineString);
					xlSht.Cells(row+1, 1).Comment.Shape.TextFrame.AutoSize = true;
					//20060621 wangchuan update
					//xlSht.Cells(row+1, 1).Value2 = " ";
				}				
				column++;
			}
		} else { // 指标绑定
			currencyType = currencyType.split(";")[0];
			var row = area.Row;
			var col = area.Column;
			for ( var i = 0; i < target.length; i++) {
				var oldComment = "";
				if(col == 1 && xlSht.Cells(row, 1).Comment != null) { // 第一列的[表结束]要保留
					oldComment = xlSht.Cells(row, 1).Comment.Text();
					if(oldComment.indexOf("[表结束]") > -1)
						oldComment = "[表结束]";
					else
						oldComment = "";
				}
				xlSht.Cells(row, col).ClearComments();
				xlSht.Cells(row, col).AddComment(oldComment 
						+ "[指标]\n代码:"+target[i].value
						+"\n币种:"+currencyType
						+"\n属性:"+itemproperty
						+"\n日期:"+time
						+"\n频度:"+freq
						+"\n机构:"+info
						+"\n扩展1:"+dimension1
						+"\n扩展2:"+dimension2
						+"\n期数:"+issue
						+"\n可编辑:"+isEditAble
						+"\n颜色:"+itemcolor
						+"");
				if(null!= xlSht.Cells(row, col).Comment.Shape
						&& null!=xlSht.Cells(row, col).Comment.Shape.TextFrame){
					xlSht.Cells(row, col).Comment.Shape.TextFrame.AutoSize = true;
				}
				
				//20160426 Liaoxl repair
				//xlSht.Cells(row, col).Value2 = " ";
				
				if(isHorizontal == true)
					col++;
				else
					row++;
			}
			//20160426 Liaoxl repair
			//xlDoc.Application.Selection = "  "; 
		}
		alert("绑定成功");
	}
}
/*author:guochenglian 
 * 属性绑定
 * 在同一片区域内选择的不连续的指标之间也可以进行属性更改绑定
 */
function unionbound(){
	
	var xlDoc = document.getElementById("WebOffice").GetDocumentObject();
	var area = xlDoc.Application.Selection;
	 var row =0;
	 var col =0;
	 var hasCommentProc = false;
	 var xlSht = xlDoc.ActiveSheet;
	
	// 获取当前区域的行集和列集
   var columns = area.Columns;
   var rows =area. Rows
  
   for ( var i = 0; i < columns.Count; i++) 
   {
	   for(var j=0;j<rows.Count;j++)
		   {
			   var target="";
			    row = area.Row+j; 
			    col = area.Column+i;
			    
			   if(xlSht.Cells(row, col).Comment !=null){
					var oldComment = xlSht.Cells(row, col).Comment.Text();
					if(oldComment!=null){
						
					   // 获取原批注中的指标代码
						
						
				        var items = oldComment.split("\n");
					    $.each(items,function(i,itemss){
					    	var pair = itemss.split(":");
					    	if(pair.length==2){
					    		if(pair[0]=="代码"){
					    			target=pair[1];
									}
								}
					  		})
					    // 获取当前对象中的初指标之外的属性
					    var currencyType = document.getElementById("currencyType").value;
						var itemproperty = document.getElementById("itemproperty").value;		
						var time = document.getElementById("time").value;
						var info = document.getElementById("info").value;
						var dimension1 = document.getElementById("dimension1").value;
						var dimension2 = document.getElementById("dimension2").value;
						var issue = document.getElementById("issue").value;
						var freq = document.getElementById("freqCategory").value;
						var isEditAble='2';
						if($("input[name=isEditAble]").is(':checked')) {
							isEditAble='1';
						}
						var suit = document.getElementById("suit").value;		
						var isHorizontal = document.getElementById("checkbox1").checked;
						var itemcolor = document.getElementById("itemcolor").value;
					  
							// 重新绑定指标
						xlSht.Cells(row, col).ClearComments();
						xlSht.Cells(row, col).AddComment(
											"[指标]\n代码:"+target
											+"\n币种:"+currencyType.split(";")[0]
											+"\n属性:"+itemproperty
											+"\n日期:"+time
											+"\n频度:"+freq
											+"\n机构:"+info
											+"\n扩展1:"+dimension1
											+"\n扩展2:"+dimension2
											+"\n期数:"+issue
											+"\n可编辑:"+isEditAble
											+"\n颜色:"+itemcolor
											+"");
						hasCommentProc= true;
						if(null!= xlSht.Cells(row, col).Comment.Shape
								&& null!=xlSht.Cells(row, col).Comment.Shape.TextFrame){
							xlSht.Cells(row, col).Comment.Shape.TextFrame.AutoSize = true;
						} 
				   }
			   }
		   }
	   }
	   if(hasCommentProc){
		   alert("属性绑定成功");	
		   
	   }else{
		   alert("请先绑定指标再进行属性绑定");	
	   }
	   
   
   }


function saveFile(){
    var WebOffice = document.getElementById("WebOffice");
    var URL = $("#basePath").val()+"report/UploadWebOfficeExcelFile.action?path="+$("#path").val();
    URL+="&CalcInstName="+$("#calcInstName").val();
	
    WebOffice.HttpInit(); //初始化Http引擎
    WebOffice.HttpAddPostCurrFile("uploadFile", ""); //上传打开的文件
    returnValue = WebOffice.HttpPost(URL); //执行上传动作
    if("success" == returnValue){
    	alert("文件上传成功"); 
    } 
    else if("html_fail" == returnValue){
    	alert("转换成HTML文件失败"); 
    }
    else if(returnValue.indexOf("miss_CalcInstName")==0){
    	alert("缺少计算实例，公式未转换！"); 
    }
    else if(returnValue.indexOf("exist_unParseFormula:")==0){
    	alert(returnValue.substr(21));
    }
    else {
   		alert("文件上传失败")
    }   
}
