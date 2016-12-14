package coresystem.service.imps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import coresystem.helper.TableClassGenerator;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.IVoidResultExecute;
import framework.show.ShowContext;

public class InitAutoDTOService implements IVoidResultExecute{

	@SuppressWarnings("unchecked")
	public void voidResultExecute() throws Exception {
		try {
			class SortByNSeq implements Comparator {
				public int compare(Object o1, Object o2) {
					ReportModel_Field s1 = (ReportModel_Field) o1;
					ReportModel_Field s2 = (ReportModel_Field) o2;
					if (s1.getNSeq() > s2.getNSeq())
						return 1;
					return 0;
				}
			}
			
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Table"));
			detachedCriteria.addOrder(Order.asc("strAddFields"));
			List<ReportModel_Table> tables = (List<ReportModel_Table>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
	
			for(ReportModel_Table table : tables) { // 生成每个表的类
				if(table.getStrAutoDTO() != null && table.getStrAutoDTO().equals("1")) ;
				else continue;
				
				detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Field"));
				detachedCriteria.add(Restrictions.eq("reportModel_Table", table));
				
				// 查询 singleObjectFindByCriteriaDao
				dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		    	List<ReportModel_Field> objectListSub = (List<ReportModel_Field>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    	
		    	Map<String, String> fieldNameMap = new LinkedHashMap<String,String>();
		    	boolean hasPrimaryKey = false;
		    	Map<String, String> autoFieldMap = ShowContext.getInstance().getShowEntityMap().get("autoField");
		    	ArrayList<String>  autoFields = new ArrayList<String>();
		    	
		    	if(autoFieldMap == null 
		    			|| (table.getStrAddFields() != null && !table.getStrAddFields().equals("1"))){
		    		autoFieldMap = new HashMap<String,String>();
		    	}
		    	
				for(Map.Entry<String, String> entry: autoFieldMap.entrySet()) {
					String strField = entry.getValue();
					autoFields.add(strField);
				}
				
		    	for(ReportModel_Field field : objectListSub) { // 检测添加固定字段

					for(Map.Entry<String, String> entry: autoFieldMap.entrySet()) {
						String strField = entry.getValue();
						if(strField.indexOf(',') > -1) {
							if(strField.substring(0, strField.indexOf(',')).toUpperCase().equals(ReflectOperation.getFieldValue(field, "strFieldName").toString().toUpperCase()))
								autoFields.remove(strField);
						}
					}
					
					// 检测同名字段
					if(fieldNameMap.get(field.getStrFieldName()) == null)
						fieldNameMap.put(field.getStrFieldName(), "");
					else {
						break;
					}
					
					// 保证只有一个主键
					if(field.getIsKey().equals("1") && !hasPrimaryKey) {//主键
						hasPrimaryKey = true;
					}
					/*else if(field.getIsKey().equals("1")){
						break;
					}*/
				}

				ArrayList<ReportModel_Field> fieldArray = new ArrayList<ReportModel_Field>(objectListSub);
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
					 IParamVoidResultExecute dao0 =
						 (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
					 dao0.paramVoidResultExecute(new Object[]{fieldID, null});
				}
				
				// 生成配置给明细表的固定字段
				for(String strField: autoFields) {
					 String[] strArray = strField.split(","); //[.]
					 if(strArray.length < 4) continue;
					 
					 if(strArray[1].equals("time")) {
						 ReportModel_Field fieldDate = new ReportModel_Field();
						 fieldDate.setStrChinaName(strArray[2]);
						 fieldDate.setIsEnable("1");
						 fieldDate.setIsKey("2");
						 fieldDate.setNSeq(strArray[3]);
						 fieldDate.setStrDiscription("");
						 fieldDate.setStrEmptyType("2");
						 fieldDate.setStrLogicEmptyType("2");
						 fieldDate.setStrFieldName(strArray[0]);
						 fieldDate.setStrFieldType("3");
						 fieldDate.setReportModel_Table(table);
						 
						 fieldArray.add(fieldDate);
						 					
						 // 将字段记录保存到字段表
						 IParamVoidResultExecute dao2 =
							 (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
						 
						 dao2.paramVoidResultExecute(new Object[]{fieldDate, null});
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
						fieldInst.setStrEmptyType("2");
						fieldInst.setStrLogicEmptyType("2");
						fieldInst.setStrFieldName(strArray[0]);
						fieldInst.setStrFieldType("2");
						fieldInst.setStrConstList("coresystem.dto.InstInfo-strInstCode");
						fieldInst.setReportModel_Table(table);

						fieldArray.add(fieldInst);

						// 将字段记录保存到字段表
						IParamVoidResultExecute dao3 = (IParamVoidResultExecute) FrameworkFactory
								.CreateBean("singleObjectSaveDao");

						 dao3.paramVoidResultExecute(new Object[]{fieldInst, null});
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
							IParamVoidResultExecute dao3 = (IParamVoidResultExecute) FrameworkFactory
									.CreateBean("singleObjectSaveDao");
							dao3.paramVoidResultExecute(new Object[]{fieldUser, null});
					 }
					 else if(strArray[1].equals("timestamp")) {
						 ReportModel_Field fieldDate = new ReportModel_Field();
						 fieldDate.setStrChinaName(strArray[2]);
						 fieldDate.setIsEnable("1");
						 fieldDate.setIsKey("2");
						 fieldDate.setNSeq(strArray[3]);
						 fieldDate.setStrDiscription("");
						 fieldDate.setStrEmptyType("2");
						 fieldDate.setStrLogicEmptyType("2");
						 fieldDate.setStrFieldName(strArray[0]);
						 fieldDate.setStrFieldType("7");
						 fieldDate.setReportModel_Table(table);
						 
						 fieldArray.add(fieldDate);
						 					
						 // 将字段记录保存到字段表
						 IParamVoidResultExecute dao3 =
							 (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
						 dao3.paramVoidResultExecute(new Object[]{fieldDate, null});
					 }
					 else if(strArray[1].equals("vchar")) {
						if (strArray.length < 5)
							continue;

						ReportModel_Field fieldInst = new ReportModel_Field();
						fieldInst.setStrChinaName(strArray[2]);
						fieldInst.setIsEnable("1");
						fieldInst.setIsKey("2");
						fieldInst.setNLength(strArray[3]);
						fieldInst.setNSeq(strArray[4]);
						fieldInst.setStrDiscription("");
						fieldInst.setStrEmptyType("2");
						fieldInst.setStrLogicEmptyType("2");
						fieldInst.setStrFieldName(strArray[0]);
						fieldInst.setStrFieldType("2");
						fieldInst.setReportModel_Table(table);
						
						if(strArray.length > 5) {
							fieldInst.setStrConstList(strArray[5]);
						}
						if(strArray.length > 6) {
							fieldInst.setStrDBConstList(strArray[6]);
						}

						fieldArray.add(fieldInst);

						// 将字段记录保存到字段表
						IParamVoidResultExecute dao4 = (IParamVoidResultExecute) FrameworkFactory
								.CreateBean("singleObjectSaveDao");
						
						 dao4.paramVoidResultExecute(new Object[]{fieldInst, null});
					}
				}

				Collections.sort(fieldArray, new SortByNSeq() ); // 排序
				String path = this.getClass().getClassLoader().getResource("").getPath();
				path = path.substring(0, path.length()-16);
				
				String strTablePrefix = "";
				if(table.getStrAutoDTO() != null && table.getStrAutoDTO().equals("1"))
					strTablePrefix = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("tablePrefix");
				TableClassGenerator.generateDTO(table.getStrTableName(), table.getStrChinaName(),fieldArray, table.getDataSource(), false, strTablePrefix);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
