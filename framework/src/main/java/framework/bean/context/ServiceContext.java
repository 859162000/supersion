package framework.bean.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * service bean属性配置类
 * 当满足dto的匹配规则时，替换指定bean的相关属性设置
 */
public class ServiceContext {
	private String beanId;
	public String getBeanId() {
		return beanId;
	}
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	public Map<String,Object> getBeanProperty() {
		return beanProperty;
	}
	public void setBeanProperty(Map<String,Object> beanProperty) {
		this.beanProperty = beanProperty;
	}
	
	public String getDtoMatchRule() {
		return dtoMatchRule;
	}
	public void setDtoMatchRule(String dtoMatchRule) {
		this.dtoMatchRule = dtoMatchRule;
	}
	private Map<String,Object> beanProperty;
	
	private String dtoMatchRule;
	/*
	 * 替换属性列表，用于属性为集合类型
	 */
	private List<String> replacePropList;
	public List<String> getReplacePropList() {
		if(replacePropList==null)
		{
			replacePropList=new ArrayList<String>();
		}
		return replacePropList;
	}
	public void setReplacePropList(List<String> replacePropList) {
		this.replacePropList = replacePropList;
		
	}
	public Map<String,Integer> getInsertPropMap() {
		if(insertPropMap==null)
		{
			insertPropMap=new HashMap<String,Integer>();
		}
		return insertPropMap;
	}
	public void setInsertPropMap(Map<String,Integer> insertPropMap) {
		this.insertPropMap = insertPropMap;
	}
	/**
	 *插入属性map
	 *属性，插入位置
	 */
	private Map<String,Integer> insertPropMap;
	

}
