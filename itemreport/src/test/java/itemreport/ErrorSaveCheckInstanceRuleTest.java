package itemreport;

import org.junit.Test;

import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.test.ActionTestCase;
import framework.test.CheckResult;
import framework.test.ExcelUtils;

public class ErrorSaveCheckInstanceRuleTest extends ActionTestCase {
	int i = 0;

	@Test
	public void errorCheckInstanceRule() {
		try {
			String resultCode = null;
			int sheetNum = ExcelUtils.getTestDataSheetCount(
					"checkRuleError.xls", "CheckInstanceRule");
			for (; i < sheetNum; i++) {
				initServletMockObjects();
				resultCode = testAction(
						new CheckResult() {
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
						},
						"/framework/TreeNodeSave-report.dto.CheckRule.action",
						"checkRuleError.xls", i);

			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
