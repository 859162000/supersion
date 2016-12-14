package framework.services.interfaces.imps;

import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.hibernate.criterion.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowInstance;


public class ParserExcelPoi {

	private static String defaultSheetName = "Sheet1";
    public StringBuilder error = new StringBuilder();
    
	/**
	 * 读取03Excel文件
	 */
	class Excel2003Reader implements HSSFListener{

		/** 
		 * 抽象Excel2003读取器，通过实现HSSFListener监听器，采用事件驱动模式解析excel2003 
		 * 中的内容，遇到特定事件才会触发，大大减少了内存的使用。 
		 */  
	    private int minColumns = -1;  
	    private POIFSFileSystem fs;  
	    private int lastRowNumber;  
	    private int lastColumnNumber;  
	    private boolean outputFormulaValues = true;  
	    private SheetRecordCollectingListener workbookBuildingListener;  
	    private HSSFWorkbook stubWorkbook;   // excel2003工作薄  
	    private SSTRecord sstRecord;  
	    private FormatTrackingHSSFListener formatListener;  
	    private int sheetIndex = -1;   //表索引  ，处理dao第几张表
	    private int sheetIndex2 = -1;   //表索引，将需要处理的表，加入数组
	    private int sheetIndex3 = -1;   //表索引  ,判断是否进入
	    private BoundSheetRecord[] orderedBSRs;  
	    private ArrayList boundSheetRecords = new ArrayList();  
	    private int nextRow;  
	    private int nextColumn;  
	    private boolean outputNextStringRecord;  
	    private int curRow = 0; //当前行  
	    private String sheetName;  
	    private List<String> rowlist = new ArrayList<String>(); 
	    
	    private ArrayList<ShowField> showFields = new ArrayList<ShowField>();
	    public ArrayList<Object> insertDataList = new ArrayList<Object>();
	    private String sheetNames;  
	    private String tableName;
	    
	    private String afterIgnorSeg;
	    private int startLine;
	    private int startCol;
	    
	    boolean showForeignId;
	    boolean showHeader;
	    private Workbook workbook;
	    private Sheet hssfSheet;
	    
	    //遍历excel下所有的sheet 
	    public void process(ArrayList<ShowField> showFields,String sessionFactory, String tableName, String path, String sheetNames, 
	    		boolean showForeignId, boolean showHeader, String afterIgnorSeg, int startLine, int startCol) throws IOException {
	        this.fs = new POIFSFileSystem(new FileInputStream(path));
	        this.showFields = showFields;
	        this.tableName = tableName;
	        
	        this.afterIgnorSeg = afterIgnorSeg;
	        this.startLine = startLine;
	        this.startCol = startCol;
	        this.sheetNames = sheetNames;
		    this.showForeignId = showForeignId;
		    this.showHeader = showHeader;
		    workbook = new HSSFWorkbook(fs);
		    
	        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);  
	        formatListener = new FormatTrackingHSSFListener(listener);  
	        HSSFEventFactory factory = new HSSFEventFactory();  
	        HSSFRequest request = new HSSFRequest();  
	        if (outputFormulaValues) {  
	            request.addListenerForAllRecords(formatListener);  
	        } else {  
	            workbookBuildingListener = new SheetRecordCollectingListener(formatListener);  
	            request.addListenerForAllRecords(workbookBuildingListener);  
	        }  
	        factory.processWorkbookEvents(request, fs);  
	    }
	      
	    // HSSFListener 监听方法，处理 Record 
	    public void processRecord(Record record) {  
	        int thisRow = -1;  
	        int thisColumn = -1;  
	        String thisStr = null;  
	        String value = null;  
	        switch (record.getSid()) {  
	            case BoundSheetRecord.sid:  
	    	        String sheet = ((BoundSheetRecord)record).getSheetname();
	    	        sheetIndex2++;
	    	        
	    	        if(sheetNames == null || sheetNames.equals(""))
	    	        	sheetNames = getSheetName(sheetNames);
	    	        
	    	        if(sheetNames != null && !"".equals(sheetNames) && sheetNames.equals(sheet)){
		                boundSheetRecords.add(record);
		                sheetIndex3 = sheetIndex2;
                    }
	    	        else if(sheetIndex2 == 0 && sheetNames == null){
	    	        	boundSheetRecords.add(record);
	    	        	sheetIndex3 = -2;
	    	        }
	    	        hssfSheet = workbook.getSheet(sheetNames);
	                break;
	                
	            case BOFRecord.sid:  
	                BOFRecord br = (BOFRecord) record;  
	                if (br.getType() == BOFRecord.TYPE_WORKSHEET) {  
	                    // 如果有需要，则建立子工作薄  
	                    if (workbookBuildingListener != null && stubWorkbook == null) {  
	                        stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();  
	                    }  
	                    sheetIndex++;  
	                    if(sheetIndex == sheetIndex3){
		                    if (orderedBSRs == null) {  
		                        orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);  
		                    }  
		                    sheetName = orderedBSRs[0].getSheetname();
	                    }else if(sheetIndex == 0 && sheetIndex3 == -2){
		                    if (orderedBSRs == null) {  
		                        orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);  
		                    }  
		                    sheetName = orderedBSRs[0].getSheetname();
	                    }
	                }  
	                break;  
	      
	            case SSTRecord.sid:  
	                sstRecord = (SSTRecord) record;  
	                break;  
	      
	            case BlankRecord.sid:  
	                BlankRecord brec = (BlankRecord) record;  
	                thisRow = brec.getRow();  
	                thisColumn = brec.getColumn();  
	                thisStr = "";  
	                rowlist.add(thisColumn, getCellValue(thisStr,afterIgnorSeg)); 
	                break;  
	            case BoolErrRecord.sid: //单元格为布尔类型  
	                BoolErrRecord berec = (BoolErrRecord) record;  
	                thisRow = berec.getRow();  
	                thisColumn = berec.getColumn();  
	                thisStr = berec.getBooleanValue()+"";  
	                rowlist.add(thisColumn,  getCellValue(thisStr,afterIgnorSeg));
	                break;  
	      
