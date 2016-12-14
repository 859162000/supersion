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
		<title>ShowSaveOrUpdateReport</title>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/selectdown.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
		<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath %>js/Comml.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/ShowOrUpdateSet.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript" ></script>
		<style>
		
		.ts{
		float:left; 
		font-family:微软雅黑;
		color:Black;
		font-size:12px;
		cursor:pointer; 
		margin-left:7px; 
		width:450px; 
		text-align:left;
		}
		</style>
		
		<script type="text/javascript">

        function load(){
        	$("#DataDiv").children("div").find("[readonly='readonly']").css("color","#CCC");

			if(window.parent.location.href.indexOf("ShowTree") <= -1){
				$("body").removeAttr("style");
            }
            
            $("#loading").remove();
           	$("#query_hint").remove();
        	
        	
        	var URL_showModal = window.location.search;
						
        	if(URL_showModal.indexOf("showModal=true") >= 0){
        		$("#DataDiv").css("height","190px");
        		$("#DataDiv").children("div").css("margin-left","80px");//RemoveDiv("false1")
            	$("#buttons").children("[value='返回']").removeAttr('onclick');
            }

            // 加载机构集
            var instInfoIdList = document.getElementById("instSetID");
            var fileFileIdList = document.getElementById("fileSetID");
            var rptIdList = document.getElementById("rptSetID");
            var reportmodel_tableIdList = document.getElementById("reportModel_TableSetID");
            if(instInfoIdList != null){
            	showSet("instSetID", "serviceParam.instInfoIdList", "Inst");
            }
            if(fileFileIdList != null){
            	showSet("fileSetID", "serviceParam.fileFileIdList", "File");
            }
            if(rptIdList != null){
            	showSet("rptSetID", "serviceParam.rptInfoIdList", "Rpt");
            }
            if(reportmodel_tableIdList != null){
            	showSet("reportModel_TableSetID", "serviceParam.reportModel_TableIdList", "ReportModel_Table");
            }
        }

	    $(document).ready(function() {
	        $(":text").keydown(function(event) {
	            if (event.which == 27) {
	                return false;
	            }
	        });

	        $(document).keydown(function(event) {
	            if (event.which == 13) {
	                $("#buttons").children("[value='保存']").each(function(index) {
	                    var onchlick = $(this).attr("onclick");
	                    var conditionOne = onchlick.substring(onchlick.indexOf("'") + 1, onchlick.indexOf(",") - 1);
	                    var conditionTwo = onchlick.substring(onchlick.indexOf(",") + 2, onchlick.lastIndexOf("'"));
	                    $(this).bind('click', onOperationClick(conditionOne, conditionTwo));
	                });
	            }
	        });
	    });

        function RemoveDiv(isButone)
        {
            window.parent.RemoveBlockadeWindowDiv(isButone);
        }
        function onSelectChange(obj){
    		if(obj)
    		{
    			document.form1.action="<%=basePath%>${serviceResult.namespace}/${serviceResult.showUpdateListActionName}.action";
    			document.form1.submit();
    		}
    	}

    	function onOperationClick(actionNamespace,actionName)
    	{
    		document.form1.action="<%=basePath%>" + actionNamespace + "/" + actionName + ".action";
    		document.form1.submit();
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
        
    </script>
	</head>
<body id="bodID" onload="load()" style="overflow: hidden;" ondragstart="return false;">
	<div id="loading" class="loading" style="">
		<div id="query_hint" class="query_hint">
           		页面加载中，请稍等...
        </div>
    </div>
	<s:form name="form1" enctype="multipart/form-data">
	<div id="buttons" class="Menu_Frame_Div">
			<s:iterator value="serviceResult.operationList" id="sr">
				<s:if test="%{#sr.operationType=='Post'}">
					<a class="Menu_Frame_Div_Cell"
						href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"><s:property value="operationName" /> </a>

				</s:if>
				<s:elseif test="%{#sr.operationType=='Get'}">
					<a class="Menu_Frame_Div_Cell"
						href="javascript:onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action')"><s:property value="operationName" /> </a>
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Get1'}">
					<input class="button button:hover button:focus button:active" style="width:<s:property value="operationName.length()<=9?100:105+operationName.length()*11"/>px; height: 26px; margin-right: auto;"
						type="button" value="<s:property value="operationName"/>"
						onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action?id=<s:property value="serviceResult.id"/>')" />
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Get2'}">
					<input class="button button:hover button:focus button:active" style="width:<s:property value="operationName.length()<=9?100:105+operationName.length()*11"/>px; height: 26px; margin-right: auto;"
						type="button" value="<s:property value="operationName"/>"                                                                                                                                    
						onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction.substring(0,operationAction.indexOf('?'))"/>.action?id=<s:property value="serviceResult.id"/>&type=<s:property value="operationAction.substring(operationAction.indexOf('?')+6)"/>')" />
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='Upload'}">
					<s:file id="123456" class="files" name="uploadFile"></s:file>
					<a class="button chat icon"
						style="margin-left: 5px; margin-top: 2px; font-size: 12px; font-family: 微软雅黑;"
						href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"><s:property value="operationName" /> </a>
				</s:elseif>
			</s:iterator>
		</div>
		
		<div id="DataDiv" style="width:100%;float:left;overflow:auto;">
			<s:iterator value="serviceResult.showFieldValueList" id="sr" status="i">
					<s:if test="%{#sr.showField.singleTag=='textField'}">
					    <div class="UpSave_Control_Frame_Div">
							<!--正常文本框-->
							<div>
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="color:<s:property value="%{#sr.showColor}"/>; ">
									<samp>
										<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
												value="%{#sr.checkMessage}" /> </font>
									</samp>
								</div>
							</div>
							<!--i class="fa UpSave_TextBox_I"></i-->
							<s:textfield ttt="one"
								cssClass="UpSave_TextBox"
								name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"
								value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}" >
							</s:textfield>
						</div>
					</s:if>
					<s:elseif test="%{#sr.showField.singleTag=='selectField'}">
					    <s:if test="%{#sr.showField.errorSpace == false}">
					        <div class="UpSave_Control_Frame_Div" >
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
									style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<s:if test="#sr.readOnly==true">
									<s:hidden name="serviceParam.%{#sr.showField.paramName}"
										value="%{#sr.fieldValue}"></s:hidden>
								</s:if>
								<s:select sstyle="TextBox" class="TextBox TextBox:hover"
									style="float:left; margin-left:5px; width:135px;margin-top:4px;"
									list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									multiple="%{#sr.showField.selectMultiple}"
									value="%{#sr.fieldValue}"
									headerKey="%{#application.emptySelectValueName}"
									headerValue="---请选择---"
									onchange="onSelectChange(%{#sr.selectChange});"
									disabled="%{#sr.readOnly}" />
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
									<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
										<font size="2" color="<s:property value="%{#sr.showColor}"/>"><s:property
												value="%{#sr.checkMessage}" /> </font>
									</samp>
								</div>
							</div>
					    </s:if>
					    <s:else>
					        <div class="ts">
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
									style="float: left; width: 80px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<s:if test="#sr.readOnly==true">
									<s:hidden name="serviceParam.%{#sr.showField.paramName}"
										value="%{#sr.fieldValue}"></s:hidden>
								</s:if>
								<s:select sstyle="TextBox" class="TextBox TextBox:hover"
									style="float:left; margin-left:5px; width:135px;margin-top:4px;"
									list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									multiple="%{#sr.showField.selectMultiple}"
									value="%{#sr.fieldValue}"
									headerKey="%{#application.emptySelectValueName}"
									headerValue="---请选择---"
									onchange="onSelectChange(%{#sr.selectChange});"
									disabled="%{#sr.readOnly}" />
							</div>
					    </s:else>
					</s:elseif>
					<s:elseif test="%{#sr.showField.singleTag=='multipleSelectField'}">
					    <s:if test="%{#sr.showField.errorSpace == false}">
					    	<s:if test="%{#sr.showField.paramName == 'instInfoIdList'}"> <!-- 机构集 -->
								<div class="ts">
									<samp class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										机构集
								 	</samp>
									<div id="instSetID">
								   		<select style="float:left; margin-left:5px; width:250px;height:250px;margin-top:4px;margin-bottom: 2%;" multiple="multiple">
								   			<!-- <option>&nbsp;&nbsp;&nbsp;--请选择--&nbsp;&nbsp;&nbsp;</option>  -->
								   		</select>
									</div>
								</div>
					    	</s:if>
							<s:if test="%{#sr.showField.paramName == 'fileFileIdList'}"> <!-- 文件集 -->
								<div class="ts">
									<samp style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; 
										white-space: nowrap;text-overflow: ellipsis;">文件集 </samp>
									<div id="fileSetID">
								   		<select style="float:left; margin-left:5px; width:250px;height:250px;margin-top:4px;margin-bottom: 2%;" multiple="multiple">
								   		</select>
									</div>
								</div>
							</s:if>
							<s:if test="%{#sr.showField.paramName == 'rptInfoIdList'}"> <!-- 报表集 -->
								<div class="ts">
									<samp class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										报表集
									</samp>
									<div id="rptSetID">
								   		<select style="float:left; margin-left:5px; width:250px;height:250px;margin-top:4px;margin-bottom: 2%;" multiple="multiple">
								   		</select>
									</div>
								</div>
							</s:if>
							<s:if test="%{#sr.showField.paramName == 'reportModel_TableIdList'}"> <!-- 明细表集 -->
								<div class="ts">
									<samp class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										明细表集
									</samp>
									<div id="reportModel_TableSetID" style="float:left; width: 115px;">
								   		<select style="float:left; margin-left:5px; width:250px;height:250px;margin-top:4px;margin-bottom: 2%;" multiple="multiple">
								   		</select>
									</div>
								</div>
							</s:if>
							
							<div class="ts">
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
									style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<s:if test="%{#sr.showField.height != null}">
									<s:select sstyle="TextBox" class="TextBox TextBox:hover"
										list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
										multiple="true" label="%{#sr.showField.fieldShowName}"
										value="%{#sr.fieldValue}" 
										cssStyle="width:135px;float:left; margin-left:5px;margin-top:4px;margin-bottom: 2%;"
										/>
								</s:if>
								<s:else>
								    <s:select sstyle="TextBox" class="TextBox TextBox:hover"
										style="float:left; margin-left:5px; width:250px;height:250px;margin-top:4px;margin-bottom: 2%;"
										list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
										multiple="true" label="%{#sr.showField.fieldShowName}"
										value="%{#sr.fieldValue}" />
								</s:else>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
									<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
										<font size="2" color="<s:property value="%{#sr.showColor}"/>"><s:property value="%{#sr.checkMessage}" /> </font>
									</samp>
								</div>
							</div>
					    </s:if>
					    <s:else>
						    <div class="ts">
								<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
									style="float: left; width: 80px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
									<s:property value="%{#sr.showField.fieldShowName}" />
								</samp>
								<s:select sstyle="TextBox" class="TextBox TextBox:hover"
										list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
										multiple="true" label="%{#sr.showField.fieldShowName}"
										value="%{#sr.fieldValue}" 
										cssStyle="width:200px;height:200px;float:left; margin-left:5px;margin-top:4px;margin-bottom: 2%;"
										/>
							</div>
					    </s:else>
					</s:elseif>
					<s:elseif test="%{#sr.showField.singleTag=='fileField'}">
					    <div class="ts">
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
								style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<s:file sstyle="TextBox" class="TextBox TextBox:hover"
								style="float:left; margin-left:5px; width:130px;margin-top:4px;"
								name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"></s:file>
							<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
								<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
									<font size="2" color="<s:property value="%{#sr.showColor}"/>"><s:property
											value="%{#sr.checkMessage}" /> </font>
								</samp>
							</div>
						</div>
					</s:elseif>
					<s:elseif test="%{#sr.showField.singleTag=='multipleTextField'}">
						<div class="ts">
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
								style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<s:textarea sstyle="TextBox" class="TextBox TextBox:hover"
								style="float:left; margin-left:5px; width:130px;margin-top:4px;"
								name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"
								value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"></s:textarea>
							<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
								<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
									<font size="2" color="<s:property value="%{#sr.showColor}"/>"><s:property
											value="%{#sr.checkMessage}" /> </font>
								</samp>
							</div>
						</div>
					</s:elseif>
					<s:elseif test="%{#sr.showField.singleTag=='dateField'}">
						<div class="ts">
							<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
								style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
								<s:property value="%{#sr.showField.fieldShowName}" />
							</samp>
							<s:if test="#sr.readOnly==true">
								<s:textfield sstyle="TextBox" class="TextBox TextBox:hover"
									style="float:left; margin-left:5px; width:130px;margin-top:4px;"
									name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"></s:textfield>
								<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
								<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
									<font size="2" color="<s:property value="%{#sr.showColor}"/>"><s:property
											value="%{#sr.checkMessage}" /> </font>
								</samp>
							</div>
							</s:if>
							<s:elseif test="#sr.readOnly==false">
								<s:textfield sstyle="TextBox" class="TextBox TextBox:hover"
									style="float:left; margin-left:5px; width:130px;margin-top:4px;"
									name="serviceParam.%{#sr.showField.paramName}"
									onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"
									label="%{#sr.showField.fieldShowName}"
									value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"></s:textfield>
									<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
										<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
											<font size="2" color="<s:property value="%{#sr.showColor}"/>"><s:property
													value="%{#sr.checkMessage}" /> </font>
										</samp>
									</div>
							</s:elseif>
						</div>
					</s:elseif>
					<s:elseif test="%{#sr.showField.singleTag=='hiddenField'}">
						<s:hidden name="serviceParam.%{#sr.showField.paramName}"
							value="%{#sr.fieldValue}"></s:hidden>
					</s:elseif>
			</s:iterator>
		</div>
		<s:hidden id="windowId" name="windowId"></s:hidden>
	</s:form>
</body>
</html>
