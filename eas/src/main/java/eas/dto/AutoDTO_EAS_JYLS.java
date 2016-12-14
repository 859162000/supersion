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
@Table(name = "EAS_JYLS")
@IEntity(description= "交易流水")
public class AutoDTO_EAS_JYLS implements java.io.Serializable{

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

	@Column(name = "HXJYLSH", length = 299, nullable = true)
	@IColumn(description="核心交易流水号")
	private String HXJYLSH;

	public String getHXJYLSH() {
		return HXJYLSH;
	}

	public void setHXJYLSH(String in) {
		HXJYLSH = in;
	}

	@Column(name = "ZJYLSH", length = 299, nullable = true)
	@IColumn(description="子交易流水号")
	private String ZJYLSH;

	public String getZJYLSH() {
		return ZJYLSH;
	}

	public void setZJYLSH(String in) {
		ZJYLSH = in;
	}

	@Column(name = "BCXH", nullable = true)
	@IColumn(description="笔次序号")
	private Integer BCXH;

	public Integer getBCXH() {
		return BCXH;
	}

	public void setBCXH(String in) {
		BCXH = TypeParse.parseInt(in);
	}

	@Column(name = "JYRQ", length = 8, nullable = true)
	@IColumn(description="交易日期")
	private String JYRQ;

	public String getJYRQ() {
		return JYRQ;
	}

	public void setJYRQ(String in) {
		JYRQ = in;
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

	@Column(name = "NBJGH", length = 299, nullable = true)
	@IColumn(description="内部机构号")
	private String NBJGH;

	public String getNBJGH() {
		return NBJGH;
	}

	public void setNBJGH(String in) {
		NBJGH = in;
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

	@Column(name = "MXKMBH", length = 299, nullable = true)
	@IColumn(description="明细科目编号")
	private String MXKMBH;

	public String getMXKMBH() {
		return MXKMBH;
	}

	public void setMXKMBH(String in) {
		MXKMBH = in;
	}

	@Column(name = "JYSJ", length = 299, nullable = true)
	@IColumn(description="交易时间")
	private String JYSJ;

	public String getJYSJ() {
		return JYSJ;
	}

	public void setJYSJ(String in) {
		JYSJ = in;
	}

	@Column(name = "JZRQ", length = 8, nullable = true)
	@IColumn(description="记账日期")
	private String JZRQ;

	public String getJZRQ() {
		return JZRQ;
	}

	public void setJZRQ(String in) {
		JZRQ = in;
	}

	@Column(name = "JZSJ", length = 299, nullable = true)
	@IColumn(description="记账时间")
	private String JZSJ;

	public String getJZSJ() {
		return JZSJ;
	}

	public void setJZSJ(String in) {
		JZSJ = in;
	}

	@Column(name = "JYJGMC", length = 299, nullable = true)
	@IColumn(description="交易机构名称")
	private String JYJGMC;

	public String getJYJGMC() {
		return JYJGMC;
	}

	public void setJYJGMC(String in) {
		JYJGMC = in;
	}

	@Column(name = "JYZH", length = 299, nullable = true)
	@IColumn(description="交易帐号")
	private String JYZH;

	public String getJYZH() {
		return JYZH;
	}

	public void setJYZH(String in) {
		JYZH = in;
	}

	@Column(name = "JYHM", length = 299, nullable = true)
	@IColumn(description="交易户名")
	private String JYHM;

	public String getJYHM() {
		return JYHM;
	}

	public void setJYHM(String in) {
		JYHM = in;
	}

	@Column(name = "JYXTMC", length = 299, nullable = true)
	@IColumn(description="交易系统名称")
	private String JYXTMC;

	public String getJYXTMC() {
		return JYXTMC;
	}

	public void setJYXTMC(String in) {
		JYXTMC = in;
	}

	@Column(name = "DFXH", length = 299, nullable = true)
	@IColumn(description="对方行号")
	private String DFXH;

	public String getDFXH() {
		return DFXH;
	}

	public void setDFXH(String in) {
		DFXH = in;
	}

	@Column(name = "DFJGMC", length = 299, nullable = true)
	@IColumn(description="对方机构名称")
	private String DFJGMC;

	public String getDFJGMC() {
		return DFJGMC;
	}

	public void setDFJGMC(String in) {
		DFJGMC = in;
	}

	@Column(name = "DFZH", length = 299, nullable = true)
	@IColumn(description="对方账号")
	private String DFZH;

	public String getDFZH() {
		return DFZH;
	}

	public void setDFZH(String in) {
		DFZH = in;
	}

	@Column(name = "DFHM", length = 299, nullable = true)
	@IColumn(description="对方户名")
	private String DFHM;

	public String getDFHM() {
		return DFHM;
	}

	public void setDFHM(String in) {
		DFHM = in;
	}

	@Column(name = "JYJE", length = 20, nullable = true)
	@IColumn(description="交易金额")
	private BigDecimal JYJE;

	public BigDecimal getJYJE() {
		return JYJE;
	}

	public void setJYJE(String in) {
		JYJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZHYE", length = 20, nullable = true)
	@IColumn(description="账户余额")
	private BigDecimal ZHYE;

	public BigDecimal getZHYE() {
		return ZHYE;
	}

	public void setZHYE(String in) {
		ZHYE = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getJDBZTag",description="借贷标志")
	@Column(name = "JDBZ", nullable =true)
	private String JDBZ;

	public static Map<String,String> getJDBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_DC_IND");
	}

	public String getJDBZ() {
		return JDBZ;
	}

	public void setJDBZ(String in) {
		JDBZ = in;
	}

	@IColumn(tagMethodName="getXZBZTag",description="现转标志")
	@Column(name = "XZBZ", nullable =true)
	private String XZBZ;

	public static Map<String,String> getXZBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CAS_TFR_FLG");
	}

	public String getXZBZ() {
		return XZBZ;
	}

	public void setXZBZ(String in) {
		XZBZ = in;
	}

	@IColumn(tagMethodName="getBZTag",description="币种")
	@Column(name = "BZ", nullable =true)
	private String BZ;

	public static Map<String,String> getBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_CURR_CD");
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String in) {
		BZ = in;
	}

	@Column(name = "YWLX", length = 299, nullable = true)
	@IColumn(description="业务类型")
	private String YWLX;

	public String getYWLX() {
		return YWLX;
	}

	public void setYWLX(String in) {
		YWLX = in;
	}

	@Column(name = "JYLX", length = 299, nullable = true)
	@IColumn(description="交易类型")
	private String JYLX;

	public String getJYLX() {
		return JYLX;
	}

	public void setJYLX(String in) {
		JYLX = in;
	}

	@IColumn(tagMethodName="getJYQDTag",description="交易渠道")
	@Column(name = "JYQD", nullable =true)
	private String JYQD;

	public static Map<String,String> getJYQDTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_TX_CHAN");
	}

