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
import zxptsystem.dto.AutoDTO_QY_MYRZ_BS;
import zxptsystem.dto.AutoDTO_QY_RZXYXX;
import zxptsystem.dto.AutoDTO_QY_RZXYJEXX;
import zxptsystem.dto.AutoDTO_QY_RZYWXX;
import zxptsystem.dto.AutoDTO_QY_RZYWHKXX;
import zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX;

@Entity
@Table(name = "QY_MYRZ_JC")
@IEntity(description= "贸易融资业务信息基础段")
public class AutoDTO_QY_MYRZ_JC implements java.io.Serializable{

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

	@Column(name = "JRJGDM", length = 50, nullable = true)
	@IColumn(description="金融机构代码")
	private String JRJGDM;

	public String getJRJGDM() {
		return JRJGDM;
	}

	public void setJRJGDM(String in) {
		JRJGDM = in;
	}

	@Column(name = "DKKBM", length = 50, nullable = true)
	@IColumn(description="贷款卡编码")
	private String DKKBM;

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String in) {
		DKKBM = in;
	}

	@Column(name = "RZXYBH", length = 200, nullable = true)
	@IColumn(description="融资协议编号")
	private String RZXYBH;

	public String getRZXYBH() {
		return RZXYBH;
	}

	public void setRZXYBH(String in) {
		RZXYBH = in;
	}

	@IColumn(tagMethodName="getXXJLCZLXTag",description="信息记录操作类型")
	@Column(name = "XXJLCZLX", nullable =true)
	private String XXJLCZLX;

	public static Map<String,String> getXXJLCZLXTag() {
		try {
			return HelpTool.getSystemConstVal("XXJLCZLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getXXJLCZLX() {
		return XXJLCZLX;
	}

	public void setXXJLCZLX(String in) {
		XXJLCZLX = in;
	}

	@Column(name = "YWFSRQ", length = 50, nullable = true)
	@IColumn(description="业务发生日期")
	private String YWFSRQ;

	public String getYWFSRQ() {
		return YWFSRQ;
	}

	public void setYWFSRQ(String in) {
		YWFSRQ = in;
	}

	@Column(name = "XXJLGZBH", length = 50, nullable = true)
	@IColumn(description="信息记录跟踪编号")
	private String XXJLGZBH;

	public String getXXJLGZBH() {
		return XXJLGZBH;
	}

	public void setXXJLGZBH(String in) {
		XXJLGZBH = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_MYRZ_BS> OneToMany_QY_MYRZ_BS= new HashSet<zxptsystem.dto.AutoDTO_QY_MYRZ_BS>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_MYRZ_BS> getOneToMany_QY_MYRZ_BS() {
		return OneToMany_QY_MYRZ_BS;
	}

	public void setOneToMany_QY_MYRZ_BS(Set<zxptsystem.dto.AutoDTO_QY_MYRZ_BS> in) {
		OneToMany_QY_MYRZ_BS = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_RZXYXX> OneToMany_QY_RZXYXX= new HashSet<zxptsystem.dto.AutoDTO_QY_RZXYXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_RZXYXX> getOneToMany_QY_RZXYXX() {
		return OneToMany_QY_RZXYXX;
	}

	public void setOneToMany_QY_RZXYXX(Set<zxptsystem.dto.AutoDTO_QY_RZXYXX> in) {
		OneToMany_QY_RZXYXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_RZXYJEXX> OneToMany_QY_RZXYJEXX= new HashSet<zxptsystem.dto.AutoDTO_QY_RZXYJEXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_RZXYJEXX> getOneToMany_QY_RZXYJEXX() {
		return OneToMany_QY_RZXYJEXX;
	}

	public void setOneToMany_QY_RZXYJEXX(Set<zxptsystem.dto.AutoDTO_QY_RZXYJEXX> in) {
		OneToMany_QY_RZXYJEXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_RZYWXX> OneToMany_QY_RZYWXX= new HashSet<zxptsystem.dto.AutoDTO_QY_RZYWXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_RZYWXX> getOneToMany_QY_RZYWXX() {
		return OneToMany_QY_RZYWXX;
	}

	public void setOneToMany_QY_RZYWXX(Set<zxptsystem.dto.AutoDTO_QY_RZYWXX> in) {
		OneToMany_QY_RZYWXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_RZYWHKXX> OneToMany_QY_RZYWHKXX= new HashSet<zxptsystem.dto.AutoDTO_QY_RZYWHKXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_RZYWHKXX> getOneToMany_QY_RZYWHKXX() {
		return OneToMany_QY_RZYWHKXX;
	}

	public void setOneToMany_QY_RZYWHKXX(Set<zxptsystem.dto.AutoDTO_QY_RZYWHKXX> in) {
		OneToMany_QY_RZYWHKXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX> OneToMany_QY_MYRZ_ZQXX= new HashSet<zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX> getOneToMany_QY_MYRZ_ZQXX() {
		return OneToMany_QY_MYRZ_ZQXX;
	}

	public void setOneToMany_QY_MYRZ_ZQXX(Set<zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX> in) {
		OneToMany_QY_MYRZ_ZQXX = in;
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

