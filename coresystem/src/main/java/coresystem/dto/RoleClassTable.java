package coresystem.dto;

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

import extend.dto.ReportModel_Table;
import framework.interfaces.IColumn;
import framework.security.SecurityContext;


@Entity
@Table(name = "RoleClassTable")
public class RoleClassTable  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private RoleInfo roleInfo;
	
	@IColumn(tagMethodName="getClassTag",description="表", isNullable = false)
	@Column(name = "strClassCode", nullable = false)
	private String strClassCode;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strReportModel_TableId", nullable = false)
	private ReportModel_Table reportModel_Table;

	@Transient
	@IColumn(isIdListField = true, target="reportModel_Table")
	private String[] strReportModel_TableIdList;
	
	@Transient
	@IColumn(isIdListField = true, target="strClassCode")
	private String[] strClassIdList;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	

	public static Map<String,String> getClassTag() throws Exception{
		return SecurityContext.getInstance().getDataSecurityShowMap().get("coresystem.dto.RoleClassTable");
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

	public void setReportModel_Table(ReportModel_Table reportModel_Table) {
		this.reportModel_Table = reportModel_Table;
	}

	public ReportModel_Table getReportModel_Table() {
		return reportModel_Table;
	}

	public void setStrReportModel_TableIdList(
			String[] strReportModel_TableIdList) {
		this.strReportModel_TableIdList = strReportModel_TableIdList;
	}

	public String[] getStrReportModel_TableIdList() {
		return strReportModel_TableIdList;
	}
	
}
