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
@Table(name = "EAS_YTDK")
@IEntity(description= "银团贷款")
public class AutoDTO_EAS_YTDK implements java.io.Serializable{

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

	@Column(name = "XYLX", length = 299, nullable = true)
	@IColumn(description="协议类型")
	private String XYLX;

	public String getXYLX() {
		return XYLX;
	}

	public void setXYLX(String in) {
		XYLX = in;
	}

	@Column(name = "ZBXXH", length = 299, nullable = true)
	@IColumn(description="主办行行号")
	private String ZBXXH;

	public String getZBXXH() {
		return ZBXXH;
	}

	public void setZBXXH(String in) {
		ZBXXH = in;
	}

	@Column(name = "CDXXH", length = 299, nullable = true)
	@IColumn(description="参贷行行号")
	private String CDXXH;

	public String getCDXXH() {
		return CDXXH;
	}

	public void setCDXXH(String in) {
		CDXXH = in;
	}

	@Column(name = "DLXXH", length = 299, nullable = true)
	@IColumn(description="代理行行号")
	private String DLXXH;

	public String getDLXXH() {
		return DLXXH;
	}

	public void setDLXXH(String in) {
		DLXXH = in;
	}

	@Column(name = "ZBXXM", length = 299, nullable = true)
	@IColumn(description="主办行行名")
	private String ZBXXM;

	public String getZBXXM() {
		return ZBXXM;
	}

	public void setZBXXM(String in) {
		ZBXXM = in;
	}

	@Column(name = "CDXXM", length = 299, nullable = true)
	@IColumn(description="参贷行行名")
	private String CDXXM;

	public String getCDXXM() {
		return CDXXM;
	}

	public void setCDXXM(String in) {
		CDXXM = in;
	}

	@Column(name = "DLXXM", length = 299, nullable = true)
	@IColumn(description="代理行行名")
	private String DLXXM;

	public String getDLXXM() {
		return DLXXM;
	}

	public void setDLXXM(String in) {
		DLXXM = in;
	}

	@Column(name = "DLCDBZ", length = 299, nullable = true)
	@IColumn(description="代理参贷标志")
	private String DLCDBZ;

	public String getDLCDBZ() {
		return DLCDBZ;
	}

	public void setDLCDBZ(String in) {
		DLCDBZ = in;
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

	@Column(name = "SQDKZE", length = 20, nullable = true)
	@IColumn(description="申请贷款总额")
	private BigDecimal SQDKZE;

	public BigDecimal getSQDKZE() {
		return SQDKZE;
	}

	public void setSQDKZE(String in) {
		SQDKZE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CDDKJE", length = 20, nullable = true)
	@IColumn(description="承担贷款金额")
	private BigDecimal CDDKJE;

	public BigDecimal getCDDKJE() {
		return CDDKJE;
	}

	public void setCDDKJE(String in) {
		CDDKJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SJCDDKJE", length = 20, nullable = true)
	@IColumn(description="实际承担贷款金额")
	private BigDecimal SJCDDKJE;

	public BigDecimal getSJCDDKJE() {
		return SJCDDKJE;
	}

	public void setSJCDDKJE(String in) {
		SJCDDKJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "YFFDKJE", length = 20, nullable = true)
	@IColumn(description="已发放贷款金额")
	private BigDecimal YFFDKJE;

	public BigDecimal getYFFDKJE() {
		return YFFDKJE;
	}

	public void setYFFDKJE(String in) {
		YFFDKJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "YFFCDDKJE", length = 20, nullable = true)
	@IColumn(description="已发放承担贷款金额")
	private BigDecimal YFFCDDKJE;

	public BigDecimal getYFFCDDKJE() {
		return YFFCDDKJE;
	}

	public void setYFFCDDKJE(String in) {
		YFFCDDKJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CDSYDKJE", length = 20, nullable = true)
	@IColumn(description="承担剩余贷款金额")
	private BigDecimal CDSYDKJE;

	public BigDecimal getCDSYDKJE() {
		return CDSYDKJE;
	}

	public void setCDSYDKJE(String in) {
		CDSYDKJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JLXYXJGDM", length = 299, nullable = true)
	@IColumn(description="经理行银行机构代码")
	private String JLXYXJGDM;

	public String getJLXYXJGDM() {
		return JLXYXJGDM;
	}

	public void setJLXYXJGDM(String in) {
		JLXYXJGDM = in;
	}

	@Column(name = "XYQSRQ", length = 8, nullable = true)
	@IColumn(description="协议起始日期")
	private String XYQSRQ;

	public String getXYQSRQ() {
		return XYQSRQ;
	}

	public void setXYQSRQ(String in) {
		XYQSRQ = in;
	}

	@Column(name = "XYZZRQ", length = 8, nullable = true)
	@IColumn(description="协议终止日期")
	private String XYZZRQ;

	public String getXYZZRQ() {
		return XYZZRQ;
	}

	public void setXYZZRQ(String in) {
		XYZZRQ = in;
	}

	@Column(name = "XYZT", length = 299, nullable = true)
	@IColumn(description="协议状态")
	private String XYZT;

	public String getXYZT() {
		return XYZT;
	}

	public void setXYZT(String in) {
		XYZT = in;
	}

	@Column(name = "FHGY", length = 299, nullable = true)
	@IColumn(description="复核柜员")
	private String FHGY;

	public String getFHGY() {
		return FHGY;
	}

	public void setFHGY(String in) {
		FHGY = in;
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

