package extend.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_Procedure")
@IEntity(navigationName="存储过程",description="存储过程")
public class AutoETL_Procedure implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@IColumn(tagMethodName="getProcedureTypeTag",description="存储过程类型", isNullable = false)
	@Column(name = "strProcedureType", nullable = false)
	private String strProcedureType;
	
	public static Map<String,String> getProcedureTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("procedureType");
	}
	
	@Column(name="strProcedureName",length = 50,nullable=false)
	@IColumn(description="存储过程",isKeyName=true, isNullable = false)
	private String strProcedureName;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@ManyToOne(fetch = FetchType.EAGER)                          
	@JoinColumn(name = "autoDataSourceID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_DataSource autoETL_DataSource;

	@Id
	@Column(name = "autoProcedureID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoProcedureID;

	
	public String getStrProcedureType() {
		return strProcedureType;
	}

	public void setStrProcedureType(String strProcedureType) {
		this.strProcedureType = strProcedureType;
	}

	public String getStrProcedureName() {
		return strProcedureName;
	}

	public void setStrProcedureName(String strProcedureName) {
		this.strProcedureName = strProcedureName;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoProcedureID(String autoProcedureID) {
		this.autoProcedureID = autoProcedureID;
	}

	public String getAutoProcedureID() {
		return autoProcedureID;
	}

	public void setAutoETL_DataSource(AutoETL_DataSource autoETL_DataSource) {
		this.autoETL_DataSource = autoETL_DataSource;
	}

	public AutoETL_DataSource getAutoETL_DataSource() {
		return autoETL_DataSource;
	}
}





