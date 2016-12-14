<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'CalcRuleDetail.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=basePath%>js/jquery/jquery.1.7.js"
			type="text/javascript"></script>
		<script src="<%=basePath%>js/DetailObject.js" type="text/javascript"></script>
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
table {
	margin-top: 5px;
	margin-bottom: 5px;
	border: 1px solid black;
	border-collapse: collapse;
	color: #666;
}

table td,table th {
	border: 1px solid black;
	padding: 0 0em 0;
}
</style>
		<script type="text/javascript">
	function clearall(contain) {
		$(':input', contain).each(function() {
			var type = this.type;
			var tag = this.tagName.toLowerCase();
			if (type == 'text' || type == 'password' || tag == 'textarea')
				this.value = "";
			else if (type == 'checkbox' || type == 'radio')
				this.checked = false;
			else if (tag == 'select'){
					this.value = "-1";
				}
		});
	}
	$(document).ready(function() {
		var uArr = window.dialogArguments;
		if (uArr != "") {
			$("#fieldSelect").val(uArr.field);
			$("#calcSelect").val(uArr.calc);
			//debugger
			$.each(uArr.data, function(i, uPara) {
				if (i > 0) {
					var cloneTr = $("#dataTr").children().eq(0).clone();
					clearall(cloneTr);
					cloneTr.appendTo($("#dataTr"));
				}
				$.each(uPara, function(j, us) {
					var sum1 = j + 1;
					if (j == 0) {
						$("#dataTr").children().eq(i).children().eq(sum1).children().eq(0).val(us.split("|")[0]);
					} else {
						$("#dataTr").children().eq(i).children().eq(sum1).children().eq(0).val(us);
					}
				})
			})
		}
		$("#cancelBtn").click(function() {
			window.close();
		})
		$("#addRowbtn").click(function() {
			var cloneTr = $("#dataTr").children().eq(0).clone();
			clearall(cloneTr);
			cloneTr.appendTo($("#dataTr"));
		})
		$("#okBtn").click(function() {
					if ($("#fieldSelect").val() != "-1" && $("#calcSelect").val() != "-1") {
						var trArray = $("#dataTr").children();
						var obj = new DetailObject($("#fieldSelect").val(), $("#calcSelect").val());
						trArray.each(function() {
							var v1 = $(this).find("td select[name=fieldSelect]").val();
							var v2 = $(this).find("td select[name=conditionSelect]").val();
							var v3 = $(this).find("td input[name=valueInput]").val();
							var v4 = $(this).find("td select[name=typeSelect]").val();

							var tmp = new Array(v1, v2, v3, v4);
							obj.AddData(tmp);
						});
						window.returnValue = obj;
						window.close();
					} else
						alert("计算方式和计算字段不能为空");
				})

		//数据删除
		$("#delBtn").click(function() {
			var $obj = $("#dataTr :input:checkbox[checked]");
			if ($obj.size() > 0) {
				if ($("#dataTr").children().size() == $obj.size() && confirm('确定要全部删除数据？')) {					
					$.each($("#dataTr tr:gt(0)"), function(i, tr) {
						$(tr).remove();
					});
					$("input:checkbox").prop("checked","");
					$("#dataTr").find("select").val("-1");
					$("#dataTr").find("input").val("");
					
				} else {
					confirm('确定要删除数据？');
					$obj.parent().parent().remove();
				}

			}
			})
		//数据复制
		$("#copyBtn").click(
				function() {
					save = "";
					var $obj = $("#dataTr :input:checkbox[checked]");
					if ($obj.size() > 0 && confirm('数据复制成功')) {
						$("#dataTr :input:checkbox[checked]").attr("checked",
								false);
						save = $obj.parent().parent().clone();
						$.each(save, function(i, row) {
							var selects = $(row).find("select");
							$.each(selects, function(j, select) {
								$(select).val(
										$obj.parent().parent().eq(i).find("select").eq(j).val());
							});
						});
					}
				})

		//数据粘贴
		$("#pasteBtn").click(function() {
			if (save.length == 0) {
				alert("当前没有数据可以粘贴，请先复制数据在进行粘贴");
			} else {
				var $obj = $("#dataTr :input:checkbox[checked]");
				$obj.attr("checked", false);
				if ($obj.size() > 1) {
					alert("数据插入的位置元素只能有一个");
				} else {
					$obj.parent().parent().after(save);
					$obj.checked = false;
					alert("数据粘贴成功");
				}
			}
		})
		//数据上移
		$("#upBtn").click(function() {
			var $obj = $("#dataTr :input:checkbox[checked]");
			if ($obj.size() == 1) {
				if ($obj.parent().parent().index() > 0) {
					var prevele = $obj.parent().parent().prev("tr");
					$obj.parent().parent().remove();
					$obj.parent().parent().insertBefore(prevele);
				}

			} else {
				alert("请选择你要上移的一条元素");
			}
		})
		//数据下移
		$("#downBtn").click(
				function() {
					var $obj = $("#dataTr :input:checkbox[checked]");
					if ($obj.size() == 1) {
						if ($obj.parent().parent().index() < $obj.parent()
								.parent().parent().children().size() - 1) {
							var nextele = $obj.parent().parent().next("tr");
							$obj.parent().parent().remove();
							$obj.parent().parent().insertAfter(nextele);
						}
					} else {

						alert("请选择你需要下移的一条元素");
					}
				})

		$("#JS_Grid_Check").click(function() {
			var r = $("#JS_Grid_Check").attr("checked");
			$.each($("[name='JS_Grid_Check']"), function(i, ck) {
				if (r == "checked")
					$(ck).attr("checked", "checked");
				else
					$(ck).removeAttr("checked");
			})
		});

	})
