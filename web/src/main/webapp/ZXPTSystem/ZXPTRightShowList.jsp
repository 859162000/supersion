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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<script src="<%=basePath %>js/Comml.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/JsGrid.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/ShowList.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/Shortcutkeys.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/easyui/jquery.easyui.min.js" type="text/javascript" ></script>
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
				
				/**
				 * 消除IE6中，错误信息提示内容为空时，DIV仍然占用空间的问题
				 */
				function hideErrorMsgDiv(){
					var errorText = $("#ShowErrMes_Div").text();
					if(errorText == '' || errorText == null){
						$("#ShowErrMes_Div").hide();
					}
				}
				
				$(document).ready(function(){
					clear_blank_ie6();
					hideErrorMsgDiv();
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
				
				/**
				 * 消除IE6中，错误信息提示内容为空时，DIV仍然占用空间的问题
				 */
				function hideErrorMsgDiv(){
					var errorText = $("#ShowErrMes_Div").text();
					if(errorText == '' || errorText == null){
						$("#ShowErrMes_Div").hide();
					}
				}
				
				$(document).ready(function(){
					clear_blank_ie7();
					hideErrorMsgDiv();
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
	           	
				
				$("#Table_Frame_Div").height($(window).height()-60); // 以前判断是否存在分页层。因设计修改，现直接固定存在分也层
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
	            /* if(url.indexOf("?") > -1){
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
	            } */
	            window.parent.location.href=url;
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
	        AutiSize();
	        
	        var headName = '<s:property value="#request.action_autodtoTableName" />';
	        var titleObj = parent.parent.document.getElementById("WindowTitleId");
	        
	        if(headName != null && headName != "" && titleObj!=null){
	        	var tmpTitle = "";
	        	var titleArray = new Array();
	        	var title = parent.parent.document.getElementById("WindowTitleId").innerHTML;
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
				parent.parent.document.getElementById("WindowTitleId").innerHTML = tmpTitle.substring(1)+"-"+headName;
	        }else if(titleObj!=null && titleObj){
	       		var title = parent.parent.document.getElementById("WindowTitleId").innerHTML;
	        	if(title.split("-").length>1){
	        		parent.parent.document.getElementById("WindowTitleId").innerHTML = title.split("-")[0];
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
		function fill(){
			if($(":input:checkbox[name=idList][checked]").size()==1){
				var $con = $(":input:checkbox[name=idList][checked]").parent().parent();
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
							va+=$(":input:checkbox[name=idList][checked]").val();
						}else{
							va+=$.trim($con.children().eq(index).text())+"-";
						}
					});
					rv.push(va);
				});
				window.returnValue=rv;
				window.close();
			}
			else{
				alert('请选中一条数据');
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
						<s:elseif test="%{#sr.operationType=='FillData'}">
							<a class="Menu_Frame_Div_Cell" href="javascript:fill()">
								<s:property value="operationName" /> 
							</a>
					    </s:elseif>
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
				<div style="float:left;padding-top:2px;">
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
			
			<!-- 在IE6中，此处的错误提示信息会占用空间，如果此处内容为空的时候，分页栏会被往下推 -->
			<div id="ShowErrMes_Div" class="ShowErrMes_Div">
				<% String requestValue= (String)request.getAttribute("position_view"); %>
				<%=requestValue %>
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
						<s:elseif test="%{#sr.multipleSelect==true}">
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
					</s:iterator>
	
					<div class="selectButtonDiv">
						<div>
							<s:iterator value="serviceResult.operationList" id="sr">
								<s:if test="%{#sr.operationType=='Select'}">
									<a href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"><s:property value="operationName" /></a>
								</s:if>
							</s:iterator>
						</div>
					</div>
					<div style="width:100%; height:0px;float:left;"></div>
					<br style="clear:both;"/>
				</div>
			</s:if>

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
			<div id="Table_Frame_Div" class="Table_Frame_Div">
				<table id="Grid_Task" class="Grid_Table" border="1px"  cellpadding="0" cellspacing="0" Rules=rows>
					<tr id="JS_Grid_Head_Row" class="JS_Grid_Head_Row">
						<s:iterator value="serviceResult.linkedList.get(0)" id="linkedList" status="linkedListStatus">
							<td title="<s:property value="operationName" />" class="JS_Grid_Head_Row_Cell" style="margin-left:5px; width: <s:property value="(width==null || width=='')?(operationName.equals('导入')?250:100):width" />px;" columncode="Col1">
								<div class="TD_Div_Nowrap"> <s:property value="operationName" /> </div>
							</td>
						</s:iterator>
						<s:iterator value="serviceResult.showNameList" id="showNameList" status="showNameListStatus">
							<td title="<s:property value="showName"/>" class="JS_Grid_Head_Row_Cell" style="width: <s:property value="(width==null || width=='')?80:width"/>px;">
								<div class="TD_Div_Nowrap"> <s:property value="showName" /></div>
							</td>
						</s:iterator>
					</tr>
					<s:if test="serviceResult.valueTable==null || serviceResult.valueTable.size==0"></s:if>
					<s:else>
						<s:iterator value="serviceResult.valueTable" id="vt" status="st">
							<tr class="JS_Grid_Data_Row" style="font-size: 12px; font-family: 微软雅黑;" Rows_Type='DataRow'>
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
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="margin-left:5px; width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
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
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="margin-left:5px; width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
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
										<div title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="margin-left:5px; width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA">
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
										<div title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="margin-left:5px; width: <s:property value="(width==null || width=='')?75:width" />px;text-overflow:ellipsis; white-space:nowrap;overflow: hidden;" columncode="ColA">
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
									<s:else>
										<td title="<s:property value='operationName'/>" class="JS_Grid_Data_Row_CellLink" style="margin-left:5px; width: <s:property value="(width==null || width=='')?100:width" />px;" columncode="ColA"> 
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
											<s:else>
												<div class="TD_Div_Nowrap"> <s:property value="value" />&nbsp; </div>
			            			 		</s:else>
										</td>
									</s:if>
								</s:iterator>
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
	</body>
	<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript" ></script>
</html>
