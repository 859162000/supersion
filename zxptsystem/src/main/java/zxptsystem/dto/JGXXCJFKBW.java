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

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.UserInfo;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "JGXXCJFKBW")
public class JGXXCJFKBW  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(description="版本号")
	@Column(name = "strVersionCode", length = 50)
	private String strVersionCode;
	
	@IColumn(description="金融机构代码")
	@Column(name = "strJRJGCode", length = 200)
	private String strJRJGCode;
	
	@IColumn(description="报文生成时间")
	@Column(name = "strBWSCSJ", length = 200)
	private String strBWSCSJ;

	@IColumn(description="出错报文名")
	@Column(name = "strBWCCWJM", length = 200)
	private String strBWCCWJM;
		
	@IColumn(tagMethodName="getCCYYDMTag",description="出错原因代码")
	@Column(name = "strCCYYDM", length = 50)
	private String strCCYYDM;
	
	public static Map<String,String> getCCYYDMTag(){
		return ShowContext.getInstance().getShowEntityMap().get("JG_CCYY");
	}
	
	@IColumn(description="联系人")
	@Column(name = "strLXR", length = 200)
	private String strLXR;
	
	@IColumn(description="联系电话")
	@Column(name = "strLXDH", length = 200)
	private String strLXDH;
	
	@Transient
	@IColumn(description="出错原因",isListShow = true)
	private String strShowCCYY;

	@IColumn(description="导入时间")
	@Column(name = "dtInportTime",nullable=true)
	private Timestamp dtInportTime;
	
	@IColumn(description="导入人员")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;
	
	@IColumn(tagMethodName="getJG_BWLXTag",description="报文类型")
	@Column(name = "strBWLX", length = 200)
	private String strBWLX;
	
	public static Map<String,String> getJG_BWLXTag(){
		return ShowContext.getInstance().getShowEntityMap().get("JG_BWLX");
	}
	
	@IColumn(description="预留字段")
	@Column(name = "strYLZD", length = 200)
	private String strYLZD;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getStrCCYYDM() {
		return strCCYYDM;
	}

	public void setStrCCYYDM(String strCCYYDM) {
		this.strCCYYDM = strCCYYDM;
	}

	public String getStrLXR() {
		return strLXR;
	}

	public void setStrLXR(String strLXR) {
		this.strLXR = strLXR;
	}

	public String getStrLXDH() {
		return strLXDH;
	}

	public void setStrLXDH(String strLXDH) {
		this.strLXDH = strLXDH;
	}
	
	public void setStrShowCCYY(String strShowCCYY) {
		this.strShowCCYY = strShowCCYY;
	}

	public String getStrShowCCYY() {
		
		String[] strCCYYDMs = {strCCYYDM};
		String strCCYYName = ""; 
		for(String ccyydm : strCCYYDMs){
			if(!StringUtils.isBlank(ccyydm)){
				strCCYYName += getCCYYSubName(ccyydm)+" ";
			}
		}
		return strCCYYName;
	}
	
	private String getCCYYSubName(String strCCYYSub){
		String strCCYYSubName = strCCYYSub;
		if(ShowContext.getInstance().getShowEntityMap().get("JG_CCYY").containsKey(strCCYYSub)){
			strCCYYSubName = ShowContext.getInstance().getShowEntityMap().get("JG_CCYY").get(strCCYYSub);
		}
		return strCCYYSubName;
	}

	public void setStrBWLX(String strBWLX) {
		this.strBWLX = strBWLX;
	}

	public String getStrBWLX() {
		return strBWLX;
	}

	public void setStrYLZD(String strYLZD) {
		this.strYLZD = strYLZD;
	}

	public String getStrYLZD() {
		return strYLZD;
	}
	
}
