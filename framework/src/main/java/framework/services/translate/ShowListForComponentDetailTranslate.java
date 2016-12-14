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

public class ShowListForComponentDetailTranslate implements ITranslate{

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

		String masterTypeName=RequestManager.getTypeName();
		String materId=RequestManager.getLevelIdValue();
		if(!StringUtils.isBlank(masterTypeName)){
			if(!StringUtils.isBlank(materId)){
				Object masterObject=Class.forName(masterTypeName).newInstance();
				Field pk=ReflectOperation.getPrimaryKeyField(Class.forName(masterTypeName));
				ReflectOperation.setFieldValue(masterObject, pk.getName(), materId);
				List<Field> joinFieldList=ReflectOperation.getJoinColumnFieldList(type);
				for(Field field:joinFieldList)
				{
					if(masterTypeName.equals(field.getType().getName()))
					{
						detachedCriteria.add(Restrictions.eq(field.getName(), masterObject));
					}
							
				}
				
			}
			
		}
		
		LogicParamManager.setDetachedCriteria(detachedCriteria);

	}
}

