package zxptsystem.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import framework.interfaces.IColumn;
import framework.show.ShowContext;
import framework.helper.TypeParse;
import extend.helper.HelpTool;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import framework.interfaces.IEntity;
import java.util.Set;

import coresystem.dto.UserInfo;
import coresystem.dto.InstInfo;
import zxptsystem.dto.AutoDTO_JG_JBSX;
import zxptsystem.dto.AutoDTO_JG_JGZT;
import zxptsystem.dto.AutoDTO_JG_LLXX;
import zxptsystem.dto.AutoDTO_JG_GGJZYGXR;
import zxptsystem.dto.AutoDTO_JG_ZYGD;
import zxptsystem.dto.AutoDTO_JG_ZYGLQY;
import zxptsystem.dto.AutoDTO_JG_SJJG;

@Entity
@Table(name = "JG_JGJBXX_JC")
@IEntity(description= "机构基本信息基础段")
public class AutoDTO_JG_JGJBXX_JC implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5")
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4")
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3")
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2")
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1")
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden=true)
	@Column(name = "RPTFeedbackType", nullable =true)
	private String RPTFeedbackType;

	public static Map<String,String> getRPTFeedbackTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
	}

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	public void setRPTFeedbackType(String in) {
		RPTFeedbackType = in;
	}

	@Column(name = "XZXH", length = 100, nullable = true)
	@IColumn(description="下载序号",isSingleTagHidden=true)
	private String XZXH;

	public String getXZXH() {
		return XZXH;
	}

	public void setXZXH(String in) {
		XZXH = in;
	}

	@IColumn(tagMethodName="getRPTSubmitStatusTag",description="提交状态",isSingleTagHidden=true)
	@Column(name = "RPTSubmitStatus", nullable =true)
	private String RPTSubmitStatus;

	public static Map<String,String> getRPTSubmitStatusTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSubmitStatus");
	}

	public String getRPTSubmitStatus() {
		return RPTSubmitStatus;
	}

	public void setRPTSubmitStatus(String in) {
		RPTSubmitStatus = in;
	}

	@IColumn(tagMethodName="getRPTVerifyTypeTag",description="审核状态",isSingleTagHidden=true)
	@Column(name = "RPTVerifyType", nullable =true)
	private String RPTVerifyType;

	public static Map<String,String> getRPTVerifyTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTVerifyType");
	}

	public String getRPTVerifyType() {
		return RPTVerifyType;
	}

	public void setRPTVerifyType(String in) {
		RPTVerifyType = in;
	}

	@Column(name = "lastUpdateDate", nullable = true)
	@IColumn(description="最后修改时间", isNullable = false,isSingleTagHidden=true)
	private Timestamp lastUpdateDate;

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String in) {
		lastUpdateDate = TypeParse.parseTimestamp(in);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operationUser")
	private coresystem.dto.UserInfo operationUser;

	public coresystem.dto.UserInfo getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(coresystem.dto.UserInfo in) {
		operationUser = in;
	}

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=true)
	@Column(name = "RPTSendType", nullable =true)
	private String RPTSendType;

	public static Map<String,String> getRPTSendTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSendType");
	}

	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTSendType(String in) {
		RPTSendType = in;
	}

	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态",isSingleTagHidden=true)
	@Column(name = "RPTCheckType", nullable =true)
	private String RPTCheckType;

	public static Map<String,String> getRPTCheckTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTCheckType");
	}

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String in) {
		RPTCheckType = in;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instInfo", nullable = false)
	private coresystem.dto.InstInfo instInfo;

	public coresystem.dto.InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(coresystem.dto.InstInfo in) {
		instInfo = in;
	}

	@Column(name = "dtDate", nullable = true)
	@IColumn(description="数据时间", isNullable = false,isSingleTagHidden=true)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	@Column(name = "KHH", length = 100, nullable = true)
	@IColumn(description="客户号")
	private String KHH;

	public String getKHH() {
		return KHH;
	}

	public void setKHH(String in) {
		KHH = in;
	}

	@Column(name = "DEPTCODE", length = 50, nullable = true)
	@IColumn(description="管理行代码")
	private String DEPTCODE;

	public String getDEPTCODE() {
		return DEPTCODE;
	}

	public void setDEPTCODE(String in) {
		DEPTCODE = in;
	}

	@IColumn(tagMethodName="getKHLXTag",description="客户类型")
	@Column(name = "KHLX", nullable =true)
	private String KHLX;

	public static Map<String,String> getKHLXTag() {
		try {
			return HelpTool.getSystemConstVal("KHLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getKHLX() {
		return KHLX;
	}

	public void setKHLX(String in) {
		KHLX = in;
	}

	@Column(name = "JGXYDM", length = 50, nullable = true)
	@IColumn(description="机构信用代码")
	private String JGXYDM;

	public String getJGXYDM() {
		return JGXYDM;
	}

	public void setJGXYDM(String in) {
		JGXYDM = in;
	}

	@Column(name = "ZZJGDM", length = 50, nullable = true)
	@IColumn(description="组织机构代码")
	private String ZZJGDM;

	public String getZZJGDM() {
		return ZZJGDM;
	}

	public void setZZJGDM(String in) {
		ZZJGDM = in;
	}

	@IColumn(tagMethodName="getDJZCHLXTag",description="登记注册号类型")
	@Column(name = "DJZCHLX", nullable =true)
	private String DJZCHLX;

	public static Map<String,String> getDJZCHLXTag() {
		try {
			return HelpTool.getSystemConstVal("DJZCHLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDJZCHLX() {
		return DJZCHLX;
	}

	public void setDJZCHLX(String in) {
		DJZCHLX = in;
	}

	@Column(name = "DJZCH", length = 50, nullable = true)
	@IColumn(description="登记注册号码")
	private String DJZCH;

	public String getDJZCH() {
		return DJZCH;
	}

	public void setDJZCH(String in) {
		DJZCH = in;
	}

	@Column(name = "NSRSBHGS", length = 50, nullable = true)
	@IColumn(description="纳税人识别号（国税）")
	private String NSRSBHGS;

	public String getNSRSBHGS() {
		return NSRSBHGS;
	}

	public void setNSRSBHGS(String in) {
		NSRSBHGS = in;
	}

	@Column(name = "NSRSBHDS", length = 50, nullable = true)
	@IColumn(description="纳税人识别号（地税）")
	private String NSRSBHDS;

	public String getNSRSBHDS() {
		return NSRSBHDS;
	}

	public void setNSRSBHDS(String in) {
		NSRSBHDS = in;
	}

	@Column(name = "KHXKZHZH", length = 50, nullable = true)
	@IColumn(description="开户许可证核准号")
	private String KHXKZHZH;

	public String getKHXKZHZH() {
		return KHXKZHZH;
	}

	public void setKHXKZHZH(String in) {
		KHXKZHZH = in;
	}

	@Column(name = "DKKBM", length = 50, nullable = true)
	@IColumn(description="中征码")
	private String DKKBM;

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String in) {
		DKKBM = in;
	}

	@Column(name = "XXGXRQ", length = 50, nullable = true)
	@IColumn(description="数据提取日期")
	private String XXGXRQ;

	public String getXXGXRQ() {
		return XXGXRQ;
	}

	public void setXXGXRQ(String in) {
		XXGXRQ = in;
	}

	@Column(name = "YLZD", length = 100, nullable = true)
	@IColumn(description="预留字段")
	private String YLZD;

	public String getYLZD() {
		return YLZD;
	}

	public void setYLZD(String in) {
		YLZD = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_JG_JBSX> OneToMany_JG_JBSX= new HashSet<zxptsystem.dto.AutoDTO_JG_JBSX>(0);

	public Set<zxptsystem.dto.AutoDTO_JG_JBSX> getOneToMany_JG_JBSX() {
		return OneToMany_JG_JBSX;
	}

	public void setOneToMany_JG_JBSX(Set<zxptsystem.dto.AutoDTO_JG_JBSX> in) {
		OneToMany_JG_JBSX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_JG_JGZT> OneToMany_JG_JGZT= new HashSet<zxptsystem.dto.AutoDTO_JG_JGZT>(0);

	public Set<zxptsystem.dto.AutoDTO_JG_JGZT> getOneToMany_JG_JGZT() {
		return OneToMany_JG_JGZT;
	}

	public void setOneToMany_JG_JGZT(Set<zxptsystem.dto.AutoDTO_JG_JGZT> in) {
		OneToMany_JG_JGZT = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_JG_LLXX> OneToMany_JG_LLXX= new HashSet<zxptsystem.dto.AutoDTO_JG_LLXX>(0);

	public Set<zxptsystem.dto.AutoDTO_JG_LLXX> getOneToMany_JG_LLXX() {
		return OneToMany_JG_LLXX;
	}

	public void setOneToMany_JG_LLXX(Set<zxptsystem.dto.AutoDTO_JG_LLXX> in) {
		OneToMany_JG_LLXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_JG_GGJZYGXR> OneToMany_JG_GGJZYGXR= new HashSet<zxptsystem.dto.AutoDTO_JG_GGJZYGXR>(0);

	public Set<zxptsystem.dto.AutoDTO_JG_GGJZYGXR> getOneToMany_JG_GGJZYGXR() {
		return OneToMany_JG_GGJZYGXR;
	}

	public void setOneToMany_JG_GGJZYGXR(Set<zxptsystem.dto.AutoDTO_JG_GGJZYGXR> in) {
		OneToMany_JG_GGJZYGXR = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_JG_ZYGD> OneToMany_JG_ZYGD= new HashSet<zxptsystem.dto.AutoDTO_JG_ZYGD>(0);

	public Set<zxptsystem.dto.AutoDTO_JG_ZYGD> getOneToMany_JG_ZYGD() {
		return OneToMany_JG_ZYGD;
	}

	public void setOneToMany_JG_ZYGD(Set<zxptsystem.dto.AutoDTO_JG_ZYGD> in) {
		OneToMany_JG_ZYGD = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_JG_ZYGLQY> OneToMany_JG_ZYGLQY= new HashSet<zxptsystem.dto.AutoDTO_JG_ZYGLQY>(0);

	public Set<zxptsystem.dto.AutoDTO_JG_ZYGLQY> getOneToMany_JG_ZYGLQY() {
		return OneToMany_JG_ZYGLQY;
	}

	public void setOneToMany_JG_ZYGLQY(Set<zxptsystem.dto.AutoDTO_JG_ZYGLQY> in) {
		OneToMany_JG_ZYGLQY = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_JG_SJJG> OneToMany_JG_SJJG= new HashSet<zxptsystem.dto.AutoDTO_JG_SJJG>(0);

	public Set<zxptsystem.dto.AutoDTO_JG_SJJG> getOneToMany_JG_SJJG() {
		return OneToMany_JG_SJJG;
	}

	public void setOneToMany_JG_SJJG(Set<zxptsystem.dto.AutoDTO_JG_SJJG> in) {
		OneToMany_JG_SJJG = in;
	}

	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	private String autoID;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String in) {
		autoID = in;
	}

}

