package zxptsystem.helper.HTMLParseAndJSONSendTool;

import zxptsystem.dto.GR_A1;
import zxptsystem.dto.GR_A2;
import zxptsystem.dto.GR_B1;
import zxptsystem.dto.GR_B2;
import zxptsystem.dto.GR_B3;
import zxptsystem.dto.GR_B41;
import zxptsystem.dto.GR_C1;
import zxptsystem.dto.GR_C21;
import zxptsystem.dto.GR_C22;
import zxptsystem.dto.GR_C23;
import zxptsystem.dto.GR_C24;
import zxptsystem.dto.GR_C31;
import zxptsystem.dto.GR_C32;
import zxptsystem.dto.GR_C33;
import zxptsystem.dto.GR_C34;
import zxptsystem.dto.GR_D1;
import zxptsystem.dto.GR_D2;
import zxptsystem.dto.GR_D31;
import zxptsystem.dto.GR_D32;
import zxptsystem.dto.GR_D33;
import zxptsystem.dto.GR_D34;
import zxptsystem.dto.GR_D41;
import zxptsystem.dto.GR_D42;
import zxptsystem.dto.GR_D43;
import zxptsystem.dto.GR_D44;
import zxptsystem.dto.GR_D51;
import zxptsystem.dto.GR_D52;
import zxptsystem.dto.GR_D53;
import zxptsystem.dto.GR_D54;
import zxptsystem.dto.GR_D6;
import zxptsystem.dto.GR_E1;
import zxptsystem.dto.GR_E10;
import zxptsystem.dto.GR_E21;
import zxptsystem.dto.GR_E22;
import zxptsystem.dto.GR_E3;
import zxptsystem.dto.GR_E4;
import zxptsystem.dto.GR_E51;
import zxptsystem.dto.GR_E52;
import zxptsystem.dto.GR_E6;
import zxptsystem.dto.GR_E7;
import zxptsystem.dto.GR_E8;
import zxptsystem.dto.GR_E9;
import zxptsystem.dto.GR_F1;
import zxptsystem.dto.GR_F2;
import zxptsystem.dto.GR_G1;
import zxptsystem.dto.GR_G2;
import zxptsystem.dto.GrzxReportParseLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.RequestManager;

/**
 * 
 * @description <p>HTML征信报告文件解析工具类</P>
 * @createTime 2016-8-26 下午02:24:38
 * @updateTime 2016-8-26 下午02:24:38
 * @author Liutao
 * @updater Liutao
 */
public class HTMLParseTool {
	
	private String[] infoTypeStrs = new String[] {"个人基本信息", "信息概要", "信贷交易信息明细", "公共信息明细", "本人声明", "异议标注", "查询记录"};
	public static String reg = "[, “ ” \" \" ？《   》  （  ）  ，  。  、~ ! @ \\$ \\^ & \\* \\+ \\| \\ ; \\{ \\} \\( \\( \\s]+";
	private static HTMLParseTool parse = null;
	private GR_A1 reportHeader = null;
	
	private static void getInstance() throws Exception {
		if(parse == null) {
			parse = HTMLParseTool.class.newInstance();
		}
	} 
	
	/**
	 * 
	 * @description <p>生成json文件</P>
	 * @createTime 2016-9-20 上午10:39:15
	 * @updateTime 2016-9-20 上午10:39:15
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void createJSONFile(Map<String, Object> parseData, String jsonFilePath) throws Exception {
		File jsonFile = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			if(parseData != null && !parseData.isEmpty() && !StringUtils.isBlank(jsonFilePath)) {
				if(!StringUtils.isBlank(jsonFilePath)) {
					jsonFile = new File(jsonFilePath);
					
					if(!jsonFile.isDirectory()) {
						if(jsonFile.exists()) {
							jsonFile.delete();
						}
						jsonFile.createNewFile();
						
						fw = new FileWriter(jsonFile);
						bw = new BufferedWriter(fw);
						bw.write(JSONObject.fromObject(parseData).toString());
						bw.flush();
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(bw != null) {
				bw.close();
			}
			
			if(fw != null) {
				fw.close();
			}
		}
	}
	
	public static Map<String, Object> parseHTMLData(String htmlPath, String jsonFilePath, GrzxReportParseLog parseLog) throws Exception {
		Map<String, Object> parseData = parseHTMLData(htmlPath, parseLog);
		if(parseData != null && !parseData.isEmpty()) {
			parse.createJSONFile(parseData, jsonFilePath);
		}
		return parseData;
	}
	public static Map<String, Object> parseHTMLData(String htmlPath, GrzxReportParseLog parseLog) throws Exception {
		// 解析结果记录对象Map
		Map<String, Object> jsonMap = null;
		
		try {
			jsonMap = new LinkedHashMap<String, Object>();
			File htmlFile = new File(htmlPath);
			Document document = Jsoup.parse(htmlFile, "GBK");
			
			getInstance();
			
			if(document.html().indexOf("个人征信系统中没有此人的征信记录") > -1 || document.html().indexOf("查询人证件号码无效") > -1) {
				Map<String, String> errorMap = new HashMap<String, String>();
				if(document.html().indexOf("个人征信系统中没有此人的征信记录") > -1) {
					errorMap.put("code", "100");
					errorMap.put("error", "个人征信系统中没有此人的征信记录");
				}
				
				if(document.html().indexOf("查询人证件号码无效") > -1) {
					errorMap.put("code", "101");
					errorMap.put("error", "个人征信系统中没有此人的征信记录");
				}
				jsonMap.put("errorMap", errorMap) ;
				
				Elements tableList = document.getElementsContainingText("姓名");
				if(tableList != null && !tableList.isEmpty()) {
					Element table = tableList.get(0);
					Elements trList = table.select("tr");
					if(trList != null && !trList.isEmpty()) {
						Element nextTr = null;
						for(Element tr : trList) {
							if(tr != null && !StringUtils.isBlank(tr.text()) && tr.text().startsWith("姓名")) {
								nextTr = tr.nextElementSibling();
								if(nextTr != null && !StringUtils.isBlank(nextTr.text()) && 
										tr.select("td") != null && nextTr.select("td") != null &&
										tr.select("td").size() == nextTr.select("td").size()) {
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									
									for(int colIndex = 0; colIndex < tr.select("td").size(); colIndex++) {
										parse.setFieldValue(GR_A2.class, "被查询者"+tr.select("td").get(colIndex).text(), nextTr.select("td").get(colIndex).text(), fieldValueMap);
									}
									
									if(!fieldValueMap.isEmpty()) {
										jsonMap.put(GR_A2.class.getSimpleName(), fieldValueMap);
									}
								}
								break;
							}
						}
					}
				}
				return jsonMap;
			}
			
			// 获取报告头信息
			parse.reportHeader = parse.getReportHeaderInfo(document, jsonMap);
			
			// 获取其余的信息数据
			Elements trList = document.select("table").get(2).children().get(0).children();
			if(trList != null && !trList.isEmpty()) {
				Element table = null;
				String infoType_DL = null;// 信息类型（大类）
				Element tr = null;
				boolean xdxxmxFlag = false;// 信贷信息明细信息是否已经被解析的标志
				for(int index = 0; index < trList.size(); index++) {
					tr = trList.get(index);
					if(tr == null || StringUtils.isBlank(tr.text())) {
						continue;
					}
					
					if(tr.tagName().equals("style") || tr.html().indexOf("nbsp") > -1) {
						continue;
					}
					
					if("报告说明".equals(tr.text())) {
						break;
					}
					
					//System.out.println(tr.text());
					
					if(xdxxmxFlag && "信贷交易信息明细".equals(infoType_DL)) {
						infoType_DL = "";
					}
					
					if(StringUtils.isBlank(infoType_DL)) {
						infoType_DL = parse.getInfoType(tr.text()).trim();
						if(StringUtils.isBlank(infoType_DL)) {
							// 判断tr中是否直接包含了一个table，主要处理报告头查询信息解析
							if(!jsonMap.containsKey(GR_A2.class.getSimpleName()) && tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									parse.getSingleTableData(GR_A2.class, table, 0, 1, 0, table.select("tr:eq(0) td").size(), fieldValueMap);
									
									if(!fieldValueMap.isEmpty()) {
										jsonMap.put(GR_A2.class.getSimpleName(), fieldValueMap);
									}
								}
							}
							continue;
						}
					}
					
					/******************************个人基本信息******************************/
					if("个人基本信息".equals(infoType_DL)) {
						tr = parse.getNextTrNode(tr, trList);
						
						if(tr != null && "身份信息".equals(tr.text().trim())) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									parse.getSingleTableData(GR_B1.class, table, 0, 1, 0, table.select("tr:eq(0) td").size(), fieldValueMap);
									parse.getSingleTableData(GR_B1.class, table, 2, 3, 0, table.select("tr:eq(2) td").size(), fieldValueMap);
									if(!fieldValueMap.isEmpty()) {
										jsonMap.put(GR_B1.class.getSimpleName(), fieldValueMap);
									}
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						} 
						
