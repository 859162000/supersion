package extend.dto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import extend.dto.ReportModel_Table;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_DataSource")
@IEntity(navigationName="数据源",description="数据源")
public class AutoETL_DataSource implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name="sessionFactory",length = 50,nullable=false)
	@IColumn(description="会话工厂",isKeyName=true, isNullable = false)
	private String sessionFactory;
	
	@Column(name="strDatabaseType",length = 50,nullable=false)
	@IColumn(tagMethodName="getDatabaseTypeTag",description="数据库类型", isNullable = false)
	private String strDatabaseType;
	
	public static Map<String,String> getDatabaseTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("databaseType");
	}
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@Id
	@Column(name = "autoDataSourceID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoDataSourceID;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "dataSource")
	private Set<ReportModel_Table> reportModel_Tables = new HashSet<ReportModel_Table>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_DataSource")
	private Set<AutoETL_Procedure> autoETL_Procedures = new HashSet<AutoETL_Procedure>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "dataSource")
	private Set<AutoETL_View> autoETL_Views = new HashSet<AutoETL_View>(0);
	
	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoDataSourceID(String autoDataSourceID) {
		this.autoDataSourceID = autoDataSourceID;
	}

	public String getAutoDataSourceID() {
		return autoDataSourceID;
	}

	public void setSessionFactory(String sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getSessionFactory() {
		return sessionFactory;
	}

	public void setStrDatabaseType(String strDatabaseType) {
		this.strDatabaseType = strDatabaseType;
	}

	public String getStrDatabaseType() {
		return strDatabaseType;
	}

	public void setReportModel_Tables(Set<ReportModel_Table> reportModel_Tables) {
		this.reportModel_Tables = reportModel_Tables;
	}

	public Set<ReportModel_Table> getReportModel_Tables() {
		return reportModel_Tables;
	}

	public void setAutoETL_Procedures(Set<AutoETL_Procedure> autoETL_Procedures) {
		this.autoETL_Procedures = autoETL_Procedures;
	}

	public Set<AutoETL_Procedure> getAutoETL_Procedures() {
		return autoETL_Procedures;
	}

	public void setAutoETL_Views(Set<AutoETL_View> autoETL_Views) {
		this.autoETL_Views = autoETL_Views;
	}

	public Set<AutoETL_View> getAutoETL_Views() {
		return autoETL_Views;
	}
}



