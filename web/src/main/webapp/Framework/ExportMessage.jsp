<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>"></base>
		<title>Send Message Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="Send Message">
		<link href="CSS/resources/css/common.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript"></script>
		<style>
			body { /*font-family:sans-serif;*/
				font-family: "Trebuchet MS", "Helvetica", "Arial", "Verdana",
					"sans-serif";
				font-size: 75%;
			}
			
			li {
				list-style:none;
				width: 250px;
				float: left;
			}
			
			fieldset {
				margin: 10px
			}
			
			ul {
				margin: 10px
			}
			
			label {
				width: 220px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}
			
			input {
				padding-right: 2px;
				vertical-align: middle;
			}
		</style>
	<script type="text/javascript">
	function validate()
	{
		var dtDate=document.getElementById('dtDate').value;
		if(dtDate=='')
		{
			alert('请选择数据日期!')
			return false;
		}
		var ProductCategray=document.getElementsByName('ProductCategray');
		var OpertFlag=document.getElementsByName('OpertFlag');
		var OpertFlagFlag=false;
		for(var i=0;i<OpertFlag.length;i++)
		{
			if(OpertFlag[i].checked){
				OpertFlagFlag=true;
				break;
			}
		}
		var ProductCategrayFlag=false;
		for(var i=0;i<ProductCategray.length;i++)
		{
			if(ProductCategray[i].checked){
				ProductCategrayFlag=true;
				break;
			}
		}
		if(!OpertFlagFlag)
		{
			alert('请选择数据类型!')
			return false;
		}
		if(!ProductCategrayFlag)
		{
			alert('请选择报文类型!')
			return false;
		}
		return true;
	}
	function onOperationClick(actionNamespace,actionName){
			document.form1.action = "<%=basePath%>" + actionNamespace + "/" + actionName + ".action";
        	document.form1.submit();
        }
	</script>
	</head>

	<body>
		<s:form id="form1" action="SendMessage" namespace="/framework">
			<label for="dtDate">
				数据日期：
			</label>
			<input id="dtDate" name="dtDate" style="font-family: Terminal; width: 153px" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
			<input type="button" value="报送报文" 
		          onmouseover="this.style.backgroundColor='#c0de98';" 
		          onmouseout="this.style.backgroundColor='';"
		          Class="normButton" 		          
		           onclick="if(validate()){onOperationClick('/framework','SendMessage');}"/>
		    <input type="button" value="下载报文" 
		          onmouseover="this.style.backgroundColor='#c0de98';" 
		          onmouseout="this.style.backgroundColor='';"
		          Class="normButton" 
		          onclick="if(validate()){onOperationClick('/framework','DownLoadMessage');}"/>			
			<br>
			<fieldset>
				<legend>
					数据类型
				</legend>
				<input id="OpertFlag_A" type="checkbox" name="OpertFlag" value="1" />
				<label for="OpertFlag_A">
					新增
				</label>
				<input id="OpertFlag_U" type="checkbox" name="OpertFlag" value="2" />
				<label for="OpertFlag_U">
					更改
				</label>
				<input id="OpertFlag_D" type="checkbox" name="OpertFlag" value="3" />
				<label for="OpertFlag_D">
					删除
				</label>
			</fieldset>
			<div style="height:340px;overflow:auto">
			<fieldset>
				<legend>
					报文类型
				</legend>
				<fieldset>
					<legend>
						资金来源
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210101" type="checkbox"
								name="ProductCategray" value="210101" />
							<label for="ProductCategray_210101">
								单位存款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210102" type="checkbox"
								name="ProductCategray" value="210102" />
							<label for="ProductCategray_210102">
								FTU内部活动-金融债券
							</label>
						</li>
						<li>
							<input id="ProductCategray_210103" type="checkbox"
								name="ProductCategray" value="210103" />
							<label for="ProductCategray_210103">
								FTU内部活动-中长期借款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210104" type="checkbox"
								name="ProductCategray" value="210104" />
							<label for="ProductCategray_210104">
								应付及暂收款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210105" type="checkbox"
								name="ProductCategray" value="210105" />
							<label for="ProductCategray_210105">
								卖出回购资产
							</label>
						</li>
						<li>
							<input id="ProductCategray_210106" type="checkbox"
								name="ProductCategray" value="210106" />
							<label for="ProductCategray_210106">
								向中央银行借款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210107" type="checkbox"
								name="ProductCategray" value="210107" />
							<label for="ProductCategray_210107">
								同业往来
							</label>
						</li>
						<li>
							<input id="ProductCategray_210108" type="checkbox"
								name="ProductCategray" value="210108" />
							<label for="ProductCategray_210108">
								系统内资金往来
							</label>
						</li>
						<li>
							<input id="ProductCategray_210109" type="checkbox"
								name="ProductCategray" value="210109" />
							<label for="ProductCategray_210109">
								外汇买卖
							</label>
						</li>
						<li>
							<input id="ProductCategray_210110" type="checkbox"
								name="ProductCategray" value="210110" />
							<label for="ProductCategray_210110">
								FTU委托存款及委托投资基金
							</label>
						</li>
						<li>
							<input id="ProductCategray_210111" type="checkbox"
								name="ProductCategray" value="210111" />
							<label for="ProductCategray_210111">
								代理金融机构委托贷款基金
							</label>
						</li>
						<li>
							<input id="ProductCategray_210112" type="checkbox"
								name="ProductCategray" value="210112" />
							<label for="ProductCategray_210112">
								各项准备
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						资金运用
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210201" type="checkbox"
								name="ProductCategray" value="210201" />
							<label for="ProductCategray_210201">
								各项贷款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210202" type="checkbox"
								name="ProductCategray" value="210202" />
							<label for="ProductCategray_210202">
								有价证券
							</label>
						</li>
						<li>
							<input id="ProductCategray_210203" type="checkbox"
								name="ProductCategray" value="210203" />
							<label for="ProductCategray_210203">
								股权及其他投资
							</label>
						</li>
						<li>
							<input id="ProductCategray_210204" type="checkbox"
								name="ProductCategray" value="210204" />
							<label for="ProductCategray_210204">
								应收及预付款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210205" type="checkbox"
								name="ProductCategray" value="210205" />
							<label for="ProductCategray_210205">
								买入返售资产
							</label>
						</li>
						<li>
							<input id="ProductCategray_210206" type="checkbox"
								name="ProductCategray" value="210206" />
							<label for="ProductCategray_210206">
								存放中央准备金存款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210207" type="checkbox"
								name="ProductCategray" value="210207" />
							<label for="ProductCategray_210207">
								存放中央银行特种存款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210208" type="checkbox"
								name="ProductCategray" value="210208" />
							<label for="ProductCategray_210208">
								缴存中央银行财政性存款
							</label>
						</li>
						<li>
							<input id="ProductCategray_210209" type="checkbox"
								name="ProductCategray" value="210209" />
							<label for="ProductCategray_210209">
								同业往来（运用方）
							</label>
						</li>
						<li>
							<input id="ProductCategray_210211" type="checkbox"
								name="ProductCategray" value="210211" />
							<label for="ProductCategray_210211">
								库存现金
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						表外或其他（表外理财除外）
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210301" type="checkbox"
								name="ProductCategray" value="210301" />
							<label for="ProductCategray_210301">
								代理发债
							</label>
						</li>
						<li>
							<input id="ProductCategray_210302" type="checkbox"
								name="ProductCategray" value="210302" />
							<label for="ProductCategray_210302">
								应付信用证（进口开证）
							</label>
						</li>
						<li>
							<input id="ProductCategray_210303" type="checkbox"
								name="ProductCategray" value="210303" />
							<label for="ProductCategray_210303">
								应付保函/备用证（保函/备用证开立）
							</label>
						</li>
						<li>
							<input id="ProductCategray_210304" type="checkbox"
								name="ProductCategray" value="210304" />
							<label for="ProductCategray_210304">
								信用证保兑（进口应付信用证加保）
							</label>
						</li>
						<li>
							<input id="ProductCategray_210305" type="checkbox"
								name="ProductCategray" value="210305" />
							<label for="ProductCategray_210305">
								应付银行承兑汇票
							</label>
						</li>
						<li>
							<input id="ProductCategray_210306" type="checkbox"
								name="ProductCategray" value="210306" />
							<label for="ProductCategray_210306">
								应收信用证（出口交单）
							</label>
						</li>
						<li>
							<input id="ProductCategray_210307" type="checkbox"
								name="ProductCategray" value="210307" />
							<label for="ProductCategray_210307">
								应收保函/备用证
							</label>
						</li>
						<li>
							<input id="ProductCategray_210308" type="checkbox"
								name="ProductCategray" value="210308" />
							<label for="ProductCategray_210308">
								信用证保兑（出口应收信用证加保）
							</label>
						</li>
						<li>
							<input id="ProductCategray_210309" type="checkbox"
								name="ProductCategray" value="210309" />
							<label for="ProductCategray_210309">
								应收银行承兑汇票
							</label>
						</li>
						<li>
							<input id="ProductCategray_210310" type="checkbox"
								name="ProductCategray" value="210310" />
							<label for="ProductCategray_210310">
								远期结售汇
							</label>
						</li>
						<li>
							<input id="ProductCategray_210311" type="checkbox"
								name="ProductCategray" value="210311" />
							<label for="ProductCategray_210311">
								汇率掉期业务（远期未交割部分）
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						表外理财
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210401" type="checkbox"
								name="ProductCategray" value="210401" />
							<label for="ProductCategray_210401">
								表外理财
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						账户信息报送
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210501" type="checkbox"
								name="ProductCategray" value="210501" />
							<label for="ProductCategray_210501">
								FTA、FTN、FTU 账户信息报送
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						关联账户信息下发
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_310101" type="checkbox"
								name="ProductCategray" value="310101" />
							<label for="ProductCategray_310101">
								关联账户信息下发
							</label>
						</li>
					</ul>
				</fieldset>
			</fieldset>
			</div>			
		</s:form>
	</body>
</html>
