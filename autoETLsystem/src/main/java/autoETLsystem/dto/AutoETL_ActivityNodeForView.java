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
import extend.dto.AutoETL_View;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForView")
@IEntity(navigationName="视图活动结点",description="视图活动结点")
public class AutoETL_ActivityNodeForView implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_DataSourceID", nullable = false)
	@IColumn(description="源会话工厂", isNullable = false)
	private AutoETL_DataSource autoETL_DataSource;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTargetViewID", nullable = false)
	@IColumn(description="目标视图", isNullable = false)
	private AutoETL_View autoETL_TargetView;
	
	@Column(name = "cacheLine", nullable = false)
	@IColumn(description="缓存行数", isNullable = false)
	private Integer cacheLine;

	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Column(name="strDataSourceSql",length = 4000, nullable = false)
	@IColumn(description="源SQL", isNullable = false)
	private String strDataSourceSql;

	@Id
	@Column(name = "autoActivityNodeForViewID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForViewID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	/*@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)       
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForView")
	private Set<AutoETL_ActivityNodeForViewC> autoETL_ActivityNodeForViewCs = new HashSet<AutoETL_ActivityNodeForViewC>(0);
*/
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

	public void setCacheLine(String cacheLine) {
		this.cacheLine = TypeParse.parseInt(cacheLine);
	}

	public Integer getCacheLine() {
		return cacheLine;
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

	/*public void setAutoETL_ActivityNodeForSqlCs(
			Set<AutoETL_ActivityNodeForSqlC> autoETL_ActivityNodeForSqlCs) {
		this.autoETL_ActivityNodeForSqlCs = autoETL_ActivityNodeForSqlCs;
	}

	public Set<AutoETL_ActivityNodeForSqlC> getAutoETL_ActivityNodeForSqlCs() {
		return autoETL_ActivityNodeForSqlCs;
	}*/

	public void setAutoETL_TargetView(AutoETL_View autoETL_TargetView) {
		this.autoETL_TargetView = autoETL_TargetView;
	}

	public AutoETL_View getAutoETL_TargetView() {
		return autoETL_TargetView;
	}

	public void setAutoActivityNodeForViewID(String autoActivityNodeForViewID) {
		this.autoActivityNodeForViewID = autoActivityNodeForViewID;
	}

	public String getAutoActivityNodeForViewID() {
		return autoActivityNodeForViewID;
	}

}

