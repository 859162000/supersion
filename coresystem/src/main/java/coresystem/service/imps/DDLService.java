package coresystem.service.imps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import coresystem.helper.TableClassGenerator;
import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;


// 主要功能：根据当前DTO创建出SQL语句和连接供DAO执行
public class DDLService extends BaseVoidDaoResultService{

	// 由配置文件传入的本地连接类型，需优先使用当前表所属连接类型
	private String strDBType;
	public String getStrDBType() {
		return strDBType;
	}
	public void setStrDBType(String strDBType) {
		this.strDBType = strDBType;
	}
	/**
	private Map<String, String> autoFieldMap;
	public void setAutoFieldMap(Map<String, String> autoFieldMap) {
		this.autoFieldMap = autoFieldMap;
	}
	public Map<String, String> getAutoFieldMap() {
		return autoFieldMap;
	}
	 * 
	 */
	
	@Override
    public void init() throws Exception{
		super.init();
	}
	
	@Override
	public void initSuccessResult() {
		// 检查库中是否存在表 调用DAO 放入参数  DBType, DBDriver, DBURL, DBPassword, DBUser
		
		// 检查表中是否存在字段
		
		
		if(RequestManager.getTName().equals("extend.dto.ReportModel_Table")) {
			try {
				CreateTable();
			} catch (Exception e) {
				setServiceResult(new MessageResult(false, "生成表失败！"));
				e.printStackTrace();
			}
		}
	}
	
