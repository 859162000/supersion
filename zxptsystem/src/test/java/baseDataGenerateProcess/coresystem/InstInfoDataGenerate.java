package baseDataGenerateProcess.coresystem;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.CheckResult;
import framework.test.ExcelUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstInfoDataGenerate extends ActionTestCase {
//	private static boolean initTestDBDataFlag = true;// 是否初始化测试数据
	static int pivotalDataSheetIndex = 1;
	private static String dataFileName = "InstInfo.xls";
	List<String> condition = new ArrayList<String>();
	Map<String, Object> context = new HashMap<String, Object>();
	private String importDataFileName = "ExcelFileForInstInfoImport.xls";
	
	/**
	 * 点击桌面图标（机构信息管理） 注：点击桌面图标时执行了点击树形菜单时所执行的action链接，故此就没做单独的测试
	 */
	@Test
	public void step00_TestClickDesktopIcon() {
		try {
			context.put("windowId", "InstInfo");
			testAction("/framework/ShowTree-coresystem.dto.InstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 点击新增按钮
	 */
	@Test
	public void step01_TestClickAddBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfo");
			testAction("/framework/ShowSave-coresystem.dto.InstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 点击返回按钮
	 */
	@Test
	public void step02_TestClickBackBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfo");
			testAction("/framework/BackFirst-coresystem.dto.InstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 再次点击新增按钮
	 */
	@Test
	public void step03_TestClickAddBtnAgain() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfo");
			testAction("/framework/ShowSave-coresystem.dto.InstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 关键性数据测试
	 */
	@Test
	public void step04_TestClickAddSavePivotalDatas() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int dataCount = ExcelUtils.getTestDataSheetCount(dataFileName, "InstInfo");
			if(dataCount > 0) {
				String resultCode = null;
				for(int i = 1; i <= dataCount; i++){
					initServletMockObjects();
					pivotalDataSheetIndex = i;
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							Object serviceResult = LogicParamManager.getServiceResult();
							if(serviceResult instanceof MessageResult) {
								assertEquals(((MessageResult) serviceResult).getMessage()+"，当前测试的sheet下标是"+pivotalDataSheetIndex, 
										ExcelUtils.getExpectResult(dataFileName, pivotalDataSheetIndex), resultCode);
							} else {
								assertEquals("测试失败，当前测试的sheet下标是"+pivotalDataSheetIndex, 
										ExcelUtils.getExpectResult(dataFileName, pivotalDataSheetIndex), resultCode);
							}
						}
					}, "/framework/TreeNodeSave-coresystem.dto.InstInfo.action", dataFileName, i);
					if (!resultCode.equals(ExcelUtils.getExpectResult(dataFileName, i))) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "coresystem.dto.InstInfo，当前正在测试关键性数据，此组数据存放的sheet下标是"+pivotalDataSheetIndex);
		}
	}
	
	/**
	 * 新增保存（正常数据）
	 */
	@Test
	@Rollback(false)
	public void step05_TestClickAddSaveBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/TreeNodeSave-coresystem.dto.InstInfo.action", dataFileName);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 导出数据
	 */
	@Test
	@Rollback(false)
	public void step06_TestClickExport() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfo");
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					try {
						Object serviceResult = LogicParamManager.getServiceResult();
						if (serviceResult != null) {
							if (serviceResult instanceof DownloadResult) {
								DownloadResult downloadResult = (DownloadResult) serviceResult;
								InputStream is = downloadResult.getInputStream();
								checkExportFile(is);
							} else if (serviceResult instanceof MessageResult) {
								assertEquals(((MessageResult) serviceResult).getMessage(), "success", resultCode);
							} else {
								assertEquals("导出失败", "success", resultCode);
							}
						}
					} catch (Exception e) {
						fail(e.getMessage());
					}
				}
			}, "/framework/ExportData-coresystem.dto.InstInfo.action", ActionTestUtils.getTestDataProvider(), context);
			// 删除已经新增的数据
			ActionTestUtils.deleteDBData(ExcelUtils.getDetachedCriteriaList(dataFileName, "0", "coresystem.dto.InstInfo"));
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 导入数据
	 */
	@Test
	@Rollback(false)
	public void step07_TestClickImport() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			String uploadFileParh = this.getClass().getResource("/" + importDataFileName).toString().substring(6);
			context.put("uploadFile", uploadFileParh);
			context.put("windowId", "InstInfo");
			testAction("/framework/ImportData-coresystem.dto.InstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 点击修改链接
	 */
	@Test
	public void step08_TestClickUpdateLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfo");
			context.put("id", ExcelUtils.getExcelValue(dataFileName, "0~0~1", "~"));
			testAction("/framework/ShowUpdate-coresystem.dto.InstInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 修改保存
	 */
	@Test
	public void step09_TestClickUpdateSave() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			condition.add("0~addRow~serviceParam.UpdateTime~" + updateTime.toString());
			ExcelUtils.updateExcelValue(dataFileName, condition, "~");
			testAction("/framework/TreeNodeUpdate-coresystem.dto.InstInfo.action",dataFileName);
			// 删除添加的excel文件中的数据行
			condition.add("0~endRow");
			ExcelUtils.deleteExcelRow(dataFileName, condition, "~");
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

	/**
	 * 点击查询按钮
	 */
	@Test
	public void step10_TestClickQuery() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("currentPage", "1");
			context.put(ExcelUtils.getExcelValue(dataFileName, "0~0~0", "~"), ExcelUtils.getExcelValue(dataFileName, "0~0~1", "~"));
			context.put(ExcelUtils.getExcelValue(dataFileName, "0~1~0", "~"), ExcelUtils.getExcelValue(dataFileName, "0~1~1", "~"));
			context.put(ExcelUtils.getExcelValue(dataFileName, "0~2~0", "~"), ExcelUtils.getExcelValue(dataFileName, "0~2~1", "~"));
			testAction("/framework/ShowListForTree-coresystem.dto.InstInfo.action", dataFileName);// 这里没写页数
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}

//	@Test
//	public void step11_TestLogout(){
//		try {
//			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
//			// 注销
//			initServletMockObjects();
//			testAction("/coresystem/Logout-coresystem.dto.UserInfo.action", "");
//		} catch (Exception e) {
//			ActionTestUtils.setTestResultWhenException(e, null);
//		}
//	}
//	
//	@Test
//	public void step12_TLogin(){
//		try {
//			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
//			// 重新登录
//			context.put("serviceParam.strUserCode", "admin");
//			context.put("serviceParam.strPassword", "123");
//			testAction("/coresystem/Login-coresystem.dto.UserInfo.action", ActionTestUtils.getTestDataProvider(), context);
//		} catch (Exception e) {
//			ActionTestUtils.setTestResultWhenException(e, null);
//		}
//	}
	
	/**
	 * 校验导出文件是否成功
	 * @param is 输入流
	 */
	private void checkExportFile(InputStream is) {
		try {
			if (is != null) {
				boolean hasData = false;
				List<Object> instInfoList = ActionTestUtils.getDBData(dataFileName, "0", "coresystem.dto.InstInfo");// 查看数据库是否有数据
				if(instInfoList != null && instInfoList.size() > 0) {// 数据库中有数据
					hasData = true;
				}
				
				is.reset();
				if(is.available() > 0) {
					HSSFWorkbook wb = new HSSFWorkbook(is);
					HSSFSheet sheet = wb.getSheetAt(0);
					if(sheet != null) {
						int maxRowNum = sheet.getLastRowNum();
						if(maxRowNum < 0) {
							fail("导出失败，导出文件中无标题行");
						} else {
							if(hasData) {
								if(maxRowNum == 0) {
									fail("导出失败，导出数据与数据库数据不匹配");
								}
							} else {
								if(maxRowNum >= 1) {
									fail("导出失败，导出数据与数据库数据不匹配");
								}
							}
						}
					} else {
						fail("导出数据文件中存放数据的sheet页不存在");
					}
				} else {
					if(hasData) {
						fail("导出失败，当前导出文件中没有任何数据");
					}
				}
			} else {
				fail("导出文件失败");
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}
	
}
