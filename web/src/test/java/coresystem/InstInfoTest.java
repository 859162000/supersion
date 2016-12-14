package coresystem;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import framework.test.ActionTestUtils;
import framework.test.ExcelUtils;

import baseDataGenerateProcess.coresystem.InstInfoDataGenerate;

@RunWith(Suite.class)
@SuiteClasses(value = {
	InstInfoDataGenerate.class
})
public class InstInfoTest {
	@BeforeClass
	public static void testClassEnd() {
		ActionTestUtils.deleteFilePath(System.getProperty("java.io.tmpdir")+"exceptionFlagFile.txt");
	}
	
	@AfterClass
	public static void deleteTestDBData() {
		try {
			deleteDBData();// 测试结束清除测试数据
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * 删除数据库数据
	 * @throws Exception
	 */
	private static void deleteDBData() throws Exception {
		ActionTestUtils.deleteDBData(ExcelUtils.getDetachedCriteriaList("InstInfo.xls", "0", "coresystem.dto.InstInfo"));
		ActionTestUtils.deleteFilePath(System.getProperty("java.io.tmpdir")+"exceptionFlagFile.txt");
	}
}
