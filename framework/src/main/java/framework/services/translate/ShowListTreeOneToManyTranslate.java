package framework.services.translate;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
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

public class ShowListTreeOneToManyTranslate implements ITranslate {

	@Override
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
		String treeTypeName=SessionManager.getTreeTypeName(tName);
		if(!StringUtils.isBlank(treeTypeName)){
			
			
			String treeIdValue=SessionManager.getTreeIdValue(tName);
			if(!StringUtils.isBlank(treeIdValue)){
				String[] types= treeTypeName.split(",");
				String treeDtoType=types[1];
				String relationDtoType=types[0];
				List<Field> fieldList = ReflectOperation.getOneToManyField(tName);
				Field oneToManyField=null;
				for(Field f:fieldList)
				{
					Type fc = f.getGenericType(); 
					  
		             if(fc == null) continue;  
		  
		             if(fc instanceof ParameterizedType)    
		             {   
		                   ParameterizedType pt = (ParameterizedType) fc;  
		  
		                   Class genericClazz = (Class)pt.getActualTypeArguments()[0];
		                   if(genericClazz.getName().equals(relationDtoType))
		                   {
		                	   oneToManyField=f;
		                   }
		             }
					
				}
				
				Field joinFieldWithTreeDto=null;
				
				List<Field> fieldList2 = ReflectOperation.getJoinColumnFieldList(relationDtoType); 
				for(Field f:fieldList2)
				{
					if(f.getType().getName().equals(treeDtoType))
					{
						joinFieldWithTreeDto=f;
						continue;
					}
					
				}
				
				if(joinFieldWithTreeDto!=null && oneToManyField!=null)
				{
					Object treeDtoObj=joinFieldWithTreeDto.getType().newInstance();
					Field primaryKey=ReflectOperation.getPrimaryKeyField(joinFieldWithTreeDto.getType());
					ReflectOperation.setFieldValue(treeDtoObj,primaryKey.getName(), treeIdValue);
					
					detachedCriteria.createCriteria(oneToManyField.getName()).add(Restrictions.eq(joinFieldWithTreeDto.getName(),treeDtoObj));
				}
				
				
			}
			
		}
		
		LogicParamManager.setDetachedCriteria(detachedCriteria);

	}

}