	public String getJYQD() {
		return JYQD;
	}

	public void setJYQD(String in) {
		JYQD = in;
	}

	@IColumn(tagMethodName="getJYJZMCTag",description="交易介质名称")
	@Column(name = "JYJZMC", nullable =true)
	private String JYJZMC;

	public static Map<String,String> getJYJZMCTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_TX_MED_NAME");
	}

	public String getJYJZMC() {
		return JYJZMC;
	}

	public void setJYJZMC(String in) {
		JYJZMC = in;
	}

	@Column(name = "JYJZH", length = 299, nullable = true)
	@IColumn(description="交易介质号")
	private String JYJZH;

	public String getJYJZH() {
		return JYJZH;
	}

	public void setJYJZH(String in) {
		JYJZH = in;
	}

	@Column(name = "CZGYH", length = 299, nullable = true)
	@IColumn(description="操作柜员号")
	private String CZGYH;

	public String getCZGYH() {
		return CZGYH;
	}

	public void setCZGYH(String in) {
		CZGYH = in;
	}

	@Column(name = "GYLSH", length = 299, nullable = true)
	@IColumn(description="柜员流水号")
	private String GYLSH;

	public String getGYLSH() {
		return GYLSH;
	}

	public void setGYLSH(String in) {
		GYLSH = in;
	}

	@Column(name = "FHGYH", length = 299, nullable = true)
	@IColumn(description="复核柜员号")
	private String FHGYH;

	public String getFHGYH() {
		return FHGYH;
	}

	public void setFHGYH(String in) {
		FHGYH = in;
	}

	@Column(name = "ZY", length = 500, nullable = true)
	@IColumn(description="摘要")
	private String ZY;

	public String getZY() {
		return ZY;
	}

	public void setZY(String in) {
		ZY = in;
	}

	@Column(name = "ZPZZL", length = 299, nullable = true)
	@IColumn(description="主凭证种类")
	private String ZPZZL;

	public String getZPZZL() {
		return ZPZZL;
	}

	public void setZPZZL(String in) {
		ZPZZL = in;
	}

	@Column(name = "ZPZH", length = 299, nullable = true)
	@IColumn(description="主凭证号")
	private String ZPZH;

	public String getZPZH() {
		return ZPZH;
	}

	public void setZPZH(String in) {
		ZPZH = in;
	}

	@Column(name = "FPZZL", length = 299, nullable = true)
	@IColumn(description="副凭证种类")
	private String FPZZL;

	public String getFPZZL() {
		return FPZZL;
	}

	public void setFPZZL(String in) {
		FPZZL = in;
	}

	@Column(name = "FPZH", length = 299, nullable = true)
	@IColumn(description="副凭证号")
	private String FPZH;

	public String getFPZH() {
		return FPZH;
	}

	public void setFPZH(String in) {
		FPZH = in;
	}

	@IColumn(tagMethodName="getCBMBZTag",description="冲补抹标志")
	@Column(name = "CBMBZ", nullable =true)
	private String CBMBZ;

	public static Map<String,String> getCBMBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_REV_MEND_IND");
	}

	public String getCBMBZ() {
		return CBMBZ;
	}

	public void setCBMBZ(String in) {
		CBMBZ = in;
	}

	@Column(name = "SJC", length = 299, nullable = true)
	@IColumn(description="时间戳")
	private String SJC;

	public String getSJC() {
		return SJC;
	}

	public void setSJC(String in) {
		SJC = in;
	}

	@IColumn(tagMethodName="getZHBZTag",description="账户标志")
	@Column(name = "ZHBZ", nullable =true)
	private String ZHBZ;

	public static Map<String,String> getZHBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_ACCT_IND");
	}

	public String getZHBZ() {
		return ZHBZ;
	}

	public void setZHBZ(String in) {
		ZHBZ = in;
	}

	@IColumn(tagMethodName="getKXHBZTag",description="开销户标志")
	@Column(name = "KXHBZ", nullable =true)
	private String KXHBZ;

	public static Map<String,String> getKXHBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_WRTOFF_IND");
	}

	public String getKXHBZ() {
		return KXHBZ;
	}

	public void setKXHBZ(String in) {
		KXHBZ = in;
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

