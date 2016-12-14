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
	<script src="<%=basePath%>js/DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/SelectInfol.js" type="text/javascript"></script>
	
	<link rel="stylesheet" href="css/buttons.css" type="text/css"></link>
	<link href="css/Comm.css" rel="stylesheet" type="text/css"/>
	<link href="css/NCR/JSGrid.css" rel="stylesheet" type="text/css"/>

   <script type="text/javascript" >

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
    	if(form.startDate.value==null||form.startDate.value==""){
    		Str=Str+"\n"+"上报年月为空";
			boo=true;
        }
        if(boo==true){
			alert(Str);
        }else{
        	if (checkMutilSelect(form.strCheckbox)) {
                if (confirm("文件下载？")) {
                    form.submit();
                    return true;
                }
            }else {
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

  <form action="<%=basePath %>/ncr/ShowView-InterfaceDownNewCustom.action" method="post">
	<div id="Top_Bar" style="width: 100%; margin-left: 0px; border-left: 0px solid #dadada; background-color: #F3F3F3; font-size: 12px; font-family: 微软雅黑; float: left;margin-bottom: 5px;">
		<a class="button chat icon" style="margin-left: 5px; margin-top: 2px; font-size: 12px; font-family: 微软雅黑;" onclick="downFile(document.forms[0]);">下载</a>
	</div>
	<div id="Top_Condtion" style="width: 100%; height: 30px; margin-top: 5px; background-color: #F3F3F3; font-size: 12px; font-family: 微软雅黑;clear: both;">
		<div style="float: left; width: 250px; margin-right: 5px; margin-top: 7px; line-height: 25px; height: 23px;">
			<div style="float: left; width: 85px; text-align: right;line-height: 22px;" class="Font_css_normal_Condtion">上报年月：</div>
			<div style="float: left; width: 120px; text-align: left;margin-left: 2px;">
				<input type="text" name="startDate" id="time" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" ></input>
			</div>
		</div>
		<div style="float: left; width: 220px; margin-right: 5px; margin-top: 7px; line-height: 25px; height: 23px;">
			<div style="float: left; width: 85px; text-align: right;line-height: 22px;" class="Font_css_normal_Condtion">填报机构：</div>
				<%-- multiple="multiple" 内部机构号（区分机构）;银监会非现场机构代码;金融许可证号--%>
			<div style="float: left; width: 120px; text-align: left;margin-left: 2px;">
				<select name="reportInstinfo">
					<s:iterator value="serviceResult.get(0)" id="rt">
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
		<div id="JS_Grid_Data" class="JS_Grid_Data" style="overflow-x: hidden; overflow-y: auto; height: 298px; margin-top: 0px;">  <!-- width: 100%; -->
			<div style="width: 100%; height: 1px; line-height: 0px;"></div>
				<div class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;width: 100%;" >
					<div id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="width: 20px; margin: 5px;"><input type="checkbox" name="strCheckbox" value="tableOne" /></div>
					<div title="对公及同业客户授信和表内外业务统计表" class="JS_Grid_Data_Row_Cell" style="text-align:left;width: 250px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
						表1、对公及同业客户授信和表内外业务统计表</div>
				</div>
				
				<div class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;width: 100%;" >
					<div id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="width: 20px; margin: 5px;"><input type="checkbox" name="strCheckbox" value="tableTwo" /></div>
					<div title="集团客户、供应链融资基本信息统计表" class="JS_Grid_Data_Row_Cell" style="text-align:left;width: 250px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
						表2、集团客户、供应链融资基本信息统计表</div>
				</div>
				
				<div class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;width: 100%;" >
					<div id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="width: 20px; margin: 5px;"><input type="checkbox" name="strCheckbox" value="tableThree" /></div>
					<div title="单一法人客户基本信息统计表" class="JS_Grid_Data_Row_Cell" style="text-align:left;width: 250px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
						表3、单一法人客户基本信息统计表</div>
				</div>
				
				<div class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;width: 100%;" >
					<div id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="width: 20px; margin: 5px;"><input type="checkbox" name="strCheckbox" value="tableFour" /></div>
					<div title="对公客户担保情况统计表" class="JS_Grid_Data_Row_Cell" style="text-align:left;width: 250px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
						表4、对公客户担保情况统计表</div>
				</div>
				
				<div class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;width: 100%;" >
					<div id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="width: 20px; margin: 5px;"><input type="checkbox" name="strCheckbox" value="tableFive" /></div>
					<div title="个人贷款违约情况统计表" class="JS_Grid_Data_Row_Cell" style="text-align:left;width: 250px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
						表5、个人贷款违约情况统计表</div>
				</div>
				
				<div class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;width: 100%;" >
					<div id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="width: 20px; margin: 5px;"><input type="checkbox" name="strCheckbox" value="tableSix" /></div>
					<div title="个人违约贷款担保情况统计表" class="JS_Grid_Data_Row_Cell" style="text-align:left;width: 250px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
						表6、个人违约贷款担保情况统计表</div>
				</div>
				
				
			<div style="width: 100%; height: 1px; line-height: 0px;"></div>
		</div>
	</div>
	</form>
</body>
</html>



