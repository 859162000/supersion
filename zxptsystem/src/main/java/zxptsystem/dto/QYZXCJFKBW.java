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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.UserInfo;

import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "QYZXCJFKBW")
public class QYZXCJFKBW  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(description="导入时间")
	@Column(name = "dtInportTime",nullable=true)
	private Timestamp dtInportTime;
	
	@IColumn(description="导入人员")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;
	
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

	public String getStrBWCCWJM() {
		return strBWCCWJM;
	}

	public void setStrBWCCWJM(String strBWCCWJM) {
		this.strBWCCWJM = strBWCCWJM;
	}

	public String getStrBWLX() {
		return strBWLX;
	}

	public void setStrBWLX(String strBWLX) {
		this.strBWLX = strBWLX;
	}

	public String getStrCCYY() {
		return strCCYY;
	}

	public void setStrCCYY(String strCCYY) {
		this.strCCYY = strCCYY;
	}

	public String getStrCBYQ() {
		return strCBYQ;
	}

	public void setStrCBYQ(String strCBYQ) {
		this.strCBYQ = strCBYQ;
	}

	@IColumn(description="版本号")
	@Column(name = "strVersionCode", length = 200)
	private String strVersionCode;
	
	@IColumn(description="金融机构代码")
	@Column(name = "strJRJGCode", length = 200)
	private String strJRJGCode;
	
	@IColumn(description="报文生成时间")
	@Column(name = "strBWSCSJ", length = 200)
	private String strBWSCSJ;
	
	@IColumn(description="出错报文文件名")
	@Column(name = "strBWCCWJM", length = 200)
	private String strBWCCWJM;
	
	@IColumn(tagMethodName="getBWLXTag",description="报文类型")
	@Column(name = "strBWLX", length = 200)
	private String strBWLX;
	
	public static Map<String,String> getBWLXTag(){
		return ShowContext.getInstance().getShowEntityMap().get("BWLX");
	}
	
	@IColumn(tagMethodName="getCCYYTag",description="出错原因")
	@Column(name = "strCCYY", length = 200)
	private String strCCYY;
	
	public static Map<String,String> getCCYYTag(){
		return ShowContext.getInstance().getShowEntityMap().get("CCYY");
	}
	
	@Transient
	@IColumn(description="出错原因",isListShow = true)
	private String strShowCCYY;
	
	@IColumn(tagMethodName="getCBYQTag",description="重报要求")
	@Column(name = "strCBYQ", length = 2000)
	private String strCBYQ;
	
	public static Map<String,String> getCBYQTag(){
		return ShowContext.getInstance().getShowEntityMap().get("CBYQ");
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setStrShowCCYY(String strShowCCYY) {
		this.strShowCCYY = strShowCCYY;
	}

	public String getStrShowCCYY() {
		
        String strCCYYName = "";
		
		String strCCYY1 = getCCYYSubName(strCCYY.substring(0,4));
		String strCCYY2 = getCCYYSubName(strCCYY.substring(4,8));
		String strCCYY3 = getCCYYSubName(strCCYY.substring(8,12));
		String strCCYY4 = getCCYYSubName(strCCYY.substring(12,16));
		String strCCYY5 = getCCYYSubName(strCCYY.substring(16,20));

		strCCYYName = strCCYY1 + " " + strCCYY2 + " " + strCCYY3 + " " + strCCYY4 + " " + strCCYY5;
		
		return strCCYYName;
	}
	
	private String getCCYYSubName(String strCCYYSub){
		String strCCYYSubName = strCCYYSub;
		if(ShowContext.getInstance().getShowEntityMap().get("CCYY").containsKey(strCCYYSub)){
			strCCYYSubName = ShowContext.getInstance().getShowEntityMap().get("CCYY").get(strCCYYSub);
		}
		return strCCYYSubName;
	}

}
