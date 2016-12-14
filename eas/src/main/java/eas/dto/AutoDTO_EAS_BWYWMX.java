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
@Table(name = "EAS_BWYWMX")
@IEntity(description= "授信情况-表外业务明细")
public class AutoDTO_EAS_BWYWMX implements java.io.Serializable{

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

	@Column(name = "BWYWKHMC", length = 299, nullable = true)
	@IColumn(description="表外业务客户名称")
	private String BWYWKHMC;

	public String getBWYWKHMC() {
		return BWYWKHMC;
	}

	public void setBWYWKHMC(String in) {
		BWYWKHMC = in;
	}

	@Column(name = "BWYWKHDM", length = 299, nullable = true)
	@IColumn(description="表外业务客户代码")
	private String BWYWKHDM;

	public String getBWYWKHDM() {
		return BWYWKHDM;
	}

	public void setBWYWKHDM(String in) {
		BWYWKHDM = in;
	}

	@Column(name = "CBYXJGDM", length = 299, nullable = true)
	@IColumn(description="承办银行机构代码")
	private String CBYXJGDM;

	public String getCBYXJGDM() {
		return CBYXJGDM;
	}

	public void setCBYXJGDM(String in) {
		CBYXJGDM = in;
	}

	@IColumn(tagMethodName="getBWYWLXTag",description="表外业务类型")
	@Column(name = "BWYWLX", nullable =true)
	private String BWYWLX;

	public static Map<String,String> getBWYWLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_OFF_BIZ_TYPE");
	}

	public String getBWYWLX() {
		return BWYWLX;
	}

	public void setBWYWLX(String in) {
		BWYWLX = in;
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

	@Column(name = "HTH", length = 299, nullable = true)
	@IColumn(description="合同号")
	private String HTH;

	public String getHTH() {
		return HTH;
	}

	public void setHTH(String in) {
		HTH = in;
	}

	@Column(name = "YWHM", length = 299, nullable = true)
	@IColumn(description="业务号码")
	private String YWHM;

	public String getYWHM() {
		return YWHM;
	}

	public void setYWHM(String in) {
		YWHM = in;
	}

	@Column(name = "YWYE", length = 20, nullable = true)
	@IColumn(description="业务余额")
	private BigDecimal YWYE;

	public BigDecimal getYWYE() {
		return YWYE;
	}

	public void setYWYE(String in) {
		YWYE = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getBZDMTag",description="币种代码")
	@Column(name = "BZDM", nullable =true)
	private String BZDM;

	public static Map<String,String> getBZDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_CURR_CD");
	}

	public String getBZDM() {
		return BZDM;
	}

	public void setBZDM(String in) {
		BZDM = in;
	}

	@Column(name = "FSRQ", length = 8, nullable = true)
	@IColumn(description="发生日期")
	private String FSRQ;

	public String getFSRQ() {
		return FSRQ;
	}

	public void setFSRQ(String in) {
		FSRQ = in;
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

	@Column(name = "BZJJE", length = 20, nullable = true)
	@IColumn(description="保证金金额")
	private BigDecimal BZJJE;

	public BigDecimal getBZJJE() {
		return BZJJE;
	}

	public void setBZJJE(String in) {
		BZJJE = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getCYJGDZLXTag",description="产业结构调整类型")
	@Column(name = "CYJGDZLX", nullable =true)
	private String CYJGDZLX;

	public static Map<String,String> getCYJGDZLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_INDUSTRY_STRU_ADJ_TYPE");
	}

	public String getCYJGDZLX() {
		return CYJGDZLX;
	}

	public void setCYJGDZLX(String in) {
		CYJGDZLX = in;
	}

	@IColumn(tagMethodName="getGYZXSJBSTag",description="工业转型升级标识")
	@Column(name = "GYZXSJBS", nullable =true)
	private String GYZXSJBS;

	public static Map<String,String> getGYZXSJBSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_INDUSTRY_UPGD_IDTFY");
	}

	public String getGYZXSJBS() {
		return GYZXSJBS;
	}

	public void setGYZXSJBS(String in) {
		GYZXSJBS = in;
	}

	@IColumn(tagMethodName="getZLXXCYLXTag",description="战略新兴产业类型")
	@Column(name = "ZLXXCYLX", nullable =true)
	private String ZLXXCYLX;

	public static Map<String,String> getZLXXCYLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_NEW_INDUSTRY_TYPE");
	}

	public String getZLXXCYLX() {
		return ZLXXCYLX;
	}

	public void setZLXXCYLX(String in) {
		ZLXXCYLX = in;
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

