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
@Table(name = "EAS_JTKHSXCFQK")
@IEntity(description= "授信情况-集团客户授信拆分情况")
public class AutoDTO_EAS_JTKHSXCFQK implements java.io.Serializable{

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
	@IColumn(description="集团客户名称")
	private String KHMC;

	public String getKHMC() {
		return KHMC;
	}

	public void setKHMC(String in) {
		KHMC = in;
	}

	@Column(name = "KHLX", length = 299, nullable = true)
	@IColumn(description="集团客户代码")
	private String KHLX;

	public String getKHLX() {
		return KHLX;
	}

	public void setKHLX(String in) {
		KHLX = in;
	}

	@Column(name = "JTKHSXHM", length = 299, nullable = true)
	@IColumn(description="集团客户授信号码")
	private String JTKHSXHM;

	public String getJTKHSXHM() {
		return JTKHSXHM;
	}

	public void setJTKHSXHM(String in) {
		JTKHSXHM = in;
	}

	@Column(name = "CYDWDKHMC", length = 299, nullable = true)
	@IColumn(description="成员单位的客户名称")
	private String CYDWDKHMC;

	public String getCYDWDKHMC() {
		return CYDWDKHMC;
	}

	public void setCYDWDKHMC(String in) {
		CYDWDKHMC = in;
	}

	@Column(name = "CYDWDKHDM", length = 299, nullable = true)
	@IColumn(description="成员单位的客户代码")
	private String CYDWDKHDM;

	public String getCYDWDKHDM() {
		return CYDWDKHDM;
	}

	public void setCYDWDKHDM(String in) {
		CYDWDKHDM = in;
	}

	@Column(name = "SXHM", length = 299, nullable = true)
	@IColumn(description="授信号码")
	private String SXHM;

	public String getSXHM() {
		return SXHM;
	}

	public void setSXHM(String in) {
		SXHM = in;
	}

	@Column(name = "SXED", length = 20, nullable = true)
	@IColumn(description="授信额度")
	private BigDecimal SXED;

	public BigDecimal getSXED() {
		return SXED;
	}

	public void setSXED(String in) {
		SXED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKSXED", length = 20, nullable = true)
	@IColumn(description="贷款授信额度")
	private BigDecimal DKSXED;

	public BigDecimal getDKSXED() {
		return DKSXED;
	}

	public void setDKSXED(String in) {
		DKSXED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKYE", length = 20, nullable = true)
	@IColumn(description="贷款余额")
	private BigDecimal DKYE;

	public BigDecimal getDKYE() {
		return DKYE;
	}

	public void setDKYE(String in) {
		DKYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CYZQYE", length = 20, nullable = true)
	@IColumn(description="持有债券余额")
	private BigDecimal CYZQYE;

	public BigDecimal getCYZQYE() {
		return CYZQYE;
	}

	public void setCYZQYE(String in) {
		CYZQYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CYGQYE", length = 20, nullable = true)
	@IColumn(description="持有股权余额")
	private BigDecimal CYGQYE;

	public BigDecimal getCYGQYE() {
		return CYGQYE;
	}

	public void setCYGQYE(String in) {
		CYGQYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QTBNXYFXZCYE", length = 20, nullable = true)
	@IColumn(description="其他表内信用风险资产余额")
	private BigDecimal QTBNXYFXZCYE;

	public BigDecimal getQTBNXYFXZCYE() {
		return QTBNXYFXZCYE;
	}

	public void setQTBNXYFXZCYE(String in) {
		QTBNXYFXZCYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BWYWYE", length = 20, nullable = true)
	@IColumn(description="表外业务余额")
	private BigDecimal BWYWYE;

	public BigDecimal getBWYWYE() {
		return BWYWYE;
	}

	public void setBWYWYE(String in) {
		BWYWYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "XYYWYEZYSXED", length = 20, nullable = true)
	@IColumn(description="现有业务余额占用授信额度")
	private BigDecimal XYYWYEZYSXED;

	public BigDecimal getXYYWYEZYSXED() {
		return XYYWYEZYSXED;
	}

	public void setXYYWYEZYSXED(String in) {
		XYYWYEZYSXED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DKYEZYDKSXED", length = 20, nullable = true)
	@IColumn(description="贷款余额占用贷款授信额度")
	private BigDecimal DKYEZYDKSXED;

	public BigDecimal getDKYEZYDKSXED() {
		return DKYEZYDKSXED;
	}

	public void setDKYEZYDKSXED(String in) {
		DKYEZYDKSXED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SKSYSXED", length = 20, nullable = true)
	@IColumn(description="尚可使用授信额度")
	private BigDecimal SKSYSXED;

	public BigDecimal getSKSYSXED() {
		return SKSYSXED;
	}

	public void setSKSYSXED(String in) {
		SKSYSXED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SKSYDKSXED", length = 20, nullable = true)
	@IColumn(description="尚可使用贷款授信额度")
	private BigDecimal SKSYDKSXED;

	public BigDecimal getSKSYDKSXED() {
		return SKSYDKSXED;
	}

	public void setSKSYDKSXED(String in) {
		SKSYDKSXED = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CJRQ", length = 20, nullable = true)
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

