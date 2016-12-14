package framework.reportCheck;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;


public class CheckTable {
	
	IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("updateSqlListDao");
	
	private String strSessionFactory;
	
	public String getStrSessionFactory() {
		return strSessionFactory;
	}

	public void setStrSessionFactory(String strSessionFactory) {
		this.strSessionFactory = strSessionFactory;
	}
	
	public CheckTable(){
		checkFieldParamFieldList = new ArrayList<CheckFieldParamField>();
		checkUniqueFieldList = new ArrayList<CheckUniqueField>();
		checkTableForeignList = new ArrayList<CheckTableForeign>();
		checkFieldBasicList = new ArrayList<CheckFieldBasic>();
		checkFieldOrList = new ArrayList<CheckFieldOr>();
		checkFieldEffectiveList = new ArrayList<CheckFieldEffective>();
		checkFieldCaseWhenList = new ArrayList<CheckFieldCaseWhen>();
		checkFieldLineList = new ArrayList<CheckFieldLine>();
		checkFieldClassList=new ArrayList<CheckFieldClass>();//XML类校验
	}

	private String getUniqueStr(Map<String,Object> mapObject, List<CheckUniqueField> checkUniqueFieldList){
		String uniqueStr = "唯一属性，";
		for(CheckUniqueField checkUniqueField: checkUniqueFieldList){
			uniqueStr += checkUniqueField.getDiscription() + "：" + mapObject.get(checkUniqueField.getName().toUpperCase()) + "，";
		}
		return uniqueStr;
	}
	

	private String getPrcedureString(String procedureName,String procedureParam){
		if(procedureParam == null){
			procedureParam = "";
		}
		
		String[] procedureParams = procedureParam.split(",");
		String callprcedure = "";
		
		if(!StringUtils.isBlank(procedureParam)){
			for(int i =0;i<procedureParams.length;i++){
				if(!StringUtils.isBlank(callprcedure)){
					callprcedure += ",";
				}
				callprcedure += "?";
			}
		}

		String prcedureString = "{call " + procedureName +"(" + callprcedure + ")}";
		return prcedureString;
	}

	public String successPreSql() throws Exception{

		String strSQL = "update " + this.getOriginTable() + " set ";
		if(this.getErrorStateField() != null && !this.getErrorStateField().equals("")
				&& this.getSuccessStateValue() != null && !this.getSuccessStateValue().equals(""))
			strSQL += this.getErrorStateField() + " = ? ";
		else
			return ""; 

		String strWhere = "";
		for(CheckUniqueField checkUniqueField: checkUniqueFieldList){
			if(!strWhere.equals("")) strWhere += " and ";
			strWhere += checkUniqueField.getOriginField() + "= ?";
		}
		
		strSQL += " where " + strWhere;
		if(this.getOriginTable() != null && !this.getOriginTable().equals("")) {
			return strSQL;
		}
		return "";
	}
	
	public String errorPreSql() throws Exception{

		String strSQL = "update " + this.getOriginTable() + " set ";
		if(this.getErrorMsgField() != null && !this.getErrorMsgField().equals("")) {
			strSQL += this.getErrorMsgField() + " =  ? ";
			
			if(this.getErrorStateField() != null && !this.getErrorStateField().equals("")
					&& this.getErrorStateValue() != null && !this.getErrorStateValue().equals(""))
				strSQL += ", " + this.getErrorStateField() + " = ? ";
		}
		else if(this.getErrorStateField() != null && !this.getErrorStateField().equals("")
				&& this.getErrorStateValue() != null && !this.getErrorStateValue().equals(""))
			strSQL += this.getErrorStateField() + " = ? ";
		else
			return ""; // 不记录出错信息

		String strWhere = "";
		for(CheckUniqueField checkUniqueField: checkUniqueFieldList){
			if(!strWhere.equals("")) strWhere += " and ";
			strWhere += checkUniqueField.getOriginField() + "= ?";
		}
		
		strSQL += " where " + strWhere;
		if(this.getOriginTable() != null && !this.getOriginTable().equals("") && !strWhere.equals("")) {
			return strSQL;
		}
		return "";
	}
	
