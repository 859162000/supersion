package report.service.imps;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.JoinColumn;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dao.imps.ItemDataCacheManger;
import report.dto.BindDetailFieldInfo;
import report.dto.ItemBindInfo;
import report.dto.ItemDataType;
import report.dto.ItemInfo;

import report.dto.SimpleItemData;
import report.dto.TaskRptInst;
import report.helper.DoubleUtil;

import coresystem.dto.InstInfo;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;

public class ItemTemplate {

	//[指标][代码:A000002;币种:CNY;属性:0001;日期:;机构:;扩展1:;扩展2:;期数:1;可编辑:1;颜色:#00FF00]
	public static final String Start="[指标]";
	public static final String ContentStart="[";
	public static final String ContentEnd="]";
	public static final String SectionSpliter=";";
	public static final String SectionSpliterWithExcel="\n";
	public static final String ValueSpliter=":";
	
	public static final String DetailStart="[字段]";
	public static final String DetailEnd="[表结束]";
	
	
	public static final String Item="代码";
	public static final String Currency="币种";
	public static final String Prop="属性";
	
	public static final String DtDate="日期";
	public static final String Inst="机构";
	public static final String Ext1="扩展1";
	
	public static final String Ext2="扩展2";
	public static final String Period="期数";
	public static final String Editable="可编辑";
	
	public static final String Freq="频度";
	
	public static final String Color="颜色";
	
	//20160623 Liaoxl repair struts中ognl表达式中不支持特殊字符（比如[]）,
//	public static final String BankName="[BankName]";
//	public static final String RptDate="[RptDate]";
//	public static final String Reporter="[lister]";
//	public static final String Checker="[checker]";
//	public static final String Manager="[Manager]";
	public static final String BankName="Transino_BankName";
	public static final String RptDate="Transino_RptDate";
	public static final String Reporter="Transino_Reporter";
	public static final String Checker="Transino_Checker";
	public static final String Manager="Transino_Manager";
	
	public static final String dtoNameSpliter="[字段][表名:";
	public static final String fieldStartFlag="字段:";
	public static final String dtoEndFlag=";";
	public static final String fieldEndFlag="]";
	public static final String NewLine="\n";
	public static final String SHOW_PRE_PERIOD_FILE_FLAG="_showPrePeriod";
	
	
	private  Map<String,List<Object>> curRowDetialObject;
	// 取TaskRptInst的日期，机构作为参数
	private String strDtDate;
	private String strFreq;
	private InstInfo instInfo;
	
	
	private boolean isShowThousand;
	private Integer nRptMaxLine;
	private Integer nRptPrecise = 2; // 精度
	private Integer nRptUnit = 1; // 单位
	private TaskRptInst taskRptInst;
	
	public ItemTemplate(TaskRptInst taskRptInst) throws Exception 
	{
		this.taskRptInst=taskRptInst;
		curRowDetialObject=new HashMap<String,List<Object>>();
		getRptInfo();
	}
	
	private void getRptInfo() throws Exception
	{
		//cacheManger=ItemDataCacheManger.getInsance();
		// 取当前TaskRptInst
		
		
		
		if(taskRptInst == null) 
			throw new Exception("报表不存在");
		
		// 取TaskRptInst的日期，机构作为参数
		strDtDate =TypeParse.parseString(taskRptInst.getTaskFlow().getDtTaskDate(),"yyyy-MM-dd");
		instInfo = taskRptInst.getInstInfo();
		
		nRptMaxLine = taskRptInst.getRptInfo().getIntMaxLine();
		if(nRptMaxLine==null)
			nRptMaxLine=5;
		
		
		strFreq=taskRptInst.getTaskFlow().getStrFreq();
	
		nRptPrecise = 2; // 精度
		if(taskRptInst.getRptInfo().getIntPrecise() != null)
			nRptPrecise = taskRptInst.getRptInfo().getIntPrecise();
		
		if("1".equals(taskRptInst.getRptInfo().getStrIsThousands()))
		{
			isShowThousand=true;
		}
		nRptUnit = 1; // 单位
		if(taskRptInst.getRptInfo().getIntRptUnit() != null)
			nRptUnit = taskRptInst.getRptInfo().getIntRptUnit();
		
	}
	
