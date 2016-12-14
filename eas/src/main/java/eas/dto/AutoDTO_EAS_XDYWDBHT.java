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
@Table(name = "EAS_XDYWDBHT")
@IEntity(description= "信贷业务担保合同")
public class AutoDTO_EAS_XDYWDBHT implements java.io.Serializable{

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

	@Column(name = "DBHTH", length = 299, nullable = true)
	@IColumn(description="担保合同号")
	private String DBHTH;

	public String getDBHTH() {
		return DBHTH;
	}

	public void setDBHTH(String in) {
		DBHTH = in;
	}

	@Column(name = "BZRKHTYBH", length = 299, nullable = true)
	@IColumn(description="保证人客户统一编号")
	private String BZRKHTYBH;

	public String getBZRKHTYBH() {
		return BZRKHTYBH;
	}

	public void setBZRKHTYBH(String in) {
		BZRKHTYBH = in;
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

	@Column(name = "YXJGDM", length = 299, nullable = true)
	@IColumn(description="银行机构代码")
	private String YXJGDM;

	public String getYXJGDM() {
		return YXJGDM;
	}

	public void setYXJGDM(String in) {
		YXJGDM = in;
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

	@IColumn(tagMethodName="getDBLXTag",description="担保类型")
	@Column(name = "DBLX", nullable =true)
	private String DBLX;

	public static Map<String,String> getDBLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_GUAR_TYPE");
	}

	public String getDBLX() {
		return DBLX;
	}

	public void setDBLX(String in) {
		DBLX = in;
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

	@IColumn(tagMethodName="getBZRLBTag",description="保证人类别")
	@Column(name = "BZRLB", nullable =true)
	private String BZRLB;

	public static Map<String,String> getBZRLBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_GUAR_PERSON_CATE");
	}

	public String getBZRLB() {
		return BZRLB;
	}

	public void setBZRLB(String in) {
		BZRLB = in;
	}

	@Column(name = "BZRMC", length = 299, nullable = true)
	@IColumn(description="保证人名称")
	private String BZRMC;

	public String getBZRMC() {
		return BZRMC;
	}

	public void setBZRMC(String in) {
		BZRMC = in;
	}

	@IColumn(tagMethodName="getZJLBTag",description="证件类别")
	@Column(name = "ZJLB", nullable =true)
	private String ZJLB;

	public static Map<String,String> getZJLBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CERT_CATE");
	}

	public String getZJLB() {
		return ZJLB;
	}

	public void setZJLB(String in) {
		ZJLB = in;
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

	@Column(name = "BZRJZC", length = 20, nullable = true)
	@IColumn(description="保证人净资产")
	private BigDecimal BZRJZC;

	public BigDecimal getBZRJZC() {
		return BZRJZC;
	}

	public void setBZRJZC(String in) {
		BZRJZC = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DBQSRQ", length = 8, nullable = true)
	@IColumn(description="担保起始日期")
	private String DBQSRQ;

	public String getDBQSRQ() {
		return DBQSRQ;
	}

	public void setDBQSRQ(String in) {
		DBQSRQ = in;
	}

	@Column(name = "DBDQRQ", length = 8, nullable = true)
	@IColumn(description="担保到期日期")
	private String DBDQRQ;

	public String getDBDQRQ() {
		return DBDQRQ;
	}

	public void setDBDQRQ(String in) {
		DBDQRQ = in;
	}

	@IColumn(tagMethodName="getDBHTZTTag",description="担保合同状态")
	@Column(name = "DBHTZT", nullable =true)
	private String DBHTZT;

	public static Map<String,String> getDBHTZTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_GUAR_CONTR_STATUS");
	}

	public String getDBHTZT() {
		return DBHTZT;
	}

	public void setDBHTZT(String in) {
		DBHTZT = in;
	}

	@Column(name = "DBHTQDRQ", length = 8, nullable = true)
	@IColumn(description="担保合同签订日期")
	private String DBHTQDRQ;

	public String getDBHTQDRQ() {
		return DBHTQDRQ;
	}

	public void setDBHTQDRQ(String in) {
		DBHTQDRQ = in;
	}

	@Column(name = "DBHTSXRQ", length = 8, nullable = true)
	@IColumn(description="担保合同生效日期")
	private String DBHTSXRQ;

	public String getDBHTSXRQ() {
		return DBHTSXRQ;
	}

	public void setDBHTSXRQ(String in) {
		DBHTSXRQ = in;
	}

	@Column(name = "DBHTDQRQ", length = 8, nullable = true)
	@IColumn(description="担保合同到期日期")
	private String DBHTDQRQ;

	public String getDBHTDQRQ() {
		return DBHTDQRQ;
	}

	public void setDBHTDQRQ(String in) {
		DBHTDQRQ = in;
	}

	@Column(name = "DBBZ", length = 299, nullable = true)
	@IColumn(description="担保币种")
	private String DBBZ;

	public String getDBBZ() {
		return DBBZ;
	}

	public void setDBBZ(String in) {
		DBBZ = in;
	}

	@Column(name = "DBZJE", length = 20, nullable = true)
	@IColumn(description="担保总金额")
	private BigDecimal DBZJE;

	public BigDecimal getDBZJE() {
		return DBZJE;
	}

	public void setDBZJE(String in) {
		DBZJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DBWPXH", length = 299, nullable = true)
	@IColumn(description="担保物品顺序")
	private String DBWPXH;

	public String getDBWPXH() {
		return DBWPXH;
	}

	public void setDBWPXH(String in) {
		DBWPXH = in;
	}

	@Column(name = "JLYGH", length = 299, nullable = true)
	@IColumn(description="建立员工号")
	private String JLYGH;

	public String getJLYGH() {
		return JLYGH;
	}

	public void setJLYGH(String in) {
		JLYGH = in;
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

