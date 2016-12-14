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
@Table(name = "EAS_LCCPXXB")
@IEntity(description= "理财产品信息表")
public class AutoDTO_EAS_LCCPXXB implements java.io.Serializable{

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

	@Column(name = "MXKMBH", length = 299, nullable = true)
	@IColumn(description="明细科目编号")
	private String MXKMBH;

	public String getMXKMBH() {
		return MXKMBH;
	}

	public void setMXKMBH(String in) {
		MXKMBH = in;
	}

	@Column(name = "YXJGMC", length = 299, nullable = true)
	@IColumn(description="银行机构名称")
	private String YXJGMC;

	public String getYXJGMC() {
		return YXJGMC;
	}

	public void setYXJGMC(String in) {
		YXJGMC = in;
	}

	@Column(name = "MXKMMC", length = 299, nullable = true)
	@IColumn(description="明细科目名称")
	private String MXKMMC;

	public String getMXKMMC() {
		return MXKMMC;
	}

	public void setMXKMMC(String in) {
		MXKMMC = in;
	}

	@Column(name = "CPMC", length = 299, nullable = true)
	@IColumn(description="产品名称")
	private String CPMC;

	public String getCPMC() {
		return CPMC;
	}

	public void setCPMC(String in) {
		CPMC = in;
	}

	@Column(name = "CPDJBM", length = 299, nullable = true)
	@IColumn(description="产品登记编码")
	private String CPDJBM;

	public String getCPDJBM() {
		return CPDJBM;
	}

	public void setCPDJBM(String in) {
		CPDJBM = in;
	}

	@Column(name = "HNBSM", length = 299, nullable = true)
	@IColumn(description="行内标识码")
	private String HNBSM;

	public String getHNBSM() {
		return HNBSM;
	}

	public void setHNBSM(String in) {
		HNBSM = in;
	}

	@Column(name = "CPPP", length = 299, nullable = true)
	@IColumn(description="产品品牌")
	private String CPPP;

	public String getCPPP() {
		return CPPP;
	}

	public void setCPPP(String in) {
		CPPP = in;
	}

	@Column(name = "CPQC", length = 299, nullable = true)
	@IColumn(description="产品期次")
	private String CPQC;

	public String getCPQC() {
		return CPQC;
	}

	public void setCPQC(String in) {
		CPQC = in;
	}

	@Column(name = "CPSPRSFZH", length = 299, nullable = true)
	@IColumn(description="产品审批人身份证号")
	private String CPSPRSFZH;

	public String getCPSPRSFZH() {
		return CPSPRSFZH;
	}

	public void setCPSPRSFZH(String in) {
		CPSPRSFZH = in;
	}

	@Column(name = "CPSPRXM", length = 299, nullable = true)
	@IColumn(description="产品审批人姓名")
	private String CPSPRXM;

	public String getCPSPRXM() {
		return CPSPRXM;
	}

	public void setCPSPRXM(String in) {
		CPSPRXM = in;
	}

	@Column(name = "CPSJRSFZH", length = 299, nullable = true)
	@IColumn(description="产品设计人身份证号")
	private String CPSJRSFZH;

	public String getCPSJRSFZH() {
		return CPSJRSFZH;
	}

	public void setCPSJRSFZH(String in) {
		CPSJRSFZH = in;
	}

	@Column(name = "CPSJRXM", length = 299, nullable = true)
	@IColumn(description="产品设计人姓名")
	private String CPSJRXM;

	public String getCPSJRXM() {
		return CPSJRXM;
	}

	public void setCPSJRXM(String in) {
		CPSJRXM = in;
	}

	@Column(name = "TZJLSFZH", length = 299, nullable = true)
	@IColumn(description="投资经理身份证号")
	private String TZJLSFZH;

	public String getTZJLSFZH() {
		return TZJLSFZH;
	}

	public void setTZJLSFZH(String in) {
		TZJLSFZH = in;
	}

	@Column(name = "TZJLXM", length = 299, nullable = true)
	@IColumn(description="投资经理姓名")
	private String TZJLXM;

	public String getTZJLXM() {
		return TZJLXM;
	}

	public void setTZJLXM(String in) {
		TZJLXM = in;
	}

