package bwdrsystem.service.check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstInfo;

import bwdrsystem.helper.ReportContextParse;
import bwdrsystem.dto.ReportContext;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

/**
 * 
 * @description <p>校验导入报文的格式</P>
 * @createTime 2016-8-9 上午11:26:42
 * @updateTime 2016-8-9 上午11:26:42
 * @author Liutao
 * @updater Liutao
 */
public class ReportFileFormatCheck implements ICheck {
	String reportName = "";
	
	@Override
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		checkFile(messageResult);
		return messageResult;
	}
	
	/**
	 * 
	 * @description <p>上传文件是否为空</P>
	 * @createTime 2016-8-9 上午11:29:22
	 * @updateTime 2016-8-9 上午11:29:22
	 * @author Liutao
	 * @updater Liutao
	 */
	private void checkFile(MessageResult messageResult) {
		
		if(RequestManager.getUploadFile() == null) {
			messageResult.setSuccess(false);
			messageResult.getMessageList().add("请选择报文文件");
		} 
		
		if(RequestManager.getId() == null || StringUtils.isBlank(RequestManager.getId().toString())) {
			messageResult.setSuccess(false);
			messageResult.getMessageList().add("请选择机构信息");
		}
		
		
		if(messageResult.isSuccess()) {
			checkReportFileName(messageResult);
			if(messageResult.isSuccess()) {
				checkSingleReportData(messageResult);
			}
		} else {
			messageResult.AlertTranslate();
		}
	}
	
	/**
	 * 
	 * @description <p>校验报文文件名是否正确</P>
	 * @createTime 2016-8-9 上午11:33:33
	 * @updateTime 2016-8-9 上午11:33:33
	 * @author Liutao
	 * @updater Liutao
	 */
	private void checkReportFileName(MessageResult messageResult) {
		try {
			String reportFileName = RequestManager.getReportCheckParam().get("uploadFileFileName");
			if(!reportFileName.endsWith(".txt")) {
				messageResult.setMessage("请选择TXT格式的报文文件");
			} else {
				if(reportFileName.length()-4 == 27) {// 个人征信
					RequestManager.getReportCheckParam().put("zxFlag", "GR");
					List<String> fileType = Arrays.asList(new String[] {"1", "4", "8", "9", "A"});
					if(!fileType.contains(reportFileName.substring(23, 24))) {
						messageResult.getMessageList().add("报文文件名称中，表示报文类型的字符只能是1（个人征信正常报文文件）、4（账户标识变更报文文件）、8（数据删除报文文件）、9（历史逾期记录更新报文文件）、A（历史逾期记录删除报文文件）其中之一");
					} else {
						if(!reportFileName.substring(23, 24).equals("1") && !reportFileName.substring(22, 23).equals("0")) {
							messageResult.getMessageList().add("报文文件名称中，当表示报文类型的字符为1（个人征信正常报文文件）、4（账户标识变更报文文件）、8（数据删除报文文件）、9（历史逾期记录更新报文文件）、A（历史逾期记录删除报文文件）时，报文文件名称第23位字符必须为0");
						} else {
							RequestManager.getReportCheckParam().put("reportType", reportFileName.substring(23, 24));
						}
					}
				} else if(reportFileName.length()-4 == 28) {// 企业
					RequestManager.getReportCheckParam().put("zxFlag", "QY");
					if(reportFileName.substring(26, 27).equals("0") && reportFileName.substring(27, 28).equals("0")) {
						List<String> fileDataType = Arrays.asList(new String[] {"1", "2"});// 报文文件数据类型
						List<String> fileType = Arrays.asList(new String[] {"11", "12", "14", "31"});// 报文文件种类
						
						if(!reportFileName.substring(0, 1).equals("1")) {
							messageResult.getMessageList().add("报文文件名称中，表示应用系统代码的字符必须为1（企业征信系统）");
						}
						
						if(!reportFileName.substring(1, 2).equals("1")) {
							messageResult.getMessageList().add("报文文件名称中，表示机构类型的字符必须为1（拥有金融机构代码机构）");
						}
						
						if(!fileType.contains(reportFileName.substring(19, 21))) {
							messageResult.getMessageList().add("报文文件名称中，表示报文文件种类的字符只能是11（借款人基本信息文件）、12（贷款业务信息文件）、14（不良信贷资产处置信息问文件）、31（批量信贷业务数据删除请求文件）其中之一");
						} else {
							RequestManager.getReportCheckParam().put("reportType", reportFileName.substring(19, 21));
						}
						
						if(!fileDataType.contains(reportFileName.substring(21, 22))) {
							messageResult.getMessageList().add("报文文件名称中，表示报文文件数据类型的字符中能是1（正常数据）和2（重报报文）其中之一");
						}
						
						String reg = "[0-9]{3}[1-9]";
						Pattern p = Pattern.compile(reg);
						if(!p.matcher(reportFileName.substring(22, 26)).matches()) {
							messageResult.getMessageList().add("报文文件名称中，表示序列号的字符的范围只能为0001至0009");
						}
					} else {
						messageResult.setMessage("报文名称倒数第一位（报文类型标识）应当为0（TXT报文），倒数第二位（反馈标识）应当为0（上报报文）");
					}
				} else if(reportFileName.length()-4 == 37) {// 机构
					RequestManager.getReportCheckParam().put("zxFlag", "JG");
					if(reportFileName.substring(35, 36).equals("0") && reportFileName.substring(36, 37).equals("0")) {
						List<String> fileType = Arrays.asList(new String[] {"51", "32"});// 报文文件种类
						
						if(!reportFileName.substring(0, 1).equals("1")) {
							messageResult.getMessageList().add("报文文件名称中，表示应用系统代码的字符必须为1（企业征信系统）");
						}
						
						if(!reportFileName.substring(1, 2).equals("1")) {
							messageResult.getMessageList().add("报文文件名称中，表示机构类型的字符必须为1（拥有金融机构代码机构）");
						}
						
						if(!reportFileName.substring(2, 22).startsWith("000000000")) {
							messageResult.getMessageList().add("报文文件名称中，表示报送机构代码的字符必须以“000000000”开头");
						}
						
						if(!fileType.contains(reportFileName.substring(28, 30))) {
							messageResult.getMessageList().add("报文文件名称中，表示报文文件种类的字符只能是51（机构基本信息采集报文文件）和32（机构基本信息删除报文文件）其中之一");
						} else {
							RequestManager.getReportCheckParam().put("reportType", reportFileName.substring(28, 30));
						}
						
						if(!reportFileName.substring(30, 31).equals("1")) {
							messageResult.getMessageList().add("报文文件名称中，表示报文文件数据类型的字符只能是1（正常数据）");
						}
						
						String reg = "[0-9]{3}[1-9]";
						Pattern p = Pattern.compile(reg);
						if(!p.matcher(reportFileName.substring(31, 35)).matches()) {
							messageResult.getMessageList().add("报文文件名称中，表示序列号的字符的范围只能为0001至0009");
						}
					} else {
						messageResult.setMessage("报文名称倒数第一位（报文类型标识）应当为0（TXT报文），倒数第二位（反馈标识）应当为0（上报报文）");
					}
				} else {
					messageResult.setMessage("报文文件名长度错误");
				}
			}
			
			if(messageResult.getMessageList().size() > 0 || messageResult.getMessageSet().size() > 0) {
				messageResult.AlertTranslate();
			}
			
			if(!StringUtils.isBlank(messageResult.getMessage())) {
				messageResult.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messageResult.setMessage("系统异常");
			messageResult.setSuccess(false);
		}
	}
	

	/**
	 * 
	 * @description <p>校验单个报文数据</P>
	 * @createTime 2016-8-10 下午07:04:01
	 * @updateTime 2016-8-10 下午07:04:01
	 * @author Liutao
	 * @updater Liutao
	 */
	private void checkSingleReportData(MessageResult messageResult) {
		try {
			int nullReportCount = 0;
			List<ReportContext> reportDateList = ReportContextParse.readReportContext();
			String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
			for(ReportContext reportContext : reportDateList) {
				// 记录空报文的数目
				if(reportContext.getBodyList().isEmpty()) {
					nullReportCount++;
					if(reportDateList.size() == nullReportCount) {
						messageResult.setSuccess(false);
						messageResult.setMessage("当前报文文件中的数据全为空报文数据，系统不予导入");
						break;
					} else {
						continue;
					}
				}
				
				if(zxFlag.equals("GR")) {// 个人征信
					checkReportHeader(reportContext, messageResult);
					if(!messageResult.isSuccess()) {
						break;
					}
				} else {
					checkReportHeader(reportContext, messageResult);
					if(messageResult.isSuccess()) {
						checkReportFooter(reportContext, messageResult);
						if(!messageResult.isSuccess()) {
							break;
						}
					} else {
						break;
					}
				}
			}
			
			if(messageResult.isSuccess()) {
				LogicParamManager.setSaveObjectList(reportDateList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messageResult.setMessage("系统异常");
			messageResult.setSuccess(false);
		}
	}
	
	/**
	 * 
	 * @description <p>以GBK的编码格式获取字符串的字节长度</P>
	 * @createTime 2016-8-17 下午04:42:57
	 * @updateTime 2016-8-17 下午04:42:57
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private int getStringByteLength(String str) throws Exception {
		try {
			return str.getBytes("GBK").length;
		} catch (Exception e) {
			throw new Exception("获取字符串长度异常");
		}
	}
	
	/**
	 * 
	 * @description <p>校验报文头是否正确</P>
	 * @createTime 2016-8-10 上午11:08:35
	 * @updateTime 2016-8-10 上午11:08:35
	 * @author Liutao
	 * @throws Exception 
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void checkReportHeader(ReportContext reportContext, MessageResult messageResult) throws Exception {
		String reportType = "";// 报文类型（即：当前报文文件属于哪一种报文文件类型）
		String zxVersions = "";// 征信报文格式版本号
		String JRJGDMCheckResult = "";
		String reportTypeCheckResult = "";
		String header = reportContext.getHeader();
		String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
		String reportFileName = RequestManager.getReportCheckParam().get("uploadFileFileName");
		if(zxFlag.equals("GR")) {
			reportType = reportFileName.substring(23, 24);
			zxVersions = ShowContext.getInstance().getShowEntityMap().get("zxVersion").get("GRZX");
			switch (getStringByteLength(header)) {
				case 147:// 正常报文
					if(!zxVersions.contains(header.substring(0, 3))) {
						messageResult.getMessageList().add("个人征信正常报文文件中存在数据格式版本号不正确的报文数据，填写内容只能是"+zxVersions.replaceAll(",", "、"));
					}
					
					JRJGDMCheckResult = checkReportInstInfo(header.substring(3, 17));
					if(!StringUtils.isBlank(JRJGDMCheckResult)) {
						messageResult.getMessageList().add(JRJGDMCheckResult);
					}
					
					if(!header.substring(34, 35).equals("1")) {
						messageResult.getMessageList().add("个人征信正常报文文件中存在重报提示不为1（非重报报文）的报文数据");
					}
					
					if(!header.substring(35, 36).equals("1")) {
						messageResult.getMessageList().add("个人征信正常报文文件中存在报文类别不为1（正常报文）的报文数据");
					}
					break;
				case 40:// 账户标识变更报文
					if(!zxVersions.contains(header.substring(0, 3))) {
						messageResult.getMessageList().add("个人征信账户标识变更报文文件中存在数据格式版本号不正确的报文数据，填写内容只能是"+zxVersions.replaceAll(",", "、"));
					}
					
					JRJGDMCheckResult = checkReportInstInfo(header.substring(17, 31));
					if(!StringUtils.isBlank(JRJGDMCheckResult)) {
						messageResult.getMessageList().add(JRJGDMCheckResult);
					}
					
					if(!header.substring(31, 32).equals("4")) {
						messageResult.getMessageList().add("个人征信账户标识变更报文文件中存在报文类别不为4（账户标识变更报文）的报文数据");
					}
					break;
				case 125:// 数据删除报文
					if(!zxVersions.contains(header.substring(0, 3))) {
						messageResult.getMessageList().add("个人征信数据删除报文文件中存在数据格式版本号不正确的报文数据，填写内容只能是"+zxVersions.replaceAll(",", "、"));
					}
					
					JRJGDMCheckResult = checkReportInstInfo(header.substring(17, 31));
					if(!StringUtils.isBlank(JRJGDMCheckResult)) {
						messageResult.getMessageList().add(JRJGDMCheckResult);
					}					
					
					if(!header.substring(31, 32).equals("8")) {
						messageResult.getMessageList().add("个人征信数据删除报文文件中存在报文类别不为8（数据删除报文）的报文数据");
					}
					break;
				case 95:// 历史逾期报文
					reportName = ShowContext.getInstance().getShowEntityMap().get("GrReportType").get(reportType);
					if(!header.substring(31, 32).equals("9") && !header.substring(31, 32).equals("A")) {
						messageResult.getMessageList().add("当前报文文件中存在报文类别不为9（历史逾期记录更新报文）或者A（历史逾期记录删除报文）的报文数据");
					} else {
						if(!zxVersions.contains(header.substring(0, 3))) {
							messageResult.getMessageList().add(reportName+"文件中存在数据格式版本号不正确的报文数据，填写内容只能是"+zxVersions.replaceAll(",", "、"));
						}
						
						JRJGDMCheckResult = checkReportInstInfo(header.substring(17, 31));
						if(!StringUtils.isBlank(JRJGDMCheckResult)) {
							messageResult.getMessageList().add(JRJGDMCheckResult);
						}					
					}
					break;
				default:
					messageResult.getMessageList().add("当前报文文件中存在报文头长度错误的报文数据");
					break;
			}
		} else {
			if(zxFlag.equals("QY")) {
				reportType = reportFileName.substring(19, 21);
			}
			
			if(zxFlag.equals("JG")) {
				reportType = reportFileName.substring(28, 30);
			}
			
			if(!StringUtils.isBlank(reportType)) {
				zxVersions = ShowContext.getInstance().getShowEntityMap().get("zxVersion").get("QYZX");
				if(!header.substring(0, 1).equals("A")) {
					messageResult.getMessageList().add("企业征信报文文件的报文头标识必须为A");
				}
				
				// 企业
				if(reportType.equals("11") || reportType.equals("12") || reportType.equals("14") || reportType.equals("31")) {
					if(!reportType.equals("31")) {// 企业征信采集报文
						if(getStringByteLength(header) == 61) {
							if(!zxVersions.contains(header.substring(1, 4))) {
								messageResult.getMessageList().add("企业征信采集报文文件报文格式版本号只能是"+zxVersions.replaceAll(",", "、"));
							}
							
							JRJGDMCheckResult = checkReportInstInfo(header.substring(4, 15));
							if(!StringUtils.isBlank(JRJGDMCheckResult)) {
								messageResult.getMessageList().add(JRJGDMCheckResult);
							}
							
							reportTypeCheckResult = checkBWLB(header.substring(29, 31), reportType, zxFlag);
							if(!StringUtils.isBlank(reportTypeCheckResult)) {
								messageResult.getMessageList().add(reportTypeCheckResult);
							}
						} else {
							messageResult.getMessageList().add("企业征信采集报文文件报文头长度不正确，应该为61");
						}
					} else {// 批量信贷报文
						if(getStringByteLength(header) == 63) {
							if(!zxVersions.contains(header.substring(1, 4))) {
								messageResult.getMessageList().add("企业征信批量信贷业务删除请求报文文件报文格式版本号只能是"+zxVersions.replaceAll(",", "、"));
							}
							
							JRJGDMCheckResult = checkReportInstInfo(header.substring(4, 15));
							if(!StringUtils.isBlank(JRJGDMCheckResult)) {
								messageResult.getMessageList().add(JRJGDMCheckResult);
							}
							
							reportTypeCheckResult = checkBWLB(header.substring(29, 31), reportType, zxFlag);
							if(!StringUtils.isBlank(reportTypeCheckResult)) {
								messageResult.getMessageList().add(reportTypeCheckResult);
							}
						} else {
							messageResult.getMessageList().add("企业征信批量信贷业务删除请求报文文件报文头长度不正确，应该为63");
						}
					}
				}
				
				// 机构
				if(reportType.equals("51") || reportType.equals("32")) {
					if(getStringByteLength(header) == 127) {
						if(!zxVersions.contains(header.substring(1, 4))) {
							messageResult.getMessageList().add(reportFileName+"文件报文格式版本号只能是"+zxVersions.replaceAll(",", "、"));
						}
						
						JRJGDMCheckResult = checkReportInstInfo(header.substring(4, 23));
						if(!StringUtils.isBlank(JRJGDMCheckResult)) {
							messageResult.getMessageList().add(JRJGDMCheckResult);
						}
						
						String BWXXLB = header.substring(38, 40);
						if(!BWXXLB.equals("51") && !BWXXLB.equals("32")) {
							messageResult.getMessageList().add(reportFileName+"文件报文头中报文信息类别必须与企业征信报文文件类型相同，应填写填写"+reportType+"("+reportFileName+")");
						}
						
						reportTypeCheckResult = checkBWLB(header.substring(40, 41), reportType, zxFlag);
						if(!StringUtils.isBlank(reportTypeCheckResult)) {
							messageResult.getMessageList().add(reportTypeCheckResult);
						}
						
						if(!header.substring(41, 42).equals("0") && !header.substring(41, 42).equals("1")) {
							if(!header.substring(41, 42).equals("0")) {
								messageResult.getMessageList().add(reportFileName+"文件报文头中反馈标志只能填写0（非反馈报文）");
							}
						} else {
							if(!header.substring(41, 42).equals("0") ) {
								messageResult.getMessageList().add(reportFileName+"文件报文头中反馈标志只能填写0（非反馈报文）");
							}
						}
					} else {
						messageResult.getMessageList().add(reportFileName+"文件报文头长度不正确，应该为127");
					}
				}
			} else {
				messageResult.getMessageList().add("获取报文类型失败");
			}
		}

		if(!messageResult.getMessageList().isEmpty()) {
			messageResult.AlertTranslate();
			messageResult.setSuccess(false);
		}
	}
	
	/**
	 * 
	 * @description <p>校验报文尾是否正确</P>
	 * @createTime 2016-8-10 上午11:08:35
	 * @updateTime 2016-8-10 上午11:08:35
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void checkReportFooter(ReportContext reportContext, MessageResult messageResult) throws Exception {
		String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
		if(!zxFlag.equals("GR")) {
			if(getStringByteLength(reportContext.getFooter()) != 11) {
				messageResult.setMessage("企业征信报文文件报文尾长度因为11");
			}
		} else {
			if(!StringUtils.isBlank(reportContext.getFooter())) {
				messageResult.setMessage("个人征信报文文件中不能存在报文尾");
			}
		}
		
		if(!StringUtils.isBlank(messageResult.getMessage())) {
			messageResult.setSuccess(false);
		}
	}
	
	
	/**
	 * 
	 * @description <p>检测报文头中报送金融机构是否已经被录入到系统中</P>
	 * @createTime 2016-8-10 下午07:34:52
	 * @updateTime 2016-8-10 下午07:34:52
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private String checkReportInstInfo(String JRJGDM){
		String result = "";
		try {
			String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
			DetachedCriteria dc = DetachedCriteria.forClass(ReportInstInfo.class);
			dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(ReportInstInfo.class).getName(), JRJGDM));
			IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			if(zxFlag.equals("GR")) {
				dc.add(Restrictions.eq("suit.strSuitCode", "22"));
			}
			if(zxFlag.equals("QY")) {
				dc.add(Restrictions.eq("suit.strSuitCode", "21"));
			}
			if(zxFlag.equals("JG")) {
				dc.add(Restrictions.eq("suit.strSuitCode", "24"));
			}
			
			List<ReportInstInfo> reportInstInfoList = (List<ReportInstInfo>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
			if(reportInstInfoList == null || reportInstInfoList.isEmpty()) {
				result = "系统中不存在代码为"+JRJGDM+"的报送机构，请先录入该报送机构";
			} //else {
//				ReportInstInfo reportInstInfo = reportInstInfoList.get(0);
//				if(reportInstInfo.getReportInstSubInfos().isEmpty()) {
//					result = "报送机构（"+JRJGDM+"）未设置任何子机构（即：行内机构或者机构信息）信息";
//				} else {
//					Iterator<ReportInstSubInfo>  it = reportInstInfo.getReportInstSubInfos().iterator();
//					RequestManager.getReportCheckParam().put("instInfoCode", it.next().getInstInfo().getStrInstCode());
//				}
//			}
		} catch (Exception e) {
			result = "系统异常";
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @description <p>报文类别校验</P>
	 * @createTime 2016-8-11 下午03:12:27
	 * @updateTime 2016-8-11 下午03:12:27
	 * @author Liutao
	 * @updater Liutao
	 */
	private String checkBWLB(String BWLB, String reportType, String zxFlag) {
		String result = "";
		String[] typeArr = null;
		List<String> reportTypeList = null;
		Map<String, String> BWLBMap = null;
		Map<String, String> reportTypeMap = null;
		if(zxFlag.equals("GR")) {
			typeArr = new String[] {"1", "4", "8", "9", "A"};
			reportTypeList = Arrays.asList(typeArr);
			reportTypeMap = ShowContext.getInstance().getShowEntityMap().get("GrReportType");
		} 
		
		if(zxFlag.equals("JG")) {
			typeArr = new String[] {"51", "32"};
			reportTypeList = Arrays.asList(typeArr);
			BWLBMap = ShowContext.getInstance().getShowEntityMap().get("JG_BWLX");
			reportTypeMap = ShowContext.getInstance().getShowEntityMap().get("JgReportType");
		}
		
		if(zxFlag.equals("QY")) {
			typeArr = new String[] {"11", "12", "14", "31"};
			reportTypeList = Arrays.asList(typeArr);
			reportTypeMap = ShowContext.getInstance().getShowEntityMap().get("ReportType");
			
			if(!reportType.equals("31")) {
				BWLBMap = ShowContext.getInstance().getShowEntityMap().get("BWLX");
			} else {
				BWLBMap = ShowContext.getInstance().getShowEntityMap().get("PLXDYWSJBWLX");
			}
		}
		
		if(reportTypeList != null) {
			if(reportTypeMap != null && !reportTypeMap.isEmpty()) {
				reportName = reportTypeMap.get(reportType);
				if(zxFlag.equals("GR")) {
					if(!reportType.equals(BWLB)) {
						result = reportName+"文件类型为"+reportTypeMap.get(reportType).split("-")[1]+"（"+reportTypeMap.get(reportType).split("-")[0]+"）时，报文头中的报文类别必须填写与报文名中表示报文类型的字符相同的值";
					}
				} else {
					if(BWLBMap != null && !BWLBMap.isEmpty()) {
						List<String> arr = null;
						if(zxFlag.equals("QY")) {
							if(!reportType.equals("31")) {
								if(reportType.equals("11")) {
									arr = Arrays.asList(new String[] {"01", "02", "03", "04"});
									if(!arr.contains(BWLB)) {
										result = reportName+"文件类型为"+reportTypeMap.get(reportType).split("-")[1]+"（"+reportTypeMap.get(reportType).split("-")[0]+"）时，报文头中的报文类别只能是";
										for(String str : arr) {
											if(arr.indexOf(str) != arr.size()-1) {
												result += BWLBMap.get(str).split("-")[1]+"（"+BWLBMap.get(str).split("-")[0]+"）、";
											} else {
												result += BWLBMap.get(str).split("-")[1]+"（"+BWLBMap.get(str).split("-")[0]+"）其中之一";
											}
										}
									}
								}
								
								if(reportType.equals("12")) {
									arr = new ArrayList<String>();
									for(Integer i = 11; i <= 21; i++) {
										arr.add(i.toString());
									}
									
									if(!arr.contains(BWLB)) {
										result = reportName+"文件类型为"+reportTypeMap.get(reportType).split("-")[1]+"（"+reportTypeMap.get(reportType).split("-")[0]+"）时，报文头中的报文类别只能是";
										for(String str : arr) {
											if(arr.indexOf(str) != arr.size()-1) {
												result += BWLBMap.get(str).split("-")[1]+"（"+BWLBMap.get(str).split("-")[0]+"）、";
											} else {
												result += BWLBMap.get(str).split("-")[1]+"（"+BWLBMap.get(str).split("-")[0]+"）其中之一";
											}
										}
									}
								}
								
								if(reportType.equals("14")) {
									if(!BWLB.equals("61")) {
										result = reportName+"文件类型为"+reportTypeMap.get(reportType).split("-")[1]+"（"+reportTypeMap.get(reportType).split("-")[0]+"）时，报文头中的报文类别只能为"+BWLBMap.get("61").split("-")[1]+"（"+BWLBMap.get("61").split("-")[0]+"）";
									}
								}
							} else {
								if(!BWLB.equals("51")) {
									result = reportName+"文件类型为"+reportTypeMap.get(reportType).split("-")[1]+"（"+reportTypeMap.get(reportType).split("-")[0]+"）时，报文头中的报文类别只能为"+BWLBMap.get("51").split("-")[1]+"（"+BWLBMap.get("51").split("-")[0]+"）";
								}
							}
						}
						
						if(zxFlag.equals("JG")) {
							if(reportType.equals("51")) {
								if(!BWLB.equals("0") && !BWLB.equals("1")) {
									result = reportName+"文件类型为"+reportTypeMap.get(reportType).split("-")[1]+"（51）时，报文头中的报文类别只能是机构基本信息报文（0）和家族成员信息报文（1）其中之一";
								}
							}
							
							if(reportType.equals("32")) {
								if(!BWLB.equals("0") && !BWLB.equals("1")) {
									result = reportName+"文件类型为"+reportTypeMap.get(reportType).split("-")[1]+"（32）时，报文头中的报文类别只能是机构基本信息删除报文（0）和家族成员信息删除报文（1）其中之一";
								}
							}
						}
					} else {
						result = "获取上报报文文件类型对应的报文数据类型失败";
					}
				}
			} else {
				result = "获取系统中定义的上报报文文件类型失败";
			}
		} else {
			result = "未知的上报报文类型";
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
