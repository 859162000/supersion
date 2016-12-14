package report.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import report.helper.DateUtil;

import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;



public class DownloadRepDetailService extends BaseService{
	
	private String error="下载错误";
	private String where="";
	private String tbName="";
	private String select="";
	private String sql = "";
	private HSSFWorkbook wb;
	
	@Override
	public void initSuccessResult() throws Exception{
		sql = ServletActionContext.getRequest().getParameter("sql");
		String calcRuleName = ServletActionContext.getRequest().getParameter("calcRuleName");
		String sqlNum = ServletActionContext.getRequest().getParameter("sqlNum");
		String temFilePath = "Download/tmp/"; // 下载生成文件路径
		if(null != sql){
			parseSql();
			if(StringUtils.isNotBlank(tbName)){
				temFilePath += calcRuleName+"_"+"SQL"+"_"+tbName+"_"+sqlNum+".xls";
				IParamObjectResultExecute criteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria mTableCri = DetachedCriteria.forClass(ReportModel_Table.class);
				mTableCri.add(Restrictions.eq("strTableName", tbName).ignoreCase());
				List<ReportModel_Table> mTableList = (List<ReportModel_Table>) criteriaDao.paramObjectResultExecute(new Object[]{mTableCri,null});
				if(mTableList.size()>0){
					ReportModel_Table table = mTableList.get(0);
					DetachedCriteria mFieldCri = DetachedCriteria.forClass(ReportModel_Field.class);
					mFieldCri.add(Restrictions.eq("reportModel_Table", table));
					mFieldCri.addOrder(Order.asc("nSeq"));
					List<ReportModel_Field> fileds = (List<ReportModel_Field>) criteriaDao.paramObjectResultExecute(new Object[]{mFieldCri,null});
					if(null != fileds){
						IParamObjectResultExecute listMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryListMapDao");
						List<Map<String, Object>> listMap = (List<Map<String, Object>>) listMapDao.paramObjectResultExecute(new Object[]{sql,null});
						List<Object[]> datas = new ArrayList<Object[]>();
						for(Map<String, Object> map : listMap){
							Object[] objs = new Object[fileds.size()];
							int i = 0;
							for(ReportModel_Field field : fileds){
								objs[i] = map.get(field.getStrFieldName());
								i++;
							}
							datas.add(objs);
						}
						File file = new File(ServletActionContext.getServletContext().getRealPath(temFilePath));
						exportData(table, fileds, datas,file);
						DownloadResult downloadResult = new FileHandler().GetStreamResultFromInputStream(new FileInputStream(file),file.getName(), 1024*1024*5);
						this.setServiceResult(downloadResult);
						return;
					}
				}else{
					error="SQL查询出错!";
				}
			}else{
				error="SQL语句错误!";
			}
		}
		this.setServiceResult(new MessageResult(false,error));
	}
	
	public void parseSql(){
		String _sql = sql.toUpperCase();
		int f = _sql.indexOf("FROM");
		int w = _sql.indexOf("WHERE");
		int s = _sql.indexOf("SELECT");
		if(f != -1 && s != -1){
			sql = "SELECT * "+sql.substring(f);
			select = _sql.substring(6,f);
			if(w != -1){
				tbName = _sql.substring(f+4,w).trim();
				where = _sql.substring(w);
			}else{
				tbName = _sql.substring(f+4).replace(";", "").trim();
			}
		}
	}
	
	public void exportData(ReportModel_Table table,List<ReportModel_Field> fileds,List<Object[]> datas,File file) throws IOException{
		wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("数据项");
        // 创建单元格样式  
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        // 指定单元格居中对齐  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 指定单元格垂直居中对齐  
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        
		//构建标题 -----------------------------------------------------------------
        HSSFFont fontT = wb.createFont();    
        fontT.setFontName("黑体");    
        fontT.setFontHeightInPoints((short) 20);//设置字体大小  
        HSSFCellStyle cellStyleT = wb.createCellStyle(); 
        cellStyleT.cloneStyleFrom(cellStyle);
        cellStyleT.setFont(fontT);
		HSSFRow title = sheet.createRow(0); 
		HSSFCell cell = title.createCell(0);
		cell.setCellValue(table.getStrChinaName()+"【"+table.getStrTableName()+"】");
        cell.setCellStyle(cellStyleT);
        sheet.addMergedRegion(new CellRangeAddress(0,0, 0, fileds.size()-1));
        //-----------------------------------------------------------------------
        
		//构建表头******************************************************************
        HSSFFont fontRed = wb.createFont();    
        fontRed.setFontName("宋体");    
        fontRed.setColor(HSSFColor.RED.index);
        HSSFCellStyle cellStyleRed = wb.createCellStyle(); 
        cellStyleRed.cloneStyleFrom(cellStyle);
        cellStyleRed.setFont(fontRed);
        
        HSSFFont fontBlue = wb.createFont();    
        fontBlue.setFontName("宋体");    
        fontBlue.setColor(HSSFColor.BLUE.index);
        HSSFCellStyle cellStyleBlue = wb.createCellStyle(); 
        cellStyleBlue.cloneStyleFrom(cellStyle);
        cellStyleBlue.setFont(fontBlue);
        
		HSSFRow head = sheet.createRow(1);
		int t = -1;
		int cNum = 0;
		String c = null;
		for(ReportModel_Field field:fileds){
			cell = head.createCell(cNum);
			cell.setCellValue(field.getStrChinaName()+"【"+field.getStrFieldName()+"】");
			cell.setCellStyle(cellStyle);
			if((t=where.indexOf(field.getStrFieldName().toUpperCase())) != -1){
				t += field.getStrFieldName().length();
				c = where.substring(t, t+1);
				if((" ").equals(c)||(" ").equals(c)||(" ").equals(c))
					cell.setCellStyle(cellStyleRed);
			}
			if((t=select.indexOf(field.getStrFieldName().toUpperCase())) != -1){
				t += field.getStrFieldName().length();
				c = select.substring(t, t+1);
				if((" ").equals(c)||(")").equals(c)||(",").equals(c))
					cell.setCellStyle(cellStyleBlue);
			}
			sheet.setColumnWidth(cNum,cell.getStringCellValue().getBytes().length*2*200);
			cNum++;
		}

		//***********************************************************************
		
		int rNum = 2;
		HSSFRow row = null;
		//构建数据流################################################################
		for(Object[] objs:datas){
			row = sheet.createRow(rNum);
			cNum = 0;
			for(Object obj:objs){
				cell = row.createCell(cNum);
				cell.setCellStyle(cellStyle);
				if(null != obj){
					if(obj instanceof Timestamp){
						String date= DateUtil.formarDate((Timestamp)obj, "yyyy-MM-dd HH:mm:ss");
						cell.setCellValue(date);
					}
					else if(obj instanceof Date){
						String date= DateUtil.formarDate((Date)obj, "yyyy-MM-dd");
						cell.setCellValue(date);
					}
					else if(obj instanceof Boolean){
						cell.setCellValue((Boolean)obj);
					}
					else if(obj instanceof Double || obj instanceof BigDecimal || obj instanceof Float){
						cell.setCellValue(Double.parseDouble(obj.toString()));	
					}
					
					else{
						cell.setCellValue(obj+"");
					}
				}
				cNum++;
			}
			rNum++;
		}
		//#######################################################################
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}
	
}
