package report;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import baseDataGenerateProcess.coresystem.InstInfoDataGenerate;
import baseDataGenerateProcess.report.ReportInstInfoDataGenerate;

import framework.test.ActionTestUtils;


@RunWith(Suite.class)
@SuiteClasses(value={
	InstInfoDataGenerate.class,
	ReportInstInfoDataGenerate.class
})
public class ReportInstInfoTest {
	@BeforeClass
	public static void testClassEnd() {
		try {
			ActionTestUtils.deleteFilePath(System.getProperty("java.io.tmpdir")+"exceptionFlagFile.txt");
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, "extend.dto.Suit");
		}
	}
	
	@AfterClass
	public static void endTest() {
		try {
			// 删除测试数据
			List<String> dtoNameList = new ArrayList<String>();
			dtoNameList.add("report.dto.ReportInstInfo");
			dtoNameList.add("coresystem.dto.InstInfo");
			ActionTestUtils.deleteDBData(dtoNameList);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
}
