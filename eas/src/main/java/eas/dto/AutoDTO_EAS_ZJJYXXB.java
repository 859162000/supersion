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
@Table(name = "EAS_ZJJYXXB")
@IEntity(description= "资金交易信息表")
public class AutoDTO_EAS_ZJJYXXB implements java.io.Serializable{

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

	@Column(name = "JYBH", length = 299, nullable = true)
	@IColumn(description="交易编号")
	private String JYBH;

	public String getJYBH() {
		return JYBH;
	}

	public void setJYBH(String in) {
		JYBH = in;
	}

	@Column(name = "WBXTJYBH", length = 299, nullable = true)
	@IColumn(description="外部系统交易编号")
	private String WBXTJYBH;

	public String getWBXTJYBH() {
		return WBXTJYBH;
	}

	public void setWBXTJYBH(String in) {
		WBXTJYBH = in;
	}

	@Column(name = "JYLX", length = 299, nullable = true)
	@IColumn(description="资金交易类型")
	private String JYLX;

	public String getJYLX() {
		return JYLX;
	}

	public void setJYLX(String in) {
		JYLX = in;
	}

	@Column(name = "JYZL", length = 299, nullable = true)
	@IColumn(description="资金交易子类")
	private String JYZL;

	public String getJYZL() {
		return JYZL;
	}

	public void setJYZL(String in) {
		JYZL = in;
	}

	@Column(name = "JRGJBH", length = 299, nullable = true)
	@IColumn(description="金融工具编号")
	private String JRGJBH;

	public String getJRGJBH() {
		return JRGJBH;
	}

	public void setJRGJBH(String in) {
		JRGJBH = in;
	}

	@Column(name = "JYZHLX", length = 299, nullable = true)
	@IColumn(description="账户类型")
	private String JYZHLX;

	public String getJYZHLX() {
		return JYZHLX;
	}

	public void setJYZHLX(String in) {
		JYZHLX = in;
	}

	@Column(name = "HTH", length = 299, nullable = true)
	@IColumn(description="合同号")
	private String HTH;

	public String getHTH() {
		return HTH;
	}

	public void setHTH(String in) {
		HTH = in;
	}

	@Column(name = "HTJE", length = 20, nullable = true)
	@IColumn(description="合同金额")
	private BigDecimal HTJE;

	public BigDecimal getHTJE() {
		return HTJE;
	}

