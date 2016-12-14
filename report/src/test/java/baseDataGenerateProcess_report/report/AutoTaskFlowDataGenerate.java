package baseDataGenerateProcess_report.report;

import java.lang.reflect.Field;
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

import report.dto.InstInfoSet;

import extend.dto.Suit;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.CheckResult;
import framework.test.ExcelUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoTaskFlowDataGenerate extends ActionTestCase {
	private static String dataFileName = "AutoTaskFlow.xls";
	static Map<String, Object> context = new HashMap<String, Object>();
	
	/**
	 * 
	 * <P>点击桌面图标</P>
	 */
	@Test
	@Rollback(false)
	public void step00_TestClickDeskTopIcon() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());

			initSuitInfo();// 初始化主题数据信息，此句话最好不要放到其它的@Test中，否则有可能引起不必要的错误
			
			context.put("windowId", "AutoTaskFlow");
			testAction("/framework/ShowList-report.dto.AutoTaskFlow.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.AutoTaskFlow，点击桌面“自动报表任务定义器”图标出错");
		}
	}
	
	
	/**
	 * 
	 * <P>显示新增数据界面</P>
	 */
	@Test
	@Rollback(false)
	public void step01_TestClickAddBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "AutoTaskFlow");
			testAction("/framework/ShowSave-report.dto.AutoTaskFlow.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.AutoTaskFlow，点击界面“新增”按钮出错");
		}
	}
	
	/**
	 * 
	 * <P>关键性数据新增保存测试</P>
	 */
	@Test
	public void step02_TestClickAddSavePivotalDatas() {
		int curSheetIndex = 1;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int sheetCount = ExcelUtils.getTestDataSheetCount(dataFileName, "AutoTaskFlow");// 获取存放关键性测试数据的sheet页总数
			if(sheetCount > 0) {
				String resultCode = null;
				String expectResult = null;
				
				for(int index = 1; index <= sheetCount; index++) {
					curSheetIndex = index;
					expectResult = ExcelUtils.getExpectResult(dataFileName, index);
					initServletMockObjects();
					resultCode = testAction("/framework/Save-report.dto.AutoTaskFlow.action", dataFileName, index);
					
					if(!resultCode.equals(expectResult)) {
						fail("自动任务关键性数据新增保存测试失败，当前测试的数据位于"+dataFileName+"文件中的第"+(index+1)+"个sheet页中");
						break;
					} else {
						assertTrue(true);
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.AutoTaskFlow，自动任务关键性数据新增保存测试失败，当前测试的数据位于"+dataFileName+"文件中的第"+(curSheetIndex+1)+"个sheet页中");
		}
	}
	
	/**
	 * 
	 * <P>新增保存</P>
	 */
	@Test
	@Rollback(false)
	public void step03_TestClickAddSaveBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					try {
						Object serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult) {
							assertEquals("测试失败，当前测试DTO是：report.dto.AutoTaskFlow，错误信息如下："+((MessageResult) serviceResult).getMessage(), "success", resultCode);
						} else {
							assertEquals("测试失败，当前测试DTO是：report.dto.AutoTaskFlow", "success", resultCode);
						}
						
						// 如果新增成功，将新增数据的主键字段值放入相应的Excel文件中
						if(resultCode.equals("success")) {
							Object dataObj = RequestManager.getTOject();
							Field pkField = ActionTestUtils.getPKField("report.dto.AutoTaskFlow");
							String pkVal = ReflectOperation.getFieldValue(dataObj, pkField.getName()).toString();
							
							int rowIndex = ExcelUtils.getRowNumByFieldName(dataFileName, "AutoTaskFlow", pkField.getName());
							ExcelUtils.updateExcelValue(dataFileName, "AutoTaskFlow~"+rowIndex+"~1~"+pkVal, "~");
						}
					} catch (Exception e) {
						ActionTestUtils.setTestResultWhenException(e, "report.dto.AutoTaskFlow，自动任务新增保存测试失败");
					}
				}
			}, "/framework/Save-report.dto.AutoTaskFlow.action", dataFileName);
			
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.AutoTaskFlow，自动任务新增保存测试失败");
		}
	}
	
	/**
	 * 
	 * <P>点击修改按钮</P>
	 */
	@Test
	public void step04_TestClickUpdateBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			Field pkField = ActionTestUtils.getPKField("report.dto.AutoTaskFlow");
			int rowIndex = ExcelUtils.getRowNumByFieldName(dataFileName, "AutoTaskFlow", pkField.getName());
			String pkVal = ExcelUtils.getExcelValue(dataFileName, "AutoTaskFlow~"+rowIndex+"~1", "~");
			
			context.put("id", pkVal);
			context.put("windowId", "AutoTaskFlow");
			testAction("/framework/ShowUpdate-report.dto.AutoTaskFlow.action", ActionTestUtils.getTestDataProvider(), context);
			
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.AutoTaskFlow，自动任务新增保存测试失败");
		}
	}
	
	/**
	 * 
	 * <P>点击修改保存按钮</P>
	 */
	@Test
	public void step05_TestClickUpdateSaveBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			String resultCode = testAction("/framework/Update-report.dto.AutoTaskFlow.action", dataFileName);
			if(resultCode.equals("success")) {
				initServletMockObjects();
				resultCode = testAction("/framework/BackFirst-report.dto.AutoTaskFlow.action", null);
				if(!resultCode.equals("success")) {
					fail("数据修改成功，返回上一层级时出错");
				}
			} else {
				fail("修改数据保存时出错");
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.AutoTaskFlow，自动任务修改保存测试失败");
		}
	}
	
	/**
	 * 
	 * <P>点击明细表任务设置连接</P>
	 */
	@Test
	public void step06_TestClickMXRWSZLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			Field pkField = ActionTestUtils.getPKField("report.dto.AutoTaskFlow");
			int rowIndex = ExcelUtils.getRowNumByFieldName(dataFileName, "AutoTaskFlow", pkField.getName());
			String pkVal = ExcelUtils.getExcelValue(dataFileName, "AutoTaskFlow~"+rowIndex+"~1", "~");
			
			context.put("id", pkVal);
			context.put("windowId", "AutoTaskFlow");
			String resultCode = testAction("/report/InstInfoShowUpdateListLevelAutoTaskModelInst-report.dto.AutoTaskModelInst.action", ActionTestUtils.getTestDataProvider(), context);

			ShowSaveOrUpdate saveOrUpdate = (ShowSaveOrUpdate) LogicParamManager.getServiceResult();
			if(saveOrUpdate != null && saveOrUpdate.getShowFieldValueList() != null && saveOrUpdate.getShowFieldValueList().size() > 0){
				Map<String, String> tag = null;
				List<ShowFieldValue> showFieldValueList = saveOrUpdate.getShowFieldValueList();
				for(ShowFieldValue sfv : showFieldValueList){
					if(sfv.getShowField() != null && ("reportModel_Table").equals(sfv.getShowField().getFieldName())){// 找到表名
						tag = (Map<String, String>) sfv.getTag();
						break;
					}
				}
				if(tag != null){
					int index = 0;
					String[] tableIdList = new String[tag.entrySet().size()];
					for(Map.Entry<String, String> en : tag.entrySet()){
						tableIdList[index] = en.getKey();
						index ++;
					}
					
					// 获取任务主键字段信息
					pkField = ActionTestUtils.getPKField("report.dto.AutoTaskFlow");
					rowIndex = ExcelUtils.getRowNumByFieldName("AutoTaskFlow.xls", "AutoTaskFlow", pkField.getName());
					pkVal = ExcelUtils.getExcelValue("AutoTaskFlow.xls", "AutoTaskFlow~"+rowIndex+"~1", "~");
					
					context.put("windowId", "AutoTaskFlow");
					context.put("serviceParam.autoTaskFlow.id", pkVal);
					context.put("serviceParam.reportModel_TableIdList", tableIdList);
					
					
					// 获取机构主键字段信息
					pkField = ActionTestUtils.getPKField("coresystem.dto.InstInfo");
					rowIndex = ExcelUtils.getRowNumByFieldName("InstInfo.xls", "InstInfo", pkField.getName());
					pkVal = ExcelUtils.getExcelValue("InstInfo.xls", "AutoTaskFlow~"+rowIndex+"~1", "~");

					context.put("serviceParam.instInfoIdList", pkVal);
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.AutoTaskFlow，点击“明细表任务设置”连接");
		}
	}
	
	
	@Test
	public void step07_TestShowInstSetAjax() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					try {
						// 获取数据库机构集的信息
						DetachedCriteria dc = DetachedCriteria.forClass(InstInfoSet.class);
						List<Object> tableSet = ActionTestUtils.getDBData(dc);
						
						// 获取界面初始化时返回的
						
						
						
						assertEquals("界面初始化时显示机构集信息失败", "success", resultCode);
					} catch (Exception e) {
						ActionTestUtils.setTestResultWhenException(e, "null，获取机构集数据信息失败");
					}
					
					
				}
			}, "/report/Select-showInstSet.action", null);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "null，界面初始化时显示机构集信息失败");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * <p>初始化主题信息<P>
	 * <p>当数据库中不存在主题信息时，新增主题信息<p>
	 * <p>如果数据库中不存在主题信息，则默认添加企业征信所对应的主题信息<p>
	 * @throws Exception
	 */
	private void initSuitInfo() throws Exception {
		int index = ExcelUtils.getRowNumByFieldName("TaskFlow.xls", "TaskFlow", "suit.strSuitCode");
		if(index > 0) {
			Suit suit = new Suit();
			String condition = null;
			String suitCode = ExcelUtils.getExcelValue("TaskFlow.xls", "TaskFlow~"+index+"~1", "~");
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
