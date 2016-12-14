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
@Table(name = "EAS_DYFRJBQK")
@IEntity(description= "单一法人基本情况")
public class AutoDTO_EAS_DYFRJBQK implements java.io.Serializable{

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

	@Column(name = "ZZJGDM", length = 299, nullable = true)
	@IColumn(description="组织机构代码")
	private String ZZJGDM;

	public String getZZJGDM() {
		return ZZJGDM;
	}

	public void setZZJGDM(String in) {
		ZZJGDM = in;
	}

	@Column(name = "ZZJGDJHNJHGXRQ", length = 8, nullable = true)
	@IColumn(description="组织机构登记或年检或更新日期")
	private String ZZJGDJHNJHGXRQ;

	public String getZZJGDJHNJHGXRQ() {
		return ZZJGDJHNJHGXRQ;
	}

	public void setZZJGDJHNJHGXRQ(String in) {
		ZZJGDJHNJHGXRQ = in;
	}

	@Column(name = "DJZCDM", length = 299, nullable = true)
	@IColumn(description="登记注册代码")
	private String DJZCDM;

	public String getDJZCDM() {
		return DJZCDM;
	}

	public void setDJZCDM(String in) {
		DJZCDM = in;
	}

	@Column(name = "DJZCRQ", length = 8, nullable = true)
	@IColumn(description="登记注册日期")
	private String DJZCRQ;

	public String getDJZCRQ() {
		return DJZCRQ;
	}

	public void setDJZCRQ(String in) {
		DJZCRQ = in;
	}

	@Column(name = "DJNJRQ", length = 8, nullable = true)
	@IColumn(description="登记年检日期")
	private String DJNJRQ;

	public String getDJNJRQ() {
		return DJNJRQ;
	}

	public void setDJNJRQ(String in) {
		DJNJRQ = in;
	}

	@Column(name = "DJGXRQ", length = 8, nullable = true)
	@IColumn(description="登记更新日期")
	private String DJGXRQ;

	public String getDJGXRQ() {
		return DJGXRQ;
	}

	public void setDJGXRQ(String in) {
		DJGXRQ = in;
	}

	@IColumn(tagMethodName="getZCGJHDQTag",description="注册国家或地区")
	@Column(name = "ZCGJHDQ", nullable =true)
	private String ZCGJHDQ;

	public static Map<String,String> getZCGJHDQTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getZCGJHDQ() {
		return ZCGJHDQ;
	}

	public void setZCGJHDQ(String in) {
		ZCGJHDQ = in;
	}

	@IColumn(tagMethodName="getGBDMTag",description="国别代码")
	@Column(name = "GBDM", nullable =true)
	private String GBDM;

	public static Map<String,String> getGBDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getGBDM() {
		return GBDM;
	}

	public void setGBDM(String in) {
		GBDM = in;
	}

	@Column(name = "ZCDZ", length = 500, nullable = true)
	@IColumn(description="注册地址")
	private String ZCDZ;

	public String getZCDZ() {
		return ZCDZ;
	}

	public void setZCDZ(String in) {
		ZCDZ = in;
	}

	@IColumn(tagMethodName="getXZQHDMTag",description="行政区划代码")
	@Column(name = "XZQHDM", nullable =true)
	private String XZQHDM;

	public static Map<String,String> getXZQHDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getXZQHDM() {
		return XZQHDM;
	}

	public void setXZQHDM(String in) {
		XZQHDM = in;
	}

	@Column(name = "SSGSBZ", length = 299, nullable = true)
	@IColumn(description="上市公司标志")
	private String SSGSBZ;

	public String getSSGSBZ() {
		return SSGSBZ;
	}

	public void setSSGSBZ(String in) {
		SSGSBZ = in;
	}

	@Column(name = "SSDGJDM", length = 299, nullable = true)
	@IColumn(description="上市的国家代码")
	private String SSDGJDM;

	public String getSSDGJDM() {
		return SSDGJDM;
	}

	public void setSSDGJDM(String in) {
		SSDGJDM = in;
	}

	@Column(name = "SSGSDM", length = 299, nullable = true)
	@IColumn(description="上市公司代码")
	private String SSGSDM;

	public String getSSGSDM() {
		return SSGSDM;
	}

	public void setSSGSDM(String in) {
		SSGSDM = in;
	}

	@IColumn(tagMethodName="getFXYJXHTag",description="风险预警信号")
	@Column(name = "FXYJXH", nullable =true)
	private String FXYJXH;

