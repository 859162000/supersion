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
@Table(name = "EAS_PJPMXXB")
@IEntity(description= "票据票面信息表")
public class AutoDTO_EAS_PJPMXXB implements java.io.Serializable{

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

	@Column(name = "PJHM", length = 299, nullable = true)
	@IColumn(description="票据号码")
	private String PJHM;

	public String getPJHM() {
		return PJHM;
	}

	public void setPJHM(String in) {
		PJHM = in;
	}

	@Column(name = "CPRBH", length = 299, nullable = true)
	@IColumn(description="出票人编号")
	private String CPRBH;

	public String getCPRBH() {
		return CPRBH;
	}

	public void setCPRBH(String in) {
		CPRBH = in;
	}

	@Column(name = "FKXDM", length = 299, nullable = true)
	@IColumn(description="付款行代码")
	private String FKXDM;

	public String getFKXDM() {
		return FKXDM;
	}

	public void setFKXDM(String in) {
		FKXDM = in;
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

	@IColumn(tagMethodName="getPJLXTag",description="票据类型")
	@Column(name = "PJLX", nullable =true)
	private String PJLX;

	public static Map<String,String> getPJLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_BILL_TYPE");
	}

	public String getPJLX() {
		return PJLX;
	}

	public void setPJLX(String in) {
		PJLX = in;
	}

	@Column(name = "CPRQC", length = 299, nullable = true)
	@IColumn(description="出票人全称")
	private String CPRQC;

	public String getCPRQC() {
		return CPRQC;
	}

	public void setCPRQC(String in) {
		CPRQC = in;
	}

	@Column(name = "CPRZH", length = 299, nullable = true)
	@IColumn(description="出票人账号")
	private String CPRZH;

	public String getCPRZH() {
		return CPRZH;
	}

	public void setCPRZH(String in) {
		CPRZH = in;
	}

	@Column(name = "FKXMC", length = 299, nullable = true)
	@IColumn(description="付款行名称")
	private String FKXMC;

	public String getFKXMC() {
		return FKXMC;
	}

	public void setFKXMC(String in) {
		FKXMC = in;
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

	@Column(name = "PMJE", length = 20, nullable = true)
	@IColumn(description="票面金额")
	private BigDecimal PMJE;

	public BigDecimal getPMJE() {
		return PMJE;
	}

	public void setPMJE(String in) {
		PMJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QFRQ", length = 8, nullable = true)
	@IColumn(description="签发日期")
	private String QFRQ;

	public String getQFRQ() {
		return QFRQ;
	}

	public void setQFRQ(String in) {
		QFRQ = in;
	}

	@Column(name = "PJDQRQ", length = 8, nullable = true)
	@IColumn(description="票据到期日期")
	private String PJDQRQ;

	public String getPJDQRQ() {
		return PJDQRQ;
	}

	public void setPJDQRQ(String in) {
		PJDQRQ = in;
	}

	@Column(name = "SKRMC", length = 299, nullable = true)
	@IColumn(description="收款人名称")
	private String SKRMC;

	public String getSKRMC() {
		return SKRMC;
	}

	public void setSKRMC(String in) {
		SKRMC = in;
	}

	@Column(name = "SKRZH", length = 299, nullable = true)
	@IColumn(description="收款人账号")
	private String SKRZH;

	public String getSKRZH() {
		return SKRZH;
	}

	public void setSKRZH(String in) {
		SKRZH = in;
	}

	@Column(name = "SKRKHX", length = 299, nullable = true)
	@IColumn(description="收款人开户行")
	private String SKRKHX;

	public String getSKRKHX() {
		return SKRKHX;
	}

	public void setSKRKHX(String in) {
		SKRKHX = in;
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