	public void setHTJE(String in) {
		HTJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JYGY", length = 299, nullable = true)
	@IColumn(description="交易柜员")
	private String JYGY;

	public String getJYGY() {
		return JYGY;
	}

	public void setJYGY(String in) {
		JYGY = in;
	}

	@Column(name = "SPR", length = 299, nullable = true)
	@IColumn(description="审批人")
	private String SPR;

	public String getSPR() {
		return SPR;
	}

	public void setSPR(String in) {
		SPR = in;
	}

	@Column(name = "JYDSDM", length = 299, nullable = true)
	@IColumn(description="交易对手代码")
	private String JYDSDM;

	public String getJYDSDM() {
		return JYDSDM;
	}

	public void setJYDSDM(String in) {
		JYDSDM = in;
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

	@Column(name = "JYRQ", length = 8, nullable = true)
	@IColumn(description="交易日期")
	private String JYRQ;

	public String getJYRQ() {
		return JYRQ;
	}

	public void setJYRQ(String in) {
		JYRQ = in;
	}

	@Column(name = "DQRQ", length = 8, nullable = true)
	@IColumn(description="到期日期")
	private String DQRQ;

	public String getDQRQ() {
		return DQRQ;
	}

	public void setDQRQ(String in) {
		DQRQ = in;
	}

	@Column(name = "MMBZ", length = 299, nullable = true)
	@IColumn(description="买卖标志")
	private String MMBZ;

	public String getMMBZ() {
		return MMBZ;
	}

	public void setMMBZ(String in) {
		MMBZ = in;
	}

	@Column(name = "JYQBZ", length = 299, nullable = true)
	@IColumn(description="即远期标志")
	private String JYQBZ;

	public String getJYQBZ() {
		return JYQBZ;
	}

	public void setJYQBZ(String in) {
		JYQBZ = in;
	}

	@Column(name = "MRBZ", length = 299, nullable = true)
	@IColumn(description="买入币种")
	private String MRBZ;

	public String getMRBZ() {
		return MRBZ;
	}

	public void setMRBZ(String in) {
		MRBZ = in;
	}

	@Column(name = "MRJE", length = 20, nullable = true)
	@IColumn(description="买入金额")
	private BigDecimal MRJE;

	public BigDecimal getMRJE() {
		return MRJE;
	}

	public void setMRJE(String in) {
		MRJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "MCBZ", length = 299, nullable = true)
	@IColumn(description="卖出币种")
	private String MCBZ;

	public String getMCBZ() {
		return MCBZ;
	}

	public void setMCBZ(String in) {
		MCBZ = in;
	}

	@Column(name = "MCJE", length = 20, nullable = true)
	@IColumn(description="卖出金额")
	private BigDecimal MCJE;

	public BigDecimal getMCJE() {
		return MCJE;
	}

	public void setMCJE(String in) {
		MCJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CJHL", length = 299, nullable = true)
	@IColumn(description="成交汇率")
	private BigDecimal CJHL;

	public BigDecimal getCJHL() {
		return CJHL;
	}

	public void setCJHL(String in) {
		CJHL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "YWZT", length = 299, nullable = true)
	@IColumn(description="交易状态")
	private String YWZT;

	public String getYWZT() {
		return YWZT;
	}

	public void setYWZT(String in) {
		YWZT = in;
	}

	@Column(name = "FHRQ", length = 8, nullable = true)
	@IColumn(description="复核日期")
	private String FHRQ;

	public String getFHRQ() {
		return FHRQ;
	}

	public void setFHRQ(String in) {
		FHRQ = in;
	}

	@Column(name = "QXRQ", length = 8, nullable = true)
	@IColumn(description="取消日期")
	private String QXRQ;

	public String getQXRQ() {
		return QXRQ;
	}

	public void setQXRQ(String in) {
		QXRQ = in;
	}

	@Column(name = "SJJGRQ", length = 8, nullable = true)
	@IColumn(description="实际交割日期")
	private String SJJGRQ;

	public String getSJJGRQ() {
		return SJJGRQ;
	}

	public void setSJJGRQ(String in) {
		SJJGRQ = in;
	}

	@Column(name = "QSBZ", length = 299, nullable = true)
	@IColumn(description="清算标志")
	private String QSBZ;

	public String getQSBZ() {
		return QSBZ;
	}

	public void setQSBZ(String in) {
		QSBZ = in;
	}

	@Column(name = "JFZH", length = 299, nullable = true)
	@IColumn(description="借方账号")
	private String JFZH;

	public String getJFZH() {
		return JFZH;
	}

	public void setJFZH(String in) {
		JFZH = in;
	}

	@Column(name = "DFZH", length = 299, nullable = true)
	@IColumn(description="贷方账号")
	private String DFZH;

	public String getDFZH() {
		return DFZH;
	}

	public void setDFZH(String in) {
		DFZH = in;
	}

	@Column(name = "JFJE", length = 20, nullable = true)
	@IColumn(description="借方金额")
	private BigDecimal JFJE;

	public BigDecimal getJFJE() {
		return JFJE;
	}

	public void setJFJE(String in) {
		JFJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DFJE", length = 20, nullable = true)
	@IColumn(description="贷方金额")
	private BigDecimal DFJE;

	public BigDecimal getDFJE() {
		return DFJE;
	}

	public void setDFJE(String in) {
		DFJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JFBZ", length = 299, nullable = true)
	@IColumn(description="借方币种")
	private String JFBZ;

	public String getJFBZ() {
		return JFBZ;
	}

	public void setJFBZ(String in) {
		JFBZ = in;
	}

	@Column(name = "DFBZ", length = 299, nullable = true)
	@IColumn(description="贷方币种")
	private String DFBZ;

	public String getDFBZ() {
		return DFBZ;
	}

	public void setDFBZ(String in) {
		DFBZ = in;
	}

	@Column(name = "JFLL", length = 299, nullable = true)
	@IColumn(description="借方利率")
	private BigDecimal JFLL;

	public BigDecimal getJFLL() {
		return JFLL;
	}

	public void setJFLL(String in) {
		JFLL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DFLL", length = 299, nullable = true)
	@IColumn(description="贷方利率")
	private BigDecimal DFLL;

	public BigDecimal getDFLL() {
		return DFLL;
	}

	public void setDFLL(String in) {
		DFLL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BZJJYBZ", length = 299, nullable = true)
	@IColumn(description="保证金交易标志")
	private String BZJJYBZ;

	public String getBZJJYBZ() {
		return BZJJYBZ;
	}

	public void setBZJJYBZ(String in) {
		BZJJYBZ = in;
	}

	@Column(name = "GLBZJZH", length = 299, nullable = true)
	@IColumn(description="关联保证金账户")
	private String GLBZJZH;

	public String getGLBZJZH() {
		return GLBZJZH;
	}

	public void setGLBZJZH(String in) {
		GLBZJZH = in;
	}

	@Column(name = "GLYWBH", length = 299, nullable = true)
	@IColumn(description="关联业务编号")
	private String GLYWBH;

	public String getGLYWBH() {
		return GLYWBH;
	}

	public void setGLYWBH(String in) {
		GLYWBH = in;
	}

	@Column(name = "WBGLXTMC", length = 299, nullable = true)
	@IColumn(description="外部关联系统名称")
	private String WBGLXTMC;

	public String getWBGLXTMC() {
		return WBGLXTMC;
	}

	public void setWBGLXTMC(String in) {
		WBGLXTMC = in;
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

