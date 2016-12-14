package szzxpt.service.check;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.AutoDTO_GR_DBXX;
import zxptsystem.dto.AutoDTO_GR_GRSFXX;
import zxptsystem.dto.AutoDTO_GR_GRXX_JC;
import zxptsystem.dto.AutoDTO_JG_GGJZYGXR;
import zxptsystem.dto.AutoDTO_JG_ZYGD;
import zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC;
import zxptsystem.dto.AutoDTO_QY_CZZBGC;
import zxptsystem.dto.AutoDTO_QY_FRDBJZQYCY;
import zxptsystem.dto.AutoDTO_QY_GJGLRQK;
import zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX;
import zxptsystem.dto.AutoDTO_QY_ZRRDYHTXX;
import zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX;

import autoETLsystem.dto.AutoETL_Workflow;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class AboutSFFXXAndNameRepeatCheck implements ICheck {

	@SuppressWarnings("unchecked")
	public MessageResult Check() throws Exception {
		
		MessageResult messageResult = new MessageResult();
		Object object=RequestManager.getTOject();

		List<Field> fieldList=ReflectOperation.getColumnFieldList(object.getClass());
		Map<String,Field> mapFieldSet=new LinkedHashMap<String,Field>();
		for(Field field : fieldList){
			String name=field.getName();
			if(name.indexOf("ZJLX")>=0 || name.indexOf("ZJHM")>=0 || name.indexOf("GDMC")>=0 || name.indexOf("XM")>=0 || name.indexOf("CZF")>=0 || name.indexOf("RMC")>=0){
				mapFieldSet.put(field.getName(),field);
			}
		}
		//查询出所有表中关于证件号码和姓名的关系
		//动态的添加表的证件号码和姓名的对应关系
		Map<String,String> nameList=new LinkedHashMap<String,String>();
		nameList.put(AutoDTO_GR_DBXX.class.getName(),"ZJHM,XM");
		nameList.put(AutoDTO_GR_GRXX_JC.class.getName(),"ZJHM,XM");
		nameList.put(AutoDTO_GR_GRSFXX.class.getName(),"POZJHM,POXM");
		nameList.put(AutoDTO_QY_FRDBJZQYCY.class.getName(),"ZJHM,JZCYXM");
		nameList.put(AutoDTO_QY_CZZBGC.class.getName(),"ZJHM,CZF");
		nameList.put(AutoDTO_QY_GJGLRQK.class.getName(),"ZJHM,GGRYXM");
		nameList.put(AutoDTO_QY_ZRRBZHTXX.class.getName(),"ZJHM,BZRMC");
		nameList.put(AutoDTO_QY_ZRRDYHTXX.class.getName(),"ZJHM,DYRMC");
		nameList.put(AutoDTO_QY_ZRRZYHTXX.class.getName(),"ZJHM,CZRMC");
		nameList.put(AutoDTO_JG_GGJZYGXR.class.getName(),"GGZJHM,GGXM");
		nameList.put(AutoDTO_JG_ZYGD.class.getName(),"GDZJHM,GDMC");
		nameList.put(AutoDTO_JZ_JZCYXX_JC.class.getName(),"ZJHM_1,ZYGXRXM,ZJHM_2,JZCYXM");
		
		Map<Object,Object> mapIDAndName=new LinkedHashMap<Object,Object>();
		//1.查询担保信息段等相关的数据
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_GR_DBXX.class);
		ProjectionList projList = Projections.projectionList();
		for(Map.Entry<String, String> map : nameList.entrySet()){
			String className=map.getKey();
			String[] ArrayFieldName=map.getValue().split(",");
			for(int i=0;i<ArrayFieldName.length;i=i+2){
				String fieldNameZJHM=ArrayFieldName[i];
				String filedNamename=ArrayFieldName[i+1];
				detachedCriteria = DetachedCriteria.forClass(Class.forName(className));
				projList = Projections.projectionList();
				projList.add(Projections.groupProperty(fieldNameZJHM));
				projList.add(Projections.groupProperty(filedNamename));
				detachedCriteria.setProjection(projList);
				List<Object> objList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				for(Object obj : objList){
					Object[] objectx=(Object[])obj;
					if(!mapIDAndName.containsKey(objectx[0])){
						mapIDAndName.put(objectx[0], objectx[1]);
					}
				}
			}
		}
		//2.获取当前dto中当前的类的证件号码和名称
		String[] ArraynameList=nameList.get(RequestManager.getTName()).split(",");
		for(int i=0;i<ArraynameList.length;i=i+2){
			String fieldNameZJHM=ArraynameList[i];
			String filedNamename=ArraynameList[i+1];
			String fieldZXHMShowName="";
			String fieldNameShowName="";
			Object valueZJHM=ReflectOperation.getFieldValue(object,fieldNameZJHM);
			Object valuename=ReflectOperation.getFieldValue(object,filedNamename);
			if(mapFieldSet.get(fieldNameZJHM).isAnnotationPresent(IColumn.class)){
				IColumn iColumn = mapFieldSet.get(fieldNameZJHM).getAnnotation(IColumn.class);
				if(!StringUtils.isBlank(iColumn.description())){
					fieldZXHMShowName=iColumn.description();
				}
			}
			if(mapFieldSet.get(filedNamename).isAnnotationPresent(IColumn.class)){
				IColumn iColumn = mapFieldSet.get(filedNamename).getAnnotation(IColumn.class);
				if(!StringUtils.isBlank(iColumn.description())){
					fieldZXHMShowName=iColumn.description();
				}
			}
			if(valueZJHM !=null && valuename!=null){
				if(mapIDAndName.containsKey(valueZJHM) && mapIDAndName.get(valueZJHM)!=null && !mapIDAndName.get(valueZJHM).equals(valuename)){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("系统中同一"+fieldZXHMShowName+"应于"+fieldNameShowName+"保持一致");
				}
			}
		}
		
		
		return messageResult;
	}

}
