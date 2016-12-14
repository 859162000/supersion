package eas.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import framework.interfaces.IColumn;
import framework.show.ShowContext;
import framework.helper.TypeParse;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.TemporalType;

import framework.interfaces.IEntity;

@Entity
@Table(name = "EAS_DGKH")
@IEntity(description= "对公客户")
public class AutoDTO_EAS_DGKH implements java.io.Serializable{

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

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态")
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

	@IColumn(tagMethodName="getRPTSubmitStatusTag",description="提交状态")
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

	@IColumn(tagMethodName="getRPTVerifyTypeTag",description="审核状态")
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
	@IColumn(description="最后修改时间", isNullable = false)
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

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态")
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

	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态")
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
	@IColumn(description="数据时间", isNullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	@Column(name = "KHTYBH", length = 299, nullable = true)
	@IColumn(description="客户统一编号")
	private String KHTYBH;

	public String getKHTYBH() {
		return KHTYBH;
	}

	public void setKHTYBH(String in) {
		KHTYBH = in;
	}

	@Column(name = "ZZJGDM", length = 299, nullable = true)
	@IColumn(description="组织机构代码")
	private String ZZJGDM;

	public String getZZJGDM() {
		return ZZJGDM;
	}

	public void setZZJGDM(String in) {
		ZZJGDM = in;
	}

	@Column(name = "YXJGDM", length = 299, nullable = true)
	@IColumn(description="银行机构代码")
	private String YXJGDM;

	public String getYXJGDM() {
		return YXJGDM;
	}

	public void setYXJGDM(String in) {
		YXJGDM = in;
	}

	@Column(name = "JRXKZH", length = 299, nullable = true)
	@IColumn(description="金融许可证号")
	private String JRXKZH;

	public String getJRXKZH() {
		return JRXKZH;
	}

	public void setJRXKZH(String in) {
		JRXKZH = in;
	}

	@Column(name = "NBJGH", length = 299, nullable = true)
	@IColumn(description="内部机构号")
	private String NBJGH;

	public String getNBJGH() {
		return NBJGH;
	}

	public void setNBJGH(String in) {
		NBJGH = in;
	}

	@Column(name = "KHMC", length = 299, nullable = true)
	@IColumn(description="客户名称")
	private String KHMC;

	public String getKHMC() {
		return KHMC;
	}

	public void setKHMC(String in) {
		KHMC = in;
	}

	@Column(name = "KHYWMC", length = 299, nullable = true)
	@IColumn(description="客户英文名称")
	private String KHYWMC;

	public String getKHYWMC() {
		return KHYWMC;
	}

	public void setKHYWMC(String in) {
		KHYWMC = in;
	}

	@Column(name = "FRDB", length = 299, nullable = true)
	@IColumn(description="法人代表")
	private String FRDB;

	public String getFRDB() {
		return FRDB;
	}

	public void setFRDB(String in) {
		FRDB = in;
	}

	@IColumn(tagMethodName="getFRDBZJLBTag",description="法人代表证件类别")
	@Column(name = "FRDBZJLB", nullable =true)
	private String FRDBZJLB;

	public static Map<String,String> getFRDBZJLBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LEGAL_REP_CERT_CATE");
	}

	public String getFRDBZJLB() {
		return FRDBZJLB;
	}

	public void setFRDBZJLB(String in) {
		FRDBZJLB = in;
	}

	@Column(name = "FRDBZJHM", length = 299, nullable = true)
	@IColumn(description="法人代表证件号码")
	private String FRDBZJHM;

	public String getFRDBZJHM() {
		return FRDBZJHM;
	}

	public void setFRDBZJHM(String in) {
		FRDBZJHM = in;
	}

	@Column(name = "CWRY", length = 299, nullable = true)
	@IColumn(description="财务人员")
	private String CWRY;

	public String getCWRY() {
		return CWRY;
	}

	public void setCWRY(String in) {
		CWRY = in;
	}

	@Column(name = "CWRYZJLB", length = 299, nullable = true)
	@IColumn(description="财务人员证件类别")
	private String CWRYZJLB;

	public String getCWRYZJLB() {
		return CWRYZJLB;
	}

	public void setCWRYZJLB(String in) {
		CWRYZJLB = in;
	}

	@Column(name = "CWRYZJHM", length = 299, nullable = true)
	@IColumn(description="财务人员证件号码")
	private String CWRYZJHM;

	public String getCWRYZJHM() {
		return CWRYZJHM;
	}

	public void setCWRYZJHM(String in) {
		CWRYZJHM = in;
	}

	@Column(name = "JBCKZH", length = 299, nullable = true)
	@IColumn(description="基本存款账号")
	private String JBCKZH;

