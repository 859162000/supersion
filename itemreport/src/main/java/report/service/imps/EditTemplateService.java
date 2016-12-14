package report.service.imps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.CalculationExampleInfo;
import report.dto.CheckInstance;
import report.dto.CurrencyType;
import report.dto.ItemProperty;
import report.dto.RptInfo;
import coresystem.dto.InstInfo;

import extend.dto.ReportModel_Table;
import extend.dto.Suit;
import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.show.ShowContext;

public class EditTemplateService  extends BaseObjectDaoResultService{

	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		// 取当前RptInfo
		String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
		String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/")+ strTmpRootPath;
		Object oldID = RequestManager.getId();
		RequestManager.setId(RequestManager.getLevelIdValue());
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		RptInfo rptInfoList = (RptInfo)singleObjectFindByIdDao.paramObjectResultExecute(null);
		RequestManager.setId(oldID);
		ArrayList<String> rptInfo = new ArrayList<String>();
		rptInfo.add(rptInfoList.getStrRptCode());
		rptInfo.add(rptInfoList.getStrRptName());
		rptInfo.add(strTmpRootPath + "/" + rptInfoList.getStrRptPath() + ".xls");
		rptInfo.add(rptInfoList.getStrRptPath());
		String strSuitCode =  rptInfoList.getSuit().getStrSuitCode();
		
		IParamObjectResultExecute criteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CalculationExampleInfo.class);
		String calInstance=rptInfoList.getStrCalcInst();
		if(!StringUtils.isBlank(calInstance)){		
			String str1=rptInfoList.getStrCalcInst().contains(",")?rptInfoList.getStrCalcInst().split(",")[0]:rptInfoList.getStrCalcInst();
			detachedCriteria.add(Restrictions.eq("strCalculationName",str1));
			List<CalculationExampleInfo> exampleInfoRuleList= (List<CalculationExampleInfo>)criteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
			if(exampleInfoRuleList.size()>0){
				rptInfo.add(str1);
			}else{
				rptInfo.add("");
			}
		}else{
			rptInfo.add("");
		}
		String checkInstance=rptInfoList.getStrCheckInst();
		if(!StringUtils.isBlank(checkInstance)){	
			detachedCriteria = DetachedCriteria.forClass(CheckInstance.class);
			String str2=rptInfoList.getStrCheckInst().contains(",")?rptInfoList.getStrCheckInst().split(",")[0]:rptInfoList.getStrCheckInst();
			detachedCriteria.add(Restrictions.eq("instanceName",str2));
			List<CheckInstance> exampleInfoRuleList= (List<CheckInstance>)criteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
			if(exampleInfoRuleList.size()>0){
				rptInfo.add(str2);
			}else{
				rptInfo.add("");
			}
		}else{
			rptInfo.add("");
		}

		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) { // 如果文件夹不存在则报错
			this.setServiceResult("fail");
			return;
		}
		
		String fileName = rptInfoList.getStrRptPath();
		int existNum1 = fileName.indexOf("\\");
		int existNum2 = fileName.indexOf("/");
//		int existNum3 = fileName.indexOf(".");
//		if(existNum1>-1 || existNum2>-1 || existNum3>-1){
//			this.setServiceResult("fail");
//			return;
//		}
		if(existNum1>-1 || existNum2>-1){
			this.setServiceResult("fail");
			return;
		}
		file = new File(path +"/"+ rptInfoList.getStrRptPath() + ".xls");
		if(!file.exists()) {
			// 从模板拷贝一个xls文件
			String strTmp = path + "/Template.xls";
			HelpTool.copyFile(new File(strTmp), new File(path +"/"+ rptInfoList.getStrRptPath() + ".xls"));
		}
		
		//查询所有ReportModel_Table
		IParamObjectResultExecute singleObjectFindAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<ReportModel_Table> objectListTable = (List<ReportModel_Table>)singleObjectFindAllDao.paramObjectResultExecute(new Object[]{"extend.dto.ReportModel_Table",null});
		ArrayList<List<String>> tables = new ArrayList<List<String>>();
		for(ReportModel_Table code : objectListTable) {
			ArrayList<String> table = new ArrayList<String>();
			table.add(code.getStrShowTableName());
			table.add(code.getAutoTableID());
			table.add(ReflectOperation.getAutoDTOTName("sessionFactory", code.getStrTableName()));		
			tables.add(table);
		}

		//查询所有Suit