	/**
	 * 修改基础段的扩展字段5的值为1
	 * @param strTableName 基础段
	 * @param idList 主键值列表
	 * @throws Exception
	 */
	public void preSql(String strTableName,List<String> idList) throws Exception{
		String strSql = "update " + strTableName + " set extend5='1' where autoID=? ";
		List<List<String>> list = new ArrayList<List<String>>();
		if(idList.size()>0){
			for(String id:idList){
				List<String> l = new ArrayList<String>();
				l.add(id.trim());
				list.add(l);
			}
			ExcuteSql(strSql, list);
		}
	}
	
	public List<String> writeSuccessMsg(Map<String,Object> mapObject) throws Exception{
		List<String> list = new ArrayList<String>();
		
		if(this.getErrorStateField() != null && !this.getErrorStateField().equals("")
				&& this.getSuccessStateValue() != null && !this.getSuccessStateValue().equals(""))
			list.add(this.getSuccessStateValue());
		else
			list.add(""); 
		
		for(CheckUniqueField checkUniqueField: checkUniqueFieldList){
			list.add(mapObject.get(checkUniqueField.getName().toUpperCase()).toString()); 
		}
		
		return list;
	}
	
	// 将出错信息写入原表出错信息字段中
	public List<String> writeErrorMsg(Map<String,Object> mapObject, String errMsg) {
		List<String> list = new ArrayList<String>();

		if(this.getErrorMsgField() != null && !this.getErrorMsgField().equals("")) {
			list.add(errMsg);
			
			if(this.getErrorStateField() != null && !this.getErrorStateField().equals("")
					&& this.getErrorStateValue() != null && !this.getErrorStateValue().equals(""))
				list.add(this.getErrorStateValue());
		}
		else if(this.getErrorStateField() != null && !this.getErrorStateField().equals("")
				&& this.getErrorStateValue() != null && !this.getErrorStateValue().equals(""))
			list.add(this.getErrorStateValue());
		else
			return null; // 不记录出错信息


		for(CheckUniqueField checkUniqueField: checkUniqueFieldList){
			list.add(mapObject.get(checkUniqueField.getName().toUpperCase()).toString());
		}
		
		return list;
	}
	
	private Map<String,Object> getProcedureParamMap(String procedureParam){
		if(procedureParam == null){
			procedureParam = "";
		}
		
		String[] procedureParams = procedureParam.split(",");
		Map<String,Object> procedureParamMap = new LinkedHashMap<String,Object>();
		if(!StringUtils.isBlank(procedureParam)){
			for(int i =0;i<procedureParams.length;i++){
				boolean isHaveValue = false;
				for(CheckFieldParamField checkFieldParamField : this.checkFieldParamFieldList){
					if(checkFieldParamField.getParamName().equals(procedureParams[i])){
						if(StringUtils.isBlank(checkFieldParamField.getValue())
								&& StringUtils.isBlank(checkFieldParamField.getDefaultValue())){
							procedureParamMap.put(procedureParams[i], null);
						}
					
						if(StringUtils.isBlank(checkFieldParamField.getValue())) // 常量参数,直接传
							procedureParamMap.put(procedureParams[i], checkFieldParamField.getDefaultValue());
						else if(checkFieldParamField.getType().equals("")
							|| checkFieldParamField.getType().equals("1")){
							procedureParamMap.put(procedureParams[i], checkFieldParamField.getValue());
						}
						else if(checkFieldParamField.getType().equals("2")){
							Date procedureDate = TypeParse.parseDate(checkFieldParamField.getValue());
							if(procedureDate.equals(TypeParse.maxDate())){
								procedureParamMap.put("", "参数" + checkFieldParamField.getParamName() + "：时间类型错误。");
								return procedureParamMap;  
							}
							procedureDate = java.sql.Date.valueOf(TypeParse.parseString(procedureDate, "yyyy-MM-dd"));
							procedureParamMap.put(procedureParams[i], procedureDate);
						}
						isHaveValue = true;
						break;
					}
				}
				if(!isHaveValue){
					procedureParamMap.put(procedureParams[i], null);
				}
			}
		}
		return procedureParamMap;
	}
	
