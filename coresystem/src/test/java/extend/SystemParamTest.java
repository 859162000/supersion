package extend;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import extend.dto.Suit;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.CheckResult;
import framework.test.ExcelUtils;

/**
 * 
 * @description <P>系统参数测试类</P>
 * 				<P>没有对返回按钮做单独的测试，因为在新增保存数据之后，界面有一个返回的操作，故此没做返回按钮的单独测试</P>
 * @author transino_liutao
 * @createDate 2016/06/07 AM
 * @lastUpdateTime 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemParamTest extends ActionTestCase {
	private static boolean initFlag = false;// 初始化标志
	String resultCode = null;// Action执行结果
	String expectResult = null;
	int pivotalDataSheetIndex = 1;// 当前测试关键性数据的sheet页的下标（即：当前关键性数据测试所用的sheet位置）
	int curTestDataIndex = 0;// 当前测试的多组数据的组数（即：当前测试的是第几组数据）
	private String dataFileName = "SystemParam.xls";
	private static Map<String, Object> context = new HashMap<String, Object>();
	private static Map<Integer, Map<String, Object>> datasMap = null;
	
	/**
	 * 
	 * <P>点击桌面图标</P>
	 * @author liutao
	 * @createDate 2016/06/07 AM
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step00_TestClickDesktopIcon() {
		try {
			context.put("windowId", "SystemParam");
			testAction("/framework/ShowList-extend.dto.SystemParam.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击系统参数桌面图标出现异常");
		}
	}
	
	/**
	 * 
	 * <P>点击新增按钮</P>
	 * @author liutao
	 * @createDate 2016/06/07 AM
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step01_TestClickAddBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "SystemParam");
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					checkShowSaveOrUpdateViewerResult(resultCode, "新增");
				}
			}, "/framework/ShowSave-extend.dto.SystemParam.action", ActionTestUtils.getTestDataProvider(), context);
			
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击系统参数桌面图标出现异常");
		}
	}
	
	/**
	 * 
	 * <P>系统参数关键性数据保存</P>
	 * @author liutao
	 * @createDate 2016/06/07 AM
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step02_TestClickAddSavePivotalDatas() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			int dataCount = ExcelUtils.getTestDataSheetCount(dataFileName, "SystemParam");
			if(dataCount > 0) {
				for(int i = 1; i <= dataCount; i++){
					initServletMockObjects();
					pivotalDataSheetIndex = i;// 当前记录关键性数据的Sheet页的下标
					expectResult = ExcelUtils.getExpectResult(dataFileName, i);
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							Object serviceResult = LogicParamManager.getServiceResult();
							if(serviceResult instanceof MessageResult) {
								assertEquals("系统参数关键性数据测试失败，Action执行结果（"+resultCode +"）与给定的预期结果（"+expectResult+"）不一致，"+
										"当前测试数据位于"+dataFileName+"文件下的第"+(pivotalDataSheetIndex+1)+"个sheet页中，具体的错误信息是："+
										((MessageResult) serviceResult).getMessage(),
										expectResult, resultCode);
							} else {
								assertEquals("系统参数关键性数据测试失败，Action执行结果（"+resultCode +"）与给定的预期结果（"+expectResult+"）不一致，"+
										"当前测试数据位于"+dataFileName+"文件下的第"+(pivotalDataSheetIndex+1)+"个sheet页中，具体的错误信息是：", 
										expectResult, resultCode);
							}
						}
					}, "/framework/Save-extend.dto.SystemParam.action", dataFileName, i);
					if (!resultCode.equals(expectResult)) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "系统参数关键性数据测试失败，Action执行结果（"+resultCode +"）与给定的预期结果（"+expectResult+"）不一致，"+
					"当前测试数据位于"+dataFileName+"文件下的第"+(pivotalDataSheetIndex+1)+"个sheet页中，具体的错误信息是：");
		}
	}
	
	/**
	 * 
	 * <P>系统参数数据新增保存测试（支持多组数据的保存）</P>
	 * @author liutao
	 * @createDate 2016/06/07 AM
	 * @lastUpdateDate 
	 *
	 */
	@Test
	@Rollback(false)
	public void step03_TestClickAddSaveBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			datasMap = ExcelUtils.getSameDTOAllDataByExcel("extend.dto.SystemParam", dataFileName, "SystemParam", true);
			if(datasMap != null && datasMap.size() > 0) {
				// 循环每一组数据
				for(int testDataIndex = 1; testDataIndex <= datasMap.size(); testDataIndex++) {
					curTestDataIndex = testDataIndex;// 记录当前测试的数据组数（即：当前是在测试第几组数据）
					
					context.putAll(datasMap.get(testDataIndex));
					initServletMockObjects();// 初始化servlet相关对象
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							Object serviceResult = LogicParamManager.getServiceResult();
							if(!resultCode.equals("success")) {
								if(serviceResult instanceof MessageResult) {
									ActionTestUtils.setTestResultWhenException(null, "系统参数多组数据新增测试失败，当前测试数据位于"+dataFileName+
											"文件中的第1个sheet页中的第"+(curTestDataIndex+1)+"列单元格中，"+
											"当前错误信息如下"+((MessageResult) serviceResult).getMessage());
								} else {
									ActionTestUtils.setTestResultWhenException(null, "系统参数多组数据新增测试失败，当前测试数据位于"+dataFileName+
											"文件中的第1个sheet页中的第"+(curTestDataIndex+1)+"列单元格中");
								}
							}
						}
					}, "/framework/Save-extend.dto.SystemParam.action", ActionTestUtils.getTestDataProvider(), context);

					if(!resultCode.equals("success")) {
						break;
					}
				}
			} else {// 如若Excel文件中没有配置要测试的数据，则后续的测试都不会执行
				throw new AssumptionViolatedException("没有配置相应的测试数据");
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(null, "系统参数多组数据新增测试异常，当前测试数据位于"+dataFileName+
					"文件中的第1个sheet页中的第"+(curTestDataIndex+1)+"列单元格中");
		}
	}
	
	/**
	 * 
	 * <P>新增保存成功之后返回数据列表展示界面</P>
	 * @author liutao
	 * @createDate 2013/06/07 PM 
	 * @lastUpdateDate 
	 *
	 */
	public void step04_TestBackShowLIstViewerAfterAddSave() {
		backShowListDataViewer("新增");
	}
	
	/**
	 * 
	 * <P>系统参数数据修改链接测试（支持多组数据的保存）</P>
	 * @author liutao
	 * @createDate 2016/06/07 PM
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step05_TestClickUpdateLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			List<String> idList = ExcelUtils.getSameDTOFieldValueByExcel(dataFileName, "SystemParam", "strItemCode");
			if(idList != null && idList.size() > 0) {
				for(int index = 0; index < idList.size(); index++ ) {
					curTestDataIndex = index+1;
					context.put("windwoId", "SystemParam");
					context.put("id", idList.get(index));
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							Object serviceResult = LogicParamManager.getServiceResult();
							if(!resultCode.equals("success")) {
								if(serviceResult instanceof MessageResult) {
									ActionTestUtils.setTestResultWhenException(null, "点击修改链接失败，当前修改链接所属的数据位于"+dataFileName+
											"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中，具体错误信息是："
											+((MessageResult) serviceResult).getMessage());
								} else {
									ActionTestUtils.setTestResultWhenException(null, "点击修改链接失败，当前修改链接所属的数据位于"+dataFileName+
											"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
								}
							} else {
								checkShowSaveOrUpdateViewerResult(resultCode, "修改");
							}
						}
					}, "/framework/ShowUpdate-extend.dto.SystemParam.action", ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			} else {// 如若Excel文件中没有配置要测试的数据，则后续的测试都不会执行
				throw new AssumptionViolatedException("获取主键字段值失败，请配置相应的测试数据");
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击修改链接失败，当前修改链接所属的数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
		}
	}
	
	/**
	 * 
	 * <P>系统参数数据修改测试（支持多组数据的保存）</P>
	 * @author liutao
	 * @createDate 2016/06/07 PM
	 * @lastUpdateDate 
	 *
	 */
	@Test
	public void step06_TestClickUpdateSaveBtn() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			//datasMap = ExcelUtils.getSameDTOAllDataByExcel(dataFileName);
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
									ActionTestUtils.setTestResultWhenException(null, "点击修改保存按钮失败，当前修改的数据位于"+dataFileName+
											"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中，具体的错误信息如下："+((MessageResult) serviceResult).getMessage());
								} else {
									ActionTestUtils.setTestResultWhenException(null, "点击修改保存按钮失败，当前修改的数据位于"+dataFileName+
											"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
								}
							}
						}
					}, "/framework/Update-extend.dto.SystemParam.action", ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			} else {// 如若Excel文件中没有配置要测试的数据，则后续的测试都不会执行
				throw new AssumptionViolatedException("没有配置相应的测试数据");
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "点击修改保存按钮失败，当前修改的数据位于"+dataFileName+
					"文件中的第一个sheet页中的第"+curTestDataIndex+"列单元格中");
		}
	}
	
	/**
	 * 
	 * <P>修改保存成功之后返回数据列表展示界面</P>
	 * @author liutao
	 * @createDate 2013/06/07 PM 
	 * @lastUpdateDate 
	 *
	 */
	public void step07_TestBackShowLIstViewerAfterUpdateSave() {
		backShowListDataViewer("修改");
	}
	
	/**
	 * 
	 * <P>批量删除数据测试</P>
	 * @author liutao
	 * @createDate 2013/06/07 PM 
	 * @lastUpdateDate 
	 *
	 */
	public void step08_TestDeleteByIdList() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			List<String> idList = ExcelUtils.getSameDTOFieldValueByExcel(dataFileName, "SystemParam", "strItemCode");
			if(idList != null && idList.size() > 0) {
				context.put("idList", idList);
				context.put("windowId", "SystemParam");
				
				resultCode = testAction("DeleteListByIdList-extend.dto.SystemParam.action", ActionTestUtils.getTestDataProvider(), context);
				
				if(resultCode.equals("success")) {
					initServletMockObjects();
					resultCode = testAction("/framework/ShowList-extend.dto.SystemParam.action ", null);
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
	
	@Before
	public void before() {
		if(!initFlag) {
			List<String> suitCodeList = ExcelUtils.getSameDTOFieldValueByDto("extend.dto.SystemParam", "suit.strSuitCode");
			if(suitCodeList != null && suitCodeList.size() > 0) {
				Suit suit = new Suit();
				DetachedCriteria dc = DetachedCriteria.forClass(Suit.class);
				for(String suitCode : suitCodeList) {
					if(!StringUtils.isBlank(suitCode)) {
						dc.add(Restrictions.eq("suit.strSuitCode", suitCode));
						if(!ActionTestUtils.isDataExist(dc)){
							suit.setStrSuitCode(suitCode);
							ActionTestUtils.saveDBData(suit);
						}
					}
				}
			}
			initFlag = true;
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
			List<DetachedCriteria> dcList = ExcelUtils.getSameDTOAllDetachedCriteria("extend.dto.SystemParam", true);
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
	 * <P>返回系统参数数据列表展示界面</P>
	 * 
	 * @param btnName 按钮的操作名称（新增/修改）
	 * @author liutao
	 * @createDate 2016/06/07 PM
	 * @lastUpdateDate 
	 *
	 */
	public void backShowListDataViewer(String btnName) {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			resultCode = testAction("/framework/BackFirst-extend.dto.SystemParam.action", null);
			if(!resultCode.equals("success")) {
				ActionTestUtils.setTestResultWhenException(null, btnName+"保存操作成功之后，返回上一层级失败");
			} else {
				initServletMockObjects();
				resultCode = testAction("/framework/ShowList-extend.dto.SystemParam.action", null);
				if(!resultCode.equals("success")) {
					ActionTestUtils.setTestResultWhenException(null, btnName+"保存操作成功之后，返回上一层级显示列表数据失败");
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
	 * @param btnName 按钮的操作名称（新增/修改）
	 * @param resultCode Action执行结果
	 * @author liutao
	 * @createDate 2016/06/07 AM
	 * @lastUpdateDate 
	 */
	private void checkShowSaveOrUpdateViewerResult(String resultCode, String btnName) {
		if(!resultCode.equals("success")) {
			Object serviceResult = LogicParamManager.getServiceResult();
			if(serviceResult instanceof MessageResult) {
				ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"按钮出错，错误信息如下："+((MessageResult) serviceResult).getMessage());
			} else {
				ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"按钮出错");
			}
		} else {
			ShowFieldValue systemCode = null;
			ShowFieldValue isEnabled = null;
			ShowFieldValue suit = null;
			ShowSaveOrUpdate saveOrUpdate = (ShowSaveOrUpdate) LogicParamManager.getServiceResult();
			if(saveOrUpdate == null) {// 界面数据对象为空则测试失败
				if("修改".equals(btnName)) {
					resultCode = "error";
				}
				ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"按钮出错，界面数据获取异常");
			} else {
				List<ShowFieldValue> showFieldValueList = saveOrUpdate.getShowFieldValueList();
				if(showFieldValueList == null || showFieldValueList.size() <= 0) {
					if("修改".equals(btnName)) {
						resultCode = "error";
					}
					ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"按钮出错，界面无任何显示数据");
				} else {
					String msg = "";
					Object tag = null;
					Map<String, String> xmlTag = null;
					ShowField sf = null;
					for(ShowFieldValue sfv : showFieldValueList) {
						sf = sfv.getShowField();
						if(sf == null || StringUtils.isBlank(sf.getFieldName()) || StringUtils.isBlank(sf.getFieldShowName())) {
							msg += "界面中有字段显示错误，该字段的fieldName或者fieldShowName属性为空；";
						} else {
							if(sf.getFieldName().equals("strItemCode")) {
								systemCode = sfv;
							} else if(sf.getFieldName().equals("strEnable")) {
								isEnabled = sfv;
							} else if(sf.getFieldName().equals("suit")) {
								suit = sfv;
							} else {
								continue;
							}
						}
					}
					
					if(systemCode != null) {
						tag = systemCode.getTag();
						xmlTag = ShowContext.getInstance().getShowEntityMap().get("configParam");
						if(!((tag == null && xmlTag == null) || 
								(tag != null && xmlTag != null && ((LinkedHashMap<String, String>) tag).size() == xmlTag.size()))) {
							msg +="；参数代码字段备选数据显示不正常，与系统配置的显示数据有差异；";
						}
					}
					
					if(isEnabled != null) {
						tag = isEnabled.getTag();
						xmlTag = ShowContext.getInstance().getShowEntityMap().get("isPrimaryKey");
						if(!((tag == null && xmlTag == null) || 
								(tag != null && xmlTag != null && ((LinkedHashMap<String, String>) tag).size() == xmlTag.size()))) {
							msg +="；是否可用字段备选数据显示不正常，与系统配置的显示数据有差异；";
						}
					}
					
					if(suit != null) {
						try {
							tag = suit.getTag();
							// 查询数据库的主题数据
							DetachedCriteria dc = DetachedCriteria.forClass(Suit.class);
							
							List<Object> suitList = ActionTestUtils.getDBData(dc);
							if(!((tag == null && suitList == null) ||
									(tag != null && suitList != null && ((LinkedHashMap<String, String>) tag).size() == suitList.size()))) {
								msg += "；主题字段备选数据显示不正常，与数据库中的suit表的数据不对应；";
							}
						} catch (Exception e) {
							ActionTestUtils.setTestResultWhenException(e, "测试失败，获取数据库主题信息异常；");
						}
					}
					
					if(!StringUtils.isBlank(msg)) {
						if("修改".equals(btnName)) {
							resultCode = "error";
						}
						ActionTestUtils.setTestResultWhenException(null, "点击"+btnName+"按钮出错，"+msg);
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
