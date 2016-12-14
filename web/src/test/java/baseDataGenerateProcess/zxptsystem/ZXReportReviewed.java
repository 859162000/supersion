package baseDataGenerateProcess.zxptsystem;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowTree;
import framework.show.ShowTreeNode;
import framework.show.ShowValue;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.CheckResult;
import framework.test.ExcelUtils;
import framework.test.TestDataProvider;
import framework.test.TxtUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZXReportReviewed extends ActionTestCase {
	private static String uri = "/";
	private String curTestDto = null;
	private String curTestLinked = null;
	private String instInfoFileName = "InstInfo.xls";
	private String dataFileName = "ZXGenerateReport.xls";
	private List<String> curTestJCDTOList = null;
	private List<String> curTestMXDTOList = null;
	
	private static ShowList taskShowList = null;// 任务层列表，用于记录当前机构下有哪些任务需要做审核
	private static Map<String, Object> context = new HashMap<String, Object>();
	private static Map<String, List<ShowValue>> showValueMap = new HashMap<String, List<ShowValue>>();// 存放显示数据
	private static Map<String, List<ShowOperation>> linkedMap = new HashMap<String, List<ShowOperation>>();// 存放链接数据
	private static Map<String, Map<String, Object>> paramMap = new HashMap<String, Map<String,Object>>();
	private static Map<String, ShowSaveOrUpdate> saveOrUpdateMap = new HashMap<String, ShowSaveOrUpdate>();// 存放信息展示界面（新增、修改界面）数据，目前只是针对于基础段的“基础段”链接而用 
	
	/**
	 * <P>点击桌面图标</P>
	 */
	@Test
	public void Step00_TestClickDeskTopIcon() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "TaskModelInstSH");
			testAction("/framework/ReviewShowTree-report.dto.TaskModelInst.action", ActionTestUtils.getTestDataProvider(), context);
			
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
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, null+"，点击“明细数据审核”桌面图标出错");
		}
	}
	
	
	/**
	 * <P>点击树形机构菜单</P>
	 */
	@Test
	public void Step01_TestClickTreeMenu() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			if(uri.equals("/")){// 没有下发这个任务时，不做这个测试
				return;
			}
			// 显示当前所有已经提交的基础段
			testAction(uri, ActionTestUtils.getTestDataProvider(), context);
			uri = "/";
			
			curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			ShowList showList = (ShowList) LogicParamManager.getServiceResult();
			
			if(showList == null || curTestJCDTOList == null || curTestJCDTOList.size() <= 0) {
				return;
			}
			
			List<List<ShowValue>> showValueListList = showList.getValueTable();// 每一行的数据集合
			if (showValueListList == null || showValueListList.size() <= 0) {
				return;
			}// 显示数据判断完成
			
			// 找出已经提交的基础段数据
			String tName = null;
			for(List<ShowValue> showValueList : showValueListList) {
				// 将当前测试的且已经提交的数据获取到
				Map<String, Object> map = new HashMap<String, Object>();
				tName = ActionTestUtils.getTName(showValueList.get(1).getValue());
				if(curTestJCDTOList.contains(tName)) {
					map.put("id", showValueList.get(0).getValue());
					map.put("type", showValueList.get(1).getValue());
					map.put("windowId", "TaskModelInstSH");
					paramMap.put(tName, map);
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.ReportModelInst"+"，点击树形菜单出错");
		}
	}
	
	/**
	 * <P>点击基础段查看链接</P>
	 */
	@Test
	public void Step02_TestClickJCCKLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(paramMap.size() > 0) {
				String resultCode = null;
				curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
				for(String jcDto : curTestJCDTOList) {
					curTestDto = jcDto;// 记录当前测试的DTO
					
					initServletMockObjects();
					resultCode = testAction( "/framework/SHJumpByTypeLevelAUTODTOSH-report.dto.TaskModelInst.action", ActionTestUtils.getTestDataProvider(), paramMap.get(jcDto));
					
					Object serviceResult = LogicParamManager.getServiceResult();
					if(!resultCode.equals("success")) {
						if(serviceResult instanceof MessageResult) {
							assertEquals("测试任务层“查看”链接失败，当前测试DTO是："+jcDto+",错误信息是："+((MessageResult) serviceResult).getMessage(), "success", resultCode);
						} else {
							assertEquals("测试任务层“查看”链接失败，当前测试DTO是："+jcDto, "success", resultCode);
						}
						break;
					} else {
						ShowList showList = ((ShowList) serviceResult);
						if(showList.getValueTable() != null && showList.getValueTable().size() > 0) {
							showValueMap.put(jcDto, ((ShowList) serviceResult).getValueTable().get(0));
						}
						if(showList.getLinkedList() != null && showList.getLinkedList().size() > 0) {
							linkedMap.put(jcDto, ((ShowList) serviceResult).getLinkedList().get(0));
						}
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，点击任务层“查看”链接出错");
		}
	}
	
	/**
	 * <P>点击当前基础段的各个明细段链接</P>
	 */
	@Test
	public void Step03_TestClickMXFieldLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());

			if(showValueMap.size() > 0 && linkedMap.size() > 0) {
				curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
				curTestMXDTOList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);
				
				if(curTestJCDTOList != null && curTestMXDTOList != null
						&& curTestJCDTOList.size() > 0
						&& curTestMXDTOList.size() > 0) {
					
					Object serviceResult = null;
					ShowList showList = null;
					String url = null;
					boolean endFlag = false;
					String tName = null;
					String resultCode = null;
					List<ShowOperation> linkedList = null;
					for(String jcDto : curTestJCDTOList) {
						curTestDto = jcDto;// 记录当前进行测试的DTO
						linkedList = linkedMap.get(jcDto);
						if(linkedList == null || linkedList.size() <= 0) {// 如当前基础段本身边无任务的链接这不做测试，继续下一个基础段的测试
							continue;
						}
						for(ShowOperation linked : linkedList) {
							tName = ActionTestUtils.getTName(linked.getOperationAction());
							if(curTestMXDTOList.contains(tName)){// 当前的链接在配置要测试的明细段中，则开始测试明细段链接
								curTestLinked = linked.getOperationName();// 记录当前进行测试的明细段链接
								
								context.put("id", showValueMap.get(jcDto).get(0).getValue());
								context.put("type", showValueMap.get(jcDto).get(1).getValue());
								context.put("windowId", "TaskModelInstSH");
								
								url = "/"+linked.getOperationNamespace()+"/"+linked.getOperationAction()+".action";
								initServletMockObjects();
								resultCode = testAction(url, ActionTestUtils.getTestDataProvider(), context);
								
								if(!resultCode.equals("success")) {
									endFlag = true;
									serviceResult = LogicParamManager.getServiceResult();
									if(serviceResult instanceof MessageResult) {
										fail("测试失败，在测试DTO："+curTestDto+"的"+curTestLinked+"链接时出现错误。"+((MessageResult) serviceResult).getMessage());
									} else {
										fail("测试失败，在测试DTO："+curTestDto+"的"+curTestLinked+"链接时出现错误。");
									}
								} else {
									showList = (ShowList) LogicParamManager.getServiceResult();
									if(showList.getValueTable() != null && showList.getValueTable().size() > 0) {
										showValueMap.put(tName, showList.getValueTable().get(0));// 设置明细段的显示数据
									}
									if(showList.getLinkedList() != null && showList.getLinkedList().size() > 0) {
										linkedMap.put(tName, showList.getLinkedList().get(0));// 设置明细段的链接数据
									}
								}
								if(endFlag) {
									break;
								}
							}
						}// 链接循环完毕（不包括基础段链接）
						if(endFlag) {
							break;
						}
					}// 基础段循环完毕
					
				}// 基础段和明细段判断完毕
			}// map判断结束
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，测试点击该DTO的“"+curTestLinked+"”链接时出错");
		}
	}
	
	/**
	 * <P>点击明细段的查看链接</P>
	 */
	@Test
	public void Step04_TestClickMXFieldCHLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			// 如果当前的明细段无数据，或者没有任何的链接，则不做测试
			if(showValueMap.size() <= 0 || linkedMap.size() <= 0) {
				return;
			}
			
			curTestMXDTOList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);
			if(curTestMXDTOList != null && curTestMXDTOList.size() > 0) {
				String url = null;
				String resultCode = null;
				Object serviceResult = null;
				ShowOperation linked = null;
				List<ShowValue> showValueList = null;
				for(String mxDto : curTestMXDTOList) {
					curTestDto = mxDto;// 记录当前测试的基础段DTO
					
					showValueList = showValueMap.get(mxDto);
					// 如果当前的明细段无任何数据或者是说没有任何的链接，则继续下一个明细段的测试
					if(showValueList == null || linkedMap.get(mxDto) == null || linkedMap.get(mxDto).size() <= 0) {
						continue;
					}
					linked = linkedMap.get(mxDto).get(0);
					
					curTestLinked = linked.getOperationName();// 记录当前测试的链接名称
					
					context.put("id", showValueList.get(0).getValue());
					context.put("type", showValueList.get(1).getValue());
					context.put("windowId", "TaskModelInstSH");
					
					url = "/"+linked.getOperationNamespace()+"/"+linked.getOperationAction()+".action";
					
					initServletMockObjects();
					resultCode = testAction(url, ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						serviceResult = LogicParamManager.getServiceResult();
						if(serviceResult instanceof MessageResult) {
							fail("测试失败，在测试DTO："+curTestDto+"的"+curTestLinked+"链接时出现错误。"+((MessageResult) serviceResult).getMessage());
						} else {
							fail("测试失败，在测试DTO："+curTestDto+"的"+curTestLinked+"链接时出现错误。");
						}
					}
				}// 明细段循环结束
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，测试点击该DTO的“"+curTestLinked+"”链接时出错");
		}
	}
	
	/**
	 * <P>从明细段的信息查看界面返回明细列表展示界面</P>
	 */
	@Test
	public void Step05_TestClickBackBtnAfterMXCK() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			curTestMXDTOList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);
			
			if(curTestMXDTOList != null && curTestMXDTOList.size() > 0) {
				//ShowOperation linked = null;
				String url = null;
				String resultCode = null;
				for(String mxDto : curTestMXDTOList) {
					curTestDto = mxDto;
					
					//linked = linkedMap.get(mxDto).get(0);
					
					context.put("windowId", "TaskModelInstSH");
					url = "/framework/SHShowListLevelAUTODTOSHMX-"+mxDto+".action";
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							String tName = ActionTestUtils.getTName(request.getPathInfo());
							Object serviceResult = LogicParamManager.getServiceResult();
							if(!resultCode.equals("success")){
								if(serviceResult instanceof MessageResult) {
									fail("测试失败，当前测试的明细DTO是："+tName+"，从当前DTO的信息查看界面返回到明细列表展示界面出错，错误信息是："+((MessageResult) serviceResult).getMessage());
								} else {
									fail("测试失败，当前测试的明细DTO是："+tName+"，从当前DTO的信息查看界面返回到明细列表展示界面出错");
								}
							}
						}
					}, url, ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			}
			
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，从明细的信息查看界面返回明细列表展示界面出错");
		}
	}
	
	/**
	 * <P>从明细段信息展示界面返回基础段列表展示界面</P>
	 */
	@Test
	public void Step06_TestClickBackBtnAfterMXList() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			curTestMXDTOList = ActionTestUtils.getCurrentTestMXDTOList(dataFileName);
			
			if(curTestMXDTOList != null && curTestMXDTOList.size() > 0) {
				String url = null;
				String resultCode = null;
				for(String mxDto : curTestMXDTOList) {
					curTestDto = mxDto;
					
					context.put("windowId", "TaskModelInstSH");
					url = "/framework/BackPreviousFirstLevelAUTODTOSHMX-"+mxDto+".action";
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							String tName = ActionTestUtils.getTName(request.getPathInfo());
							Object serviceResult = LogicParamManager.getServiceResult();
							if(!resultCode.equals("success")){
								if(serviceResult instanceof MessageResult) {
									fail("测试失败，当前测试的明细DTO是："+tName+"，从当前明细段列表展示界面返回到基础段列表展示界面时出错，错误信息是："+((MessageResult) serviceResult).getMessage());
								} else {
									fail("测试失败，当前测试的明细DTO是："+tName+"，从当前明细段列表展示界面返回到基础段列表展示界面时出错");
								}
							}
						}
					}, url, ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，从明细段信息展示界面返回基础段列表展示界面时出错");
		}
	}
	
	
	/**
	 * <P>点击基础段列表展示界面中的基础段链接</P>
	 */
	@Test
	public void Step07_TestClickJCFieldLinked() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			// 如若显示数据和链接数据的map中无任何记录，则不做测试
			if(showValueMap.size() <= 0 || linkedMap.size() <= 0) {
				return;
			}
			
			curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			
			if(curTestJCDTOList != null && curTestJCDTOList.size() > 0) {
				ShowOperation jcLinked = null;
				String url = null;
				String resultCode = null;
				for(String jcDto : curTestJCDTOList) {
					curTestDto = jcDto;
					
					// 如若当前的基础段中无任何的链接数据或者显示数据，则测试下一个基础段数据
					if(linkedMap.get(jcDto) == null || linkedMap.get(jcDto).size() <= 0
							|| showValueMap.get(jcDto) == null || showValueMap.get(jcDto).size() <= 0) {
						continue;
					}
					
					for(ShowOperation linked : linkedMap.get(jcDto)) {
						if("基础段".equals(linked.getOperationName())) {
							jcLinked = linked;
							break;
						}
					}
					
					url = "/"+jcLinked.getOperationNamespace()+"/"+jcLinked.getOperationAction()+".action";
					
					context.put("id", showValueMap.get(jcDto).get(0).getValue());
					context.put("type", showValueMap.get(jcDto).get(1).getValue());
					context.put("windowId", "TaskModelInstSH");
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							String tName = ActionTestUtils.getTName(request.getPathInfo());
							Object serviceResult = LogicParamManager.getServiceResult();
							if(!resultCode.equals("success")){
								if(serviceResult instanceof MessageResult) {
									fail("测试失败，当前测试的基础段DTO是："+tName+"，点击基础段链接时出错，错误信息是："+((MessageResult) serviceResult).getMessage());
								} else {
									fail("测试失败，当前测试的基础段DTO是："+tName+"，点击基础段链接时出错");
								}
							} else {
								saveOrUpdateMap.put(tName, (ShowSaveOrUpdate) serviceResult);
							}
						}
					}, url, ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，点击基础段链接时出错");
		}
	}
	
	
	/**
	 * <P>在基础段的信息查看界面点击“审核未通过”按钮</P>
	 */
	@Test
	public void Step07_TestClickReviewedFalseBtnFromJCFieldCKViewer() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			if(saveOrUpdateMap.size() <= 0) {
				return;
			}
			
			curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			
			if(curTestJCDTOList != null && curTestJCDTOList.size() > 0) {
				String url = null;
				Field pkField = null;
				ShowOperation operation = null;
				ShowSaveOrUpdate saveOrUpdate = null;
				for(String jcDto : curTestJCDTOList) {
					curTestDto = jcDto;
					
					saveOrUpdate = saveOrUpdateMap.get(jcDto);
					// 如果当前的基础段信息查看界面的数据是空的，或者当前基础段的信息查看界面没有相应的按钮，则测试下一个基础段
					if(saveOrUpdate == null) {
						continue;
					}
					if(saveOrUpdate.getOperationList() == null || saveOrUpdate.getOperationList().size() <= 0) {
						continue;
					}
					
					for(ShowOperation op : saveOrUpdate.getOperationList()) {
						if("审核未通过".equals(op.getOperationName())) {
							operation = op;
							break;
						}
					}
					
					url = "/"+operation.getOperationNamespace()+"/"+operation.getOperationAction()+".action";
					
					pkField = ActionTestUtils.getPKField(jcDto);
					context.put("serviceParam."+pkField.getName(), saveOrUpdate.getId());
					context.put("windowId", "TaskModelInstSH");
					
					initServletMockObjects();
					testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							assertTrue(true);// 发生异常就直接报错，如若没有进行下一个基础段的测试
						}
					}, url, ActionTestUtils.getTestDataProvider(), context);
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，在基础段信息查看界面点击“审核未通过”按钮时出错");
		}
	}
	
	/**
	 * <P>在基础段的信息查看界面点击“审核通过”按钮</P>
	 */
	@Test
	@Rollback(false)
	public void Step08_TestClickReviewedTrueBtnFromJCFieldCKViewer() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			if(saveOrUpdateMap.size() <= 0) {
				return;
			}
			
			curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			
			if(curTestJCDTOList != null && curTestJCDTOList.size() > 0) {
				String url = null;
				Field pkField = null;
				ShowOperation operation = null;
				ShowSaveOrUpdate saveOrUpdate = null;
				for(String jcDto : curTestJCDTOList) {
					curTestDto = jcDto;
					
					saveOrUpdate = saveOrUpdateMap.get(jcDto);
					// 如果当前的基础段信息查看界面的数据是空的，或者当前基础段的信息查看界面没有相应的按钮，则测试下一个基础段
					if(saveOrUpdate == null) {
						continue;
					}
					if(saveOrUpdate.getOperationList() == null || saveOrUpdate.getOperationList().size() <= 0) {
						continue;
					}
					
					for(ShowOperation op : saveOrUpdate.getOperationList()) {
						if("审核通过".equals(op.getOperationName())) {
							operation = op;
							break;
						}
					}
					
					url = "/"+operation.getOperationNamespace()+"/"+operation.getOperationAction()+".action";
					
					pkField = ActionTestUtils.getPKField(jcDto);
					context.put("serviceParam."+pkField.getName(), saveOrUpdate.getId());
					context.put("windowId", "TaskModelInstSH");
					
					initServletMockObjects();
					testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							assertTrue(true);// 发生异常就直接报错，如若没有进行下一个基础段的测试
						}
					}, url, ActionTestUtils.getTestDataProvider(), context);
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，在基础段信息查看界面点击“审核通过”按钮时出错");
		}
	}
	
	
	/**
	 * <P>在基础段的信息查看界面点击“返回”按钮</P>
	 */
	@Test
	public void Step09_TestClickBackBtnFromJCFieldCKViewer() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			if(saveOrUpdateMap.size() <= 0) {
				return;
			}
			
			curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			
			if(curTestJCDTOList != null && curTestJCDTOList.size() > 0) {
				String url = null;
				String resultCode = null;
				ShowOperation operation = null;
				ShowSaveOrUpdate saveOrUpdate = null;
				for(String jcDto : curTestJCDTOList) {
					curTestDto = jcDto;
					
					saveOrUpdate = saveOrUpdateMap.get(jcDto);
					// 如果当前的基础段信息查看界面的数据是空的，或者当前基础段的信息查看界面没有相应的按钮，则测试下一个基础段
					if(saveOrUpdate == null) {
						continue;
					}
					if(saveOrUpdate.getOperationList() == null || saveOrUpdate.getOperationList().size() <= 0) {
						continue;
					}
					
					for(ShowOperation op : saveOrUpdate.getOperationList()) {
						if("返回".equals(op.getOperationName())) {
							operation = op;
							break;
						}
					}
					
					url = "/"+operation.getOperationNamespace()+"/"+operation.getOperationAction()+".action";
					
					context.put("windowId", "TaskModelInstSH");
					
					initServletMockObjects();
					resultCode = testAction(new CheckResult() {
						@Override
						public void check(String resultCode, Object actionInstance) {
							if(!resultCode.equals("success")){
								String tName = ActionTestUtils.getTName(request.getPathInfo());
								Object serviceResult = LogicParamManager.getServiceResult();
								if(serviceResult instanceof MessageResult) {
									fail("测试失败，当前测试的基础段DTO是："+tName+"，在基础段信息查看界面点击“返回”按钮时出错，错误信息是："+((MessageResult) serviceResult).getMessage());
								} else {
									fail("测试失败，当前测试的基础段DTO是："+tName+"，在基础段信息查看界面点击“返回”按钮时出错");
								}
							}
						}
					}, url, ActionTestUtils.getTestDataProvider(), context);
					
					if(!resultCode.equals("success")) {
						break;
					}
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, curTestDto+"，在基础段信息查看界面点击“返回”按钮时出错");
		}
	}
	
	
	/**
	 * <P>在基础段的列表展示界面点击“返回”按钮</P>
	 * <P>因此“返回”按钮不涉及到具体的基础段或者明细段数据，只是单纯的展示任务层数据，因此只做一遍测试即可</P>
	 */
	@Test
	public void Step10_TestClickBackBtnFromJCListViewer() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			String url = "/framework/SHReviewShowListForTree-report.dto.TaskModelInst.action";
			context.put("windowId", "TaskModelInstSH");
			
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					Object serviceResult = LogicParamManager.getServiceResult();
					if(!resultCode.equals("success")){
						if(serviceResult instanceof MessageResult) {
							fail("测试失败，在基础段信息列表展示界面点击“返回”按钮时出错，错误信息是："+((MessageResult) serviceResult).getMessage());
						} else {
							fail("测试失败，在基础段信息列表展示界面点击“返回”按钮时出错");
						}
					} else {
						taskShowList = (ShowList) serviceResult;
					}
				}
			}, url, ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskModelInst，在基础段信息列表展示界面点击“返回”按钮时出错");
		}
	}
	
	
	/**
	 * <P>在任务层点击“审核未通过”按钮</P>
	 * <P>因此“审核未通过”按钮不涉及到具体的基础段或者明细段数据，只是单纯的展示任务层数据，因此只做一遍测试即可</P>
	 */
	@Test
	public void Step11_TestClickReviewedFalseFromTaskListViewer() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(curTestJCDTOList == null || curTestJCDTOList.size() <= 0) {
				return;
			}
			
			// 如果说当前的任务层无显示数据，则不做任何测试
			if(taskShowList == null || taskShowList.getOperationList() == null 
					|| taskShowList.getOperationList().size() <= 0
					|| taskShowList.getValueTable() == null
					|| taskShowList.getValueTable().size() <= 0) {
				return;
			}
			
			String url = null;
			for(ShowOperation op : taskShowList.getOperationList()) {
				if("审核未通过".equals(op.getOperationName())) {
					url = "/"+op.getOperationNamespace()+"/"+op.getOperationAction()+".action";
					break;
				}
			}
			
			if(StringUtils.isBlank(url)) {
				return;
			}
			
			if(taskShowList.getValueTable() == null ||taskShowList.getValueTable().size() <= 0) {
				return;
			}
			
			int index = 0;
			String[] idList = new String[curTestJCDTOList.size()];
			for(List<ShowValue> showValueList : taskShowList.getValueTable()) {
				if(!curTestJCDTOList.contains(ActionTestUtils.getTName(showValueList.get(1).getValue()))) {
					continue;
				}
				idList[index] = showValueList.get(0).getValue();
				index++;
			}
			
			context.put("windowId", "TaskModelInstSH");
			context.put("idList", idList);
			
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					assertTrue(true);
				}
			}, url, new TestDataProvider() {
				@Override
				public Map<String, String> getData(Map<String, Object> context) {
					request.removeAllParameters();
					if(context.size() > 0) {
						Set<Map.Entry<String, Object>> enSet = context.entrySet();
						for(Map.Entry<String, Object> en : enSet) {
							if(en.getValue().getClass().isArray()) {
								request.addParameter(en.getKey(), (String[]) en.getValue());
							} else {
								request.addParameter(en.getKey(), en.getValue().toString());
							}
						}
					}
					return null;
				}
			}, context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskModelInst，在任务层界面点击“审核未通过”按钮时出错");
		}
	}
	
	
	/**
	 * <P>在任务层点击“审核通过”按钮</P>
	 * <P>因此“审核通过”按钮不涉及到具体的基础段或者明细段数据，只是单纯的展示任务层数据，因此只做一遍测试即可</P>
	 */
	@Test
	public void Step12_TestClickReviewedTrueFromTaskListViewer() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			curTestJCDTOList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
			if(curTestJCDTOList == null || curTestJCDTOList.size() <= 0) {
				return;
			}
			
			// 如果说当前的任务层无显示数据，则不做任何测试
			if(taskShowList == null || taskShowList.getOperationList() == null 
					|| taskShowList.getOperationList().size() <= 0
					|| taskShowList.getValueTable() == null
					|| taskShowList.getValueTable().size() <= 0) {
				return;
			}
			
			String url = null;
			for(ShowOperation op : taskShowList.getOperationList()) {
				if("审核通过".equals(op.getOperationName())) {
					url = "/"+op.getOperationNamespace()+"/"+op.getOperationAction()+".action";
					break;
				}
			}
			
			if(StringUtils.isBlank(url)) {
				return;
			}
			
			if(taskShowList.getValueTable() == null ||taskShowList.getValueTable().size() <= 0) {
				return;
			}
			
			int index = 0;
			String[] idList = new String[curTestJCDTOList.size()];
			for(List<ShowValue> showValueList : taskShowList.getValueTable()) {
				if(!curTestJCDTOList.contains(ActionTestUtils.getTName(showValueList.get(1).getValue()))) {
					continue;
				}
				idList[index] = showValueList.get(0).getValue();
			}
			
			context.put("windowId", "TaskModelInstSH");
			context.put("idList", idList);
			
			testAction(new CheckResult() {
				@Override
				public void check(String resultCode, Object actionInstance) {
					assertTrue(true);
				}
			}, url, new TestDataProvider() {
				@Override
				public Map<String, String> getData(Map<String, Object> context) {
					request.removeAllParameters();
					if(context.size() > 0) {
						Set<Map.Entry<String, Object>> enSet = context.entrySet();
						for(Map.Entry<String, Object> en : enSet) {
							if(en.getValue().getClass().isArray()) {
								request.addParameter(en.getKey(), (String[]) en.getValue());
							} else {
								request.addParameter(en.getKey(), en.getValue().toString());
							}
						}
					}
					return null;
				}
			}, context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "report.dto.TaskModelInst，在任务层界面点击“审核通过”按钮时出错");
		}
	}
	
	
	
	
	
	
	
	
	
}
