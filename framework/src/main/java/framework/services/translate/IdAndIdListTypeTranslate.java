package framework.services.translate;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class IdAndIdListTypeTranslate implements ITranslate{

	public void Translate() throws Exception {
		String masterType=RequestManager.getTypeName();
		String tName=RequestManager.getTName();
		DetachedCriteria detachedCriteria;
		if(LogicParamManager.getDetachedCriteria() == null){
			{
				detachedCriteria = DetachedCriteria.forEntityName(tName);
				LogicParamManager.setDetachedCriteria(detachedCriteria);
			}
			
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		/*征信版本合并后明细填报报错，暂时注释
		 * if(!StringUtils.isBlank(masterType))//复合表单明细操作
		{
			
			Object masterObj=Class.forName(masterType).newInstance();
			Field masterPk=ReflectOperation.getPrimaryKeyField(masterType);
			ReflectOperation.setFieldValue(masterObj, masterPk.getName(), RequestManager.getLevelIdValue());
			List<Field> joinFieldList=ReflectOperation.getJoinColumnFieldList(tName);
			for(Field joinField :joinFieldList)
			{
				if(masterType.equals(joinField.getType().getName()))
				{
					detachedCriteria.add(Restrictions.eq(joinField.getName(), masterObj));
				}
			}
			
		}*/
		Field pkField=ReflectOperation.getPrimaryKeyField(RequestManager.getTName());
		if(RequestManager.getId() != null){
		    
			if(pkField.getType().equals(Integer.class)){
				RequestManager.setId(Integer.parseInt(RequestManager.getId().toString()));
			}
			else if(ReflectOperation.getPrimaryKeyField(RequestManager.getTName()).getType().equals(Long.class)){
				RequestManager.setId(Long.parseLong(RequestManager.getId().toString()));
			}
			detachedCriteria.add(Restrictions.eq(pkField.getName(), RequestManager.getId()));
		}
		else if(RequestManager.getIdList() != null){
			Object[] idList = (Object[])RequestManager.getIdList();
			Object[] objs = new Object[idList.length];
			if(ReflectOperation.getPrimaryKeyField(RequestManager.getTName()).getType().equals(Integer.class)){
				for(int i=0;i<idList.length;i++){
					objs[i] = Integer.parseInt(idList[i].toString());
				}
				RequestManager.setIdList(objs);
			}
			else if(ReflectOperation.getPrimaryKeyField(RequestManager.getTName()).getType().equals(Long.class)){
				for(int i=0;i<idList.length;i++){
					objs[i] = Long.parseLong(idList[i].toString());
				}
				RequestManager.setIdList(objs);
			}
			detachedCriteria.add(Restrictions.in(pkField.getName(), (Object[])RequestManager.getIdList()));
			
		}
	}
}

