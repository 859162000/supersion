package itemreport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import com.opensymphony.xwork2.ActionProxy;

import report.dto.ItemInfo;
import extend.dto.Suit;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowList;
import framework.test.ActionTestCase;
import framework.test.CheckResult;
import framework.test.TestDataProvider;

/**
 * 指标基本信息管理基本流程的单元测试
 * @author Admin
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class ItemInfoShowListForTreeTest extends ActionTestCase{
	public static String Id = null;
	public static String suitCode = null;
	Map<String,Object> context = new HashMap<String,Object>();
	Map<String,String> map = new HashMap<String,String>();
	//主题添加
	@Test
	@Rollback(false)
	public void step00_suitAdd() throws Exception{
		testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls",1);
		Suit suit = (Suit)RequestManager.getTOject();
		suitCode = suit.getStrSuitCode();
		
	}
	//指标数据添加
	@Test
	@Rollback(false)
	public void step01_itemInfoAdd() throws Exception{
		SaveLevelRptDto("forKeyAdd.xls",5);
		testAction("/framework/TreeNodeSave-report.dto.ItemInfo.action","forKeyAdd.xls",5);
		ItemInfo itemInfo = (ItemInfo)RequestManager.getTOject();
		Id = itemInfo.getStrItemCode();
	}
	//指标数据修改
	@Test
	@Rollback(true)
	public void step02_itemInfoUpdate() throws Exception{
		
		context.put("windowId", "ItemInfo");
		context.put("id", Id);
		testAction("/framework/ShowUpdate-report.dto.ItemInfo.action",new TestDataProvider(){
			
			public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					datas.put("windowId",context.get("windowId").toString());
					datas.put("id",context.get("id").toString());
					return datas;
				}
			 },context);
		
		map.put("serviceParam.strItemName", "FREE");
		map.put("serviceParam.strDataType", "3");
		map.put("serviceParam.suit.strSuitCode",suitCode);
		testAction("/framework/Update-report.dto.ItemInfo.action",new TestDataProvider(){
			public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					datas.put("serviceParam.strItemCode",context.get("id").toString());
					request.addParameters(map);
					/*datas.put("serviceParam.strDataType",context.get("serviceParam.strDataType").toString());
					datas.put("serviceParam.strItemName",context.get("serviceParam.strItemName").toString());
					datas.put("serviceParam.suit.strSuitCode",context.get("serviceParam.suit.strSuitCode").toString());
					*/
					return datas;
				}
			 },context);
	}
	//指标数据导出
	@Test
	@Rollback(true)
	public void step03_itemInfoExport() throws Exception{
		context.put("windowId", "ItemInfo");
		context.put("id", "22");
		context.put("type","extend.dto.Suit");
		testAction("/framework/ShowListForTree-report.dto.ItemInfo.action",new TestDataProvider(){
			
			public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					datas.put("windowId",context.get("windowId").toString());
					datas.put("id",context.get("id").toString());
					datas.put("type",context.get("type").toString());
					return datas;
				}
			 },context);
	
		testAction("/framework/ExportData-report.dto.ItemInfo.action", "");
		DownloadResult serviceResult = (DownloadResult) LogicParamManager.getServiceResult();
		
	}
	//文件导入
	@Test
	@Rollback(true)
	public void step04_itemInfoImport() throws Exception{
		context.put("windowId", "ItemInfo");
		context.put("id", "22");
		context.put("type","extend.dto.Suit");
		testAction("/framework/ShowListForTree-report.dto.ItemInfo.action",new TestDataProvider(){
			
			public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					datas.put("windowId",context.get("windowId").toString());
					datas.put("id",context.get("id").toString());
					datas.put("type",context.get("type").toString());
					return datas;
				}
			 },context);
		
		ShowList showlist = (ShowList) LogicParamManager.getServiceResult();
		initServletMockObjects();
		testAction("/framework/ExportData-report.dto.ItemInfo.action", "");
		DownloadResult serviceResult = (DownloadResult) LogicParamManager.getServiceResult();
		String path = serviceResult.getContentDisposition();
		String fileName = path.substring(path.indexOf("=")+1);
		
		InputStream is =  serviceResult.getInputStream();
		is.reset();
		String fName = System.getProperty("java.io.tmpdir");
		File f= new File(fName+"/"+fileName);
		if(f.exists()){
			f.delete();
		}
			f.createNewFile();
		
		FileOutputStream os = new FileOutputStream(f);
		byte[] b = new byte[1024];
		int len = 0;
		while((len = is.read(b)) != -1)
		{
			os.write(b,0,len);
		}	
		context.put("uploadFile", f.getPath());
		initServletMockObjects();
		testAction("/framework/ImportDataForTree-report.dto.ItemInfo.action",new TestDataProvider(){
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas=new HashMap<String,String>();
				datas.put("windowId",context.get("windowId").toString());
				datas.put("uploadFile",context.get("uploadFile").toString());
				return datas;
			}
		 },context);
		
		f.delete();
		is.close();
		os.close();
		
	}
	//指标数据删除
	@Test
	@Rollback(false)
	public void step05_itemInfoIdListDelete() throws Exception{
		context.put("windowId", "ItemInfo");
	    List list = new ArrayList();
	    list.add(Id);
	    String[] idList = (String[])list.toArray(new String[list.size()]);
	    context.put("idList", idList);
		testAction("/framework/TreeNodeDelete-report.dto.ItemInfo.action",new TestDataProvider(){
			
			public Map<String, String> getData(Map<String, Object> context) {
					//Map<String,String> datas=new HashMap<String,String>();
				    request.removeAllParameters();
					request.addParameter("idList",(String[]) context.get("idList"));
					return null;
				}
			 },context);
	}
	
	//指标数据返回
	@Test
	public void step06_itemInfoBackFirst() throws Exception{
		Map<String,Object> context = new HashMap<String,Object>();
		context.put("windowId", "ItemInfo");
		testAction("/framework/BackFirst-report.dto.ItemInfo.action",new TestDataProvider(){
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas=new HashMap<String,String>();
				datas.put("windowId",context.get("windowId").toString());
				return datas;
			}
		 },context);
	}
    private void SaveLevelRptDto (String dataFileName,int index) throws IOException
	 {
		 InputStream  is=this.getClass().getClassLoader().getResourceAsStream(dataFileName);
		 POIFSFileSystem fs = new POIFSFileSystem(is);
		 HSSFWorkbook wwb = new HSSFWorkbook(fs);
		 HSSFSheet sheet = wwb.getSheetAt(index);
		 for(int i =0;i<=sheet.getLastRowNum();i++){
			 String keyValue =sheet.getRow(i).getCell(0).getStringCellValue();
			 if(keyValue.equals("serviceParam.itemInfo.strItemCode")){
					 sheet.getRow(i).getCell(1).setCellValue(suitCode.toString());
				 }
		 }
	 }
    @Test
    @Rollback(false)
    public void step07_suitDeleteIdList() throws Exception{
		context.put("windowId", "Suit");
		context.put("idList",suitCode.toString());
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
