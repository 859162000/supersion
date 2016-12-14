<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>模板编辑</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/EditTemplate.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=basePath %>css/EditTemplate.css" type="text/css">
  </head>	
  <body ondragstart="return false;">
  	<div>
  		<div>
  			<input type="hidden" value="<%=basePath %>" id="basePath"/>
  			<input type="hidden" value="<s:property value="serviceResult.get(0).get(2)"/>" id="path"/>
  			<input type="hidden" value="<s:property value="serviceResult.get(0).get(3)"/>" id="TmpName"/>
  			<input type="hidden" value="<s:property value="serviceResult.get(0).get(4)"/>" id="calcInstName"/>
  			<input type="hidden" value="<s:property value="serviceResult.get(0).get(5)"/>" id="checkInstName"/>
  			<input type="hidden" value="<s:property value="serviceResult.get(9)"/>" id="currSuitValue" />		
  		</div>  			
  		<div style="height:40px;background-color:#e8e8e8">
  			
  			<a href="javascript:;" name="initExcel" class="tableTitle color">初始化Excel</a>
  			<a href="javascript:;" name="open" class="tableTitle color">打开本地文档</a>
  			<a href="javascript:;" name="savedoc" class="tableTitle color">保存文档</a>
   			<!--<a href="javascript:;" name="saveas" class="tableTitle color">保存到本地</a>
   			<a href="javascript:;" name="exit" class="tableTitle color">退出编辑</a>
   			<a href="javascript:;" name="print" class="tableTitle color">打印</a>-->
   			<a href="javascript:;" id='btnItemMapping' name="btnItemMapping" class="tableTitle color">计算规则</a>
   			<a href="javascript:;" id='btnCheckMapping' name="btnCheckMapping" class="tableTitle color">校验规则</a>
  		</div>
  	</div>
  	<div class="content">
  		<div class="contentLeft">
  			<div class="nav">
				<a href="javascript:;" class="color1 color">指标</a>
				<a href="javascript:;"  class="color1">明细</a>
			</div>
			<div class="actionarea">
				<div class="action item">					
					<div class="module mart20">
						<div class="font14">期数</div>				
						<div class="mart15 marl10">
							<select id="issue">
			    				<option value="1">本期</option>
			    				<option value="2">上期</option>
			    				<option value="3">去年同期</option>
			    				<option value="4">年初</option>
			    				<option value="5">上年末</option>
			    			</select>
						</div>				
					</div>					
					<div class="module mart20">
						<div class="font14">属性</div>				
						<div class="mart15 marl10">
							<select id="itemproperty">
			 					<s:iterator value="serviceResult.get(4)" id="pt">
			    					<option value="<s:property value="%{#pt.get(1)}"/>"><s:property value="%{#pt.get(0)}"/></option>
			          			</s:iterator>
			    			</select>
						</div>				
					</div>
					<div class="module mart20">
						<div class="font14">币种</div>				
						<div class="mart15 marl10">
							<select id="currencyType">
			 					<s:iterator value="serviceResult.get(3)" id="ct">
			    					<option value="<s:property value="%{#ct.get(0)}"/>;<s:property value="%{#ct.get(2)}"/>"><s:property value="%{#ct.get(1)}"/></option>
			          			</s:iterator>
			    			</select>
						</div>				
					</div>					
					
					<div class="module mart20">
						<div class="font14">颜色</div>				
						<div class="mart15 marl10">
							<select id="itemcolor">
			 					<option ></option>
			 					<option style="background-color:#00FF00" value="#00FF00">绿色</option>
			 					<option style="background-color:#99CCFF" value="#99CCFF">1104蓝色</option><!-- mashoucheng 喜增 -->
			 					<option style="background-color:#CCFFFF" value="#CCFFFF">水绿色</option>
			 					<option style="background-color:#CCFFCC" value="#CCFFCC">石灰绿</option>
			 					<option style="background-color:#FFCC99" value="#FFCC99">橙色</option>
			 					<option style="background-color:#C8C8C8" value="#C8C8C8">淡灰</option>
			 					<option style="background-color:#CCCCFF" value="#CCCCFF">淡紫</option>
			 					<option style="background-color:#CC99FF" value="#CC99FF">深紫</option>
			 					<option style="background-color:#FF8080" value="#FF8080">粉红</option>
			 					<option style="background-color:#FF0000" value="#FF0000">红色</option>
			 					<option style="background-color:#FFFF00" value="#FFFF00">黄色</option>

			    			</select>
						</div>				
					</div>
					<div class="module mart20">
						<div class="font14">主题</div>				
						<div class="mart15 marl10">
							<select id="suit"><!-- onchange="selectTarget(this)" -->
			    				<option value="-1">--请选择--</option>
			  					<s:iterator value="serviceResult.get(2)" id="st">
				    				<option value="<s:property value="%{#st.get(0)}"/>"><s:property value="%{#st.get(1)}"/></option>
			           			</s:iterator>
				    		</select>
						</div>				
					</div>
					<div class="module mart20">
						<div class="font14">频度</div>				
						<div class="mart15 marl10">
							<select id="freqCategory">
								<option value="<s:property value="%{#application.emptySelectValueName}"/>">--请选择--</option>
			 					<s:iterator value="serviceResult.get(8)" id="ct">
			    					<option value="<s:property value="%{#ct.get(0)}"/>"><s:property value="%{#ct.get(1)}"/></option>
			          			</s:iterator>
			    			</select>
						</div>				
					</div>
					<div class="module mart20">
						<div class="font14">维度</div>					
						<div class="mart15 marl10">
							<select id="dimension1"  style="float:left;width:49%">
				    			<option value="<s:property value="%{#application.emptySelectValueName}"/>">--请选择--</option>
			  					<s:iterator value="serviceResult.get(6)" id="st">
				    				<option value="<s:property value="%{#st.get(0)}"/>"><s:property value="%{#st.get(1)}"/></option>
			           			</s:iterator>
				    		</select>
				    		<select id="dimension2" style="width:49%">
				    			<option value="<s:property value="%{#application.emptySelectValueName}"/>">--请选择--</option>
			  					<s:iterator value="serviceResult.get(7)" id="st">
				    				<option value="<s:property value="%{#st.get(0)}"/>"><s:property value="%{#st.get(1)}"/></option>
			           			</s:iterator>
				    		</select>
						</div>				
					</div>
					<div class="module mart20">
						<div class="font14">时间</div>				
						<div class="mart15 marl10">
							<s:textfield id="time" style="width:99%" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})">
							</s:textfield>
						</div>				
					</div>
					<div class="module mart20">
						<div class="font14">机构</div>				
						<div class="mart15 marl10">
							<select id="info">
				    			<option value="<s:property value="%{#application.emptySelectValueName}"/>">--请选择--</option>
			 					<s:iterator value="serviceResult.get(5)" id="pt">
			    					<option value="<s:property value="%{#pt.get(0)}"/>"><s:property value="%{#pt.get(1)}"/></option>
			          			</s:iterator>
				    		</select>
						</div>				
					</div>
					<div class="module mart20" style="text-align:center">						
						横向<input type="checkbox" id="checkbox1" name="checkbox1">
						可编辑<input type="checkbox" checked name="isEditAble">
					</div>
					<div class="module mart20" style="text-align:center">						
						<a href="javascript:;" name="bound" class="tableTitle color">绑定</a>
						<a href="javascript:;" name="unionbound" class="tableTitle color">属性绑定</a>			
					</div>					
					<div class="module mart20">
						<div class="font14">指标</div>				
						<div class="mart15 marl10">
							<input type="input" id="inputHelper" style="width:99%">
							<select id="target" style="width:215px;height:200px" multiple="multiple">
					    	</select>
						</div>				
					</div>
					
				</div>
				<div class="action detail"  style="display:none">
					<div class="module mart20">					
						<div class="mart15 marl10" style="margin-left:1px;width:99%">
							<select name="roleInfoIdList" id="tableName" onchange="selectTable(this)">
			  					<s:iterator value="serviceResult.get(1)" id="tn">
				    				<option value="<s:property value="%{#tn.get(1)}"/>;<s:property value="%{#tn.get(2)}"/>"><s:property value="%{#tn.get(0)}"/></option>
			           			</s:iterator>
				    		</select>	
				    		<div><a href="javascript:;" name="bound" class="tableTitle color">绑定</a>
				    		
				    		</div>	  
						</div>				
					</div>
					<div class="module mart20">					
						<div class="mart15 marl10" style="margin-left:1px;width:99%">
							<select id="fieldName" style="height:350px" multiple="multiple">
				    			<option value="<s:property value="%{#application.emptySelectValueName}"/>">--请选择字段--</option>
				    		</select>
						</div>				
					</div>  
				</div>
			</div>
  		</div>
  		<div class="contentRight">
  			<object classid="clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5" codebase="WebOffice/weboffice.cab#V6.5.0.0" 
				id="WebOffice" width="100%" height="100%" style="LEFT: 0px; TOP: 0px" >
					<param name="_ExtentX" value="6350">
					<param name="_ExtentY" value="6350">
			</object> 
  		</div>
  	</div>
  	<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript"></script>	
  </body>
</html>
