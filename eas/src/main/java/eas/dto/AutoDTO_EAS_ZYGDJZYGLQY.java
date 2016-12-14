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
@Table(name = "EAS_ZYGDJZYGLQY")
@IEntity(description= "单一法人基本情况-重要股东及主要关联企业")
public class AutoDTO_EAS_ZYGDJZYGLQY implements java.io.Serializable{

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

	@IColumn(tagMethodName="getGLLXTag",description="关联类型")
	@Column(name = "GLLX", nullable =true)
	private String GLLX;

	public static Map<String,String> getGLLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_RELA_TYPE");
	}

	public String getGLLX() {
		return GLLX;
	}

	public void setGLLX(String in) {
		GLLX = in;
	}

	@Column(name = "GDHGLQYMC", length = 299, nullable = true)
	@IColumn(description="股东或关联企业名称")
	private String GDHGLQYMC;

	public String getGDHGLQYMC() {
		return GDHGLQYMC;
	}

	public void setGDHGLQYMC(String in) {
		GDHGLQYMC = in;
	}

	@IColumn(tagMethodName="getGDHGLQYLXTag",description="股东或关联企业类型")
	@Column(name = "GDHGLQYLX", nullable =true)
	private String GDHGLQYLX;

	public static Map<String,String> getGDHGLQYLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_SHAREHD_RELA_CORP_TYPE");
	}

	public String getGDHGLQYLX() {
		return GDHGLQYLX;
	}

	public void setGDHGLQYLX(String in) {
		GDHGLQYLX = in;
	}

	@IColumn(tagMethodName="getGDHGLQYZJLXTag",description="股东或关联企业证件类型")
	@Column(name = "GDHGLQYZJLX", nullable =true)
	private String GDHGLQYZJLX;

	public static Map<String,String> getGDHGLQYZJLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_SH_REL_CORP_CERT_TYPE");
	}

	public String getGDHGLQYZJLX() {
		return GDHGLQYZJLX;
	}

	public void setGDHGLQYZJLX(String in) {
		GDHGLQYZJLX = in;
	}

	@Column(name = "GDHGLQYZJDM", length = 299, nullable = true)
	@IColumn(description="股东关联企业证件代码")
	private String GDHGLQYZJDM;

	public String getGDHGLQYZJDM() {
		return GDHGLQYZJDM;
	}

	public void setGDHGLQYZJDM(String in) {
		GDHGLQYZJDM = in;
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

	@Column(name = "GDHGLQYKHDM", length = 299, nullable = true)
	@IColumn(description="股东或关联企业客户代码")
	private String GDHGLQYKHDM;

	public String getGDHGLQYKHDM() {
		return GDHGLQYKHDM;
	}

	public void setGDHGLQYKHDM(String in) {
		GDHGLQYKHDM = in;
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

	@Column(name = "CGBL", length = 20, nullable = true)
	@IColumn(description="持股比例")
	private String CGBL;

	public String getCGBL() {
		return CGBL;
	}

	public void setCGBL(String in) {
		CGBL = in;
	}

	@Column(name = "GDJGDYRQ", length = 8, nullable = true)
	@IColumn(description="股东结构对应日期")
	private String GDJGDYRQ;

	public String getGDJGDYRQ() {
		return GDJGDYRQ;
	}

	public void setGDJGDYRQ(String in) {
		GDJGDYRQ = in;
	}

	@Column(name = "GXXXRQ", length = 8, nullable = true)
	@IColumn(description="更新信息日期")
	private String GXXXRQ;

	public String getGXXXRQ() {
		return GXXXRQ;
	}

	public void setGXXXRQ(String in) {
		GXXXRQ = in;
	}

	@IColumn(tagMethodName="getSJKZRBSTag",description="实际控制人标识")
	@Column(name = "SJKZRBS", nullable =true)
	private String SJKZRBS;

	public static Map<String,String> getSJKZRBSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_ACTL_CTRL_PERSON_IDTFY");
	}

	public String getSJKZRBS() {
		return SJKZRBS;
	}

	public void setSJKZRBS(String in) {
		SJKZRBS = in;
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

