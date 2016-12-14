package itemreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import report.dto.CheckInstance;
import framework.interfaces.RequestManager;
import framework.test.ActionTestCase;
import framework.test.TestDataProvider;

/**
 * 校验规则实例管理单元测试
 * 
 * @author Admin
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckInstanceShowListTest extends ActionTestCase {
	// 校验实例新增
	public static String InstanceName = null;
	Map<String, Object> context = new HashMap<String, Object>();

	@Test
	@Rollback(false)
	public void step00_CheckInstanceAdd() throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("windowId", "checkInstance");
		testAction("/framework/ShowList-report.dto.CheckInstance.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);

		testAction("/framework/ShowSave-report.dto.CheckInstance.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);

		testAction("/framework/Save-report.dto.CheckInstance.action","checkInstance.xls", 0);
		CheckInstance checkInstance = (CheckInstance) RequestManager.getTOject();
		InstanceName = checkInstance.getInstanceName();
	}

	// 校验实例修改
	@Test
	@Rollback(true)
	public void step01_CheckInstanceUpdate() throws Exception {
		// 数据修改
		context.put("id", InstanceName);
		testAction("/framework/ShowUpdate-report.dto.CheckInstance.action",
				new TestDataProvider() {
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);
		context.put("dblTolerance", 5);
		initServletMockObjects();

		testAction("/framework/Update-report.dto.CheckInstance.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("serviceParam.dblTolerance", context.get(
								"dblTolerance").toString());
						datas.put("serviceParam.instanceName", context
								.get("id").toString());
						return datas;
					}
				}, context);
	}

	// 校验实例查询
	@Test
	@Rollback(false)
	public void step02_CheckInstanceSelect() throws Exception {
		// 数据查询
		context.put("instanceName", InstanceName);
		testAction("/framework/ShowList-report.dto.CheckInstance.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("serviceParam.InstanceName", context.get(
								"instanceName").toString());
						return datas;
					}
				}, context);
	}

	// 校验实例删除
	@Test
	@Rollback(false)
	public void step03_CheckInstanceDelete() throws Exception {
		context.put("id", InstanceName);
		testAction("/framework/DeleteById-report.dto.CheckInstance.action",
				new TestDataProvider() {

					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("id", context.get("id").toString());
						return datas;
					}
				}, context);

	}

}