	public String getJBCKZH() {
		return JBCKZH;
	}

	public void setJBCKZH(String in) {
		JBCKZH = in;
	}

	@Column(name = "JBZHKHXMC", length = 299, nullable = true)
	@IColumn(description="基本账户开户行名称")
	private String JBZHKHXMC;

	public String getJBZHKHXMC() {
		return JBZHKHXMC;
	}

	public void setJBZHKHXMC(String in) {
		JBZHKHXMC = in;
	}

	@Column(name = "ZCZB", length = 20, nullable = true)
	@IColumn(description="注册资本")
	private BigDecimal ZCZB;

	public BigDecimal getZCZB() {
		return ZCZB;
	}

	public void setZCZB(String in) {
		ZCZB = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZCDZ", length = 500, nullable = true)
	@IColumn(description="注册地址")
	private String ZCDZ;

	public String getZCDZ() {
		return ZCDZ;
	}

	public void setZCDZ(String in) {
		ZCDZ = in;
	}

	@Column(name = "LXDH", length = 299, nullable = true)
	@IColumn(description="联系电话")
	private String LXDH;

	public String getLXDH() {
		return LXDH;
	}

	public void setLXDH(String in) {
		LXDH = in;
	}

	@Column(name = "YYZZH", length = 299, nullable = true)
	@IColumn(description="营业执照号")
	private String YYZZH;

	public String getYYZZH() {
		return YYZZH;
	}

	public void setYYZZH(String in) {
		YYZZH = in;
	}

	@Column(name = "YYZZYXJZRQ", length = 8, nullable = true)
	@IColumn(description="营业执照有效截止日期")
	private String YYZZYXJZRQ;

	public String getYYZZYXJZRQ() {
		return YYZZYXJZRQ;
	}

	public void setYYZZYXJZRQ(String in) {
		YYZZYXJZRQ = in;
	}

	@Column(name = "JYFW", length = 299, nullable = true)
	@IColumn(description="经营范围")
	private String JYFW;

	public String getJYFW() {
		return JYFW;
	}

	public void setJYFW(String in) {
		JYFW = in;
	}

	@Column(name = "CLRQ", length = 8, nullable = true)
	@IColumn(description="成立日期")
	private String CLRQ;

	public String getCLRQ() {
		return CLRQ;
	}

	public void setCLRQ(String in) {
		CLRQ = in;
	}

	@Column(name = "SSXY", length = 299, nullable = true)
	@IColumn(description="所属行业")
	private String SSXY;

	public String getSSXY() {
		return SSXY;
	}

	public void setSSXY(String in) {
		SSXY = in;
	}

	@IColumn(tagMethodName="getKHLBTag",description="客户类别")
	@Column(name = "KHLB", nullable =true)
	private String KHLB;

	public static Map<String,String> getKHLBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CUST_CATE");
	}

	public String getKHLB() {
		return KHLB;
	}

	public void setKHLB(String in) {
		KHLB = in;
	}

	@Column(name = "DKZH", length = 299, nullable = true)
	@IColumn(description="贷款证号")
	private String DKZH;

	public String getDKZH() {
		return DKZH;
	}

	public void setDKZH(String in) {
		DKZH = in;
	}

	@Column(name = "GSZH", length = 299, nullable = true)
	@IColumn(description="国税证号")
	private String GSZH;

	public String getGSZH() {
		return GSZH;
	}

	public void setGSZH(String in) {
		GSZH = in;
	}

	@Column(name = "DSZH", length = 299, nullable = true)
	@IColumn(description="地税证号")
	private String DSZH;

	public String getDSZH() {
		return DSZH;
	}

	public void setDSZH(String in) {
		DSZH = in;
	}

	@Column(name = "MGSKHTYBH", length = 299, nullable = true)
	@IColumn(description="母公司客户统一编号")
	private String MGSKHTYBH;

	public String getMGSKHTYBH() {
		return MGSKHTYBH;
	}

	public void setMGSKHTYBH(String in) {
		MGSKHTYBH = in;
	}

	@IColumn(tagMethodName="getTYSXBZTag",description="统一授信标志")
	@Column(name = "TYSXBZ", nullable =true)
	private String TYSXBZ;