	/**
	 * <p>方法描述: 获取存储过程的结果集</p>
	 * <p>方法备注: </p>
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-4-28 下午3:29:28</p>
	 *
	 */
	public ResultSet getProcdureResulsetData() throws Exception{
		
		String procedureName = this.getProcedureName();
		String procedureParam = this.getProcedureParam();
		//根据驱动获取当前使用的数据库类型
		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(new Object[]{this.getStrSessionFactory()});
		if(dataBaseType!=null && dataBaseType.equals("oracle")){//oracle数据库加rt参数
			procedureParam="rt,"+procedureParam;
		}
		String defaultLogicDaoBeanId = this.getDefaultLogicDaoBeanId(dataBaseType);
		if(defaultLogicDaoBeanId == null || defaultLogicDaoBeanId.equals("")){
			defaultLogicDaoBeanId = LogicParamManager.getDefaultLogicDaoBeanId();
		}
		
		Map<String,Object> procedureParamMap = getProcedureParamMap(procedureParam);
		
		if(procedureParamMap.containsKey("")){
			throw new Exception("存储过程参数错误");
		}
		
		String strProcedureParam = getPrcedureString(procedureName,procedureParam);
		IParamObjectResultExecute procedureListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultLogicDaoBeanId);
 		return (ResultSet)procedureListMapDao.paramObjectResultExecute(new Object[]{strProcedureParam,procedureParamMap,this.getStrSessionFactory()});
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> Check(Object param,MessageResult messageResult,List<IParamObjectResultExecute> iCheckList,String strEntityClass) throws Exception {
		
		List<Integer> statisticList = new ArrayList();
		String defaultLogicDaoBeanId = this.getDefaultLogicDaoBeanId();
		if(defaultLogicDaoBeanId == null || defaultLogicDaoBeanId.equals("")){
			defaultLogicDaoBeanId = LogicParamManager.getDefaultLogicDaoBeanId();
		}
		String procedureName = this.getProcedureName();
		String procedureParam = this.getProcedureParam();
		
		//根据驱动获取当前使用的数据库类型
		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(new Object[]{this.getStrSessionFactory()});
		if(dataBaseType!=null && dataBaseType.equals("oracle")){//oracle数据库加rt参数
			procedureParam="rt,"+procedureParam;
		}
		
		Map<String,Object> procedureParamMap = getProcedureParamMap(procedureParam);
		
		if(procedureParamMap.containsKey("")){
			messageResult.AddLineSplitFlag();
			messageResult.getMessageList().add(procedureParamMap.get("").toString());
			return statisticList;
		}
		
		String strProcedureParam = getPrcedureString(procedureName,procedureParam);
		IParamObjectResultExecute procedureListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultLogicDaoBeanId);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");