	public List<BindDetailFieldInfo> getDetailBindInfo(String bindLine,boolean isExcel) throws Exception
	{
		if(isExcel)
		{
			bindLine=processDetailBindInfoFormat(bindLine);
		}
		return getDetailBindInfo(bindLine);
	}
	public  List<BindDetailFieldInfo> getDetailBindInfoWithExcel(Row firstRow)
	{
		List<BindDetailFieldInfo> lstField=new ArrayList<BindDetailFieldInfo>();
		curRowDetialObject.clear();
		for(int k=0; k<firstRow.getLastCellNum(); k++) {
    		Cell c1 = firstRow.getCell(k);
    		if(c1 != null) {
    			try {
    				if(c1.getCellType() == Cell.CELL_TYPE_FORMULA)
					{
						continue;
					}
    				String cellVal= c1.getStringCellValue();
    				if(!StringUtils.isBlank(cellVal))
    				{
    					
    					List<BindDetailFieldInfo> temp=getDetailBindInfo(processDetailBindInfoFormat(cellVal));
    					if(temp.size()>0)
    					{
    						temp.get(0).startIndex=k;
    						temp.get(0).endIndex=k;
    					}
    					lstField.addAll(temp);
    				}
    			} catch(Exception ex) {
    				ExceptionLog.CreateLog(ex);
    			}
    		}
    	}
		return lstField;
	}
	
	private void getDetialDTOData(Set<String> fieldBindInfo) throws Exception
	{
		
		for(String dtoName:fieldBindInfo)
		{
			getDetailDTOData(dtoName);
			
		}
	
		    	
	}
	public int getDetailMaxRow() {
		int maxrow=0;
		for(List<Object> dtoDatas:curRowDetialObject.values())
		{
			maxrow=Math.max(maxrow, dtoDatas.size());
		}
		return maxrow;
	}
	public List<BindDetailFieldInfo> getDetailBindInfoByHtml(String bindLine) throws Exception
	{
		curRowDetialObject.clear();
		return getDetailBindInfo(bindLine);
	}
	private  List<BindDetailFieldInfo> getDetailBindInfo(String bindLine) throws Exception
	{
		
		//[字段][表名:zxptsystem.dto.AutoDTO_QY_HTXX;字段:SXXYHM]
		//Map<String,List<BindDetailFieldInfo>> bindInfo=new HashMap<String,List<BindDetailFieldInfo>>(); 
		
		
		
		int lenFieldStartFlag=fieldStartFlag.length();
		
		
		int len=dtoNameSpliter.length();
		int dtoStartIndex=bindLine.indexOf(dtoNameSpliter);
		int dtoEndIndex;
		int fieldEndIndex;
		List<BindDetailFieldInfo> lstField=new ArrayList<BindDetailFieldInfo>();
		Set<String> dtoSet=new HashSet<String>();
		while(dtoStartIndex>-1)
		{
			dtoEndIndex=bindLine.indexOf(dtoEndFlag, dtoStartIndex+len);
			String dtoName=bindLine.substring(dtoStartIndex+len, dtoEndIndex);
			fieldEndIndex=bindLine.indexOf(fieldEndFlag, dtoEndIndex+lenFieldStartFlag);
			String fieldName=bindLine.substring(dtoEndIndex+lenFieldStartFlag+1,fieldEndIndex);
			dtoSet.add(dtoName);
			lstField.add(new BindDetailFieldInfo(dtoName,fieldName,dtoStartIndex,fieldEndIndex+1));
			
			
			dtoStartIndex=bindLine.indexOf(dtoNameSpliter,fieldEndIndex);
		}
		getDetialDTOData(dtoSet);
		return lstField;
	}
	
	private List<Object> getDetailDTOData(String dtoName) throws Exception
	{
		List<Object> objectList;
		if(curRowDetialObject.containsKey(dtoName))
		{
			objectList=curRowDetialObject.get(dtoName);
		}
		else
		{
		
			if(!StringUtils.isBlank(dtoName))
			{
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass( Class.forName(dtoName));
				if(ReflectOperation.getFieldByName(dtoName, "instInfo") != null)
					detachedCriteria.add(Restrictions.eq("instInfo",  instInfo));
				if(ReflectOperation.getFieldByName(dtoName, "dtDate") != null)
					detachedCriteria.add(Restrictions.eq("dtDate", TypeParse.parseDate(strDtDate)));

				// 查询 singleObjectFindByCriteriaDao
				IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByPageCriteriaDao");
				ShowParamManager.setFirstResult(0);
				ShowParamManager.setPageSize(nRptMaxLine);
				LogicParamManager.setDetachedCriteria(detachedCriteria);
				//Object lstObj=dao.paramObjectResultExecute(null);
		    	objectList = (List<Object>)dao.paramObjectResultExecute(null);
		    	

			}
			else
			{
				objectList=new ArrayList<Object>();
			}
			curRowDetialObject.put(dtoName, objectList);
			
		}
		return objectList;
		    	
	}
	
