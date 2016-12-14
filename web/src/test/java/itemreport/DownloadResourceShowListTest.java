package itemreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import report.dto.DownloadResource;

import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowList;
import framework.show.ShowValue;
import framework.test.ActionTestCase;
import framework.test.TestDataProvider;

/**
 * 下载资源库------文件下载单元测试
 * 
 * @author Admin
 * 
 */
public class DownloadResourceShowListTest extends ActionTestCase {
	// 文件下载
	Map<String, Object> context = new HashMap<String, Object>();
	String[] idList = null;

	@Test
	@Rollback(true)
	public void fileDown() throws Exception {
		context.put("windowId", "DownloadResourceStoreroom");
		testAction("/framework/ShowList-report.dto.DownloadResource.action",new TestDataProvider() {
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		String id = "";
		ShowList showList = (ShowList) LogicParamManager.getServiceResult();
		List<List<ShowValue>> showValue = showList.getValueTable();
		if (showValue != null && showValue.size() != 0) {
			for (int i = 0; i < showValue.size(); i++) {
				List<ShowValue> str = showValue.get(i);
				for (int j = 0; j < str.size(); j++) {
					id = str.get(0).getValue();
					System.out.println("第" + i + "个的id" + id);
					break;

				}
			}

			context.put("id", id);
			testAction("/report/DownloadResource-report.dto.DownloadResource.action",new TestDataProvider() {
						public Map<String, String> getData(Map<String, Object> context) {
							Map<String, String> datas = new HashMap<String, String>();
							datas.put("windowId", context.get("windowId").toString());
							datas.put("id", context.get("id").toString());
							return datas;
						}
					}, context);
		}
	}

	// 文件下载删除
	@Test
	@Rollback(true)
	public void FileDelete() throws Exception {
		context.put("windowId", "DownloadResourceStoreroom");
		List<String> list = new ArrayList<String>();
		testAction("/framework/ShowList-report.dto.DownloadResource.action",new TestDataProvider() {
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		ShowList showList = (ShowList) LogicParamManager.getServiceResult();
		List<List<ShowValue>> showValue = showList.getValueTable();
		for (int i = 0; i < showValue.size(); i++) {
			List<ShowValue> str = showValue.get(i);
			for (int j = 0; j < str.size(); j++) {
				list.add(str.get(0).getValue());
				System.out.println("第" + i + "个的id" + str.get(0).getValue());
				break;

			}
		}
		idList = (String[]) list.toArray(new String[list.size()]);

		testAction("/framework/DeleteListByIdList-report.dto.DownloadResource.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						request.removeAllParameters();
						request.addParameter("idList", idList);
						return datas;
					}
				}, context);

	}

	// 文件下载查询
	public void FileSelect() throws Exception {
		context.put("windowId", "DownloadResourceStoreroom");
		context.put("suit", "22");
		context.put("startTime", "2015-10-26");
		testAction("/framework/ShowList-report.dto.DownloadResource.action",new TestDataProvider() {
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("serviceParam.suit.strSuitCode", context.get("suit").toString());
						datas.put("serviceParam.startTime", context.get("startTime").toString());

						return datas;
					}
				}, context);

	}

}
