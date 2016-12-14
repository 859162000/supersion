package baseDataGenerateProcess_zxptsystem.zxptsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import extend.dto.SystemParam;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.ExcelUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JGZXReportGenerate extends ActionTestCase {
	static boolean zxFilePathUpdateFlag = false;
	static boolean zxVersionUpdateFlag = false;
	String dataFileName = "ZXGenerateReport.xls";
	static List<String> reportTypeList = new ArrayList<String>();
	Map<String,Object> context = new HashMap<String, Object>();
	
	/**
	 * 点击“个人征信报送-报文生成”功能图标
	 */
	@Test
	public void step00_TestClickReportGenerateIcon(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			testAction("/framework/ShowList-zxptsystem.dto.JGXXDownload.action", "");
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
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.JGXXDownload");
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
					context.put("serviceParam.strJgReportType", reportType);
					testAction("/framework/ShowList-zxptsystem.dto.JGXXDownload.action", ActionTestUtils.getTestDataProvider(), context);
				}
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.JGXXDownload");
		}
	}
	
	/**
	 * 生成征信所需要的系统参数数据
	 */
	@Test
	public void step02_SetZXSystemParam() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			DetachedCriteria dc = null;
			dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
			dc.add(Restrictions.eq("strItemCode", "jgzx_reportFilePath"));
			if(ActionTestUtils.isDataExist(dc)) {
				try {
					SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
					if(!param.getStrEnable().equals("1")) {// 不可用，改为可用
						zxFilePathUpdateFlag = true;
						param.setStrEnable("1");
						ActionTestUtils.updateDBData(param);
					}
				} catch (Exception e) {
					Assert.fail("测试失败"+e.getMessage());
				} finally {
					dc = null;
				}
			} else {
				// 创建企业征信报文文件保存路径
				ActionTestUtils.createFilePath(System.getProperty("java.io.tmpdir")+"JGZXReportFile");
				
				SystemParam newParam = new SystemParam();
				newParam.setStrEnable("1");
				newParam.setStrItemCode("jgzx_reportFilePath");
				newParam.setStrParamValue(System.getProperty("java.io.tmpdir")+"JGZXReportFile");
				ActionTestUtils.saveDBData(newParam);
			}
			
			dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
			dc.add(Restrictions.eq("strItemCode", "jgzx_strVersion"));
			if(ActionTestUtils.isDataExist(dc)){
				try {
					SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
					if(!param.getStrEnable().equals("1")) {
						zxVersionUpdateFlag = true;
						param.setStrEnable("1");
						ActionTestUtils.updateDBData(param);
					}
				} catch (Exception e) {
					Assert.fail("测试失败"+e.getMessage());
				} finally {
					dc = null;
				}
			} else {
				SystemParam newParam = new SystemParam();
				newParam.setStrEnable("1");
				newParam.setStrItemCode("jgzx_strVersion");
				newParam.setStrParamValue("3.0");
				ActionTestUtils.saveDBData(newParam);
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.JGXXDownload");
		}
	}
	
	/**
	 * 51-机构基本信息采集报文
	 */
	@Test
	public void step03_TestGenerateReport_JGJBXXCJBW(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(reportTypeList != null && reportTypeList.size() > 0){
				context.put("serviceParam.strJgReportType", reportTypeList.get(0));
				context.put("serviceParam.strJgJRJGCode",  ExcelUtils.getExcelValue("ReportInstInfo.xls", "0~0~1", "~"));
				context.put("serviceParam.XXGXRQ", getXXGXRQ());
				testAction("/ajaxForReport/GetJsonForDownLoadReport-zxptsystem.dto.JGXXDownload.action", ActionTestUtils.getTestDataProvider(), context);
				initServletMockObjects();
				context.put("download", "1");
				testAction("/framework/DownLoadRport-zxptsystem.dto.JGXXDownload.action", ActionTestUtils.getTestDataProvider(), context);
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.JGXXDownload");
		}
	}
	
	/**
	 * 32-机构基本信息删除报文
	 */
	@Test
	public void step04_TestGenerateReport_JGJBXXSCBW(){
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			if(reportTypeList != null && reportTypeList.size() > 0){
				context.put("serviceParam.strJgReportType", reportTypeList.get(0));
				context.put("serviceParam.strJgJRJGCode",  ExcelUtils.getExcelValue(dataFileName, "1~0~1", "~"));
				context.put("serviceParam.XXGXRQ", getXXGXRQ());
				testAction("/ajaxForReport/GetJsonForDownLoadReport-zxptsystem.dto.JGXXDownload.action", ActionTestUtils.getTestDataProvider(), context);
				initServletMockObjects();
				context.put("download", "1");
				testAction("/framework/DownLoadRport-zxptsystem.dto.JGXXDownload.action", ActionTestUtils.getTestDataProvider(), context);
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "zxptsystem.dto.JGXXDownload");
		}
	}
	
	
	private String getXXGXRQ(){
		String XXGXRQ = ExcelUtils.getExcelValue("TaskFlow.xls", "0~2~1", "~");
		if(!StringUtils.isBlank(XXGXRQ)) {
			XXGXRQ.replaceAll("-", "");
		}
		return XXGXRQ;
	}
	
	/**
	 * 删除征信所需要的数据
	 */
	@AfterClass
	public static void deleteZXSystemParam() {
		try {
			DetachedCriteria dc = null;
			if(!zxFilePathUpdateFlag) {// 未被修改过（即：本条数据原本不存在）
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "jgzx_reportFilePath"));
				ActionTestUtils.deleteDBData(dc);
			} else {
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "jgzx_reportFilePath"));
				SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
				param.setStrEnable("2");
				ActionTestUtils.updateDBData(param);
			}
			
			if(!zxVersionUpdateFlag) {// 未被修改过（即：本条数据原本不存在）
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "jgzx_strVersion"));
				ActionTestUtils.deleteDBData(dc);
			} else {
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "jgzx_strVersion"));
				SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
				param.setStrEnable("2");
				ActionTestUtils.updateDBData(param);
			}
		} catch (Exception e) {
			fail("测试失败，"+e.getMessage());
		}
	}
	
	
}
