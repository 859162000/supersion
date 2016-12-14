package framework.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.JoinColumn;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.ReflectOperation;
import framework.helper.TypeParse;

public class ExcelUtils {
	public static Map<String,String> getSheetData(String dataFileName,int sheetIndex)
	{
		Map<String,String> datas=new IdentityHashMap<String,String>();
		if(!StringUtils.isBlank(dataFileName))
		{
			InputStream  is=ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
			POIFSFileSystem fs;
			try {
				fs = new POIFSFileSystem(is);
				HSSFWorkbook wwb = new HSSFWorkbook(fs);
				Sheet sheet = wwb.getSheetAt(sheetIndex);
				
				datas=getSheetData(sheet);
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
		}
		return datas;
	}
	
	public static Map<String,String > getSheetData(Sheet sheet)
	{
		Map<String,String> datas=new HashMap<String,String>();
		if(sheet!=null)
		{
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if(row!=null)
				{
					HSSFCell cell = (HSSFCell) row.getCell(0);
					if(cell!=null )
					{
						String paraName=cell.getStringCellValue();
						String val="";
						HSSFCell cellVal = (HSSFCell) row.getCell(1);
						if(cellVal!=null )
						{
							val=cellVal.toString();
												
						}
						datas.put(paraName, val);
					}
				}
				
				
			}
			
		}
		return datas;
	}
	
