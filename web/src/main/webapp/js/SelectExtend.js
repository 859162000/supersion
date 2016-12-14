$(document).ready(function(e){
	$("[name=popBtn]").click(function(){
		var url= $(this).attr('url');
		if(url.indexOf("zxptsystem.dto.EIS_AuthorizationDocumentInfo")>0){
			if(document.getElementsByName("serviceParam.strCustomerID.strCustomerID")[0].value==""){
				alert("请选择授权客户名称！");
				return;
			}
			var type="";
			var objs=window.location.href.split(".action")[0].split("-");
			type=objs[objs.length-1];
			
			url+="&lastType="+type+"&strC="+document.getElementsByName("serviceParam.strCustomerID.strCustomerID")[0].value;
		}
		var retValue = window.showModalDialog(url,'',"dialogWidth=800px;dialogHeight=500px");
		
		if(typeof(retValue) != "undefined"){
			$.each(retValue,function(i,item){
				var kv= item.split("=");
				if(kv[1].substring(kv[1].length-1,kv[1].length)=="-"){
					$("input[name='serviceParam."+kv[0]+"']").val(kv[1].substring(0,kv[1].length-1));
				}else{
					$("input[name='serviceParam."+kv[0]+"']").val(kv[1]);
				}
			});
		}
	});
	$("li").mouseover(function(e){
			$(this).css("background","skyblue");
	    });
	$("li").mouseout(function(e){
		$(this).css("background","");
	});
	$("li").click(function(e){
		$(this).parent().parent().parent().children(":eq(0)").val($(this).attr("code"));
		$(this).parent().parent().parent().children(":eq(1)").val($.trim($(this).text()));
		
		$(".popdiv").hide();
	});
	$(":input.selectClass").click(function(event){
		$(".popdiv").hide();
		var top =$(this).offset().top+$(this).height()+5;
		var left =$(this).offset().left;
		var height="370px";
		var width=$(this).width()+10;
		var $div = $(this).parent().children(":eq(2)");
		if($div.find("ul li").size()<=20){
			height="auto";
		}
		if($div.width()>width){
			width="";
		}
		$div.find("ul li").show();
		$div.css({"height":height,"width":width}).css({"top":top+"px","left":left+"px"}).show();
		event.stopPropagation();
		})
	$(":input.selectClass").keyup(function(){		
		var str = $(this).val();
		var top =$(this).offset().top+$(this).height();
		var left =$(this).offset().left;
		var selector ="ul li";
		var contains=":contains("+str+")";
		if(str==''){
			contains='';
		}
		var $curDiv=$(this).next();
		var selector_contains=selector+contains;
		var $selector=$curDiv.find(selector_contains);
		if($selector.size()>0){
			$curDiv.find(selector).hide();
			$selector.show();
			if($selector.size()>20){
				$curDiv.css("height","370px");
			}
			else{
				$curDiv.css("height","");
			}
			$curDiv.css("width",$(this).width());
			$curDiv.css({"top":top+"px","left":left+"px"}).show();
		}
	});
	$(document).click(function(){
		if($(".popdiv:visible").size()>0){
		
			var oldvalue = $(".popdiv:visible").parent().children(":eq(0)").val();
			if(oldvalue!=''){
				var attrbute="li[code="+oldvalue+"]";
				
				$(".popdiv:visible").parent().children(":eq(1)").val($.trim($(".popdiv:visible").find(attrbute).text()));
			}else{
				$(".popdiv:visible").parent().children(":eq(1)").val();
			}
		}
		$(".popdiv").hide();
	});
});