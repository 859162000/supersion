package report.service.imps;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import report.dao.imps.ItemDataCacheManger;
import report.dto.BindDetailFieldInfo;
import report.dto.InstRptBaseInfo;
import report.dto.ItemBindInfo;
import report.dto.ItemDataType;
import report.dto.ItemInfo;
import report.dto.RptSubmitInfo;
import report.dto.SimpleItemData;
import report.dto.TaskRptInst;
import report.helper.DoubleUtil;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;
import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.show.ShowContext;


// 主要功能：根据当前DTO(rptTaskInst)及ID 取得报表所需明细和指标数据
public class ShowReportService extends BaseObjectDaoResultService{

	private boolean onlyShow;
	private StringBuilder template;
	// 取TaskRptInst的日期，机构作为参数
	private String strDtDate;
	private InstInfo instInfo;
	private String strRptpFile;
	private boolean canEdit;
	private String strFreq;
	private String rptCode;
	private boolean isNegativeShowRed;
	private boolean isNegativeShowMinus;
	private String strTaskRptInstID;
	private String path;
	private String basePath;
	private ItemTemplate itemTemplate;
	private ItemDataCacheManger cacheManger;
	
	public ShowReportService()
	{
		template=new StringBuilder();
	}
	
	public void setOnlyShow(boolean onlyShow) {
		this.onlyShow = onlyShow;
	}

	public boolean isOnlyShow() {
		return onlyShow;
	}
	
