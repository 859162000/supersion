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
@Table(name = "EAS_XDHTB")
@IEntity(description= "信贷合同表")
public class AutoDTO_EAS_XDHTB implements java.io.Serializable{

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

	@Column(name = "ZHTH", length = 299, nullable = true)
	@IColumn(description="主合同号")
	private String ZHTH;

	public String getZHTH() {
		return ZHTH;
	}

	public void setZHTH(String in) {
		ZHTH = in;
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

	@Column(name = "KHMC", length = 299, nullable = true)
	@IColumn(description="客户名称")
	private String KHMC;

	public String getKHMC() {
		return KHMC;
	}

	public void setKHMC(String in) {
		KHMC = in;
	}

	@Column(name = "CPMC", length = 299, nullable = true)
	@IColumn(description="产品名称")
	private String CPMC;

	public String getCPMC() {
		return CPMC;
	}

	public void setCPMC(String in) {
		CPMC = in;
	}

	@IColumn(tagMethodName="getDKXGZLTag",description="贷款新规种类")
	@Column(name = "DKXGZL", nullable =true)
	private String DKXGZL;

	public static Map<String,String> getDKXGZLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LOAN_NEW_TYPE");
	}

	public String getDKXGZL() {
		return DKXGZL;
	}

	public void setDKXGZL(String in) {
		DKXGZL = in;
	}

	@IColumn(tagMethodName="getXDYWZLTag",description="信贷业务种类")
	@Column(name = "XDYWZL", nullable =true)
	private String XDYWZL;

	public static Map<String,String> getXDYWZLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CRDT_BIZ_TYPE");
	}

	public String getXDYWZL() {
		return XDYWZL;
	}

	public void setXDYWZL(String in) {
		XDYWZL = in;
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

	@Column(name = "JE", length = 20, nullable = true)
	@IColumn(description="金额")
	private BigDecimal JE;

	public BigDecimal getJE() {
		return JE;
	}

	public void setJE(String in) {
		JE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "HTYDRQ", length = 8, nullable = true)
	@IColumn(description="合同约定日期")
	private String HTYDRQ;

	public String getHTYDRQ() {
		return HTYDRQ;
	}

	public void setHTYDRQ(String in) {
		HTYDRQ = in;
	}

	@Column(name = "HTDQRQ", length = 8, nullable = true)
	@IColumn(description="合同到期日期")
	private String HTDQRQ;

	public String getHTDQRQ() {
		return HTDQRQ;
	}

	public void setHTDQRQ(String in) {
		HTDQRQ = in;
	}

	@Column(name = "LL", nullable = true)
	@IColumn(description="利率(万分之一)")
	private BigDecimal LL;

	public BigDecimal getLL() {
		return LL;
	}

	public void setLL(String in) {
		LL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "LLFD", nullable = true)
	@IColumn(description="利率浮动(万分之一)")
	private BigDecimal LLFD;

	public BigDecimal getLLFD() {
		return LLFD;
	}

	public void setLLFD(String in) {
		LLFD = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getZYDBFSTag",description="主要担保方式")
	@Column(name = "ZYDBFS", nullable =true)
	private String ZYDBFS;

	public static Map<String,String> getZYDBFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_PRI_GUAR_MODE");
	}

	public String getZYDBFS() {
		return ZYDBFS;
	}

	public void setZYDBFS(String in) {
		ZYDBFS = in;
	}

	@Column(name = "GHJLGH", length = 299, nullable = true)
	@IColumn(description="客户经理工号")
	private String GHJLGH;

	public String getGHJLGH() {
		return GHJLGH;
	}

	public void setGHJLGH(String in) {
		GHJLGH = in;
	}

	@Column(name = "DKQX", nullable = true)
	@IColumn(description="贷款期限")
	private Integer DKQX;

	public Integer getDKQX() {
		return DKQX;
	}

	public void setDKQX(String in) {
		DKQX = TypeParse.parseInt(in);
	}

	@IColumn(tagMethodName="getFKFSTag",description="放款方式")
	@Column(name = "FKFS", nullable =true)
	private String FKFS;

	public static Map<String,String> getFKFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_DISTR_MODE");
	}

	public String getFKFS() {
		return FKFS;
	}

	public void setFKFS(String in) {
		FKFS = in;
	}

	@Column(name = "ZJLY", length = 299, nullable = true)
	@IColumn(description="资金来源")
	private String ZJLY;

	public String getZJLY() {
		return ZJLY;
	}

	public void setZJLY(String in) {
		ZJLY = in;
	}

	@Column(name = "DKYT", length = 299, nullable = true)
	@IColumn(description="贷款用途")
	private String DKYT;

	public String getDKYT() {
		return DKYT;
	}

	public void setDKYT(String in) {
		DKYT = in;
	}

	@Column(name = "DKTXXY", length = 299, nullable = true)
	@IColumn(description="贷款投向行业")
	private String DKTXXY;

	public String getDKTXXY() {
		return DKTXXY;
	}

	public void setDKTXXY(String in) {
		DKTXXY = in;
	}

	@IColumn(tagMethodName="getSFGJXZXYTag",description="是否国家限制行业")
	@Column(name = "SFGJXZXY", nullable =true)
	private String SFGJXZXY;

	public static Map<String,String> getSFGJXZXYTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LIMT_INDUST_TAG");
	}

	public String getSFGJXZXY() {
		return SFGJXZXY;
	}

	public void setSFGJXZXY(String in) {
		SFGJXZXY = in;
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

