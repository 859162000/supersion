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
		<title>ShowSaveOrUpdate_RoleRptData</title>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="dtree/dtree.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/selectdown.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
		<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>dtree/dtreeTest.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/Shortcutkeys.js" type="text/javascript" ></script>
		<script src="<%=basePath%>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
		<script src="<%=basePath%>js/DatePicker/WdatePicker.js" type="text/javascript" ></script>
		<script src="<%=basePath%>js/SelectExtend.js" type="text/javascript"> </script>
		<style type="text/css">
			ul {margin: 0;padding: 0}
			ul li {list-style-type: none;}
			.selectClass {float: left;width: 130px;margin-top: 4px;}
			.popdiv {z-index: 100;position: absolute;overflow-x: hidden;overflow-y: scroll;border: 1px solid #ccc;
				background: white;display: none}
			.triangle {text-decoration: none;color: skyblue;}
		</style>
		<script type="text/javascript">

		    function load() {
		        $("#DataDiv").children("div").find("[readonly='readonly']").css("color", "#CCC");
		        if (window.parent.location.href.indexOf("ShowTree") <= -1) {
		            $("body").removeAttr("style");
		        }
		        if ($(":file").attr("id") == "123456") {
		            $("#from1").attr("enctype", "multipart/form-data");
		        }

		        $("#loading").remove();

		        var URL_showModal = window.location.search;

		        if (URL_showModal.indexOf("showModal=true") >= 0) {
		            $("#DataDiv").css("height", "190px");
		            $("#DataDiv").children("div").css("margin-left", "80px"); //RemoveDiv("false1")
		            $("#buttons").children("[value='返回']").removeAttr('onclick');
		        }
		        AutiSize();
		    }

		    $(document).ready(function() {
		        $("#buttons").children("[value='返回']").bind('click', function() {
		            if (window.location.search.indexOf("showModal=true") >= 0)
		                RemoveDiv('true1');
		        });
		    });

		    function RemoveDiv(isButone) {
		        window.parent.RemoveBlockadeWindowDiv(isButone);
		    }
		    function onSelectChange(obj) {
		        if (obj) {
		            document.form1.action = "<%=basePath%>${serviceResult.namespace}/${serviceResult.showUpdateListActionName}.action";
		            document.form1.submit();
		        }
		    }

        	function onOperationAlertClick(actionNamespace,actionName,page,name){
            	var gnl=confirm("确定要"+name+"操作吗?");
            	if(gnl){
            		onOperationClick(actionNamespace, actionName);
            	}
            }
            
		    function onOperationClick(actionNamespace, actionName) {
		        document.form1.action = "<%=basePath%>" + actionNamespace + "/" + actionName + ".action";
		        document.form1.submit();
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
		    $(document).ready(function() {
		        $(":text").keydown(function(event) {
		            if (event.which == 27) {
		                return false;
		            }
		        });
		        
				var flag=0;
				$("input[type=text], input[type=password], textarea").focus(function(){
	   				flag=1;
				});
				$("textarea").focus(function(){
	   				flag=2;
				});
				$("input[type=text], input[type=password], textarea").blur(function(){
	   				flag=0;
				});
	            $(document).keydown(function(event){
		             if(flag==0){
		                if(event.which == 13){
		                	$("#buttons").children("[value='保存']").each(function(index){
		                    	var onchlick = $(this).attr("onclick");
		                    	var conditionOne = onchlick.substring(onchlick.indexOf("'")+1,onchlick.indexOf(",")-1);
		                    	var conditionTwo = onchlick.substring(onchlick.indexOf(",")+2,onchlick.lastIndexOf("'"));
		                    	$(this).bind('click',onOperationClick(conditionOne,conditionTwo));
		                    });
		                }
		             }
		             else if(flag==1 && event.which == 13){
						return false;
		             }
	            }); 
            
 				/*
 				new Drag("ShowErrMes"); // 让窗口可以拖动  已经固定窗体，注释
 				// 给窗体背景色变化  去除
				 if($("#ShowErreSub").text().replace(" ","") != "")
				{
					$("#ShowErrMes_Div").css("background-color","red");
				}
				else
				{
					$("#ShowErrMes_Div").css("background-color","#0060A6");
				} */
		    });
		    function Time() {
		        if ($("#loading").hasClass("loading") == true) {
		            var gnl = confirm("页面加载错误,是否重新加载？")
		            if (gnl == true) {
		                location.reload();
		                return true;
		            } else {
		                $("#loading").remove();
		                return;
		            }

		        }
		    }

		    function onKeyPrice(t) {
		        var startWithSub = false;
		        if (t.value.length > 0 && t.value.substring(0, 1) == "-") {
		            startWithSub = true;
		        }
		        var stmp = "";
		        if (t.value == stmp) return;
		        var ms = t.value.replace(/[^\d\.]/g, "").replace(/(\.\d{100}).+$/, "$1").replace(/^0+([1-9])/, "$1").replace(/^0+$/, "0");
		        var txt = ms.split(".");
		        while (/\d{4}(,|$)/.test(txt[0]))
		            txt[0] = txt[0].replace(/(\d)(\d{3}(,|$))/, "$1,$2");
		        t.value = stmp = txt[0] + (txt.length > 1 ? "." + txt[1] : "");
		        if (startWithSub) {
		            t.value = "-" + t.value;
		        }
		    }
		    
	    // 待修正
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
        
		function onBlur(t,autoFill){
    		// xml配置小数精确位（只允许小数位大于0），则进入判断
			if(autoFill>0){
				var tValue = (t.value).replace(/,/g,"");
				// 加并且判断：t.value.length<17 因字符串转Float只支持16位数值的转换，超过则不作处理
				if(t.value!="" && t.value.split("").reverse().join("").indexOf(".")<autoFill && tValue.length<17){
					if(!isNaN(t.value)){
						t.value = parseFloat(t.value).toFixed(autoFill);
					}
					else if(!isNaN(tValue)){
						t.value = parseFloat(tValue).toFixed(autoFill);
						onKeyPrice(t);
					}
				}
			}
        }
        function loadTree(){
        	$(".dtree").find("#treeCheckbox").css("margin-bottom","0px");
			$(".dtree").find("div").css("font-size","13px");
			$("#checkAll").live('click',function(){
				$("#checkBoxTree").children(".dtree").find(".dTreeNode").children(":checkbox").attr("checked",true);
			});
			$("#checkCancel").live('click',function(){
				$("#checkBoxTree").children(".dtree").find(".dTreeNode").children(":checkbox").removeAttr("checked");
			});
			$("#treeCheckbox").live('click',function(){
				var xuanzhong = $(this).attr("checked");
				var peradID = $(this).prev("a").attr("peradid");
				if(xuanzhong){
					$(this).parents("[peradid]").prev().find(":checkbox").attr("checked",true);
					$("#dd"+peradID).find("#treeCheckbox").attr("checked",true);
				}else{
					$(this).parents(".clip").each(function(indexClip){
						var peradid = $(this).attr("peradid");
						if(peradid >= 1){
							var checkedCount = new Number(0);
							$(this).children(".dTreeNode").each(function(indexChildren){
								var isChecked = $(this).children("#treeCheckbox").attr("checked");
								if(isChecked == 'checked')checkedCount+=1;
							});
							if(checkedCount == 0){
								$(this).prev().children("#treeCheckbox").removeAttr("checked");
							}
						}
					});
					$("#dd"+peradID).find("#treeCheckbox").removeAttr("checked");
				}
			});
        }   
    </script>
    
	</head>
<body onResize="AutiSize();" id="bodID" onload="load()" style="overflow: hidden;z-index: 1;" ondragstart="return false;">

	<div id="loading" class="loading" style="">
		<div id="query_hint" class="query_hint">
        	页面加载中，请稍等...
        </div>
    </div>

	<s:form name="form1" id="from1" enctype="multipart/form-data" method="POST">
		<div id="buttons" class="Menu_Frame_Div">
			<s:iterator value="serviceResult.operationList" id="sr">
				<s:if test="%{#sr.operationType=='Post'}">
					<a class="Menu_Frame_Div_Cell" href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"
						style="width:<s:property value='(width!=null && width!="")?width'/>px; height:25px; color:<s:property value="color"/>;">
						<s:if test="%{#sr.imageUrl==null||imageUrl.equals('')}">
							<s:property value="operationName" />
						</s:if>
						<s:else>
							<img src="<%=basePath%><s:property value="imageUrl"/>" width="<s:property value='(width!=null && width!="")?width:(operationName.length()<=9?100:105+operationName.length()*11)'/>px" height="25px" />
						</s:else>
					</a>
				</s:if>
				<s:if test="%{#sr.operationType=='PostConfirm'}">
					<a class="Menu_Frame_Div_Cell" href="javascript:onOperationAlertClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>','','<s:property value="operationName" />')"
						style="width:<s:property value='(width!=null && width!="")?width'/>px; height:25px; color:<s:property value="color"/>;">
						<s:if test="%{#sr.imageUrl==null||imageUrl.equals('')}">
							<s:property value="operationName" />
						</s:if>
						<s:else>
							<img src="<%=basePath%><s:property value="imageUrl"/>" width="<s:property value='(width!=null && width!="")?width:(operationName.length()<=9?100:105+operationName.length()*11)'/>px" height="25px" />
						</s:else>
					</a>
				</s:if>
				<s:elseif test="%{#sr.operationType=='Get'}">
					<a class="Menu_Frame_Div_Cell" href="javascript:onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action')"
						style="width:<s:property value='(width!=null && width!="")?width'/>px; height:25px; color:<s:property value="color"/>;">
						<s:if test="%{#sr.imageUrl==null||imageUrl.equals('')}">
							<s:property value="operationName" />
						</s:if>
						<s:else>
							<img src="<%=basePath%><s:property value="imageUrl"/>" width="<s:property value='(width!=null && width!="")?width:(operationName.length()<=9?100:105+operationName.length()*11)'/>px" height="25px" />
						</s:else>
					</a>
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Get1'}">
					<a class="Menu_Frame_Div_Cell" href="javascript:onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action?id=<s:property value="serviceResult.id"/>')" 
						style="width:<s:property value='(width!=null && width!="")?width'/>px; height:25px; color:<s:property value="color"/>;">
						<s:if test="%{#sr.imageUrl==null||imageUrl.equals('')}">
							<s:property value="operationName" />
						</s:if>
						<s:else>
							<img src="<%=basePath%><s:property value="imageUrl"/>" width="<s:property value='(width!=null && width!="")?width:(operationName.length()<=9?100:105+operationName.length()*11)'/>px" height="25px" />
						</s:else>
					</a>
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Get2'}">
					<a class="Menu_Frame_Div_Cell" href="javascript:onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction.substring(0,operationAction.indexOf('?'))"/>.action?id=<s:property value="serviceResult.id"/>&type=<s:property value="operationAction.substring(operationAction.indexOf('?')+6)"/>')" 
						style="width:<s:property value='(width!=null && width!="")?width'/>px; height:25px; color:<s:property value="color"/>;">
						<s:if test="%{#sr.imageUrl==null||imageUrl.equals('')}">
							<s:property value="operationName" />
						</s:if>
						<s:else>
							<img src="<%=basePath%><s:property value="imageUrl"/>" width="<s:property value='(width!=null && width!="")?width:(operationName.length()<=9?100:105+operationName.length()*11)'/>px" height="25px" />
						</s:else>
					</a>
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Upload'}">
					<s:file id="123456" class="files" cssStyle="float:left; margin-left:2px; font-family:'Microsoft Yahei'; font-size:11px;" name="uploadFile"></s:file>
					<a class="Menu_Frame_Div_Cell" href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"
						style="width:<s:property value='(width!=null && width!="")?width'/>px; height:25px; color:<s:property value="color"/>;">
						<s:if test="%{#sr.imageUrl==null||imageUrl.equals('')}">
							<s:property value="operationName" />
						</s:if>
						<s:else>
							<img src="<%=basePath%><s:property value="imageUrl"/>" width="<s:property value='(width!=null && width!="")?width:(operationName.length()<=9?100:105+operationName.length()*11)'/>px" height="25px" />
						</s:else>
					</a>
				</s:elseif>
			</s:iterator>
		</div>
		<div id="ShowErrMes" class="ShowErrMes_Div">
			<div id="ShowErreSub"><s:actionerror/></div>
		</div>
		<div id="DataDiv" style="width:100%;float:left;overflow:auto;">
			<s:iterator value="serviceResult.showFieldValueList" id="sr" status="i">
				<s:if test="%{#sr.showField.thousandth == true}">
					<div class="UpSave_Control_Frame_Div">
						<!--带千分位数字格式的文本框-->
						<div>
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
								<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>;">
								<samp>
									<font color="<s:property value="%{#sr.showColor}"/>">
										<s:property value="%{#sr.checkMessage}" /> 
									</font>
								</samp>
							</div>
						</div>
						<s:if test="#sr.readOnly == false">
							<s:textfield number='true' cssClass="UpSave_TextBox"
								name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"
								value="%{#sr.fieldValue}" 
								onkeyup="onKeyPrice(this);"
								onblur="onBlur(this,%{#sr.showField.autoFill})">
							</s:textfield>
						</s:if>
						<s:else>
							<s:textfield number='true' cssClass="UpSave_TextBox"
								name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"
								value="%{#sr.fieldValue}" 
								readonly="%{#sr.readOnly}"
								onfocus="this.blur();"
								onkeyup="onKeyPrice(this);">
							</s:textfield>
						</s:else>
					</div>
				</s:if>
				<s:elseif test="%{#sr.showField.singleTag=='textField'}">
				    <div class="UpSave_Control_Frame_Div">
						<!--正常文本框-->
						<div>
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
								<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
								<samp>
									<font color="<s:property value="%{#sr.showColor}"/>">
										<s:property value="%{#sr.checkMessage}" /> 
									</font>
								</samp>
							</div>
						</div>
						<!--i class="fa UpSave_TextBox_I"></i-->
						<s:if test="#sr.readOnly == false">
							<s:textfield ttt="one"
								cssClass="UpSave_TextBox"
								name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"
								value="%{#sr.fieldValue}" 
								onblur="onBlur(this,%{#sr.showField.autoFill})"
								readonly="%{#sr.readOnly}" >
							</s:textfield>
						</s:if>
						<s:else>
							<s:textfield ttt="one"
								cssClass="UpSave_TextBox"
								name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"
								value="%{#sr.fieldValue}" 
								onfocus="this.blur();"
								readonly="%{#sr.readOnly}" >
							</s:textfield>
						</s:else>
					</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='textPassWordField'}">
					<div class="UpSave_Control_Frame_Div">
						<!--密码文本框-->
						<div>
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
								<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
								<samp>
									<font color="<s:property value="%{#sr.showColor}"/>">
										<s:property value="%{#sr.checkMessage}" /> 
									</font>
								</samp>
							</div>
						</div>
						<s:if test="#sr.readOnly == false">	
							<input type="password" 
								name="serviceParam.<s:property value="%{#sr.showField.paramName}"/>" 
						       	value="<s:property value="%{#sr.fieldValue}"/>"
						       	class="UpSave_TextBox"/>
						</s:if>
						<s:else>
							<input type="password" name="serviceParam.<s:property value="%{#sr.showField.paramName}"/>" 
						       value="<s:property value="%{#sr.fieldValue}"/>" 
						       class="UpSave_TextBox" 
						       readonly="readonly" 
						       onfocus="this.blur();"/>
						</s:else>
					</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='selectField'}">
				    <s:if test="%{#sr.showField.errorSpace == false}">
						<div class="UpSave_Control_Frame_Div" >
							<!--没有错误提示的下拉框-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:hidden name="serviceParam.%{#sr.showField.paramName}"
									value="%{#sr.fieldValue}">
								</s:hidden>
								<div class="UpSave_Control_Frame_Div_selectField_readOnly">
									<div class="UpSave_Control_Frame_Div_selectField_Hidden">
										<s:select cssClass="UpSave_Control_Frame_Div_selectField_Option_readOnly"
											list="#sr.tag" 
											name="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											multiple="%{#sr.showField.selectMultiple}"
											value="%{#sr.fieldValue}"
											headerKey="%{#application.emptySelectValueName}"
											headerValue="---请选择---"
											onchange="onSelectChange(%{#sr.selectChange});"
											disabled="%{#sr.readOnly}" 
											id="serviceParam.%{#sr.showField.paramName}"
											onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');"/>
									</div>
								</div>
							</s:if>
							<s:else>
								<div class="UpSave_Control_Frame_Div_selectField">
									<div class="UpSave_Control_Frame_Div_selectField_Hidden">
										<s:select cssClass="UpSave_Control_Frame_Div_selectField_Option"
											list="#sr.tag" 
											name="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											multiple="%{#sr.showField.selectMultiple}"
											value="%{#sr.fieldValue}"
											headerKey="%{#application.emptySelectValueName}"
											headerValue="---请选择---"
											onchange="onSelectChange(%{#sr.selectChange});"
											disabled="%{#sr.readOnly}" 
											id="serviceParam.%{#sr.showField.paramName}"
											onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');"/>
									</div>
								</div>
							</s:else>
						</div>
				    </s:if>
				    <s:else>
						<div class="UpSave_Control_Frame_Div">
							<!--有错误提示的下拉框-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:hidden name="serviceParam.%{#sr.showField.paramName}" value="%{#sr.fieldValue}"></s:hidden>
							</s:if>
							<div class="UpSave_Control_Frame_Div_selectField">
								<div class="UpSave_Control_Frame_Div_selectField_Hidden">
									<s:select cssClass="UpSave_Control_Frame_Div_selectField_Option"
										list="#sr.tag" 
										name="serviceParam.%{#sr.showField.paramName}"
										label="%{#sr.showField.fieldShowName}"
										multiple="%{#sr.showField.selectMultiple}"
										value="%{#sr.fieldValue}"
										headerKey="%{#application.emptySelectValueName}"
										headerValue="---请选择---"
										onchange="onSelectChange(%{#sr.selectChange});"
										disabled="%{#sr.readOnly}" 
										id="serviceParam.%{#sr.showField.paramName}"
										onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');"/>
								</div>
							</div>
						</div>
				    </s:else>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='selectExtendField'}">
					<div class="UpSave_Control_Frame_Div">
						<div>
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<div title='<s:property value="%{#sr.checkMessage}"/>' style='color:<s:property value="%{#sr.showColor}"/>; '>
								<samp>
									<font color="<s:property value="%{#sr.showColor}"/>">
										<s:property value="%{#sr.checkMessage}" /> 
									</font>
								</samp>
							</div>
						</div>
						<div>
							<s:hidden name="serviceParam.%{#sr.showField.paramName}" value="%{#sr.fieldValue}"></s:hidden>
							<input class="UpSave_TextBox selectClass TextBox TextBox:hover"  style="width:250px"
								<s:if test="#sr.readOnly==true">disabled</s:if>
								 value="<s:property value="%{#sr.tag[0]}" />" />										
							<div class="popdiv" style="overflow:auto;">
								<ul>
									<li code='<s:property value="%{#application.emptySelectValueName}" />'>---请选择---</li>
									<s:property value='%{#sr.tag[1]}' escape="false"/>											
								</ul>
							</div>
						</div>
					</div>
				</s:elseif>
				<!-- 模态对话框 -->
				<s:elseif test="%{#sr.showField.singleTag=='modelField'}">
					<div class="UpSave_Control_Frame_Div">
						<div>
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
								<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
								<samp>
									<font color="<s:property value="%{#sr.showColor}"/>">
										<s:property value="%{#sr.checkMessage}" /> 
									</font>
								</samp>
							</div>
						</div>
						<div style="position:relative;">							
							<s:textfield cssClass="UpSave_TextBox" name="serviceParam.%{#sr.showField.paramName}"
								readonly="true" 
								label="%{#sr.showField.fieldShowName}"
								value="%{#sr.fieldValue}">
							</s:textfield>
							<s:if test="%{#sr.readOnly == false}">
							<div name='popBtn' url='<%=basePath%><s:property value="%{#sr.tag}" />' style="background-image: url('images/button/fdj.png');position:absolute;width:22px;height:22px;top:5px; left:245px;z-index:99;"></div>
							<!-- <input name='popBtn' type='button' url='<%=basePath%><s:property value="%{#sr.tag}" />' value='...' />-->
							</s:if>
						</div>	
					</div>					
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='multipleSelectField'}">
				    <s:if test="%{#sr.showField.errorSpace == false}">
						<div class="UpSave_Control_Frame_Div" style="height:205px;">
							<!--没有错误提示的多选框类似Listbox-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:hidden name="serviceParam.%{#sr.showField.paramName}"
									value="%{#sr.fieldValue}">
								</s:hidden>
							</s:if>
							<s:if test="%{#sr.showField.height != null}">
								<s:select cssClass="UpSave_MultipleSelectField"
									list="#sr.tag" 
									name="serviceParam.%{#sr.showField.paramName}"
									multiple="true" label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" 
									id="serviceParam.%{#sr.showField.paramName}"
									onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');"/>
							</s:if>
							<s:else>
							    <s:select cssClass="UpSave_MultipleSelectField" 
									list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
									multiple="true" label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" 
									id="serviceParam.%{#sr.showField.paramName}"
									onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');"/>
							</s:else>
						</div>
				    </s:if>
				    <s:else>
						<div class="UpSave_Control_Frame_Div"  style="height:205px;">
							<!--有错误提示的多选框类似Listbox-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:select cssClass="UpSave_MultipleSelectField"
									style="height:200px;"
									list="#sr.tag" 
									name="serviceParam.%{#sr.showField.paramName}"
									multiple="true" label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" 
									id="serviceParam.%{#sr.showField.paramName}"
									onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');"/>
						</div>
				    </s:else>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='treeField'}">
					<div class="UpSave_Control_Frame_Div_TreeField" style="height:205px;">
						<!-- 这是一种未知模式的Listbox -->
						<div>
						<input type="hidden" name='__checkbox_serviceParam.<s:property value="%{#sr.showField.paramName}"/>' value='' />
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" style="width: 70px;" class="UpSave_Control_Frame_Div_TreeField_samp">
								<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
								<samp>
									<font color="<s:property value="%{#sr.showColor}"/>">
										<s:property value="%{#sr.checkMessage}" /> 
									</font>
								</samp>
						</div>
						<script type="text/javascript">
							$(document).ready(function(){
								loadTree();
							});
						</script>
						<div id="checkBoxTree" style="width:300px;height:300px;float:left; margin-left:5px;margin-top:4px;margin-bottom: 2%;border: 1px solid #CDCDCD;overflow-y: auto;overflow-x:hidden;">
							<script type="text/javascript">
								d = new dTree('d','<%=basePath %>');
								d.add(0,-1,'');
								<s:iterator value="#sr.tag" id="vt">
									d.add('<s:property value="%{#vt.functionCode}"/>','<s:property value="%{#vt.functionParentId}"/>','<s:property value="%{#vt.functionName}"/>','<s:property value="%{#sr.fieldValue}"/>','','','','');
								</s:iterator>
								document.write(d);
							</script>
						</div> 
					</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='fileField'}">
						<div class="UpSave_Control_Frame_Div" >
							<!--文件上传控件-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:hidden name="serviceParam.%{#sr.showField.paramName}" 
									value="%{#sr.fieldValue}">
								</s:hidden>
							</s:if>
							<s:file id="123456" cssClass="UpSave_SelectBox"
								name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"
								disabled="%{#sr.readOnly}">
							</s:file>
						</div>
						
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='multipleTextField'}">
					<div class="UpSave_Control_Frame_Div" style="height:140px;width:605px;float:left;/*float:none;*/">
						<!--多行文本框，类似论坛回复框-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:textarea cssClass="UpSave_Textarea"
									name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}"
									readonly="#sr.readOnly" 
									onfocus="this.blur();">
								</s:textarea>
							</s:if>
							<s:else>
								<s:textarea cssClass="UpSave_Textarea"
									name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}"
									readonly="#sr.readOnly" >
								</s:textarea>
							</s:else>
						</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='dateField'}">
					<div class="UpSave_Control_Frame_Div">
							<!--带分隔符的日期控件，文本框[yyyy-MM-dd]-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" 
									readonly="%{#sr.readOnly}" 
									onfocus="this.blur();">
								</s:textfield>
							</s:if>
							<s:else>
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}"></s:textfield>
							</s:else>
						</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='dateFieldNoSlash'}">
					<div class="UpSave_Control_Frame_Div">
						<!--不带分隔符的日期控件，文本框[yyyyMMdd]-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" 
									readonly="%{#sr.readOnly}" 
									onfocus="this.blur();">
								</s:textfield>
							</s:if>
							<s:else>
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMMdd'})"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" >
								</s:textfield>
							</s:else>
					</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='dateFieldMonth'}">
					<div class="UpSave_Control_Frame_Div">
						<!--不带分隔符的日期控件，文本框[yyyyMM]-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" 
									readonly="%{#sr.readOnly}" 
									onfocus="this.blur();">
								</s:textfield>
							</s:if>
							<s:else>
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMM'})"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" >
								</s:textfield>
							</s:else>
					</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='dateFieldHMS'}">
					<div class="UpSave_Control_Frame_Div">
						<!--不带分隔符的日期控件，文本框[yyyyMM]-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" 
									readonly="%{#sr.readOnly}" 
									onfocus="this.blur();">
								</s:textfield>
							</s:if>
							<s:else>
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									onfocus="WdatePicker({doubleCalendar:true,dateFmt:'HHmmss'})"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" >
								</s:textfield>
							</s:else>
					</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='dateFieldSlashHMS'}">
					<div class="UpSave_Control_Frame_Div">
							<!--带分隔符的日期控件，文本框[yyyy-MM-dd]-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /></span>
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font color="<s:property value="%{#sr.showColor}"/>">
											<s:property value="%{#sr.checkMessage}" /> 
										</font>
									</samp>
								</div>
							</div>
							<s:if test="#sr.readOnly==true">
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" 
									readonly="%{#sr.readOnly}" 
									onfocus="this.blur();">
								</s:textfield>
							</s:if>
							<s:else>
								<s:textfield cssClass="UpSave_TextBox"
									name="serviceParam.%{#sr.showField.paramName}"
									onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy/MM/dd HH:mm:ss'})"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}"></s:textfield>
							</s:else>
						</div>
				</s:elseif>
				<s:elseif test="%{#sr.showField.singleTag=='hiddenField'}">
					<!--隐藏控件-->
					<s:hidden name="serviceParam.%{#sr.showField.paramName}"
						value="%{#sr.fieldValue}"
						style="background-color:Black;">
					</s:hidden>
				</s:elseif>
			</s:iterator>
		</div>
		
		<s:hidden id="windowId" name="windowId"></s:hidden>
	</s:form>
</body>
</html>

