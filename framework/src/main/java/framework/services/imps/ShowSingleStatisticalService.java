package framework.services.imps;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.ICondition;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowFieldCondition;
import framework.show.ShowInstance;

public class ShowSingleStatisticalService extends SingleObjectShowListService {

	private String[] countFieldList;
	
	private String[] showFieldList;
	
	private boolean showNullCondtion;
	
	private String dealWithClassName;
	
	private String conditionAboutCountFieldName;
	
	private List<String> statisticalTranslateClassList;
	
	public  String[] getCountFieldList() {
		return countFieldList;
	}
	public void setCountFieldList(String[] countFieldList) {
		this.countFieldList = countFieldList;
	}
	public String[] getShowFieldList() {
		return showFieldList;
	}
	public void setShowFieldList(String[] showFieldList) {
		this.showFieldList = showFieldList;
	}
	public boolean isShowNullCondtion() {
		return showNullCondtion;
	}
	public void setShowNullCondtion(boolean showNullCondtion) {
		this.showNullCondtion = showNullCondtion;
	}
	public String getDealWithClassName() {
		return dealWithClassName;
	}
	public void setDealWithClassName(String dealWithClassName) {
		this.dealWithClassName = dealWithClassName;
	}
	public String getConditionAboutCountFieldName() {
		return conditionAboutCountFieldName;
	}
	public void setConditionAboutCountFieldName(String conditionAboutCountFieldName) {
		this.conditionAboutCountFieldName = conditionAboutCountFieldName;
	}
	
	public void setStatisticalTranslateClassList(
			List<String> statisticalTranslateClassList) {
		this.statisticalTranslateClassList = statisticalTranslateClassList;
	}