	@Column(name = "CPSYLX", length = 299, nullable = true)
	@IColumn(description="产品收益类型")
	private String CPSYLX;

	public String getCPSYLX() {
		return CPSYLX;
	}

	public void setCPSYLX(String in) {
		CPSYLX = in;
	}

	@Column(name = "CPQX", length = 299, nullable = true)
	@IColumn(description="产品期限")
	private String CPQX;

	public String getCPQX() {
		return CPQX;
	}

	public void setCPQX(String in) {
		CPQX = in;
	}

	@Column(name = "TZZLX", length = 299, nullable = true)
	@IColumn(description="投资者类型")
	private String TZZLX;

	public String getTZZLX() {
		return TZZLX;
	}

	public void setTZZLX(String in) {
		TZZLX = in;
	}

	@Column(name = "ZJTXDQ", length = 299, nullable = true)
	@IColumn(description="资金投向地区")
	private String ZJTXDQ;

	public String getZJTXDQ() {
		return ZJTXDQ;
	}

	public void setZJTXDQ(String in) {
		ZJTXDQ = in;
	}

	@Column(name = "CPTZGJHDQ", length = 299, nullable = true)
	@IColumn(description="产品投资国家或地区")
	private String CPTZGJHDQ;

	public String getCPTZGJHDQ() {
		return CPTZGJHDQ;
	}

	public void setCPTZGJHDQ(String in) {
		CPTZGJHDQ = in;
	}

	@Column(name = "LCYWFWMS", length = 299, nullable = true)
	@IColumn(description="理财业务服务模式")
	private String LCYWFWMS;

	public String getLCYWFWMS() {
		return LCYWFWMS;
	}

	public void setLCYWFWMS(String in) {
		LCYWFWMS = in;
	}

	@Column(name = "CPYZMS", length = 299, nullable = true)
	@IColumn(description="产品运作模式")
	private String CPYZMS;

	public String getCPYZMS() {
		return CPYZMS;
	}

	public void setCPYZMS(String in) {
		CPYZMS = in;
	}

	@Column(name = "KJHSFS", length = 299, nullable = true)
	@IColumn(description="会计核算方式")
	private String KJHSFS;

	public String getKJHSFS() {
		return KJHSFS;
	}

	public void setKJHSFS(String in) {
		KJHSFS = in;
	}

	@Column(name = "CPZCPZFS", length = 299, nullable = true)
	@IColumn(description="产品资产配置方式")
	private String CPZCPZFS;

	public String getCPZCPZFS() {
		return CPZCPZFS;
	}

	public void setCPZCPZFS(String in) {
		CPZCPZFS = in;
	}

	@Column(name = "CPGLMS", length = 299, nullable = true)
	@IColumn(description="产品管理模式")
	private String CPGLMS;

	public String getCPGLMS() {
		return CPGLMS;
	}

	public void setCPGLMS(String in) {
		CPGLMS = in;
	}

	@Column(name = "SJGLLMC", length = 299, nullable = true)
	@IColumn(description="实际管理人名称")
	private String SJGLLMC;

	public String getSJGLLMC() {
		return SJGLLMC;
	}

	public void setSJGLLMC(String in) {
		SJGLLMC = in;
	}

	@Column(name = "CPDJFS", length = 299, nullable = true)
	@IColumn(description="产品定价方式")
	private String CPDJFS;

	public String getCPDJFS() {
		return CPDJFS;
	}

	public void setCPDJFS(String in) {
		CPDJFS = in;
	}

	@Column(name = "TZLX", length = 299, nullable = true)
	@IColumn(description="投资类型")
	private String TZLX;

	public String getTZLX() {
		return TZLX;
	}

	public void setTZLX(String in) {
		TZLX = in;
	}

	@Column(name = "HZMS", length = 299, nullable = true)
	@IColumn(description="合作模式")
	private String HZMS;

	public String getHZMS() {
		return HZMS;
	}

	public void setHZMS(String in) {
		HZMS = in;
	}

	@Column(name = "HZJGMC", length = 299, nullable = true)
	@IColumn(description="合作机构名称")
	private String HZJGMC;

