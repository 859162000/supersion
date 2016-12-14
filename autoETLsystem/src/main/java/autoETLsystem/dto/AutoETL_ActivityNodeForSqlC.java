package autoETLsystem.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForSqlC")
@IEntity(navigationName="SQL传输数据设置映射",description="SQL传输数据设置映射")
public class AutoETL_ActivityNodeForSqlC implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="strFieldName",length = 100, nullable = false)
	@IColumn(description="字段名", isNullable = false)
	private String strFieldName;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_ActivityNodeForSqlID")
	private AutoETL_ActivityNodeForSql autoETL_ActivityNodeForSql;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForSqlC")
	private Set<AutoETL_ActivityNodeForSqlCR> autoETL_ActivityNodeForSqlCRs = new HashSet<AutoETL_ActivityNodeForSqlCR>(0);

	public void setStrFieldName(String strFieldName) {
		this.strFieldName = strFieldName;
	}

	public String getStrFieldName() {
		return strFieldName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAutoETL_ActivityNodeForSql(AutoETL_ActivityNodeForSql autoETL_ActivityNodeForSql) {
		this.autoETL_ActivityNodeForSql = autoETL_ActivityNodeForSql;
	}

	public AutoETL_ActivityNodeForSql getAutoETL_ActivityNodeForSql() {
		return autoETL_ActivityNodeForSql;
	}

	public void setAutoETL_ActivityNodeForSqlCRs(
			Set<AutoETL_ActivityNodeForSqlCR> autoETL_ActivityNodeForSqlCRs) {
		this.autoETL_ActivityNodeForSqlCRs = autoETL_ActivityNodeForSqlCRs;
	}

	public Set<AutoETL_ActivityNodeForSqlCR> getAutoETL_ActivityNodeForSqlCRs() {
		return autoETL_ActivityNodeForSqlCRs;
	}
}
