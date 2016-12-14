package report.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.CheckNode;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.reportCheck.CheckFieldAggregationField;
import framework.reportCheck.CheckFieldBasic;
import framework.reportCheck.CheckFieldCaseWhen;
import framework.reportCheck.CheckFieldCaseWhenField;
import framework.reportCheck.CheckFieldEffective;
import framework.reportCheck.CheckFieldLine;
import framework.reportCheck.CheckFieldLineField;
import framework.reportCheck.CheckFieldOr;
import framework.reportCheck.CheckFieldParamField;
import framework.reportCheck.CheckUniqueField;

public class CheckInstanceCopy {
	
	public static framework.reportCheck.CheckInstance CopyCheckInstance(String checkInstanceName) throws Exception{
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		report.dto.CheckInstance reportCheckInstance = (report.dto.CheckInstance)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{report.dto.CheckInstance.class.getName(),checkInstanceName,null});

		return CopyCheckInstance(reportCheckInstance);
	}
	
	@SuppressWarnings("unchecked")
	public static framework.reportCheck.CheckInstance CopyCheckInstance(report.dto.CheckInstance reportCheckInstance) throws Exception{
		
		if(reportCheckInstance == null){
			return null;
		}
		
		framework.reportCheck.CheckInstance checkInstance = new framework.reportCheck.CheckInstance();
		//
		checkInstance.setInstanceName(reportCheckInstance.getInstanceName());
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(report.dto.CheckNode.class);
		detachedCriteria.add(Restrictions.eq("checkInstance", reportCheckInstance));
		List<report.dto.CheckNode> checkNodeList = (List<report.dto.CheckNode>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		for(CheckNode checkNode : checkNodeList){
			//CheckTable
			if(checkNode.getCheckNodeType().equals("1")){
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckTable.class);
				detachedCriteria.add(Restrictions.eq("checkNode", checkNode));
				List<report.dto.CheckTable> checkTableList = (List<report.dto.CheckTable>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(checkTableList.size() == 0){
					throw new Exception("未设置Table结点");
				}

				report.dto.CheckTable reportCheckTable = checkTableList.get(0);
				
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckTableNode.class);
				detachedCriteria.add(Restrictions.eq("checkTable", reportCheckTable));
				List<report.dto.CheckTableNode> checkTableNodeList = (List<report.dto.CheckTableNode>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});

				//CheckTable 参数
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckExtendTableParam.class);
				detachedCriteria.add(Restrictions.eq("checkTable", reportCheckTable));
				List<report.dto.CheckExtendTableParam> checkExtendTableParamList = (List<report.dto.CheckExtendTableParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				List<CheckFieldParamField> checkFieldParamFieldList = new ArrayList<CheckFieldParamField>();
				for(report.dto.CheckExtendTableParam checkExtendTableParam : checkExtendTableParamList){
					framework.reportCheck.CheckFieldParamField checkFieldParamFields = new CheckFieldParamField();
					checkFieldParamFields.setParamName(checkExtendTableParam.getAutoETL_Param().getStrParamName());
					checkFieldParamFields.setType("1");
					//checkFieldParamFields.setValue("1");		
					checkFieldParamFieldList.add(checkFieldParamFields);
				}
				
				framework.reportCheck.CheckTable checkTable = new framework.reportCheck.CheckTable();
				
				checkTable.setDefaultLogicDaoBeanId(reportCheckTable.getDefaultLogicDaoBeanId());
				checkTable.setProcedureName(reportCheckTable.getProcedureName());
				checkTable.setProcedureDiscription(reportCheckTable.getProcedureDiscription());
				checkTable.setPeriodFieldField(reportCheckTable.getPeriodFieldField());
				checkTable.setOriginTable(reportCheckTable.getOriginTable());
				checkTable.setErrorMsgField(reportCheckTable.getErrorMsgField());
				checkTable.setErrorStateField(reportCheckTable.getErrorStateField());
				checkTable.setErrorStateValue(reportCheckTable.getErrorStateValue());
				checkTable.setSuccessStateValue(reportCheckTable.getSuccessStateValue());
				checkTable.setProcedureParam(reportCheckTable.getProcedureParam());
				checkTable.setCheckFieldParamFieldList(checkFieldParamFieldList);
			
				for(report.dto.CheckTableNode checkTableNode : checkTableNodeList){

					if(checkTableNode.getCheckTableNodeType().equals("1")){
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(report.dto.CheckUniqueField.class);
						detachedCriteria.add(Restrictions.eq("checkTableNode", checkTableNode));
						List<report.dto.CheckUniqueField> checkUniqueFieldList = (List<report.dto.CheckUniqueField>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						if(checkUniqueFieldList.size() == 0){
							throw new Exception("未设置标识列");
						}
						
						for(report.dto.CheckUniqueField checkUniqueField : checkUniqueFieldList){
							framework.reportCheck.CheckUniqueField checkUniqueFields = new CheckUniqueField();
							checkUniqueFields.setDiscription(checkUniqueField.getDiscription());
							checkUniqueFields.setName(checkUniqueField.getName());
							checkUniqueFields.setOriginField(checkUniqueField.getOriginField());
							checkTable.getCheckUniqueFieldList().add(checkUniqueFields);
						}	
					}
					
					else if(checkTableNode.getCheckTableNodeType().equals("2")){
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(report.dto.CheckBasic.class);
						detachedCriteria.add(Restrictions.eq("checkTableNode", checkTableNode));
						List<report.dto.CheckBasic> checkBasicList = (List<report.dto.CheckBasic>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});

						for(report.dto.CheckBasic checkBasic : checkBasicList){

							framework.reportCheck.CheckFieldBasic checkBasics = new CheckFieldBasic();
							checkBasics.setName(checkBasic.getName());
							
							if(checkBasic.getChnCheck().equals("1")){
								checkBasics.setChnCheck(true);
							}
							
							checkBasics.setCompareValue(checkBasic.getCompareValue());
							
							if(checkBasic.getDateCheck().equals("1")){
								checkBasics.setDateCheck(true);
							}
							
							if(checkBasic.getDigitalCheck().equals("1")){
								checkBasics.setDigitalCheck(true);
							}
							
							checkBasics.setDiscription(checkBasic.getDiscription());

							if(checkBasic.getEmptyCheck().equals("1")){
								checkBasics.setEmptyCheck(true);
							}
							
							if(checkBasic.getNumCheck().equals("1")){
								checkBasics.setNumCheck(true);
							}
							
							checkBasics.setEndLength(checkBasic.getEndLength());
							
							checkBasics.setStartLength(checkBasic.getStartLength());
							
							checkBasics.setValueDecimalLength(checkBasic.getValueDecimalLength());
							
							if(checkBasic.getUpperCheck().equals("1")){
								checkBasics.setUpperCheck(true);
							}
							
							if(checkBasic.getRepeatLetter().equals("1")){
								checkBasics.setRepeatLetter(true);
							}
							
							if(checkBasic.getErrorcharCheck().equals("1")){
								checkBasics.setErrorcharCheck(true);
							}
							
							checkBasics.setIgnoreVal(checkBasic.getIgnoreVal());
							
							if(checkBasic.getNumAndBeginNum().equals("1")){
								checkBasics.setNumAndBeginNum(true);
							}
							
							if(checkBasic.getValueMax() != null){
								checkBasics.setValueMax(checkBasic.getValueMax().toString());
							}
							
							if(checkBasic.getValueMin() != null){
								checkBasics.setValueMin(checkBasic.getValueMin().toString());
							}
							
							checkBasics.setNotEqualValue(checkBasic.getNotEqualValue());
							
							if(checkBasic.getDateCheckForSixBit()!=null){
								if(checkBasic.getDateCheckForSixBit().equals("1")){
									checkBasics.setDateCheckForSixBit(true);
								}
							}
							
							checkTable.getCheckFieldBasicList().add(checkBasics);
								
						}
					}
					
					else if(checkTableNode.getCheckTableNodeType().equals("3")){
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(report.dto.CheckCaseWhen.class);
						detachedCriteria.add(Restrictions.eq("checkTableNode", checkTableNode));
						List<report.dto.CheckCaseWhen> checkCaseWhenList = (List<report.dto.CheckCaseWhen>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						for(report.dto.CheckCaseWhen checkCaseWhen : checkCaseWhenList){
							framework.reportCheck.CheckFieldCaseWhen checkFieldCaseWhens = new CheckFieldCaseWhen();
							checkFieldCaseWhens.setName(checkCaseWhen.getName());
							checkFieldCaseWhens.setDiscription(checkCaseWhen.getDiscription());
							checkFieldCaseWhens.setCaseValue(checkCaseWhen.getCaseValue());
							checkFieldCaseWhens.setCaseCompareType(checkCaseWhen.getCaseCompareType());
							checkFieldCaseWhens.setCaseValue(checkCaseWhen.getCaseValue());
							checkFieldCaseWhens.setWhenValue(checkCaseWhen.getWhenValue());
							checkFieldCaseWhens.setCaseValueScope(checkCaseWhen.getCaseValueScope());
							checkFieldCaseWhens.setWhenValueScope(checkCaseWhen.getWhenValueScope());
							checkFieldCaseWhens.setWhenType(checkCaseWhen.getWhenType());
							checkFieldCaseWhens.setWhenCompareType(checkCaseWhen.getWhenCompareType());
							checkFieldCaseWhens.setWhenCompareValue(checkCaseWhen.getWhenCompareValue());
							
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							detachedCriteria = DetachedCriteria.forClass(report.dto.CheckCaseWhenField.class);
							detachedCriteria.add(Restrictions.eq("checkCaseWhen",checkCaseWhen));
							List<report.dto.CheckCaseWhenField> checkCaseWhenFields = (List<report.dto.CheckCaseWhenField>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							for(report.dto.CheckCaseWhenField checkCaseWhenField : checkCaseWhenFields){
								framework.reportCheck.CheckFieldCaseWhenField checkFieldCaseWhenFields = new CheckFieldCaseWhenField();
								checkFieldCaseWhenFields.setName(checkCaseWhenField.getName());
								checkFieldCaseWhenFields.setDiscription(checkCaseWhenField.getDiscription());
								checkFieldCaseWhenFields.setJoinType(checkCaseWhenField.getJoinType());
								checkFieldCaseWhenFields.setWhenValueRule(checkCaseWhenField.getWhenValueRule());
								checkFieldCaseWhenFields.setWhenValue(checkCaseWhenField.getWhenValue());
								checkFieldCaseWhenFields.setWhenValueScope(checkCaseWhenField.getWhenValueScope());
								checkFieldCaseWhenFields.setWhenCompareType(checkCaseWhenField.getWhenCompareType());
								checkFieldCaseWhens.getCheckFieldCaseWhenFieldList().add(checkFieldCaseWhenFields);
							}
							checkTable.getCheckFieldCaseWhenList().add(checkFieldCaseWhens);
						}
					}
					
					else if(checkTableNode.getCheckTableNodeType().equals("4")){
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldEffective.class);
						detachedCriteria.add(Restrictions.eq("checkTableNode", checkTableNode));
						List<report.dto.CheckFieldEffective> checkFieldEffectiveList = (List<report.dto.CheckFieldEffective>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						for(report.dto.CheckFieldEffective checkFieldEffective : checkFieldEffectiveList){
							framework.reportCheck.CheckFieldEffective checkFieldEffectives = new CheckFieldEffective();
							checkFieldEffectives.setName(checkFieldEffective.getName());
							checkFieldEffectives.setDiscription(checkFieldEffective.getDiscription());
							
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldEffectiveValues.class);
							detachedCriteria.add(Restrictions.eq("checkFieldEffective", checkFieldEffective));
							List<report.dto.CheckFieldEffectiveValues> checkFieldEffectiveValueLists = (List<report.dto.CheckFieldEffectiveValues>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							Set<String> reportValueSet = new HashSet<String>();
							
							for(report.dto.CheckFieldEffectiveValues checkFieldEffectiveValues : checkFieldEffectiveValueLists){			
								reportValueSet.add(checkFieldEffectiveValues.getValue());	
							}
							checkFieldEffectives.setValueSet(reportValueSet);
							checkTable.getCheckFieldEffectiveList().add(checkFieldEffectives);
						}
					}	
					//非聚合校验
					else if(checkTableNode.getCheckTableNodeType().equals("5")){
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldLine.class);
						detachedCriteria.add(Restrictions.eq("checkTableNode", checkTableNode));
						List<report.dto.CheckFieldLine> checkFieldLineList = (List<report.dto.CheckFieldLine>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						for(report.dto.CheckFieldLine checkFieldLine : checkFieldLineList){
							framework.reportCheck.CheckFieldLine checkFieldLines = new CheckFieldLine();
							checkFieldLines.setFieldType(checkFieldLine.getFieldType());
							checkFieldLines.setCompareType(checkFieldLine.getCompareType());
							checkFieldLines.setStartRate(checkFieldLine.getStartRate());
							checkFieldLines.setEndRate(checkFieldLine.getEndRate());
							checkFieldLines.setStartValue(checkFieldLine.getStartValue());
							checkFieldLines.setEndValue(checkFieldLine.getEndValue());
							checkFieldLines.setProcedureDiscription(checkFieldLine.getProcedureDiscription());
							checkFieldLines.setProcedureName(checkFieldLine.getProcedureName());
							checkFieldLines.setProcedureParam(checkFieldLine.getProcedureParam());
							checkFieldLines.setIgnoreVal(checkFieldLine.getIgnoreVal());
							
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldLineLeft.class);
							detachedCriteria.add(Restrictions.eq("checkFieldLine", checkFieldLine));
							List<report.dto.CheckFieldLineLeft> checkFieldLineLefts = (List<report.dto.CheckFieldLineLeft>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							List<framework.reportCheck.CheckFieldLineField> LeftFieldLists  = new ArrayList<framework.reportCheck.CheckFieldLineField>();
							
							for(report.dto.CheckFieldLineLeft checkFieldLineLeft : checkFieldLineLefts){
								framework.reportCheck.CheckFieldLineField checkFieldLineField = new CheckFieldLineField();
								checkFieldLineField.setName(checkFieldLineLeft.getName());
								checkFieldLineField.setDiscription(checkFieldLineLeft.getDiscription());
								checkFieldLineField.setJoinType(checkFieldLineLeft.getJoinType());
								checkFieldLineField.setValue(checkFieldLineLeft.getValue());
								LeftFieldLists.add(checkFieldLineField);
								checkFieldLines.setLeftFieldList(LeftFieldLists);
							}
							
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldLineRight.class);
							detachedCriteria.add(Restrictions.eq("checkFieldLine", checkFieldLine));
							List<report.dto.CheckFieldLineRight> checkFieldLineRights = (List<report.dto.CheckFieldLineRight>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							List<framework.reportCheck.CheckFieldLineField> rightFieldLists  = new ArrayList<framework.reportCheck.CheckFieldLineField>();
							
							for(report.dto.CheckFieldLineRight checkFieldLineRight : checkFieldLineRights){
								framework.reportCheck.CheckFieldLineField checkFieldLineField = new CheckFieldLineField();
								checkFieldLineField.setName(checkFieldLineRight.getName());
								checkFieldLineField.setDiscription(checkFieldLineRight.getDiscription());
								checkFieldLineField.setJoinType(checkFieldLineRight.getJoinType());
								checkFieldLineField.setValue(checkFieldLineRight.getValue());
								checkFieldLineField.setValueRule(checkFieldLineRight.getValueRule());
								rightFieldLists.add(checkFieldLineField);
								checkFieldLines.setRightFieldList(rightFieldLists);
							}
							checkTable.getCheckFieldLineList().add(checkFieldLines);
						}
					}
					
					//条件校验
					else if(checkTableNode.getCheckTableNodeType().equals("6")){
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldOrList.class);
						detachedCriteria.add(Restrictions.eq("checkTableNode", checkTableNode));
						List<report.dto.CheckFieldOrList> checkFieldOrListList = (List<report.dto.CheckFieldOrList>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						for(report.dto.CheckFieldOrList checkFieldOrList : checkFieldOrListList){
		                           
							framework.reportCheck.CheckFieldOr checkFieldOrs = new CheckFieldOr();
							
							if(checkFieldOrList.getType().equals("1")){
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(report.dto.CheckOrBasic.class);
								detachedCriteria.add(Restrictions.eq("checkFieldOrList", checkFieldOrList));
								List<report.dto.CheckOrBasic> checkOrBasics = (List<report.dto.CheckOrBasic>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								for(report.dto.CheckOrBasic checkOrBasic : checkOrBasics){
									framework.reportCheck.CheckFieldBasic checkFieldBasic = new framework.reportCheck.CheckFieldBasic();
									
									checkFieldBasic.setName(checkOrBasic.getName());
									
									if(checkOrBasic.getChnCheck().equals("1")){
										checkFieldBasic.setChnCheck(true);
									}
									
									checkFieldBasic.setCompareValue(checkOrBasic.getCompareValue());
									
									if(checkOrBasic.getDateCheck().equals("1")){
										checkFieldBasic.setDateCheck(true);
									}
									
									if(checkOrBasic.getDigitalCheck().equals("1")){
										checkFieldBasic.setDigitalCheck(true);
									}
									
									checkFieldBasic.setDiscription(checkOrBasic.getDiscription());

									if(checkOrBasic.getEmptyCheck().equals("1")){
										checkFieldBasic.setEmptyCheck(true);
									}
									
									if(checkOrBasic.getNumCheck().equals("1")){
										checkFieldBasic.setNumCheck(true);
									}
									
									checkFieldBasic.setEndLength(checkOrBasic.getEndLength());
									
									checkFieldBasic.setStartLength(checkOrBasic.getStartLength());
									
									checkFieldBasic.setValueDecimalLength(checkOrBasic.getValueDecimalLength());
									
									if(checkOrBasic.getUpperCheck().equals("1")){
										checkFieldBasic.setUpperCheck(true);
									}
									
									if(checkOrBasic.getRepeatLetter().equals("1")){
										checkFieldBasic.setRepeatLetter(true);
									}
									
									if(checkOrBasic.getErrorcharCheck().equals("1")){
										checkFieldBasic.setErrorcharCheck(true);
									}
									
									checkFieldBasic.setIgnoreVal(checkOrBasic.getIgnoreVal());
									
									if(checkOrBasic.getNumAndBeginNum().equals("1")){
										checkFieldBasic.setNumAndBeginNum(true);
									}
									
									if(checkOrBasic.getValueMax() != null){
										checkFieldBasic.setValueMax(checkOrBasic.getValueMax().toString());
									}
									
									if(checkOrBasic.getValueMin() != null){
										checkFieldBasic.setValueMin(checkOrBasic.getValueMin().toString());
									}
									
									checkFieldBasic.setNotEqualValue(checkOrBasic.getNotEqualValue());
									if(checkOrBasic.getDateCheckForSixBit()!=null){
										if(checkOrBasic.getDateCheckForSixBit().equals("1")){
											checkFieldBasic.setDateCheckForSixBit(true);
										}
									}
									checkFieldOrs.getCheckFieldBasicList().add(checkFieldBasic);
								}
							}	
							else if(checkFieldOrList.getType().equals("2")){
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(report.dto.CheckOrCaseWhen.class);
								detachedCriteria.add(Restrictions.eq("checkFieldOrList", checkFieldOrList));
								List<report.dto.CheckOrCaseWhen> checkOrCaseWhenList = (List<report.dto.CheckOrCaseWhen>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								for(report.dto.CheckOrCaseWhen checkOrCaseWhen : checkOrCaseWhenList){
									framework.reportCheck.CheckFieldCaseWhen checkFieldCaseWhens = new CheckFieldCaseWhen();
									
									checkFieldCaseWhens.setName(checkOrCaseWhen.getName());
									checkFieldCaseWhens.setDiscription(checkOrCaseWhen.getDiscription());
									checkFieldCaseWhens.setCaseValue(checkOrCaseWhen.getCaseValue());
									checkFieldCaseWhens.setCaseValueScope(checkOrCaseWhen.getCaseValueScope());
									checkFieldCaseWhens.setCaseCompareType(checkOrCaseWhen.getCaseCompareType());
									checkFieldCaseWhens.setWhenValue(checkOrCaseWhen.getWhenValue());
									checkFieldCaseWhens.setWhenValueScope(checkOrCaseWhen.getWhenValueScope());
									checkFieldCaseWhens.setWhenType(checkOrCaseWhen.getWhenType());
									checkFieldCaseWhens.setWhenCompareType(checkOrCaseWhen.getWhenCompareType());
									checkFieldCaseWhens.setWhenCompareValue(checkOrCaseWhen.getWhenCompareValue());
									checkTable.getCheckFieldCaseWhenList().add(checkFieldCaseWhens);
									
									singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
									detachedCriteria = DetachedCriteria.forClass(report.dto.CheckOrCaseWhenField.class);
									detachedCriteria.add(Restrictions.eq("checkOrCaseWhen", checkOrCaseWhen));
									List<report.dto.CheckOrCaseWhenField> checkOrCaseWhenFields = (List<report.dto.CheckOrCaseWhenField>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									
									for(report.dto.CheckOrCaseWhenField checkOrCaseWhenField : checkOrCaseWhenFields){
										framework.reportCheck.CheckFieldCaseWhenField checkFieldCaseWhenFields = new CheckFieldCaseWhenField();
										checkFieldCaseWhenFields.setName(checkOrCaseWhenField.getName());
										checkFieldCaseWhenFields.setDiscription(checkOrCaseWhenField.getDiscription());
										checkFieldCaseWhenFields.setJoinType(checkOrCaseWhenField.getJoinType());
										checkFieldCaseWhenFields.setWhenValueRule(checkOrCaseWhenField.getWhenValueRule());
										checkFieldCaseWhenFields.setWhenValue(checkOrCaseWhenField.getWhenValue());
										checkFieldCaseWhenFields.setWhenValueScope(checkOrCaseWhenField.getWhenValueScope());
										checkFieldCaseWhens.getCheckFieldCaseWhenFieldList().add(checkFieldCaseWhenFields);
										checkFieldOrs.getCheckFieldCaseWhenList().add(checkFieldCaseWhens);
									}
								}
							}
							checkTable.getCheckFieldOrList().add(checkFieldOrs);
						}
					}
						/*
						report.dto.CheckFieldOrList reportCheckFieldOrLists = checkFieldOrListList.get(0);
						
						framework.reportCheck.CheckFieldOr checkFieldOrs = new CheckFieldOr();
						
						for(report.dto.CheckFieldOrList checkFieldOrList : checkFieldOrListList){
							if(checkFieldOrList.getType().equals("1")){
								
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(report.dto.CheckOrBasic.class);
								detachedCriteria.add(Restrictions.eq("checkFieldOrList", reportCheckFieldOrLists));
								List<report.dto.CheckOrBasic> checkOrBasics = (List<report.dto.CheckOrBasic>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								if(checkOrBasics.size() == 0){
									throw new Exception("未设置条件校验下的基本校验结点");
								}
								
								framework.reportCheck.CheckFieldBasic checkFieldBasic = new framework.reportCheck.CheckFieldBasic();
								
								for(report.dto.CheckOrBasic checkOrBasic : checkOrBasics){
									if(checkOrBasic.getEmptyCheck().equals("1")){
										checkFieldBasic.setEmptyCheck(true);
									}
									checkFieldBasic.setName(checkOrBasic.getName());
									checkFieldBasic.setDiscription(checkOrBasic.getDiscription());
									checkFieldOrs.getCheckFieldBasicList().add(checkFieldBasic);
								}
							}
							 else if(checkFieldOrList.getType().equals("2")){
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(report.dto.CheckOrCaseWhen.class);
								detachedCriteria.add(Restrictions.eq("checkFieldOrList", reportCheckFieldOrLists));
								List<report.dto.CheckOrCaseWhen> checkOrCaseWhenList = (List<report.dto.CheckOrCaseWhen>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								if(checkOrCaseWhenList.size() == 0){
									throw new Exception("未设置条件校验下的CaseWhen校验结点");
								}
								
								report.dto.CheckOrCaseWhen reportCheckOrCaseWhens = checkOrCaseWhenList.get(0);
								
								framework.reportCheck.CheckFieldCaseWhen checkFieldCaseWhens = new CheckFieldCaseWhen();
								
								checkFieldCaseWhens.setName(reportCheckOrCaseWhens.getName());
								checkFieldCaseWhens.setDiscription(reportCheckOrCaseWhens.getDiscription());
								checkFieldCaseWhens.setCaseValue(reportCheckOrCaseWhens.getCaseValue());
								checkFieldCaseWhens.setCaseValueScope(reportCheckOrCaseWhens.getCaseValueScope());
								checkFieldCaseWhens.setCaseCompareType(reportCheckOrCaseWhens.getCaseCompareType());
								checkFieldCaseWhens.setWhenValue(reportCheckOrCaseWhens.getWhenValue());
								checkFieldCaseWhens.setWhenValueScope(reportCheckOrCaseWhens.getWhenValueScope());
								checkFieldCaseWhens.setWhenType(reportCheckOrCaseWhens.getWhenType());
								checkFieldCaseWhens.setWhenCompareType(reportCheckOrCaseWhens.getWhenCompareType());
								checkFieldCaseWhens.setWhenCompareValue(reportCheckOrCaseWhens.getWhenCompareValue());
								checkTable.getCheckFieldCaseWhenList().add(checkFieldCaseWhens);
								
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(report.dto.CheckOrCaseWhenField.class);
								detachedCriteria.add(Restrictions.eq("checkOrCaseWhen", reportCheckOrCaseWhens));
								List<report.dto.CheckOrCaseWhenField> checkOrCaseWhenFields = (List<report.dto.CheckOrCaseWhenField>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								for(report.dto.CheckOrCaseWhenField checkOrCaseWhenField : checkOrCaseWhenFields){
									framework.reportCheck.CheckFieldCaseWhenField checkFieldCaseWhenFields = new CheckFieldCaseWhenField();
									checkFieldCaseWhenFields.setName(checkOrCaseWhenField.getName());
									checkFieldCaseWhenFields.setDiscription(checkOrCaseWhenField.getDiscription());
									checkFieldCaseWhenFields.setJoinType(checkOrCaseWhenField.getJoinType());
									checkFieldCaseWhenFields.setWhenValueRule(checkOrCaseWhenField.getWhenValueRule());
									checkFieldCaseWhenFields.setWhenValue(checkOrCaseWhenField.getWhenValue());
									checkFieldCaseWhenFields.setWhenValueScope(checkOrCaseWhenField.getWhenValueScope());
									checkFieldCaseWhens.getCheckFieldCaseWhenFieldList().add(checkFieldCaseWhenFields);
									checkFieldOrs.getCheckFieldCaseWhenList().add(checkFieldCaseWhens);
								}
							}
						}
						checkTable.getCheckFieldOrList().add(checkFieldOrs);
						
					}*/
				}
				checkInstance.getCheckTableList().add(checkTable);	
			}
			
			//聚合校验
			else if(checkNode.getCheckNodeType().equals("2")){
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldAggregationList.class);
				detachedCriteria.add(Restrictions.eq("checkNode", checkNode));
				List<report.dto.CheckFieldAggregationList> checkFieldAggregationListList = (List<report.dto.CheckFieldAggregationList>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(checkFieldAggregationListList.size() == 0){
					throw new Exception("未设置聚合校验结点");
				}
				
				report.dto.CheckFieldAggregationList reportCheckFieldAggregationList = checkFieldAggregationListList.get(0);
				
				//聚合校验 参数
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckExtendAggregationParam.class);
				detachedCriteria.add(Restrictions.eq("checkFieldAggregationList", reportCheckFieldAggregationList));		
				List<report.dto.CheckExtendAggregationParam> checkExtendAggregationParamList = (List<report.dto.CheckExtendAggregationParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});	
				
				List<CheckFieldParamField> checkFieldParamFieldList = new ArrayList<CheckFieldParamField>();
				
				for(report.dto.CheckExtendAggregationParam checkExtendAggregationParam : checkExtendAggregationParamList){
					framework.reportCheck.CheckFieldParamField checkFieldParamFields = new CheckFieldParamField();
					checkFieldParamFields.setParamName(checkExtendAggregationParam.getAutoETL_Param().getStrParamName());
					checkFieldParamFields.setType("1");
					checkFieldParamFieldList.add(checkFieldParamFields);
				}
				
				framework.reportCheck.CheckFieldAggregationList checkFieldAggregationLists = new framework.reportCheck.CheckFieldAggregationList();
				
				checkFieldAggregationLists.setDiscription(reportCheckFieldAggregationList.getDiscription());
				checkFieldAggregationLists.setCheckFieldParamFieldList(checkFieldParamFieldList);
				
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldAggregation.class);
				detachedCriteria.add(Restrictions.eq("checkFieldAggregationList", reportCheckFieldAggregationList));		
				List<report.dto.CheckFieldAggregation> checkFieldAggregationList = (List<report.dto.CheckFieldAggregation>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});	
				
				report.dto.CheckFieldAggregation reportCheckFieldAggregations = checkFieldAggregationList.get(0);
				
					framework.reportCheck.CheckFieldAggregation checkFieldAggregations = new framework.reportCheck.CheckFieldAggregation();
					checkFieldAggregations.setSplitDiscription(reportCheckFieldAggregations.getSplitDiscription());
					checkFieldAggregations.setFieldType(reportCheckFieldAggregations.getFieldType());
					checkFieldAggregations.setCompareType(reportCheckFieldAggregations.getCompareType());
					checkFieldAggregations.setEndRate(reportCheckFieldAggregations.getEndRate());
					checkFieldAggregations.setEndValue(reportCheckFieldAggregations.getEndValue());
					checkFieldAggregations.setLeftSplitField(reportCheckFieldAggregations.getLeftSplitField());
					checkFieldAggregations.setRightSplitField(reportCheckFieldAggregations.getRightSplitField());
					checkFieldAggregations.setOperationType(reportCheckFieldAggregations.getOperationType());
					checkFieldAggregations.setStartRate(reportCheckFieldAggregations.getStartRate());
					checkFieldAggregations.setStartValue(reportCheckFieldAggregations.getStartValue());				
					
					singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldAggregationLeftField.class);
					detachedCriteria.add(Restrictions.eq("checkFieldAggregation", reportCheckFieldAggregations));		
					List<report.dto.CheckFieldAggregationLeftField> checkFieldAggregationLeftFieldLists = (List<report.dto.CheckFieldAggregationLeftField>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});		
					
					List<framework.reportCheck.CheckFieldAggregationField> leftAggregationFieldLists  = new ArrayList<framework.reportCheck.CheckFieldAggregationField>();
					
					for(report.dto.CheckFieldAggregationLeftField checkFieldAggregationLeftField : checkFieldAggregationLeftFieldLists){
						framework.reportCheck.CheckFieldAggregationField checkFieldAggregationField = new CheckFieldAggregationField();
						checkFieldAggregationField.setDiscription(checkFieldAggregationLeftField.getDiscription());
						checkFieldAggregationField.setJoinType(checkFieldAggregationLeftField.getJoinType());
						checkFieldAggregationField.setProcedureName(checkFieldAggregationLeftField.getProcedureName());
						checkFieldAggregationField.setProcedureDiscription(checkFieldAggregationLeftField.getProcedureDiscription());
						checkFieldAggregationField.setProcedureParam(checkFieldAggregationLeftField.getProcedureParam());
						checkFieldAggregationField.setProcedureResultField(checkFieldAggregationLeftField.getProcedureResultField());
						checkFieldAggregationField.setProcedureSplitField(checkFieldAggregationLeftField.getProcedureSplitField());
						checkFieldAggregationField.setValue(checkFieldAggregationLeftField.getValue());
						leftAggregationFieldLists.add(checkFieldAggregationField);
					}
					checkFieldAggregations.setLeftFieldList(leftAggregationFieldLists);
					
					singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldAggregationRightField.class);
					detachedCriteria.add(Restrictions.eq("checkFieldAggregation", reportCheckFieldAggregations));		
					List<report.dto.CheckFieldAggregationRightField> checkFieldAggregationRightFieldLists = (List<report.dto.CheckFieldAggregationRightField>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});		
					
					List<framework.reportCheck.CheckFieldAggregationField> rightAggregationFieldLists  = new ArrayList<framework.reportCheck.CheckFieldAggregationField>();
					
					for(report.dto.CheckFieldAggregationRightField checkFieldAggregationRightField : checkFieldAggregationRightFieldLists){
						framework.reportCheck.CheckFieldAggregationField checkFieldAggregationField = new CheckFieldAggregationField();
						checkFieldAggregationField.setDiscription(checkFieldAggregationRightField.getDiscription());
						checkFieldAggregationField.setJoinType(checkFieldAggregationRightField.getJoinType());
						checkFieldAggregationField.setProcedureName(checkFieldAggregationRightField.getProcedureName());
						checkFieldAggregationField.setProcedureDiscription(checkFieldAggregationRightField.getProcedureDiscription());
						checkFieldAggregationField.setProcedureParam(checkFieldAggregationRightField.getProcedureParam());
						checkFieldAggregationField.setProcedureResultField(checkFieldAggregationRightField.getProcedureResultField());
						checkFieldAggregationField.setProcedureSplitField(checkFieldAggregationRightField.getProcedureSplitField());
						checkFieldAggregationField.setValue(checkFieldAggregationRightField.getValue());
						rightAggregationFieldLists.add(checkFieldAggregationField);
					}
					checkFieldAggregations.setRightFieldList(rightAggregationFieldLists);
					
					checkFieldAggregationLists.getCheckFieldAggregationList().add(checkFieldAggregations);
					
					checkInstance.getCheckFieldAggregationList().add(checkFieldAggregationLists);
			}
			
			//一致性校验,唯一性校验
			else if(checkNode.getCheckNodeType().equals("3")){
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckConsistentList.class);
				detachedCriteria.add(Restrictions.eq("checkNode", checkNode));
				List<report.dto.CheckConsistentList> checkConsistentListList = (List<report.dto.CheckConsistentList>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(checkConsistentListList.size() == 0){
					throw new Exception("未设置唯一性/一致性校验结点");
				}
				
				report.dto.CheckConsistentList reportCheckConsistentList = checkConsistentListList.get(0);
				
				//一致性校验,唯一性校验 参数
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckExtendConsistentParam.class);
				detachedCriteria.add(Restrictions.eq("checkConsistentList", reportCheckConsistentList));		
				List<report.dto.CheckExtendConsistentParam> checkExtendConsistentParamList = (List<report.dto.CheckExtendConsistentParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});	
				
				List<CheckFieldParamField> checkFieldParamFieldList = new ArrayList<CheckFieldParamField>();
				
				for(report.dto.CheckExtendConsistentParam checkExtendConsistentParam : checkExtendConsistentParamList){
					framework.reportCheck.CheckFieldParamField checkFieldParamFields = new CheckFieldParamField();
					checkFieldParamFields.setParamName(checkExtendConsistentParam.getAutoETL_Param().getStrParamName());
					checkFieldParamFields.setType("1");
					checkFieldParamFieldList.add(checkFieldParamFields);
				}
				
				framework.reportCheck.CheckFieldConsistentList checkFieldConsistentList = new framework.reportCheck.CheckFieldConsistentList();
				
				checkFieldConsistentList.setDiscription(reportCheckConsistentList.getDiscription());
				checkFieldConsistentList.setIgnoreVal(reportCheckConsistentList.getIgnoreVal());
				checkFieldConsistentList.setConsistenttype(reportCheckConsistentList.getConsistenttype());
				checkFieldConsistentList.setCheckFieldParamFieldList(checkFieldParamFieldList);
				
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckConsistentGroup.class);
				detachedCriteria.add(Restrictions.eq("checkConsistentList", reportCheckConsistentList));		
				List<report.dto.CheckConsistentGroup> checkConsistentGroupList = (List<report.dto.CheckConsistentGroup>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});		
				
				framework.reportCheck.CheckFieldConsistent checkFieldConsistent = new framework.reportCheck.CheckFieldConsistent();
				
				
				for(report.dto.CheckConsistentGroup checkConsistentGroups : checkConsistentGroupList){
					framework.reportCheck.ConsisentGroup consisentGroups = new framework.reportCheck.ConsisentGroup();
					consisentGroups.setKeyDiscription(checkConsistentGroups.getKeyDiscription());
					consisentGroups.setKeyField(checkConsistentGroups.getKeyField());
					consisentGroups.setNameDiscription(checkConsistentGroups.getNameDiscription());
					consisentGroups.setNameField(checkConsistentGroups.getNameField());
					consisentGroups.setProcedureName(checkConsistentGroups.getProcedureName());
					consisentGroups.setProcedureDiscription(checkConsistentGroups.getProcedureDiscription());
					consisentGroups.setProcedureParam(checkConsistentGroups.getProcedureParam());
					consisentGroups.setProcedureDiscription(checkConsistentGroups.getProcedureDiscription());
					checkFieldConsistent.getConsistentGroupList().add(consisentGroups);
				}
				
				checkFieldConsistentList.getCheckFieldConsistentList().add(checkFieldConsistent);
				
				checkInstance.getCheckFieldConsistentList().add(checkFieldConsistentList);
			}
			//完整性校验
			else if(checkNode.getCheckNodeType().equals("4")){
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldScopeList.class);
				detachedCriteria.add(Restrictions.eq("checkNode", checkNode));
				List<report.dto.CheckFieldScopeList> checkFieldScopeListList = (List<report.dto.CheckFieldScopeList>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(checkFieldScopeListList.size() == 0){
					throw new Exception("未设置完整性校验结点");
				}
				report.dto.CheckFieldScopeList reportCheckFieldScopeList = checkFieldScopeListList.get(0);
				
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckExtendScopeParam.class);
				detachedCriteria.add(Restrictions.eq("checkFieldScopeList", reportCheckFieldScopeList));		
				List<report.dto.CheckExtendScopeParam> checkExtendScopeParamList = (List<report.dto.CheckExtendScopeParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});	
				
				List<CheckFieldParamField> checkFieldParamFieldList = new ArrayList<CheckFieldParamField>();
				
				for(report.dto.CheckExtendScopeParam checkExtendScopeParam : checkExtendScopeParamList){
					framework.reportCheck.CheckFieldParamField checkFieldParamFields = new CheckFieldParamField();
					checkFieldParamFields.setParamName(checkExtendScopeParam.getAutoETL_Param().getStrParamName());
					checkFieldParamFields.setType("1");
					checkFieldParamFieldList.add(checkFieldParamFields);
				}
				
				framework.reportCheck.CheckFieldScopeList checkFieldScopeLists = new framework.reportCheck.CheckFieldScopeList();
				
				checkFieldScopeLists.setDiscription(reportCheckFieldScopeList.getDiscription());
				checkFieldScopeLists.setCheckFieldParamFieldList(checkFieldParamFieldList);
				
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldScope.class);
				detachedCriteria.add(Restrictions.eq("checkFieldScopeList", reportCheckFieldScopeList));		
				List<report.dto.CheckFieldScope> checkFieldScopeList = (List<report.dto.CheckFieldScope>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});	
				
				report.dto.CheckFieldScope reportCheckFieldScopes = checkFieldScopeList.get(0);
				
				framework.reportCheck.CheckFieldScope checkFieldScopes = new framework.reportCheck.CheckFieldScope();
				checkFieldScopes.setBelongType(reportCheckFieldScopes.getBelongType());
					
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldScopeLeftField.class);
				detachedCriteria.add(Restrictions.eq("checkFieldScope", reportCheckFieldScopes));		
				List<report.dto.CheckFieldScopeLeftField> checkFieldScopeLeftFields = (List<report.dto.CheckFieldScopeLeftField>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});		
				
				List<framework.reportCheck.CheckFieldScopeField> leftScopeFieldLists  = new ArrayList<framework.reportCheck.CheckFieldScopeField>();
				
				for(report.dto.CheckFieldScopeLeftField checkFieldScopeLeftField : checkFieldScopeLeftFields){
					framework.reportCheck.CheckFieldScopeField checkFieldScopeFields = new framework.reportCheck.CheckFieldScopeField();
					checkFieldScopeFields.setDiscription(checkFieldScopeLeftField.getDiscription());
					checkFieldScopeFields.setName(checkFieldScopeLeftField.getName());
					checkFieldScopeFields.setProcedureDiscription(checkFieldScopeLeftField.getProcedureDiscription());
					checkFieldScopeFields.setProcedureName(checkFieldScopeLeftField.getProcedureName());
					checkFieldScopeFields.setProcedureParam(checkFieldScopeLeftField.getProcedureParam());
					leftScopeFieldLists.add(checkFieldScopeFields);
				}
				checkFieldScopes.setLeftFieldList(leftScopeFieldLists);
				
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(report.dto.CheckFieldScopeRightField.class);
				detachedCriteria.add(Restrictions.eq("checkFieldScope", reportCheckFieldScopes));		
				List<report.dto.CheckFieldScopeRightField> checkFieldScopeRightFieldLists = (List<report.dto.CheckFieldScopeRightField>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});		
				
				List<framework.reportCheck.CheckFieldScopeField> rightScopeFieldLists  = new ArrayList<framework.reportCheck.CheckFieldScopeField>();
				
				for(report.dto.CheckFieldScopeRightField checkFieldScopeRightField : checkFieldScopeRightFieldLists){
					framework.reportCheck.CheckFieldScopeField checkFieldScopeFields = new framework.reportCheck.CheckFieldScopeField();
					checkFieldScopeFields.setDiscription(checkFieldScopeRightField.getDiscription());
					checkFieldScopeFields.setName(checkFieldScopeRightField.getName());
					checkFieldScopeFields.setProcedureDiscription(checkFieldScopeRightField.getProcedureDiscription());
					checkFieldScopeFields.setProcedureName(checkFieldScopeRightField.getProcedureName());
					checkFieldScopeFields.setProcedureParam(checkFieldScopeRightField.getProcedureParam());
					rightScopeFieldLists.add(checkFieldScopeFields);
				}
				checkFieldScopes.setRightFieldList(rightScopeFieldLists);
				
				checkFieldScopeLists.getCheckFieldScopeList().add(checkFieldScopes);
				
				checkInstance.getCheckFieldScopeList().add(checkFieldScopeLists);
			}
		}
		return checkInstance;
	}
}
