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
			alert('��ѡ����������!')
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
			alert('��ѡ����������!')
			return false;
		}
		if(!ProductCategrayFlag)
		{
			alert('��ѡ��������!')
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
				�������ڣ�
			</label>
			<input id="dtDate" name="dtDate" style="font-family: Terminal; width: 153px" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
			<input type="button" value="���ͱ���" 
		          onmouseover="this.style.backgroundColor='#c0de98';" 
		          onmouseout="this.style.backgroundColor='';"
		          Class="normButton" 		          
		           onclick="if(validate()){onOperationClick('/framework','SendMessage');}"/>
		    <input type="button" value="���ر���" 
		          onmouseover="this.style.backgroundColor='#c0de98';" 
		          onmouseout="this.style.backgroundColor='';"
		          Class="normButton" 
		          onclick="if(validate()){onOperationClick('/framework','DownLoadMessage');}"/>			
			<br>
			<fieldset>
				<legend>
					��������
				</legend>
				<input id="OpertFlag_A" type="checkbox" name="OpertFlag" value="1" />
				<label for="OpertFlag_A">
					����
				</label>
				<input id="OpertFlag_U" type="checkbox" name="OpertFlag" value="2" />
				<label for="OpertFlag_U">
					����
				</label>
				<input id="OpertFlag_D" type="checkbox" name="OpertFlag" value="3" />
				<label for="OpertFlag_D">
					ɾ��
				</label>
			</fieldset>
			<div style="height:340px;overflow:auto">
			<fieldset>
				<legend>
					��������
				</legend>
				<fieldset>
					<legend>
						�ʽ���Դ
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210101" type="checkbox"
								name="ProductCategray" value="210101" />
							<label for="ProductCategray_210101">
								��λ���
							</label>
						</li>
						<li>
							<input id="ProductCategray_210102" type="checkbox"
								name="ProductCategray" value="210102" />
							<label for="ProductCategray_210102">
								FTU�ڲ��-����ծȯ
							</label>
						</li>
						<li>
							<input id="ProductCategray_210103" type="checkbox"
								name="ProductCategray" value="210103" />
							<label for="ProductCategray_210103">
								FTU�ڲ��-�г��ڽ��
							</label>
						</li>
						<li>
							<input id="ProductCategray_210104" type="checkbox"
								name="ProductCategray" value="210104" />
							<label for="ProductCategray_210104">
								Ӧ�������տ�
							</label>
						</li>
						<li>
							<input id="ProductCategray_210105" type="checkbox"
								name="ProductCategray" value="210105" />
							<label for="ProductCategray_210105">
								�����ع��ʲ�
							</label>
						</li>
						<li>
							<input id="ProductCategray_210106" type="checkbox"
								name="ProductCategray" value="210106" />
							<label for="ProductCategray_210106">
								���������н��
							</label>
						</li>
						<li>
							<input id="ProductCategray_210107" type="checkbox"
								name="ProductCategray" value="210107" />
							<label for="ProductCategray_210107">
								ͬҵ����
							</label>
						</li>
						<li>
							<input id="ProductCategray_210108" type="checkbox"
								name="ProductCategray" value="210108" />
							<label for="ProductCategray_210108">
								ϵͳ���ʽ�����
							</label>
						</li>
						<li>
							<input id="ProductCategray_210109" type="checkbox"
								name="ProductCategray" value="210109" />
							<label for="ProductCategray_210109">
								�������
							</label>
						</li>
						<li>
							<input id="ProductCategray_210110" type="checkbox"
								name="ProductCategray" value="210110" />
							<label for="ProductCategray_210110">
								FTUί�д�ί��Ͷ�ʻ���
							</label>
						</li>
						<li>
							<input id="ProductCategray_210111" type="checkbox"
								name="ProductCategray" value="210111" />
							<label for="ProductCategray_210111">
								������ڻ���ί�д������
							</label>
						</li>
						<li>
							<input id="ProductCategray_210112" type="checkbox"
								name="ProductCategray" value="210112" />
							<label for="ProductCategray_210112">
								����׼��
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						�ʽ�����
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210201" type="checkbox"
								name="ProductCategray" value="210201" />
							<label for="ProductCategray_210201">
								�������
							</label>
						</li>
						<li>
							<input id="ProductCategray_210202" type="checkbox"
								name="ProductCategray" value="210202" />
							<label for="ProductCategray_210202">
								�м�֤ȯ
							</label>
						</li>
						<li>
							<input id="ProductCategray_210203" type="checkbox"
								name="ProductCategray" value="210203" />
							<label for="ProductCategray_210203">
								��Ȩ������Ͷ��
							</label>
						</li>
						<li>
							<input id="ProductCategray_210204" type="checkbox"
								name="ProductCategray" value="210204" />
							<label for="ProductCategray_210204">
								Ӧ�ռ�Ԥ����
							</label>
						</li>
						<li>
							<input id="ProductCategray_210205" type="checkbox"
								name="ProductCategray" value="210205" />
							<label for="ProductCategray_210205">
								���뷵���ʲ�
							</label>
						</li>
						<li>
							<input id="ProductCategray_210206" type="checkbox"
								name="ProductCategray" value="210206" />
							<label for="ProductCategray_210206">
								�������׼������
							</label>
						</li>
						<li>
							<input id="ProductCategray_210207" type="checkbox"
								name="ProductCategray" value="210207" />
							<label for="ProductCategray_210207">
								��������������ִ��
							</label>
						</li>
						<li>
							<input id="ProductCategray_210208" type="checkbox"
								name="ProductCategray" value="210208" />
							<label for="ProductCategray_210208">
								�ɴ��������в����Դ��
							</label>
						</li>
						<li>
							<input id="ProductCategray_210209" type="checkbox"
								name="ProductCategray" value="210209" />
							<label for="ProductCategray_210209">
								ͬҵ���������÷���
							</label>
						</li>
						<li>
							<input id="ProductCategray_210211" type="checkbox"
								name="ProductCategray" value="210211" />
							<label for="ProductCategray_210211">
								����ֽ�
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						�����������������Ƴ��⣩
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210301" type="checkbox"
								name="ProductCategray" value="210301" />
							<label for="ProductCategray_210301">
								����ծ
							</label>
						</li>
						<li>
							<input id="ProductCategray_210302" type="checkbox"
								name="ProductCategray" value="210302" />
							<label for="ProductCategray_210302">
								Ӧ������֤�����ڿ�֤��
							</label>
						</li>
						<li>
							<input id="ProductCategray_210303" type="checkbox"
								name="ProductCategray" value="210303" />
							<label for="ProductCategray_210303">
								Ӧ������/����֤������/����֤������
							</label>
						</li>
						<li>
							<input id="ProductCategray_210304" type="checkbox"
								name="ProductCategray" value="210304" />
							<label for="ProductCategray_210304">
								����֤���ң�����Ӧ������֤�ӱ���
							</label>
						</li>
						<li>
							<input id="ProductCategray_210305" type="checkbox"
								name="ProductCategray" value="210305" />
							<label for="ProductCategray_210305">
								Ӧ�����гжһ�Ʊ
							</label>
						</li>
						<li>
							<input id="ProductCategray_210306" type="checkbox"
								name="ProductCategray" value="210306" />
							<label for="ProductCategray_210306">
								Ӧ������֤�����ڽ�����
							</label>
						</li>
						<li>
							<input id="ProductCategray_210307" type="checkbox"
								name="ProductCategray" value="210307" />
							<label for="ProductCategray_210307">
								Ӧ�ձ���/����֤
							</label>
						</li>
						<li>
							<input id="ProductCategray_210308" type="checkbox"
								name="ProductCategray" value="210308" />
							<label for="ProductCategray_210308">
								����֤���ң�����Ӧ������֤�ӱ���
							</label>
						</li>
						<li>
							<input id="ProductCategray_210309" type="checkbox"
								name="ProductCategray" value="210309" />
							<label for="ProductCategray_210309">
								Ӧ�����гжһ�Ʊ
							</label>
						</li>
						<li>
							<input id="ProductCategray_210310" type="checkbox"
								name="ProductCategray" value="210310" />
							<label for="ProductCategray_210310">
								Զ�ڽ��ۻ�
							</label>
						</li>
						<li>
							<input id="ProductCategray_210311" type="checkbox"
								name="ProductCategray" value="210311" />
							<label for="ProductCategray_210311">
								���ʵ���ҵ��Զ��δ����֣�
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						�������
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210401" type="checkbox"
								name="ProductCategray" value="210401" />
							<label for="ProductCategray_210401">
								�������
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						�˻���Ϣ����
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_210501" type="checkbox"
								name="ProductCategray" value="210501" />
							<label for="ProductCategray_210501">
								FTA��FTN��FTU �˻���Ϣ����
							</label>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>
						�����˻���Ϣ�·�
					</legend>
					<ul>
						<li>
							<input id="ProductCategray_310101" type="checkbox"
								name="ProductCategray" value="310101" />
							<label for="ProductCategray_310101">
								�����˻���Ϣ�·�
							</label>
						</li>
					</ul>
				</fieldset>
			</fieldset>
			</div>			
		</s:form>
	</body>
</html>
