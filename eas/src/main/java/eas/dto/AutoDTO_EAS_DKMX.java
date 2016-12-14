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
@Table(name = "EAS_DKMX")
@IEntity(description= "授信情况-贷款明细")
public class AutoDTO_EAS_DKMX implements java.io.Serializable{

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

	@Column(name = "SBJGDM", length = 299, nullable = true)
	@IColumn(description="上报机构代码")
	private String SBJGDM;

	public String getSBJGDM() {
		return SBJGDM;
	}

	public void setSBJGDM(String in) {
		SBJGDM = in;
	}

	@Column(name = "DKKHMC", length = 299, nullable = true)
	@IColumn(description="贷款客户名称")
	private String DKKHMC;

	public String getDKKHMC() {
		return DKKHMC;
	}

	public void setDKKHMC(String in) {
		DKKHMC = in;
	}

	@Column(name = "DKKHDM", length = 299, nullable = true)
	@IColumn(description="贷款客户代码")
	private String DKKHDM;

	public String getDKKHDM() {
		return DKKHDM;
	}

	public void setDKKHDM(String in) {
		DKKHDM = in;
	}

	@Column(name = "DKFFXDM", length = 299, nullable = true)
	@IColumn(description="贷款发放行代码")
	private String DKFFXDM;

	public String getDKFFXDM() {
		return DKFFXDM;
	}

	public void setDKFFXDM(String in) {
		DKFFXDM = in;
	}

	@Column(name = "SXHM", length = 299, nullable = true)
	@IColumn(description="授信号码")
	private String SXHM;

	public String getSXHM() {
		return SXHM;
	}

	public void setSXHM(String in) {
		SXHM = in;
	}

	@Column(name = "DKHTH", length = 299, nullable = true)
	@IColumn(description="贷款合同号")
	private String DKHTH;

	public String getDKHTH() {
		return DKHTH;
	}

	public void setDKHTH(String in) {
		DKHTH = in;
	}

	@Column(name = "JJH", length = 299, nullable = true)
	@IColumn(description="借据号")
	private String JJH;

	public String getJJH() {
		return JJH;
	}

	public void setJJH(String in) {
		JJH = in;
	}

	@Column(name = "FFRQ", length = 8, nullable = true)
	@IColumn(description="发放日期")
	private String FFRQ;

	public String getFFRQ() {
		return FFRQ;
	}

	public void setFFRQ(String in) {
		FFRQ = in;
	}

	@Column(name = "DQRQ", length = 8, nullable = true)
	@IColumn(description="到期日期")
	private String DQRQ;

	public String getDQRQ() {
		return DQRQ;
	}

	public void setDQRQ(String in) {
		DQRQ = in;
	}

	@Column(name = "FFJE", length = 20, nullable = true)
	@IColumn(description="发放金额(万元)")
	private BigDecimal FFJE;

	public BigDecimal getFFJE() {
		return FFJE;
	}

	public void setFFJE(String in) {
		FFJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKYE", length = 20, nullable = true)
	@IColumn(description="贷款余额(万元)")
	private BigDecimal DKYE;

	public BigDecimal getDKYE() {
		return DKYE;
	}

	public void setDKYE(String in) {
		DKYE = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getWJFLTag",description="五级分类")
	@Column(name = "WJFL", nullable =true)
	private String WJFL;

	public static Map<String,String> getWJFLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_FIVE_CLASS");
	}

	public String getWJFL() {
		return WJFL;
	}

	public void setWJFL(String in) {
		WJFL = in;
	}

	@IColumn(tagMethodName="getDKLXTag",description="贷款类型")
	@Column(name = "DKLX", nullable =true)
	private String DKLX;

	public static Map<String,String> getDKLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LOAN_TYPE");
	}

	public String getDKLX() {
		return DKLX;
	}

	public void setDKLX(String in) {
		DKLX = in;
	}

	@IColumn(tagMethodName="getDKYWZLTag",description="贷款业务种类")
	@Column(name = "DKYWZL", nullable =true)
	private String DKYWZL;

