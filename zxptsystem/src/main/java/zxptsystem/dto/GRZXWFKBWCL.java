package zxptsystem.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRZXWFKBWCL")
@IEntity(description= "个人征信无反馈报文处理")
public class GRZXWFKBWCL  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	@IColumn(description="金融机构代码")
	@Column(name = "strJRJGCode", length = 200)
	private String strJRJGCode;
	
	@IColumn(tagMethodName="getGrReportTypeTag",description="报文类别")
	@Column(name = "strGrReportType", length = 200)
	private String strGrReportType;
	
	public static Map<String,String> getGrReportTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GrReportType");
	}
	
	@IColumn(description="报文生成日期")
	@Column(name = "strBWSCRQ", length = 200)
	private String strBWSCRQ;
	
	@IColumn(description="报文名")
	@Column(name = "strBWM", length = 200)
	private String strBWM;
	
	@IColumn(tagMethodName="getBWCLTag",description="报文处理")
	@Column(name = "strBWCL", length = 200)
	private String strBWCL;
	
	public static Map<String,String> getBWCLTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GRZXBWCL");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStrJRJGCode() {
		return strJRJGCode;
	}

	public void setStrJRJGCode(String strJRJGCode) {
		this.strJRJGCode = strJRJGCode;
	}

	public String getStrGrReportType() {
		return strGrReportType;
	}

	public void setStrGrReportType(String strGrReportType) {
		this.strGrReportType = strGrReportType;
	}

	public String getStrBWSCRQ() {
		return strBWSCRQ;
	}

	public void setStrBWSCRQ(String strBWSCRQ) {
		this.strBWSCRQ = strBWSCRQ;
	}

	public String getStrBWM() {
		return strBWM;
	}

	public void setStrBWM(String strBWM) {
		this.strBWM = strBWM;
	}

	public String getStrBWCL() {
		return strBWCL;
	}

	public void setStrBWCL(String strBWCL) {
		this.strBWCL = strBWCL;
	}
	
}
