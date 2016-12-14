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
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRZXZHBSBGCJFKBW")
@IEntity(description= "个人征信账户标识变更采集反馈报文头")
public class GRZXZHBSBGCJFKBW  implements java.io.Serializable {

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

	@IColumn(description="数据格式版本号")
	@Column(name = "strVersionCode", length = 50)
	private String strVersionCode;
	
	@IColumn(description="报文生成时间")
	@Column(name = "strBWSCSJ", length = 200)
	private String strBWSCSJ;
	
	@IColumn(description="出错报文文件名")
	@Column(name = "strBWCCWJM", length = 200)
	private String strBWCCWJM;

	@IColumn(tagMethodName="getGRZXZHBSBGCCXXTag",description="出错信息")
	@Column(name = "strGRZXZHBSBGCCXX", length = 50)
	private String strGRZXZHBSBGCCXX;
	
	public static Map<String,String> getGRZXZHBSBGCCXXTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GRZXZHBSBGCCXX");
	}
	
	@IColumn(description="联系人")
	@Column(name = "strLXR", length = 100)
	private String strLXR;
	
	@IColumn(description="联系电话")
	@Column(name = "strLXDH", length = 50)
	private String strLXDH;
	
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

	public void setStrGRZXZHBSBGCCXX(String strGRZXZHBSBGCCXX) {
		this.strGRZXZHBSBGCCXX = strGRZXZHBSBGCCXX;
	}

	public String getStrGRZXZHBSBGCCXX() {
		return strGRZXZHBSBGCCXX;
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

	
}
