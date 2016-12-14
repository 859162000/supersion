<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String relation = "";
	String relation1 = "";
	if (session.getAttribute("relation") != null) {
		relation = "&" + session.getAttribute("relation").toString();
		relation1 = relation.substring(1);
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>"></base>
<link href="css/Comm.css" rel="stylesheet" type="text/css" />
<link href="css/JSGrid.css" rel="stylesheet" type="text/css" />
<link href="css/loading.css" rel="stylesheet" type="text/css" />
<link href="css/buttons.css" rel="stylesheet" type="text/css" />
<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
<link href="css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="css/themes/icon.css" rel="stylesheet" type="text/css" />
<script src="js/jquery/jquery.1.7.js" type="text/javascript"></script>
<script src="js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="js/jquery.upload.js" type="text/javascript"></script>
<script src="js/showcomponent.js" type="text/javascript"></script>
<script src="js/DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">

	//待修正
	function changeOptionsTitle(name){ 
	 	var options = document.getElementById(name).options;
	    for (var i=0;i<options.length;i++){ 
	        var text = options[i].text;
	        options[i].title = text;
	    }
	}
	
	function onSelectChange(obj) {
    }

	function clearData(){
		var dtype1 = $("#form1_serviceParam_dimensionType1").val();
	    var dtype2 = $("#form1_serviceParam_dimensionType2").val();
	    if(dtype1 != "01" && dtype2 != "01"){
	    	$("#form1_serviceParam_startDate").attr("readonly",true).val("");
	    	$("[id='serviceParam.timeFunc']").attr("readonly",true).val("");
	    }
	}

	$(document).ready(function() {
		$("#form1_serviceParam_startDate").attr("title","FC_STAYEAR");
	    $("#form1_serviceParam_endDate").attr("title","FC_NOWDATE");
	    
	    $("[id='serviceParam.timeFunc']").change(function(){
			$("#form1_serviceParam_endDate").attr("readonly",false);
			if($(this).val()!=""){
				if($(this).val()=="yearToNow"){
					$("#form1_serviceParam_startDate").attr("readonly",true).val("FC_STAYEAR");
					$("#form1_serviceParam_endDate").attr("readonly",true).val("FC_NOWDATE");
				}
				else{
					$("#form1_serviceParam_startDate").attr("readonly",true).val("");
				}
			}
			else{
				$("#form1_serviceParam_startDate").attr("readonly",false);
			}
		});
		clearData();
		
		// 设置指标输入框的出事文字
		$("#inputItemCodeHelper").val("请输入指标代码");
		$("#inputItemCodeHelper").css("color", "gray");	
		
		$("#inputItemCodeHelper").focus(function(){
			$("#inputItemCodeHelper").val("");
		}).blur(function(){
			if($("#inputItemCodeHelper").val() == ""){
				$("#inputItemCodeHelper").val("请输入指标代码");
			}
		}).keyup(function(){
			// 消除IE浏览器兼容视图中，select尺寸随指标信息的输入值得次数改变
			$("#target").css("width", $("#target").parent().width());	
			$("#target").css("height", $("#target").parent().height());	
			var select=document.getElementById("serviceParam.suitCode"); 
	  		var index = select.selectedIndex; // 选中索引
	  		var selectValue= select.options[index].value;//要的值
			
			if(selectValue == null || selectValue == ''){
				alert("请选择主题");
				return;
			}
			
			var str = $(this).val();
			if($.trim(str)!=''&& str !="请输入指标代码"){
				var u=$("#basePath").val() + "ajax/GetJsonInfo-report.dto.ItemInfo.action?"+new Date();
				$.ajax({
					type:"GET",
		            url:u,
		            data:"itemCode="+str+"&suit="+selectValue,
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
		
		// 移除选中的指标信息
		$("#selectedTarget").dblclick(function(){
			removeSelectedTargetOptions();
		});
		
		//给chart_type_select 元素赋值
		$("#chart_type_select").append($("[id='serviceParam.strChartType']").html());
		
		//模型图形--change事件
		$("[id='serviceParam.strChartType']").change(function(){
			var selectValue = $(this).val();
			$("#chart_type_select").val(selectValue);
		});
		//分析模型下拉选择事件
		$("#chart_type_select").change(function(){
			var selectValue = $(this).val();
			$("[id='serviceParam.strChartType']").val(selectValue);
			$('#form1').submit();
		});
		
		<s:if test="serviceResult.analyse == true">
			onWithDataShowClick('report','ModelAnalyse-report.dto.analyse.Rep_AnalyseModel');
		</s:if>
	});
	
	//分析
	function onWithDataShowClick(actionNamespace,actionName){
		var url=basePath + actionNamespace + "/" + actionName + ".action";
		$('#form1').attr('action',url);
		clearkeyMap();
		addkey1Map();
		addkey2Map();
		$('#form1').submit();
		$('#form1').hide();
		$('#modelChart_div').show();
	}
	
	//关闭
	function close_modelChart(){
		$('#form1').show();
		$('#modelChart_div').hide();
	}
	
	var chart = 11;
	// 显示出已经选择的指标信息
	function addSelectedTargetOptions(){
		var select=document.getElementById("target"); 
      	var index = select.selectedIndex; // 选中索引
		// 获取可选指标select中选中的指标信息          	
      	var selectValue= select.options[index].value;
      	var selectText= select.options[index].text;
		if((selectValue != null || selectValue != '') && (selectText != null || selectText != '')){
			// 判断已选指标信息中是否存在当前值
			var obj = document.getElementById("selectedTarget").options;
			if(obj.length == 0){
				var varItem = new Option(selectText, selectValue);      
				varItem.selected = true;
    			obj.add(varItem); 
			}else{
				var isExsit = false;
				for(var i = 0; i < obj.length; i++){
					if(obj[i].value == selectValue){
						isExsit = true;
						break;
					}
				}
				if(!isExsit){
					var varItem = new Option(selectText, selectValue);    
					varItem.selected = true;  
        			obj.add(varItem); 
				}else{return;}
			}
		}else{
			return;
		}
	}
	
	//清除 key
	function clearkeyMap(){
		$("#div_keyMap_input").html("");
	}
	
	//添加维度1一 keyvalue
	function addkey1Map(){
		var dtype = $("[id='serviceParam.dimensionType1']").val();
		var keyName;
		var preName = "serviceParam.";
		switch(dtype){
			case("02"):keyName="selectedTarget";break;
			case("03"):keyName=preName+"orgIds";break;
			case("04"):keyName=preName+"currIds";break;
			case("05"):keyName=preName+"itemProIds";break;
			case("06"):keyName=preName+"dimension1Types";break;
			case("07"):keyName=preName+"dimension2Types";break;
		}
		if(keyName != null){
			var selectVals = $("[id='"+keyName+"']").val();
			$.each(selectVals,function(){
				var kName = preName+"key1Map['"+this+"']";
				var kValue = $("[id='"+keyName+"']").find("option[value='"+this+"']").text();
				kValue = kValue.replace(/\s+/g,"");
				$("#div_keyMap_input").append("<input type=text name="+kName+" value='"+kValue+"'>");
			});
		}
	}
	
	//添加维度2一 keyvalue
	function addkey2Map(){
		var dtype = $("[id='serviceParam.dimensionType2']").val();
		var keyName;
		var preName = "serviceParam.";
		switch(dtype){
			case("02"):keyName="selectedTarget";break;
			case("03"):keyName=preName+"orgIds";break;
			case("04"):keyName=preName+"currIds";break;
			case("05"):keyName=preName+"itemProIds";break;
			case("06"):keyName=preName+"dimension1Types";break;
			case("07"):keyName=preName+"dimension2Types";break;
		}
		if(keyName != null){
			var selectVals = $("[id='"+keyName+"']").val();
			$.each(selectVals,function(){
				var kName = preName+"key2Map['"+this+"']";
				var kValue = $("[id='"+keyName+"']").find("option[value='"+this+"']").text();
				kValue = kValue.replace(/\s+/g,"");
				$("#div_keyMap_input").append("<input type=text name="+kName+" value='"+kValue+"'>");
			});
		}
	}
	
	//指标单选
	function addSelectedTargetOption(){
		var select=document.getElementById("target"); 
      	var index = select.selectedIndex; // 选中索引
		// 获取可选指标select中选中的指标信息          	
      	var selectValue= select.options[index].value;
      	var selectText= select.options[index].text;
		if((selectValue != null || selectValue != '') && (selectText != null || selectText != '')){
			$("#selectedTarget option").each(function(){
				$(this).remove();
			});
			$("#selectedTarget").append("<option value='"+selectValue+"' selected='selected'>"+selectText+"</option>");
		}else{
			return;
		}
	}
	
	// 删除已选指标的数据信息
	function removeSelectedTargetOptions(){
      	var flag = confirm("确定要撤销选中的指标数据吗?");
      	if(flag){
      		var select=document.getElementById("selectedTarget"); 
          	var index = select.selectedIndex; // 选中索引
      		var obj = select.options;
      		obj.remove(index);
      		for(var i=0;i < obj.length; i++){
      			obj[i].selected=true;
      		}
      	}
	}
	
	//var headName=<s:property value="serviceResult.listHeadJsons" escape="false" />;
	var basePath="<%=basePath%>";
	var line=0;
	
	var Now_Mode = "";
	var address = window.location.pathname;
    	var one=address.split("/");
    	var two=window.location.protocol+"//"+window.location.host+"/"+one[1]+"/";
    	$(document).ready(function(){
      	$("#insertButton").live('click',insertRow);// 新增按钮时间结束
		// 编辑按钮
		$("#editButton").live('click',edit);// 编辑按钮结束
	  		$("#loading").hide();
	  		// table滚动时，固定table的头部信息
	  		scrollGrid_Table();
	});// ready方法结束
			
</script>
</head>
<body style="margin: 0px; width: 100%; height: 100%;">
	<div id="loading" class="loading">
		<div id="query_hint" class="query_hint">页面加载中，请稍等...</div>
	</div>
	<s:form id="form1" style="width:100%;height:100%;" target="modelFrmChart">
		<!-- 按钮区域 -->
		<div class="easyui-panel" style="padding: 5px; width: 100%; height: auto;">
			<s:iterator value="serviceResult.showSaveOrUpdate.operationList" id="sr">
				<s:if test="%{#sr.operationType=='Post'}">
					<a href="javascript:void(0);" onclick="onMasterOpClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')" class="Menu_Frame_Div_Cell"> <s:property
							value="operationName" />
					</a>
				</s:if>
				<s:elseif test="%{#sr.operationType=='Post1'}">
					<a href="javascript:void(0);" onclick="onWithDataShowClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')" class="Menu_Frame_Div_Cell"> <s:property
							value="operationName" />
					</a>
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Get'}">
					<a href="javascript:void(0);" onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action')" class="Menu_Frame_Div_Cell"> <s:property
							value="operationName" />
					</a>
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Get1'}">
					<a href="javascript:void(0);" onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action?id=<s:property value="serviceResult.id"/>')"
						class="Menu_Frame_Div_Cell"> <s:property value="operationName" />
					</a>
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Get2'}">
					<a href="javascript:void(0);"
						onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction.substring(0,operationAction.indexOf('?'))"/>.action?id=<s:property value="serviceResult.id"/>&type=<s:property value="operationAction.substring(operationAction.indexOf('?')+6)"/>')"
						class="Menu_Frame_Div_Cell"> <s:property value="operationName" />
					</a>
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Upload'}">
					<a href="javascript:void(0);" onclick="onMasterOpClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')" class="Menu_Frame_Div_Cell"> <s:property
							value="operationName" />
					</a>
				</s:elseif>
			</s:iterator>
		</div>
		
		<div id="div_keyMap_input" name="div_keyMap_input" style="display:none"></div>

		<!-- 基础信息 -->
		<div  style="padding: 10px; width: 100%; height: auto;">
			<input id="id" name="id" type="hidden" value="<s:property value="serviceResult.showSaveOrUpdate.id" />" /> <input id="type" name="type" type="hidden"
				value="<s:property value="serviceResult.showSaveOrUpdate.tName" />" />
			<!-- 将要显示的数据对象放入重新定义的变量中供界面展示所用 -->
			<s:set name="showObject" value="serviceResult.showSaveOrUpdate" />
			<s:include value="ShowTabForBase.jsp" />
		</div>

		<!-- 选项卡-->
		<div id="tt" class="easyui-tabs" style="width: 100%; height: auto;" data-options="fit:true,border:false">
			<!-- 以卡片形式显示明细信息 -->
			<s:iterator value="serviceResult.showCardList" id="scl">
				<div title="<s:property value='#scl.navigationName'/>" style="padding: 10px; width: 100%; height: auto;">
					<!-- 将要显示的数据对象放入重新定义的变量中供界面展示所用 -->
					<s:set name="showObject" value="#scl" />
					<s:if test="%{#scl.tabUrl!=null}">
						<s:include value="%{#scl.tabUrl}" />
					</s:if>
					<s:else>
						<s:include value="ShowTabForBase.jsp" />
					</s:else>
				</div>
			</s:iterator>
		</div>
		<!-- 窗口信息区域 -->
		<s:hidden id="windowId" name="windowId"></s:hidden>
	</s:form>
	<div id="modelChart_div" style="width: 100%; height: 100%; right: 0px; bottom: 0px; overflow-x: hidden; overflow-y: hidden; padding-top: 0px; position: absolute;display:none;">
		<div class="easyui-panel" style="padding: 5px; width: 100%; height: auto;">
			<a href="javascript:close_modelChart();"  class="Menu_Frame_Div_Cell">参数</a>
		<s:if test="serviceResult.analyse == true">
			<a href="javascript:void(0);" onclick="onLinkClick('http://localhost:8080/web/framework/BackFirst-report.dto.analyse.Rep_AnalyseModel.action')" class="Menu_Frame_Div_Cell"> 返回</a>
		</s:if>
			<select id="chart_type_select" name="chart_type_select" style="float:right;margin-right:150px;height:25px;"></select>
		</div>
		<iframe allowtransparency="true" class="right_iframe"  id="modelFrmChart" name="modelFrmChart" marginwidth="0"  height="100%" width="100%" frameBorder=0  scrolling="yes">
		</iframe>
	</div>
	
</body>
</html>
