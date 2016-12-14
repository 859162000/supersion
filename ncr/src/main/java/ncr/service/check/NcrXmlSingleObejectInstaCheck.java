package ncr.service.check;

import framework.services.check.XMLSingleObjectInstanceCheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

public class NcrXmlSingleObejectInstaCheck extends XMLSingleObjectInstanceCheck{
	public MessageResult Check() throws Exception 
	{
		Object object=LogicParamManager.getDefaultCheckInstance();
		if(object.equals("extend.dto.ReportModel_Table.strCheckInstance"))
		{
			String FiledName=object.toString();
			if(FiledName.indexOf(".")>-1)
			{
				String tname=this.getBaseObject().getClass().getName();
				if(ShowContext.getInstance().getShowEntityMap().get("sysParam").get("tablePrefix")==null){
					if(tname.indexOf("AutoDTO_")>-1){
						tname="Check_"+tname.substring(tname.indexOf("AutoDTO_")+8);
						LogicParamManager.setDefaultCheckInstance(tname);
					}
				}
			}
	     }
		return super.Check();
      }
}
