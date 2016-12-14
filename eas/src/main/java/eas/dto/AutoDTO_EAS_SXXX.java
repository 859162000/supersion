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
@Table(name = "EAS_SXXX")
@IEntity(description= "授信信息")
public class AutoDTO_EAS_SXXX implements java.io.Serializable{

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

	@Column(name = "SXXYH", length = 299, nullable = true)
	@IColumn(description="授信协议号")
	private String SXXYH;

	public String getSXXYH() {
		return SXXYH;
	}

	public void setSXXYH(String in) {
		SXXYH = in;
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

	@Column(name = "FSRQ", length = 8, nullable = true)
	@IColumn(description="发生日期")
	private String FSRQ;

	public String getFSRQ() {
		return FSRQ;
	}

	public void setFSRQ(String in) {
		FSRQ = in;
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

	@Column(name = "SXZL", length = 299, nullable = true)
	@IColumn(description="授信种类")
	private String SXZL;

	public String getSXZL() {
		return SXZL;
	}

	public void setSXZL(String in) {
		SXZL = in;
	}

	@Column(name = "SXXYMC", length = 299, nullable = true)
	@IColumn(description="授信协议名称")
	private String SXXYMC;

	public String getSXXYMC() {
		return SXXYMC;
	}

	public void setSXXYMC(String in) {
		SXXYMC = in;
	}

	@Column(name = "EDJE", length = 20, nullable = true)
	@IColumn(description="额度金额")
	private BigDecimal EDJE;

	public BigDecimal getEDJE() {
		return EDJE;
	}

	public void setEDJE(String in) {
		EDJE = TypeParse.parseBigDecimal(in);
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

	@Column(name = "SXRQ", length = 8, nullable = true)
	@IColumn(description="生效日期")
	private String SXRQ;

	public String getSXRQ() {
		return SXRQ;
	}

	public void setSXRQ(String in) {
		SXRQ = in;
	}

	@IColumn(tagMethodName="getSXZTTag",description="授信状态")
	@Column(name = "SXZT", nullable =true)
	private String SXZT;

	public static Map<String,String> getSXZTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CRDT_STATUS");
	}

	public String getSXZT() {
		return SXZT;
	}

	public void setSXZT(String in) {
		SXZT = in;
	}

	@Column(name = "SXKSRQ", length = 8, nullable = true)
	@IColumn(description="授信开始日期")
	private String SXKSRQ;

	public String getSXKSRQ() {
		return SXKSRQ;
	}

	public void setSXKSRQ(String in) {
		SXKSRQ = in;
	}

	@Column(name = "SXDQRQ", length = 8, nullable = true)
	@IColumn(description="授信到期日期")
	private String SXDQRQ;

	public String getSXDQRQ() {
		return SXDQRQ;
	}

	public void setSXDQRQ(String in) {
		SXDQRQ = in;
	}

	@Column(name = "SCSXRQ", length = 299, nullable = true)
	@IColumn(description="首次授信日期")
	private String SCSXRQ;

	public String getSCSXRQ() {
		return SCSXRQ;
	}

	public void setSCSXRQ(String in) {
		SCSXRQ = in;
	}

	@Column(name = "JCDYJ", length = 299, nullable = true)
	@IColumn(description="决策单意见")
	private String JCDYJ;

	public String getJCDYJ() {
		return JCDYJ;
	}

	public void setJCDYJ(String in) {
		JCDYJ = in;
	}

	@Column(name = "ZZSPR", length = 299, nullable = true)
	@IColumn(description="最终审批人")
	private String ZZSPR;

	public String getZZSPR() {
		return ZZSPR;
	}

	public void setZZSPR(String in) {
		ZZSPR = in;
	}

	@IColumn(tagMethodName="getSFXHEDTag",description="是否循环额度")
	@Column(name = "SFXHED", nullable =true)
	private String SFXHED;

	public static Map<String,String> getSFXHEDTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_CIRCL_LIMIT");
	}

	public String getSFXHED() {
		return SFXHED;
	}

	public void setSFXHED(String in) {
		SFXHED = in;
	}

	@IColumn(tagMethodName="getSFLSEDTag",description="是否临时额度")
	@Column(name = "SFLSED", nullable =true)
	private String SFLSED;

	public static Map<String,String> getSFLSEDTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_TEMP_LIMIT");
	}

	public String getSFLSED() {
		return SFLSED;
	}

	public void setSFLSED(String in) {
		SFLSED = in;
	}

	@Column(name = "SXYGH", length = 299, nullable = true)
	@IColumn(description="授信员工号")
	private String SXYGH;

	public String getSXYGH() {
		return SXYGH;
	}

	public void setSXYGH(String in) {
		SXYGH = in;
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

