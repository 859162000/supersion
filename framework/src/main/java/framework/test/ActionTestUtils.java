package framework.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;

import framework.dao.imps.SingleObjectDeleteByCriteriaDao;
import framework.dao.imps.SingleObjectFindByCriteriaDao;
import framework.dao.imps.SingleObjectSaveDao;
import framework.dao.imps.SingleObjectUpdateDao;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.security.LoginInfo;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowPage;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowValue;
import framework.helper.TypeParse;

public class ActionTestUtils {
	
	/******************************TestDataProvider实例化start******************************/
	
	/**
	 * 实例化接口对象TestDataProvider
	 * @return TestDataProvider
	 */
	public static TestDataProvider getTestDataProvider(){
		return new TestDataProvider() {
			@Override
			public Map<String, String> getData(Map<String, Object> context) {
				Map<String, String> datas = null;
				if(context != null){
					datas = new HashMap<String, String>();
					for(Map.Entry<String, Object> en : context.entrySet()){
						datas.put(en.getKey(), en.getValue().toString());
					}
				}
				context.clear();
				return datas;
			}
		};
	}
	
	/******************************TestDataProvider实例化end******************************/
	
//	/**
//	 * 根据DTO名称（参数excel文件中第一个sheet中定义的需要参与测试的DTO）获取sheet（以类名命名）页名称
//	 * @param dtoNameList
//	 * @return
//	 */
//	public static List<String> getSheetInfoList(List<String> dtoNameList){
//		List<String> sheetInfoList = null;
//		if(dtoNameList != null && dtoNameList.size() > 0){
//			int index = 0;
//			sheetInfoList = new ArrayList<String>();
//			for(String dtoName : dtoNameList){
//				index = dtoName.lastIndexOf(".")+1;
//				sheetInfoList.add(dtoName.substring(index));
//			}
//		}
//		return sheetInfoList;
//	}

	/******************************获取excel的相关数据start******************************/
	
	/**
	 * 根据DTO名称获取相应的EXCEL参数文件名称（前提条件：Excel文件和sheet页以DTO名称命名才有效）
	 * @param dtoName DTO名称
	 * @param sheetFlag 是否是获取sheet名称标识（false：获取Excel文件名称）
	 * @return Excel参数文件名称
	 */
	public static String getExcelFileOrSheetName(String dtoName, boolean sheetFlag) {
		String name = null;
		if(!StringUtils.isBlank(dtoName)){
			int index = dtoName.lastIndexOf(".")+1;
			name = dtoName.substring(index);
			if(!sheetFlag){
				name += ".xls";
			}
		}
		return name;
	}
	
	/**
	 * 根据DTO名称获取相应的EXCEL参数文件名称（前提条件：Excel文件和sheet页以DTO名称命名才有效）
	 * @param dtoName DTO名称
	 * * @param sheetFlag 是否是获取sheet名称标识（false：获取Excel文件名称)
	 * @return Excel参数文件名称
	 */
	public static List<String> getExcelFileOrSheetName(List<String> dtoNameList, boolean sheetFlag) {
		List<String> nameList = null;
		if(dtoNameList != null && dtoNameList.size() > 0){
			int index = 0;
			String name = "";
			nameList = new ArrayList<String>();
			for(String dtoName : dtoNameList){
				index = dtoName.lastIndexOf(".")+1;
				name = dtoName.substring(index);
				if(!sheetFlag){
					name += ".xls";
				}
				nameList.add(name);
			}
		}
		return nameList;
	}
	
	/******************************获取excel的相关数据很多end******************************/
	
	
	
	/******************************时间相关操作start******************************/
	
	/**
	 * 转换日期格式
	 * @param date 待转换的日期字符串
	 * @param format 转换格式
	 * @return 转换之后的日期字符串
	 */
	public static String translateDate(String date, String format){
		String dateString = null;
		if(!StringUtils.isBlank(date) && !StringUtils.isBlank(format)){
			Date d = TypeParse.parseDate(date);
			dateString = TypeParse.parseString(d, format);
		}
		return dateString;
	}

	/******************************时间相关操作end******************************/
	
	
	/******************************DTO相关操作start******************************/
	
	/**
	 * 根据Action获取DTO的名称
	 * @param actionName
	 * @return
	 */
	public static String getTName(String actionName){
		int startIndex = actionName.indexOf("-") + 1;
		String tName = actionName.substring(startIndex);
		tName = tName.replace(".action", "");
		if(tName.indexOf("?") > -1){
			tName = tName.substring(0,tName.indexOf("?"));
		}
		return tName;
	}
	
