var selectInput = "";
var readonly = "";
$(document).ready(function() {
	$(":text").focus(function() {
		var input = $(this);
		selectInput = input.attr("id");
		readonly = input.attr("readonly");
		//alert(selectInput);
	});
	$("body").keydown(function(event) {
		if (event.which == 8 && readonly == "readonly") {
			return false;
		}
	});

});
$(function() {
	$(window).scroll(function() {

		/*var top = $(window).scrollTop();
		var left = $(window).scrollLeft();
		$("#headNav").css({
			left : left,
			top : top
		});*/
		/*  var bottomNav=$("#bottomNav");
		  x=bottomNav.position();
		  var docHeight = $(document).height();
		　var windowHeight = $(window).height();
		　if(top + windowHeight < docHeight){
			  var newTop=x.top+top-bottomNav.height();
			  if(newTop>docHeight)
			  {
				  newTop=docHeight-bottomNav.height();
			  }
			  
		　　 　 $("#bottomNav").css({ left:left,top:newTop});
		  		//x=bottomNav.position();
		       $("#test").val(x.top);
		　　}*/

	});
});
//数据追查
function dataTrace() {
	if (selectInput == "") {
		alert("请先选中要追查的数据！");
	} else {
		var strRptCode = $("#strRptCode").val();
		var instCode = $("#strInstCode").val();
		var taskInstId = $("#strTaskRptInstID").val();
		var dtDate = $("#dtDate").val();
		var val = $("#" + selectInput).val();
		var url = $("#itemDataTrace").val() + selectInput + "&val=" + val
				+ "&dtDate=" + dtDate + "&strTaskRptInstID=" + taskInstId
				+ "&strInstCode=" + instCode + "&strRptCode=" + strRptCode;

		new window.parent.parent.parent.ObjectWindows("Main_Body", selectInput,
				520, 1000, url, "数据追查", "yes");
	}

}

//填报说明
function repMarkInfo() {
	var strRptCode = $("#strRptCode").val();
	var url = $("#repMark").val() + strRptCode;
	new window.parent.parent.parent.ObjectWindows("Main_Body",strRptCode+"_Win_Mark", 520, 1000, url, "填报说明", "yes");
}

//数据历史
function dataHistory() {
	if (selectInput == "") {
		alert("请先选中要对比的指标！");
	} else {
		$.ajax({
			type:"POST",
			url: "report/ShowAjax-ItemDataHistory.action", 
			data:{"id":selectInput,"strRptCode":$("#strRptCode").val()},
			success: function(msg){
				using('messager', function() {
					$.messager.show({
						title : '填报记录',
						msg : msg,
						width : 500,
						height : 300,
						timeout : 0
					});
				});
			}
		});
	}
}




