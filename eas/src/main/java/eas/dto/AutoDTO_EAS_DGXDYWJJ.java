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
@Table(name = "EAS_DGXDYWJJ")
@IEntity(description= "对公信贷业务借据")
public class AutoDTO_EAS_DGXDYWJJ implements java.io.Serializable{

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

	@Column(name = "XDJJH", length = 299, nullable = true)
	@IColumn(description="信贷借据号")
	private String XDJJH;

	public String getXDJJH() {
		return XDJJH;
	}

	public void setXDJJH(String in) {
		XDJJH = in;
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

	@Column(name = "KHTYBH", length = 299, nullable = true)
	@IColumn(description="客户统一编号")
	private String KHTYBH;

	public String getKHTYBH() {
		return KHTYBH;
	}

	public void setKHTYBH(String in) {
		KHTYBH = in;
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

	@Column(name = "XDHTH", length = 299, nullable = true)
	@IColumn(description="信贷合同号")
	private String XDHTH;

	public String getXDHTH() {
		return XDHTH;
	}

	public void setXDHTH(String in) {
		XDHTH = in;
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

	@Column(name = "JKJE", length = 20, nullable = true)
	@IColumn(description="借款金额")
	private BigDecimal JKJE;

	public BigDecimal getJKJE() {
		return JKJE;
	}

	public void setJKJE(String in) {
		JKJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JKYE", length = 20, nullable = true)
	@IColumn(description="借款余额")
	private BigDecimal JKYE;

	public BigDecimal getJKYE() {
		return JKYE;
	}

	public void setJKYE(String in) {
		JKYE = TypeParse.parseBigDecimal(in);
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

	@Column(name = "ZQCS", nullable = true)
	@IColumn(description="展期次数")
	private Integer ZQCS;

	public Integer getZQCS() {
		return ZQCS;
	}

	public void setZQCS(String in) {
		ZQCS = TypeParse.parseInt(in);
	}

	@Column(name = "ZQS", nullable = true)
	@IColumn(description="总期数")
	private Integer ZQS;

	public Integer getZQS() {
		return ZQS;
	}

	public void setZQS(String in) {
		ZQS = TypeParse.parseInt(in);
	}

	@Column(name = "DQQS", nullable = true)
	@IColumn(description="当前期数")
	private Integer DQQS;

	public Integer getDQQS() {
		return DQQS;
	}

	public void setDQQS(String in) {
		DQQS = TypeParse.parseInt(in);
	}

	@Column(name = "FKFS", length = 299, nullable = true)
	@IColumn(description="放款方式")
	private String FKFS;

	public String getFKFS() {
		return FKFS;
	}

	public void setFKFS(String in) {
		FKFS = in;
	}

	@Column(name = "DKSJFFRQ", length = 8, nullable = true)
	@IColumn(description="贷款实际发放日期")
	private String DKSJFFRQ;

	public String getDKSJFFRQ() {
		return DKSJFFRQ;
	}

	public void setDKSJFFRQ(String in) {
		DKSJFFRQ = in;
	}

	@Column(name = "DKYSDQRQ", length = 8, nullable = true)
	@IColumn(description="贷款原始到期日期")
	private String DKYSDQRQ;

	public String getDKYSDQRQ() {
		return DKYSDQRQ;
	}

	public void setDKYSDQRQ(String in) {
		DKYSDQRQ = in;
	}

	@Column(name = "DKSJDQRQ", length = 8, nullable = true)
	@IColumn(description="贷款实际到期日期")
	private String DKSJDQRQ;

	public String getDKSJDQRQ() {
		return DKSJDQRQ;
	}

	public void setDKSJDQRQ(String in) {
		DKSJDQRQ = in;
	}

	@Column(name = "BNQXYE", length = 20, nullable = true)
	@IColumn(description="表内欠息余额")
	private BigDecimal BNQXYE;

	public BigDecimal getBNQXYE() {
		return BNQXYE;
	}

	public void setBNQXYE(String in) {
		BNQXYE = TypeParse.parseBigDecimal(in);
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

	@Column(name = "DKZT", length = 299, nullable = true)
	@IColumn(description="贷款状态")
	private String DKZT;

	public String getDKZT() {
		return DKZT;
	}

	public void setDKZT(String in) {
		DKZT = in;
	}

	@Column(name = "ZJRQ", length = 8, nullable = true)
	@IColumn(description="终结日期")
	private String ZJRQ;

	public String getZJRQ() {
		return ZJRQ;
	}

	public void setZJRQ(String in) {
		ZJRQ = in;
	}

	@Column(name = "DKLX", length = 299, nullable = true)
	@IColumn(description="贷款类型")
	private String DKLX;

	public String getDKLX() {
		return DKLX;
	}

	public void setDKLX(String in) {
		DKLX = in;
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

	@Column(name = "JZLL", nullable = true)
	@IColumn(description="基准利率(万分之一)")
	private BigDecimal JZLL;

	public BigDecimal getJZLL() {
		return JZLL;
	}

	public void setJZLL(String in) {
		JZLL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "LLFDBL", nullable = true)
	@IColumn(description="利率浮动比例(万分之一)")
	private BigDecimal LLFDBL;

	public BigDecimal getLLFDBL() {
		return LLFDBL;
	}

	public void setLLFDBL(String in) {
		LLFDBL = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getHKFSTag",description="还款方式")
	@Column(name = "HKFS", nullable =true)
	private String HKFS;

	public static Map<String,String> getHKFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_REPAY_MODE");
	}

	public String getHKFS() {
		return HKFS;
	}

	public void setHKFS(String in) {
		HKFS = in;
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

	@Column(name = "HKQD", length = 299, nullable = true)
	@IColumn(description="还款渠道")
	private String HKQD;

	public String getHKQD() {
		return HKQD;
	}

	public void setHKQD(String in) {
		HKQD = in;
	}

	@IColumn(tagMethodName="getJXFSTag",description="计息方式")
	@Column(name = "JXFS", nullable =true)
	private String JXFS;

	public static Map<String,String> getJXFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_INT_MODE");
	}

	public String getJXFS() {
		return JXFS;
	}

	public void setJXFS(String in) {
		JXFS = in;
	}

	@Column(name = "BZJBL", nullable = true)
	@IColumn(description="保证金比例(百分之一)")
	private BigDecimal BZJBL;

	public BigDecimal getBZJBL() {
		return BZJBL;
	}

	public void setBZJBL(String in) {
		BZJBL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BZJBZ", length = 299, nullable = true)
	@IColumn(description="保证金币种")
	private String BZJBZ;

	public String getBZJBZ() {
		return BZJBZ;
	}

	public void setBZJBZ(String in) {
		BZJBZ = in;
	}

	@Column(name = "BZJJE", length = 20, nullable = true)
	@IColumn(description="保证金金额")
	private BigDecimal BZJJE;

	public BigDecimal getBZJJE() {
		return BZJJE;
	}

	public void setBZJJE(String in) {
		BZJJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BZJZH", length = 299, nullable = true)
	@IColumn(description="保证金帐号")
	private String BZJZH;

	public String getBZJZH() {
		return BZJZH;
	}

	public void setBZJZH(String in) {
		BZJZH = in;
	}

	@Column(name = "PJHM", length = 299, nullable = true)
	@IColumn(description="票据号码")
	private String PJHM;

	public String getPJHM() {
		return PJHM;
	}

	public void setPJHM(String in) {
		PJHM = in;
	}

	@Column(name = "PMJE", length = 20, nullable = true)
	@IColumn(description="票面金额")
	private BigDecimal PMJE;

	public BigDecimal getPMJE() {
		return PMJE;
	}

	public void setPMJE(String in) {
		PMJE = TypeParse.parseBigDecimal(in);
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

	@Column(name = "XDYGH", length = 299, nullable = true)
	@IColumn(description="信贷员工号")
	private String XDYGH;

	public String getXDYGH() {
		return XDYGH;
	}

	public void setXDYGH(String in) {
		XDYGH = in;
	}

	@IColumn(tagMethodName="getXZBZTag",description="现转标志")
	@Column(name = "XZBZ", nullable =true)
	private String XZBZ;

	public static Map<String,String> getXZBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CAS_TFR_FLG");
	}

	public String getXZBZ() {
		return XZBZ;
	}

	public void setXZBZ(String in) {
		XZBZ = in;
	}

	@Column(name = "LJQKQS", nullable = true)
	@IColumn(description="累计欠款期数")
	private Integer LJQKQS;

	public Integer getLJQKQS() {
		return LJQKQS;
	}

	public void setLJQKQS(String in) {
		LJQKQS = TypeParse.parseInt(in);
	}

	@Column(name = "LXQKQS", nullable = true)
	@IColumn(description="连续欠款期数")
	private Integer LXQKQS;

	public Integer getLXQKQS() {
		return LXQKQS;
	}

	public void setLXQKQS(String in) {
		LXQKQS = TypeParse.parseInt(in);
	}

	@Column(name = "STZFBZ", length = 299, nullable = true)
	@IColumn(description="受托支付标志")
	private String STZFBZ;

	public String getSTZFBZ() {
		return STZFBZ;
	}

	public void setSTZFBZ(String in) {
		STZFBZ = in;
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

