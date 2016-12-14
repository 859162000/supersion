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
@Table(name = "EAS_WTDK")
@IEntity(description= "委托贷款")
public class AutoDTO_EAS_WTDK implements java.io.Serializable{

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

	@Column(name = "WTRKHTYBH", length = 299, nullable = true)
	@IColumn(description="委托人客户统一编号")
	private String WTRKHTYBH;

	public String getWTRKHTYBH() {
		return WTRKHTYBH;
	}

	public void setWTRKHTYBH(String in) {
		WTRKHTYBH = in;
	}

	@Column(name = "WTRKHMC", length = 299, nullable = true)
	@IColumn(description="委托人客户名称")
	private String WTRKHMC;

	public String getWTRKHMC() {
		return WTRKHMC;
	}

	public void setWTRKHMC(String in) {
		WTRKHMC = in;
	}

	@Column(name = "SYRMC", length = 299, nullable = true)
	@IColumn(description="受益人名称")
	private String SYRMC;

	public String getSYRMC() {
		return SYRMC;
	}

	public void setSYRMC(String in) {
		SYRMC = in;
	}

	@Column(name = "WTJJKHXH", length = 299, nullable = true)
	@IColumn(description="委托基金开户行号")
	private String WTJJKHXH;

	public String getWTJJKHXH() {
		return WTJJKHXH;
	}

	public void setWTJJKHXH(String in) {
		WTJJKHXH = in;
	}

	@Column(name = "WTJJKHXMC", length = 299, nullable = true)
	@IColumn(description="委托基金开户行名称")
	private String WTJJKHXMC;

	public String getWTJJKHXMC() {
		return WTJJKHXMC;
	}

	public void setWTJJKHXMC(String in) {
		WTJJKHXMC = in;
	}

	@Column(name = "WTJJZH", length = 299, nullable = true)
	@IColumn(description="委托账号")
	private String WTJJZH;

	public String getWTJJZH() {
		return WTJJZH;
	}

	public void setWTJJZH(String in) {
		WTJJZH = in;
	}

	@IColumn(tagMethodName="getWTZHLXTag",description="委托账号类型")
	@Column(name = "WTZHLX", nullable =true)
	private String WTZHLX;

	public static Map<String,String> getWTZHLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_ENTR_ACCT_TYPE");
	}

	public String getWTZHLX() {
		return WTZHLX;
	}

	public void setWTZHLX(String in) {
		WTZHLX = in;
	}

	@Column(name = "WTJE", length = 20, nullable = true)
	@IColumn(description="委托金额")
	private BigDecimal WTJE;

	public BigDecimal getWTJE() {
		return WTJE;
	}

	public void setWTJE(String in) {
		WTJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SJWTDKJE", length = 20, nullable = true)
	@IColumn(description="实际委托贷款金额")
	private BigDecimal SJWTDKJE;

	public BigDecimal getSJWTDKJE() {
		return SJWTDKJE;
	}

	public void setSJWTDKJE(String in) {
		SJWTDKJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "WTDKYT", length = 299, nullable = true)
	@IColumn(description="委托贷款用途")
	private String WTDKYT;

	public String getWTDKYT() {
		return WTDKYT;
	}

	public void setWTDKYT(String in) {
		WTDKYT = in;
	}

	@IColumn(tagMethodName="getSFSXTag",description="是否收息")
	@Column(name = "SFSX", nullable =true)
	private String SFSX;

	public static Map<String,String> getSFSXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_COLL_INSTR");
	}

	public String getSFSX() {
		return SFSX;
	}

	public void setSFSX(String in) {
		SFSX = in;
	}

	@Column(name = "SXFFS", length = 299, nullable = true)
	@IColumn(description="手续费方式")
	private String SXFFS;

	public String getSXFFS() {
		return SXFFS;
	}

	public void setSXFFS(String in) {
		SXFFS = in;
	}

	@Column(name = "SXFJE", length = 20, nullable = true)
	@IColumn(description="手续费金额")
	private BigDecimal SXFJE;

	public BigDecimal getSXFJE() {
		return SXFJE;
	}

	public void setSXFJE(String in) {
		SXFJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XYQSRQ", length = 8, nullable = true)
	@IColumn(description="协议起始日期")
	private String XYQSRQ;

	public String getXYQSRQ() {
		return XYQSRQ;
	}

	public void setXYQSRQ(String in) {
		XYQSRQ = in;
	}

	@Column(name = "XYDQRQ", length = 8, nullable = true)
	@IColumn(description="协议到期日期")
	private String XYDQRQ;

	public String getXYDQRQ() {
		return XYDQRQ;
	}

	public void setXYDQRQ(String in) {
		XYDQRQ = in;
	}

	@Column(name = "JBRXM", length = 299, nullable = true)
	@IColumn(description="经办人姓名")
	private String JBRXM;

	public String getJBRXM() {
		return JBRXM;
	}

	public void setJBRXM(String in) {
		JBRXM = in;
	}

	@Column(name = "JBJGMC", length = 299, nullable = true)
	@IColumn(description="经办机构名称")
	private String JBJGMC;

	public String getJBJGMC() {
		return JBJGMC;
	}

	public void setJBJGMC(String in) {
		JBJGMC = in;
	}

	@Column(name = "XYZT", length = 299, nullable = true)
	@IColumn(description="协议状态")
	private String XYZT;

	public String getXYZT() {
		return XYZT;
	}

	public void setXYZT(String in) {
		XYZT = in;
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