	public static Map<String,String> getDKYWZLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LOAN_BIZ_TYPE");
	}

	public String getDKYWZL() {
		return DKYWZL;
	}

	public void setDKYWZL(String in) {
		DKYWZL = in;
	}

	@IColumn(tagMethodName="getTXXYTag",description="投向行业")
	@Column(name = "TXXY", nullable =true)
	private String TXXY;

	public static Map<String,String> getTXXYTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ECON_INDUSTRY_CLASS");
	}

	public String getTXXY() {
		return TXXY;
	}

	public void setTXXY(String in) {
		TXXY = in;
	}

	@IColumn(tagMethodName="getBZDMTag",description="币种代码")
	@Column(name = "BZDM", nullable =true)
	private String BZDM;

	public static Map<String,String> getBZDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_CURR_CD");
	}

	public String getBZDM() {
		return BZDM;
	}

	public void setBZDM(String in) {
		BZDM = in;
	}

	@IColumn(tagMethodName="getDBFSTag",description="担保方式")
	@Column(name = "DBFS", nullable =true)
	private String DBFS;

	public static Map<String,String> getDBFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_GUAR_MODE");
	}

	public String getDBFS() {
		return DBFS;
	}

	public void setDBFS(String in) {
		DBFS = in;
	}

	@Column(name = "QBYE", length = 20, nullable = true)
	@IColumn(description="欠本余额(万元)")
	private BigDecimal QBYE;

	public BigDecimal getQBYE() {
		return QBYE;
	}

	public void setQBYE(String in) {
		QBYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QBTS", nullable = true)
	@IColumn(description="欠本天数")
	private Integer QBTS;

	public Integer getQBTS() {
		return QBTS;
	}

	public void setQBTS(String in) {
		QBTS = TypeParse.parseInt(in);
	}

	@Column(name = "QXYE", length = 20, nullable = true)
	@IColumn(description="欠息余额(万元)")
	private BigDecimal QXYE;

	public BigDecimal getQXYE() {
		return QXYE;
	}

	public void setQXYE(String in) {
		QXYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QXTS", nullable = true)
	@IColumn(description="欠息天数")
	private Integer QXTS;

	public Integer getQXTS() {
		return QXTS;
	}

	public void setQXTS(String in) {
		QXTS = TypeParse.parseInt(in);
	}

	@Column(name = "BQHK", length = 20, nullable = true)
	@IColumn(description="本期还款(万元)")
	private BigDecimal BQHK;

	public BigDecimal getBQHK() {
		return BQHK;
	}

	public void setBQHK(String in) {
		BQHK = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getHBFSTag",description="还本方式")
	@Column(name = "HBFS", nullable =true)
	private String HBFS;

	public static Map<String,String> getHBFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_REPAY_CMBC_TYPE");
	}

	public String getHBFS() {
		return HBFS;
	}

	public void setHBFS(String in) {
		HBFS = in;
	}

	@IColumn(tagMethodName="getHXFSTag",description="还息方式")
	@Column(name = "HXFS", nullable =true)
	private String HXFS;

	public static Map<String,String> getHXFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_REPAY_INTRES_TYPE");
	}

	public String getHXFS() {
		return HXFS;
	}

	public void setHXFS(String in) {
		HXFS = in;
	}

	@Column(name = "XQHBRQ", length = 8, nullable = true)
	@IColumn(description="下期还本日期")
	private String XQHBRQ;

	public String getXQHBRQ() {
		return XQHBRQ;
	}

	public void setXQHBRQ(String in) {
		XQHBRQ = in;
	}

	@Column(name = "XQHBJE", length = 20, nullable = true)
	@IColumn(description="下期还本金额(万元)")
	private BigDecimal XQHBJE;

	public BigDecimal getXQHBJE() {
		return XQHBJE;
	}

	public void setXQHBJE(String in) {
		XQHBJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XQHXRQ", length = 8, nullable = true)
	@IColumn(description="下期还息日期")
	private String XQHXRQ;

	public String getXQHXRQ() {
		return XQHXRQ;
	}

	public void setXQHXRQ(String in) {
		XQHXRQ = in;
	}

	@Column(name = "XQHXJE", length = 20, nullable = true)
	@IColumn(description="下期还息金额(万元)")
	private BigDecimal XQHXJE;

	public BigDecimal getXQHXJE() {
		return XQHXJE;
	}

	public void setXQHXJE(String in) {
		XQHXJE = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getDKFFLXTag",description="贷款发放类型")
	@Column(name = "DKFFLX", nullable =true)
	private String DKFFLX;

	public static Map<String,String> getDKFFLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LOAN_DISTR_TYPE");
	}

	public String getDKFFLX() {
		return DKFFLX;
	}

	public void setDKFFLX(String in) {
		DKFFLX = in;
	}

	@Column(name = "JZZB", length = 20, nullable = true)
	@IColumn(description="减值准备(万元)")
	private BigDecimal JZZB;

	public BigDecimal getJZZB() {
		return JZZB;
	}

	public void setJZZB(String in) {
		JZZB = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getCYJGDZLXTag",description="产业结构调整类型")
	@Column(name = "CYJGDZLX", nullable =true)
	private String CYJGDZLX;

	public static Map<String,String> getCYJGDZLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_INDUSTRY_STRU_ADJ_TYPE");
	}

	public String getCYJGDZLX() {
		return CYJGDZLX;
	}

	public void setCYJGDZLX(String in) {
		CYJGDZLX = in;
	}

	@IColumn(tagMethodName="getGYZXSJBSTag",description="工业转型升级标识")
	@Column(name = "GYZXSJBS", nullable =true)
	private String GYZXSJBS;

	public static Map<String,String> getGYZXSJBSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_INDUSTRY_UPGD_IDTFY");
	}

	public String getGYZXSJBS() {
		return GYZXSJBS;
	}

	public void setGYZXSJBS(String in) {
		GYZXSJBS = in;
	}

	@IColumn(tagMethodName="getZLXXCYLXTag",description="战略新兴产业类型")
	@Column(name = "ZLXXCYLX", nullable =true)
	private String ZLXXCYLX;

	public static Map<String,String> getZLXXCYLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_NEW_INDUSTRY_TYPE");
	}

	public String getZLXXCYLX() {
		return ZLXXCYLX;
	}

	public void setZLXXCYLX(String in) {
		ZLXXCYLX = in;
	}

	@IColumn(tagMethodName="getYTDKBSTag",description="银团贷款标识")
	@Column(name = "YTDKBS", nullable =true)
	private String YTDKBS;

	public static Map<String,String> getYTDKBSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_SYN_LOAN_TAG_ROLE");
	}

	public String getYTDKBS() {
		return YTDKBS;
	}

	public void setYTDKBS(String in) {
		YTDKBS = in;
	}

	@IColumn(tagMethodName="getZFFSTag",description="支付方式")
	@Column(name = "ZFFS", nullable =true)
	private String ZFFS;

	public static Map<String,String> getZFFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_PAY_MODE");
	}

	public String getZFFS() {
		return ZFFS;
	}

	public void setZFFS(String in) {
		ZFFS = in;
	}

	@Column(name = "CJRQ", length = 299, nullable = true)
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

