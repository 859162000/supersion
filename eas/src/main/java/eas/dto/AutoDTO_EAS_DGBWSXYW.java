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
@Table(name = "EAS_DGBWSXYW")
@IEntity(description= "对公表外授信业务")
public class AutoDTO_EAS_DGBWSXYW implements java.io.Serializable{

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

	@Column(name = "XDJJH", length = 299, nullable = true)
	@IColumn(description="信贷借据号")
	private String XDJJH;

	public String getXDJJH() {
		return XDJJH;
	}

	public void setXDJJH(String in) {
		XDJJH = in;
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

	@Column(name = "KHTYBH", length = 299, nullable = true)
	@IColumn(description="客户统一编号")
	private String KHTYBH;

	public String getKHTYBH() {
		return KHTYBH;
	}

	public void setKHTYBH(String in) {
		KHTYBH = in;
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

	@Column(name = "XDHTH", length = 299, nullable = true)
	@IColumn(description="信贷合同号")
	private String XDHTH;

	public String getXDHTH() {
		return XDHTH;
	}

	public void setXDHTH(String in) {
		XDHTH = in;
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

	@Column(name = "MXKMBH", length = 299, nullable = true)
	@IColumn(description="明细科目编号")
	private String MXKMBH;

	public String getMXKMBH() {
		return MXKMBH;
	}

	public void setMXKMBH(String in) {
		MXKMBH = in;
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

	@Column(name = "YWLX", length = 299, nullable = true)
	@IColumn(description="业务类型")
	private String YWLX;

	public String getYWLX() {
		return YWLX;
	}

	public void setYWLX(String in) {
		YWLX = in;
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

	@Column(name = "JE", length = 20, nullable = true)
	@IColumn(description="金额")
	private BigDecimal JE;

	public BigDecimal getJE() {
		return JE;
	}

	public void setJE(String in) {
		JE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "YE", length = 20, nullable = true)
	@IColumn(description="余额")
	private BigDecimal YE;

	public BigDecimal getYE() {
		return YE;
	}

	public void setYE(String in) {
		YE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKQX", nullable = true)
	@IColumn(description="贷款期限")
	private Integer DKQX;

	public Integer getDKQX() {
		return DKQX;
	}

	public void setDKQX(String in) {
		DKQX = TypeParse.parseInt(in);
	}

	@IColumn(tagMethodName="getSFDFTag",description="是否垫付")
	@Column(name = "SFDF", nullable =true)
	private String SFDF;

	public static Map<String,String> getSFDFTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_ADV_MONEY");
	}

	public String getSFDF() {
		return SFDF;
	}

	public void setSFDF(String in) {
		SFDF = in;
	}

	@Column(name = "DKSJFFRQ", length = 8, nullable = true)
	@IColumn(description="贷款实际发放日期")
	private String DKSJFFRQ;

	public String getDKSJFFRQ() {
		return DKSJFFRQ;
	}

	public void setDKSJFFRQ(String in) {
		DKSJFFRQ = in;
	}

	@Column(name = "DKYSDQRQ", length = 8, nullable = true)
	@IColumn(description="贷款原始到期日期")
	private String DKYSDQRQ;

	public String getDKYSDQRQ() {
		return DKYSDQRQ;
	}

	public void setDKYSDQRQ(String in) {
		DKYSDQRQ = in;
	}

	@Column(name = "DKSJDQRQ", length = 8, nullable = true)
	@IColumn(description="贷款实际到期日期")
	private String DKSJDQRQ;

	public String getDKSJDQRQ() {
		return DKSJDQRQ;
	}

	public void setDKSJDQRQ(String in) {
		DKSJDQRQ = in;
	}

	@Column(name = "ZJRQ", length = 8, nullable = true)
	@IColumn(description="终结日期")
	private String ZJRQ;

	public String getZJRQ() {
		return ZJRQ;
	}

	public void setZJRQ(String in) {
		ZJRQ = in;
	}

	@IColumn(tagMethodName="getDKWJFLTag",description="贷款五级分类")
	@Column(name = "DKWJFL", nullable =true)
	private String DKWJFL;

	public static Map<String,String> getDKWJFLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LOAN_FIVECLASS");
	}

	public String getDKWJFL() {
		return DKWJFL;
	}

	public void setDKWJFL(String in) {
		DKWJFL = in;
	}

	@Column(name = "SXFJE", length = 20, nullable = true)
	@IColumn(description="手续费金额")
	private BigDecimal SXFJE;

	public BigDecimal getSXFJE() {
		return SXFJE;
	}

	public void setSXFJE(String in) {
		SXFJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BZJBL", nullable = true)
	@IColumn(description="保证金比例(百分之一)")
	private BigDecimal BZJBL;

	public BigDecimal getBZJBL() {
		return BZJBL;
	}

	public void setBZJBL(String in) {
		BZJBL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BZJBZ", length = 299, nullable = true)
	@IColumn(description="保证金币种")
	private String BZJBZ;

	public String getBZJBZ() {
		return BZJBZ;
	}

	public void setBZJBZ(String in) {
		BZJBZ = in;
	}

	@Column(name = "BZJJE", length = 20, nullable = true)
	@IColumn(description="保证金金额")
	private BigDecimal BZJJE;

	public BigDecimal getBZJJE() {
		return BZJJE;
	}

	public void setBZJJE(String in) {
		BZJJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BZJZH", length = 299, nullable = true)
	@IColumn(description="保证金帐号")
	private String BZJZH;

	public String getBZJZH() {
		return BZJZH;
	}

	public void setBZJZH(String in) {
		BZJZH = in;
	}

	@Column(name = "PJHM", length = 299, nullable = true)
	@IColumn(description="票据号码")
	private String PJHM;

	public String getPJHM() {
		return PJHM;
	}

	public void setPJHM(String in) {
		PJHM = in;
	}

	@Column(name = "PMJE", length = 20, nullable = true)
	@IColumn(description="票面金额")
	private BigDecimal PMJE;

	public BigDecimal getPMJE() {
		return PMJE;
	}

	public void setPMJE(String in) {
		PMJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XDYGH", length = 299, nullable = true)
	@IColumn(description="信贷员工号")
	private String XDYGH;

	public String getXDYGH() {
		return XDYGH;
	}

	public void setXDYGH(String in) {
		XDYGH = in;
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

