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
@Table(name = "EAS_GRDKWYQKTJB")
@IEntity(description= "个人贷款违约情况统计表")
public class AutoDTO_EAS_GRDKWYQKTJB implements java.io.Serializable{

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

	@Column(name = "JKRXM", length = 299, nullable = true)
	@IColumn(description="借款人姓名")
	private String JKRXM;

	public String getJKRXM() {
		return JKRXM;
	}

	public void setJKRXM(String in) {
		JKRXM = in;
	}

	@IColumn(tagMethodName="getYXSFZJLXTag",description="有效身份证件类型")
	@Column(name = "YXSFZJLX", nullable =true)
	private String YXSFZJLX;

	public static Map<String,String> getYXSFZJLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_VALID_IDTFY_CERT_TYPE");
	}

	public String getYXSFZJLX() {
		return YXSFZJLX;
	}

	public void setYXSFZJLX(String in) {
		YXSFZJLX = in;
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

	@Column(name = "DKFFXDM", length = 299, nullable = true)
	@IColumn(description="贷款发放行代码")
	private String DKFFXDM;

	public String getDKFFXDM() {
		return DKFFXDM;
	}

	public void setDKFFXDM(String in) {
		DKFFXDM = in;
	}

	@Column(name = "DKHTH", length = 299, nullable = true)
	@IColumn(description="贷款合同号")
	private String DKHTH;

	public String getDKHTH() {
		return DKHTH;
	}

	public void setDKHTH(String in) {
		DKHTH = in;
	}

	@Column(name = "JJH", length = 299, nullable = true)
	@IColumn(description="借据号")
	private String JJH;

	public String getJJH() {
		return JJH;
	}

	public void setJJH(String in) {
		JJH = in;
	}

	@IColumn(tagMethodName="getDKPZTag",description="贷款品种")
	@Column(name = "DKPZ", nullable =true)
	private String DKPZ;

	public static Map<String,String> getDKPZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_LOAN_BREED");
	}

	public String getDKPZ() {
		return DKPZ;
	}

	public void setDKPZ(String in) {
		DKPZ = in;
	}

	@IColumn(tagMethodName="getDBFSTag",description="担保方式")
	@Column(name = "DBFS", nullable =true)
	private String DBFS;

	public static Map<String,String> getDBFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_GUAR_MODE");
	}

	public String getDBFS() {
		return DBFS;
	}

	public void setDBFS(String in) {
		DBFS = in;
	}

	@Column(name = "FFJE", length = 20, nullable = true)
	@IColumn(description="发放金额")
	private String FFJE;

	public String getFFJE() {
		return FFJE;
	}

	public void setFFJE(String in) {
		FFJE = in;
	}

	@Column(name = "DKYE", length = 20, nullable = true)
	@IColumn(description="贷款余额")
	private String DKYE;

	public String getDKYE() {
		return DKYE;
	}

	public void setDKYE(String in) {
		DKYE = in;
	}

	@Column(name = "FFRQ", length = 8, nullable = true)
	@IColumn(description="发放日期")
	private String FFRQ;

	public String getFFRQ() {
		return FFRQ;
	}

	public void setFFRQ(String in) {
		FFRQ = in;
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

	@Column(name = "WYJE", length = 20, nullable = true)
	@IColumn(description="违约金额")
	private String WYJE;

	public String getWYJE() {
		return WYJE;
	}

	public void setWYJE(String in) {
		WYJE = in;
	}

	@Column(name = "WYTS", nullable = true)
	@IColumn(description="违约天数")
	private String WYTS;

	public String getWYTS() {
		return WYTS;
	}

	public void setWYTS(String in) {
		WYTS = in;
	}

	@IColumn(tagMethodName="getWJFLTag",description="五级分类")
	@Column(name = "WJFL", nullable =true)
	private String WJFL;

	public static Map<String,String> getWJFLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_FIVE_CLASS");
	}

	public String getWJFL() {
		return WJFL;
	}

	public void setWJFL(String in) {
		WJFL = in;
	}

	@IColumn(tagMethodName="getHKFSTag",description="还款方式")
	@Column(name = "HKFS", nullable =true)
	private String HKFS;

	public static Map<String,String> getHKFSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_REPAY_MODE");
	}

	public String getHKFS() {
		return HKFS;
	}

	public void setHKFS(String in) {
		HKFS = in;
	}

	@Column(name = "ZJYCHKJE", length = 20, nullable = true)
	@IColumn(description="最近一次还款金额")
	private String ZJYCHKJE;

	public String getZJYCHKJE() {
		return ZJYCHKJE;
	}

	public void setZJYCHKJE(String in) {
		ZJYCHKJE = in;
	}

	@Column(name = "ZJYCHKRQ", length = 8, nullable = true)
	@IColumn(description="最近一次还款日期")
	private String ZJYCHKRQ;

	public String getZJYCHKRQ() {
		return ZJYCHKRQ;
	}

	public void setZJYCHKRQ(String in) {
		ZJYCHKRQ = in;
	}

	@Column(name = "KHZZ", length = 500, nullable = true)
	@IColumn(description="客户住址")
	private String KHZZ;

	public String getKHZZ() {
		return KHZZ;
	}

	public void setKHZZ(String in) {
		KHZZ = in;
	}

	@IColumn(tagMethodName="getZZXZQHDMTag",description="住址行政区划代码")
	@Column(name = "ZZXZQHDM", nullable =true)
	private String ZZXZQHDM;

	public static Map<String,String> getZZXZQHDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getZZXZQHDM() {
		return ZZXZQHDM;
	}

	public void setZZXZQHDM(String in) {
		ZZXZQHDM = in;
	}

	@Column(name = "XXMC", length = 299, nullable = true)
	@IColumn(description="学校名称")
	private String XXMC;

	public String getXXMC() {
		return XXMC;
	}

	public void setXXMC(String in) {
		XXMC = in;
	}

	@Column(name = "XXDZ", length = 500, nullable = true)
	@IColumn(description="学校地址")
	private String XXDZ;

	public String getXXDZ() {
		return XXDZ;
	}

	public void setXXDZ(String in) {
		XXDZ = in;
	}

	@IColumn(tagMethodName="getXXXZQHDMTag",description="学校行政区划代码")
	@Column(name = "XXXZQHDM", nullable =true)
	private String XXXZQHDM;

	public static Map<String,String> getXXXZQHDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getXXXZQHDM() {
		return XXXZQHDM;
	}

	public void setXXXZQHDM(String in) {
		XXXZQHDM = in;
	}

	@Column(name = "XSZH", length = 299, nullable = true)
	@IColumn(description="学生证号")
	private String XSZH;

	public String getXSZH() {
		return XSZH;
	}

	public void setXSZH(String in) {
		XSZH = in;
	}

	@Column(name = "DKSJTZZ", length = 500, nullable = true)
	@IColumn(description="贷款时家庭住址")
	private String DKSJTZZ;

	public String getDKSJTZZ() {
		return DKSJTZZ;
	}

	public void setDKSJTZZ(String in) {
		DKSJTZZ = in;
	}

	@IColumn(tagMethodName="getDKSJTZZXZQHDMTag",description="贷款时家庭住址行政区划代码")
	@Column(name = "DKSJTZZXZQHDM", nullable =true)
	private String DKSJTZZXZQHDM;

	public static Map<String,String> getDKSJTZZXZQHDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getDKSJTZZXZQHDM() {
		return DKSJTZZXZQHDM;
	}

	public void setDKSJTZZXZQHDM(String in) {
		DKSJTZZXZQHDM = in;
	}

	@IColumn(tagMethodName="getZXDKLXTag",description="助学贷款类型")
	@Column(name = "ZXDKLX", nullable =true)
	private String ZXDKLX;

	public static Map<String,String> getZXDKLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_EDU_LOAN_TYPE");
	}

	public String getZXDKLX() {
		return ZXDKLX;
	}

	public void setZXDKLX(String in) {
		ZXDKLX = in;
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

