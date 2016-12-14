package itemreport;

import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.test.ActionTestCase;
import framework.test.CheckResult;
import framework.test.ExcelUtils;

import org.junit.Test;

public class ErrorSaveReportmodel_tableSetTest extends ActionTestCase {
	// 报表定义 ----报表定义器 ---明细表集合管理错误数据的添加
	@Test
	public void errorReportmodel_table() {
		int sheetNum = ExcelUtils.getTestDataSheetCount(
				"reportmodel_tableSetError.xls", "Reportmodel_tableSet");
		for (int i = 0; i < sheetNum; i++) {
			try {
				testAction(
						new CheckResult() {
							public void check(String resultCode,
									Object actionInstance) {
								initServletMockObjects();
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
						},"/framework/Save-report.dto.Reportmodel_tableSet.action","reportmodel_tableSetError.xls", i);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
