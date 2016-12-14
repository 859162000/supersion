package itemreport;

/**
 * 校验规则配置
 * @author Admin
 *
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.annotation.Rollback;

import report.dto.CalculationExampleInfo;
import report.dto.CheckRule;
import report.dto.CheckInstance;
import report.dto.CurrencyType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;

import extend.dto.Suit;
import framework.interfaces.RequestManager;
import framework.test.ActionTestCase;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckInstanceRuleShowTreeTest extends ActionTestCase {
	public static String Id = null;
	Map<String, Object> context = new HashMap<String, Object>();
	// 外键的数据构建
	public final static Map<String, String> IdList = new HashMap<String, String>();

	// Suit
	@Test
	@Rollback(false)
	public void step00_suitAddTest() throws Exception {
		testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls", 1);
		Suit suit = (Suit) RequestManager.getTOject();
		String suitCode = suit.getStrSuitCode();
		IdList.put("suit", suitCode);

	}

	// ItemInfo
	@Test
	@Rollback(false)
	public void step01_forKeyItemInfoAdd() throws Exception {
		SaveLevelRptDto("forKeyAdd.xls", "", 5);
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
	}

	// CurrencyType
	@Test
	@Rollback(false)
	public void step03_forKeyCurrencyTypeAdd() throws Exception {
		testAction("/framework/Save-report.dto.CurrencyType.action","forKeyAdd.xls", 3);
		CurrencyType currencyType = (CurrencyType) RequestManager.getTOject();
		String currencyTypeCode = currencyType.getStrCurrencyCode();
		IdList.put("currencyType", currencyTypeCode);
	}

	// 校验实例管理新增
	@Test
	@Rollback(false)
	public void step04_CheckInstanceAdd() throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("windowId", "checkInstance");
		testAction("/framework/ShowList-report.dto.CheckInstance.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);
		initServletMockObjects();

		testAction("/framework/ShowSave-report.dto.CheckInstance.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);
		initServletMockObjects();
		testAction("/framework/Save-report.dto.CheckInstance.action","checkInstance.xls", 0);
		CheckInstance checkInstance = (CheckInstance) RequestManager.getTOject();
		String instanceName = checkInstance.getInstanceName();
		IdList.put("checkInstance", instanceName);
	}

	// 校验规则数据新增
	@Test
	@Rollback(false)
	public void step05_CheckRuleAdd() throws Exception {
		context.put("windowId", "CheckRule");
		testAction("/framework/ShowTree-report.dto.CheckInstanceRule.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);
		context.put("type","report.dto.CheckInstanceRule,report.dto.CheckInstance");
		context.put("id", "");
		initServletMockObjects();
		testAction("/framework/ShowListForTree-report.dto.CheckRule.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString()); 
						datas.put("id", context.get("id").toString());
						datas.put("type", context.get("type").toString());
						return datas;
					}
				}, context);
		initServletMockObjects();
		testAction("/framework/ShowSaveForTree-report.dto.CheckRule.action",new TestDataProvider() {
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);

		SaveLevelRptDto("checkRuleShowTree.xls", "", 0);
		initServletMockObjects();
		testAction("/framework/TreeNodeSave-report.dto.CheckRule.action","checkRuleShowTree.xls", 0);
		CheckRule CheckRule = (CheckRule) RequestManager.getTOject();
		Id = CheckRule.getAutoCalculationRuleID();
	}

	// 校验规则数据修改
	@Test
	@Rollback(true)
	public void step06_CheckRuleUpdate() throws Exception {
		context.put("id", Id);
		context.put("windowId", "CheckRule");
		testAction("/framework/ShowUpdateForTree-report.dto.CheckRule.action",new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);
		initServletMockObjects();
		SaveLevelRptDto("checkRuleShowTree.xls", Id, 1);
		testAction("/framework/Update-report.dto.CheckRule.action","checkRuleShowTree.xls", 1);
	}

	private void SaveLevelRptDto(String dataFileName, String id, int index)throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(dataFileName);
		POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wwb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wwb.getSheetAt(index);
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			String keyValue = sheet.getRow(i).getCell(0).getStringCellValue();

			if (keyValue.equals("serviceParam.calculationRuleIdList")) {

				sheet.getRow(i).getCell(1).setCellValue(
						IdList.get("checkInstance"));
			} else if (keyValue.equals("serviceParam.rptInfo.strRptCode")) {
				sheet.getRow(i).getCell(1).setCellValue(IdList.get("rptInfo"));

			} else if (keyValue
					.equals("serviceParam.itemProperty.strPropertyCode")) {
				sheet.getRow(i).getCell(1).setCellValue(
						IdList.get("itemProperty"));
			} else if (keyValue
					.equals("serviceParam.currencyType.strCurrencyCode")) {
				sheet.getRow(i).getCell(1).setCellValue(
						IdList.get("currencyType"));
			} else if (keyValue.equals("serviceParam.itemInfo.strItemCode")) {
				sheet.getRow(i).getCell(1).setCellValue(IdList.get("itemInfo"));
			} else if (keyValue.equals("serviceParam.autoCalculationRuleID")
					&& id.trim().length() > 0) {
				sheet.getRow(i).getCell(1).setCellValue(id.toString());
			} else if (keyValue.equals("serviceParam.suit.strSuitCode")) {
				sheet.getRow(i).getCell(1).setCellValue(IdList.get("suit"));
			}
		}

		FileOutputStream fileOut = new FileOutputStream(this.getClass()
				.getResource("/" + dataFileName).toString().substring(6));
		wwb.write(fileOut);
		fileOut.close();
		fs.close();
		is.close();
	}

	// 校验规则数据层级子项
	@Test
	@Rollback(false)
	public void step07_ItemsCalculation() throws Exception {
		context.put("id", Id);

		testAction("/report/ShowCalcRuleCfg-report.dto.ItemsCheck.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);
		initServletMockObjects();
		String calcdate = "[{'type':'item','curr':'RMB','itemCode':'0123','property':'itemPropertyCheck','extend1':'1','extend2':'1','time':'2','freq':'4','dtDate':'2016-05-19','instInfo':'mavenTest'},{'type':'symbol','op':'+'},{'type':'const','constv':'5'}]";
		context.put("calcData", calcdate);
		context.put("vs", "S");
		testAction("/ajax/ShowFromSavebyAjax-report.dto.ItemsCheck.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("calcData", context.get("calcData")
								.toString());
						datas.put("vs", context.get("vs").toString());
						return datas;
					}
				}, context);
	}

	// 会计项数据校验
	@Test
	@Rollback(false)
	public void step08_CalcRuleDetailShow() throws Exception {
		context.put("id", "QY_BLYW_BS");
		testAction(
				"/report/ShowCalcRuleDetail-report.dto.ItemsCalculation.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);
	}

	// 校验规则数据根据Id删除
	@Test
	@Rollback(true)
	public void step09_CheckRuleDelete() throws Exception {
		context.put("id", Id);
		testAction("/framework/DeleteByIdForTree-report.dto.CheckRule.action",
				new TestDataProvider() {
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);
	}

	// 校验规则数据根据IdList删除
	@Test
	@Rollback(false)
	public void step10_CheckRuleDeleteIdList() throws Exception {
		context.put("windowId", "CheckRule");
		testAction("/framework/TreeNodeDelete-report.dto.CheckRule.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						request.removeAllParameters();
						request.addParameter("idList", Id);
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
		testAction("/framework/DeleteListByIdList-report.dto.ItemInfo.action",
				new TestDataProvider() {

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
	public void step12_itemPropertyDeleteIdList() throws Exception {
		context.put("windowId", "ItemInfo");
		context.put("idList", IdList.get("itemProperty"));
		testAction(
				"/framework/DeleteListByIdList-report.dto.ItemProperty.action",
				new TestDataProvider() {

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
	public void step13_currencyTypeDeleteIdList() throws Exception {
		context.put("windowId", "CurrencyType");
		context.put("idList", IdList.get("currencyType"));
		testAction(
				"/framework/DeleteListByIdList-report.dto.CurrencyType.action",
				new TestDataProvider() {

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
	public void step14_CheckInstanceDeleteIdList() throws Exception {
		context.put("windowId", "checkInstance");
		context.put("idList", IdList.get("checkInstance"));
		testAction(
				"/framework/DeleteListByIdList-report.dto.CheckInstance.action",
				new TestDataProvider() {

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

	public void step15_CalculationRuleUpdate() throws Exception {
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
		SaveLevelRptDto("exampleInfoRuleShowTree.xls", Id, 2);
		testAction("/framework/Update-report.dto.CalculationRule.action","exampleInfoRuleShowTree.xls", 2);
	}

	@Test
	@Rollback(false)
	public void step15_suitDeleteIdList() throws Exception {
		context.put("windowId", "Suit");
		context.put("idList", IdList.get("suit"));
		testAction("/framework/TreeNodeDelete-extend.dto.Suit.action",
				new TestDataProvider() {

					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("idList", context.get("idList").toString());
						return datas;
					}
				}, context);

	}
}
