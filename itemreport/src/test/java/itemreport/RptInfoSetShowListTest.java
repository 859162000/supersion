package itemreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import report.dto.RptInfo;
import report.dto.RptInfoSet;
import extend.dto.Suit;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;
import framework.test.ActionTestCase;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;

/**
 * 报表定义器------报表集合管理
 * @author Admin
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RptInfoSetShowListTest extends ActionTestCase {
	Map<String,Object> context = new HashMap<String,Object>();
	public static Map<String,Object> IdList = new HashMap<String,Object>();
	public static String Id = "";
	String[] tag = null;
    //报表数据定义
	//Suit
	@Test
	@Rollback(false)
	public void step00_suitAddTest() throws Exception{
		  testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls",1);
		  Suit suit = (Suit)RequestManager.getTOject();
		  IdList.put("suit", suit.getStrSuitCode());	 	 
		 
	 }
	 	 
	//RptInfo
	@Test
	@Rollback(false)
	public void step01_rptInfoAdd() throws Exception{
		 context.put("windowId", "RptInfo");
		 int row =ExcelUtils.getRowNumByFieldName("rptInfoAdd.xls", 0, "suit.strSuitCode");
		 
			// sheet下标+分隔符+行+分隔符+列+分隔符+值
		 String condition =0+"~"+row +"~"+1+"~"+IdList.get("suit");
		 ExcelUtils.updateExcelValue("rptInfoAdd.xls", condition, "~");
		// SaveLevelRptDto("rptInfoAdd.xls",0);
		 testAction("/framework/Save-report.dto.RptInfo.action","rptInfoAdd.xls",0);
		 RptInfo rptInfo = (RptInfo)RequestManager.getTOject(); 

         IdList.put("rptInfo",rptInfo.getStrRptCode());
		
	}
	
	//新增按钮
	@Test
	public void step02_rptInfoSetShowSave() throws Exception{
		context.put("windowId", "RptInfoSet");
		//tag = strRptCodeIdListGet();
		testAction("/framework/ShowSave-report.dto.RptInfoSet.action",new TestDataProvider() {
			
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas = new HashMap<String,String>();
				datas.put("windowId", context.get("windowId").toString());
				return datas;
			}
		},context);
	}
	//保存
	@Test
	@Rollback(false)
	public void step03_rptInfoSetSave() throws Exception{
		context.put("windowId", "RptInfoSet");
		testAction("/framework/Save-report.dto.RptInfoSet.action",new TestDataProvider() {
			
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas = new HashMap<String,String>();
				datas.put("windowId", context.get("windowId").toString());
				request.removeAllParameters();
				request.addParameter("serviceParam.strRptSetCode","7");
				request.addParameter("serviceParam.strRptSetName","Maven");
				request.addParameter("serviceParam.strRptCodeIdList",IdList.get("rptInfo").toString());
				return datas;
			}
		},context);
		RptInfoSet rptInfoSet =(RptInfoSet)RequestManager.getTOject();
		Id =rptInfoSet.getStrRptSetCode();
	}
	
	
	//修改 保存
	@Test
	public void step04_rptInfoSetUpdate() throws Exception{
		context.put("windowId", "RptInfoSet");
		context.put("id", Id);
		testAction("/framework/ShowUpdate-report.dto.RptInfoSet.action",new TestDataProvider() {
			
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas= new HashMap<String,String>();
				datas.put("id", context.get("id").toString());
				datas.put("windowId", context.get("windowId").toString());
				return datas;
			}
		},context);
		
		testAction("/framework/Update-report.dto.RptInfoSet.action",new TestDataProvider() {	
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas = new HashMap<String,String>();
				datas.put("windowId", context.get("windowId").toString());
				request.removeAllParameters();
				request.addParameter("serviceParam.strRptSetCode", "7");
				request.addParameter("serviceParam.strRptSetName","MavenTest");
				request.addParameter("serviceParam.strRptCodeIdList",IdList.get("rptInfo").toString());
				return datas;
			}
		},context);
	}
	
	//删除
	@Test
	@Rollback(false)
	public void step05_rptInfoSetShowSave() throws Exception{
		context.put("windowId", "RptInfoSet");
		context.put("idList", Id);
		testAction("/framework/DeleteListByIdList-report.dto.RptInfoSet.action",new TestDataProvider() {
			
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas = new HashMap<String,String>();
				datas.put("windowId", context.get("windowId").toString());
				datas.put("idList",context.get("idList").toString());
				return datas;
			      }
		},context);
	}
	
	private String[] strRptCodeIdListGet(){
		Map<String,String> map = new HashMap<String,String>();
		ShowSaveOrUpdate showSaveOrUpdate =(ShowSaveOrUpdate)LogicParamManager.getServiceResult();
		ArrayList<ShowFieldValue>  list =(ArrayList<ShowFieldValue>) showSaveOrUpdate.getShowFieldValueList();
		if(list.size()>0){
		for(ShowFieldValue showFieldValue: list){
			if(showFieldValue.getShowField().getFieldShowName().equals("报表名称")&&showFieldValue.getShowField().getFieldName().equals("rptInfoAndList")){
				 map =(Map<String, String>) showFieldValue.getTag();
			}
		}
			Set<Entry<String, String>> entryList = map.entrySet();
			  if(map.size()>0){
					 Set<String> setList =map.keySet();						 						   
				     tag=(String[]) setList.toArray(new String[] {});
					 
				   }
		
		}
		return tag;
	}
	//RptInfo删除
	@Test
	@Rollback(false)
	public void step06_rptInfoDeleteTest() throws Exception{
		 context.put("id", IdList.get("rptInfo"));
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
     public void step07_suitDeleteIdList() throws Exception{
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
