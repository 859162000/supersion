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
@Table(name = "EAS_DKZQ")
@IEntity(description= "贷款展期")
public class AutoDTO_EAS_DKZQ implements java.io.Serializable{

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

	@Column(name = "DKZQBH", length = 299, nullable = true)
	@IColumn(description="贷款展期编号")
	private String DKZQBH;

	public String getDKZQBH() {
		return DKZQBH;
	}

	public void setDKZQBH(String in) {
		DKZQBH = in;
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

	@Column(name = "ZQRQ", length = 8, nullable = true)
	@IColumn(description="展期日期")
	private String ZQRQ;

	public String getZQRQ() {
		return ZQRQ;
	}

	public void setZQRQ(String in) {
		ZQRQ = in;
	}

	@Column(name = "ZQDQRQ", length = 8, nullable = true)
	@IColumn(description="展期到期日期")
	private String ZQDQRQ;

	public String getZQDQRQ() {
		return ZQDQRQ;
	}

	public void setZQDQRQ(String in) {
		ZQDQRQ = in;
	}

	@Column(name = "ZQJE", length = 20, nullable = true)
	@IColumn(description="展期金额")
	private BigDecimal ZQJE;

	public BigDecimal getZQJE() {
		return ZQJE;
	}

	public void setZQJE(String in) {
		ZQJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "YLL", nullable = true)
	@IColumn(description="原利率(万分之一)")
	private BigDecimal YLL;

	public BigDecimal getYLL() {
		return YLL;
	}

	public void setYLL(String in) {
		YLL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZQLL", nullable = true)
	@IColumn(description="展期利率(万分之一)")
	private BigDecimal ZQLL;

	public BigDecimal getZQLL() {
		return ZQLL;
	}

	public void setZQLL(String in) {
		ZQLL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "YHTH", length = 299, nullable = true)
	@IColumn(description="原合同号")
	private String YHTH;

	public String getYHTH() {
		return YHTH;
	}

	public void setYHTH(String in) {
		YHTH = in;
	}

	@Column(name = "YJJH", length = 299, nullable = true)
	@IColumn(description="原借据号")
	private String YJJH;

	public String getYJJH() {
		return YJJH;
	}

	public void setYJJH(String in) {
		YJJH = in;
	}

	@Column(name = "JYGYH", length = 299, nullable = true)
	@IColumn(description="交易柜员号")
	private String JYGYH;

	public String getJYGYH() {
		return JYGYH;
	}

	public void setJYGYH(String in) {
		JYGYH = in;
	}

	@Column(name = "SQGYH", length = 299, nullable = true)
	@IColumn(description="授权柜员号")
	private String SQGYH;

	public String getSQGYH() {
		return SQGYH;
	}

	public void setSQGYH(String in) {
		SQGYH = in;
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

