<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>服务器信息</title>
<meta charset="utf-8">
<base href="<%=basePath%>"></base>
<link href="css/TopMenuCss.css" rel="stylesheet" type="text/css" />
<script src="js/jquery/jquery.1.7.js" type="text/javascript"></script>
<script src="js/jquery/jquery-ui.min.js" type="text/javascript"></script>
<script src="js/ECharts/echarts.js"></script>

<script type="text/javascript">

	Date.prototype.pattern = function(fmt) {
		var o = {
			"M+" : this.getMonth() + 1, //月份         
			"d+" : this.getDate(), //日         
			"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时         
			"H+" : this.getHours(), //小时         
			"m+" : this.getMinutes(), //分         
			"s+" : this.getSeconds(), //秒         
			"q+" : Math.floor((this.getMonth() + 3) / 3), //季度         
			"S" : this.getMilliseconds() //毫秒         
		};
		var week = {
			"0" : "/u65e5",
			"1" : "/u4e00",
			"2" : "/u4e8c",
			"3" : "/u4e09",
			"4" : "/u56db",
			"5" : "/u4e94",
			"6" : "/u516d"
		};
		if (/(y+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		if (/(E+)/.test(fmt)) {
			fmt = fmt
					.replace(
							RegExp.$1,
							((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
									: "/u5468")
									: "")
									+ week[this.getDay() + ""]);
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(fmt)) {
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
			}
		}
		return fmt;
	}

	function showDataChart(chart, option, url, cfq, limit) {
		$.ajax({
			type : "post",
			async : false, //同步执行  
			url : url,
			data : {
				"sid" : '<s:property value="sid"/>',
				"pid" : '<s:property value="pid"/>',
				"cfq" : cfq,
				"limit" : limit
			},
			dataType : "json", //返回数据形式为json  
			success : function(result) {
				if (result) {
					option.xAxis[0].data = result.category;
					option.series[0].data = result.data;
					chart.setOption(option);
				}
			},
			error : function(errorMsg) {
				alert("不好意思，数据加载失败啦!");
				chart.hideLoading();
			}
		});
	}

	function showDymicChart(chart, option, url, cfq, limit) {
		$.ajax({
			type : "post",
			async : false, //同步执行  
			url : url,
			data : {
				"sid" : '<s:property value="sid"/>',
				"pid" : '<s:property value="pid"/>',
				"cfq" : cfq,
				"limit" : limit
			},
			dataType : "json", //返回数据形式为json  
			success : function(result) {
				if (result) {
					chart.addData([ [ 0, // 系列索引
					result.data[0], // 新增数据
					false, // 新增数据是否从队列头部插入
					false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
					result.category[0] // 坐标轴标签
					] ]);
				}
			},
			error : function(errorMsg) {
				alert("不好意思，数据加载失败啦!");
			}
		});
	}

	function showJCChart(chart, option, url, limit) {
		var cdate = $("#cdate").val();
		var cfq = $("#cfq").val();
		$.ajax({
			type : "post",
			async : false, //同步执行  
			url : url,
			data : {
				"sid" : '<s:property value="sid"/>',
				"pid" : '<s:property value="pid"/>',
				"cfq" : cfq,
				"cdate" : cdate,
				"devices" : "CPU,MEM",
				"limit" : limit
			},
			dataType : "json", //返回数据形式为json  
			success : function(result) {
				if (result) {
					option.xAxis[0].data = result.category;
					option.series[0].data = result.CPU;
					option.series[1].data = result.MEM;
					chart.setOption(option,true);
					chart.hideLoading();
				}
			},
			error : function(errorMsg) {
				alert("不好意思，数据加载失败啦!");
			}
		});
		
	}

	function onLinkClick(url) {
		if (url.indexOf("?") > -1) {
			url += "&windowId=" + document.getElementById('windowId').value;
		} else {
			url += "?windowId=" + document.getElementById('windowId').value;
		}

		if ('<s:property value="serviceResult.showModal"/>' == 'true') {
			url += "&showModal="
					+ '<s:property value="serviceResult.showModal"/>';
			AddUser(url);
		} else {
			window.location.href = url;
		}
	}

	$(document).ready(
			function() {
				$("#cdate").on("change", function() {
					$("#cdate_").val(this.value);
					$("#tiemfresh").click();
				});
				$("#cfq").on("change", function() {
					var cfq = this.value;
					var d = $("#cdate").val();
					$("#cdate_select img").not("#tiemfresh").hide();
					if (cfq == 's') {
						$("#cdateyMdHms").show();
						formatDate(d, 19);
					} else if (cfq == 'm') {
						$("#cdateyMdHm").show();
						formatDate(d, 16);
					} else if (cfq == 'h') {
						$("#cdateyMdH").show();
						formatDate(d, 13);
					} else if (cfq == 'd') {
						$("#cdateyMd").show();
						formatDate(d, 10);
					} else if (cfq == 'Mt') {
						$("#cdateyM").show();
						formatDate(d, 7);
					} else if (cfq == 'y') {
						$("#cdatey").show();
						formatDate(d, 4);
					}
					$("#tiemfresh").click();
				});

				$("#cdate_select img").not("#tiemfresh").on("click",
						function() {
							WdatePicker({
								el : 'cdate_',
								onpicked : function(dp) {
									$('#cdate').val(dp.cal.getNewDateStr());
									$("#tiemfresh").click();
								},
								dateFmt : $(this).attr('dateFmt')
							});
						});

				function formatDate(d, l) {
					if (d.length > l) {
						$("#cdate_").val(d.substr(0, l));
						$("#cdate").val(d.substr(0, l));
					} else {
						var today = new Date();
						var t = today.pattern("yyyy-MM-dd HH:mm:ss").substr(d.length, l - d.length);
						d += t;
						if (today.getMonth() == 1 && date.getDay() > 28 && l > 7) {
							var d1 = d.substr(0, 7);
							var d2 = d.substr(10, l - 10);
							d = d1 + "-28" + d2;
						}
						$("#cdate_").val(d);
						$("#cdate").val(d);
					}
				}

			});
</script>
</head>
<body style="margin:0px; width: 100%;height:100%;">
	<div id="Menu_Frame_Div" class="Menu_Frame_Div">
		<div style="float:left;width:100%;">
			<a class="Menu_Frame_Div_Cell" href="javascript:onLinkClick('<%=basePath%>framework/ShowList-jmx.dto.<s:property value="type"/>.action')"> 返回 </a>
		</div>
	</div>

	<div align="center">
		<s:hidden name="sid"></s:hidden>
		<s:hidden name="pid"></s:hidden>
		<s:hidden name="windowId"></s:hidden>
		<%-- <s:hidden name="cfq"></s:hidden>
		<s:hidden name="cdate"></s:hidden> --%>
		<div id="cpu" style="width: 800px;height:180px;"></div>
		<div id="mem" style="width: 800px;height:180px;"></div>

		<div id="cdate_select">
			<img id="cdateyMdHms" dateFmt="yyyy-MM-dd HH:mm:ss" src="<%=basePath %>js/DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"> <img id="cdateyMdHm"
				dateFmt="yyyy-MM-dd HH:mm" src="<%=basePath %>js/DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="display: none;"> <img id="cdateyMdH" dateFmt="yyyy-MM-dd HH"
				src="<%=basePath %>js/DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="display: none;"> <img id="cdateyMd" dateFmt="yyyy-MM-dd"
				src="<%=basePath %>js/DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="display: none;"> <img id="cdateyM" dateFmt="yyyy-MM"
				src="<%=basePath %>js/DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="display: none;"> <img id="cdatey" dateFmt="yyyy"
				src="<%=basePath %>js/DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="display: none;"> <input type="hidden" id="cdate_" name="cdate_" /> <input id="cdate"
				name="cdate" />
			<s:select id="cfq" list="cfqTypes" listKey="key" listValue="value" value="s"></s:select>
			<img id="tiemfresh" src="<%=basePath %>images/button/refresh-icon.png" width="18" height="18" align="absmiddle" style="display: none;" />
		</div>
		<div id="timecpumem" style="width: 800px;height:180px;"></div>
	</div>
	<script type="text/javascript">
		
		require.config({  
	       paths: {  
	     	  echarts: '<%=basePath%>/js/ECharts'
			}
		});

		require([ 'echarts', 'echarts/chart/line' ], function(ec) {
			var myChart = ec.init(document.getElementById('cpu'));// 初始化图表，注意这里不能用JQuery方式，否则会无法初始化  
			var timeTicket;
			var option = {
				noDataLoadingOption : {
					text : '暂无数据',
					effect : 'bubble',
					effectOption : {
						effect : {
							n : 0
						}
					}
				},
				title : {},
				tooltip : {
					trigger : 'axis'
				},
				toolbox : {
				//工具条
				},
				dataZoom : {},
				legend : {
					data : [ 'CPU' ]
				},
				grid : {
					y : '20px',
					backgroundColor : '#FFF'
				},
				xAxis : [ {
					type : 'category',
					scale : true,
					boundaryGap : false,
					axisLabel : {
						rotate : 50,
						interval : 0,
						formatter : function(value) {
							return value.substr(14);
						}
					},
					axisLine : {
						lineStyle : {
							width : 1,
							color : '#117DBB'
						}
					},
					splitLine : {
						lineStyle : {
							color : '#D9EAF4'
						}
					},
					axisTick : {
						show : false
					},
					data : []
				} ],
				yAxis : [ {
					type : 'value',
					min : 0,
					max : 100,
					splitNumber : 5,
					axisLine : {
						lineStyle : {
							width : 1,
							color : '#117DBB'
						}
					},
					splitLine : {
						lineStyle : {
							color : '#D9EAF4'
						}
					},
					axisLabel : {
						formatter : function(v) {
							return v + ' %'
						}
					}
				} ],
				series : [ {
					name : 'CPU',
					type : 'line',
					symbol : 'none',
					itemStyle : {
						normal : {
							color : '#4C9DCB',
							areaStyle : {
								type : 'default'
							}
						}
					},
					data : []
				} ]
			};
			//加载数据  
			showDataChart(myChart, option, "jmx/showCpu.action", "s", 30);

			clearInterval(timeTicket);
			timeTicket = setInterval(function() {
				// 动态数据接口 addData
				showDymicChart(myChart, option, "jmx/showCpu.action", "s", 1)
			}, <s:property value="cli"/>);

		});

		require([ 'echarts', 'echarts/chart/line' ], function(ec) {
			var myChart = ec.init(document.getElementById('mem'));// 初始化图表，注意这里不能用JQuery方式，否则会无法初始化  
			var timeTicket;
			var option = {
				noDataLoadingOption : {
					text : '暂无数据',
					effect : 'bubble',
					effectOption : {
						effect : {
							n : 0
						}
					}
				},
				title : {},
				tooltip : {
					trigger : 'axis'
				},
				toolbox : {
				//工具条
				},
				dataZoom : {},
				legend : {
					data : [ 'MEM' ]
				},
				grid : {
					y : '20px',
					backgroundColor : '#FFF'
				},
				xAxis : [ {
					type : 'category',
					scale : true,
					boundaryGap : false,
					axisLabel : {
						rotate : 50,
						interval : 0,
						formatter : function(value) {
							return value.substr(14);
						}
					},
					axisLine : {
						lineStyle : {
							width : 1,
							color : '#117DBB'
						}
					},
					splitLine : {
						lineStyle : {
							color : '#D9EAF4'
						}
					},
					axisTick : {
						show : false
					},
					data : []
				} ],
				yAxis : [ {
					type : 'value',
					min : 0,
					max : 100,
					splitNumber : 5,
					axisLine : {
						lineStyle : {
							width : 1,
							color : '#117DBB'
						}
					},
					splitLine : {
						lineStyle : {
							color : '#D9EAF4'
						}
					},
					axisLabel : {
						formatter : function(v) {
							return v + ' %'
						}
					}
				} ],
				series : [ {
					name : 'MEM',
					type : 'line',
					symbol : 'none',
					itemStyle : {
						normal : {
							color : '#4C9DCB',
							areaStyle : {
								type : 'default'
							}
						}
					},
					data : []
				} ]
			};
			//加载数据  
			showDataChart(myChart, option, "jmx/showMem.action", "s", 30);

			clearInterval(timeTicket);
			timeTicket = setInterval(function() {
				// 动态数据接口 addData
				showDymicChart(myChart, option, "jmx/showMem.action", "s", 1)
			}, <s:property value="cli"/>);

		});

		require([ 'echarts', 'echarts/chart/line' ], function(ec) {
			var myChart = ec.init(document.getElementById('timecpumem'));// 初始化图表，注意这里不能用JQuery方式，否则会无法初始化  
			var option = {
				noDataLoadingOption : {
					text : '暂无数据',
					effect : 'bubble',
					effectOption : {
						effect : {
							n : 0
						}
					}
				},
				title : {},
				tooltip : {
					trigger : 'axis'
				},
				toolbox : {
				//工具条
				},
				dataZoom : {},
				legend : {
					data : [ 'CPU', 'MEM' ]
				},
				grid : {
					y : '20px',
					backgroundColor : '#FFF'
				},
				xAxis : [ {
					type : 'category',
					scale : true,
					boundaryGap : false,
					axisLabel : {
						rotate : 50,
						interval : 0,
						formatter : function(value) {
							if (value.length > 5)
								return value.substr(value.length - 5);
							else
								return value;
						}
					},
					axisLine : {
						lineStyle : {
							width : 1,
							color : '#117DBB'
						}
					},
					splitLine : {
						lineStyle : {
							color : '#D9EAF4'
						}
					},
					axisTick : {
						show : false
					},
					data : []
				} ],
				yAxis : [ {
					type : 'value',
					min : 0,
					max : 100,
					splitNumber : 5,
					axisLine : {
						lineStyle : {
							width : 1,
							color : '#117DBB'
						}
					},
					splitLine : {
						lineStyle : {
							color : '#D9EAF4'
						}
					},
					axisLabel : {
						formatter : function(v) {
							return v + ' %'
						}
					}
				} ],
				series : [ {
					name : 'CPU',
					type : 'line',
					symbol : 'none',
					itemStyle : {
						normal : {
							color : '#4C9DCB',
							areaStyle : {
								type : 'default'
							}
						}
					},
					data : []
				}, {
					name : 'MEM',
					type : 'line',
					symbol : 'none',
					itemStyle : {
						normal : {
							color : 'red',
							areaStyle : {
								type : 'default'
							}
						}
					},
					data : []
				} ]
			};
			//加载数据  
			showJCChart(myChart, option, "jmx/showJC.action", 30);
			$("#tiemfresh").on("click", function() {
				showJCChart(myChart, option, "jmx/showJC.action", 30);
			});
		});
	</script>
</body>
<script src="<%=basePath %>js/DatePicker/WdatePicker.js" type="text/javascript"></script>
</html>