	public List<String> getStatisticalTranslateClassList() {
		return statisticalTranslateClassList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		countFieldList=(countFieldList==null?new String[0]:countFieldList);
		
		showFieldList=(showFieldList==null?new String[0]:showFieldList);
		boolean isExistCondition=true;
		List<Object> objectList = new ArrayList<Object>();
		Object condition = RequestManager.getTOject();//获取当前的TObject的值
		try
		{
		
			for(int i =0;i<showFieldList.length;i++){
				String[] fieldSplit = showFieldList[i].split(",");
				if(fieldSplit[0].equals("1")){
					setConditionAboutCountFieldName(fieldSplit[1]) ;
				}
				if(fieldSplit[0].equals("2")){
					ReflectOperation.getFieldValue(condition, fieldSplit[1]);
				}
			}
			
			
		}
		catch(Exception ex){
			condition= Class.forName(RequestManager.getTName()).newInstance();
			RequestManager.setTOject(condition);
			isExistCondition=false;
		}
		int firstResult = 0;
		if(RequestManager.getCurrentPage() != null){
			firstResult = (RequestManager.getCurrentPage() - 1) * ShowParamManager.getPageSize();
		}
		Set<String> ClassName=new HashSet<String>();
		Set<String> conditionList=new HashSet<String>();
		Set<String> ParentShowField=new HashSet<String> ();
		for(String countField :countFieldList){
			for(String showFiled : countField.split(",")){
				if(showFiled.indexOf(".")>-1 ){
					if(ClassName.contains(showFiled.substring(0,showFiled.lastIndexOf(".")-1))){
						ClassName.add(showFiled.substring(0,showFiled.lastIndexOf(".")-1));
					}
				}
			}
			
		}
		for(String countField :showFieldList){
			for(String showFiled : countField.split(",")){
				if(showFiled.indexOf(".")>-1 ){
					if(!ClassName.contains(showFiled.substring(0,showFiled.lastIndexOf(".")))){
						ClassName.add(showFiled.substring(0,showFiled.lastIndexOf(".")));
					}
				}
			}
			
		}
		
		
		if(condition != null || showNullCondtion){
			boolean isNullCondition = false;
			if(condition == null){
				isNullCondition = true;
			}
			else{
				int isNullVondition=0;
				for(int i =0;i<showFieldList.length;i++){
					String[] fieldSplit = showFieldList[i].split(",");
					if(fieldSplit[0].equals("2")){
						if(countFieldList.length==1 && fieldSplit[1].equals(conditionAboutCountFieldName)){
							ReflectOperation.setFieldValue(condition, fieldSplit[1], countFieldList[0]);
						}
						else{

							conditionList.add(fieldSplit[1]);
						}
						Object fieldValue = ReflectOperation.getFieldValue(condition, fieldSplit[1]);
						if(fieldValue == null || fieldValue.toString().equals("")){
							isNullVondition++;
						}
					}
				}
				if(isNullVondition==conditionList.size()){
					isNullCondition = true;
				}
			}
			IParamObjectResultExecute singleObjectFindCountByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			if(condition != null && !isNullCondition && conditionAboutCountFieldName!=null){
				Object objectValue=ReflectOperation.getFieldValue(condition,conditionAboutCountFieldName);
				boolean isEmptyObjectValue=false;
				if(objectValue ==null){
					isEmptyObjectValue=true;
				}
				else if(StringUtils.isBlank(objectValue.toString())){
					isEmptyObjectValue=true;
				}
				Set<String> ChooseconditionAboutCountField =new HashSet<String>();
				for(String s : countFieldList){
					if(!isEmptyObjectValue && objectValue.equals(s)){
						ChooseconditionAboutCountField=new HashSet<String>();
						ChooseconditionAboutCountField.add(s);
						for(String sshow: s.split(",")){
							ParentShowField.add(sshow);
						}
						break;
					}
					else{
						ChooseconditionAboutCountField.add(s);
					}
				}
				HandleDate(ChooseconditionAboutCountField,singleObjectFindCountByCriteriaDao,objectList,conditionList,ClassName,isExistCondition);
			}
			else if((condition==null || isNullCondition) && showNullCondtion){
				Set<String> ChooseconditionAboutCountField =new HashSet<String>();
				for(String s : countFieldList){
					ChooseconditionAboutCountField.add(s);
				}
				HandleDate(ChooseconditionAboutCountField,singleObjectFindCountByCriteriaDao,objectList,conditionList,ClassName,isExistCondition);
			}
		}
		Set<String> showListl=new HashSet<String>();
		Map<String,Object> mapconditionMap=new LinkedHashMap<String,Object>();
		for(String showList : showFieldList){
			if(ParentShowField.isEmpty() && !showListl.contains(showList.split(",")[1])){
				if(showList.split(",")[0].equals("2")){
					if(countFieldList.length==1 && showList.split(",")[1].equals(conditionAboutCountFieldName)){
						continue;
					}
					Object filedValue=ReflectOperation.getFieldValue(condition, showList.split(",")[1]);
					if(filedValue!=null){
						if(!ReflectOperation.isBaseType(filedValue)){
							Object otemp=ReflectOperation.getPrimaryKeyValue(filedValue);
							if(otemp !=null){
								if(!StringUtils.isBlank(otemp.toString())){
									showListl.add(showList.split(",")[1]);
									mapconditionMap.put(showList.split(",")[1], showList.split(",")[1]);
								}
							}
						}
						else{
							showListl.add(showList.split(",")[1]);
							mapconditionMap.put(showList.split(",")[1], showList.split(",")[1]);
						}
					}
				}
				else{
					if(countFieldList.length==1  && showList.split(",")[1].equals(conditionAboutCountFieldName)){
						continue;
					}
					showListl.add(showList.split(",")[1]);
					mapconditionMap.put(showList.split(",")[1], showList.split(",")[1]);
				}
			}
			else  if(!showListl.contains(showList.split(",")[1]) 
					&& ((showList.split(",")[0].equals("5") 
							&& ParentShowField.contains(showList.split(",")[1])) 
					   || !showList.split(",")[0].equals("5"))){
				
					if(showList.split(",")[0].equals("2")){
						if(countFieldList.length==1 && showList.split(",")[1].equals(conditionAboutCountFieldName)){
							continue;
						}
						Object filedValue=ReflectOperation.getFieldValue(condition, showList.split(",")[1]);
						if(filedValue!=null){
							if(!ReflectOperation.isBaseType(filedValue)){
								Object otemp=ReflectOperation.getPrimaryKeyValue(filedValue);
								if(otemp !=null){
									if(!StringUtils.isBlank(otemp.toString())){
										showListl.add(showList.split(",")[1]);
										mapconditionMap.put(showList.split(",")[1], showList.split(",")[1]);
									}
								}
							}
							else{
								showListl.add(showList.split(",")[1]);
								mapconditionMap.put(showList.split(",")[1], showList.split(",")[1]);
							}
						}
					}
					else{
						if(countFieldList.length==1  && showList.split(",")[1].equals(conditionAboutCountFieldName)){
							continue;
						}
						showListl.add(showList.split(",")[1]);
						mapconditionMap.put(showList.split(",")[1], showList.split(",")[1]);
					}
			}
		}	
		ShowInstance showInstance = ReflectOperation.getShowInstance(RequestManager.getTName(), ShowParamManager.getShowInstanceName());
		for(ShowField showField : showInstance.getShowFieldList()){
			if(!mapconditionMap.containsKey(showField.getFieldName())){
				showField.setListVisible(false);
			}
			else{
				showField.setListVisible(true);
			}
		}
		
		//处理countFieldList类
		Map<Object,Object> returnmapList=new LinkedHashMap<Object,Object>();
		for(String strCountFiled : countFieldList){
			if(dealWithClassName ==null){continue;}
			Object objectOld = Class.forName(dealWithClassName).newInstance();
			List<Field> fieldList = ReflectOperation.getColumnFieldList(objectOld.getClass());
			Map<Object,Object> mapList=new LinkedHashMap<Object,Object>();
			for(Field field : fieldList){
				 if(field.isAnnotationPresent(IColumn.class)){
		            	IColumn iColumn = field.getAnnotation(IColumn.class);
		            	if(!StringUtils.isBlank(iColumn.description())){
		            		mapList.put(field.getName(), iColumn.description());
		            	}
		            	else{
		            		 Field pkList=ReflectOperation.getPrimaryKeyField(field.getType());
		            		 if(pkList.isAnnotationPresent(IColumn.class)){
					            	IColumn pkiColumn = pkList.getAnnotation(IColumn.class);
					            	if(!StringUtils.isBlank(pkiColumn.description())){
					            		mapList.put(field.getName(), pkiColumn.description());
					            	}
					            	else{
					            		mapList.put(field.getName(), field.getName());
					            	}
					           }
		            		 Field pkListn=ReflectOperation.getKeyNameField(field.getType());
			            	 if(pkListn!=null && mapList.get(field.getName())==null){
			            		 if(pkListn.isAnnotationPresent(IColumn.class)){
						            	IColumn pkiColumn = pkListn.getAnnotation(IColumn.class);
						            	if(!StringUtils.isBlank(pkiColumn.description())){
						            		mapList.put(field.getName(), pkiColumn.description());
						            	}
						            	else{
						            		mapList.put(field.getName(), field.getName());
						            	}
						           }
			            	 }
			            	 if(mapList.get(field.getName())==null){
			            		 mapList.put(field.getName(), field.getName());
			            	 }
		            	 }
		            	 
		           }
			}
			for(Field field : fieldList){
				if(ClassName.contains(field.getName())){
					List<Field> fieldListk = ReflectOperation.getColumnFieldList(field.getType());
					for(Field fieldk : fieldListk){
						 if(fieldk.isAnnotationPresent(IColumn.class)){
				            	IColumn iColumn = fieldk.getAnnotation(IColumn.class);
				            	if(!StringUtils.isBlank(iColumn.description())){
				            		mapList.put(field.getName()+"."+fieldk.getName(), iColumn.description());
				            	}
				            	else{
				            		Field pkList=ReflectOperation.getPrimaryKeyField(field.getType());
				            		 if(pkList.isAnnotationPresent(IColumn.class)){
							            	IColumn pkiColumn = pkList.getAnnotation(IColumn.class);
							            	if(!StringUtils.isBlank(pkiColumn.description())){
							            		mapList.put(field.getName()+"."+fieldk.getName(), pkiColumn.description());
							            	}
							            	else{
							            		mapList.put(field.getName()+"."+fieldk.getName(), fieldk.getName());
							            	}
							            }
				            	 }
				            	 Field pkListn=ReflectOperation.getKeyNameField(field.getType());
				            	 if(pkListn!=null && mapList.get(field.getName())==null){
				            		 if(pkListn.isAnnotationPresent(IColumn.class)){
							            	IColumn pkiColumn = pkListn.getAnnotation(IColumn.class);
							            	if(!StringUtils.isBlank(pkiColumn.description())){
							            		mapList.put(field.getName(), pkiColumn.description());
							            	}
							            	else{
							            		mapList.put(field.getName(), field.getName());
							            	}
							           }
				            	 }
				            	 
				            	 if(mapList.get(field.getName())==null){
				            		 mapList.put(field.getName(), field.getName());
				            	 }
				           }
					}
				}
			}
			Object fieldValue="";
			for(String ColumnName : strCountFiled.split(",")){
				if(mapList.containsKey(ColumnName)){
					fieldValue=fieldValue+mapList.get(ColumnName).toString()+"&";
				}
			}
			//fieldValue=fieldValue.toString().trim().replace(" ", "&");
			if(!StringUtils.isBlank(fieldValue.toString())){
				fieldValue=fieldValue.toString().substring(0, fieldValue.toString().length()-1);
			}
			returnmapList.put(strCountFiled, fieldValue);
		}

		((Map<String,Object>)ServletActionContext.getContext().get("request")).put(RequestManager.getTName()+"showListData", returnmapList);
		
		Map<String,Object> mapListCondition=new LinkedHashMap<String,Object>();
		for(String scondition : conditionList){
			mapListCondition.put(scondition, scondition);
		}
		((Map<String,Object>)ServletActionContext.getContext().get("request")).put(RequestManager.getTName()+"ConditionListData", mapListCondition);
		
		
		List<Object> pageList = new ArrayList<Object>();
		for(int i=firstResult;i<firstResult + ShowParamManager.getPageSize();i++){
			if(i > objectList.size() - 1){
				break;
			}
			pageList.add(objectList.get(i));
		}
		LogicParamManager.setTotalCount(objectList.size());
		this.setServiceResult(pageList);	
	}
	