	public String getDetailFieldVal(BindDetailFieldInfo detailBindInfo,int curRow) throws Exception
	{
		String dtoName;
		Object obj;
		List<Object> listObj;
		String fieldName;
		
		dtoName=detailBindInfo.dtoName;
		fieldName=detailBindInfo.fieldName;
		listObj=getDetailDTOData(dtoName);
		if(listObj.size()>curRow)
		{
			obj=listObj.get(curRow);
			String fieldValue = "";
			Object value = ReflectOperation.getFieldValue(obj, fieldName);
			if(value != null) {
				Field field = ReflectOperation.getFieldByName(Class.forName(dtoName), fieldName);
				if(field.isAnnotationPresent(JoinColumn.class)){
					Field keyNameField = ReflectOperation.getKeyNameField(value.getClass());
					value = ReflectOperation.getFieldValue(value, keyNameField.getName());
					if(value != null)
						fieldValue = value.toString();
				}
				else
					fieldValue = ReflectOperation.getFieldValue(obj, fieldName).toString();
			}
			return fieldValue;
		}
		return "";
		
		
		
	}
	
	
	
	private String processDetailBindInfoFormat(String strBindInfo)
	{
	    return processBindInfoFormat(strBindInfo);
	}
	private static String processBindInfoFormat(String strBindInfo)
	{
		String htmlBindInfo=strBindInfo.replaceFirst("\n", ContentStart);
		htmlBindInfo=htmlBindInfo.replaceAll("\n",SectionSpliter);
		htmlBindInfo=htmlBindInfo+ContentEnd;
		return htmlBindInfo;
	}
	
	
	/**
	 * <p>方法描述: 获取指标数据</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param itemBindInfo
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-7-14 下午3:24:57</p>
	 */
	public SimpleItemData getItemData(ItemBindInfo itemBindInfo) throws Exception{
		return ItemDataCacheManger.getInsance().getItemData(itemBindInfo.instCode, itemBindInfo.date, itemBindInfo.itemCode, itemBindInfo.currency, itemBindInfo.property, itemBindInfo.ext1, itemBindInfo.ext2, itemBindInfo.freq);
	}
	
	public String getItemVal(ItemBindInfo itemBindInfo) throws Exception {
		ItemDataCacheManger cacheManager = ItemDataCacheManger.getInsance();
		Object obj = cacheManager.getItem(itemBindInfo.itemCode);
		ItemInfo itemInfo = null;
		if (obj != null) {
			itemInfo = (ItemInfo) obj;
		} else {
			throw new Exception("指标不存在：" + itemBindInfo.itemCode);
		}
		String val = getItemData(itemBindInfo).StrValue;
		return handleAmoutValue(itemInfo.getStrDataType(), val);
	}
	
	/**
	 * <p>方法描述: 处理金额</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param dataType
	 * @param val
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-7-14 下午3:22:21</p>
	 */
	public String handleAmoutValue(String dataType,String val){
		if (ItemDataType.Amount.toString().equals(dataType) && !StringUtils.isBlank(val)) 
		{
			Double dVal = TypeParse.parseDouble(val);
			if (Double.compare(dVal, Double.MAX_VALUE) < 0) {
				if (nRptUnit != 0)
					dVal /= nRptUnit;

				if (nRptPrecise < 0) {
					nRptPrecise = 2;
				}
				val = DoubleUtil.format(dVal, nRptPrecise, isShowThousand);
			} else
				val = "0";
		}
		else if(ItemDataType.Percent.toString().equals(dataType) && !StringUtils.isBlank(val))
			val = val+"%";
		else if (ItemDataType.Change_Amt_Str.toString().equals(dataType) && !StringUtils.isBlank(val)) 
		{
			Double dVal = TypeParse.parseDouble(val);
			if (Double.compare(dVal, Double.MAX_VALUE) < 0) {
				if (nRptUnit != 0)
					dVal /= nRptUnit;

				if (nRptPrecise < 0) {
					nRptPrecise = 2;
				}
				val = DoubleUtil.format(dVal, nRptPrecise, isShowThousand);
			} else
				val = val;
		}
		return val;
	
	}
	