	            case FormulaRecord.sid: //单元格为公式类型  
	                FormulaRecord frec = (FormulaRecord) record;  
	                thisRow = frec.getRow();  
	                thisColumn = frec.getColumn();  
	                if (outputFormulaValues) {  
	                    if (Double.isNaN(frec.getValue())) {  
	                        // Formula result is a string  
	                        // This is stored in the next record  
	                        outputNextStringRecord = true;  
	                        nextRow = frec.getRow();  
	                        nextColumn = frec.getColumn();  
	                    } else {  
	                        thisStr = formatListener.formatNumberDateCell(frec);  
	                    }  
	                } else {  
	                    thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook,  
	                            frec.getParsedExpression()) + '"';  
	                }  
	                rowlist.add(thisColumn, getCellValue(thisStr,afterIgnorSeg));
	                break;  
	            case StringRecord.sid: // 单元格中公式的字符串  
	                if (outputNextStringRecord) {  
	                    StringRecord srec = (StringRecord) record;  
	                    thisStr = srec.getString();  
	                    thisRow = nextRow;  
	                    thisColumn = nextColumn;  
	                    outputNextStringRecord = false;  
	                }  
	                break;  
	            case LabelRecord.sid:  
	                LabelRecord lrec = (LabelRecord) record;  
	                curRow = thisRow = lrec.getRow();  
	                thisColumn = lrec.getColumn();  
	                value = lrec.getValue().trim();  
	                value = value.equals("")?"":value;  
	                this.rowlist.add(thisColumn,  getCellValue(value,afterIgnorSeg));
	                break;  
	            case LabelSSTRecord.sid:  // 单元格为字符串类型  
	                LabelSSTRecord lsrec = (LabelSSTRecord) record;  
	                curRow = thisRow = lsrec.getRow();  
	                thisColumn = lsrec.getColumn();  
	                if (sstRecord == null) {  
	                    rowlist.add(thisColumn, "");  
	                } else {  
	                    value =  sstRecord  
	                    .getString(lsrec.getSSTIndex()).toString().trim();  
	                    value = value.equals("")?"":value;  
	                    rowlist.add(thisColumn, getCellValue(value,afterIgnorSeg)); 
	                }  
	                break;  
	            case NumberRecord.sid:  //单元格为数字类型  
	                NumberRecord numrec = (NumberRecord) record;  
	                curRow = thisRow = numrec.getRow();  
	                thisColumn = numrec.getColumn(); 
	                
	                //用poi的常用机制取单元格的类型，判断是否为时间日期。
	                if(hssfSheet == null) break;
	                Cell cell = hssfSheet.getRow(curRow).getCell(thisColumn);
	                System.out.println(curRow+" "+thisColumn);
	                if(curRow==99 && thisColumn==10){
	                	System.out.print("begin");
	                }
	                try{
	                	if(DateUtil.isCellDateFormatted(cell)){
		                	value = (new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(numrec.getValue())));
						}else{
			                value = formatListener.formatNumberDateCell(numrec).trim(); 
						}
	                }catch(Exception ee){
	                	value = cell.getRichStringCellValue().toString();
	                }
	                /*zhouqin
	                if(DateUtil.isCellDateFormatted(cell)){
	                	value = (new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(numrec.getValue())));
					}else{
		                value = formatListener.formatNumberDateCell(numrec).trim(); 
					}
					*/
	                rowlist.add(thisColumn,getCellValue(value,afterIgnorSeg));  // 向容器加入列值  
	                break;  
	            default:  
	                break;  
	        }  
	  
	        // 遇到新行的操作  
	        if (thisRow != -1 && thisRow != lastRowNumber) {  
	            lastColumnNumber = -1;  
	        }  
	  
	        // 空值的操作  
	        if (record instanceof MissingCellDummyRecord) {  
	            MissingCellDummyRecord mc = (MissingCellDummyRecord) record;  
	            curRow = thisRow = mc.getRow();  
	            thisColumn = mc.getColumn();  
	            rowlist.add(thisColumn,""); 
	        }  
	  
	        // 更新行和列的值  
	        if (thisRow > -1)  
	            lastRowNumber = thisRow;  
	        if (thisColumn > -1)  
	            lastColumnNumber = thisColumn;  
	  
	        // 行结束时的操作  
	        if (record instanceof LastCellOfRowDummyRecord && (sheetIndex == sheetIndex3 || sheetIndex3 == -2)) {
	        	System.out.println("------------Row "+curRow+" End!");
	            if (minColumns > 0) {
	                // 列值重新置空  
	                if (lastColumnNumber == -1) {  
	                    lastColumnNumber = 0;  
	                }
	            }
	            
	            lastColumnNumber = -1;
		        Object tableObject;
		        if((curRow == this.startLine || curRow > 10000000) && showHeader){
	    		    int rowSize = rowlist.size() - startCol;
	    		    if(rowSize < (showFields.size())) {
	    		    	addHanderError(sheetNames);
        				curRow = 100000000; // 跳出，提示插入表格不符合规则
	    		    }
	    		    
	        		for (int i = 0; i < rowSize && i< showFields.size(); i++) {
	        			String fieldShowName = showFields.get(i).getFieldShowName();
	        			String fieldExcelName = rowlist.get(startCol+i);
	        			if(!fieldShowName.equals(fieldExcelName)){
		    		    	addHanderError(sheetNames);
	        				curRow = 100000000; // 跳出，提示插入表格不符合规则
	        			}
			        }
	        	}
		        else { // if(curRow > this.startLine) {
	        		try{
	        			tableObject = getRows(curRow, rowlist, showFields, tableName, startCol, showForeignId);
						if(tableObject==null){
							//跳出，提示插入表格不符合规则-->添加一个if判断，取得所有错误列
						}else{
							insertDataList.add(tableObject);
						}
					}catch (Exception e) {
						e.printStackTrace();
						addError(curRow+1, 0, e.getMessage());
					}
				}
		        rowlist.clear();
	        } 
	    }
	}

    
	/**
	 * 读取07Excel文件
	 *  boolean showForeignId, boolean showHeader, boolean deleteOld,
	 */
	class Excel2007Reader extends DefaultHandler{  
	   
	    private SharedStringsTable sst; //共享字符串表  
	    private String lastContents;  //上一次的内容  
	    private boolean nextIsString;  
	    private int sheetIndex = -1;
	    private int curRow = 0;  //当前行
		private int curCol = 0;  //当前列  
		private int preCol = 0;	 //上一列列索引
	    private boolean dateFlag;  //日期标志
	    private boolean numberFlag;  //数字标志  
	    private boolean isTElement;  
    	long Time=0;
		private ArrayList<String> rowlist = new ArrayList<String>();
	    
		private String exceptionMessage; // 异常信息，如果为空则表示没有异常
		private CellDataType nextDataType = CellDataType.SSTINDEX; //单元格数据类型，默认为字符串类型
		private final DataFormatter formatter = new DataFormatter();
		private short formatIndex;
		private String formatString;
		private StylesTable stylesTable;
		
		
	    private ArrayList<ShowField> showFields = new ArrayList<ShowField>();
	    public ArrayList<Object> insertDataList = new ArrayList<Object>();
	    private String sheetNames;  
	    private String tableName;
	    private Class<?> targetType;
	    private String afterIgnorSeg;
	    private int startLine;
	    private int startCol;
	    boolean showForeignId;
	    boolean showHeader;
	    /**只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3 
	    public void processOneSheet(String filename,int sheetId) throws Exception {  
	        OPCPackage pkg = OPCPackage.open(filename);  
	        XSSFReader r = new XSSFReader(pkg);  
	        SharedStringsTable sst = r.getSharedStringsTable();  
	        XMLReader parser = fetchSheetParser(sst);  
	          
	        // 根据 rId# 或 rSheet# 查找sheet  
	        InputStream sheet2 = r.getSheet("rId"+sheetId);  
	        sheetIndex++;  
	        InputSource sheetSource = new InputSource(sheet2);  
	        parser.parse(sheetSource);  
	        sheet2.close();  
	    }  
	     */ 
    	
	    //遍历工作簿中所有的电子表格 
	    public void process(ArrayList<ShowField> showFields,String sessionFactory, String tableName, String path, String sheetNames, boolean showForeignId, 
	    		boolean showHeader, String afterIgnorSeg, int startLine, int startCol) throws Exception {  	        
	        this.showFields = showFields;
	        this.tableName = tableName;
	        this.sheetNames = sheetNames;
	        this.targetType = targetType;
	        this.afterIgnorSeg = afterIgnorSeg;
	        this.startLine = startLine;
	        this.startCol = startCol;
		    this.showForeignId = showForeignId;
		    this.showHeader = showHeader;
	        
	        OPCPackage pkg = OPCPackage.open(path);  
	        XSSFReader r = new XSSFReader(pkg);  
			stylesTable = r.getStylesTable();
	        SharedStringsTable sst = r.getSharedStringsTable();  
	        XMLReader parser = fetchSheetParser(sst);
	        Iterator<InputStream> sheets = r.getSheetsData(); 
	        
            if(sheetNames == null || "".equals(sheetNames)){ 
	            if(sheets.hasNext()){ 
		            curRow = this.startLine;
		            InputStream sheet = sheets.next();  
		            InputSource sheetSource = new InputSource(sheet);  
		            parser.parse(sheetSource); 
		            sheet.close();
	            };
            }else{
            	/*//lanyuesheng   r.getSheet("rId"+sheetNames)是根据Sheet的ID来获取，ID的值，固定格式为："rId"+index（索引）。
            	 * 上次修改者：zhouqin*/
            	
            	XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(path));
            	int index = xssfWorkbook.getSheetIndex(sheetNames)+1;
            	if(index<1){
            		error.append("Sheet名应为："+sheetNames);
            		return;
            	}else{
		            InputStream sheet = r.getSheet("rId"+index); 
		            InputSource sheetSource = new InputSource(sheet);  
		            parser.parse(sheetSource); 
		            sheet.close();
            	}
            	
        	} 
	    }  
	  
	    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {  
	        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");  
	        this.sst = sst;  
	        parser.setContentHandler(this);  
	        return parser;  
	    }  
	 
		
	    public void startElement(String uri, String localName, String name,Attributes attributes) throws SAXException { 

	        if ("c".equals(name)) {  
	        	String rowStr = attributes.getValue("r");
	        	curCol = this.getRowIndex(rowStr);
	        	if(startCol != 0 && curCol <= startCol)
	        		curCol = this.startCol+1;
	        	
	            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true  
	            String cellType = attributes.getValue("t");  
	            if ("s".equals(cellType)) {  
	                nextIsString = true;  
	            } else {  
	                nextIsString = false;  
	            }

	         // 设定单元格类型
				this.setNextDataType(attributes);
	    		
	        }  
	        //当元素为t时  
	        if("t".equals(name)){  
	            isTElement = true;  
	        } else {  
	        	isTElement = false; 
	        }  
	        
	        lastContents = "";  // 置空
	    }  
	  
	    public void endElement(String uri, String localName, String name) {  
	        // 根据SST的索引值的到单元格的真正要存储的字符串     这时characters()方法可能会被调用多次  
	        if (nextIsString) {  
	                int idx = Integer.parseInt(lastContents);  
	                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();  
	                nextIsString = false;
	        }  
	        
	        //t元素也包含字符串  
	        if(isTElement){  
	            String value = lastContents.trim();  

	            int cols = curCol-preCol;   
                if (cols>1){   
                    for (int i = 0;i < cols-1;i++){   
                       rowlist.add(preCol,""); 
//                    	strb.append("'',");  
                    }   
                }
                preCol = curCol;
	            
	            rowlist.add(curCol-1, getCellValue(value,afterIgnorSeg));  
	            curCol++;  
	            isTElement = false;  

	            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引  
	            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符  
	        } 
	        else if ("v".equals(name)) {  
	            String value = lastContents.trim();  
//	            value = value.equals("")?"":value;  
	            
	            int cols = curCol-preCol;   
                if (cols>1){   
                    for (int i = 0;i < cols-1;i++){   
                       rowlist.add(preCol,""); 
                    }   
                }
                preCol = curCol;


    			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
    			value = this.getDataValue(lastContents.trim(), "");
	            
	            rowlist.add(curCol-1, getCellValue(value,afterIgnorSeg));  
	            curCol++;  
	        }
	        else if (name.equals("row")) {
		        //如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法  
	        	Object tableObject;
				try {
				    if((curRow==this.startLine || curRow>10000000) && showHeader){
		    		    int rowSize = rowlist.size()-startCol;
//		    		    int rowSize = rowlist.size()-1-startCol;
//		    		    if(rowSize<(showFields.size()-1)){
		    		    if(rowSize<(showFields.size())){
		    		    	addHanderError(sheetNames);
	        				curRow = 100000000;//跳出，提示插入表格不符合规则
		    		    }
		        		for (int i = 0; i<rowSize && i<showFields.size(); i++) {
		        			String fieldShowName = showFields.get(i).getFieldShowName();
		        			String fieldExcelName = rowlist.get(startCol+i);
		        			if(!fieldShowName.equals(fieldExcelName)){
		        				addHanderError(sheetNames);
		        				curRow = 100000000;//跳出，提示插入表格不符合规则
		        			}
				        }
		        	}else{
	        			tableObject = getRows(curRow,rowlist,showFields,tableName,startCol,showForeignId);
						if(tableObject==null){
							//跳出，提示插入表格不符合规则-->添加一个if判断，取得所有错误列
						}else{
							insertDataList.add(tableObject);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	            rowlist.clear();
                curRow++;
                curCol = 0; 
                preCol = 0; 
	        }
	    }  

		/**
		 * 对解析出来的数据进行类型处理
		 * 
		 * @param value
		 *            单元格的值（这时候是一串数字）
		 * @param thisStr
		 *            一个空字符串
		 * @return
		 */
		@SuppressWarnings("deprecation")
		public String getDataValue(String value, String thisStr)
		{
			switch (nextDataType)
			{
				// 这几个的顺序不能随便交换，交换了很可能会导致数据错误
				case BOOL:
					char first = value.charAt(0);
					thisStr = first == '0' ? "FALSE" : "TRUE";
					break;
				case ERROR:
					thisStr = "\"ERROR:" + value.toString() + '"';
					break;
				case FORMULA:
					thisStr = '"' + value.toString() + '"';
					break;
				case INLINESTR:
					XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());

					thisStr = rtsi.toString();
					rtsi = null;
					break;
				case SSTINDEX:
					thisStr = value;
					/*zhouqin
					String sstIndex = value.toString();
					try
					{
						int idx = Integer.parseInt(sstIndex);
						XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));
						thisStr = rtss.toString();
						rtss = null;
					}
					catch (NumberFormatException ex)
					{
						thisStr = value.toString();
					}
					*/
					break;
				case NUMBER:
					if (formatString != null)
					{
						thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
					}
					else
					{
						thisStr = value;
					}

					thisStr = thisStr.replace("_", "").trim();
					break;
				case DATE:
					thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);

					// 对日期字符串作特殊处理
					//thisStr = thisStr.replace("", "T");
					break;
				default:
					thisStr = "";

					break;
			}

			return thisStr;
		}
		/**
		 * 处理数据类型
		 * 
		 * @param attributes
		 */
		public void setNextDataType(Attributes attributes)
		{
			nextDataType = CellDataType.NUMBER;
			formatIndex = -1;
			formatString = null;
			String cellType = attributes.getValue("t");
			String cellStyleStr = attributes.getValue("s");

			if ("b".equals(cellType))
			{
				nextDataType = CellDataType.BOOL;
			}
			else if ("e".equals(cellType))
			{
				nextDataType = CellDataType.ERROR;
			}
			else if ("inlineStr".equals(cellType))
			{
				nextDataType = CellDataType.INLINESTR;
			}
			else if ("s".equals(cellType))
			{
				nextDataType = CellDataType.SSTINDEX;
			}
			else if ("str".equals(cellType))
			{
				nextDataType = CellDataType.FORMULA;
			}

			if (cellStyleStr != null)
			{
				int styleIndex = Integer.parseInt(cellStyleStr);
				XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
				formatIndex = style.getDataFormat();
				formatString = style.getDataFormatString();

				if ("m/d/yy" == formatString)
				{
					nextDataType = CellDataType.DATE;
					formatString = "yyyy-MM-dd";
				}

				if (formatString == null)
				{
					nextDataType = CellDataType.NULL;
					formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
				}
			}
		}
	    public void characters(char[] ch, int start, int length) throws SAXException {  
	        //得到单元格内容的值  
	        lastContents += new String(ch, start, length);  
	    }

	    public int getCurRow() {
			return curRow;
		}

		public void setCurRow(int curRow) {
			this.curRow = curRow;
		}

		public int getRowIndex(String rowStr){
			rowStr = rowStr.replaceAll("[^A-Z]", "");
			byte[] rowAbc = rowStr.getBytes();
			int len = rowAbc.length;
			float num = 0;
			for (int i=0;i<len;i++){
				num += (rowAbc[i]-'A'+1)*Math.pow(26,len-i-1 );
			}
			return (int) num;
		}
	}
	
	/**
	 * 导出表数据 	07Excel
	 */
	public int writeDataTo07Excel(ShowInstance showInstance, String path, String sheetNames,
			Object retObj, Class<?> targetType,
			int cacheLine, boolean showForeignId, boolean showHeader,
			int startLine, int startCol, ByteArrayOutputStream baos, String strDateFormat,String modelPath)throws Exception {
		int rowaccess = 1000; // 内存中缓存记录行数
		
		SXSSFWorkbook wwb = null;
		if(StringUtils.isBlank(modelPath)){
			wwb = new SXSSFWorkbook(rowaccess); // 创建excel文件对象 
		}
		else{
			
		}
		
        Sheet sheet = null;
        
		int nLine = 0;
		int nCol = 0;
		int sheetNum = 0;
		for(Object lineObj : (ArrayList<Object>) retObj) { // 每行记录
			if(nLine % cacheLine == 0) { // excel页面首行
				String[] sheets = null;
				if(sheetNames != null) {
					sheets = sheetNames.split(",");
				}
				
				String sheetName = getSheetName(sheets, sheetNum);
				sheetNum++;
				
		        sheet = wwb.createSheet(sheetName);
				
		        nLine = startLine;
				if(showHeader) {
					Row rowTitle = sheet.createRow(nLine);
					nCol=startCol;
					
					for(ShowField showField : showInstance.getShowFieldList()) {
						Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
						//if(!showField.getSingleTag().equals(ApplicationManager.getSingleTagHidden()) || (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable())) { // 生成各字段显示名
						if(showField.isListVisible()) { // 生成各字段显示名
							Cell cell = rowTitle.createCell(nCol);
							cell.setCellValue(showField.getFieldShowName());
							nCol++;
						}
					}
					
					nLine++;
				}
			}
			
			nCol = startCol;
			Row rowData = sheet.createRow(nLine);
			for(ShowField showField : showInstance.getShowFieldList()) { // 生成各字段值
				Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
				
				//if(!showField.getSingleTag().equals(ApplicationManager.getSingleTagHidden()) || (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable())) { // 生成各字段显示名
				if(showField.isListVisible()) { // 生成各字段显示名
					String fieldValue = "";
					if(field != null) {
						String tagMethodName = null; // 检查是否对应取列表方法
						if(field.isAnnotationPresent(IColumn.class)){
							IColumn iColumn = field.getAnnotation(IColumn.class);
							if(!StringUtils.isBlank(iColumn.tagMethodName())) {
								tagMethodName = iColumn.tagMethodName();
							}
						}

						Object value = ReflectOperation.getFieldValue(lineObj, field.getName()); // 取对象整对应字段值
						
						if(!StringUtils.isBlank(tagMethodName)){ //有列表方法对应,调用取得列表map
							Method method = ReflectOperation.getReflectMethod(Class.forName(RequestManager.getTName()),tagMethodName);
							Map<String,String> tagMap =  (Map<String,String>)method.invoke(Class.forName(RequestManager.getTName()));
							if(value != null)
								if(showForeignId)
									fieldValue = value.toString();
								else
									fieldValue = tagMap.get(value.toString());
						}
						else{ // 没有列表方法
							if(showField.getFieldTargetPrimaryKeyName() == null && value != null){//也没有对应表，直接取字段值
								if(value != null) {
									if(value.getClass().equals(Date.class) || value.getClass().equals(java.sql.Date.class) ){
										SimpleDateFormat sFormat = new SimpleDateFormat(strDateFormat);
										try {
											fieldValue = sFormat.format(value);
										} catch(Exception ex) {
											fieldValue = value.toString();
										}
									}
									if(showField.getReportUnitCode()!=0){
										if(showField.getReportUnitCode()==1){//元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1")).toString();
											}
										}
										else if(showField.getReportUnitCode()==2){//十元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10")).toString();
											}
										}
										else if(showField.getReportUnitCode()==3){//百元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100")).toString();
											}
										}
										else if(showField.getReportUnitCode()==4){//千元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==5){//万元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==6){//十万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==7){//百万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1000000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==8){//千万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10000000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==9){//亿
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100000000")).toString();
											}
										}
									}
									else {
										fieldValue = value.toString();
									}
								}
							}
							else { // 有关联表，则取关联表的关键名
								if(value != null) {
//									Object objVal = ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKeyName());
//									if(objVal != null)
									if(showForeignId){
										if(ReflectOperation.getPrimaryKeyValue(value)!=null){
											fieldValue = ReflectOperation.getPrimaryKeyValue(value).toString();
										}
									}
										
									else{
										if(ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKeyName())!=null){
											fieldValue = ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKeyName()).toString();
										}
									}
								}
								
							} 
						}
					}
					else if(field != null  && showForeignId) {
						Object value = ReflectOperation.getFieldValue(lineObj, field.getName());
						if(value != null) {
							fieldValue = value.toString();
						}
					}
					
					if(fieldValue != null) {
						Cell cell = rowData.createCell(nCol);
						cell.setCellValue(fieldValue);
					}
					nCol++;
				}
			}
			
			nLine++;
		}

		if(baos == null) {
			FileOutputStream out = new FileOutputStream(path);
			wwb.write(out);
			out.close();
		}
		else {
			wwb.write(baos);
		}
		return nLine - startLine;
	}
	
	public int writeMapDataTo07Excel(ShowInstance showInstance, String path, String sheetNames,
			List<Map<String,Object>> retObj, Class<?> targetType,
			int cacheLine, boolean showForeignId, boolean showHeader,
			int startLine, int startCol, ByteArrayOutputStream baos, String strDateFormat,String modelPath)throws Exception {
		int rowaccess = 1000; // 内存中缓存记录行数
		
		SXSSFWorkbook wwb = null;
		if(StringUtils.isBlank(modelPath)){
			wwb = new SXSSFWorkbook(rowaccess); // 创建excel文件对象 
		}
		else{
			
		}
        Sheet sheet = null;
		int nLine = 0;
		int nCol = 0;
		int sheetNum = 0;
		for(Map<String,Object> lineObj : (List<Map<String,Object>>) retObj) { // 每行记录
			if(nLine % cacheLine == 0) { // excel页面首行
				String[] sheets = null;
				if(sheetNames != null) {
					sheets = sheetNames.split(",");
				}
				
				String sheetName = getSheetName(sheets, sheetNum);
				sheetNum++;
				
		        sheet = wwb.createSheet(sheetName);
				
		        nLine = startLine;
				if(showHeader) {
					Row rowTitle = sheet.createRow(nLine);
					nCol=startCol;
					
					for(ShowField showField : showInstance.getShowFieldList()) {
						Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
						if(showField.isListVisible()) { // 生成各字段显示名
							Cell cell = rowTitle.createCell(nCol);
							cell.setCellValue(showField.getFieldShowName());
							nCol++;
						}
					}
					
					nLine++;
				}
			}
			
			nCol = startCol;
			Row rowData = sheet.createRow(nLine);
			for(ShowField showField : showInstance.getShowFieldList()) { // 生成各字段值
				Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
				if(showField.isListVisible()) { // 生成各字段显示名
					String fieldValue = "";
					if(field != null) {
						String tagMethodName = null; // 检查是否对应取列表方法
						if(field.isAnnotationPresent(IColumn.class)){
							IColumn iColumn = field.getAnnotation(IColumn.class);
							if(!StringUtils.isBlank(iColumn.tagMethodName())) {
								tagMethodName = iColumn.tagMethodName();
							}
						}
						Object value=null;
						if(lineObj.getClass().getName().equals("java.util.LinkedHashMap")){
							value = ((Map<String,Object>)lineObj).get(field.getName());
						}
						else{
							value = ReflectOperation.getFieldValue(lineObj, field.getName()); // 取对象整对应字段值
						}
						
						if(!StringUtils.isBlank(tagMethodName)){ //有列表方法对应,调用取得列表map
							Method method = ReflectOperation.getReflectMethod(Class.forName(RequestManager.getTName()),tagMethodName);
							Map<String,String> tagMap =  (Map<String,String>)method.invoke(Class.forName(RequestManager.getTName()));
							if(value != null)
								if(showForeignId)
									fieldValue = value.toString();
								else
									fieldValue = tagMap.get(value.toString());
						}
						else{ // 没有列表方法
							if(showField.getFieldTargetPrimaryKeyName() == null && value != null){//也没有对应表，直接取字段值
								if(value != null) {
									if(value.getClass().equals(Date.class) || value.getClass().equals(java.sql.Date.class) ){
										SimpleDateFormat sFormat = new SimpleDateFormat(strDateFormat);
										try {
											fieldValue = sFormat.format(value);
										} catch(Exception ex) {
											fieldValue = value.toString();
										}
									}
									if(showField.getReportUnitCode()!=0){
										if(showField.getReportUnitCode()==1){//元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1")).toString();
											}
										}
										else if(showField.getReportUnitCode()==2){//十元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10")).toString();
											}
										}
										else if(showField.getReportUnitCode()==3){//百元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100")).toString();
											}
										}
										else if(showField.getReportUnitCode()==4){//千元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==5){//万元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==6){//十万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==7){//百万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1000000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==8){//千万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10000000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==9){//亿
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100000000")).toString();
											}
										}
									}
									else {
										fieldValue = value.toString();
									}
								}
							}
							else { // 有关联表，则取关联表的关键名
								if(value != null) {
									if(showForeignId)
										fieldValue = ReflectOperation.getPrimaryKeyValue(value).toString();
									else
										fieldValue = ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKeyName()).toString();
								}
								
							} 
						}
					}
					else if(field != null  && showForeignId) {
						Object value=null;
						if(lineObj.getClass().getName().equals("java.util.LinkedHashMap")){
							value = ((Map<String,Object>)lineObj).get(field.getName());
						}
						else{
							value = ReflectOperation.getFieldValue(lineObj, field.getName()); // 取对象整对应字段值
						}
						if(value != null) {
							fieldValue = value.toString();
						}
					}
					
					if(fieldValue != null) {
						Cell cell = rowData.createCell(nCol);
						cell.setCellValue(fieldValue);
					}
					nCol++;
				}
			}
			
			nLine++;
		}

		if(baos == null) {
			FileOutputStream out = new FileOutputStream(path);
			wwb.write(out);
			out.close();
		}
		else {
			wwb.write(baos);
		}
		return nLine - startLine;
	}

	/**
	 * 导出表数据 	03Excel
	 */   
	public int writeDataTo03Excel(ShowInstance showInstance, String path, String sheetNames,
			Object retObj, Class<?> targetType, int cacheLine, boolean showForeignId, boolean showHeader,
			int startLine, int startCol, ByteArrayOutputStream baos, String strDateFormat,String modelPath) throws Exception {

		HSSFWorkbook wwb= null;
		if(StringUtils.isBlank(modelPath)){
			wwb = new HSSFWorkbook();
		}
		else{
			//wwb = new HSSFWorkbook(ServletActionContext.getServletContext().getResourceAsStream(modelPath));
			String classPath="";
			if(ShowContext.getInstance().getShowEntityMap().get("currentResourcePath") != null && ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath") != null){
				classPath = ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath");
			}
			else{
				classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath().substring(0).replace("%20", " ");
			}
			
			String strPath = classPath.substring(1,classPath.lastIndexOf("WEB-INF/class"));
			strPath += modelPath;
			strPath = strPath.replace("/", "\\");
			InputStream inputStream=new FileInputStream(strPath);
			wwb = new HSSFWorkbook(inputStream);
		}

		HSSFSheet sheet = null;
		int nLine = 0;
		int nCol = 0;
		int sheetNum = 0;
		String[] sheets = null;
		if(sheetNames != null){
			sheets = sheetNames.split(",");
		}
		
		for(Object lineObj : (ArrayList<Object>) retObj) { // 每行记录
			if(nLine%cacheLine == 0) {

				String sheetName = getSheetName(sheets, sheetNum);
				
				if(StringUtils.isBlank(modelPath)){
					sheet = wwb.createSheet(sheetName);
				}
				else{
					sheet = wwb.getSheet(sheetName);
				}
		        
				nLine = startLine;
				
				if(showHeader) {
					Row rowTitle = sheet.createRow(startLine);
					nCol=startCol;
					for(ShowField showField : showInstance.getShowFieldList()){
						Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
						//if(!showField.getSingleTag().equals(ApplicationManager.getSingleTagHidden()) || (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable())) { // 生成各字段显示名
						if(showField.isListVisible()) { // 生成各字段显示名
						Cell cell = rowTitle.createCell(nCol);
							cell.setCellValue(showField.getFieldShowName());
							nCol++;
						}
					}
					
					nLine++;
				}
				
				sheetNum++;
			}
			
			nCol = startCol;
			Row rowData = sheet.createRow(nLine);
			for(ShowField showField : showInstance.getShowFieldList()){ // 生成各字段值
				Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
				//if(!showField.getSingleTag().equals(ApplicationManager.getSingleTagHidden()) || (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable())) { // 生成各字段显示名
				if(showField.isListVisible()) { // 生成各字段显示名
					String fieldValue = "";
					if(field != null) {
						String tagMethodName = null; // 检查是否对应取列表方法
						if(field.isAnnotationPresent(IColumn.class)){
							IColumn iColumn = field.getAnnotation(IColumn.class);
							if(!StringUtils.isBlank(iColumn.tagMethodName())){
								tagMethodName = iColumn.tagMethodName();
							}
						}

						Object value = ReflectOperation.getFieldValue(lineObj, field.getName()); // 取对象整对应字段值
						
						if(!StringUtils.isBlank(tagMethodName)){ //有列表方法对应,调用取得列表map
							Method method = ReflectOperation.getReflectMethod(Class.forName(RequestManager.getTName()),tagMethodName);
							Map<String,String> tagMap =  (Map<String,String>)method.invoke(Class.forName(RequestManager.getTName()));
							if(value != null) {
								if(showForeignId)
									fieldValue = value.toString();
								else
									fieldValue = tagMap.get(value.toString());
							}
						}
						else{ // 没有列表方法
							if(showField.getFieldTargetPrimaryKeyName() == null){ // 也没有对应表，直接取字段值
								value = ReflectOperation.getFieldValue(lineObj, field.getName());
								if(value != null){
									if(value.getClass().equals(Date.class) || value.getClass().equals(java.sql.Date.class) ){
										if(strDateFormat != null && !strDateFormat.equals("")) {
											SimpleDateFormat sFormat = new SimpleDateFormat(strDateFormat);
											try {
												fieldValue = sFormat.format(value);
											} catch(Exception ex) {
												fieldValue = value.toString();
											}
										}
										else fieldValue = value.toString();
											
									}
									else{
										fieldValue = value.toString();
									}
									if(showField.getReportUnitCode()!=0){
										if(showField.getReportUnitCode()==1){//元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1")).toString();
											}
										}
										else if(showField.getReportUnitCode()==2){//十元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10")).toString();
											}
										}
										else if(showField.getReportUnitCode()==3){//百元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100")).toString();
											}
										}
										else if(showField.getReportUnitCode()==4){//千元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==5){//万元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==6){//十万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==7){//百万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1000000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==8){//千万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10000000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==9){//亿
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100000000")).toString();
											}
										}
									}
								}
							}
							else{ // 有关联表，则取关联表的关键名
								if(value != null){
									//Object objVal = ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKeyName());
									//if(objVal != null) {
										if(showForeignId){
											if(ReflectOperation.getPrimaryKeyValue(value)!=null){
												fieldValue = ReflectOperation.getPrimaryKeyValue(value).toString();
											}
											
										}
										else{
											if(ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKeyName())!=null){
												fieldValue = ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKeyName()).toString();
											}
										}
											
									//}
								}
								
							} 
						}
					}
					
					if(fieldValue != null){
						Cell cell = rowData.createCell(nCol);
						cell.setCellValue(fieldValue);
					}
					nCol++;
				}
			}
			
			nLine++;
		}

		if(baos == null) {
			FileOutputStream out = new FileOutputStream(path);
			wwb.write(out);
			out.close();
		}
		else {
			wwb.write(baos);
		}
			
		return nLine - startLine;
	}
	
	public int writeMapDataTo03Excel(ShowInstance showInstance, String path, String sheetNames,
			List<Map<String,Object>> retObj, Class<?> targetType, int cacheLine, boolean showForeignId, boolean showHeader,
			int startLine, int startCol, ByteArrayOutputStream baos, String strDateFormat,String modelPath) throws Exception {

		HSSFWorkbook wwb= null;
		if(StringUtils.isBlank(modelPath)){
			wwb = new HSSFWorkbook();
		}
		else{
			String classPath="";
			if(ShowContext.getInstance().getShowEntityMap().get("currentResourcePath") != null && ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath") != null){
				classPath = ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath");
			}
			else{
				classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath().substring(0).replace("%20", " ");
			}
			
			String strPath = classPath.substring(1,classPath.lastIndexOf("WEB-INF/class"));
			strPath += modelPath;
			strPath = strPath.replace("/", "\\");
			InputStream inputStream=new FileInputStream(strPath);
			wwb = new HSSFWorkbook(inputStream);
		}

		HSSFSheet sheet = null;
		int nLine = 0;
		int nCol = 0;
		int sheetNum = 0;
		String[] sheets = null;
		if(sheetNames != null){
			sheets = sheetNames.split(",");
		}
		
		for(Map<String,Object> lineObj : (List<Map<String,Object>>) retObj) { // 每行记录
			if(nLine%cacheLine == 0) {
				String sheetName = getSheetName(sheets, sheetNum);
				if(StringUtils.isBlank(modelPath)){
					sheet = wwb.createSheet(sheetName);
				}
				else{
					sheet = wwb.getSheet(sheetName);
				}
		        
				nLine = startLine;
				
				if(showHeader) {
					Row rowTitle = sheet.createRow(startLine);
					nCol=startCol;
					for(ShowField showField : showInstance.getShowFieldList()){
						Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
						if(showField.isListVisible()) { // 生成各字段显示名
						Cell cell = rowTitle.createCell(nCol);
							cell.setCellValue(showField.getFieldShowName());
							nCol++;
						}
					}
					
					nLine++;
				}
				
				sheetNum++;
			}
			
			nCol = startCol;
			Row rowData = sheet.createRow(nLine);
			for(ShowField showField : showInstance.getShowFieldList()){ // 生成各字段值
				Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
				if(showField.isListVisible()) { // 生成各字段显示名
					String fieldValue = "";
					if(field != null) {
						String tagMethodName = null; // 检查是否对应取列表方法
						if(field.isAnnotationPresent(IColumn.class)){
							IColumn iColumn = field.getAnnotation(IColumn.class);
							if(!StringUtils.isBlank(iColumn.tagMethodName())){
								tagMethodName = iColumn.tagMethodName();
							}
						}
						Object value=null;
						if(lineObj.getClass().getName().equals("java.util.LinkedHashMap")){
							value = ((Map<String,Object>)lineObj).get(field.getName());
						}
						else{
							value = ReflectOperation.getFieldValue((Map<String,Object>)lineObj, field.getName()); // 取对象整对应字段值
						}
						
						if(!StringUtils.isBlank(tagMethodName)){ //有列表方法对应,调用取得列表map
							Method method = ReflectOperation.getReflectMethod(Class.forName(RequestManager.getTName()),tagMethodName);
							Map<String,String> tagMap =  (Map<String,String>)method.invoke(Class.forName(RequestManager.getTName()));
							if(value != null) {
								if(showForeignId)
									fieldValue = value.toString();
								else
									fieldValue = tagMap.get(value.toString());
							}
						}
						else{ // 没有列表方法
							if(showField.getFieldTargetPrimaryKeyName() == null){ // 也没有对应表，直接取字段值
								if(lineObj.getClass().getName().equals("java.util.LinkedHashMap")){
									value = ((Map<String,Object>)lineObj).get(field.getName());
								}
								else{
									value = ReflectOperation.getFieldValue((Map<String,Object>)lineObj, field.getName()); // 取对象整对应字段值
								}
								
								if(value != null){
									if(value.getClass().equals(Date.class) || value.getClass().equals(java.sql.Date.class) ){
										if(strDateFormat != null && !strDateFormat.equals("")) {
											SimpleDateFormat sFormat = new SimpleDateFormat(strDateFormat);
											try {
												fieldValue = sFormat.format(value);
											} catch(Exception ex) {
												fieldValue = value.toString();
											}
										}
										else fieldValue = value.toString();
											
									}
									if(showField.getReportUnitCode()!=0){
										if(showField.getReportUnitCode()==1){//元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1")).toString();
											}
										}
										else if(showField.getReportUnitCode()==2){//十元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10")).toString();
											}
										}
										else if(showField.getReportUnitCode()==3){//百元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100")).toString();
											}
										}
										else if(showField.getReportUnitCode()==4){//千元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==5){//万元
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==6){//十万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==7){//百万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("1000000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==8){//千万
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("10000000")).toString();
											}
										}
										else if(showField.getReportUnitCode()==9){//亿
											if(!StringUtils.isBlank(value.toString())){
												BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
												fieldValue=bdCurrencyValue.divide(new BigDecimal("100000000")).toString();
											}
										}
									}
									
									else
										fieldValue = value.toString();
								}
							}
							else{ // 有关联表，则取关联表的关键名
								if(value != null){
										if(showForeignId)
											fieldValue = ReflectOperation.getPrimaryKeyValue(value).toString();
										else
											fieldValue = ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKeyName()).toString();
								}
								
							} 
						}
					}
					
					if(fieldValue != null){
						Cell cell = rowData.createCell(nCol);
						cell.setCellValue(fieldValue);
					}
					nCol++;
				}
			}
			
			nLine++;
		}

		if(baos == null) {
			FileOutputStream out = new FileOutputStream(path);
			wwb.write(out);
			out.close();
		}
		else {
			wwb.write(baos);
		}
			
		return nLine - startLine;
	}
	
    /** 
     * 读取 03 || 07 业务逻辑实现方法 
     */  
    public Object getRows(int curRow, List<String> rowlist, ArrayList<ShowField> showFields, String tableName, int startCol,
    		boolean showForeignId) throws Exception{
    	Class<?> type = Class.forName(tableName);
    	Object obj = type.newInstance();
		int rowSize;
		if(rowlist.size()-startCol>showFields.size()){
			rowSize = showFields.size();
		}else{
			rowSize = rowlist.size()-startCol;
		}
		
        for (int i = 0; i < rowSize && i<showFields.size(); i++) {
			String filedValue = "";
			Field field = ReflectOperation.getReflectField(type,showFields.get(i).getFieldName());
			if(field != null) {
				String tagMethodName = null; // 检查是否对应取列表方法
				if(field.isAnnotationPresent(IColumn.class)){
					IColumn iColumn = field.getAnnotation(IColumn.class);
					if(!StringUtils.isBlank(iColumn.tagMethodName())){
						tagMethodName = iColumn.tagMethodName();
					}
				}
				
				if(!StringUtils.isBlank(tagMethodName)){ //有列表方法对应,调用取得列表map
					Object value = rowlist.get(i+startCol);
					if(!value.toString().equals("")){
						if(showForeignId) {
							filedValue = value.toString();
						}
						else {
							Method method = ReflectOperation.getReflectMethod(Class.forName(RequestManager.getTName()),tagMethodName);
							Map<String,String> tagMap =  (Map<String,String>)method.invoke(Class.forName(RequestManager.getTName()));
							Set<String>kset = tagMap.keySet(); 
							for(String ks:kset){
								if(value.equals(tagMap.get(ks))) {
										filedValue = ks;
								} 
							}
						}
						
						if(filedValue==""){
//							addError(curRow+1,i+startCol+1,value.toString()); // 判断Map取值正确与否，弹出对话框，多少行出错。
//							return null;
						}else{
							ReflectOperation.setFieldValue(obj, showFields.get(i).getFieldName(), filedValue);
						}
					}
				}
				else{ // 没有列表方法
					if(showFields.get(i).getFieldTargetPrimaryKeyName() == null){//也没有对应表，直接取字段值
						Object objValue = rowlist.get(i+startCol);
						if(objValue != null){
							filedValue = objValue.toString();
						}
						if(objValue!=null && showFields.get(i).getReportUnitCode()!=0){
							if(showFields.get(i).getReportUnitCode()==1){//元
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("1")).toString();
								}
							}
							else if(showFields.get(i).getReportUnitCode()==2){//十元
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("10")).toString();
								}
							}
							else if(showFields.get(i).getReportUnitCode()==3){//百元
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("100")).toString();
								}
							}
							else if(showFields.get(i).getReportUnitCode()==4){//千元
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("1000")).toString();
								}
							}
							else if(showFields.get(i).getReportUnitCode()==5){//万元
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("10000")).toString();
								}
							}
							else if(showFields.get(i).getReportUnitCode()==6){//十万
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("100000")).toString();
								}
							}
							else if(showFields.get(i).getReportUnitCode()==7){//百万
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("1000000")).toString();
								}
							}
							else if(showFields.get(i).getReportUnitCode()==8){//千万
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("10000000")).toString();
								}
							}
							else if(showFields.get(i).getReportUnitCode()==9){//亿
								if(!StringUtils.isBlank(objValue.toString())){
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									filedValue=bdCurrencyValue.multiply(new BigDecimal("100000000")).toString();
								}
							}
							ReflectOperation.setFieldValue(obj, showFields.get(i).getFieldName(), filedValue);
						}
							
