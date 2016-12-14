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
@Table(name = "EAS_CYQYZQMX")
@IEntity(description= "授信情况-持有企业债券明细")
public class AutoDTO_EAS_CYQYZQMX implements java.io.Serializable{

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

	@Column(name = "YXCZQYMC", length = 299, nullable = true)
	@IColumn(description="银行持债企业名称")
	private String YXCZQYMC;

	public String getYXCZQYMC() {
		return YXCZQYMC;
	}

	public void setYXCZQYMC(String in) {
		YXCZQYMC = in;
	}

	@Column(name = "YXCZQYDM", length = 299, nullable = true)
	@IColumn(description="银行持债企业代码")
	private String YXCZQYDM;

	public String getYXCZQYDM() {
		return YXCZQYDM;
	}

	public void setYXCZQYDM(String in) {
		YXCZQYDM = in;
	}

	@Column(name = "CYXJGDM", length = 299, nullable = true)
	@IColumn(description="持有行机构代码")
	private String CYXJGDM;

	public String getCYXJGDM() {
		return CYXJGDM;
	}

	public void setCYXJGDM(String in) {
		CYXJGDM = in;
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

	@Column(name = "ZQDM", length = 299, nullable = true)
	@IColumn(description="债券代码")
	private String ZQDM;

	public String getZQDM() {
		return ZQDM;
	}

	public void setZQDM(String in) {
		ZQDM = in;
	}

	@IColumn(tagMethodName="getZQLXTag",description="债券类型")
	@Column(name = "ZQLX", nullable =true)
	private String ZQLX;

	public static Map<String,String> getZQLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_BOND_TYPE");
	}

	public String getZQLX() {
		return ZQLX;
	}

	public void setZQLX(String in) {
		ZQLX = in;
	}

	@Column(name = "ZQMZ", length = 20, nullable = true)
	@IColumn(description="债券面值(万元)")
	private BigDecimal ZQMZ;

	public BigDecimal getZQMZ() {
		return ZQMZ;
	}

	public void setZQMZ(String in) {
		ZQMZ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZMYE", length = 20, nullable = true)
	@IColumn(description="账面余额(万元)")
	private BigDecimal ZMYE;

	public BigDecimal getZMYE() {
		return ZMYE;
	}

	public void setZMYE(String in) {
		ZMYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "FXRQ", length = 8, nullable = true)
	@IColumn(description="发行日期")
	private String FXRQ;

	public String getFXRQ() {
		return FXRQ;
	}

	public void setFXRQ(String in) {
		FXRQ = in;
	}

	@Column(name = "DQDFRQ", length = 8, nullable = true)
	@IColumn(description="到期兑付日期")
	private String DQDFRQ;

	public String getDQDFRQ() {
		return DQDFRQ;
	}

	public void setDQDFRQ(String in) {
		DQDFRQ = in;
	}

	@IColumn(tagMethodName="getZHLXTag",description="账户类型")
	@Column(name = "ZHLX", nullable =true)
	private String ZHLX;

	public static Map<String,String> getZHLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_ACCT_TYPE");
	}

	public String getZHLX() {
		return ZHLX;
	}

	public void setZHLX(String in) {
		ZHLX = in;
	}

	@Column(name = "NBPJ", length = 299, nullable = true)
	@IColumn(description="内部评级")
	private String NBPJ;

	public String getNBPJ() {
		return NBPJ;
	}

	public void setNBPJ(String in) {
		NBPJ = in;
	}

	@Column(name = "WBPJ", length = 299, nullable = true)
	@IColumn(description="外部评级")
	private String WBPJ;

	public String getWBPJ() {
		return WBPJ;
	}

	public void setWBPJ(String in) {
		WBPJ = in;
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

