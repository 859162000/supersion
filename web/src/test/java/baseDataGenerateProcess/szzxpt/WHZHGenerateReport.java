package baseDataGenerateProcess.szzxpt;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import extend.dto.SystemParam;
import framework.test.ActionTestCase;
import framework.test.ActionTestUtils;
import framework.test.ExcelUtils;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WHZHGenerateReport extends ActionTestCase{
	private static boolean whFilePathUpdateFlag = false;
	private static boolean whHistoryFilePathUpdateFlag = false;
	private String reportInstInfoFilePath = "ReportInstInfo.xls";
	Map<String,Object> context = new HashMap<String, Object>();
	
	/**
	 * 
	 * <P>点击桌面“报文生成”图标</P>
	 */
	@Test
	public void step00_TestClickDestopIcon() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			context.put("windowId", "WHZHDownload");
			testAction("/framework/ShowList-szzxpt.dto.WHZHDownload.action", ActionTestUtils.getTestDataProvider(), context);
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "szzxpt.dto.WHZHDownload，点击外汇账户“报文生成”桌面图标出错");
		}
	}
	
	/**
	 * 
	 * <P>点击“报文生成”按钮---为选择报送金融机构</P>
	 */
	@Test
	public void step01_TestClickReportGenerateUnselectInst() {
		try {
			Assume.assumeTrue(!ActionTestUtils.checkExceptionFlag());
			
			setWHSystemParam();
			
			int rowIndex = ExcelUtils.getRowNumByFieldName(reportInstInfoFilePath, "ReportInstInfo", "strReportInstCode");
			String jrjgCode = ExcelUtils.getExcelValue(reportInstInfoFilePath, "ReportInstInfo~"+rowIndex+"~1", "~");
			
			context.put("windowId", "WHZHDownload");
			context.put("serviceParam.strJRJGCode", jrjgCode);
			String resultCode = testAction("/framework/ShowList-szzxpt.dto.WHZHDownload.action", ActionTestUtils.getTestDataProvider(), context);
			if(resultCode.equals("success")) {
				initServletMockObjects();
				context.put("download", "1");
				testAction("/framework/DownloadXML-szzxpt.dto.WHZHDownload.action",  ActionTestUtils.getTestDataProvider(), context);
			}
		} catch (Exception e) {
			ActionTestUtils.setTestResultWhenException(e, "szzxpt.dto.WHZHDownload，点击外汇账户“报文生成”功能按钮出错");
		}
	}
	
	/**
	 * 
	 * <P>初始化外汇账户系统参数</P>
	 * @throws Exception 
	 */
	@Test
	public void setWHSystemParam() throws Exception {
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
		dc.add(Restrictions.eq("strItemCode", "whzh_reportFilePath"));
		if(ActionTestUtils.isDataExist(dc)){
			SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
			if(!param.getStrEnable().equals("1")) {
				whFilePathUpdateFlag = true;
				param.setStrEnable("1");
				ActionTestUtils.updateDBData(param);
			}
		} else {
			SystemParam newParam = new SystemParam();
			newParam.setStrEnable("1");
			newParam.setStrItemCode("whzh_reportFilePath");
			newParam.setStrParamValue("外汇账户报文生成路径");
			ActionTestUtils.saveDBData(newParam);
		}
		
		dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
		dc.add(Restrictions.eq("strItemCode", "whzh_SendHistoryFilePath"));
		if(ActionTestUtils.isDataExist(dc)){
			SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
			if(!param.getStrEnable().equals("1")) {
				whHistoryFilePathUpdateFlag = true;
				param.setStrEnable("1");
				ActionTestUtils.updateDBData(param);
			}
		} else {
			SystemParam newParam = new SystemParam();
			newParam.setStrEnable("1");
			newParam.setStrItemCode("whzh_SendHistoryFilePath");
			newParam.setStrParamValue("外汇账户上报报文历史存放路径");
			ActionTestUtils.saveDBData(newParam);
		}
	}
	
	/**
	 * 
	 * <P>测试结束，删除外汇系统参数</P>
	 */
	@AfterClass
	public static void deleteWHSystemParam() {
		try {
			DetachedCriteria dc = null;
			if(!whFilePathUpdateFlag) {// 未被修改过（即：本条数据原本不存在）
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "whzh_reportFilePath"));
				ActionTestUtils.deleteDBData(dc);
			} else {
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "whzh_reportFilePath"));
				SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
				param.setStrEnable("2");
				ActionTestUtils.updateDBData(param);
			}
			
			if(!whHistoryFilePathUpdateFlag) {// 未被修改过（即：本条数据原本不存在）
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "whzh_SendHistoryFilePath"));
				ActionTestUtils.deleteDBData(dc);
			} else {
				dc = DetachedCriteria.forEntityName("extend.dto.SystemParam");
				dc.add(Restrictions.eq("strItemCode", "whzh_SendHistoryFilePath"));
				SystemParam param = (SystemParam) ActionTestUtils.getDBData(dc).get(0);
				param.setStrEnable("2");
				ActionTestUtils.updateDBData(param);
			}
		} catch (Exception e) {
			fail("测试失败，"+e.getMessage());
		}
	}
	
	
	
}