//						else{
//							if(field.isAnnotationPresent(Column.class)){
//								Column column = field.getAnnotation(Column.class);
//								if(!column.nullable()){
//									addError(curRow+1,i+startCol+1,filedValue);
//								}
//							}
//						}
						
						Field[] fields = ReflectOperation.getReflectFields(obj.getClass());
						for(Field fieldTmp : fields) {
							if(fieldTmp.getName().equals(showFields.get(i).getFieldName())) {
								if(fieldTmp.getType().equals(java.util.Date.class) || fieldTmp.getType().equals(java.sql.Date.class)) {
									if(!isValidDate(filedValue)){
										addError(curRow+1, i+startCol+1, showFields.get(i).getFieldShowName() + "应为日期类型");
									}
									else 
										ReflectOperation.setFieldValue(obj, showFields.get(i).getFieldName(), filedValue);
								}
								else if(fieldTmp.getType().equals(java.sql.Timestamp.class)) {
									if(filedValue.equals(TypeParse.maxTimestamp())){
										addError(curRow+1, i+startCol+1, showFields.get(i).getFieldShowName() + "应为时间类型");
									}
									else 
										ReflectOperation.setFieldValue(obj, showFields.get(i).getFieldName(), filedValue);
								}
								else
									ReflectOperation.setFieldValue(obj, showFields.get(i).getFieldName(), filedValue);
								
								break;
							}
						}
					}
					else{ // 有关联表，则取关联表的关键名
						
						Object value = rowlist.get(i+startCol);
						if(value != null && !value.toString().equals("")){
							String beanID = "singleObjectFindByCriteriaDao";
							IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
							Field keyNameField = ReflectOperation.getKeyNameField(field.getType());
							Field primayField = ReflectOperation.getPrimaryKeyField(field.getType());
							
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(field.getType());
							if(showForeignId)
								detachedCriteria.add(Restrictions.eq(primayField.getName(), value.toString()));
							else
								detachedCriteria.add(Restrictions.eq(keyNameField.getName(), value.toString()));
								
							Object tObject = dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
//							
							if ((tObject == null) || (((ArrayList<Object>)tObject).size()==0)) { // 找不到对象,设置SQL为空
//								addError(curRow+1,i+startCol+1,value.toString()); // 判断Map取值正确与否，弹出对话框，多少行出错。
//								return null;
								
								//ReflectOperation.setFieldNullValue(obj, showFields.get(i).getFieldName(), type);
							}else{
						       ReflectOperation.setFieldValue(obj, showFields.get(i).getFieldName(), ((ArrayList<Object>)tObject).get(0));
							}
						}
					} 
				}
			}
        }
        