	private boolean getTemplateContent(TaskRptInst taskRptInst) throws Exception
	{
		// 读取模板文件
		String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
		String strRootPath = ServletActionContext.getServletContext().getRealPath("/");
		//StringBuffer template = new StringBuffer();
/*		Map<String,String>  showParam=ShowContext.getInstance().getShowEntityMap().get("reportShowPrePeriodDataParam");
		String isShow=showParam.get("isShow");
		String suitcode=showParam.get("suitCode");*/
		
		Boolean isShowFlag = false;
		String arraySuitCode[] = null;
		SystemParam systemParam = HelpTool.getSystemParam("IsShowPrePeriodData");
		if(systemParam!=null && systemParam.getStrEnable().equals("1"))
		{
			isShowFlag=Boolean.valueOf(systemParam.getStrParamValue().split("\\|")[0].trim().toLowerCase());
			
			if(isShowFlag)
			{
				arraySuitCode = systemParam.getStrParamValue().split("\\|");
			}
		}
		
		
		String path=strRootPath + strTmpRootPath + "/" + strRptpFile + ".htm";
		File file = new File(path);
		if(!file.exists()){
			return false;
		}
		
		/*if(!StringUtils.isBlank(isShow)&&"1".equals(isShow))
		{
			if(taskRptInst.getTaskFlow().getSuit().getStrSuitCode().equals(suitcode))
			{
				
				path=strRootPath + strTmpRootPath + "/" + strRptpFile +ItemTemplate.SHOW_PRE_PERIOD_FILE_FLAG+ ".htm";
			    File showPrePeriodFile=new File(path);
			    if(showPrePeriodFile.exists())
			    {
			    	file=showPrePeriodFile;
			    }
			}
		}*/
		
		if(isShowFlag)
		{
			if(SmallTools.arrayUtilsContains(arraySuitCode, taskRptInst.getTaskFlow().getSuit().getStrSuitCode()))
			{
				path=strRootPath + strTmpRootPath + "/" + strRptpFile +ItemTemplate.SHOW_PRE_PERIOD_FILE_FLAG+ ".htm";
			    File showPrePeriodFile=new File(path);
			    if(showPrePeriodFile.exists())
			    {
			    	file=showPrePeriodFile;
			    }
			}
		}

		// 先去除方括号中的<>中内容
		String strIn = HelpTool.readToString(file, "GB2312");
		//去除 vml
		if(strIn.indexOf(strRptpFile + ".files") != -1){
			strIn = strIn.replaceAll(strRptpFile + ".files", basePath+"Download/formwork/"+strRptpFile + ".files");
		}
		StringBuffer sb = new StringBuffer(strIn);
		int staPos = sb.indexOf("<!--[if gte vml 1]>");
		int endPos = -1;
		while(staPos != -1){
			endPos = sb.indexOf("<![endif]-->",staPos);
			if(endPos ==-1)
				break;
			sb.delete(staPos, endPos+12);
			
			endPos = sb.indexOf("<![if !vml]>",staPos);
			if(endPos ==-1)
				break;
			sb.delete(endPos, endPos+12);
			
			endPos = sb.indexOf("<![endif]>",endPos);
			if(endPos ==-1)
				break;
			sb.delete(endPos, endPos+10);
			
			staPos = sb.indexOf("<!--[if gte vml 1]>",endPos);
		}
		template.append(sb);
		
		return true;
	}
	
	
	
	
	private String getCheckMsg(ItemBindInfo itemBindInfo,String id) throws Exception
	{
		StringBuilder checkMsgHtml=new StringBuilder();
		checkMsgHtml.append("<input type='hidden'");
		checkMsgHtml.append(" id='");
		checkMsgHtml.append("msg_"+id);
		checkMsgHtml.append("' value='校验结果:<br/>");
		
		String checkMsg=cacheManger.getItemCheckMsg(itemBindInfo.instCode, 
												itemBindInfo.date, 
												itemBindInfo.itemCode,
												itemBindInfo.currency, 
												itemBindInfo.property,
												itemBindInfo.ext1,
												itemBindInfo.ext2,
												itemBindInfo.freq);
		if(StringUtils.isBlank(checkMsg))
		{
			return "";
		}
		else
		{
			checkMsgHtml.append(checkMsg.replaceAll("\r\n","<br/>"));
			checkMsgHtml.append("' />");
			return checkMsgHtml.toString();
		}
	
	}
	private String getValToHtml(ItemBindInfo itemBindInfo,String val,Integer rptUnit,Integer rptPrecise) throws Exception
	{
		SimpleItemData itemData = itemTemplate.getItemData(itemBindInfo);
		ItemInfo item=cacheManger.getItem(itemBindInfo.itemCode);
		StringBuilder  htmlBuilder=new StringBuilder();
		StringBuilder nameBuilder=new StringBuilder("ItemCode_");
		nameBuilder.append(itemBindInfo.itemCode);
		nameBuilder.append("Property_");
		nameBuilder.append(itemBindInfo.property);
		nameBuilder.append("Currency_");
		nameBuilder.append(itemBindInfo.currency);
		nameBuilder.append("Date_");
		nameBuilder.append(itemBindInfo.date.replaceAll("-", ""));
		nameBuilder.append("InstCode_");
		nameBuilder.append(itemBindInfo.instCode);
		nameBuilder.append("ext1_");
		nameBuilder.append(itemBindInfo.ext1);
		nameBuilder.append("ext2_");
		nameBuilder.append(itemBindInfo.ext2);
		nameBuilder.append("freq_");
		nameBuilder.append(itemBindInfo.freq);
		
		String strCheckMsg=getCheckMsg(itemBindInfo,nameBuilder.toString());
		boolean isDifference = false;
		if(StringUtils.isNotBlank(itemData.value1))
		{
//			if(!itemData.value1.equals(itemData.StrValue))
//				isDifference = true;
			
			if(ItemDataType.Amount.toString().equals(item.getStrDataType()))
			{
				try
				{
					if(!DoubleUtil.format(Double.parseDouble(itemData.StrValue)/rptUnit, rptPrecise).equals(DoubleUtil.format(Double.parseDouble(itemData.value1)/rptUnit, rptPrecise)))
						isDifference = true;
				}
				catch(Exception ex)
				{}
			}
			else if(!itemData.value1.equals(itemData.StrValue))
				isDifference = true;
		}
		
		StringBuilder styleBuilder=new StringBuilder(" style='");
		if(!StringUtils.isBlank(strCheckMsg))
		{
			styleBuilder.append("background-color:pink;");
		}else if(isDifference){
			styleBuilder.append("background-color:#EEC900;");
		}else if(!StringUtils.isBlank(itemBindInfo.color)){
			styleBuilder.append("background-color:"+itemBindInfo.color+";");
		}
		
		if(ItemDataType.Amount.toString().equals(item.getStrDataType()) // 金额
				||ItemDataType.Num.toString().equals(item.getStrDataType()))
		{
			if(isNegativeShowRed&& val.startsWith("-"))
			{
				styleBuilder.append("color:red;");
			}
		}
		styleBuilder.append("'");
		
		StringBuilder valueBuilder=new StringBuilder();
		if(isNegativeShowMinus==false &&(ItemDataType.Amount.toString().equals(item.getStrDataType()) // 金额
				||ItemDataType.Num.toString().equals(item.getStrDataType())))
		{
			valueBuilder.append("<input type='hidden'  name='");
			valueBuilder.append(nameBuilder);
			valueBuilder.append("' id='");
			valueBuilder.append(nameBuilder);
			valueBuilder.append("' value='");
			valueBuilder.append(val);
			valueBuilder.append("'/>");
			
			
			htmlBuilder.append("<input type='text'  name='display_");
			htmlBuilder.append(nameBuilder);
			htmlBuilder.append("'");
			
			htmlBuilder.append(" value='");
			htmlBuilder.append(val.replaceFirst("-",""));
			htmlBuilder.append("'");
			htmlBuilder.append(" onchange=setValue('");
			htmlBuilder.append(nameBuilder);
			htmlBuilder.append("',"+isNegativeShowMinus);
			htmlBuilder.append(","+isNegativeShowRed);
			htmlBuilder.append(")");
			htmlBuilder.append(" id='display_");
			
			htmlBuilder.append(nameBuilder);
			htmlBuilder.append("'");
		}
		else
		{
			htmlBuilder.append("<input type='text'  name='");
			htmlBuilder.append(nameBuilder);
			htmlBuilder.append("'");
			
			htmlBuilder.append(" value='");
			htmlBuilder.append(val);
			htmlBuilder.append("'");
			htmlBuilder.append(" id='");
			
			htmlBuilder.append(nameBuilder);
			htmlBuilder.append("'");
		}
		htmlBuilder.append(" ondblclick=ShowAlertById('msg_");
		htmlBuilder.append(nameBuilder);
		htmlBuilder.append("')");
		
		String css_class="";
		
		if(!"1".equals(itemBindInfo.editable))
		{
			htmlBuilder.append(" readonly='readonly'");
		}
		if(StringUtils.isNotBlank(item.getRemark())){
			css_class += "simpletooltip ";
			styleBuilder.append(" title='"+item.getRemark()+"' ");
		}
		if(isDifference){
			css_class +="powertip ";
			
			if(ItemDataType.Amount.toString().equals(item.getStrDataType()))
			{
				BigDecimal b = new BigDecimal(itemData.value1);
				b = b.divide(new BigDecimal(rptUnit));
				String temp = DoubleUtil.format(TypeParse.parseDouble(b.toString()), rptPrecise);
				styleBuilder.append(" msgTitle='系统计算出的原始值为："+temp+"'");
			}	
			else
				styleBuilder.append(" msgTitle='系统计算出的原始值为："+itemData.value1+"'");
		}
		if(StringUtils.isNotBlank(css_class)){
			styleBuilder.append(" class='"+css_class+"' ");
		}
		htmlBuilder.append(styleBuilder);
		htmlBuilder.append("/>");
		htmlBuilder.append(valueBuilder);
		//htmlBuilder.append("<input type='button' value='数据追查'/>");
		htmlBuilder.append(strCheckMsg);
		return htmlBuilder.toString();
	}
	
	
	private void parseItemData(Integer rptUnit,Integer rptPrecise) throws Exception
	{
		// 解析模板中的指标，并设置值
		// 指标格式:[指标][ItemCode;Currency;Property;date;inst;ext1;ext2]
		int itenKeyLen=ItemTemplate.Start.length();
		int startPos = template.indexOf(ItemTemplate.Start);
		int endPos=-1;
		while(startPos > -1) {
			endPos = template.indexOf(ItemTemplate.ContentEnd, startPos + itenKeyLen);
			String strItem = template.substring(startPos, endPos + 1);
			String strSub = getStrIn(strItem);
			strItem = deleteStrIn(strItem);
			ItemBindInfo itemBindInfo=itemTemplate.getItemBindInfo(strItem,instInfo.getStrInstCode(),strDtDate,strFreq);
			String val=itemTemplate.getItemVal(itemBindInfo);
			String valHtml=getValToHtml(itemBindInfo,val,rptUnit,rptPrecise);
			template.replace(startPos,endPos+1, valHtml + strSub);
			startPos = template.indexOf(ItemTemplate.Start, startPos+valHtml.length()+strSub.length());
		}
	}
	
