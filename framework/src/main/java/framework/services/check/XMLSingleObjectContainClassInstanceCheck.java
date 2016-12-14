package framework.services.check;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

public class XMLSingleObjectContainClassInstanceCheck extends
		XMLSingleObjectInstanceCheck {
	
	public XMLSingleObjectContainClassInstanceCheck(){
		super();
	}
	
	public XMLSingleObjectContainClassInstanceCheck(Object baseObject){
		super(baseObject);
	}


	@SuppressWarnings("unchecked")
	@Override
	public MessageResult Check() throws Exception {
		
		Object object=LogicParamManager.getDefaultCheckInstance();
		if(object!=null){
			String FiledName=object.toString();
			if(FiledName.indexOf(".")>-1){
				String tname=this.getBaseObject().getClass().getName();
				if(ShowContext.getInstance().getShowEntityMap().get("sysParam").get("tablePrefix")==null){
					if(tname.indexOf("AutoDTO_")>-1){
						tname=tname.substring(tname.indexOf("AutoDTO_")+8);
					}
				}
				
				String ClassName=FiledName.substring(0, FiledName.lastIndexOf("."));
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(ClassName));
				detachedCriteria.add(Restrictions.eq("strTableName", tname));
				ProjectionList projection=Projections.projectionList();
				projection.add(Projections.groupProperty("strCheckInstance"));
				detachedCriteria.setProjection(projection);
				List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				for(Object o: objectList){
					if(o != null && !o.toString().equals("")){
						LogicParamManager.setDefaultCheckInstance(o.toString());
						break;
					}
					else{
						return new MessageResult();
					}
				}
			}
		}
		
		return super.Check();
	}

	
	
}
