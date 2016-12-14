package baseDataGenerateProcess_szzxpt.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import extend.dto.Suit;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.ExcelUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReportInstInfoDataGenerate extends ActionTestCase {
	private static String dataFileName = "ReportInstInfo.xls";
	Map<String, Object> context = new HashMap<String, Object>();
	
	/**
	 * 点击桌面图标
	 */
	@Test
	@Rollback(false)
	public void step00_TestClickDesktopIcon(){
		try {
			initSuitInfo();// 初始化主题数据信息，此句话最好不要放到其它的@Test中，否则有可能引起不必要的错误
			context.put("windowId", "ReportInstInfo");
			testAction("/framework/ShowList-report.dto.ReportInstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstInfo");
		}
	}
	
	/**
	 * 点击新增按钮
	 */
	@Test
	public void step01_TestClickAddBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "ReportInstInfo");
			testAction("/framework/ShowSave-report.dto.ReportInstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstInfo");
		}
	}
	
	/**
	 * 
	 * <P>关键性数据保存测试</P>
	 */
	@Test
	public void step02_TestClickAddSavePivotalDatas(){
		int curSheetIndex = 1;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int dataCount = ExcelUtils.getTestDataSheetCount(dataFileName, "ReportInstInfo");
			if(dataCount > 0) {
				String resultCode = null;
				String expectResult = null;
				for(int index = 1; index <= dataCount; index++) {
					curSheetIndex = index;// 记录当前的sheet下标
					initServletMockObjects();
					resultCode = testAction("/framework/Save-report.dto.ReportInstInfo.action", dataFileName, index);
					// 判断结果
					expectResult = ExcelUtils.getExpectResult(dataFileName, index);
					if(!resultCode.equals(expectResult)) {
						fail("报送机构关键性数据新增保存测试失败，当前测试的数据位于"+dataFileName+"文件中的第"+(index+1)+"个sheet页中");
						break;
					} else {
						assertTrue(true);// 测试成功
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstInfo，报送机构关键性数据新增保存测试失败，当前测试的数据位于"+dataFileName+"文件中的第"+(curSheetIndex+1)+"个sheet页中");
		}
	}
	
	/**
	 * 新增保存按钮
	 */
	@Test
	@Rollback(false)
	public void step03_TestAddSaveBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/Save-report.dto.ReportInstInfo.action", dataFileName);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstInfo");
		}
	}
	
	/**
	 * 点击修改按钮
	 */
	@Test
	public void step04_TestUpdateBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "ReportInstInfo");
			context.put("id", ExcelUtils.getExcelValue(dataFileName, "0~0~1", "~"));
			testAction("/framework/ShowUpdate-report.dto.ReportInstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstInfo");
		}
	}
	
	/**
	 * 修改保存按钮
	 */
	@Test
	public void step05_TestUpdateSaveBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/Update-report.dto.ReportInstInfo.action", dataFileName);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstInfo");
		}
	}
	
	/**
	 * 点击子机构链接
	 */
	@Test
	public void step06_TestClickSubInstInfoLinked(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/ShowUpdateListLevelReportInstSubInfo-report.dto.ReportInstSubInfo.action", dataFileName);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstSubInfo");
		}
	}
	
	/**
	 * 子机构信息保存
	 */
	@Test
	@Rollback(false)
	public void step07_TestSaveSubInstInfo(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "ReportInstInfo");
			context.put("serviceParam.instInfoIdList", ExcelUtils.getExcelValue("InstInfo.xls", "0~0~1", "~"));
			context.put("serviceParam.reportInstInfo.strReportInstCode", ExcelUtils.getExcelValue(dataFileName, "0~0~1", "~"));
			testAction("/framework/UpdateListLevelReportInstSubInfo-report.dto.ReportInstSubInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstSubInfo");
		}
	}
	
	/**
	 * 点击删除（批量）按钮
	 */
	@Test
	public void step08_TestClickDeleteBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "ReportInstInfo");
			context.put("idList", ExcelUtils.getExcelValue(dataFileName, "0~0~1", "~"));
			testAction("/framework/DeleteListByIdList-report.dto.ReportInstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportInstInfo");
		}
	}
	
	/**
	 * <p>初始化主题信息<P>
	 * <p>当数据库中不存在主题信息时，新增主题信息<p>
	 * <p>如果数据库中不存在主题信息，则默认添加企业征信所对应的主题信息<p>
	 * @throws Exception
	 */
	private void initSuitInfo() throws Exception {
		Suit suit = null;
		int index = ExcelUtils.getRowNumByFieldName("ReportInstInfo.xls", "ReportInstInfo", "suit.strSuitCode");
		if(index > 0) {
			suit = new Suit();
			String condition = null;
			String suitCode = ExcelUtils.getExcelValue("ReportInstInfo.xls", "ReportInstInfo~"+index+"~1", "~");
			if(!StringUtils.isBlank(suitCode)) {
				suit.setStrSuitCode(suitCode);
				if(suitCode.equals("21")) {
					suit.setStrSuitName("企业征信");
					condition = "0~0~1~mavenTest12";
				} else if (suitCode.equals("22")) {
					suit.setStrSuitName("个人征信");
					condition = "0~0~1~mavenTest12345";
				} else if(suitCode.equals("24")) {
					suit.setStrSuitName("机构征信");
					condition = "0~0~1~mavenTest12";
				} else if(suitCode.equals("WHZH")) {
					suit.setStrSuitName("外汇账户");
					condition = "0~0~1~mavenTest12";
				}
			} else {
				suit.setStrSuitCode("21");
				suit.setStrSuitName("企业征信");
				condition = "0~0~1~mavenTest12";
			}
			
			// 修改报送金融机构的代码长度
			ExcelUtils.updateExcelValue(dataFileName, condition, "~");
			
			// 查询主题信息是否存在
			DetachedCriteria dc = DetachedCriteria.forEntityName("extend.dto.Suit");
			dc.add(Restrictions.eq("strSuitCode", suit.getStrSuitCode()));
			List<Object> suitList = ActionTestUtils.getDBData(dc);
			if(suitList == null || suitList.size() <= 0) {// 不存在
				ActionTestUtils.saveDBData(suit);
			}
		}
	}
	
	
}
