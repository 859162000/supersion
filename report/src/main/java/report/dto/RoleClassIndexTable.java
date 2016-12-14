package report.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.RoleInfo;

import report.dto.RptInfo;

import framework.interfaces.IColumn;
import framework.security.SecurityContext;


@Entity
@Table(name = "RoleClassIndexTable")
public class RoleClassIndexTable implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -7942368103590146359L;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private RoleInfo roleInfo;
	
	@IColumn(tagMethodName="getClassTag",description="表", isNullable = false)
	@Column(name = "strClassCode", nullable = false)
	private String strClassCode;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRptCode", nullable = false)
	private RptInfo RptInfo;

	@Transient
	@IColumn(isIdListField = true, target="RptInfo")
	private String[] strRptInfoIdList;
	
	@Transient
	@IColumn(isIdListField = true, target="strClassCode")
	private String[] strClassIdList;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	

	public static Map<String,String> getClassTag() throws Exception{
		return SecurityContext.getInstance().getDataSecurityShowMap().get("report.dto.RoleClassIndexTable");
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setStrClassCode(String strClassCode) {
		this.strClassCode = strClassCode;
	}

	public String getStrClassCode() {
		return strClassCode;
	}

	public void setStrClassIdList(String[] strClassIdList) {
		this.strClassIdList = strClassIdList;
	}

	public String[] getStrClassIdList() {
		return strClassIdList;
	}

	public RptInfo getRptInfo() {
		return RptInfo;
	}

	public void setRptInfo(RptInfo rptInfo) {
		RptInfo = rptInfo;
	}

	public String[] getStrRptInfoIdList() {
		return strRptInfoIdList;
	}

	public void setStrRptInfoIdList(String[] strRptInfoIdList) {
		this.strRptInfoIdList = strRptInfoIdList;
	}
}
