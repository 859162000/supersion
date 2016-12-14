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
@Table(name = "EAS_XDZCZR")
@IEntity(description= "信贷资产转让")
public class AutoDTO_EAS_XDZCZR implements java.io.Serializable{

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

	@Column(name = "ZRHTH", length = 299, nullable = true)
	@IColumn(description="转让合同号")
	private String ZRHTH;

	public String getZRHTH() {
		return ZRHTH;
	}

	public void setZRHTH(String in) {
		ZRHTH = in;
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

	@Column(name = "JYDSBH", length = 299, nullable = true)
	@IColumn(description="交易对手编号")
	private String JYDSBH;

	public String getJYDSBH() {
		return JYDSBH;
	}

	public void setJYDSBH(String in) {
		JYDSBH = in;
	}

	@Column(name = "JYDSMC", length = 299, nullable = true)
	@IColumn(description="交易对手名称")
	private String JYDSMC;

	public String getJYDSMC() {
		return JYDSMC;
	}

	public void setJYDSMC(String in) {
		JYDSMC = in;
	}

	@Column(name = "XDJYLX", length = 299, nullable = true)
	@IColumn(description="信贷交易类型")
	private String XDJYLX;

	public String getXDJYLX() {
		return XDJYLX;
	}

	public void setXDJYLX(String in) {
		XDJYLX = in;
	}

	@IColumn(tagMethodName="getJYZCLXTag",description="交易资产类型")
	@Column(name = "JYZCLX", nullable =true)
	private String JYZCLX;

	public static Map<String,String> getJYZCLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_TX_ASSET_TYPE");
	}

	public String getJYZCLX() {
		return JYZCLX;
	}

	public void setJYZCLX(String in) {
		JYZCLX = in;
	}

	@Column(name = "ZRDKBJZE", length = 20, nullable = true)
	@IColumn(description="转让贷款本金总额")
	private BigDecimal ZRDKBJZE;

	public BigDecimal getZRDKBJZE() {
		return ZRDKBJZE;
	}

	public void setZRDKBJZE(String in) {
		ZRDKBJZE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZRDKLXZE", length = 20, nullable = true)
	@IColumn(description="转让贷款利息总额")
	private BigDecimal ZRDKLXZE;

	public BigDecimal getZRDKLXZE() {
		return ZRDKLXZE;
	}

	public void setZRDKLXZE(String in) {
		ZRDKLXZE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZRSXF", length = 20, nullable = true)
	@IColumn(description="转让手续费")
	private BigDecimal ZRSXF;

	public BigDecimal getZRSXF() {
		return ZRSXF;
	}

	public void setZRSXF(String in) {
		ZRSXF = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZRZJ", length = 20, nullable = true)
	@IColumn(description="转让总价")
	private BigDecimal ZRZJ;

	public BigDecimal getZRZJ() {
		return ZRZJ;
	}

	public void setZRZJ(String in) {
		ZRZJ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "HGLL", nullable = true)
	@IColumn(description="回购利率(万分之一)")
	private BigDecimal HGLL;

	public BigDecimal getHGLL() {
		return HGLL;
	}

	public void setHGLL(String in) {
		HGLL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZRJGRQ", length = 8, nullable = true)
	@IColumn(description="转让交割日期")
	private String ZRJGRQ;

	public String getZRJGRQ() {
		return ZRJGRQ;
	}

	public void setZRJGRQ(String in) {
		ZRJGRQ = in;
	}

	@Column(name = "HGJZRQ", length = 8, nullable = true)
	@IColumn(description="回购基准日期")
	private String HGJZRQ;

	public String getHGJZRQ() {
		return HGJZRQ;
	}

	public void setHGJZRQ(String in) {
		HGJZRQ = in;
	}

	@Column(name = "ZRHTQSRQ", length = 8, nullable = true)
	@IColumn(description="转让合同起始日期")
	private String ZRHTQSRQ;

	public String getZRHTQSRQ() {
		return ZRHTQSRQ;
	}

	public void setZRHTQSRQ(String in) {
		ZRHTQSRQ = in;
	}

	@Column(name = "ZRHTDQRQ", length = 8, nullable = true)
	@IColumn(description="转让合同到期日期")
	private String ZRHTDQRQ;

	public String getZRHTDQRQ() {
		return ZRHTDQRQ;
	}

	public void setZRHTDQRQ(String in) {
		ZRHTDQRQ = in;
	}

	@Column(name = "JYDSZZH", length = 299, nullable = true)
	@IColumn(description="交易对手转账号")
	private String JYDSZZH;

	public String getJYDSZZH() {
		return JYDSZZH;
	}

	public void setJYDSZZH(String in) {
		JYDSZZH = in;
	}

	@Column(name = "JYDSZZZHHM", length = 299, nullable = true)
	@IColumn(description="交易对手转账账号户名")
	private String JYDSZZZHHM;

	public String getJYDSZZZHHM() {
		return JYDSZZZHHM;
	}

	public void setJYDSZZZHHM(String in) {
		JYDSZZZHHM = in;
	}

	@Column(name = "JYDSXH", length = 299, nullable = true)
	@IColumn(description="交易对手行号")
	private String JYDSXH;

	public String getJYDSXH() {
		return JYDSXH;
	}

	public void setJYDSXH(String in) {
		JYDSXH = in;
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