	public String getHZJGMC() {
		return HZJGMC;
	}

	public void setHZJGMC(String in) {
		HZJGMC = in;
	}

	@Column(name = "TZZCZLJBL", length = 299, nullable = true)
	@IColumn(description="投资资产种类及比例")
	private String TZZCZLJBL;

	public String getTZZCZLJBL() {
		return TZZCZLJBL;
	}

	public void setTZZCZLJBL(String in) {
		TZZCZLJBL = in;
	}

	@Column(name = "SFYYQSYL", length = 299, nullable = true)
	@IColumn(description="是否有预期收益率")
	private String SFYYQSYL;

	public String getSFYYQSYL() {
		return SFYYQSYL;
	}

	public void setSFYYQSYL(String in) {
		SFYYQSYL = in;
	}

	@Column(name = "YJKHZGNSYL", length = 299, nullable = true)
	@IColumn(description="预计客户最高年收益率")
	private String YJKHZGNSYL;

	public String getYJKHZGNSYL() {
		return YJKHZGNSYL;
	}

	public void setYJKHZGNSYL(String in) {
		YJKHZGNSYL = in;
	}

	@Column(name = "YJKHZDNSYL", length = 299, nullable = true)
	@IColumn(description="预计客户最低年收益率")
	private String YJKHZDNSYL;

	public String getYJKHZDNSYL() {
		return YJKHZDNSYL;
	}

	public void setYJKHZDNSYL(String in) {
		YJKHZDNSYL = in;
	}

	@Column(name = "SFYSYLCSYJ", length = 299, nullable = true)
	@IColumn(description="是否有收益率测算依据")
	private String SFYSYLCSYJ;

	public String getSFYSYLCSYJ() {
		return SFYSYLCSYJ;
	}

	public void setSFYSYLCSYJ(String in) {
		SFYSYLCSYJ = in;
	}

	@Column(name = "TZZFXPH", length = 299, nullable = true)
	@IColumn(description="投资者风险偏好")
	private String TZZFXPH;

	public String getTZZFXPH() {
		return TZZFXPH;
	}

	public void setTZZFXPH(String in) {
		TZZFXPH = in;
	}

	@Column(name = "CPXSQY", length = 299, nullable = true)
	@IColumn(description="产品销售区域")
	private String CPXSQY;

	public String getCPXSQY() {
		return CPXSQY;
	}

	public void setCPXSQY(String in) {
		CPXSQY = in;
	}

	@Column(name = "MJBZ", length = 299, nullable = true)
	@IColumn(description="募集币种")
	private String MJBZ;

	public String getMJBZ() {
		return MJBZ;
	}

	public void setMJBZ(String in) {
		MJBZ = in;
	}

	@Column(name = "DFBJBZ", length = 299, nullable = true)
	@IColumn(description="兑付本金币种")
	private String DFBJBZ;

	public String getDFBJBZ() {
		return DFBJBZ;
	}

	public void setDFBJBZ(String in) {
		DFBJBZ = in;
	}

	@Column(name = "DFSYBZ", length = 299, nullable = true)
	@IColumn(description="兑付收益币种")
	private String DFSYBZ;

	public String getDFSYBZ() {
		return DFSYBZ;
	}

	public void setDFSYBZ(String in) {
		DFSYBZ = in;
	}

	@Column(name = "QDXSJE", length = 20, nullable = true)
	@IColumn(description="起点销售金额")
	private BigDecimal QDXSJE;

	public BigDecimal getQDXSJE() {
		return QDXSJE;
	}

