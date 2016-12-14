package framework.services.translate;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;


// 用过过滤树右侧显示结果
public class ShowTreeTranslate implements ITranslate{

	public void Translate() throws Exception {

		if(ShowParamManager.getTreeServiceMap() == null){

			Class<?> type = Class.forName(RequestManager.getTName());

			DetachedCriteria detachedCriteria = null;
			if(LogicParamManager.getDetachedCriteria() == null){
				detachedCriteria = DetachedCriteria.forClass(type);
			}
			else{
				detachedCriteria = LogicParamManager.getDetachedCriteria();
			}
			
			if(ShowParamManager.getConstructType() == null || ShowParamManager.getConstructType().equals("1")){
				if(ShowParamManager.getForeignFieldMap() != null){
					for(Map.Entry<String, String> entry : ShowParamManager.getForeignFieldMap().entrySet()){
						detachedCriteria.add(Restrictions.isNull(entry.getKey()));
					}
				}
			}

			LogicParamManager.setDetachedCriteria(detachedCriteria);
		}
	}
}
