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
@Table(name = "EAS_DGXDFHZ")
@IEntity(description= "对公信贷分户账")
public class AutoDTO_EAS_DGXDFHZ implements java.io.Serializable{

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

	@Column(name = "DKFHZH", length = 299, nullable = true)
	@IColumn(description="贷款分户账号")
	private String DKFHZH;

	public String getDKFHZH() {
		return DKFHZH;
	}

	public void setDKFHZH(String in) {
		DKFHZH = in;
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

	@Column(name = "ZHMC", length = 299, nullable = true)
	@IColumn(description="账户名称")
	private String ZHMC;

	public String getZHMC() {
		return ZHMC;
	}

	public void setZHMC(String in) {
		ZHMC = in;
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

	@Column(name = "ZJHKRQ", length = 8, nullable = true)
	@IColumn(description="上次还款日期")
	private String ZJHKRQ;

	public String getZJHKRQ() {
		return ZJHKRQ;
	}

	public void setZJHKRQ(String in) {
		ZJHKRQ = in;
	}

	@Column(name = "TJKMBH", length = 299, nullable = true)
	@IColumn(description="统计科目编号")
	private String TJKMBH;

	public String getTJKMBH() {
		return TJKMBH;
	}

	public void setTJKMBH(String in) {
		TJKMBH = in;
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

	@Column(name = "XDYXM", length = 299, nullable = true)
	@IColumn(description="信贷员姓名")
	private String XDYXM;

	public String getXDYXM() {
		return XDYXM;
	}

	public void setXDYXM(String in) {
		XDYXM = in;
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

	@Column(name = "HKZH", length = 299, nullable = true)
	@IColumn(description="还款账号")
	private String HKZH;

	public String getHKZH() {
		return HKZH;
	}

	public void setHKZH(String in) {
		HKZH = in;
	}

	@Column(name = "DKRZZH", length = 299, nullable = true)
	@IColumn(description="贷款入账账号")
	private String DKRZZH;

	public String getDKRZZH() {
		return DKRZZH;
	}

	public void setDKRZZH(String in) {
		DKRZZH = in;
	}

	@Column(name = "DKLL", length = 299, nullable = true)
	@IColumn(description="贷款利率")
	private String DKLL;

	public String getDKLL() {
		return DKLL;
	}

	public void setDKLL(String in) {
		DKLL = in;
	}

	@Column(name = "DKBJZE", length = 20, nullable = true)
	@IColumn(description="贷款本金总额")
	private BigDecimal DKBJZE;

	public BigDecimal getDKBJZE() {
		return DKBJZE;
	}

	public void setDKBJZE(String in) {
		DKBJZE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKZCYE", length = 20, nullable = true)
	@IColumn(description="贷款正常余额")
	private BigDecimal DKZCYE;

	public BigDecimal getDKZCYE() {
		return DKZCYE;
	}

	public void setDKZCYE(String in) {
		DKZCYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKYQYE", length = 20, nullable = true)
	@IColumn(description="贷款逾期余额")
	private BigDecimal DKYQYE;

	public BigDecimal getDKYQYE() {
		return DKYQYE;
	}

	public void setDKYQYE(String in) {
		DKYQYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QBYE", length = 20, nullable = true)
	@IColumn(description="欠本余额")
	private BigDecimal QBYE;

	public BigDecimal getQBYE() {
		return QBYE;
	}

	public void setQBYE(String in) {
		QBYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BWQXYE", length = 20, nullable = true)
	@IColumn(description="表外欠息余额")
	private BigDecimal BWQXYE;

	public BigDecimal getBWQXYE() {
		return BWQXYE;
	}

	public void setBWQXYE(String in) {
		BWQXYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKQX", length = 299, nullable = true)
	@IColumn(description="贷款期限")
	private String DKQX;

	public String getDKQX() {
		return DKQX;
	}

	public void setDKQX(String in) {
		DKQX = in;
	}

	@Column(name = "DQRQ", length = 299, nullable = true)
	@IColumn(description="到期日期")
	private String DQRQ;

	public String getDQRQ() {
		return DQRQ;
	}

	public void setDQRQ(String in) {
		DQRQ = in;
	}

	@Column(name = "QXRQ", length = 8, nullable = true)
	@IColumn(description="起息日期")
	private String QXRQ;

	public String getQXRQ() {
		return QXRQ;
	}

	public void setQXRQ(String in) {
		QXRQ = in;
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

	@Column(name = "XHRQ", length = 8, nullable = true)
	@IColumn(description="销户日期")
	private String XHRQ;

	public String getXHRQ() {
		return XHRQ;
	}

	public void setXHRQ(String in) {
		XHRQ = in;
	}

	@Column(name = "YQRQ", length = 299, nullable = true)
	@IColumn(description="逾期日期")
	private String YQRQ;

	public String getYQRQ() {
		return YQRQ;
	}

	public void setYQRQ(String in) {
		YQRQ = in;
	}

	@Column(name = "ZHZT", length = 299, nullable = true)
	@IColumn(description="账户状态")
	private String ZHZT;

	public String getZHZT() {
		return ZHZT;
	}

	public void setZHZT(String in) {
		ZHZT = in;
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

