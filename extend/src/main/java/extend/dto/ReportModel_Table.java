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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "ReportModel_Table")
@IEntity(navigationName="数据表",description="数据表")
public class ReportModel_Table implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="strTableName",length = 128,nullable=false)
	@IColumn(description="实体表名", isNullable = false)
	private String strTableName;
	
	@Column(name="strChinaName",length = 50,nullable=false)
	@IColumn(description="表名", isNullable = false, isSpecialCharCheck=true)
	private String strChinaName;
	
	@Transient
	@IColumn(isListShow=true, description="表名", isKeyName=true, isNullable = false)
	private String strShowTableName;
	
	@Column(name="strCheckInstance",length = 50)
	@IColumn(description="校验实例")
	private String strCheckInstance;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "reportModel_Table")
	private Set<ReportModel_Field> reportModel_Fields = new HashSet<ReportModel_Field>(0);
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suit")
	@IColumn(description="主题")
	private Suit suit;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dataSource")
	@IColumn(description="数据源",isNullable=false)
	private AutoETL_DataSource dataSource;
	
	@IColumn(tagMethodName="getAutoDTOTag",description="是否自动生成", isNullable = false)
	@Column(name = "strAutoDTO", nullable = false)
	private String strAutoDTO;
	
	public static Map<String,String> getAutoDTOTag(){
		return ShowContext.getInstance().getShowEntityMap().get("isPrimaryKey");
	}
	
	@Column(name = "strAddFields", length = 10, nullable = false)
	@IColumn(tagMethodName="getAutoDTOTag", description="固定字段", isNullable = false,isSingleTagHidden=false)
	private String strAddFields;
	
	public static Map<String,String> getAddFields(){
		return ShowContext.getInstance().getShowEntityMap().get("isPrimaryKey");
	}
	
	@Id
	@Column(name = "autoTableID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoTableID;
	
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

	public void setAutoTableID(String autoTableID) {
		this.autoTableID = autoTableID;
	}

	public String getAutoTableID() {
		return autoTableID;
	}

	public void setReportModel_Fields(Set<ReportModel_Field> reportModel_Fields) {
		this.reportModel_Fields = reportModel_Fields;
	}

	public Set<ReportModel_Field> getReportModel_Fields() {
		return reportModel_Fields;
	}

	public void setStrChinaName(String strChinaName) {
		this.strChinaName = strChinaName;
	}

	public String getStrChinaName() {
		return strChinaName;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setDataSource(AutoETL_DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public AutoETL_DataSource getDataSource() {
		return dataSource;
	}

	public void setStrAddFields(String strAddFields) {
		this.strAddFields = strAddFields;
	}

	public String getStrAddFields() {
		return strAddFields;
	}

	public void setStrAutoDTO(String strAutoDTO) {
		this.strAutoDTO = strAutoDTO;
	}

	public String getStrAutoDTO() {
		return strAutoDTO;
	}

	public void setStrCheckInstance(String strCheckInstance) {
		this.strCheckInstance = strCheckInstance;
	}

	public String getStrCheckInstance() {
		return strCheckInstance;
	}

	public String getStrShowTableName() {
		 return strTableName+" | "+strChinaName;
	}

	public void setStrShowTableName(String strShowTableName) {
	}
	
	

}




