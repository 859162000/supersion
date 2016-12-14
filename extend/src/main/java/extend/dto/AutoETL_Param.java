package extend.dto;


import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_Param")
@IEntity(navigationName="参数",description="参数")
public class AutoETL_Param implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="strParamName",length = 50,nullable=false)
	@IColumn(description="参数名称",isKeyName=true, isNullable = false)
	private String strParamName;

	@IColumn(tagMethodName="getFieldTypeTag",description="字段类型", isNullable = false)
	@Column(name = "strFieldType", nullable = false)
	private String strFieldType;
	
	public static Map<String,String> getFieldTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("fieldType");
	}

	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	

	@Id
	@Column(name = "autoParamID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoParamID;

	public String getStrFieldType() {
		return strFieldType;
	}

	public void setStrFieldType(String strFieldType) {
		this.strFieldType = strFieldType;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setStrParamName(String strParamName) {
		this.strParamName = strParamName;
	}

	public String getStrParamName() {
		return strParamName;
	}

	public void setAutoParamID(String autoParamID) {
		this.autoParamID = autoParamID;
	}

	public String getAutoParamID() {
		return autoParamID;
	}
}