						if(tr != null && "配偶信息".equals(tr.text())) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									parse.getSingleTableData(GR_B2.class, table, 0, 1, 0, table.select("tr:eq(0) td").size(), fieldValueMap);
									if(!fieldValueMap.isEmpty()) {
										jsonMap.put(GR_B2.class.getSimpleName(), fieldValueMap);
									}
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						} 
						
						if(tr != null && "居住信息".equals(tr.text())) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_B3.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						} 
						
						if(tr != null && "职业信息".equals(tr.text())) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMultiTileAndRowTableData(GR_B41.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}
						
						infoType_DL = "";
					} // 个人基本信息结束
					
					
					/******************************信息概要******************************/
					if("信息概要".equals(infoType_DL)) {
						tr = parse.getNextTrNode(tr, trList);
						if(tr != null && tr.text().indexOf("信用提示") > -1) {
							tr = parse.getNextTrNode(tr, trList);
							Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getSingleTableData(GR_C1.class, table, 0, 1, 0, table.select("tr:eq(0) td").size(), fieldValueMap);
								}
								
								tr = parse.getNextTrNode(tr, trList);
								if(tr != null && tr.text().indexOf("中征信评分") > -1) {
									tr = parse.getNextTrNode(tr, trList);
									if(tr != null && tr.html().indexOf("table") > -1) {
										table = tr.getElementsByTag("table").get(0);
										if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1
												&& table.select("tr").size() == 2 && table.select("tr td").size() == 4) {
											
											for(int colIndex = 0; colIndex < 2; colIndex++) {
												parse.setFieldValue(GR_C1.class, 
														table.select("tr:eq(0) td("+colIndex+")").get(0).text(), 
														table.select("tr:eq(1) td("+colIndex+")").get(0).text(), fieldValueMap);
											}
										}
										tr = parse.getNextTrNode(tr, trList);
									}
								}
							}
							
							if(!fieldValueMap.isEmpty()) {
								jsonMap.put(GR_C1.class.getSimpleName(), fieldValueMap);
							}
							
						}// 信用提示
						
						if(tr != null && tr.text().indexOf("逾期及违约信息概要") > -1) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {// 逾期呆账、资产处置、保证人代偿信息的汇总
								table = tr.getElementsByTag("table").get(0);
								if(table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									// 获取第一行标题说明
									Elements colSpanTds = table.select("tr:eq(0) td");
									for(Element colSpanTd : colSpanTds) {
										Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
										if("呆账信息汇总".equals(colSpanTd.text())) {
											parse.setFieldValue(GR_C21.class,
													table.select("tr:eq(1) td:eq(0)").text(), 
													table.select("tr:eq(2) td:eq(0)").text(), fieldValueMap);
											
											parse.setFieldValue(GR_C21.class,
													table.select("tr:eq(1) td:eq(1)").text(), 
													table.select("tr:eq(2) td:eq(1)").text(), fieldValueMap);
											
											fieldValueMap.put("ReportNo", parse.reportHeader.getReportNo());
											jsonMap.put(GR_C21.class.getSimpleName(), fieldValueMap);
										}
										
										if("资产处置信息汇总".equals(colSpanTd.text())) {
											Class.forName(GR_C22.class.getName()).newInstance();
											parse.setFieldValue(GR_C22.class,
													table.select("tr:eq(1) td:eq(2)").text(), 
													table.select("tr:eq(2) td:eq(2)").text(), fieldValueMap);
											
											parse.setFieldValue(GR_C22.class,
													table.select("tr:eq(1) td:eq(3)").text(), 
													table.select("tr:eq(2) td:eq(3)").text(), fieldValueMap);
											
											fieldValueMap.put("ReportNo", parse.reportHeader.getReportNo());
											jsonMap.put(GR_C22.class.getSimpleName(), fieldValueMap);
										}
										
										if("保证人代偿信息汇总".equals(colSpanTd.text())) {
											parse.setFieldValue(GR_C23.class,
													table.select("tr:eq(1) td:eq(4)").text(), 
													table.select("tr:eq(2) td:eq(4)").text(), fieldValueMap);
											
											parse.setFieldValue(GR_C23.class,
													table.select("tr:eq(1) td:eq(5)").text(), 
													table.select("tr:eq(2) td:eq(5)").text(), fieldValueMap);
											
											fieldValueMap.put("ReportNo", parse.reportHeader.getReportNo());
											jsonMap.put(GR_C23.class.getSimpleName(), fieldValueMap);
										}
										
									}
									
								}
								
								tr = parse.getNextTrNode(tr, trList);
							}
							
							if(tr != null && tr.text().indexOf("逾期（透支）信息汇总") > -1) {
								tr = parse.getNextTrNode(tr, trList);
								if(tr != null && tr.html().indexOf("table") > -1) {
									table = tr.getElementsByTag("table").get(0);
									if(table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
										parse.getColSpanTableData(GR_C24.class, table, jsonMap);
									}
									tr = parse.getNextTrNode(tr, trList);
								}
							}
							
						}// 逾期及违约信息概要
						
						
						if(tr != null && tr.text().indexOf("授信及负债信息概要") > -1) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && "未结清贷款信息汇总".equals(tr.text())) {
								tr = parse.getNextTrNode(tr, trList);
								if(tr != null && tr.html().indexOf("table") > -1) {
									table = tr.getElementsByTag("table").get(0);
									if(table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
										Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
										parse.getSingleTableData(GR_C31.class, table, 0, 1, 0, table.getElementsByTag("tr").get(1).children().size(), fieldValueMap);
										
										if(!fieldValueMap.isEmpty()) {
											jsonMap.put(GR_C31.class.getSimpleName(), fieldValueMap);
										}
									}
									tr = parse.getNextTrNode(tr, trList);
								}
							}
							
							if(tr != null && "未销户贷记卡信息汇总".equals(tr.text())) {
								tr = parse.getNextTrNode(tr, trList);
								if(tr != null && tr.html().indexOf("table") > -1) {
									table = tr.getElementsByTag("table").get(0);
									if(table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
										Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
										parse.getSingleTableData(GR_C32.class, table, 0, 1, 0, table.getElementsByTag("tr").get(1).children().size(), fieldValueMap);
										
										if(!fieldValueMap.isEmpty()) {
											jsonMap.put(GR_C32.class.getSimpleName(), fieldValueMap);
										}
									}
									tr = parse.getNextTrNode(tr, trList);
								}
							}
							
							if(tr != null && "未销户准贷记卡信息汇总".equals(tr.text())) {
								tr = parse.getNextTrNode(tr, trList);
								if(tr != null && tr.html().indexOf("table") > -1) {
									table = tr.getElementsByTag("table").get(0);
									if(table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
										Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
										parse.getSingleTableData(GR_C33.class, table, 0, 1, 0, table.getElementsByTag("tr").get(1).children().size(), fieldValueMap);
										
										if(!fieldValueMap.isEmpty()) {
											jsonMap.put(GR_C33.class.getSimpleName(), fieldValueMap);
										}
									}
									tr = parse.getNextTrNode(tr, trList);
								}
							}
							
							if(tr != null && "对外担保信息汇总".equals(tr.text())) {
								tr = parse.getNextTrNode(tr, trList);
								if(tr != null && tr.html().indexOf("table") > -1) {
									table = tr.getElementsByTag("table").get(0);
									if(table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
										Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
										parse.getSingleTableData(GR_C34.class, table, 0, 1, 0, table.getElementsByTag("tr").get(1).children().size(), fieldValueMap);
										
										if(!fieldValueMap.isEmpty()) {
											jsonMap.put(GR_C34.class.getSimpleName(), fieldValueMap);
										}
									}
									tr = parse.getNextTrNode(tr, trList);
								}
							}
							
						}// 授信及负债信息概要
						
						if(!xdxxmxFlag) {
							if(tr!=null && !StringUtils.isBlank(tr.text()) && tr.text().indexOf("信贷交易信息明细") > -1) {
								infoType_DL = "信贷交易信息明细";
							} else {
								infoType_DL = "";
							}
						} else {
							infoType_DL = "";
						}
					}// 信息概要结束
					
					/******************************信贷交易信息明细******************************/
					if("信贷交易信息明细".equals(infoType_DL) && !xdxxmxFlag) {
						tr = parse.getNextTrNode(tr, trList);
						
						if(tr != null && tr.text().endsWith("资产处置信息")) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_D1.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 资产处置信息
			
						if(tr != null && tr.text().endsWith("保证人代偿信息")) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_D2.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 保证人代偿信息
						
						
						LinkedList<Element> dkTrList = new LinkedList<Element>();
						LinkedList<Element> djkTrList = new LinkedList<Element>();
						LinkedList<Element> zdjkTrList = new LinkedList<Element>();
						LinkedList<Element> dbxxList = new LinkedList<Element>();
						XDXXGroupTool.getXDXXGroupDataList(dkTrList, djkTrList, zdjkTrList, dbxxList, document.select("table").get(2));
						
						// 贷款
						if(!dkTrList.isEmpty()) {
							int trIndex = 0;
							String context = null;
							for(int i = trIndex; i < dkTrList.size(); i = trIndex) {
								// 呆账
								if(dkTrList.get(i).text().indexOf("呆账") > -1) {
									context = dkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的", "元（", "，业务号", "到期。截至", "，账户状态为“", "，余额为", "元", "，"};
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									
									String[] fieldNames = null;
									if(context.split("-").length == 13) {
										fieldNames = new String[] {"LoanNo","DistriDate", "LoanInstName", "ContractMoney", "CurrDesc", "LoanType",
												"BusiNoDesc", "GuarWayDesc", "RepayFreqDesc", "ExpireDate", "StateDeadline", "accountState", "BalanceDesc"};
									} else {
										fieldNames = new String[] {"LoanNo","DistriDate", "LoanInstName", "ContractMoney", "CurrDesc", "LoanType",
												"BusiNoDesc", "GuarWayDesc", "RepayPeriodDesc", "RepayFreqDesc", "ExpireDate", "StateDeadline", "accountState", "BalanceDesc"};
									}
									
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == dkTrList.size()) {
										parse.getRepeatDatas(null, GR_D31.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(dkTrList.get(i+1).select("table"), GR_D31.class, fieldValueMap, jsonMap);
									}
									if(i+1 < dkTrList.size() && dkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(dkTrList.get(i).text().indexOf("结清") > -1 && dkTrList.get(i).text().indexOf("截至") > -1) {// 已结清
									// 整理出描述信息内容
									context = dkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的", "元（", "，业务号", "。截至", "，账户状态为“", "，"};
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									
									String[] fieldNames = null;
									if(context.split("-").length == 11) {
										fieldNames = new String[] {"LoanNo","DistriDate", "LoanInstName", "ContractMoney", "CurrDesc", "LoanType", 
												"BusiNoDesc", "GuarWayDesc", "RepaymentFreqDesc", "StateDeadline", "Square"};
									} else {
										fieldNames = new String[] {"LoanNo","DistriDate", "LoanInstName", "ContractMoney", "CurrDesc", "LoanType", 
												"BusiNoDesc", "GuarWayDesc", "RepayPeriodDesc", "RepaymentFreqDesc", "StateDeadline", "Square"};
									}
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == dkTrList.size()) {
										parse.getRepeatDatas(null, GR_D34.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(dkTrList.get(i+1).select("table"), GR_D34.class, fieldValueMap, jsonMap);
									}
									if(i+1 < dkTrList.size() && dkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(dkTrList.get(i).text().indexOf("转出") > -1) {// 转出
									// 整理出描述信息内容
									context = dkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的", "元（", "，业务号", "到期。截至", "，账户状态为“", "，"};
									for(String excludeStr : excludeStrs) {
										if(excludeStr.indexOf("转出") > -1) {
											context = context.replaceAll(excludeStr, "-转出");
										} else {
											context = context.replaceAll(excludeStr, "-");
										}
									}
									context = parse.fileterSpecialChar(context);
									
									String[] fieldNames = null;
									if(context.split("-").length == 12) {
										fieldNames = new String[] {"LoanNo","DistriDate", "LoanInstName", "ContractMoney", "CurrDesc", "LoanType", 
												"BusiNoDesc", "GuarWayDesc", "RepayFreqDesc", "ExpireDate", "StateDeadline", "RollOut"};
									} else {
										fieldNames = new String[] {"LoanNo","DistriDate", "LoanInstName", "ContractMoney", "CurrDesc", "LoanType", 
												"BusiNoDesc", "GuarWayDesc", "RepayPeriodDesc", "RepayFreqDesc", "ExpireDate", "StateDeadline", "RollOut"};
									}
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}
									
									if(i+1 == dkTrList.size()) {
										parse.getRepeatDatas(null, GR_D33.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(dkTrList.get(i+1).select("table"), GR_D33.class, fieldValueMap, jsonMap);
									}
									if(i+1 < dkTrList.size() && dkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(dkTrList.get(i).text().indexOf("结清") == -1 && dkTrList.get(i).text().indexOf("截至") > -1) {// 未结清
									// 整理出描述信息内容
									context = dkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的", "元（", "，业务号", "到期。截至", "，"};
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									
									String[] fieldNames = null;
									if(context.split("-").length == 12) {
										fieldNames = new String[] {"LoanNo","DistriDate", "LoanInstName", "ContractMoney", "CurrDesc", "LoanType", 
												"BusiNoDesc", "GuarWayDesc", "RepayPeriodDesc", "RepayFreqDesc", "ExpireDate", "StateDeadline"};
									} else {
										fieldNames = new String[] {"LoanNo","DistriDate", "LoanInstName", "ContractMoney", "CurrDesc", "LoanType", 
												"BusiNoDesc", "GuarWayDesc", "RepayFreqDesc", "ExpireDate", "StateDeadline"};
									}
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}
									
									if(i+1 == dkTrList.size()) {
										parse.getRepeatDatas(null, GR_D32.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(dkTrList.get(i+1).select("table"), GR_D32.class, fieldValueMap, jsonMap);
									}
									if(i+1 < dkTrList.size() && dkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else {
									trIndex++;
								}
							}// 循环贷款信息的所有tr
						}// 贷款
						
						// 贷记卡
						if(!djkTrList.isEmpty()) {
							int trIndex = 0;
							String context = null;
							for(int i = trIndex; i < djkTrList.size(); i = trIndex) {
								// 呆账
								if(djkTrList.get(i).text().indexOf("呆账") > -1) {
									context = djkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的贷记卡（", "账户），业务号", "，授信额度", "元，共享授信额度", "。截至", "，账户状态为“", "，余额为", "），业务号", "元，", "元"};
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									String[] fieldNames = new String[] {"LoanNo","CardDate", "CardInstName", "Currency", "BusiNoDesc", "CreditMoney", 
											"ShareAmount", "GuarWayDesc", "StateDeadline", "accountState", "BalanceDesc"};
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}
									
									if(i+1 == djkTrList.size()) {
										parse.getRepeatDatas(null, GR_D41.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(djkTrList.get(i+1).select("table"), GR_D41.class, fieldValueMap, jsonMap);
									}
									if(i+1 < djkTrList.size() && djkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(djkTrList.get(i).text().indexOf("销户") > -1) {// 已销户
									context = djkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的贷记卡（", "），业务号", "，授信额度", "元，共享授信额度", "，截至", "，账户状态为“", "元，"};
								
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									
									String[] fieldNames = new String[] {"LoanNo","CardDate", "CardInstName", "Currency", "BusiNoDesc",
											"CreditMoney", "ShareAmount", "GuarWayDesc", "StateClosMonth", "AccountCancel"};
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == djkTrList.size()) {
										parse.getRepeatDatas(null, GR_D43.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(djkTrList.get(i+1).select("table"), GR_D43.class, fieldValueMap, jsonMap);
									}
									if(i+1 < djkTrList.size() && djkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(djkTrList.get(i).text().indexOf("未激活") > -1) {// 未激活
									context = djkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的贷记卡（", "），业务号", "，授信额度", "元，共享授信额度", "，截至", "，账户状态为“", "元，"};
								
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									
									String[] fieldNames = new String[] {"LoanNo","CardDate", "CardInstName", "Currency", "BusiNoDesc",
											"CreditMoney", "ShareAmount", "GuarWayDesc", "StateDeadline", "NotActivated"};
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == djkTrList.size()) {
										parse.getRepeatDatas(null, GR_D44.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(djkTrList.get(i+1).select("table"), GR_D44.class, fieldValueMap, jsonMap);
									}
									if(i+1 < djkTrList.size() && djkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(djkTrList.get(i).text().indexOf("呆账") == -1 &&
										djkTrList.get(i).text().indexOf("销户") == -1 &&
										djkTrList.get(i).text().indexOf("未激活") == -1 &&
										djkTrList.get(i).text().indexOf("截至") > -1) {// 未销户
									
									context = djkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的贷记卡（", "），业务号", "，授信额度", "元，共享授信额度", "。截至", "元，"};
								
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									String[] fieldNames = new String[] {"LoanNo","CardDate", "CardInstName", "Currency", "BusiNoDesc", 
											"CreditMoney", "ShareAmount", "GuarWayDesc", "StateDeadline"};
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == djkTrList.size()) {
										parse.getRepeatDatas(null, GR_D42.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(djkTrList.get(i+1).select("table"), GR_D42.class, fieldValueMap, jsonMap);
									}
									if(i+1 < djkTrList.size() && djkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else {// 未销户信息结束
									trIndex ++;
								}
							}// 贷记卡相关tr信息循环
						}// 贷记卡
						
						// 准贷记卡
						if(!zdjkTrList.isEmpty()) {
							int trIndex = 0;
							String context = null;
							for(int i = trIndex; i < zdjkTrList.size(); i = trIndex) {
								// 呆账
								if(zdjkTrList.get(i).text().indexOf("呆账") > -1) {
									context = zdjkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的准贷记卡（", "，授信额度", "元，共享授信额度", "。截至", "，账户状态为“", "，余额为", "元"};
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									String[] fieldNames = new String[] {"LoanNo","CardDate", "CardInstName", "Currency", "BusiNoDesc", "CreditMoney", 
											"ShareAmount", "GuarWayDesc", "StateDeadline", "accountState", "BalanceDesc"};
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == zdjkTrList.size()) {										
										parse.getRepeatDatas(null, GR_D51.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(zdjkTrList.get(i+1).select("table"), GR_D51.class, fieldValueMap, jsonMap);
									}
									if(i+1 < zdjkTrList.size() && zdjkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(zdjkTrList.get(i).text().indexOf("销户") > -1) {// 已销户
									context = zdjkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的准贷记卡（", "），业务号", "，授信额度", "元，共享授信额度", "，截至", "，账户状态为“", "元，"};
								
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									
									String[] fieldNames = new String[] {"LoanNo","CardDate", "CardInstName", "Currency", "BusiNoDesc", 
											"CreditMoney", "ShareAmount", "GuarWayDesc", "StateClosMonth", "AccountCancel"};
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == zdjkTrList.size()) {
										parse.getRepeatDatas(null, GR_D53.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(zdjkTrList.get(i+1).select("table"), GR_D53.class, fieldValueMap, jsonMap);
									}
									if(i+1 < zdjkTrList.size() && zdjkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(zdjkTrList.get(i).text().indexOf("未激活") > -1) {// 未激活
									context = zdjkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的准贷记卡（", "），业务号", "，授信额度", "元，共享授信额度", "，截至", "，账户状态为“", "元，"};
								
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									String[] fieldNames = new String[] {"LoanNo","CardDate", "CardInstName", "Currency", "BusiNoDesc", 
											"CreditMoney", "ShareAmount", "GuarWayDesc", "StateDeadline", "NotActivated"};
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == zdjkTrList.size()) {
										parse.getRepeatDatas(null, GR_D54.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(zdjkTrList.get(i+1).select("table"), GR_D54.class, fieldValueMap, jsonMap);
									}
									if(i+1 < zdjkTrList.size() && zdjkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else if(zdjkTrList.get(i).text().indexOf("呆账") == -1 &&
										zdjkTrList.get(i).text().indexOf("销户") == -1 &&
										zdjkTrList.get(i).text().indexOf("未激活") == -1 &&
										(zdjkTrList.get(i).text().indexOf("截至") > -1 ||
										 zdjkTrList.get(i).text().indexOf("截止") > -1)) {// 未销户
									
									context = zdjkTrList.get(i).text();
									context = context.substring(0,context.indexOf("."))+"-"+ context.substring(context.indexOf(".")+1);
									context = context.substring(0, context.indexOf("日")+1)+"-"+context.substring(context.indexOf("日")+1);
									context = parse.filterCharInNumber(context);
									
									String[] excludeStrs = new String[] {"”发放的准贷记卡（", "），业务号", "，授信额度", "元，共享授信额度", "。截至", "。截止", "元，"};
								
									for(String excludeStr : excludeStrs) {
										context = context.replaceAll(excludeStr, "-");
									}
									context = parse.fileterSpecialChar(context);
									String[] fieldNames = new String[] {"LoanNo","CardDate", "CardInstName", "Currency", "BusiNoDesc", 
											"CreditMoney", "ShareAmount", "GuarWayDesc", "StateDeadline"};
									
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									for(int j = 0; j < fieldNames.length; j++) {
										fieldValueMap.put(fieldNames[j], context.split("-")[j]);
									}

									if(i+1 == zdjkTrList.size()) {
										parse.getRepeatDatas(null, GR_D52.class, fieldValueMap, jsonMap);
									} else {
										parse.getRepeatDatas(zdjkTrList.get(i+1).select("table"), GR_D52.class, fieldValueMap, jsonMap);
									}
									if(i+1 < zdjkTrList.size() && zdjkTrList.get(i+1).html().indexOf("table") > -1) {
										trIndex += 2;
									} else {
										trIndex++;
									}
								} else {// 未销户信息结束
									trIndex++;
								}
							}// 贷记卡相关tr信息循环
						}// 准贷记卡
						
						// 担保信息结束
						if(dbxxList != null && !dbxxList.isEmpty()) {
							for(int trIndex = 0; trIndex < dbxxList.size(); trIndex++) {
								tr = dbxxList.get(trIndex);
								// 对外贷款担保信息
								if(tr.text().trim().equals("对外贷款担保信息")) {
									tr = dbxxList.get(trIndex+1);
									if(tr != null && tr.html().indexOf("table") > -1) {
										table = tr.getElementsByTag("table").get(0);
										if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
											parse.getMutilRowTableData(GR_D6.class, table, jsonMap);
										}
									}
								}
							}
						}
						
						infoType_DL = "";
						xdxxmxFlag = true;
					}// 信贷交易信息明细结束

					if("公共信息明细".equals(infoType_DL)) {// 公共信息明细
						tr = parse.getNextTrNode(tr, trList);
						if(tr != null && tr.text().indexOf("欠税记录") > -1) {// 欠税记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMultiTileAndRowTableData(GR_E1.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 欠税记录结束
											
						if(tr != null && tr.text().indexOf("民事判决记录") > -1) {// 民事判决记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMultiTileAndRowTableData(GR_E21.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 民事判决记录结束
											
						if(tr != null && tr.text().indexOf("强制执行记录") > -1) {// 强制执行记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMultiTileAndRowTableData(GR_E22.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 强制执行记录结束
											
						if(tr != null && tr.text().indexOf("行政处罚记录") > -1) {// 行政处罚记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_E3.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 行政处罚记录结束
											
						if(tr != null && tr.text().indexOf("住房公积金参缴记录") > -1) {// 住房公积金参缴记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMultiTileAndRowTableData(GR_E4.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 住房公积金参缴记录结束
											
						if(tr != null && tr.text().indexOf("养老保险金缴存记录") > -1) {// 养老保险金缴存记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMultiTileAndRowTableData(GR_E51.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}
						
						if(tr != null && tr.text().indexOf("养老保险金发放记录") > -1) {// 养老保险金发放记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMultiTileAndRowTableData(GR_E52.class, table, jsonMap);
								}
								
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 养老保险金发放记录结束
											
											
						if(tr != null && tr.text().indexOf("低保救助记录") > -1) {// 低保救助记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_E6.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 低保救助记录结束
										
						if(tr != null && tr.text().indexOf("执业资格记录") > -1) {// 执业资格记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_E7.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 执业资格记录结束
										
						if(tr != null && tr.text().indexOf("行政奖励记录") > -1) {// 行政奖励记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_E8.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 行政奖励记录结束
										
						if(tr != null && tr.text().indexOf("车辆交易和抵押记录") > -1) {// 车辆交易和抵押记录
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_E9.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 车辆交易和抵押记录结束
										
						if(tr != null && tr.text().indexOf("电信缴费记录") > -1) {// 电信缴费记录
							tr = parse.getNextTrNode(tr, trList);
							Element table1 = tr.getElementsByTag("table").get(0);
							
							tr = parse.getNextTrNode(tr, trList);
							Element table2 = tr.getElementsByTag("table").get(0);
							
							if(table1 != null && table2 != null) {
								Elements ftrs = table1.getElementsByTag("tr");
								Elements ltrs = table2.getElementsByTag("tr");
								
								int dataCount = ftrs.size()-1;// 数据的数目
								int colCount = ftrs.get(0).getElementsByTag("td").size()-1;// 字段名称列数
								
								Element curTr = null;
								LinkedList<LinkedHashMap<String, String>> dataList = new LinkedList<LinkedHashMap<String,String>>();
 								for(int i = 1; i <= dataCount; i++) {// 循环行
 									LinkedHashMap<String, String> fieldValueMap = new LinkedHashMap<String, String>();
 									for(int colIndex = 1; colIndex <= colCount; colIndex++) {// 循环列
 										// 获取第一个table中的第i条数据
 										curTr = ftrs.get(i);
 										parse.setFieldValue(GR_E10.class, ftrs.get(0).getElementsByTag("td").get(colIndex).text().trim(),
 												curTr.getElementsByTag("td").get(colIndex).text().trim(), fieldValueMap);
 									}
 									
 									// 获取第二个table中的第i条数据
									curTr = ltrs.get(i);
									parse.setFieldValue(GR_E10.class, ltrs.get(0).getElementsByTag("td").get(1).text().trim(),
											curTr.text().substring(1).trim(), fieldValueMap);
									
 									dataList.add(fieldValueMap);
								}
								jsonMap.put(GR_E10.class.getSimpleName(), dataList);
								
								tr = parse.getNextTrNode(tr, trList);
							}
						}// 电信缴费记录结束
						
						infoType_DL = "";				
					}// 公共信息明细结束
					
					
					if("本人声明".equals(infoType_DL)) {// 声明信息
						tr = parse.getNextTrNode(tr, trList);
						if(tr != null && tr.html().indexOf("table") > -1) {
							table = tr.getElementsByTag("table").get(0);
							if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
								parse.getMutilRowTableData(GR_F1.class, table, jsonMap);
							}
							tr = parse.getNextTrNode(tr, trList);
						}
						infoType_DL = "";
					}// 声明信息结束
							
					if("声明信息".equals(infoType_DL)) {// 声明信息
						tr = parse.getNextTrNode(tr, trList);
						if(tr != null && tr.html().indexOf("table") > -1) {
							table = tr.getElementsByTag("table").get(0);
							if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
								parse.getMutilRowTableData(GR_F2.class, table, jsonMap);
							}
							tr = parse.getNextTrNode(tr, trList);
						}
						infoType_DL = "";
					}// 声明信息结束
					
					if("查询记录".equals(infoType_DL)) {// 查询记录
						tr = parse.getNextTrNode(tr, trList);
						if(tr != null && "查询记录汇总".equals(tr.text())) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
									parse.setFieldValue(GR_G1.class, "最近一个月内的贷款审批的查询机构数", table.getElementsByTag("tr").get(2).child(0).text(), fieldValueMap);
									parse.setFieldValue(GR_G1.class, "最近一个月内的信用卡审批的查询机构数", table.getElementsByTag("tr").get(2).child(1).text(), fieldValueMap);
									parse.setFieldValue(GR_G1.class, "最近一个月内的贷款审批的查询次数", table.getElementsByTag("tr").get(2).child(2).text(), fieldValueMap);
									parse.setFieldValue(GR_G1.class, "最近一个月内的信用卡审批的查询次数", table.getElementsByTag("tr").get(2).child(3).text(), fieldValueMap);
									parse.setFieldValue(GR_G1.class, "最近一个月内的本人查询的查询次数", table.getElementsByTag("tr").get(2).child(4).text(), fieldValueMap);
									parse.setFieldValue(GR_G1.class, "最近两年内的贷后管理查询次数", table.getElementsByTag("tr").get(2).child(5).text(), fieldValueMap);
									parse.setFieldValue(GR_G1.class, "最近两年内的担保资格审查查询次数", table.getElementsByTag("tr").get(2).child(6).text(), fieldValueMap);
									parse.setFieldValue(GR_G1.class, "最近两年内的特约商户实名审查的查询次数", table.getElementsByTag("tr").get(2).child(7).text(), fieldValueMap);
									
									if(!fieldValueMap.isEmpty()) {
										jsonMap.put(GR_G1.class.getSimpleName(), fieldValueMap);
									}
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}
											
						if(tr != null && "信贷审批查询记录明细".equals(tr.text())) {
							tr = parse.getNextTrNode(tr, trList);
							if(tr != null && tr.html().indexOf("table") > -1) {
								table = tr.getElementsByTag("table").get(0);
								if(table != null && table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1) {
									parse.getMutilRowTableData(GR_G2.class, table, jsonMap);
								}
								tr = parse.getNextTrNode(tr, trList);
							}
						}
						infoType_DL = "";
					}// 查询记录结束
					
				}// trList循环结束
			
			}// trList判空结束
		} catch (Exception e) {
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("code", "102");
			errorMap.put("error", "解析HTML文件失败");
			jsonMap.put("errorMap", errorMap) ;
			OtherTool.getInstance().sendDataToDB(jsonMap ,parseLog);
			e.printStackTrace();
			throw new Exception("解析HTML文件失败");
		}
		//return dataMap;
		return jsonMap;
	}
	
	
	/******************************信贷交易信息明细信息的表格数据获取start******************************/
	
	/**
	 * 
	 * @description <p>处理非重复性数据</P>
	 * @createTime 2016-9-14 下午04:21:21
	 * @updateTime 2016-9-14 下午04:21:21
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void getNoRepeatDatas(Element table, Class<?> clazz, Map<String, String> fieldValueMap) throws Exception {
		if(table != null && clazz != null) {
			Elements trList = table.getElementsByTag("tr");
			if(trList != null && !trList.isEmpty()) {
				Element tr = null;
				Elements tdList = null;
				for(int index = 0; index < trList.size(); index++) {
					tr = trList.get(index);
					if(tr != null && !StringUtils.isBlank(tr.text()) &&
							tr.tagName().equals("tr") && !StringUtils.isBlank(tr.html())
							&& tr.html().indexOf("nbsp") == -1) {
						
						if(tr.text().contains("逾期月份") || tr.text().contains("特殊交易类型")
								|| tr.text().contains("贷款机构说明") || tr.text().contains("本人声明")
								|| tr.text().contains("异议标注") || tr.text().contains("透支记录")) {// 排除重复数据行
							break;
						}
						
						if(tr.text().endsWith("还款记录")) {
							parse.setFieldValue(clazz, "贷款起始月", 
									trList.get(index).text().substring(0, tr.text().length()-5).split("-")[0], fieldValueMap);
							
							parse.setFieldValue(clazz, "贷款截止月", 
									trList.get(index).text().substring(0, tr.text().length()-5).split("-")[1], fieldValueMap);
							
							String HKZT = trList.get(index+1).text().replaceAll("[\\s]+", "");
							parse.setFieldValue(clazz, "24个月还款状态", HKZT, fieldValueMap);
							index++;
						} else if(tr.text().endsWith("逾期记录")) {
							parse.setFieldValue(clazz, "逾期起始月", 
									tr.text().substring(0, tr.text().length()-5).split("-")[0], fieldValueMap);
							parse.setFieldValue(clazz, "逾期截止月", 
									tr.text().substring(0, tr.text().length()-5).split("-")[1], fieldValueMap);
							index += parse.getComponetTableDataRowCount(trList, tr)+1;
						} else {
							tdList = tr.getElementsByTag("td");
							if(tdList != null && !tdList.isEmpty()) {
								for(Element td : tdList) {
									setFieldValue(clazz, td.text(), trList.get(index+1).child(tdList.indexOf(td)).text(), fieldValueMap);
								}
								index++;
							}
						}
					}
				}// 循环所有的行
			}
		}
	}
	
	/**
	 * 
	 * @description <p>处理信贷明细信息中的表格的重复记录</P>
	 * @createTime 2016-9-14 下午04:53:05
	 * @updateTime 2016-9-14 下午04:53:05
	 * @author Liutao
	 * @updater Liutao
	 */
	private void getRepeatDatas(Elements tables, Class<?> clazz, Map<String, String> fieldValueMap, Map<String, Object> jsonMap) throws Exception {
		if(tables == null || tables.size() == 0) {
			fieldValueMap.put("ReportNo", parse.reportHeader.getReportNo());

			if(jsonMap.get(clazz.getSimpleName()) != null) {
				((LinkedList<Map<String, String>>) jsonMap.get(clazz.getSimpleName())).add(fieldValueMap);
			} else {
				List<Map<String, String>> mapList = new LinkedList<Map<String,String>>();
				mapList.add(fieldValueMap);
				jsonMap.put(clazz.getSimpleName(), mapList);
			}
		} else if(tables != null && tables.size() > 0 && clazz != null && fieldValueMap != null) {
			Element table = tables.get(0);
			fieldValueMap.put("ReportNo", parse.reportHeader.getReportNo());
			parse.getNoRepeatDatas(table, clazz, fieldValueMap);
			
			Elements trList = table.getElementsByTag("tr");
			if(trList != null && !trList.isEmpty()) {
				//int trIndex = 0;
				int dataRowCount = 0;
				Element tr = null;
				Element otherTable = null;
				String tableStr = "<table><tbody>";
				
				List<Map<String, String>> yqList = new LinkedList<Map<String,String>>();// 逾期数据
				List<Map<String, String>> tzList = new LinkedList<Map<String,String>>();// 透支数据
				List<Map<String, String>> dkjgList = new LinkedList<Map<String,String>>();// 贷款机构说明数据
				List<Map<String, String>> smList = new LinkedList<Map<String,String>>();// 声明数据
				List<Map<String, String>> yybzList = new LinkedList<Map<String,String>>();// 异议标注数据
				List<Map<String, String>> tsjyList = new LinkedList<Map<String,String>>();// 特殊数据
				for(int index = 0; index < trList.size();) {
					dataRowCount = 0;
					tr = trList.get(index);
					if(tr == null || StringUtils.isBlank(tr.text()) &&
							!tr.tagName().equals("tr") || StringUtils.isBlank(tr.html())
							|| tr.html().indexOf("nbsp") > -1) {
						continue;
					}
					dataRowCount = parse.getComponetTableDataRowCount(trList, tr);
					
					if(tr.text().contains("逾期月份") || tr.text().contains("透支月份")) {
						if(dataRowCount > 0) {
							if(tr.text().contains("逾期月份")) {
								yqList = parse.getRepeatDataList(table, trList.indexOf(tr), dataRowCount, clazz, true);
							} else {
								tzList = parse.getRepeatDataList(table, trList.indexOf(tr), dataRowCount, clazz, true);
							}
						}
						index ++;
					} else {
						// 获取数据行数
						if(dataRowCount > 0) {
							tableStr += "<tr>"+tr.html()+"</tr>";
							for(int i = index+1, j=1; j <= dataRowCount; i++, j++) {
								tableStr += trList.get(i).html();
							}
							tableStr += "</tbody></table>";
							
							otherTable = Jsoup.parse(tableStr).getElementsByTag("table").get(0);
							if(tr.text().contains("特殊交易")) {
								tsjyList = parse.getRepeatDataList(otherTable, 0, dataRowCount, clazz, false);
							}
							
							if(tr.text().contains("贷款机构说明")) {
								dkjgList = parse.getRepeatDataList(otherTable, 0, dataRowCount, clazz, false);
							}
							
							if(tr.text().contains("本人声明")) {
								smList = parse.getRepeatDataList(otherTable, 0, dataRowCount, clazz, false);
							}
							
							if(tr.text().contains("异议标注")) {
								yybzList = parse.getRepeatDataList(otherTable, 0, dataRowCount, clazz, false);
								//break;
							}
						}
						
					}
					
					tableStr = "<table><tbody>";
					if(!tsjyList.isEmpty() || !dkjgList.isEmpty() || !smList.isEmpty() || !yybzList.isEmpty()) {
						index = trList.indexOf(tr)+dataRowCount+1;
					} else {
						index++;
					}
				}// 循环tr
			
				// 最终的结果数据存储变量
				List<Map<String, String>> resultList = new LinkedList<Map<String,String>>();
				
				addRepeatData(yqList, resultList);
				addRepeatData(tzList, resultList);
				addRepeatData(tsjyList, resultList);
				addRepeatData(dkjgList, resultList);
				addRepeatData(smList, resultList);
				addRepeatData(yybzList, resultList);

				if(!resultList.isEmpty()) {
					List<Map<String, String>> dataList = new LinkedList<Map<String,String>>();
					dataList.addAll(resultList);
					resultList.clear();
					Map<String, String> tempMap = null;
					for(Map<String, String> map : dataList) {
						if(map != null) {
							tempMap = new LinkedHashMap<String, String>();
							tempMap.putAll(fieldValueMap);
							tempMap.putAll(map);
							resultList.add(tempMap);
						}
					}
					
					if(jsonMap.get(clazz.getSimpleName()) != null) {
						((LinkedList<Map<String, String>>) jsonMap.get(clazz.getSimpleName())).addAll(resultList);
					} else {
						jsonMap.put(clazz.getSimpleName(), resultList);
					}
				} else {
					if(jsonMap.get(clazz.getSimpleName()) != null) {
						((LinkedList<Map<String, String>>) jsonMap.get(clazz.getSimpleName())).add(fieldValueMap);
					} else {
						List<Map<String, String>> mapList = new LinkedList<Map<String,String>>();
						mapList.add(fieldValueMap);
						jsonMap.put(clazz.getSimpleName(), mapList);
					}
				}
			}// tr数组是否为空判断
		}
	}

	/**
	 * 
	 * @description <p>添加重复数据</P>
	 * @createTime 2016-10-13 下午07:18:40
	 * @updateTime 2016-10-13 下午07:18:40
	 * @author Liutao
	 * @updater Liutao
	 */
	private void addRepeatData(List<Map<String, String>> dataMapList, List<Map<String, String>> resultList) {
		if(dataMapList != null && dataMapList != null && !dataMapList.isEmpty()) {
			removeNullValInMap(dataMapList);// 去除空数据
			List<Map<String, String>> temList = new LinkedList<Map<String,String>>();
			if(resultList.isEmpty()) {
				resultList.addAll(dataMapList);
			} else {
				for(Map<String, String> map1 : dataMapList) {
					if(map1 == null || map1.isEmpty()) {
						continue;
					}

					for(Map<String, String> map2 : resultList) {
						if(map2 == null || map2.isEmpty()) {
							continue;
						}
						
						Map<String, String> resultMap = new LinkedHashMap<String, String>();
						resultMap.putAll(map2);
						resultMap.putAll(map1);
						temList.add(resultMap);
					}
				}
				
				resultList.clear();
				resultList.addAll(temList);
				temList = null;
			}
		}
	}
	
	/**
	 * 
	 * @description <p>去除Map中的空数据</P>
	 * @createTime 2016-10-14 下午12:54:05
	 * @updateTime 2016-10-14 下午12:54:05
	 * @author Liutao
	 * @updater Liutao
	 */
	private void removeNullValInMap(List<Map<String, String>> dataMapList) {
		if(dataMapList != null && !dataMapList.isEmpty()) {
			int nullCount = 0;
			List<Map<String, String>> temList = new ArrayList<Map<String,String>>();
			for(Map<String, String> map : dataMapList) {
				nullCount = 0;
				for(Map.Entry<String, String> en : map.entrySet()) {
					if(StringUtils.isBlank(en.getValue()) || en.getValue().equals("--")) {
						nullCount++;
					}
				}
				
				if(nullCount == map.entrySet().size()) {
					temList.add(map);
				}
			}
			
			if(!temList.isEmpty()) {
				dataMapList.removeAll(temList);
			}
		}
	}
	
	
	/**
	 * 
	 * @description <p>获取每一种类型的重复数据</P>
	 * @createTime 2016-9-14 下午05:25:16
	 * @updateTime 2016-9-14 下午05:25:16
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private List<Map<String, String>> getRepeatDataList(Element table, int fieldRowIndex, int dataCount, Class<?> clazz, boolean YQOrTZFlag) throws Exception {
		List<Map<String, String>> fieldValueMapList = null;
		if(table != null && clazz != null) {
			fieldValueMapList = new LinkedList<Map<String,String>>();
			if(YQOrTZFlag) {
				String val = null;
				int fenshu = table.select("tr:eq("+(fieldRowIndex)+") td").size()/3;// 同一行内逾期信息所占区域的份数，因为逾期记录信息可能是在同一行内显示的
				
				// 循环区域数
				for(int areaIndex = 1,tdIndex = 0; areaIndex <= fenshu; areaIndex++,tdIndex += 3) {
					// 循环行数
					for(int dataRowIndex = fieldRowIndex+1; dataRowIndex < fieldRowIndex+dataCount+1; dataRowIndex++) {
						Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
						// 循环列数
						for(int colIndex = tdIndex; colIndex < 3*areaIndex; colIndex++) {
							val = table.select("tr:eq("+dataRowIndex+") td:eq("+colIndex+")").get(0).text();
							if(!StringUtils.isBlank(val)) {
								parse.setFieldValue(clazz, table.select("tr:eq("+(fieldRowIndex)+") td:eq("+colIndex+")").get(0).text(), val, fieldValueMap);
							}
						}
						
						if(!fieldValueMap.isEmpty()) {
							fieldValueMapList.add(fieldValueMap);
						}
					}// 行
				}// 区域数
			} else {
				int colCount = 0;// 表格的列数（即字段数目）
				if(table.children() != null && !table.children().isEmpty()) {
					if(table.children().size() == 1) {
						dataCount = table.children().get(0).children().size()-1;
						colCount = table.children().get(0).children().get(0).children().size();
					}
					
					if(table.children().size() > 1) {
						dataCount = table.children().get(1).children().size()-1;
						colCount = table.children().get(1).children().get(0).children().size();
					}
					
					for(int rowIndex = 1; rowIndex <= dataCount; rowIndex++) {
						Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
						getSingleTableData(clazz, table, 0, rowIndex, 0, colCount, fieldValueMap);
						if(fieldValueMap != null && !fieldValueMap.isEmpty()) {
							fieldValueMapList.add(fieldValueMap);
						}
					}
				}
			}
			
		}
		
		return fieldValueMapList;
	}
	
	/**
	 * 
	 * @description <p>获取复合表格所展示的数据的表格行的总数目</P>
	 * @createTime 2016-9-11 下午05:52:14
	 * @updateTime 2016-9-11 下午05:52:14
	 * @author Liutao
	 * @updater Liutao
	 */
	private int getComponetTableDataRowCount(Elements trList, Element curTr) {
		int dataCount = 0;
		if(trList != null && !trList.isEmpty() && curTr != null) {
			for(int index = trList.indexOf(curTr)+1; index < trList.size(); index++) {
				if(trList.get(index).text().contains("发卡机构说明") || 
						trList.get(index).text().contains("本人声明") || 
						trList.get(index).text().contains("异议标注") || 
						trList.get(index).text().contains("特殊交易类型") ) {
					
					if(curTr.text().endsWith("逾期记录")) {
						dataCount = index - trList.indexOf(curTr)-2;
					} else {
						dataCount = index - trList.indexOf(curTr)-1;
					}
					break;
				} else {
					if(index == trList.size()-1) {
						if(curTr.text().endsWith("逾期记录")) {
							dataCount = index - trList.indexOf(curTr)-1;
						} else {
							dataCount = index - trList.indexOf(curTr);
						}
						//dataCount = index - trList.indexOf(curTr);
					}
				}
			}
		}
		return dataCount;
	}
	
	/******************************信贷交易信息明细信息的表格数据获取end******************************/
	
	/**
	 * 
	 * @description <p>获取报告头信息</P>
	 * @createTime 2016-9-9 下午06:55:41
	 * @updateTime 2016-9-9 下午06:55:41
	 * @author Liutao
	 * @updater Liutao
	 */
	private GR_A1 getReportHeaderInfo(Document document, Map<String, Object> jsonMap) throws Exception {
		GR_A1 reportHeader = null;
		if(document != null) {
			String val = null;
			reportHeader = GR_A1.class.newInstance();
			Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
			
			val = document.select("table:eq(1) tr:eq(2) td:eq(0)").get(0).text();
			if(!StringUtils.isBlank(val)) {
				if("--".equals(val)) {
					fieldValueMap.put("ReportNo", "");
				} else {
					if(val.split(":").length == 2) {
						reportHeader.setReportNo(val.split(":")[1]);
						fieldValueMap.put("ReportNo", val.split(":")[1]);
					} else {
						fieldValueMap.put("ReportNo", "");
					}
				}
			}
			
			int mhIndex = 0;// 第一个冒号下标
			val = document.select("table:eq(1) tr:eq(2) td:eq(1)").get(0).text();
			if(!StringUtils.isBlank(val)) {
				if("--".equals(val)) {
					fieldValueMap.put("QueryRequestTime", "");
				} else {
					mhIndex = val.indexOf(":");
					if(mhIndex < val.length() && mhIndex > -1) {
						reportHeader.setQueryRequestTime(val.substring(mhIndex+1));
						fieldValueMap.put("QueryRequestTime", val.substring(mhIndex+1));
					} else {
						fieldValueMap.put("QueryRequestTime", "");
					}
				}
			}
			
			val = document.select("table:eq(1) tr:eq(2) td:eq(2)").get(0).text();
			if(!StringUtils.isBlank(val)) {
				if("--".equals(val)) {
					fieldValueMap.put("ReportTime", "");
				} else {
					mhIndex = val.indexOf(":");
					if(mhIndex < val.length() && mhIndex > -1) {
						reportHeader.setReportTime(val.substring(mhIndex+1));
						fieldValueMap.put("ReportTime", val.substring(mhIndex+1));
					} else {
						fieldValueMap.put("ReportTime", "");
					}
				}
			}
			
			if(reportHeader != null && fieldValueMap != null && !fieldValueMap.isEmpty()) {
				jsonMap.put(GR_A1.class.getSimpleName(), fieldValueMap);
			}
		}
		return reportHeader;
	}
	
	/**
	 * 
	 * @description <p>获取单行表格数据</P>
	 * @createTime 2016-9-9 下午07:02:14
	 * @updateTime 2016-9-9 下午07:02:14
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void getSingleTableData(Class<?> clazz, Element element, int fieldRowIndex, int fieldValueRowIndex, int colStartIndex, int colCount, Map<String, String> fieldValueMap) throws Exception {
		String fieldDes = null;
		String fieldValue = null;
		if(element != null && fieldValueMap != null && clazz != null) {
			for(int index = colStartIndex; index < colCount; index++) {
				if(element.children() == null || element.children().size() == 0) {
					continue;
				}
				
				if(element.children().size() == 1) {
					fieldDes = element.children().get(0).child(fieldRowIndex).child(index).text();
					fieldValue = element.children().get(0).child(fieldValueRowIndex).child(index).text();
				}

				if(element.children().size() > 1) {
					fieldDes = element.children().get(1).child(fieldRowIndex).child(index).text();
					fieldValue = element.children().get(1).child(fieldValueRowIndex).child(index).text();
				}
				
				if(!StringUtils.isBlank(fieldValue) && !StringUtils.isBlank(fieldDes)) {
					setFieldValue(clazz, fieldDes, fieldValue, fieldValueMap);
				}
			}
		}
		
		if(!fieldValueMap.isEmpty()) {
			fieldValueMap.put("ReportNo", parse.reportHeader.getReportNo());
		}
	}
	
	/**
	 * 
	 * @description <p>获取多行数据表格数据</P>
	 * @createTime 2016-9-10 下午01:25:24
	 * @updateTime 2016-9-10 下午01:25:24
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void getMutilRowTableData(Class<?> clazz, Element table, Map<String, Object> jsonMap) throws Exception {
		if(clazz != null && table != null && jsonMap != null) {
			int colCount = 0;// 表格的列数（即字段数目）
			int dataCount = 0;// 表格的数据数目（出表头以外）
			List<Map<String, String>> fieldValueMapList = new LinkedList<Map<String,String>>();
			
			if(table.children() != null && !table.children().isEmpty()) {
				if(table.children().size() == 1) {
					dataCount = table.children().get(0).children().size()-1;
					colCount = table.children().get(0).children().get(0).children().size();
				}
				
				if(table.children().size() > 1) {
					dataCount = table.children().get(1).children().size()-1;
					colCount = table.children().get(1).children().get(0).children().size();
				}
				
				for(int rowIndex = 1; rowIndex <= dataCount; rowIndex++) {
					Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
					getSingleTableData(clazz, table, 0, rowIndex, 1, colCount, fieldValueMap);
					if(fieldValueMap != null && !fieldValueMap.isEmpty()) {
						fieldValueMapList.add(fieldValueMap);
					}
				}
				
				if(!fieldValueMapList.isEmpty()) {
					jsonMap.put(clazz.getSimpleName(), fieldValueMapList);
				}
			}
			
		}
	}
	
	/**
	 * 
	 * @description <p>存在多行标题以及多条数据的表格的数据的获取</P>
	 * @createTime 2016-9-10 下午03:56:25
	 * @updateTime 2016-9-10 下午03:56:25
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void getMultiTileAndRowTableData(Class<?> clazz, Element table, Map<String, Object> jsonMap) throws Exception {
		if(clazz != null && table != null) {
			// 获取所有的标题行以及数据的条数
			if(table.children() != null && !table.children().isEmpty()) {
				Elements trList = null;
				// 每个标题行的信息(有顺序)
				Map<Integer, Element> titleRowMap = new LinkedHashMap<Integer, Element>();
				
				if(table.children().size() == 1) {
					trList = table.children().get(0).children();
				}
				
				if(table.children().size() > 1) {
					trList = table.children().get(1).children();
				}
				
				if(trList != null && !trList.isEmpty()) {
					for(Element tr : trList) {
						if(tr.text().startsWith("编号")) {
							titleRowMap.put(trList.indexOf(tr), tr);
						}
					}// 循环tr
				}
				
				int dataCount = 0;
				if(!titleRowMap.isEmpty()) {
					dataCount = (trList.size() - titleRowMap.size())/titleRowMap.size();
				}
				
				// 开始循环取数
				List<Map<String, String>> fieldValueMapList = new LinkedList<Map<String,String>>();
				for(int dataRowIndex = 1; dataRowIndex <= dataCount; dataRowIndex++) {// 循环数据条数
					Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
					for(Map.Entry<Integer, Element> titleRow : titleRowMap.entrySet()) {// 循环标题行数目
						for(int colIndex = 1; colIndex < titleRow.getValue().children().size(); colIndex++) {// 循环每一列
							setFieldValue(clazz,titleRow.getValue().children().get(colIndex).text(), 
									table.select("tr:eq("+(titleRow.getKey()+dataRowIndex)+") td:eq("+colIndex+")").text(), fieldValueMap);
						}// 循环每一列
					}// 循环标题行数目结束
					
					if(!fieldValueMap.isEmpty()) {
						fieldValueMap.put("ReportNo", parse.reportHeader.getReportNo());
						fieldValueMapList.add(fieldValueMap);
					}
				}// 循环数据条数结束
				
				if(!fieldValueMapList.isEmpty()) {
					jsonMap.put(clazz.getSimpleName(), fieldValueMapList);
				}
				
			}// 当前表格子节点判空结束

		}// element判空结束
	}
	
	/**
	 * 
	 * @description <p>获取存在跨列情况的表格数据</P>
	 * @createTime 2016-9-10 下午05:46:35
	 * @updateTime 2016-9-10 下午05:46:35
	 * @author Liutao
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @updater Liutao
	 */
	private void getColSpanTableData(Class<?> clazz, Element element, Map<String, Object> jsonMap) throws Exception {
		if(clazz != null && element != null) {
			if(element.children() != null && !element.children().isEmpty()) {
				Elements trList = null;
				if(element.children().size() == 1 && !element.children().get(0).children().isEmpty()) {
					trList = element.children().get(0).children();
				}
				
				if(element.children().size() > 1 && !element.children().get(1).children().isEmpty()) {
					trList = element.children().get(1).children();
				}
				
				int colSpanCount = 0;// 跨列数
				if(trList != null && !trList.isEmpty()) {
					boolean hasVal = false;
					List<Map<String, String>> fieldValueMapList = new LinkedList<Map<String,String>>();
					for(int dataRowIndex = 2; dataRowIndex < trList.size(); dataRowIndex++) {
						Map<String, String> fieldValueMap = new LinkedHashMap<String, String>();
						for(Element td : trList.get(0).children()) {
							colSpanCount += Integer.parseInt(td.attr("colspan"));

							if(trList.get(0).children().indexOf(td) == 0) {
								for(int colIndex = 0; colIndex < colSpanCount; colIndex++) {
									setFieldValue(clazz,
											td.text()+trList.get(1).children().get(colIndex).text(), 
											trList.get(dataRowIndex).children().get(colIndex).text(), fieldValueMap);
									hasVal = true;
								}
							} else {
								for(int colIndex = colSpanCount-Integer.parseInt(td.attr("colspan")); colIndex < colSpanCount; colIndex++) {
									setFieldValue(clazz,
											td.text()+trList.get(1).children().get(colIndex).text(), 
											trList.get(dataRowIndex).children().get(colIndex).text(), fieldValueMap);
									hasVal = true;
								}
							}
						}// 每个跨列
						colSpanCount = 0;
						
						if(hasVal) {
							if(!fieldValueMap.isEmpty()) {
								fieldValueMap.put("ReportNo", parse.reportHeader.getReportNo());
								fieldValueMapList.add(fieldValueMap);
							}
						}
					}// 每一行数据
					
					jsonMap.put(clazz.getSimpleName(), fieldValueMapList);
					
				}
			}
		}
	}
	
	/**
	 * 
	 * @description <p>设置字段值</P>
	 * @createTime 2016-9-9 下午07:28:50
	 * @updateTime 2016-9-9 下午07:28:50
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void setFieldValue(Class<?> clazz, String fieldDes, Object fieldValue, Map<String, String> fieldValueMap) throws Exception {
		if(clazz != null && !StringUtils.isBlank(fieldDes) && fieldValue != null && !StringUtils.isBlank(fieldValue.toString())) {
			fieldDes = fieldDes.replaceAll("[\\s]+", "").replaceAll("－", "-");
			if(fieldValue.equals("--")){
				fieldValue = fieldValue.toString().replaceAll("[\\s -]+", "");
			}else{
				fieldValue = fieldValue.toString().replaceAll("[\\s]+", "");
			}
			IColumn ic = null;
			Method targetMethod = null;
			Object targetMethodValue = null;
			
			String temp = fieldValue.toString();
			Pattern p = Pattern.compile("[\\d]+(,|，)");
			Matcher m = p.matcher(fieldValue.toString());
			String group = null;
			while(m.find()) {
				group = m.group().replace(",", "").replace("，", "");
				temp = temp.replace(m.group(), group);
			}
			if(!StringUtils.isBlank(temp)) {
				fieldValue = temp;
			}
			
			//fieldValue = fieldValue.toString().replaceAll("\"", "'");
			
			Field[] fields = ReflectOperation.getReflectFields(clazz);
			for(Field field : fields) {
				if(field.getName().equals("serialVersionUID")) {
					continue;
				}
				
				ic = field.getAnnotation(IColumn.class);
				if(ic != null && !StringUtils.isBlank(ic.description()) && ic.description().equals(fieldDes)) {
					if(fieldValue == null || StringUtils.isBlank(fieldValue.toString())) {
						fieldValueMap.put(field.getName(), "");
					} else {
						if(!StringUtils.isBlank(ic.tagMethodName())) {
							targetMethod = ReflectOperation.getReflectMethod(clazz, ic.tagMethodName());
							if(targetMethod != null) {
								targetMethodValue = targetMethod.invoke(clazz.newInstance());
								if(targetMethodValue instanceof Map) {
									for(Map.Entry<String, String> en : ((Map<String, String>) targetMethodValue).entrySet()) {
										if(en.getValue().equals(fieldValue.toString())) {
											fieldValueMap.put(field.getName(), en.getValue());
											break;
										}
									}
								}
								break;
							}
						} else {
							if(fieldValue != null && !StringUtils.isBlank(fieldValue.toString())) {
								fieldValueMap.put(field.getName(), fieldValue.toString());
							} else {
								fieldValueMap.put(field.getName(), "");
							}
						}
					}
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * @description <p>过滤特殊字符</P>
	 * @createTime 2016-9-9 下午03:41:37
	 * @updateTime 2016-9-9 下午03:41:37
	 * @author Liutao
	 * @updater Liutao
	 */
	private String fileterSpecialChar(String val) {
		if(!StringUtils.isBlank(val)) {
			String group = "";
			String tempVal = val;
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(tempVal);
			
			while(m.find()) {
				group = m.group();
				if("\"".equals(group)) {
					val = val.replace(group, "'");
				} else {
					val = val.replace(group, "");
				}
			}
			
			val = val.replaceAll("账户", "");
			val = val.replaceAll("业务号", "");
		}
		return val;
	}
	
	/**
	 * 
	 * @description <p>获取下一个非空的tr节点</P>
	 * @createTime 2016-9-10 下午08:41:31
	 * @updateTime 2016-9-10 下午08:41:31
	 * @author Liutao
	 * @updater Liutao
	 */
	private Element getNextTrNode(Element tr, Elements trList) {
		if(tr == null || trList == null || trList.isEmpty()) {
			tr = null;
		} else {
			for(int index = trList.indexOf(tr)+1; index < trList.size(); index++) {
				if(!trList.get(index).tagName().equals("tr") || StringUtils.isBlank(trList.get(index).text()) || trList.get(index).html().indexOf("nbsp") > -1) {
					if(index == trList.size()-1) {// 最后一个元素
						tr = null;
						break;
					}
					continue;
				}
				tr = trList.get(index);
				break;
			}
		}
		return tr;
	}
	
	/**
	 * 
	 * @description <p>获取信息类型标志（大类）</P>
	 * @createTime 2016-9-10 下午08:17:32
	 * @updateTime 2016-9-10 下午08:17:32
	 * @author Liutao
	 * @updater Liutao
	 */
	private String getInfoType(String text) {
		if(!StringUtils.isBlank(text)) {
			boolean findFlag = false;
			for(String infoTypeStr : infoTypeStrs) {
				if(text.indexOf(infoTypeStr) > -1) {
					findFlag = true;
					text = infoTypeStr;
					break;
				}
			}
			
			if(!findFlag) {
				text = "";
			}
		}
		return text;
	}
	
	/**
	 * 
	 * @description <p>去除描述信息中数字中的逗号字符</P>
	 * @createTime 2016-9-12 下午12:24:56
	 * @updateTime 2016-9-12 下午12:24:56
	 * @author Liutao
	 * @updater Liutao
	 */
	private String filterCharInNumber(String context) {
		String temp = null;
		if(!StringUtils.isBlank(context)) {
			temp = context;
			Pattern p = Pattern.compile("[\\d]+(,|，)[\\d]+");
			Matcher m = p.matcher(context);
			String group = null;
			while(m.find()) {
				group = m.group().replace(",", "").replace("，", "");
				temp = temp.replace(m.group(), group);
			}
			
			p = Pattern.compile("[\\d]+(（|\\()");
			m = p.matcher(context);
			while(m.find()) {
				group = m.group().replace("（", "").replace("\\(", "");
				temp = temp.replace(m.group(), group+"-");
			}
			
			int index = temp.indexOf("）");
			temp = temp.substring(0, index)+"-"+temp.substring(index+1);
			
			
			p = Pattern.compile("[\\d]+期(,|，)");
			m = p.matcher(context);
			while (!m.find()) {
				p = Pattern.compile("[\\d]+期");
				m = p.matcher(context);
				break;
			}
			
			while(m.find()) {
				group = m.group()+"-";
				temp = temp.replace(m.group(), group);
			}
		}
		return temp;
	}
	
	
}
