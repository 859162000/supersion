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
@Table(name = "EAS_XMDKXXB")
@IEntity(description= "项目贷款信息表")
public class AutoDTO_EAS_XMDKXXB implements java.io.Serializable{

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

	@Column(name = "JKRMC", length = 299, nullable = true)
	@IColumn(description="借款人名称")
	private String JKRMC;

	public String getJKRMC() {
		return JKRMC;
	}

	public void setJKRMC(String in) {
		JKRMC = in;
	}

	@Column(name = "XMMC", length = 299, nullable = true)
	@IColumn(description="项目名称")
	private String XMMC;

	public String getXMMC() {
		return XMMC;
	}

	public void setXMMC(String in) {
		XMMC = in;
	}

	@Column(name = "XMLX", length = 299, nullable = true)
	@IColumn(description="项目类型")
	private String XMLX;

	public String getXMLX() {
		return XMLX;
	}

	public void setXMLX(String in) {
		XMLX = in;
	}

	@IColumn(tagMethodName="getSFYTTag",description="是否银团")
	@Column(name = "SFYT", nullable =true)
	private String SFYT;

	public static Map<String,String> getSFYTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_SYNDIC");
	}

	public String getSFYT() {
		return SFYT;
	}

	public void setSFYT(String in) {
		SFYT = in;
	}

	@Column(name = "XMZTZ", length = 20, nullable = true)
	@IColumn(description="项目总投资")
	private BigDecimal XMZTZ;

	public BigDecimal getXMZTZ() {
		return XMZTZ;
	}

	public void setXMZTZ(String in) {
		XMZTZ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZBJ", length = 20, nullable = true)
	@IColumn(description="资本金")
	private BigDecimal ZBJ;

	public BigDecimal getZBJ() {
		return ZBJ;
	}

	public void setZBJ(String in) {
		ZBJ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XMDKJE", length = 20, nullable = true)
	@IColumn(description="项目贷款金额")
	private BigDecimal XMDKJE;

	public BigDecimal getXMDKJE() {
		return XMDKJE;
	}

	public void setXMDKJE(String in) {
		XMDKJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XMDKQX", length = 299, nullable = true)
	@IColumn(description="项目贷款期限")
	private String XMDKQX;

	public String getXMDKQX() {
		return XMDKQX;
	}

	public void setXMDKQX(String in) {
		XMDKQX = in;
	}

	@Column(name = "PWWH", length = 299, nullable = true)
	@IColumn(description="批文文号")
	private String PWWH;

	public String getPWWH() {
		return PWWH;
	}

	public void setPWWH(String in) {
		PWWH = in;
	}

	@Column(name = "LXPW", length = 299, nullable = true)
	@IColumn(description="立项批文")
	private String LXPW;

	public String getLXPW() {
		return LXPW;
	}

	public void setLXPW(String in) {
		LXPW = in;
	}

	@Column(name = "GHXKZBH", length = 299, nullable = true)
	@IColumn(description="规划许可证编号")
	private String GHXKZBH;

	public String getGHXKZBH() {
		return GHXKZBH;
	}

	public void setGHXKZBH(String in) {
		GHXKZBH = in;
	}

	@Column(name = "JSYDXKZBH", length = 299, nullable = true)
	@IColumn(description="建设用地许可证编号")
	private String JSYDXKZBH;

	public String getJSYDXKZBH() {
		return JSYDXKZBH;
	}

	public void setJSYDXKZBH(String in) {
		JSYDXKZBH = in;
	}

	@Column(name = "HPXKZBH", length = 299, nullable = true)
	@IColumn(description="环评许可证编号")
	private String HPXKZBH;

	public String getHPXKZBH() {
		return HPXKZBH;
	}

	public void setHPXKZBH(String in) {
		HPXKZBH = in;
	}

	@Column(name = "SGXKZBH", length = 299, nullable = true)
	@IColumn(description="施工许可证编号")
	private String SGXKZBH;

	public String getSGXKZBH() {
		return SGXKZBH;
	}

	public void setSGXKZBH(String in) {
		SGXKZBH = in;
	}

	@Column(name = "QTXKZ", length = 299, nullable = true)
	@IColumn(description="其他许可证")
	private String QTXKZ;

	public String getQTXKZ() {
		return QTXKZ;
	}

	public void setQTXKZ(String in) {
		QTXKZ = in;
	}

	@Column(name = "QTXKZBH", length = 299, nullable = true)
	@IColumn(description="其他许可证编号")
	private String QTXKZBH;

	public String getQTXKZBH() {
		return QTXKZBH;
	}

	public void setQTXKZBH(String in) {
		QTXKZBH = in;
	}

	@Column(name = "KGRQ", length = 8, nullable = true)
	@IColumn(description="开工日期")
	private String KGRQ;

	public String getKGRQ() {
		return KGRQ;
	}

	public void setKGRQ(String in) {
		KGRQ = in;
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

