<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<base href="<%=basePath %>"></base>    
    <title>My JSP 'ShowList.jsp' starting page</title>
	<link rel="StyleSheet" href="dtree/dtree.css" type="text/css" />
	<link href="css/Comm.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>dtree/dtree.js" type="text/javascript" ></script>
	<script src="<%=basePath%>js/Comml.js" type="text/javascript" ></script>
	<script src="<%=basePath %>js/Windows.js" type="text/javascript" ></script>
	<script src="<%=basePath %>js/Shortcutkeys.js" type="text/javascript" ></script>
	<script src="<%=basePath%>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
	<script type="text/javascript">
	
		$(document).ready(function() {

			var adr = $("#tree_meum_div").find(".dTreeNode:first").attr("strurl");
			
			if('<s:property value="serviceResult.rootURL"/>'==''){
				$("#frmtreeright1").attr("src",adr);
			}
			
			
			$(".dTreeNode").click(function () {
			
				$(".dtree").find(".dTreeNode").css("background-color","white");
				$(this).css("background-color","#938E8E");
				
				var strUrl = $(this).attr("strUrl");
		
				document.all.frmtreeright1.src = strUrl;
		
		   	});
		   	
			TreeBindResizeThree("Split_Div", "tree_meum_div", "Grid_Task");
		});
		
		function AutiSize()
        {
			
		       
	        var treeWidth = parseInt(document.getElementById('tree_meum_div').style.width);
			//document.getElementById('frmtreeright1').style.width = ($(window).width() - treeWidth - 10) +'px';
			//alert(document.getElementById('frmtreeright1').style.width);
			//alert(document.getElementById('frmtreeright1').style.width);

	        document.getElementById('Grid_Task').style.width = ($(window).width() - treeWidth - 5) +'px';
        }

		function TreeBindResizeThree(SqlitDiv,LeftDiv,RightDiv){
			//初始化参数
			var el = document.getElementById(SqlitDiv);
			var LeftDiv_Object = document.getElementById(LeftDiv).style;
			var RightDiv_Object = document.getElementById(RightDiv).style;
			var RightDiv_Object_Width = $("#" +RightDiv).width();
			//鼠标的 X 和 Y 轴坐标 
			LeftDiv_x = 0;
			RightDiv_x = 0;
			//邪恶的食指 
			$(el).mousedown(function(e) {
				//按下元素后，计算当前鼠标与对象计算后的坐标 
				LeftDiv_x = e.clientX - el.offsetWidth - $("#" +LeftDiv).width();
				RightDiv_x = e.clientX - el.offsetWidth + $("#" +RightDiv).width();
				//在支持 setCapture 做些东东 
				el.setCapture ? (
				//捕捉焦点 
				el.setCapture(),
				//设置事件 
				el.onmousemove = function(ev) {
					mouseMove(ev || event);
				},
				el.onmouseup = mouseUp) : (
				//绑定事件 
				$(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp));
				//防止默认事件发生 
				e.preventDefault();
			});
			//移动事件 
			function mouseMove(e) {
				
				
			     if((e.clientX - LeftDiv_x) < 50 || (RightDiv_x - e.clientX) < 50)
			        return;
				 LeftDiv_Object.width = e.clientX - LeftDiv_x + 'px';
				 // RightDiv_Object.left = e.clientX + 'px';
				 RightDiv_Object.left= el.style.left = (e.clientX+1) + 'px';
				 //alert((RightDiv_x - e.clientX) + 'px');
				 RightDiv_Object.width = (RightDiv_x - e.clientX) + 'px';

				 AutiSize();
				   
			}
			//停止事件 
			function mouseUp() {
				//在支持 releaseCapture 做些东东 
				el.releaseCapture ? (
				//释放焦点 
				el.releaseCapture(),
				//移除事件 
				el.onmousemove = el.onmouseup = null) : (
				//卸载事件 
				$(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp));
			}
		}
	</script>
  </head>
	    
  <body onResize="AutiSize();" onload ="AutiSize();" style="" ondragstart="return false;">
  <div id="tree_meum_div" style="width: 220px; height: 100%; float: left; font-family:'Microsoft Yahei'; margin-top: 0px; margin-left: 0px;overflow-y: auto;overflow-x:hidden;">
		<script type="text/javascript">
			  d = new dTree('d','<%=basePath %>');
			  //d.target="frmtreeright";
			  // target设置为frmtreeright
			  <s:iterator value="serviceResult.treeValue" id="vt" >
				 d.add('<s:property value="%{#vt.treeNodeID}"/>', '<s:property value="%{#vt.parentID}"/>',
				   '<s:property  value="%{#vt.showName}"/>', '<s:property value="%{#vt.URL}"/>', '',
				   'frmtreeright','<%=basePath %><s:property value="%{#vt.normalImageURL}"/>',
				   '<%=basePath %><s:property value="%{#vt.expandImageURL}"/>');
			  </s:iterator>
			  document.write(d);
			  //d.closeAll();
		</script>

	</div>
	<div id="Split_Div" style="margin-left: 1px; left: 220px; position: absolute;"></div>
    <div id="Grid_Task" style="left: 225px; top: 0px; width: 770px; height: 100%; right: 0px; bottom: 0px; overflow-x:hidden; overflow-y:hidden; padding-top: 0px; position: absolute;">
       <iframe allowtransparency="true" src="<s:property value="serviceResult.rootURL"/>" class=right_iframe id=frmtreeright1 name=frmtreeright marginwidth="0"  style="" height="100%" width="100%" frameBorder=0 >
       </iframe>
    </div>
  </body>
</html>











