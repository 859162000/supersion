package framework.services.translate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.ReflectOperation;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
/**
 * 
 * @author transino2
 *用于多选情况下，多选的数据只是新增，
 *保存时不会删除已存在的数据
 */
public class MultiIdListByNewTranslate extends BaseConstructor implements ITranslate{

	public MultiIdListByNewTranslate(){
		super();
	}
	
	public MultiIdListByNewTranslate(Object baseObject){
		super(baseObject);
	}
	@Override
	public void Translate() throws Exception {
		// TODO Auto-generated method stub
		Object tObject = this.getBaseObject();
		MultiIdListTranslate multiIdListTranslate=new MultiIdListTranslate(tObject);
		multiIdListTranslate.Translate();
		List<Field> idListTargetFieldList = ReflectOperation.getIdListTargetFieldList(tObject.getClass());
		Collection saveObjectList=(Collection) LogicParamManager.getSaveObjectList();
		
		DetachedCriteria  detachedCriteria=LogicParamManager.getDetachedCriteria();
		for(Field idListTargetField : idListTargetFieldList)
		{	
			List<Object> listObject=new ArrayList<Object>();
			for(Object saveObject:saveObjectList)
			{
				listObject.add( ReflectOperation.getFieldValue(saveObject,idListTargetField.getName()));
			}
			LogicParamManager.setSaveObjectList(saveObjectList);
			detachedCriteria.add(Restrictions.in(idListTargetField.getName(), listObject));
		}
	}

}
