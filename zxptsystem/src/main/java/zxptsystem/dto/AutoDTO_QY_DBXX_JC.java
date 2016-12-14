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
import zxptsystem.dto.AutoDTO_QY_DBXX_BS;
import zxptsystem.dto.AutoDTO_QY_DBHTXX;
import zxptsystem.dto.AutoDTO_QY_DYHTXX;
import zxptsystem.dto.AutoDTO_QY_ZYHTXX;
import zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX;
import zxptsystem.dto.AutoDTO_QY_ZRRDYHTXX;
import zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX;

@Entity
@Table(name = "QY_DBXX_JC")
@IEntity(description= "担保信息基础段")
public class AutoDTO_QY_DBXX_JC implements java.io.Serializable{

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

	@Column(name = "ZHTBH", length = 200, nullable = true)
	@IColumn(description="主合同编号")
	private String ZHTBH;

	public String getZHTBH() {
		return ZHTBH;
	}

	public void setZHTBH(String in) {
		ZHTBH = in;
	}

	@IColumn(tagMethodName="getXDYWZLTag",description="信贷业务种类")
	@Column(name = "XDYWZL", nullable =true)
	private String XDYWZL;

	public static Map<String,String> getXDYWZLTag() {
		try {
			return HelpTool.getSystemConstVal("XDYWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getXDYWZL() {
		return XDYWZL;
	}

	public void setXDYWZL(String in) {
		XDYWZL = in;
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
	private Set<zxptsystem.dto.AutoDTO_QY_DBXX_BS> OneToMany_QY_DBXX_BS= new HashSet<zxptsystem.dto.AutoDTO_QY_DBXX_BS>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_DBXX_BS> getOneToMany_QY_DBXX_BS() {
		return OneToMany_QY_DBXX_BS;
	}

	public void setOneToMany_QY_DBXX_BS(Set<zxptsystem.dto.AutoDTO_QY_DBXX_BS> in) {
		OneToMany_QY_DBXX_BS = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_DBHTXX> OneToMany_QY_DBHTXX= new HashSet<zxptsystem.dto.AutoDTO_QY_DBHTXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_DBHTXX> getOneToMany_QY_DBHTXX() {
		return OneToMany_QY_DBHTXX;
	}

	public void setOneToMany_QY_DBHTXX(Set<zxptsystem.dto.AutoDTO_QY_DBHTXX> in) {
		OneToMany_QY_DBHTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_DYHTXX> OneToMany_QY_DYHTXX= new HashSet<zxptsystem.dto.AutoDTO_QY_DYHTXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_DYHTXX> getOneToMany_QY_DYHTXX() {
		return OneToMany_QY_DYHTXX;
	}

	public void setOneToMany_QY_DYHTXX(Set<zxptsystem.dto.AutoDTO_QY_DYHTXX> in) {
		OneToMany_QY_DYHTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_ZYHTXX> OneToMany_QY_ZYHTXX= new HashSet<zxptsystem.dto.AutoDTO_QY_ZYHTXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_ZYHTXX> getOneToMany_QY_ZYHTXX() {
		return OneToMany_QY_ZYHTXX;
	}

	public void setOneToMany_QY_ZYHTXX(Set<zxptsystem.dto.AutoDTO_QY_ZYHTXX> in) {
		OneToMany_QY_ZYHTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX> OneToMany_QY_ZRRBZHTXX= new HashSet<zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX> getOneToMany_QY_ZRRBZHTXX() {
		return OneToMany_QY_ZRRBZHTXX;
	}

	public void setOneToMany_QY_ZRRBZHTXX(Set<zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX> in) {
		OneToMany_QY_ZRRBZHTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_ZRRDYHTXX> OneToMany_QY_ZRRDYHTXX= new HashSet<zxptsystem.dto.AutoDTO_QY_ZRRDYHTXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_ZRRDYHTXX> getOneToMany_QY_ZRRDYHTXX() {
		return OneToMany_QY_ZRRDYHTXX;
	}

	public void setOneToMany_QY_ZRRDYHTXX(Set<zxptsystem.dto.AutoDTO_QY_ZRRDYHTXX> in) {
		OneToMany_QY_ZRRDYHTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX> OneToMany_QY_ZRRZYHTXX= new HashSet<zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX> getOneToMany_QY_ZRRZYHTXX() {
		return OneToMany_QY_ZRRZYHTXX;
	}

	public void setOneToMany_QY_ZRRZYHTXX(Set<zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX> in) {
		OneToMany_QY_ZRRZYHTXX = in;
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

