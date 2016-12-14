package itemreport;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.test.ActionTestCase;
import framework.test.CheckResult;
import framework.test.ExcelUtils;

public class DtoSaveTest extends ActionTestCase {
	String dataFileName = "";

	// 部分数据单元测试
	// Suit
	@Test
	public void errorSuitForKey() throws Exception {
		int sheetNum = ExcelUtils
				.getTestDataSheetCount("suitError.xls", "Suit");
		for (int i = 0; i < sheetNum; i++) {
			initServletMockObjects();
			testAction(new CheckResult() {
				public void check(String resultCode, Object actionInstance) {
					try {
						Object serviceResult = LogicParamManager
								.getServiceResult();
						if (serviceResult instanceof MessageResult) {
							MessageResult mr = (MessageResult) serviceResult;
							assertEquals(mr.getMessage(), "error", resultCode);
						} else {
							assertEquals("error", resultCode);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, "/framework/TreeNodeSave-extend.dto.Suit.action",
					"suitError.xls", i);
		}
	}

	// InstInfo
	@Test
	public void errorInstInfoForKey() throws Exception {
		int sheetNum = ExcelUtils.getTestDataSheetCount("InstInfoError.xls",
				"InstInfo");
		for (int i = 0; i < sheetNum; i++) {
			initServletMockObjects();
			testAction(new CheckResult() {
				public void check(String resultCode, Object actionInstance) {
					try {
						Object serviceResult = LogicParamManager
								.getServiceResult();
						if (serviceResult instanceof MessageResult) {
							MessageResult mr = (MessageResult) serviceResult;
							assertEquals(mr.getMessage(), "error", resultCode);
						} else {
							assertEquals("error", resultCode);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, "/framework/TreeNodeSave-coresystem.dto.InstInfo.action",
					"InstInfoError.xls", i);
		}
	}
}
