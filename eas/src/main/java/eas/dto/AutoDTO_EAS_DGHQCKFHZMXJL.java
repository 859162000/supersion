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
@Table(name = "EAS_DGHQCKFHZMXJL")
@IEntity(description= "对公活期存款分户账明细记录")
public class AutoDTO_EAS_DGHQCKFHZMXJL implements java.io.Serializable{

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

	@Column(name = "HQCKZH", length = 299, nullable = true)
	@IColumn(description="活期存款账号")
	private String HQCKZH;

	public String getHQCKZH() {
		return HQCKZH;
	}

	public void setHQCKZH(String in) {
		HQCKZH = in;
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

	@Column(name = "HXJYRQ", length = 8, nullable = true)
	@IColumn(description="核心交易日期")
	private String HXJYRQ;

	public String getHXJYRQ() {
		return HXJYRQ;
	}

	public void setHXJYRQ(String in) {
		HXJYRQ = in;
	}

	@Column(name = "HXJYSJ", length = 299, nullable = true)
	@IColumn(description="核心交易时间")
	private String HXJYSJ;

	public String getHXJYSJ() {
		return HXJYSJ;
	}

	public void setHXJYSJ(String in) {
		HXJYSJ = in;
	}

	@Column(name = "JYLX", length = 299, nullable = true)
	@IColumn(description="交易类型")
	private String JYLX;

	public String getJYLX() {
		return JYLX;
	}

	public void setJYLX(String in) {
		JYLX = in;
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

	@Column(name = "KHHJGH", length = 299, nullable = true)
	@IColumn(description="开户行机构号")
	private String KHHJGH;

	public String getKHHJGH() {
		return KHHJGH;
	}

	public void setKHHJGH(String in) {
		KHHJGH = in;
	}

	@Column(name = "YWBLJGH", length = 299, nullable = true)
	@IColumn(description="业务办理机构号")
	private String YWBLJGH;

	public String getYWBLJGH() {
		return YWBLJGH;
	}

	public void setYWBLJGH(String in) {
		YWBLJGH = in;
	}

	@Column(name = "ZHYE", length = 20, nullable = true)
	@IColumn(description="账户余额")
	private BigDecimal ZHYE;

	public BigDecimal getZHYE() {
		return ZHYE;
	}

	public void setZHYE(String in) {
		ZHYE = TypeParse.parseBigDecimal(in);
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

	@Column(name = "DFHM", length = 299, nullable = true)
	@IColumn(description="对方户名")
	private String DFHM;

	public String getDFHM() {
		return DFHM;
	}

	public void setDFHM(String in) {
		DFHM = in;
	}

	@Column(name = "DFXH", length = 299, nullable = true)
	@IColumn(description="对方行号")
	private String DFXH;

	public String getDFXH() {
		return DFXH;
	}

	public void setDFXH(String in) {
		DFXH = in;
	}

	@Column(name = "DFXM", length = 299, nullable = true)
	@IColumn(description="对方行名")
	private String DFXM;

	public String getDFXM() {
		return DFXM;
	}

	public void setDFXM(String in) {
		DFXM = in;
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

	@Column(name = "DBRXM", length = 299, nullable = true)
	@IColumn(description="代办人姓名")
	private String DBRXM;

	public String getDBRXM() {
		return DBRXM;
	}

	public void setDBRXM(String in) {
		DBRXM = in;
	}

	@Column(name = "DBRZJLB", length = 299, nullable = true)
	@IColumn(description="代办人证件类别")
	private String DBRZJLB;

	public String getDBRZJLB() {
		return DBRZJLB;
	}

	public void setDBRZJLB(String in) {
		DBRZJLB = in;
	}

	@Column(name = "DBRZJHM", length = 299, nullable = true)
	@IColumn(description="代办人证件号码")
	private String DBRZJHM;

	public String getDBRZJHM() {
		return DBRZJHM;
	}

	public void setDBRZJHM(String in) {
		DBRZJHM = in;
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

	@Column(name = "GYLSH", length = 299, nullable = true)
	@IColumn(description="柜员流水号")
	private String GYLSH;

	public String getGYLSH() {
		return GYLSH;
	}

	public void setGYLSH(String in) {
		GYLSH = in;
	}

	@Column(name = "SQGYH", length = 299, nullable = true)
	@IColumn(description="授权柜员号")
	private String SQGYH;

	public String getSQGYH() {
		return SQGYH;
	}

	public void setSQGYH(String in) {
		SQGYH = in;
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

	@IColumn(tagMethodName="getCBMBZTag",description="冲补抹标志")
	@Column(name = "CBMBZ", nullable =true)
	private String CBMBZ;

	public static Map<String,String> getCBMBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_REV_MEND_IND");
	}

	public String getCBMBZ() {
		return CBMBZ;
	}

	public void setCBMBZ(String in) {
		CBMBZ = in;
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

