package framework.bean.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotReadablePropertyException;
import framework.helper.BeanFactoryUtils;
/*
 * ServiceContext的集合类
 */
public class ServiceContextList {
	
	private List<ServiceContext> scs;
	public List<ServiceContext> getSCList() {
		return scs;
	}
	public void setSCList(List<ServiceContext> scs) {
		this.scs = scs;
		listToMap();
	}

	private Map<String,List<List<ServiceContext>>> contextMap;
	private Map<String,Pattern> rulePatternMap;
	public ServiceContextList(List<ServiceContext> scs)
	{
		this.scs=scs;
		listToMap();
		
	}
	private void listToMap()
	{
		contextMap=new HashMap<String,List<List<ServiceContext>>>();
		rulePatternMap=new HashMap<String,Pattern>();
		if(scs!=null)
		{
			String beanId;
			for(ServiceContext sc:scs)
			{
				beanId=sc.getBeanId();
				if(!contextMap.containsKey(beanId))
				{
					contextMap.put(beanId, new ArrayList<List<ServiceContext>>());
					contextMap.get(beanId).add(new ArrayList<ServiceContext>());//有dto 匹配规则集合
					contextMap.get(beanId).add(new ArrayList<ServiceContext>());//无dto集合
					
				}
				String dtoMatchRule=sc.getDtoMatchRule();
				if(!StringUtils.isBlank(dtoMatchRule))
				{
					if(!rulePatternMap.containsKey(dtoMatchRule))
					{
						 Pattern p = Pattern.compile(dtoMatchRule);
						 rulePatternMap.put(dtoMatchRule, p);
					}
					contextMap.get(beanId).get(0).add(sc);
				}
				else
				{
					contextMap.get(beanId).get(1).add(sc);
				}
				
			}
		}
	}
	/*
	 * dto匹配规则采用正则表达式，
	 */
	public List<ServiceContext> match(String beanId,String dto)
	{
		List<ServiceContext> serviceContextList=new ArrayList<ServiceContext>();
		List<List<ServiceContext>> servicesList=contextMap.get(beanId);
		if(servicesList!=null )
		{
			if(!StringUtils.isBlank(dto))
			{
				boolean matchSuccess=false;
				for(ServiceContext sc:servicesList.get(0))
				{
					String dtoMatchRule=sc.getDtoMatchRule();
					Pattern p=rulePatternMap.get(dtoMatchRule);
					if(p!=null && p.matcher(dto).matches())
					{
						serviceContextList.add(sc);
						matchSuccess=true;
						break;
					}
					
				}
				if(!matchSuccess&&servicesList.get(1).size()>0)
				{
					serviceContextList=(List<ServiceContext>) servicesList.get(1);
				}
			}
			else if(servicesList.get(1).size()>0)
			{
				serviceContextList=(List<ServiceContext>) servicesList.get(1);
			}
			
			
		}
		
		return serviceContextList;
		
	}
	
	public void meragePropertys(String beanId,String dtoName,Object service) throws Exception
	{
		if(service!=null)
		{
			List<ServiceContext> scList=match(beanId, dtoName);
			if(scList!=null)
			{
				BeanWrapperImpl beanWrapper=new BeanWrapperImpl(service);
				List<String> replacePropList=null;
				Map<String,Integer> insPropMap=null;
				for(ServiceContext sc:scList)
				{
					if(scList.size()==1)//匹配的servicecontext大于1，只支持合并
					{
						replacePropList=sc.getReplacePropList();
					}
					insPropMap=sc.getInsertPropMap();
					Map<String,Object> merageProps=sc.getBeanProperty();
					for(Entry<String,Object> entry:merageProps.entrySet())
					{
						String propName=entry.getKey();
						Object newPropVal=entry.getValue();
						
						Object propVal=null;
						try
						{
							propVal=beanWrapper.getPropertyValue(propName);
						}
						catch(NotReadablePropertyException ex)
						{
							propVal=null;
						}
						
						
						
						if(propVal!=null && !replacePropList.contains(propName))
						{
							if(Collection.class.isInstance(newPropVal)&& 
									Collection.class.isInstance(propVal))
							{
								if(insPropMap.containsKey(propName))
								{
									Integer insertPos=insPropMap.get(propName);
									if(insertPos!=null &&insertPos>=0  
											&& insertPos<=((Collection)propVal).size()
											&&List.class.isInstance(propVal))
									{
										((List)propVal).addAll(insertPos,(Collection)newPropVal);
										continue;
									}
									
								}
								((Collection)propVal).addAll((Collection)newPropVal);
								continue;
								
							}
							else if(Map.class.isInstance(newPropVal) && 
									Map.class.isInstance(propVal))
							{
								if(insPropMap.containsKey(propName))
								{
									Integer insertPos=insPropMap.get(propName);
									if(insertPos!=null &&insertPos>=0  
											&& insertPos<=((Map)propVal).size())
									{
										Map newProp=(Map)propVal.getClass().newInstance();
										Map propValMap=(Map)propVal;
										Set keySet=propValMap.keySet();
										
										int i=0;
										for(Object key:keySet)
										{
											if(i==insertPos)
											{
												newProp.putAll((Map)newPropVal);
											}
											newProp.put(key,propValMap.get(key));
											
											i++;
											
										}
										beanWrapper.setPropertyValue(propName, newProp);
										continue;
									}
									
									
								}
								((Map)propVal).putAll((Map)newPropVal);
								continue;
							}
							
						}
						beanWrapper.setPropertyValue(propName, newPropVal);
						
					}
				}
			
			
			}
				
		}
	}

}
