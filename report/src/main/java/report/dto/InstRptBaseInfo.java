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

import coresystem.dto.InstInfo;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;


@Entity
@Table(name = "InstRptBaseInfo")
@IEntity(description= "机构报表填报基础信息",navigationName="机构信息")
public class InstRptBaseInfo implements java.io.Serializable {
	
	/**  **/
	private static final long serialVersionUID = -2050269741131390687L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo; 
	
	@Transient
	@IColumn(description="主题", isNullable = true,tagMethodName="getStrSuitCodeTag")
	private String strSuitCode;
	
	public static Map<String, String> getStrSuitCodeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("Str_1104_SuitCode");
	}
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRptCode", nullable = false)
	private RptInfo rptInfo;
	
	@Column(name="reporter",length=50,nullable=true)
	@IColumn(description="填报人", isNullable = true)
	private String reporter;
	
	@Column(name="checker",length=50,nullable=true)
	@IColumn(description="复核人", isNullable = true)
	private String checker;
	
	@Column(name="manager",length=50,nullable=true)
	@IColumn(description="负责人", isNullable = true)
	private String manager;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public RptInfo getRptInfo() {
		return rptInfo;
	}

	public void setRptInfo(RptInfo rptInfo) {
		this.rptInfo = rptInfo;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getStrSuitCode() {
		return strSuitCode;
	}

	public void setStrSuitCode(String strSuitCode) {
		this.strSuitCode = strSuitCode;
	}

}
