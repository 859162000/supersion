<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String relation ="";
	String relation1="";
	if(session.getAttribute("relation")!=null){
		relation="&"+session.getAttribute("relation").toString();
		relation1=relation.substring(1);
	}
%>

<%!Boolean IsShow=true;%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>"></base>
		<base target="_self" /> 
		<title>ShowList</title>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/JSGrid.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
		<link href="css/themes/default/easyui.css" rel="stylesheet" type="text/css">
		<link href="css/themes/icon.css" rel="stylesheet" type="text/css">
		<style type="text/css">
	        #bg{ display: none;  position: absolute;  top: 0%;  
	        	left: 0%;  width: 100%;  height: 100%;  
	        	background-color: black;  z-index:1001;  
	        	-moz-opacity: 0.7;  opacity:.30;  filter: alpha(opacity=70);
	        	}
	        #show{display: none;  position: absolute;
	        	 left: 22%;  width: 53%;  height: 70%;  padding: 8px;  
	        	 border: 8px solid #E8E9F7;  background-color: white;  
	        	 z-index:1002;  overflow: auto;}
	        ul {margin: 0;padding: 0}
			ul li {list-style-type: none;}
			.selectClass {float: left;width: 130px;margin-top: 4px;}
			.popdiv {z-index: 100;position: absolute;overflow-x: hidden;overflow-y: scroll;border: 1px solid #ccc;
				background: white;display: none}
			.triangle {text-decoration: none;color: skyblue;}
		</style>
		<script src="<%=basePath %>js/Comml.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/JsGrid.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/ShowList.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/Shortcutkeys.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/easyui/jquery.easyui.min.js" type="text/javascript" ></script>
		<script src="<%=basePath%>js/SelectExtend.js" type="text/javascript"> </script>
		<!--[if IE 6]>
			<script type="text/javascript">
				
				/**
				  * 在IE6中id为Menu_Frame_Div所对应的div内容为空时会显示多余的空白，此处脚本用于消除此影响
				  */
				function clear_blank_ie6(){
					var menuContent = $('#Menu_Frame_Div').html();
					if(null == menuContent || menuContent == ''){
						$('#Menu_Frame_Div').toggleClass("Menu_Frame_Div_Clear_Black_For_IE6-7",true);
					}
				}
				
				$(document).ready(function(){
					clear_blank_ie6();
				});
				
			</script>
		<![endif]-->
		
		<!--[if IE 7]>
			<script type="text/javascript">
				
				/**
				  * 在IE7中id为Menu_Frame_Div所对应的div内容为空时会显示多余的空白，此处脚本用于消除此影响
				  */
				function clear_blank_ie7(){
					var menuContent = $('#Menu_Frame_Div').html();
					if(null == menuContent || menuContent == ''){
						$('#Menu_Frame_Div').toggleClass("Menu_Frame_Div_Clear_Black_For_IE6-7",true);
					}
				}
				
				$(document).ready(function(){
					clear_blank_ie7();
				});
			</script>
		<![endif]-->
		
		<script type="text/javascript">
			var Now_Mode = "";
			var address = window.location.pathname;
	      	var one=address.split("/");
	      	var two=window.location.protocol+"//"+window.location.host+"/"+one[1]+"/";
	
			$(document).ready(function(){
				$("select[multiple='multiple']").each(function(){
			    	$(this).combobox({
						onSelect: setSelectValues,
						onUnselect:unSelectValues
			    	});
  				});
			}); 
			function showdiv() {            
			            document.getElementById("bg").style.display ="block";
			            document.getElementById("show").style.display ="block";
			        }
			function hidediv() {
			            document.getElementById("bg").style.display ='none';
			            document.getElementById("show").style.display ='none';
			        }
			function fullScreenImg(src)
			{
				$("#show").find("img").attr("src",src);                
				showdiv();
			}
			function setDefaultVal(e)
			{
			   var selectEle=$(e);
			   var id=selectEle.attr("id");
			   var aryIds=id.split("_");
			   if(aryIds.length>1)
			   {
			   		var valId="#value_"+aryIds[1];
			   		var v=$("[id='value_"+aryIds[1]+"']");
			   		var val=v.val();
			   		if(val!="")
			   		{
			   			var aryVal=val.split(",");
			   			selectEle.combobox('setValues', aryVal);
			   		}
			   }
			}
			function unSelectValues(param)
			{
			   var id=$(this).attr("id");
			   var aryIds=id.split("_");
			   if(aryIds.length>1)
			   {
			   		var valId="#value_"+aryIds[1];
			   		var v=$("[id='value_"+aryIds[1]+"']");
			   		var oldVal=v.val();
			   		var curVal=param.value;
			   		var newVal="";
			   		if(oldVal.indexOf(curVal+",")>-1) //开始位置
			   		{
			   			newVal=oldVal.replace(curVal+",","");
			   		}
			   		else if(oldVal.indexOf(","+curVal+",")>-1)//中间位置；
			   		{
			   			newVal=oldVal.replace(","+curVal+",","");
			   		}
			   		else if(oldVal.indexOf(","+curVal)>-1)//最后位置；
			   		{
			   			newVal=oldVal.replace(","+curVal,"");
			   		}
			   	    else if(oldVal==curVal)
			   	    {
			   	    	newVal="";
			   	    }
			   	
			   		v.val(newVal);
			   }
			}
			function setSelectValues(param)
			{
			   var id=$(this).attr("id");
			   var aryIds=id.split("_");
			   if(aryIds.length>1)
			   {
			   		var valId="#value_"+aryIds[1];
			   		var v=$("[id='value_"+aryIds[1]+"']");
			   		
			   		//alert(param.value);
			   		var oldVal=v.val();
			   		var newVal=oldVal;
			   		var curVal=param.value;
			   		if(oldVal=="")
			   		{
			   			newVal=param.value;
			   		}
			   		else
			   		{
			   		     if(oldVal.indexOf(curVal+",")<0 
			   		        && oldVal.indexOf(","+curVal+",")<0
			   		        && oldVal.indexOf(","+curVal)<0
			   		        && oldVal!=curVal
			   		     ) //开始位置
				   		{
				   			newVal=oldVal+","+param.value;
				   		}
			   		}
			   		
			   		v.val(newVal);
			   }
			}
	        function WhenUserClikeDiv(Div_Id)
	        {
	            /*$("#" + Div_Id).slideToggle();*/
	            $("#" + Div_Id).fadeToggle("slow");
	            $("select[multiple='multiple']").each(function(){
				    setDefaultVal(this);
  				});
	        }
	        
	        function AutiSize()
	        {
				if(window.location.href.indexOf("Tree")>-1){
				    $("#Grid_Task_1").css("height","300px");
				}
	               
	           	$("#loading").remove();
	            if(document.getElementById("Grid_Task") != null){
			        document.getElementById('Grid_Task').style.width = $(window).width()+'px'; // 注释部分为通过计算取总宽度，无滚动条； 下面为直接取窗口宽度,过宽则显示滚动条
	            }

	            var PageAndMenuHeight=$("#Menu_Frame_Div").height()+$("#Top_Page").height()+4;
	            $("#Table_Frame_Div").height($(window).height()-PageAndMenuHeight);
		        
	        }
	
	        function onOperationClick(actionNamespace,actionName,page){
	        
	            var extendParam = "";
				if(actionName.indexOf("param") == 0 && actionName.indexOf("|")>0){
	            	extendParam = actionName.split("|")[0];
	            	actionName = actionName.split("|")[1];
				}
	        
	            if(actionName.indexOf("Show")>-1 && '<s:property value="serviceResult.showView"/>' == true){
		            if(Now_Mode=="Ico"){
		            	Now_Mode="Table";
		            }else if(Now_Mode == "Table"){
		            	Now_Mode="Ico";
		            }else if(Now_Mode==""){
		            	Now_Mode="Ico";
		            }
	            }
	            
	        	if(actionName == "${serviceResult.selectActionName}"){
	        		var currentPage=1;
	        		if(page != null){
	        			currentPage = page;
	        		}
	        		document.form1.action = "<%=basePath%>" + actionNamespace + "/" + actionName + ".action?currentPage=" + currentPage+"<%=relation%>";
	        		
	        		if(extendParam != ""){
	        		    document.form1.action = document.form1.action + "&" +extendParam;
	        		}
				}
				else{
					if("<%=relation%>"==''){
						document.form1.action = "<%=basePath%>" + actionNamespace + "/" + actionName + ".action";
					}else{
						document.form1.action = "<%=basePath%>" + actionNamespace + "/" + actionName + ".action?<%=relation1%>";
					}
				}
	        	document.form1.submit();
	        }
	
	        function onOperationAlertClick(actionNamespace,actionName,page,name){
	            var gnl=confirm("确定要"+name+"操作吗?");
	            if (gnl==true){
	            	if(actionName.indexOf("Show")>-1 && '<s:property value="serviceResult.showView"/>' == true){
	    	            if(Now_Mode=="Ico"){
	    	            	Now_Mode="Table";
	    	            }else if(Now_Mode == "Table"){
	    	            	Now_Mode="Ico";
	    	            }else if(Now_Mode==""){
	    	            	Now_Mode="Ico";
	    	            }
	                }
	            	if(actionName == "${serviceResult.selectActionName}"){
	            		var currentPage=1;
	            		if(page != null){
	            			currentPage = page;
	            		}
	            		document.form1.action = two + actionNamespace + "/" + actionName + ".action?currentPage=" + currentPage+"&Now_ModeValue="+Now_Mode;
	            	}else{
	            		document.form1.action = two + actionNamespace + "/" + actionName + ".action?Now_ModeValue="+Now_Mode;
	            	}
	            	document.form1.submit();
	            }else{ 
	            	return;  
	            }  
	        }
	
	        function onOperationClickBtn(actionNamespace,actionName,id){
	       		document.form1.action = "<%=basePath%>" + actionNamespace + "/" + actionName + ".action?id=" + id;
	       		document.form1.submit();
	        }
	        
	        function onLinkClick(url){
	            if(url.indexOf("?") > -1){
	            	url += "&windowId=" + document.getElementById('windowId').value; 
	            }
	            else{
	            	url += "?windowId=" + document.getElementById('windowId').value; 
	            }
	            
	            if('<s:property value="serviceResult.showModal"/>' == 'true'){
	                url += "&showModal="+'<s:property value="serviceResult.showModal"/>';
	            	AddUser(url);
	            }else{
	            	window.location.href=url;
	            }
	        }
	
	        function onOpenLinkClick(url){
	            if(url.indexOf("?") > -1){
	            	url += "&windowId=" + document.getElementById('windowId').value; 
	            }
	            else{
	            	url += "?windowId=" + document.getElementById('windowId').value; 
	            }
	
	            <s:iterator value="#session.showNavigation_ShowNavigationComponent" id="vt">
					if(url.indexOf('<s:property value="url" />') > -1){
						 new window.parent.ObjectWindows("Main_Body", '<s:property value="id" />' + "Windows", 520, 1000, url, '<s:property value="name" />', "no");
			    	}
			    </s:iterator>
	        }
	
	        function onOpenLinkWindows(url, windowId, index, name){
	        	if(name==null){
	        		name=="";
	        	}
				if(window.name==""){
					new window.parent.ObjectWindows("Main_Body", windowId, 520, 1000, url, name+index, "yes");
				}
				else if(window.parent.name==""){
					new window.parent.parent.ObjectWindows("Main_Body", windowId, 520, 1000, url, name+index, "yes");
				}
				else if(window.parent.parent.name==""){
					new window.parent.parent.parent.ObjectWindows("Main_Body", windowId, 520, 1000, url, name+index, "yes");
				}
	        }

	        function onOpenModelConditionWindows(url, windowId, name){
	    		windowId = windowId.replace(/-/g, "").replace(/\./g, "");
	        	url = getCondition(url);
				window.showModalDialog(url, "", "dialogWidth=700px;dialogHeight=500px;");
	        }
	        
	        function onOpenModelWindows(url, windowId, name){
	    		windowId = windowId.replace(/-/g, "").replace(/\./g, "");
				window.showModalDialog(url, "", "dialogWidth=700px;dialogHeight=500px;");
	        }

	    	// 根据条件打开模态对话框
	        function onOpenCommonConditionWindows(url, windowId, name){
	        	url = getCondition(url); 
	        	onOpenCommonWindows(url, windowId, name);
	        }
	
	        function onOpenCommonWindows(url, windowId, name){
	    		windowId = windowId.replace(/-/g, "").replace(/\./g, "");
	        	url = getCondition(url); 
				onOpenLinkWindows(url, windowId, "", name);
				showOrHideModel(windowId, name);
	        }
	
	        function onOpenNewWindows(url){
	        	url = getCondition(url);
				window.open(url);
	        }
	        
	        function getCondition(url){
	        
	        	var from = $("#from1")[0];
	        	
	            for ( var i = 0; i < from.elements.length; i++) {
	            	if(from.elements(i).name != null && from.elements(i).name.indexOf("serviceParam")>-1 && from.elements(i).value != null && from.elements(i).value!=""){
			        	if(url.indexOf("?")<0){
			        		url += "?";
			        	}else{
			        		url += "&";
			        	}
	            		url = url+from.elements(i).name+"="+from.elements(i).value;
	            	}
				}
				
	    		return url;
	        }
	        
	        function showOrHideModel(windowId, name){
	        
	        	var strInsertHtml = "<div id='" + windowId +"_model' style='width:2000px; height:2000px; position: absolute; top:0px; left:0px; z-index:999; background-color: #333; display: block;'></div>";
	        	var HideIndex = "900";
	        	var ShowIndex = "10000"; 
	        	var TaskBarLine = "TaskBarLine";
	        	var TaskBarLine_Frame = "TaskBarLine_Frame";
	        	
	        	if(name==null){
	        		$("#Main_Body").append(strInsertHtml); // 将窗体插入到制定的容器中
	        		$("#" + windowId + "_model").css("opacity","0.1");
	        		$("#"+TaskBarLine).css("z-index",HideIndex);
	        		$("#"+TaskBarLine_Frame).css("z-index",HideIndex);
	        		
				    $("#"+windowId+"_Close").click(function(){
				        $("#" + windowId + "_model").remove();
		        		$("#"+TaskBarLine).css("z-index",ShowIndex);
		        		$("#"+TaskBarLine_Frame).css("z-index",ShowIndex);
				    });
	        	}
				if(window.name==""){
					window.parent.$("#Main_Body").append(strInsertHtml); // 将窗体插入到制定的容器中
	        		window.parent.$("#" + windowId + "_model").css("opacity","0.1");
	        		window.parent.$("#"+TaskBarLine).css("z-index",HideIndex);
	        		window.parent.$("#"+TaskBarLine_Frame).css("z-index",HideIndex);
	        		
				    window.parent.$("#"+windowId+"_Close").click(function(){
				        window.parent.$("#" + windowId + "_model").remove();
		        		window.parent.$("#"+TaskBarLine).css("z-index",ShowIndex);
		        		window.parent.$("#"+TaskBarLine_Frame).css("z-index",ShowIndex);
				    });
				}
				else if(window.parent.name==""){
					window.parent.parent.$("#Main_Body").append(strInsertHtml); // 将窗体插入到制定的容器中
	        		window.parent.parent.$("#" + windowId + "_model").css("opacity","0.1");
	        		window.parent.parent.$("#"+TaskBarLine).css("z-index",HideIndex);
	        		window.parent.parent.$("#"+TaskBarLine_Frame).css("z-index",HideIndex);
				    window.parent.parent.$("#"+windowId+"_Close").click(function(){
				        window.parent.parent.$("#" + windowId + "_model").remove();
		        		window.parent.parent.$("#"+TaskBarLine).css("z-index",ShowIndex);
		        		window.parent.parent.$("#"+TaskBarLine_Frame).css("z-index",ShowIndex);
				    });
				}
				else if(window.parent.parent.name==""){
					window.parent.parent.parent.$("#Main_Body").append(strInsertHtml); // 将窗体插入到制定的容器中
	        		window.parent.parent.parent.$("#" + windowId + "_model").css("opacity","0.1");
	        		window.parent.parent.parent.$("#"+TaskBarLine).css("z-index",HideIndex);
	        		window.parent.parent.parent.$("#"+TaskBarLine_Frame).css("z-index",HideIndex);
	        		
				    window.parent.parent.parent.$("#"+windowId+"_Close").click(function(){
				        window.parent.parent.parent.$("#" + windowId + "_model").remove();
		        		window.parent.parent.parent.$("#"+TaskBarLine).css("z-index",ShowIndex);
		        		window.parent.parent.parent.$("#"+TaskBarLine_Frame).css("z-index",ShowIndex);
				    });
				}
	        }
	        
	        function AddUser(url)
	        {
	            BlockadeWindow();
	            var strInsertHtml = "";
	            var top = ($(window).height() - 322)/2;
	            var left = ($(window).width() - 602)/2;
	            
	            strInsertHtml+= "<iframe id='AddUserIfram' frameborder='0' style='width:602px; height:322px; background-color:White;position:absolute; left:"+left+"px; top:"+top+"px;box-shadow:0 5px 30px Black;z-index:1001'  src='"+url+"' scrolling='no' ></iframe>";
	            $("body").append(strInsertHtml);
	        }
	
	        function RemoveBlockadeWindowDiv(isButone)
	        {
	            $("#AddUserIfram").remove();
	            $("#AddUserIfram_Jiao").remove();
	            $("#BlockadeWindow_Div").remove();
	            
	            if(isButone == 'true1'){
	                location.reload();
	            }
	        }
	
	        function IsTrue_Modle(){
	            if('<s:property value="serviceResult.showModal"/>' == 'true'){
	            	return '<s:property value="serviceResult.showModal"/>';    
	            }
	        }
	
	        function View_Method(View_Method_Vaule){
	        	var count=0;
	   		 	if(View_Method_Vaule == "Table"){
   		 			Now_Mode = "Ico";
	       			$("#Grid_Task").css("display","none");
	       			$("#Grid_Task_1").css("display","block");
	       			$("#JS_Grid_Data").children("div").children("div").children(":checkbox").removeAttr("checked"); 

			        var isSplitField123 = "";
			        $(".JS_Grid_Data").children("[rows_type='DataRow']").each(function(index){
						
			        	var splitField123 = '********************************';
			        	var checkBox123;
			        	$(this).children("[splitfield='true']").each(function(index1){
			        	    var bulabula = $(this).attr("title");
			        	    
			        		if(isSplitField123.indexOf(bulabula) == -1){
			        			isSplitField123 += $(this).attr("title");
			        			View_Div_ch($(this).attr("title"));
			        		}
			        		
							splitField123 = $(this).attr("title");
			        	});
			        	
			        	var title123 = '';
			        	$(this).children("[titlefield='true']").each(function(index2){
			        		if(title123 != ''){
			        			title123 += ",";
			        			title123 += $(this).attr("title");
			        		}else{
			        			title123 += $(this).attr("title");
			        		}
			        		checkBox123 = $(this).prevAll("#chckeboxList").find(":checkbox").val();
			        	});
			        	
			        	if(splitField123 == '********************************'){
			        		InsertNewICO('Grid_Task_1',"DIV_"+checkBox123,"images/icomeum/ShowView.png",title123.split(",")[0],title123.split(",")[1],title123.split(",")[2],checkBox123);}
			        	else{
			        		InsertNewICO("div_splitfield_"+splitField123,"DIV_"+checkBox123,"images/icomeum/ShowView.png",title123.split(",")[0],title123.split(",")[1],title123.split(",")[2],checkBox123);
			        	}
    				});
			        $("#Grid_Task_1").children(".titlesss").find(".Ico_Css:last").after("<br style='clear:both;'/>");	
	        		
				    $("#Grid_Task_1").css('overflow-y','auto');
				    $("#Grid_Task_1").css('overflow-X','hidden');
		           	$("#Grid_Task_1").bind("selectstart",function(){return false;});
				 }	
			     else{        	 
                	 $("#RightMeum_viev").remove();
       			 	 Now_Mode = "Table"
	       			 $("#Grid_Task_1").children().remove();
	       			 
	       			 $("#Grid_Task_1").css("display","none");
	       			 $("#Grid_Task").css("display","block");

	       			 $("#Grid_Task_1").children(":checkbox").removeAttr("checked");
	       			 $("#Grid_Task_1").removeClass("JS_Grid_Data_Row_Select");
	       			 $("#Grid_Task_1").find(":checkbox").attr("checkBoxIf",false);
                }
	        }
	        
	        function View_Div_ch(titleValse){
	        	var InsertHTML = "";
	        	InsertHTML +="<div id='splitfieldheald' style='width:100%; height:30px;'>";
				InsertHTML +="  <img src='images/User/plus_tmp.gif' style='margin-top:5px; float:left;cursor:pointer;'/>";
				InsertHTML +="  <span id='"+titleValse+"' style='margin-top:5px; float:left;font-family:微软雅黑; font-size:13px; cursor:pointer;' >"+titleValse+"</span>";
				InsertHTML +="  <div style='width:620px; height:0px;border-top:1px solid #dadada; float:left; margin-top:14px;'></div>";
				InsertHTML +="</div>";
				InsertHTML +="<div id='div_splitfield_"+titleValse+"' class='titlesss' style='width:100%; height:auto;'>";
				InsertHTML +="</div>";
				$("#Grid_Task_1").append(InsertHTML);
	        }
	
	        function Time(){
	        	if($("#loading").hasClass("loading") == true) {
	            	var gnl=confirm("页面加载错误,是否重新加载？")
	            	if(gnl == true){
	            		location.reload();
	            		return true;
	                }else{
	                	$("#loading").remove();
	                	return ; 
	                }
	            }
	        }
	        
	        $(document).ready(function(){
	        	$("#ChangeView").unbind();
	        	$("#ChangeView").click(function(){
	        		
	        		var count=0;
	                if(Now_Mode == "" || Now_Mode == null){
	                	Now_Mode = "Table";
	                }
	                
	       		 	if(Now_Mode == "Table"){
	       		 	    Now_Mode = "Ico";
		       			$("#Grid_Task").css("display","none");
		       			$("#Grid_Task_1").css("display","block");
		       			$("#JS_Grid_Data").children("div").children("div").children(":checkbox").removeAttr("checked"); 
	
				        var isSplitField123 = "";
				        $(".JS_Grid_Data").children("[rows_type='DataRow']").each(function(index){
							
				        	var splitField123 = '********************************';
				        	var checkBox123;
				        	$(this).children("[splitfield='true']").each(function(index1){
				        	    var bulabula = $(this).attr("title");
				        	    
				        		if(isSplitField123.indexOf(bulabula) == -1){
				        			isSplitField123 += $(this).attr("title");
				        			View_Div_ch($(this).attr("title"));
				        		}
				        		
								splitField123 = $(this).attr("title");
				        	});
				        	
				        	var title123 = '';
				        	$(this).children("[titlefield='true']").each(function(index2){
				        		if(title123 != ''){
				        			title123 += ",";
				        			title123 += $(this).attr("title");
				        		}else{
				        			title123 += $(this).attr("title");
				        		}
				        		checkBox123 =  $(this).prevAll("#chckeboxList").find(":checkbox").val();	
				        	});
				        	
				        	if(splitField123 == '********************************'){
				        		InsertNewICO('Grid_Task_1',"DIV_"+checkBox123,"images/icomeum/ShowView.png",title123.split(",")[0],title123.split(",")[1],title123.split(",")[2],checkBox123);
				        		$("#DIV_"+checkBox123).css('margin-top','5px');
				        	}
				        	else{
				        		InsertNewICO("div_splitfield_"+splitField123,"DIV_"+checkBox123,"images/icomeum/ShowView.png",title123.split(",")[0],title123.split(",")[1],title123.split(",")[2],checkBox123);
				        	}
				        	
	    				});
	    				$("#Grid_Task_1").children(".titlesss").find(".Ico_Css:last").after("<br style='clear:both;'/>");	
		        		
					    $("#Grid_Task_1").css('overflow-y','auto');
					    $("#Grid_Task_1").css('overflow-X','hidden');
			           	$("#Grid_Task_1").bind("selectstart",function(){return false;});
					 }	
				     else{        	 
	                	 $("#RightMeum_viev").remove();
	       			 	 Now_Mode = "Table"
		       			 $("#Grid_Task_1").children().remove();
		       			 
		       			 $("#Grid_Task_1").css("display","none");
		       			 $("#Grid_Task").css("display","block");
	
		       			 $("#Grid_Task_1").children(":checkbox").removeAttr("checked");
		       			 $("#Grid_Task_1").removeClass("JS_Grid_Data_Row_Select");
		       			 $("#Grid_Task_1").find(":checkbox").attr("checkBoxIf",false);
	                }
	            });	
	
	            $("#splitfieldheald").live('click', function(event){
	               var titleId=$(this).children("span").attr("id")
	               $("#div_splitfield_"+titleId).slideToggle();
	            });
	
	        	$("[rows_type='View']").live('mousedown', function(event){
	        		if(event.which== 3){
	            		
						$("#RightMeum_viev").remove();
						var checkbox_val_1 = $(".Ico_Css").find(":checkbox").val();
						var a_title_Values = $("[rows_type='DataRow']").find("[value='"+checkbox_val_1+"']").parent().attr("title")
						
						var InsertHTML = "";
						InsertHTML += "  <div id='RightMeum_viev' class='RIGRH_MEUM' onselectstart='javascript:return false;'>";
						$("[rows_type='DataRow']").find("[value='"+checkbox_val_1+"']").parent().siblings("[columncode='ColA']").each(function(){
							var RightMeum_viev_values = $(this).attr("title")
							InsertHTML += "    <div type_It='"+RightMeum_viev_values+"'><img src='"+two+"images/rightmeum/refresh.png' /><a>"+RightMeum_viev_values+"</a></div>";
						});	
						InsertHTML += "    <br style='clear:both;'/>";
			            InsertHTML += "  </div>";
			            
			            $("body").append(InsertHTML);
			           
			            $("#RightMeum_viev").show();
			            $("#RightMeum_viev").css("top", event.pageY);
			            $("#RightMeum_viev").css("left", event.pageX);
			            $("#RightMeum_viev").css("z-index", 1000);
			            var number = event.pageY;
			            
			           $("[type_It]").unbind();
			           $("[type_It]").click(function() {
			        	 var strType = $(this).attr("type_It");
			        	 
			        	 var checkbox_val_1_URL = 
	
						 $("[rows_type='DataRow']").find("[value='"+checkbox_val_1+"']").parent().parent().find("[title='"+strType+"'] a").attr("href");
	                     checkbox_val_1_URL += "&Now_ModeValue="+Now_Mode;
	
						 if(strType == '修改' && '<s:property value="serviceResult.showModal"/>' == 'true'){
							 checkbox_val_1_URL += "&showModal="+'<s:property value="serviceResult.showModal"/>';
							 AddUser(checkbox_val_1_URL)
				         }else{
				        	 window.location.href=checkbox_val_1_URL;
					     }
			        	 
			        	$("#RightMeum_viev").remove();
			           });
					 }else{
						$("#RightMeum_viev").remove();
						
						var checkBoxIf=$(this).find(":checkbox").attr("checkBoxIf");
							if(checkBoxIf == 'false'){
							$(this).toggleClass("JS_Grid_Data_Row_Select");
							$(this).find(":checkbox").attr("checkBoxIf",true);
							$(this).find(":checkbox").attr("checked",'true');
	     				}else{
							$(this).removeClass("JS_Grid_Data_Row_Select");
							$(this).find(":checkbox").attr("checkBoxIf",false);
							$(this).find(":checkbox").removeAttr("checked");
	             		}
					 }
	            });
	        	     	   
	        	$("#JS_Grid_Data").children("[Rows_Type='DataRow']").unbind();
	        	$("#JS_Grid_Data").children("[Rows_Type='DataRow']").click(function(){
	                 if(!$(this).hasClass("JS_Grid_Data_Row_Select"))
	                 $("#JS_Grid_Data").children("[Rows_Type='DataRow']").removeClass("JS_Grid_Data_Row_Select");
	                 $(this).toggleClass("JS_Grid_Data_Row_Select");
	            });
	
	            $("[columncode='ColA']").live('mousedown', function(event){
					var titleValues1 = $(this).attr("title");
					var url1 = $(this).find("a").attr("href");
	                if(titleValues1 == '修改' && '<s:property value="serviceResult.showModal"/>' == 'true'){
	                	
	                	 $(this).find("a").bind("href",function(){return false});
	           		     url1 += "&showModal="+'<s:property value="serviceResult.showModal"/>';
	                 	 AddUser(url1);
	                }
	            });
	        	
				$("#Grid_Task_Check").click(function(){
					var check=$(this).attr("checked");
					if(check==null){
						$("#Grid_Task").find("tr").find(":checkbox").removeAttr("checked"); 
					}
					else{
						$("#Grid_Task").find("tr").find(":checkbox").attr("checked",'true');
					}
				});
	
				$("#titleOnes").live('mousedown', function(event){
					if(event.which== 3){
						$("#RightMeumIco_viev").remove();
						var isValue = $(this).siblings(":checkbox").val();
	
						var InsertHTML = "";
						InsertHTML += "  <div id='RightMeumIco_viev' class='RIGRH_MEUM' onselectstart='javascript:return false;'>";
						$(".JS_Grid_Data").children(".JS_Grid_Data_Row").find("[value='"+isValue+"']").parent().siblings("[columncode='ColA']").each(function(index){
							var values = $(this).attr("title");
							InsertHTML += "    <div type_It='"+values+"'><img src='"+two+"images/rightmeum/refresh.png' /><a>"+values+"</a></div>";
						});
						InsertHTML += "    <br style='clear:both;'/>";
			            InsertHTML += "  </div>";
	
			            $("body").append(InsertHTML);
			            $("#RightMeumIco_viev").css("top", event.pageY);
			            $("#RightMeumIco_viev").css("left", event.pageX);
			            $("#RightMeumIco_viev").css("z-index", 1000);
			            $("#RightMeumIco_viev").show();
	
			            $("[type_It]").unbind();
				        $("[type_It]").click(function() {
				           var strType = $(this).attr("type_It");
				           var checkbox_val_1_URL =
						   $("[rows_type='DataRow']").find("[value='"+isValue+"']").parent().parent().find("[title='"+strType+"'] a").attr("href");
							window.location.href=checkbox_val_1_URL;
				        	 
				        	$("#RightMeum_viev").remove();
				        });
					}else if(event.which== 1){
						$("#RightMeumIco_viev").remove();
					}
				});
	
				$("#imgIco").live('click', function(event){
					var srcValue = $(this).attr("src");
					if(srcValue.indexOf("No")>-1){
						$(this).attr("src",'images/navigationbar/switch_Yes.png');
					}else{
						$(this).attr("src",'images/navigationbar/switch_No.png');
					}								
				});
	
				$("#titleOnes").live('click',function(){
					if($(this).hasClass("JS_Grid_Data_Row_Select")){
						$(this).prev(":checkbox").removeAttr('checked');
					}else{
						$(this).prev(":checkbox").attr('checked',true);
					}
					$(this).toggleClass("JS_Grid_Data_Row_Select");
				});
	
	        	if(getFunctionSize() == 0){
	        		$("#Top_Bar").hide();
	            }
	        	if(getConditionSize() == 0){
	        		$("#Top_Condtion").hide();
	            }
	
				$(":text").keydown(function(event){
					 if(event.which == 27){
						 return false;
					 }
				});
				
				// 质量信息考评界面去掉高级查询按钮且显示查询条件
				if($('#queryBtn').text() == '' || $('#queryBtn').text() == 'undefined'){
					$("#SelectDivFrame").show();
					return false;
				}else{
<%--					WhenUserClikeDiv("SelectDivFrame");		lanyuesheng	20160203	据条件是否存在值显示/隐藏 --%>
					
		            $("select[multiple='multiple']").each(function(){
					    setDefaultVal(this);
	  				});

                    var flag = false;
                    var divList = $("[name^='serviceParam.']");
                    for(var i=0;i<divList.length;i++){
                    	if(divList[i].value!=null && divList[i].value!="" && divList[i].value!="undefined"){
                    		flag = true;
                    		break;
                    	}
                    }

                    if($("#SelectDivFrame").is(":hidden")!=true && flag==true){
                   		$("#SelectDivFrame").show();
                    }else{
                    	$("#SelectDivFrame").hide();
                    }
				}
	    });
	
	    function getFunctionSize(){
	        var count = 0;
	        <s:iterator value="serviceResult.operationList" id="sr">
			  	<s:if test="%{#sr.operationType=='Post'}"> 
			  	    count++;
			    </s:if>
			    <s:elseif test="%{#sr.operationType=='Get'}">
			        count++;
			    </s:elseif>
			    <s:elseif test="%{#sr.operationType=='Upload'}">
			        count++;
				</s:elseif>
				<s:elseif test="%{#sr.operationType=='PostConfirm'}">
					count++;
				</s:elseif>
	         </s:iterator>
	        return count;
	    }
	
	    function getConditionSize(){
	        var count = 0;
	        <s:iterator value="serviceResult.showCondition" id="sr">
	            <s:if test="%{#sr.singleTag=='textField'}">
			  	    count++;
			    </s:if>
			    <s:elseif test="%{#sr.singleTag=='selectField'}">
			        count++;
			    </s:elseif>
			    <s:elseif test="%{#sr.singleTag=='dateField'}">
			        count++;
				</s:elseif>
	        </s:iterator>
	        return count;
	    }
	
	    function getDataSize(){
	        var count = <s:property value="serviceResult.valueTable.size()"/>;
	        return count;
	    }
	
	    function skip(object){
	    	 var num =  $(object).prev(":text").val();
	         if(isNaN(num) || num.indexOf(".")>-1 || num.indexOf("-")>-1 || num == 0){
	             alert("数字非法");
	             return false;
	         }
	        
	         var countString = $(object).parent().siblings("div").eq(1).html();
	         var count = parseInt(countString.substring(countString.indexOf(":")+1));
	         if(num > count){
	             alert("超出总共页数");
	             return false;
	         }
	         onOperationClick('<s:property value="serviceResult.namespace" />','<s:property value="serviceResult.selectActionName" />',num);
	    }
	
	    function showListDeleteConfirm(strss,acionStr,val0,val1,window1){
	    	var gnl1=confirm("确定要删除操作吗?");
	        if (gnl1==true){
	        	window.location.href = strss + "/" + acionStr+".action?id="+val0+"&type="+val1+"&windowId="+window1;
	        }
	    }
	
	    function showListDivWidthBig(strss,acionStr,val0,val1,window1){
			window.location.href = strss + "/" + acionStr+".action?id="+val0+"&type="+val1+"&windowId="+window1;
	    }
	
	    function ShowHeadName(){
	    	if(window.location.href.indexOf("ShowModelList")>0){
	    		$("#SelectDivFrame").fadeToggle("slow");
	    	}
	        AutiSize();
			var titleObj = null;
			var WindowTitleId = null;
	        var action_windowId = '<s:property value="#request.action_windowId" />';
	        var headName = '<s:property value="#request.action_autodtoTableName" />';

	        WindowTitleId = action_windowId+"WindowId";
			titleObj = parent.parent.document.getElementById(WindowTitleId);
			if(titleObj == null) {
				titleObj = parent.parent.parent.parent.document.getElementById(WindowTitleId);
			}
				
	        if(headName != null && headName != "" && titleObj!=null){
	        	var jc = "基础段";
	        	var jcChart = new Array();
	        	jcChart = jc.split("");
	        	var jcCode = "";
	        	for	(var j = 0; j < jcChart.length; j++) {
					jcCode += "&#"+jcChart[j].charCodeAt()+";";
				}
				var title = titleObj.innerHTML;
				var tmpTitle = "";
				
				// 判断当前跳转的是否是基础段数据
	        	if(headName.indexOf(jcCode) > -1){
	        		title = title.split("-");
	        		titleObj.innerHTML = title[0];
	        	}else{
	        		tmpTitle = titleObj.innerHTML;
	        		if(tmpTitle.indexOf("-") > -1) {
	        			var arr = tmpTitle.split("-");
	        			tmpTitle = "";
	        			if(arr.length == 2) {
	        				tmpTitle += arr[0]+"-"+arr[1];
	        			} else {
	        				for(var i = 0; i <= 1; i++) {
			        			if(i == 1) {
			        				tmpTitle += arr[i]
				        		} else {
				        			tmpTitle += arr[i]+"-";
						        }
			        		}
			        	}
			        }
		        	titleObj.innerHTML = tmpTitle;
	        	}
	        	
	        	tmpTitle = "";
	        	var titleArray = new Array();
	        	title = titleObj.innerHTML;
	        	titleArray = title.split("-");
	        	var boo = false;
				
				for (var i = 0; i < titleArray.length; i++) {
					var str = new Array();
					str = titleArray[i].split("");
					var code = "";
					for	(var j = 0; j < str.length; j++) {
						code += "&#"+str[j].charCodeAt()+";";
					}

					if(code == headName){
						boo = true;
						break;
					}
					tmpTitle =  tmpTitle+"-"+code;
				}
				titleObj.innerHTML = tmpTitle.substring(1)+"-"+headName;
	        }else if(titleObj!=null){
	       		var title = parent.parent.document.getElementById(WindowTitleId).innerHTML;
	        	if(title.split("-").length>1){
	        		parent.parent.document.getElementById(WindowTitleId).innerHTML = title.split("-")[0];
	        	}
	        }
	    }
	    
	    // 待修正
		function changeOptionsTitle(name){ 
		 	var options = document.getElementById(name).options;
		    for (var i=0;i<options.length;i++){ 
		        var text = options[i].text;
		        options[i].title = text;
		    }
		}
		 	
    </script>
    <script type="text/javascript">
		function fill(t){
			var $con =$(t).parent().parent();
			//var $con = $(":input:checkbox[name=idList][checked]").parent().parent();
				var params = window.location.href.split("?")[1].split("&");
				var pv="";
				$.each(params,function(i,paramItem){
					var pa=paramItem.split("=");
					if(pa[0]=="relation"){
						pv= pa[1];
						return false;
					}
				});
				var keyvaluepair = pv.split("@");
				var rv=new Array();
				$.each(keyvaluepair,function(i,paramItem){
					var pa=paramItem.split("-");
					var va=pa[0]+"=";
					$.each(pa[1].split(","),function(j,index){
						if(index=="0"){
							va+=$con.children().eq(0).children().eq(0).val();
						}else{
							va+=$.trim($con.children().eq(index).eq(0).text())+"-";
						}
					});
					rv.push(va);
				});
				window.returnValue=rv;
				window.close();
		}
		<!--合并征信版本-->
		function resetform(form)
		{
		    	var formObj  =document.getElementsByName(form)[0];   
				var myDate = new Date();
				
				for(var i=0; i<formObj.elements.length; i++) 
				{
					if(formObj.elements[i].type == "text")   
    				{   
    					if(formObj.elements[i].name=="serviceParam.startDate")
    					{
    						if(myDate.getDate()==1)
    						{
    							var cdt = new Date(myDate.getTime()-1000*60*60*24);
    							var syd= cdt.getFullYear()+"";
    							 if(cdt.getMonth()+1<10)
    							{
    								syd+="0";
    								syd+=cdt.getMonth()+1+"";
    							}else
    							{
    								syd+=cdt.getMonth()+1+"";
    							}
    							syd+="01";
    							formObj.elements[i].value =syd;
    						}
    						else
    						{
    							cdt = new Date(myDate.getTime());
    							var syd= cdt.getFullYear()+"";
    							if(cdt.getMonth()+1<10)
    							{
    								syd+="0";
    								syd+=cdt.getMonth()+1+"";
    							}else
    							{
    								syd+=cdt.getMonth()+1+"";
    							}
    							syd+="01";
    							formObj.elements[i].value = syd;
    						}
    					}else if(formObj.elements[i].name=="serviceParam.endDate")
    					{
    						if(myDate.getDate()==1)
    						{
    							cdt = new Date(myDate.getTime()-1000*60*60*24);
    							var syd= cdt.getFullYear()+"";
    							if(cdt.getMonth()+1<10)
    							{
    								syd+="0";
    								syd+=cdt.getMonth()+1+"";
    							}else
    							{
    								syd+=cdt.getMonth()+1+"";
    							}
    							syd+=cdt.getDate();
    							formObj.elements[i].value =syd;
    						}else
    						{
    							cdt = new Date(myDate.getTime());
    							var syd= cdt.getFullYear()+"";
    							if(cdt.getMonth()+1<10)
    							{
    								syd+="0";
    								syd+=cdt.getMonth()+1+"";
    							}else
    							{
    								syd+=cdt.getMonth()+1+"";
    							}
    							if(cdt.getDate().toString().length==1)
    							{
    								syd+="0";
        						}
    							syd+=cdt.getDate();
    							formObj.elements[i].value = syd;
    						}
    					}
    					else
    					{
    						formObj.elements[i].value = ""; 
    					}
    				}   
    				else if(formObj.elements[i].type == "password")   
    				{   
        				formObj.elements[i].value = "";   
    				}   
    				else if(formObj.elements[i].type == "radio")   
    				{   
        				formObj.elements[i].checked = false;   
    				}   
    				else if(formObj.elements[i].type == "checkbox")   
    				{   
        				formObj.elements[i].checked = false;   
    				}   
    				else if(formObj.elements[i].type == "select-one")   
    				{   
        				formObj.elements[i].options[0].selected = true;   
    				}   
    				else if(formObj.elements[i].type == "select-multiple")   
    				{   
        				var id=formObj.elements[i].id;
        				var comboboxPrefix="display_";
        				if(id!=null && id.length> comboboxPrefix.length && id.substr(0,comboboxPrefix.length)==comboboxPrefix)
        				{
            				continue;
            			}  
        				
        				for(var j = 0; j < formObj.elements[i].options.length; j++)   
        				{   
            				formObj.elements[i].options[j].selected = false;   
        				}
        				 
    				}   
    				else if(formObj.elements[i].type == "file")   
    				{   
        				var file = formObj.elements[i];   
         				if (file.outerHTML) {   
             				file.outerHTML = file.outerHTML;   
         				} else {   
             						file.value = "";  // FF(包括3.5)   
        						}   
    					}   
    				else if(formObj.elements[i].type == "textarea")   
    				{   
        				formObj.elements[i].value = "";   
    				}  
    				else if(formObj.elements[i].type == "select")   
    				{   
        				var sel=$(formObj.elements[i]);
        				var id=$(this).attr("id");
        				var aryIds=id.split("_");
        				if(aryIds.length>1)
        				{
        					if(aryIds[0]=="display")
            				{
        						var valId="#value_"+aryIds[1];
        				   		var v=$("[id='value_"+aryIds[1]+"']");
        				   		v.val("");
        				   		var aryVal="".split(",");
        				   		sel.combobox('setValues', aryVal);
        				   		
                    		}
             				
        			   }
        				
    				}   
				}  
				reset2();
		}
		function reset2()
		{
			$("select[multiple='multiple']").each(function(){
				resetSelect($(this));
		    	});
		}
		function resetSelect(element)
		{
			var sel=$(element);
			
			var id=sel.attr("id");
			if(id!=null)
			{
				var aryIds=id.split("_");
				if(aryIds.length>1)
				{
					if(aryIds[0]=="display")
					{
						var valId="#value_"+aryIds[1];
				   		var v=$("[id='value_"+aryIds[1]+"']");
				   		v.val("");
				   		
				   		$("[id='"+id+"']").combobox('clear');
				   		var fieldPos=aryIds[1].indexOf(".");
				   		if(fieldPos>-1)
				   		{
							var fieldName=aryIds[1].substr(fieldPos+1);
							if(fieldName=="RPTSendType")
							{
								v.val("1,5");
								$("[id='"+id+"']").combobox('setValues', ['1','5']);
								
							}
								
						}
				   	
	        		}
			   }

			}
			
		}
	</script>
	</head>
	<body onResize="AutiSize();" style="margin:0px; width: 100%;height:100%;" onload="ShowHeadName();" ondragstart="return false;">
		
		<div id="loading" class="loading">
			<div id="query_hint" class="query_hint">
	           		页面加载中，请稍等...
	        </div>
        </div>
		<s:form name="form1" enctype="multipart/form-data" method="POST" id="from1">
			<!-- 此处添加的ID属性是为了在IE6中消除此DIV多余空白时便于准确获取此div -->
			<div id="Menu_Frame_Div" class="Menu_Frame_Div">
				<div style="float:left;width:100%;">
					<s:iterator value="serviceResult.operationList" id="sr">
						<s:if test="%{#sr.operationType=='Post'}">
							<a class="Menu_Frame_Div_Cell" href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')">
								<s:property value="operationName" /> 
							</a>
						</s:if>
						<s:if test="%{#sr.operationType=='PostConfirm'}">
							<a class="Menu_Frame_Div_Cell" href="javascript:onOperationAlertClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>','','<s:property value="operationName" />')">
								<s:property value="operationName" /> 
							</a>
						</s:if>
						<s:elseif test="%{#sr.operationType=='Get'}">
							<a class="Menu_Frame_Div_Cell" href="javascript:onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action')">
								<s:property value="operationName" />
							</a>
						</s:elseif>
						<s:elseif test="%{#sr.operationType=='Upload'}">
							<s:file id="123456" class="files" cssStyle="float:left; margin-left:2px; font-family:'Microsoft Yahei'; font-size:11px;" name="uploadFile"></s:file>
							<a class="Menu_Frame_Div_Cell" href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')">
								<s:property value="operationName" />
							</a>
						</s:elseif>
						<s:elseif test="%{#sr.operationType=='ModelWin'}">
						    <a class="Menu_Frame_Div_Cell" href="javascript:onOpenModelWindows('<%=basePath%><s:property value='%{#sr.operationNamespace}'/>/<s:property value='%{#sr.operationAction}'/>.action', '<s:property value='%{#sr.operationAction}'/>', '<s:property value="operationName" />')">
								<s:property value="operationName" />
							</a>
						</s:elseif>
						<s:elseif test="%{#sr.operationType=='CommonWin'}">
						    <a class="Menu_Frame_Div_Cell" href="javascript:onOpenCommonWindows('<%=basePath%><s:property value='%{#sr.operationNamespace}'/>/<s:property value='%{#sr.operationAction}'/>.action', '<s:property value='%{#sr.operationAction}'/>', '<s:property value="operationName" />')">
								<s:property value="operationName" />
							</a>
						</s:elseif>
						<s:elseif test="%{#sr.operationType=='NewWin'}"> 
						    <a class="Menu_Frame_Div_Cell" href="javascript:onOpenNewWindows('<%=basePath%><s:property value='%{#sr.operationNamespace}'/>/<s:property value='%{#sr.operationAction}'/>.action')">
								<s:property value="operationName" />新
							</a>
						</s:elseif>
					</s:iterator>
					<s:if test="serviceResult.showView==true">
						<a id="ChangeView">视图</a>
					</s:if>
					<s:if test="serviceResult.showCondition.size()>0">
						<s:iterator value="serviceResult.operationList" id="sr">
							<s:if test="%{#sr.operationType=='Select'}">
								<div id="queryBtn" class="Menu_Frame_Div_Cell" onclick ="WhenUserClikeDiv('SelectDivFrame');">高级查询</div>
							</s:if>
						</s:iterator>
					</s:if>
				</div>
				<div id="Jump_Button_Div" style="float:left;padding-top:2px;">
					<s:iterator value="serviceResult.operationList" id="sr">
						<s:if test="%{#sr.operationType=='PostJump'}">
							<a class="Menu_Frame_Div_Cell" href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')">
								<s:property value="operationName" /> 
							</a>
						</s:if>
						<s:if test="%{#sr.operationType=='GetJump'}">
							<a class="Menu_Frame_Div_Cell" href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')">
								<s:property value="operationName" /> 
							</a>
						</s:if>
					</s:iterator>	
				</div>
			</div>
			<s:if test="serviceResult.showCondition.size()>0">
				<div id="SelectDivFrame" style="width: 100%; height:auto;border-bottom:1px solid #0060A6;font-size: 12px; font-family: 微软雅黑;clear: both; top:30px; background-color:White; z-index:1000;">
					<s:iterator value="serviceResult.showCondition" id="sr" status="i">
						<s:if test="%{#sr.singleTag=='textField'}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								<div class="Control_Frame">
									<s:textfield cssClass="Select_Menu_TextBox" name="serviceParam.%{#sr.paramName}" 
									label='<s:property value="%{#sr.fieldShowName}"/>'></s:textfield>
								</div>
							</div>
						</s:if>
						<s:elseif test="%{#sr.singleTag!='hiddenField' && #sr.multipleSelect==true}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
									
								</div>
								<div class="Select_Menu_SelectBox_Div">
						    		<div class="Select_Menu_SelectBox_Div_Hidden" id="Select_Menu_SelectBox_Div_Hidden">
						    			<s:hidden id="value_serviceParam.%{#sr.paramName}" name="serviceParam.%{#sr.paramName}"  ></s:hidden>
										<s:select cssClass="easyui-combobox Select_Menu_SelectBox_Div_Option" style="width:127px;"
											list="#sr.tag"
											id="display_serviceParam.%{#sr.paramName}"
											name="display_serviceParam.%{#sr.paramName}"
											headerKey="%{#application.emptySelectValueName}"
											multiple="true" />
									</div>
								</div>
							</div>
						</s:elseif>
						<s:elseif test="%{#sr.singleTag=='selectField'}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								
								<div class="Select_Menu_SelectBox_Div">
									<div class="Select_Menu_SelectBox_Div_Hidden">
										<s:select cssClass="Select_Menu_SelectBox_Div_Option"
											list="#sr.tag"
											id="serviceParam.%{#sr.paramName}"
											name="serviceParam.%{#sr.paramName}"
											headerKey="%{#application.emptySelectValueName}"
											headerValue="---请选择---" 
											onmousemove="changeOptionsTitle('serviceParam.%{#sr.paramName}');"/>
									</div>
								</div>
							</div>
						</s:elseif>
						<s:elseif test="%{#sr.singleTag=='selectExtendField'}">							
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								<div class="Control_Frame">
									<s:hidden name="serviceParam.%{#sr.paramName}"></s:hidden>
									<s:textfield name='serviceParam.%{#sr.tag[0]}' cssClass="Select_Menu_TextBox selectClass" />
									<div class="popdiv" style="overflow:auto;">
										<ul>
											<li code='<s:property value="%{#application.emptySelectValueName}" />'>---请选择---</li>
											<s:property value='%{#sr.tag[1]}' escape="false"/>											
										</ul>
									</div>
								</div>
							</div>
						</s:elseif>
						<!-- 模态对话框 -->
						<s:elseif test="%{#sr.singleTag=='modelField'}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								<div class="Control_Frame" style="position:relative;">
										<s:textfield cssClass="Select_Menu_TextBox" name="serviceParam.%{#sr.paramName}"
											readonly="true" label='<s:property value="%{#sr.fieldShowName}"/>'>
										</s:textfield>
										<div name='popBtn' url='<%=basePath%><s:property value="%{#sr.tag}" />' style="background-image: url('images/button/fdj.png');position:absolute;width:22px;height:22px;top:5px; left:125px;z-index:99;"></div>
								</div>										
							</div>
						</s:elseif>
						<s:elseif test="%{#sr.singleTag=='dateField'}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								<div  class="Control_Frame">
									<s:textfield cssClass="Select_Menu_TextBox"  name="serviceParam.%{#sr.paramName}" 
									onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"></s:textfield>
								</div>
							</div>
						</s:elseif>
						<s:elseif test="%{#sr.singleTag=='dateFieldNoSlash'}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								<div class="Control_Frame">
									<s:textfield cssClass="Select_Menu_TextBox"  name="serviceParam.%{#sr.paramName}"
										onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMMdd'})"></s:textfield>
								</div>
							</div>
						</s:elseif>
						<s:elseif test="%{#sr.singleTag=='dateFieldMonth'}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								<div class="Control_Frame">
									<s:textfield cssClass="Select_Menu_TextBox"  name="serviceParam.%{#sr.paramName}"
										onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMM'})"></s:textfield>
								</div>
							</div>
						</s:elseif>
						<s:elseif test="%{#sr.singleTag=='dateFieldHMS'}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								<div class="Control_Frame">
									<s:textfield cssClass="Select_Menu_TextBox"  name="serviceParam.%{#sr.paramName}"
										onfocus="WdatePicker({doubleCalendar:true,dateFmt:'H:m:s'})"></s:textfield>
								</div>
							</div>
						</s:elseif>
						<s:elseif test="%{#sr.singleTag=='dateFieldSlashHMS'}">
							<div class="Select_Menu_Control_Frame_Div">
								<div class="Title"  title="<s:property value="%{#sr.fieldShowName}"/>" class="Font_css_normal_Condtion">
									<s:property value="%{#sr.fieldShowName}" />
								</div>
								<div  class="Control_Frame">
									<s:textfield cssClass="Select_Menu_TextBox"  name="serviceParam.%{#sr.paramName}" 
									onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy/MM/dd HH:mm:ss'})"></s:textfield>
								</div>
							</div>
						</s:elseif>
					</s:iterator>
					<div class="selectButtonDiv">
						<div>
							<s:iterator value="serviceResult.operationList" id="sr">
								<s:if test="%{#sr.operationType=='Select'}">
									<a href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"><s:property value="operationName" /></a>
								</s:if>
							
						<s:if test="%{#sr.operationType=='ReSet'}">
							<a href="javascript:resetform('form1')"><s:property value="operationName" />
							</a>
						</s:if>
							</s:iterator>
						</div>
					</div>
					<div style="width:100%; height:0px;float:left;"></div>
					<br style="clear:both;"/>
				</div>
			</s:if>
			
			<!-- 在IE6中，由于查询条件在隐藏的时候会将分页栏或者list列表展示区域给一同隐藏掉，故，此处DIV是为了消除此影响 -->
			<div></div>
			
			<div id="Top_Page" class="Top_Page">
				<s:if test="serviceResult.showPage.showPage">
					<div>总条数:<s:property value="serviceResult.showPage.totalCount" /></div>
					<div >总共页:<s:property value="serviceResult.showPage.totalPage" /></div>
					<div>当前页:<s:property value="serviceResult.showPage.currentPage" /></div>
					<div>每页条数:<s:property value="serviceResult.showPage.pageSize" /></div>
					<div onclick="javascript:onOperationClick('${serviceResult.namespace}','${serviceResult.selectActionName}',1)">首页</div>
					<s:if test="serviceResult.showPage.showPrePage">
						<div onclick="javascript:onOperationClick('${serviceResult.namespace}','${serviceResult.selectActionName}',${serviceResult.showPage.currentPage - 1})">上一页</div>
					</s:if>
					<s:if test="serviceResult.showPage.showNextPage">
						<div onclick="javascript:onOperationClick('${serviceResult.namespace}','${serviceResult.selectActionName}',${serviceResult.showPage.currentPage + 1})">下一页</div>
					</s:if>
					<div onclick="javascript:onOperationClick('${serviceResult.namespace}','${serviceResult.selectActionName}',${serviceResult.showPage.totalPage})">尾页</div>
					<div><input name="" type="text" style="width: 28px;" /><a onclick="javascript:skip(this)">跳转</a></div>
				</s:if>
				<s:else>
					<div>总共页:1</div>
					<div>当前页:1</div>
				</s:else>
			</div>
			
			<!-- 在IE6中，由于查询条件在隐藏的时候会将分页栏或者list列表展示区域给一同隐藏掉，故，此处DIV是为了消除此影响 -->
			<div></div>
			
			<div id="Table_Frame_Div" class="Table_Frame_Div">
				<table id="Grid_Task" class="Grid_Table" border="1px"  cellpadding="0" cellspacing="0" Rules=rows>
					<tr id="JS_Grid_Head_Row" class="JS_Grid_Head_Row">
						<td class="JS_Grid_Head_Row_Cell" style="width: 20px;">
							<input id="Grid_Task_Check" class="Grid_CheckBox" type='checkbox'></input>
						</td>
						<td class="JS_Grid_Head_Row_Cell" style="width: 20px;">行号</td>
						<s:iterator value="serviceResult.showLinkNameList" id="showLinkNameList" status="showLinkNameListStatus">
							<td title="<s:property value="showName"/>" class="JS_Grid_Head_Row_Cell" style="width: <s:property value="(width==null || width=='')?(operationName.equals('导入')?250:100):width" />px;" columncode="Col1">
								<div class="TD_Div_Nowrap"> <s:property value="showName" /></div>
							</td>
						</s:iterator>
						<s:iterator value="serviceResult.showNameList" id="showNameList" status="showNameListStatus">
							<td title="<s:property value="showName"/>" class="JS_Grid_Head_Row_Cell" style="width: <s:property value="(width==null || width=='')?80:width"/>px;">
								<div class="TD_Div_Nowrap"> <s:property value="showName" /></div>
							</td>
						</s:iterator>
						<td title="详情" class="JS_Grid_Head_Row_Cell" style="width: 80px;">
							<div class="TD_Div_Nowrap">详情</div>
						</td>
					</tr>
					<s:if test="serviceResult.valueTable==null || serviceResult.valueTable.size==0"></s:if>
					<s:else>
						<s:iterator value="serviceResult.valueTable" id="vt" status="st">
							<tr class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;" Rows_Type='DataRow'>
								<td id="chckeboxList" class="JS_Grid_Data_Row_Cell" style="overflow:visible;">
									<input name="idList" class="Grid_CheckBox" type="checkbox" value="<s:property value="%{#vt.get(0).value}"/>"></input>
								</td>
								<td id="chckeboxList" class="JS_Grid_Data_Row_Cell">
									<s:property value="#st.index+1"/>
								</td>
								<s:iterator value="serviceResult.linkedList.get(#st.index)" id="ll">
									<s:if test="%{#ll.operationType=='Upload'}">
										<td class="JS_Grid_Data_Row_CellLink" title="<s:property value='operationName'/>" style="width: <s:property value="(width==null || width=='')?250:width" />px;" columncode="Col1">

											<div title="<s:property value='operationName'/>">
												<s:file class="files" name="uploadFile" cssStyle="float:left; margin-top:11px; font-family:'Microsoft Yahei'; font-size:11px;"></s:file>
                                              	<a href="javascript:onOperationClickBtn('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>','<s:property value="%{#vt.get(0).value}"/>');">
	                                               	<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
														<s:property value="operationName" />
													</s:if>
													<s:else>
														<img src="<s:property value="imageUrl" />"/>
													</s:else>
                                            	</a>
											</div>
										</td>
									</s:if>
									<s:elseif test="%{#ll.operationType=='TypeHidden'}">
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
											<s:if test="%{#vt.get(1).value!='**********'}">
													<s:a namespace="%{#ll.operationNamespace}" action="%{#ll.operationAction}?id=%{#vt.get(0).value}&type=%{#vt.get(1).value}&windowId=%{#request.windowId}" cssStyle="display:inline-block;color:%{#ll.color};">
											        <s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
														<s:property value="operationName" />
													</s:if>
													<s:else>
														<img src="<s:property value="imageUrl" />"/>
													</s:else>
										        </s:a>
											</s:if>
										</td>
									</s:elseif>
									<s:elseif test="%{#ll.operationType=='Pop'}">
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
											<s:a namespace="%{#ll.operationNamespace}" action="%{#ll.operationAction}?id=%{#vt.get(0).value}&type=%{#vt.get(1).value}&windowId=%{#request.windowId}" target="_blank" cssStyle="display:inline-block;color:%{#ll.color};">
												<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
													<s:property value="operationName" />
												</s:if>
												<s:else>
													<img src="<s:property value="imageUrl" />"/>
												</s:else>
											</s:a>
										</td>
									</s:elseif>
									<s:elseif test="%{#ll.operationType=='Get1'}"> 
										<div title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
											<a href="javascript:showListDeleteConfirm('<s:property value='%{#ll.operationNamespace}'/>','<s:property value='%{#ll.operationAction}'/>','<s:property value='%{#vt.get(0).value}'/>','<s:property value='%{#vt.get(1).value}'/>','<s:property value='%{#request.windowId}'/>')"  style="display:inline-block;color:%{#ll.color};">
												<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
													<s:property value="operationName" />
												</s:if>
												<s:else>
													<img src="<s:property value="imageUrl" />"/>
												</s:else>
											</a>
										</div>
									</s:elseif>
									<s:elseif test="%{#ll.operationType=='DivWidthBig'}"> 
										<div title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?75:width" />px;text-overflow:ellipsis; white-space:nowrap;overflow: hidden;" columncode="ColA">
											<div onclick="javascript:showListDivWidthBig('<s:property value='%{#ll.operationNamespace}'/>','<s:property value='%{#ll.operationAction}'/>','<s:property value='%{#vt.get(0).value}'/>','<s:property value='%{#vt.get(1).value}'/>','<s:property value='%{#request.windowId}'/>')"
												     style="width: 75px;text-overflow:ellipsis; white-space:nowrap;overflow: hidden;color:#00F;text-decoration:underline;display:inline-block;color:%{#ll.color};">
												<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
													<s:property value="operationName" />
												</s:if>
												<s:else>
													<img src="<s:property value="imageUrl" />"/>
												</s:else>
											</div>
										</div>
									</s:elseif>
									<s:elseif test="%{#ll.operationType=='OpenWin'}">
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
										    <a href="javascript:onOpenLinkWindows('<%=basePath%><s:property value='%{#ll.operationNamespace}'/>/<s:property value='%{#ll.operationAction}'/>.action?id=<s:property value='%{#vt.get(0).value}'/>&type=<s:property value='%{#vt.get(1).value}'/>', '<s:property value="serviceResult.showPage.currentPage" /><s:property value='%{#vt.get(0).value}'/>', '_<s:property value="#st.index+1"/>', '<s:property value="operationName" />')"
	 											cssStyle="width: <s:property value='width==null?100:width' />px; color:%{#ll.color};">
												<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
													<s:property value="operationName" />
												</s:if>
												<s:else>
													<img src="<s:property value="imageUrl" />"/>
												</s:else>
											</a>
										</td>
									</s:elseif>
									<s:elseif test="%{#ll.operationType=='GetPic'}">
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
										 	<s:iterator value="imageUrl.split('@')" var="iUrl">  
										 		<a href="javascript:void(0);" onclick="fullScreenImg('<s:property value="#iUrl"/>')">
													<img style="width:30px;height:30px" border="0" src="<s:property value="#iUrl"/>"/>
											 	</a>
											</s:iterator>  
										</td>
									</s:elseif>
									<s:elseif test="%{#ll.operationType=='OpenNewWindow'}">
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
										 	<a href="<s:property value="imageUrl" />" target="_blank"><s:property value="operationName" /></a>  
										</td>
									</s:elseif>
									<s:elseif test="%{#ll.operationType=='FillData'}">
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
										 	<a class="Menu_Frame_Div_Cell" onclick="fill(this)">
												<s:property value="operationName" /> 
											</a>
										</td>
									</s:elseif>
									<s:else>
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA"> 
										     <s:a namespace="%{#ll.operationNamespace}"  action="%{#ll.operationAction}?id=%{#vt.get(0).value}&type=%{#vt.get(1).value}&windowId=%{#request.windowId}" 
										     cssStyle="width: <s:property value='width==null?100:width' />px; color:%{#ll.color};">
												<s:if test="%{#ll.imageUrl==null||imageUrl.equals('')}">
													<s:property value="operationName" />
												</s:if>
												<s:else>
													<img src="<s:property value="imageUrl" />"/>
												</s:else>
											 </s:a>
										</td>
									</s:else>
								</s:iterator>
	
								<s:iterator value="#vt" status="fl">
									<s:if test="#fl.index!=0 && #fl.index!=1">
										<td title="<s:property value="value" />" class="JS_Grid_Data_Row_Cell" 
										style="width: <s:property value="(width==null || width=='')?80:width"/>px;"
											columncode="Col2" titleField='<s:property value="titleField" />' splitField='<s:property value="splitField" />' >
											<s:if test="value!=null&& value.indexOf('openLinkedUrl')>=0">
												<a href="javascript:onOpenLinkClick('<%=basePath%><s:property value="value.substring(value.lastIndexOf('_')+1)"/>.action')">
													<s:property value="value.substring(value.indexOf('_')+1,value.lastIndexOf('_'))" />
												</a>
											</s:if>
											<s:elseif test="value!=null && value.indexOf('linkedUrl')>=0">
											    <a href="javascript:onLinkClick('<%=basePath%><s:property value="value.substring(value.lastIndexOf('_')+1)"/>.action')">
													<s:property value="value.substring(value.indexOf('_')+1,value.lastIndexOf('_'))" />
												</a>
											</s:elseif>
											<s:elseif test="showColor!=null">
											    <div class="TD_Div_Nowrap">
												<font color="<s:property value="showColor"/>">
													<s:property value="value" />&nbsp;  
												</font> 
												</div>
											</s:elseif>
											<s:else>
												<div class="TD_Div_Nowrap">
													<s:property value="value" />&nbsp;  
												</div>
			            			 		</s:else>
			            			 		
										</td>
									</s:if>
								</s:iterator>
								<td title="查看" class="JS_Grid_Data_Row_CellLink" style="width: 80px;" columncode="ColA"> 
								     <a id="from1_" href="jmx/showServerChart.action?windowId=<s:property value="windowId"/>&type=J_App&sid=0&pid=<s:property value="%{#vt.get(0).value}"/>&cfq=s" style="color:#4169E1;">查看</a>
								</td>
							</tr>
						</s:iterator>
					</s:else>
				</table>
			</div>
			<div id="Grid_Task_1" style="height: 365px;display:none;"></div>
			<div id="Setting_Node" style="left: 0px; width: 765px; height: 470px; overflow: auto; padding-top: 0px; margin-top: 0px; position: absolute;display: none;">
			</div>
			<s:hidden id="windowId" name="windowId"></s:hidden>
		</s:form>
		<div id="bg"></div>
		<div id="show" onclick="hidediv()"><img style="width:100%;height:100%" src="" alt="" />
		</div>
	</body>
	<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript" ></script>
</html>
