<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<title>ShowComponentSaveOrUpdate</title>
		<base href="<%=basePath%>"></base>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/JSGrid.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
		<link href="css/themes/default/easyui.css" rel="stylesheet"	type="text/css" />
		<link href="css/themes/icon.css" rel="stylesheet" type="text/css" />
		<script src="js/jquery/jquery.1.7.js" type="text/javascript"></script>
		<script src="js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/jquery.upload.js" type="text/javascript"></script>
		<script src="js/showcomponent.js" type="text/javascript"></script>
		<script type="text/javascript">
		   var headName=<s:property value="serviceResult.listHeadJsons" escape="false" />;
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
			<div id="query_hint" class="query_hint">
				页面加载中，请稍等...
			</div>
		</div>
		<s:form id="form1" style="width:100%;height:100%;">
			<!-- 按钮区域 -->
			<div class="easyui-panel"
				style="padding: 5px; width: 100%; height: auto;">
				<s:iterator value="serviceResult.showSaveOrUpdate.operationList"
					id="sr">
					<s:if test="%{#sr.operationType=='Post'}">
						<a href="javascript:void(0);"
							onclick="onMasterOpClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"
							class="Menu_Frame_Div_Cell"> <s:property value="operationName" />
						</a>
					</s:if>
					<s:elseif test="%{#sr.operationType=='Get'}">
						<a href="javascript:void(0);"
							onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action')"
							class="Menu_Frame_Div_Cell"> <s:property value="operationName" />
						</a>
					</s:elseif>
					<s:elseif test="%{#sr.operationType=='Get1'}">
						<a href="javascript:void(0);"
							onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action?id=<s:property value="serviceResult.id"/>')"
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
						<a href="javascript:void(0);"
							onclick="onMasterOpClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"
							class="Menu_Frame_Div_Cell"> <s:property value="operationName" />
						</a>
					</s:elseif>
				</s:iterator>
			</div>

			<!-- 选项卡-->
			<div id="tt" class="easyui-tabs" style="width: 100%; height: auto;" data-options="fit:true,border:false">
				<!-- 基础段信息 -->
				<div
					title="<s:property value='serviceResult.showSaveOrUpdate.navigationName'/>"
					style="padding: 10px; width: 100%; height: auto;" >
					<input id="id" name="id" type="hidden" value="<s:property value="serviceResult.showSaveOrUpdate.id" />"/>
					<input id="type" name="type" type="hidden" value="<s:property value="serviceResult.showSaveOrUpdate.tName" />"/>
					<!-- 将要显示的数据对象放入重新定义的变量中供界面展示所用 -->
					<s:set name="showObject" value="serviceResult.showSaveOrUpdate" />
					<s:include value="ShowComponentForBase.jsp" />
					
				</div>
				
				<!-- 以卡片形式显示明细信息 -->
				<s:iterator  value="serviceResult.showCardList" id="scl">
					 <div
						title="<s:property value='#scl.navigationName'/>"
						style="padding: 10px; width: 100%; height: auto;" >
						<!-- 将要显示的数据对象放入重新定义的变量中供界面展示所用 -->
						<s:set name="showObject" value="#scl" />
						<s:include value="ShowComponentForBase.jsp" />
						
					</div>
				</s:iterator>

				<!-- 明细段信息 -->
				<s:iterator value="serviceResult.showListList" id="slls"
					status="sll">
					<div tag="<s:property value="#sll.index" />"
						title="<s:property value='#slls.navigationName'/>"
						style="padding: 10px; width: 100%; height: 400px; overflow: auto;" data-options="fit:true,border:false">
						<div class="listButton" style="padding: 5px; width: 100%; height: 40px;" >
							
								<s:iterator value="#slls.operationList" id="op">
								
									<s:if test="%{#op.operationType=='Client'}">
										<a class="easyui-linkbutton"
											href="javascript:<s:property value="operationAction.split('-')[0]"/>('Grid_Task<s:property value='#sll.index+1' />','<s:property value="operationAction.split('-')[1]"/>')">
											<s:property value="operationName" /> </a>
									</s:if>
									<s:elseif test="%{#op.operationType=='AjaxPostConfirm'}">
										<a class="easyui-linkbutton" href="javascript:ajaxAlertOp('Grid_Task<s:property value='#sll.index+1' />','<s:property value="operationNamespace"/>','<s:property value="operationAction"/>','<s:property value="operationName" />')">
											<s:property value="operationName" />
										</a>
									</s:elseif>
									<s:elseif test="%{#op.operationType=='AjaxPost'}">
										<a class="easyui-linkbutton" href="javascript:ajaxOp('Grid_Task<s:property value='#sll.index+1' />','<s:property value="operationNamespace"/>','<s:property value="operationAction"/>','<s:property value="operationName" />')">
											<s:property value="operationName" />
										</a>
									</s:elseif>
									<s:elseif test="%{#op.operationType=='Post'}">
										<a class="easyui-linkbutton"
											href="javascript:onDetailHeadOpClick('Grid_Task<s:property value='#sll.index+1' />','<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')">
											<s:property value="operationName" /> </a>
									</s:elseif>
									<s:elseif test="%{#op.operationType=='PostConfirm'}">
										<a id="deleteButton" class="easyui-linkbutton"
											href="javascript:onDetailHeadOpAlertClick('Grid_Task<s:property value='#sll.index+1' />','<s:property value="operationNamespace"/>','<s:property value="operationAction"/>','<s:property value="serviceResult.showView"/>','<s:property value="operationName" />')">
											<s:property value="operationName" /> </a>
									</s:elseif>
									<s:elseif test="%{#op.operationType=='Upload'}">
										<!--<s:file id="123456" class="files"
											cssStyle="float:left; margin-left:2px; font-family:'Microsoft Yahei'; font-size:11px;"
											name="uploadFile"></s:file>
										--><a class="easyui-linkbutton"
											href="javascript:onUpload('Grid_Task<s:property value='#sll.index+1' />','<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')">
											<s:property value="operationName" /> </a>
									</s:elseif>
								</s:iterator>
								
								<div id="Grid_Task<s:property value='#sll.index+1' />_Page" class="Top_Page" style="float: right;">
									<input id="Grid_Task<s:property value='#sll.index+1' />_CurrentPage" type="hidden" value='<s:property value="#slls.showPage.currentPage"/>' />
									<input id="Grid_Task<s:property value='#sll.index+1' />_PageUrl" type="hidden" value='<s:property value="#slls.namespace"/>/<s:property value="#slls.selectActionName" />' />
									<s:if test="#slls.showPage.showPage">
										<div>
											总条数:
											<s:property value="#slls.showPage.totalCount" />
										</div>
										<div>
											总共页:
											<s:property value="#slls.showPage.totalPage" />
										</div>
										<div>
											当前页:
											<s:property value="#slls.showPage.currentPage" />
										</div>
										<div>
											每页条数:
											<s:property value="#slls.showPage.pageSize" />
										</div>
										<div
											onclick="javascript:onPage('Grid_Task<s:property value="#sll.index+1" />','<s:property value="#slls.namespace" />','<s:property value="#slls.selectActionName" />',1)">
											首页
										</div>
										<s:if test="#slls.showPage.showPrePage">
											<div
												onclick="javascript:onPage('Grid_Task<s:property value="#sll.index+1" />','<s:property value="#slls.namespace" />','<s:property value="#slls.selectActionName" />',<s:property value="#slls.showPage.currentPage" /> - 1)">
												上一页
											</div>
										</s:if>
										<s:if test="#slls.showPage.showNextPage">
											<div
												onclick="javascript:onPage('Grid_Task<s:property value="#sll.index+1" />','<s:property value="#slls.namespace" />','<s:property value="#slls.selectActionName" />',<s:property value="#slls.showPage.currentPage" /> + 1)">
												下一页
											</div>
										</s:if>
										<div
											onclick="javascript:onPage('Grid_Task<s:property value="#sll.index+1" />','<s:property value="#slls.namespace" />','<s:property value="#slls.selectActionName" />',<s:property value="#slls.showPage.totalPage" />)">
											尾页
										</div>
										<div>
											<input name="" type="text" style="width: 28px;" />
											<a onclick="javascript:skip('Grid_Task<s:property value="#sll.index+1" />','<s:property value="#slls.namespace" />','<s:property value="#slls.selectActionName" />',this)">跳转</a>
										</div>
									</s:if>
									<s:else>
										<div>
											总共页:1
										</div>
										<div>
											当前页:1
										</div>
									</s:else>
								</div>
						</div>
						
						<div  class="grid_head"
							style="padding: 5px 5px 0px 5px;padding-bottom: 0px; width: 100%;overflow:hidden;height:40px;" >
							<table id="Grid_Task<s:property value="#sll.index+1" />_head"
								class="Grid_Table" border="1px" cellpadding="0" cellspacing="0"
								Rules=rows>
								
								<tr id="JS_Grid_Head_Row" class="JS_Grid_Head_Row">
									<td class="JS_Grid_Head_Row_Cell" style="width: 20px;">
										<input id="Grid_Task_Check" class="Grid_CheckBox"
											type='checkbox' onclick="selectAll('Grid_Task<s:property value="#sll.index+1" />_head', 'Grid_Task<s:property value="#sll.index+1" />');"></input>
									</td>
									<td class="JS_Grid_Head_Row_Cell" style="width: 20px;">
										行号
									</td>

									<!-- 连接 -->
									<s:iterator value="#slls.showLinkNameList" id="showNameList">
										<td title="<s:property value="showName"/>"
											class="JS_Grid_Head_Row_Cell"
											style="width: <s:property value="(width==null || width=='')?100:width"/>px;">
											<div class="TD_Div_Nowrap">
												<s:property value="showName" />
											</div>
										</td>
									</s:iterator>

									<s:iterator value="#slls.showNameList" id="showNameList"
										status="showNameListStatus">
										<td title="<s:property value="showName"/>"
											class="JS_Grid_Head_Row_Cell"
											style="width: <s:property value="(width==null || width=='')?100:width"/>px;">
											<div class="TD_Div_Nowrap">
												<s:property value="showName" />
											</div>
										</td>
									</s:iterator>
								</tr>
								
							</table>
						</div>
						
						<div  class="grid_body" style="padding: 0px 5px 5px 5px; width: 100%;height:300px; overflow:auto;" >
							<table id="Grid_Task<s:property value="#sll.index+1" />"
								class="Grid_Table" border="1px" cellpadding="0" cellspacing="0"
								Rules=rows>
									<tr id="JS_Grid_Head_Row" class="JS_Grid_Head_Row" style="visibility:collapse;">
										<td class="JS_Grid_Head_Row_Cell" style="width: 20px;">
											<input id="Grid_Task_Check" class="Grid_CheckBox"
												type='checkbox'></input>
										</td>
										<td class="JS_Grid_Head_Row_Cell" style="width:20px;">
											行号
											<input id="Grid_Task<s:property value="#sll.index+1" />_linkedCount" type="hidden"
												value='<s:property value="#slls.showLinkNameList.size" />' />
										</td>
	
										<!-- 连接 -->
										<s:iterator value="#slls.showLinkNameList" id="showNameList">
											<td title="<s:property value="showName"/>"
												class="JS_Grid_Head_Row_Cell"
												style="width: <s:property value="(width==null || width=='')?100:width"/>px;">
												<div class="TD_Div_Nowrap">
													<s:property value="showName" /><s:property value="(width==null || width=='')?100:width"/>
												</div>
											</td>
										</s:iterator>
	
										<s:iterator value="#slls.showNameList" id="showNameList"
											status="showNameListStatus">
											<td title="<s:property value="showName"/>"
												class="JS_Grid_Head_Row_Cell"
												style="width: <s:property value="(width==null || width=='')?100:width"/>px;">
												<div class="TD_Div_Nowrap">
													<s:property value="showName" />
												</div>
											</td>
										</s:iterator>
									</tr>
								<!-- 表头 -->

								<s:if test="slls.valueTable==null || slls.valueTable.size==0"></s:if>

								<!-- 数据展示开始 -->
								
								<s:else>
									<s:iterator value="#slls.valueTable" id="vt" status="st">
										<tr name="canEdit" class="JS_Grid_Data_Row"
											style="font-size: 12px; font-family: 微软雅黑;"
											Rows_Type='DataRow'>
											<td id="chckeboxList" class="JS_Grid_Data_Row_Cell"
												style="overflow: visible;">
												<input name="idList" class="Grid_CheckBox" type="checkbox"
													value="<s:property value="%{#vt.get(0).value}"/>"></input>
												<input name="" type="hidden"
													id="id<s:property value="#st.index+1" />"
													value="<s:property value="%{#vt.get(0).postFieldName}"/>" />
												<input name="" class="dataId" type="hidden"
													value="<s:property value="%{#vt.get(0).value}"/>" />
											</td>
											<td id="chckeboxList" class="JS_Grid_Data_Row_Cell">
												<s:property value="#st.index+1" />
											</td>

											<s:if test="#slls.linkedList.get(#st.index).size > 0">
												<!-- 链接按钮 -->
												<s:iterator value="#slls.linkedList.get(#st.index)" id="ll">
													<s:if test="%{#ll.operationType=='Upload'}">
														<td class="JS_Grid_Data_Row_CellLink"
															title="<s:property value='operationName'/>"
															style="width: <s:property value="(width==null || width=='')?100:width" />px;"
															columncode="Col1">

															<div title="<s:property value='operationName'/>"><!--
																<s:file class="files" name="uploadFile"
																	cssStyle="float:left; margin-top:11px; font-family:'Microsoft Yahei'; font-size:11px;"></s:file>
																--><a
																	href="javascript:onUpload('Grid_Task<s:property value='#sll.index+1' />','<s:property value="operationNamespace"/>','<s:property value="operationAction"/>','<s:property value="%{#vt.get(0).value}"/>');">
																	<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
																		<s:property value="operationName" />
																	</s:if> <s:else>
																		<img src="<s:property value="imageUrl" />" />
																	</s:else> </a>
															</div>
														</td>
													</s:if>
													<s:elseif test="%{#ll.operationType=='TypeHidden'}">
														<td title="<s:property value='operationName'/>"
															class="JS_Grid_Data_Row_CellLink"
															style="width: <s:property value="(width==null || width=='')?100:width" />px;"
															columncode="ColA">
															<s:if test="%{#vt.get(1).value!='**********'}">
																<s:a namespace="%{#ll.operationNamespace}"
																	action="%{#ll.operationAction}?id=%{#vt.get(0).value}&type=%{#vt.get(1).value}&windowId=%{#request.windowId}"
																	cssStyle="display:inline-block;color:%{#ll.color};">
																	<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
																		<s:property value="operationName" />
																	</s:if>
																	<s:else>
																		<img src="<s:property value="imageUrl" />" />
																	</s:else>
																</s:a>
															</s:if>
														</td>
													</s:elseif>
													<s:elseif test="%{#ll.operationType=='Pop'}">
														<td title="<s:property value='operationName'/>"
															class="JS_Grid_Data_Row_CellLink"
															style="width: <s:property value="(width==null || width=='')?100:width" />px;"
															columncode="ColA">
															<s:a namespace="%{#ll.operationNamespace}"
																action="%{#ll.operationAction}?id=%{#vt.get(0).value}&type=%{#vt.get(1).value}&windowId=%{#request.windowId}"
																target="_blank"
																cssStyle="display:inline-block;color:%{#ll.color};">
																<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
																	<s:property value="operationName" />
																</s:if>
																<s:else>
																	<img src="<s:property value="imageUrl" />" />
																</s:else>
															</s:a>
														</td>
													</s:elseif>
													<s:elseif test="%{#ll.operationType=='GetConfirm'}">
													  	<td title="<s:property value='operationName'/>"
															class="JS_Grid_Data_Row_CellLink"
															style="width: <s:property value="(width==null || width=='')?100:width" />px;"
															columncode="ColA">
															<a
																href="javascript:ajaxAlertOpForDetail('Grid_Task<s:property value="#sll.index+1" />','<s:property value='%{#ll.operationNamespace}'/>','<s:property value='%{#ll.operationAction}'/>','<s:property value='%{#vt.get(0).value}'/>','<s:property value='%{#vt.get(1).value}'/>','<s:property value='%{#request.windowId}'/>')"
																style="display: inline-block; color: %{ #ll . color">
																<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
																	<s:property value="operationName" />
																</s:if>
																<s:else>
																	<img src="<s:property value="imageUrl" />" />
																</s:else> 
															</a>
														</td>
													</s:elseif>
													<s:elseif test="%{#ll.operationType=='Post'}">
													  	<td title="<s:property value='operationName'/>"
															class="JS_Grid_Data_Row_CellLink"
															style="width: <s:property value="(width==null || width=='')?100:width" />px;"
															columncode="ColA">
															<a
																href="javascript:onOpForDetail('Grid_Task<s:property value="#sll.index+1" />','<s:property value='%{#ll.operationNamespace}'/>','<s:property value='%{#ll.operationAction}'/>','<s:property value='%{#vt.get(0).value}'/>','<s:property value='%{#vt.get(1).value}'/>','<s:property value='%{#request.windowId}'/>')"
																style="display: inline-block; color: %{ #ll . color">
																<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
																	<s:property value="operationName" />
																</s:if>
																<s:else>
																	<img src="<s:property value="imageUrl" />" />
																</s:else> 
															</a>
														</td>
													</s:elseif>
													<s:elseif test="%{#ll.operationType=='DivWidthBig'}">
													  
														<td title="<s:property value='operationName'/>"
															class="JS_Grid_Data_Row_CellLink"
															style="width: <s:property value="(width==null || width=='')?100:width" />px;text-overflow:ellipsis; white-space:nowrap;overflow: hidden;"
															columncode="ColA">
															<div
																onclick="javascript:showListDivWidthBig('<s:property value='%{#ll.operationNamespace}'/>','<s:property value='%{#ll.operationAction}'/>','<s:property value='%{#vt.get(0).value}'/>','<s:property value='%{#vt.get(1).value}'/>','<s:property value='%{#request.windowId}'/>')"
																style="width: 75px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; color: #00F; text-decoration: underline; display: inline-block; color: %{ #ll . color">
																<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
																	<s:property value="operationName" />
																</s:if>
																<s:else>
																	<img src="<s:property value="imageUrl" />" />
																</s:else>
															</div>
														</td>
													</s:elseif>
													<s:elseif test="%{#ll.operationType=='OpenWin'}">
														<td title="<s:property value='operationName'/>"
															class="JS_Grid_Data_Row_CellLink"
															style="width: <s:property value="(width==null || width=='')?100:width" />px;"
															columncode="ColA">
															<a
																href="javascript:onOpenLinkWindows('<%=basePath%><s:property value='%{#ll.operationNamespace}'/>/<s:property value='%{#ll.operationAction}'/>.action?id=<s:property value='%{#vt.get(0).value}'/>&type=<s:property value='%{#vt.get(1).value}'/>', '<s:property value="serviceResult.showPage.currentPage" /><s:property value='%{#vt.get(0).value}'/>', '_<s:property value="#st.index+1"/>', '<s:property value="operationName" />')"
																cssStyle="width: <s:property value='width==null?100:width' />px; color:%{#ll.color};">
																<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
																	<s:property value="operationName" />
																</s:if> <s:else>
																	<img src="<s:property value="imageUrl" />" />
																</s:else> </a>
														</td>
													</s:elseif>
													<s:else>
														<td title="<s:property value='operationName'/>"
															class="JS_Grid_Data_Row_CellLink"
															style="width: <s:property value="(width==null || width=='')?100:width" />px;"
															columncode="ColA">
															<a
																href="javascript:ajaxOpForDetail('Grid_Task<s:property value="#sll.index+1" />','<s:property value='%{#ll.operationNamespace}'/>','<s:property value='%{#ll.operationAction}'/>','<s:property value='%{#vt.get(0).value}'/>','<s:property value='%{#vt.get(1).value}'/>','<s:property value='%{#request.windowId}'/>')"
																style="display: inline-block; color: %{ #ll . color">
																<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
																	<s:property value="operationName" />
																</s:if>
																<s:else>
																	<img src="<s:property value="imageUrl" />" />
																</s:else> 
															</a>
														</td>
													</s:else>
												</s:iterator>
											</s:if>
											<s:else>
												<s:iterator value="#slls.showLinkNameList" id="showNameList">
													<td class="JS_Grid_Data_Row_CellLink">r</td>
												</s:iterator>
											</s:else>
											<!-- 链接按钮结束 -->

											<s:iterator value="#vt" id="v" status="fl">
												<s:if test="#fl.index!=0 && #fl.index!=1">
													<td title="<s:property value="value" />"
														class="JS_Grid_Data_Row_Cell"
														style="width: <s:property value="(width==null || width=='')?100:width"/>px;"
														columncode="Col2"
														titleField='<s:property value="titleField" />'
														splitField='<s:property value="splitField" />'>
														<s:if test="singleTag == 'selectField'">
															<input type="hidden" value='<s:property value="key" />' />
														</s:if>
														<div class="TD_Div_Nowrap">
															<s:property value="value" />
															&nbsp;
														</div>
													</td>
												</s:if>
											</s:iterator>
										</tr>
									</s:iterator>
								</s:else>
								<!-- 数据展示结束 -->
							</table>
						</div>
						<!--  面板结束 -->
					</div>
					<!-- 明细选项卡结束 -->
				</s:iterator>
			</div>

			<!-- 窗口信息区域 -->
			<s:hidden id="windowId" name="windowId"></s:hidden>
		</s:form>
		<script src="<%=basePath%>js/DatePicker/WdatePicker.js"
			type="text/javascript"></script>
	</body>
</html>
