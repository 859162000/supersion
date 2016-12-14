<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>机构表单关系</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/Comml.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/jquery/jquery.1.7.js"
			type="text/javascript"></script>

		<style type="text/css">
			samp {
				font-family: 'Microsoft Yahei';
				font-size: 11px;
				cursor: pointer;
				font-weight: bold;
				float: left;
				width: 115px;
				text-align: right;
				line-height: 30px;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}
			
			ul
			{
				margin:0px;
				padding:0px;
				width:300px;
				height:200px;
				overflow:auto;
				margin-top:0px;
				cursor: default;
			}
			
			li
			{
				list-style-type:none;
				width:280px;
				text-align: left;
				line-height: 20px;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}
			
			.selectedLi{
				color:#ffffff;
				background-color:#3399FF;
			}
			
			.infoDiv
			{
				width:490px;
				height:250px;
				float:left;
				margin-top:5px;
			}
			
			select
			{
				float:left;
				width:300px;
				overflow:auto;
				margin-left:5px;
			}
			
			#currencyTypeSelect{height:230px;}
			
			#instInfoSelect{height:230px;}
			
			#suitDiv{height:30px;}
			
			#rptInfoDiv{
				width:100%;;
				height:220px;
			}
			
			#rptInfoDiv select{
				height:195px;
				margin-top:5px;
				_height:190px;
			}
			
		</style>

		<script type="text/javascript">
			var xmlhttp;
			$(document).ready(function(){
				loadDataList('coresystem.dto.InstInfo');
				loadDataList('extend.dto.Suit');
				loadDataList('report.dto.CurrencyType');

				$("#suitSelect").live("change",function(){
					$("li").remove();
					loadDataList('extend.dto.Suit');
				});
				
				$("li").live("click",function(){
					$(this).siblings().removeClass("selectedLi");
					$(this).addClass("selectedLi");
					$("#strRptCode").val($(this).attr("name"));
				});
			});
			
			function load(){
	            $("#loading").remove();
	            $("#query_hint").remove();
           	}
           	
           	/**
           	 * 
           	 * 加载界面数据信息
           	 */
			function loadDataList(dto){
				var url = "";
				url = $("base").attr("href")+"report/InsertInstRptRelaDataInitAction-"+dto+".action";
				var strSuitCode = $("#suitSelect option:selected").val();
				$.ajax({
					type:"POST",
			        url:url,
			        data:"id="+strSuitCode, 
			    	datatype: "json",
			    	success:function(data){
			        	var dataObj=data;
			        	$.each(dataObj,function(k,item){
			            	if(k == "instInfo" && item != "null"){
			            		$.each(item,function(index,value){
			            			$("<option value='"+item[index].id+"'>"+item[index].name+"</option>").appendTo($("#instInfoSelect"));
			            		});
			            	}
			            	else if(k == "suit"  && item != "null"){
			            		$.each(item,function(index,value){
				            		$("<option value='"+item[index].id+"'>"+item[index].name+"</option>").appendTo($("#suitSelect"));
				            	});
			           		}else if(k == "currencyType" && item != "null"){
			           			$.each(item,function(index,value){
			            			$("<option value='"+item[index].id+"'>"+item[index].name+"</option>").appendTo($("#currencyTypeSelect"));
			            		});
			           		}else if(k == "rptInfo" && item != "null"){
			           			$("li").remove();
			           			$.each(item,function(index,value){
				            		$("<li name='"+item[index].id+"'>"+item[index].name+"</li>").appendTo($("#rptInfoSelect"));
				            	});
				            	var fontFamily = $("#instInfoSelect option:eq(0)").css("font-family");
				            	var fontSize = $("#instInfoSelect option:eq(0)").css("font-size");
				            	$("li").css("font-family",fontFamily);
				            	$("li").css("font-size",fontSize);
			           		}
			        	});
			   		},
					error:function(data){
						var dataObj=eval("("+data+")");
			        	$.each(dataObj,function(k,item){
			            	if(k == "instInfo"){
			            		if(item == "error"){
			            			$("<option value=''>当前列表加载错误</option>").appendTo($("#instInfoSelect"));
			            		}
			            	}
			            	else if(k == "suit"){
			            		if(item == "error"){
			            			$("<option value=''>当前列表加载错误</option>").appendTo($("#suitSelect"));
			            		}
			           		}else if(k == "rptInfo"){
			            		if(item == "error"){
			            			$("li").remove();
			            			$("<li name='"+item[index].id+"'>"+item[index].name+"</li>").appendTo($("#rptInfoSelect"));
			            		}
			           		}else{
			            		if(item == "error"){
			            			$("<option value=''>当前列表加载错误</option>").appendTo($("#currencyTypeSelect"));
			            		}
			           		}
			        	});
					}
				});
			}
           	
           	function onOperationClick(actionNamespace, actionName) {
               	if($("#currencyTypeSelect option:selected").length <= 0){
                  	alert("币种不能为空");
					return;
                }else{
                	var url = "<%=basePath%>" + actionNamespace + "/" + actionName + ".action"
                	var form=$("#form1");
					var param=form.serialize();	
					$.ajax({
						type:"POST",
				        url:url,
				        data:param, 
				    	datatype: "json",
				    	success:function(data){
				    		alert(data.message);
				   		},
						error:function(data){
							alert(data.message);
						}
					});
                }
		    }
		    
		    function onOperationAlertClick(actionNamespace,actionName,page,name){
            	var gnl=confirm("确定要"+name+"操作吗?");
            	if(gnl){
            		onOperationClick(actionNamespace, actionName);
            	}
            }
           	
           	function onLinkClick(url) {
		        if (url.indexOf("?") > -1) {
		            url += "&windowId=" + document.getElementById('windowId').value;
		        }
		        else {
		            url += "?windowId=" + document.getElementById('windowId').value;
		        }
		        window.location.href = url;
		    }
           	
		</script>

	</head>

	<body id="bodID" onload="load()" style="overflow: hidden;"
		ondragstart="return false;">
		<div id="loading" class="loading" style="">
			<div id="query_hint" class="query_hint"> 
				页面加载中，请稍等... 
			</div>
		</div>
		<s:form id="form1" name="form1" enctype="multipart/form-data">
			<div id="buttons" class="Menu_Frame_Div">
				<s:iterator value="serviceResult.operationList" id="sr">
					<s:if test="%{#sr.operationType=='Post'}">
						<a class="Menu_Frame_Div_Cell"
							href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"><s:property
								value="operationName" /> </a>
	
					</s:if>
					<s:elseif test="%{#sr.operationType=='Get'}">
						<a class="Menu_Frame_Div_Cell"
							href="javascript:onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action')"><s:property
								value="operationName" /> </a>
					</s:elseif>
					<s:elseif test="%{#sr.operationType=='Get1'}">
						<input class="button button:hover button:focus button:active"
							style="width:<s:property value="operationName.length()<=9?100:105+operationName.length()*11"/>px; height: 26px; margin-right: auto;"
							type="button" value="<s:property value="operationName"/>"
							onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action?id=<s:property value="serviceResult.id"/>')" />
					</s:elseif>
					<s:elseif test="%{#sr.operationType=='Get2'}">
						<input class="button button:hover button:focus button:active"
							style="width:<s:property value="operationName.length()<=9?100:105+operationName.length()*11"/>px; height: 26px; margin-right: auto;"
							type="button" value="<s:property value="operationName"/>"
							onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction.substring(0,operationAction.indexOf('?'))"/>.action?id=<s:property value="serviceResult.id"/>&type=<s:property value="operationAction.substring(operationAction.indexOf('?')+6)"/>')" />
					</s:elseif>
					<s:elseif test="%{#sr.operationType=='Upload'}">
						<s:file id="123456" class="files" name="uploadFile"></s:file>
						<a class="button chat icon"
							style="margin-left: 5px; margin-top: 2px; font-size: 12px; font-family: 微软雅黑;"
							href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"><s:property
								value="operationName" /> </a>
					</s:elseif>
				</s:iterator>
			</div>
			
			<div id="DataDiv" style="width:100%;float:left;overflow:auto;">
				<div class="infoDiv">
					<samp>机构列表</samp>
					<select id="instInfoSelect" name="serviceParam.instInfoList" multiple="multiple"></select>
				</div>
				
				<div class="infoDiv">
					<div id="suitDiv">
						<samp>主题列表</samp>
						<select id="suitSelect" name="serviceParam.suit.strSuitCode" style="height:30px;">
							<option value="">--请选择--</option>
						</select>
					</div>
					<div id="rptInfoDiv">
						<samp>报表列表</samp>
						<div style="width:5px;height:10px;float:left;"></div>
						<ul id="rptInfoSelect" class="TextBox"></ul>
					</div>
				</div>
				
				<div class="infoDiv">
					<samp>币种列表</samp>
					<select id="currencyTypeSelect" name="serviceParam.currencyTypeList" multiple="multiple"></select>
				</div>
				
			</div>
			<s:hidden id="windowId" name="windowId"></s:hidden>
			<!--<s:hidden id="strRptCode" name="serviceParam.rptInfo.strRptCode" value="" />-->
			<s:hidden id="strRptCode" name="serviceParam.rptInfoIdList" value="" />
		</s:form>

	</body>
</html>
