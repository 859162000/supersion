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
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRZXCJFKBW")
@IEntity(description= "个人征信采集反馈报文头")
public class GRZXCJFKBW  implements java.io.Serializable {

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

	@IColumn(description="报文格式版本号")
	@Column(name = "strVersionCode", length = 50)
	private String strVersionCode;
	
	@IColumn(description="金融机构代码")
	@Column(name = "strJRJGCode", length = 200)
	private String strJRJGCode;
	
	@IColumn(description="报文生成时间")
	@Column(name = "strBWSCSJ", length = 200)
	private String strBWSCSJ;
	
	@IColumn(description="原报文文件名")
	@Column(name = "strYBWWJM", length = 200)
	private String strYBWWJM;
	
	@IColumn(description="出错报文文件名")
	@Column(name = "strBWCCWJM", length = 200)
	private String strBWCCWJM;
	
	@IColumn(description="账户记录总数")
	@Column(name = "strZHJLZS", length = 200)
	private String strZHJLZS;
	
	@IColumn(tagMethodName="getCCYYDM_1Tag",description="出错原因代码")
	@Column(name = "strCCYYDM_1", length = 50)
	private String strCCYYDM_1;
	
	public static Map<String,String> getCCYYDM_1Tag(){
		return ShowContext.getInstance().getShowEntityMap().get("GR_CCYY");
	}
	
	@IColumn(tagMethodName="getCCYYDM_2Tag",description="出错原因代码")
	@Column(name = "strCCYYDM_2", length = 50)
	private String strCCYYDM_2;
	
	public static Map<String,String> getCCYYDM_2Tag(){
		return ShowContext.getInstance().getShowEntityMap().get("GR_CCYY");
	}
	
	@IColumn(tagMethodName="getCCYYDM_3Tag",description="出错原因代码")
	@Column(name = "strCCYYDM_3", length = 50)
	private String strCCYYDM_3;
	
	public static Map<String,String> getCCYYDM_3Tag(){
		return ShowContext.getInstance().getShowEntityMap().get("GR_CCYY");
	}
	
	@IColumn(tagMethodName="getCCYYDM_4Tag",description="出错原因代码")
	@Column(name = "strCCYYDM_4", length = 50)
	private String strCCYYDM_4;
	
	public static Map<String,String> getCCYYDM_4Tag(){
		return ShowContext.getInstance().getShowEntityMap().get("GR_CCYY");
	}
	
	@IColumn(tagMethodName="getCCYYDM_5Tag",description="出错原因代码")
	@Column(name = "strCCYYDM_5", length = 50)
	private String strCCYYDM_5;
	
	public static Map<String,String> getCCYYDM_5Tag(){
		return ShowContext.getInstance().getShowEntityMap().get("GR_CCYY");
	}
	
	@IColumn(tagMethodName="getCBYQTag",description="重报要求")
	@Column(name = "strCBYQ", length = 200)
	private String strCBYQ;
	
	public static Map<String,String> getCBYQTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GRZX_CBYQ");
	}
	
	@IColumn(description="联系人")
	@Column(name = "strLXR", length = 200)
	private String strLXR;
	
	@IColumn(description="联系电话")
	@Column(name = "strLXDH", length = 200)
	private String strLXDH;
	
	@IColumn(description="预留字段")
	@Column(name = "strYLZD", length = 200)
	private String strYLZD;
	
	@Transient
	@IColumn(description="出错原因",isListShow = true)
	private String strShowCCYY;
	
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

	public String getStrYBWWJM() {
		return strYBWWJM;
	}

	public void setStrYBWWJM(String strYBWWJM) {
		this.strYBWWJM = strYBWWJM;
	}

	public String getStrBWCCWJM() {
		return strBWCCWJM;
	}

	public void setStrBWCCWJM(String strBWCCWJM) {
		this.strBWCCWJM = strBWCCWJM;
	}

	public String getStrZHJLZS() {
		return strZHJLZS;
	}

	public void setStrZHJLZS(String strZHJLZS) {
		this.strZHJLZS = strZHJLZS;
	}

	public String getStrCCYYDM_1() {
		return strCCYYDM_1;
	}

	public void setStrCCYYDM_1(String strCCYYDM_1) {
		this.strCCYYDM_1 = strCCYYDM_1;
	}

	public String getStrCCYYDM_2() {
		return strCCYYDM_2;
	}

	public void setStrCCYYDM_2(String strCCYYDM_2) {
		this.strCCYYDM_2 = strCCYYDM_2;
	}

	public String getStrCCYYDM_3() {
		return strCCYYDM_3;
	}

	public void setStrCCYYDM_3(String strCCYYDM_3) {
		this.strCCYYDM_3 = strCCYYDM_3;
	}

	public String getStrCCYYDM_4() {
		return strCCYYDM_4;
	}

	public void setStrCCYYDM_4(String strCCYYDM_4) {
		this.strCCYYDM_4 = strCCYYDM_4;
	}

	public String getStrCCYYDM_5() {
		return strCCYYDM_5;
	}

	public void setStrCCYYDM_5(String strCCYYDM_5) {
		this.strCCYYDM_5 = strCCYYDM_5;
	}

	public String getStrCBYQ() {
		return strCBYQ;
	}

	public void setStrCBYQ(String strCBYQ) {
		this.strCBYQ = strCBYQ;
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
		
		String[] strCCYYDMs = {strCCYYDM_1,strCCYYDM_2,strCCYYDM_3,strCCYYDM_4,strCCYYDM_5};
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
		if(ShowContext.getInstance().getShowEntityMap().get("GR_CCYY").containsKey(strCCYYSub)){
			strCCYYSubName = ShowContext.getInstance().getShowEntityMap().get("GR_CCYY").get(strCCYYSub);
		}
		return strCCYYSubName;
	}

	public void setStrYLZD(String strYLZD) {
		this.strYLZD = strYLZD;
	}

	public String getStrYLZD() {
		return strYLZD;
	}
	
}
