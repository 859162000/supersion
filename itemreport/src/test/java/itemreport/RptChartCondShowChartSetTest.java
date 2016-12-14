package itemreport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import coresystem.dto.InstInfo;

import report.dto.CurrencyType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;

import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.test.ActionTestCase;
import framework.test.TestDataProvider;
/**
 * 数据查询平台------指标分析单元测试
 * @author Admin
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RptChartCondShowChartSetTest extends ActionTestCase {
      Map<String,Object> context = new HashMap<String,Object>(); 
      //外键表生成
      public final static Map<String,String> IdList = new HashMap<String,String>(); 
      @Test
      @Rollback(false)
      public void step00_forKeySuitAdd() throws Exception{
    	  //Suit
    	testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls",1);
    	Suit suit = (Suit)RequestManager.getTOject();
    	String suitCode = suit.getStrSuitCode();
    	IdList.put("extend.dto.Suit", suitCode);
      }
    //ItemInfo
      @Test
      @Rollback(false)
      public void step01_forKeyItemInfoAdd() throws Exception{
          SaveLevelRptDto("forKeyAdd.xls",5);
          testAction("/framework/TreeNodeSave-report.dto.ItemInfo.action","forKeyAdd.xls",5);
          ItemInfo itemInfo =(ItemInfo)RequestManager.getTOject();
    	  String itemInfoCode = itemInfo.getStrItemCode();
    	  IdList.put("report.dto.ItemInfo", itemInfoCode);  
      }
    //InstInfo
      @Test
      @Rollback(false)
      public void step02_forKeyInstInfoAdd() throws Exception{
    	  
    	  testAction("/framework/TreeNodeSave-coresystem.dto.InstInfo.action","forKeyAdd.xls",2);
    	  InstInfo instInfo =(InstInfo)RequestManager.getTOject();
    	  String instCode = instInfo.getStrInstCode();
    	  IdList.put("coresystem.dto.InstInfo", instCode);
    	  
      }
      //CurrencyType
      @Test
      @Rollback(false)
      public void step03_forKeyCurrencyTypeAdd() throws Exception{
    	  
    	  testAction("/framework/Save-report.dto.CurrencyType.action","forKeyAdd.xls",3);
    	  CurrencyType currencyType =(CurrencyType)RequestManager.getTOject();
    	  String currencyTypeCode = currencyType.getStrCurrencyCode();
    	  IdList.put("report.dto.CurrencyType", currencyTypeCode);
    	  
      }//ItemProperty
      @Test
      @Rollback(false)
      public void step04_forKeyItemPropertyAdd() throws Exception{
    	  testAction("/framework/Save-report.dto.ItemProperty.action","forKeyAdd.xls",4);
    	  ItemProperty itemProperty =(ItemProperty)RequestManager.getTOject();
    	  String itemPropertyCode = itemProperty.getStrPropertyCode();
    	  IdList.put("report.dto.ItemProperty", itemPropertyCode);  
    	  System.out.println(IdList);
      }
      //指标分析
      @Test
      public void step05_RptChartCondAnalysis() throws Exception{
    	  context.put("windowId", "ItemDataChart");
    	  testAction("/report/ShowChartSet-report.dto.RptChartCond.action",new TestDataProvider() {
			
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas = new HashMap<String,String>();
				datas.put("windowId", context.get("windowId").toString());
			 	return datas;
			  }
		  },context);
    	
         context.put("suit", IdList.get("extend.dto.Suit"));
         context.put("itemCode", IdList.get("report.dto.ItemInfo"));
         testAction("/undefinedajax/GetJsonInfo-report.dto.ItemInfo.action",new TestDataProvider() {
			
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas = new HashMap<String,String>();
				datas.put("suit", context.get("suit").toString());
				datas.put("itemCode", context.get("itemCode").toString());
				return datas;
			  }
		  },context);
         SaveLevelRptDto("rptChartCondShowChartSet.xls",0);
         testAction("/report/ShowChart-report.dto.RptChartCond.action","rptChartCondShowChartSet.xls");
    	  
      }
      //外键删除
      @Test
		@Rollback(false)
	  public void step06_itemInfoDeleteIdList() throws Exception{
			context.put("windowId", "ItemInfo");
			context.put("idList", IdList.get("report.dto.ItemInfo"));
			testAction("/framework/DeleteListByIdList-report.dto.ItemInfo.action",new TestDataProvider(){
				
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
	  public void step07_itemPropertyDeleteIdList() throws Exception{
			context.put("windowId", "ItemInfo");
			context.put("idList", IdList.get("report.dto.ItemProperty"));
			testAction("/framework/DeleteListByIdList-report.dto.ItemProperty.action",new TestDataProvider(){
				
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
	   public void step08_currencyTypeDeleteIdList() throws Exception{
			context.put("windowId", "CurrencyType");
			context.put("idList", IdList.get("report.dto.CurrencyType"));
			testAction("/framework/DeleteListByIdList-report.dto.CurrencyType.action",new TestDataProvider(){
				
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
	   public void step09_suitDeleteIdList() throws Exception{
			context.put("windowId", "Suit");
			context.put("idList", IdList.get("extend.dto.Suit"));
			testAction("/framework/TreeNodeDelete-extend.dto.Suit.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					 datas.put("windowId",context.get("windowId").toString());
					 datas.put("idList",context.get("idList").toString());
					 return datas;
				}
			 },context);
			  
		}
		
       //将指定文件写到excel中作为参数
 	   private void SaveLevelRptDto (String dataFileName, int index) throws IOException
 	    {
 		 InputStream  is=this.getClass().getClassLoader().getResourceAsStream(dataFileName);
 		 POIFSFileSystem fs = new POIFSFileSystem(is);
 		 HSSFWorkbook wwb = new HSSFWorkbook(fs);
 		 HSSFSheet sheet = wwb.getSheetAt(index);
 		 for(int i =0;i<=sheet.getLastRowNum();i++){
 			 String keyValue =sheet.getRow(i).getCell(0).getStringCellValue();
 			 if(keyValue.equals("serviceParam.suit.strSuitCode")){
 				     sheet.getRow(i).getCell(1).setCellValue(IdList.get("extend.dto.Suit"));
 			 }else if(keyValue.equals("serviceParam.itemIdList")){
 				     sheet.getRow(i).getCell(1).setCellValue(IdList.get("report.dto.ItemInfo"));
 			 }else if(keyValue.equals("serviceParam.itemProperty.strPropertyCode")){
 	 				 sheet.getRow(i).getCell(1).setCellValue(IdList.get("report.dto.ItemProperty"));
 			 }else if(keyValue.equals("serviceParam.currencyType.strCurrencyCode")){
 	 				 sheet.getRow(i).getCell(1).setCellValue(IdList.get("report.dto.CurrencyType"));
 			 }else if(keyValue.equals("serviceParam.instInfo.strInstCode")){
 	 				 sheet.getRow(i).getCell(1).setCellValue(IdList.get("coresystem.dto.InstInfo"));
 			 }
 		 }
 		 FileOutputStream fileOut = new FileOutputStream(this.getClass().getResource("/"+dataFileName).toString().substring(6));
 		 wwb.write(fileOut);
 		 fileOut.close();
 		 fs.close();
 		 is.close();
 	 }
 	 
 	  @AfterClass
 	  public static void step10_deleteTestDate() throws Exception{
 		IParamVoidResultExecute singleObjectDeleteByIdDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectDeleteByIdDao");
 		Set keys =IdList.keySet();
 		if(keys!= null){
 		Iterator iterator = keys.iterator();
 	     while(iterator.hasNext()) {
 	    String key = iterator.next().toString();
 	    if(key.equals("coresystem.dto.InstInfo")){
 	    singleObjectDeleteByIdDao.paramVoidResultExecute(new Object[]{Class.forName(key).getName(),IdList.get(key),null});
 	     }
 	     }
 	
 	 }
 	 
}
}
