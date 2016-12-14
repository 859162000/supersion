$(document).ready(function(){
	var maxSessionTime=$("#maxSessionTime").val();
	var saveData;
	
	var WHERE_CONDTION_FIELD_SPIT='$';
	var WHERE_CONDTION_FIELD_COLUMN_SPIT='#';
	var WHERE_CONDTION_FIELD_COLUMN_TYPE_SPIT='|';
	
	var setInt = setInterval(function(){
		maxSessionTime=maxSessionTime-1;
        var m=Math.floor(maxSessionTime/60%60);
        var s=Math.floor(maxSessionTime%60);
        $("#t_d").html(m+'分'+s+'秒');
        if(maxSessionTime==0){
        	clearInterval(setInt);
        	$("#t_d").html('Session TimeOut！');
        }
	},1000);
	function GetFromData(){
		var fromdata=new Array();
		$.each($("#data").children(),function(i,tr){
			if(i>0){
				var type = $(tr).find(":input[name=type]").val();
				if(type=="symbol"){
					fromdata.push({type:'symbol',op:$(tr).find(":input[name=op]").val()});
				}else if(type=="item"){
					fromdata.push({type:'item',curr:$(tr).find(":input[name=curr]").val()
						,itemCode:$(tr).find(":input[name=itemCode]").val()
						,property:$(tr).find(":input[name=property]").val()
						,extend1:$(tr).find(":input[name=extend1]").val()
						,extend2:$(tr).find(":input[name=extend2]").val()
						,time:$(tr).find(":input[name=time]").val()
						,freq:$(tr).find(":input[name=freq]").val()
						,dtDate:$(tr).find(":input[name=dtDate]").val()
						,instInfo:$(tr).find(":input[name=instInfo]").val()});
				}else if(type=="const"){
					fromdata.push({type:'const',constv:$(tr).find(":input[name=const]").val()});
				}else if(type=="sql"){
					fromdata.push({type:'sql',sqlContent:$(tr).children(":eq(3)").text()});
				}else if(type=="detail"){
					fromdata.push({type:'detail',acctableName:$(tr).find(":input[name=acctableName]").val()
						,CalcField:$(tr).find(":input[name=CalcField]").val()
						,CalcType:$(tr).find(":input[name=CalcType]").val()
						,WhereCondition:$(tr).find(":input[name=WhereCondition]").val()});
				}
			}
		});
		return fromdata;
	}
	$("#btnSave").click(function(){
		var u=$("#basePath").val() + "ajax/ShowFromSavebyAjax-"+$("#type").val()+".action";
		var fromdata=GetFromData();		
		
		$.ajax({
			type:"POST",
            url:u,
            data:{calcData:JSON.stringify(fromdata),vs:"S"},
            datatype: "json",
            beforeSend:function(){},
            success:function(data){
            	var s=eval("("+data+")");
            	if(s==null || s=='' || s=='null'){
            		alert('保存成功!');
            	}else{
            		alert(s[0]);
            	}
            },
			error:function(data){alert(data.responseText)}
		});
	})
	$("#btnDel").click(function(){
		var $check=$("#data :input:checkbox[checked]");
		
		if($check.size()>0 && confirm('确定要删除数据？删除后保存才有效。')){
			$check.parent().parent().remove();
		}
		$.each($("#data tr:gt(0)"),function(i,tr){			
			$(tr).children().eq(1).html(i+1);
		})
	})
	$("#detailControl").click(function(){
		var tableId= $("#accSelect").val();
		if(tableId!='-1'){
			var url = $("#basePath").val()+'report/ShowCalcRuleDetail-report.dto.ItemsCalculation.action?id='+tableId;
			var retValue = window.showModalDialog(url,'',"dialogWidth=700px;dialogHeight=500px");
			if (typeof(retValue) != "undefined"){				
				AddTr('detail',retValue);
			}
		}		
	})
	$("#data").on('click','tr',function(){
		$("#data tr").removeClass("tr_highlight");
		$(this).addClass("tr_highlight");
	})
	$("tbody").on('click', 'a[name=bound]', function() {
		var acctableName=$(this).siblings("input[name=acctableName]").val();
		var CalcField=$(this).siblings("input[name=CalcField]").val();
		var CalcType=$(this).siblings("input[name=CalcType]").val();
		var WhereCondition=$(this).siblings("input[name=WhereCondition]").val();
		var obj = new DetailObject(CalcField,CalcType);		
		$.each(WhereCondition.split(WHERE_CONDTION_FIELD_SPIT),function(i,row){
			var colArr=row.split(WHERE_CONDTION_FIELD_COLUMN_SPIT);
			obj.AddData(new Array(colArr[0],colArr[1],colArr[2],colArr[3]));
		})
				
		var url = $("#basePath").val()+'report/ShowCalcRuleDetail-report.dto.ItemsCalculation.action?id='+acctableName;
		var retValue = window.showModalDialog(url,obj,"dialogWidth=700px;dialogHeight=500px");
		if (typeof(retValue) != "undefined"){
			$(this).siblings("input[name=CalcField]").val(retValue.field);
			$(this).siblings("input[name=CalcType]").val(retValue.calc);
			var where = '';	
			for(var i=0;i<retValue.data.length;i++){
				var tt = retValue.data[i];
				var s1 = '';
				for(var j=0;j<tt.length;j++){
					if(j==tt.length-1){
						s1=s1+tt[j];
					}else{
						s1=s1+tt[j]+WHERE_CONDTION_FIELD_COLUMN_SPIT;
					}					
				}
				if(i==retValue.data.length-1){
					where =where +s1;
				}else{
					where =where +s1+WHERE_CONDTION_FIELD_SPIT;
				}				
			}
			$(this).siblings("input[name=WhereCondition]").val(where);
			$(this).text('{'+$(this).siblings("input[name=acctableName]").val()+':'+$(this).siblings("input[name=CalcType]").val()+'('
					+$(this).siblings("input[name=CalcField]").val()+')'+where+'}');			
		}
	});

	function AddTr(type,thsObj){
		var $dataBody = $("#data");
		var index = $dataBody.children().size();
		var tr = '<tr class="JS_Grid_Data_Row"  style="font-size: 12px; font-family: 微软雅黑; text-align:center;">';
		tr+='<td style="overflow:visible;vertical-align: middle;"><input  name="JS_Grid_Check" class="Grid_CheckBox" type="checkbox"/></td>';
		tr+='<td>'+index+'</td>';
		switch(type){
			case 'symbol':
				tr+="<td>符号<input type='hidden' name='type' value='symbol'/></td>";
				tr+='<td>'+thsObj.attr("key")+"<input type='hidden' name='op' value='"+thsObj.attr("key")+"'/>"+'</td>';
				break;
			case 'item':
				tr+="<td>指标<input type='hidden' name='type' value='item'/></td>";
				tr+="<td>"+$('#itemSelect').val()+"-"+$('#txtItemName').val()+"  指标属性：["+ $("#protertySelect").find("option:selected").text()+"]  币种：["
				+ $("#currSelect").find("option:selected").text()+"] 期数：["+ $("#timeSelect").find("option:selected").text()+"] 扩展维度1：["
				+ $("#extendSelect1").find("option:selected").text()+"] 扩展维度2：["+ $("#extendSelect2").find("option:selected").text()+"]  频度：["+
				$("#freqSelect").find("option:selected").text()+"]  机构：["+ $("#instInfo").find("option:selected").text()+"] 日期：["
				+ $("#dtDate").val()+"]"
				+"<input type='hidden' name='itemCode' value='"+$("#itemSelect").val()+"'/>"
				+"<input type='hidden' name='curr' value='"+$("#currSelect").val()+"'/>"
				+"<input type='hidden' name='property' value='"+$("#protertySelect").val()+"'/>"
				+"<input type='hidden' name='extend1' value='"+$("#extendSelect1").val()+"'/>"
				+"<input type='hidden' name='extend2' value='"+$("#extendSelect2").val()+"'/>"
				+"<input type='hidden' name='time' value='"+$("#timeSelect").val()+"'/>"
				+"<input type='hidden' name='freq' value='"+$("#freqSelect").val()+"'/>"
				+"<input type='hidden' name='dtDate' value='"+$("#dtDate").val()+"'/>"
				+"<input type='hidden' name='instInfo' value='"+$("#instInfo").val()+"'/>"
				+"</td>";
				break;
			case 'const':
				var v = thsObj.prev().val();
				tr+="<td>常量<input type='hidden' name='type' value='const'/></td>";
				tr+='<td>'+v+"<input type='hidden' name='const' value='"+v+"'/>"+'</td>';
				break;
			case 'sql':
				var v = thsObj.prev().val();
				tr+="<td>SQL<input type='hidden' name='type' value='sql'/></td>";
				tr+='<td>'+v+'</td>';
				break;
			case 'detail':
				var where = '';	
				for(var i=0;i<thsObj.data.length;i++){
					var tt = thsObj.data[i];
					var s1 = '';
					for(var j=0;j<tt.length;j++){
						s1=s1+tt[j]+WHERE_CONDTION_FIELD_COLUMN_SPIT;
					}					
					where =where +s1.substring(0, s1.length-1)+WHERE_CONDTION_FIELD_SPIT;
				}
				where=where.substring(0, where.length-1);
				var displayTxt = "{"+$("#accSelect").val()+":"+thsObj.calc+"("+thsObj.field+")"
				+"["+where+"]"+"}";
				tr+="<td>明细表<input type='hidden' name='type' value='detail'/></td>";				
				tr+='<td>'+"<a href='javascript:;' name='bound'>"+displayTxt.replace(new RegExp(WHERE_CONDTION_FIELD_COLUMN_SPIT,'gm'),' ')+"</a>"
				+"<input type='hidden' name='acctableName' value='"+$("#accSelect").val()+"'/>"
				+"<input type='hidden' name='CalcField' value='"+thsObj.field+"'/>"
				+"<input type='hidden' name='CalcType' value='"+thsObj.calc+"'/>"
				+"<input type='hidden' name='WhereCondition' value='"+where+"'/>"
				+'</td>';
				break;
		}
		tr+='<td></td>';
		tr+='</tr>';
				
		var selTr=$(".tr_highlight");
		
		if(selTr.size()>0){
			if(selTr.index()<selTr.parent().children().size()){
				
				selTr.after($(tr));
			
			}
		}else{
			$(tr).appendTo($dataBody);
		}
		$.each($("#data tr:gt(0)"),function(i,tr){			
			$(tr).children().eq(1).html(i+1);
		})		
	}
		
	$(".ControlArea .symbol input[type=button]").click(function(){
		AddTr('symbol',$(this));
	})
	$("#constBtn").click(function(){
		AddTr('const',$(this));
	})	
	$("#sqlBtn").click(function(){
		AddTr('sql',$(this));
	})
	$("#btnUp").click(function(){
		var selTr=$(".tr_highlight");
		if(selTr.size()>0){
			if(selTr.index()>1){
				var prevele=selTr.prev("tr");
				selTr.remove();
				selTr.insertBefore(prevele);
			}
		}
		$.each($("#data tr:gt(0)"),function(i,tr){			
			$(tr).children().eq(1).html(i+1);
		})
	})
	$("#btnDown").click(function(){
		var selTr=$(".tr_highlight");
		if(selTr.size()>0){
			if(selTr.index()<selTr.parent().children().size()-1){
				var nextele=selTr.next("tr");
				selTr.remove();
				selTr.insertAfter(nextele);
			}
		}
		$.each($("#data tr:gt(0)"),function(i,tr){			
			$(tr).children().eq(1).html(i+1);
		})
	})
	//数据复制
	$("#btnCopy").click(function(){
		var $check=$("#data :input:checkbox[checked]");
		if($check.size()>0){
			$check.attr("checked",false);
			saveData=$check.parent().parent().clone();
			//clear high css
			saveData.removeClass("tr_highlight");
			alert("数据复制完成");
		}else{
			alert("请选择要复制的数据");
		}		
	})
	
	//数据粘贴
	$("#btnPaste").click(function(){debugger
		if(saveData==null){
			return;
		}
		 var $obj = $("#data :input:checkbox[checked]");
	        if(saveData.size() !=0){
	          if($obj.size()!=1){
	        	  if($obj.size()>1){
	        		  alert("数据插入的位置元素只能有一个");
	        	  }else{
	        		  alert("请选择数据插入的位置");
	        	  }	        	
 	            }else{
 	            $obj.attr("checked",false);
			    $obj.parent().parent().after(saveData);
			    
			    $.each($("#data tr:gt(0)"),function(i,tr){			
					$(tr).children().eq(1).html(i+1);
				})
			    
			    alert("数据粘贴成功");
 	           }
	        }else{
	        	alert("请先复制你需要粘贴数据");
	        }

	})
	
	
	
	
	$("#btnVal").click(function(){
		var u=$("#basePath").val() + "ajax/ShowFromSavebyAjax-"+$("#type").val()+".action";
		var fromdata=GetFromData();		
		
		$.ajax({
			type:"POST",
            url:u,
            data:{calcData:JSON.stringify(fromdata),vs:"V"},
            datatype: "json",
            success:function(data){
            	var s=eval("("+data+")");
            	if(s==null || s=='' || s=='null'){
            		alert('校验成功!');
            	}else{
            		alert(s[0]);
            	}
            },
			error:function(data){alert(data.responseText)}
		});
	})
	$("#itemBtn").click(function(){
		if($("#itemSelect").val()!='-1' 
			&& $("#currSelect").val()!='-1'
				&& $("#protertySelect").val()!='-1'
					&& $("#timeSelect").val()!='-1'){
						AddTr('item',$(this));
					}
	})
	
	$("#txtItemName").keyup(function(ev){		
		var str = $(this).val();
		var top =$(this).offset().top+$(this).height();
		var left =$(this).offset().left;
		//data
		if($.trim(str)!=''){
			var fromdata=new Array();
			fromdata.push({key:str});
			var u=$("#basePath").val() + "ajax/GetJsonInfo-report.dto.ItemInfo.action?"+new Date();
			$.ajax({
				type:"GET",
	            url:u,
	            data:{itemCode:str},
	            datatype: "json",
	            beforeSend:function(){},
	            success:function(data){
	            	$(".popdiv ul li:gt(0)").remove();
	            	var dataObj=eval("("+data+")");	  
	            	var keyArr= new Array();
	            	var dataArr=new Array();
	            	$.each(dataObj,function(i,item){		            		
	            		keyArr.push(i);
	            		dataArr[i]=item;
	            	});
	            	keyArr.sort();
	            	$.each(keyArr,function(index,key){
	            		$("<li code='"+key+"'>"+key+"-"+dataArr[key]+"</li>").appendTo($(".popdiv ul"));
	            	})},
				error:function(data){alert(data.responseText);}
			});
			var $curDiv=$(this).next();
			
			$curDiv.css({"top":top+"px","left":left+"px"}).show();
			$curDiv.show();
		}		
	});
	
	$("ul").on("mouseover","li",function(e){
		$(this).css("background","skyblue");
    });
	
	$("ul").on("mouseout","li",function(e){
		$(this).css("background","");
    });
	
	$("ul").on("click","li",function(e){
		$("#itemSelect").val($(this).attr("code"));
		$("#txtItemName").val($.trim($(this).text().substring($(this).attr("code").length+1,$(this).text().length)));
		$(".popdiv").hide();
    });
	
	$(document).click(function(){
		if($(".popdiv:visible").size()>0){		
			var oldvalue = $(".popdiv:visible").parent().children(":eq(0)").val();
			if(oldvalue!=''){
				var attrbute="li[code="+oldvalue+"]";				
				$("#txtItemName").val($.trim($(".popdiv:visible").find(attrbute).text()));
			}else{
				$("#txtItemName").val();
			}
		}
		$(".popdiv").hide();
	});
	
	$("#JS_Grid_Check").click(function() {
		var r=$("#JS_Grid_Check").attr("checked");
		$.each($("[name='JS_Grid_Check']"),function(i,ck){
			if(r=="checked")
			$(ck).attr("checked","checked");
			else
				$(ck).removeAttr("checked");
		})
		});
})