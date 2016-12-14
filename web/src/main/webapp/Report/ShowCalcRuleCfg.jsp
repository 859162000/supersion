<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

int MaxSessionTimeout = request.getSession().getMaxInactiveInterval();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>计算规则配置</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/json2.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/DetailObject.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/CalcRuleCfg.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=basePath %>css/CalcRuleCfg.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath %>css/JSGrid.css" type="text/css">
	<style type="text/css">
		ul {margin: 0;padding: 0}
		ul li {list-style-type: none;}
		.selectClass {float: left;width: 130px;margin-top: 4px;}
		.popdiv {z-index: 9999;position: absolute;overflow-x: hidden;border: 1px solid #ccc;
			background: white;display: none}
		.triangle {text-decoration: none;color: skyblue;}
	</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript">
		// 设置窗体标题
		function setTitle(){
			var winTitle = window.dialogArguments;
			document.title = winTitle;
		}
		
	</script>	
	
  </head>  
  <body style="font-size:12px" ondragstart="return false;" onload="setTitle();">
  	<input type='hidden' id='basePath' value='<%=basePath %>'>
  	<input type='hidden' id='maxSessionTime' value='<%=MaxSessionTimeout %>'>
  	<input type='hidden' id='type' value='<s:property value="serviceResult.type"/>'>
   <div class="HeaderArea">
		<div style="height:98%"><span>规则名称：</span><samp><s:property value="serviceResult.header.strCalculationRuleName"/></samp></div>
		<div ><span>指标名称：</span><samp><s:property value="serviceResult.header.itemInfo.strItemName.substring(0,15)"/>...</samp></div>
		<div><span>属性名称：</span><samp><s:property value="serviceResult.header.itemProperty.strPropertyName"/></samp></div>
		<div><span>币种名称：</span><samp><s:property value="serviceResult.header.currencyType.strCurrencyName"/></samp></div>
		<div><span>扩展维度1：</span><samp><s:property value="serviceResult.header.strExtendDimension1"/></samp></div>
		<div><span>扩展维度2：</span><samp><s:property value="serviceResult.header.strExtendDimension2"/></samp></div>
		<DIV id="CountMsg" style="float:right">
		    <span id="t_d"></span>
		</DIV>
	</div>
   <div class="ControlArea">
   		<div>
   			<div style="float:left">符号：</div>
   			<div class="symbol">
   				<s:iterator value="serviceResult.symbol" id="column">   				
   					 <input type='button' key='<s:property value="key"/>' value='<s:property value="value"/>'/>
				</s:iterator>
   			</div>
   		</div>
   		<div>
   			<div style="float:left">指标：</div>
   			<div style="">
   				<input type='hidden' id='itemSelect'/>
   				<input type='text' id='txtItemName'/>
   				<div class="popdiv">
					<ul>
						<li code="-1">---请选择---</li>	
					</ul>
				</div>
	    		<select id='currSelect'>
	    			<option value="-1">--请选择币种--</option>
	    			<s:iterator value="serviceResult.currencyType" id="ct">
   						<option value='<s:property value="%{#ct.strCurrencyCode}" />'>
   							<s:property value="%{#ct.strCurrencyName}" />
   						</option>
   					</s:iterator>	  
	    		</select>
	    		<select id='protertySelect'>
	    			<option value="-1">--请选择属性--</option>
	    			<s:iterator value="serviceResult.itemProperty" id="ip">
   						<option value='<s:property value="%{#ip.strPropertyCode}" />'>
   							<s:property value="%{#ip.strPropertyName}" />
   						</option>
   					</s:iterator>
	    		</select>
	    		<select id='timeSelect'>
	    			<option value="-1">--请选择期数--</option>
	    			<s:iterator value="serviceResult.timeProperty" id="tp"> 
	   					 <option value='<s:property value="%{#tp.key}" />'>
   							<s:property value="%{#tp.value}" />
   						</option>
					</s:iterator>
	    		</select>
	    		<select id='freqSelect'>
	    			<option value="-1">--请选择频度--</option>
	    			<s:iterator value="serviceResult.freqProperty" id="fp"> 
	   					 <option value='<s:property value="%{#fp.key}" />'>
   							<s:property value="%{#fp.value}" />
   						</option>
					</s:iterator>
	    		</select>		    		
	    		<select id='extendSelect1'>
	    			<option value="-1">--请选择维度1--</option>
	    			<s:iterator value="serviceResult.extendProperty1" id="ep">   				
	   					 <option value='<s:property value="%{#ep.key}" />'>
   							<s:property value="%{#ep.value}" />
   						</option>
					</s:iterator>
	    		</select>
	    		<select id='extendSelect2'>
	    			<option value="-1">--请选择维度2--</option>
	    			<s:iterator value="serviceResult.extendProperty2" id="ep">   				
	   					 <option value='<s:property value="%{#ep.key}" />'>
   							<s:property value="%{#ep.value}" />
   						</option>
					</s:iterator>
	    		</select>
	    		<input type="text" id="dtDate" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" />
	    		<select id='instInfo'>
	    			<option value="-1">--请选择机构--</option>
	    			<s:iterator value="serviceResult.InstInfo" id="ii">   				
	   					 <option value='<s:property value="%{#ii.strInstCode}" />'>
   							<s:property value="%{#ii.strInstName}" />
   						</option>
					</s:iterator>
	    		</select>
	    		<input type='button' id="itemBtn" value='添加'/>
   			</div>
   		</div>
   		<div>
   			<div style="float:left">常量：</div>
   			<div style="">
   				<input value=''/><input id="constBtn" type='button' value='添加'/>
   			</div>
   		</div>
   		<div>
   			<div style="float:left">公式：</samp></div>
   			<div style="">
   				<select id='accSelect'>
	    			<option value="-1">--请选择会计表--</option>
	    			<s:iterator value="serviceResult.ReportModel_Table" id="tp"> 
	   					 <option value='<s:property value="%{#tp.strTableName}" />'>
   							<s:property value="%{#tp.strChinaName}" />
   						</option>
					</s:iterator>
	    		</select>
   				<input id='detailControl' type='button' value='会计项'/>
   			</div>
   		</div>
   		<div>
   			<div style="float:left">SQL：</div>
   			<div style="">
   				<textarea id="txtArea" style="width:500px"></textarea>
				<input id="sqlBtn" type='button' value='添加'/>
   			</div>
   		</div>
   </div>
   <div class="CommandArea">
	   	<input type='button' id="btnSave" name='btnSave' value='保存'/>
	   	<input type='button' id="btnCopy" name='copy' value='复制'/>
	   	<input type='button' id="btnPaste" name='paste' value='粘贴'/>
	   	<input type='button' id="btnDel" value='删除'/>
	   	<input type='button' id="btnUp" value='上移'/>
	   	<input type='button' id="btnDown" value='下移'/>
	   	<input type='button' id="btnVal" value='验证'/>
   </div>
   <form name="form1" id="from1" method="POST">
   		<div class="DataArea">
   			<div style="height:35px;padding-right:16px;">
			   <table style="border-bottom:0px;margin-bottom:0px;width:99%;border-top:1px solid black;border-left:1px solid black;border-right:1px solid black;">
			   	<tbody>
			   		<tr class="JS_Grid_Head_Row">
			   			<th style="width:5%" class = "JS_Grid_Head_Row_Cell" style="vertical-align:middle;">
							<input id="JS_Grid_Check" class="Grid_CheckBox"  type='checkbox' onclick="click()"></input>
						</th>
			   			<th style="width:5%">序号</th>
			   			<th style="width:10%">项类型</th>
			   			<th style="width:70%">计算项</th>
			   			<th style="width:10%">描述</th>
			   		</tr>
			   	</tbody>
			   </table>
		   </div>
		   <div style="height:280px;overflow-y:scroll;">
		   	<table  rules="rows" style="border-top:0px;margin-top:0px;width:99%;border-bottom:1px solid black;border-left:1px solid black;border-right:1px solid black;">
			   	<tbody id="data">
			   		<tr>
			   			<td style="width:5%"></td>
			   			<td style="width:5%"></td>
			   			<td style="width:10%"></td>
			   			<td style="width:70%"></td>
			   			<td style="width:10%"></td>
			   		</tr>			   		
			   		<s:iterator value="serviceResult.ItemsCalculation" id="icList" status="st"> 
			   			<tr class="JS_Grid_Data_Row"  style="font-size: 12px; font-family: 微软雅黑;text-align:center;width:5%">
			   			    <td id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="overflow:visible;vertical-align:middle;">
									<input name="JS_Grid_Check" class="Grid_CheckBox" type="checkbox" value="<s:property value="%{icList.get(0).value}"/>"></input>
							</td>
							<td id="chckeboxList" class="JS_Grid_Data_Row_Cell">
									<s:property value="#st.index+1"/>
							</td>
				   			<td><s:property value="%{#icList.strItemTypeDesc}" /><INPUT type='hidden' value='<s:property value="%{#icList.strItemType}" />' name='type' /></td>
				   			<td>
				   			<s:iterator value="serviceResult.ItemsCalculation.get(#st.index)" id="icList">
				   			<s:if test="%{#icList.strItemType=='item'}">
				   				<s:property value="%{#icList.itemCode}" />-<s:property value="%{#icList.strItemCodeName}" />指标属性：[<s:property value="%{#icList.proName}" />]  币种:[
				   				<s:property value="%{#icList.currName}" />]  期数:[<s:property value="%{#icList.timeName}" />]  扩展维度1:[<s:property value="%{#icList.extend1Name}" />
				   				]  扩展维度2:[<s:property value="%{#icList.extend2Name}" />]  频度:[<s:property value="%{#icList.freqName}" />]  机构:[<s:property value="%{#icList.instName}"/>
				   				]  日期:[<s:property value="%{#icList.dtDate}" />]
				   				<input type='hidden' value='<s:property value="%{#icList.itemCode}" />' name='itemCode' />
								<input type='hidden' value='<s:property value="%{#icList.curr}" />' name='curr' />
								<input type='hidden' value='<s:property value="%{#icList.property}" />' name='property' />
								<input type='hidden' value='<s:property value="%{#icList.extend1}" />' name='extend1'/>
								<input type='hidden' value='<s:property value="%{#icList.extend2}" />' name='extend2'/>
								<input type='hidden' value='<s:property value="%{#icList.time}" />' name='time'  />
								<input type='hidden' value='<s:property value="%{#icList.freq}" />' name='freq'  />
								<input type='hidden' value='<s:property value="%{#icList.instInfo}" />' name='instInfo'  />
								<input type='hidden' value='<s:property value="%{#icList.dtDate}" />' name='dtDate'  />
							</s:if>
							<s:elseif test="%{#icList.strItemType=='symbol'}"><s:property value="%{#icList.strItemValue}" />
								<input type='hidden' value='<s:property value="%{#icList.strItemValue}" />' name='op' />
							</s:elseif>
							<s:elseif test="%{#icList.strItemType=='const'}"><s:property value="%{#icList.strItemValue}" />
								<input type='hidden' value='<s:property value="%{#icList.strItemValue}" />' name='const' />
							</s:elseif>
							<s:elseif test="%{#icList.strItemType=='sql'}">
								<s:property value="%{#icList.strItemValue}" />
							</s:elseif>
							<s:elseif test="%{#icList.strItemType=='detail'}">
								<A href="javascript:;" name=bound><s:property value="%{#icList.strItemValue}" /></A>
								<INPUT type='hidden' value='<s:property value="%{#icList.acctableName}" />' name='acctableName' />
								<INPUT type='hidden' value='<s:property value="%{#icList.calcField}" />' name='CalcField' />
								<INPUT type='hidden' value='<s:property value="%{#icList.calcType}" />' name='CalcType' />
								<INPUT type='hidden' value='<s:property value="%{#icList.whereCondition}" />' name='WhereCondition' >
							</s:elseif>
							</s:iterator>
				   			</td>
				   			<td></td>
			   			</tr>
	   					
					</s:iterator>
			   	</tbody>
			   </table>
		   </div>
		   </div>
   </form>
   <script src="<%=basePath%>js/DatePicker/WdatePicker.js" type="text/javascript" ></script>
   </body>
</html>
