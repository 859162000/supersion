package report;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import framework.test.ActionTestUtils;
import framework.test.ExcelUtils;

import baseDataGenerateProcess.coresystem.InstInfoDataGenerate;
import baseDataGenerateProcess.report.TaskFlowDataGenerate;

@RunWith(Suite.class)
@SuiteClasses(value = {
	InstInfoDataGenerate.class,
	TaskFlowDataGenerate.class
})
public class TaskFlowTest {
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
		// 删除测试数据
		try {
			int rowIndex = ExcelUtils.getRowNumByFieldName("TaskFlow.xls", "TaskFlow", "id");
			
			DetachedCriteria dc = DetachedCriteria.forClass(Class.forName("report.dto.TaskModelInst"));
			dc.add(Restrictions.eq("taskFlow.id", ExcelUtils.getExcelValue("TaskFlow.xls", "TaskFlow~"+rowIndex+"~1", "~")));
			ActionTestUtils.deleteDBData(dc);
			
			List<String> dtoNameList = new ArrayList<String>();
			dtoNameList.add("report.dto.TaskFlow");
			dtoNameList.add("coresystem.dto.InstInfo");
			ActionTestUtils.deleteDBData(dtoNameList);
			
			// 清空当前的任务对应的Excel中的主键字段对应的值
			ExcelUtils.updateExcelValue("TaskFlow.xls", "TaskFlow~"+rowIndex+"~1", "~");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
}