	/**
	 * 获取excel指定位置的参数值
	 * @param condition
	 * @return
	 */
	public static String getExcelValue(String dataFileName, String condition, String seq){
		String value = null;
		if(!StringUtils.isBlank(condition) && !StringUtils.isBlank(seq)){
			InputStream is = null;
			String[] arr = condition.split(seq);
			try {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				HSSFWorkbook wb = new HSSFWorkbook(is);
				HSSFSheet sheet = null;
				try {
					sheet = wb.getSheetAt(Integer.parseInt(arr[0]));
				} catch (Exception e) {
					if(arr[0].length() > 31){
						sheet = wb.getSheet(arr[0].substring(0, 31));
					}else{
						sheet = wb.getSheet(arr[0]);
					}
				}
				
				if(sheet != null){
					HSSFRow row = sheet.getRow(Integer.parseInt(arr[1]));
					if(row != null){
						HSSFCell cell = row.getCell(Integer.parseInt(arr[2]));
						if(cell != null){
							value = cell.getStringCellValue();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(is != null){
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	/**
	 * 修改指定位置的excel的值
	 */
	public static void updateExcelValue(String dataFileName, String condition, String seq){
		if(!StringUtils.isBlank(dataFileName) && !StringUtils.isBlank(condition) && !StringUtils.isBlank(seq)) {
			InputStream is = null;
			OutputStream os = null;
			String[] arr = null;
			try {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				HSSFWorkbook wb = new HSSFWorkbook(is);
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell = null;
				HSSFRichTextString cellVal = null;
				arr = condition.split(seq);
				try {
					sheet = wb.getSheetAt(Integer.parseInt(arr[0]));
				} catch (Exception e) {
					if(arr[0].length() > 31){
						sheet = wb.getSheet(arr[0].substring(0, 31));
					}else{
						sheet = wb.getSheet(arr[0]);
					}
				}
				
				if(sheet != null){
					if(arr[1].equals("addRow")){
						row = sheet.createRow(sheet.getLastRowNum()+1);
						cell = row.createCell(0);
						cell.setCellValue(new HSSFRichTextString(arr[2]));
						cell = row.createCell(1);
						if(arr.length == 4){
							cell.setCellValue(new HSSFRichTextString(arr[3]));
						}else{
							cell.setCellValue(new HSSFRichTextString());
						}
					}else{
						row = sheet.getRow(Integer.parseInt(arr[1]));
						if(row == null){
							row = sheet.createRow(Integer.parseInt(arr[1]));
						}
						cell = row.getCell(Integer.parseInt(arr[2]));
						if(cell == null){
							cell = row.createCell(Integer.parseInt(arr[2]));
						}
						if(arr.length == 4){
							cellVal = new HSSFRichTextString(arr[3]);
						}else{
							cellVal = new HSSFRichTextString();
						}
						cell.setCellValue(cellVal);
					}
				}
				os = new FileOutputStream(getPath(dataFileName));
				wb.write(os);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(os != null){
						os.close();
					}
					if(is != null){
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static String getPath(String resourceName) throws FileNotFoundException
	{
		 URL url = ExcelUtils.class.getClassLoader().getResource(resourceName);
		 if(url==null)
		 {
			 throw new FileNotFoundException(resourceName);
		 }
		 String path=URLDecoder.decode(url.getPath());
		return path;
	}
	
	/**
	 * 修改指定位置的excel的值
	 */
	public static void updateExcelValue(String dataFileName, List<String> condition, String seq){
		if(condition != null && condition.size() > 0 && !StringUtils.isBlank(seq)){
			InputStream is = null;
			OutputStream os = null;
			String[] arr = null;
			try {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				HSSFWorkbook wb = new HSSFWorkbook(is);
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell = null;
				HSSFRichTextString cellVal = null;
				for(String str : condition){
					arr = str.split(seq);
					try {
						sheet = wb.getSheetAt(Integer.parseInt(arr[0]));
					} catch (Exception e) {
						if(arr[0].length() > 31){
							sheet = wb.getSheet(arr[0].substring(0, 31));
						}else{
							sheet = wb.getSheet(arr[0]);
						}
					}
					
					if(sheet != null){
						if(arr[1].equals("addRow")){
							row = sheet.createRow(sheet.getLastRowNum()+1);
							cell = row.createCell(0);
							cell.setCellValue(new HSSFRichTextString(arr[2]));
							cell = row.createCell(1);
							if(arr.length == 4){
								cell.setCellValue(new HSSFRichTextString(arr[3]));
							}else{
								cell.setCellValue(new HSSFRichTextString());
							}
						}else{
							row = sheet.getRow(Integer.parseInt(arr[1]));
							if(row == null){
								row = sheet.createRow(Integer.parseInt(arr[1]));
							}
							cell = row.getCell(Integer.parseInt(arr[2]));
							if(cell == null){
								cell = row.createCell(Integer.parseInt(arr[2]));
							}
							if(arr.length == 4){
								cellVal = new HSSFRichTextString(arr[3]);
							}else{
								cellVal = new HSSFRichTextString();
							}
							cell.setCellValue(cellVal);
						}
					}
				}
				os = new FileOutputStream(getPath(dataFileName));
				wb.write(os);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				condition.clear();
				try {
					if(os != null){
						os.close();
					}
					if(is != null){
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除excel中执行位置的行
	 * @param condition
	 * @param delAllFlag 全部删除标志（即，删除整个sheet页中的所有行）
	 * @param seq
	 */
	public static void deleteExcelRow(String dataFileName, List<String> condition, String seq){
		if(condition != null && condition.size() > 0 && !StringUtils.isBlank(seq)){
			InputStream is = null;
			OutputStream os = null;
			String[] arr = null;
			try {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				HSSFWorkbook wb = new HSSFWorkbook(is);
				HSSFSheet sheet = null;
				HSSFRow row = null;
				for(String str : condition){
					arr = str.split(seq);
					try {
						sheet = wb.getSheetAt(Integer.parseInt(arr[0]));
					} catch (Exception e) {
						if(arr[0].length() > 31){
							sheet = wb.getSheet(arr[0].substring(0, 31));
						}else{
							sheet = wb.getSheet(arr[0]);
						}
					}
					
					if(sheet != null){
						if(arr[1].equals("all")){
							for(int i = 0; i <= sheet.getLastRowNum(); i++){
								row = sheet.getRow(i);
								sheet.removeRow(row);
							}
						}else{
							if(arr[1].equals("endRow")){
								row = sheet.getRow(sheet.getLastRowNum());
							}else{
								row = sheet.getRow(Integer.parseInt(arr[1]));
							}
							if(row != null){
								sheet.removeRow(row);
							}
						}
					}
				}
				os = new FileOutputStream(getPath(dataFileName));
				wb.write(os);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				condition.clear();
				try {
					if(is != null){
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取相应DTO对应的查询条件
	 * @param dataFileName 数据文件名称
	 * @param sheetInfoList sheet页名称或者是下标集合（按照逻辑顺序排列），内容和dtoNameList一一对应且参数传入的顺序要和数据库需要删除的数据的主外键删除顺序相同
	 * @param dtoNameList DTO名称的集合（按照逻辑排列），内容和sheetInfo一一对应且参数传入的顺序要和数据库需要删除的数据的主外键删除顺序相同
	 * @return 按照顺序排列的DTO对应的查询条件（如：DTO排序是A、B，则查询条件的排序就是条件A、条件B）
	 */
	@SuppressWarnings("finally")
	public static List<DetachedCriteria> getDetachedCriteriaList(String dataFileName, List<String> sheetInfoList, List<String> dtoNameList){
		int index = 0;// 遍历下标
		int rowNum = 0;
		HSSFRow row = null;
		InputStream is = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		String valCellVal = null;
		String nameCellVal = null;
		DetachedCriteria dc = null;
		HSSFCell paramValCell = null;
		HSSFCell paramNameCell = null;
		List<DetachedCriteria> dcList = null;
		Field pkField = null;
		try{
			if(!StringUtils.isBlank(dataFileName) && sheetInfoList != null
					&& dtoNameList != null && sheetInfoList.size() > 0
					&& dtoNameList.size() > 0 && sheetInfoList.size() == dtoNameList.size()){
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				wb = new HSSFWorkbook(is);
				dcList = new ArrayList<DetachedCriteria>();
				for(String dtoName : dtoNameList){
					pkField = ReflectOperation.getPrimaryKeyField(dtoName);
					try {
						sheet = wb.getSheetAt(Integer.parseInt(sheetInfoList.get(index)));
					} catch (Exception e) {
						if(sheetInfoList.get(index).length() > 31){
							sheet = wb.getSheet(sheetInfoList.get(index).substring(0, 31));
						}else{
							sheet = wb.getSheet(sheetInfoList.get(index));
						}
					}
					
					if(sheet != null && sheet.getLastRowNum() >= 0){
						dc = DetachedCriteria.forClass(Class.forName(dtoName));
						while(rowNum <= sheet.getLastRowNum()){
							row = sheet.getRow(rowNum);
							if(row != null){
								paramValCell = row.getCell(1);
								if(paramValCell != null){
									valCellVal = paramValCell.getStringCellValue();
									if(!StringUtils.isBlank(valCellVal)){
										paramNameCell = row.getCell(0);
										if(paramNameCell != null){
											nameCellVal = paramNameCell.getStringCellValue();
											if(!StringUtils.isBlank(nameCellVal) && !nameCellVal.contains(pkField.getName())
													&& !"windowId".equals(nameCellVal) && !"runAloneFlag".equals(nameCellVal)){
												if(ActionTestUtils.isDateField(dtoName, nameCellVal)){
													dc.add(Restrictions.eq(nameCellVal.substring(nameCellVal.indexOf(".")+1), TypeParse.parseDate(valCellVal)));
												}else{
													dc.add(Restrictions.eq(nameCellVal.substring(nameCellVal.indexOf(".")+1), valCellVal));
												}
											}
										}
									}
								}
							}
							rowNum ++;
						}
						rowNum = 0;
						if(dc != null){
							dcList.add(dc);
						}
					}
					// 遍历获取下一个dtoName
					index ++;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(is != null){
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return dcList;
		}
		
	}
	
	/**
	 * 根据sheet获取dto的查询条件
	 * @param dataFileName EXCEL参数文件
	 * @param sheetInfo sheet页信息（名称或者下标）
	 * @param dtoName dto名称
	 * @return DetachedCriteria
	 */
	@SuppressWarnings("finally")
	public static DetachedCriteria getDetachedCriteriaList(String dataFileName, String sheetInfo, String dtoName){
		int rowNum = 0;
		HSSFRow row = null;
		InputStream is = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		String valCellVal = null;
		String nameCellVal = null;
		DetachedCriteria dc = null;
		HSSFCell paramValCell = null;
		HSSFCell paramNameCell = null;
		Field pkField = null;
		try{
			if(!StringUtils.isBlank(dataFileName) && !StringUtils.isBlank(dtoName)){
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				wb = new HSSFWorkbook(is);
				
				pkField = ReflectOperation.getPrimaryKeyField(dtoName);
				if(StringUtils.isBlank(sheetInfo)){
					sheet = wb.getSheetAt(0);
				}else{
					try {
						sheet = wb.getSheetAt(Integer.parseInt(sheetInfo));
					} catch (Exception e) {
						if(sheetInfo.length() >= 31){
							sheet = wb.getSheet(sheetInfo.substring(0, 31));
						}else{
							sheet = wb.getSheet(sheetInfo);
						}
					}
				}
				
				if(sheet != null && sheet.getLastRowNum() >= 0){
					dc = DetachedCriteria.forClass(Class.forName(dtoName));
					while(rowNum <= sheet.getLastRowNum()){
						row = sheet.getRow(rowNum);
						if(row != null){
							paramValCell = row.getCell(1);
							if(paramValCell != null){
								valCellVal = paramValCell.getStringCellValue();
								if(!StringUtils.isBlank(valCellVal)){
									paramNameCell = row.getCell(0);
									if(paramNameCell != null){
										nameCellVal = paramNameCell.getStringCellValue();
										if(!StringUtils.isBlank(nameCellVal) && !nameCellVal.contains(pkField.getName())
												&& !"windowId".equals(nameCellVal) && !nameCellVal.contains("lastUpdateDate")){
											if(ActionTestUtils.isDateField(dtoName, nameCellVal)){
												dc.add(Restrictions.eq(nameCellVal.substring(nameCellVal.indexOf(".")+1), TypeParse.parseDate(valCellVal)));
											} else if(ActionTestUtils.isTimeStampField(dtoName, nameCellVal)) {
												dc.add(Restrictions.eq(nameCellVal.substring(nameCellVal.indexOf(".")+1), TypeParse.parseTimestamp(valCellVal)));
											} else {
												dc.add(Restrictions.eq(nameCellVal.substring(nameCellVal.indexOf(".")+1), valCellVal));
											}
										}
									}
								}
							}
						}
						rowNum ++;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(is != null){
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return dc;
		}
		
	}
	
	/**
	 * 类加载是获取测试DTO列表
	 * @return
	 */
	@SuppressWarnings("finally")
	public static List<String> getTestDto(String dataFileName){
		InputStream is = null;
		List<String> dtoList = null;
		try {
			is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
			POIFSFileSystem fs = new POIFSFileSystem(is);
			HSSFWorkbook wwb = new HSSFWorkbook(fs);
			is.close();
			Sheet sheet = wwb.getSheetAt(0);
			if(sheet != null){
				int rowNum = 0;
				String val = null;
				dtoList = new ArrayList<String>();
				while(rowNum <= sheet.getLastRowNum()){
					HSSFRow row = (HSSFRow) sheet.getRow(rowNum);
					if(row != null){
						HSSFCell cell = row.getCell(0);
						if(cell != null){
							val = cell.getStringCellValue();
							if(!StringUtils.isBlank(val)){
								dtoList.add(val);
							}
						}
					}
					rowNum++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return dtoList;
		}
	}
	
	/**
	 * 根据DTO名称获取sheet页下标（前提：sheet必须以DTO名称命名）
	 * @param dataFileName
	 * @param dtoName
	 * @return
	 */
	@SuppressWarnings("finally")
	public static int getSheetIndex(String dataFileName, String dtoName){
		int sheetIndex = -1;
		InputStream is = null;
		try {
			if(!StringUtils.isBlank(dataFileName) && !StringUtils.isBlank(dtoName)){
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				POIFSFileSystem fs = new POIFSFileSystem(is);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				String sheetName = "";
				int index = dtoName.lastIndexOf(".")+1;
				sheetName = dtoName.substring(index);
				sheetIndex = wb.getSheetIndex(sheetName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(is != null){
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			return sheetIndex;
		}
	}

	/**
	 * 获取根据字段名称获取行号
	 * @param dataFileName
	 * @param sheetInfo
	 * @param value
	 * @return
	 */
	@SuppressWarnings("finally")
	public static int getRowNumByFieldName(String dataFileName, Object sheetInfo, String fieldName){
		int rowNum = -1;
		InputStream is = null;
		try {
			if(!StringUtils.isBlank(dataFileName) && !StringUtils.isBlank(fieldName)){
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				POIFSFileSystem fs = new POIFSFileSystem(is);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = null;
				if(sheetInfo.getClass().equals(String.class)){
					sheet = wb.getSheet(sheetInfo.toString());
				} else if(sheetInfo.getClass().equals(Integer.class)){
					sheet = wb.getSheetAt((Integer) sheetInfo);
				}
				
				if(sheet != null){
					int index = 0;
					HSSFRow row = null;
					HSSFCell cell = null;
					String cellVal = null;
					while(index <= sheet.getLastRowNum()){
						row = sheet.getRow(index);
						if(row != null){
							cell = row.getCell(0);
							if(cell != null){
								cellVal = cell.getStringCellValue();
								if(!StringUtils.isBlank(cellVal) && cellVal.contains(fieldName)){
									rowNum = row.getRowNum();
									break;
								}
							}
						}
						index++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null){
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			return rowNum;
		}
	}
	
	/**
	 * 获取预期结果
	 * @param dataFileName
	 * @param sheetIndex
	 * @return
	 */
	public static String getExpectResult(String dataFileName, int sheetIndex){
		String expectResult = null;
		InputStream is = null;
		try {
			if(!StringUtils.isBlank(dataFileName)) {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				HSSFWorkbook wb = new HSSFWorkbook(is);
				HSSFSheet sheet = wb.getSheetAt(sheetIndex);
				if(sheet != null) {
					String sheetName = sheet.getSheetName();
					expectResult = sheetName.substring(0, sheetName.indexOf("_"));
				}
			}
			if(is != null) {
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return expectResult;
	}

	/**
	 * 获取参数文件中记录测试数据的
	 * @param dataFileName 参数excel文件名称 
	 * @param className DTO类名
	 * @return
	 */
	@SuppressWarnings("finally")
	public static int getTestDataSheetCount(String dataFileName, String className){
		InputStream is = null;
		POIFSFileSystem fs = null;
		int sheetNum = 0;
		try {
			if(!StringUtils.isBlank(dataFileName) && !StringUtils.isBlank(className)){
				String sheetName = null;
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(dataFileName);
				fs = new POIFSFileSystem(is);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
			    int sheetCount = wb.getNumberOfSheets();//获取Excel中所有表数目
			    if(sheetCount > 0){
			    	for(int index = 0; index < sheetCount; index++) {
			    		sheetName = wb.getSheetName(index);
			    		if(!className.equals(sheetName)) {
			    			sheetNum ++;
			    		}
			    	}
			    }
			} 
		}catch (IOException e) {
			e.printStackTrace();
		} finally {		
			return sheetNum;
		}
	}

	

	
	
	
/****************************************Excel同一个DTO多条数据的操作start****************************************/
	
	/**
	 * <P>根据DTO名称获取同一个DTO需要保存的数据的数目</P>
	 * @param dtoName DTO名称
	 */
	public static int getSameDTOTestDataCountByDto(String dtoName) {
		int dataCount = 0;
		if(!StringUtils.isBlank(dtoName)) {
			String excelName = ActionTestUtils.getExcelFileOrSheetName(dtoName, false);
			String sheetName = ActionTestUtils.getExcelFileOrSheetName(dtoName, true);
			dataCount = getSameDTOTestDataCountByExcel(excelName, sheetName);
		}
		return dataCount;
	}
	
	/**
	 * <P>根据Excel名称获取同一个DTO需要保存的数据的数目</P>
	 * @param excelName Excel文件名称
	 * @param sheetInfo 当前Excel文件中存放参数信息的sheet页的名称或者下标
	 */
	public static int getSameDTOTestDataCountByExcel(String excelName, Object sheetInfo) {
		int dataCount = 0;
		InputStream is = null;
		POIFSFileSystem fs = null;
		HSSFRow row = null;
		HSSFCell valCell = null;
		HSSFCell nameCell = null;
		HSSFSheet sheet = null;
		try {
			if(!StringUtils.isBlank(excelName)) {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(excelName);
				fs = new POIFSFileSystem(is);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				
				if(sheetInfo == null) {
					sheet = wb.getSheetAt(0);
				} else if(sheetInfo instanceof Integer) {
					sheet = wb.getSheetAt((Integer) sheetInfo);
				} else if(sheetInfo instanceof String) {
					if(!StringUtils.isBlank(sheetInfo.toString())) {
						if(sheetInfo.toString().length() > 31) {
							sheet = wb.getSheet(sheetInfo.toString().substring(0, 31));
						} else {
							sheet = wb.getSheet(sheetInfo.toString());
						}
					} else {
						sheet = wb.getSheetAt(0);
					}
				} else {
					sheet = null;
				}
				
				if(sheet != null) {
					int cellNum = 0;
					int rowNum = sheet.getLastRowNum();
					if(rowNum >= 0) {
						for(int rowIndex = 0; rowIndex <= rowNum; rowIndex++) {
							row = sheet.getRow(rowIndex);
							if(row == null) {
								continue;
							}
							nameCell = row.getCell(0);
							cellNum = row.getLastCellNum();
							if(cellNum < 1 || nameCell == null || StringUtils.isBlank(nameCell.toString())) {
								continue;
							}
							for(int celIndex = 1; celIndex <= cellNum; celIndex++) {
								valCell = row.getCell(celIndex);
								if(valCell == null || StringUtils.isBlank(valCell.toString())) {// 如果单元格为空或者其内容为空
									celIndex -= 1;
									break;
								}
								
								if(dataCount < celIndex) {// 如果数据的数目小于当前单元格的下标
									dataCount = celIndex;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fs != null) {
					fs.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dataCount;
	}
	
	/**
	 * <P>根据DTO获取同一个DTO的所有要保存的数据</P>
	 * <P>同一个DTO可能会保存多条数据</P>
	 * @param dtoName DTO名称
	 * @param idFlag 是否将主键信息放入查询条件中的标志
	 */
	public static Map<Integer, Map<String, Object>> getSameDTOAllDataByDto(String dtoName, boolean idFlag) {
		Map<Integer, Map<String, Object>> map = null;
		if(!StringUtils.isBlank(dtoName)) {
			String excelName = ActionTestUtils.getExcelFileOrSheetName(dtoName, false);
			String sheetName = ActionTestUtils.getExcelFileOrSheetName(dtoName, true);
			map = getSameDTOAllDataByExcel(dtoName, excelName, sheetName, idFlag);
		}
		return map;
	}
	
	
	
	/**
	 * <P>根据Excel获取同一个DTO的所有要保存的数据</P>
	 * <P>同一个DTO可能会保存多条数据</P>
	 * @param dtoName DTO名称
	 * @param excelName Excel文件名称
	 * @param sheetInfo 当前Excel文件中存放参数信息的sheet页的名称或者下标
	 * @param idFlag 是否将主键信息放入查询条件中的标志（true：要主键）
	 */
	public static Map<Integer, Map<String, Object>> getSameDTOAllDataByExcel(String dtoName, String excelName, Object sheetInfo, boolean idFlag) {
		Field pkField = null;
		InputStream is = null;
		POIFSFileSystem fs = null;
		HSSFRow row = null;
		HSSFCell valCell = null;
		HSSFCell nameCell = null;
		Map<Integer, Map<String, Object>> map = null;
		Map<String, Object> valMap = null;
		try {
			if(!idFlag) {
				pkField = ActionTestUtils.getPKField(dtoName);
			}
			if(!StringUtils.isBlank(excelName)) {
				int dataCount = getSameDTOTestDataCountByExcel(excelName, sheetInfo);
				if(dataCount > 0) {
					valMap = new HashMap<String, Object>();
					
					is = ExcelUtils.class.getClassLoader().getResourceAsStream(excelName);
					fs = new POIFSFileSystem(is);
					HSSFWorkbook wb = new HSSFWorkbook(fs);
					HSSFSheet sheet = null;
					
					if(sheetInfo == null) {
						sheet = wb.getSheetAt(0);
					} else if(sheetInfo instanceof Integer) {
						sheet = wb.getSheetAt((Integer) sheetInfo);
					} else if(sheetInfo instanceof String) {
						if(!StringUtils.isBlank(sheetInfo.toString())) {
							if(sheetInfo.toString().length() > 31) {
								sheet = wb.getSheet(sheetInfo.toString().substring(0, 31));
							} else {
								sheet = wb.getSheet(sheetInfo.toString());
							}
						} else {
							sheet = wb.getSheetAt(0);
						}
					} else {
						sheet = null;
					}
					
					if(sheet != null) {
						int rowNum = sheet.getLastRowNum();// 获取行数
						map = new HashMap<Integer, Map<String,Object>>();// 新建一个数据值存储对象
						// 循环每一个数据列
						for(int dataIndex = 1; dataIndex <= dataCount; dataIndex++) {
							valMap = new HashMap<String, Object>();
							for(int rowIndex = 0; rowIndex <= rowNum; rowIndex++) {// 循环每一行
								row = sheet.getRow(rowIndex);
								if(row != null) {
									nameCell = row.getCell(0);
									valCell = row.getCell(dataIndex);
									if(nameCell == null || StringUtils.isBlank(nameCell.toString()) ||
											valCell == null || StringUtils.isBlank(valCell.toString())) {
										continue;
									}
									
									if(!idFlag && (nameCell.toString().equals(pkField.getName()) || 
											 nameCell.toString().contains(pkField.getName()))) {
										continue;
									}
									valMap.put(nameCell.toString(), valCell.toString());
								}
							}
							map.put(dataIndex, valMap);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fs != null) {
					fs.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 
	 * <P>根据字段名称获取DTO存放数据的Excel文件中相应字段的所有值</P>
	 * @param dtoName DTO名称
	 * @param fieldName 当前DTO中需要获取参数值得字段名称
	 */
	public static List<String> getSameDTOFieldValueByDto(String dtoName, String fieldName) {
		String excelName = ActionTestUtils.getExcelFileOrSheetName(dtoName, false);
		String sheetName = ActionTestUtils.getExcelFileOrSheetName(dtoName, true);
		return getSameDTOFieldValueByExcel(excelName, sheetName, fieldName);
	}
	
	/**
	 * 
	 * <P>根据字段名称获取DTO存放数据的Excel文件中相应字段的所有值</P>
	 * @param excelName Excel文件名称
	 * @param sheetInfo 记录当前DTO参数的sheet页的名称或者下标
	 * @param fieldName 当前DTO中需要获取参数值得字段名称
	 */
	public static List<String> getSameDTOFieldValueByExcel(String excelName, Object sheetInfo, String fieldName) {
		List<String> fieldValList = null;
		InputStream is = null;
		POIFSFileSystem fs = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		int dataCount = 0;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		int rowNum = -1;
		try {
			if(!StringUtils.isBlank(excelName) && !StringUtils.isBlank(fieldName)) {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(excelName);
				fs = new POIFSFileSystem(is);
				wb = new HSSFWorkbook(fs);
				
				if(sheetInfo == null) {
					sheet = wb.getSheetAt(0);
				} else if(sheetInfo instanceof Integer) {
					sheet = wb.getSheetAt((Integer) sheetInfo);
				} else if(sheetInfo instanceof String) {
					if(!StringUtils.isBlank(sheetInfo.toString())) {
						if(sheetInfo.toString().length() > 31) {
							sheet = wb.getSheet(sheetInfo.toString().substring(0, 31));
						} else {
							sheet = wb.getSheet(sheetInfo.toString());
						}
					} else {
						sheet = wb.getSheetAt(0);
					}
				} else {
					sheet = null;
				}
				
				if(sheet != null) {
					rowNum = sheet.getLastRowNum();
					for(int rowIndex = 0; rowIndex <= rowNum; rowIndex++) {
						row = sheet.getRow(rowIndex);
						if(row != null) {
							cell = row.getCell(0);
							if(cell != null && (cell.toString().contains(fieldName) || cell.toString().equals(fieldName))) {
								break;
							}
						}
					}
					
					dataCount = getSameDTOTestDataCountByExcel(excelName, sheetInfo);
					if(row != null) {
						fieldValList = new ArrayList<String>();
						for(int cellIndex = 1; cellIndex <= dataCount; cellIndex++) {
							cell = row.getCell(cellIndex);
							if(cell == null) {
								fieldValList.add("");
							} else {
								fieldValList.add(cell.toString());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fs != null) {
					fs.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return fieldValList;
	}
	
	/**
	 * 
	 * <P>根据DTO名称和数据所在的列号信息获取DTO多组数据中的某一个查询条件对象/P>
	 * @param dtoName 相应DTO名称（全类名）
	 * @param colIndex 某一个具体参数所在的列号
	 * @param idFlag 是否将DTO的数据数据放入查询条件中（因为想机构集之类的DTO，如果将主键数据放入查询条件中，查询的结果将更加的准确，不容易造成数据的误删）
	 */
	public static DetachedCriteria getSameDTOOneDetachedCriteria(String dtoName, int colIndex, boolean idFlag) {
		DetachedCriteria dc = null;
		if(!StringUtils.isBlank(dtoName) && colIndex > 0) {
			String excelName = ActionTestUtils.getExcelFileOrSheetName(dtoName, false);
			String sheetName = ActionTestUtils.getExcelFileOrSheetName(dtoName, true);
			dc = getSameDTOOneDetachedCriteria(dtoName, excelName, sheetName, colIndex, idFlag);
		}
		return dc;
	}
	
	/**
	 * 
	 * <P>根据Excel文件以及sheet页信息获取DTO多组数据中的某一个查询条件对象/P>
	 * @param dtoName 相应DTO名称
	 * @param excelName 存放参数信息的Excel文件名称
	 * @param sheetNameOrIndex 存放多组数据的sheet页的信息（名称或者下标，默认取第一个sheet页）
	 * @param colIndex 某一个具体参数所在的列号
	 * @param idFlag 是否将DTO的数据数据放入查询条件中（因为想机构集之类的DTO，如果将主键数据放入查询条件中，查询的结果将更加的准确，不容易造成数据的误删）
	 */
	public static DetachedCriteria getSameDTOOneDetachedCriteria(String dtoName, String excelName, Object sheetNameOrIndex, int colIndex, boolean idFlag) {
		DetachedCriteria dc = null;
		InputStream is = null;
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell nameCell = null;
		HSSFCell valCell = null;
		Field pkField = null;
		try {
			if(!StringUtils.isBlank(dtoName) && colIndex > 0 && !StringUtils.isBlank(excelName)) {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(excelName);
				fs = new POIFSFileSystem(is);
				wb = new HSSFWorkbook(fs);
				
				if(sheetNameOrIndex == null) {
					sheet = wb.getSheetAt(0);
				} else if(sheetNameOrIndex instanceof Integer) {
					sheet = wb.getSheetAt((Integer) sheetNameOrIndex);
				} else if(sheetNameOrIndex instanceof String) {
					if(!StringUtils.isBlank(sheetNameOrIndex.toString())) {
						if(sheetNameOrIndex.toString().length() > 31) {
							sheet = wb.getSheet(sheetNameOrIndex.toString().substring(0, 31));
						} else {
							sheet = wb.getSheet(sheetNameOrIndex.toString());
						}
					} else {
						sheet = wb.getSheetAt(0);
					}
				} else {
					sheet = null;
				}
				
				if(sheet != null) {
					int rowNum = sheet.getLastRowNum();
					if(rowNum >= 0) {
						dc = DetachedCriteria.forEntityName(dtoName);
						
						pkField = ActionTestUtils.getPKField(dtoName);
						
						for(int rowIndex = 0; rowIndex <= rowNum; rowIndex++) {
							row = sheet.getRow(rowIndex);
							if(row == null) {
								continue;
							}
							
							nameCell = row.getCell(0);
							if(nameCell == null) {
								continue;
							}
							
							valCell = row.getCell(colIndex);
							// 判断当前的字段信息是否是需要的字段信息
							if(nameCell != null && valCell != null
									&& !StringUtils.isBlank(valCell.toString())
									&& ActionTestUtils.isDBField(dtoName, nameCell.toString())
									&& !"windowId".equals(nameCell.toString()) 
									&& nameCell.toString().indexOf("lastUpdateDate") < 0)
							{
								if(!idFlag && nameCell.toString().indexOf(pkField.getName()) > 0) {
									continue;
								} else {
									if(ActionTestUtils.isDateField(dtoName, nameCell.toString())) {
										dc.add(Restrictions.eq(nameCell.toString().substring(nameCell.toString().indexOf(".")+1), TypeParse.parseDate(valCell.toString())));
									} else {
										dc.add(Restrictions.eq(nameCell.toString().substring(nameCell.toString().indexOf(".")+1), valCell.toString()));
									}
								}
							}
						}// for
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fs != null) {
					fs.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dc;
	}
	
	/**
	 * 
	 * <P>根据DTO名称获取同一个DTO多组数据的查询条件</P>
	 * @param dtoName DTO名称
	 * @param idFlag 是否将主键信息放入查询条件中的标志
	 */
	public static List<DetachedCriteria> getSameDTOAllDetachedCriteria(String dtoName, boolean idFlag) {
		String excelName = ActionTestUtils.getExcelFileOrSheetName(dtoName, false);
		String sheetName = ActionTestUtils.getExcelFileOrSheetName(dtoName, true);
		return getSameDTOAllDetachedCriteria(dtoName, excelName, sheetName, idFlag);
	}
	
	/**
	 * 
	 * <P>根据excel文件获取同一个DTO多组数据的查询条件</P>
	 * @param dtoName DTO名称
	 * @param excelName Execl文件名称
	 * @param sheetNameOrIndex Sheet页的名称或者是下标
	 * @param idFlag 是否将主键信息放入查询条件中的标志
	 */
	public static List<DetachedCriteria> getSameDTOAllDetachedCriteria(String dtoName, String excelName, Object sheetNameOrIndex, boolean idFlag) {
		DetachedCriteria dc = null;
		List<DetachedCriteria> dcList = new ArrayList<DetachedCriteria>();
		if(!StringUtils.isBlank(dtoName) && !StringUtils.isBlank(excelName)) {
			int dataCount = getSameDTOTestDataCountByExcel(excelName, sheetNameOrIndex);
			if(dataCount > 0) {
				for(int colIndex = 1; colIndex <= dataCount; colIndex++) {
					dc = getSameDTOOneDetachedCriteria(dtoName, excelName, sheetNameOrIndex, colIndex, idFlag);
					if(dc != null) {
						dcList.add(dc);
					}
				}
			}
		}
		return dcList;
	}
	
	/**
	 * 
	 * <P>将Excel中的数据转换成DTO对象</P>
	 * 
	 * @param dtoMap 定义了dto名称以及是否将主键放入标志的Map
	 * @author Liutao
	 *
	 */
	public static List<Object> getObjectListByDto(Map<String, Boolean> dtoMap) {
		List<Object> objectList = null;
		if(dtoMap != null && dtoMap.size() > 0) {
			String excelName = null;
			String sheetName = null;
			objectList = new ArrayList<Object>();
			Set<Map.Entry<String, Boolean>> ens = dtoMap.entrySet();
			for(Map.Entry<String, Boolean> en : ens) {
				excelName = ActionTestUtils.getExcelFileOrSheetName(en.getKey(), false);
				sheetName = ActionTestUtils.getExcelFileOrSheetName(en.getKey(), true);
				
				objectList.addAll(getObjectListByExcel(en.getKey(), excelName, sheetName, en.getValue()));
			}
		}
		return objectList;
	}
	
	/**
	 * 
	 * <P>将Excel中的数据转换成DTO对象</P>
	 * 
	 * @param dtoName DTO名称
	 * @param idFlag 是否将主键放入的标志
	 * @author Liutao
	 *
	 */
	public static List<Object> getObjectListByDto(String dtoName, boolean idFlag) {
		if(!StringUtils.isBlank(dtoName)) {
			String excelName = ActionTestUtils.getExcelFileOrSheetName(dtoName, false);
			String sheetName = ActionTestUtils.getExcelFileOrSheetName(dtoName, true);
			return getObjectListByExcel(dtoName, excelName, sheetName, idFlag);
		}
		return null;
	}
	
	/**
	 * 
	 * <P>将Excel中的数据转换成DTO对象</P>
	 * 
	 * @param dtoName DTO名称
	 * @param excelName Excel文件名称
	 * @param sheetNameOrIndex sheet下标或者名称
	 * @param idFlag 是否将主键放入的标志
	 * @author Liutao
	 *
	 */
	public static List<Object> getObjectListByExcel(String dtoName, String excelName, Object sheetNameOrIndex, boolean idFlag) {
		JoinColumn jc = null;
		Object object = null;
		Object fkObj = null;
		Field pkField = null;
		List<Object> objectList = null;
		InputStream is = null;
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell nameCell = null;
		HSSFCell valCell = null;
		int index = 0;
		Field field = null;
		String fieldName = null;
		try {
			if(!StringUtils.isBlank(dtoName) && !StringUtils.isBlank(excelName)) {
				is = ExcelUtils.class.getClassLoader().getResourceAsStream(excelName);
				fs = new POIFSFileSystem(is);
				wb = new HSSFWorkbook(fs);
				
				if(sheetNameOrIndex == null) {
					sheet = wb.getSheetAt(0);
				} else if(sheetNameOrIndex instanceof Integer) {
					sheet = wb.getSheetAt((Integer) sheetNameOrIndex);
				} else if(sheetNameOrIndex instanceof String) {
					if(!StringUtils.isBlank(sheetNameOrIndex.toString())) {
						if(sheetNameOrIndex.toString().length() > 31) {
							sheet = wb.getSheet(sheetNameOrIndex.toString().substring(0, 31));
						} else {
							sheet = wb.getSheet(sheetNameOrIndex.toString());
						}
					} else {
						sheet = wb.getSheetAt(0);
					}
				} else {
					sheet = null;
				}
				
				if(sheet != null) {
					objectList = new ArrayList<Object>();
					int rowNum = sheet.getLastRowNum();
					int dataCount = getSameDTOTestDataCountByExcel(excelName, sheetNameOrIndex);
					if(rowNum >= 0 && dataCount > 0) {
						for(int cellIndex = 1; cellIndex <= dataCount; cellIndex++) {// loop cell
							object = Class.forName(dtoName).newInstance();
							for(int rowIndex = 0; rowIndex <= rowNum; rowIndex++) {// loop row
								row = sheet.getRow(rowIndex);
								nameCell = row.getCell(0);
								valCell = row.getCell(cellIndex);
								if(nameCell != null && StringUtils.isNotBlank(nameCell.toString())){
									fieldName = nameCell.toString().replace("serviceParam.", "").trim();
									if(valCell == null || StringUtils.isBlank(valCell.toString())
											|| !ActionTestUtils.isDBField(dtoName, fieldName)) {
										continue;
									}
									index = fieldName.indexOf(".");
									if(index > 0) {// 判断当前的字段中是否还有点号存在
										fieldName = fieldName.substring(0, index);
									}
									
									field = Class.forName(dtoName).getDeclaredField(fieldName);
									if(field != null) {
										jc = field.getAnnotation(JoinColumn.class);
										if(jc != null) {// 是外键字段
											 fkObj = field.getType().newInstance();
											 pkField = ReflectOperation.getPrimaryKeyField(field.getType());
											 ReflectOperation.setFieldValue(fkObj, pkField.getName(), valCell.toString());
											 
											 ReflectOperation.setFieldValue(object, fieldName, fkObj);
										} else {// 不是外键字段
											ReflectOperation.setFieldValue(object, fieldName, valCell.toString());
										}
									}
								}
							}
						}// loop row
						objectList.add(object);
					}// loop cell
				}
			}
		} catch (Exception e) {
			objectList = null;// 清空数据
			e.printStackTrace();
		} finally {
			try {
				if(fs != null) {
					fs.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
				objectList = null;// 清空数据
				e2.printStackTrace();
			}
		}
		return objectList;
	}
	
	
	
	
	
	
	/****************************************Excel同一个DTO多条数据的操作end****************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
