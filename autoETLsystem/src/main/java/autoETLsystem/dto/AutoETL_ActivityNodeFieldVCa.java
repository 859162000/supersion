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
@Table(name = "AutoETL_ActivityNodeFieldVCa")
@IEntity(navigationName="映射值",description="映射值")
public class AutoETL_ActivityNodeFieldVCa implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="strWhenValue",length = 500)
	@IColumn(description="源值")
	private String strWhenValue;
	
	@Column(name="strThenValue",length = 4000, nullable = false)
	@IColumn(description="映射值", isNullable = false)
	private String strThenValue;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeFieldValueID", nullable = false)
	@IColumn(description="源字段值",isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeFieldV autoETL_ActivityNodeFieldV;

	@Id
	@Column(name = "autoActivityNodeFieldCaseId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeFieldCaseId;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeFieldVCa")
	private Set<AutoETL_ActivityNodeFieldVCo> autoETL_ActivityNodeFieldVCos = new HashSet<AutoETL_ActivityNodeFieldVCo>(0);

	public String getAutoActivityNodeFieldCaseId() {
		return autoActivityNodeFieldCaseId;
	}

	public void setAutoActivityNodeFieldCaseId(String autoActivityNodeFieldCaseId) {
		this.autoActivityNodeFieldCaseId = autoActivityNodeFieldCaseId;
	}

	public void setAutoETL_ActivityNodeFieldV(AutoETL_ActivityNodeFieldV autoETL_ActivityNodeFieldV) {
		this.autoETL_ActivityNodeFieldV = autoETL_ActivityNodeFieldV;
	}

	public AutoETL_ActivityNodeFieldV getAutoETL_ActivityNodeFieldV() {
		return autoETL_ActivityNodeFieldV;
	}

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


	public void setAutoETL_ActivityNodeFieldVCos(
			Set<AutoETL_ActivityNodeFieldVCo> autoETL_ActivityNodeFieldVCos) {
		this.autoETL_ActivityNodeFieldVCos = autoETL_ActivityNodeFieldVCos;
	}

	public Set<AutoETL_ActivityNodeFieldVCo> getAutoETL_ActivityNodeFieldVCos() {
		return autoETL_ActivityNodeFieldVCos;
	}
}


