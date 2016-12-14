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
@Table(name = "EAS_KHLCZHXXB")
@IEntity(description= "客户理财账户信息表")
public class AutoDTO_EAS_KHLCZHXXB implements java.io.Serializable{

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

	@Column(name = "LCZH", length = 299, nullable = true)
	@IColumn(description="理财帐号")
	private String LCZH;

	public String getLCZH() {
		return LCZH;
	}

	public void setLCZH(String in) {
		LCZH = in;
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

	@Column(name = "KHXM", length = 299, nullable = true)
	@IColumn(description="客户姓名")
	private String KHXM;

	public String getKHXM() {
		return KHXM;
	}

	public void setKHXM(String in) {
		KHXM = in;
	}

	@Column(name = "GLHQCKZH", length = 299, nullable = true)
	@IColumn(description="关联活期存款账号")
	private String GLHQCKZH;

	public String getGLHQCKZH() {
		return GLHQCKZH;
	}

	public void setGLHQCKZH(String in) {
		GLHQCKZH = in;
	}

	@Column(name = "LCCPMC", length = 299, nullable = true)
	@IColumn(description="理财产品名称")
	private String LCCPMC;

	public String getLCCPMC() {
		return LCCPMC;
	}

	public void setLCCPMC(String in) {
		LCCPMC = in;
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

	@Column(name = "FEZS", length = 299, nullable = true)
	@IColumn(description="份额总数")
	private String FEZS;

	public String getFEZS() {
		return FEZS;
	}

	public void setFEZS(String in) {
		FEZS = in;
	}

	@Column(name = "DJFE", length = 299, nullable = true)
	@IColumn(description="冻结份额")
	private String DJFE;

	public String getDJFE() {
		return DJFE;
	}

	public void setDJFE(String in) {
		DJFE = in;
	}

	@Column(name = "HLZTZBZ", length = 299, nullable = true)
	@IColumn(description="再投资标志")
	private String HLZTZBZ;

	public String getHLZTZBZ() {
		return HLZTZBZ;
	}

	public void setHLZTZBZ(String in) {
		HLZTZBZ = in;
	}

	@Column(name = "BQSY", length = 20, nullable = true)
	@IColumn(description="本期收益")
	private BigDecimal BQSY;

	public BigDecimal getBQSY() {
		return BQSY;
	}

	public void setBQSY(String in) {
		BQSY = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "LJSY", length = 20, nullable = true)
	@IColumn(description="累计收益")
	private BigDecimal LJSY;

	public BigDecimal getLJSY() {
		return LJSY;
	}

	public void setLJSY(String in) {
		LJSY = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "MRCB", length = 20, nullable = true)
	@IColumn(description="买入成本")
	private BigDecimal MRCB;

	public BigDecimal getMRCB() {
		return MRCB;
	}

	public void setMRCB(String in) {
		MRCB = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BQQSRQ", length = 8, nullable = true)
	@IColumn(description="本期起始日期")
	private String BQQSRQ;

	public String getBQQSRQ() {
		return BQQSRQ;
	}

	public void setBQQSRQ(String in) {
		BQQSRQ = in;
	}

	@Column(name = "BQDQRQ", length = 8, nullable = true)
	@IColumn(description="本期到期日期")
	private String BQDQRQ;

	public String getBQDQRQ() {
		return BQDQRQ;
	}

	public void setBQDQRQ(String in) {
		BQDQRQ = in;
	}

	@Column(name = "KHRQ", length = 8, nullable = true)
	@IColumn(description="开户日期")
	private String KHRQ;

	public String getKHRQ() {
		return KHRQ;
	}

	public void setKHRQ(String in) {
		KHRQ = in;
	}

	@Column(name = "SCDHRQ", length = 8, nullable = true)
	@IColumn(description="上次动户日期")
	private String SCDHRQ;

	public String getSCDHRQ() {
		return SCDHRQ;
	}

	public void setSCDHRQ(String in) {
		SCDHRQ = in;
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

