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
@Table(name = "EAS_XYKZHJYMXB")
@IEntity(description= "信用卡帐户交易明细表")
public class AutoDTO_EAS_XYKZHJYMXB implements java.io.Serializable{

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

	@Column(name = "KH", length = 299, nullable = true)
	@IColumn(description="卡号")
	private String KH;

	public String getKH() {
		return KH;
	}

	public void setKH(String in) {
		KH = in;
	}

	@Column(name = "XYKZH", length = 299, nullable = true)
	@IColumn(description="信用卡账户")
	private String XYKZH;

	public String getXYKZH() {
		return XYKZH;
	}

	public void setXYKZH(String in) {
		XYKZH = in;
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

	@Column(name = "JRXKZH", length = 299, nullable = true)
	@IColumn(description="金融许可证号")
	private String JRXKZH;

	public String getJRXKZH() {
		return JRXKZH;
	}

	public void setJRXKZH(String in) {
		JRXKZH = in;
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

	@Column(name = "JYJZRQ", length = 8, nullable = true)
	@IColumn(description="交易记帐日期")
	private String JYJZRQ;

	public String getJYJZRQ() {
		return JYJZRQ;
	}

	public void setJYJZRQ(String in) {
		JYJZRQ = in;
	}

	@Column(name = "HXJYLSH", length = 299, nullable = true)
	@IColumn(description="核心交易流水号")
	private String HXJYLSH;

	public String getHXJYLSH() {
		return HXJYLSH;
	}

	public void setHXJYLSH(String in) {
		HXJYLSH = in;
	}

	@Column(name = "ZJYLSH", length = 299, nullable = true)
	@IColumn(description="子交易流水号")
	private String ZJYLSH;

	public String getZJYLSH() {
		return ZJYLSH;
	}

	public void setZJYLSH(String in) {
		ZJYLSH = in;
	}

	@Column(name = "BCXH", nullable = true)
	@IColumn(description="笔次序号")
	private Integer BCXH;

	public Integer getBCXH() {
		return BCXH;
	}

	public void setBCXH(String in) {
		BCXH = TypeParse.parseInt(in);
	}

	@Column(name = "KPJYLX", length = 299, nullable = true)
	@IColumn(description="卡片交易类型")
	private String KPJYLX;

	public String getKPJYLX() {
		return KPJYLX;
	}

	public void setKPJYLX(String in) {
		KPJYLX = in;
	}

	@IColumn(tagMethodName="getCHLBTag",description="钞汇类别")
	@Column(name = "CHLB", nullable =true)
	private String CHLB;

	public static Map<String,String> getCHLBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_EXCH_CATE");
	}

	public String getCHLB() {
		return CHLB;
	}

	public void setCHLB(String in) {
		CHLB = in;
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

	@Column(name = "JYPZH", length = 299, nullable = true)
	@IColumn(description="交易凭证号")
	private String JYPZH;

	public String getJYPZH() {
		return JYPZH;
	}

	public void setJYPZH(String in) {
		JYPZH = in;
	}

	@IColumn(tagMethodName="getJDBZTag",description="借贷标志")
	@Column(name = "JDBZ", nullable =true)
	private String JDBZ;

	public static Map<String,String> getJDBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_DC_IND");
	}

	public String getJDBZ() {
		return JDBZ;
	}

	public void setJDBZ(String in) {
		JDBZ = in;
	}

	@Column(name = "JYJE", length = 20, nullable = true)
	@IColumn(description="交易金额")
	private BigDecimal JYJE;

	public BigDecimal getJYJE() {
		return JYJE;
	}

	public void setJYJE(String in) {
		JYJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "KHSXFJE", length = 20, nullable = true)
	@IColumn(description="客户手续费金额")
	private BigDecimal KHSXFJE;

	public BigDecimal getKHSXFJE() {
		return KHSXFJE;
	}

	public void setKHSXFJE(String in) {
		KHSXFJE = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getQKGHCXTag",description="欠款归还次序")
	@Column(name = "QKGHCX", nullable =true)
	private String QKGHCX;

	public static Map<String,String> getQKGHCXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_OWE_MONEY_RTN");
	}

	public String getQKGHCX() {
		return QKGHCX;
	}

	public void setQKGHCX(String in) {
		QKGHCX = in;
	}

	@Column(name = "JYQXRQ", length = 8, nullable = true)
	@IColumn(description="交易起息日期")
	private String JYQXRQ;

	public String getJYQXRQ() {
		return JYQXRQ;
	}

	public void setJYQXRQ(String in) {
		JYQXRQ = in;
	}

	@Column(name = "JYFSRQ", length = 8, nullable = true)
	@IColumn(description="交易发生日期")
	private String JYFSRQ;

	public String getJYFSRQ() {
		return JYFSRQ;
	}

	public void setJYFSRQ(String in) {
		JYFSRQ = in;
	}

	@Column(name = "JYZDRQ", length = 8, nullable = true)
	@IColumn(description="交易帐单日期")
	private String JYZDRQ;

	public String getJYZDRQ() {
		return JYZDRQ;
	}

	public void setJYZDRQ(String in) {
		JYZDRQ = in;
	}

	@Column(name = "DFZH", length = 299, nullable = true)
	@IColumn(description="对方账号")
	private String DFZH;

	public String getDFZH() {
		return DFZH;
	}

	public void setDFZH(String in) {
		DFZH = in;
	}

	@IColumn(tagMethodName="getFQFKBZTag",description="分期付款标志")
	@Column(name = "FQFKBZ", nullable =true)
	private String FQFKBZ;

	public static Map<String,String> getFQFKBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_AMORT_PAY_IND");
	}

