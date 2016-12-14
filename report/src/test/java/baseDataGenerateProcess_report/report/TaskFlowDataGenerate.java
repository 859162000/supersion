package baseDataGenerateProcess_report.report;

import java.util.ArrayList;
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

import report.dto.TaskFlow;

import extend.dto.Suit;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskFlowDataGenerate extends ActionTestCase {
	private Suit suit = null;
	private static String dataFileName = "TaskFlow.xls";
	private String instInfoFileName = "InstInfo.xls";
	List<String> condition = new ArrayList<String>();
	static Map<String, Object> context = new HashMap<String, Object>();
	
	@Test
	@Rollback(false)
	public void step00_TestClickAddBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());

			initSuitInfo();// 初始化主题数据信息，此句话最好不要放到其它的@Test中，否则有可能引起不必要的错误
			
			context.put("windowId", "TaskFlow");
			testAction("/framework/ShowSave-report.dto.TaskFlow.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskFlow");
		}
	}
	
	/**
	 * 
	 * <P>关键性数据保存测试</P>
	 */
	@Test
	public void step01_TestClickAddSavePivotalDatas(){
		int curSheetIndex = 1;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int dataCount = ExcelUtils.getTestDataSheetCount(dataFileName, "TaskFlow");
			if(dataCount > 0) {
				String resultCode = null;
				String expectResult = null;
				for(int index = 1; index <= dataCount; index++) {
					curSheetIndex = index;// 记录当前的sheet下标
					initServletMockObjects();
					resultCode = testAction("/framework/Save-report.dto.TaskFlow.action", dataFileName, index);
					// 判断结果
					expectResult = ExcelUtils.getExpectResult(dataFileName, index);
					if(!resultCode.equals(expectResult)) {
						fail("任务（TaskFlow）关键性数据新增保存测试失败，当前测试的数据位于"+dataFileName+"文件中的第"+(index+1)+"个sheet页中");
						break;
					} else {
						assertTrue(true);// 测试成功
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskFlow，任务（TaskFlow）关键性数据新增保存测试失败，当前测试的数据位于"+dataFileName+"文件中的第"+(curSheetIndex+1)+"个sheet页中");
		}
	}
	
	@Test
	@Rollback(false)
	public void step02_TestClickAddSaveBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/Save-report.dto.TaskFlow.action", dataFileName);
			// 将新增的数据的主键字段值写入Excel文件中
			Object task = RequestManager.getTOject();
			int rowIndex = ExcelUtils.getRowNumByFieldName("TaskFlow.xls", "TaskFlow", "id");
			ExcelUtils.updateExcelValue("TaskFlow.xls", "TaskFlow~"+rowIndex+"~1~"+((TaskFlow) task).getId(), "~");
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskFlow");
		}
	}
	
	
	@Test
	public void step03_TestClickUpdateBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			int rowIndex = ExcelUtils.getRowNumByFieldName("TaskFlow.xls", "TaskFlow", "id");
			String taskId = ExcelUtils.getExcelValue("TaskFlow.xls", "TaskFlow~"+rowIndex+"~1", "~");
			context.put("id", taskId);
			testAction("/framework/ShowUpdate-report.dto.TaskFlow.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskFlow");
		}
	}
	
	
	@Test
	public void step04_TestClickUpdateSaveBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/Update-report.dto.TaskFlow.action", dataFileName);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskFlow");
		}
	}
	
	
	@Test
	public void step05_TestClickMXBRWSZLinked(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("id", ExcelUtils.getExcelValue(dataFileName, "0~0~1", "~"));
			context.put("windowId", "TaskFlow");
			testAction("/report/InstInfoShowUpdateListLevelTaskModelInst-report.dto.TaskModelInst.action", ActionTestUtils.getTestDataProvider(), context);
			
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
					context.put("windowId", "TaskFlow");
					context.put("serviceParam.taskFlow.id", ExcelUtils.getExcelValue(dataFileName, "0~0~1", "~"));
					context.put("serviceParam.reportModel_TableIdList", tableIdList);
					context.put("serviceParam.instInfoIdList", ExcelUtils.getExcelValue(instInfoFileName, "0~0~1", "~"));
					//testAction("/framework/UpdateListLevelTaskModelInst-report.dto.TaskModelInst.action", getTestDataProvider(), context);
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskModelInst");
		}
	}
	
	@Test
	@Rollback(false)
	public void step06_TestClickTaskModelInstSaveBtn(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/UpdateListLevelTaskModelInst-report.dto.TaskModelInst.action",
				new TestDataProvider() {
						
					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						if(context != null){
							request.removeAllParameters();
							for(Map.Entry<String, Object> en : context.entrySet()){
								if(en.getValue().getClass().isArray()){
									request.addParameter(en.getKey(), (String[]) en.getValue());
								}else{
									request.addParameter(en.getKey(), en.getValue().toString());
								}
							}
						}
						context.clear();
						return null;
					}
				}, context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskFlow");
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
			suit = new Suit();
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
