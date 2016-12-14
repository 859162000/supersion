package report.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dao.imps.ItemDataCacheManger;
import report.dto.ItemBindInfo;
import report.dto.ItemDataType;
import report.dto.ItemInfo;
import report.dto.RptSubmitInfo;
import report.dto.TaskRptInst;
import report.dto.xml.CbrcReportsX;
import report.dto.xml.CellX;
import report.dto.xml.DataX;
import report.dto.xml.InfoX;
import report.dto.xml.ReportX;
import report.dto.xml.RowX;
import report.helper.GetFilePath;
import coresystem.dto.InstInfo;
import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.FileHelper;
import framework.helper.FrameworkFactory;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

public class XmlFileBuilder {

	private static final String ENCODE="UTF-8";
	private static final String XLSX =".xlsx";
	private static final String XLS =".xls";
	private List<TaskRptInst> lstTaskRptInst;
	private Map<String,CbrcReportsX> mapCbrcReportsX;
	private Map<String,Workbook> templateFiles;
	private List<File> zipFiles;
	private String dataScope;
	
	
	
	public XmlFileBuilder(String dataScope,List<TaskRptInst> taskRptInstList)
	{
		this.dataScope=dataScope;
		this.lstTaskRptInst=new ArrayList<TaskRptInst>(taskRptInstList);
		this.templateFiles=new HashMap<String,Workbook>();
		this.zipFiles=new ArrayList<File>();
		mapCbrcReportsX=new HashMap<String,CbrcReportsX>();
	}
	
	public void make() {
		try {
			if(lstTaskRptInst.size()>0)
			{
				getReportData();
				buildFile();
			}
			
		} catch (Exception e) {
    	   ExceptionLog.CreateLog(e);
       }
	}
	
	private Workbook getWorkbook(String strRptFile)
	{
		
		Workbook wwb=null;
		try
		{
			if( strRptFile.endsWith(XLS))
			{
				
				POIFSFileSystem	fs = new POIFSFileSystem(new FileInputStream(strRptFile));
				wwb= new HSSFWorkbook(fs);
			}
			else
			{
				FileInputStream ipt= new FileInputStream(strRptFile);
				wwb = new XSSFWorkbook(ipt);
			}
		}
		catch(Exception ex)
		{
			
			ExceptionLog.CreateLog(ex);
		}
		return wwb;
		
		 
	}
    private String getFileName(TaskRptInst taskRptInst)
    {
    	return String.format("%s_%s_%s.xml",
    			 taskRptInst.getInstInfo().getStrInstCode(),
    			 taskRptInst.getRptInfo().getStrBCode(), 
    			 TypeParse.parseString(taskRptInst.getTaskFlow().getDtTaskDate(),"yyyyMMdd"));
    }
	
	private Workbook getTemplateContent(String strRptFile) throws Exception
	{
		Workbook wb=templateFiles.get(strRptFile);
		if(wb==null && !templateFiles.containsKey(strRptFile))
		{
			String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
			String strRootPath = ServletActionContext.getServletContext().getRealPath("/");
			String strTempPath =strRootPath + strTmpRootPath + "/" +strRptFile+XLS;
			wb=getWorkbook(strTempPath);
			templateFiles.put(strRptFile, wb);
			
		}
		return wb;
		
		
		
	}
	
	private String getCellComment(Cell cell)
	{
		String cellValue="";
		if(cell != null && cell.getCellComment() != null) {
			try {
			cellValue = cell.getCellComment().getString().toString();
			
			} catch(Exception ex) {
				ExceptionLog.CreateLog(ex);
				cellValue = "";
			}
			
		}
		return cellValue;
	}
	
	private String getMapItemType(String itemType)
	{
		if(itemType.equals(ItemDataType.Amount.toString())||
				itemType.equals(ItemDataType.Num.toString())||	
				itemType.equals(ItemDataType.Seq.toString())||
				itemType.equals(ItemDataType.Ratio.toString())||
				itemType.equals(ItemDataType.Percent.toString())||
				itemType.equals(ItemDataType.Date.toString()))
		{
			return itemType;
		}
		else
			return itemType;
		

	}
	