	private String getDetailLine(String line)
	{
		String leftSpliter=ItemTemplate.ContentStart;
		String rightSpliter=ItemTemplate.ContentEnd;
		StringBuilder lineBuilder=new StringBuilder(line);
		String strSpan="<span style='display:none'>";
		int intSpanStart=lineBuilder.indexOf(strSpan);
		if(intSpanStart>-1)
			lineBuilder.replace(intSpanStart,intSpanStart+strSpan.length()+1 , "");
		
		// 处理掉[]之间的所有<>中的内容
		int nextStart = lineBuilder.indexOf(leftSpliter);
		StringBuilder strSub = new StringBuilder();
		while(nextStart > -1) {
			int nStop = lineBuilder.indexOf(rightSpliter, nextStart)+1;
			String strTmp = lineBuilder.substring(nextStart, nStop);
			strSub.append(getStrIn(strTmp));
			strTmp = deleteStrIn(strTmp);
			lineBuilder.replace(nextStart, nStop, strTmp);
			nextStart = lineBuilder.indexOf(leftSpliter, nextStart + strTmp.length());
		}
		lineBuilder.append(strSub);
		return lineBuilder.toString();
	}
	
	
	
	private void parseDetailData() throws Exception
	{
		String strFieldSpliter="[字段]";
		int nPos = template.indexOf(strFieldSpliter);
		while(nPos > -1) { // 字段格式:[字段][表名:TableName;字段：FieldName]
			// 定位出表格的一行
			StringBuilder detailHtml=new StringBuilder();
			String tmp = template.substring(0, nPos);
			int nLineStart = tmp.lastIndexOf("<tr");
			int nLineEnd = template.indexOf("</tr>", nPos) + "</tr>".length();
			String line =getDetailLine(template.substring(nLineStart, nLineEnd));
		    
			List<BindDetailFieldInfo> lstBindFieldInfo=itemTemplate.getDetailBindInfoByHtml(line);
			
			StringBuilder lineBuilder = processDetailField(line,lstBindFieldInfo);
		
			detailHtml.append(lineBuilder);
		
			template.replace(nLineStart, nLineEnd, detailHtml.toString());
			
			// 循环所有字段的行
			nPos = template.indexOf(strFieldSpliter, nLineStart+detailHtml.length());
		}
	}

	
    private String emptyTDContent(String line,List<BindDetailFieldInfo> bindInfo)
    {
    	StringBuilder lineBuilder=new StringBuilder(line);
    	String strTDStart="<td";
    	String strTDEnd="</td>";
    	String greatFlag=">";
    	String tdContent;
    	int lenTDContent;
    	
    	int lenTDStart=strTDStart.length();
    	int lenTDEnd=strTDEnd.length();
    	int startIndex=lineBuilder.indexOf(strTDStart);
    	while(startIndex>-1)
    	{
    		int endIndex=lineBuilder.indexOf(strTDEnd,startIndex+lenTDStart);
    		int greatFlagPos=lineBuilder.indexOf(greatFlag,startIndex+lenTDStart);
    		tdContent=lineBuilder.substring(greatFlagPos+1, endIndex);
    		lenTDContent=tdContent.length();
    		if(!tdContent.contains(ItemTemplate.dtoNameSpliter)&&
    				!StringUtils.isBlank(tdContent))
    		{
    		   	lineBuilder.replace(greatFlagPos+1, endIndex, "");
    		   	for(BindDetailFieldInfo field:bindInfo)
    		   	{
    		   		if(field.startIndex>endIndex+lenTDEnd)
    		   		{
    		   			field.startIndex-=lenTDContent;
    		   			field.endIndex-=lenTDContent;
    		   		}
    		   	}
    		}
    		startIndex=lineBuilder.indexOf(strTDStart,endIndex);
    		
    	}
    	return lineBuilder.toString();
    }
	private StringBuilder processDetailField(String line,List<BindDetailFieldInfo> bindInfo) throws Exception {
		
		int maxrow =itemTemplate.getDetailMaxRow();
		//int cellStartPos=lineBuilder.indexOf(strFieldSpliter);
		int curRow=0;
		
		int startIndex=-1;
		int endIndex=-1;
		int subIndex=0;
	
		
		String fieldVal="";
		
		List<BindDetailFieldInfo> firstLineBindInfo = copyBindInfo(bindInfo);
		List<BindDetailFieldInfo> curBindInfo;
		String curLine;
		
		
		String newLine=emptyTDContent(line,bindInfo);
		StringBuilder allLineBuilder=new StringBuilder();
		for(;curRow<maxrow;curRow++)
		{
			subIndex=0;
			startIndex=-1;
			endIndex=-1;
			if(curRow==0)
			{
				curLine=line;
				curBindInfo=firstLineBindInfo;
			}
			else
			{
				curLine=newLine;
				curBindInfo=bindInfo;
			}
			
			StringBuilder lineBuilder=new StringBuilder(curLine);
			for(BindDetailFieldInfo field:curBindInfo)
			{
				fieldVal="";
				startIndex=field.startIndex-subIndex;
				endIndex=field.endIndex-subIndex;
				
			    fieldVal=itemTemplate.getDetailFieldVal(field,curRow);
				lineBuilder.replace(startIndex, endIndex, fieldVal);
				subIndex+=field.endIndex-field.startIndex-fieldVal.length();
				
			}
		
			allLineBuilder.append(lineBuilder);
			
		}
		
		return allLineBuilder;
	}