//		IParamObjectResultExecute 
		singleObjectFindAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<Suit> objectListSuit = (List<Suit>)singleObjectFindAllDao.paramObjectResultExecute(new Object[]{"extend.dto.Suit",null});
		ArrayList<List<String>> suits = new ArrayList<List<String>>();
		for(Suit suit : objectListSuit) {
			ArrayList<String> suitList = new ArrayList<String>();
			suitList.add(suit.getStrSuitCode());
			suitList.add(suit.getStrSuitName());
			suits.add(suitList);
		}

		//查询所有currencytype
//		IParamObjectResultExecute 
		singleObjectFindAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<CurrencyType> objectCurrencyTypes = (List<CurrencyType>)singleObjectFindAllDao.paramObjectResultExecute(new Object[]{"report.dto.CurrencyType",null});
		ArrayList<List<String>> currencyTypeList = new ArrayList<List<String>>();
		for(CurrencyType currencyTypes : objectCurrencyTypes) {
			ArrayList<String> currencyType = new ArrayList<String>();
			currencyType.add(currencyTypes.getStrCurrencyCode());
			currencyType.add(currencyTypes.getStrCurrencyName());
			currencyType.add(currencyTypes.getStrAbbrv());
			currencyTypeList.add(currencyType);
		}

		//查询所有itemproperty
//		IParamObjectResultExecute 
		singleObjectFindAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<ItemProperty> itempropertys = (List<ItemProperty>)singleObjectFindAllDao.paramObjectResultExecute(new Object[]{"report.dto.ItemProperty",null});
		ArrayList<List<String>> itempropertyList = new ArrayList<List<String>>();
		for(ItemProperty ItemProperty : itempropertys) {
			ArrayList<String> suitList = new ArrayList<String>();
			suitList.add(ItemProperty.getStrPropertyName());
			suitList.add(ItemProperty.getStrPropertyCode());
			itempropertyList.add(suitList);
		}

		//查询所有instInfo
//		IParamObjectResultExecute 
		singleObjectFindAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<InstInfo> instInfos = (List<InstInfo>)singleObjectFindAllDao.paramObjectResultExecute(new Object[]{"coresystem.dto.InstInfo",null});
		ArrayList<List<String>> instInfoList = new ArrayList<List<String>>();
		for(InstInfo instInfo : instInfos) {
			ArrayList<String> instInfoDto = new ArrayList<String>();
			instInfoDto.add(instInfo.getStrInstCode());
			instInfoDto.add(instInfo.getStrInstName());
			instInfoList.add(instInfoDto);
		}
		
		// 扩展属性1
		ArrayList<List<String>> ext1List = new ArrayList<List<String>>();
		Map<String, String> map1 = ShowContext.getInstance().getShowEntityMap().get("extendProperty1");
		for(Map.Entry<String, String> entry: map1.entrySet()) {
			ArrayList<String> ext1 = new ArrayList<String>();
			ext1.add(entry.getKey());
			ext1.add(entry.getValue());
			ext1List.add(ext1);
		}
		
		// 扩展属性2
		ArrayList<List<String>> ext2List = new ArrayList<List<String>>();
		Map<String, String> map2 = ShowContext.getInstance().getShowEntityMap().get("extendProperty2");
		for(Map.Entry<String, String> entry: map2.entrySet()) {
			ArrayList<String> ext2 = new ArrayList<String>();
			ext2.add(entry.getKey());
			ext2.add(entry.getValue());
			ext2List.add(ext2);
		}
		
		// 频度
		ArrayList<List<String>> freqList = new ArrayList<List<String>>();
		Map<String, String> freqMap = ShowContext.getInstance().getShowEntityMap().get("reportFreq");
		for(Map.Entry<String, String> entry: freqMap.entrySet()) {
			ArrayList<String> ext2 = new ArrayList<String>();
			ext2.add(entry.getKey());
			ext2.add(entry.getValue());
			freqList.add(ext2);
		}
		
		ArrayList Arrs = new ArrayList();
		Arrs.add(rptInfo);
		Arrs.add(tables);
		Arrs.add(suits);
		Arrs.add(currencyTypeList);
		Arrs.add(itempropertyList);
		Arrs.add(instInfoList);
		Arrs.add(ext1List);
		Arrs.add(ext2List);
		Arrs.add(freqList);
		Arrs.add(strSuitCode);
		
		this.setServiceResult(Arrs);
	}
}
