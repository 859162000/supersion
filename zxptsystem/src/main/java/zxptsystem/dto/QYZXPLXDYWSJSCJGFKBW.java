package zxptsystem.dto;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.UserInfo;

import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "QYZXPLXDYWSJSCJGFKBW")
public class QYZXPLXDYWSJSCJGFKBW  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(description="版本号")
	@Column(name = "strVersionCode", length = 200)
	private String strVersionCode;
	
	@IColumn(description="金融机构代码")
	@Column(name = "strJRJGCode", length = 200)
	private String strJRJGCode;
	
	@IColumn(description="报文生成时间")
	@Column(name = "strBWSCSJ", length = 200)
	private String strBWSCSJ;
	
	@IColumn(tagMethodName="getBWLXTag",description="报文类型")
	@Column(name = "strBWLX", length = 200)
	private String strBWLX;
	
	public static Map<String,String> getBWLXTag(){
		return ShowContext.getInstance().getShowEntityMap().get("PLXDYWSJBWLX");
	}
	
	@IColumn(tagMethodName="getBWCLJGTag",description="报文处理结果")
	@Column(name = "strBWCLJG", length = 200)
	private String strBWCLJG;
	
	public static Map<String,String> getBWCLJGTag(){
		return ShowContext.getInstance().getShowEntityMap().get("BWCLJG");
	}
	
	@IColumn(description="导入时间")
	@Column(name = "dtInportTime",nullable=true)
	private Timestamp dtInportTime;
	
	@IColumn(description="导入人员")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;
	
	@IColumn(description="出错报文文件名")
	@Column(name = "strBWCCWJM", length = 200)
	private String strBWCCWJM;
	
	public Timestamp getDtInportTime() {
		return dtInportTime;
	}

	public void setDtInportTime(Timestamp dtInportTime) {
		this.dtInportTime = dtInportTime;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getStrVersionCode() {
		return strVersionCode;
	}

	public void setStrVersionCode(String strVersionCode) {
		this.strVersionCode = strVersionCode;
	}

	public String getStrJRJGCode() {
		return strJRJGCode;
	}

	public void setStrJRJGCode(String strJRJGCode) {
		this.strJRJGCode = strJRJGCode;
	}

	public String getStrBWSCSJ() {
		return strBWSCSJ;
	}

	public void setStrBWSCSJ(String strBWSCSJ) {
		this.strBWSCSJ = strBWSCSJ;
	}

	public String getStrBWLX() {
		return strBWLX;
	}

	public void setStrBWLX(String strBWLX) {
		this.strBWLX = strBWLX;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setStrBWCLJG(String strBWCLJG) {
		this.strBWCLJG = strBWCLJG;
	}

	public String getStrBWCLJG() {
		return strBWCLJG;
	}

	public String getStrBWCCWJM() {
		return strBWCCWJM;
	}

	public void setStrBWCCWJM(String strBWCCWJM) {
		this.strBWCCWJM = strBWCCWJM;
	}
	
	
}
