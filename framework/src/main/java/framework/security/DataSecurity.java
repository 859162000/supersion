package framework.security;

import java.util.HashSet;
import java.util.Set;

public class DataSecurity implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DataSecurity(){
    	this.dataSet = new HashSet<String>();
	}
	private String className;
	private String fieldName;
	private Set<String> dataSet;
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassName() {
		return className;
	}
	public void setDataSet(Set<String> dataSet) {
		this.dataSet = dataSet;
	}
	public Set<String> getDataSet() {
		return dataSet;
	}
}