	public void setQDXSJE(String in) {
		QDXSJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JHMJJE", length = 20, nullable = true)
	@IColumn(description="计划募集金额")
	private BigDecimal JHMJJE;

	public BigDecimal getJHMJJE() {
		return JHMJJE;
	}

	public void setJHMJJE(String in) {
		JHMJJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "MJQSRQ", length = 8, nullable = true)
	@IColumn(description="募集起始日期")
	private String MJQSRQ;

	public String getMJQSRQ() {
		return MJQSRQ;
	}

	public void setMJQSRQ(String in) {
		MJQSRQ = in;
	}

	@Column(name = "TZBJDZR", length = 8, nullable = true)
	@IColumn(description="投资本金到账日")
	private String TZBJDZR;

	public String getTZBJDZR() {
		return TZBJDZR;
	}

	public void setTZBJDZR(String in) {
		TZBJDZR = in;
	}

	@Column(name = "TZSYDZR", length = 8, nullable = true)
	@IColumn(description="投资收益到账日")
	private String TZSYDZR;

	public String getTZSYDZR() {
		return TZSYDZR;
	}

	public void setTZSYDZR(String in) {
		TZSYDZR = in;
	}

	@Column(name = "XSSXFL", length = 299, nullable = true)
	@IColumn(description="销售手续费率")
	private BigDecimal XSSXFL;

	public BigDecimal getXSSXFL() {
		return XSSXFL;
	}

	public void setXSSXFL(String in) {
		XSSXFL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JNTGJGMC", length = 299, nullable = true)
	@IColumn(description="境内托管机构名称")
	private String JNTGJGMC;

	public String getJNTGJGMC() {
		return JNTGJGMC;
	}

	public void setJNTGJGMC(String in) {
		JNTGJGMC = in;
	}

	@Column(name = "JNTGJGDM", length = 299, nullable = true)
	@IColumn(description="境内托管机构代码")
	private String JNTGJGDM;

	public String getJNTGJGDM() {
		return JNTGJGDM;
	}

	public void setJNTGJGDM(String in) {
		JNTGJGDM = in;
	}

	@Column(name = "JWTGJGGB", length = 299, nullable = true)
	@IColumn(description="境外托管机构国别")
	private String JWTGJGGB;

	public String getJWTGJGGB() {
		return JWTGJGGB;
	}

	public void setJWTGJGGB(String in) {
		JWTGJGGB = in;
	}

	@Column(name = "JWTGJGMC", length = 299, nullable = true)
	@IColumn(description="境外托管机构名称")
	private String JWTGJGMC;

	public String getJWTGJGMC() {
		return JWTGJGMC;
	}

	public void setJWTGJGMC(String in) {
		JWTGJGMC = in;
	}

	@Column(name = "TGFL", length = 299, nullable = true)
	@IColumn(description="托管费率")
	private BigDecimal TGFL;

	public BigDecimal getTGFL() {
		return TGFL;
	}

	public void setTGFL(String in) {
		TGFL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CPFXDJ", length = 299, nullable = true)
	@IColumn(description="产品风险等级")
	private String CPFXDJ;

	public String getCPFXDJ() {
		return CPFXDJ;
	}

	public void setCPFXDJ(String in) {
		CPFXDJ = in;
	}

	@Column(name = "FXJGTQZZQBS", length = 299, nullable = true)
	@IColumn(description="发行机构提前终止权标识")
	private String FXJGTQZZQBS;

	public String getFXJGTQZZQBS() {
		return FXJGTQZZQBS;
	}

	public void setFXJGTQZZQBS(String in) {
		FXJGTQZZQBS = in;
	}

	@Column(name = "KHSHQBS", length = 299, nullable = true)
	@IColumn(description="客户赎回权标识")
	private String KHSHQBS;

	public String getKHSHQBS() {
		return KHSHQBS;
	}

	public void setKHSHQBS(String in) {
		KHSHQBS = in;
	}

	@Column(name = "CPZXBS", length = 299, nullable = true)
	@IColumn(description="产品增信标识")
	private String CPZXBS;

	public String getCPZXBS() {
		return CPZXBS;
	}

	public void setCPZXBS(String in) {
		CPZXBS = in;
	}

	@Column(name = "CPZXJGLX", length = 299, nullable = true)
	@IColumn(description="产品增信机构类型")
	private String CPZXJGLX;

	public String getCPZXJGLX() {
		return CPZXJGLX;
	}

	public void setCPZXJGLX(String in) {
		CPZXJGLX = in;
	}

	@Column(name = "CPZXXS", length = 299, nullable = true)
	@IColumn(description="产品增信形式")
	private String CPZXXS;

	public String getCPZXXS() {
		return CPZXXS;
	}

	public void setCPZXXS(String in) {
		CPZXXS = in;
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

