package baseDataGenerateProcess_zxptsystem.zxptsystem;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import report.dto.TaskModelInst;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowTree;
import framework.show.ShowTreeNode;
import framework.show.ShowValue;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.CheckResult;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;
import framework.test.TxtUtils;
import framework.services.interfaces.MessageResult;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZXBaseDataGenerate extends ActionTestCase {
	static String uri = "/";
	static String dataFileName = "ZXGenerateReport.xls";
	static String instInfoFileName = "InstInfo.xls";
	List<String> condition = new ArrayList<String>();
	List<String> testDtoList = null;
	List<String> currTestJCDtoList = null;
	List<String> currTestMXDtoList = null;
	static String SEQ = System.getProperty("line.separator");
	static Map<String, Object> context = new HashMap<String, Object>();
	static Map<String, List<ShowValue>> showValueMap = new HashMap<String, List<ShowValue>>();// 存放显示数据
	static Map<String, List<ShowOperation>> linkedMap = new HashMap<String, List<ShowOperation>>();// 存放链接数据
	static Map<String, List<ShowOperation>> jumpBtnMap = new HashMap<String, List<ShowOperation>>();// 存放快捷跳转按钮的信息
	static Map<String, Map<String, Object>> paramMap = new HashMap<String, Map<String,Object>>();
	
	@Test
	public void step00_TestLogout(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			// 注销
			initServletMockObjects();
			testAction("/coresystem/Logout-coresystem.dto.UserInfo.action", "");
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}
	
	@Test
	public void step01_TestLogin(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			// 重新登录
			context.put("serviceParam.strUserCode", "admin");
			context.put("serviceParam.strPassword", "123");
			testAction("/coresystem/Login-coresystem.dto.UserInfo.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}
	
	/**
	 * 点击报表处理平台——明细数据填报图标
	 */
	@Test
	public void step02_TestShowTreeMenue(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "TaskModelInst");
			testAction("/framework/ShowTree-report.dto.TaskModelInst.action", ActionTestUtils.getTestDataProvider(), context);
			ShowTree showTree = (ShowTree) LogicParamManager.getServiceResult();
			if (showTree != null && showTree.getTreeValue() != null) {
				String instName = ExcelUtils.getExcelValue(instInfoFileName, "0~1~1", "~");
				for (ShowTreeNode node : showTree.getTreeValue()) {
					if (StringUtils.isNotBlank(node.getShowName())
							&& node.getShowName().equals(instName)) {
						uri += node.getURL();
					}
					if (!uri.equals("/")) {
						String uriTemp = uri.substring(uri.indexOf("=")+1);
						uri = uri.substring(0, uri.indexOf("?"));
						context.put("id", uriTemp.substring(0, uriTemp.indexOf("&")));
						context.put("type", uriTemp.substring(uriTemp.indexOf("=")+1, uriTemp.lastIndexOf("&")));
						context.put("windowId", uriTemp.substring(uriTemp.lastIndexOf("=")+1));
						break;
					}
				}
			}
			
			// 判断URL是否正确，如果正确，则跳过后续的所有测试代码
			if(uri.equals("/")) {
				throw new AssumptionViolatedException(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, "点击桌面“明细数据填报”图标出错");
		}
	}
	
	/**
	 * 点击树形结构菜单，显示所有的可填报的基础段信息列表
	 */
	@Test
	public void step03_TestClickTreeMenue(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			// 显示当前下发的所有任务，列表展示将要填报的JC段，并进行基础段填报跳转（即：点击填报链接）
			testAction(uri, ActionTestUtils.getTestDataProvider(), context);
			uri = "/";
			
			ShowList showList = (ShowList) LogicParamManager.getServiceResult();
			if (showList != null) {
				testDtoList = ExcelUtils.getTestDto(dataFileName);
				if (testDtoList != null && testDtoList.size() > 0) {
					// 为每一个需要测试的DTO的参数EXCEL赋值
					List<List<ShowValue>> showValueListList = showList.getValueTable();// 每一行的数据集合
					if (showValueListList != null && showValueListList.size() > 0) {
						String type = null;
						String dtoName = null;
						Map<String, Object> param = null;
						List<String> jcDtoList = new ArrayList<String>();
						for (List<ShowValue> showValueList : showValueListList) {// 遍历每一行数据，为填报链接请求时创建参数值
							if (showValueList != null && showValueList.size() > 0) {
								type = showValueList.get(1).getValue();
								if (!StringUtils.isBlank(type)) {
									dtoName = type.substring(type.indexOf("-") + 1, type.length());
									if (testDtoList.contains(dtoName)) {// 判断当前的行数据是否是需要进行测试的DTO数据
										// 记录当前
										jcDtoList.add(dtoName);
										// 创建sheet页并设置值(得优化)
										param =  new HashMap<String, Object>();
										param.put("id", showValueList.get(0).getValue());
										param.put("type", type);
										param.put("windowId", "TaskModelInst");
										paramMap.put(dtoName, param);
									}
								}
							}
						}
						// 设置测试的基础段DTO名称
						TxtUtils.setCurrentTestJCDTOListToTextFile(jcDtoList);
					}else{
						fail("请在对应任务中设置明细报表任务");
					}
				}else{
					fail("请在对应的参数EXCEL文件中的第一个sheet中配置需要测试的DTO");
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}
	
	/**
	 * 点击填报链接
	 */
	@Test
	public void step04_TestClickTianBaoLinked (){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			// 点击填报链接
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0){
				String resultCode = "";
				for(String currTestDtoName : currTestJCDtoList){
					if(paramMap != null && paramMap.entrySet().size() > 0 && paramMap.entrySet().size() == currTestJCDtoList.size()){
						initServletMockObjects();
						resultCode = testAction(new CheckResult() {
							@Override
							public void check(String resultCode, Object actionInstance) {
								String actionName = null;
								String tName = null;
								actionName = request.getPathInfo();
								int startIndex = actionName.indexOf("-") + 1;
								tName = actionName.substring(startIndex);
								tName = tName.replace(".action", "");
								if(tName.indexOf("?") > -1){
									tName = tName.substring(0,tName.indexOf("?"));
								}
								Object serviceResult = LogicParamManager.getServiceResult();
								if(serviceResult instanceof MessageResult)
								{
									MessageResult mr=(MessageResult)serviceResult;
									assertEquals("当前测试的DTO是："+tName+"，错误信息是："+mr.getMessage(),"success", resultCode);
									return;
								}else{
									assertEquals("当前测试的DTO是："+tName, "success", resultCode);
								}
							}
						}, "/framework/JumpByTypeLevelAUTODTO-"+currTestDtoName+".action", ActionTestUtils.getTestDataProvider(), paramMap.get(currTestDtoName));
						
						if(!StringUtils.isBlank(resultCode) && !resultCode.equals("success")){
							break;
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, null);
		}
	}
	
	/**
	 * 点击新增按钮，进入新增界面
	 */
	@Test
	public void step05_TestClickJCShowSaveBtn(){
		String currDtoName = "";
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			String resultCode = "";
			Object serviceResult = null;
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0){
				for(String dtoName : currTestJCDtoList){
					currDtoName = dtoName;
					initServletMockObjects();
					context.put("windowId", "TaskModelInst");
					resultCode = testAction("/framework/ShowSaveLevelAUTODTO-"+dtoName+".action", ActionTestUtils.getTestDataProvider(), context);
					serviceResult = LogicParamManager.getServiceResult();
					if(serviceResult instanceof MessageResult){
						assertEquals("测试失败，当前测试DTO是："+currDtoName+"错误信息如下："+SEQ+
								((MessageResult) serviceResult).getMessage(), "success", resultCode);
					}else{
						assertEquals("测试失败，当前测试DTO是："+currDtoName, "success", resultCode);
					}
					if(!StringUtils.isBlank(resultCode) && !resultCode.equals("success")){
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, currDtoName);
		}
	}
	
	
	/**
	 * 基础段关键性数据新增保存
	 */
	@Test
	public void step06_TestJCFieldAddSavePivotalDatas(){
		String currDtoName = null;
		int curSheetIndex = 1;
		String excelName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0){
				String sheetName = null;
				int rowIndex = -1;
				int sheetCount = 0;// 存放关键性数据的sheet个数
				int doteIndex = 0;
				String className = null;// 当前正在测试的DTO对应的类名
				boolean endFlag = false;// 循环结束标志
				String resultCode = null;
				String expectResult = null;
				
				for(String currTestDtoName : currTestJCDtoList){
					doteIndex = currTestDtoName.lastIndexOf(".");
					className = currTestDtoName.substring(doteIndex+1);
					excelName = ActionTestUtils.getExcelFileOrSheetName(currTestDtoName, false);
					sheetCount = ExcelUtils.getTestDataSheetCount(excelName, className);
					
					if(sheetCount <= 0) {
						continue;
					}
					
					currDtoName = currTestDtoName;
					
					if(currTestDtoName.indexOf("AutoDTO_GR_GRXX_JC") > -1) {// 个人信息基础段的查询条件的时间是以“结算预还款日期”进行查询的
						sheetName = ActionTestUtils.getExcelFileOrSheetName(currTestDtoName, true);
						rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, "JSYHKRQ");
						// 设置计算应还款日期在当前月的时间之内
						SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
						Calendar ca= Calendar.getInstance();
						ca.setTime(new Date());
						int day = ca.get(Calendar.DAY_OF_MONTH);
						ca.set(Calendar.DAY_OF_MONTH, day-1);
						Date date = ca.getTime();
						
						ExcelUtils.updateExcelValue(excelName, sheetName+"~"+rowIndex+"~1~"+df.format(date), "~");
					}
					
					for(int index = 1; index <= sheetCount; index++) {
						initServletMockObjects();
						resultCode = testAction("/framework/SaveLevelAUTODTO-"+currTestDtoName+".action", excelName, index);
						expectResult = ExcelUtils.getExpectResult(excelName, index);
						if(!resultCode.equals(expectResult)) {
							fail("当前DTO："+currTestDtoName+"关键性数据新增保存失败，当前测试的数据位于"+excelName+"文件中的第"+(index+1)+"个sheet页中");
							endFlag = true;
							break;
						} else {
							assertTrue(true);
						}
					}
					
					if(endFlag) {
						break;
					}
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
			clearDataMap();
			ActionTestUtils.setTestResultWhenException(e, currDtoName+"，且当前DTO的关键性数据新增保存失败，当前测试的数据位于"+excelName+"文件中的第"+(curSheetIndex+1)+"个sheet页中");
		}
	}
	
	/**
	 * 基础段新增保存
	 */
	@Test
	@Rollback(false)
	public void step07_TestJCFieldAddSave(){
		String currDtoName = null;
		String exceptionMSG = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0){
				String excelName = null;
				String sheetName = null;
				int rowIndex = -1;
				for(String currTestDtoName : currTestJCDtoList){
					currDtoName = currTestDtoName;
					exceptionMSG = ",当前模拟的是新增保存按钮";
					// 设置最后修改时间
					//ActionTestUtils.setLastUpdateTime(currTestDtoName);
					excelName = ActionTestUtils.getExcelFileOrSheetName(currTestDtoName, false);
					
					if(currTestDtoName.indexOf("AutoDTO_GR_GRXX_JC") > -1) {// 个人信息基础段的查询条件的时间是以“结算预还款日期”进行查询的
						sheetName = ActionTestUtils.getExcelFileOrSheetName(currTestDtoName, true);
						rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, "JSYHKRQ");
						// 设置计算应还款日期在当前月的时间之内
						SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
						Calendar ca= Calendar.getInstance();
						ca.setTime(new Date());
						int day = ca.get(Calendar.DAY_OF_MONTH);
						ca.set(Calendar.DAY_OF_MONTH, day-1);
						Date date = ca.getTime();
						
						ExcelUtils.updateExcelValue(excelName, sheetName+"~"+rowIndex+"~1~"+df.format(date), "~");
					}
					
					initServletMockObjects();
					testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							String tName = ActionTestUtils.getTName(request.getPathInfo());
							
							if(resultCode.equals("success")) {
								try {
									String excelName = ActionTestUtils.getExcelFileOrSheetName(tName, false);
									String sheetName = ActionTestUtils.getExcelFileOrSheetName(tName, true);
									Field pkField = ActionTestUtils.getPKField(tName);
									int rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, pkField.getName());
									
									Object jcData = RequestManager.getTOject();
									String pkVal = ReflectOperation.getFieldValue(jcData, pkField.getName()).toString();
									
									ExcelUtils.updateExcelValue(excelName, sheetName+"~"+rowIndex+"~1~"+pkVal, "~");
								} catch (Exception e) {
									e.printStackTrace();
									clearDataMap();
									ActionTestUtils.setTestResultWhenException(e, tName+"，写入当前基础段的主键字段值到Excel失败");
								}
							}
							
							Object serviceResult = LogicParamManager.getServiceResult();
							if(serviceResult instanceof MessageResult){
								assertEquals("测试失败，当前测试DTO是："+tName+"错误信息如下："+SEQ+
										((MessageResult) serviceResult).getMessage(), "success", resultCode);
							}else{
								assertEquals("测试失败，当前测试DTO是："+tName, "success", resultCode);
							}
						}
					}, "/framework/SaveLevelAUTODTO-"+currTestDtoName+".action", excelName);
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
			clearDataMap();
			ActionTestUtils.setTestResultWhenException(e, currDtoName+exceptionMSG+"，基础段新增保存失败");
		}
	}
	
	/**
	 * 基础段新增保存成功之后返回上一个界面
	 */
	@Test
	public void step08_TestBackFirstAfterJCAddSave() {
		String currDtoName = null;
		try {
 			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0) {
				for(String jcDTO : currTestJCDtoList) {
					currDtoName = jcDTO;
					initServletMockObjects();
					testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							String tName = ActionTestUtils.getTName(request.getPathInfo());
							Object serviceResult = LogicParamManager.getServiceResult();
							if(serviceResult instanceof MessageResult){
								assertEquals("测试失败，当前测试DTO是："+tName+"错误信息如下："+SEQ+
										((MessageResult) serviceResult).getMessage(), "success", resultCode);
							}else{
								assertEquals("测试失败，当前测试DTO是："+tName, "success", resultCode);
							}
						}
					}, "/framework/BackFirst-"+jcDTO+".action", "");
				}
			}
		} catch (Exception e) {
			clearDataMap();
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, currDtoName+"，基础段新增保存成功后，返回上一层级失败");
		}
	}
	
	/**
	 * 基础段新增保存成功之后返回上一个界面，且展示出界面数据
	 */
	@Test
	public void step09_TestShowJCDTOListAfterJCAddSave() {
		String currDtoName = null;
		String resultCode = null;
		ShowList showList = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0) {
				for(String jcDTO : currTestJCDtoList) {
					currDtoName = jcDTO;
					Object newQueryConditionObject = null;
					Object queryCondiObject = getSession().get("action_tCondition-"+jcDTO);
					if(queryCondiObject != null) {
						newQueryConditionObject = queryCondiObject.getClass().newInstance();
					}
					getSession().put("action_tCondition-"+jcDTO, newQueryConditionObject);
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							String tName = ActionTestUtils.getTName(request.getPathInfo());
							Object serviceResult = LogicParamManager.getServiceResult();
							if(serviceResult instanceof MessageResult){
								assertEquals("测试失败，当前测试DTO是："+tName+"错误信息如下："+SEQ+
										((MessageResult) serviceResult).getMessage(), "success", resultCode);
							}else{
								assertEquals("测试失败，当前测试DTO是："+tName, "success", resultCode);
							}
						}
					}, "/framework/ShowListLevelAUTODTO-"+jcDTO+".action", "");
					
					if(("success").equals(resultCode)){
						showList = (ShowList) LogicParamManager.getServiceResult();
						if(showList != null){
							showValueMap.put(jcDTO, showList.getValueTable().get(0));
							linkedMap.put(jcDTO, showList.getLinkedList().get(0));
						}
					} else {
						fail("显示数据列表失败");
						break;
					}
				}
			}
		} catch (Exception e) {
			clearDataMap();
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, currDtoName+"，基础段新增保存成功后，返回上一层级失败");
		}
	}
	
	/**
	 * 点击“基础段”连接
	 */
	@Test
	public void step10_TestClickJCFieldLinked(){
		String curTestDto = "";
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();// 当前正在测试的DTO
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0){
				if(showValueMap.entrySet().size() > 0 && linkedMap.entrySet().size() > 0
						&& showValueMap.entrySet().size() == linkedMap.entrySet().size()){
					String resultCode = "";
					List<ShowValue> showValList = null;
					List<ShowOperation> linkedList = null;
					ShowOperation JCLinked = null;
					String uri = null;
					for(String curDtoName : currTestJCDtoList){
						// 链接信息与新增的数据信息已经获取到了，接下来就是查看基础段的数据信息了，然后下一个@Test在做其余的链接，此处做了基础段链接之后是否删除基础段链接还待定
						// 点击“基础段”链接，然后将基础段链接删除掉，只剩下明细段数据的链接操作
						uri = "/";
						curTestDto = curDtoName;
						showValList = showValueMap.get(curDtoName);
						linkedList = linkedMap.get(curDtoName);
						
						if(showValList != null && linkedList != null){
							for(ShowOperation linked : linkedList){
								if(linked.getOperationName().equals("基础段")){
									JCLinked = linked;
									break;
								}
							}
							
							linkedList.remove(JCLinked);
							linkedMap.put(curDtoName, linkedList);
							
							uri += JCLinked.getOperationNamespace()+"/"+JCLinked.getOperationAction()+".action";
							
							context.put("id", showValList.get(0).getValue());
							context.put("type", showValList.get(1).getValue());
							context.put("windowId", "TaskModelInst");
							
							// 测试基础段链接
							initServletMockObjects();
							resultCode = testAction(new CheckResult() {
								@Override
								public void check(String resultCode, Object actionInstance) {
									String tName = ActionTestUtils.getTName(request.getPathInfo());
									
									Object serviceResult = LogicParamManager.getServiceResult();
									if(serviceResult instanceof MessageResult){
										assertEquals("测试失败，当前测试DTO是："+tName+"错误信息如下："+SEQ+
												((MessageResult) serviceResult).getMessage(), "success", resultCode);
									}else{
										assertEquals("测试失败，当前测试DTO是："+tName, "success", resultCode);
									}
								}
							}, uri, ActionTestUtils.getTestDataProvider(), context);
							
							if(!StringUtils.isBlank(resultCode) && !resultCode.equals("success")){
								clearDataMap();
								break;
							}
						}
					}// 循环DTO
				}else{
					clearDataMap();
					fail("测试失败，数据异常，请检查当前测试基础段的DTO："+curTestDto+"是否配置");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			clearDataMap();
			ActionTestUtils.setTestResultWhenException(e, curTestDto);
		}
	}
	
	/**
	 * 测试基础段的修改保存
	 */
	@Test
	public void step11_TestJCFieldUpdateSave(){
		String curDtoName = "";
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();// 当前正在测试的DTO
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0){
				String resultCode = "";
				String excelName = "";
				Object serviceResult = null;
				for(String curTestDtoName : currTestJCDtoList){
					curDtoName = curTestDtoName;
					excelName = ActionTestUtils.getExcelFileOrSheetName(curTestDtoName, false);
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							String tName = ActionTestUtils.getTName(request.getPathInfo());
							Object serviceResult = LogicParamManager.getServiceResult();
							if(serviceResult instanceof MessageResult){
								MessageResult mr = ((MessageResult) serviceResult);
								if (!resultCode.equals("success") && mr.getMessage().contains("逻辑主键重复")) {
									assertTrue(true);
								} else {
									assertEquals("测试失败，当前测试DTO是："+tName+"错误信息如下："+SEQ+
											mr.getMessage(), "success", resultCode);
								}
							}else{
								assertEquals("测试失败，当前测试DTO是："+tName, "success", resultCode);
							}
						}
					}, "/framework/UpdateLevelAUTODTO-"+curTestDtoName+".action", excelName);
					
					if(!StringUtils.isBlank(resultCode) && !resultCode.equals("success")){
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult){
							assertEquals("测试失败，当前测试DTO是："+curTestDtoName+"错误信息如下："+SEQ+
									((MessageResult) serviceResult).getMessage(), "success", resultCode);
						}
						clearDataMap();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curDtoName);
		}
	}
	
	/**
	 * 测试点击明细段链接
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	public void step12_TestClickMXLinked(){
		String uri = null;
		String curJCDtoName = null;
		currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();// 当前正在测试的明细DTO
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0){
				if(showValueMap.entrySet().size() > 0 && linkedMap.entrySet().size() > 0
						&& showValueMap.entrySet().size() == linkedMap.entrySet().size()){
					String jcId = null;// 基础段ID
					Field jcPkField = null;
					Field mxFkField = null;
					String mxTName = null;
					int rowIndex = -1;
					String excelName = null;
					String sheetName = null;
					String resultCode = "";
					Object serviceResult = null;
					List<ShowValue> showValList = null;
					List<ShowOperation> linkedList = null;

					currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 获取当前将要进行测试的所有明细DTO
					
					for(String curTestJCDtoName : currTestJCDtoList){
						
						// 判断测试的明细段是否为空（有可能出现当前基础段没有明细段的情况）
						if(currTestMXDtoList == null || currTestMXDtoList.size() <= 0){
							break;
						}
						
						uri = "/";
						curJCDtoName = curTestJCDtoName;
						showValList = showValueMap.get(curTestJCDtoName);// 当前基础段的展示数据
						linkedList = linkedMap.get(curTestJCDtoName);// 当前基础段的所有连接（不包含基础段链接，只有明细链接）
						
						jcPkField = ActionTestUtils.getPKField(curTestJCDtoName);// 基础段主键字段
						excelName = ActionTestUtils.getExcelFileOrSheetName(curTestJCDtoName, false);// 基础段对应的参数文件名称
						sheetName = ActionTestUtils.getExcelFileOrSheetName(curTestJCDtoName, true);// 基础段对应的sheet页名称
						rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, jcPkField.getName());// 基础段主键字段所在的行号
						jcId = ExcelUtils.getExcelValue(excelName, sheetName+"~"+rowIndex+"~1", "~");// 基础段主键字段的值
						
						if(showValList != null && linkedList != null && linkedList.size() > 0){
							for(ShowOperation linked : linkedList){// 循环链接
								mxTName = ActionTestUtils.getTName(linked.getOperationAction());
								
								if(!currTestMXDtoList.contains(mxTName)){
									continue;
								}
								
								uri += linked.getOperationNamespace()+"/"+linked.getOperationAction()+".action";
								
								context.put("id", showValList.get(0).getValue());
								context.put("type", showValList.get(1).getValue());
								context.put("windowId", "TaskModelInst");
								
								// 明细段链接
								initServletMockObjects();
								resultCode = testAction(uri, ActionTestUtils.getTestDataProvider(), context);
								
								if(!StringUtils.isBlank(resultCode)){
									if(!resultCode.equals("success")){
										serviceResult = LogicParamManager.getServiceResult();
										if(serviceResult instanceof MessageResult){
											assertEquals("测试失败，当前测试DTO是："+curTestJCDtoName+"错误信息如下："+SEQ+
													((MessageResult) serviceResult).getMessage(), "success", resultCode);
										}else {
											assertEquals("测试失败，当前测试DTO是："+mxTName, "success", resultCode);
										}
										clearDataMap();
										break;
									}else{
										// 获取当前明细段的快捷跳转按钮
										ShowList showList = (ShowList) LogicParamManager.getServiceResult();
										getJumpBtn(mxTName, showList);
										
										// 将基础段的ID数据写入到当前基础段对应的明细段的参数文件中去
										excelName = ActionTestUtils.getExcelFileOrSheetName(mxTName, false);// 明细段参数excel文件名称
										sheetName = ActionTestUtils.getExcelFileOrSheetName(mxTName, true);// 明细段参数文件中记录数据额sheet页名称
										mxFkField = ActionTestUtils.getFKField(mxTName, curTestJCDtoName);// 明细段外键字段
										rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, mxFkField.getName());// 明细段外键字段所在的行号
										
										// 将基础段的ID放入明细段对应的Excel文件中
										condition.add(sheetName+"~"+rowIndex+"~1~"+jcId);
										ExcelUtils.updateExcelValue(excelName, condition, "~");
									}
								}
							}// 循环链接
						}else{//判断当前dto的link和显示数据是否为空
							clearDataMap();
							fail("测试失败，测试数据异常");
						}
						
					}// 循环dto
				}else{// 判断map的数据
					clearDataMap();
					fail("测试失败，测试数据异常");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			clearDataMap();
			ActionTestUtils.setTestResultWhenException(e, curJCDtoName);
		}
	}
	
	/**
	 * 明细段新增按钮
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	public void step13_TestClickMXAddBtn(){
		String curMXDtoName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0){
				String resultCode = null;
				Object serviceResult = null;
				for(String mxDtoName : currTestMXDtoList){
					initServletMockObjects();
					context.put("windowId", "TaskModelInst");
					resultCode = testAction("/framework/ShowSaveLevelAutoDTOMX-"+mxDtoName+".action", ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")){
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult){
							assertEquals("测试失败，当前测试DTO是："+mxDtoName+"错误信息如下："+SEQ+
									((MessageResult) serviceResult).getMessage(), "success", resultCode);
						}else {
							assertEquals("测试失败，当前测试DTO是："+mxDtoName, "success", resultCode);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName);
		}
	}
	
	
	/**
	 * 明细段关键性数据新增保存
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	public void step14_TestClickMXAddSaveBtnPivotalDatas(){
		String curMXDtoName = null;
		int curSheetIndex = 1;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0){
				String resultCode = null;
				String excelName = null;
				String sheetName = null;
				Field fkField = null;
				String fkVal = null;
				int rowIndex = -1;
				int sheetCount = 0;// 记录存放关键性数据的Sheet页的数目
				int doteIndex = 0;
				String className = null;
				String exceptResult = null;
				
				for(String mxDtoName : currTestMXDtoList){
					curMXDtoName = mxDtoName;
					// 获取明细段对应基础段的ID
					fkVal = getMXFKFieldValue(mxDtoName, currTestJCDtoList);
					if(StringUtils.isBlank(fkVal)) {
						continue;
					}
					// 获取当前明细段的外键字段
					fkField = getMXFKFieldByCurTestJCDtos(mxDtoName, currTestJCDtoList);
					excelName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, false);
					sheetName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, true);
					rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, fkField.getName());
					ExcelUtils.updateExcelValue(excelName, sheetName+"~"+rowIndex+"~1~"+fkVal, "~");
					
					doteIndex = mxDtoName.lastIndexOf(".");
					className = mxDtoName.substring(doteIndex);
					sheetCount = ExcelUtils.getTestDataSheetCount(excelName, className);
					
					for(int index = 1; index <= sheetCount; index++) {
						curSheetIndex = index;
						initServletMockObjects();
						resultCode = testAction("/framework/SaveLevelAutoDTOMX-"+mxDtoName+".action", excelName, index);
						
						exceptResult = ExcelUtils.getExpectResult(excelName, index);
						if(!exceptResult.equals(resultCode)) {
							fail("当前明细段DTO："+mxDtoName+"关键性数据测试失败，当前测试的关键性数据位于"+excelName+"文件中的第"+(index+1)+"个sheet页中");
							break;
						} else {
							assertTrue(true);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName+"，且当前DTO的关键性数据测试失败，当前测试的关键性数据位于"+curMXDtoName+"文件中的第"+(curSheetIndex+1)+"个sheet页中");
		}
	}
	
	/**
	 * 明细段的新增保存
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	@Rollback(false)
	public void step15_TestClickMXAddSaveBtn(){
		String curMXDtoName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0){
				String resultCode = null;
				String excelName = null;
				String sheetName = null;
				Object serviceResult = null;
				Object tObject = null;
				Field pkField = null;
				String pkVal = null;
				int rowIndex = -1;
				for(String mxDtoName : currTestMXDtoList){
					curMXDtoName = mxDtoName;
					excelName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, false);
					initServletMockObjects();
					resultCode = testAction("/framework/SaveLevelAutoDTOMX-"+mxDtoName+".action", excelName);
					
					if(!resultCode.equals("success")){
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult){
							assertEquals("测试失败，当前测试DTO是："+mxDtoName+"错误信息如下："+SEQ+
									((MessageResult) serviceResult).getMessage(), "success", resultCode);
						}else {
							assertEquals("测试失败，当前测试DTO是："+mxDtoName, "success", resultCode);
						}
						break;
					}else{
						sheetName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, true);
						tObject = RequestManager.getTOject();// 获取保存的明细数据
						pkField = ActionTestUtils.getPKField(mxDtoName);
						pkVal = ReflectOperation.getFieldValue(tObject, pkField.getName()).toString();
						rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, pkField.getName());
						
						condition.add(sheetName+"~"+rowIndex+"~1~"+pkVal);
						ExcelUtils.updateExcelValue(excelName, condition, "~");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName);
		}
	}
	
	/**
	 * 明细修改链接
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	public void step16_TestClickMXUpdateLinked(){
		String curMXDtoName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0){
				String resultCode = null;
				String excelName = null;
				String sheetName = null;
				Object serviceResult = null;
				String mxId = null;
				Field pkField = null;
				int rowIndex = -1;
				for(String mxDtoName : currTestMXDtoList){
					curMXDtoName = mxDtoName;
					pkField = ActionTestUtils.getPKField(mxDtoName);
					excelName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, false);
					sheetName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, true);
					rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, pkField.getName());
					mxId = ExcelUtils.getExcelValue(excelName, sheetName+"~"+rowIndex+"~1", "~");

					context.put("id", mxId);
					context.put("type", "ShowCheckUpdateLevelAutoDTOMX");
					context.put("windowId", "TaskModelInst");
					
					initServletMockObjects();
					resultCode = testAction("/framework/ShowCheckUpdateLevelAutoDTOMX-"+mxDtoName+".action", ActionTestUtils.getTestDataProvider(), context);

					if(!resultCode.equals("success")){
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult){
							assertEquals("测试失败，当前测试DTO是："+mxDtoName+"错误信息如下："+SEQ+
									((MessageResult) serviceResult).getMessage(), "success", resultCode);
						}else {
							assertEquals("测试失败，当前测试DTO是："+mxDtoName, "success", resultCode);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName);
		}
	}
	
	/**
	 * 明细修改保存
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	public void step17_TestClickMXUpdateSaveBtn(){
		String curMXDtoName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0){
				String resultCode = null;
				String excelName = null;
				String sheetName = null;
				Field fkField = null;
				String fkFieldVale = null;
				Object serviceResult = null;
				int rowIndex = -1;
				
				currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
				for(String mxDtoName : currTestMXDtoList){
					curMXDtoName = mxDtoName;
					
					// 设置当前的层级ID
					excelName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, false);
					sheetName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, true);
					
					for(String jcDto : currTestJCDtoList) {
						fkField = ActionTestUtils.getFKField(mxDtoName, jcDto);
						if(fkField != null) {
							break;
						}
					}
					rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, fkField.getName());
					fkFieldVale = ExcelUtils.getExcelValue(excelName, sheetName+"~"+rowIndex+"~1", "~");
					setLevelIdValue("/framework/UpdateLevelAutoDTOMX-"+mxDtoName+".action", fkFieldVale);
					
					initServletMockObjects();
					resultCode = testAction("/framework/UpdateLevelAutoDTOMX-"+mxDtoName+".action", excelName);
					if(!resultCode.equals("success")){
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult){
							assertEquals("测试失败，当前测试DTO是："+mxDtoName+"错误信息如下："+SEQ+
									((MessageResult) serviceResult).getMessage(), "success", resultCode);
						}else {
							assertEquals("测试失败，当前测试DTO是："+mxDtoName, "success", resultCode);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName);
		}
	}
	
	/**
	 * 漏报新增按钮
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	public void step18_TestClickOtherAddBtn(){
		String curMXDtoName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0){
				String resultCode = null;
				Object serviceResult = null;
				for(String mxDtoName : currTestMXDtoList){
					initServletMockObjects();
					resultCode = testAction("/framework/OtherShowSaveLevelAutoDTOMX-"+mxDtoName+".action", "");
					
					if(!resultCode.equals("success")){
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult){
							assertEquals("测试失败，当前测试DTO是："+mxDtoName+"错误信息如下："+SEQ+
									((MessageResult) serviceResult).getMessage(), "success", resultCode);
						}else {
							assertEquals("测试失败，当前测试DTO是："+mxDtoName, "success", resultCode);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName);
		}
	}
	
	/**
	 * 漏报新增保存
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	public void step19_TestClickOtherSaveBtn(){
		String curMXDtoName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0){
				String resultCode = null;
				String excelName = null;
				Object serviceResult = null;
				for(String mxDtoName : currTestMXDtoList){
					curMXDtoName = mxDtoName;
					excelName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, false);
					
					initServletMockObjects();
					resultCode = testAction("/framework/OtherSaveLevelAutoDTOMX-"+mxDtoName+".action", excelName);
					if(!resultCode.equals("success")){
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult){
							assertEquals("测试失败，当前测试DTO是："+mxDtoName+"错误信息如下："+SEQ+
									((MessageResult) serviceResult).getMessage(), "success", resultCode);
						}else {
							assertEquals("测试失败，当前测试DTO是："+mxDtoName, "success", resultCode);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName);
		}
	}
	
	/**
	 * 明细数据删除
	 * 说明：某些基础段中是没有明细段的，因此，直接跳过明细段的功能
	 */
	@Test
	public void step20_TestClickDeleteBtnForMX(){
		String curMXDtoName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0){
				int rowIndex = -1;
				Field pkField = null;
				String pkFielVal = null;
				String resultCode = null;
				String excelName = null;
				String sheetName = null;
				Object serviceResult = null;
				for(String mxDtoName : currTestMXDtoList){
					curMXDtoName = mxDtoName;
					pkField = ActionTestUtils.getPKField(mxDtoName);
					excelName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, false);
					sheetName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, true);
					rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, pkField.getName());
					pkFielVal = ExcelUtils.getExcelValue(excelName, sheetName+"~"+rowIndex+"~1", "~");
					
					context.put("idList", pkFielVal);
					initServletMockObjects();
					resultCode = testAction("/framework/AutoDTOMXDeleteListByIdListLevelAutoDTOMX-"+mxDtoName+".action", ActionTestUtils.getTestDataProvider(), context);
					if(!resultCode.equals("success")){
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult){
							assertEquals("测试失败，当前测试DTO是："+mxDtoName+"错误信息如下："+SEQ+
									((MessageResult) serviceResult).getMessage(), "success", resultCode);
						}else {
							assertEquals("测试失败，当前测试DTO是："+mxDtoName, "success", resultCode);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName);
		}
	}
	
	/**
	 * 测试快捷跳转按钮
	 */
	@Test
	public void step21_TestClickJumpBtn() {
		String curMXDtoName = null;
		String opName = null;
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);// 当前正在测试的明细DTO
			if(currTestMXDtoList != null && currTestMXDtoList.size() > 0) {
				String resultCode = null;
				String excelName = null;
				String sheetName = null;
				Object serviceResult = null;
				String mxId = null;
				Field pkField = null;
				int rowIndex = -1;
				String opType = null;
				String uri = "/";
				boolean endFlag = false;
				List<ShowOperation> jumpBtnList = null;
				
				for(String mxDtoName : currTestMXDtoList) {
					if(endFlag) {
						break;
					}
					curMXDtoName = mxDtoName;
					jumpBtnList = jumpBtnMap.get(mxDtoName);
					
					if(jumpBtnList == null) {
						continue;
					}
					
					for(ShowOperation op : jumpBtnList) {
						context.put("windowId", "TaskModelInst");
						opType = op.getOperationType();
						opName = op.getOperationName();
						if(opType.equals("PostJump")) {
							pkField = ActionTestUtils.getPKField(mxDtoName);
							excelName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, false);
							sheetName = ActionTestUtils.getExcelFileOrSheetName(mxDtoName, true);
							rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, pkField.getName());
							mxId = ExcelUtils.getExcelValue(excelName, sheetName+"~"+rowIndex+"~1", "~");
							
							context.put("idList", mxId);
						}
						
						// 点击快捷跳转按钮
						initServletMockObjects();
						uri += op.getOperationNamespace()+"/"+op.getOperationAction()+".action";
						resultCode = testAction(uri, ActionTestUtils.getTestDataProvider(), context);
						if(!resultCode.equals("success")) {
							endFlag = true;
							serviceResult = LogicParamManager.getServiceResult();
							if(serviceResult instanceof MessageResult) {
								fail("测试失败，当前测试DTO是："+mxDtoName+"，测试的快捷跳转按钮是："+opName+SEQ+"错误信息如下："+((MessageResult) serviceResult).getMessage());
							} else {
								fail("测试失败，当前测试DTO是："+mxDtoName+"，测试的快捷跳转按钮是："+opName);
							}
							break;
						}
					}// jumpBtn loop end
				}// MXDTO loop end
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curMXDtoName+"测试的快捷跳转按钮是："+opName);
		}
	}
	
	/**
	 * <P>直接提交基础段数据</P>
	 */
	@Test
	public void step22_TestClickSubmitBtnForJCField() {
		String curTestDTOName = null;
		try {
			String excelName = null;
			String sheetName = null;
			int rowIndex = -1;
			String jcId = null;// 基础段的ID数据
			Field pkField = null;// 基础段的主键字段
			String resultCode = null;
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(currTestJCDtoList != null && currTestJCDtoList.size() > 0) {
				for(String jcDTO : currTestJCDtoList) {
					curTestDTOName = jcDTO;
					excelName = ActionTestUtils.getExcelFileOrSheetName(jcDTO, false);
					sheetName = ActionTestUtils.getExcelFileOrSheetName(jcDTO, true);
					pkField = ActionTestUtils.getPKField(jcDTO);
					rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, pkField.getName());
					jcId = ExcelUtils.getExcelValue(excelName, sheetName+"~"+rowIndex+"~1", "~");
					
					context.put("idList", jcId);
					context.put("windowId", "TaskModelInst");
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							String tNameString = ActionTestUtils.getTName(request.getPathInfo());
							if(!resultCode.equals("success")) {
								Object serviceResult = LogicParamManager.getServiceResult();
								if(serviceResult instanceof MessageResult) {
									assertEquals("提交基础段失败，当前提交的DTO是："+tNameString+"，错误信息是："+((MessageResult) serviceResult).getMessage(), "success", resultCode);
								} else {
									assertEquals("提交基础段失败，当前提交的DTO是："+tNameString, "success", resultCode);
								}
							}
						}
					}, "/framework/ZxptsystemRptSubmitStatusUpdateFieldLevelAUTODTO-"+jcDTO+".action", ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, curTestDTOName+"，基础段数据测试提交失败");
		}
	}
	
	/**
	 * <P>直接提交任务明细机构树</P>
	 */
	@Test
	@Rollback(false)
	public void step23_TestClickSubmitBtnForTaskModelInst() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			List<String> tableNameList = getTableNameList(currTestJCDtoList);// 获取当前基础段对应的数据库表名称
			if(tableNameList != null && tableNameList.size() > 0) {
				// 查询ReportModel_Table数据库表
				DetachedCriteria dc = DetachedCriteria.forEntityName("extend.dto.ReportModel_Table");
				dc.add(Restrictions.in("strTableName", tableNameList));
				List<Object> modelTableList = ActionTestUtils.getDBData(dc);
				
				if(modelTableList != null && modelTableList.size() > 0) {
					// 获取当前的任务信息
					int rowIndex = ExcelUtils.getRowNumByFieldName("TaskFlow.xls", "TaskFlow", "id");
					String taskId = ExcelUtils.getExcelValue("TaskFlow.xls", "TaskFlow~"+rowIndex+"~1", "~");
					if(!StringUtils.isBlank(taskId)) {
						dc = DetachedCriteria.forEntityName("report.dto.TaskModelInst");
						dc.add(Restrictions.eq("taskFlow.id", taskId));
						dc.add(Restrictions.in("reportModel_Table", modelTableList));
						List<Object> tmiList = ActionTestUtils.getDBData(dc);
						if(tmiList != null && tmiList.size() > 0) {
							int index = 0;
							String[] idList = new String[currTestJCDtoList.size()];
							for(Object rmt : tmiList) {
								idList[index] = ((TaskModelInst) rmt).getId();
								index++;
							}
							context.put("idList", idList);
							context.put("windowId", "TaskModelInst");
							
							// 提交数据
							testAction("/framework/RptSubmitStatusALLUpdateField-report.dto.TaskModelInst.action", new TestDataProvider() {
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
										context.clear();
									}
									return null;
								}
							}, context);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskModelInst"+"，任务明细机构树测试提交失败");
		}
	}
	
	
	
	/**
	 * <P>获取DTO的对应数据库表名</P>
	 * @param dtoNameList 需要获取表明的DTO列表参数
	 * @return 每一个DTO对应的数据库表名
	 * @throws Exception
	 */
	private List<String> getTableNameList(List<String> dtoNameList) throws Exception {
		List<String> tableNameList = null;
		if(dtoNameList != null && dtoNameList.size() > 0) {
			tableNameList = new ArrayList<String>();
			for(String dtoName : dtoNameList) {
				Class<?> c = Class.forName(dtoName);
				Entity e = c.getAnnotation(Entity.class);
				if(e != null) {
					Table t = c.getAnnotation(Table.class);
					if(t != null) {
						tableNameList.add(t.name());
					}
				}
			}
		}
		return tableNameList;
	} 
	
	/**
	 * 清空Map（存放链接按钮、新增数据）
	 */
	private static void clearDataMap(){
		showValueMap.clear();
		linkedMap.clear();
		jumpBtnMap.clear();
	}

	/**
	 * 获取当前明细段的快捷跳转按钮
	 */
	private void getJumpBtn(String mxDtoName, ShowList showList) {
		if(!StringUtils.isBlank(mxDtoName) && showList != null) {
			List<ShowOperation> operationList = showList.getOperationList();
			if(operationList != null && operationList.size() > 0) {
				String opType = null;
				List<ShowOperation> tempList = new ArrayList<ShowOperation>();
				for(ShowOperation op : operationList) {
					opType = op.getOperationType();
					if(!StringUtils.isBlank(opType) && (opType.equals("PostJump") || opType.equals("GetJump"))) {
						tempList.add(op);
					}
				}
				jumpBtnMap.put(mxDtoName, tempList);
			}
		}
	}
	
	@BeforeClass
	public static void beforeTestClass() {
		// 创建此文件，如果不创建此文件且在任务数据生成时没有访问这个文件，则在测试执行完成之后删除数据时会出现异常（文件找不到）
		ActionTestUtils.createFilePath(System.getProperty("java.io.tmpdir")+"allTestJCDTOListFile.txt");
	}
	
	/**
	 * <p>测试类运行完毕，则将参数文件中的主键以及外键值全部清空</p>
	 */
	@AfterClass
	public static void afterTestClass() {
		clearDataMap();
		
		int rowIndex = -1;
		Field pkField = null;
		Field fkField = null;
		String excelName = null;
		String sheetName = null;
		List<String> condition = new ArrayList<String>();
		
		// 删除任务对应的Excel文件中的主键值
		rowIndex = ExcelUtils.getRowNumByFieldName("TaskFlow.xls", "TaskFlow", "id");
		ExcelUtils.updateExcelValue("TaskFlow.xls", "TaskFlow~"+rowIndex+"~1", "~");
		
		// 获取当前测试的基础段DTO
		List<String> currTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
		// 获取当前测试的明细段DTO
		List<String> currTestMXDtoList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);
		
		if(currTestJCDtoList != null && currTestJCDtoList.size() > 0) {
			for(String curJCDto : currTestJCDtoList) {// 基础
				excelName = ActionTestUtils.getExcelFileOrSheetName(curJCDto, false);
				sheetName = ActionTestUtils.getExcelFileOrSheetName(curJCDto, true);
				pkField = ActionTestUtils.getPKField(curJCDto);
				rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, pkField.getName());
				ExcelUtils.updateExcelValue(excelName, sheetName+"~"+rowIndex+"~1", "~");
				
				if(currTestMXDtoList != null && currTestMXDtoList.size() > 0) {// 明细
					for(String curMXDto : currTestMXDtoList) {
						excelName = ActionTestUtils.getExcelFileOrSheetName(curMXDto, false);
						sheetName = ActionTestUtils.getExcelFileOrSheetName(curMXDto, true);
						
						pkField = ActionTestUtils.getPKField(curMXDto);
						rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, pkField.getName());
						condition.add(sheetName+"~"+rowIndex+"~1");
						
						fkField = ActionTestUtils.getFKField(curMXDto, curJCDto);
						rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, fkField.getName());
						condition.add(sheetName+"~"+rowIndex+"~1");
						
						ExcelUtils.updateExcelValue(excelName, condition, "~");
					}
				}
			}
		}
	}
	
	/**
	 * <p>多个数据时，设置层级数据ID，不然取到的会是session中的最后一个</p>
	 * @param 层级数据ID
	 * @actionURL actionURL
	 */
	private void setLevelIdValue(String actionURL, String idValue) {
		String SEG = "-";
		String LEVEL = "Level";
		String level = actionURL.substring(actionURL.indexOf(LEVEL));
		String currentLevel = level.substring(LEVEL.length(),level.indexOf(SEG));
		Map<String, Object> session = getSession();
		if(session != null) {
			session.put("action_levelIdValue-"+currentLevel, idValue);
		}
	}
	
	/**
	 * 根据当前测试的基础段获取明细段的外键字段的值
	 */
	private String getMXFKFieldValue(String mxDto, List<String> jcDtoList) throws Exception{
		String type = null;
		String fkValue = null;
		Field fkField = null;
		if(!StringUtils.isBlank(mxDto) && jcDtoList != null && jcDtoList.size() > 0) {
			Field[] fs = Class.forName(mxDto).getFields();
			for(Field f : fs) {
				type = f.getType().toString().replace("class", "").trim();
				if(jcDtoList.contains(type)){
					fkField = f;
					break;
				}
			}
			
			if(fkField != null) {
				String excelName = ActionTestUtils.getExcelFileOrSheetName(fkField.getType().toString(), false);
				String sheetName = ActionTestUtils.getExcelFileOrSheetName(fkField.getType().toString(), true);
				int rowIndex = ExcelUtils.getRowNumByFieldName(excelName, sheetName, fkField.getName());
				if(rowIndex >= 0) {
					fkValue = ExcelUtils.getExcelValue(excelName, sheetName+"~"+rowIndex+"~1", "~");
				}
			}
		}
		return fkValue;
	}
	
	/**
	 * 根据当前测试的基础段获取明细段的外键字段
	 */
	private Field getMXFKFieldByCurTestJCDtos(String mxDto, List<String> jcDtoList) throws Exception{
		String type = null;
		Field fkField = null;
		if(!StringUtils.isBlank(mxDto) && jcDtoList != null && jcDtoList.size() > 0) {
			Field[] fs = Class.forName(mxDto).getFields();
			for(Field f : fs) {
				type = f.getType().toString().replace("class", "").trim();
				if(jcDtoList.contains(type)){
					fkField = f;
					break;
				}
			}
			
		}
		return fkField;
	}
	
	
	
}
