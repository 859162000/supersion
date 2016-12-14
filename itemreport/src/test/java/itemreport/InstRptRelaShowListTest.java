package itemreport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;
import coresystem.dto.InstInfo;

import report.dto.CurrencyType;
import report.dto.RptInfo;
import extend.dto.Suit;
import framework.dao.imps.SingleObjectDeleteByIdDao;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.test.ActionTestCase;
import framework.test.TestDataProvider;

/**
 * 报表定义------机构报表关系
 * @author Admin
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)  
public class InstRptRelaShowListTest extends ActionTestCase{
	Map<String,Object> context = new HashMap<String,Object>();
	public static Map<String,String> IdList = new HashMap<String,String>();
	
	@Test
	public void step00_show() throws Exception{
		context.put("windowId", "instRptRela");
		testAction("/framework/ShowList-report.dto.InstRptRela.action",new TestDataProvider() {
		
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas = new HashMap<String,String>();
				datas.put("windowId", context.get("windowId").toString());
				return datas;
			}
		},context);
	}
	
	//Suit
	@Test
	@Rollback(false)
	public void step01_forKeySuit() throws Exception{
		testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls",1);
		Suit suit = (Suit)RequestManager.getTOject();
		String suitCode = suit.getStrSuitCode();
		IdList.put("suit", suitCode);
	}
	//CurrencyType
	@Test
	@Rollback(false)
	public void step02_forKeyCurrencyType() throws Exception{
		testAction("/framework/Save-report.dto.CurrencyType.action","forKeyAdd.xls",3);
		CurrencyType currencyType =(CurrencyType)RequestManager.getTOject();
		String currencyTypeCode = currencyType.getStrCurrencyCode();
		IdList.put("currencyType", currencyTypeCode);
		
	}
	//InstInfo
	@Test
	@Rollback(false)
	public void step03_forKeyInstInfo() throws Exception{
		testAction("/framework/TreeNodeSave-coresystem.dto.InstInfo.action","forKeyAdd.xls",2);
		InstInfo instInfo =(InstInfo)RequestManager.getTOject();
		String instCode = instInfo.getStrInstCode();
		IdList.put("coresystem.dto.InstInfo", instCode);
	}
	//RptInfo
	@Test
	@Rollback(false)
	public void step04_forKey() throws Exception{
		SaveRptDto("rptInfoAdd.xls",0); 
		testAction("/framework/Save-report.dto.RptInfo.action","rptInfoAdd.xls",0);
		RptInfo rptInfo = (RptInfo)RequestManager.getTOject(); 
		String rptInfoCode = rptInfo.getStrRptCode();
		IdList.put("rptInfo", rptInfoCode);
		  
	}
	@Test
	public void step05_instRptRelaShowSave() throws Exception{
		context.put("windowId", "instRptRela");
		testAction("/report/InsertInstRptRelaPageInitAction-report.dto.InstRptRela.action",new TestDataProvider() {			
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
			    Map<String,String> datas = new HashMap<String,String>();
			    datas.put("windowId", context.get("windowId").toString());
				return datas;
			}
		},context);
		
	}
	@Test
	public void step06_instRptRelaSave() throws Exception{
		SaveRptDto("instRptRelaShowList.xls",0);
		testAction("/report/InstRptRelaSave-report.dto.InstRptRela.action","instRptRelaShowList.xls",0);
	}
	private void SaveRptDto (String dataFileName ,int index) throws IOException
	 {
		 InputStream  is=this.getClass().getClassLoader().getResourceAsStream(dataFileName);
		 POIFSFileSystem fs = new POIFSFileSystem(is);
		 HSSFWorkbook wwb = new HSSFWorkbook(fs);
		 HSSFSheet sheet = wwb.getSheetAt(index);
		 for(int i =0;i<=sheet.getLastRowNum();i++){
			 String keyValue =sheet.getRow(i).getCell(0).getStringCellValue();			 
			 if(keyValue.equals("serviceParam.suit.strSuitCode")){
				 sheet.getRow(i).getCell(1).setCellValue(IdList.get("suit").toString());
				 }else if(keyValue.equals("serviceParam.currencyTypeList")){
					 sheet.getRow(i).getCell(1).setCellValue(IdList.get("currencyType").toString());
						 
				 }else if(keyValue.equals("serviceParam.rptInfoIdList")){
					 sheet.getRow(i).getCell(1).setCellValue(IdList.get("rptInfo").toString());
				 }else if(keyValue.equals("serviceParam.instInfoList")){
					 sheet.getRow(i).getCell(1).setCellValue(IdList.get("coresystem.dto.InstInfo").toString());
				 }
			 }
				 
		 FileOutputStream fileOut = new FileOutputStream(this.getClass().getResource("/"+dataFileName).toString().substring(6));
		 wwb.write(fileOut);
		 fileOut.close();
		 fs.close();
		 is.close();
	 }
	 
	 //外键删除
	 @Test
	 @Rollback(false)	 
     public void step07_rptInfoDeleteIdList() throws Exception{
		context.put("windowId", "RptInfo");
		context.put("idList", IdList.get("rptInfo"));
		testAction("/framework/TreeNodeDelete-report.dto.RptInfo.action",new TestDataProvider(){
				
	      public Map<String, String> getData(Map<String, Object> context) {
			Map<String,String> datas=new HashMap<String,String>();
			datas.put("windowId",context.get("windowId").toString());
			datas.put("idList",context.get("idList").toString());
			return datas;
			}
			 },context);
			  
		}
	 @Test
	 @Rollback(false)
	 public void step08_suitDeleteIdList() throws Exception{
			context.put("windowId", "Suit");
			context.put("idList", IdList.get("suit"));
			testAction("/framework/TreeNodeDelete-extend.dto.Suit.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					 datas.put("windowId",context.get("windowId").toString());
					 datas.put("idList",context.get("idList").toString());
					 return datas;
				}
			 },context);
			  
		}
		
	@Test
	@Rollback(false)
	 public void step09_currencyTypeDeleteIdList() throws Exception{
			context.put("windowId", "CurrencyType");
			context.put("idList", IdList.get("currencyType"));
			testAction("/framework/DeleteListByIdList-report.dto.CurrencyType.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					 datas.put("windowId",context.get("windowId").toString());
					 datas.put("idList",context.get("idList").toString());
					 return datas;
				}
			 },context);			  
		}
		//机构删除
	@Test
	@Rollback(false)
	 public void step10_instInfoDelete() throws Exception{
			IParamVoidResultExecute singleObjectDeleteByIdDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectDeleteByIdDao");
			
			if( IdList.containsKey("coresystem.dto.InstInfo")){
				singleObjectDeleteByIdDao.paramVoidResultExecute(new Object[]{"coresystem.dto.InstInfo",IdList.get("coresystem.dto.InstInfo"),null});
			}
			
		}
		
}