	public static Map<String,String> getFXYJXHTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_LP_RISK_WARN_CD");
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
		return ShowContext.getInstance().getShowEntityMap().get("GB_LP_CONCERN_EVENT_CD");
	}

	public String getGZSJ() {
		return GZSJ;
	}

	public void setGZSJ(String in) {
		GZSJ = in;
	}

	@Column(name = "WYGL", length = 20, nullable = true)
	@IColumn(description="违约概率")
	private String WYGL;

	public String getWYGL() {
		return WYGL;
	}

	public void setWYGL(String in) {
		WYGL = in;
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

	@Column(name = "ZCZE", length = 20, nullable = true)
	@IColumn(description="资产总额")
	private String ZCZE;

	public String getZCZE() {
		return ZCZE;
	}

	public void setZCZE(String in) {
		ZCZE = in;
	}

	@Column(name = "FZZE", length = 20, nullable = true)
	@IColumn(description="负债总额")
	private String FZZE;

	public String getFZZE() {
		return FZZE;
	}

	public void setFZZE(String in) {
		FZZE = in;
	}

	@Column(name = "SQLR", length = 20, nullable = true)
	@IColumn(description="税前利润")
	private String SQLR;

	public String getSQLR() {
		return SQLR;
	}

	public void setSQLR(String in) {
		SQLR = in;
	}

	@Column(name = "ZYYWSR", length = 20, nullable = true)
	@IColumn(description="主营业务收入")
	private String ZYYWSR;

	public String getZYYWSR() {
		return ZYYWSR;
	}

	public void setZYYWSR(String in) {
		ZYYWSR = in;
	}

	@Column(name = "CH", length = 20, nullable = true)
	@IColumn(description="存货")
	private String CH;

	public String getCH() {
		return CH;
	}

	public void setCH(String in) {
		CH = in;
	}

	@Column(name = "YSZK", length = 20, nullable = true)
	@IColumn(description="应收账款")
	private String YSZK;

	public String getYSZK() {
		return YSZK;
	}

	public void setYSZK(String in) {
		YSZK = in;
	}

	@Column(name = "QTYSK", length = 20, nullable = true)
	@IColumn(description="其他应收款")
	private String QTYSK;

	public String getQTYSK() {
		return QTYSK;
	}

	public void setQTYSK(String in) {
		QTYSK = in;
	}

	@Column(name = "LDZCHJ", length = 20, nullable = true)
	@IColumn(description="流动资产合计")
	private String LDZCHJ;

	public String getLDZCHJ() {
		return LDZCHJ;
	}

	public void setLDZCHJ(String in) {
		LDZCHJ = in;
	}

	@Column(name = "LDFZHJ", length = 20, nullable = true)
	@IColumn(description="流动负债合计")
	private String LDFZHJ;

	public String getLDFZHJ() {
		return LDFZHJ;
	}

	public void setLDFZHJ(String in) {
		LDFZHJ = in;
	}

	@IColumn(tagMethodName="getCWBBLXTag",description="财务报表类型")
	@Column(name = "CWBBLX", nullable =true)
	private String CWBBLX;

	public static Map<String,String> getCWBBLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_FIN_REPT_TYPE");
	}

	public String getCWBBLX() {
		return CWBBLX;
	}

	public void setCWBBLX(String in) {
		CWBBLX = in;
	}

	@Column(name = "CWBBRQ", length = 8, nullable = true)
	@IColumn(description="财务报表日期")
	private String CWBBRQ;

	public String getCWBBRQ() {
		return CWBBRQ;
	}

	public void setCWBBRQ(String in) {
		CWBBRQ = in;
	}

	@IColumn(tagMethodName="getKHLXTag",description="客户类型")
	@Column(name = "KHLX", nullable =true)
	private String KHLX;

	public static Map<String,String> getKHLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CUST_TYPE_3_1");
	}

	public String getKHLX() {
		return KHLX;
	}

	public void setKHLX(String in) {
		KHLX = in;
	}

	@IColumn(tagMethodName="getKHSSXYDMTag",description="客户所属行业代码")
	@Column(name = "KHSSXYDM", nullable =true)
	private String KHSSXYDM;

	public static Map<String,String> getKHSSXYDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ECON_INDUSTRY_CLASS");
	}

	public String getKHSSXYDM() {
		return KHSSXYDM;
	}

	public void setKHSSXYDM(String in) {
		KHSSXYDM = in;
	}

	@Column(name = "DKKH", length = 299, nullable = true)
	@IColumn(description="贷款卡号")
	private String DKKH;

	public String getDKKH() {
		return DKKH;
	}

	public void setDKKH(String in) {
		DKKH = in;
	}

	@Column(name = "HJHSHFXFL", length = 299, nullable = true)
	@IColumn(description="环境和社会风险分类")
	private String HJHSHFXFL;

	public String getHJHSHFXFL() {
		return HJHSHFXFL;
	}

	public void setHJHSHFXFL(String in) {
		HJHSHFXFL = in;
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

