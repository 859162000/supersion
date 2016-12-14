package eas.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
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
@Table(name = "EAS_DBQKTJB")
@IEntity(description= "担保情况统计表")
public class AutoDTO_EAS_DBQKTJB implements java.io.Serializable{

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

	@Column(name = "KHMC", length = 299, nullable = true)
	@IColumn(description="客户名称")
	private String KHMC;

	public String getKHMC() {
		return KHMC;
	}

	public void setKHMC(String in) {
		KHMC = in;
	}

	@Column(name = "KHDM", length = 299, nullable = true)
	@IColumn(description="客户代码")
	private String KHDM;

	public String getKHDM() {
		return KHDM;
	}

	public void setKHDM(String in) {
		KHDM = in;
	}

	@Column(name = "YXSFZJLX", length = 299, nullable = true)
	@IColumn(description="证件类型")
	private String YXSFZJLX;

	public String getYXSFZJLX() {
		return YXSFZJLX;
	}

	public void setYXSFZJLX(String in) {
		YXSFZJLX = in;
	}

	@Column(name = "ZJHM", length = 299, nullable = true)
	@IColumn(description="证件号码")
	private String ZJHM;

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String in) {
		ZJHM = in;
	}

	@Column(name = "DKHBWYWHTH", length = 299, nullable = true)
	@IColumn(description="贷款或表外业务合同号")
	private String DKHBWYWHTH;

	public String getDKHBWYWHTH() {
		return DKHBWYWHTH;
	}

	public void setDKHBWYWHTH(String in) {
		DKHBWYWHTH = in;
	}

	@Column(name = "DBHTH", length = 299, nullable = true)
	@IColumn(description="担保合同号")
	private String DBHTH;

	public String getDBHTH() {
		return DBHTH;
	}

	public void setDBHTH(String in) {
		DBHTH = in;
	}

	@IColumn(tagMethodName="getDBHTLXTag",description="担保合同类型")
	@Column(name = "DBHTLX", nullable =true)
	private String DBHTLX;

	public static Map<String,String> getDBHTLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_GUAR_CONTR_TYPE");
	}

	public String getDBHTLX() {
		return DBHTLX;
	}

	public void setDBHTLX(String in) {
		DBHTLX = in;
	}

	@IColumn(tagMethodName="getYPLXTag",description="押品类型")
	@Column(name = "YPLX", nullable =true)
	private String YPLX;

	public static Map<String,String> getYPLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_NEGOT_CLASS_ENCODE");
	}

	public String getYPLX() {
		return YPLX;
	}

	public void setYPLX(String in) {
		YPLX = in;
	}

	@Column(name = "YPMC", length = 299, nullable = true)
	@IColumn(description="押品名称")
	private String YPMC;

	public String getYPMC() {
		return YPMC;
	}

	public void setYPMC(String in) {
		YPMC = in;
	}

	@Column(name = "YPDM", length = 299, nullable = true)
	@IColumn(description="押品代码")
	private String YPDM;

	public String getYPDM() {
		return YPDM;
	}

	public void setYPDM(String in) {
		YPDM = in;
	}

	@Column(name = "YPQSRHBZRMC", length = 299, nullable = true)
	@IColumn(description="押品权属人或保证人名称")
	private String YPQSRHBZRMC;

	public String getYPQSRHBZRMC() {
		return YPQSRHBZRMC;
	}

	public void setYPQSRHBZRMC(String in) {
		YPQSRHBZRMC = in;
	}

	@IColumn(tagMethodName="getYPQSRHBZRLXTag",description="押品权属人或保证人类型")
	@Column(name = "YPQSRHBZRLX", nullable =true)
	private String YPQSRHBZRLX;

	public static Map<String,String> getYPQSRHBZRLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_NEGOT_ATTRIBUTER_TYPE");
	}

	public String getYPQSRHBZRLX() {
		return YPQSRHBZRLX;
	}

	public void setYPQSRHBZRLX(String in) {
		YPQSRHBZRLX = in;
	}

	@IColumn(tagMethodName="getYPQSRHBZRZJLXTag",description="押品权属人或保证人证件类型")
	@Column(name = "YPQSRHBZRZJLX", nullable =true)
	private String YPQSRHBZRZJLX;

	public static Map<String,String> getYPQSRHBZRZJLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_NEGOT_ATTR_CERT_TYPE");
	}

	public String getYPQSRHBZRZJLX() {
		return YPQSRHBZRZJLX;
	}

	public void setYPQSRHBZRZJLX(String in) {
		YPQSRHBZRZJLX = in;
	}

	@Column(name = "YPQSRHBZRZJDM", length = 299, nullable = true)
	@IColumn(description="押品权属人或保证人证件代码")
	private String YPQSRHBZRZJDM;

	public String getYPQSRHBZRZJDM() {
		return YPQSRHBZRZJDM;
	}

	public void setYPQSRHBZRZJDM(String in) {
		YPQSRHBZRZJDM = in;
	}

	@Column(name = "YPPGJZHBZJE", length = 20, nullable = true)
	@IColumn(description="押品评估价值或保证金额")
	private String YPPGJZHBZJE;

	public String getYPPGJZHBZJE() {
		return YPPGJZHBZJE;
	}

	public void setYPPGJZHBZJE(String in) {
		YPPGJZHBZJE = in;
	}

	@IColumn(tagMethodName="getYPQSRSFDSFTag",description="押品权属人是否第三方")
	@Column(name = "YPQSRSFDSF", nullable =true)
	private String YPQSRSFDSF;

	public static Map<String,String> getYPQSRSFDSFTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_NEGOT_ATTR_TRDPTY");
	}

	public String getYPQSRSFDSF() {
		return YPQSRSFDSF;
	}

	public void setYPQSRSFDSF(String in) {
		YPQSRSFDSF = in;
	}

	@Column(name = "SCGZRQ", length = 8, nullable = true)
	@IColumn(description="首次估值日期")
	private String SCGZRQ;

	public String getSCGZRQ() {
		return SCGZRQ;
	}

	public void setSCGZRQ(String in) {
		SCGZRQ = in;
	}

	@Column(name = "ZXGZRQ", length = 8, nullable = true)
	@IColumn(description="最新估值日期")
	private String ZXGZRQ;

	public String getZXGZRQ() {
		return ZXGZRQ;
	}

	public void setZXGZRQ(String in) {
		ZXGZRQ = in;
	}

	@Column(name = "GZDQRQ", length = 8, nullable = true)
	@IColumn(description="估值到期日期")
	private String GZDQRQ;

	public String getGZDQRQ() {
		return GZDQRQ;
	}

	public void setGZDQRQ(String in) {
		GZDQRQ = in;
	}

	@Column(name = "BZRBZNLSX", length = 20, nullable = true)
	@IColumn(description="保证人保证能力上限")
	private String BZRBZNLSX;

	public String getBZRBZNLSX() {
		return BZRBZNLSX;
	}

	public void setBZRBZNLSX(String in) {
		BZRBZNLSX = in;
	}

	@Column(name = "SPDZYL", length = 20, nullable = true)
	@IColumn(description="审批抵质押率")
	private String SPDZYL;

	public String getSPDZYL() {
		return SPDZYL;
	}

	public void setSPDZYL(String in) {
		SPDZYL = in;
	}

	@Column(name = "SJDZYL", length = 299, nullable = true)
	@IColumn(description="实际抵质押率")
	private String SJDZYL;

	public String getSJDZYL() {
		return SJDZYL;
	}

	public void setSJDZYL(String in) {
		SJDZYL = in;
	}

	@Column(name = "DBGLLX", length = 299, nullable = true)
	@IColumn(description="担保关联类型")
	private String DBGLLX;

	public String getDBGLLX() {
		return DBGLLX;
	}

	public void setDBGLLX(String in) {
		DBGLLX = in;
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

