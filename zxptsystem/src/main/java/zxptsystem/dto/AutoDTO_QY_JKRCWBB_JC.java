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
import zxptsystem.dto.AutoDTO_QY_JKRCWBB_BS;
import zxptsystem.dto.AutoDTO_QY_02ZCFZB;
import zxptsystem.dto.AutoDTO_QY_02LRJLRFPB;
import zxptsystem.dto.AutoDTO_QY_02XJLLB;
import zxptsystem.dto.AutoDTO_QY_07ZCFZB;
import zxptsystem.dto.AutoDTO_QY_07LRJLRFPB;
import zxptsystem.dto.AutoDTO_QY_07XJLLB;
import zxptsystem.dto.AutoDTO_QY_SYDWZCFZB;
import zxptsystem.dto.AutoDTO_QY_SYDWSRZCB;

@Entity
@Table(name = "QY_JKRCWBB_JC")
@IEntity(description= "借款人财务报表基础段")
public class AutoDTO_QY_JKRCWBB_JC implements java.io.Serializable{

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

	@IColumn(tagMethodName="getXXJLLXTag",description="信息记录类型")
	@Column(name = "XXJLLX", nullable =true)
	private String XXJLLX;

	public static Map<String,String> getXXJLLXTag() {
		try {
			return HelpTool.getSystemConstVal("CWBB_XXJLLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void setXXJLLX(String xXJLLX) {
		XXJLLX = xXJLLX;
	}

	public String getXXJLLX() {
		return XXJLLX;
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

	@Column(name = "JKRMC", length = 200, nullable = true)
	@IColumn(description="借款人名称")
	private String JKRMC;

	public String getJKRMC() {
		return JKRMC;
	}

	public void setJKRMC(String in) {
		JKRMC = in;
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

	@Column(name = "BBNF", length = 50, nullable = true)
	@IColumn(description="报表年份")
	private String BBNF;

	public String getBBNF() {
		return BBNF;
	}

	public void setBBNF(String in) {
		BBNF = in;
	}

	@IColumn(tagMethodName="getBBLXTag",description="报表类型")
	@Column(name = "BBLX", nullable =true)
	private String BBLX;

	public static Map<String,String> getBBLXTag() {
		try {
			return HelpTool.getSystemConstVal("BBLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBBLX() {
		return BBLX;
	}

	public void setBBLX(String in) {
		BBLX = in;
	}

	@IColumn(tagMethodName="getBBLXXFTag",description="报表类型细分")
	@Column(name = "BBLXXF", nullable =true)
	private String BBLXXF;

	public static Map<String,String> getBBLXXFTag() {
		try {
			return HelpTool.getSystemConstVal("BBLXXF");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBBLXXF() {
		return BBLXXF;
	}

	public void setBBLXXF(String in) {
		BBLXXF = in;
	}

	@Column(name = "SJSWSMC", length = 200, nullable = true)
	@IColumn(description="审计事务所名称")
	private String SJSWSMC;

	public String getSJSWSMC() {
		return SJSWSMC;
	}

	public void setSJSWSMC(String in) {
		SJSWSMC = in;
	}

	@Column(name = "SJRYMC", length = 100, nullable = true)
	@IColumn(description="审计人员名称")
	private String SJRYMC;

	public String getSJRYMC() {
		return SJRYMC;
	}

	public void setSJRYMC(String in) {
		SJRYMC = in;
	}

	@Column(name = "SJSJ", length = 50, nullable = true)
	@IColumn(description="审计时间")
	private String SJSJ;

	public String getSJSJ() {
		return SJSJ;
	}

	public void setSJSJ(String in) {
		SJSJ = in;
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
	private Set<zxptsystem.dto.AutoDTO_QY_JKRCWBB_BS> OneToMany_QY_JKRCWBB_BS= new HashSet<zxptsystem.dto.AutoDTO_QY_JKRCWBB_BS>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_JKRCWBB_BS> getOneToMany_QY_JKRCWBB_BS() {
		return OneToMany_QY_JKRCWBB_BS;
	}

	public void setOneToMany_QY_JKRCWBB_BS(Set<zxptsystem.dto.AutoDTO_QY_JKRCWBB_BS> in) {
		OneToMany_QY_JKRCWBB_BS = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_02ZCFZB> OneToMany_QY_02ZCFZB= new HashSet<zxptsystem.dto.AutoDTO_QY_02ZCFZB>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_02ZCFZB> getOneToMany_QY_02ZCFZB() {
		return OneToMany_QY_02ZCFZB;
	}

	public void setOneToMany_QY_02ZCFZB(Set<zxptsystem.dto.AutoDTO_QY_02ZCFZB> in) {
		OneToMany_QY_02ZCFZB = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_02LRJLRFPB> OneToMany_QY_02LRJLRFPB= new HashSet<zxptsystem.dto.AutoDTO_QY_02LRJLRFPB>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_02LRJLRFPB> getOneToMany_QY_02LRJLRFPB() {
		return OneToMany_QY_02LRJLRFPB;
	}

	public void setOneToMany_QY_02LRJLRFPB(Set<zxptsystem.dto.AutoDTO_QY_02LRJLRFPB> in) {
		OneToMany_QY_02LRJLRFPB = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_02XJLLB> OneToMany_QY_02XJLLB= new HashSet<zxptsystem.dto.AutoDTO_QY_02XJLLB>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_02XJLLB> getOneToMany_QY_02XJLLB() {
		return OneToMany_QY_02XJLLB;
	}

	public void setOneToMany_QY_02XJLLB(Set<zxptsystem.dto.AutoDTO_QY_02XJLLB> in) {
		OneToMany_QY_02XJLLB = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_07ZCFZB> OneToMany_QY_07ZCFZB= new HashSet<zxptsystem.dto.AutoDTO_QY_07ZCFZB>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_07ZCFZB> getOneToMany_QY_07ZCFZB() {
		return OneToMany_QY_07ZCFZB;
	}

	public void setOneToMany_QY_07ZCFZB(Set<zxptsystem.dto.AutoDTO_QY_07ZCFZB> in) {
		OneToMany_QY_07ZCFZB = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_07LRJLRFPB> OneToMany_QY_07LRJLRFPB= new HashSet<zxptsystem.dto.AutoDTO_QY_07LRJLRFPB>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_07LRJLRFPB> getOneToMany_QY_07LRJLRFPB() {
		return OneToMany_QY_07LRJLRFPB;
	}

	public void setOneToMany_QY_07LRJLRFPB(Set<zxptsystem.dto.AutoDTO_QY_07LRJLRFPB> in) {
		OneToMany_QY_07LRJLRFPB = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_07XJLLB> OneToMany_QY_07XJLLB= new HashSet<zxptsystem.dto.AutoDTO_QY_07XJLLB>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_07XJLLB> getOneToMany_QY_07XJLLB() {
		return OneToMany_QY_07XJLLB;
	}

	public void setOneToMany_QY_07XJLLB(Set<zxptsystem.dto.AutoDTO_QY_07XJLLB> in) {
		OneToMany_QY_07XJLLB = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_SYDWZCFZB> OneToMany_QY_SYDWZCFZB= new HashSet<zxptsystem.dto.AutoDTO_QY_SYDWZCFZB>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_SYDWZCFZB> getOneToMany_QY_SYDWZCFZB() {
		return OneToMany_QY_SYDWZCFZB;
	}

	public void setOneToMany_QY_SYDWZCFZB(Set<zxptsystem.dto.AutoDTO_QY_SYDWZCFZB> in) {
		OneToMany_QY_SYDWZCFZB = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_QY_SYDWSRZCB> OneToMany_QY_SYDWSRZCB= new HashSet<zxptsystem.dto.AutoDTO_QY_SYDWSRZCB>(0);

	public Set<zxptsystem.dto.AutoDTO_QY_SYDWSRZCB> getOneToMany_QY_SYDWSRZCB() {
		return OneToMany_QY_SYDWSRZCB;
	}

	public void setOneToMany_QY_SYDWSRZCB(Set<zxptsystem.dto.AutoDTO_QY_SYDWSRZCB> in) {
		OneToMany_QY_SYDWSRZCB = in;
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

