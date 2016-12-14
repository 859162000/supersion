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
import zxptsystem.dto.AutoDTO_GR_GRSFXX;
import zxptsystem.dto.AutoDTO_GR_ZYXX;
import zxptsystem.dto.AutoDTO_GR_JZDZ;
import zxptsystem.dto.AutoDTO_GR_DBXX;
import zxptsystem.dto.AutoDTO_GR_JYBSBG;
import zxptsystem.dto.AutoDTO_GR_TSJY;

@Entity
@Table(name = "GR_GRXX_JC")
@IEntity(description= "个人信息基础段")
public class AutoDTO_GR_GRXX_JC implements java.io.Serializable{

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
	@IColumn(description="金融机构代码（业务发生机构代码）")
	private String JRJGDM;

	public String getJRJGDM() {
		return JRJGDM;
	}

	public void setJRJGDM(String in) {
		JRJGDM = in;
	}

	@IColumn(tagMethodName="getYWZLTag",description="业务种类")
	@Column(name = "YWZL", nullable =true)
	private String YWZL;

	public static Map<String,String> getYWZLTag() {
		try {
			return HelpTool.getSystemConstVal("YWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getYWZL() {
		return YWZL;
	}

	public void setYWZL(String in) {
		YWZL = in;
	}

	@IColumn(tagMethodName="getYWZLXFTag",description="业务种类细分")
	@Column(name = "YWZLXF", nullable =true)
	private String YWZLXF;

	public static Map<String,String> getYWZLXFTag() {
		try {
			return HelpTool.getSystemConstVal("YWZLXF");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getYWZLXF() {
		return YWZLXF;
	}

	public void setYWZLXF(String in) {
		YWZLXF = in;
	}

	@Column(name = "YWH", length = 100, nullable = true)
	@IColumn(description="业务号")
	private String YWH;

	public String getYWH() {
		return YWH;
	}

	public void setYWH(String in) {
		YWH = in;
	}

	@IColumn(tagMethodName="getFSDDTag",description="发生地点")
	@Column(name = "FSDD", nullable =true)
	private String FSDD;

	public static Map<String,String> getFSDDTag() {
		try {
			return HelpTool.getSystemConstVal("XZQH");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getFSDD() {
		return FSDD;
	}

	public void setFSDD(String in) {
		FSDD = in;
	}

	@Column(name = "KHRQ", length = 50, nullable = true)
	@IColumn(description="开户日期")
	private String KHRQ;

	public String getKHRQ() {
		return KHRQ;
	}

	public void setKHRQ(String in) {
		KHRQ = in;
	}

	@Column(name = "DQRQ", length = 50, nullable = true)
	@IColumn(description="到期日期")
	private String DQRQ;

	public String getDQRQ() {
		return DQRQ;
	}

	public void setDQRQ(String in) {
		DQRQ = in;
	}

	@IColumn(tagMethodName="getBZTag",description="币种")
	@Column(name = "BZ", nullable =true)
	private String BZ;

	public static Map<String,String> getBZTag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String in) {
		BZ = in;
	}

	@Column(name = "SXED", length = 50, nullable = true)
	@IColumn(description="授信额度")
	private String SXED;

	public String getSXED() {
		return SXED;
	}

	public void setSXED(String in) {
		SXED = in;
	}

	@Column(name = "GXSXED", length = 50, nullable = true)
	@IColumn(description="共享授信额度")
	private String GXSXED;

	public String getGXSXED() {
		return GXSXED;
	}

	public void setGXSXED(String in) {
		GXSXED = in;
	}

	@Column(name = "ZDFZE", length = 50, nullable = true)
	@IColumn(description="最大负债额")
	private String ZDFZE;

	public String getZDFZE() {
		return ZDFZE;
	}

	public void setZDFZE(String in) {
		ZDFZE = in;
	}

	@IColumn(tagMethodName="getDBFSTag",description="担保方式")
	@Column(name = "DBFS", nullable =true)
	private String DBFS;

	public static Map<String,String> getDBFSTag() {
		try {
			return HelpTool.getSystemConstVal("DBFS");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDBFS() {
		return DBFS;
	}

	public void setDBFS(String in) {
		DBFS = in;
	}

	@IColumn(tagMethodName="getHKPLTag",description="还款频率")
	@Column(name = "HKPL", nullable =true)
	private String HKPL;

	public static Map<String,String> getHKPLTag() {
		try {
			return HelpTool.getSystemConstVal("HKPL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getHKPL() {
		return HKPL;
	}

	public void setHKPL(String in) {
		HKPL = in;
	}

	@Column(name = "HKYS", length = 50, nullable = true)
	@IColumn(description="还款月数")
	private String HKYS;

	public String getHKYS() {
		return HKYS;
	}

	public void setHKYS(String in) {
		HKYS = in;
	}

	@Column(name = "SYHKYS", length = 50, nullable = true)
	@IColumn(description="剩余还款月数")
	private String SYHKYS;

	public String getSYHKYS() {
		return SYHKYS;
	}

	public void setSYHKYS(String in) {
		SYHKYS = in;
	}

	@Column(name = "JSYHKRQ", length = 50, nullable = true)
	@IColumn(description="结算/应还款日期")
	private String JSYHKRQ;

	public String getJSYHKRQ() {
		return JSYHKRQ;
	}

	public void setJSYHKRQ(String in) {
		JSYHKRQ = in;
	}

	@Column(name = "ZHYCSJHKRQ", length = 50, nullable = true)
	@IColumn(description="最近一次实际还款日期")
	private String ZHYCSJHKRQ;

	public String getZHYCSJHKRQ() {
		return ZHYCSJHKRQ;
	}

	public void setZHYCSJHKRQ(String in) {
		ZHYCSJHKRQ = in;
	}

	@Column(name = "BYYHKJE", length = 50, nullable = true)
	@IColumn(description="本月应还款金额")
	private String BYYHKJE;

	public String getBYYHKJE() {
		return BYYHKJE;
	}

	public void setBYYHKJE(String in) {
		BYYHKJE = in;
	}

	@Column(name = "BYSJHKJE", length = 50, nullable = true)
	@IColumn(description="本月实际还款金额")
	private String BYSJHKJE;

	public String getBYSJHKJE() {
		return BYSJHKJE;
	}

	public void setBYSJHKJE(String in) {
		BYSJHKJE = in;
	}

	@Column(name = "YE", length = 50, nullable = true)
	@IColumn(description="余额")
	private String YE;

	public String getYE() {
		return YE;
	}

	public void setYE(String in) {
		YE = in;
	}

	@Column(name = "DQYQQS", length = 50, nullable = true)
	@IColumn(description="当前逾期期数")
	private String DQYQQS;

	public String getDQYQQS() {
		return DQYQQS;
	}

	public void setDQYQQS(String in) {
		DQYQQS = in;
	}

	@Column(name = "DQYQZE", length = 50, nullable = true)
	@IColumn(description="当前逾期总额")
	private String DQYQZE;

	public String getDQYQZE() {
		return DQYQZE;
	}

	public void setDQYQZE(String in) {
		DQYQZE = in;
	}

	@Column(name = "YQ31_60HDKBJ", length = 50, nullable = true)
	@IColumn(description="逾期31-60天未归还贷款本金")
	private String YQ31_60HDKBJ;

	public String getYQ31_60HDKBJ() {
		return YQ31_60HDKBJ;
	}

	public void setYQ31_60HDKBJ(String in) {
		YQ31_60HDKBJ = in;
	}

	@Column(name = "YQ61_90HDKBJ", length = 50, nullable = true)
	@IColumn(description="逾期61-90天未归还贷款本金")
	private String YQ61_90HDKBJ;

	public String getYQ61_90HDKBJ() {
		return YQ61_90HDKBJ;
	}

	public void setYQ61_90HDKBJ(String in) {
		YQ61_90HDKBJ = in;
	}

	@Column(name = "YQ91_180HDKBJ", length = 50, nullable = true)
	@IColumn(description="逾期91-180天未归还贷款本金")
	private String YQ91_180HDKBJ;

	public String getYQ91_180HDKBJ() {
		return YQ91_180HDKBJ;
	}

	public void setYQ91_180HDKBJ(String in) {
		YQ91_180HDKBJ = in;
	}

	@Column(name = "YQ180YSHDKBJ", length = 50, nullable = true)
	@IColumn(description="逾期180天以上未归还贷款本金")
	private String YQ180YSHDKBJ;

	public String getYQ180YSHDKBJ() {
		return YQ180YSHDKBJ;
	}

	public void setYQ180YSHDKBJ(String in) {
		YQ180YSHDKBJ = in;
	}

	@Column(name = "LJYQQS", length = 50, nullable = true)
	@IColumn(description="累计逾期期数")
	private String LJYQQS;

	public String getLJYQQS() {
		return LJYQQS;
	}

	public void setLJYQQS(String in) {
		LJYQQS = in;
	}

	@Column(name = "ZGYQQS", length = 50, nullable = true)
	@IColumn(description="最高逾期期数")
	private String ZGYQQS;

	public String getZGYQQS() {
		return ZGYQQS;
	}

	public void setZGYQQS(String in) {
		ZGYQQS = in;
	}

	@IColumn(tagMethodName="getWJFLZTTag",description="五级分类状态")
	@Column(name = "WJFLZT", nullable =true)
	private String WJFLZT;

	public static Map<String,String> getWJFLZTTag() {
		try {
			return HelpTool.getSystemConstVal("WJFLZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getWJFLZT() {
		return WJFLZT;
	}

	public void setWJFLZT(String in) {
		WJFLZT = in;
	}

	@IColumn(tagMethodName="getZHZTTag",description="账户状态")
	@Column(name = "ZHZT", nullable =true)
	private String ZHZT;

	public static Map<String,String> getZHZTTag() {
		try {
			return HelpTool.getSystemConstVal("DK_ZHZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZHZT() {
		return ZHZT;
	}

	public void setZHZT(String in) {
		ZHZT = in;
	}

	@Column(name = "HKZT", length = 50, nullable = true)
	@IColumn(description="24个月（账户）还款状态",isSingleTagHidden=true)
	private String HKZT;

	public String getHKZT() {
		return HKZT;
	}

	public void setHKZT(String in) {
		HKZT = in;
	}

	@IColumn(tagMethodName="getHKZT_24Tag",description="24个月（账户）还款状态")
	@Column(name = "HKZT_24", nullable =true)
	private String HKZT_24;

	public static Map<String,String> getHKZT_24Tag() {
		try {
			return HelpTool.getSystemConstVal("HKZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getHKZT_24() {
		return HKZT_24;
	}

	public void setHKZT_24(String in) {
		HKZT_24 = in;
	}

	@Column(name = "WZFYE", length = 50, nullable = true)
	@IColumn(description="透支180天以上未付余额")
	private String WZFYE;

	public String getWZFYE() {
		return WZFYE;
	}

	public void setWZFYE(String in) {
		WZFYE = in;
	}

	@IColumn(tagMethodName="getZHYYZXXTSTag",description="账户拥有者信息提示")
	@Column(name = "ZHYYZXXTS", nullable =true)
	private String ZHYYZXXTS;

	public static Map<String,String> getZHYYZXXTSTag() {
		try {
			return HelpTool.getSystemConstVal("ZHYYZXXTS");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZHYYZXXTS() {
		return ZHYYZXXTS;
	}

	public void setZHYYZXXTS(String in) {
		ZHYYZXXTS = in;
	}

	@IColumn(tagMethodName="getZJLXTag",description="证件类型")
	@Column(name = "ZJLX", nullable =true)
	private String ZJLX;

	public static Map<String,String> getZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZJLX() {
		return ZJLX;
	}

	public void setZJLX(String in) {
		ZJLX = in;
	}

	@Column(name = "ZJHM", length = 50, nullable = true)
	@IColumn(description="证件号码")
	private String ZJHM;

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String in) {
		ZJHM = in;
	}

	@Column(name = "XM", length = 100, nullable = true)
	@IColumn(description="姓名")
	private String XM;

	public String getXM() {
		return XM;
	}

	public void setXM(String in) {
		XM = in;
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
	private Set<zxptsystem.dto.AutoDTO_GR_GRSFXX> OneToMany_GR_GRSFXX= new HashSet<zxptsystem.dto.AutoDTO_GR_GRSFXX>(0);

	public Set<zxptsystem.dto.AutoDTO_GR_GRSFXX> getOneToMany_GR_GRSFXX() {
		return OneToMany_GR_GRSFXX;
	}

	public void setOneToMany_GR_GRSFXX(Set<zxptsystem.dto.AutoDTO_GR_GRSFXX> in) {
		OneToMany_GR_GRSFXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_GR_ZYXX> OneToMany_GR_ZYXX= new HashSet<zxptsystem.dto.AutoDTO_GR_ZYXX>(0);

	public Set<zxptsystem.dto.AutoDTO_GR_ZYXX> getOneToMany_GR_ZYXX() {
		return OneToMany_GR_ZYXX;
	}

	public void setOneToMany_GR_ZYXX(Set<zxptsystem.dto.AutoDTO_GR_ZYXX> in) {
		OneToMany_GR_ZYXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_GR_JZDZ> OneToMany_GR_JZDZ= new HashSet<zxptsystem.dto.AutoDTO_GR_JZDZ>(0);

	public Set<zxptsystem.dto.AutoDTO_GR_JZDZ> getOneToMany_GR_JZDZ() {
		return OneToMany_GR_JZDZ;
	}

	public void setOneToMany_GR_JZDZ(Set<zxptsystem.dto.AutoDTO_GR_JZDZ> in) {
		OneToMany_GR_JZDZ = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_GR_DBXX> OneToMany_GR_DBXX= new HashSet<zxptsystem.dto.AutoDTO_GR_DBXX>(0);

	public Set<zxptsystem.dto.AutoDTO_GR_DBXX> getOneToMany_GR_DBXX() {
		return OneToMany_GR_DBXX;
	}

	public void setOneToMany_GR_DBXX(Set<zxptsystem.dto.AutoDTO_GR_DBXX> in) {
		OneToMany_GR_DBXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_GR_JYBSBG> OneToMany_GR_JYBSBG= new HashSet<zxptsystem.dto.AutoDTO_GR_JYBSBG>(0);

	public Set<zxptsystem.dto.AutoDTO_GR_JYBSBG> getOneToMany_GR_JYBSBG() {
		return OneToMany_GR_JYBSBG;
	}

	public void setOneToMany_GR_JYBSBG(Set<zxptsystem.dto.AutoDTO_GR_JYBSBG> in) {
		OneToMany_GR_JYBSBG = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<zxptsystem.dto.AutoDTO_GR_TSJY> OneToMany_GR_TSJY= new HashSet<zxptsystem.dto.AutoDTO_GR_TSJY>(0);

	public Set<zxptsystem.dto.AutoDTO_GR_TSJY> getOneToMany_GR_TSJY() {
		return OneToMany_GR_TSJY;
	}

	public void setOneToMany_GR_TSJY(Set<zxptsystem.dto.AutoDTO_GR_TSJY> in) {
		OneToMany_GR_TSJY = in;
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

