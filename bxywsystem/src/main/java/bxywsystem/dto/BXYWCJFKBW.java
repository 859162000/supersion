package bxywsystem.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import coresystem.dto.UserInfo;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "BXYWCJFKBW")
@IEntity(navigationName="保险业务采集反馈报文",description="保险业务采集反馈报文")
public class BXYWCJFKBW  implements java.io.Serializable {

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
	

	@IColumn(description="版本号")
	@Column(name = "strVersionCode", length = 200)
	private String strVersionCode;
	
	@IColumn(description="报文生成时间")
	@Column(name = "strBWSCSJ", length = 200)
	private String strBWSCSJ;
	
	@IColumn(tagMethodName="getBWLXTag",description="报文类型")
	@Column(name = "strBWLX", length = 200)
	private String strBWLX;
	
	public static Map<String,String> getBWLXTag(){
		return ShowContext.getInstance().getShowEntityMap().get("BXYWBWLX");
	}
	
	@IColumn(description="出错报文名")
	@Column(name = "strCCBWM", length = 200)
	private String strCCBWM;
	
	
	@IColumn(description="信息记录数")
	@Column(name = "strXXJLS", length = 200)
	private String strXXJLS;
	
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "bXYWCJFKBW")
	private Set<BXYWCJFKBWSub> bXYWCJFKBWSubs = new HashSet<BXYWCJFKBWSub>(0);
	
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

	public String getStrBWLX() {
		return strBWLX;
	}

	public void setStrBWLX(String strBWLX) {
		this.strBWLX = strBWLX;
	}

	public String getStrCCBWM() {
		return strCCBWM;
	}

	public void setStrCCBWM(String strCCBWM) {
		this.strCCBWM = strCCBWM;
	}
	
	public String getStrXXJLS() {
		return strXXJLS;
	}

	public void setStrXXJLS(String strXXJLS) {
		this.strXXJLS = strXXJLS;
	}

	public Set<BXYWCJFKBWSub> getbXYWCJFKBWSubs() {
		return bXYWCJFKBWSubs;
	}

	public void setbXYWCJFKBWSubs(Set<BXYWCJFKBWSub> bXYWCJFKBWSubs) {
		this.bXYWCJFKBWSubs = bXYWCJFKBWSubs;
	}
	
}