	private List<BindDetailFieldInfo> copyBindInfo(
			List<BindDetailFieldInfo> bindInfo) {
		List<BindDetailFieldInfo> firstLineBindInfo=new ArrayList<BindDetailFieldInfo>();
		for(BindDetailFieldInfo field:bindInfo)
		{
			firstLineBindInfo.add(new BindDetailFieldInfo(field.dtoName,field.fieldName,field.startIndex,field.endIndex));
			
		}
		return firstLineBindInfo;
	}
	
	private String getParamToHtml()
	{
		StringBuilder htmlParam=new StringBuilder();
		htmlParam.append("<input type='hidden' id='strInstCode' name='strInstCode' ");
		htmlParam.append(" value='");
		htmlParam.append(instInfo.getStrInstCode());
		htmlParam.append("'/>");		
		
		htmlParam.append("<input type='hidden' id='strRptCode' name='strRptCode' ");
		htmlParam.append(" value='");
		htmlParam.append(rptCode);
		htmlParam.append("'/>");
		
		htmlParam.append("<input type='hidden' id='dtDate' name='dtDate' ");
		htmlParam.append(" value='");
		htmlParam.append(strDtDate);
		htmlParam.append("'/>");
		
		htmlParam.append("<input type='hidden' id='strTaskRptInstID' name='strTaskRptInstID' ");
		htmlParam.append(" value='");
		htmlParam.append(strTaskRptInstID);
		htmlParam.append("'/>");
		
		htmlParam.append("<input type='hidden' id='strFreq' name='strFreq' ");
		htmlParam.append(" value='");
		htmlParam.append(strFreq);
		htmlParam.append("'/>");
		//http://transino2-pc:8080/DevelopmentFramework2/report/ShowReport-report.dto.TaskRptInst.action?id=402880484ff8e5da0150025ef697003f
		//framework/TaskRptInstShowListForTree-report.dto.TaskRptInst.action?id=402880484ff8e5da0150025ef697003f&type=report.dto.TaskFlow,coresystem.dto.InstInfo&windowId=FrameworkTaskRptInst
		htmlParam.append("<input type='hidden' id='strOldAction' name='strOldAction' ");
		htmlParam.append(" value='");
		htmlParam.append(basePath + "framework/TaskRptInstShowListForTree-report.dto.TaskRptInst.action?id=" + RequestManager.getLevelIdValue()+"&type=report.dto.TaskFlow,coresystem.dto.InstInfo&windowId=FrameworkTaskRptInst");
		htmlParam.append("'/>");
		 
		htmlParam.append("<input type='hidden' id='submitItemsAction' ");
		htmlParam.append(" value='");
		htmlParam.append(basePath + "report/SubmitItems.action");
		htmlParam.append("'/>");
		
		htmlParam.append("<input type='hidden' id='checkAction' ");
		htmlParam.append(" value='");
		htmlParam.append(basePath + "report/ReportCheckBack-report.dto.TaskRptInst.action?id="+strTaskRptInstID);
		htmlParam.append("'/>");
		
		htmlParam.append("<input type='hidden' id='calcAction' ");
		htmlParam.append(" value='");
		htmlParam.append(basePath + "report/ReportMergeBack-report.dto.TaskRptInst.action?isRebuild=N&id="+strTaskRptInstID);
		htmlParam.append("'/>");
		
		htmlParam.append("<input type='hidden' id='itemDataTrace' ");
		htmlParam.append(" value='");
		htmlParam.append(basePath + "report/ItemDataTrace.action?dataInfo=");
		htmlParam.append("'/>");
		htmlParam.append("<input type='hidden' id='repMark' ");
		htmlParam.append(" value='");
		htmlParam.append(basePath + "report/RepMark.action?strRptCode=");
		htmlParam.append("'/>");
		htmlParam.append("<input type='hidden' id='sumCheckAction' ");
		htmlParam.append(" value='");
		htmlParam.append(basePath + "report/ReportSumCheckBack-report.dto.TaskRptInst.action?id="+strTaskRptInstID);
		htmlParam.append("'/>");
		
		htmlParam.append("<input type='hidden' id='rebuildDataAction' ");
		htmlParam.append(" value='");
		htmlParam.append(basePath + "report/ReportMergeBack-report.dto.TaskRptInst.action?id="+strTaskRptInstID);
		htmlParam.append("'/>");
		
		return htmlParam.toString();
		
	}
	private String getOpBtn()
	{
		StringBuilder opBtnHtmlBuilder=new StringBuilder();
		//opBtnHtmlBuilder.append("<input type='button' name='back' value='返回' class='OpBtn' onclick='javascript:window.close();'/>"); // history.go(-1)
		if(!onlyShow)
			opBtnHtmlBuilder.append("<input type='button' name='backfirst' value='返回' class='OpBtn' onclick='up()'/>"); // history.go(-1)
		if(canEdit&&!onlyShow)
		{
			
			opBtnHtmlBuilder.append("<input type='button' name='save' value='保存修改' class='OpBtn' onclick='submitDirtyItems()'/>"); 
			opBtnHtmlBuilder.append("<input type='reset' name='reset' value='取消修改' class='OpBtn' />"); 
			opBtnHtmlBuilder.append("<input type='button' name='merge' value='数据计算' class='OpBtn' onclick='submitCalc()'/>"); 
			opBtnHtmlBuilder.append("<input type='button' name='check' value='数据校验' class='OpBtn' onclick='submitCheck()'/>");
			opBtnHtmlBuilder.append("<input type='button' name='sumCheck' value='总分校验' class='OpBtn' onclick='submitSumCheck()'/>");
			opBtnHtmlBuilder.append("<input type='button' name='datahistory' value='历史对比' class='OpBtn' onclick='dataHistory()'/>");
			opBtnHtmlBuilder.append("<input type='button' name='rebuilddata' value='重新生成数据' class='OpBtn' onclick='rebuildData()'/>");
		}
		/*else
		{
			//opBtnHtmlBuilder.append("<span>已提交或审核通过报表不可修改、计算及校验</span>");
		}*/
		opBtnHtmlBuilder.append("<input type='button' name='datatrace' value='数据追查' class='OpBtn' onclick='dataTrace()'/>");
		opBtnHtmlBuilder.append("<input type='button' name='datatrace' value='填报说明' class='OpBtn' onclick='repMarkInfo()'/>");
		opBtnHtmlBuilder.append("");
		return opBtnHtmlBuilder.toString();
	}
	private void addJs()
	{
	    StringBuilder sbJs=new StringBuilder("<title>报表填报</title>");
	    sbJs.append("<link href='"+basePath+"css/ShowReport.css' rel='stylesheet' type='text/css' />");
	    sbJs.append("<link rel='stylesheet' type='text/css' href='"+basePath+"css/themes/default/easyui.css'>");
	    sbJs.append("<link rel='stylesheet' type='text/css' href='"+basePath+"css/themes/themes/icon.css'>");
	    sbJs.append("<link rel='stylesheet' type='text/css' href='"+basePath+"js/powertip/powertip-yellow.min.css'>");
	    sbJs.append("<link rel='stylesheet' type='text/css' href='"+basePath+"js/simpletooltip/simpletooltip.min.css' media='screen'>");
	    sbJs.append("<script type='text/javascript' src='" + basePath + "js/Comml.js'></script>");
	    sbJs.append("<script type='text/javascript' src='" + basePath + "js/jquery/jquery.1.7.js'></script>");
	    sbJs.append("<script type='text/javascript' src='" + basePath + "js/popup.js'></script>");
	    sbJs.append("<script type='text/javascript' src='" + basePath + "js/showReport.js'></script>");
	    sbJs.append("<script type='text/javascript' src='"+basePath+"js/easyui/easyloader.js'></script>");
	    sbJs.append("<script type='text/javascript' src='"+basePath+"js/powertip/powertip.min.js'></script>");
	    sbJs.append("<script type='text/javascript' src='"+basePath+"js/simpletooltip/simpletooltip.min.js'></script>\n");
	    sbJs.append(css());
	    sbJs.append(js());
	    sbJs.append("</head>");
	    replaceAll(template,"</head>",sbJs.toString());
	}
	
