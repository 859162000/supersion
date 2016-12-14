package framework.services.translate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import framework.helper.ReflectOperation;

import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;


public class ExportForComponentTranslate implements ITranslate{

	/**
	 * gechenglian
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void Translate() throws Exception {
		String jcName = null;
		String mxName =null;
		Class<?> mxClass = null;
		//获取选中数据列表
		DetachedCriteria detachedCriteria= null;
		Map<String, Object> param = ActionContext.getContext().getParameters();
		String[] idList = (String[]) param.get("idList");
		if(idList != null && idList.length>0 ){
	        mxName = RequestManager.getTName();
	        mxClass = Class.forName(mxName);
	        detachedCriteria = DetachedCriteria.forClass(mxClass);
	        String primayKeyFieldName = ReflectOperation.getPrimaryKeyField(mxName).getName();
			detachedCriteria.add(Restrictions.in(primayKeyFieldName, idList));
		}else{
			String foreignId = null;
			//获取基础段的Dto名称
			jcName = RequestManager.getTypeName();
			//获取明细段的Dto名称
			mxName = RequestManager.getTName();
			
		    mxClass = Class.forName(mxName);
		    Object jcDto= Class.forName(jcName).newInstance();
		    detachedCriteria = DetachedCriteria.forClass(mxClass);
		    //获取外键值
			ArrayList<Field> foreignList = (ArrayList<Field>) ReflectOperation.getJoinColumnFieldList(mxName);
			for(Field field :foreignList)
			{
				if(field.getType().equals(jcDto.getClass())){
					foreignId = field.getName();
				}
			}
		    
		    ReflectOperation.setFieldValue(jcDto, ReflectOperation.getPrimaryKeyField(jcDto.getClass()).getName(), RequestManager.getLevelIdValue());
		    
			detachedCriteria.add(Restrictions.eq(foreignId,jcDto));			
		}
		
		LogicParamManager.setDetachedCriteria(detachedCriteria);
		
		
	}

}
