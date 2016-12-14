<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>"></base>
    <title>EAS接口下载</title>
    <script src="js/jquery/jquery.1.7.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/SelectInfol.js" type="text/javascript"></script>
	
	<link rel="stylesheet" href="css/buttons.css" type="text/css"></link>
	<link href="css/Comm.css" rel="stylesheet" type="text/css" />
	<link href="css/JSGrid.css" rel="stylesheet" type="text/css" />

   <script type="text/javascript">
	
	
	 /* //全选
    function checkAll() {
        var el = document.getElementsByTagName('INPUT');
        var len = el.length;
        //全选按钮是否选中
        var selectall = document.getElementById("selectall").value;
        for(var i=0; i<len; i++) {
            if((el[i].type=="checkbox") && (el[i].name=="strCheckbox"))
            {
	            if("false"==selectall){
	        		
        			document.getElementById("selectall").value="true";
	                if(!el[i].checked)
	                    el[i].checked = true;
	                else(el[i].checked)
	                    el[i].checked = true;
	       		 }else{
	        		
        			document.getElementById("selectall").value="false";
	       		 	if(el[i].checked)
	                    el[i].checked = false;
	       		 }
            }
        }
    }
    //反选
    function clearAll() {
        var el = document.getElementsByTagName('input');
        var len = el.length;
        for(var i=0; i<len; i++) {
            if((el[i].type=="checkbox") && (el[i].name=="strCheckbox")) {
                el[i].checked = false;
            }
        }
    }*/
    
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
    function downFile(form) {
        
        var Str="";
        var boo = false;
    	if(form.reportInstinfo.value==null||form.reportInstinfo.value==""){
    		Str=Str+"\n"+"机构为空";
			boo=true;
        }
    	if(form.endDate.value==null||form.endDate.value==""){
    		Str=Str+"\n"+"结束日期为空";
			boo=true;
        }
    	if(form.startDate.value==null||form.startDate.value==""){
    		Str=Str+"\n"+"开始日期为空";
			boo=true;
        }
        if(boo==true){
			alert(Str);
        }
        else if(form.startDate.value.length!=10||form.endDate.value.length!=10){
        	alert("日期格式有误");
        }
        else if(form.startDate.value>form.endDate.value){
        	alert("开始日期大于结束日期");
        }
        else{
            if (checkMutilSelect(form.strCheckbox)) {
                if (confirm("文件下载？")) {
                    form.submit();
                    return true;
                }
            } else {
                alert("请选择需要下载的文件！");
            }
        }
	}
    /**
     * 校验checkBox、radio是否选择
     * chk: object
     */
    function checkMutilSelect(chk) {
        if (chk == null) {
            return false;
        }
        var size = chk.length;
        if (size == null) {
            if (chk.checked) {
                return true;
            } else {
                return false;
            }
        } else {
            for (var i = 0; i < size; i++) {
                if (chk[i].checked) {
                    return true;
                }
            }
        }
        return false;
    }
</script>
</head>	
<body>

  <form action="<%=basePath %>/eas/ShowView-InterfaceDownEast.action" method="post">
	
	<div id="Top_Bar" style="width: 100%; margin-left: 0px; border-left: 0px solid #dadada; background-color: #F3F3F3; font-size: 12px; font-family: 微软雅黑; float: left;margin-bottom: 5px;">
		<a class="button chat icon" style="margin-left: 5px; margin-top: 2px; font-size: 12px; font-family: 微软雅黑;" onclick="downFile(document.forms(0));">下载</a>
	</div>
	<div id="Top_Condtion" style="width: 100%; height: 30px; margin-top: 5px; background-color: #F3F3F3; font-size: 12px; font-family: 微软雅黑;clear: both;">
		<div style="float: left; width: 220px; margin-right: 5px; margin-top: 7px; line-height: 25px; height: 23px;">
			<div style="float: left; width: 85px; text-align: right;line-height: 22px;" class="Font_css_normal_Condtion">开始日期：</div>
			<div style="float: left; width: 120px; text-align: left;margin-left: 2px;">
				<input type="text" name="startDate" id="time" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"/></div>
		</div>
		
		<div style="float: left; width: 220px; margin-right: 5px; margin-top: 7px; line-height: 25px; height: 23px;">
			<div style="float: left; width: 85px; text-align: right;line-height: 22px;" class="Font_css_normal_Condtion">结束日期：</div>
			<div style="float: left; width: 120px; text-align: left;margin-left: 2px;">
				<input type="text" name="endDate" id="time" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"/></div>
		</div>
		
		<div style="float: left; width: 220px; margin-right: 5px; margin-top: 7px; line-height: 25px; height: 23px;">
			<div style="float: left; width: 85px; text-align: right;line-height: 22px;" class="Font_css_normal_Condtion">填报机构：</div>
				<%-- multiple="multiple" 内部机构号（区分机构）;银监会非现场机构代码;金融许可证号--%>
			<div style="float: left; width: 120px; text-align: left;margin-left: 2px;">
				<select name="reportInstinfo" style="width:100%;">
					<s:iterator value="serviceResult.get(1)" id="rt">
						<option value="<s:property value="%{#rt.get(0)}"/>"><s:property value="%{#rt.get(1)}"/></option>
					</s:iterator>
				</select>
			</div>
		</div>
		
		<div style="float: left; width: 220px; margin-right: 5px; margin-top: 7px; line-height: 25px; height: 23px;">
			<div style="float: left; width: 85px; text-align: right;line-height: 22px;" class="Font_css_normal_Condtion">生成格式：</div>
			<div style="float: left; width: 120px; text-align: left;margin-left: 2px;">
				<select name="createFormat" style="width:100%;">
					<s:iterator value="serviceResult.get(2)" id="rt">
						<option value="<s:property value="%{#rt.get(0)}"/>"><s:property value="%{#rt.get(1)}"/></option>
					</s:iterator>
				</select>
			</div>
		</div>
	</div>
	<div id="Grid_Task" style="overflow-x: hidden; overflow-y: hidden; height: 365px;width: 100%;">
		<div id="JS_Grid_Head_Row" class="JS_Grid_Head_Row" style="font-size: 12px; font-family: 微软雅黑; margin-left: 3px;width: 100%;">
			<div class="JS_Grid_Head_Row_Cell" style='width: 20px;'>
				<input type="checkbox" value="false" id="selectall" name="selectall" /></div>
			<div title="名称" class="JS_Grid_Head_Row_Cell" style="width: 200px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">名称</div>
		</div>
		
		<div id="JS_Grid_Data" class="JS_Grid_Data" style="overflow-x: hidden; overflow-y: auto; height: 298px; width: 100%; margin-top: 0px;">
			<div style="width: 100%; height: 1px; line-height: 0px;"></div>
				<s:iterator value="serviceResult.get(0)" id="vt">
					<div class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;width: 100%" >
						<div id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="width: 20px;">
							<input type="checkbox" name="strCheckbox" value="<s:property value="%{#vt.get(0)}" />"></input>
						</div>
						<div title="<s:property value="%{#vt.get(1)}"/>" class="JS_Grid_Data_Row_Cell" style="text-align:left;width: 200px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
							<s:property value="%{#vt.get(1)}"/> 			
						</div>
					</div>
				</s:iterator>
			<div style="width: 100%; height: 1px; line-height: 0px;"></div>
		</div>
	</div>
	</form>
</body>
</html>