function seashowtip(tips, flag, iwidth) { //显示指标相关提示的js
	var my_tips = document.all.mytips;
	if (flag) {
		my_tips.innerHTML = tips;
		my_tips.style.display = "block";
		my_tips.style.width = iwidth;
		my_tips.style.left = event.clientX + 10 + document.body.scrollLeft;
		my_tips.style.top = event.clientY + 10 + document.body.scrollTop;
	} else {
		my_tips.style.display = "none";
	}
}
function ShowAlert(str) {
	if (str == null || str == "")
		return;
	var pop = new Popup({
		contentType : 4,
		isReloadOnClose : true,
		width : 450,
		height : 180
	});
	pop.setContent("title", "信息");
	pop.setContent("alertCon", str);
	pop.build();
	pop.show();
}
function ShowAlertById(id) {
	var msg = $("#" + id).val();
	if (msg != "" && typeof (msg) != "undefined") {
		using('messager', function() {
			$.messager.show({
				title : '检验结果',
				msg : msg,
				width : 500,
				height : 300,
				timeout : 0
			});
			
		});
	}

	//var msg=$("#"+id).val();
	//$.messager.alert('My Title',msg);
	//ShowAlert(msg);
}
function up() {
	// 提交修改过的指标的js
	//var f = document.createElement("form"); 
	//f.style.display = "none";
	//window.history.go(-1);
	//alert(history.length );
	//window.history.go(history.length*-1);
	//window.location.reload()

	var f = $("#form1");
	f.attr("action", $("#strOldAction").val());
	f.submit();
}
function submitDirtyItems() {
	// 提交修改过的指标的js
	//var f = document.createElement("form"); 
	//f.style.display = "none";
	var f = $("#form1");
	f.attr("action", $("#submitItemsAction").val());
	//f.attr("action",$("#submitItemsAction").val());// " + basePath + report/SubmitItems.action + "; // 提交指标的action
	/*var controls = document.getElementsByTagName('input');
	document.body.appendChild(f);
	for(var i=controls.length-1; i>=0; i--){
	    if(controls[i].type=='text'){
	    	if(controls[i].defaultValue!=controls[i].value) {
	    		f.appendChild(controls[i]);
	    		controls[i].defaultValue=controls[i].value;
	        }
	    }
	}
	 //添加固定的几个信息框 strInstCode，strRptCode，dtDate
	f.appendChild(document.getElementsByName("strInstCode")[0]);
	f.appendChild(document.getElementsByName("strRptCode")[0]);
	f.appendChild(document.getElementsByName("dtDate")[0]);
	f.appendChild(document.getElementsByName("strOldAction")[0]);*/
	f.submit();
}
function submitCheck() {
	var f = $("#form1");
	f.attr("action", $("#checkAction").val());
	f.submit();
	/*
	var f = document.createElement("form"); 
	f.style.display = "none";  
	f.action =$("#checkAction").val();// " + basePath + report/ReportCheckBack- + strCheckInstName + .action?strTaskRptInstID= + taskRptInst.getId() + "; // 提交校验的action
	document.body.appendChild(f);
	f.appendChild(document.getElementsByName("strInstCode")[0]);
	f.appendChild(document.getElementsByName("dtDate")[0]);
	f.appendChild(document.getElementsByName("strTaskRptInstID")[0]);
	f.appendChild(document.getElementsByName("strFreq")[0]);*/
	//f.submit();
}
function submitSumCheck() {
	var f = $("#form1");
	f.attr("action", $("#sumCheckAction").val());
	f.submit();
	/*
	var f = document.createElement("form"); 
	f.style.display = "none";  
	f.action =$("#checkAction").val();// " + basePath + report/ReportCheckBack- + strCheckInstName + .action?strTaskRptInstID= + taskRptInst.getId() + "; // 提交校验的action
	document.body.appendChild(f);
	f.appendChild(document.getElementsByName("strInstCode")[0]);
	f.appendChild(document.getElementsByName("dtDate")[0]);
	f.appendChild(document.getElementsByName("strTaskRptInstID")[0]);
	f.appendChild(document.getElementsByName("strFreq")[0]);*/
	//f.submit();
}
function submitCalc() {
	var f = $("#form1");
	f.attr("action", $("#calcAction").val());
	f.submit();
	//var f = document.createElement("form"); 
	//f.style.display = "none";  
	//f.action =$("#calcAction").val();// " + basePath + report/ReportMergeBack- + strCalcInstName + .action?id= + taskRptInst.getId() + "; // 提交校验的action
	//document.body.appendChild(f); 
	//	f.appendChild(document.getElementsByName("strInstCode")[0]);
	//	f.appendChild(document.getElementsByName("dtDate")[0]);
	//	f.appendChild(document.getElementsByName("strTaskRptInstID")[0]);
	//window.location.href=f.action; 
}
var isSetting = false;
function setValue(id, isShowMinus, isShowRed) {
	if (isSetting == false) {
		isSetting = true;

		var v = $("#display_" + id).val();
		$("#" + id).val(v);
		if (v < 0) {
			if (isShowMinus == false) {
				$("#display_" + id).val(Math.abs(v));
			}
			if (isShowRed == true) {
				$("#display_" + id).css("color", "red");
			}

		} else {
			$("#display_" + id).css("color", "black");
		}
		isSetting = false;
	}

}

function rebuildData(){
	var f = $("#form1");
	f.attr("action", $("#rebuildDataAction").val());
	f.submit();
}

function downloadDetail(obj){
	sql = $(obj).text();
	$('#sql').val(sql);
	$('#sqlNum').val($(obj).attr('sqlcId'));
	$('#form1').submit();
}