	public String getFQFKBZ() {
		return FQFKBZ;
	}

	public void setFQFKBZ(String in) {
		FQFKBZ = in;
	}

	@IColumn(tagMethodName="getJYQDTag",description="交易渠道")
	@Column(name = "JYQD", nullable =true)
	private String JYQD;

	public static Map<String,String> getJYQDTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_TX_CHAN");
	}

	public String getJYQD() {
		return JYQD;
	}

	public void setJYQD(String in) {
		JYQD = in;
	}

	@Column(name = "JYSJ", length = 299, nullable = true)
	@IColumn(description="交易时间")
	private String JYSJ;

	public String getJYSJ() {
		return JYSJ;
	}

	public void setJYSJ(String in) {
		JYSJ = in;
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

	@Column(name = "JYJGH", length = 299, nullable = true)
	@IColumn(description="交易机构号")
	private String JYJGH;

	public String getJYJGH() {
		return JYJGH;
	}

	public void setJYJGH(String in) {
		JYJGH = in;
	}

	@IColumn(tagMethodName="getGZZXZTag",description="工作站性质")
	@Column(name = "GZZXZ", nullable =true)
	private String GZZXZ;

	public static Map<String,String> getGZZXZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_WORKSTATINO_CHAR");
	}

	public String getGZZXZ() {
		return GZZXZ;
	}

	public void setGZZXZ(String in) {
		GZZXZ = in;
	}

	@Column(name = "GZZBH", length = 299, nullable = true)
	@IColumn(description="工作站编号")
	private String GZZBH;

	public String getGZZBH() {
		return GZZBH;
	}

	public void setGZZBH(String in) {
		GZZBH = in;
	}

	@Column(name = "ZY", length = 500, nullable = true)
	@IColumn(description="摘要")
	private String ZY;

	public String getZY() {
		return ZY;
	}

	public void setZY(String in) {
		ZY = in;
	}

	@Column(name = "BZH", length = 299, nullable = true)
	@IColumn(description="备注")
	private String BZH;

	public String getBZH() {
		return BZH;
	}

	public void setBZH(String in) {
		BZH = in;
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

