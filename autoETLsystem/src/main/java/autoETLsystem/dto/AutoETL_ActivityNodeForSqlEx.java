package autoETLsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import extend.dto.AutoETL_DataSource;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForSqlEx")
@IEntity(navigationName="SQL语句执行",description="SQL语句执行")
public class AutoETL_ActivityNodeForSqlEx implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_DataSourceID", nullable = false)
	@IColumn(description="会话工厂", isNullable = false)
	private AutoETL_DataSource autoETL_DataSource;
	
	@Column(name="strDataSourceSql",length = 4000, nullable = false)
	@IColumn(description="SQL语句", isNullable = false,isSpecialCharCheck=true)
	private String strDataSourceSql;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@Id
	@Column(name = "autoActivityNodeForSqlID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForSqlID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoETL_DataSource(AutoETL_DataSource autoETL_DataSource) {
		this.autoETL_DataSource = autoETL_DataSource;
	}

	public AutoETL_DataSource getAutoETL_DataSource() {
		return autoETL_DataSource;
	}

	public void setStrDataSourceSql(String strDataSourceSql) {
		this.strDataSourceSql = strDataSourceSql;
	}

	public String getStrDataSourceSql() {
		return strDataSourceSql;
	}

	public void setAutoActivityNodeForSqlID(String autoActivityNodeForSqlID) {
		this.autoActivityNodeForSqlID = autoActivityNodeForSqlID;
	}

	public String getAutoActivityNodeForSqlID() {
		return autoActivityNodeForSqlID;
	}

}