		Map<String,Boolean> errorStateMap = new HashMap<String,Boolean>();
		List<Map<String,Object>> valueMapList=new ArrayList<Map<String,Object>>();
 		ResultSet resultSet = (ResultSet)procedureListMapDao.paramObjectResultExecute(new Object[]{strProcedureParam,procedureParamMap,this.getStrSessionFactory()});
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int fieldCount = resultSetMetaData.getColumnCount(); 
		int caschLine=10000;
		int totalLine=10000;
		int i=0;
		int baseErrorCount=0;
		List<String> baseMessageList=new ArrayList<String>();
		int classErrorCount=0;
		List<String> classMessageList=new ArrayList<String>();
		int effectiveErrorCount=0;
		List<String> effectiveMessageList=new ArrayList<String>();
		int caseWhenErrorCount=0;
		List<String> caseWhenMessageList=new ArrayList<String>();
		int fieldOrErrorCount=0;
		List<String> fieldOrMessageList=new ArrayList<String>();
		int checkFieldErrorCount=0;
		List<String> checkFieldMessageList=new ArrayList<String>();
		int checkFieldClassErrorCount=0;
		List<String> checkFieldClassMassageList=new ArrayList<String>();
		int totalCount=0;
		String successPreSql= successPreSql();
		String errorPreSql= errorPreSql();
		List<List<String>> successList = new ArrayList<List<String>>();
		List<List<String>> errorList = new ArrayList<List<String>>();
		while (resultSet.next()){
			Map<String,Object> valueMap = new LinkedHashMap<String, Object>();  
            boolean isAdd=false;
			for (int j = 1; j <= fieldCount; j++) {  
                String fieldName = resultSetMetaData.getColumnName(j);   
                Object object = resultSet.getObject(fieldName);
                if(!StringUtils.isBlank(fieldName) && fieldName.toUpperCase().equals("RPTCHECKTYPE") && object!=null){
                	if(object.toString().trim().equals("1") || object.toString().trim().equals("4")){//仅校验‘未校验’和‘未全部校验’的数据
                		isAdd=true;
                	}
                }
                valueMap.put(fieldName.toUpperCase(), object);
            }
			if(isAdd){
				valueMapList.add(valueMap);
			}
 		
		}
		
		resultSet.close();
		
