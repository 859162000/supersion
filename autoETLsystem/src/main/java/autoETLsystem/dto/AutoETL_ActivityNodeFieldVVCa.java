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
@Table(name = "AutoETL_ActivityNodeFieldVVCa")
@IEntity(navigationName="视图字段映射值",description="视图字段映射值")
public class AutoETL_ActivityNodeFieldVVCa implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="strWhenValue",length = 50)
	@IColumn(description="源值")
	private String strWhenValue;
	
	@Column(name="strThenValue",length = 4000, nullable = false)
	@IColumn(description="映射值", isNullable = false)
	private String strThenValue;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeFieldVValueID", nullable = false)
	@IColumn(description="源视图字段值",isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeFieldViewV autoETL_ActivityNodeFieldViewV;

	@Id
	@Column(name = "autoActivityNodeFieldVVCaseId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeFieldVVCaseId;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeFieldVVCa")
	private Set<AutoETL_ActivityNodeFieldVVCon> autoETL_ActivityNodeFieldVVCons = new HashSet<AutoETL_ActivityNodeFieldVVCon>(0);

	public void setStrWhenValue(String strWhenValue) {
		this.strWhenValue = strWhenValue;
	}

	public String getStrWhenValue() {
		return strWhenValue;
	}

	public void setStrThenValue(String strThenValue) {
		this.strThenValue = strThenValue;
	}

	public String getStrThenValue() {
		return strThenValue;
	}

	public void setAutoETL_ActivityNodeFieldViewV(
			AutoETL_ActivityNodeFieldViewV autoETL_ActivityNodeFieldViewV) {
		this.autoETL_ActivityNodeFieldViewV = autoETL_ActivityNodeFieldViewV;
	}

	public AutoETL_ActivityNodeFieldViewV getAutoETL_ActivityNodeFieldViewV() {
		return autoETL_ActivityNodeFieldViewV;
	}

	public void setAutoActivityNodeFieldVVCaseId(
			String autoActivityNodeFieldVVCaseId) {
		this.autoActivityNodeFieldVVCaseId = autoActivityNodeFieldVVCaseId;
	}

	public String getAutoActivityNodeFieldVVCaseId() {
		return autoActivityNodeFieldVVCaseId;
	}

	public void setAutoETL_ActivityNodeFieldVVCons(
			Set<AutoETL_ActivityNodeFieldVVCon> autoETL_ActivityNodeFieldVVCons) {
		this.autoETL_ActivityNodeFieldVVCons = autoETL_ActivityNodeFieldVVCons;
	}

	public Set<AutoETL_ActivityNodeFieldVVCon> getAutoETL_ActivityNodeFieldVVCons() {
		return autoETL_ActivityNodeFieldVVCons;
	}
}


