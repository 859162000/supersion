<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<div id="ShowErrMes" class="ShowErrMes_Div">
	<div id="ShowErreSub">
		<s:actionerror />
	</div>
</div>
<div id="DataDiv" style="width: 100%; float: left; overflow: auto;">
	<s:iterator value="#showObject.showFieldValueList" id="sr" status="i">
		<s:if test="%{#sr.showField.paramName == 'suitCode'}">
			<div class="UpSave_Control_Frame_Div">
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
				</div>
				<div class="UpSave_Control_Frame_Div_selectField">
							<div class="UpSave_Control_Frame_Div_selectField_Hidden">
								<s:select cssClass="UpSave_Control_Frame_Div_selectField_Option"
									list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									multiple="%{#sr.showField.selectMultiple}"
									value="%{#sr.fieldValue}"
									headerKey="%{#application.emptySelectValueName}"
									headerValue="---请选择---"
									disabled="%{#sr.readOnly}"
									id="serviceParam.%{#sr.showField.paramName}" name="suitCode"
									onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');" />
							</div>
						</div>
			</div>
		</s:if>
		<s:elseif test="%{#sr.showField.paramName == 'strItemCode'}">
			<div class="UpSave_Control_Frame_Div" style="height:300px;">
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
				</div>
				<s:textfield ttt="one" cssClass="UpSave_TextBox" name="inputItemCodeHelper" id="inputItemCodeHelper" label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}">
				</s:textfield>
				<div style="height:200px;width:262px;">
					<select id="target" class="UpSave_MultipleSelectField" style="width:100%;height:100%;overflow:auto;" multiple="multiple"></select>
				</div>
			</div>
		</s:elseif>
		
		<s:elseif test="%{#sr.showField.paramName == 'itemIds'}">
			<div class="UpSave_Control_Frame_Div" style="height:300px;margin-top:65px;margin-left:-20px;">
				<div style="height:200px;width:262px;">
					<s:select cssClass="UpSave_MultipleSelectField" list="#sr.tag"
							name="serviceParam.%{#sr.showField.paramName}" multiple="true"
							label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
							id="selectedTarget" style="width:100%;height:100%;overflow:auto;"
							 />
				</div>
			</div>
			<s:if test="%{#sr.showField.singleTag=='multipleSelectField'}">
				<script type="text/javascript">
					// 展示出选择的指标信息
					$("#target").dblclick(function(){
						addSelectedTargetOptions();
					});
				</script>
			</s:if>
			<s:else>
				<script type="text/javascript">
					// 展示出选择的指标信息
					$("#target").dblclick(function(){
						addSelectedTargetOption();
					});
				</script>
			</s:else>
		</s:elseif>
		
	</s:iterator>
</div>