		List<Map<String, Object>> _valueMapList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : valueMapList) {
			i++;
			String uniqueStr = getUniqueStr(map, checkUniqueFieldList);
			errorStateMap.put(uniqueStr, true);
			_valueMapList.add(map);
			if(this.checkFieldBasicList.size()>0){
	            for(CheckFieldBasic checkFieldBasic : this.checkFieldBasicList) { // 每个基本校验
					
					List<String> messageList = checkFieldBasic.Check(map, uniqueStr); // 检验一行
					for(String message : messageList){
						if(totalCount<totalLine){
							baseMessageList.add(message);
						}
					}
					baseErrorCount += messageList.size();
					totalCount += messageList.size();
					if(messageList.size() > 0){
						errorStateMap.put(uniqueStr, false);
					}
				}
			}
			if(!StringUtils.isBlank(strEntityClass)){
				if(checkUniqueFieldList.size()>0){
					String idValue = "";
					for(CheckUniqueField checkUniqueField: checkUniqueFieldList){
						idValue = map.get(checkUniqueField.getName().toUpperCase()).toString();
					}
					
					singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					Object tObject = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{strEntityClass,idValue,this.getStrSessionFactory()});
					for(IParamObjectResultExecute icheck  : iCheckList){
						
						MessageResult classCheckMessage = (MessageResult)icheck.paramObjectResultExecute(tObject);
						
						List<String> messageList = new ArrayList<String>();
						for(String str :classCheckMessage.getMessageList()){
							messageList.add(str);
						}
						for(String str :classCheckMessage.getMessageSet()){
							messageList.add(str);
						}
						for(ErrorField errorField: classCheckMessage.getErrorFieldList()){
							messageList.add(errorField.getMessage());
						}

						for(String message : messageList){
							if(totalCount<totalLine){
								classMessageList.add(message);
							}
						}
						classErrorCount += messageList.size();
						totalCount += messageList.size();
						if(messageList.size() > 0){
							errorStateMap.put(uniqueStr, false);
						}
					}
				}
			}
			
			if(this.checkFieldEffectiveList.size()>0){
	           for(CheckFieldEffective checkFieldEffective : this.checkFieldEffectiveList){
					List<String> messageList = checkFieldEffective.Check(map,uniqueStr,null);
					
					for(String message : messageList){
						if(totalCount<totalLine){
							effectiveMessageList.add(message);
						}
					}
					effectiveErrorCount += messageList.size();
					totalCount += messageList.size();
					if(messageList.size() > 0){
						errorStateMap.put(uniqueStr, false);
					}
				}
			}
			
			if(this.checkFieldCaseWhenList.size()>0){
				for(CheckFieldCaseWhen checkFieldCaseWhen : this.checkFieldCaseWhenList){

					List<String> messageList = checkFieldCaseWhen.Check(map,uniqueStr,null);

					for(String message : messageList){
						if(totalCount<totalLine){
							caseWhenMessageList.add(message);
						}
					}
					caseWhenErrorCount += messageList.size();
					totalCount += messageList.size();
					if(messageList.size() > 0){
						errorStateMap.put(uniqueStr, false);
					}
				}
			}
			
			if(this.checkFieldOrList.size()>0){
				 for(CheckFieldOr checkFieldOr : this.checkFieldOrList){
					List<String> messageList = checkFieldOr.Check(map,uniqueStr);
					if(messageList.size() > 0){
						if(totalCount<totalLine){
							fieldOrMessageList.add(MessageResult.SHORTSPLITFLAG);
						}
						fieldOrErrorCount += messageList.size();
						totalCount += messageList.size();
					}
					for(String message : messageList){
						if(totalCount<totalLine){
							fieldOrMessageList.add(message);
						}
					}
					if(messageList.size() > 0){
						if(totalCount<totalLine){
							fieldOrMessageList.add(MessageResult.SHORTSPLITFLAG);
						}
					}
					
					if(messageList.size() > 0){
						errorStateMap.put(uniqueStr, false);
					}
				}
			}
			
			for(CheckFieldLine checkFieldLine : this.checkFieldLineList){

				List<String> messageList = checkFieldLine.Check(map,uniqueStr);
				
				String errMsg = "";
				for(String message : messageList){
					if(totalCount<totalLine){
						checkFieldMessageList.add(message);
					}
					errMsg += message + "\r\n"; // 生成行出错信息
				}
				checkFieldErrorCount += messageList.size();
				totalCount += messageList.size();
				
				if(messageList.size() > 0){
					errorStateMap.put(uniqueStr, false);
				}
			}
			
			if(this.checkFieldClassList.size()>0){
	            for(CheckFieldClass checkFieldClass: this.checkFieldClassList) { // XML类校验
					List<String> messageList = checkFieldClass.Check(map);
					for(String message : messageList){
						if(totalCount<totalLine){
							checkFieldClassMassageList.add(message);
						}
					}
					checkFieldClassErrorCount += messageList.size();
					totalCount += messageList.size();
					if(messageList.size() > 0){
						errorStateMap.put(uniqueStr, false);
					}
				}
			}
			
			if(errorStateMap.get(uniqueStr)){
				successList.add(writeSuccessMsg(map));
			}
			else{
				errorList.add(writeErrorMsg(map, "Error"));
			}
			
			if(i>caschLine){
				ExcuteSql(successPreSql, successList); // A执行语句--------------
				ExcuteSql(errorPreSql, errorList);
				successList.clear();
				errorList.clear();
				
				//**将校验过的数据的基础段的extend5改为1：大于10000行时执行*//
				if(_valueMapList.size()>0){
					for(int ii = 0;ii<3;ii++){
						String strTableName=getJCTableName(_valueMapList);//获取基础段表名
						List<String> idList=getAutoIdList(_valueMapList);//获取基础段主键
						preSql(strTableName,idList);//获取sql语句以及执行sql语句
					}
				}
				_valueMapList=new ArrayList<Map<String,Object>>();
				errorStateMap = new HashMap<String,Boolean>();
				i = 0;
			}
		}
		
		/**将校验过的数据的基础段的extend5改为1：小于10000行时执行*/
		if(_valueMapList.size()>0){
			String strTableName=getJCTableName(_valueMapList);//获取基础段表名
			List<String> idList=getAutoIdList(_valueMapList);//获取基础段主键
			preSql(strTableName,idList);//获取sql语句以及执行sql语句
		}
		
		ExcuteSql(successPreSql, successList); // B执行语句--------------
		ExcuteSql(errorPreSql, errorList);
		
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("基本校验：");
		for (String string : baseMessageList) {
			messageResult.getMessageList().add(string);
		}
		int currentSize = messageResult.getMessageList().size();
		messageResult.getMessageList().add(currentSize, "错误条数：" + baseErrorCount);
		statisticList.add(baseErrorCount);
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("类校验：");
		for (String string : classMessageList) {
			messageResult.getMessageList().add(string);
		}
		currentSize = messageResult.getMessageList().size();
		
		messageResult.getMessageList().add(currentSize, "错误条数：" + classErrorCount);
		statisticList.add(classErrorCount);

		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("数据有效性校验：");
		for (String string : effectiveMessageList) {
			messageResult.getMessageList().add(string);
		}
		currentSize = messageResult.getMessageList().size();
		
		messageResult.getMessageList().add(currentSize,"错误条数：" + effectiveErrorCount);
		statisticList.add(effectiveErrorCount);
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("CaseWhen校验：");
		for (String string : caseWhenMessageList) {
			messageResult.getMessageList().add(string);
		}
		currentSize = messageResult.getMessageList().size();
		
		messageResult.getMessageList().add(currentSize,"错误条数：" + caseWhenErrorCount);
		statisticList.add(caseWhenErrorCount);
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("条件校验：");
		for (String string : fieldOrMessageList) {
			messageResult.getMessageList().add(string);
		}
		currentSize = messageResult.getMessageList().size();
		
		messageResult.getMessageList().add(currentSize,"错误条数：" + fieldOrErrorCount);
		statisticList.add(fieldOrErrorCount);
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("非聚合校验：");
		for (String string : checkFieldMessageList) {
			messageResult.getMessageList().add(string);
		}
		currentSize = messageResult.getMessageList().size();

		messageResult.getMessageList().add(currentSize,"错误条数：" + checkFieldErrorCount);
		statisticList.add(checkFieldErrorCount);
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("XML类校验：");
		for (String string : checkFieldClassMassageList) {
			messageResult.getMessageList().add(string);
		}
		currentSize = messageResult.getMessageList().size();
		messageResult.getMessageList().add(currentSize, "错误条数：" + checkFieldClassErrorCount);
		statisticList.add(checkFieldClassErrorCount);
		
		return statisticList;
    }

	/**
	 * 获取基础段的AutoID
	 * @param objectAllList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> getAutoIdList(List<Map<String, Object>> objectAllList) {
		List<String> idList=new ArrayList<String>();
		boolean isFlag=false;//判断是否为明细段
		for (Map<String, Object> map2 : objectAllList) {
			if(map2.containsKey("FOREIGNID")){
				isFlag=true;
				break;
			}
		}
		if(isFlag){//如果是明细段则获取FOREIGNID
			for (Map<String, Object> map2 : objectAllList) {
				idList.add(map2.get("FOREIGNID").toString());
			}
			
		}else{//如果是基础段则获取AutoID
			for (Map<String, Object> map2 : objectAllList) {
				idList.add(map2.get("AUTOID").toString());
			}
		}
		
		if(idList.size()>0){
			List<String> listWithoutDup = new ArrayList(new HashSet(idList));//去重
			return listWithoutDup;
		}else{
			return idList;
		}
	}

	/**
	 * 获取基础段的表名
	 * @param objectAllList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String getJCTableName(List<Map<String, Object>> objectAllList) throws Exception {
		boolean isFlag=false;//判断是否为明细段
		String jcTableName="";
		for (Map<String, Object> map2 : objectAllList) {
			if(map2.containsKey("FOREIGNID")){
				isFlag=true;
				break;
			}
		}
		if(isFlag){//是明细段，则根据明细段得到基础段表名
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Table"));
			detachedCriteria.add(Restrictions.eq("strTableName", this.getOriginTable()));
			List<Object> reportModel_TableList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});
			
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Field"));
			detachedCriteria.add(Restrictions.eq("strFieldName", "FOREIGNID"));
			detachedCriteria.add(Restrictions.eq("reportModel_Table", reportModel_TableList.get(0)));
			List<Object> reportModel_FieldList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});
			String strConstList="";
			if(reportModel_FieldList.size()>0){
				if(ReflectOperation.getFieldValue(reportModel_FieldList.get(0), "strConstList")!=null){
					strConstList=ReflectOperation.getFieldValue(reportModel_FieldList.get(0), "strConstList").toString();
				}
			}
			
			if(strConstList.indexOf("-")>-1){
				String[] strArr=strConstList.split("-");
				jcTableName= strArr[0].substring(strArr[0].indexOf("_")+1);
			}
		}
		
		if(isFlag){
			return jcTableName;
		}else{
			return this.getOriginTable();
		}
		
	}

	public void ExcuteSql(String strSql, List<List<String>> list){
		if(!"".equals(strSql)){
			try {
				singleObjectUpdateDao.paramVoidResultExecute(new Object[]{strSql, list, this.getStrSessionFactory()});
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private String periodFieldField;
	private List<CheckFieldParamField> checkFieldParamFieldList;
	private List<CheckUniqueField> checkUniqueFieldList;
	private List<CheckTableForeign> checkTableForeignList;
	private List<CheckFieldBasic> checkFieldBasicList;
	private List<CheckFieldOr> checkFieldOrList;
	private List<CheckFieldEffective> checkFieldEffectiveList;
	private List<CheckFieldCaseWhen> checkFieldCaseWhenList;
	private List<CheckFieldLine> checkFieldLineList;
	private List<CheckFieldClass> checkFieldClassList;
	
	private String procedureName;
	private String procedureParam;
	private String procedureDiscription;
	private String originTable; // 原表名，用于写入记录出错信息
	private String errorMsgField; // 出错信息
	private String errorStateField; // 出错状态字段
	private String errorStateValue; // 出错状态值
	private String successStateValue;
	private String defaultLogicDaoBeanId;

	public void setCheckUniqueFieldList(List<CheckUniqueField> checkUniqueFieldList) {
		this.checkUniqueFieldList = checkUniqueFieldList;
	}

	public List<CheckUniqueField> getCheckUniqueFieldList() {
		return checkUniqueFieldList;
	}

	public void setCheckTableForeignList(List<CheckTableForeign> checkTableForeignList) {
		this.checkTableForeignList = checkTableForeignList;
	}

	public List<CheckTableForeign> getCheckTableForeignList() {
		return checkTableForeignList;
	}

	public void setCheckFieldBasicList(List<CheckFieldBasic> checkFieldBasicList) {
		this.checkFieldBasicList = checkFieldBasicList;
	}

	public List<CheckFieldBasic> getCheckFieldBasicList() {
		return checkFieldBasicList;
	}

	public void setCheckFieldOrList(List<CheckFieldOr> checkFieldOrList) {
		this.checkFieldOrList = checkFieldOrList;
	}

	public List<CheckFieldOr> getCheckFieldOrList() {
		return checkFieldOrList;
	}

	public List<CheckFieldParamField> getCheckFieldParamFieldList() {
		return checkFieldParamFieldList;
	}

	public void setCheckFieldParamFieldList(
			List<CheckFieldParamField> checkFieldParamFieldList) {
		this.checkFieldParamFieldList = checkFieldParamFieldList;
	}

	public List<CheckFieldEffective> getCheckFieldEffectiveList() {
		return checkFieldEffectiveList;
	}

	public void setCheckFieldEffectiveList(
			List<CheckFieldEffective> checkFieldEffectiveList) {
		this.checkFieldEffectiveList = checkFieldEffectiveList;
	}

	public List<CheckFieldCaseWhen> getCheckFieldCaseWhenList() {
		return checkFieldCaseWhenList;
	}

	public void setCheckFieldCaseWhenList(
			List<CheckFieldCaseWhen> checkFieldCaseWhenList) {
		this.checkFieldCaseWhenList = checkFieldCaseWhenList;
	}

	public void setPeriodFieldField(String periodFieldField) {
		this.periodFieldField = periodFieldField;
	}

	public String getPeriodFieldField() {
		return periodFieldField;
	}

	public void setCheckFieldLineList(List<CheckFieldLine> checkFieldLineList) {
		this.checkFieldLineList = checkFieldLineList;
	}

	public List<CheckFieldLine> getCheckFieldLineList() {
		return checkFieldLineList;
	}
	
	public List<CheckFieldClass> getCheckFieldClassList() {
		return checkFieldClassList;
	}

	public void setCheckFieldClassList(List<CheckFieldClass> checkFieldClassList) {
		this.checkFieldClassList = checkFieldClassList;
	}

	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
	}

	public String getProcedureDiscription() {
		if(StringUtils.isBlank(procedureDiscription)){
			return this.procedureName;
		}
		return procedureDiscription;
	}

	public void setProcedureParam(String procedureParam) {
		this.procedureParam = procedureParam;
	}

	public String getProcedureParam() {
		return procedureParam;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setOriginTable(String originTable) {
		this.originTable = originTable;
	}

	public String getOriginTable() {
		return originTable;
	}

	public void setErrorMsgField(String errorMsgField) {
		this.errorMsgField = errorMsgField;
	}

	public String getErrorMsgField() {
		return errorMsgField;
	}

	public void setErrorStateField(String errorStateField) {
		this.errorStateField = errorStateField;
	}

	public String getErrorStateField() {
		return errorStateField;
	}

	public void setErrorStateValue(String errorStateValue) {
		this.errorStateValue = errorStateValue;
	}

	public String getErrorStateValue() {
		return errorStateValue;
	}

	public void setDefaultLogicDaoBeanId(String defaultLogicDaoBeanId){
		this.defaultLogicDaoBeanId = defaultLogicDaoBeanId;
	}

	public String getDefaultLogicDaoBeanId() throws Exception {
		//根据驱动获取当前使用的数据库类型
		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(new Object[]{this.getStrSessionFactory()});
		if(dataBaseType!=null){
			if(dataBaseType.equals("sqlserver")|| dataBaseType.equals("mySql")){
				defaultLogicDaoBeanId="sqlserverProcedureResultSetDao";
			}
			else if(dataBaseType.equals("oracle")){
				defaultLogicDaoBeanId="oracleProcedureResultSetDao";
			}
			else if(dataBaseType.equals("db2")){
				defaultLogicDaoBeanId="db2ProcedureResultSetDao";
			}
		}
		return defaultLogicDaoBeanId;
	}

	
	private String getDefaultLogicDaoBeanId(String dataBaseType) throws Exception {
		//根据驱动获取当前使用的数据库类型
		if(dataBaseType!=null){
			if(dataBaseType.equals("sqlserver")|| dataBaseType.equals("mySql")){
				defaultLogicDaoBeanId="sqlserverProcedureResultSetDao";
			}
			else if(dataBaseType.equals("oracle")){
				defaultLogicDaoBeanId="oracleProcedureResultSetDao";
			}
			else if(dataBaseType.equals("db2")){
				defaultLogicDaoBeanId="db2ProcedureResultSetDao";
			}
		}
		return defaultLogicDaoBeanId;
	}
	
	public void setSuccessStateValue(String successStateValue) {
		this.successStateValue = successStateValue;
	}

	public String getSuccessStateValue() {
		return successStateValue;
	}

}