	public ItemBindInfo getItemBindInfo(String strBindInfo,String instCode,String strDtDate,String strFreq,boolean isExcel) throws Exception
	{
		return getItemBindInfoWithClass(strBindInfo,instCode,strDtDate,strFreq,isExcel);
	}
	public static  ItemBindInfo getItemBindInfoWithClass(String strBindInfo,boolean isExcel) throws Exception
	{
		return getItemBindInfoWithClass(strBindInfo,"","","",isExcel);
	}
	public static  ItemBindInfo getItemBindInfoWithClass(String strBindInfo,String instCode,String strDtDate,String strFreq,boolean isExcel) throws Exception
	{
		// 解析出本指标ItemCode
		//excel format:[指标]\n代码:A095867\n币种:CNY\n属性:0001\n日期:\n机构:\n扩展1:\n扩展2:\n期数:1\n可编辑:2\n	颜色:#FFFFFF
		//[指标][代码:A000002;币种:CNY;属性:0001;日期:;机构:;频度:;扩展1:;扩展2:;期数:1;可编辑:1;颜色:#00FF00]
		
		
		String strValue;
		String strSection;
		String strSectionName;
		
		if(isExcel)
		{
			strBindInfo=processBindInfoFormat(strBindInfo);
		}
		int nStartPos =Start.length()+ContentStart.length();
		int nEndPos = strBindInfo.indexOf(ItemTemplate.SectionSpliter, nStartPos);
		int valStartPos;
		ItemBindInfo itemBindInfo=new ItemBindInfo();
		while(nEndPos>-1)
		{

		   strSection=strBindInfo.substring(nStartPos, nEndPos);
		   
		   valStartPos=strSection.indexOf(ItemTemplate.ValueSpliter);
		   
		   if(valStartPos>-1)
		   {
			   strSectionName=strSection.substring(0,valStartPos);
			   strValue=strSection.substring(valStartPos+1);
			   setBindInfoValue(strSectionName, strValue, itemBindInfo);
		   }
		   else
		   {
			   throw new Exception("模板指标绑定信息有误:"+strBindInfo+",位置："+strSection);
		   }
		   nStartPos=nEndPos+1;
		   nEndPos = strBindInfo.indexOf(ItemTemplate.SectionSpliter,nStartPos );
		}

		//颜色处理：
		strSection=strBindInfo.substring(nStartPos);
		valStartPos=strSection.indexOf(ItemTemplate.ValueSpliter);
		if(valStartPos>-1)
		{	
			strSectionName=strSection.substring(0,valStartPos);
			strValue=strSection.substring(valStartPos+1,strSection.length()-1);
			setBindInfoValue(strSectionName, strValue, itemBindInfo);   
		}
        
		if(StringUtils.isBlank(itemBindInfo.instCode))
			 itemBindInfo.instCode= instCode;
		
		Date realDate = null;
		if(StringUtils.isBlank(itemBindInfo.date))
			itemBindInfo.date=strDtDate;
		if(!report.dto.Period.CurPeriod.toString().equals(itemBindInfo.period))
		{			
			if(!StringUtils.isBlank(itemBindInfo.date))
			{
				//去年同期
				if(report.dto.Period.LastYearSameTime.toString().equals(itemBindInfo.period))
				{
					realDate=SmallTools.getLastYearSameTime(TypeParse.parseDate(itemBindInfo.date), strFreq);
					itemBindInfo.date=TypeParse.parseString(realDate,"yyyy-MM-dd");
				}
				//年初
				else if(report.dto.Period.FirstDayofYear.toString().equals(itemBindInfo.period))
				{
					 Calendar calendar = Calendar.getInstance();
			    	 calendar.setTime(TypeParse.parseDate(itemBindInfo.date));
			    	 int currentYear = calendar.get(Calendar.YEAR);  
			    	 calendar.clear();
			    	 calendar.set(Calendar.YEAR, currentYear); 
			    	 itemBindInfo.date=TypeParse.parseString(calendar.getTime(),"yyyy-MM-dd");
				}
				//上年末
				else if(report.dto.Period.LastYearEnd.toString().equals(itemBindInfo.period))
				{
					Calendar calendar = Calendar.getInstance();
			    	calendar.setTime(TypeParse.parseDate(itemBindInfo.date));
			    	int currentYear = calendar.get(Calendar.YEAR);  
			    	calendar.clear();
			    	calendar.set(Calendar.YEAR, currentYear); 
			    	calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
			    	itemBindInfo.date=TypeParse.parseString(calendar.getTime(),"yyyy-MM-dd"); 
				}
				//上期(当期数不为本期、去年同期、年初和上年末时，系统默认为上期)
				else if(!StringUtils.isBlank(strFreq))
				{
					realDate=SmallTools.getFreqDate(TypeParse.parseDate(itemBindInfo.date), strFreq);
					itemBindInfo.date=TypeParse.parseString(realDate,"yyyy-MM-dd");
				}
			}
			
		}
		
	    return itemBindInfo;
			
			
	}
	
	
	
