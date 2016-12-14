package itemreport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import coresystem.dto.InstInfo;

import report.dto.CalculationExampleInfo;
import report.dto.CalculationRule;
import report.dto.CurrencyType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import extend.dto.Suit;
import framework.interfaces.RequestManager;
import framework.test.ActionTestCase;
import framework.test.TestDataProvider;

/**
 * 计算规则配置单元测试
 * 
 * @author Admin
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInfoRuleShowTreeTest extends ActionTestCase {
	public static String Id = null;
	Map<String, Object> context = new HashMap<String, Object>();
	// 外键的数据构建
	public final static Map<String, String> IdList = new HashMap<String, String>();

	// Suit主题
	@Test
	@Rollback(false)
	public void step00_forKeySuitAdd() throws Exception {
		testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls", 1);
		Suit suit = (Suit) RequestManager.getTOject();
		String suitCode = suit.getStrSuitCode();
		IdList.put("suit", suitCode);
	}

	// ItemInfo
	@Test
	@Rollback(false)
	public void step01_forKeyItemInfoAdd() throws Exception {
		SaveRptDto("forKeyAdd.xls", 5);
		testAction("/framework/TreeNodeSave-report.dto.ItemInfo.action","forKeyAdd.xls", 5);
		ItemInfo itemInfo = (ItemInfo) RequestManager.getTOject();
		String itemInfoCode = itemInfo.getStrItemCode();
		IdList.put("itemInfo", itemInfoCode);
	}

	// ItemProperty
	@Test
	@Rollback(false)
	public void step02_forKeyItemPropertyAdd() throws Exception {
		testAction("/framework/Save-report.dto.ItemProperty.action","forKeyAdd.xls", 4);
		ItemProperty itemProperty = (ItemProperty) RequestManager.getTOject();
		String itemPropertyCode = itemProperty.getStrPropertyCode();
		IdList.put("itemProperty", itemPropertyCode);
		System.out.println(IdList);
	}

	// CurrencyType
	@Test
	@Rollback(false)
	public void step03_forKeyCurrencyTypeAdd() throws Exception {
		context.put("windowId", "CurrencyType");
		testAction("/framework/ShowList-report.dto.CurrencyType.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		testAction("/framework/ShowSave-report.dto.CurrencyType.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		testAction("/framework/Save-report.dto.CurrencyType.action","forKeyAdd.xls", 3);
		CurrencyType currencyType = (CurrencyType) RequestManager.getTOject();
		String currencyTypeCode = currencyType.getStrCurrencyCode();
		IdList.put("currencyType", currencyTypeCode);
	}

	// 计算实例管理新增
	@Test
	@Rollback(false)
	public void step04_CalculationExampleInfoAdd() throws Exception {
		context.put("windowId", "CalculationExampleInfo");
		testAction("/framework/ShowList-report.dto.CalculationExampleInfo.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);

		testAction("/framework/ShowSave-report.dto.CalculationExampleInfo.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);
		testAction("/framework/Save-report.dto.CalculationExampleInfo.action","exampleInfoRuleShowTree.xls", 3);
		CalculationExampleInfo calculationExampleInfo = (CalculationExampleInfo) RequestManager.getTOject();
		String calculationExampleInfoCode = calculationExampleInfo.getStrCalculationName();
		IdList.put("calculationExampleInfo", calculationExampleInfoCode);
	}

	// 计算规则数据新增
	@Test
	@Rollback(false)
	public void step05_CalculationRuleAdd() throws Exception {
		context.put("windowId", "CalculationRule");
		testAction("/framework/ShowTree-report.dto.ExampleInfoRule.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		testAction("/framework/ShowListForTree-report.dto.CalculationRule.action","exampleInfoRuleShowTree.xls", 0);
		SaveRptDto("exampleInfoRuleShowTree.xls", 1);
		testAction("/framework/TreeNodeSave-report.dto.CalculationRule.action","exampleInfoRuleShowTree.xls", 1);
		CalculationRule calculationRule = (CalculationRule) RequestManager.getTOject();
		Id = calculationRule.getAutoCalculationRuleID();
	}

	// 计算规则数据修改
	@Test
	@Rollback(true)
	public void step06_CalculationRuleUpdate() throws Exception {
		context.put("id", Id);
		context.put("windowId", "CalculationRule");
		testAction("/framework/ShowUpdateForTree-report.dto.CalculationRule.action",new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);
		initServletMockObjects();
		SaveRptDto("exampleInfoRuleShowTree.xls", 2);
		testAction("/framework/Update-report.dto.CalculationRule.action","exampleInfoRuleShowTree.xls", 2);
	}

	// 计算规则数据层级子项
	@Test
	@Rollback(false)
	public void step07_ItemsCalculation() throws Exception {
		context.put("id", Id);

		testAction("/report/ShowCalcRuleCfg-report.dto.ItemsCalculation.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);

		String calcdate = "[{'type':'item','curr':'RMB','itemCode':'0123','property':'itemPropertyCheck','extend1':'1','extend2':'1','time':'2','freq':'4','dtDate':'2016-05-19','instInfo':'mavenTest'},{'type':'symbol','op':'+'},{'type':'const','constv':'5'}]";
		context.put("calcData", calcdate);
		context.put("vs", "S");
		testAction("/ajax/ShowFromSavebyAjax-report.dto.ItemsCalculation.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("calcData", context.get("calcData").toString());
						datas.put("vs", context.get("vs").toString());
						return datas;
					}
				}, context);
	}

	// 会计项数据校验
	@Test
	@Rollback(false)
	public void step08_CalcRuleDetailShow() throws Exception {
		context.put("id", "QY_HTJEXX");
		testAction("/report/ShowCalcRuleDetail-report.dto.ItemsCalculation.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);
	}

	// 计算规则数据根据Id删除
	@Test
	@Rollback(true)
	public void step09_CalculationRuleDelete() throws Exception {
		context.put("id", Id);
		testAction("/framework/DeleteByIdForTree-report.dto.CalculationRule.action",new TestDataProvider() {
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);
	}

	// 计算规则数据根据IdList删除
	@Test
	@Rollback(false)
	public void step10_CalculationRuleDeleteIdList() throws Exception {
		context.put("windowId", "CalculationRule");
		testAction("/framework/TreeNodeDelete-report.dto.CalculationRule.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						request.removeAllParameters();
						request.addParameter("idList", Id);
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);

	}

	// 外键删除
	@Test
	@Rollback(false)
	public void step11_itemInfoDeleteIdList() throws Exception {
		context.put("windowId", "ItemInfo");
		context.put("idList", IdList.get("itemInfo"));
		testAction("/framework/DeleteListByIdList-report.dto.ItemInfo.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						datas.put("idList", context.get("idList").toString());
						return datas;
					}
				}, context);

	}

	@Test
	@Rollback(false)
	public void step12_itemPropertyDeleteIdList() throws Exception {
		context.put("windowId", "ItemInfo");
		context.put("idList", IdList.get("itemProperty"));
		testAction("/framework/DeleteListByIdList-report.dto.ItemProperty.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("idList", context.get("idList").toString());
						return datas;
					}
				}, context);

	}

	@Test
	@Rollback(false)
	public void step13_currencyTypeDeleteIdList() throws Exception {
		context.put("windowId", "CurrencyType");
		context.put("idList", IdList.get("currencyType"));
		testAction("/framework/DeleteListByIdList-report.dto.CurrencyType.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("idList", context.get("idList").toString());
						return datas;
					}
				}, context);

	}

	@Test
	@Rollback(false)
	public void step14_calculationExampleInfoDeleteIdList() throws Exception {
		context.put("windowId", "CalculationExampleInfo");
		context.put("idList", IdList.get("calculationExampleInfo"));
		testAction("/framework/DeleteListByIdList-report.dto.CalculationExampleInfo.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("idList", context.get("idList").toString());
						return datas;
					}
				}, context);

	}

	@Test
	@Rollback(false)
	public void step15_suitDeleteIdList() throws Exception {
		context.put("windowId", "Suit");
		context.put("idList", IdList.get("suit"));
		testAction("/framework/TreeNodeDelete-extend.dto.Suit.action",new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("idList", context.get("idList").toString());
						return datas;
					}
				}, context);

	}

	private void SaveRptDto(String dataFileName, int index) throws IOException {
		POIFSFileSystem fs = null;
		InputStream is = null;
		FileOutputStream fileOut = null;
		try {
			is = this.getClass().getClassLoader().getResourceAsStream(dataFileName);
			fs = new POIFSFileSystem(is);
			HSSFWorkbook wwb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wwb.getSheetAt(index);

			for(int i =0;i<=sheet.getLastRowNum();i++){
				 String keyValue =sheet.getRow(i).getCell(0).getStringCellValue();			 
			/*if (sheet != null) {
				for (int i = 0; i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					cell = row.getCell(0);

					keyValue = cell.getStringCellValue();

					cell = row.getCell(1);
					if (cell == null) {
						cell = row.createCell(1);
					}
*/
					if (keyValue.equals("serviceParam.suit.strSuitCode")) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(IdList.get("suit")));
						// sheet.getRow(i).getCell(1).setCellValue(IdList.get("suit").toString());
					} else if (keyValue.equals("serviceParam.itemInfo.strItemCode")) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(IdList.get("itemInfo")));
						// sheet.getRow(i).getCell(1).setCellValue(IdList.get("itemInfo"));
					} else if (keyValue.equals("serviceParam.autoCalculationRuleID")&&Id.trim().length() > 0) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(Id));
						// sheet.getRow(i).getCell(1).setCellValue(Id.toString());
					} else if (keyValue.equals("serviceParam.calculationRuleIdList")) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(IdList.get("calculationExampleInfo")));
						// sheet.getRow(i).getCell(1).setCellValue(IdList.get("calculationExampleInfo"));
					} else if (keyValue.equals("serviceParam.rptInfo.strRptCode")) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(IdList.get("rptInfo")));
						// sheet.getRow(i).getCell(1).setCellValue(IdList.get("rptInfo"));
					} else if (keyValue.equals("serviceParam.itemProperty.strPropertyCode")) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(IdList.get("itemProperty")));
						// sheet.getRow(i).getCell(1).setCellValue(IdList.get("itemProperty"));
					} else if (keyValue.equals("serviceParam.currencyType.strCurrencyCode")) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(IdList.get("currencyType")));
						// sheet.getRow(i).getCell(1).setCellValue(IdList.get("currencyType"));
					} else if (keyValue.equals("serviceParam.itemInfo.strItemCode")) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(IdList.get("itemInfo")));
						// sheet.getRow(i).getCell(1).setCellValue(IdList.get("itemInfo"));
					} else if (keyValue.equals("serviceParam.autoCalculationRuleID")) {
						sheet.getRow(i).getCell(1).setCellValue(new HSSFRichTextString(IdList.get("calculationExampleInfo")));
					}
				}// end for

				fileOut = new FileOutputStream(this.getClass().getResource("/" + dataFileName).toString().substring(6));
				wwb.write(fileOut);
			//}// end if
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
			if (fs != null) {
				fs.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

}
