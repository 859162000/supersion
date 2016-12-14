package framework.services.procese;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowComponentSaveOrUpdate;
import framework.show.ShowHeaderName;
import framework.show.ShowInstance;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowValue;

public class ComponentAllProcese extends BaseConstructor implements IProcese{

	
	public ComponentAllProcese(){
		super();
	}
	
	public ComponentAllProcese(Object baseObject){
		super(baseObject);
	}
	private List<String> getProceseList(String dtoName)
	{
		List<String> proceseList=null;
		if(LogicParamManager.getDtoProceseMap()!=null)
		{
			proceseList=LogicParamManager.getDtoProceseMap().get(dtoName);
		}
		if(proceseList==null)
		{
			proceseList=new ArrayList<String>();
		}
		return proceseList;
		
	}
	private Object processDetails(Object serviceResult) throws Exception 
	{
		
		String detailDtoName;
		String currentTName=RequestManager.getTName();
		String showInstanceName = ShowParamManager.getShowInstanceName();
		ShowInstance showInstance=ReflectOperation.getShowInstance(currentTName, showInstanceName);
		List<Field> fieldList=ReflectOperation.getComponentDetailList(currentTName,showInstance.getFieldListWithShowList());
		for (Field field : fieldList) {
			Class<?> fieldType=ReflectOperation.getGenericClass(field.getGenericType());
			detailDtoName=fieldType.getName();
			List<String> detailProcess=getProceseList(detailDtoName);
			for(String strProceseClass:detailProcess)
			{
				IProcese procese = (IProcese)FrameworkFactory.CreateClass(strProceseClass);
				serviceResult = procese.Procese(serviceResult);
			}

			
		}
		return serviceResult;
	}
	public Object Procese(Object serviceResult) throws Exception {
		this.setBaseObject(serviceResult);
		List<String> proceseList=LogicParamManager.getDefaultComponentProceseClassList();
		if(proceseList!=null)
		{
			for (String strProceseClass : proceseList) {
				IProcese procese = (IProcese)FrameworkFactory.CreateClass(strProceseClass);
				if(BaseConstructor.class.isInstance(procese))
				{
					((BaseConstructor)procese).setBaseObject(getBaseObject());
				}
				serviceResult = procese.Procese(serviceResult);
			}
		}
		
		
		return  processDetails(serviceResult);
		
		
	}

}