	public static Map<String,String> getTYSXBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_COMM_CRDT_IND");
	}

	public String getTYSXBZ() {
		return TYSXBZ;
	}

	public void setTYSXBZ(String in) {
		TYSXBZ = in;
	}

	@Column(name = "SXED", length = 20, nullable = true)
	@IColumn(description="授信额度")
	private BigDecimal SXED;

	public BigDecimal getSXED() {
		return SXED;
	}

	public void setSXED(String in) {
		SXED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "YYED", length = 20, nullable = true)
	@IColumn(description="已用额度")
	private BigDecimal YYED;

	public BigDecimal getYYED() {
		return YYED;
	}

	public void setYYED(String in) {
		YYED = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getSSGSBZTag",description="上市公司标志")
	@Column(name = "SSGSBZ", nullable =true)
	private String SSGSBZ;

	public static Map<String,String> getSSGSBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LIST_CORP_IND");
	}

	public String getSSGSBZ() {
		return SSGSBZ;
	}

	public void setSSGSBZ(String in) {
		SSGSBZ = in;
	}

	@Column(name = "XYDJBH", length = 299, nullable = true)
	@IColumn(description="信用评级结果")
	private String XYDJBH;

	public String getXYDJBH() {
		return XYDJBH;
	}

	public void setXYDJBH(String in) {
		XYDJBH = in;
	}

	@Column(name = "ZCZBBZ", length = 299, nullable = true)
	@IColumn(description="注册资本币种")
	private String ZCZBBZ;

	public String getZCZBBZ() {
		return ZCZBBZ;
	}

	public void setZCZBBZ(String in) {
		ZCZBBZ = in;
	}

	@Column(name = "SSZBBZ", length = 299, nullable = true)
	@IColumn(description="实收资本币种")
	private String SSZBBZ;

	public String getSSZBBZ() {
		return SSZBBZ;
	}

	public void setSSZBBZ(String in) {
		SSZBBZ = in;
	}

	@Column(name = "SSZB", length = 20, nullable = true)
	@IColumn(description="实收资本")
	private BigDecimal SSZB;

	public BigDecimal getSSZB() {
		return SSZB;
	}

	public void setSSZB(String in) {
		SSZB = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZZC", length = 20, nullable = true)
	@IColumn(description="总资产")
	private BigDecimal ZZC;

	public BigDecimal getZZC() {
		return ZZC;
	}

	public void setZZC(String in) {
		ZZC = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JZC", length = 20, nullable = true)
	@IColumn(description="净资产")
	private BigDecimal JZC;

	public BigDecimal getJZC() {
		return JZC;
	}

	public void setJZC(String in) {
		JZC = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "NSR", length = 20, nullable = true)
	@IColumn(description="年收入")
	private BigDecimal NSR;

	public BigDecimal getNSR() {
		return NSR;
	}

	public void setNSR(String in) {
		NSR = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SCJLXDGXNY", length = 299, nullable = true)
	@IColumn(description="首次建立信贷关系年月")
	private String SCJLXDGXNY;

	public String getSCJLXDGXNY() {
		return SCJLXDGXNY;
	}

	public void setSCJLXDGXNY(String in) {
		SCJLXDGXNY = in;
	}

	@Column(name = "YZBM", length = 299, nullable = true)
	@IColumn(description="邮政编码")
	private String YZBM;

	public String getYZBM() {
		return YZBM;
	}

	public void setYZBM(String in) {
		YZBM = in;
	}

	@Column(name = "CZHM", length = 299, nullable = true)
	@IColumn(description="传真号码")
	private String CZHM;

	public String getCZHM() {
		return CZHM;
	}

	public void setCZHM(String in) {
		CZHM = in;
	}

	@Column(name = "YGRS", nullable = true)
	@IColumn(description="员工人数")
	private Integer YGRS;

	public Integer getYGRS() {
		return YGRS;
	}

	public void setYGRS(String in) {
		YGRS = TypeParse.parseInt(in);
	}

	@Column(name = "XZQHDM", length = 299, nullable = true)
	@IColumn(description="行政区划代码")
	private String XZQHDM;

	public String getXZQHDM() {
		return XZQHDM;
	}

	public void setXZQHDM(String in) {
		XZQHDM = in;
	}

	@IColumn(tagMethodName="getKHLXTag",description="客户类型")
	@Column(name = "KHLX", nullable =true)
	private String KHLX;

	public static Map<String,String> getKHLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CUST_TYPE");
	}

	public String getKHLX() {
		return KHLX;
	}

	public void setKHLX(String in) {
		KHLX = in;
	}

	@IColumn(tagMethodName="getFXYJXHTag",description="风险预警信号")
	@Column(name = "FXYJXH", nullable =true)
	private String FXYJXH;

	public static Map<String,String> getFXYJXHTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_RISK_WARN_LETTER_NUM");
	}

	public String getFXYJXH() {
		return FXYJXH;
	}

	public void setFXYJXH(String in) {
		FXYJXH = in;
	}

	@Column(name = "CJRQ", length = 8, nullable = true)
	@IColumn(description="采集日期")
	private String CJRQ;

	public String getCJRQ() {
		return CJRQ;
	}

	public void setCJRQ(String in) {
		CJRQ = in;
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

