<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"></base>
		<title>ShowRptChart</title>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/JSGrid.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/Comml.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/Windows.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/Comml.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/JsGrid.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/Shortcutkeys.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/jquery/jquery.1.7.js" type="text/javascript"></script>
		

		<style type="text/css">
			.chooseingItemTitle{
				clear:left;
				float: left;
				width: 160px;
				height: 40px;
				margin-left:10px;
				_margin-left:5px;
				text-align: left;
				line-height: 40px;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}
			.choosedItemTitle{
				clear:right;
				float: left;
				width: 160px;
				height: 40px;
				margin-left:10px;
				text-align: left;
				line-height: 40px;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}
			.itemTitle{
				margin-top:3px;
				width:30px;
				margin-left:10px;
				_margin-left:5px;
				text-align:left;
				float:left;
				line-height:25px;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}
		</style>
		
		<script type="text/javascript">
	
		$(document).ready(function() {
			// 设置指标输入框的出事文字
			$("#inputHelper").val("请输入指标代码");
			$("#inputHelper").css("color", "gray");	
			
			$("#inputHelper").focus(function(){
				$("#inputHelper").val("");
			}).blur(function(){
				if($("#inputHelper").val() == ""){
					$("#inputHelper").val("请输入指标代码");
				}
			}).keyup(function(){
				// 消除IE浏览器兼容视图中，select尺寸随指标信息的输入值得次数改变
				$("#target").css("width", $("#target").parent().width());	
				$("#target").css("height", $("#target").parent().height());	
				
				var select=document.getElementById("serviceParam.suit.strSuitCode"); 
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
			
			// 展示出选择的指标信息
			$("#target").dblclick(function(){
				addSelectedTargetOptions();
			});
			
			// 移除选中的指标信息
			$("#selectedTarget").dblclick(function(){
				removeSelectedTargetOptions();
			});
			
			$('#chart').click(function(){
				checkMustSelectField();
				var url = $('#chartInput').val();
				$('#form1').attr("action",url);
				$('#form1').submit();
			});
			
		});
		
		// 判断必选项字段的值是否为空
		function checkMustSelectField(){
			var index = null;
			var startDate = document.getElementById("serviceParam.startDate").value;
			var endDate = document.getElementById("serviceParam.endDate").value;
			
			index = document.getElementById("serviceParam.itemProperty.strPropertyCode").selectedIndex;
			var itemProperty = document.getElementById("serviceParam.itemProperty.strPropertyCode").options[index].value;
			index = document.getElementById("serviceParam.currencyType.strCurrencyCode").selectedIndex;
			var currencyType = document.getElementById("serviceParam.currencyType.strCurrencyCode").options[index].value;
			index = document.getElementById("serviceParam.strChartType").selectedIndex;
			var strChartType = document.getElementById("serviceParam.strChartType").options[index].value;
			index = document.getElementById("serviceParam.suit.strSuitCode").selectedIndex;
			var suit = document.getElementById("serviceParam.suit.strSuitCode").options[index].value;
			var itemIdListOps = $("#selectedTarget").find("option:selected");
			
			if(startDate == null || startDate == ''){
				alert("请选择开始日期");
				return false;
			}
			if(endDate == null || endDate == ''){
				alert("请选择结束日期");
				return false ;
			}
			if(itemProperty == null || itemProperty == ''){
				alert("请选择指标属性");
				return false;
			}
			if(currencyType == null || currencyType == ''){
				alert("请选择币种");
				return false;
			}
			if(strChartType == null || strChartType == ''){
				alert("请选择展示图形");
				return false;
			}
			if(itemIdListOps.length == 0){
				alert("已选指标为空");
				return false;
			}
			if(suit == null || suit == ''){
				alert("请选择主题");
				return false;
			}
			return true;
		}
		
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
		function onOperatorClick(url)
		{
		   
		    var d=$("select[id='serviceParam.strFreq']").val();
			var form= $("#form1");
			form.attr("action",url);
			form.submit();
			
			
		}
		
		function AutiSize()
        {
			   
	        var treeWidth = parseInt(document.getElementById('SelectDivFrame').style.width);
			document.getElementById('Grid_Task').style.width = ($(window).width() - treeWidth - 5) +'px';
        }

		// 待修正
		function changeOptionsTitle(name){ 
		 	var options = document.getElementById(name).options;
		    for (var i=0;i<options.length;i++){ 
		        var text = options[i].text;
		        options[i].title = text;
		    }
		}
		
		function jumpStep(jumpTag, nextStepNum){
			if(jumpTag == 'next'){
				$('#step'+nextStepNum).hide();
				$('#step'+(nextStepNum+1)).show();
			}
			if(jumpTag == 'pre'){
				$('#step'+nextStepNum).hide();
				$('#step'+(nextStepNum-1)).show();
			}else{
				return;
			}
		}
		
	</script>
	</head>

	<body onResize="AutiSize();" onload="AutiSize();" ondragstart="return false;">
			<div id="SelectDivFrame"
				style="width: 350px; height: 100%; float: left; font-family: 'Microsoft Yahei'; margin-top: 0px; margin-left: 0px; overflow-y: auto; overflow-x: hidden;border-bottom:1px solid #0060A6;">
			<s:form id="form1" name="form1" enctype="multipart/form-data" method="post" target="frmChart">
				<!-- 第一步 -->
				<div id="step1" style="width:100%;height:auto;">
					<div style="width:100%;height:20px;line-height:20px;text-align:center;">分析指标</div>
					<s:iterator value="serviceResult.showCondition" id="sr" status="i">
						<!-- 主题信息 -->
						<s:if test="%{#sr.singleTag=='selectField'}">
							<s:if test="%{#sr.paramName=='suit.strSuitCode'}">
								<div class="Select_Menu_Control_Frame_Div" style="width:340px;height:40px;">
									<div class="Title" style="width:30px;text-align: left;"
										title="<s:property value="%{#sr.fieldShowName}"/>"
										class="Font_css_normal_Condtion">
										<s:property value="%{#sr.fieldShowName}" />
									</div>
			
									<div class="Select_Menu_SelectBox_Div" style="width:290px;_width:285px;_margin-left:5px;">
										<div class="Select_Menu_SelectBox_Div_Hidden" style="width:100%;">
											<s:select cssClass="Select_Menu_SelectBox_Div_Option"
												list="#sr.tag" id="serviceParam.%{#sr.paramName}"
												name="serviceParam.%{#sr.paramName}"
												headerKey="%{#application.emptySelectValueName}"
												headerValue="---请选择---"  style="width:283px;_width:100%;"
												onmousemove="changeOptionsTitle('serviceParam.%{#sr.paramName}');" />
										</div>
									</div>
								</div>
							</s:if>
						</s:if>
					</s:iterator>
					<div style="width:340px;height: 28px;margin-top:3px;">
						<div class="itemTitle">指标</div>
						<input type="input" id="inputHelper" style="display:block;float:left;width:285px;height:20px;line-height:20px;margin-top:3px;margin-left:9px;_margin-left:5px;"/>
					    <!-- 
					    <div style="margin-top:3px;float:left;width:290px;margin-left:10px;height: 25px;background-color:blue;">
					    </div> -->
					</div>
					
					<div style="width:340px;height: 350px;clear:both;">
						<div style="width:100%;clear:both;height: 40px;">
							<div class="chooseingItemTitle">可选指标</div>
							<div class="choosedItemTitle">已选指标</div>
						</div>
						<div style="width:340px;height: 300px;clear:both;both;">
							<div style="float:left;width:160px;height:300px;margin-left:10px;_margin-left:5px;">
								<select id="target" style="width:100%;height:100%;overflow:auto;" multiple="multiple"></select>
							</div>
							<div style="float:left;width:160px;height:300px;margin-left:10px;">
								<select name="serviceParam.itemIdList" id="selectedTarget" style="width:100%;height:100%;overflow:auto;" multiple="multiple"></select>
							</div>
						</div>
					</div>
					
					<div style="clear:both;width:100%;height:10px;"></div>
					<!-- 步骤跳转按钮 -->
					<div style="width:100%;height:30px;line-height:30px;align:center;">
						<div onClick="jumpStep('next',1);" style="width:100px;height:30px;line-height:30px;text-align:center;cursor:pointer;background-color:#0060A6;color:white;margin-left:auto; margin-right:auto;">下一步</div>
					</div>
					
				</div>
				<!-- 第一步结束 -->
				
				<!-- 第二步 -->
				<div id="step2" style="width:100%;height:auto;display:none;">
					<div style="width:100%;height:20px;line-height:20px;text-align:center;">数据过滤</div>
					
					<s:iterator value="serviceResult.showCondition" id="sr" status="i">
						<s:if test="%{#sr.paramName!='suit.strSuitCode' && #sr.paramName!='strChartType' && #sr.paramName!='dimensions'}">
							<!-- 下拉框 -->
							<s:if test="%{#sr.singleTag=='selectField'}">
								<div class="Select_Menu_Control_Frame_Div">
									<div class="Title"
										title="<s:property value="%{#sr.fieldShowName}"/>"
										class="Font_css_normal_Condtion">
										<s:property value="%{#sr.fieldShowName}" />
									</div>
				
									<div class="Select_Menu_SelectBox_Div">
										<div class="Select_Menu_SelectBox_Div_Hidden">
											<s:select cssClass="Select_Menu_SelectBox_Div_Option"
												list="#sr.tag" id="serviceParam.%{#sr.paramName}"
												name="serviceParam.%{#sr.paramName}"
												headerKey="%{#application.emptySelectValueName}"
												headerValue="---请选择---"
												onmousemove="changeOptionsTitle('serviceParam.%{#sr.paramName}');" />
										</div>
									</div>
								</div>
							</s:if>
							
							<s:elseif test="%{#sr.singleTag=='textField'}">
								<div class="Select_Menu_Control_Frame_Div">
									<div class="Title"
										title="<s:property value="%{#sr.fieldShowName}"/>"
										class="Font_css_normal_Condtion">
										<s:property value="%{#sr.fieldShowName}" />
									</div>
									<div class="Control_Frame">
										<s:textfield cssClass="Select_Menu_TextBox"
											name="serviceParam.%{#sr.paramName}"
											label='<s:property value="%{#sr.fieldShowName}"/>'></s:textfield>
									</div>
								</div>
							</s:elseif>
							
							<!-- 下拉多选框 -->
							<s:elseif test="%{#sr.singleTag=='multipleSelectField'}">
								<div class="Select_Menu_Control_Frame_DivList">
									<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
											<s:property value="%{#sr.fieldShowName}" />
									 </div>
									 <div class="Control_Frame">
									 <s:select cssClass="Select_MultipleSelectField" 
										list="#sr.tag" name="serviceParam.%{#sr.paramName}"
										multiple="true" label="%{#sr.fieldShowName}"
										value="%{#sr.fieldValue}"
										id="serviceParam.%{#sr.paramName}"
										onmousemove="changeOptionsTitle('serviceParam.%{#sr.paramName}');"/>
									 </div>
								</div>
							</s:elseif>
							
							<!-- 日期选择框 -->
							<s:elseif test="%{#sr.singleTag=='dateField'}">
								<div class="Select_Menu_Control_Frame_Div">
									<div class="Title"
										title="<s:property value="%{#sr.fieldShowName}"/>"
										class="Font_css_normal_Condtion">
										<s:property value="%{#sr.fieldShowName}" />
									</div>
									<div class="Control_Frame">
										<s:textfield cssClass="Select_Menu_TextBox"
											name="serviceParam.%{#sr.paramName}"
											id="serviceParam.%{#sr.paramName}"
											onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"></s:textfield>
									</div>
								</div>
							</s:elseif>
							<s:elseif test="%{#sr.singleTag=='dateFieldNoSlash'}">
								<div class="Select_Menu_Control_Frame_Div">
									<div class="Title"
										title="<s:property value="%{#sr.fieldShowName}"/>"
										class="Font_css_normal_Condtion">
										<s:property value="%{#sr.fieldShowName}" />
									</div>
									<div class="Control_Frame">
										<s:textfield cssClass="Select_Menu_TextBox"
											name="serviceParam.%{#sr.paramName}"
											id="serviceParam.%{#sr.paramName}"
											onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMMdd'})"></s:textfield>
									</div>
								</div>
							</s:elseif>
							<s:elseif test="%{#sr.singleTag=='dateFieldMonth'}">
								<div class="Select_Menu_Control_Frame_Div">
									<div class="Title"
										title="<s:property value="%{#sr.fieldShowName}"/>"
										class="Font_css_normal_Condtion">
										<s:property value="%{#sr.fieldShowName}" />
									</div>
									<div class="Control_Frame">
										<s:textfield cssClass="Select_Menu_TextBox"
											name="serviceParam.%{#sr.paramName}"
											id="serviceParam.%{#sr.paramName}"
											onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMM'})"></s:textfield>
									</div>
								</div>
							</s:elseif>
							<s:elseif test="%{#sr.singleTag=='dateFieldHMS'}">
								<div class="Select_Menu_Control_Frame_Div">
									<div class="Title"
										title="<s:property value="%{#sr.fieldShowName}"/>"
										class="Font_css_normal_Condtion">
										<s:property value="%{#sr.fieldShowName}" />
									</div>
									<div class="Control_Frame"">
										<s:textfield cssClass="Select_Menu_TextBox"
											name="serviceParam.%{#sr.paramName}"
											id="serviceParam.%{#sr.paramName}"
											onfocus="WdatePicker({doubleCalendar:true,dateFmt:'H:m:s'})"></s:textfield>
									</div>
								</div>
							</s:elseif>
						</s:if>
					</s:iterator>
				
					<div style="clear:both;width:100%;height:20px;"></div>
					<!-- 步骤跳转按钮 -->
					<div style="width:100%;height:30px;line-height:30px;align:center;">
						<div style="width:210px;height:30px;line-height:30px;margin-left:auto; margin-right:auto;">
							<div onClick="jumpStep('pre',2);" style="width:100px;height:30px;line-height:30px;text-align:center;cursor:pointer;background-color:#0060A6;color:white;margin-left:auto; margin-right:auto;float:left;">上一步</div>
							<div onClick="jumpStep('next',2);" style="width:100px;height:30px;line-height:30px;text-align:center;cursor:pointer;background-color:#0060A6;color:white;margin-left:10px; margin-right:auto;float:left;">下一步</div>
						</div>
					</div>
				</div>
				<!-- 第二步结束 -->
				
				<!-- 第三步 -->
				<div id="step3" style="width:100%;height:400px;display:none;">
					<div style="width:100%;height:20px;line-height:20px;text-align:center;">分析图形</div>
					
					<s:iterator value="serviceResult.showCondition" id="sr" status="i">
						<s:if test="%{#sr.paramName=='strChartType' || #sr.paramName=='dimensions'}">
							<!-- 下拉多选框 -->
							<s:if test="%{#sr.singleTag=='multipleSelectField'}">
								<div class="Select_Menu_Control_Frame_DivList">
									<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
											<s:property value="%{#sr.fieldShowName}" />
									 </div>
									 <div class="Control_Frame">
									 <s:select cssClass="Select_MultipleSelectField" 
										list="#sr.tag" name="serviceParam.%{#sr.paramName}"
										multiple="true" label="%{#sr.fieldShowName}"
										value="%{#sr.fieldValue}"
										id="serviceParam.%{#sr.paramName}"
										onmousemove="changeOptionsTitle('serviceParam.%{#sr.paramName}');"/>
									 </div>
								</div>
							</s:if>
							
							<!-- 下拉框 -->
							<s:if test="%{#sr.singleTag=='selectField'}">
								<div class="Select_Menu_Control_Frame_Div">
									<div class="Title"
										title="<s:property value="%{#sr.fieldShowName}"/>"
										class="Font_css_normal_Condtion">
										<s:property value="%{#sr.fieldShowName}" />
									</div>
				
									<div class="Select_Menu_SelectBox_Div">
										<div class="Select_Menu_SelectBox_Div_Hidden">
											<s:select cssClass="Select_Menu_SelectBox_Div_Option"
												list="#sr.tag" id="serviceParam.%{#sr.paramName}"
												name="serviceParam.%{#sr.paramName}"
												headerKey="%{#application.emptySelectValueName}"
												headerValue="---请选择---"
												onmousemove="changeOptionsTitle('serviceParam.%{#sr.paramName}');" />
										</div>
									</div>
								</div>
							</s:if>
						</s:if>
					</s:iterator>
					<div style="clear:both;width:100%;height:0px;"></div>
					<!-- 步骤跳转按钮 -->
					<!-- <div style="clear:both;width:100%;height:30px;line-height:30px;align:center;">
						<div onClick="jumpStep('pre',3);" style="width:100px;height:30px;line-height:30px;text-align:center;cursor:pointer;background-color:#0060A6;color:white;margin-left:auto; margin-right:auto;margin-top:20px;">上一步</div>
					</div>
					 -->
					<div style="clear:both;width:100%;height:20px;"></div>
					<div style="clear:both;width:100%;height:30px;line-height:30px;align:center;">
						<div style="width:210px;height:30px;line-height:30px;margin-left:auto; margin-right:auto;">
							<div onClick="jumpStep('pre',3);" style="width:100px;height:30px;line-height:30px;text-align:center;cursor:pointer;background-color:#0060A6;color:white;margin-left:auto; margin-right:auto;float:left;">上一步</div>
							<s:iterator value="serviceResult.operationList" id="sr">
							<s:if test="%{#sr.operationType=='Select'}">
								<div id="chart" style="width:100px;height:30px;line-height:30px;text-align:center;cursor:pointer;background-color:#0060A6;color:white;margin-left:10px; margin-right:auto;float:left;">
							分析<input id="chartInput" type="hidden" value="<%=basePath %><s:property value="operationNamespace"/>/<s:property value="operationAction"/>"/>
							</div>
							</s:if>
						</s:iterator>
							
						</div>
					</div>
				</div>
				
				<div style="clear:both;"></div>
			</s:form>
			</div>


			<div id="Split_Div" style="margin-left: 1px; left: 350px; position: absolute;"></div>
			<div id="Grid_Task"
				style="left: 355px; top: 0px; width: 1024px; height: 100%; right: 0px; bottom: 0px; overflow-x: hidden; overflow-y: hidden; padding-top: 0px; position: absolute;">
				<iframe allowtransparency="true" src="" class="right_iframe"
					id="frmtreeright1" name="frmChart" marginwidth="0" 
					height="100%" width="100%" frameBorder=0  scrolling="yes">
				</iframe>
			</div>
		<script src="<%=basePath%>js/DatePicker/WdatePicker.js" type="text/javascript"></script>
	</body>
</html>