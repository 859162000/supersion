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
@Table(name = "EAS_XYKXX")
@IEntity(description= "信用卡信息")
public class AutoDTO_EAS_XYKXX implements java.io.Serializable{

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

	@Column(name = "KPMC", length = 299, nullable = true)
	@IColumn(description="卡片名称")
	private String KPMC;

	public String getKPMC() {
		return KPMC;
	}

	public void setKPMC(String in) {
		KPMC = in;
	}

	@Column(name = "KPJB", length = 299, nullable = true)
	@IColumn(description="卡片级别")
	private String KPJB;

	public String getKPJB() {
		return KPJB;
	}

	public void setKPJB(String in) {
		KPJB = in;
	}

	@IColumn(tagMethodName="getKPZTTag",description="卡片状态")
	@Column(name = "KPZT", nullable =true)
	private String KPZT;

	public static Map<String,String> getKPZTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CARD_STATUS");
	}

	public String getKPZT() {
		return KPZT;
	}

	public void setKPZT(String in) {
		KPZT = in;
	}

	@Column(name = "KDQNY", length = 299, nullable = true)
	@IColumn(description="卡到期年月")
	private String KDQNY;

	public String getKDQNY() {
		return KDQNY;
	}

	public void setKDQNY(String in) {
		KDQNY = in;
	}

	@Column(name = "KHZHJYRQ", length = 8, nullable = true)
	@IColumn(description="客户最后交易日期")
	private String KHZHJYRQ;

	public String getKHZHJYRQ() {
		return KHZHJYRQ;
	}

	public void setKHZHJYRQ(String in) {
		KHZHJYRQ = in;
	}

	@IColumn(tagMethodName="getYGKBZTag",description="员工卡标志")
	@Column(name = "YGKBZ", nullable =true)
	private String YGKBZ;

	public static Map<String,String> getYGKBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_EMPLY_CARD_IND");
	}

	public String getYGKBZ() {
		return YGKBZ;
	}

	public void setYGKBZ(String in) {
		YGKBZ = in;
	}

	@IColumn(tagMethodName="getFSKBZTag",description="附属卡标志")
	@Column(name = "FSKBZ", nullable =true)
	private String FSKBZ;

	public static Map<String,String> getFSKBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_ATTACH_CARD_IND");
	}

	public String getFSKBZ() {
		return FSKBZ;
	}

	public void setFSKBZ(String in) {
		FSKBZ = in;
	}

	@Column(name = "ZKH", length = 299, nullable = true)
	@IColumn(description="主卡号")
	private String ZKH;

	public String getZKH() {
		return ZKH;
	}

	public void setZKH(String in) {
		ZKH = in;
	}

	@IColumn(tagMethodName="getNFBZTag",description="年费标志")
	@Column(name = "NFBZ", nullable =true)
	private String NFBZ;

	public static Map<String,String> getNFBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_ANNUAL_FEE_IND");
	}

	public String getNFBZ() {
		return NFBZ;
	}

	public void setNFBZ(String in) {
		NFBZ = in;
	}

	@Column(name = "BBXYED", length = 20, nullable = true)
	@IColumn(description="本币信用额度")
	private BigDecimal BBXYED;

	public BigDecimal getBBXYED() {
		return BBXYED;
	}

	public void setBBXYED(String in) {
		BBXYED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "WBXYED", length = 20, nullable = true)
	@IColumn(description="外币信用额度")
	private BigDecimal WBXYED;

	public BigDecimal getWBXYED() {
		return WBXYED;
	}

	public void setWBXYED(String in) {
		WBXYED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "WBBZ", length = 299, nullable = true)
	@IColumn(description="外币币种")
	private String WBBZ;

	public String getWBBZ() {
		return WBBZ;
	}

	public void setWBBZ(String in) {
		WBBZ = in;
	}

	@Column(name = "BBXJZQED", length = 20, nullable = true)
	@IColumn(description="本币现金支取额度")
	private BigDecimal BBXJZQED;

	public BigDecimal getBBXJZQED() {
		return BBXJZQED;
	}

	public void setBBXJZQED(String in) {
		BBXJZQED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "WBXJZQED", length = 20, nullable = true)
	@IColumn(description="外币现金支取额度")
	private BigDecimal WBXJZQED;

	public BigDecimal getWBXJZQED() {
		return WBXJZQED;
	}

	public void setWBXJZQED(String in) {
		WBXJZQED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZKRQ", length = 8, nullable = true)
	@IColumn(description="制卡日期")
	private String ZKRQ;

	public String getZKRQ() {
		return ZKRQ;
	}

	public void setZKRQ(String in) {
		ZKRQ = in;
	}

	@Column(name = "SLRQ", length = 8, nullable = true)
	@IColumn(description="受理日期")
	private String SLRQ;

	public String getSLRQ() {
		return SLRQ;
	}

	public void setSLRQ(String in) {
		SLRQ = in;
	}

	@Column(name = "SLYGH", length = 299, nullable = true)
	@IColumn(description="受理员工号")
	private String SLYGH;

	public String getSLYGH() {
		return SLYGH;
	}

	public void setSLYGH(String in) {
		SLYGH = in;
	}

	@Column(name = "KKRQ", length = 8, nullable = true)
	@IColumn(description="开卡日期")
	private String KKRQ;

	public String getKKRQ() {
		return KKRQ;
	}

	public void setKKRQ(String in) {
		KKRQ = in;
	}

	@Column(name = "KKYGH", length = 299, nullable = true)
	@IColumn(description="开卡员工号")
	private String KKYGH;

	public String getKKYGH() {
		return KKYGH;
	}

	public void setKKYGH(String in) {
		KKYGH = in;
	}

	@Column(name = "XKRQ", length = 8, nullable = true)
	@IColumn(description="销卡日期")
	private String XKRQ;

	public String getXKRQ() {
		return XKRQ;
	}

	public void setXKRQ(String in) {
		XKRQ = in;
	}

	@Column(name = "XKYGH", length = 299, nullable = true)
	@IColumn(description="销卡员工号")
	private String XKYGH;

	public String getXKYGH() {
		return XKYGH;
	}

	public void setXKYGH(String in) {
		XKYGH = in;
	}

	@IColumn(tagMethodName="getZHZTTag",description="帐户状态")
	@Column(name = "ZHZT", nullable =true)
	private String ZHZT;

	public static Map<String,String> getZHZTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_ACCT_STATUS_CRDT_CARD");
	}

	public String getZHZT() {
		return ZHZT;
	}

	public void setZHZT(String in) {
		ZHZT = in;
	}

	@Column(name = "BBQXJE", length = 20, nullable = true)
	@IColumn(description="本币取现金额")
	private BigDecimal BBQXJE;

	public BigDecimal getBBQXJE() {
		return BBQXJE;
	}

	public void setBBQXJE(String in) {
		BBQXJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BBXFJE", length = 20, nullable = true)
	@IColumn(description="本币消费金额")
	private BigDecimal BBXFJE;

	public BigDecimal getBBXFJE() {
		return BBXFJE;
	}

	public void setBBXFJE(String in) {
		BBXFJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BBTZJE", length = 20, nullable = true)
	@IColumn(description="本币透支金额")
	private BigDecimal BBTZJE;

	public BigDecimal getBBTZJE() {
		return BBTZJE;
	}

	public void setBBTZJE(String in) {
		BBTZJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "WBXFJE", length = 20, nullable = true)
	@IColumn(description="外币消费金额")
	private BigDecimal WBXFJE;

	public BigDecimal getWBXFJE() {
		return WBXFJE;
	}

	public void setWBXFJE(String in) {
		WBXFJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "WBQXJE", length = 20, nullable = true)
	@IColumn(description="外币取现金额")
	private BigDecimal WBQXJE;

	public BigDecimal getWBQXJE() {
		return WBQXJE;
	}

	public void setWBQXJE(String in) {
		WBQXJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "WBTZJE", length = 20, nullable = true)
	@IColumn(description="外币透支金额")
	private BigDecimal WBTZJE;

	public BigDecimal getWBTZJE() {
		return WBTZJE;
	}

	public void setWBTZJE(String in) {
		WBTZJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BBYE", length = 20, nullable = true)
	@IColumn(description="本币余额")
	private BigDecimal BBYE;

	public BigDecimal getBBYE() {
		return BBYE;
	}

	public void setBBYE(String in) {
		BBYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "WBYE", length = 20, nullable = true)
	@IColumn(description="外币余额")
	private BigDecimal WBYE;

	public BigDecimal getWBYE() {
		return WBYE;
	}

	public void setWBYE(String in) {
		WBYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DJYE", length = 20, nullable = true)
	@IColumn(description="冻结余额")
	private BigDecimal DJYE;

	public BigDecimal getDJYE() {
		return DJYE;
	}

	public void setDJYE(String in) {
		DJYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BLJE", length = 20, nullable = true)
	@IColumn(description="保留金额")
	private BigDecimal BLJE;

	public BigDecimal getBLJE() {
		return BLJE;
	}

	public void setBLJE(String in) {
		BLJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BNLJXFJE", length = 20, nullable = true)
	@IColumn(description="本年累计消费金额")
	private BigDecimal BNLJXFJE;

	public BigDecimal getBNLJXFJE() {
		return BNLJXFJE;
	}

	public void setBNLJXFJE(String in) {
		BNLJXFJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "LSED", length = 20, nullable = true)
	@IColumn(description="临时额度")
	private BigDecimal LSED;

	public BigDecimal getLSED() {
		return LSED;
	}

	public void setLSED(String in) {
		LSED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DQQFJE", length = 20, nullable = true)
	@IColumn(description="当前欠费金额")
	private BigDecimal DQQFJE;

	public BigDecimal getDQQFJE() {
		return DQQFJE;
	}

	public void setDQQFJE(String in) {
		DQQFJE = TypeParse.parseBigDecimal(in);
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

	@Column(name = "DBSM", length = 500, nullable = true)
	@IColumn(description="担保说明")
	private String DBSM;

	public String getDBSM() {
		return DBSM;
	}

	public void setDBSM(String in) {
		DBSM = in;
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

	@Column(name = "DQTZLX", length = 20, nullable = true)
	@IColumn(description="当前透支利息")
	private BigDecimal DQTZLX;

	public BigDecimal getDQTZLX() {
		return DQTZLX;
	}

	public void setDQTZLX(String in) {
		DQTZLX = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZHDHJYRQ", length = 8, nullable = true)
	@IColumn(description="最后动户交易日期")
	private String ZHDHJYRQ;

	public String getZHDHJYRQ() {
		return ZHDHJYRQ;
	}

	public void setZHDHJYRQ(String in) {
		ZHDHJYRQ = in;
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

