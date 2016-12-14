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
@Table(name = "EAS_MYRZYWXXB")
@IEntity(description= "贸易融资业务信息表")
public class AutoDTO_EAS_MYRZYWXXB implements java.io.Serializable{

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

	@Column(name = "DGXDJJH", length = 299, nullable = true)
	@IColumn(description="借据号")
	private String DGXDJJH;

	public String getDGXDJJH() {
		return DGXDJJH;
	}

	public void setDGXDJJH(String in) {
		DGXDJJH = in;
	}

	@Column(name = "XDHTH", length = 299, nullable = true)
	@IColumn(description="合同号")
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

	@Column(name = "JKRMC", length = 299, nullable = true)
	@IColumn(description="借款人名称")
	private String JKRMC;

	public String getJKRMC() {
		return JKRMC;
	}

	public void setJKRMC(String in) {
		JKRMC = in;
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

	@IColumn(tagMethodName="getMYRZPZTag",description="贸易融资品种")
	@Column(name = "MYRZPZ", nullable =true)
	private String MYRZPZ;

	public static Map<String,String> getMYRZPZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_TRADE_FCG_BREED");
	}

	public String getMYRZPZ() {
		return MYRZPZ;
	}

	public void setMYRZPZ(String in) {
		MYRZPZ = in;
	}

	@Column(name = "MYHTH", length = 299, nullable = true)
	@IColumn(description="贸易合同号")
	private String MYHTH;

	public String getMYHTH() {
		return MYHTH;
	}

	public void setMYHTH(String in) {
		MYHTH = in;
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

	@Column(name = "MYHTZJE", length = 20, nullable = true)
	@IColumn(description="贸易合同总金额")
	private BigDecimal MYHTZJE;

	public BigDecimal getMYHTZJE() {
		return MYHTZJE;
	}

	public void setMYHTZJE(String in) {
		MYHTZJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BNYE", length = 20, nullable = true)
	@IColumn(description="表内余额")
	private BigDecimal BNYE;

	public BigDecimal getBNYE() {
		return BNYE;
	}

	public void setBNYE(String in) {
		BNYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BWYE", length = 299, nullable = true)
	@IColumn(description="表外余额")
	private String BWYE;

	public String getBWYE() {
		return BWYE;
	}

	public void setBWYE(String in) {
		BWYE = in;
	}

	@Column(name = "YXGJYWBH", length = 299, nullable = true)
	@IColumn(description="银行国际业务编号")
	private String YXGJYWBH;

	public String getYXGJYWBH() {
		return YXGJYWBH;
	}

	public void setYXGJYWBH(String in) {
		YXGJYWBH = in;
	}

	@Column(name = "SYFPHM", length = 299, nullable = true)
	@IColumn(description="商业发票号码")
	private String SYFPHM;

	public String getSYFPHM() {
		return SYFPHM;
	}

	public void setSYFPHM(String in) {
		SYFPHM = in;
	}

	@Column(name = "SYFPBZ", length = 299, nullable = true)
	@IColumn(description="商业发票币种")
	private String SYFPBZ;

	public String getSYFPBZ() {
		return SYFPBZ;
	}

	public void setSYFPBZ(String in) {
		SYFPBZ = in;
	}

	@Column(name = "SYFPJE", length = 20, nullable = true)
	@IColumn(description="商业发票金额")
	private BigDecimal SYFPJE;

	public BigDecimal getSYFPJE() {
		return SYFPJE;
	}

	public void setSYFPJE(String in) {
		SYFPJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XYZCDX", length = 299, nullable = true)
	@IColumn(description="信用证承兑行(或保兑行)名称")
	private String XYZCDX;

	public String getXYZCDX() {
		return XYZCDX;
	}

	public void setXYZCDX(String in) {
		XYZCDX = in;
	}

	@IColumn(tagMethodName="getFFTZLTag",description="福费庭种类")
	@Column(name = "FFTZL", nullable =true)
	private String FFTZL;

	public static Map<String,String> getFFTZLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_FORFAITING_TYPE");
	}

	public String getFFTZL() {
		return FFTZL;
	}

	public void setFFTZL(String in) {
		FFTZL = in;
	}

	@Column(name = "XYZBH", length = 299, nullable = true)
	@IColumn(description="信用证编号")
	private String XYZBH;

	public String getXYZBH() {
		return XYZBH;
	}

	public void setXYZBH(String in) {
		XYZBH = in;
	}

	@Column(name = "XYZBZ", length = 299, nullable = true)
	@IColumn(description="信用证币种")
	private String XYZBZ;

	public String getXYZBZ() {
		return XYZBZ;
	}

	public void setXYZBZ(String in) {
		XYZBZ = in;
	}

	@Column(name = "XYZJE", length = 20, nullable = true)
	@IColumn(description="信用证金额")
	private BigDecimal XYZJE;

	public BigDecimal getXYZJE() {
		return XYZJE;
	}

	public void setXYZJE(String in) {
		XYZJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XYZKZRQ", length = 8, nullable = true)
	@IColumn(description="信用证开证日期")
	private String XYZKZRQ;

	public String getXYZKZRQ() {
		return XYZKZRQ;
	}

	public void setXYZKZRQ(String in) {
		XYZKZRQ = in;
	}

	@IColumn(tagMethodName="getXYZQXLXTag",description="信用证期限类型")
	@Column(name = "XYZQXLX", nullable =true)
	private String XYZQXLX;

	public static Map<String,String> getXYZQXLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CRDT_CERT_TERM_TYPE");
	}

	public String getXYZQXLX() {
		return XYZQXLX;
	}

	public void setXYZQXLX(String in) {
		XYZQXLX = in;
	}

	@Column(name = "XYZXQ", length = 8, nullable = true)
	@IColumn(description="信用证效期")
	private String XYZXQ;

	public String getXYZXQ() {
		return XYZXQ;
	}

	public void setXYZXQ(String in) {
		XYZXQ = in;
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

