package framework.reportCheck;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.w3c.dom.Element;

import framework.helper.DomXMLOperation;
import framework.helper.SqlConstructor.ConditionClass;

public class CheckContext {
	
	private static CheckContext checkContext = null; 
	
	synchronized public static CheckContext getInstance() throws Exception{  
        if (checkContext == null)  {
        	checkContext = new CheckContext();  
        }
        return checkContext;  
    } 
	
	public static void Init() throws Exception{  
        checkContext = null;
    } 
	
	private void AddCheckFieldBasicListElement(CheckTable checkTable,CheckFieldOr checkFieldOr,Element checkFieldBasicListElement){
		Element[] checkFieldBasicsElement = DomXMLOperation.getElementsByName(checkFieldBasicListElement,"checkFieldBasic");
		for(int k=0;k<checkFieldBasicsElement.length;k++){
			CheckFieldBasic checkFieldBasic = new CheckFieldBasic();
			
			checkFieldBasic.setName(DomXMLOperation.getElementAttr(checkFieldBasicsElement[k],"name"));
			checkFieldBasic.setDiscription(DomXMLOperation.getElementAttr(checkFieldBasicsElement[k],"discription"));
			checkFieldBasic.setStartLength(DomXMLOperation.getElementIntegerAttr(checkFieldBasicsElement[k], "startLength"));
			checkFieldBasic.setEndLength(DomXMLOperation.getElementIntegerAttr(checkFieldBasicsElement[k], "endLength"));
			checkFieldBasic.setStartByteLength(DomXMLOperation.getElementIntegerAttr(checkFieldBasicsElement[k], "startByteLength"));
			checkFieldBasic.setEndByteLength(DomXMLOperation.getElementIntegerAttr(checkFieldBasicsElement[k], "endByteLength"));
			checkFieldBasic.setValueDecimalLength(DomXMLOperation.getElementIntegerAttr(checkFieldBasicsElement[k], "valueDecimalLength"));
			checkFieldBasic.setMustValueDecimalLength(DomXMLOperation.getElementIntegerAttr(checkFieldBasicsElement[k], "mustValueDecimalLength"));
			checkFieldBasic.setValueMin(DomXMLOperation.getElementAttr(checkFieldBasicsElement[k], "valueMin"));
			checkFieldBasic.setValueMax(DomXMLOperation.getElementAttr(checkFieldBasicsElement[k], "valueMax"));
			checkFieldBasic.setIgnoreVal(DomXMLOperation.getElementAttr(checkFieldBasicsElement[k], "ignoreVal"));
			checkFieldBasic.setNotEqualValue(DomXMLOperation.getElementAttr(checkFieldBasicsElement[k], "notEqualValue"));
			checkFieldBasic.setRegularExpressionCheck(DomXMLOperation.getElementAttr(checkFieldBasicsElement[k], "regularExpressionCheck"));
			checkFieldBasic.setRegularExpressionCheckDisc(DomXMLOperation.getElementAttr(checkFieldBasicsElement[k], "regularExpressionCheckDisc"));
			
			Boolean emptyCheck = DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"emptyCheck");
			Boolean chnCheck = DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"chnCheck");
			Boolean noChnCheck = DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"noChnCheck");
			Boolean upperCheck = DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"upperCheck");
			Boolean digitalCheck = DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"digitalCheck");
			Boolean dateCheck = DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"dateCheck");
			Boolean numAndBeginNum = DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"numAndBeginNum");
			Boolean dateCheckForSixBit=DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"dateCheckForSixBit");
			Boolean systemIDCardNo=DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"systemIDCardNo");
			Boolean systemZZJGDM=DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"systemZZJGDM");
			Boolean systemDKKBM=DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"systemDKKBM");
			Boolean systemJRJGDM=DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"systemJRJGDM");
			Boolean systemKHXKZHZH=DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"systemKHXKZHZH");
			Boolean systemNSRSBH=DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"systemNSRSBH");
			Boolean systemJGXYDM=DomXMLOperation.getElementBooleanAttr(checkFieldBasicsElement[k],"systemJGXYDM");
		
			if(emptyCheck != null){
				checkFieldBasic.setEmptyCheck(emptyCheck);
			}
            if(chnCheck != null){
            	checkFieldBasic.setChnCheck(chnCheck);
			}
            if(noChnCheck != null){
            	checkFieldBasic.setNoChnCheck(noChnCheck);
			}
			if(upperCheck != null){
				checkFieldBasic.setUpperCheck(upperCheck);		
			}
			if(digitalCheck != null){
				checkFieldBasic.setDigitalCheck(digitalCheck);
			}
			if(dateCheck != null){
				checkFieldBasic.setDateCheck(dateCheck);
			}
			
			if(checkTable != null){
				checkTable.getCheckFieldBasicList().add(checkFieldBasic);
			}
			if(checkFieldOr != null){
				checkFieldOr.getCheckFieldBasicList().add(checkFieldBasic);
			}
			if(numAndBeginNum != null){
				checkFieldBasic.setNumAndBeginNum(numAndBeginNum);
			}
			if(dateCheckForSixBit != null){
				checkFieldBasic.setDateCheckForSixBit(dateCheckForSixBit);
			}
			if(systemIDCardNo != null){
				checkFieldBasic.setSystemIDCardNo(systemIDCardNo);
			}
			if(systemJRJGDM != null){
				checkFieldBasic.setSystemJRJGDM(systemJRJGDM);
			}
			if(systemZZJGDM != null){
				checkFieldBasic.setSystemZZJGDM(systemZZJGDM);
			}
			if(systemDKKBM != null){
				checkFieldBasic.setSystemDKKBM(systemDKKBM);
			}
			if(systemKHXKZHZH != null){
				checkFieldBasic.setSystemKHXKZHZH(systemKHXKZHZH);
			}
			if(systemNSRSBH != null){
				checkFieldBasic.setSystemNSRSBH(systemNSRSBH);
			}
			if(systemJGXYDM != null){
				checkFieldBasic.setSystemJGXYDM(systemJGXYDM);
			}
			
		}
	}
	
    private void AddCheckFieldCaseWhenListElement(CheckTable checkTable,CheckFieldOr checkFieldOr,Element checkFieldCaseWhenListElement){
    	Element[] checkFieldCaseWhensElement = DomXMLOperation.getElementsByName(checkFieldCaseWhenListElement,"checkFieldCaseWhen");
    	for(int k=0;k<checkFieldCaseWhensElement.length;k++){
    		CheckFieldCaseWhen checkFieldCaseWhen = new CheckFieldCaseWhen();
    		checkFieldCaseWhen.setName(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "name"));
    		checkFieldCaseWhen.setDiscription(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "discription"));
    		checkFieldCaseWhen.setCaseValue(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "caseValue"));
    		checkFieldCaseWhen.setCaseCompareType(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "caseCompareType"));
    		checkFieldCaseWhen.setWhenValue(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "whenValue"));
    		checkFieldCaseWhen.setCaseValueScope(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "caseValueScope"));
    		checkFieldCaseWhen.setWhenValueScope(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "whenValueScope"));
    		checkFieldCaseWhen.setWhenType(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "whenType"));
    		checkFieldCaseWhen.setWhenCompareType(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "whenCompareType"));
    		checkFieldCaseWhen.setWhenCompareValue(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "whenCompareValue"));
    		checkFieldCaseWhen.setCaseValueDiscription(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "caseValueDiscription"));
    		/**
    		 * 2016/08/04  刘涛  新增自定义错误提示信息
    		 */
    		checkFieldCaseWhen.setErrorMsg(DomXMLOperation.getElementAttr(checkFieldCaseWhensElement[k], "errorMsg"));
    		
    		Element[] checkFieldCaseWhenFieldsElement = DomXMLOperation.getElementsByName(checkFieldCaseWhensElement[k],"checkFieldCaseWhenField");
    		for(int l=0;l<checkFieldCaseWhenFieldsElement.length;l++){
    			CheckFieldCaseWhenField checkFieldCaseWhenField = new CheckFieldCaseWhenField();
    			checkFieldCaseWhenField.setName(DomXMLOperation.getElementAttr(checkFieldCaseWhenFieldsElement[l], "name"));
    			checkFieldCaseWhenField.setDiscription(DomXMLOperation.getElementAttr(checkFieldCaseWhenFieldsElement[l], "discription"));
    			checkFieldCaseWhenField.setJoinType(DomXMLOperation.getElementAttr(checkFieldCaseWhenFieldsElement[l], "joinType"));
    			checkFieldCaseWhenField.setWhenValueRule(DomXMLOperation.getElementAttr(checkFieldCaseWhenFieldsElement[l], "whenValueRule"));
    			checkFieldCaseWhenField.setWhenValue(DomXMLOperation.getElementAttr(checkFieldCaseWhenFieldsElement[l], "whenValue"));
    			checkFieldCaseWhenField.setWhenValueScope(DomXMLOperation.getElementAttr(checkFieldCaseWhenFieldsElement[l], "whenValueScope"));
    			checkFieldCaseWhenField.setWhenCompareType(DomXMLOperation.getElementAttr(checkFieldCaseWhenFieldsElement[l], "whenCompareType"));
    			checkFieldCaseWhenField.setCaseCompareType(DomXMLOperation.getElementAttr(checkFieldCaseWhenFieldsElement[l], "caseCompareType"));
    			checkFieldCaseWhen.getCheckFieldCaseWhenFieldList().add(checkFieldCaseWhenField);
    		}
    		
    		if(checkTable != null){
        		checkTable.getCheckFieldCaseWhenList().add(checkFieldCaseWhen);
			}
			if(checkFieldOr != null){
				checkFieldOr.getCheckFieldCaseWhenList().add(checkFieldCaseWhen);
			}
    	}
	}
	
	private CheckContext() throws Exception { 
		checkInstanceMap = new LinkedHashMap<String, CheckInstance>();
		for(int f=0;f<reportCheckLocation.length;f++){
    		Element reportCheckRootElement = DomXMLOperation.getElementByName(reportCheckLocation[f],"reportCheckRoot");
           	Element[] checkInstanceElements = DomXMLOperation.getElementsByName(reportCheckRootElement,"checkInstance");
        	for(int i=0;i<checkInstanceElements.length;i++){ // 每个Instance
        		CheckInstance checkInstance = new CheckInstance();
        		String checkInstanceName = DomXMLOperation.getElementAttr(checkInstanceElements[i], "name");
        		checkInstance.setInstanceName(checkInstanceName);
        		
        		// Instance参数解析
        		Map<String, String> checkParam = new LinkedHashMap<String, String>();
        		Element paramValueListElement = DomXMLOperation.getElementByName(checkInstanceElements[i],"paramValueList");
        		Element[] paramValuesElement = DomXMLOperation.getElementsByName(paramValueListElement,"paramValue");
        		if(paramValuesElement != null){
        			for(int j=0;j<paramValuesElement.length;j++){
            			checkParam.put(DomXMLOperation.getElementAttr(paramValuesElement[j], "name"), DomXMLOperation.getElementAttr(paramValuesElement[j], "value"));
            		}
        		}
        		checkInstance.setCheckParam(checkParam);
        		
        		
        		Element[] checkTableListElement = DomXMLOperation.getElementsByName(checkInstanceElements[i],"checkTable");
        		for(int j=0;j<checkTableListElement.length;j++){ // 每个checkTable
        			CheckTable checkTable = new CheckTable();
        			String checkTableName = DomXMLOperation.getElementAttr(checkTableListElement[j], "procedureName");
        			String checkTableDiscription = DomXMLOperation.getElementAttr(checkTableListElement[j], "procedureDiscription");
        			String checkTablePeriodField = DomXMLOperation.getElementAttr(checkTableListElement[j], "periodField");
        			String checkTableParam=DomXMLOperation.getElementAttr(checkTableListElement[j], "procedureParam");
        			String checkTableOriginTalbe=DomXMLOperation.getElementAttr(checkTableListElement[j], "originTable");
        			String errorMsgField=DomXMLOperation.getElementAttr(checkTableListElement[j], "errorMsgField");
        			String errorStateField=DomXMLOperation.getElementAttr(checkTableListElement[j], "errorStateField");
        			String errorStateValue=DomXMLOperation.getElementAttr(checkTableListElement[j], "errorStateValue");
        			String successStateValue=DomXMLOperation.getElementAttr(checkTableListElement[j], "successStateValue");
        			String defaultLogicDaoBeanId=DomXMLOperation.getElementAttr(checkTableListElement[j], "defaultLogicDaoBeanId");
        			checkTable.setProcedureName(checkTableName);
        			checkTable.setProcedureDiscription(checkTableDiscription);
        			checkTable.setProcedureParam(checkTableParam);
        			checkTable.setPeriodFieldField(checkTablePeriodField);
        			checkTable.setOriginTable(checkTableOriginTalbe);
        			checkTable.setErrorMsgField(errorMsgField);
        			checkTable.setErrorStateField(errorStateField);
        			checkTable.setErrorStateValue(errorStateValue);
        			checkTable.setSuccessStateValue(successStateValue);
        			checkTable.setDefaultLogicDaoBeanId(defaultLogicDaoBeanId);
        			
        			// checkTable参数
        			Element paramFieldListElement = DomXMLOperation.getElementByName(checkTableListElement[j],"paramFieldList");
        			Element[] paramFieldsElement = DomXMLOperation.getElementsByName(paramFieldListElement,"paramField");
        			if(paramFieldListElement != null)
	        			for(int k=0;k<paramFieldsElement.length;k++){
	        				CheckFieldParamField checkFieldParamField = new CheckFieldParamField();
	        				checkFieldParamField.setParamName(DomXMLOperation.getElementAttr(paramFieldsElement[k],"paramName"));
	        				checkFieldParamField.setType(DomXMLOperation.getElementAttr(paramFieldsElement[k],"type"));
	        				checkFieldParamField.setDefaultValue(DomXMLOperation.getElementAttr(paramFieldsElement[k],"value"));
	            			checkTable.getCheckFieldParamFieldList().add(checkFieldParamField);
	            		}
        			
        			// 标识列/组合
        			Element checkUniqueFieldListElement = DomXMLOperation.getElementByName(checkTableListElement[j],"checkUniqueFieldList");
        			Element[] checkUniqueFieldsElement = DomXMLOperation.getElementsByName(checkUniqueFieldListElement,"checkUniqueField");
        			if(checkUniqueFieldsElement != null)
	        			for(int k=0;k<checkUniqueFieldsElement.length;k++){
	        				CheckUniqueField checkUniqueField = new CheckUniqueField();
	            			checkUniqueField.setName(DomXMLOperation.getElementAttr(checkUniqueFieldsElement[k],"name"));
	            			checkUniqueField.setDiscription(DomXMLOperation.getElementAttr(checkUniqueFieldsElement[k],"discription"));
	            			checkUniqueField.setOriginField(DomXMLOperation.getElementAttr(checkUniqueFieldsElement[k],"originField"));
	            			checkTable.getCheckUniqueFieldList().add(checkUniqueField);
	            		}

        			// 解析基本检验配置
        			Element checkFieldBasicListElement = DomXMLOperation.getElementByName(checkTableListElement[j],"checkFieldBasicList");
        			if(checkFieldBasicListElement != null)
        				AddCheckFieldBasicListElement(checkTable, null, checkFieldBasicListElement);
        			
        			// 解析CaseWhen校验配置
        			Element checkFieldCaseWhenListElement = DomXMLOperation.getElementByName(checkTableListElement[j],"checkFieldCaseWhenList");
        			if(checkFieldCaseWhenListElement != null)
        				AddCheckFieldCaseWhenListElement(checkTable, null, checkFieldCaseWhenListElement);
        			
        			// 解析或校验配置
        			Element checkFieldOrListElement = DomXMLOperation.getElementByName(checkTableListElement[j],"checkFieldOrList");
        			Element[] checkFieldOrsElement = DomXMLOperation.getElementsByName(checkFieldOrListElement,"checkFieldOr");
        			if(checkFieldOrListElement != null)
	        			for(int k=0;k<checkFieldOrsElement.length;k++){
	        				CheckFieldOr checkFieldOr = new CheckFieldOr();
	        				
	        				/**
	        	    		 * 2016/08/04  刘涛  新增自定义错误提示信息
	        	    		 */
	        				checkFieldOr.setErrorMsg(DomXMLOperation.getElementAttr(checkFieldOrsElement[k], "errorMsg"));
	        				
	        				String type = DomXMLOperation.getElementAttr(checkFieldOrsElement[k], "type");
	        				if(StringUtils.isBlank(type) || type.equals("1")){ // 或校验中的基本校验
	        					AddCheckFieldBasicListElement(null, checkFieldOr, checkFieldOrsElement[k]);
	        				}
	        				else if(type.equals("2")){// 或检验中的CaseWhen校验
	        					AddCheckFieldCaseWhenListElement(null, checkFieldOr, checkFieldOrsElement[k]);
	        				}
	        				checkTable.getCheckFieldOrList().add(checkFieldOr);
	            		}
        			
        			// 解析有效性校验配置
        			Element checkFieldEffectiveListElement = DomXMLOperation.getElementByName(checkTableListElement[j],"checkFieldEffectiveList");
        			Element[] checkFieldEffectivesElement = DomXMLOperation.getElementsByName(checkFieldEffectiveListElement,"checkFieldEffective");
        			if(checkFieldEffectiveListElement != null)
	        			for(int k=0;k<checkFieldEffectivesElement.length;k++){
	        				CheckFieldEffective checkFieldEffective = new CheckFieldEffective();
	        				checkFieldEffective.setName(DomXMLOperation.getElementAttr(checkFieldEffectivesElement[k], "name"));
	        				checkFieldEffective.setDiscription(DomXMLOperation.getElementAttr(checkFieldEffectivesElement[k], "discription"));
	        				Element[] valueListElement = DomXMLOperation.getElementsByName(checkFieldEffectivesElement[k],"value");
	        				for(int l=0;l<valueListElement.length;l++){
	        					checkFieldEffective.getValueSet().add(DomXMLOperation.getElementValue(valueListElement[l]));
	        				}
	        				
	        				/**
	        	    		 * 2016/08/04  刘涛  新增自定义错误提示信息
	        	    		 */
	        				checkFieldEffective.setErrorMsg(DomXMLOperation.getElementAttr(checkFieldEffectivesElement[k], "errorMsg"));
	        				
	            			checkTable.getCheckFieldEffectiveList().add(checkFieldEffective);
	            		}
        			
        			// 解析行内校验配置
        			Element checkFieldLineListElement = DomXMLOperation.getElementByName(checkTableListElement[j],"checkFieldLineList");
        			Element[] checkFieldLinesElement = DomXMLOperation.getElementsByName(checkFieldLineListElement,"checkFieldLine");
        			if(checkFieldLineListElement != null)
	        			for(int k=0;k<checkFieldLinesElement.length;k++){
	        				CheckFieldLine checkFieldLine = new CheckFieldLine();
	        				checkFieldLine.setFieldType(DomXMLOperation.getElementAttr(checkFieldLinesElement[k], "fieldType"));
	        				checkFieldLine.setCompareType(DomXMLOperation.getElementAttr(checkFieldLinesElement[k], "compareType"));
	        				checkFieldLine.setStartRate(DomXMLOperation.getElementDoubleAttr(checkFieldLinesElement[k], "startRate"));
	        				checkFieldLine.setEndRate(DomXMLOperation.getElementDoubleAttr(checkFieldLinesElement[k], "endRate"));
	        				checkFieldLine.setStartValue(DomXMLOperation.getElementDoubleAttr(checkFieldLinesElement[k], "startValue"));
	        				checkFieldLine.setEndValue(DomXMLOperation.getElementDoubleAttr(checkFieldLinesElement[k], "endValue"));
	        				checkFieldLine.setProcedureDiscription(DomXMLOperation.getElementAttr(checkFieldLinesElement[k], "procedureDiscription"));
	        				checkFieldLine.setProcedureName(DomXMLOperation.getElementAttr(checkFieldLinesElement[k], "procedureName"));
	        				checkFieldLine.setProcedureParam(DomXMLOperation.getElementAttr(checkFieldLinesElement[k], "procedureParam"));
	        				checkFieldLine.setIgnoreVal(DomXMLOperation.getElementAttr(checkFieldLinesElement[k], "ignoreVal"));
	        				
	        				/**
	        	    		 * 2016/08/04  刘涛  新增自定义错误提示信息
	        	    		 */
	        				checkFieldLine.setErrorMsg(DomXMLOperation.getElementAttr(checkFieldLinesElement[k], "errorMsg"));
	        				
	        				Element leftFieldListElement = DomXMLOperation.getElementByName(checkFieldLinesElement[k],"leftFieldList");
	    					Element[] leftFieldsElement = DomXMLOperation.getElementsByName(leftFieldListElement,"checkFieldLineField");
	    					for(int l=0;l<leftFieldsElement.length;l++){
	        					CheckFieldLineField checkFieldLineField = new CheckFieldLineField();
	        					checkFieldLineField.setName(DomXMLOperation.getElementAttr(leftFieldsElement[l], "name"));
	        					checkFieldLineField.setDiscription(DomXMLOperation.getElementAttr(leftFieldsElement[l], "discription"));
	        					checkFieldLineField.setJoinType(DomXMLOperation.getElementAttr(leftFieldsElement[l], "joinType"));
	        					checkFieldLineField.setValue(DomXMLOperation.getElementAttr(leftFieldsElement[l], "value"));
	        					checkFieldLine.getLeftFieldList().add(checkFieldLineField);
	    					}
	
	    					Element rightFieldListElement = DomXMLOperation.getElementByName(checkFieldLinesElement[k],"rightFieldList");
	        				Element[] rightFieldsElement = DomXMLOperation.getElementsByName(rightFieldListElement,"checkFieldLineField");
	        				for(int l=0;l<rightFieldsElement.length;l++){
	        					CheckFieldLineField checkFieldLineField = new CheckFieldLineField();
	        					checkFieldLineField.setName(DomXMLOperation.getElementAttr(rightFieldsElement[l], "name"));
	        					checkFieldLineField.setDiscription(DomXMLOperation.getElementAttr(rightFieldsElement[l], "discription"));
	        					checkFieldLineField.setJoinType(DomXMLOperation.getElementAttr(rightFieldsElement[l], "joinType"));
	        					checkFieldLineField.setValue(DomXMLOperation.getElementAttr(rightFieldsElement[l], "value"));
	        					checkFieldLineField.setValueRule(DomXMLOperation.getElementAttr(rightFieldsElement[l], "valueRule"));
	        					checkFieldLine.getRightFieldList().add(checkFieldLineField);
	        				}
	        				checkTable.getCheckFieldLineList().add(checkFieldLine);
	        			}
        		
        			
        			//解析XML类校验
        			Element checkFieldClassListElement=DomXMLOperation.getElementByName(checkTableListElement[j], "checkFieldClassList");
        			Element[] checkFieldClassesElement = DomXMLOperation.getElementsByName(checkFieldClassListElement,"checkFieldClass");
        			if(checkFieldClassListElement!=null){
        				for(int k=0;k<checkFieldClassesElement.length;k++){
        					CheckFieldClass checkFieldClass=new CheckFieldClass();
        					checkFieldClass.setName(DomXMLOperation.getElementAttr(checkFieldClassesElement[k], "name"));
        					
        					/**
	        	    		 * 2016/08/04  刘涛  新增自定义错误提示信息
	        	    		 */
        					checkFieldClass.setErrorMsg(DomXMLOperation.getElementAttr(checkFieldClassesElement[k], "errorMsg"));
        					
        					checkTable.getCheckFieldClassList().add(checkFieldClass);
        				}
        			}
        			///////
    				checkInstance.getCheckTableList().add(checkTable);
        		}
        		
        		
        		// 解析聚合校验配置
        		Element[] checkFieldAggregationListsElement = DomXMLOperation.getElementsByName(checkInstanceElements[i],"checkFieldAggregationList");
        		
    			for(int k=0;k<checkFieldAggregationListsElement.length;k++){
    				CheckFieldAggregationList checkfieldaggregationlist=new CheckFieldAggregationList();
    				checkfieldaggregationlist.setDiscription(DomXMLOperation.getElementAttr(checkFieldAggregationListsElement[k],"discription"));
    				Element paramFieldListElement = DomXMLOperation.getElementByName(checkFieldAggregationListsElement[k],"paramFieldList");
        			Element[] paramFieldsElement = DomXMLOperation.getElementsByName(paramFieldListElement,"paramField");
        			for(int j=0;j<paramFieldsElement.length;j++){
        				
        				CheckFieldParamField checkFieldParamField = new CheckFieldParamField();
        				checkFieldParamField.setParamName(DomXMLOperation.getElementAttr(paramFieldsElement[j],"paramName"));
        				checkFieldParamField.setType(DomXMLOperation.getElementAttr(paramFieldsElement[j],"type"));
        				checkfieldaggregationlist.getCheckFieldParamFieldList().add(checkFieldParamField);
        		
            		}
        			Element[] checkFieldAggregationsElement = DomXMLOperation.getElementsByName(checkFieldAggregationListsElement[k],"checkFieldAggregation");
     				for(int j=0;j<checkFieldAggregationsElement.length;j++){
    					CheckFieldAggregation checkfieldaggregation=new CheckFieldAggregation();
    					checkfieldaggregation.setSplitDiscription(DomXMLOperation.getElementAttr(checkFieldAggregationsElement[j], "splitDiscription"));
        				checkfieldaggregation.setFieldType(DomXMLOperation.getElementAttr(checkFieldAggregationsElement[j], "fieldType"));
        				checkfieldaggregation.setCompareType(DomXMLOperation.getElementAttr(checkFieldAggregationsElement[j], "compareType"));
        				checkfieldaggregation.setEndRate(DomXMLOperation.getElementDoubleAttr(checkFieldAggregationsElement[j], "endRate"));
        				checkfieldaggregation.setEndValue(DomXMLOperation.getElementDoubleAttr(checkFieldAggregationsElement[j], "endValue"));
        				checkfieldaggregation.setLeftSplitField(DomXMLOperation.getElementAttr(checkFieldAggregationsElement[j], "leftSplitField"));
        				checkfieldaggregation.setRightSplitField(DomXMLOperation.getElementAttr(checkFieldAggregationsElement[j], "rightSplitField"));
        				checkfieldaggregation.setOperationType(DomXMLOperation.getElementAttr(checkFieldAggregationsElement[j], "operationType"));
        				checkfieldaggregation.setStartRate(DomXMLOperation.getElementDoubleAttr(checkFieldAggregationsElement[j], "startRate"));
        				checkfieldaggregation.setStartValue(DomXMLOperation.getElementDoubleAttr(checkFieldAggregationsElement[j], "startValue"));
        			
        				
        				Element leftFieldListElement = DomXMLOperation.getElementByName(checkFieldAggregationsElement[j],"leftFieldList");
    					Element[] leftFieldsElement = DomXMLOperation.getElementsByName(leftFieldListElement,"checkFieldAggregationField");
    					for(int l=0;l<leftFieldsElement.length;l++){
    						CheckFieldAggregationField checkFieldAggregationField = new CheckFieldAggregationField();
    						checkFieldAggregationField.setDiscription(DomXMLOperation.getElementAttr(leftFieldsElement[l], "discription"));
    						checkFieldAggregationField.setJoinType(DomXMLOperation.getElementAttr(leftFieldsElement[l], "joinType"));
    						checkFieldAggregationField.setProcedureName(DomXMLOperation.getElementAttr(leftFieldsElement[l], "procedureName"));
    						checkFieldAggregationField.setProcedureDiscription(DomXMLOperation.getElementAttr(leftFieldsElement[l], "procedureDiscription"));
    						checkFieldAggregationField.setProcedureParam(DomXMLOperation.getElementAttr(leftFieldsElement[l], "procedureParam"));
    						checkFieldAggregationField.setProcedureResultField(DomXMLOperation.getElementAttr(leftFieldsElement[l], "procedureResultField"));
    						checkFieldAggregationField.setProcedureSplitField(DomXMLOperation.getElementAttr(leftFieldsElement[l], "procedureSplitField"));
    						checkFieldAggregationField.setValue(DomXMLOperation.getElementAttr(leftFieldsElement[l], "value"));
    						checkfieldaggregation.getLeftFieldList().add(checkFieldAggregationField);
    					}
    					Element rightFieldListElement = DomXMLOperation.getElementByName(checkFieldAggregationsElement[j],"rightFieldList");
    					Element[] rightFieldsElement = DomXMLOperation.getElementsByName(rightFieldListElement,"checkFieldAggregationField");
    					for(int l=0;l<rightFieldsElement.length;l++){
    						CheckFieldAggregationField checkFieldAggregationField = new CheckFieldAggregationField();
    						checkFieldAggregationField.setDiscription(DomXMLOperation.getElementAttr(rightFieldsElement[l], "discription"));
    						checkFieldAggregationField.setJoinType(DomXMLOperation.getElementAttr(rightFieldsElement[l], "joinType"));
    						checkFieldAggregationField.setProcedureName(DomXMLOperation.getElementAttr(rightFieldsElement[l], "procedureName"));
    						checkFieldAggregationField.setProcedureDiscription(DomXMLOperation.getElementAttr(rightFieldsElement[l], "procedureDiscription"));
    						checkFieldAggregationField.setProcedureParam(DomXMLOperation.getElementAttr(rightFieldsElement[l], "procedureParam"));
    						checkFieldAggregationField.setProcedureResultField(DomXMLOperation.getElementAttr(rightFieldsElement[l], "procedureResultField"));
    						checkFieldAggregationField.setProcedureSplitField(DomXMLOperation.getElementAttr(rightFieldsElement[l], "procedureSplitField"));
    						checkFieldAggregationField.setValue(DomXMLOperation.getElementAttr(rightFieldsElement[l], "value"));
    						checkfieldaggregation.getRightFieldList().add(checkFieldAggregationField);
    					}
    					checkfieldaggregationlist.getCheckFieldAggregationList().add(checkfieldaggregation);	
        			}
     				checkInstance.getCheckFieldAggregationList().add(checkfieldaggregationlist);
    				
				}
    			

    			/**
				 * 一致性校验,唯一性校验	
				 */
    			Element[] checkFieldConsistentsListElement = DomXMLOperation.getElementsByName(checkInstanceElements[i],"checkFieldConsistentList");
        		for(int k=0;k<checkFieldConsistentsListElement.length;k++){
					CheckFieldConsistentList  checkFieldConsistentList =new CheckFieldConsistentList();
					checkFieldConsistentList.setDiscription(DomXMLOperation.getElementAttr(checkFieldConsistentsListElement[k],"discription"));
					checkFieldConsistentList.setConsistenttype(DomXMLOperation.getElementAttr(checkFieldConsistentsListElement[k],"consistenttype"));
					checkFieldConsistentList.setIgnoreVal(DomXMLOperation.getElementAttr(checkFieldConsistentsListElement[k],"ignoreVal"));
					Element paramFieldListElement = DomXMLOperation.getElementByName(checkFieldConsistentsListElement[k],"paramFieldList");
        			Element[] paramFieldsElement = DomXMLOperation.getElementsByName(paramFieldListElement,"paramField");
        			for(int j=0;j<paramFieldsElement.length;j++){
        				CheckFieldParamField checkFieldParamField = new CheckFieldParamField();
        				checkFieldParamField.setParamName(DomXMLOperation.getElementAttr(paramFieldsElement[j],"paramName"));
        				checkFieldParamField.setType(DomXMLOperation.getElementAttr(paramFieldsElement[j],"type"));
        				checkFieldConsistentList.getCheckFieldParamFieldList().add(checkFieldParamField);
        			}
        			Element[] checkFieldConsistentListElement = DomXMLOperation.getElementsByName(checkFieldConsistentsListElement[k],"checkFieldConsistent");
        			for(int g=0;g<checkFieldConsistentListElement.length;g++){
					Element[] checkFieldConsistentsElement = DomXMLOperation.getElementsByName(checkFieldConsistentListElement[g],"consistentGroup");
					CheckFieldConsistent checkFieldConsistent=new CheckFieldConsistent();
					for(int l=0;l<checkFieldConsistentsElement.length;l++){
						ConsisentGroup consistentgroup=new ConsisentGroup();
						consistentgroup.setKeyDiscription(DomXMLOperation.getElementAttr(checkFieldConsistentsElement[l], "keyDiscription"));
						consistentgroup.setKeyField(DomXMLOperation.getElementAttr(checkFieldConsistentsElement[l], "keyField"));
						consistentgroup.setNameDiscription(DomXMLOperation.getElementAttr(checkFieldConsistentsElement[l], "nameDiscription"));
						consistentgroup.setNameField(DomXMLOperation.getElementAttr(checkFieldConsistentsElement[l], "nameField"));
						consistentgroup.setProcedureName(DomXMLOperation.getElementAttr(checkFieldConsistentsElement[l], "procedureName"));
						consistentgroup.setProcedureParam(DomXMLOperation.getElementAttr(checkFieldConsistentsElement[l], "procedureParam"));
						consistentgroup.setProcedureDiscription(DomXMLOperation.getElementAttr(checkFieldConsistentsElement[l], "procedureDiscription"));
						checkFieldConsistent.getConsistentGroupList().add(consistentgroup);
					}
					checkFieldConsistentList.getCheckFieldConsistentList().add(checkFieldConsistent);
        			}
					checkInstance.getCheckFieldConsistentList().add(checkFieldConsistentList);
        		}
        		
        		/**完整性校验*/	
        		Element[] checkFieldScopeListElement = DomXMLOperation.getElementsByName(checkInstanceElements[i],"checkFieldScopeList");
        		for(int k=0;k<checkFieldScopeListElement.length;k++){
        			CheckFieldScopeList  checkFieldScopeList =new CheckFieldScopeList();
        			checkFieldScopeList.setDiscription(DomXMLOperation.getElementAttr(checkFieldScopeListElement[k],"discription"));
        			Element paramFieldListElement = DomXMLOperation.getElementByName(checkFieldScopeListElement[k],"paramFieldList");
        			Element[] paramFieldsElement = DomXMLOperation.getElementsByName(paramFieldListElement,"paramField");
        			for(int j=0;j<paramFieldsElement.length;j++){
        				CheckFieldParamField checkFieldParamField = new CheckFieldParamField();
        				checkFieldParamField.setParamName(DomXMLOperation.getElementAttr(paramFieldsElement[j],"paramName"));
        				checkFieldParamField.setType(DomXMLOperation.getElementAttr(paramFieldsElement[j],"type"));
        				checkFieldScopeList.getCheckFieldParamFieldList().add(checkFieldParamField);
        			}
        			Element[] checkFieldScopeElement = DomXMLOperation.getElementsByName(checkFieldScopeListElement[k],"checkFieldScope");
        			for(int g=0;g<checkFieldScopeElement.length;g++){
        				CheckFieldScope  checkFieldScope=new CheckFieldScope();
        				checkFieldScope.setBelongType(DomXMLOperation.getElementAttr(checkFieldScopeElement[g], "belongType"));
        				
        				Element leftFieldElement = DomXMLOperation.getElementByName(checkFieldScopeElement[g],"leftField");
        				CheckFieldScopeField checkFieldScopeField=new CheckFieldScopeField();
        				checkFieldScopeField.setDiscription(DomXMLOperation.getElementAttr(leftFieldElement, "discription"));
        				checkFieldScopeField.setName(DomXMLOperation.getElementAttr(leftFieldElement, "name"));
        				checkFieldScopeField.setProcedureDiscription(DomXMLOperation.getElementAttr(leftFieldElement, "procedureDiscription"));
        				checkFieldScopeField.setProcedureName(DomXMLOperation.getElementAttr(leftFieldElement, "procedureName"));
        				checkFieldScopeField.setProcedureParam(DomXMLOperation.getElementAttr(leftFieldElement, "procedureParam"));
        				checkFieldScope.getLeftFieldList().add(checkFieldScopeField);
        				
        				Element rightFieldElement = DomXMLOperation.getElementByName(checkFieldScopeElement[g],"rightField");
        				checkFieldScopeField.setDiscription(DomXMLOperation.getElementAttr(rightFieldElement, "discription"));
        				checkFieldScopeField.setName(DomXMLOperation.getElementAttr(rightFieldElement, "name"));
        				checkFieldScopeField.setProcedureDiscription(DomXMLOperation.getElementAttr(rightFieldElement, "procedureDiscription"));
        				checkFieldScopeField.setProcedureName(DomXMLOperation.getElementAttr(rightFieldElement, "procedureName"));
        				checkFieldScopeField.setProcedureParam(DomXMLOperation.getElementAttr(rightFieldElement, "procedureParam"));
        				checkFieldScope.getRightFieldList().add(checkFieldScopeField);
        				checkFieldScopeList.getCheckFieldScopeList().add(checkFieldScope);
        			}
        			
        			checkInstance.getCheckFieldScopeList().add(checkFieldScopeList);
        		}
        		
        		// 解析指标校验配置
        		Element[] checkItemListElement = DomXMLOperation.getElementsByName(checkInstanceElements[i],"checkItemList");
        		
    			for(int k=0; k < checkItemListElement.length; k++) {
    				CheckItemList checkItemlist = new CheckItemList();
    				Element paramFieldListElement = DomXMLOperation.getElementByName(checkItemListElement[k],"paramFieldList");
        			Element[] paramFieldsElement = DomXMLOperation.getElementsByName(paramFieldListElement,"paramField");
        			for(int j=0;j<paramFieldsElement.length;j++) {
        				
        				CheckFieldParamField checkFieldParamField = new CheckFieldParamField();
        				checkFieldParamField.setParamName(DomXMLOperation.getElementAttr(paramFieldsElement[j],"paramName"));
        				checkFieldParamField.setType(DomXMLOperation.getElementAttr(paramFieldsElement[j],"type"));
        				checkItemlist.getCheckFieldParamFieldList().add(checkFieldParamField);
            		}
        			
        			Element[] checkItemRuleListElement = DomXMLOperation.getElementsByName(checkItemListElement[k],"checkItemRule");
     				for(int j=0; j < checkItemRuleListElement.length; j++) {
     					CheckItemRule checkItemRule = new CheckItemRule();
     					checkItemRule.setCode(DomXMLOperation.getElementAttr(checkItemRuleListElement[j], "code"));
     					checkItemRule.setCurrency(DomXMLOperation.getElementAttr(checkItemRuleListElement[j], "currency"));
     					checkItemRule.setCompareType(DomXMLOperation.getElementAttr(checkItemRuleListElement[j], "compareType"));
     					checkItemRule.setPropty(DomXMLOperation.getElementAttr(checkItemRuleListElement[j], "property"));
     					checkItemRule.setInstCode(DomXMLOperation.getElementAttr(checkItemRuleListElement[j], "instCode"));
     					checkItemRule.setDtDate(DomXMLOperation.getElementAttr(checkItemRuleListElement[j], "dtDate"));
     					checkItemRule.setExt1(DomXMLOperation.getElementAttr(checkItemRuleListElement[j], "ext1"));
     					checkItemRule.setExt2(DomXMLOperation.getElementAttr(checkItemRuleListElement[j], "ext2"));
        				
    					Element[] checkItemSubRuleElement = DomXMLOperation.getElementsByName(checkItemRuleListElement[j], "checkItemSubRule"); // 替换节点：checkNode
    					for(int l=0; l < checkItemSubRuleElement.length; l++) { // 读取每个指标校验规则
    						CheckItemSubRule checkItemSubRule = new CheckItemSubRule();
    						checkItemSubRule.setType(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "type"));
    						checkItemSubRule.setValue(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "value"));
    						checkItemSubRule.setTable(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "table"));
    						checkItemSubRule.setField(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "field"));
    						checkItemSubRule.setOperate(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "operate"));
    						checkItemSubRule.setCompareType(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "compareType"));
    						
    						checkItemSubRule.setCode(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "code"));
    						checkItemSubRule.setCurrency(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "currency"));
    						checkItemSubRule.setCompareType(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "compareType"));
    						checkItemSubRule.setPropty(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "property"));
    						checkItemSubRule.setInstCode(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "instCode"));
    						checkItemSubRule.setDtDate(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "dtDate"));
    						checkItemSubRule.setExt1(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "ext1"));
    						checkItemSubRule.setExt2(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "ext2"));
    						checkItemSubRule.setLastFreq(DomXMLOperation.getElementAttr(checkItemSubRuleElement[l], "lastFreq"));

    						// 读取每个规则的where条件
    						Element[] checkWhereElement = DomXMLOperation.getElementsByName(checkItemSubRuleElement[l], "where");
        					for(int m=0; m < checkWhereElement.length; m++) { // 读取每个指标校验规则
        						ConditionClass conditionClass = new ConditionClass();
        						conditionClass.setFieldName(DomXMLOperation.getElementAttr(checkWhereElement[m], "field"));
        						conditionClass.setCompareType(DomXMLOperation.getElementAttr(checkWhereElement[m], "compareType"));
        						conditionClass.setStrValue(DomXMLOperation.getElementAttr(checkWhereElement[m], "value"));
        						conditionClass.setConditionJoinType(DomXMLOperation.getElementAttr(checkWhereElement[m], "conditionJoinType"));

        						checkItemSubRule.getWhereFieldList().add(conditionClass);
        					}
    						
    						checkItemRule.getCheckSubRuleList().add(checkItemSubRule);
    					}

    					checkItemlist.getCheckItemRuleList().add(checkItemRule);	
        			}
     				
     				checkInstance.getCheckItemList().add(checkItemlist);
				}
    			
    			checkInstanceMap.put(checkInstanceName, checkInstance);
    		}
        }
    }  

	
	public void setCheckInstanceMap(Map<String, CheckInstance> checkInstanceMap) {
		this.checkInstanceMap = checkInstanceMap;
	}

	public Map<String, CheckInstance> getCheckInstanceMap() {
		return checkInstanceMap;
	}

	public static void setReportCheckLocation(String[] reportCheckLocation) {
		CheckContext.reportCheckLocation = reportCheckLocation;
	}

	public static String[] getReportCheckLocation() {
		return reportCheckLocation;
	}

	private Map<String, CheckInstance> checkInstanceMap;
	
    private static String[] reportCheckLocation;
    
  
}
