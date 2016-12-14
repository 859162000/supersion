<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>"></base>
		<title>ZXPTShowList</title>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
		<script type="text/javascript">
			$(document).ready(function() {
				TreeBindResizeThree("Split_Div", "iframe1", "iframe2");
			});

			function AutiSize()
	        {
		        var treeWidth = parseInt(document.getElementById('iframe1').style.width);
		        document.getElementById('iframe2').style.width = ($(window).width() - treeWidth) +'px';
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
	<body onResize="AutiSize();" onload ="AutiSize();" style="height: 99%;" ondragstart="return false;">
		<div id="iframe1" style="float: left; height:100%; width:280px;">
			<iframe allowtransparency="true" src="<%=basePath%><s:property value="serviceResult.valueTable.get(0).get(0).value" />" 
			marginwidth="0" height="100%" width="100%" frameBorder=0>
			</iframe>
		</div>
 		<div id="Split_Div" style="width:1px; float: left; height:100%; left: 280px; background-color:#dadada;"></div>
	   	<div id="iframe2" style="float: left;height:100%;">
		   	<iframe allowtransparency="true" src="<%=basePath%><s:property value="serviceResult.valueTable.get(0).get(1).value"/>"
		     marginwidth="0"  height="100%" width="100%" frameBorder=0>
			</iframe>
		</div>
	</body>
</html>