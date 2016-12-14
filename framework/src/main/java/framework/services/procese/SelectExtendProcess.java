package framework.services.procese;

import framework.services.interfaces.IProcese;

public class SelectExtendProcess implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
			StringBuilder sb = new StringBuilder();
			if(serviceResult instanceof framework.show.ShowSaveOrUpdate){
				framework.show.ShowSaveOrUpdate list =(framework.show.ShowSaveOrUpdate)serviceResult;
				
				for(framework.show.ShowFieldValue sfv : list.getShowFieldValueList()){								
					if(sfv.getShowField().getSingleTag().equals("selectExtendField")){
						java.util.Map<String,String> map = (java.util.Map<String,String>)sfv.getTag();
						String[] tags = new String[2];
						tags[0]=map.get(sfv.getFieldValue());						
						sb.setLength(0);
						java.util.Iterator<String> iterator = map.keySet().iterator();
						while(iterator.hasNext()){
							String key = iterator.next();
							sb.append("<li code=\""+key+"\">"+map.get(key).toString()+"</li>");
						}
						tags[1]=sb.toString();
						sfv.setTag(tags);
					}
				}
		   }else if(serviceResult instanceof framework.show.ShowList){
			   framework.show.ShowList list =(framework.show.ShowList)serviceResult;
			   for(framework.show.ShowFieldCondition sfc:list.getShowCondition()){
				   if(sfc.getSingleTag().equals("selectExtendField")){
					   java.util.Map<String,String> map = (java.util.Map<String,String>)sfc.getTag();
					   String[] tags = new String[2];
					   if(null!=map){
							tags[0]="";					
							sb.setLength(0);
							java.util.Iterator<String> iterator = map.keySet().iterator();
							while(iterator.hasNext()){
								String key = iterator.next();
								sb.append("<li code=\""+key+"\">"+map.get(key).toString()+"</li>");
							}
							tags[1]=sb.toString();
					   }else{
						   tags[0]="";
						   tags[1]="";
					   }
						
						sfc.setTag(tags);
				   }
			   }
		   }
		  return serviceResult;
	}

}
