package framework.services.translate;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.JoinColumn;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class ShowListTreeTranslate implements ITranslate{

	public void Translate() throws Exception {
		
		String tName = RequestManager.getTName();
        Class<?> type = Class.forName(tName);
		
		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}

		if(SessionManager.getTreeTypeName(tName) != null){
			if(StringUtils.isBlank(SessionManager.getTreeIdValue(tName))){
				List<Field> fieldList = ReflectOperation.getJoinColumnFieldList(tName);
				for(Field field : fieldList){
					if(field.getType().getName().equals(SessionManager.getTreeTypeName(tName))){
						detachedCriteria.add(Restrictions.isNull(field.getName()));
						break;
					}
				}
			}
			else{
				List<Field> fieldList = ReflectOperation.getJoinColumnFieldList(tName);
				int isExsist = 0;
				for(Field field : fieldList){
					if(field.getType().getName().equals(SessionManager.getTreeTypeName(tName))){
						isExsist ++;
					}
				}
				if(isExsist == 0){
					String[] types= SessionManager.getTreeTypeName(tName).split(",");
					Set<String> typsSet = new HashSet<String>();
					for(int i=0;i<types.length;i++){
						typsSet.add(types[i]);
					}
					IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					RequestManager.setId(SessionManager.getTreeIdValue(tName));
					Object object = defaultLogicDaoDao.paramObjectResultExecute(null);
					for(Field field : fieldList){
						if(typsSet.contains(field.getType().getName())){
							detachedCriteria.add(Restrictions.eq(field.getName(), ReflectOperation.getFieldValue(object, field.getName())));
						}
					}
				}
				else if(isExsist == 1){
					for(Field field : fieldList){
						if(field.getType().getName().equals(SessionManager.getTreeTypeName(tName))){
							Object obj = Class.forName(SessionManager.getTreeTypeName(tName)).newInstance();
							Field primaryField = ReflectOperation.getPrimaryKeyField(SessionManager.getTreeTypeName(tName));
							ReflectOperation.setFieldValue(obj, primaryField.getName(), SessionManager.getTreeIdValue(tName));
							detachedCriteria.add(Restrictions.eq(field.getName(), obj));
							break;
						}
					}
				}
	            else if(isExsist == 2){
	            	IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					RequestManager.setId(SessionManager.getTreeIdValue(tName));
					Object object = defaultLogicDaoDao.paramObjectResultExecute(null);
					Field nullField = null;
					Field notNullField = null;
					for(Field field : fieldList){
						if(field.getType().getName().equals(SessionManager.getTreeTypeName(tName))){
							JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
							if(joinColumn.nullable()){
								nullField = field;
							}
							else{
								notNullField = field;
							}
						}
					}
					detachedCriteria.add(Restrictions.eq(nullField.getName(), ReflectOperation.getFieldValue(object, notNullField.getName())));
				}
			}
		}
		
		LogicParamManager.setDetachedCriteria(detachedCriteria);

	}
}

