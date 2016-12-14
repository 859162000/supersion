package itemreport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import report.dto.RptDto;
import report.dto.RptInfo;
import extend.dto.Suit;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;
import framework.test.ActionTestCase;
import framework.test.TestDataProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RptInfoTest extends ActionTestCase{
	 private static String Id = null;
	 private static String MXId = null;
	 Map<String,String> tag = null;
	 Map<String,Object> context = new HashMap<String,Object>();
	 public static Map<String,Object> IdList = new HashMap<String,Object>();
	
	 @Rollback(false)	
	 @Test
	public void step00_suitAddTest() throws Exception{
		  testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls",1);
		  Suit suit = (Suit)RequestManager.getTOject();
		  IdList.put("suit", suit.getStrSuitCode());	 	 
		 
	 }
	 	 
	 @Rollback(false)
	 @Test
	public void step01_rptInfoAddTest() throws Exception{
		 context.put("windowId", "RptInfo");
		 testAction("/framework/ShowTree-report.dto.RptInfo.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					 Map<String,String> datas=new HashMap<String,String>();
					 datas.put("windowId",context.get("windowId").toString());
					 return datas;
				}
			 },context);
		 context.put("id", IdList.get("suit"));
		 context.put("type", "extend.dto.Suit");
		 testAction("/framework/ShowListForTree-report.dto.RptInfo.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					 Map<String,String> datas=new HashMap<String,String>();
					 datas.put("windowId",context.get("windowId").toString());
					 datas.put("id",context.get("id").toString());
					 datas.put("type",context.get("type").toString());
					 return datas;
				}
			 },context);
		 testAction("/framework/ShowSave-report.dto.RptInfo.action","");
		 SaveLevelRptDto("rptInfoAdd.xls",0);
		 testAction("/framework/Save-report.dto.RptInfo.action","rptInfoAdd.xls",0);
		 RptInfo rptInfo = (RptInfo)RequestManager.getTOject(); 
		 Id = rptInfo.getStrRptCode();
 
		 
	 } 
	
	 //数据查询
	 @Test
	 public void step02_rptInfoSelectTest() throws Exception{
		 context.put("strBCode","455" );
		 testAction("/framework/ShowListForTree-report.dto.RptInfo.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					 Map<String, String> datas=new HashMap<String,String>();
					 datas.put("strBCode",(String) context.get("strBCode"));
					 return datas;
				}
			 },context);
	 } 
	 
	 //数据修改
	 
	 @Test
	 public void step03_rptInfoUpdateTest() throws Exception{
		
		 context.put("windowId", "RptInfo");
		 context.put("id", Id);
		 testAction("/framework/ShowUpdate-report.dto.RptInfo.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					 Map<String, String> datas=new HashMap<String,String>();
					 datas.put("windowId",context.get("windowId").toString());
					 datas.put("id", context.get("id").toString());
					 return datas;
				}
			 },context);
	 SaveLevelRptDto("rptInfoUpdate.xls",0);
	 testAction("/framework/Update-report.dto.RptInfo.action","rptInfoUpdate.xls",0);
	 }

	 //设置明细添加
	 @Test
	 @Rollback(false)
     public void step04_rptInfoLevelMXAddTest() throws Exception{
		 context.put("windowId", "RptInfo");
		 context.put("parentId", Id);
		 testAction("/framework/ShowSaveLevelRptDto-report.dto.RptDto.action",new TestDataProvider(){	
				public Map<String, String> getData(Map<String, Object> context) {
					 Map<String, String> datas=new HashMap<String,String>();
					 datas.put("id", context.get("parentId").toString());
					 datas.put("windowId", context.get("windowId").toString());
					 return datas;
						}
					 },context);	
		 initServletMockObjects();
		 String key= null ;
		 tag = getMXList();
		 if(tag!=null&& tag.size()>0){
			 Iterator<String> keyList = tag.keySet().iterator();
			 if(keyList.hasNext()){
				 key = keyList.next();
			 }
			
			 request.addParameter("serviceParam.table.autoTableID", key);
			 SaveLevelRptDto("rptInfoLevelMXAdd.xls",0);
			 testAction("/framework/SaveLevelRptDto-report.dto.RptDto.action","rptInfoLevelMXAdd.xls",0);
			 RptDto rptDto = (RptDto)RequestManager.getTOject();
			 MXId = rptDto.getId();
		 }
		
		
		
		 
   }
	 private Map getMXList(){
			ShowSaveOrUpdate saveOrUpdate = ((ShowSaveOrUpdate) LogicParamManager.getServiceResult());
			if(saveOrUpdate != null) {
				List<ShowFieldValue> showFieldVaList = saveOrUpdate.getShowFieldValueList();
				if(showFieldVaList != null) {
					ShowField showField = null;
					for(ShowFieldValue showFieldValue : showFieldVaList){
						if(showFieldValue != null) {
							showField = showFieldValue.getShowField();
							if(!StringUtils.isBlank(showField.getFieldName())&& showField.getFieldName().equals("表名") ) {
							tag = (Map<String, String>) showFieldValue.getTag();
							}
						}
						
					}
				}
			}
		  return tag;
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
			 if(keyValue.equals("serviceParam.rptInfo.strRptCode") || keyValue.equals("id") || keyValue.equals("serviceParam.id")
					 ){
				 
				 if(keyValue.equals("serviceParam.id")){
					 sheet.getRow(i).getCell(1).setCellValue(MXId.toString());
				 }else{
					
					 sheet.getRow(i).getCell(1).setCellValue(Id.toString());
				 }
			 }else if(keyValue.equals("serviceParam.suit.strSuitCode")){
				 sheet.getRow(i).getCell(1).setCellValue(IdList.get("suit").toString());
				 
			 }else if(keyValue.equals("serviceParam.strRptCode")&& null!=Id){
				 sheet.getRow(i).getCell(1).setCellValue(Id.toString());
			 }else if(keyValue.equals("serviceParam.table.autoTableID")){
				 sheet.getRow(i).getCell(1).setCellValue(request.getParameter("serviceParam.table.autoTableID"));
				 
			 }
		 }
		 FileOutputStream fileOut = new FileOutputStream(this.getClass().getResource("/"+dataFileName).toString().substring(6));
		 wwb.write(fileOut);
		 fileOut.close();
		 fs.close();
		 is.close();
	 }
	
	//设置明细修改
	 @Test
	 @Rollback(false)
     public void step05_rptInfoLevelMXUpdateTest() throws Exception{
		 if(MXId!=null && MXId.trim().length()>0){
			 context.put("id",MXId);
			 context.put("windowId", "RptInfo");
			 testAction("/framework/ShowUpdateLevelRptDto-report.dto.RptDto.action",new TestDataProvider(){
					
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas=new HashMap<String,String>();
						 datas.put("windowId",context.get("windowId").toString());
						 datas.put("id",context.get("id").toString());
						 return datas;
					}
				 },context);
			 SaveLevelRptDto("rptInfoLevelMXUpdate.xls",0);
			 initServletMockObjects();
			 testAction("/framework/UpdateLevelRptDto-report.dto.RptDto.action","rptInfoLevelMXUpdate.xls",0);	
			 }
   }
	 //设置明细字段返回
	 @Test
	 @Rollback(false)
     public void step06_rptInfoLevelMXBackFirstTest() throws Exception{
		 context.put("windowId", "RptInfo");
		 testAction("/framework/BackFirstLevelRptDto-report.dto.RptDto.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					Map<String, String> datas=new HashMap<String,String>();
					 datas.put("windowId",context.get("windowId").toString());
					 return datas;
				}
			 },context);	
		 
   }
	 //设置明细字段删除
	 @Test
	 @Rollback(false)
	 public void step07_rptInfoLevelMXIdListDelete() throws Exception{
		if( MXId!=null && MXId.trim().length()>0){
			 context.put("windowId", "RptInfo");
			 List list = new ArrayList();
			 list.add(MXId);
			 final String[] idList =(String[])list.toArray(new String[list.size()]);
			 context.put("idList", idList);
			 testAction("/framework/DeleteListByIdListLevelRptDto-report.dto.RptDto.action",new TestDataProvider(){
					
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas=new HashMap<String,String>();
						datas.put("windowId", context.get("windowId").toString());
						request.removeAllParameters();
						request.addParameter("idList", (String[])context.get("idList"));
						 return datas; 
					}
				 },context);	
		}
	 }
	 
	 //报表定义器
	/* public void rptInfoReport() throws Exception{
		 context.put("id", Id);
		 testAction("/report/EditTemplate-report.dto.RptInfo.action",new TestDataProvider(){
			 public Map<String,String> getData(Map<String,Object>context){
				 Map<String, String> datas=new HashMap<String,String>();
					datas.put("id", context.get("id").toString());
					 return datas; 
			 }
		 },context);
		context.put("AutoTableId", "");
		testAction("/report/Select-showFields.action",new TestDataProvider(){
			 public Map<String,String> getData(Map<String,Object>context){
				 Map<String, String> datas=new HashMap<String,String>();
					datas.put("AutoTableId", context.get("AutoTableId").toString());
					 return datas; 
			 }
		 },context);
		testAction("/ajax/GetJsonInfo-report.dto.ItemInfo.action","");
	 }*/
	 //多行数据删除

	 @Test
	 @Rollback(true)
	public void step08_rptInfoDeleteListTest() throws Exception{
		 List list = new ArrayList();
		 list.add(Id);
		 String[] idList = (String[]) list.toArray(new String[list.size()]);
		 context.put("idList",idList);
		 testAction("/framework/TreeNodeDelete-report.dto.RptInfo.action",new TestDataProvider(){
				
				public Map<String, String> getData(Map<String, Object> context) {
					request.removeAllParameters();
					request.addParameter("idList", (String[])context.get("idList"));
					 return null;
				}
			 },context);
	 }
	 
	/* //读取需要删除的Id列表
	 private String[] IdListDelete(String dataFileName) throws IOException
	 {
		 InputStream  is=this.getClass().getClassLoader().getResourceAsStream(dataFileName);
		 POIFSFileSystem fs = new POIFSFileSystem(is);
		 HSSFWorkbook wwb = new HSSFWorkbook(fs);
		 HSSFSheet sheet = wwb.getSheetAt(0);
		 List list = new ArrayList();
		 String[] idList= null;
		 for(int i =0;i<=sheet.getLastRowNum();i++){
			 String str =sheet.getRow(i).getCell(1).getStringCellValue();
			 if(str.trim().length()!=0){
				 list.add(str); 
			 }
			 
		 }
		idList=(String[]) list.toArray(new String[list.size()]);
		fs.close();
		is.close();
		return idList;
	 }*/
	 
	
//根据Id删除指定数据
	 
	 @Test
	 @Rollback(false)
	public void step09_rptInfoDeleteTest() throws Exception{
		 context.put("id",Id);
		 testAction("/framework/DeleteByIdForTree-report.dto.RptInfo.action",new TestDataProvider(){
			
			public Map<String, String> getData(Map<String, Object> context) {
				 Map<String,String> datas=new HashMap<String,String>();
				 datas.put("id",context.get("id").toString());
				 return datas;
			}
		 },context);
		 
	 }
	  //Suit删除
	    @Test
		@Rollback(false)
	public void step10_suitDeleteIdList() throws Exception{
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
	 
	    
	
}
 

