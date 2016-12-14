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
@Table(name = "EAS_DKWJXTBD")
@IEntity(description= "贷款五级形态变动")
public class AutoDTO_EAS_DKWJXTBD implements java.io.Serializable{

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

	@Column(name = "TZRQ", length = 8, nullable = true)
	@IColumn(description="调整日期")
	private String TZRQ;

	public String getTZRQ() {
		return TZRQ;
	}

	public void setTZRQ(String in) {
		TZRQ = in;
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

	@Column(name = "XDHTH", length = 299, nullable = true)
	@IColumn(description="信贷合同号")
	private String XDHTH;

	public String getXDHTH() {
		return XDHTH;
	}

	public void setXDHTH(String in) {
		XDHTH = in;
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

	@Column(name = "YXJGMC", length = 299, nullable = true)
	@IColumn(description="银行机构名称")
	private String YXJGMC;

	public String getYXJGMC() {
		return YXJGMC;
	}

	public void setYXJGMC(String in) {
		YXJGMC = in;
	}

	@Column(name = "YMXKMBH", length = 299, nullable = true)
	@IColumn(description="原明细科目编号")
	private String YMXKMBH;

	public String getYMXKMBH() {
		return YMXKMBH;
	}

	public void setYMXKMBH(String in) {
		YMXKMBH = in;
	}

	@IColumn(tagMethodName="getYWJXTTag",description="原五级形态")
	@Column(name = "YWJXT", nullable =true)
	private String YWJXT;

	public static Map<String,String> getYWJXTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_FORMER_FIVEMODAL");
	}

	public String getYWJXT() {
		return YWJXT;
	}

	public void setYWJXT(String in) {
		YWJXT = in;
	}

	@Column(name = "XMXKMBH", length = 299, nullable = true)
	@IColumn(description="新明细科目编号")
	private String XMXKMBH;

	public String getXMXKMBH() {
		return XMXKMBH;
	}

	public void setXMXKMBH(String in) {
		XMXKMBH = in;
	}

	@IColumn(tagMethodName="getXWJXTTag",description="新五级形态")
	@Column(name = "XWJXT", nullable =true)
	private String XWJXT;

	public static Map<String,String> getXWJXTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_NEW_FIVE_MODAL");
	}

	public String getXWJXT() {
		return XWJXT;
	}

	public void setXWJXT(String in) {
		XWJXT = in;
	}

	@Column(name = "ZRJE", length = 20, nullable = true)
	@IColumn(description="转入金额")
	private BigDecimal ZRJE;

	public BigDecimal getZRJE() {
		return ZRJE;
	}

	public void setZRJE(String in) {
		ZRJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZCJE", length = 20, nullable = true)
	@IColumn(description="转出金额")
	private BigDecimal ZCJE;

	public BigDecimal getZCJE() {
		return ZCJE;
	}

	public void setZCJE(String in) {
		ZCJE = TypeParse.parseBigDecimal(in);
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

	@Column(name = "JBGYH", length = 299, nullable = true)
	@IColumn(description="经办员工号")
	private String JBGYH;

	public String getJBGYH() {
		return JBGYH;
	}

	public void setJBGYH(String in) {
		JBGYH = in;
	}

	@Column(name = "TZGYH", length = 299, nullable = true)
	@IColumn(description="调整员工号")
	private String TZGYH;

	public String getTZGYH() {
		return TZGYH;
	}

	public void setTZGYH(String in) {
		TZGYH = in;
	}

	@Column(name = "SQGYH", length = 299, nullable = true)
	@IColumn(description="授权员工号")
	private String SQGYH;

	public String getSQGYH() {
		return SQGYH;
	}

	public void setSQGYH(String in) {
		SQGYH = in;
	}

	@Column(name = "SPGYH", length = 299, nullable = true)
	@IColumn(description="审批员工号")
	private String SPGYH;

	public String getSPGYH() {
		return SPGYH;
	}

	public void setSPGYH(String in) {
		SPGYH = in;
	}

	@IColumn(tagMethodName="getBDFSTag",description="变动方式")
	@Column(name = "BDFS", nullable =true)
	private String BDFS;

	public static Map<String,String> getBDFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CHANGE_MODE");
	}

	public String getBDFS() {
		return BDFS;
	}

	public void setBDFS(String in) {
		BDFS = in;
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