	/**
	 * 根据某一个DTO，获取引用它的DTO的外键字段
	 * @param fkDtoName 最终要获取外键字段的DTO
	 * @param pkDtoName	参照DTO
	 * @return Field
	 */
	public static Field getFKField(String fkDtoName, String pkDtoName){
		Field fkField = null;
		try {
			if(!StringUtils.isBlank(fkDtoName) && !StringUtils.isBlank(pkDtoName)){
				JoinColumn jc = null;
				Field[] fs = Class.forName(fkDtoName).getDeclaredFields();
				for(Field f : fs){
					jc = f.getAnnotation(JoinColumn.class);
					if(jc != null && f.getType().toString().contains(pkDtoName)){
						fkField = f;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fkField;
	}

	/**
	 * 获取某个指定DTO的主键字段
	 * @param dtoName DTO完整的名称
	 * @return Field
	 */
	public static Field getPKField(String dtoName){
		Field field = null;
		try {
			Id id = null;
			Field[] fs = Class.forName(dtoName).getDeclaredFields();
			for(Field f : fs){
				id = f.getAnnotation(Id.class);
				if(id != null){
					field = f;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return field;
	}
	
	/**
	 * 获取当前将要进行测试的明细段
	 * @param jcDtoName
	 * @param dataFileName
	 * @return List<String>
	 */
	public static List<String> getCurrentTestMXDTOList(String dataFileName){
		List<String> dtoList = null;
		try {
			if(!StringUtils.isBlank(dataFileName)){
				List<String> curTestJCDtoList = TxtUtils.getCurrentTestJCDTOListFromTextFile();
				List<String> allTestDtoList = ExcelUtils.getTestDto(dataFileName);// 当前参数文件中配置的所有需要测试的类
				if(allTestDtoList != null && allTestDtoList.size() > 0
						&& curTestJCDtoList != null && curTestJCDtoList.size() > 0){
					int index = -1;
					JoinColumn jc = null;
					Field[] fs = null;
					dtoList = new ArrayList<String>();
					for(String testDto : allTestDtoList){
						if(!curTestJCDtoList.contains(testDto) && !testDto.endsWith("JC")){// 判断是否是当前测试的基础段，且dto名称不能以JC（基础段）结尾
							fs = Class.forName(testDto).getDeclaredFields();
							for(Field f : fs){
								jc = f.getAnnotation(JoinColumn.class);
								if(jc != null) {
									index = f.getType().toString().indexOf("class ")+6;
									if(curTestJCDtoList.contains(f.getType().toString().substring(index))) {// 外键字段对应的类是否为当前测试的基础段
										dtoList.add(testDto);
										break;
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtoList;
	}
	
	/**
	 * 
	 * <P>判断当前字段是否是数据库字段</P>
	 * @param dtoName DTO名称
	 * @param fieldName 需要判断的字段名称
	 */
	public static boolean isDBField(String dtoName, String fieldName) throws Exception {
		boolean dbFieldflag = false;
		Column c = null;
		JoinColumn jc = null;
		if(StringUtils.isNotBlank(dtoName) && StringUtils.isNotBlank(fieldName)) {
			Field[] fs = Class.forName(dtoName).getDeclaredFields();
			for(Field f : fs) {
				c = f.getAnnotation(Column.class);
				jc = f.getAnnotation(JoinColumn.class);
				
				if((c != null || jc != null) && (fieldName.equals(f.getName()) || fieldName.indexOf(f.getName()) > -1)) {
					dbFieldflag = true;
					break;
				}
			}
		}
		return dbFieldflag;
	}
	
	/******************************DTO相关操作end******************************/
	
	
	/******************************数据库操作start******************************/
	
	/**
	 * 检测数据是否存在
	 * @param dc
	 * @return
	 * @throws Exception
	 */
	public static boolean isDataExist(DetachedCriteria dc) {
		boolean flag = false;
		try {
			List<Object> dataList = getDBData(dc);
			if(dataList != null && dataList.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取数据
	 * @param dc
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getDBData(DetachedCriteria dc) throws Exception{
		SingleObjectFindByCriteriaDao singleObjectFindByCriteriaDao = (SingleObjectFindByCriteriaDao) FrameworkFactory.CreateClass("framework.dao.imps.SingleObjectFindByCriteriaDao");
		SessionFactory sessionFactory = getSessionFactory();
		if(sessionFactory != null) {
			singleObjectFindByCriteriaDao.setSessionFactory(sessionFactory);
		}else{
			singleObjectFindByCriteriaDao.setSessionFactory(getDefaultSessionFactory());
		}
		List<Object> dataList = null;
		if(dc != null) {
			dataList = (List<Object>) singleObjectFindByCriteriaDao.objectResultExecute(dc);
		}
		return dataList;
	}
	
	/**
	 * 修改数据
	 * @param object
	 * @return
	 */
	public static boolean updateDBData(Object object) {
		boolean updateFlag = true;
		try {
			SingleObjectUpdateDao singleObjectUpdateDao = (SingleObjectUpdateDao) FrameworkFactory.CreateClass("framework.dao.imps.SingleObjectUpdateDao");
			SessionFactory sessionFactory = getSessionFactory();
			if(sessionFactory != null) {
				singleObjectUpdateDao.setSessionFactory(sessionFactory);
			}else{
				singleObjectUpdateDao.setSessionFactory(getDefaultSessionFactory());
			}
			singleObjectUpdateDao.voidResultExecute(object);
		} catch (Exception e) {
			updateFlag = false;
		}
		return updateFlag;
	}
	
	/**
	 * 保存数据（不返回保存之后的ID值）
	 * @param object
	 * @return
	 */
	public static boolean saveDBData(Object object) {
		boolean updateFlag = true;
		try {
			SingleObjectSaveDao singleObjectSaveDao = (SingleObjectSaveDao) FrameworkFactory.CreateClass("framework.dao.imps.SingleObjectSaveDao");
			SessionFactory sessionFactory = getSessionFactory();
			if(sessionFactory != null) {
				singleObjectSaveDao.setSessionFactory(sessionFactory);
			}else{
				singleObjectSaveDao.setSessionFactory(getDefaultSessionFactory());
			}
			singleObjectSaveDao.voidResultExecute(object);
		} catch (Exception e) {
			updateFlag = false;
		}
		return updateFlag;
	}
	
	/**
	 * 保存数据（返回保存之后的ID值）
	 * @param object
	 * @return
	 */
	public static String saveDBDataAndReturnId(Object object) {
		String id = "";
		try {
			SingleObjectSaveDao singleObjectSaveDao = (SingleObjectSaveDao) FrameworkFactory.CreateClass("framework.dao.imps.SingleObjectSaveDao");
			SessionFactory sessionFactory = getSessionFactory();
			if(sessionFactory != null) {
				singleObjectSaveDao.setSessionFactory(sessionFactory);
			}else{
				singleObjectSaveDao.setSessionFactory(getDefaultSessionFactory());
			}
			id = singleObjectSaveDao.getHibernateTemplate().save(object).toString();
		} catch (Exception e) {
			id = "";
		}
		return id;
	}
	
	/**
	 * 获取sessionFactory，SessionManager中由于未明原因而无法获取到
	 * @param session
	 * @return String sessionFactory
	 */
	public static SessionFactory getSessionFactory(){
		SessionFactory sessionFactory = null;
		Map<String, Object> session = ActionTestCase.getSession();
		if(session != null && session.entrySet().size() > 0){
			String sf = ((LoginInfo)session.get("loginInfo")).getSessionFactory();
			if(!StringUtils.isBlank(sf) && !sf.equals("sessionFactory")){
				sessionFactory = (SessionFactory)FrameworkFactory.CreateBean(sf);
			}
		}
		return sessionFactory;
	}
	
	/**
	 * 获取默认的SessionFactory
	 * @return
	 */
	public static SessionFactory getDefaultSessionFactory(){
		return (SessionFactory) FrameworkFactory.CreateBean("sessionFactory");
	}
	
	/**
	 * 获取数据
	 * @param dc
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getDBData(String dataFileName, String sheet, String dtoName) throws Exception{
		DetachedCriteria dc = ExcelUtils.getDetachedCriteriaList(dataFileName, sheet, dtoName);
		List<Object> dataList = getDBData(dc);
		return dataList;
	}
	
	/**
	 * 删除数据库数据（针对一个excel中的多个DTO，批量）
	 * @param dataFileName 数据文件名称
	 * @param sheetInfo sheet页名称或者是下标集合（按照逻辑顺序排列）
	 * @param dtoNameList DTO名称的集合（按照逻辑排列）
	 * @return 返回的是某个预定顺序下的DTO所对应的查询条件
	 * @exception
	 */
	public static void deleteDBData(String dataFileName, List<String> sheetInfoList, List<String> dtoNameList) throws Exception{
		List<DetachedCriteria> dcList = ExcelUtils.getDetachedCriteriaList(dataFileName, sheetInfoList, dtoNameList);
		for(DetachedCriteria dc : dcList){
			deleteDBData(dc);
		}
		sheetInfoList.clear();
		dtoNameList.clear();
	}
	
	/**
	 *  删除数据（单条）
	 * @param dc 查询条件
	 * @throws Exception
	 */
	public static void deleteDBData(DetachedCriteria dc) throws Exception {
		if(dc != null && ActionTestUtils.isDataExist(dc)){
			SingleObjectDeleteByCriteriaDao singleObjectDeleteByCriteriaDao = (SingleObjectDeleteByCriteriaDao) FrameworkFactory.CreateClass("framework.dao.imps.SingleObjectDeleteByCriteriaDao");
			SessionFactory sessionFactory = getSessionFactory();
			if(sessionFactory != null){
				singleObjectDeleteByCriteriaDao.setSessionFactory(sessionFactory);
			}else{
				singleObjectDeleteByCriteriaDao.setSessionFactory(getDefaultSessionFactory());
			}
			singleObjectDeleteByCriteriaDao.voidResultExecute(new Object[]{dc, null});
		}
	}
	
	/**
	 * 按照dto名称删除数据（批量）
	 * @param dtoNameList DTO名称列表
	 */
	public static void deleteDBData(List<String> dtoNameList){
		try {
			if(dtoNameList != null && dtoNameList.size() > 0){
				String excelFileName = "";
				DetachedCriteria dc = null;
				for(String dtoName : dtoNameList){
					excelFileName = getExcelFileOrSheetName(dtoName, false);
					if(!StringUtils.isBlank(excelFileName)){
						dc = ExcelUtils.getDetachedCriteriaList(excelFileName, "0", dtoName);
						deleteDBData(dc);
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dtoNameList.clear();
		}
	}
	
	
	
	/**
	 * 
	 * <P>通过DTO列表批量删除测试数据（针对于多组数据测试）</P>
	 * 
	 * @param dtoMap 存放DTO信息的Map（key为dto名称，value为是否将当前DTO的主键字段作为查询条件的标志）
	 * @author liutao
	 * @throws Exception 
	 * @createDate 2016/06/08 PM
	 * @lastUpdateDate 
	 *
	 */
	public static void deleteDBDataForMutilpGroupDataByDtoList(Map<String, Boolean> dtoMap) throws Exception {
		if(dtoMap != null && dtoMap.size() > 0) {
			Set<Map.Entry<String, Boolean>> ens = dtoMap.entrySet();
			List<DetachedCriteria> dcList = new ArrayList<DetachedCriteria>();
			List<DetachedCriteria> dcListTem = null;
			for(Map.Entry<String, Boolean> en : ens) {
				dcListTem = ExcelUtils.getSameDTOAllDetachedCriteria(en.getKey(), en.getValue());
				if(dcListTem != null && dcListTem.size() > 0) {
					dcList.addAll(dcListTem);
				}
			}
			
			if(dcList.size() > 0) {
				for(DetachedCriteria dc : dcList) {
					deleteDBData(dc);
				}
			}
		}
	}
	
	/**
	 * 
	 * <P>通过单个DTO名称删除测试数据（针对于多组数据测试）</P>
	 * 
	 * @param dtoName 单个DTO名称
	 * @param idFlag 是否将主键字段放入查询条件的标志
	 * @author liutao
	 * @throws Exception 
	 * @createDate 2016/06/08 PM
	 * @lastUpdateDate 
	 *
	 */
	public static void deleteDBDataForMutilpGroupDataByDto(String dtoName, boolean idFlag) throws Exception {
		if(!StringUtils.isBlank(dtoName)) {
			List<DetachedCriteria> dcList = ExcelUtils.getSameDTOAllDetachedCriteria(dtoName, idFlag);
			if(dcList != null && dcList.size() > 0) {
				for(DetachedCriteria dc : dcList) {
					deleteDBData(dc);
				}
			}
		}
	}
	
	
	/******************************数据库操作end******************************/
	
	
	
	
	/******************************文件（夹）操作start******************************/

	/**
	 * 创建文件夹或者文件
	 * @param patah
	 */
	public static void createFilePath(String path){
		try {
			if(!StringUtils.isBlank(path)){
				File file = new File(path);
				if(file != null && !file.exists()){
					if(file.isDirectory()){
						file.mkdirs();
					}else{
						file.createNewFile();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文件夹 或者文件
	 * @param patah
	 */
	public static void deleteFilePath(String path){
		try {
			if(!StringUtils.isBlank(path)){
				File file = new File(path);
				if(file != null && file.exists()){
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/******************************文件（夹）操作end******************************/
	
	
	
	/******************************字段类型判断start******************************/
	
	/**
	 * 判断当前的参数字段是否是日期字段
	 */
	public static boolean isDateField(String dtoName, String name) throws Exception{
		boolean result = false;
		String compareName = name.substring(name.indexOf(".")+1);
		Field[] fs = Class.forName(dtoName).getDeclaredFields();
		for(Field f : fs){
			if(f.getType().equals(java.util.Date.class) || f.getType().equals(java.sql.Date.class)){
				if(compareName.contains(f.getName()) || compareName.equals(f.getName())){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 判断当前的参数字段是否是时间戳字段
	 */
	public static boolean isTimeStampField(String dtoName, String name) throws Exception{
		boolean result = false;
		String compareName = name.substring(name.indexOf(".")+1);
		Field[] fs = Class.forName(dtoName).getDeclaredFields();
		for(Field f : fs){
			if(f.getType().equals(java.sql.Timestamp.class)){
				if(compareName.contains(f.getName()) || compareName.equals(f.getName())){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/******************************字段类型判断end******************************/
	
	
	/******************************测试的异常检测start******************************/
	/**
	 * 读取异常错误标志信息，执行每一个测试方法之前，检测在当前测试方法之前是否发生异常或者错误信息，
	 * 如若发生将不会执行下一个测试方法，且整个测试流程全部停止且测试失败。默认为未发生异常
	 * @throws IOException 
	 * @return boolean 发生异常：true，未发生异常：false
	 */
	@SuppressWarnings("finally")
	public static boolean checkExceptionFlag(){
		boolean exceptionFlag = false;// 发生异常标志
		FileReader reader = null;
		BufferedReader br = null;
		try {
			String path = System.getProperty("java.io.tmpdir")+"exceptionFlagFile.txt";
			File file = new File(path);
			if(file.exists()) {
				reader = new FileReader(file);
				br = new BufferedReader(reader);
				String text = null;
				while(!StringUtils.isBlank(text = br.readLine())) {
					if(text.equals("true")) {
						exceptionFlag = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
				if(reader != null) {
					reader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			return exceptionFlag;
		}
	}
	
	
	/**
	 * 测试异常处理
	 * @param exception 异常类型
	 * @param curTestDtoName 当前测试的DTO名称(当前存在多个DTO测试时，可以定位到是哪一个DTO测试出错)
	 */
	public static void setTestResultWhenException(Exception exception, String messge){
		ActionTestUtils.setExceptionFlagToTxtFile(true);// 设置异常标志
		
		if(exception == null) {
			Assert.fail(messge);
		}
		else if(exception instanceof AssumptionViolatedException){// 如果出现此异常，说面在这个测试（或测试方法）之前已经存在异常，则后续的测试都不会执行
			Assert.assertTrue(true);
		} else {
			try {
				Method method = ReflectOperation.getReflectMethod(exception.getClass(), "printStackTrace");
				if(method != null) {
					method.invoke(exception);
				}
				
				method = ReflectOperation.getReflectMethod(exception.getClass(), "getMessage");
				Object msg = null;
				if(method != null) {
					msg = method.invoke(exception);
				}
				if (msg != null) {
					Assert.fail(messge+",异常信息如下："+System.getProperty("line.separator")+msg.toString());
				} else {
					Assert.fail(messge);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}
	
	
	/**
	 * 设置异常错误标志信息到文本文件（.txt）
	 * @param flag
	 * @throws IOException
	 */
	private static void setExceptionFlagToTxtFile(boolean flag){
		FileOutputStream fos = null;
		try {
			String path = System.getProperty("java.io.tmpdir")+"exceptionFlagFile.txt";
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			
			String text = null;
			fos = new FileOutputStream(file);
			if(flag) {
				text = "true";
			} else {
				text = "false";
			}
			fos.write(text.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fos != null) {
					fos.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/******************************测试的异常检测end******************************/
	
	
	
	/********************关闭打开需要文件的程序，主要方式是结束进程START********************/
	/**
	 * 关闭打开的文件
	 * @param fileName 文件名称
	 */
	public static void closeOpenedFile(String fileName) {
		if(!StringUtils.isBlank(fileName)){
			File file = new File(fileName);
			if(!file.renameTo(file)) {// 如果更新名称失败，说明有进程在占用此文件，需要结束掉当前占用的进程
				String fileTypeFlag = getFileType(fileName);
				destroyFileProcess(fileTypeFlag);
			}
		}
	}
	
	/**
	 * 结束掉当前占用文件的应用进程（）
	 */
	private static void destroyFileProcess(String fileTypeFlag) {
		Process proc = null;
		try {
			 if(!StringUtils.isBlank(fileTypeFlag)) {
				 String[] cmdArr = null;
				 Runtime rt = Runtime.getRuntime();
				 String osName = System.getProperty("os.name");
				 if(osName.toLowerCase().indexOf("windows") > -1) {
					 if(fileTypeFlag.equals("excel")) {
						 cmdArr = new String[] {"taskkill /f /t /im excel.exe", "taskkill /f /t /im et.exe"};
					 } else if(fileTypeFlag.equals("txt")) {
						 cmdArr = new String[] {"taskkill /f /t /im notepad*"};
					 }
				 }
				 for(String cmd : cmdArr) {
					 proc = rt.exec(cmd);
					 proc.destroy();
					 proc = null;
				 }
			 }
		} catch (Exception e) {
			if(proc != null){
				proc.destroy();
				proc = null;
			}
		}
	}
	
	
	/**
	 * 获取文件的
	 * @param fileName
	 * @return
	 */
	private static String getFileType(String fileName) {
		String fileType = null;
		if(!StringUtils.isBlank(fileName)) {
			int index = fileName.lastIndexOf(".")+1;
			fileType = fileName.substring(index);
		}
		return fileType;
	}
	
	/********************关闭打开需要文件的程序，主要方式是结束进程END********************/
	
	
	
	/********************界面显示结果判断start********************/
	
	/**
	 * <P>判断列表显示界面的显示结果</P>
	 * <P>1、判断按钮是否显示正确，是否和Spring配置的一样（是否多显或者漏显）</P>
	 * <P>2、判断链接是否显示正确，是否和Spring配置的一样（是否多显或者漏显）</P>
	 * <P>3、判断分页栏是否显示正确</P>
	 * <P>4、判断表头是否显示正确</P>
	 * <P>5、判断显示数据是否正确，前提是传入的查询条件（即：参数dc）不为空</P>
	 * <P>6、判断条件输入框是否显示正确</P>
	 * <P>7、判断特定的条件输入框的显示类型是否正确</P>
	 * <P>8、判断下拉框或者多选框的待选数据是否正确（是否多显或者漏显）</P>
	 * @param showDatadc 界面显示数据的查询条件
	 * @param fieldShowTypeMap 字段在界面预期的显示类型的Map
	 * @param dataSourceMap 此参数表示若某些特定字段的显示形式为下拉框或者多选框时，显示数据的来源（1、entity配置；2、直接来自DB）
	 * @throws Exception 
	 */
	public static void checkShowListViewer(DetachedCriteria showDatadc, Map<String, String> fieldShowTypeMap, Map<String, Object>  dataSourceMap) throws Exception {
		String message = "";
		ShowPage showPage = null;
		List<ShowOperation> operationList = null;
		List<List<ShowOperation>> linkedList = null;
		List<ShowFieldCondition> showCondition = null;
		
		Map<String, String> opertionMap = null;
		Map<String, String> linkedMap = null;
		
		Set<String> setTem = null;
		
		ShowList showList = (ShowList) LogicParamManager.getServiceResult();
		if(showList != null) {
			showPage = showList.getShowPage();
			showCondition = showList.getShowCondition();
			
			operationList = showList.getOperationList();
			opertionMap = ShowParamManager.getOperationMap();
			
			linkedList = showList.getLinkedList();
			linkedMap = ShowParamManager.getLinkedMap();
			
			if(showPage == null) {
				message += "列表显示界面无分页栏；";
			} else if(showCondition == null || showCondition.size() <= 0) {
				message += "列表显示界面无查询条件输入框；";
			} else if(showCondition != null && showCondition.size() > 0){
				Object tagSourceType = null;// 当前字段的Tag来源（1、DB；2、entity配置）
				Object tag = null;
				Object tagDatas = null;// 根据tag数据来源获取的tag数据
				String fieldName = null;
				for(ShowFieldCondition condition : showCondition) {
					fieldName = condition.getFieldName();
					if(StringUtils.isBlank(condition.getFieldShowName())) {
						message += "字段"+fieldName+"对应的条件输入框的显示名称（即：fieldShowName属性）为空；";
					} else if(StringUtils.isBlank(condition.getParamName())) {
						message += "字段"+fieldName+"对应的条件输入框的HTTP提交参数（即：paramName属性）为空；";
					} else if(StringUtils.isBlank(condition.getSingleTag())) {
						message += "字段"+fieldName+"对应的条件输入框显示形式（即：singleTag属性）为空；";
					} else {
						if(fieldShowTypeMap != null && fieldShowTypeMap.size() >= 0
								&& dataSourceMap != null && dataSourceMap.size() >= 0
								&& fieldShowTypeMap.size() == dataSourceMap.size()){
							if(!StringUtils.isBlank(fieldShowTypeMap.get(fieldName))
									&& (dataSourceMap.get(fieldName) != null
									|| !StringUtils.isBlank(dataSourceMap.get(fieldName).toString()))) {
								if(!condition.getSingleTag().equals(fieldShowTypeMap.get(fieldName))) {// 若显示类型与预期的不一致
									message += "字段"+fieldName+"对应的输入框的显示形式（即：singleTag属性）与预期的显示形式不一致；";
								} else {
									tagSourceType = dataSourceMap.get(fieldName);
									if(tagSourceType != null) {
										tag = condition.getTag();
										if(tagSourceType instanceof DetachedCriteria) {// 数据库查询条件
											tagDatas = getDBData((DetachedCriteria) tagSourceType);
										} else {// entity配置
											tagDatas = ShowContext.getInstance().getShowEntityMap().get(tagSourceType.toString());
										}
										
										if(tag == null || ((LinkedHashMap<String, String>) tag).size() <= 0) {// 界面显示的数据为空
											if(tagDatas != null) {
												if(tagDatas instanceof List<?>) {// List
													if(tagDatas != null && ((List<Object>) tagDatas).size() > 0) {
														message += "字段"+fieldName+"对应的显示数据在数据库中存在，而界面没显示；";
													}
												} else {// LinkedHashMap
													if(tagDatas != null && ((LinkedHashMap<String, String>) tagDatas).size() > 0) {
														message += "字段"+fieldName+"对应的显示数据Entity.xml文件中已经配置，但是界面没有显示；";
													}
												}
											}
										} else {// tag不空
											if(tagDatas != null) {
												if(tagDatas instanceof List<?>) {// List
													if(((List<Object>) tagDatas).size() > ((LinkedHashMap<String, String>) tag).size()) {
														message += "字段"+fieldName+"未将数据库中的相应数据显示完全；";
													} else if(((List<Object>) tagDatas).size() < ((LinkedHashMap<String, String>) tag).size()) {
														message += "字段"+fieldName+"显示的数据中有一部分在数据库中不存在；";
													}
												} else {// LinkedHashMap
													if(((LinkedHashMap<String, String>) tagDatas).size() > ((LinkedHashMap<String, String>) tag).size()) {
														message += "字段"+fieldName+"未Entity.xml文件中配置的相应数据显示完全；";
													} else if(((LinkedHashMap<String, String>) tagDatas).size() < ((LinkedHashMap<String, String>) tag).size()) {
														message += "字段"+fieldName+"显示的数据中有一部分未在Entity.xml文件中配置；";
													}
												}
											} else {
												message += "字段"+fieldName+"对应的显示数据在系统（DB或者Entity.xml）中不存在；";
											}
										}
									}
								}
							}
						}// condition不空判断结束
					}
				}
			}else if(showList.getShowNameList() == null || showList.getShowNameList().size() <= 0) {
				message += "界面中未显示数据对应的表头信息；";
			} else if(showDatadc != null) {// 查询条件不为空则检测显示数据的条数是否正确
				List<Object> dataList = getDBData(showDatadc);
				List<List<ShowValue>> valueTable = showList.getValueTable();
				if(dataList != null && valueTable != null) {
					if(dataList.size() > valueTable.size()) {
						message += "界面显示的数据的数目小于数据库中同等条件下查询得到的数据数目；";
					} else if(dataList.size() < valueTable.size()) {
						message += "界面显示的数据的数目大于数据库中同等条件下查询得到的数据数目；";
					}
				}
			} else {// 操作按钮和链接按钮的判断
				if((opertionMap == null || opertionMap.size() <= 0) ||
						(opertionMap != null && opertionMap.size() <= 0)) {// spring中没有配置操作按钮信息
					if(operationList != null && operationList.size() > 0) {
						message += "列表展示界面按钮显示异常，Spring中未配置，而界面显示按钮信息；";
					}
				} else if(opertionMap != null && opertionMap.size() > 0) {
					if(operationList == null || operationList.size() <= 0) {
						message += "列表展示界面按钮显示异常，Spring中有配置，而界面未显示按钮信息；";
					} else if(operationList != null && operationList.size() > 0) {
						String notSetBtnsInSpring = "";// 没有在Spring中配置的按钮
						
						setTem = new HashSet<String>();// 记录显示界面中与sprong配置的按钮相同的按钮信息（按钮名称）
						Set<String> keySet = opertionMap.keySet();
						for(ShowOperation btn : operationList) {
							if(!keySet.contains(btn.getOperationName())) {
								notSetBtnsInSpring += btn.getOperationName()+"、";
							} else {
								setTem.add(btn.getOperationName());
							}
						}
						
						if(!StringUtils.isBlank(notSetBtnsInSpring)) {
							notSetBtnsInSpring = notSetBtnsInSpring.substring(0, notSetBtnsInSpring.length()-1);
							message += "列表展示界面显示的按钮"+notSetBtnsInSpring+"未在Spring中配置；";
						}
						
						if(setTem != null && setTem.size() > 0) {
							if(keySet.size() > setTem.size()) {// 说明spring配置的按钮未全部在界面显示
								String btnNames = "";
								keySet.removeAll(setTem);
								
								for(String btnName : keySet) {
									btnNames += btnName+"、";
								}
								btnNames = btnNames.substring(0, btnNames.length());
								
								message += "Spring中配置的按钮"+btnNames+"未在界面显示出来；";
							}
						}
					}
				} else if((linkedMap == null || linkedMap.size() <= 0) || 
						(linkedMap != null && linkedMap.size() <= 0)) {
					if(linkedList != null && linkedList.size() > 0) {
						if(linkedList.get(0).size() > 0) {
							message += "列表展示界面数据超链接显示异常，Spring中未配置，而界面显示链接信息；";
						}
					}
				} else if(linkedMap != null && linkedMap.size() > 0) {
					if(linkedList == null || linkedList.size() <= 0 || 
							(linkedList.size() > 0 && linkedList.get(0).size() <= 0) ) {
						message += "列表展示界面链接显示异常，Spring中有配置，而界面未显示链接信息；";
					} else if(linkedList != null && linkedList.size() > 0 && linkedList.get(0).size() > 0) {
						String notSetLinkedsInSpring = "";// 没有在Spring中配置的按钮
						
						setTem = new HashSet<String>();// 记录显示界面中与sprong配置的按钮相同的按钮信息（按钮名称）
						for(ShowOperation linked : linkedList.get(0)) {
							if(!linkedMap.keySet().contains(linked.getOperationName())) {
								notSetLinkedsInSpring += linked.getOperationName()+"、";
							} else {
								setTem.add(linked.getOperationName());
							}
						}
						
						if(!StringUtils.isBlank(notSetLinkedsInSpring)) {
							notSetLinkedsInSpring = notSetLinkedsInSpring.substring(0, notSetLinkedsInSpring.length()-1);
							message += "列表展示界面显示的链接"+notSetLinkedsInSpring+"未在Spring中配置；";
						}
						
						if(setTem != null && setTem.size() > 0) {
							if(linkedMap.keySet().size() > setTem.size()) {// 说明spring配置的按钮未全部在界面显示
								String linkedNames = "";
								linkedMap.keySet().removeAll(setTem);
								
								for(String linkedName : linkedMap.keySet()) {
									linkedNames += linkedName+"、";
								}
								linkedNames = linkedNames.substring(0, linkedNames.length());
								
								message += "Spring中配置的链接"+linkedNames+"未在界面显示出来；";
							}
						}
					}
				}
			}// 操作按钮和链接按钮的判断结束
		} else {
			message = "界面数据记录对象（ShowList）为空";
		}
		
		if(fieldShowTypeMap != null) {
			fieldShowTypeMap.clear();
		}
		
		if(dataSourceMap != null) {
			dataSourceMap.clear();
		}
		
		if(!StringUtils.isBlank(message)) {
			ActionTestUtils.setTestResultWhenException(null, message);
		}
	}
	
	/**
	 * 
	 * <P>判断新增修改界面的显示结果</P>
	 * <P>1、判断按钮是否显示正确，是否和Spring配置的一样（是否多显或者漏显）</P>
	 * <P>2、判断数据输入框是否显示正确</P>
	 * <P>3、判断特定的数据输入框的显示类型是否正确</P>
	 * <P>4、判断下拉框或者多选框的待选数据是否正确（是否多显或者漏显）</P>
	 * 
	 * @param dtoName 当前操作所对应的DTO名称
	 * @param fielShowTypeMap 预期的字段的显示形式Map
	 * @param updateFlag 是否是修改界面（是修改界面：true）
	 * @author Liutao
	 *
	 */
	public static void checkSaveOrUpdateViewer(String dtoName, Map<String, String> fieldShowTypeMap, Map<String, Object>  dataSourceMap, boolean updateFlag) throws Exception {
		String message = "";
		ShowSaveOrUpdate saveOrUpdate = (ShowSaveOrUpdate) LogicParamManager.getServiceResult();
		if(saveOrUpdate != null && !StringUtils.isBlank(dtoName)) {
			List<ShowOperation> operationList = saveOrUpdate.getOperationList();
			Map<String, String> operationMap = ShowParamManager.getOperationMap();
			
			if(operationMap == null || operationMap.size() <= 0) {
				if(operationList != null && operationList.size() > 0) {
					message += "Spring中未配置按钮，界面却显示有按钮信息 ";
				}
			} else if(operationMap != null && operationMap.size() > 0) {
				 if(operationList == null || operationList.size() <= 0) {
					 message += "Spring中配置有按钮，可界面没有显示任何按钮信息；";
				 } else {
					 Set<String> keySet = operationMap.keySet();
					 Set<String> setTemp = new HashSet<String>();
					 String notSetBtns = "";
					 for(ShowOperation btn : operationList) {
						 if(!keySet.contains(btn.getOperationName())){
							 notSetBtns += btn.getOperationName()+"、";
						 } else {
							setTemp.add(btn.getOperationName());
						}
					 }
					 
					 if(!StringUtils.isBlank(notSetBtns)) {
						 notSetBtns = notSetBtns.substring(0, notSetBtns.length());
						 message += "界面显示有Spring中未配置的"+notSetBtns+"按钮信息；";
					 }
					 
					 if(setTemp != null && setTemp.size() > 0) {
						 keySet.removeAll(setTemp);
						 if(keySet.size() > 0) {
							 String notShowBtns = "";
							 for(String btnName : keySet) {
								 notShowBtns += btnName+"、";
							 }
							 notShowBtns = notSetBtns.substring(0, notShowBtns.length());
							 message += "Spring中配置的按钮信息"+notShowBtns+"未显示在界面中；";
						 }
					 }
				 }
			} else {
				List<ShowFieldValue> showFieldValueList = saveOrUpdate.getShowFieldValueList();
				if(showFieldValueList == null || showFieldValueList.size() <= 0) {
					message += "界面没有任何字段信息显示；";
				} else {
					ShowField sf = null;
					Object tagSourceType = null;// 当前字段的Tag来源（1、DB；2、entity配置）
					Object tag = null;
					Object tagDatas = null;// 根据tag数据来源获取的tag数据
					String fieldName = null;
					for(ShowFieldValue sfv : showFieldValueList) {
						sf = sfv.getShowField();
						fieldName = sf.getFieldName();
						if(updateFlag) {// 判断是否是修改操作，或者当前界面是否是修改界面
							Field pkField = getPKField(dtoName);
							if(fieldName.equals(pkField.getName()) &&
									(sfv.getFieldValue() == null || StringUtils.isBlank(sfv.getFieldValue().toString()))) {
								message += "当前操作为修改操作，且当前DTO对应的主键字段的值为空";
							}
						}
						
						if(StringUtils.isBlank(sf.getFieldShowName())) {
							message += "字段"+fieldName+"在界面中的显示名称为空；";
						} else if(StringUtils.isBlank(sf.getParamName())) {
							message += "字段"+fieldName+"对应的HTTP提交参数名称（即：paramName属性）为空；";
						} else {
							if(StringUtils.isBlank(sf.getSingleTag())) {
								message += "字段"+fieldName+"的显示类型（即：singleTag属性）为空；";
							} else {
								if(fieldShowTypeMap != null && fieldShowTypeMap.size() > 0
										&& dataSourceMap != null && dataSourceMap.size() > 0
										&& fieldShowTypeMap.size() == dataSourceMap.size()) {
									if(!StringUtils.isBlank(fieldShowTypeMap.get(fieldName))
											&& (dataSourceMap.get(fieldName) != null
											|| !StringUtils.isBlank(dataSourceMap.get(fieldName).toString()))) {
										if(!sf.getSingleTag().equals(fieldShowTypeMap.get(fieldName))) {
											message += "字段"+fieldName+"的界面显示类型（即：singleTag属性）不是"+
											fieldShowTypeMap.get(fieldName)+"，当前的显示类型是"+sf.getSingleTag()+"；";
										} else {
											tagSourceType = dataSourceMap.get(fieldName);
											if(tagSourceType != null) {
												tag = sfv.getTag();
												if(tagSourceType instanceof DetachedCriteria) {// 数据库查询条件
													tagDatas = getDBData((DetachedCriteria) tagSourceType);
												} else {// entity配置
													tagDatas = ShowContext.getInstance().getShowEntityMap().get(tagSourceType.toString());
												}
												
												if(tag == null || ((LinkedHashMap<String, String>) tag).size() <= 0) {// 界面显示的数据为空
													if(tagDatas != null) {
														if(tagDatas instanceof List<?>) {// List
															if(tagDatas != null && ((List<Object>) tagDatas).size() > 0) {
																message += "字段"+fieldName+"对应的显示数据在数据库中存在，而界面没显示；";
															}
														} else {// LinkedHashMap
															if(tagDatas != null && ((LinkedHashMap<String, String>) tagDatas).size() > 0) {
																message += "字段"+fieldName+"对应的显示数据Entity.xml文件中已经配置，但是界面没有显示；";
															}
														}
													}
												} else {// tag不空
													if(tagDatas != null) {
														if(tagDatas instanceof List<?>) {// List
															if(((List<Object>) tagDatas).size() > ((LinkedHashMap<String, String>) tag).size()) {
																message += "字段"+fieldName+"未将数据库中的相应数据显示完全；";
															} else if(((List<Object>) tagDatas).size() < ((LinkedHashMap<String, String>) tag).size()) {
																message += "字段"+fieldName+"显示的数据中有一部分在数据库中不存在；";
															}
														} else {// LinkedHashMap
															if(((LinkedHashMap<String, String>) tagDatas).size() > ((LinkedHashMap<String, String>) tag).size()) {
																message += "字段"+fieldName+"未Entity.xml文件中配置的相应数据显示完全；";
															} else if(((LinkedHashMap<String, String>) tagDatas).size() < ((LinkedHashMap<String, String>) tag).size()) {
																message += "字段"+fieldName+"显示的数据中有一部分未在Entity.xml文件中配置；";
															}
														}
													} else {
														message += "字段"+fieldName+"对应的显示数据在系统（DB或者Entity.xml）中不存在；";
													}
												}
											}
										}
									}
								}
							}
						}
					}// 循环字段信息
				}
			}
		} else {
			message += "数据显示对象（ShowSaveOrUpdate）为空";
		}
		
		if(fieldShowTypeMap != null) {
			fieldShowTypeMap.clear();
		}
		
		if(dataSourceMap != null) {
			dataSourceMap.clear();
		}
		
		if (!StringUtils.isBlank(message)) {
			ActionTestUtils.setTestResultWhenException(null, message);
		}
	}
	
	
	
	/********************界面显示结果判断end********************/
	
	
	
	/********************从URL中获取参数start********************/
	
	/**
	 * 
	 * <P>从URL中获取ID、type、windowId信息</P>
	 */
	public static Map<String, String> parseURLRequestParam(String URL) {
		Map<String, String> paramMap = null;
		if(!StringUtils.isBlank(URL)) {
			int index = 0;
			paramMap = new HashMap<String, String>();
			
			index = URL.indexOf("?")+1;
			URL = URL.substring(index);
			String[] arr = URL.split("&");
			String[] proArr = null;
			for(int i = 0; i < arr.length; i++) {
				proArr = arr[i].split("=");
				if(!StringUtils.isBlank(proArr[0]) && !StringUtils.isBlank(proArr[1])) {
					paramMap.put(proArr[0], proArr[1]);
				}
			}
		}
		return paramMap;
	}
	
	/********************从URL中获取参数end********************/
	
}
