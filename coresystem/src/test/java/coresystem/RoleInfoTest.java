package coresystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.CheckResult;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;

/**
 * 
 * @description <p>角色数据测试类</P>
 * 				<p>在新增或者修改数据成功之后，都会返回到数据列表展示界面，故此没做单独的返回按钮测试</P>
 * @author transino_liutao
 * @createDate 2016/06/07 PM
 * @lastUpdateTime
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleInfoTest extends ActionTestCase {
	private String dataFileName = "RoleInfo.xls";
	private String resultCode = null;
	private int curTestDataIndex = 1;// 当前的测试数据的下标（即：第几组数据或者第几个sheet页中的数据）
	private Map<String, Object> context = new HashMap<String, Object>();
	private static Map<Integer, Map<String, Object>> datasMap = null;// 多组测试数据记录Map
	 
	/**
	 * 
	 * <P>点击桌面图标</P>
	 * @author liutao
	 * @createDate 2016/06/08 AM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step00_TestClickDesktopIcon() {
		try {
			context.put("windowId", "RoleInfo");
			testAction("/framework/ShowList-coresystem.dto.RoleInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击桌面图标出错");
		}
	}
	
	/**
	 * 
	 * <P>点击新增按钮</P>
	 * @author liutao
	 * @createDate 2016/06/08 AM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step01_TestClickAddBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "RoleInfo");
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					checkShowSaveOrUpdateViewerResult(resultCode, "新增按钮");
				}
			}, "/framework/ShowSave-coresystem.dto.RoleInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击新增按钮出错");
		}
	}
	
	/**
	 * 
	 * <P>角色信息关键性数据测试</P>
	 * @author liutao
	 * @createDate 2016/06/08 AM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step02_TestClickAddSavePivotalDatas() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int testSheetCount = ExcelUtils.getTestDataSheetCount(dataFileName, "RoleInfo");
			if(testSheetCount > 0) {
				for(int sheetIndex = 1; sheetIndex <= testSheetCount; sheetIndex++) {
					curTestDataIndex = sheetIndex;
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							if(!resultCode.equals("success")) {
								Object serviceResult = LogicParamManager.getServiceResult();
								if(serviceResult instanceof MessageResult) {
									ActionTestUtils.setTestResultWhenException(null, "角色信息关键性数据新增保存失败，当前测试数据位于"+dataFileName+
											"文件中的第"+curTestDataIndex+"个sheet页中，具体错误信息如下："+((MessageResult) serviceResult).getMessage());
								} else {
									ActionTestUtils.setTestResultWhenException(null, "角色信息关键性数据新增保存失败，当前测试数据位于"+dataFileName+
											"文件中的第"+curTestDataIndex+"个sheet页中");
								}
							}
						}
					}, "/framework/Save-coresystem.dto.RoleInfo.action", dataFileName, sheetIndex);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "角色信息关键性数据新增保存异常，当前测试数据位于"+dataFileName+
					"文件中的第"+curTestDataIndex+"个sheet页中");
		}
	}
	
	/**
	 * 
	 * <P>角色信息数据新增保存（可多组数据测试）</P>
	 * @author liutao
	 * @createDate 2016/06/08 AM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	@Rollback(false)
	public void step03_TestClickAddSaveBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			datasMap = ExcelUtils.getSameDTOAllDataByDto("coresystem.dto.RoleInfo", true);
			if(datasMap != null && datasMap.size() > 0) {
				for(int dataIndex = 1; dataIndex <= datasMap.size(); dataIndex++) {
					curTestDataIndex = dataIndex;
					
					context.putAll(datasMap.get(dataIndex));
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							if(!resultCode.equals("success")) {
								Object serviceResult = LogicParamManager.getServiceResult();
								if(serviceResult instanceof MessageResult) {
									ActionTestUtils.setTestResultWhenException(null, "角色信息数据新增保存失败，当前测试数据位于"+dataFileName+
											"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中，具体错误信息如下："+
											((MessageResult) serviceResult).getMessage());
								} else {
									ActionTestUtils.setTestResultWhenException(null, "角色信息数据新增保存失败，当前测试数据位于"+dataFileName+
											"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
								}
							}
						}
					}, "/framework/Save-coresystem.dto.RoleInfo.action", ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "角色信息数据新增保存异常，当前测试数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
		}
	}
	
	/**
	 * 
	 * <P>新增保存成功之后返回数据列表展示界面</P>
	 * @author liutao
	 * @createDate 2013/06/08 PM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step04_TestBackShowLIstViewerAfterAddSave() {
		backShowListDataViewer("新增保存");
	}
	
	/**
	 * 
	 * <P>点击角色信息数据修改链接</P>
	 * @author liutao
	 * @createDate 2016/06/08 AM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step05_TestClickAddUpdateLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			List<String> idList = ExcelUtils.getSameDTOFieldValueByExcel(dataFileName, "RoleInfo", "strRoleCode");
			if(idList.size() > 0) {
				for(int dataIndex = 0; dataIndex < idList.size(); dataIndex++) {
					curTestDataIndex = dataIndex+1;
					context.put("windwoId", "RoleInfo");
					context.put("id", idList.get(dataIndex));
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							checkShowSaveOrUpdateViewerResult(resultCode, "修改链接");
						}
					}, "/framework/ShowUpdate-coresystem.dto.RoleInfo.action", ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击修改链接异常，当前测试数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
		}
	}
	
	/**
	 * 
	 * <P>角色信息数据修改保存（可多组数据测试）</P>
	 * @author liutao
	 * @createDate 2016/06/08 AM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step06_TestClickUpdateSaveBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(datasMap !=null && datasMap.size() > 0) {
				for(int dataIndex = 1; dataIndex <= datasMap.size(); dataIndex++) {
					curTestDataIndex = dataIndex;
					
					context.putAll(datasMap.get(dataIndex));
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							if(!resultCode.equals("success")) {
								Object serviceResult = LogicParamManager.getServiceResult();
								if(serviceResult instanceof MessageResult) {
									ActionTestUtils.setTestResultWhenException(null, "角色信息数据修改保存失败，当前测试数据位于"+dataFileName+
											"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中，具体错误信息如下："+
											((MessageResult) serviceResult).getMessage());
								} else {
									ActionTestUtils.setTestResultWhenException(null, "角色信息数据修改保存失败，当前测试数据位于"+dataFileName+
											"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
								}
							}
						}
					}, "/framework/Update-coresystem.dto.RoleInfo.action", ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "角色信息数据修改保存异常，当前测试数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
		}
	}
	
	/**
	 * 
	 * <P>修改保存成功之后返回数据列表展示界面</P>
	 * @author liutao
	 * @createDate 2013/06/08 PM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step07_TestBackShowLIstViewerAfterUpdateSave() {
		backShowListDataViewer("修改保存");
	}
	
	/**
	 * 
	 * <P>批量删除数据测试</P>
	 * @author liutao
	 * @createDate 2013/06/07 PM 
	 * @lastUpdateDate 
	 *
	 */
	@Test
	@Rollback(false)
	public void step08_TestDeleteByIdList() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			List<String> idList = ExcelUtils.getSameDTOFieldValueByExcel(dataFileName, "RoleInfo", "strRoleCode");
			if(idList != null && idList.size() > 0) {
				String[] idArr = new String[idList.size()];
				for(String id : idList) {
					idArr[idList.indexOf(id)] = id;
				}
				
				context.put("idList", idArr);
				context.put("windowId", "RoleInfo");
				
				resultCode = testAction("/framework/DeleteListByIdList-coresystem.dto.RoleInfo.action", new TestDataProvider() {
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
				
				if(resultCode.equals("success")) {
					initServletMockObjects();
					resultCode = testAction("/framework/ShowList-coresystem.dto.RoleInfo.action", null);
					if(!resultCode.equals("success")) {
						fail("删除数据成功之后显示数据失败");
					}
				} else {
					fail("批量删除系统参数数据失败");
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "批量删除操作异常");
		}
	}
	
	@BeforeClass
	public static void beforeClass() {
		// 删除异常（或者错误）状态记录文件，如果此文件中的内容为true，则后续的测试将不会再进行下去，直接在当前测试将停止
		ActionTestUtils.deleteFilePath(System.getProperty("java.io.tmpdir")+"exceptionFlagFile.txt");
	}
	
	@AfterClass
	public static void afterClass() {
		try {
			List<DetachedCriteria> dcList = ExcelUtils.getSameDTOAllDetachedCriteria("coresystem.dto.RoleInfo", true);
			if(dcList !=null && dcList.size() > 0) {
				for(DetachedCriteria dc : dcList) {
					ActionTestUtils.deleteDBData(dc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("测试结束，清除测试数据时出现异常，具体的异常信息如下："+e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * <P>返回角色信息数据列表展示界面</P>
	 * 
	 * @param btnName 按钮的操作名称（新增/修改）
	 * @author liutao
	 * @createDate 2016/06/08 PM
	 * @lastUpdateDate 
	 *
	 */
	public void backShowListDataViewer(String btnName) {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			resultCode = testAction("/framework/BackFirst-coresystem.dto.RoleInfo.action", null);
			if(!resultCode.equals("success")) {
				ActionTestUtils.setTestResultWhenException(null, btnName+"操作成功之后，返回上一层级失败");
			} else {
				initServletMockObjects();
				resultCode = testAction("/framework/ShowList-coresystem.dto.RoleInfo.action", null);
				if(!resultCode.equals("success")) {
					ActionTestUtils.setTestResultWhenException(null, btnName+"操作成功之后，返回上一层级显示列表数据失败");
				}
			}
		}catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, btnName+"保存操作成功之后，返回数据列表展示界面异常");
		}
	}
	
	/**
	 * 
	 * <P>判断（新增/修改）界面是否显示正常</P>
	 * 
	 * @param btnName 按钮的操作名称（新增按钮/修改链接）
	 * @param resultCode Action执行结果
	 * @author liutao
	 * @createDate 2016/06/07 AM
	 * @lastUpdateDate 
	 */
	private void checkShowSaveOrUpdateViewerResult(String resultCode, String btnName) {
		if(!resultCode.equals("success")) {
			Object serviceResult = LogicParamManager.getServiceResult();
			if(serviceResult instanceof MessageResult) {
				ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"出错，当前测试数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中，错误信息如下："+((MessageResult) serviceResult).getMessage());
			} else {
				ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"出错，当前测试数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
			}
		} else {
			ShowSaveOrUpdate saveOrUpdate = (ShowSaveOrUpdate) LogicParamManager.getServiceResult();
			if(saveOrUpdate == null) {// 界面数据对象为空则测试失败
				if("修改链接".equals(btnName)) {
					this.resultCode = "error";
				}
				ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"出错，界面数据获取异常，当前测试数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
			} else {
				List<ShowFieldValue> showFieldValueList = saveOrUpdate.getShowFieldValueList();
				if(showFieldValueList == null || showFieldValueList.size() <= 0) {
					if("修改链接".equals(btnName)) {
						this.resultCode = "error";
					}
					ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"出错，界面无任何显示数据，当前测试数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
				}
			}
		}
	}
	
	
	
	
	
	
	
}