	public static List<ItemBindInfo> getItemBindInfos(String fileName)
	{
		List<ItemBindInfo> listItemBindInfo=new ArrayList<ItemBindInfo>();
		try
		{
			String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
			String strRootPath = ServletActionContext.getServletContext().getRealPath("/");
			String path=strRootPath + strTmpRootPath + "/" + fileName + ".xls";
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path));
			Workbook wwb= new HSSFWorkbook(fs);
			Sheet sheet = wwb.getSheetAt(0);
			for(int k=0; k<=sheet.getLastRowNum(); k++) {
				Row row = sheet.getRow(k);
				if(row == null) continue;
				for(int l=0; l<=row.getLastCellNum(); l++) {
					Cell c1 =  row.getCell(l);
					if(c1!=null)
					{
							if(c1.getCellType()!=Cell.CELL_TYPE_FORMULA)
							{
								if(c1.getCellComment() != null){
									String comment = c1.getCellComment().getString().toString();
									if(comment.startsWith(Start))
									{
										ItemBindInfo bindInfo=getItemBindInfoWithClass(comment,true);
										if(bindInfo!=null)
										{
											bindInfo.row=k;
											bindInfo.col=l;
											listItemBindInfo.add(bindInfo);
										}
									}
									
								}
								
							}
							else
							{
								c1.setCellComment(null);
							}
					}
				}
			}

		}
		catch(Exception ex)
		{
			ExceptionLog.CreateLog(ex);
		}
		return listItemBindInfo;
	}
	
	public  ItemBindInfo getItemBindInfo(String strBindInfo,String instCode,String strDtDate,String strFreq) throws Exception
	{
		return getItemBindInfo(strBindInfo,instCode,strDtDate,strFreq,false);

	}

	public static void setBindInfoValue(String strSection, String strValue, ItemBindInfo itemBindInfo) {
		
		   if("-1".equals(strValue))
		   {
			   strValue="";
		   }
		   if(strSection.equals(Item))
		   {
			   itemBindInfo.itemCode=strValue;
		   }
		   else if(strSection.equals(Currency))
		   {
			   itemBindInfo.currency=strValue;   
		   }
		   else if(strSection.equals(Prop))
		   {
			   itemBindInfo.property=strValue;   
		   }
		   else if(strSection.equals(DtDate))
		   {
			   itemBindInfo.date=strValue;
		   }
		   else if(strSection.equals(Inst))
		   {
			   itemBindInfo.instCode=strValue;   
		   }
		   else if(strSection.equals(Ext1))
		   {
			   itemBindInfo.ext1=strValue;   
		   }
		   else if(strSection.equals(Ext2))
		   {
			   itemBindInfo.ext2=strValue;   
		   }
		   else if(strSection.equals(Freq))
		   {
			   itemBindInfo.freq=strValue;
		   }
		   else if(strSection.equals(Period))
		   {
			   itemBindInfo.period=strValue;
		   }
		   else if(strSection.equals(Color))
		   {
			   itemBindInfo.color=strValue;
		   } 
		   else if(strSection.equals(Editable))
		   {
			   itemBindInfo.editable=strValue;
		   }
	}
	
	/**
	 * <p>方法描述:将注释转换为 itemBindInfo </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param comment
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-9-12 下午5:47:32</p>
	 */
	public static ItemBindInfo commentToItemInfo(String comment){
		String[] des = comment.split(SectionSpliterWithExcel);
		ItemBindInfo itemB = new ItemBindInfo();
		for(String de:des){
			String[] ds = de.split(ValueSpliter);
			if(ds.length==2){
				setBindInfoValue(ds[0], ds[1], itemB);
			}
		}
		return itemB;
	}
	
}
