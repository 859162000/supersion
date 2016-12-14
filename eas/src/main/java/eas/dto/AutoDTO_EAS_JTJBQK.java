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
@Table(name = "EAS_JTJBQK")
@IEntity(description= "集团基本情况")
public class AutoDTO_EAS_JTJBQK implements java.io.Serializable{

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

	@Column(name = "KHDMHGYLBM", length = 299, nullable = true)
	@IColumn(description="客户代码或供应链编码")
	private String KHDMHGYLBM;

	public String getKHDMHGYLBM() {
		return KHDMHGYLBM;
	}

	public void setKHDMHGYLBM(String in) {
		KHDMHGYLBM = in;
	}

	@Column(name = "GSZCBH", length = 299, nullable = true)
	@IColumn(description="工商注册编号")
	private String GSZCBH;

	public String getGSZCBH() {
		return GSZCBH;
	}

	public void setGSZCBH(String in) {
		GSZCBH = in;
	}

	@Column(name = "JTCYS", nullable = true)
	@IColumn(description="集团成员数")
	private Integer JTCYS;

	public Integer getJTCYS() {
		return JTCYS;
	}

	public void setJTCYS(String in) {
		JTCYS = TypeParse.parseInt(in);
	}

	@IColumn(tagMethodName="getSXLXTag",description="授信类型")
	@Column(name = "SXLX", nullable =true)
	private String SXLX;

	public static Map<String,String> getSXLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CRDT_TYPE");
	}

	public String getSXLX() {
		return SXLX;
	}

	public void setSXLX(String in) {
		SXLX = in;
	}

	@Column(name = "JTBBZCZE", length = 20, nullable = true)
	@IColumn(description="集团并表资产总额")
	private BigDecimal JTBBZCZE;

	public BigDecimal getJTBBZCZE() {
		return JTBBZCZE;
	}

	public void setJTBBZCZE(String in) {
		JTBBZCZE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JTBBFZZE", length = 20, nullable = true)
	@IColumn(description="集团并表负债总额")
	private BigDecimal JTBBFZZE;

	public BigDecimal getJTBBFZZE() {
		return JTBBFZZE;
	}

	public void setJTBBFZZE(String in) {
		JTBBFZZE = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getJTBBZCFZBLXTag",description="集团并表资产负债表类型")
	@Column(name = "JTBBZCFZBLX", nullable =true)
	private String JTBBZCFZBLX;

	public static Map<String,String> getJTBBZCFZBLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_ASSET_LIAB_TBL_TYPE");
	}

	public String getJTBBZCFZBLX() {
		return JTBBZCFZBLX;
	}

	public void setJTBBZCFZBLX(String in) {
		JTBBZCFZBLX = in;
	}

	@Column(name = "JTBBZCFZBRQ", length = 8, nullable = true)
	@IColumn(description="集团并表资产负债表日期")
	private String JTBBZCFZBRQ;

	public String getJTBBZCFZBRQ() {
		return JTBBZCFZBRQ;
	}

	public void setJTBBZCFZBRQ(String in) {
		JTBBZCFZBRQ = in;
	}

	@IColumn(tagMethodName="getFXYJXHTag",description="风险预警信号")
	@Column(name = "FXYJXH", nullable =true)
	private String FXYJXH;

	public static Map<String,String> getFXYJXHTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_GROUP_RISK_WARN_CD");
	}

	public String getFXYJXH() {
		return FXYJXH;
	}

	public void setFXYJXH(String in) {
		FXYJXH = in;
	}

	@IColumn(tagMethodName="getGZSJTag",description="关注事件")
	@Column(name = "GZSJ", nullable =true)
	private String GZSJ;

	public static Map<String,String> getGZSJTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_GROUP_CONCERN_EVENT_CD");
	}

	public String getGZSJ() {
		return GZSJ;
	}

	public void setGZSJ(String in) {
		GZSJ = in;
	}

	@Column(name = "XYPJJG", length = 299, nullable = true)
	@IColumn(description="信用评级结果")
	private String XYPJJG;

	public String getXYPJJG() {
		return XYPJJG;
	}

	public void setXYPJJG(String in) {
		XYPJJG = in;
	}

	@IColumn(tagMethodName="getMGSZCGJHDQTag",description="母公司注册国家或地区")
	@Column(name = "MGSZCGJHDQ", nullable =true)
	private String MGSZCGJHDQ;

	public static Map<String,String> getMGSZCGJHDQTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getMGSZCGJHDQ() {
		return MGSZCGJHDQ;
	}

	public void setMGSZCGJHDQ(String in) {
		MGSZCGJHDQ = in;
	}

	@IColumn(tagMethodName="getMGSGBDMTag",description="母公司国别代码")
	@Column(name = "MGSGBDM", nullable =true)
	private String MGSGBDM;

	public static Map<String,String> getMGSGBDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getMGSGBDM() {
		return MGSGBDM;
	}

	public void setMGSGBDM(String in) {
		MGSGBDM = in;
	}

	@Column(name = "MGSGNZCDZ", length = 500, nullable = true)
	@IColumn(description="母公司国内注册地址")
	private String MGSGNZCDZ;

	public String getMGSGNZCDZ() {
		return MGSGNZCDZ;
	}

	public void setMGSGNZCDZ(String in) {
		MGSGNZCDZ = in;
	}

	@IColumn(tagMethodName="getMGSXZQHDMTag",description="母公司行政区划代码")
	@Column(name = "MGSXZQHDM", nullable =true)
	private String MGSXZQHDM;

	public static Map<String,String> getMGSXZQHDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getMGSXZQHDM() {
		return MGSXZQHDM;
	}

	public void setMGSXZQHDM(String in) {
		MGSXZQHDM = in;
	}

	@Column(name = "MGSGXZCXXRQ", length = 8, nullable = true)
	@IColumn(description="母公司更新注册信息日期")
	private String MGSGXZCXXRQ;

	public String getMGSGXZCXXRQ() {
		return MGSGXZCXXRQ;
	}

	public void setMGSGXZCXXRQ(String in) {
		MGSGXZCXXRQ = in;
	}

	@Column(name = "BGDZ", length = 500, nullable = true)
	@IColumn(description="国内办公地址")
	private String BGDZ;

	public String getBGDZ() {
		return BGDZ;
	}

	public void setBGDZ(String in) {
		BGDZ = in;
	}

	@Column(name = "BGDZXZQHDM", length = 500, nullable = true)
	@IColumn(description="国内办公地址行政区划代码")
	private String BGDZXZQHDM;

	public String getBGDZXZQHDM() {
		return BGDZXZQHDM;
	}

	public void setBGDZXZQHDM(String in) {
		BGDZXZQHDM = in;
	}

	@Column(name = "GXGNBGDZRQ", length = 8, nullable = true)
	@IColumn(description="更新国内办公地址日期")
	private String GXGNBGDZRQ;

	public String getGXGNBGDZRQ() {
		return GXGNBGDZRQ;
	}

	public void setGXGNBGDZRQ(String in) {
		GXGNBGDZRQ = in;
	}

	@Column(name = "CJRQ", length = 299, nullable = true)
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
