<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String dtoName=request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf("-")+1);
	if(dtoName.indexOf("&")>=0){
		dtoName=dtoName.substring(0,dtoName.indexOf("&"));
	}
	if(dtoName.indexOf(".action")>=0){
			dtoName=dtoName.substring(0,dtoName.indexOf(".action"));
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"></base>
		<title>ShowSaveOrUpdate</title>
		<link href="css/Comm.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/loading.css" rel="stylesheet" type="text/css" />
		<link href="css/selectdown.css" rel="stylesheet" type="text/css" />
		<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
		<link href="css/ControlCss.css" rel="stylesheet" type="text/css" />
		<link href="css/TaskFlowCss.css" rel="stylesheet" type="text/css" />
		<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/Comml.js" type="text/javascript" ></script>
		<script src="<%=basePath%>js/jquery/jquery.1.7.js" type="text/javascript" ></script>
		<script src="<%=basePath%>js/DatePicker/WdatePicker.js" type="text/javascript" ></script>
		<script type="text/javascript">
        function load(){
        	window.setTimeout(Time,30000)
        	$(document).ready(function(){});
        	$("#DataDiv").children("div").find("[readonly='readonly']").css("color","#CCC");

			if(window.parent.location.href.indexOf("ShowTree") <= -1){
				$("body").removeAttr("style");
            }
            if($(":file").attr("id")=="123456"){
            	$("#from1").attr("enctype","multipart/form-data");
            }
            
            $("#loading").remove();
        	
        	var URL_showModal = window.location.search;
						
        	if(URL_showModal.indexOf("showModal=true") >= 0){
        		$("#DataDiv").css("height","190px");
        		$("#DataDiv").children("div").css("margin-left","80px");//RemoveDiv("false1")
            	$("#buttons").children("[value='返回']").removeAttr('onclick');
            }
        }

        $(document).ready(function(){
        	$("#buttons").children("[value='返回']").bind('click',function(){
            	if(window.location.search.indexOf("showModal=true") >= 0)
            	RemoveDiv('true1');
            });
        });

        function RemoveDiv(isButone)
        {
            window.parent.RemoveBlockadeWindowDiv(isButone);
        }
        function onSelectChange(obj){
    		if(obj)
    		{
    			document.form1.action="<%=basePath%>${serviceResult.namespace}/${serviceResult.showUpdateListActionName}.action";
    			document.form1.submit();
    		}
    	}

    	function onOperationClick(actionNamespace,actionName)
    	{
    		document.form1.action="<%=basePath%>" + actionNamespace + "/" + actionName + ".action";
    		document.form1.submit();
    	}
    	
    	function onLinkClick(url){
            if(url.indexOf("?") > -1){
            	url += "&windowId=" + document.getElementById('windowId').value; 
            }
            else{
            	url += "?windowId=" + document.getElementById('windowId').value; 
            }
        	window.location.href=url;
        }
    	$(document).ready(function(){
        	$(":text").keydown(function(event){
            	if(event.which == 27){
                	return false;
                }
            });

            $(document).keydown(function(event){
                if(event.which == 13){
                	$("#buttons").children("[value='保存']").each(function(index){
                    	var onchlick = $(this).attr("onclick");
                    	var conditionOne = onchlick.substring(onchlick.indexOf("'")+1,onchlick.indexOf(",")-1);
                    	var conditionTwo = onchlick.substring(onchlick.indexOf(",")+2,onchlick.lastIndexOf("'"));
                    	$(this).bind('click',onOperationClick(conditionOne,conditionTwo));
                    });
                }
            });
        });
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

    	function onKeyPrice(t)
        {
    		var startWithSub = false;
    		if(t.value.length > 0 && t.value.substring(0,1) == "-"){
    			startWithSub = true;
    		}
           var stmp = "";
           if(t.value==stmp) return;
           var ms = t.value.replace(/[^\d\.]/g,"").replace(/(\.\d{100}).+$/,"$1").replace(/^0+([1-9])/,"$1").replace(/^0+$/,"0");
           var txt = ms.split(".");
           while(/\d{4}(,|$)/.test(txt[0]))
           txt[0] = txt[0].replace(/(\d)(\d{3}(,|$))/,"$1,$2");
           t.value = stmp = txt[0]+(txt.length>1?"."+txt[1]:"");
    	   if(startWithSub){
     	    	t.value = "-" + t.value;
     	   }
		}
	
		function onBlur(t,autoFill){
			if(t.value.substring(t.value.lastIndexOf(".")).length<autoFill){
				t.value = parseFloat(t.value).toFixed(autoFill);
			}
	    }
		
	function isExist(objectItemvalue){
		var flag=false;
		var obj=document.getElementById("serviceParam.roleInfoIdList");
		for(var i=obj.options.length;i>=0;i--){
			obj.remove(i);
		}
		for(t=0;t<objectItemvalue.length;t++){
			var itemvalue=objectItemvalue[t].split(",");
			var varItem=new Option(itemvalue[0],itemvalue[1]);
			obj.options.add(varItem);
		}
	}
	
	String.prototype.startWith=function(s){
		var reg=new RegExp("^"+s);
		return reg.test(this);
	}
	function sub(fieldName){
		var VALUEZJHM=document.getElementById(fieldName).value;
		var fieldtrueName=fieldName.replace("serviceParam.","");
		$.ajax({
   			type:"post", //请求方式
   			datatype: "json",//请求页面返回的数据类型       
   			contentType: "application/json",//注意请求页面的contentType 要于此处保持一致     
   			url:"<%=basePath%>framework/filledWithMessage-<%=dtoName%>.action?ZJHM="+VALUEZJHM+"&FIELDNAME="+fieldtrueName+"&className=<%=dtoName%>", //发送请求地址
  			
   			//请求成功后的回调函数有两个参数
   			complete:function(XMLHttpRequest){
   				if(XMLHttpRequest.readyState==4 && XMLHttpRequest.status==200){
   					var data=XMLHttpRequest.responseText;
   					if(data !=null && data !="null"){
   						var fieldNameArray;
   						data=data.replace("{}","").replace(/\{/g,"").replace(/\}/g,"").replace(/\"/g,"");
   						if(data !=null && data !="null"){
   							var data1=data.split(",");
   							for(i=0;i<data1.length;i++){
   								if(data1[i].startWith("fieldNameArray:")){
   									if(data1[i].split(":")[1].indexOf("|")>=0){
   										fieldNameArray=data1[i].split(":")[1].split["|"];
   									}
   									else{
   										fieldNameArray=new Array(data1[i].split(":")[1]);
   									}
   									break;
   								}
   							}
   							for(i=0;i<data1.length;i++){
   								for(t=0;t<fieldNameArray.length;t++){
   									if(data1[i].startWith(fieldNameArray[t]+":")&& (document.getElementById("serviceParam."+fieldNameArray[t]).value ==null || document.getElementById("serviceParam."+fieldNameArray[t]).value =="")){
   										document.getElementById("serviceParam."+fieldNameArray[t]).value=data1[i].split(":")[1];
   										break;
   									}
   								}
   							}
   						}
   					}
   				}
   			}
   		});
	}
	function onSelectChangeRole(){
		var dtoNamed="<%=dtoName%>";
		if(dtoNamed =="coresystem.dto.UserInfo"){
			var obj=document.getElementById("serviceParam.instInfo.strInstCode");
			var VALUEstrInstCode=obj.options[obj.selectedIndex].value;
			$.ajax({
   				type:"post", //请求方式
   				datatype: "json",//请求页面返回的数据类型       
   				contentType: "application/json",//注意请求页面的contentType 要于此处保持一致     
   				url:"<%=basePath%>framework/userInfoFilledWithMessage-<%=dtoName%>.action?strInstCode="+VALUEstrInstCode, //发送请求地址
  			
   				//请求成功后的回调函数有两个参数
   				complete:function(XMLHttpRequest){
   					if(XMLHttpRequest.readyState==4 && XMLHttpRequest.status==200){
   						var data=XMLHttpRequest.responseText;
   						if(data !=null && data !="null"){
   							data=data.replace("{}","").replace(/\[/g,"").replace(/\]/g,"").replace(/\{/g,"").replace(/\}/g,"").replace(/\"/g,"");
   							if(data !=null && data !="null"){
   						  	 	var data1=data.split(",");
   						   		var strRoleCodeAndValue="";
   								for(i=0;i<data1.length;i++){
   									if(data1[i].startWith("strRoleCode:")){
   										strRoleCodeAndValue=strRoleCodeAndValue+data1[i].split(":")[1]+"|";
   									}
   									else if(data1[i].startWith("strRoleName:")){
   										strRoleCodeAndValue=strRoleCodeAndValue+data1[i].split(":")[1]+","
   									}
   								}
   								if(strRoleCodeAndValue.length>=1){
   									strRoleCodeAndValue=strRoleCodeAndValue.substring(0,strRoleCodeAndValue.length-1);
   									var objectItemValue;
   									if(strRoleCodeAndValue.indexOf("|")>=0){
   										objectItemValue=strRoleCodeAndValue.split("|");
   									}
   									else{
   										objectItemValue=new Array(strRoleCodeAndValue);
   									}
   									 
   									isExist(objectItemValue);
   								}
   							}
   						}
   					}
   				}
   			});
   		}
	}

	
    </script>
	</head>
	<body id="bodID" onload="load()" style="overflow: hidden;z-index: 1;" ondragstart="return false;">
	
		<div id="loading" class="loading" style="">
			<div id="query_hint" class="query_hint">
	           		页面加载中，请稍等...
	        </div>
        </div>
	
		<s:form name="form1" id="from1" method="POST" >
			
			<div style="width: 97%; height: 85%; border: 1px solid #dadada; margin-top: 2%;margin-left: auto;margin-right: auto;">
				<div style="float: left; width: 100%; padding: 5px;line-height: 40px;">
					<div style="float:left;width:105px;text-align:right;margin-right: 2%;">			
						<img src="../images/User/treehead.png" style="width: 40px; height: 40px; margin-left: 20px;" />
					</div>
					<div style="line-height:15px;word-wrap:break-word;float:left;width:80%;height:40px; text-align:left;overflow-x: hidden;overflow-y: auto;font-size: 12px;color: red; ">
						<s:actionerror/>
					</div>
				</div>
				<hr style="float: left; width: 95%; margin-top: 5px;" />
				
				<div id="DataDiv" style="width:100%; height:350px;	overflow-y: auto; overflow-x: hidden;">
					<s:iterator value="serviceResult.showFieldValueList" id="sr" status="i">
							<s:if test="%{#sr.showField.thousandth == true}">
								<div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
									<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										<s:property value="%{#sr.showField.fieldShowName}" />
									</samp>
									<s:textfield number='true' sstyle="TextBox" class="TextBox TextBox:hover"
										style="float:left; margin-left:5px; width:130px;margin-top:4px;"
										name="serviceParam.%{#sr.showField.paramName}"
										label="%{#sr.showField.fieldShowName}"
										id="serviceParam.%{#sr.showField.paramName}" onblur="onBlur(this,%{#sr.showField.autoFill})"
										value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}" onkeyup="onKeyPrice(this);"></s:textfield>
									<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
											<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
											<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
													value="%{#sr.checkMessage}" /> </font>
										</samp>
									</div>
								</div>
							</s:if>
							<s:elseif test="%{#sr.showField.singleTag=='textField'}">
							    <div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
									<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										<s:property value="%{#sr.showField.fieldShowName}" />
									</samp>
									<s:if test="%{#sr.showField.paramName=='ZJHM' || #sr.showField.paramName=='GGZJHM' 
									|| #sr.showField.paramName=='GDZJHM' || #sr.showField.paramName=='ZJHM_1' 
									||  #sr.showField.paramName=='ZJHM_2'}">
										<s:textfield ttt="one" sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:130px;margin-top:4px;"
											name="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											id="serviceParam.%{#sr.showField.paramName}" onblur="onBlur(this,%{#sr.showField.autoFill})"
											value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"  onblur="sub('serviceParam.%{#sr.showField.paramName}')"></s:textfield>
									</s:if>
									<s:else>
										<s:textfield ttt="one" sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:130px;margin-top:4px;"
											name="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											id="serviceParam.%{#sr.showField.paramName}"
											value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}" ></s:textfield>
									</s:else>
									<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
										<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
											<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
													value="%{#sr.checkMessage}" /> </font>
										</samp>
									</div>
								</div>
							</s:elseif>
							
							<s:if test="%{#sr.showField.singleTag=='textPassWordField'}">
							    <div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
									<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										<s:property value="%{#sr.showField.fieldShowName}" />
									</samp>
									<s:if test="#sr.readOnly == false">	
										<input type="password" name="serviceParam.<s:property value="%{#sr.showField.paramName}"/>" 
									       value="<s:property value="%{#sr.fieldValue}"/>"
									       style="float:left; margin-left:5px; width:130px;margin-top:4px;"
									       sstyle="TextBox" class="TextBox TextBox:hover"/>
									</s:if>
									<s:else>
										<input type="password" name="serviceParam.<s:property value="%{#sr.showField.paramName}"/>" 
									       value="<s:property value="%{#sr.fieldValue}"/>" 
									       id="serviceParam.%{#sr.showField.paramName}"
									       style="float:left; margin-left:5px; width:130px;margin-top:4px;"
									       sstyle="TextBox" class="TextBox TextBox:hover" readonly="readonly"/>
									</s:else>        
									<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
										<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
											<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
													value="%{#sr.checkMessage}" /> </font>
										</samp>
									</div>
								</div>
							</s:if>
							
							<s:elseif test="%{#sr.showField.singleTag=='selectField'}">
							    <s:if test="%{#sr.showField.errorSpace == false}">
							        <div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
										<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
											style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
											<s:property value="%{#sr.showField.fieldShowName}" />
										</samp>
										<s:if test="#sr.readOnly==true">
											<s:hidden name="serviceParam.%{#sr.showField.paramName}"
												value="%{#sr.fieldValue}"></s:hidden>
										</s:if>
										<s:if test="%{#sr.showField.paramName=='instInfo.strInstCode'}">
											<s:select sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:135px;margin-top:4px;"
											list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
											id="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											multiple="%{#sr.showField.selectMultiple}"
											value="%{#sr.fieldValue}"
											id="serviceParam.%{#sr.showField.paramName}"
											headerKey="%{#application.emptySelectValueName}"
											headerValue="---请选择---"
											onchange="onSelectChangeRole();"
											disabled="%{#sr.readOnly}" />
										</s:if>
										<s:else>
											<s:select sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:135px;margin-top:4px;"
											list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
											id="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											multiple="%{#sr.showField.selectMultiple}"
											value="%{#sr.fieldValue}"
											id="serviceParam.%{#sr.showField.paramName}"
											headerKey="%{#application.emptySelectValueName}"
											headerValue="---请选择---"
											onchange="onSelectChange(%{#sr.selectChange});"
											disabled="%{#sr.readOnly}" />
										</s:else>
											
										<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
											<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
												<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
														value="%{#sr.checkMessage}" /> </font>
											</samp>
										</div>
									</div>
							    </s:if>
							    <s:else>
							        <div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:280px; text-align:left;">
										<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
											style="float: left; width: 80px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
											<s:property value="%{#sr.showField.fieldShowName}" />
										</samp>
										<s:if test="#sr.readOnly==true">
											<s:hidden name="serviceParam.%{#sr.showField.paramName}"
												value="%{#sr.fieldValue}"></s:hidden>
										</s:if>
										<s:select sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:135px;margin-top:4px;"
											list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											id="serviceParam.%{#sr.showField.paramName}"
											multiple="%{#sr.showField.selectMultiple}"
											value="%{#sr.fieldValue}"
											id="serviceParam.%{#sr.showField.paramName}"
											headerKey="%{#application.emptySelectValueName}"
											headerValue="---请选择---"
											onchange="onSelectChange(%{#sr.selectChange});"
											disabled="%{#sr.readOnly}" />
									</div>
							    </s:else>
							</s:elseif>
							<s:elseif test="%{#sr.showField.singleTag=='multipleSelectField'}">
							    <s:if test="%{#sr.showField.errorSpace == false}">
									<div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
										<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
											style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
											<s:property value="%{#sr.showField.fieldShowName}" />
										</samp>
										<s:if test="%{#sr.showField.height != null}">
											<s:select sstyle="TextBox" class="TextBox TextBox:hover"
												list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
												multiple="true" label="%{#sr.showField.fieldShowName}"
												id="serviceParam.%{#sr.showField.paramName}"
												value="%{#sr.fieldValue}" 
												cssStyle="width:135px;float:left; margin-left:5px;margin-top:4px;margin-bottom: 2%;"
												/>
										</s:if>
										<s:else>
										    <s:select sstyle="TextBox" class="TextBox TextBox:hover"
												style="float:left; margin-left:5px; width:135px;margin-top:4px;margin-bottom: 2%;"
												list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
												id="serviceParam.%{#sr.showField.paramName}"
												multiple="true" label="%{#sr.showField.fieldShowName}"
												value="%{#sr.fieldValue}" />
										</s:else>
										<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
											<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
												<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
														value="%{#sr.checkMessage}" /> </font>
											</samp>
										</div>
									</div>
							    </s:if>
							    <s:else>
								    <div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:300px; text-align:left;">
										<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
											style="float: left; width: 80px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
											<s:property value="%{#sr.showField.fieldShowName}" />
										</samp>
										<s:select sstyle="TextBox" class="TextBox TextBox:hover"
												list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
												id="serviceParam.%{#sr.showField.paramName}"
												multiple="true" label="%{#sr.showField.fieldShowName}"
												value="%{#sr.fieldValue}" 
												cssStyle="width:200px;height:200px;float:left; margin-left:5px;margin-top:4px;margin-bottom: 2%;"
												/>
									</div>
							    </s:else>
							</s:elseif>
							<s:elseif test="%{#sr.showField.singleTag=='treeField'}">
								    <div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:350px; text-align:left;">
										<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
											style="float: left; width: 80px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
											<s:property value="%{#sr.showField.fieldShowName}" />
										</samp>
										<s:select sstyle="TextBox" class="TextBox TextBox:hover" listKey="functionCode" listValue="functionName"
												list="#sr.tag" name="serviceParam.%{#sr.showField.paramName}"
												multiple="true" label="%{#sr.showField.fieldShowName}"
												value="%{#sr.fieldValue}" 
												cssStyle="width:350px;height:250px;float:left; margin-left:5px;margin-top:4px;margin-bottom: 2%;"
												/>
									</div>
							</s:elseif>
							  <!--<s:elseif test="%{#sr.showField.singleTag=='treeField'}">
							    <div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:300px; text-align:left;">
									<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
										style="float: left; width: 80px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
											<s:property value="%{#sr.showField.fieldShowName}" />
									</samp>
								<div id="checkBoxTree" style="width:200px;height:200px;float:left; margin-left:5px;margin-top:4px;margin-bottom: 2%;border: 1px solid black;overflow: auto;">
										<script type="text/javascript"><!--
			  								d = new dTree('d','<%=basePath %>');
											d.add(0,-1,'');
			  								<s:iterator value="#sr.tag" id="vt">
					 							d.add('<s:property value="%{#vt.functionCode}"/>', '<s:property value="%{#vt.functionParentId}"/>','<s:property value="%{#vt.functionName}"/>');
					   						</s:iterator>
			  								document.write(d);
			  								//-->
										<!--</script>
									</div> 
								</div>
							</s:elseif>-->
							<s:elseif test="%{#sr.showField.singleTag=='fileField'}">
							    <div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
									<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										<s:property value="%{#sr.showField.fieldShowName}" />
									</samp>
									<s:file id="123456" sstyle="TextBox" class="TextBox TextBox:hover"
										style="float:left; margin-left:5px; width:130px;margin-top:4px;"
										name="serviceParam.%{#sr.showField.paramName}"
										id="serviceParam.%{#sr.showField.paramName}"
										label="%{#sr.showField.fieldShowName}"></s:file>
									<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
										<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
											<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
													value="%{#sr.checkMessage}" /> </font>
										</samp>
									</div>
								</div>
							</s:elseif>
							<s:elseif test="%{#sr.showField.singleTag=='multipleTextField'}">
								<div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
									<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										<s:property value="%{#sr.showField.fieldShowName}" />
									</samp>
									<s:textarea sstyle="TextBox" class="TextBox TextBox:hover"
										style="float:left; margin-left:5px; width:130px;margin-top:4px;"
										name="serviceParam.%{#sr.showField.paramName}"
										id="serviceParam.%{#sr.showField.paramName}"
										label="%{#sr.showField.fieldShowName}"
										value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"></s:textarea>
									<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
										<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
											<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
													value="%{#sr.checkMessage}" /> </font>
										</samp>
									</div>
								</div>
							</s:elseif>
							<s:elseif test="%{#sr.showField.singleTag=='dateField'}">
								<div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
									<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										<s:property value="%{#sr.showField.fieldShowName}" />
									</samp>
									<s:if test="#sr.readOnly==true">
										<s:textfield sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:130px;margin-top:4px;"
											name="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											id="serviceParam.%{#sr.showField.paramName}"
											value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"></s:textfield>
										<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
										<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
											<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
													value="%{#sr.checkMessage}" /> </font>
										</samp>
									</div>
									</s:if>
									<s:elseif test="#sr.readOnly==false">
										<s:textfield sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:130px;margin-top:4px;"
											name="serviceParam.%{#sr.showField.paramName}"
											onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'})"
											label="%{#sr.showField.fieldShowName}"
											id="serviceParam.%{#sr.showField.paramName}"
											value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"></s:textfield>
											<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
												<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
													<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
															value="%{#sr.checkMessage}" /> </font>
												</samp>
											</div>
									</s:elseif>
								</div>
							</s:elseif>
							<s:elseif test="%{#sr.showField.singleTag=='dateFieldNoSlash'}">
								<div style="float:left; font-family:微软雅黑;color:Black; font-size:12px; cursor:pointer; margin-left:7px; width:430px; text-align:left;">
									<samp title="<s:property value="%{#sr.showField.fieldShowName}" />" class="Font_css_normal"
										style="float: left; width: 115px; text-align: right;line-height:30px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;">
										<s:property value="%{#sr.showField.fieldShowName}" />
									</samp>
									<s:if test="#sr.readOnly==true">
										<s:textfield sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:130px;margin-top:4px;"
											name="serviceParam.%{#sr.showField.paramName}"
											id="serviceParam.%{#sr.showField.paramName}"
											label="%{#sr.showField.fieldShowName}"
											value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"></s:textfield>
										<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
										<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
											<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
													value="%{#sr.checkMessage}" /> </font>
										</samp>
									</div>
									</s:if>
									<s:elseif test="#sr.readOnly==false">
										<s:textfield sstyle="TextBox" class="TextBox TextBox:hover"
											style="float:left; margin-left:5px; width:130px;margin-top:4px;"
											name="serviceParam.%{#sr.showField.paramName}"
											onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyyMMdd'})"
											label="%{#sr.showField.fieldShowName}"
											id="serviceParam.%{#sr.showField.paramName}"
											value="%{#sr.fieldValue}" readonly="%{#sr.readOnly}"></s:textfield>
											<div title="<s:property value="%{#sr.checkMessage}"/>" style="width:160px;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;color:<s:property value="%{#sr.showColor}"/>; height:20px;">
												<samp style="margin-left: 7px;margin-top: 5px;line-height:30px;">
													<font  color="<s:property value="%{#sr.showColor}"/>"><s:property
															value="%{#sr.checkMessage}" /> </font>
												</samp>
											</div>
									</s:elseif>
								</div>
							</s:elseif>
							<s:elseif test="%{#sr.showField.singleTag=='hiddenField'}">
								<s:hidden name="serviceParam.%{#sr.showField.paramName}"
									value="%{#sr.fieldValue}"></s:hidden>
							</s:elseif>
					</s:iterator>
				</div>
			</div>
			
			<div id="buttons" style="width: 60%; padding: 0px; margin-top: 8px; margin-left:auto;">
				<s:iterator value="serviceResult.operationList" id="sr">
					<s:if test="%{#sr.operationType=='Post'}">
						<input class="button button:hover button:focus button:active" style="width:<s:property value="operationName.length()<=9?100:105+operationName.length()*11"/>px; height: 26px; margin-right: auto;"
							type="button" value="<s:property value="operationName"/>"
							onclick="onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')" />
					</s:if>
					<s:elseif test="%{#sr.operationType=='Get'}">
						<input class="button button:hover button:focus button:active" style="width:<s:property value="operationName.length()<=9?100:105+operationName.length()*11"/>px; height: 26px; margin-right: auto;"
							type="button" value="<s:property value="operationName"/>"
							onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action')" />
					</s:elseif>
					<s:elseif test="%{#sr.operationType=='Get1'}">
						<input class="button button:hover button:focus button:active" style="width:<s:property value="operationName.length()<=9?100:105+operationName.length()*11"/>px; height: 26px; margin-right: auto;"
							type="button" value="<s:property value="operationName"/>"
							onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction"/>.action?id=<s:property value="serviceResult.id"/>')" />
					</s:elseif>
					<s:elseif test="%{#sr.operationType=='Get2'}">
						<input class="button button:hover button:focus button:active" style="width:<s:property value="operationName.length()<=9?100:105+operationName.length()*11"/>px; height: 26px; margin-right: auto;"
							type="button" value="<s:property value="operationName"/>"                                                                                                                                    
							onclick="onLinkClick('<%=basePath%><s:property value="operationNamespace"/>/<s:property value="operationAction.substring(0,operationAction.indexOf('?'))"/>.action?id=<s:property value="serviceResult.id"/>&type=<s:property value="operationAction.substring(operationAction.indexOf('?')+6)"/>')" />
					</s:elseif>
					<s:elseif test="%{#sr.operationType=='Upload'}">
							<s:file id="123456" class="files" name="uploadFile"></s:file>
							<a class="button chat icon"
								style="margin-left: 5px; margin-top: 2px; font-size: 12px; font-family: 微软雅黑;"
								href="javascript:onOperationClick('<s:property value="operationNamespace"/>','<s:property value="operationAction"/>')"><s:property value="operationName" /> </a>
						</s:elseif>
				</s:iterator>
			</div>
			<s:hidden id="windowId" name="windowId"></s:hidden>
		</s:form>
	</body>
</html>
