package framework.services.procese;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class ShowSingleStatisticalProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		ShowList showList=(ShowList)serviceResult;
		
		
		Map<String,Object> map=new HashMap<String,Object>();
		try
		{
			map=(Map<String, Object>) ((Map<String,Object>)ServletActionContext.getContext().get("request")).get(RequestManager.getTName()+"ConditionListData");
		}
		catch(Exception ex){
			map=new HashMap<String,Object>();
		}
		List<ShowFieldCondition> fieldlist=showList.getShowCondition();
		List<ShowFieldCondition> removelist=new ArrayList<ShowFieldCondition>();
		
		for(ShowFieldCondition field: fieldlist){
			if(map.size()!=0){
				if(!map.containsKey(field.getFieldName())){
					removelist.add(field);
				}
			}
			else
			{
				removelist.add(field);
			}
		}
		
		for(ShowFieldCondition  rem : removelist){
			fieldlist.remove(rem);
		}
		
		return showList;
	}
}
