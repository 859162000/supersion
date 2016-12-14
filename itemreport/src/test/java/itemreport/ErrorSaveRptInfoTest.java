package itemreport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import extend.dto.Suit;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.CheckResult;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import report.dto.RptDto;
import report.dto.RptInfo;

import com.sun.source.tree.ContinueTree;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ErrorSaveRptInfoTest extends ActionTestCase {
	// 报表信息管理
	int i = 0;
	Map<String, Object> context = new HashMap<String, Object>();
	public static String Id;
	public static Map<String, Object> IdList = new HashMap<String, Object>();

	@Test
	public void step00_errorRptInfo() {
		String resultCode = null;
		int sheetNum = ExcelUtils.getTestDataSheetCount("rptInfoError.xls",
				"RptInfo");
		try {
			for (; i < sheetNum; i++) {
				resultCode = testAction(
						new CheckResult() {
							public void check(String resultCode,
									Object actionInstance) {
								initServletMockObjects();
								Object serviceResult = LogicParamManager
										.getServiceResult();
								if (serviceResult instanceof MessageResult) {
									MessageResult mr = (MessageResult) serviceResult;
									String expectResult = ExcelUtils
											.getExpectResult(
													"rptInfoError.xls", i);
									if (expectResult.equals(resultCode)) {
										fail(mr.getMessage());
									} else {
										return;
									}
								}
							}
						}, "/framework/Save-report.dto.RptInfo.action",
						"rptInfoError.xls", i);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Suit
	@Test
	@Rollback(false)
	public void step01_forKeySuit() throws Exception {
		testAction("/framework/TreeNodeSave-extend.dto.Suit.action",
				"forKeyAdd.xls", 1);
		Suit suit = (Suit) RequestManager.getTOject();
		IdList.put("suit", suit.getStrSuitCode());
	}

	// 报表信息管理 -----明细管理

	@Test
	@Rollback(false)
	public void step02_rptInfoSave() throws Exception {
		SaveLevelRptDto("rptInfoAdd.xls", 0);
		testAction("/framework/Save-report.dto.RptInfo.action",
				"rptInfoAdd.xls", 0);
		RptInfo rptInfo = (RptInfo) RequestManager.getTOject();
		Id = rptInfo.getStrRptCode();

	}

	@Test
	@Rollback(false)
	public void step03_ShowListForTree() throws Exception {
		context.put("id", "ftb0123");
		context.put("type", "extend.dto.Suit");
		context.put("windowId", "RptInfo");
		testAction("/framework/ShowListForTree-report.dto.RptInfo.action",
				new TestDataProvider() {
					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						datas.put("windowId", context.get("windowId")
								.toString());
						datas.put("type", context.get("type").toString());
						return datas;
					}
				}, context);

	}

	@Test
	@Rollback(false)
	public void step04_ShowListLevelRptDto() throws Exception {
		context.put("id", Id);
		context.put("windowId", "RptInfo");
		testAction("/framework/ShowListLevelRptDto-report.dto.RptDto.action",
				new TestDataProvider() {
					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);
	}

	@Test
	@Rollback(false)
	public void step05_ShowSaveLevelRptDto() throws Exception {
		context.put("windowId", "RptInfo");
		testAction("/framework/ShowSaveLevelRptDto-report.dto.RptDto.action",
				new TestDataProvider() {
					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);
	}

	@Test
	public void step06_rptInfoLevelMXError() throws Exception {

		int sheetNum = ExcelUtils.getTestDataSheetCount(
				"rptInfoLevelMXError.xls", "RptDto");
		int rowNum = -1;
		String resultCode = null;
		try {
			for (; i < sheetNum; i++) {
				initServletMockObjects();
				rowNum = ExcelUtils.getRowNumByFieldName(
						"rptInfoLevelMXError.xls", i, "rptInfo.strRptCode");
				initServletMockObjects();
				String condition = i + "_" + String.valueOf(rowNum) + "_1_"
						+ Id;
				ExcelUtils.updateExcelValue("rptInfoLevelMXError.xls",
						condition, "_");
				resultCode = testAction(
						new CheckResult() {
							public void check(String resultCode,
									Object actionInstance) {
								Object serviceResult = LogicParamManager
										.getServiceResult();
								if (serviceResult instanceof MessageResult) {
									MessageResult mr = (MessageResult) serviceResult;
									String expectResult = ExcelUtils
											.getExpectResult(
													"rptInfoLevelMXError.xls",
													i);
									if (expectResult.equals(resultCode)) {
										fail(mr.getMessage());
									} else {
										return;
									}
								}
							}
						},
						"/framework/SaveLevelRptDto-report.dto.RptDto.action",
						"rptInfoLevelMXError.xls", i);

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			System.out.println("成功啦、、、、、");
		}

	}

	@Test
	@Rollback(false)
	public void step07_rptInfoDelete() throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("id", Id);
		testAction("/framework/DeleteByIdForTree-report.dto.RptInfo.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);

	}

	private void SaveLevelRptDto(String dataFileName, int index)
			throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				dataFileName);
		POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wwb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wwb.getSheetAt(index);
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			String keyValue = sheet.getRow(i).getCell(0).getStringCellValue();

			if (keyValue.equals("serviceParam.suit.strSuitCode")) {
				sheet.getRow(i).getCell(1).setCellValue(
						IdList.get("suit").toString());
			}
		}

		FileOutputStream fileOut = new FileOutputStream(this.getClass()
				.getResource("/" + dataFileName).toString().substring(6));
		wwb.write(fileOut);
		fileOut.close();
		fs.close();
		is.close();
	}

	@Test
	@Rollback(false)
	public void step08_suitDeleteIdList() throws Exception {
		context.put("windowId", "Suit");
		context.put("idList", IdList.get("suit"));
		testAction("/framework/TreeNodeDelete-extend.dto.Suit.action",
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
}
