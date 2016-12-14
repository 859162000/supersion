<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>ShowRptChart</title>
		<meta charset="utf-8">
		<base href="<%=basePath %>"></base>
	    <script src="js/jquery/jquery.1.7.js" type="text/javascript"></script>
		<script src="js/jquery/jquery-ui.min.js" type="text/javascript"></script>
		<script src="js/ECharts/echarts.js"></script>
		<script type="text/javascript">
			var myChart ;
			$(document).ready(function(){
				$(window).resize(function() {
					myChart.resize();
				  });
			});
		</script>
    </head>
	<body>
	<div id="main" style="height:<s:property value="serviceResult.height"/> "></div>
	  <script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: '<%=basePath %>/js/ECharts'
            }
        });
         // 使用
        require(
            [
                'echarts',
                 <s:iterator value="serviceResult.switchCharts" var="chart">
			  	'echarts/chart/<s:property value="chart"/>',
	         	 </s:iterator> 
	         	 'echarts/chart/<s:property value="serviceResult.chartType"/>'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                myChart = ec.init(document.getElementById('main')); 
                
                var option =<s:property value="serviceResult.option" escape="false"/> ;
        		
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
            
        );
         
    </script>	
	</body>
</html>











