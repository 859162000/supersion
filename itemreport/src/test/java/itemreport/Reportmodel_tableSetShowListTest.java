package itemreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.test.annotation.Rollback;

import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowValue;
import framework.test.ActionTestCase;
import framework.test.CheckResult;
import framework.test.TestDataProvider;

import org.apache.commons.lang.xwork.StringUtils;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.sun.xml.internal.stream.Entity;

import report.dto.Reportmodel_tableSet;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/**
 * 2016-05-17:获取了当前系统中的明细表ID集
 */
public class Reportmodel_tableSetShowListTest extends ActionTestCase{
	public static String Id = null;
	Map<String,Object> context = new HashMap<String,Object>();
	String[] tag = null;
   //报表定义   ----报表定义器   ---明细表集合管理数据的添加
	@Test
	@Rollback(false)
	 public void step00_reportmodel_tableSetAdd() throws Exception{
		context.put("windowId", "Reportmodel_tableSet");	
		testAction("/framework/ShowList-report.dto.Reportmodel_tableSet.action",new TestDataProvider(){
				
			public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					datas.put("windowId",context.get("windowId").toString());
					return datas;
				}
			 },context);
		initServletMockObjects();
		testAction("/framework/ShowSave-report.dto.Reportmodel_tableSet.action",new TestDataProvider(){
				
			public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					datas.put("windowId",context.get("windowId").toString());
					return datas;
				}
			 },context);

		tag=getMXList();
		initServletMockObjects();
		testAction("/framework/Save-report.dto.Reportmodel_tableSet.action",new TestDataProvider() {
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas = new HashMap<String,String>();
				datas.put("windowId", context.get("windowId").toString());
				request.removeAllParameters();
				request.addParameter("serviceParam.strReportmodel_tableSetCode", "yyzzz");
				request.addParameter("serviceParam.strReportmodel_tableSetName", "dffufh");
				if(tag != null){
					if( tag.length>0 ){
						request.addParameter("serviceParam.strReportmodel_tableIdList",tag);					
					}		
			}else{
				request.addParameter("serviceParam.strReportmodel_tableIdList", new String[0]);
			}
				return datas;
			}
		},context);
		Reportmodel_tableSet rt = (Reportmodel_tableSet)RequestManager.getTOject();
		Id =rt.getStrReportmodel_tableSetCode();
	}
	//明细表集合管理数据修改
	@Test
	@Rollback(true)
	public void step01_reportmodel_tableSetUpdate() throws Exception{
		context.put("windowId", "Reportmodel_tableSet");
		context.put("id", Id);
		testAction("/framework/ShowUpdate-report.dto.Reportmodel_tableSet.action",new TestDataProvider(){	
			public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					datas.put("windowId",context.get("windowId").toString());
					datas.put("id", context.get("id").toString());
					return datas;
				}
			 },context);
		//context.put("serviceParam.strReportmodel_tableSetName", "Toooh");
	    //testAction("/framework/Update-report.dto.Reportmodel_tableSet.action","reportmodel_tableSetShowList.xls",1);
        tag = getMXList();
		testAction("/framework/Update-report.dto.Reportmodel_tableSet.action",new TestDataProvider() {
			
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas= new HashMap<String,String>();
				//datas.put("windowId", context.get("Reportmodel_tableSet").toString());
				//datas.put("id", context.get("id").toString());
				request.removeAllParameters();
				request.addParameter("serviceParam.strReportmodel_tableSetCode", "yyzzz");
				request.addParameter("serviceParam.strReportmodel_tableSetName", "Toooh");
				if(null != tag ){
					if(tag.length>0){
						request.addParameter("serviceParam.strReportmodel_tableIdList",tag);
					}
				}else{	
				}	request.addParameter("serviceParam.strReportmodel_tableIdList", new String[0]);
				
				return datas;
			}
		},context);
	}
	//明细表集合管理数据查询
    @Test
    @Rollback(false)
	public void step02_reportmodel_tableSetSelect() throws Exception{
    	context.put("windowId", "Reportmodel_tableSet");
		context.put("serviceParam.strReportmodel_tableSetCode", Id);
		testAction("/framework/ShowList-report.dto.Reportmodel_tableSet.action",new TestDataProvider(){
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String,String> datas=new HashMap<String,String>();
				datas.put("windowId",context.get("windowId").toString());
				datas.put("serviceParam.strReportmodel_tableSetCode",context.get("serviceParam.strReportmodel_tableSetCode").toString());
				return datas;
			}
		 },context);
	    
	}
	
	//明细表集合管理数据删除
	@Test
	@Rollback(false)
	public void step03_reportmodel_tableSetIdListDelete() throws Exception{
		 ArrayList<String> list = new ArrayList<String>();
		list.add(Id);
		final String[] idList = list.toArray(new String[list.size()]);
		context.put("windowId","Reportmodel_tableSet");
		context.put("idList", Id);
		testAction("/framework/DeleteListByIdList-report.dto.Reportmodel_tableSet.action",new TestDataProvider(){
				
			public Map<String, String> getData(Map<String, Object> context) {
					Map<String,String> datas=new HashMap<String,String>();
					datas.put("windowId",context.get("windowId").toString());
					datas.put("idList",context.get("idList").toString());
					//request.addParameter("serviceParam.strReportmodel_tableSetCode", idList);
					return datas;
				}
			 },context);
	}
	private String[] getMXList(){
		ShowSaveOrUpdate saveOrUpdate = ((ShowSaveOrUpdate) LogicParamManager.getServiceResult());
		if(saveOrUpdate != null) {
			List<ShowFieldValue> showFieldVaList = saveOrUpdate.getShowFieldValueList();
			if(showFieldVaList != null) {
				ShowField showField = null;
				for(ShowFieldValue showFieldValue : showFieldVaList){
					if(showFieldValue != null) {
						showField = showFieldValue.getShowField();
						if(!StringUtils.isBlank(showField.getFieldName())&&showField.getFieldName().equals("Reportmodel_tableAndList")&& showField.getFieldShowName().equals("表名") ) {
						  Map<String,String> map =(Map<String,String>) showFieldValue.getTag();
						  Set<Entry<String, String>> entryList = map.entrySet();
						  if(map.size()>0){
							 Set<String> setList =map.keySet();						 						   
						     tag=(String[]) setList.toArray(new String[] {});
							 
						   }
						}
					}
				}	
			}
		}
	  return tag;
	}
}
