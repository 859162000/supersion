package framework.services.check;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;

public class XmlComponentObjectCheck extends
		XMLSingleObjectContainClassInstanceCheck {
	public XmlComponentObjectCheck(){
		super();
	}
	
	public XmlComponentObjectCheck(Object baseObject){
		super(baseObject);
	}


	@SuppressWarnings("unchecked")
	@Override
	public MessageResult Check() throws Exception {
		Map<String,String> dtoCheckInstanceMap=LogicParamManager.getDtoCheckInstanceMap();
		if(dtoCheckInstanceMap!=null)
		{
			Object curObj=this.getBaseObject();
			if(curObj==null)
			{
				curObj=RequestManager.getTOject();
			}
			if(curObj!=null)
			{
				String dtoName=curObj.getClass().getName();
				String checkInstance=dtoCheckInstanceMap.get(dtoName);
				if(!StringUtils.isBlank(checkInstance))
				{
					LogicParamManager.setDefaultCheckInstance(checkInstance);
					return super.Check();
				}
			}
			
		}
		return new MessageResult();
			
		
	}
}
