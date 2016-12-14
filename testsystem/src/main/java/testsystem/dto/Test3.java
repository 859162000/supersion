package testsystem.dto;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "Test3")
public class Test3 implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtDate", nullable = false)
	@IColumn(description="数据时间", isNullable = false)
	private Date dtDate;
	
	@IColumn(tagMethodName="getCheckTypeTag",description="校验状态")
	@Column(name = "CheckType")
	private String CheckType;
	
	public static Map<String,String> getCheckTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("CheckType");
	}
	
	@IColumn(tagMethodName="getVerifyTypeTag",description="审核状态")
	@Column(name = "VerifyType")
	private String VerifyType;
	
	public static Map<String,String> getVerifyTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("VerifyType");
	}

	public void setDtDate(String dtDate) {
		this.dtDate = TypeParse.parseDate(dtDate);
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setCheckType(String checkType) {
		CheckType = checkType;
	}

	public String getCheckType() {
		return CheckType;
	}

	public void setVerifyType(String verifyType) {
		VerifyType = verifyType;
	}

	public String getVerifyType() {
		return VerifyType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
