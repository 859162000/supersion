<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="framework.helper.HttpUtils"%>
<%@page import="java.util.*"%>
<%@page import="framework.show.ShowNavigationComponent" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>导航窗口</title>
		<link href="<%=basePath %>css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>css/IcoCss.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>dtree/dtree.css" rel="StyleSheet" type="text/css" />
		<link href="<%=basePath %>css/NavigationPageCss.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath %>js/Comml.js" type="text/javascript"></script>
		<script src="<%=basePath %>dtree/dtree.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/Shortcutkeys.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/IcoMeum_Navigation.js" type="text/javascript"></script>
		<script type="text/javascript">
			var DivMeum = null;
			function stop() {
				return false;
			}
		    $(document).bind("contextmenu",function(){return false;});
		    $(document).bind("selectstart",function(){return false;});
			function AutoSize() {
		
				document.getElementById('tree_meum_div').style.height = ($(window).height() - 30) + 'px';
				document.getElementById('Grid_Task').style.height = ($(window).height() - 35) + 'px';
				document.getElementById('Grid_Task').style.width = ($(window).width() - 230) + 'px';
				DivMeum.SetDeskMeumLayout();
			}
			var Now_Mode = "Table";
			$(document).ready(function() {
		        
		        d.closeAll(); // 关闭tree所有节点
				$("#SuperManagement").hide();
				$("#TaskManagement").hide;
				DivMeum = new ObjectDeskMeum_Navigation("Grid_Task",getParameter("OpenMeum"), "Top", "",getParameter("MeumName"));
		
				$(document).click(function() {
					$("#RightMeum").remove();
				});
				
				var strids = $(".NavigationClick").last().attr("strid");
				var stridMeths =  $(".dtree").find("[strid='"+strids+"']").children("a").attr("href");
				if(stridMeths){
					var id = stridMeths.substring(16)
					var idones = id.substring(0,id.indexOf(")"))
					$(document).bind("click",d.o(idones))
				}				
				
				//运行窗体拖拽大小
				BindResizeThree("Split_Div", "tree_meum_div", "Grid_Task");
				
				$("#DeskMeum").click(function() {
							$("#Top_Bar").children("[Div_Type='Navigation']:gt(0)").remove();
							new ObjectDeskMeum_Navigation("Grid_Task","desk_ico", "Left", "desktop", "桌面");
				});

				$(".dTreeNode").click(function () {
					$(".dtree").find(".dTreeNode").css("background-color","white");
					$(this).css("background-color","#938E8E");
					
				    var strId = $(this).attr("strId");
					var meumName = $(this).attr("menuName");
					var strUrl = $(this).attr("strUrl");
					
					if(strUrl.indexOf("NavigationPage.jsp") > -1){
						new ObjectDeskMeum_Navigation("Grid_Task",strId,"Left",strId,meumName);
			       	} 
			       	else{
				      
			       		if(strUrl.indexOf(".jsp") > -1){
			       			strUrl = "<%=basePath %>" + strUrl;
			       		}
			       		else{
			       			strUrl = "<%=basePath %>" + strUrl + ".action";
						}
		               var heigth = getWindowsHeight(strUrl);
		           	   var width = getWindowsWidth(strUrl);
		               strUrl += "?windowId=" +　strId;
			    	   new window.parent.ObjectWindows("Main_Body", strId + "Windows", heigth, width, strUrl, meumName, "no");
			       }
			   });
			});
			function CreateNavigation(Tmp_Object,strMeumNodeName_tmp){
				for (var index=0; index<d.aNodes.length; index++) {
					if(strMeumNodeName_tmp==d.aNodes[index].pid){
					var path='';
						if(d.aNodes[index].url.indexOf('NavigationPage.jsp')>-1){
							path='path';
						}
						Tmp_Object.CreateNewNavigationMeum(d.aNodes[index].id,d.aNodes[index].name,path,
								d.aNodes[index].icon.substring(18),d.aNodes[index].url,d.aNodes[index].title);
					}
				}		 
			}
			function getNavigationNameHtml(strMenuID){
				return f(strMenuID).substring(1);
			}
			function f(strMenuID){
				var navigationNameHtml = '';
				for (var index=0; index<d.aNodes.length; index++) {
					if(d.aNodes[index].id==strMenuID){
						navigationNameHtml=','+d.aNodes[index].id+'|'+d.aNodes[index].name+navigationNameHtml;
						strMenuID=d.aNodes[index].pid;
						if(strMenuID!='-1'){
							navigationNameHtml=f(strMenuID)+navigationNameHtml;
						}
						break;
					}
				}
				return navigationNameHtml;
			}
			
			function getWindowsHeight(strUrl){
		        if(strUrl.indexOf("coresystem.dto.ActionExcute") > -1){
		            return <s:property value="@framework.helper.ReflectOperation@getShowInstance('coresystem.dto.ActionExcute','1').getWindowHeight()" />;
		        }
		        else{
		            return $(window).height()+50;
		        }
			}
		
			function getWindowsWidth(strUrl){
				if(strUrl.indexOf("coresystem.dto.ActionExcute") > -1){
		            return <s:property value="@framework.helper.ReflectOperation@getShowInstance('coresystem.dto.ActionExcute','1').getWindowWidth()" />;
		        }
		        else{
		            return 1000;
		        }
			}
</script>
	
	</head>
	<body onResize="AutoSize();"
		style="margin: 0px; cursor: default; width: 100%; height: 100%;font-family:'Microsoft Yahei';"
		onload="AutoSize();">
		<div id="Top_Bar">
			<div
				style="float: right; margin-right: 20px; line-height: 30px;font-family:'Microsoft Yahei';color: Black; font-size: 12px; cursor: pointer;"
				onclick="location.reload();">
				刷新
			</div>
			<div id="DeskMeum" Div_Type='Navigation'>
			</div>
		</div>
		<div id="tree_meum_div"
			style="width: 228px; height: auto; float: left;font-family:'Microsoft Yahei'; margin-top: 0px; margin-left: 0px; overflow-y: auto;overflow-x: hidden;">
			<script type="text/javascript">
				  d = new dTree('d','../');
				  <%
				  List<ShowNavigationComponent> list1=HttpUtils.GetShowNavigationComponent();
				  for(ShowNavigationComponent showNavigationComponent:list1){
				  	%>
				  	d.add('<%=showNavigationComponent.getId()%>','<%=showNavigationComponent.getParentId()%>','<%=showNavigationComponent.getName()%>'
				  	,'<%=showNavigationComponent.getUrl()%>','','','../images/icomeum/<%=showNavigationComponent.getIco()%>','../images/icomeum/<%=showNavigationComponent.getIco()%>');
				  	<%
				  }
				  %>
				  document.write(d);
			</script>
		</div>
		<div id="Split_Div" style="left: 230px;"></div>
		<div id="Grid_Task" style="font-family:'Microsoft Yahei';position: absolute; height: 438px; left: 230px; margin-top: 0px; overflow: auto; padding-top: 5px;">
		</div>
	</body>
</html>
