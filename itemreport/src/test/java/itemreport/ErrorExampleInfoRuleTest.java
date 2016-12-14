package itemreport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import report.dto.CalculationExampleInfo;
import report.dto.CalculationRule;
import report.dto.CurrencyType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import extend.dto.Suit;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.test.ActionTestCase;
import framework.test.CheckResult;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;

/**
 * 计算规则配置错误信息单元测试
 * 
 * @author Admin
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ErrorExampleInfoRuleTest extends ActionTestCase {
	int i = 0;
	String resultCode = null;
	public static Map<String, Object> IdList = new HashMap<String, Object>();
	Map<String, Object> context = new HashMap<String, Object>();
	public static String Id = null;

	@Test
	public void step00_errorExampleInfoRule() throws Exception {
		int sheetNum = ExcelUtils.getTestDataSheetCount("exampleInfoRuleError.xls", "CalculationRule");
		for (; i < sheetNum; i++) {
			initServletMockObjects();
			try {
				resultCode = testAction(new CheckResult() {

							@Override
							public void check(String resultCode,
									Object actionInstance) {
								Object serviceResult = LogicParamManager
										.getServiceResult();
								if (serviceResult instanceof MessageResult) {
									MessageResult mr = (MessageResult) serviceResult;
									assertEquals(mr.getMessage(), "error",
											resultCode);
								} else {
									assertEquals("error", resultCode);
								}
							}
						},"/framework/TreeNodeSave-report.dto.CalculationRule.action","exampleInfoRuleError.xls", i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!resultCode.equals("error")&& ExcelUtils.getExpectResult("exampleInfoRuleError.xls", i).equals("sucess")) {
				fail("报错的表是第" + i);
				Exception e = new Exception();
				e.printStackTrace();
				break;
			}
		}
	}

	// Suit
	@Test
	@Rollback(false)
	public void step01_forKeySuit() throws Exception {
		testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls", 1);
		Suit suit = (Suit) RequestManager.getTOject();
		IdList.put("suit", suit.getStrSuitCode());
	}

	// ItemInfo
	@Test
	@Rollback(false)
	public void step02_forKeyItemInfo() throws Exception {
		SaveLevelRptDto("forKeyAdd.xls", 5);
		testAction("/framework/TreeNodeSave-report.dto.ItemInfo.action","forKeyAdd.xls", 5);
		ItemInfo itemInfo = (ItemInfo) RequestManager.getTOject();
		IdList.put("itemInfo", itemInfo.getStrItemCode());
	}

	// ItemProperty
	@Test
	@Rollback(false)
	public void step03_forKeyItemProperty() throws Exception {
		testAction("/framework/Save-report.dto.ItemProperty.action","forKeyAdd.xls", 4);
		ItemProperty itemProperty = (ItemProperty) RequestManager.getTOject();
		IdList.put("itemProperty", itemProperty.getStrPropertyCode());
	}

	// CurrencyType
	@Test
	@Rollback(false)
	public void step04_forKeyCurrencyType() throws Exception {
		testAction("/framework/Save-report.dto.CurrencyType.action","forKeyAdd.xls", 3);
		CurrencyType currencyType = (CurrencyType) RequestManager.getTOject();
		String currencyTypeCode = currencyType.getStrCurrencyCode();
		IdList.put("currencyType", currencyTypeCode);
	}

	// CalculationExampleInfo
	@Test
	@Rollback(false)
	public void step05_forKeyCalculationExampleInfo() throws Exception {
		testAction("/framework/Save-report.dto.CalculationExampleInfo.action","exampleInfoRuleShowTree.xls", 3);
		CalculationExampleInfo calculationExampleInfo = (CalculationExampleInfo) RequestManager.getTOject();
		String calculationExampleInfoCode = calculationExampleInfo.getStrCalculationName();
		IdList.put("calculationExampleInfo", calculationExampleInfoCode);
	}

	@Test
	@Rollback(false)
	public void step06_addKeyCalculationRule() throws Exception {
		SaveLevelRptDto("exampleInfoRuleShowTree.xls", 1);
		testAction("/framework/TreeNodeSave-report.dto.CalculationRule.action","exampleInfoRuleShowTree.xls", 1);
		CalculationRule calculationRule = (CalculationRule) RequestManager.getTOject();
		Id = calculationRule.getAutoCalculationRuleID();

	}

	private void SaveLevelRptDto(String dataFileName, int index)throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(dataFileName);
		POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wwb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wwb.getSheetAt(index);
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			String keyValue = sheet.getRow(i).getCell(0).getStringCellValue();

			if (keyValue.equals("serviceParam.calculationRuleIdList")) {

				sheet.getRow(i).getCell(1).setCellValue(IdList.get("calculationExampleInfo").toString());
			} else if (keyValue.equals("serviceParam.rptInfo.strRptCode")) {
				sheet.getRow(i).getCell(1).setCellValue(IdList.get("rptInfo").toString());

			} else if (keyValue.equals("serviceParam.itemProperty.strPropertyCode")) {
				sheet.getRow(i).getCell(1).setCellValue(IdList.get("itemProperty").toString());
			} else if (keyValue.equals("serviceParam.currencyType.strCurrencyCode")) {
				sheet.getRow(i).getCell(1).setCellValue(IdList.get("currencyType").toString());
			} else if (keyValue.equals("serviceParam.itemInfo.strItemCode")) {
				sheet.getRow(i).getCell(1).setCellValue(IdList.get("itemInfo").toString());
			} else if (keyValue.equals("serviceParam.suit.strSuitCode")) {
				sheet.getRow(i).getCell(1).setCellValue(IdList.get("suit").toString());
			}
		}

		FileOutputStream fileOut = new FileOutputStream(this.getClass()
				.getResource("/" + dataFileName).toString().substring(6));
		wwb.write(fileOut);
		fileOut.close();
		fs.close();
		is.close();
	}

	// 子项
	@Test
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

	// 计算规则数据根据IdList删除
	@Test
	@Rollback(false)
	public void step09_CalculationRuleDeleteIdList() throws Exception {
		context.put("windowId", "CalculationRule");
		testAction("/framework/TreeNodeDelete-report.dto.CalculationRule.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						request.removeAllParameters();
						request.addParameter("idList",Id);
						return null;
					}
				}, context);

	}

	// 外键删除
	@Test
	@Rollback(false)
	public void step10_itemInfoDeleteIdList() throws Exception {
		context.put("windowId", "ItemInfo");
		context.put("idList", IdList.get("itemInfo"));
		testAction("/framework/DeleteListByIdList-report.dto.ItemInfo.action",new TestDataProvider() {

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
	public void step11_itemPropertyDeleteIdList() throws Exception {
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
	public void step12_currencyTypeDeleteIdList() throws Exception {
		context.put("windowId", "CurrencyType");
		context.put("idList", IdList.get("currencyType"));
		testAction("/framework/DeleteListByIdList-report.dto.CurrencyType.action",new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
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
	public void step13_calculationExampleInfoDeleteIdList() throws Exception {
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
	public void step14_suitDeleteIdList() throws Exception {
		context.put("windowId", "Suit");
		context.put("idList", IdList.get("suit"));
		testAction("/framework/TreeNodeDelete-extend.dto.Suit.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("idList", context.get("idList").toString());
						return datas;
					}
				}, context);

	}

}
