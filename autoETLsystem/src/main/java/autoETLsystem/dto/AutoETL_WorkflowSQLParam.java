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
@Table(name = "AutoETL_WorkflowSQLParam")
@IEntity(navigationName="SQL语句工作流参数",description="SQL语句工作流参数")
public class AutoETL_WorkflowSQLParam  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_DataSourceID", nullable = false)
	@IColumn(description="会话工厂", isNullable = false)
	private AutoETL_DataSource autoETL_DataSource;
	
	@Column(name="strDataSourceSql",length = 4000, nullable = false)
	@IColumn(description="SQL语句", isNullable = false,isSpecialCharCheck=true)
	private String strDataSourceSql;
	
	public AutoETL_DataSource getAutoETL_DataSource() {
		return autoETL_DataSource;
	}

	public void setAutoETL_DataSource(AutoETL_DataSource autoETLDataSource) {
		autoETL_DataSource = autoETLDataSource;
	}

	public String getStrDataSourceSql() {
		return strDataSourceSql;
	}

	public void setStrDataSourceSql(String strDataSourceSql) {
		this.strDataSourceSql = strDataSourceSql;
	}

	public AutoETL_Workflow getAutoETL_Workflow() {
		return autoETL_Workflow;
	}

	public void setAutoETL_Workflow(AutoETL_Workflow autoETLWorkflow) {
		autoETL_Workflow = autoETLWorkflow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoWorkflowID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_Workflow autoETL_Workflow;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
}
