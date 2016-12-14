package zxptsystem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import baseDataGenerateProcess.coresystem.InstInfoDataGenerate;
import baseDataGenerateProcess.report.ReportInstInfoDataGenerate;
import baseDataGenerateProcess.report.TaskFlowDataGenerate;
import baseDataGenerateProcess.zxptsystem.GRZXReportGenerate;
import baseDataGenerateProcess.zxptsystem.ZXBaseDataGenerate;
import baseDataGenerateProcess.zxptsystem.ZXReportReviewed;

import report.dto.TaskFlow;

import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.ExcelUtils;

@RunWith(Suite.class)
@SuiteClasses(value={
	InstInfoDataGenerate.class,
	ReportInstInfoDataGenerate.class,
	TaskFlowDataGenerate.class,
	ZXBaseDataGenerate.class,
	ZXReportReviewed.class,
	GRZXReportGenerate.class
})
public class GRZXGenerateReportTest extends ActionTestCase {
	static boolean suitIsExistFlag = false;// 主题是否存在的标志，不存在：false
	static boolean zxFilePathUpdateFlag = false;
	static boolean zxVersionUpdateFlag = false;
	static String dataFileName = "ZXGenerateReport.xls";
	static List<String> condition = new ArrayList<String>();
	
	@BeforeClass
	public static void setSuitInfo(){
		String dtoName = null;
		try {
			// 修改报送机构的编码以及主题
			dtoName = "report.dto.ReportInstInfo";
			condition.add("0~2~1~22");
			condition.add("0~0~1~mavenTest12345");
			ExcelUtils.updateExcelValue("ReportInstInfo.xls", condition, "~");
			// 修改任务的主题
			dtoName = "report.dto.TaskFlow";
			ExcelUtils.updateExcelValue("TaskFlow.xls", "0~8~1~22", "~");
			
			ActionTestUtils.deleteFilePath(System.getProperty("java.io.tmpdir")+"exceptionFlagFile.txt");
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, dtoName);
		}
	}
	
	/**
	 * 测试结束，将所有的测试数据全部删除
	 */
	@AfterClass
	public static void deleteTestData(){
		try {
			clearAllTestDatasByDtoBeforeTest();

			// 删除企业征信报文文件保存路径
			ActionTestUtils.deleteFilePath(System.getProperty("java.io.tmpdir")+"GRZXReportFile");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * 
	 * <P>测试结束之后，清空掉指定DTO所涉及到的所有测试数据</P>
	 * <P>即使在每一次的测试流程结束之后都会进行数据的删除操作</P>
	 * <P>但是，有可能会由于某些特殊的原因导致数据删除不完整</P>
	 * <P>因此，此方法主要是针对于这种情况，避免数据保存时出现诸如“（逻辑）主键重复”的情况出现</P>
	 * @throws Exception 
	 */
	private static void clearAllTestDatasByDtoBeforeTest() throws Exception {
		// 获取所有的配置的要测试的明细段和基础段DTO
		List<String> allDtoList = ExcelUtils.getTestDto(dataFileName);
		if(allDtoList == null || allDtoList.size() <= 0) {
			return;
		}
		
		Field[] fs = null;
		JoinColumn jc = null;
		String fieldType = null;
		DetachedCriteria dc = null;
		List<String> mxDtoList = new ArrayList<String>();
		//List<String> jcDtoList = new ArrayList<String>();
		for(String dto : allDtoList) {
			fs = Class.forName(dto).getDeclaredFields();
			if(fs != null && fs.length > 0) {
				for(Field f : fs) {
					jc = f.getAnnotation(JoinColumn.class);
					if(jc != null) {// 不为空，说明当前的DTO有外键字段
						fieldType = f.getType().toString().replace("class", "").trim();
						if(allDtoList.contains(fieldType)) {
							mxDtoList.add(dto);
							jc = null;
							break;
						}
					}
				}
			}
		}
		
		allDtoList.removeAll(mxDtoList);
		
		// 删除明细段数据
		ActionTestUtils.deleteDBData(mxDtoList);
		// 删除基础段数据
		ActionTestUtils.deleteDBData(allDtoList);
		
		// 删除明细任务机构树数据
		List<Object> taskList = ActionTestUtils.getDBData("TaskFlow.xls", "TaskFlow", "report.dto.TaskFlow");
		List<Object> instInfoList = ActionTestUtils.getDBData("InstInfo.xls", "0", "coresystem.dto.InstInfo");
		dc = DetachedCriteria.forClass(Class.forName("report.dto.TaskModelInst"));
		boolean delFlag = false;
		if(taskList != null && taskList.size() > 0) {
			dc.add(Restrictions.in("taskFlow", taskList));
			delFlag = true;
		}
		if(instInfoList != null && instInfoList.size() > 0) {
			dc.add(Restrictions.in("instInfo", instInfoList));
			delFlag = true;
		}
		
		if(delFlag) {
			ActionTestUtils.deleteDBData(dc);
		}
		
		// 删除任务数据
		dc = ExcelUtils.getDetachedCriteriaList("TaskFlow.xls", "TaskFlow", "report.dto.TaskFlow");
		dc.add(Restrictions.in("suit.strSuitCode", new String[] {"21","22","24","WHZH"}));
		ActionTestUtils.deleteDBData(dc);
		
		// 删除报送机构数据
		dc = ExcelUtils.getDetachedCriteriaList("ReportInstInfo.xls", "ReportInstInfo", "report.dto.ReportInstInfo");
		dc.add(Restrictions.in("suit.strSuitCode", new String[] {"21","22","24","WHZH"}));
		dc.add(Restrictions.in("strReportInstCode", new String[] {"mavenTest12345","mavenTest12"}));
		ActionTestUtils.deleteDBData(dc);
		
		// 删除机构数据
		dc = ExcelUtils.getDetachedCriteriaList("InstInfo.xls", "InstInfo", "coresystem.dto.InstInfo");
		ActionTestUtils.deleteDBData(dc);
	}
	
}