	private StringBuffer css(){
		StringBuffer sb = new StringBuffer();
		sb.append("<style>");
		sb.append("table tr td input{margin:0;padding:0;width:98%; border:0px; height:95%; text-align:center;}");
		sb.append("</style>");
		return sb;
	}
	
	private StringBuffer js() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("		$(document).ready(function() {").append("\n");
		
		sb.append("			$('#form1 table tr td input[type=text]').each(function(){$(this).parents('td').css('background-color',$(this).css('background-color'));});");
		sb.append("\n");
		sb.append("			$('.simpletooltip').simpletooltip({").append("\n");
		sb.append("      		position: 'bottom-right',").append("\n");
		sb.append("          	color: '#4B0082',").append("\n");
		sb.append("          	border_color: '#8C8C8C',").append("\n");
		sb.append("          	background_color: '#7FFFD4',").append("\n");
		sb.append("         	arrow_width: 0,").append("\n");
		sb.append("         	border_width: 1").append("\n");
		sb.append("			});		");
		sb.append("\n");
		sb.append("			$('.powertip').on('click', function(evt) {").append("\n");
		sb.append("				if (!$(this).data('powertip')) {").append("\n");
		sb.append("					$(this)").append("\n");
		sb.append("						.data('powertip', $(this).attr('msgTitle'))").append("\n");
		sb.append("						.powerTip({").append("\n");
		sb.append("							manual: true").append("\n");
		sb.append("						});").append("\n");
		sb.append("					}").append("\n");
		sb.append("				$(this).powerTip('show', evt);").append("\n");
		sb.append("			}).on('mouseleave', function() {").append("\n");
		sb.append("				$(this).powerTip('hide');").append("\n");
		sb.append("			});	").append("\n");
		sb.append("\n");
		sb.append("		});		");
		
