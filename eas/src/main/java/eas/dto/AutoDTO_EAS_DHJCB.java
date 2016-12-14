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
@Table(name = "EAS_DHJCB")
@IEntity(description= "贷后检查表")
public class AutoDTO_EAS_DHJCB implements java.io.Serializable{

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

	@Column(name = "JCFSRQ", length = 8, nullable = true)
	@IColumn(description="检查发生日期")
	private String JCFSRQ;

	public String getJCFSRQ() {
		return JCFSRQ;
	}

	public void setJCFSRQ(String in) {
		JCFSRQ = in;
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

	@Column(name = "BQCWBBRQ", length = 8, nullable = true)
	@IColumn(description="本期财务报表日期")
	private String BQCWBBRQ;

	public String getBQCWBBRQ() {
		return BQCWBBRQ;
	}

	public void setBQCWBBRQ(String in) {
		BQCWBBRQ = in;
	}

	@Column(name = "SQCWBBRQ", length = 8, nullable = true)
	@IColumn(description="上期财务报表日期")
	private String SQCWBBRQ;

	public String getSQCWBBRQ() {
		return SQCWBBRQ;
	}

	public void setSQCWBBRQ(String in) {
		SQCWBBRQ = in;
	}

	@Column(name = "YWPZ", length = 299, nullable = true)
	@IColumn(description="业务品种")
	private String YWPZ;

	public String getYWPZ() {
		return YWPZ;
	}

	public void setYWPZ(String in) {
		YWPZ = in;
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

	@Column(name = "DKHTYDFFRQ", length = 8, nullable = true)
	@IColumn(description="贷款合同约定发放日期")
	private String DKHTYDFFRQ;

	public String getDKHTYDFFRQ() {
		return DKHTYDFFRQ;
	}

	public void setDKHTYDFFRQ(String in) {
		DKHTYDFFRQ = in;
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

	@Column(name = "DKYT", length = 299, nullable = true)
	@IColumn(description="贷款用途")
	private String DKYT;

	public String getDKYT() {
		return DKYT;
	}

	public void setDKYT(String in) {
		DKYT = in;
	}

	@Column(name = "JCDD", length = 299, nullable = true)
	@IColumn(description="检查地点")
	private String JCDD;

	public String getJCDD() {
		return JCDD;
	}

	public void setJCDD(String in) {
		JCDD = in;
	}

	@Column(name = "JCNR", length = 299, nullable = true)
	@IColumn(description="检查内容")
	private String JCNR;

	public String getJCNR() {
		return JCNR;
	}

	public void setJCNR(String in) {
		JCNR = in;
	}

	@Column(name = "YJRYXM", length = 299, nullable = true)
	@IColumn(description="约见人员姓名")
	private String YJRYXM;

	public String getYJRYXM() {
		return YJRYXM;
	}

	public void setYJRYXM(String in) {
		YJRYXM = in;
	}

	@Column(name = "YJRYZW", length = 299, nullable = true)
	@IColumn(description="约见人员职务")
	private String YJRYZW;

	public String getYJRYZW() {
		return YJRYZW;
	}

	public void setYJRYZW(String in) {
		YJRYZW = in;
	}

	@Column(name = "YYZZNJRQ", length = 8, nullable = true)
	@IColumn(description="营业执照年检日期")
	private String YYZZNJRQ;

	public String getYYZZNJRQ() {
		return YYZZNJRQ;
	}

	public void setYYZZNJRQ(String in) {
		YYZZNJRQ = in;
	}

	@Column(name = "SWDJZNJRQ", length = 8, nullable = true)
	@IColumn(description="税务登记证年检日期")
	private String SWDJZNJRQ;

	public String getSWDJZNJRQ() {
		return SWDJZNJRQ;
	}

	public void setSWDJZNJRQ(String in) {
		SWDJZNJRQ = in;
	}

	@Column(name = "DKKNJRQ", length = 8, nullable = true)
	@IColumn(description="贷款卡年检日期")
	private String DKKNJRQ;

	public String getDKKNJRQ() {
		return DKKNJRQ;
	}

	public void setDKKNJRQ(String in) {
		DKKNJRQ = in;
	}

	@Column(name = "HBAQSCNJRQ", length = 8, nullable = true)
	@IColumn(description="环保、安全生产年检日期")
	private String HBAQSCNJRQ;

	public String getHBAQSCNJRQ() {
		return HBAQSCNJRQ;
	}

	public void setHBAQSCNJRQ(String in) {
		HBAQSCNJRQ = in;
	}

	@Column(name = "XKZNJRQ", length = 8, nullable = true)
	@IColumn(description="许可证年检日期")
	private String XKZNJRQ;

	public String getXKZNJRQ() {
		return XKZNJRQ;
	}

	public void setXKZNJRQ(String in) {
		XKZNJRQ = in;
	}

	@Column(name = "SXCKYE", length = 20, nullable = true)
	@IColumn(description="授信敞口余额")
	private BigDecimal SXCKYE;

	public BigDecimal getSXCKYE() {
		return SXCKYE;
	}

	public void setSXCKYE(String in) {
		SXCKYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKCKYE", length = 20, nullable = true)
	@IColumn(description="贷款敞口余额")
	private BigDecimal DKCKYE;

	public BigDecimal getDKCKYE() {
		return DKCKYE;
	}

	public void setDKCKYE(String in) {
		DKCKYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "YPCKYE", length = 20, nullable = true)
	@IColumn(description="银票敞口余额")
	private BigDecimal YPCKYE;

	public BigDecimal getYPCKYE() {
		return YPCKYE;
	}

	public void setYPCKYE(String in) {
		YPCKYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SPTXCKYE", length = 20, nullable = true)
	@IColumn(description="商票贴现（保贴）敞口余额")
	private BigDecimal SPTXCKYE;

	public BigDecimal getSPTXCKYE() {
		return SPTXCKYE;
	}

	public void setSPTXCKYE(String in) {
		SPTXCKYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "MYRZCKYE", length = 20, nullable = true)
	@IColumn(description="贸易融资（含开证）敞口余额")
	private BigDecimal MYRZCKYE;

	public BigDecimal getMYRZCKYE() {
		return MYRZCKYE;
	}

	public void setMYRZCKYE(String in) {
		MYRZCKYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BHCKYE", length = 20, nullable = true)
	@IColumn(description="保函敞口余额")
	private BigDecimal BHCKYE;

	public BigDecimal getBHCKYE() {
		return BHCKYE;
	}

	public void setBHCKYE(String in) {
		BHCKYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QTCKYE", length = 20, nullable = true)
	@IColumn(description="其他敞口余额")
	private BigDecimal QTCKYE;

	public BigDecimal getQTCKYE() {
		return QTCKYE;
	}

	public void setQTCKYE(String in) {
		QTCKYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XMTZJE", length = 20, nullable = true)
	@IColumn(description="项目投资金额")
	private BigDecimal XMTZJE;

	public BigDecimal getXMTZJE() {
		return XMTZJE;
	}

	public void setXMTZJE(String in) {
		XMTZJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZBJDWJE", length = 20, nullable = true)
	@IColumn(description="资本金到位金额")
	private BigDecimal ZBJDWJE;

	public BigDecimal getZBJDWJE() {
		return ZBJDWJE;
	}

	public void setZBJDWJE(String in) {
		ZBJDWJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKDWJE", length = 20, nullable = true)
	@IColumn(description="贷款到位金额")
	private BigDecimal DKDWJE;

	public BigDecimal getDKDWJE() {
		return DKDWJE;
	}

	public void setDKDWJE(String in) {
		DKDWJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QTZJDWJE", length = 20, nullable = true)
	@IColumn(description="其他资金到位金额")
	private BigDecimal QTZJDWJE;

	public BigDecimal getQTZJDWJE() {
		return QTZJDWJE;
	}

	public void setQTZJDWJE(String in) {
		QTZJDWJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XMZBJBL", nullable = true)
	@IColumn(description="项目资本金比例(百分之一)")
	private BigDecimal XMZBJBL;

	public BigDecimal getXMZBJBL() {
		return XMZBJBL;
	}

	public void setXMZBJBL(String in) {
		XMZBJBL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZBJDWBL", nullable = true)
	@IColumn(description="资本金到位比例(百分之一)")
	private BigDecimal ZBJDWBL;

	public BigDecimal getZBJDWBL() {
		return ZBJDWBL;
	}

	public void setZBJDWBL(String in) {
		ZBJDWBL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKDWBL", nullable = true)
	@IColumn(description="贷款到位比例(百分之一)")
	private BigDecimal DKDWBL;

	public BigDecimal getDKDWBL() {
		return DKDWBL;
	}

	public void setDKDWBL(String in) {
		DKDWBL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QTZJDWBL", nullable = true)
	@IColumn(description="其他资金到位比例(百分之一)")
	private BigDecimal QTZJDWBL;

	public BigDecimal getQTZJDWBL() {
		return QTZJDWBL;
	}

	public void setQTZJDWBL(String in) {
		QTZJDWBL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XMZJZTPJ", length = 500, nullable = true)
	@IColumn(description="项目资金总体评价")
	private String XMZJZTPJ;

	public String getXMZJZTPJ() {
		return XMZJZTPJ;
	}

	public void setXMZJZTPJ(String in) {
		XMZJZTPJ = in;
	}

	@Column(name = "XMJD", nullable = true)
	@IColumn(description="项目进度")
	private BigDecimal XMJD;

	public BigDecimal getXMJD() {
		return XMJD;
	}

	public void setXMJD(String in) {
		XMJD = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XMJSQKPJ", length = 500, nullable = true)
	@IColumn(description="项目建设情况评价")
	private String XMJSQKPJ;

	public String getXMJSQKPJ() {
		return XMJSQKPJ;
	}

	public void setXMJSQKPJ(String in) {
		XMJSQKPJ = in;
	}

	@Column(name = "XMKXSMJ", length = 20, nullable = true)
	@IColumn(description="项目可销售面积")
	private BigDecimal XMKXSMJ;

	public BigDecimal getXMKXSMJ() {
		return XMKXSMJ;
	}

	public void setXMKXSMJ(String in) {
		XMKXSMJ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XMYXSMJ", length = 20, nullable = true)
	@IColumn(description="项目已销售面积")
	private BigDecimal XMYXSMJ;

	public BigDecimal getXMYXSMJ() {
		return XMYXSMJ;
	}

	public void setXMYXSMJ(String in) {
		XMYXSMJ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XSJJ", length = 20, nullable = true)
	@IColumn(description="销售均价")
	private BigDecimal XSJJ;

	public BigDecimal getXSJJ() {
		return XSJJ;
	}

	public void setXSJJ(String in) {
		XSJJ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XMXSQK", length = 500, nullable = true)
	@IColumn(description="项目销售情况")
	private String XMXSQK;

	public String getXMXSQK() {
		return XMXSQK;
	}

	public void setXMXSQK(String in) {
		XMXSQK = in;
	}

	@Column(name = "KHJLGH", length = 299, nullable = true)
	@IColumn(description="客户经理工号")
	private String KHJLGH;

	public String getKHJLGH() {
		return KHJLGH;
	}

	public void setKHJLGH(String in) {
		KHJLGH = in;
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

