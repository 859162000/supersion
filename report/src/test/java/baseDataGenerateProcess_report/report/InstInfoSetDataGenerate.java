package baseDataGenerateProcess_report.report;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import coresystem.dto.InstInfo;

import report.dto.InstInstSet;

import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowValue;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.CheckResult;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstInfoSetDataGenerate extends ActionTestCase {
	private String dataFileName = "InstInfoSet.xls";
	static Map<String, Object> context = new HashMap<String, Object>();
	static List<ShowValue> testInstSetData = null;
	
	/**
	 * 
	 * <P>点击桌面图标</P>
	 */
	@Test
	public void step00_TestClickDesktopIcon() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfoSet");
			testAction("/framework/ShowList-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.InstInfoSet，点击桌面图标出错");
		}
	}
	
	/**
	 * 
	 * <P>点击新增按钮</P>
	 */
	@Test
	public void step01_TestClickAddBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfoSet");
			testAction("/framework/ShowSave-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.InstInfoSet，点击新增按钮出错");
		}
	}
	
	/**
	 * 
	 * <P>点击新增界面返回按钮</P>
	 */
	@Test
	public void step02_TestClickBackBtn_Add() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfoSet");
			testAction("/framework/BackFirst-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.InstInfoSet，点击新增返回按钮出错");
		}
	}
	
	/**
	 * 
	 * <P>再次点击新增按钮</P>
	 */
	@Test
	public void step03_TestClickAddBtnAgain() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "InstInfoSet");
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					if(!resultCode.equals("success")) {
						fail("机构集合管理，再次点击新增按钮失败");
					}
				}
			}, "/framework/ShowSave-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.InstInfoSet，再次点击新增按钮错误");
		}
	}
	
	/**
	 * 
	 * <P>新增操作的关键性数据保存</P>
	 */
	@Test
	public void step04_TestClickAddSaveBtnPivotalDatas() {
		int curSheetIndex = 1;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int dataSheetCount = ExcelUtils.getTestDataSheetCount(dataFileName, "InstInfoSet");// 保存关键性数据的Sheet页的数目
			if(dataSheetCount > 0) {
				String resultCode = null;
				String expectResult = null;
				for(int sheetIndex = 1; sheetIndex <= dataSheetCount; sheetIndex++) {
					curSheetIndex = sheetIndex;
					
					initServletMockObjects();
					resultCode = testAction("/framework/Save-report.dto.InstInfoSet.action", dataFileName, sheetIndex);
					expectResult = ExcelUtils.getExpectResult(dataFileName, sheetIndex);
					
					if(!resultCode.equals(expectResult)) {
						fail("机构集合关键性数据新增保存失败，当前测试数据存放在"+dataFileName+"文件中的第"+(sheetIndex+1)+"个sheet页中");
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "机构集合关键性数据新增保存失败，当前测试数据存放在"+dataFileName+"文件中的第"+(curSheetIndex+1)+"个sheet页中");
		}
	}
	
	/**
	 * 
	 * <P>新增保存机构集数据</P>
	 */
	@Test
	@Rollback(false)
	public void step05_TestClickAddSaveBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int rowIndex = ExcelUtils.getRowNumByFieldName("InstInfo.xls", "InstInfo", "strInstCode");
			String instCode = ExcelUtils.getExcelValue("InstInfo.xls", "InstInfo~"+rowIndex+"~1", "~");
			rowIndex = ExcelUtils.getRowNumByFieldName(dataFileName, "InstInfoSet", "strInstCodeIdList");
			ExcelUtils.updateExcelValue(dataFileName, "InstInfoSet~"+rowIndex+"~1~"+instCode, "~");
			
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					if(!resultCode.equals("success")) {
						Object serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult) {
							// 如果测试失败，则后续将不再执行测试，人为停止
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据新增失败，错误信息是："+((MessageResult) serviceResult).getMessage());
						} else {
							// 如果测试失败，则后续将不再执行测试，人为停止
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据新增失败");
						}
					} else {// 查看中间表是否有数据，如果说没有数据，则测试失败
						int rowIndex = ExcelUtils.getRowNumByFieldName(dataFileName, "InstInfoSet", "strInstSetCode");
						String instSetCode = ExcelUtils.getExcelValue(dataFileName, "InstInfoSet~"+rowIndex+"~1", "~");
						DetachedCriteria dc = DetachedCriteria.forEntityName("report.dto.InstInstSet");
						dc.add(Restrictions.eq("strInstSetCode", instSetCode));
						
						List<Object> instInstSetList;
						try {
							instInstSetList = ActionTestUtils.getDBData(dc);
							if(instInstSetList != null && instInstSetList.size() > 0) {
								InstInstSet instInstSet = (InstInstSet) instInstSetList.get(0);
								if(instInstSet == null) {
									ActionTestUtils.setTestResultWhenException(null, "机构集合数据新增失败，中间表InstInstSet中无相应的数据");
								} else {
									InstInfo instInfo = instInstSet.getInstInfo();
									rowIndex = ExcelUtils.getRowNumByFieldName(dataFileName, "InstInfoSet", "strInstCodeIdList");
									String instCode = ExcelUtils.getExcelValue(dataFileName, "InstInfoSet~"+rowIndex+"~1", "~");
									if(instInfo == null || !instInfo.getStrInstCode().equals(instCode)) {
										ActionTestUtils.setTestResultWhenException(null, "机构集合数据新增失败，中间表InstInstSet数据存在异常");
									}
								}
							} else {
								// 如果测试失败，则后续将不再执行测试，人为停止
								ActionTestUtils.setTestResultWhenException(null, "机构集合数据新增失败，中间表InstInstSet中无相应的数据");
							}
						} catch (Exception e) {
							ActionTestUtils.setTestResultWhenException(e, "机构集合数据新增失败，获取中间表InstInstSet数据异常");
						}
						
					}
				}
			}, "/framework/Save-report.dto.InstInfoSet.action", dataFileName);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.InstInfoSet，机构集合信息新增失败，当前保存的数据时位于"+dataFileName+"文件中的InstInfoSet工作簿中");
		}
	}
	
	/**
	 * 
	 * <P>机构集数据新增保存成功之后返回列表展示界面</P>
	 */
	@Test
	public void step06_TestBackShowListViewAfterAddSave() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			String resultCode = null;
			context.put("windowId", "InstInfoSet");
			
			resultCode = testAction("/framework/BackFirst-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
			if(resultCode.equals("success")) {
				initServletMockObjects();
				resultCode = testAction("/framework/ShowList-report.dto.InstInfoSet.action", null);
				
				if(resultCode.equals("success")) {
					ShowList showList = (ShowList) LogicParamManager.getServiceResult();
					if(showList != null) {
						List<List<ShowValue>> showValueListList = showList.getValueTable();
						if(showValueListList != null && showValueListList.size() > 0) {
							int rowIndex = ExcelUtils.getRowNumByFieldName(dataFileName, "InstInfoSet", "strInstSetCode");
							String instSetCode = ExcelUtils.getExcelValue(dataFileName, "InstInfoSet~"+rowIndex+"~1", "~");
							for(List<ShowValue> showValueList : showValueListList) {
								if(!StringUtils.isBlank(showValueList.get(0).getValue().toString()) 
										&& showValueList.get(0).getValue().toString().equals(instSetCode)) {
									testInstSetData = showValueList;
									break;
								}
							}
						}
					} else {
						ActionTestUtils.setTestResultWhenException(null, "机构集合数据新增保存成功之后返回列表展示界面，未获取到数据");
					}
				} else {
					ActionTestUtils.setTestResultWhenException(null, "机构集合数据新增保存返回列表展示界面失败");
				}
			} else {
				ActionTestUtils.setTestResultWhenException(null, "机构集合数据新增保存返回上一层级失败");
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "机构集合新增失败，当前保存的数据时位于"+dataFileName+"文件中的InstInfoSet工作簿中");
		}
	}
	
	/**
	 * 
	 * <P>点击修改链接</P>
	 */
	@Test
	public void step07_TestClickUpdateLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			context.put("type", null);
			context.put("windowId", "InstInfoSet");
			context.put("id", testInstSetData.get(0).getValue());
			
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					if(!resultCode.equals("success")) {
						Object serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult) {
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据修改链接点击失败，错误信息如下："+((MessageResult) serviceResult).getMessage());
						} else {
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据修改链接点击失败");
						}
					}
				}
			}, "/framework/ShowUpdate-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击机构集合数据信息修改链接时出现异常");
		}
	}
	
	/**
	 * 
	 * <P>点击修改保存按钮</P>
	 */
	@Test
	public void step08_TestClickeBackBtnFromUpdateViewer() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			context.put("type", null);
			context.put("windowId", "InstInfoSet");
			context.put("id", testInstSetData.get(0).getValue());
			
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					if(!resultCode.equals("success")) {
						Object serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult) {
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据修改链接点击失败，错误信息如下："+((MessageResult) serviceResult).getMessage());
						} else {
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据修改链接点击失败");
						}
					}
				}
			}, "/framework/ShowUpdate-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "机构集合数据信息修改保存时异常");
		}
	}
	
	/**
	 * 
	 * <P>机构集数据修改保存成功之后返回列表展示界面</P>
	 */
	@Test
	public void step09_TestBackShowListViewAfterUpdateSave() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			boolean findDataFlag = false;// 是否找到新增数据的标志，找到则为true 
			String resultCode = null;
			context.put("windowId", "InstInfoSet");
			
			resultCode = testAction("/framework/BackFirst-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
			if(resultCode.equals("success")) {
				initServletMockObjects();
				resultCode = testAction("/framework/ShowList-report.dto.InstInfoSet.action", null);
				
				if(resultCode.equals("success")) {
					ShowList showList = (ShowList) LogicParamManager.getServiceResult();
					if(showList != null) {
						List<List<ShowValue>> showValueListList = showList.getValueTable();
						if(showValueListList != null && showValueListList.size() > 0) {
							for(List<ShowValue> showValueList : showValueListList) {
								if(showValueList.get(0).getValue().equals(testInstSetData.get(0).getValue())) {
									findDataFlag = true;
									break;
								}
							}
							
							if(findDataFlag) {
								ActionTestUtils.setTestResultWhenException(null, "机构集合数据修改保存成功之后数据消失不见");
							}
						}
					} else {
						ActionTestUtils.setTestResultWhenException(null, "机构集合数据修改保存成功之后返回列表展示界面，未获取到数据");
					}
				} else {
					ActionTestUtils.setTestResultWhenException(null, "机构集合数据修改保存返回列表展示界面失败");
				}
			} else {
				ActionTestUtils.setTestResultWhenException(null, "机构集合数据修改保存返回上一层级失败");
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "机构集合修改失败，当前保存的数据时位于"+dataFileName+"文件中的InstInfoSet工作簿中");
		}
	}
	
	/**
	 * 
	 * <P>点击删除按钮</P>
	 */
	@Test
	public void step10_TestClickDeleteBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int rowIndex = ExcelUtils.getRowNumByFieldName(dataFileName, "InstInfoSet", "strInstSetCode");
			String instSetCode = ExcelUtils.getExcelValue(dataFileName, "InstInfoSet~"+rowIndex+"~1", "~");
			String[] idList = new String[]{instSetCode};
			context.put("idList", idList);
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					if(!resultCode.equals("success")) {
						Object serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult) {
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据点击删除按钮失败，错误信息如下："+((MessageResult) serviceResult).getMessage());
						} else {
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据点击删除按钮失败");
						}
					}
				}
			}, "/framework/DeleteListByIdList-report.dto.InstInfoSet.action", ActionTestUtils.getTestDataProvider(), context);
			
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击机构集合列表展示界面批量删除按钮异常");
		}
	}
	
	/**
	 * 
	 * <P>机构集数据修改保存成功之后返回列表展示界面</P>
	 */
	@Test
	public void step11_TestBackShowListViewAfterClickDeleteBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					if(resultCode.equals("success")) {
						boolean findDataFlag = false;// 是否找到新增数据的标志，找到则为true 
						ShowList showList = (ShowList) LogicParamManager.getServiceResult();
						if(showList != null) {
							List<List<ShowValue>> showValueListList = showList.getValueTable();
							if(showValueListList != null && showValueListList.size() > 0) {
								for(List<ShowValue> showValueList : showValueListList) {
									if(showValueList.get(0).getValue().equals(testInstSetData.get(0).getValue())) {
										findDataFlag = true;
										break;
									}
								}
								
								if(findDataFlag) {
									ActionTestUtils.setTestResultWhenException(null, "机构集合信息删除失败，数据仍然存在");
								}
							}
						} else {
							ActionTestUtils.setTestResultWhenException(null, "机构集合数据点击删除按钮之后显示数据失败，未获取到数据");
						}
					} else {
						ActionTestUtils.setTestResultWhenException(null, "机构集合数据点击删除按钮之后显示数据失败");
					}
				}
			}, "/framework/ShowList-report.dto.InstInfoSet.action", null);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "机构集合修改失败，当前保存的数据时位于"+dataFileName+"文件中的InstInfoSet工作簿中");
		}
	}
	
	
	
	
	
}
