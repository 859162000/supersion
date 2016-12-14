<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>" ></base>
    <title>新客户风险接口下载</title>
    <script src="js/jquery/jquery.1.7.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/SelectInfol.js" type="text/javascript"></script>
	
	<link rel="stylesheet" href="css/buttons.css" type="text/css"></link>
	<link href="NCR/css/Comm.css" rel="stylesheet" type="text/css" />
	<link href="NCR/css/JSGrid.css" rel="stylesheet" type="text/css" />

   <script type="text/javascript" >

    $(document).ready(function () {
        $("#selectall").click(function () {
        	var check=$(this).attr("checked");
			if(check==null){
				$("#JS_Grid_Data").children("div").children("div").children(":checkbox").removeAttr("checked"); 
			}
			else{
				$("#JS_Grid_Data").children("div").children("div").children(":checkbox").attr("checked",'true');
			}
        });
    });
    
    //xml文件下载
    function downFile() {
        
        var Str="";
        var boo = false;
        
    	if(form1.uploadFile.value==null || form1.uploadFile.value==""){
    		Str=Str+"\n"+"请选择文件!";
			boo=true;
        }
        
    	var strRadio = document.getElementsByName("strRadio");
        var radioSel = undefined;
        for(var i =0;i<strRadio.length;i++){
        	if(strRadio[i].checked){
        		radioSel = strRadio[i].value;
        	}
        }
        if(radioSel==undefined || strRadio==""){
    		Str=Str+"\n"+"客户信息为空";
			boo=true;
        }
        
        if(boo==true){
			alert(Str);
        }
        else if (confirm("文件下载？")) {
	        form1.submit();
	        return true;
        }
	}

	</script>
</head>	
<body>

  <form name="form1" action="<%=basePath %>/ncr/FeedbackReport.action" enctype="multipart/form-data" method="post">
	<div id="Top_Bar" style="width: 100%; margin-left: 0px; border-left: 0px solid #dadada; background-color: #F3F3F3; font-size: 12px; font-family: 微软雅黑; float: left;margin-bottom: 5px;">
		<a class="button chat icon" style="margin-left: 5px; margin-top: 2px; font-size: 12px; font-family: 微软雅黑;" onclick="downFile();">导入</a>
	</div>
			
	<div title="<s:property value='operationName'/>"
		class="JS_Grid_Head_Row_CellLink" style="width: 250px;">
		<s:file class="files" name="uploadFile" cssStyle="width:180px;"
		 cssClass="inputNorm99" onmouseover="this.style.backgroundColor='c0c0a0';"
		 onmouseout="this.style.backgroundColor='';"></s:file>
	</div>
	
	<div id="Grid_Task" style="overflow-x: hidden; overflow-y: hidden; height: 365px;width: 100%;">
		<div id="JS_Grid_Head_Row" class="JS_Grid_Head_Row" style="font-size: 12px; font-family: 微软雅黑; margin-left: 3px;width: 100%;">
			<div title="名称" class="JS_Grid_Head_Row_Cell" style="width: 200px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">名称</div>
		</div>
		<div id="JS_Grid_Data" class="JS_Grid_Data" style="overflow-x: hidden; overflow-y: auto; height: 298px; margin-top: 0px;">  <!-- width: 100%; -->
			<s:iterator value="serviceResult" id="sr">
				<div class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;width: 100%;" >
						<div class="JS_Grid_Data_Row_Cell" style="text-align:left;width: 100%;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
							<input type="radio" name="strRadio" value="<s:property value="%{#sr.key}" />"><s:property value="%{#sr.value}" /></input>
						</div>
				</div>
			</s:iterator>
		</div>
	</div>
	</form>
</body>
</html>



