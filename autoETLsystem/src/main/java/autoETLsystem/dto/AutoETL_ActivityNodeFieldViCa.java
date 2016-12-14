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

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeFieldViCa")
@IEntity(navigationName="设置视图字段条件",description="设置视图字段条件")
public class AutoETL_ActivityNodeFieldViCa implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="strWhenValue",length = 50)
	@IColumn(description="源值")
	private String strWhenValue;
	
	@Column(name="strThenValue",length = 4000, nullable = false)
	@IColumn(description="映射值", isNullable = false)
	private String strThenValue;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeFieldValueID", nullable = false)
	@IColumn(description="源视图字段值",isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeFieldViewV autoETL_ActivityNodeFieldViewV;

	@Id
	@Column(name = "autoActivityNodeFieldCaseId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeFieldCaseId;

	public String getAutoActivityNodeFieldCaseId() {
		return autoActivityNodeFieldCaseId;
	}

	public void setAutoActivityNodeFieldCaseId(String autoActivityNodeFieldCaseId) {
		this.autoActivityNodeFieldCaseId = autoActivityNodeFieldCaseId;
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

	public void setAutoETL_ActivityNodeFieldViewV(
			AutoETL_ActivityNodeFieldViewV autoETL_ActivityNodeFieldViewV) {
		this.autoETL_ActivityNodeFieldViewV = autoETL_ActivityNodeFieldViewV;
	}

	public AutoETL_ActivityNodeFieldViewV getAutoETL_ActivityNodeFieldViewV() {
		return autoETL_ActivityNodeFieldViewV;
	}


}


