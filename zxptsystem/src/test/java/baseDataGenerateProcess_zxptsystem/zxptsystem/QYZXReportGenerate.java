package baseDataGenerateProcess_zxptsystem.zxptsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import extend.dto.SystemParam;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.ExcelUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QYZXReportGenerate extends ActionTestCase {
	static boolean zxFilePathUpdateFlag = false;
	static boolean zxVersionUpdateFlag = false;
	static List<String> reportTypeList = new ArrayList<String>();
	Map<String,Object> context = new HashMap<String, Object>();
	
	/**
	 * 点击“企业征信报送-报文生成”功能图标
	 */
	@Test
	public void step00_TestClickReportGenerateIcon(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/ShowList-zxptsystem.dto.QYZXDownload.action", "");
			ShowList showList = (ShowList) LogicParamManager.getServiceResult();
			if(showList != null){
				List<ShowFieldCondition> showFieldConditionList = showList.getShowCondition();
				if(showFieldConditionList != null && showFieldConditionList.size() > 0){
					String fieldName = "";
					Map<String, String> tag = null;
					for(ShowFieldCondition fieldCondition : showFieldConditionList){
						fieldName = fieldCondition.getFieldName();
						if(!StringUtils.isBlank(fieldName) && fieldName.equals("strReportType")){
							tag = (Map<String, String>) fieldCondition.getTag();
							for(Map.Entry<String, String> en : tag.entrySet()){
								reportTypeList.add(en.getKey());
							}
							break;
						}
					}
				}
 			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.QYZXDownload");
		}
	}
	
	/**
	 * 改变报文文件类型下拉框的值，测试获取数据的功能是否正常
	 */
	@Test
	public void step01_TestChangeSelectValue(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(reportTypeList != null && reportTypeList.size() > 0){
				for(String reportType : reportTypeList){
					context.put("serviceParam.strReportType", reportType);
					testAction("/framework/ShowList-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.QYZXDownload");
		}
	}
	
	/**
	 * 生成报文生成所需要的系统参数
	 */
	@Test
	@Rollback(false)
	public void step02_TestSetZXSystemParam() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			setZXSytemParam();
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "extend.dto.SystemParam");
		}
	}
	
	
	/**
	 * 点击 “生成报文”按钮
	 * 报文类型：11-借款人基本信息文件
	 */
	@Test
	@Rollback(false)
	public void step03_TestGenerateReport_JKRJBXX(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(reportTypeList != null && reportTypeList.size() > 0){
				context.put("serviceParam.strReportType", reportTypeList.get(0));
				context.put("serviceParam.strJRJGCode",  ExcelUtils.getExcelValue("ReportInstInfo.xls", "0~0~1", "~"));
				context.put("serviceParam.YWFSRQ", ExcelUtils.getExcelValue("TaskFlow.xls", "0~2~1", "~"));
				testAction("/ajaxForReport/GetJsonForDownLoadReport-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
				initServletMockObjects();
				context.put("download", "1");
				testAction("/framework/DownLoadRport-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.QYZXDownload");
		}
	}
	
	/**
	 * 点击 “生成报文”按钮
	 * 报文类型：12-信贷业务信息文件
	 */
	@Test
	@Rollback(false)
	public void step04_TestGenerateReport_XDYWXX(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(reportTypeList != null && reportTypeList.size() > 0){
				context.put("serviceParam.strReportType", reportTypeList.get(1));
				context.put("serviceParam.strJRJGCode",  ExcelUtils.getExcelValue("ReportInstInfo.xls", "0~0~1", "~"));
				context.put("serviceParam.YWFSRQ", ExcelUtils.getExcelValue("TaskFlow.xls", "0~2~1", "~"));
				testAction("/ajaxForReport/GetJsonForDownLoadReport-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
				initServletMockObjects();
				context.put("download", "1");
				testAction("/framework/DownLoadRport-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.QYZXDownload");
		}
	}
	
	/**
	 * 点击 “生成报文”按钮
	 * 报文类型：14-不良信贷资产处置信息文件
	 */
	@Test
	@Rollback(false)
	public void step05_TestGenerateReport_BLXDZCCZXX(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(reportTypeList != null && reportTypeList.size() > 0){
				context.put("serviceParam.strReportType", reportTypeList.get(2));
				context.put("serviceParam.strJRJGCode",  ExcelUtils.getExcelValue("ReportInstInfo.xls", "0~0~1", "~"));
				context.put("serviceParam.YWFSRQ", ExcelUtils.getExcelValue("TaskFlow.xls", "0~2~1", "~"));
				testAction("/ajaxForReport/GetJsonForDownLoadReport-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
				initServletMockObjects();
				context.put("download", "1");
				testAction("/framework/DownLoadRport-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.QYZXDownload");
		}
	}
	
	/**
	 * 点击 “生成报文”按钮
	 * 报文类型：31-批量信贷业务数据删除请求文件
	 */
	@Test
	@Rollback(false)
	public void step06_TestGenerateReport_PLXDYWSJSCQQ(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(reportTypeList != null && reportTypeList.size() > 0){
				context.put("serviceParam.strReportType", reportTypeList.get(3));
				context.put("serviceParam.strJRJGCode", ExcelUtils.getExcelValue("ReportInstInfo.xls", "0~0~1", "~"));
				context.put("serviceParam.YWFSRQ", ExcelUtils.getExcelValue("TaskFlow.xls", "0~2~1", "~"));
				testAction("/ajaxForReport/GetJsonForDownLoadReport-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
				initServletMockObjects();
				context.put("download", "1");
				testAction("/framework/DownLoadRport-zxptsystem.dto.QYZXDownload.action", ActionTestUtils.getTestDataProvider(), context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.QYZXDownload");
		}
	}
	
	/**
	 * 设置征信系统参数
	 * @throws Exception 
	 */
	private void setZXSytemParam() throws Exception {
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
		dc.add(Restrictions.eq("strItemCode", "qyzx_reportFilePath"));
		if(ActionTestUtils.isDataExist(dc)) {
			SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
			if(!param.getStrEnable().equals("1")) {// 不可用，改为可用
				zxFilePathUpdateFlag = true;
				param.setStrEnable("1");
				ActionTestUtils.updateDBData(param);
			}
		} else {
			// 创建企业征信报文文件保存路径
			ActionTestUtils.createFilePath(System.getProperty("java.io.tmpdir")+"QYZXReportFile");
			
			SystemParam newParam = new SystemParam();
			newParam.setStrEnable("1");
			newParam.setStrItemCode("qyzx_reportFilePath");
			newParam.setStrParamValue(System.getProperty("java.io.tmpdir")+"QYZXReportFile");
			ActionTestUtils.saveDBData(newParam);
		}
		
		dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
		dc.add(Restrictions.eq("strItemCode", "qyzx_strVersion"));
		if(ActionTestUtils.isDataExist(dc)){
			SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
			if(!param.getStrEnable().equals("1")) {
				zxVersionUpdateFlag = true;
				param.setStrEnable("1");
				ActionTestUtils.updateDBData(param);
			}
		} else {
			SystemParam newParam = new SystemParam();
			newParam.setStrEnable("1");
			newParam.setStrItemCode("qyzx_strVersion");
			newParam.setStrParamValue("3.0");
			ActionTestUtils.saveDBData(newParam);
		}
	}
	
	/**
	 * 删除征信系统参数
	 * @throws Exception
	 */
	@AfterClass
	public static void deleteZXSystemParam() {
		try {
			DetachedCriteria dc = null;
			if(!zxFilePathUpdateFlag) {// 未被修改过（即：本条数据原本不存在）
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "qyzx_reportFilePath"));
				ActionTestUtils.deleteDBData(dc);
			} else {
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "qyzx_reportFilePath"));
				SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
				param.setStrEnable("2");
				ActionTestUtils.updateDBData(param);
			}
			
			if(!zxVersionUpdateFlag) {// 未被修改过（即：本条数据原本不存在）
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "qyzx_strVersion"));
				ActionTestUtils.deleteDBData(dc);
			} else {
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "qyzx_strVersion"));
				SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
				param.setStrEnable("2");
				ActionTestUtils.updateDBData(param);
			}
		} catch (Exception e) {
			fail("测试失败，"+e.getMessage());
		}
	}
	
	
	
	
}
