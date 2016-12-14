<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
			
%>
<div id="ShowErrMes" class="ShowErrMes_Div">
	<div id="ShowErreSub">
		<s:actionerror />
	</div>
</div>
<div id="DataDiv" style="width: 100%; float: left; overflow: auto;">
	
	<s:iterator value="#showObject.showFieldValueList" id="sr" status="i">
		<s:if test="%{#sr.showField.thousandth == true}">
			<div class="UpSave_Control_Frame_Div">
				<!--带千分位数字格式的文本框-->
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>;">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<s:if test="#sr.readOnly == false">
					<s:textfield number='true' cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						onkeyup="onKeyPrice(this);"
						onblur="onBlur(this,%{#sr.showField.autoFill})">
					</s:textfield>
				</s:if>
				<s:else>
					<s:textfield number='true' cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						readonly="%{#sr.readOnly}" onfocus="this.blur();"
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
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<!--i class="fa UpSave_TextBox_I"></i-->
				<s:if test="#sr.readOnly == false">
					<s:textfield ttt="one" cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						onblur="onBlur(this,%{#sr.showField.autoFill})"
						readonly="%{#sr.readOnly}">
					</s:textfield>
				</s:if>
				<s:else>
					<s:textfield ttt="one" cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						onfocus="this.blur();" readonly="%{#sr.readOnly}">
					</s:textfield>
				</s:else>
			</div>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='textPassWordField'}">
			<div class="UpSave_Control_Frame_Div">
				<!--密码文本框-->
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<s:if test="#sr.readOnly == false">
					<input type="password"
						name="serviceParam.<s:property value="%{#sr.showField.paramName}"/>"
						value="<s:property value="%{#sr.fieldValue}"/>"
						class="UpSave_TextBox" />
				</s:if>
				<s:else>
					<input type="password"
						name="serviceParam.<s:property value="%{#sr.showField.paramName}"/>"
						value="<s:property value="%{#sr.fieldValue}"/>"
						class="UpSave_TextBox" readonly="readonly" onfocus="this.blur();" />
				</s:else>
			</div>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='selectField'}">
			<s:if test="%{#sr.showField.errorSpace == false}">
				<div class="UpSave_Control_Frame_Div">
					<!--没有错误提示的下拉框-->
					<div>
						<samp
							title="<s:property value="%{#sr.showField.fieldShowName}" />">
							<span color="red"><s:property
									value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
							<s:property value="%{#sr.showField.fieldShowName}" />
						</samp>
						<div title="<s:property value="%{#sr.checkMessage}"/>"
							style="color:<s:property value="%{#sr.showColor}"/>; ">
							<samp>
								<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
										value="%{#sr.checkMessage}" /> </font>
							</samp>
						</div>
					</div>
					<s:if test="#sr.readOnly==true">
						<s:hidden name="serviceParam.%{#sr.showField.paramName}"
							value="%{#sr.fieldValue}">
						</s:hidden>
						<div class="UpSave_Control_Frame_Div_selectField_readOnly">
							<div class="UpSave_Control_Frame_Div_selectField_Hidden">
								<s:select
									cssClass="UpSave_Control_Frame_Div_selectField_Option_readOnly"
									list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									multiple="%{#sr.showField.selectMultiple}"
									value="%{#sr.fieldValue}"
									headerKey="%{#application.emptySelectValueName}"
									headerValue="---请选择---"
									onchange="onSelectChange(%{#sr.selectChange});"
									disabled="%{#sr.readOnly}"
									id="serviceParam.%{#sr.showField.paramName}"
									onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');" />
							</div>
						</div>
					</s:if>
					<s:else>
						<div class="UpSave_Control_Frame_Div_selectField">
							<div class="UpSave_Control_Frame_Div_selectField_Hidden">
								<s:select cssClass="UpSave_Control_Frame_Div_selectField_Option"
									list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
									label="%{#sr.showField.fieldShowName}"
									multiple="%{#sr.showField.selectMultiple}"
									value="%{#sr.fieldValue}"
									headerKey="%{#application.emptySelectValueName}"
									headerValue="---请选择---"
									onchange="onSelectChange(%{#sr.selectChange});"
									disabled="%{#sr.readOnly}"
									id="serviceParam.%{#sr.showField.paramName}"
									onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');" />
							</div>
						</div>
					</s:else>
				</div>
			</s:if>
			<s:else>
				<div class="UpSave_Control_Frame_Div">
					<!--有错误提示的下拉框-->
					<div>
						<samp
							title="<s:property value="%{#sr.showField.fieldShowName}" />">
							<span color="red"><s:property
									value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
							<s:property value="%{#sr.showField.fieldShowName}" />
						</samp>
						<div title="<s:property value="%{#sr.checkMessage}"/>"
							style="color:<s:property value="%{#sr.showColor}"/>; ">
							<samp>
								<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
										value="%{#sr.checkMessage}" /> </font>
							</samp>
						</div>
					</div>
					<s:if test="#sr.readOnly==true">
						<s:hidden name="serviceParam.%{#sr.showField.paramName}"
							value="%{#sr.fieldValue}"></s:hidden>
					</s:if>
					<div class="UpSave_Control_Frame_Div_selectField">
						<div class="UpSave_Control_Frame_Div_selectField_Hidden">
							<s:select cssClass="UpSave_Control_Frame_Div_selectField_Option"
								list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
								label="%{#sr.showField.fieldShowName}"
								multiple="%{#sr.showField.selectMultiple}"
								value="%{#sr.fieldValue}"
								headerKey="%{#application.emptySelectValueName}"
								headerValue="---请选择---"
								onchange="onSelectChange(%{#sr.selectChange});"
								disabled="%{#sr.readOnly}"
								id="serviceParam.%{#sr.showField.paramName}"
								onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');" />
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
					<div title='<s:property value="%{#sr.checkMessage}"/>'
						style='color:<s:property value="%{#sr.showColor}"/>; '>
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<div>
					<s:hidden name="serviceParam.%{#sr.showField.paramName}"
						value="%{#sr.fieldValue}"></s:hidden>
					<input class="UpSave_TextBox selectClass TextBox TextBox:hover"
						style="width: 250px"
						<s:if test="#sr.readOnly==true">disabled</s:if>
						value="<s:property value="%{#sr.tag[0]}" />" />
					<div class="popdiv" style="overflow: auto;">
						<ul>
							<li
								code='<s:property value="%{#application.emptySelectValueName}" />'>
								---请选择---
							</li>
							<s:property value='%{#sr.tag[1]}' escape="false" />
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
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<div>
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}" readonly="true"
						style="width:220px" label="%{#sr.showField.fieldShowName}"
						value="%{#sr.fieldValue}">
					</s:textfield>
					<s:if test="%{#sr.readOnly == false}">
						<input name='popBtn' type='button'
							url='<%=basePath%><s:property value="%{#sr.tag}" />' value='...' />
					</s:if>
				</div>
			</div>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='multipleSelectField'}">
			<s:if test="%{#sr.showField.errorSpace == false}">
				<div class="UpSave_Control_Frame_Div" style="height: 205px;">
					<!--没有错误提示的多选框类似Listbox-->
					<div>
						<samp
							title="<s:property value="%{#sr.showField.fieldShowName}" />">
							<span color="red"><s:property
									value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
							<s:property value="%{#sr.showField.fieldShowName}" />
						</samp>
						<div title="<s:property value="%{#sr.checkMessage}"/>"
							style="color:<s:property value="%{#sr.showColor}"/>; ">
							<samp>
								<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
										value="%{#sr.checkMessage}" /> </font>
							</samp>
						</div>
					</div>
					<s:if test="#sr.readOnly==true">
						<s:hidden name="serviceParam.%{#sr.showField.paramName}"
							value="%{#sr.fieldValue}">
						</s:hidden>
					</s:if>
					<s:if test="%{#sr.showField.height != null}">
						<s:select cssClass="UpSave_MultipleSelectField" list="#sr.tag"
							name="serviceParam.%{#sr.showField.paramName}" multiple="true"
							label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
							id="serviceParam.%{#sr.showField.paramName}"
							onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');" />
					</s:if>
					<s:else>
						<s:select cssClass="UpSave_MultipleSelectField" list="#sr.tag"
							name="serviceParam.%{#sr.showField.paramName}" multiple="true"
							label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
							id="serviceParam.%{#sr.showField.paramName}"
							onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');" />
					</s:else>
				</div>
			</s:if>
			<s:else>
				<div class="UpSave_Control_Frame_Div" style="height: 205px;">
					<!--有错误提示的多选框类似Listbox-->
					<div>
						<samp
							title="<s:property value="%{#sr.showField.fieldShowName}" />">
							<span color="red"><s:property
									value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
							<s:property value="%{#sr.showField.fieldShowName}" />
						</samp>
						<div title="<s:property value="%{#sr.checkMessage}"/>"
							style="color:<s:property value="%{#sr.showColor}"/>; ">
							<samp>
								<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
										value="%{#sr.checkMessage}" /> </font>
							</samp>
						</div>
					</div>
					<s:select cssClass="UpSave_MultipleSelectField"
						style="height:200px;" list="#sr.tag"
						name="serviceParam.%{#sr.showField.paramName}" multiple="true"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						id="serviceParam.%{#sr.showField.paramName}"
						onmousemove="changeOptionsTitle('serviceParam.%{#sr.showField.paramName}');" />
				</div>
			</s:else>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='treeField'}">
			<div class="UpSave_Control_Frame_Div_TreeField"
				style="height: 205px;">
				<!-- 这是一种未知模式的Listbox -->
				<div>
					<input type="hidden"
						name='__checkbox_serviceParam.<s:property value="%{#sr.showField.paramName}"/>'
						value='' />
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />"
						style="width: 70px;"
						class="UpSave_Control_Frame_Div_TreeField_samp">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
					<script type="text/javascript">
							$(document).ready(function(){
								loadTree();
							});
						</script>
					<div id="checkBoxTree"
						style="width: 300px; height: 300px; float: left; margin-left: 5px; margin-top: 4px; margin-bottom: 2%; border: 1px solid #CDCDCD; overflow-y: auto; overflow-x: hidden;">
						<script type="text/javascript">
								d = new dTree('d','<%=basePath%>');
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
			<div class="UpSave_Control_Frame_Div">
				<!--文件上传控件-->
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
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
					label="%{#sr.showField.fieldShowName}" disabled="%{#sr.readOnly}">
				</s:file>
			</div>

		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='multipleTextField'}">
			<div class="UpSave_Control_Frame_Div"
				style="height: 140px; width: 605px; float: left;">
				<!--多行文本框，类似论坛回复框-->
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<s:if test="#sr.readOnly==true">
					<s:textarea cssClass="UpSave_Textarea"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						readonly="#sr.readOnly" onfocus="this.blur();">
					</s:textarea>
				</s:if>
				<s:else>
					<s:textarea cssClass="UpSave_Textarea"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						readonly="#sr.readOnly">
					</s:textarea>
				</s:else>
			</div>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='dateField'}">
			<div class="UpSave_Control_Frame_Div">
				<!--带分隔符的日期控件，文本框[yyyy-MM-dd]-->
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<s:if test="#sr.readOnly==true">
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						readonly="%{#sr.readOnly}" onfocus="this.blur();">
					</s:textfield>
				</s:if>
				<s:else>
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"></s:textfield>
				</s:else>
			</div>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='dateFieldNoSlash'}">
			<div class="UpSave_Control_Frame_Div">
				<!--不带分隔符的日期控件，文本框[yyyyMMdd]-->
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<s:if test="#sr.readOnly==true">
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						readonly="%{#sr.readOnly}" onfocus="this.blur();">
					</s:textfield>
				</s:if>
				<s:else>
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMMdd'})"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}">
					</s:textfield>
				</s:else>
			</div>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='dateFieldMonth'}">
			<div class="UpSave_Control_Frame_Div">
				<!--不带分隔符的日期控件，文本框[yyyyMM]-->
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<s:if test="#sr.readOnly==true">
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						readonly="%{#sr.readOnly}" onfocus="this.blur();">
					</s:textfield>
				</s:if>
				<s:else>
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMM'})"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}">
					</s:textfield>
				</s:else>
			</div>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='dateFieldHMS'}">
			<div class="UpSave_Control_Frame_Div">
				<!--不带分隔符的日期控件，文本框[yyyyMM]-->
				<div>
					<samp title="<s:property value="%{#sr.showField.fieldShowName}" />">
						<span color="red"><s:property
								value="%{#sr.showField.fieldShowNamePrefix}" /> </span>
						<s:property value="%{#sr.showField.fieldShowName}" />
					</samp>
					<div title="<s:property value="%{#sr.checkMessage}"/>"
						style="color:<s:property value="%{#sr.showColor}"/>; ">
						<samp>
							<font color="<s:property value="%{#sr.showColor}"/>"> <s:property
									value="%{#sr.checkMessage}" /> </font>
						</samp>
					</div>
				</div>
				<s:if test="#sr.readOnly==true">
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}"
						readonly="%{#sr.readOnly}" onfocus="this.blur();">
					</s:textfield>
				</s:if>
				<s:else>
					<s:textfield cssClass="UpSave_TextBox"
						name="serviceParam.%{#sr.showField.paramName}"
						onfocus="WdatePicker({doubleCalendar:true,dateFmt:'HHmmss'})"
						label="%{#sr.showField.fieldShowName}" value="%{#sr.fieldValue}">
					</s:textfield>
				</s:else>
			</div>
		</s:elseif>
		<s:elseif test="%{#sr.showField.singleTag=='hiddenField'}">
			<!--隐藏控件-->
			<s:hidden name="serviceParam.%{#sr.showField.paramName}"
				value="%{#sr.fieldValue}" style="background-color:Black;">
			</s:hidden>
		</s:elseif>
	</s:iterator>
</div>
