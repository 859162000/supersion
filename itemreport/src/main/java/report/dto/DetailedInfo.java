package report.dto;

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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import extend.dto.ReportModel_Field;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "DetailedInfo")
public class DetailedInfo implements java.io.Serializable{
	
	
	/**  **/
	private static final long serialVersionUID = 8920948803985288189L;

	@Id
	@Column(name = "autoDetailedID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoDetailedID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoFieldID")
	private ReportModel_Field reportModel_Field;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoItemsCalculationID")
	@IColumn(isSingleTagHidden=true)
	private ItemsCalculation itemsCalculation;
	
	@IColumn(tagMethodName="getConditionTypeTag",description="条件")
	@Column(name = "conditionType")
	private String conditionType;
	
	public static Map<String,String> getConditionTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("conditionTypes");
	}

	@Column(name="strDescription",length = 200)
	@IColumn(description="描述")
	private String strDescription;

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "detailedInfo")
	private Set<DetailedFieldInfo> detailedFieldInfos = new HashSet<DetailedFieldInfo>(0);
	
	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public void setReportModel_Field(ReportModel_Field reportModel_Field) {
		this.reportModel_Field = reportModel_Field;
	}

	public ReportModel_Field getReportModel_Field() {
		return reportModel_Field;
	}

	public void setAutoDetailedID(String autoDetailedID) {
		this.autoDetailedID = autoDetailedID;
	}

	public String getAutoDetailedID() {
		return autoDetailedID;
	}


	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setItemsCalculation(ItemsCalculation itemsCalculation) {
		this.itemsCalculation = itemsCalculation;
	}

	public ItemsCalculation getItemsCalculation() {
		return itemsCalculation;
	}

	public void setDetailedFieldInfos(Set<DetailedFieldInfo> detailedFieldInfos) {
		this.detailedFieldInfos = detailedFieldInfos;
	}

	public Set<DetailedFieldInfo> getDetailedFieldInfos() {
		return detailedFieldInfos;
	}

	

}

