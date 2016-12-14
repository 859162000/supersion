package bwdrsystem.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstInfo;

import bwdrsystem.dto.ReportContext;

import zxptsystem.service.imps.DownLoadGRZXRportService;
import zxptsystem.service.imps.DownLoadJGXXRportService;
import zxptsystem.service.imps.DownLoadRportService;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.show.ShowContext;

/**
 * 
 * @description <p>
 *              报文文件解析工具
 *              </P>
 * @createTime 2016-8-9 下午03:22:27
 * @updateTime 2016-8-9 下午03:22:27
 * @author Liutao
 * @updater Liutao
 */
public class ReportContextParse {

	/**
	 * 
	 * @description <p>
	 *              读取报文文件中的所有报文信息（包括报文头、报文体、报文尾（个人征信无报文尾））
	 *              </P>
	 * @createTime 2016-8-9 下午03:23:17
	 * @updateTime 2016-8-9 下午03:23:17
	 * @author Liutao
	 * @throws Exception
	 * @updater Liutao
	 */
	public static LinkedList<ReportContext> readReportContext() throws Exception {
		LinkedList<ReportContext> reportList = new LinkedList<ReportContext>();
		try {
			int size = 0;
			List<String> zxVersions = null;
			String line = null;// 报文文件中每一行的内容

			FileInputStream fis = new FileInputStream(RequestManager.getUploadFile());
			InputStreamReader isr = new InputStreamReader(fis, "GBK");
			BufferedReader br = new BufferedReader(isr);

			// 导入文件类型（企业征信报文、机构征信报文、个人征信报文）
			String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
			if (zxFlag.equals("GR")) {// 个人征信
				zxVersions = Arrays.asList(ShowContext.getInstance().getShowEntityMap().get("zxVersion").get("GRZX").split(","));
				while ((line = br.readLine()) != null) {
					if (!StringUtils.isBlank(line)) {
						if (zxVersions.contains(line.substring(0, 3))) {// 报文头
							ReportContext reportContext = new ReportContext(line, null);
							reportList.add(reportContext);
						} else {// 报文体
							size = reportList.size();
							reportList.get(size - 1).getBodyList().add(line);
						}
					}
				}
			} else {// 企业征信或者机构征信
				while ((line = br.readLine()) != null) {
					if (!StringUtils.isBlank(line)) {
						if (line.startsWith("A")) {
							ReportContext reportContext = new ReportContext(line, null);
							reportList.add(reportContext);
						} else {
							size = reportList.size();
							if (line.startsWith("Z")) {
								reportList.get(size - 1).setFooter(line);
							} else {
								reportList.get(size - 1).getBodyList().add(line);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("系统异常");
		}

		return reportList;
	}

	/**
	 * 
	 * @description <p>
	 *              将报文数据解析成相应的DTO数据
	 *              </P>
	 * @createTime 2016-8-11 下午07:52:12
	 * @updateTime 2016-8-11 下午07:52:12
	 * @author Liutao
	 * @throws Exception
	 * @updater Liutao
	 */
	public static Map<String, List<Object>> translateReportToDTO(List<ReportContext> reportList) throws Exception {
		Map<String, List<Object>> saveMap = new HashMap<String, List<Object>>();
		saveMap.put("JC", new ArrayList<Object>());
		saveMap.put("MX", new ArrayList<Object>());
		String dataDTONameStrs = "";// 记录当前的报文文件中对应了哪些DTO
		
		String reportFileTypeName = "";
		try {
			int bodyLength = 0;// 报文体中每个信息记录长度
			int index = 0;// 截取字符串的起始位置
			String db = "";// 段标
			Integer nullReportDataCount = 0;// 空报文数目
			String DTOStr = "";// 每个报文体数据的DTO组成
			Object jcObject = null;// 基础段数据对象
			Map<String, String> reportMap = null;// 每个报文体数据中信息记录类型对应DTO组成情况
			Map<String, String> dbMap = new HashMap<String, String>();// 每个段标对应的DTO名称Map
			String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
			// 当前待解析的报文文件类型
			String curReportFileType = RequestManager.getReportCheckParam().get("reportType");
			
			if(zxFlag.equals("GR")) {
				dbMap.clear();
				jcObject = null;
				reportFileTypeName = ShowContext.getInstance().getShowEntityMap().get("GrReportType").get(curReportFileType);
				DownLoadGRZXRportService downLoadGRZXRportService = (DownLoadGRZXRportService) FrameworkFactory.CreateBean("downLoadGRZXReportService");
				if(curReportFileType.equals("1")) {
					reportMap = downLoadGRZXRportService.getReportMap();
					DTOStr = reportMap.get(curReportFileType);
					for(String str : DTOStr.split(",")) {
						dbMap.put(str.split("%")[0], str.split("%")[1]);
					}
					
					for(ReportContext reportContext : reportList) {
						for(String body : reportContext.getBodyList()) {
							if(StringUtils.isBlank(body)) {
								nullReportDataCount ++;
								continue;
							}
							index = 0;
							bodyLength = body.getBytes("GBK").length;
							while(bodyLength > index) {
								if(index == 0) {
									db = subStringByByte(body, 4, 1);// 基础段
									index = 5;
								} else {
									db = subStringByByte(body, index, 1);//明细段
									index++;
								}
								
								if(dbMap.keySet().contains(db)) {
									if(db.equals("A")) {
										jcObject = setFieldValue(reportContext.getHeader(), body, dbMap.get(db), index, true, null, null);
										saveMap.get("JC").add(jcObject);
										
										if(dataDTONameStrs.indexOf(dbMap.get(db)) == -1) {
											dataDTONameStrs += dbMap.get(db)+",";
										}
									} else {
										saveMap.get("MX").add(setFieldValue(reportContext.getHeader(), body, dbMap.get(db), index, false, jcObject, null));
									}
									
									db = "";
									index = Integer.parseInt(RequestManager.getReportCheckParam().get("startIndex"));
								} else {
									throw new Exception("无效段标");
								}
							}
						}
					}
				} else {
					String dtoName = "";
					if(curReportFileType.equals("4")) {
						dtoName = "zxptsystem.dto.AutoDTO_GRZXZHBSBG";
					} else if(curReportFileType.equals("8")) {
						dtoName = "zxptsystem.dto.AutoDTO_GRZXSJSC";
					} else if(curReportFileType.equals("9")) {
						dtoName = "zxptsystem.dto.AutoDTO_GRZXLSYQJLGX";
					} else {
						dtoName = "zxptsystem.dto.AutoDTO_GRZXLSYQJLSC";
					}
					
					for(ReportContext reportContext : reportList) {
						for(String body : reportContext.getBodyList()) {
							if(StringUtils.isBlank(body)) {
								nullReportDataCount++;
								continue;
							}
							index = 0;
							saveMap.get("JC").add(setFieldValue(reportContext.getHeader(), body, dtoName, index, true, null, null));
						}
					}
					
					if(dataDTONameStrs.indexOf(dtoName) == -1) {
						dataDTONameStrs += dtoName+",";
					}
				}
			}
			
			if(zxFlag.equals("QY")) {// 企业
				dbMap.clear();
				jcObject = null;
				reportFileTypeName = ShowContext.getInstance().getShowEntityMap().get("ReportType").get(curReportFileType);
				DownLoadRportService downLoadReportService = (DownLoadRportService) FrameworkFactory.CreateBean("downLoadReportService");
				reportMap = downLoadReportService.getReportMap();
				Map<String, String> fieldMap = null;
				if(curReportFileType.equals("31")) {
					fieldMap = new LinkedHashMap<String, String>();
					fieldMap.put("strDeleteBusiType", "2");
					fieldMap.put("strJRJGCode", "11");
					fieldMap.put("strDKKCode", "16");
					fieldMap.put("strZHTBH", "60");
				}
				
				for(ReportContext reportContext : reportList) {
					for(String body : reportContext.getBodyList()) {
						if(StringUtils.isBlank(body)) {
							nullReportDataCount ++;
							continue;
						}
						
						index = 0;
						if(!curReportFileType.equals("31")) {
							DTOStr = reportMap.get(body.substring(4, 6));
							for(String str : DTOStr.split(",")) {
								dbMap.put(str.split("%")[0], str.split("%")[1]);
							}
							
							bodyLength = body.getBytes("GBK").length;
							while(bodyLength > index) {
								if(index == 0) {
									db = subStringByByte(body, 6, 1);
									index = 7;
								} else {
									db = subStringByByte(body, index, 1);
									index++;
								}
								
								if(dbMap.keySet().contains(db)) {
									if(db.equals("B")) {
										jcObject = setFieldValue(reportContext.getHeader(), body, dbMap.get(db), index, true, null, null);
										saveMap.get("JC").add(jcObject);
										if(dataDTONameStrs.indexOf(dbMap.get(db)) == -1) {
											dataDTONameStrs += dbMap.get(db)+",";
										}
									} else {
										saveMap.get("MX").add(setFieldValue(reportContext.getHeader(), body, dbMap.get(db), index, false, jcObject, null));
									}
									
									db = "";
									index = Integer.parseInt(RequestManager.getReportCheckParam().get("startIndex"));
								} else {
									throw new Exception("无效段标");
								}
							}
						} else {
							String dtoName = "zxptsystem.dto.AutoDTO_QYZXPLXDYWSJSC";
							jcObject = setFieldValue(reportContext.getHeader(), body, dtoName, index, true, null, fieldMap);
							saveMap.get("JC").add(jcObject);
							if(dataDTONameStrs.indexOf(dtoName) == -1) {
								dataDTONameStrs += dtoName+",";
							}
						}
					}
				}
			}
			
			if(zxFlag.equals("JG")) {// 机构
				jcObject = null;
				String xxjllx = "";
				reportFileTypeName = ShowContext.getInstance().getShowEntityMap().get("JgReportType").get(curReportFileType);
				DownLoadJGXXRportService downLoadJGXXReportService = (DownLoadJGXXRportService) FrameworkFactory.CreateBean("downLoadJGXXReportService");
				reportMap = downLoadJGXXReportService.getReportMap();
				
				for(ReportContext reportContext : reportList) {
					for(String body : reportContext.getBodyList()) {
						if(StringUtils.isBlank(body)) {
							nullReportDataCount ++;
							continue;
						}
						index = 0;
						dbMap.clear();
						if(reportContext.getHeader().substring(40, 41).equals("0")) {
							xxjllx = curReportFileType+"00";
						}
						
						if(reportContext.getHeader().substring(40, 41).equals("1")) {
							xxjllx = curReportFileType+"11";
						}
						
						DTOStr = reportMap.get(xxjllx);
						for(String str : DTOStr.split(",")) {
							dbMap.put(str.split("%")[0], str.split("%")[1]);
						}
						
						bodyLength = body.getBytes("GBK").length;
						while(bodyLength > index) {
							if(index == 0) {
								db = subStringByByte(body, 0, 1);
								index = 1;
							} else {
								db = subStringByByte(body, index, 1);
								index ++;
							}
							if(dbMap.keySet().contains(db)) {
								if(db.equals("B")) {
									jcObject = setFieldValue(reportContext.getHeader(), body, dbMap.get(db), index, true, null, null);
									saveMap.get("JC").add(jcObject);
									if(dataDTONameStrs.indexOf(dbMap.get(db)) == -1) {
										dataDTONameStrs += dbMap.get(db)+",";
									}
								} else {
									saveMap.get("MX").add(setFieldValue(reportContext.getHeader(), body, dbMap.get(db), index, false, jcObject, null));
								}
								
								db = "";
								index = Integer.parseInt(RequestManager.getReportCheckParam().get("startIndex"));
							} else {
								throw new Exception("无效段标");
							}
						}
					}
				}
			}
			
			RequestManager.getReportCheckParam().put("nullReportDataCount", nullReportDataCount.toString());
			if(dataDTONameStrs.lastIndexOf(",") == -1) {
				RequestManager.getReportCheckParam().put("dataDTONames", dataDTONameStrs);
			} else {
				RequestManager.getReportCheckParam().put("dataDTONames", dataDTONameStrs.substring(0, dataDTONameStrs.length()-1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
			Matcher m = p.matcher(e.getMessage());
			if (m.find()) {
				throw new Exception(e.getMessage());
			} else {
				throw new Exception("将当前待解析报文文件（"+reportFileTypeName+"）中的报文数据转换为对应的DTO数据时异常");
			}
		}
		return saveMap;
	}

	/**
	 * 
	 * @description <p>设置字段名称</P>
	 * @createTime 2016-8-12 下午08:55:02
	 * @updateTime 2016-8-12 下午08:55:02
	 * @author Liutao
	 * @throws Exception 
	 * @throws Exception 
	 * @updater Liutao
	 */
	private static Object setFieldValue(String header, String body, String dtoName, Integer index, boolean jcFlag, Object jcObject, Map<String, String> fieldMap) throws Exception {
		Object saveObject = Class.forName(dtoName).newInstance();
		try {
			String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
			if(fieldMap == null || fieldMap.isEmpty()) {// 之所以加这个判断，是因为在系统中，某些DTO并未配置字段在报文中的相应位置信息
				fieldMap = ShowContext.getInstance().getShowEntityMap().get(dtoName);
			}
			
			String HKZTVlaue = null;// 24个月还款状态
			Field HKZTField = null;// 表示24个月还款状态最后一位状态的字段
			for(Entry<String, String> en : fieldMap.entrySet()) {
				if(en.getKey().equals("HKZT")) {
					HKZTVlaue = getetNtypeFieldValue(dtoName, en.getKey(), subStringByByte(body, index, Integer.parseInt(en.getValue())));
					HKZTField = ReflectOperation.getReflectField(Class.forName(dtoName), "HKZT_24");
					if(HKZTField != null) {
						ReflectOperation.setFieldValue(saveObject, HKZTField.getName(), HKZTVlaue.substring(HKZTVlaue.length()-1));
					}
				}
				
				ReflectOperation.setFieldValue(saveObject, en.getKey(), getetNtypeFieldValue(dtoName, en.getKey(), subStringByByte(body, index, Integer.parseInt(en.getValue()))));
				index += Integer.parseInt(en.getValue());
			}
			RequestManager.getReportCheckParam().put("startIndex", index.toString());
			
			if(!jcFlag) {// 明细段值设置
				List<Field> fieldList = ReflectOperation.getJoinColumnFieldList(dtoName);
				for(Field field : fieldList) {
					if(field.getType().equals(jcObject.getClass())) {
						ReflectOperation.setFieldValue(saveObject, field.getName(), jcObject);
						break;
					}
				}
				if(zxFlag.equals("GR")) {
					ReflectOperation.setFieldValue(saveObject, "extend2", ReflectOperation.getFieldValue(jcObject, "JSYHKRQ"));
				}
				
				if(zxFlag.equals("QY")) {
					ReflectOperation.setFieldValue(saveObject, "extend2", ReflectOperation.getFieldValue(jcObject, "YWFSRQ"));
				}

				if(zxFlag.equals("JG")) {
					ReflectOperation.setFieldValue(saveObject, "extend2", ReflectOperation.getFieldValue(jcObject, "XXGXRQ"));
				}
			} else {// 基础段值设置
				if(zxFlag.equals("GR")) {
					if(RequestManager.getReportCheckParam().get("reportType").equals("1")) {
						ReflectOperation.setFieldValue(saveObject, "extend4", subStringByByte(header, 17, 14));
					} else {
						ReflectOperation.setFieldValue(saveObject, "extend4", subStringByByte(header, 3, 14));
					}
				}
				
				if(zxFlag.equals("QY")) {
					ReflectOperation.setFieldValue(saveObject, "extend4", subStringByByte(header, 15, 14));
					ReflectOperation.setFieldValue(saveObject, "extend2", ReflectOperation.getFieldValue(saveObject, "YWFSRQ"));
				}

				if(zxFlag.equals("JG")) {
					ReflectOperation.setFieldValue(saveObject, "extend4", subStringByByte(header, 24, 14));
					ReflectOperation.setFieldValue(saveObject, "extend2", ReflectOperation.getFieldValue(saveObject, "XXGXRQ"));
				}
				
				
				ReflectOperation.setFieldValue(saveObject, "RPTVerifyType", "1");
				ReflectOperation.setFieldValue(saveObject, "RPTSubmitStatus", "1");
			}
			ReflectOperation.setFieldValue(saveObject, "extend1", RequestManager.getReportCheckParam().get("uploadFileFileName"));
			ReflectOperation.setFieldValue(saveObject, "extend3", "");
			ReflectOperation.setFieldValue(saveObject, "extend5", "");
			ReflectOperation.setFieldValue(saveObject, "RPTCheckType", "1");
			ReflectOperation.setFieldValue(saveObject, "RPTSendType", "1");
			ReflectOperation.setFieldValue(saveObject, "RPTFeedbackType", "1");
			ReflectOperation.setFieldValue(saveObject, ReflectOperation.getPrimaryKeyField(dtoName).getName(), UUID.randomUUID().toString().replaceAll("-", "").trim());

			Field field = ReflectOperation.getReflectField(saveObject.getClass(), "lastUpdateDate");
			if(field != null) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ReflectOperation.setFieldValue(saveObject, "lastUpdateDate", sdf.format(date));
			}
			
			field = ReflectOperation.getReflectField(saveObject.getClass(), "operationUse");
			if(field != null) {
				ReflectOperation.setFieldValue(saveObject, "lastUpdateDate", SecurityContext.getInstance().getLoginInfo().getTag());
			}
			
			field = ReflectOperation.getReflectField(saveObject.getClass(), "dtDate");
			if(field != null) {
				ReflectOperation.setFieldValue(saveObject, "dtDate", RequestManager.getReportCheckParam().get("dtDate"));
			}
			
			field = ReflectOperation.getReflectField(saveObject.getClass(), "instInfo");
			if(field != null) {
				String reportInstInfoCode = null;
				DetachedCriteria dc = DetachedCriteria.forClass(ReportInstInfo.class);
				String reportFileName = RequestManager.getReportCheckParam().get("uploadFileFileName");
				if(zxFlag.equals("GR")) {
					reportInstInfoCode = reportFileName.substring(0, 14);
				}
				
				if(zxFlag.equals("QY")) {
					reportInstInfoCode = reportFileName.substring(2, 13);
				}
				
				if(zxFlag.equals("JG")) {
					reportInstInfoCode = reportFileName.substring(2, 22).replaceAll("000000000", "").trim();
				}
				dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(ReportInstInfo.class).getName(), reportInstInfoCode));
				IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				List<ReportInstInfo> reportInstInfoList = (List<ReportInstInfo>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
				ReflectOperation.setFieldValue(saveObject, "instInfo", reportInstInfoList.get(0).getReportInstSubInfos().iterator().next().getInstInfo());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("转换报文数据为DTO对象数据时，设置字段值异常");
		}
		return saveObject;
	}
	
	/**
	 * 
	 * @throws UnsupportedEncodingException 
	 * @description <p>按照字节获取字符串子串</P>
	 */
	private static String subStringByByte(String str,int start, int subLen) throws UnsupportedEncodingException {
        String result = null;
        if (str != null) {
            byte[] a = str.getBytes("GBK");
            if (a.length <= subLen) {
                result = str;
            } else if (subLen > 0) {
            	result = new String(a, start, subLen, "GBK");
            }
        }
        return result;
    }
	
	/**
	 * 
	 * @description <p>转换字段值，取出多余的空格和0，如果字段值是代码型数据项（即：有targetMethod属性），将不会去除多余的0</P>
	 * @createTime 2016-8-17 下午06:14:55
	 * @updateTime 2016-8-17 下午06:14:55
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private static String getetNtypeFieldValue(String dtoName, String fieldName, String fieldValue) throws Exception {
		if(!StringUtils.isBlank(fieldValue)) {
			Pattern p = null;
			Matcher m = null;
			// 获取N型字段Map
			Map<String, String> NTypeFieldMap = ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field");
			// 获取N型金额字段Map
			Map<String, String> NTypeFieldJEMap = ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field_ByJE");
			
			String NTypeFields = NTypeFieldMap.get(dtoName);// N型字段
			String NTypeJEFields = NTypeFieldJEMap.get(dtoName);// N型金额型字段
			
			// 处理N型和N型金额型数据
			if(!StringUtils.isBlank(NTypeFields) || !StringUtils.isBlank(NTypeJEFields)) {
				if((NTypeFields != null && NTypeFields.indexOf(fieldName) == -1) || (NTypeJEFields != null && NTypeJEFields.indexOf(fieldName) == -1)) {
					p = Pattern.compile("[0]{"+fieldValue.length()+"}");
					m = p.matcher(fieldValue);
					if(m.matches()) {
						return "0";
					} else {
						return fieldValue.trim();
					}
				} else {
					String group = "";
					p = Pattern.compile("[1-9]");
					m = p.matcher(fieldValue);
					while(m.find()) {
						group = m.group();
						break;
					}

					if(!StringUtils.isBlank(group)) {
						int index = fieldValue.indexOf(group);
						return fieldValue.substring(index);
					} else {
						return "0";
					}
				}
			} else {
				return fieldValue.trim();
			}
		} else {
			return "";
		}
	}
	
	
}