//        if(rowlist.size()-startCol>=showFields.size()){
//        	int idIndex = showFields.size();
//        	if(rowlist.get(idIndex+startCol) != null && rowlist.get(idIndex+startCol).length()==32){
//        		//查询数据库，判断主键是否存在	
//        		String beanID = "singleObjectFindByIdDao";
//	    		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
//	    		// 调用DAO查询到tObject对象(表)
//	    		String tableId = rowlist.get(idIndex+startCol);
//	    		Object tObject = dao.paramObjectResultExecute(new Object[]{tableName, tableId, null});
//	    		if(tObject != null){
//		    		ReflectOperation.setFieldValue(obj, ReflectOperation.getPrimaryKeyField(RequestManager.getTName()).getName(), tableId);
//	    		}
//        	}
//       }
		return obj;
	}

    /** 
     * 判断单元格时否存在分隔符
     */  
    public String getCellValue(String cellValue,String afterIgnorSeg){
    	String cellVal = cellValue;
    	int index = cellValue.indexOf(afterIgnorSeg);
    	if(afterIgnorSeg != null && !"".equals(afterIgnorSeg) && index>-1){
    		cellVal = cellValue.substring(0, index);
    	}
    	return cellVal;
    }
 
    public void addError(int row,int column,String value){
		error.append("\\r\\n");
		error.append(row).append("行");
		error.append(column).append("列：“");
		error.append(value).append("”错误");
    } 
    public void addHanderError(String sheetName){
		error.setLength(0);
		error.append("Sheet:");
		error.append(sheetName);
		error.append(",导入表结构与实际表结构不符");
    }

	private String getSheetName(String sheetName){
		if(StringUtils.isBlank(sheetName)){
			return defaultSheetName;
		}
		else{
			return sheetName;
		}
	}
	
	private String getSheetName(String[] sheetNames,int index){
		if(sheetNames == null){
			return defaultSheetName + index;
		}
		else{
			if(index > sheetNames.length -1 || StringUtils.isBlank(sheetNames[index])){
				return getSheetName(sheetNames[sheetNames.length -1]) + (index - sheetNames.length);
			}
			else{
				return sheetNames[index];
			}
		}
	}
	
	public static boolean isValidDate(String date) {
		try {
			String[] strVals = date.split("-");
			if (strVals.length == 1) {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				format.setLenient(false);
    			format.parse(date.toString().trim());
			} else if (strVals.length == 3) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				format.setLenient(false);
    			format.parse(date.toString().trim());
			}
			else
				return false;

		}
		catch (Exception ex) {
			return false;
		}

		return true;
	}
}
/**
 * 单元格中的数据可能的数据类型
 */
enum CellDataType
{
	BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
}