	// 目前表的实质性创建交给Hibernate,这里只创建DTO,需要重启动应用后，Hibernate自动更新数据库表
	private void CreateTable() throws Exception { // tName为表
		// 根据当前DTO查找字段表创建出SQL语句和连接供DAO执行
		//String strSQL = "Create table " +　前缀;
		
		// 创建DAO
		String beanID = "singleObjectFindByIdDao";
		IParamObjectResultExecute SingleObjectFindByIdDao =
		(IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
		// 调用DAO查询到tObject对象(表)
		Object tObject = SingleObjectFindByIdDao.paramObjectResultExecute(null);
		if (tObject == null) { // 找不到对象,设置SQL为空
			LogicParamManager.setSqlQuery("");
			return;
		}
		
		ReportModel_Table table = (ReportModel_Table)tObject;
		//strSQL += table.getStrTableName() + "(";
		
		Set<ReportModel_Field> fields = table.getReportModel_Fields();
		if(fields.size() <= 0) { // 没有字段,设置SQL为空
			LogicParamManager.setSqlQuery("");
			
			setServiceResult(new MessageResult(false, "没有字段，不能生成表！"));
			return;
		}
		
		Map<String, String> fieldNameMap = new LinkedHashMap<String,String>();
		boolean hasPrimaryKey = false;
		ArrayList<String>  autoFields = new ArrayList<String>();
		Map<String, String> autoFieldMap = ShowContext.getInstance().getShowEntityMap().get("autoField");
		
		if(autoFieldMap == null 
    			|| (table.getStrAddFields() != null && !table.getStrAddFields().equals("1"))){
    		autoFieldMap = new HashMap<String,String>();
    	}
		
		for(Map.Entry<String, String> entry: autoFieldMap.entrySet()) {
			String strField = entry.getValue();
			autoFields.add(strField);
		}
		
		for(ReportModel_Field field : fields) { // 检测添加固定字段

			for(Map.Entry<String, String> entry: autoFieldMap.entrySet()) {
				String strField = entry.getValue();
				if(strField.indexOf(',') > -1) {
					if(strField.substring(0, strField.indexOf(',')).toUpperCase().equals(field.getStrFieldName().toUpperCase()))
						autoFields.remove(strField);
				}
			}
			
			// 检测同名字段
			if(fieldNameMap.get(field.getStrFieldName()) == null)
				fieldNameMap.put(field.getStrFieldName(), "");
			else {
				setServiceResult(new MessageResult(false, "有相同的字段名存在:" + field.getStrFieldName()));
				return;
			}
			
			if(field.getIsKey().equals("1") && !hasPrimaryKey) {//主键
				hasPrimaryKey = true;
			}
		}
		
		ArrayList<ReportModel_Field> fieldArray = new ArrayList<ReportModel_Field>(fields);
		
		if(!hasPrimaryKey) { // 没有主键，生成一个
			ReportModel_Field fieldID = new ReportModel_Field();
			fieldID.setStrChinaName("ID");
			fieldID.setIsEnable("1");
			fieldID.setIsKey("1");
			fieldID.setNSeq("1001");
			fieldID.setStrDiscription("");
			fieldID.setStrEmptyType("1");
			fieldID.setStrLogicEmptyType("1");
			fieldID.setStrFieldName("AUTOID");
			fieldID.setStrFieldType("2");
			fieldID.setReportModel_Table(table);

			fieldArray.add(fieldID);
			
			// 将字段记录保存到字段表
			 IParamVoidResultExecute dao =
				 (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
			 Object oldObj = RequestManager.getTOject();
			 RequestManager.setTOject(fieldID);
			 dao.paramVoidResultExecute(null);
			 RequestManager.setTOject(oldObj);
		}
		
		// 生成配置给明细表的固定字段
		for(String strField: autoFields) {
			 String[] strArray = strField.split(",");
			 if(strArray.length < 4) continue;
			 
			 if(strArray[1].equals("time")) {
				 ReportModel_Field fieldDate = new ReportModel_Field();
				 fieldDate.setStrChinaName(strArray[2]);
				 fieldDate.setIsEnable("1");
				 fieldDate.setIsKey("2");
				 fieldDate.setNSeq(strArray[3]);
				 fieldDate.setStrDiscription("");
				 fieldDate.setStrEmptyType("1");
				 fieldDate.setStrLogicEmptyType("2");
				 fieldDate.setStrFieldName(strArray[0]);
				 fieldDate.setStrFieldType("3");
				 fieldDate.setReportModel_Table(table);
				 
				 fieldArray.add(fieldDate);
				 					
				 // 将字段记录保存到字段表
				 IParamVoidResultExecute dao =
					 (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
				 Object oldObj = RequestManager.getTOject();
				 RequestManager.setTOject(fieldDate);
				 dao.paramVoidResultExecute(null);
				 RequestManager.setTOject(oldObj);
			 }
			 else if(strArray[1].equals("inst")) {
				if (strArray.length < 4)
					continue;

				ReportModel_Field fieldInst = new ReportModel_Field();
				fieldInst.setStrChinaName(strArray[2]);
				fieldInst.setIsEnable("1");
				fieldInst.setIsKey("2");
				fieldInst.setNLength("20");
				fieldInst.setNSeq(strArray[3]);
				fieldInst.setStrDiscription("");
				fieldInst.setStrEmptyType("1");
				fieldInst.setStrLogicEmptyType("2");
				fieldInst.setStrFieldName(strArray[0]);
				fieldInst.setStrFieldType("2");
				fieldInst.setStrConstList("coresystem.dto.InstInfo-strInstCode");
				fieldInst.setReportModel_Table(table);

				fieldArray.add(fieldInst);

				// 将字段记录保存到字段表
				IParamVoidResultExecute dao = (IParamVoidResultExecute) FrameworkFactory
						.CreateBean("singleObjectSaveDao");
				Object oldObj = RequestManager.getTOject();
				RequestManager.setTOject(fieldInst);
				dao.paramVoidResultExecute(null);
				RequestManager.setTOject(oldObj);
			}
			 else if(strArray[1].equals("user")) {
					if (strArray.length < 4)
						continue;

					ReportModel_Field fieldUser = new ReportModel_Field();
					fieldUser.setStrChinaName(strArray[2]);
					fieldUser.setIsEnable("1");
					fieldUser.setIsKey("2");
					fieldUser.setNLength("50");
					fieldUser.setNSeq(strArray[3]);
					fieldUser.setStrDiscription("");
					fieldUser.setStrEmptyType("1");
					fieldUser.setStrLogicEmptyType("1");
					fieldUser.setStrFieldName(strArray[0]);
					fieldUser.setStrFieldType("2");
					fieldUser.setStrConstList(strArray[4]);
					fieldUser.setReportModel_Table(table);

					fieldArray.add(fieldUser);

					// 将字段记录保存到字段表
					IParamVoidResultExecute dao = (IParamVoidResultExecute) FrameworkFactory
							.CreateBean("singleObjectSaveDao");
					Object oldObj = RequestManager.getTOject();
					RequestManager.setTOject(fieldUser);
					dao.paramVoidResultExecute(null);
					RequestManager.setTOject(oldObj);
			 }
			 else if(strArray[1].equals("timestamp")) {
				 ReportModel_Field fieldDate = new ReportModel_Field();
				 fieldDate.setStrChinaName(strArray[2]);
				 fieldDate.setIsEnable("1");
				 fieldDate.setIsKey("2");
				 fieldDate.setNSeq(strArray[3]);
				 fieldDate.setStrDiscription("");
				 fieldDate.setStrEmptyType("1");
				 fieldDate.setStrLogicEmptyType("2");
				 fieldDate.setStrFieldName(strArray[0]);
				 fieldDate.setStrFieldType("7");
				 fieldDate.setReportModel_Table(table);
				 
				 fieldArray.add(fieldDate);
				 					
				 // 将字段记录保存到字段表
				 IParamVoidResultExecute dao =
					 (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
				 Object oldObj = RequestManager.getTOject();
				 RequestManager.setTOject(fieldDate);
				 dao.paramVoidResultExecute(null);
				 RequestManager.setTOject(oldObj);
			 }
			 else if(strArray[1].equals("vchar")) {
				if (strArray.length < 5)
					continue;

				ReportModel_Field fieldVChar = new ReportModel_Field();
				fieldVChar.setStrChinaName(strArray[2]);
				fieldVChar.setIsEnable("1");
				fieldVChar.setIsKey("2");
				fieldVChar.setNLength(strArray[3]);
				fieldVChar.setNSeq(strArray[4]);
				fieldVChar.setStrDiscription("");
				fieldVChar.setStrEmptyType("1");
				fieldVChar.setStrLogicEmptyType("2");
				fieldVChar.setStrFieldName(strArray[0]);
				fieldVChar.setStrFieldType("2");
				fieldVChar.setReportModel_Table(table);
				
				if(strArray.length > 5) {
					fieldVChar.setStrConstList(strArray[5]);
				}
				if(strArray.length > 6) {
					fieldVChar.setStrDBConstList(strArray[6]);
				}

				fieldArray.add(fieldVChar);

				// 将字段记录保存到字段表
				IParamVoidResultExecute dao = (IParamVoidResultExecute) FrameworkFactory
						.CreateBean("singleObjectSaveDao");
				Object oldObj = RequestManager.getTOject();
				RequestManager.setTOject(fieldVChar);
				dao.paramVoidResultExecute(null);
				RequestManager.setTOject(oldObj);
			}
		}

		Collections.sort(fieldArray, new SortByNSeq()); // 排序
		
		String strTablePrefix = "";
		if(table.getStrAutoDTO() != null && table.getStrAutoDTO().equals("1"))
			strTablePrefix = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("tablePrefix");
		String strRet = TableClassGenerator.generateDTO(table.getStrTableName(),table.getStrChinaName(), fieldArray, table.getDataSource(), true, strTablePrefix);
		
		if(!strRet.equals("")) {
			setServiceResult(new MessageResult(false, strRet));
		}
		else {
			setServiceResult(new MessageResult(true, "生成成功!"));
		}

	}

	class SortByNSeq implements Comparator {
		public int compare(Object o1, Object o2) {
			ReportModel_Field s1 = (ReportModel_Field) o1;
			ReportModel_Field s2 = (ReportModel_Field) o2;
			if (s1.getNSeq() > s2.getNSeq())
				return 1;
			return 0;
		}
	}

}
