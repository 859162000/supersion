package testsystem.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.show.ShowContext;



@Entity
@Table(name = "HTABLE9")
public class Htable9 implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "field2", length = 50)
	private String field2;
	
	@IColumn(isNullable = false,aliasName="FIELD3")
	@Column(name = "field3", length = 50)
	private String field3;

	@IColumn(tagMethodName="getActivityNodeTypeTag",description="结点类型",isNullable = false,aliasName="STRACTIVITYNODETYPE")
	@Column(name = "strActivityNodeType", nullable = false)
	private String strActivityNodeType;
	
	public static Map<String,String> getActivityNodeTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("activityNodeType");
	}
	
	@IColumn(tagMethodName="getStartConditionTypeTag",description="启动条件",isNullable = false)
	@Column(name = "strStartConditionType", nullable = false)
	private String strStartConditionType;
	
	public static Map<String,String> getStartConditionTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("startConditionType");
	}

	
	
	@Id
	@Column(name = "field1", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String field1;
	
	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField1() {
		return field1;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField2() {
		return field2;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField3() {
		return field3;
	}

	public void setStrStartConditionType(String strStartConditionType) {
		this.strStartConditionType = strStartConditionType;
	}

	public String getStrStartConditionType() {
		return strStartConditionType;
	}

	public void setStrActivityNodeType(String strActivityNodeType) {
		this.strActivityNodeType = strActivityNodeType;
	}

	public String getStrActivityNodeType() {
		return strActivityNodeType;
	}
	
}