	private void getReportData() {
		try {
			Map<String, String> reportUnit = ShowContext.getInstance().getShowEntityMap().get("reportUnit");
			Map<String, String> reportRange = HelpTool.getSystemConstVal("reportRange");
			for (TaskRptInst taskRptInst : lstTaskRptInst) {

				Workbook template = getTemplateContent(taskRptInst.getRptInfo().getStrRptPath());
				if (template != null) {

					InstInfo instInfo = taskRptInst.getInstInfo();
					Sheet sheet = template.getSheetAt(0);
					ItemDataCacheManger dataCache = ItemDataCacheManger.getInsance();
					String strFreq = taskRptInst.getTaskFlow().getStrFreq();
					String strDate = TypeParse.parseString(taskRptInst.getTaskFlow().getDtTaskDate(), "yyyy-MM-dd");
					CbrcReportsX cbrcReportsX = new CbrcReportsX();
					mapCbrcReportsX.put(getFileName(taskRptInst), cbrcReportsX);
					ReportX reportX = new ReportX();
					setReportCommon(reportUnit, reportRange, taskRptInst, strDate, reportX);
					cbrcReportsX.setReport(reportX);
					ItemTemplate itemTemplate = new ItemTemplate(taskRptInst);
					IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.RptSubmitInfo"));
					detachedCriteria.add(Restrictions.eq("taskRptInstId", taskRptInst.getId()));
					List<RptSubmitInfo> rptSubmitInfoList = (List<RptSubmitInfo>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
					RptSubmitInfo rptSubmitInfo;

					if (rptSubmitInfoList.size() > 0) {
						InfoX infoX = new InfoX();
						rptSubmitInfo = rptSubmitInfoList.get(0);
						infoX.setTabulator(rptSubmitInfo.getStrReporter());
						infoX.setAuditor(rptSubmitInfo.getStrChecker());
						infoX.setPrincipal(rptSubmitInfo.getStrManager());
						reportX.setInfo(infoX);
					}
					// 保存数据
					DataX dataX = new DataX();
					dataX.setPart("0");
					reportX.getDatas().add(dataX);
					for (int curRow = 0; curRow <= sheet.getLastRowNum(); curRow++) { // 每行
						Row rowList = sheet.getRow(curRow);
						if (rowList == null)
							continue;
						RowX rowX = new RowX((curRow + 1) + "");
						for (int curCol = 0; curCol <= rowList.getLastCellNum(); curCol++) { // 每列
							Cell cell = rowList.getCell(curCol);
							String cellValue = getCellComment(cell);
							int nPos = cellValue.indexOf(ItemTemplate.Start);
							if (nPos > -1) {
								ItemBindInfo itemBindInfo = itemTemplate.getItemBindInfo(cellValue, instInfo.getStrInstCode(), strDate, strFreq, true);
								String val = itemTemplate.getItemVal(itemBindInfo);
								ItemInfo itemInfo = dataCache.getItem(itemBindInfo.itemCode);
								if (itemInfo != null) {
									if (ItemDataType.Amount.toString().equals(itemInfo.getStrDataType()) || ItemDataType.Num.toString().equals(itemInfo.getStrDataType())) {
										if (StringUtils.isBlank(val)) {
											val = "0";
										}
									}
									rowX.getCells().add(new CellX(CellReference.convertNumToColString(curCol), val));
								}
							}
						}
						dataX.getRows().add(rowX);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			ExceptionLog.CreateLog(ex);
		}

	}
	
	
	private String indexToColumn(int index) {
		String column = "";
		do {
			if (column.length() > 0) {
				index--;
			}
			column = ((char) (index % 26 + (int) 'A')) + column;
			index = (int) ((index - index % 26) / 26);
		} while (index > 0);
		return column;
	}

	private void setReportCommon(Map<String, String> reportUnit, Map<String, String> reportRange, TaskRptInst taskRptInst, String strDate, ReportX reportx) {
		reportx.setRepCode(taskRptInst.getRptInfo().getStrBCode());
		reportx.setVersionId(taskRptInst.getRptInfo().getStrRptVersion());
		reportx.setReportDate(strDate);
		reportx.setRange(reportRange.get(taskRptInst.getInstInfo().getStrInstCode()));
		reportx.setUnit(reportUnit.get(taskRptInst.getRptInfo().getIntRptUnit().toString()));
	}
	private String getZipName(TaskRptInst tri)
	{
		if("1".equals(dataScope))
		{
			return String.format("%s_%s_%s_%s.zip", tri.getTaskFlow().getStrTaskName(),
					tri.getInstInfo().getStrInstName(),
					tri.getRptInfo().getStrBCode(),
					TypeParse.parseString(tri.getTaskFlow().getDtTaskDate(), "yyyyMMdd"));
		}
		else if("2".equals(dataScope))
		{
			return String.format("%s_%s_%s.zip", tri.getTaskFlow().getStrTaskName(),
					tri.getInstInfo().getStrInstName(),
					TypeParse.parseString(tri.getTaskFlow().getDtTaskDate(), "yyyyMMdd"));
		}
		else
		{
			return String.format("%s_%s.zip", tri.getTaskFlow().getStrTaskName(),TypeParse.parseString(tri.getTaskFlow().getDtTaskDate(), "yyyyMMdd"));
		}
		
	}
	private void buildFile()
	{
		DownloadResourceService dr=new DownloadResourceService();
		GetFilePath getFilePath = new GetFilePath();
		TaskRptInst tri=lstTaskRptInst.get(0);
		String zipFileName=getZipName(tri);
		dr.addStart(zipFileName, tri.getTaskFlow().getSuit().getStrSuitCode()); 
		buildXmlFile();
		try {
			SmallTools.compressedFiles(zipFiles, getFilePath.getDownloadResourcePath(), zipFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExceptionLog.CreateLog(e);
		} // 将文件打包
		SmallTools.delFileList(zipFiles);	//清空所有临时子文件

		dr.addEnd(zipFileName, tri.getTaskFlow().getSuit().getStrSuitCode()); // 修改结束日期
		
	}
	private void buildXmlFile()
	{
	
		GetFilePath g=new GetFilePath();
		String tempPath=g.getTmpFilePath();
		for(Entry<String,CbrcReportsX> entry:mapCbrcReportsX.entrySet())
		{
			String filePath =tempPath+"/"+entry.getKey();
	        File file =FileHelper.writeXmlFile(entry.getValue(), filePath, ENCODE);
	        if(file!=null)
	        {
	        	zipFiles.add(file);
	        }
		}
        
	}
}