		sb.append("</script>");
		return sb;
	}
	
	private void replaceAll(StringBuilder sbTem,String oldStr,String newStr)
	{
		int start=sbTem.indexOf(oldStr);
		int oldStrLen=oldStr.length();
		int newStrLen=newStr.length();
		while(start>-1)
		{
			sbTem.replace(start, start+oldStrLen,newStr );
			start=sbTem.indexOf(oldStr,start+newStrLen);
		}
	}
	
	private void processHeaderAndFooter(TaskRptInst taskRptInst) throws Exception
	{
		String dateText = new SimpleDateFormat("yyyy年M月d日").format( new SimpleDateFormat("yyyy-MM-dd").parse(strDtDate));
		IParamObjectResultExecute  dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Class.forName("report.dto.RptSubmitInfo"));
		detachedCriteria.add(Restrictions.eq("taskRptInstId", strTaskRptInstID));
		List<RptSubmitInfo> rptSubmitInfoList = (List<RptSubmitInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		RptSubmitInfo rptSubmitInfo;
		if(rptSubmitInfoList.size()>0)
		{
			rptSubmitInfo=rptSubmitInfoList.get(0);
		}
		else
		{
			
			rptSubmitInfo=new RptSubmitInfo();
			rptSubmitInfo.setTaskRptInstId(strTaskRptInstID);
			rptSubmitInfo.setStrBankName(instInfo.getStrInstName());
			rptSubmitInfo.setStrRptDate(dateText);
			//机构上报报表基础配置信息  Property.forName("taskFlow.id").eq(taskID)
			DetachedCriteria find_InstRptBaseInfo=DetachedCriteria.forClass(Class.forName("report.dto.InstRptBaseInfo"));
			find_InstRptBaseInfo.add(Property.forName("instInfo.strInstCode").eq(taskRptInst.getInstInfo().getStrInstCode()));
			find_InstRptBaseInfo.add(Property.forName("rptInfo.strRptCode").eq(taskRptInst.getRptInfo().getStrRptCode()));
			List<InstRptBaseInfo> instRptBaseInfoList = (List<InstRptBaseInfo>)dao.paramObjectResultExecute(new Object[]{find_InstRptBaseInfo, null});
			if(instRptBaseInfoList != null && instRptBaseInfoList.size()>0){
				InstRptBaseInfo instRptBaseInfo = instRptBaseInfoList.get(0);
				rptSubmitInfo.setStrReporter(instRptBaseInfo.getReporter());
				rptSubmitInfo.setStrChecker(instRptBaseInfo.getChecker());
				rptSubmitInfo.setStrManager(instRptBaseInfo.getManager());
			}
		}
		
		replaceAll(template, ItemTemplate.BankName, 
				"<input type='hidden' name='" + ItemTemplate.BankName + "' value='" + rptSubmitInfo.getStrBankName() + "'></input>" + rptSubmitInfo.getStrBankName());
		replaceAll(template, ItemTemplate.RptDate, 
				"<input type='hidden' name='" + ItemTemplate.RptDate + "' value='" + strDtDate + "'></input>" + dateText);
		replaceAll(template, ItemTemplate.Reporter, 
				"<input type='hidden' name='" + ItemTemplate.Reporter + "' value='" + rptSubmitInfo.getStrReporter() + "'></input>" + rptSubmitInfo.getStrReporter());
		replaceAll(template, ItemTemplate.Checker,
				"<input type='hidden' name='" + ItemTemplate.Checker + "' value='" + rptSubmitInfo.getStrChecker() + "'></input>" + rptSubmitInfo.getStrChecker());
		replaceAll(template, ItemTemplate.Manager, 
				"<input type='hidden' name='" + ItemTemplate.Manager + "' value='" + rptSubmitInfo.getStrManager() + "'></input>" + rptSubmitInfo.getStrManager());
		
	}
	
	private TaskRptInst getRptInfo() throws Exception
	{
		cacheManger=ItemDataCacheManger.getInsance();
		// 取当前TaskRptInst
		Object oldID = RequestManager.getId();
		RequestManager.setId(RequestManager.getLevelIdValue());
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskRptInst taskRptInst = (TaskRptInst)singleObjectFindByIdDao.paramObjectResultExecute(null);
		RequestManager.setId(oldID);
		if(taskRptInst != null)
		{
			// 取TaskRptInst的日期，机构作为参数
			strDtDate =TypeParse.parseString(taskRptInst.getTaskFlow().getDtTaskDate(),"yyyy-MM-dd");
			instInfo = taskRptInst.getInstInfo();
			strRptpFile = taskRptInst.getRptInfo().getStrRptPath();
			strFreq=taskRptInst.getTaskFlow().getStrFreq();
			rptCode=taskRptInst.getRptInfo().getStrRptCode();
			strTaskRptInstID=taskRptInst.getId();
			String negativeMinus=taskRptInst.getRptInfo().getStrNegativeDisplayMinus();
			if(!StringUtils.isBlank(negativeMinus)&& !negativeMinus.equals("1"))
			{
				isNegativeShowMinus=false;
			}
			else
				isNegativeShowMinus=true;
			
			String negativeRedDisplay=taskRptInst.getRptInfo().getStrNegativeRedDisplay();
			
			if(!StringUtils.isBlank(negativeRedDisplay) && negativeRedDisplay.equals("1"))
			{
				isNegativeShowRed=true;
			}
			else
				isNegativeShowRed=false;
			
			if("2".equals(taskRptInst.getStrAllowState())||
				"2".equals(taskRptInst.getSubmitStatus()))
			{
				this.canEdit=false;
			}
			else
			{
				this.canEdit=true;
			}

			itemTemplate = new ItemTemplate(taskRptInst);
			path = ServletActionContext.getRequest().getContextPath();
			basePath = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + path + "/";
			
		}
		return taskRptInst;
	}
	
	
	private void processHtmlBodyHead()
	{
		StringBuilder htmlBuilder=new StringBuilder();
		htmlBuilder.append("<body class='ie6Body'>");
		htmlBuilder.append("<form id='form1' method='post'>");
		htmlBuilder.append(getParamToHtml());
		htmlBuilder.append("<center><div id='headNav' class='OpBtnTop'>");
		htmlBuilder.append(getOpBtn());
		htmlBuilder.append("</div><div style='height:30px;'>&nbsp;</div></center>");
		//replaceAll(template,"<body>",htmlBuilder.toString());
		replaceAll(template,"<body[^<]*>",htmlBuilder.toString());
		int start = template.indexOf("<body");
		int end = template.indexOf(">",start);
		if(start>-1){
			template.replace(start, end+1, htmlBuilder.toString());
		}
		//template.insert(0, "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
		
		
	}
	private void processHtmlBodyTail()
	{
		StringBuilder htmlBuilder=new StringBuilder();
	/*	htmlBuilder.append("<p><center><div id='bottomNav' class='OpBtnBottom'>");
		htmlBuilder.append(getOpBtn());
		
		htmlBuilder.append("</div></center></p>");*/
		
	
		htmlBuilder.append("</form></body>");
		replaceAll(template,"</body>",htmlBuilder.toString());
	}
	
	@Override
	public void initSuccessResult() throws Exception {
		// 根据当前DTO取出查询条件并组装DetachedCriteria
		HttpServletResponse response = ServletActionContext.getResponse();
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
		try
		{
			TaskRptInst taskRptInst=getRptInfo();
			if(taskRptInst!=null)
			{
				if(getTemplateContent(taskRptInst))
				{
					Integer rptUnit = taskRptInst.getRptInfo().getIntRptUnit();
					Integer rptPrecise = taskRptInst.getRptInfo().getIntPrecise();
					
					//加上html的解析头
					template.insert(0,"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">");
					addJs();
					
					processHeaderAndFooter(taskRptInst);
					
					parseItemData(rptUnit,rptPrecise);
					
					parseDetailData();
					
					processHtmlBodyHead();
					
					processHtmlBodyTail();
					
					out.print(template.toString());
					/*for(int i=0; i<template.length(); i+=1024*10) {
						if(i+1024*10 < template.length())
							out.print(template.substring(i, i+1024*10));
						else
							out.print(template.substring(i));
					}*/
					
				}
				else
				{	
					out.print(String.format("<body><h2 style='text-align:center;' >模板文件%s不存在</h2></body>",strRptpFile));
				}
			}
			else
			{	
				out.print("<body><h2 style='text-align:center;' >报表任务不存在，请刷新数据！</h2></body>");
			}
		}
		catch(Exception ex)
		{
			out.print("<body><h2 style='text-align:center;' >系统异常，请联系管理员</h2></body>");
			ExceptionLog.CreateLog(ex);
		}
		finally
		{
			out.close();
		}
		
	}
	
	
	
	// 找出<>中的字符串
	private String getStrIn(String in) {
		int nStart = in.indexOf("<");
		int nEnd = in.indexOf(">");
		String strResult = "";
		while(nStart > -1 && nEnd > -1) {
			strResult += in.subSequence(nStart, nEnd+1);
			nStart = in.indexOf("<", nStart+1);
			nEnd = in.indexOf(">", nEnd+1);
		}
		
		return strResult;
	}
	
	// 删除<>中的字符串
	private String deleteStrIn(String in) {
		int nStart = in.indexOf("<");
		int nEnd = in.indexOf(">");
		String strResult = in.toString();
		while(nStart > -1 && nEnd > -1) {
			strResult = strResult.subSequence(0, nStart) + strResult.substring(nEnd+1);
			nStart = strResult.indexOf("<");
			nEnd = strResult.indexOf(">");
		}
		
		return strResult;
	}
	
}