	@SuppressWarnings("unchecked")
	private void HandleDate(Set<String> countFieldList,IParamObjectResultExecute singleObjectFindCountByCriteriaDao,List<Object> objectList,Set<String> conditionList,Set<String> ClassName,boolean isExistCondition)  throws Exception{
		for(String TempcountFiledList : countFieldList){
			if(dealWithClassName ==null){continue;}
			
			Map<String,Object> map=new LinkedHashMap<String,Object>();
			Object objectOld = Class.forName(dealWithClassName).newInstance();
			String dealWithName=dealWithClassName.substring(0,1).toLowerCase()+dealWithClassName.substring(1);
			if(dealWithClassName.indexOf(".")>-1){
				dealWithName =dealWithClassName.substring(dealWithClassName.lastIndexOf(".")+1).substring(0,1).toLowerCase()+dealWithClassName.substring(dealWithClassName.lastIndexOf(".")+1).substring(1);
			}
			
			
			Object conditionObject = FrameworkFactory.CreateClass(TActionRule.getConditionClassName(dealWithClassName));
			Object conditionTObject = FrameworkFactory.CreateClass(TActionRule.getConditionClassName(RequestManager.getTName()));
			
			String[] strFiledName=TempcountFiledList.split(",");
			
			int GroupCount=0;
			boolean flag=true;
			while(flag){
				
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dealWithClassName),dealWithName);
				if(!ClassName.isEmpty()){
					List<Field> fieldList=ReflectOperation.getColumnFieldList(objectOld.getClass());
					for(String classname :ClassName){
						// detachedCriteria.createAlias(classname,classname.substring(classname.indexOf(".", 2)+1));
						for(Field field : fieldList){
							if(field.getName().equals(classname)){
								detachedCriteria.createAlias(field.getName(),classname);
								break;
							}
						}
					}
				}
				LogicParamManager.setDetachedCriteria(detachedCriteria);
				ProjectionList projList = Projections.projectionList();
				for(String sunbStr : TempcountFiledList.split(",")){
					if(sunbStr.indexOf(".")==-1){
						sunbStr=dealWithName+"."+sunbStr;
					}
					projList.add(Projections.groupProperty(sunbStr));
				}
				
				
				int tmepCount=0;
				for(String s: showFieldList){
					flag=false;
					if(s.split(",")[0].equals("2") && !s.split(",")[1].equals(conditionAboutCountFieldName)){
						Object o=ReflectOperation.getFieldValue(RequestManager.getTOject(),s.split(",")[1]);
						if(o !=null){
							String FileName=s.split(",")[2];
							List<ShowFieldCondition> showFieldConditionList = ReflectOperation.getShowFieldConditionList(Class.forName(dealWithClassName));
							if(s.split(",")[2].indexOf(".")==-1){
								
								for(ShowFieldCondition showField : showFieldConditionList){
									String filedMame=s.split(",")[2];
									if(showField.getFieldName().equals(filedMame) && showField.getTarget() !=null){
										FileName=showField.getTarget();
										break;
									}
									else if(showField.getFieldName().equals(filedMame)){
										FileName=showField.getFieldName();
										break;
									}
								}
								if(Class.forName(dealWithClassName).getDeclaredField(FileName).getType().equals(Date.class)){
									try{
										DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
										o=format.parse(o.toString());
									}
									catch(Exception ex){}
								}
							}
							else{
									
								String ss=FileName.substring(0,FileName.lastIndexOf("."));
								FileName=FileName.substring(FileName.lastIndexOf(".")+1);
								List<ShowFieldCondition> showFieldConditionListx = ReflectOperation.getShowFieldConditionList(Class.forName(dealWithClassName).getDeclaredField(ss).getType());
								for(ShowFieldCondition showField : showFieldConditionListx){
									String filedMame=s.split(",")[2];
									if(filedMame.indexOf(".")>-1){
										filedMame=filedMame.substring(filedMame.lastIndexOf(".")+1);
									}
									if(showField.getFieldName().equals(filedMame) && showField.getTarget() !=null){
										FileName=showField.getTarget();
										break;
									}
									else if(showField.getFieldName().equals(filedMame)){
										FileName=showField.getFieldName();
										break;
									}
								}
								if(!StringUtils.isBlank(FileName)){
									if(Class.forName(dealWithClassName).getDeclaredField(ss).getType().getDeclaredField(FileName).getType().equals(Date.class)){
										try{
											DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
											o=format.parse(o.toString());
										}
										catch(Exception ex){}
									}
								}
							}
							
							
							if(s.split(",")[2].indexOf(".")==-1){
								s.split(",")[2] =dealWithName+"."+s.split(",")[2];
							}
							if(!ReflectOperation.isBaseType(o)){
								Object otemp=ReflectOperation.getPrimaryKeyValue(o);
								if(otemp !=null){
									if(!StringUtils.isBlank(otemp.toString())){
										HandleConditionSql(detachedCriteria,s.split(",")[2],o,ICondition.Comparison.EQ.toString());
									}
								}
							}
							else{
								if(conditionObject==null && conditionTObject==null){
									HandleConditionSql(detachedCriteria,s.split(",")[2],o,ICondition.Comparison.EQ.toString());
								}
								else{
									if(conditionObject !=null){
										for(ShowFieldCondition showField : showFieldConditionList){
											if(showField.getFieldName().equals(s.split(",")[2])){
												if(showField.getComparison()==null && showField.getTarget()==null){
													HandleConditionSql(detachedCriteria,s.split(",")[2],o,ICondition.Comparison.EQ.toString());
												}
												else if(showField.getComparison()==null && showField.getTarget() !=null){
													HandleConditionSql(detachedCriteria,showField.getTarget(),o,ICondition.Comparison.EQ.toString());
												}
												else if(showField.getComparison() !=null && showField.getTarget() ==null){
													HandleConditionSql(detachedCriteria,s.split(",")[2],o,showField.getComparison().toString());
												}
												else {
													HandleConditionSql(detachedCriteria,showField.getTarget(),o,showField.getComparison().toString());
												}
												break;
											}
										}
									}
									else{
										List<ShowFieldCondition> showFieldConditionTList = ReflectOperation.getShowFieldConditionList(Class.forName(RequestManager.getTName()));
										for(ShowFieldCondition showField : showFieldConditionTList){
											if(showField.getFieldName().equals(s.split(",")[1])){
												if(showField.getComparison() !=null ){
													HandleConditionSql(detachedCriteria,s.split(",")[2],o,showField.getComparison().toString());
												}
												break;
											}
										}
									}
								}
							}
						}
					}
					else if(s.split(",")[0].equals("3") && (GroupCount==0 || tmepCount==GroupCount)){
						if(GroupCount==0){
							detachedCriteria.setProjection(projList);
						
							List<Object> value2x = (List<Object>)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							for(Object object :value2x){
								try{
									Object[] objectx=(Object[])object;
									String primaryKey="";
									for(int i=0; i<objectx.length;i++){
										primaryKey=primaryKey+objectx[i];
									}
									map.put(primaryKey, object);
								}
								catch(Exception ex){
									map.put(object.toString(),object);
								}
							}
						}
						
						String sbu=s;
						if(sbu.indexOf(".")==-1){
							sbu=s.substring(0,s.lastIndexOf(",")+1)+dealWithName+"."+s.substring(s.lastIndexOf(",")+1);
						}
						HandleProject(sbu,projList,detachedCriteria);
						detachedCriteria.setProjection(projList);
							//
						List<Object> value2 = (List<Object>)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						for(Map.Entry<String, Object> maplist: map.entrySet()){
							try{
								Object[] objectx=(Object[])maplist.getValue();	
								Object[] Object=new Object[objectx.length+1];
								for(int it=0;it<objectx.length;it++){
									Object[it]=objectx[it];
								}
								if(value2.size()==0){
									Object[objectx.length]=0;
								}
								else{
									for(Object object :value2){
										try{
											String primaryKey="";
											Object[] objectx1=(Object[])object;
											for(int i=0; i<objectx1.length-1;i++){
												primaryKey=primaryKey+objectx1[i];
											}
											if(primaryKey.equals(maplist.getKey())){
												Object[objectx.length]=objectx1[objectx1.length-1];
											}
										}
										catch(Exception ex){
											Object[objectx.length]=0;
										}
									}
									//Object[objectx.length]=100;
								}
								map.put(maplist.getKey(),Object);
							}
							catch(Exception ex){
								Object[] Object=new Object[2];
								Object[0]=maplist.getValue();
								if(value2.size()==0){
									Object[1]=0;
								}
								else{
									for(Object object :value2){
										try{
											String primaryKey="";
											Object[] objectx1=(Object[])object;
											for(int i=0; i<objectx1.length-1;i++){
												primaryKey=primaryKey+objectx1[i];
											}
											if(primaryKey.equals(maplist.getKey())){
												Object[1]=objectx1[objectx1.length-1];
											}
										}
										catch(Exception exx){
											Object[1]=0;
										}
									}
								}
								map.put(maplist.getKey(),Object);
							}
						}
						GroupCount=tmepCount+1;
						flag=true;
						break;
					}
					tmepCount++;
				}
				if(!flag){
					break;
				}
			}
			try{
				Object[] value =new Object[map.size()];
				int valuei=0;
				for(Map.Entry<String,Object> mapx : map.entrySet()){
					value[valuei]=mapx.getValue();
					valuei++;
				}

				for(String str : this.statisticalTranslateClassList){
					ITranslate translate = (ITranslate)FrameworkFactory.CreateClass(str);
					translate.Translate();
				}
				List<Field> fieldList = ReflectOperation.getColumnFieldList(objectOld.getClass());
				Map<Object,Object> mapList=new LinkedHashMap<Object,Object>();
				for(Field field : fieldList){
					 if(field.isAnnotationPresent(IColumn.class)){
			            	IColumn iColumn = field.getAnnotation(IColumn.class);
			            	if(!StringUtils.isBlank(iColumn.description())){
			            		mapList.put(field.getName(), iColumn.description());
			            	}
			            	else{
			            		Field pkList=ReflectOperation.getPrimaryKeyField(field.getType());
			            		 if(pkList.isAnnotationPresent(IColumn.class)){
						            	IColumn pkiColumn = pkList.getAnnotation(IColumn.class);
						            	if(!StringUtils.isBlank(pkiColumn.description())){
						            		mapList.put(field.getName(), pkiColumn.description());
						            	}
						            	else{
						            		mapList.put(field.getName(), field.getName());
						            	}
						            }
			            	 }
			           }
				}
				for(Object o : value){
					Object object=Class.forName(RequestManager.getTName()).newInstance();
					int t=TempcountFiledList.split(",").length;
					for(String objName : showFieldList){
						if(objName.split(",")[0].equals("1")){
							Object fieldValue="";
							fieldValue=fieldValue.toString().trim().replace(" ", "|");
							ReflectOperation.setFieldValue(object, objName.split(",")[1], TempcountFiledList);
						}
						else if(objName.split(",")[0].equals("2") && !objName.split(",")[1].equals(conditionAboutCountFieldName)){
							List<ShowFieldCondition> showFieldConditionList = ReflectOperation.getShowFieldConditionList(object.getClass());
							String FileName="";
							for(ShowFieldCondition showField : showFieldConditionList){
								if(showField.getFieldName().equals(objName.split(",")[1]) && showField.getTarget() !=null){
									FileName=showField.getTarget();
									break;
								}
								else if(showField.getFieldName().equals(objName.split(",")[1])){
									FileName=showField.getFieldName();
								}
							}
							Object fieldValue = ReflectOperation.getFieldValue(RequestManager.getTOject(), objName.split(",")[1]);
							if(fieldValue != null && !fieldValue.toString().equals("")){
								ReflectOperation.setFieldValue(object, FileName, fieldValue);
							}
						}
						else if(objName.split(",")[0].equals("5")){
							Object[] columns=(Object[])o;
							int i=0;
							boolean isExist=false;
							for(String fileldName : strFiledName){
								if(objName.split(",").length>=3){
									if(fileldName.equals(objName.split(",")[2])){
										isExist=true;
										break;
									}
								}
								else if(objName.split(",").length>=2){
									if(fileldName.equals(objName.split(",")[1])){
										isExist=true;
										break;
									}
								}
								i++;
							}
							if(isExist){
								ReflectOperation.setFieldValue(object, objName.split(",")[1],columns[i]);
							}
						}
						else if(objName.split(",")[0].equals("3")){
							   	Object[] columns=(Object[])o;
							   	if(columns[t] !=null){
							   		ReflectOperation.setFieldValue(object, objName.split(",")[1],String.valueOf(columns[t]));
							   		t++;
							   	}
							   	else{
							   		ReflectOperation.setFieldValue(object, objName.split(",")[1],"0");
							   		t++;
							   	}
							}
					}
					objectList.add(object);
				}
			}
			catch(Exception ex){
				ExceptionLog.CreateLog(ex);
			}
		}
	}
	
	private void HandleProject(String subStr,ProjectionList projList,DetachedCriteria detachedCriteria){
		int tempsubStrValue=TypeParse.parseInt(subStr.split(",")[2]);
		switch (tempsubStrValue){
			case  1:
					 if(subStr.split(",")[3].indexOf("=")>-1){
						//projList.add(Projections.sum(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1]));
						/* String CaseWhenSql="case ";
							for(String subStrCase : subStr.split(",")[3].split("=")[1].split("-")){
								if(subStrCase.toUpperCase() !="NULL"){
									CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+"='"+ subStrCase+"' then 1 ";
								}
								else{
									CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+" is null then 1 ";
								}
							}
							CaseWhenSql+="end";
							projList.add(Projections.sum(CaseWhenSql).as(subStr.split(",")[1].split("=")[0]));*/
						projList.add(Projections.sum(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1].split("=")[0]));
						detachedCriteria.add(Restrictions.in(subStr.split(",")[3].split("=")[0].split("=")[1],subStr.split(",")[3].split("=")[1].split("-")));
					}
					else{
						projList.add(Projections.sum(subStr.split(",")[3]).as(subStr.split(",")[1]));
					}
					 break;
			case 2:
					if(subStr.split(",")[3].indexOf("=")>-1){
						//projList.add(Projections.avg(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1]));
						/*String CaseWhenSql="case ";
						for(String subStrCase : subStr.split(",")[3].split("=")[1].split("-")){
							if(subStrCase.toUpperCase() !="NULL"){
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+"='"+ subStrCase+"' then 1 ";
							}
							else{
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+" is null then 1 ";
							}
						}
						CaseWhenSql+="end";
						projList.add(Projections.avg(CaseWhenSql).as(subStr.split(",")[1].split("=")[0]));*/
						projList.add(Projections.avg(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1].split("=")[0]));
						detachedCriteria.add(Restrictions.in(subStr.split(",")[3].split("=")[0],subStr.split(",")[3].split("=")[1].split("-") ));
					}
					else{
						projList.add(Projections.avg(subStr.split(",")[3]).as(subStr.split(",")[1]));
					}
					 break;
			 case 3:
					if(subStr.split(",")[3].indexOf("=")>-1){
						//projList.add(Projections.count(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1]));
						/*String CaseWhenSql="case ";
						for(String subStrCase : subStr.split(",")[3].split("=")[1].trim().split("-")){
							if(subStrCase.toUpperCase() !="NULL"){
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+"='"+ subStrCase+"' then 1 ";
							}
							else{
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+" is null then 1 ";
							}
						}
						CaseWhenSql+="end";
						projList.add(Projections.count(CaseWhenSql).as(subStr.split(",")[1].split("=")[0]));*/
						projList.add(Projections.count(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1].split("=")[0]));
						detachedCriteria.add(Restrictions.in(subStr.split(",")[3].split("=")[0],subStr.split(",")[3].split("=")[1].split("-") ));
					}
					else{
						projList.add(Projections.count(subStr.split(",")[3]).as(subStr.split(",")[1]));
					}
					break;
			case 4:
				 if(subStr.split(",")[3].indexOf("=")>-1){
						//projList.add(Projections.countDistinct(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1]));
					   /*String CaseWhenSql="case ";
						for(String subStrCase : subStr.split(",")[3].split("=")[1].split("-")){
							if(subStrCase.toUpperCase().equals("NULL")){
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+"='"+ subStrCase+"' then 1 ";
							}
							else{
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+" is null then 1 ";
							}
						}
						CaseWhenSql+="end";
						projList.add(Projections.countDistinct(CaseWhenSql).as(subStr.split(",")[1].split("=")[0]));*/
						projList.add(Projections.countDistinct(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1].split("=")[0]));
					   detachedCriteria.add(Restrictions.in(subStr.split(",")[3].split("=")[0],subStr.split(",")[3].split("=")[1].split("-") ));
					}
					else{
						projList.add(Projections.countDistinct(subStr.split(",")[3]).as(subStr.split(",")[1]));
					}
					break;
			case 5:
					if(subStr.split(",")[3].indexOf("=")>-1){
						//projList.add(Projections.max(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1]));
						/*String CaseWhenSql="case ";
						for(String subStrCase : subStr.split(",")[3].split("=")[1].split("-")){
							if(subStrCase.toUpperCase() !="NULL"){
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+"='"+ subStrCase+"' then 1 ";
							}
							else{
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+" is null then 1 ";
							}
						}
						CaseWhenSql+="end";
						projList.add(Projections.max(CaseWhenSql).as(subStr.split(",")[1].split("=")[0]));*/
						projList.add(Projections.max(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1].split("=")[0]));
						detachedCriteria.add(Restrictions.in(subStr.split(",")[3].split("=")[0],subStr.split(",")[3].split("=")[1].split("-") ));
					}
					else{
						projList.add(Projections.max(subStr.split(",")[3]).as(subStr.split(",")[1]));
					}
					break;
			case 6:
					if(subStr.split(",")[3].indexOf("=")>-1){
						//projList.add(Projections.min(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1]));
						/*String CaseWhenSql="case ";
						for(String subStrCase : subStr.split(",")[3].split("=")[1].split("-")){
							if(subStrCase.toUpperCase() !="NULL"){
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+"='"+ subStrCase+"' then 1 ";
							}
							else{
								CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+" is null then 1 ";
							}
						}
						CaseWhenSql+="end";
						projList.add(Projections.min(CaseWhenSql).as(subStr.split(",")[1].split("=")[0]));*/
						projList.add(Projections.min(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1].split("=")[0]));
						detachedCriteria.add(Restrictions.in(subStr.split(",")[3].split("=")[0],subStr.split(",")[3].split("=")[1].split("-") ));
					}
					else{
						projList.add(Projections.min(subStr.split(",")[3]).as(subStr.split(",")[1]));
					}
					break;
			default:
				if(subStr.split(",")[3].indexOf("=")>-1){
					//projList.add(Projections.sum(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1]));
					/*String CaseWhenSql="case ";
					for(String subStrCase : subStr.split(",")[3].split("=")[1].split("-")){
						if(subStrCase.toUpperCase() !="NULL"){
							CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+"='"+ subStrCase+"' then 1 ";
						}
						else{
							CaseWhenSql= CaseWhenSql+" when "+ subStr.split(",")[3].split("=")[0]+" is null then 1 ";
						}
					}
					CaseWhenSql+="end";
					projList.add(Projections.sum(CaseWhenSql).as(subStr.split(",")[1].split("=")[0]));*/
					projList.add(Projections.sum(subStr.split(",")[3].split("=")[0]).as(subStr.split(",")[1].split("=")[0]));
					detachedCriteria.add(Restrictions.in(subStr.split(",")[3].split("=")[0],subStr.split(",")[3].split("=")[1].split("-") ));
				}
				else{
						projList.add(Projections.sum(subStr.split(",")[3]).as(subStr.split(",")[1]));
					}
			}		
	}

	private void HandleConditionSql(DetachedCriteria detachedCriteria,String FiledName,Object object,String Style){
		if(Style.equals(ICondition.Comparison.EQ.toString())){
			detachedCriteria.add(Restrictions.eq(FiledName,object));
		}
		else if(Style.equals(ICondition.Comparison.GE.toString())){
			detachedCriteria.add(Restrictions.ge(FiledName,object));
		}
		else if(Style.equals(ICondition.Comparison.GT.toString())){
			detachedCriteria.add(Restrictions.gt(FiledName,object));
		}
		else if(Style.equals(ICondition.Comparison.LE.toString())){
			detachedCriteria.add(Restrictions.le(FiledName,object));
		}
		else if(Style.equals(ICondition.Comparison.LT.toString())){
			detachedCriteria.add(Restrictions.lt(FiledName,object));
		}
		else if(Style.equals(ICondition.Comparison.NONE.toString())){
			detachedCriteria.add(Restrictions.eq(FiledName,object));
		}

	}

}
