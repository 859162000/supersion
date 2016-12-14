package report.service.imps;

import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

import report.dao.imps.ItemDataCacheManger;
import report.dto.CalRule;
import report.dto.CalculationRule;
import report.dto.CurrencyType;
import report.dto.ItemData;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.RptInfo;
import report.dto.RptSubmitInfo;
import report.dto.TaskRptInst;
import report.helper.DoubleUtil;
import report.service.expression.ExpressionContextKey;
import report.service.expression.ExpressionKey;
import report.service.expression.interfaces.IExpressionBuilder;
import report.service.expression.interfaces.IExpressionCalculater;
import report.service.expression.interfaces.IExpressionLog;
import report.service.expression.interfaces.IExpressionLogFormatter;
import report.service.expression.log.ExpressionCalRuleFormatter;
import report.service.expression.parser.ItemExpParamKey;
import report.service.expression.parser.Word;
import coresystem.dto.InstInfo;
import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

public class ItemDataTraceService extends BaseObjectDaoResultService{

	private IParamObjectResultExecute hqlQueryListDao;
	private ItemDataCacheManger itemDataCacheManger;
	
	private Map<String,Object> context;
	private IExpressionCalculater calculater;
	private IExpressionLogFormatter calRuleFormatter;
	private StringBuilder htmlBody;
	private String taskRptInstId;
	private String strInstCode;
	private String strRptCode;
	private String calcRuleName;
	private int sqlC = 1;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
     	HttpServletRequest request=ServletActionContext.getRequest();
		String val=request.getParameter("val").toString();
		taskRptInstId=request.getParameter("strTaskRptInstID").toString();
		strRptCode = request.getParameter("strRptCode").toString();
		strInstCode =request.getParameter("strInstCode").toString();
		String strDate = request.getParameter("dtDate").toString();
		Enumeration enum1 =request.getParameterNames();
		
		IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskRptInst.class);
		detachedCriteria.add(Restrictions.eq("id", taskRptInstId));
    	List<TaskRptInst> listTaskRptInst=(List<TaskRptInst>)defaultLogicDaoDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
    	String taskFreq="";
    	if(listTaskRptInst.size()>0)
		{
    		taskFreq=listTaskRptInst.get(0).getTaskFlow().getStrFreq();
		}
		
		
		while(enum1.hasMoreElements()) {
			String strName = enum1.nextElement().toString();
			String strValue = request.getParameter(strName).toString();
			int startIndex=strValue.indexOf("ItemCode_");
			if(startIndex>-1) { // 指标前缀
				// 解析出指标代码，属性编码
				strName=strValue.substring(startIndex);
				String strItemCode = strName.substring(9, strName.indexOf("Property_"));
				String strProperty = strName.substring(strName.indexOf("Property_") + "Property_".length(), strName.indexOf("Currency_"));
				String strCurrency = strName.substring(strName.indexOf("Currency_") + "Currency_".length(), strName.indexOf("Date_"));
				String strItemDate = strName.substring(strName.indexOf("Date_") + "Date_".length(), strName.indexOf("InstCode_"));
				String strItemInstCode = strName.substring(strName.indexOf("InstCode_") + "InstCode_".length(), strName.indexOf("ext1_"));
				String strExt1 = strName.substring(strName.indexOf("ext1_") + "ext1_".length(), strName.indexOf("ext2_"));
				String strExt2 = strName.substring(strName.indexOf("ext2_") + "ext2_".length(), strName.indexOf("freq_"));
				String strFreq=strName.substring(strName.indexOf("freq_") + "freq_".length());
				
			    Date dtDate=TypeParse.parseDate(strDate);
			   //String strDate=TypeParse.parseString(dtDate, "yyyy-MM-dd");
				initParam(strInstCode, dtDate,taskFreq);
				getChild(true,strDate,strInstCode,strItemCode,
						 strCurrency,strProperty,strExt1,strExt2,strFreq,val);
		    }
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		String path = ServletActionContext.getRequest().getContextPath();
	    String basePath = ServletActionContext.getRequest().getScheme()
        +"://"+ServletActionContext.getRequest().getServerName()+":"
        +ServletActionContext.getRequest().getServerPort()+path+"/";
		response.setContentType("text/html");
		StringBuilder html = new StringBuilder("<html><head><title>数据追查</title>");
		html.append("<link rel='stylesheet' type='text/css' href='"+basePath+"css/themes/default/easyui.css'>");
		html.append("<link rel='stylesheet' type='text/css' href='"+basePath+"css/themes/themes/icon.css'>");
		html.append("<script type='text/javascript' src='" + basePath + "js/jquery/jquery.1.7.js'></script>");
		html.append("<script type='text/javascript' src='"+basePath+"js/easyui/jquery.easyui.min.js'></script>");
		html.append("<script type='text/javascript' src='"+basePath+"js/showReport.js'></script>");
		html.append("</head><body>");
		if(htmlBody.length()>0)
			html.append(htmlBody);
		else
			html.append("<h2 style='text-align:center;' >该指标未设置计算公式</h2>");
		html.append("<form method='post' style='display:none' target='' id='form1' action='"+basePath+"report/RepDetailDownload.action'>");
		html.append("<input type='hidden' name='sql' id='sql' value=''/><input type='hidden' name='calcRuleName' id='calcRuleName' value='"+calcRuleName+"'/><input type='hidden' name='sqlNum' id='sqlNum' value=''/>");
		html.append("</form>");
		html.append("</body>");
		html.append("</html>");
		ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
		out.print(html.toString());
		out.close();

	}

	private void initParam(String strItemInstCode, Date dtDate,String rptFreq) throws Exception {
		context=new HashMap<String,Object>();

		/*20161010 Liaoxl repair 为应对指标map使用的会计表中日期字段为字符串类型的情况，需要把DTDATE_PARAM_KEY参数转换为指导格式的日期字符串,
		 * 指定的格式从系统参数表(SystemParam)中获取，如果获取不到，就采用系统原来默认的处理方式
		*/	
		
//		context.put(ExpressionContextKey.DTDATE_PARAM_KEY,dtDate);
		
		SystemParam systemParam = HelpTool.getSystemParam("MapTableDateFormat");
		
		if(systemParam!=null && systemParam.getStrEnable().equals("1"))
			context.put(ExpressionContextKey.DTDATE_PARAM_KEY,TypeParse.parseString(dtDate, systemParam.getStrParamValue()));
		else
			context.put(ExpressionContextKey.DTDATE_PARAM_KEY,TypeParse.parseString(dtDate, "yyyy-MM-dd"));

		//20161010 Liaoxl repair end
		
		context.put(ExpressionContextKey.INSTCODE_PARAM_KEY,strItemInstCode);
		context.put(ExpressionContextKey.RPT_FREQ_PARAM_KEY, rptFreq);
		hqlQueryListDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("hqlQueryListDao");
		calculater=(IExpressionCalculater)FrameworkFactory.CreateBean("jsExpressionCalculater");
		calculater.setContext(context);
		calRuleFormatter=new ExpressionCalRuleFormatter();
		htmlBody=new StringBuilder();
	}
	
	
	
	private String builderHtml(boolean isRoot,String root,List<Word> lstWord,
								String  strDate,String strItemInstCode) throws Exception
	{
		if(isRoot)
		{
			htmlBody.append("<ul id='tt' class='easyui-tree' data-options='animate:true,lines:true'>");
			htmlBody.append("<li>");
			htmlBody.append("<span>");
			htmlBody.append(root);
			htmlBody.append("</span>");
		}
		
		
		if(lstWord.size()>0)
		{
			htmlBody.append("<ul>");
			for(Word w:lstWord)
			{
				htmlBody.append("<li>");
				if(ExpressionKey.ITEM.equals(w.wordType))
				{
					htmlBody.append("<span>");
					htmlBody.append(w.description);
					htmlBody.append("</span>");
					Map<String,Object> runtimeParam=w.getRuntimeParam();
					String strItemCode=runtimeParam.get(ItemExpParamKey.ITEMCODE_NAME).toString();
					String strCurrency=runtimeParam.get(ItemExpParamKey.CURRENCY_NAME).toString();
					String strProperty=runtimeParam.get(ItemExpParamKey.PROPERTY_NAME).toString();
					String strExt1=runtimeParam.get(ItemExpParamKey.EXT1_NAME).toString();
					String strExt2=runtimeParam.get(ItemExpParamKey.EXT2_NAME).toString();
					String strFreq=runtimeParam.get(ItemExpParamKey.FREQ_NAME).toString();
					String curVal=w.value;
					getChild(false,strDate,strItemInstCode,strItemCode,
							 strCurrency,strProperty,strExt1,strExt2,strFreq,curVal);
				}
				else if(ExpressionKey.SQL.equals(w.wordType)){
										
					String sql = w.getRuntimeParam().get("ItemDataTrace").toString();
					Object[] sqlParams = (Object[])w.getRuntimeParam().get("ItemDataTraceParams");
					for(int i = 0;i<sqlParams.length;i++)
					{
						sql = sql.replaceFirst("\\?", "'"+sqlParams[i].toString()+"'");
					}
					
					htmlBody.append("<span>");
					htmlBody.append("<a onclick='javascript:downloadDetail(this);' sqlcId='"+sqlC+"'>");
					htmlBody.append(sql);
					htmlBody.append("</a>");
					htmlBody.append("</span>");
					sqlC++;
				}
				else
				{
					htmlBody.append("<span>");
					htmlBody.append(w.word);
					htmlBody.append("</span>");
				}
				htmlBody.append("</li>");
			}
			
			
			htmlBody.append("</ul>");
		}
		if(isRoot)
		{
			htmlBody.append("</li>");
			htmlBody.append("</ul>");
		}
		
		return htmlBody.toString();
	
	}
	private void getChild(boolean isRoot,String  strDate,String strItemInstCode, String strItemCode,
								String strCurrency,String strProperty,String strExt1,
								String strExt2,String strFreq,String curVal) throws Exception
	{
		
		
		
		
		StringBuilder hql=new StringBuilder();
    	hql.append("select new report.dto.CalRule(");
		hql.append("cr.autoCalculationRuleID,");
		hql.append("cr.strCalculationRuleName,");
		hql.append("cr.itemInfo.strItemCode,");
		hql.append("cr.itemProperty.strPropertyCode,");
		hql.append("cr.currencyType.strCurrencyCode,");
		hql.append("cr.startdate,");
		hql.append("cr.enddate,");
		hql.append("cr.strExtendDimension1,");
		hql.append("cr.strExtendDimension2,");
		hql.append("cr.intOrder,");
		hql.append("cr.strExpression,");
		hql.append("cr.strFreq,");
		hql.append("cr.strCalRoundMethod,");
		hql.append("cr.intPrecise)");
		hql.append(" from CalculationRule cr");
		hql.append(" where ");
		hql.append(String.format(" cr.itemInfo.strItemCode='%s'",strItemCode));
		hql.append(String.format(" and cr.itemProperty.strPropertyCode='%s'",strProperty));
		hql.append(String.format(" and cr.currencyType.strCurrencyCode='%s'",strCurrency));
		
		if(!StringUtils.isBlank(strExt1))
			hql.append(String.format(" and cr.strExtendDimension1='%s'",strExt1));
		
		if(!StringUtils.isBlank(strExt2))
			hql.append(String.format(" and cr.strExtendDimension2='%s'",strExt2));
		
		if(!StringUtils.isBlank(strFreq))
			hql.append(String.format(" and cr.strFreq='%s'",strFreq));
				
		//hql.append(String.format(" and ('%s' >= cr.startdate and '%s' <=cr.enddate)", strDate,strDate));
		hql.append(" and cr.autoCalculationRuleID in(");
		hql.append("select eir.calculationRule.autoCalculationRuleID from ExampleInfoRule eir where eir.calculationExampleInfo.strCalculationName in(");
		hql.append(String.format("select strCalcInst from RptInfo where strRptCode='%s')",strRptCode));
		hql.append(")");
		hql.append(" and (?>=cr.startdate and ?<=cr.enddate) ");
		hql.append(" order by case when (intOrder='' OR intOrder is null) then 0 else intOrder end desc");
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dtDate = sf.parse(strDate);
		
		List<CalRule> objectList = (List<CalRule>)hqlQueryListDao.paramObjectResultExecute(new Object[]{hql.toString(),new Object[]{dtDate,dtDate},null});
				
		if(objectList.size()>0)
		{
			CalRule cr=objectList.get(0);
			if(isRoot)
				calcRuleName = cr.getStrCalculationRuleName();
			String exp=cr.getStrExpression();
			if(!StringUtils.isBlank(exp))
			{
				itemDataCacheManger = ItemDataCacheManger.getInsance();
				ItemInfo itemInfo = itemDataCacheManger.getItem(cr.getStrItemCode());
				
				String express = itemInfo.getStrItemCode()+"#"+itemInfo.getStrDataType()+"#"+cr.getStrExpression();
				
				//String rightVal=calculater.calculate(express).toString();
				calculater.calculate(express).toString();
				
				String strCalRuleFormatter=calRuleFormatter.format(cr, new Object[]{curVal});
				List<Word> lstWord=calculater.getWords();
			
				builderHtml(isRoot,strCalRuleFormatter,lstWord,strDate,strItemInstCode);
			}
			
		}
		
	}
}
