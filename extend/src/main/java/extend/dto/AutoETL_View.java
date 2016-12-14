package extend.dto;

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

import extend.dto.AutoETL_DataSource;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_View")
@IEntity(navigationName="视图",description="视图")
public class AutoETL_View implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="strTableName",length = 30,nullable=false)
	@IColumn(description="实体视图名", isNullable = false)
	private String strTableName;
	
	@Column(name="strChinaName",length = 50,nullable=false)
	@IColumn(description="视图名",isKeyName=true, isNullable = false)
	private String strChinaName;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_View")
	private Set<AutoETL_ViewField> autoETL_ViewFields = new HashSet<AutoETL_ViewField>(0);
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dataSource")
	@IColumn(description="数据源")
	private AutoETL_DataSource dataSource;
	
	@Id
	@Column(name = "autoViewID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoViewID;
	
	
	public String getStrTableName() {
		return strTableName;
	}

	public void setStrTableName(String strTableName) {
		this.strTableName = strTableName;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setStrChinaName(String strChinaName) {
		this.strChinaName = strChinaName;
	}

	public String getStrChinaName() {
		return strChinaName;
	}


	public void setDataSource(AutoETL_DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public AutoETL_DataSource getDataSource() {
		return dataSource;
	}

	public void setAutoETL_ViewFields(Set<AutoETL_ViewField> autoETL_ViewFields) {
		this.autoETL_ViewFields = autoETL_ViewFields;
	}

	public Set<AutoETL_ViewField> getAutoETL_ViewFields() {
		return autoETL_ViewFields;
	}

	public void setAutoViewID(String autoViewID) {
		this.autoViewID = autoViewID;
	}

	public String getAutoViewID() {
		return autoViewID;
	}


}