</script>
	</head>

	<body ondragstart="return false;">
		<br />
		<div>
			<div>
				<select id="fieldSelect">
					<option value="-1">
						--请选择计算字段--
					</option>
					<s:iterator value="serviceResult.objectListItemInfo" id="ii">
						<option value='<s:property value="%{#ii.strFieldName}" />'>
							<s:property value="%{#ii.strChinaName}" />
						</option>
					</s:iterator>
				</select>
				<select id="calcSelect">
					<option value="-1">
						--请选择计算方式--
					</option>
					<s:iterator value="serviceResult.CalcuFunc" id="ii">
						<option value='<s:property value="%{#ii.key}" />'>
   							<s:property value="%{#ii.value}" />
   						</option>
					</s:iterator>					
				</select>
			</div>
			<div>
				<table style="width: 100%">
					<thead>
						<tr>
							<th class="JS_Grid_Head_Row_Cell">
								<input id="JS_Grid_Check" class="Grid_CheckBox" type='checkbox' onclick="click();"></input>
							</th>
							<th style="width: 23%;">
								字段
							</th>
							<th style="width: 23%;">
								条件
							</th>
							<th style="width: 23%;">
								字段值
							</th>
							<th style="width: 23%;">
								连接类型
							</th>
						</tr>
					</thead>
					<tbody id='dataTr'>
						<tr>
							<!-- 添加复选框 -->
							<td id="chckeboxList" name="chckeboxList" class="JS_Grid_Head_Row_Cell" style="text-align: center">
								<input attr name="JS_Grid_Check"  class="Grid_CheckBox" type="checkbox" value="<s:property value="%{icList.get(0).value}"/>"></input>
							</td>
							<td>
								<select name="fieldSelect" style="width:100%">
									<option value="-1">
										--请选择字段--
									</option>
									<s:iterator value="serviceResult.objectListItemInfo" id="ii">
										<option value='<s:property value="%{#ii.strFieldName}" />'>
											<s:property value="%{#ii.strChinaName}" />
										</option>
									</s:iterator>
								</select>
							</td>
							<td>
								<select name="conditionSelect" style="width:100%">
									<option value="-1">
										--请选择条件--
									</option>
									<option value='='>
										=
									</option>
									<option value='>'>
										>
									</option>
									<option value='>='>
										>=
									</option>
									<option value='&lt'>
										<
									</option>
									<option value='<='>
										<=
									</option>
									<option value='IN'>
										IN
									</option>
									<option value='LIKE'>
										LIKE
									</option>
									<option value='NOT LIKE'>
										NOT LIKE
									</option>
									<option value='<>'>
										<>
									</option>
								</select>
							</td>
							<td>
								<input name='valueInput' type='text' />
							</td>
							<td>
								<select name="typeSelect" style="width:100%">
									<option value="-1">
										--请选择连接类型--
									</option>
									<option value='AND'>
										AND
									</option>
									<option value='OR'>
										OR
									</option>
									<option value='('>
										(
									</option>
									<option value=')'>
										)
									</option>
								</select>
							</td>
							<!--  
    					<td>
    					   <a href="javascript:void(0)" onclick = 'del(this)'>删除</a>
    					</td>
    					-->
						</tr>
					</tbody>
				</table>
			</div>
			<div>
				<input type='button' id='addRowbtn' value='添加行' />
				<input type='button' id="delBtn" value='删除' />
				<input type='button' id="upBtn" value='上移' />
				<input type='button' id="downBtn" value='下移' />
				<input type='button' id="copyBtn" value='复制' />
				<input type='button' style="margin-right: 150px;" id="pasteBtn" value='粘贴' />
				<input type='button' id="okBtn" value='确定' />
				<input type='button' id="cancelBtn" value='取消' />
			</div>
		</div>
	</body>
</html>
