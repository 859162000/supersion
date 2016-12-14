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
@Table(name = "EAS_SXQK")
@IEntity(description= "授信情况")
public class AutoDTO_EAS_SXQK implements java.io.Serializable{

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

	@IColumn(tagMethodName="getKHLXTag",description="客户类型")
	@Column(name = "KHLX", nullable =true)
	private String KHLX;

	public static Map<String,String> getKHLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CUST_TYPE_1_1");
	}

	public String getKHLX() {
		return KHLX;
	}

	public void setKHLX(String in) {
		KHLX = in;
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

	@IColumn(tagMethodName="getKHGJDMTag",description="客户国籍代码")
	@Column(name = "KHGJDM", nullable =true)
	private String KHGJDM;

	public static Map<String,String> getKHGJDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getKHGJDM() {
		return KHGJDM;
	}

	public void setKHGJDM(String in) {
		KHGJDM = in;
	}

	@Column(name = "SXYHDM", length = 299, nullable = true)
	@IColumn(description="授信银行代码")
	private String SXYHDM;

	public String getSXYHDM() {
		return SXYHDM;
	}

	public void setSXYHDM(String in) {
		SXYHDM = in;
	}

	@Column(name = "SXYHMC", length = 299, nullable = true)
	@IColumn(description="授信银行名称")
	private String SXYHMC;

	public String getSXYHMC() {
		return SXYHMC;
	}

	public void setSXYHMC(String in) {
		SXYHMC = in;
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
	private String SXED;

	public String getSXED() {
		return SXED;
	}

	public void setSXED(String in) {
		SXED = in;
	}

	@Column(name = "DKSXED", length = 20, nullable = true)
	@IColumn(description="贷款授信额度")
	private String DKSXED;

	public String getDKSXED() {
		return DKSXED;
	}

	public void setDKSXED(String in) {
		DKSXED = in;
	}

	@Column(name = "SXQSRQ", length = 8, nullable = true)
	@IColumn(description="授信起始日期")
	private String SXQSRQ;

	public String getSXQSRQ() {
		return SXQSRQ;
	}

	public void setSXQSRQ(String in) {
		SXQSRQ = in;
	}

	@Column(name = "SXDQRQ", length = 8, nullable = true)
	@IColumn(description="授信到期日期")
	private String SXDQRQ;

	public String getSXDQRQ() {
		return SXDQRQ;
	}

	public void setSXDQRQ(String in) {
		SXDQRQ = in;
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

	@Column(name = "CYZQYE", length = 20, nullable = true)
	@IColumn(description="持有债券余额")
	private String CYZQYE;

	public String getCYZQYE() {
		return CYZQYE;
	}

	public void setCYZQYE(String in) {
		CYZQYE = in;
	}

	@Column(name = "CYGQYE", length = 20, nullable = true)
	@IColumn(description="持有股权余额")
	private String CYGQYE;

	public String getCYGQYE() {
		return CYGQYE;
	}

	public void setCYGQYE(String in) {
		CYGQYE = in;
	}

	@Column(name = "QTBNXYFXZCYE", length = 20, nullable = true)
	@IColumn(description="其他表内信用风险资产余额")
	private String QTBNXYFXZCYE;

	public String getQTBNXYFXZCYE() {
		return QTBNXYFXZCYE;
	}

	public void setQTBNXYFXZCYE(String in) {
		QTBNXYFXZCYE = in;
	}

	@Column(name = "BWYWYE", length = 20, nullable = true)
	@IColumn(description="表外业务余额")
	private String BWYWYE;

	public String getBWYWYE() {
		return BWYWYE;
	}

	public void setBWYWYE(String in) {
		BWYWYE = in;
	}

	@Column(name = "XYYWYEZYSXED", length = 20, nullable = true)
	@IColumn(description="现有业务余额占用授信额度")
	private String XYYWYEZYSXED;

	public String getXYYWYEZYSXED() {
		return XYYWYEZYSXED;
	}

	public void setXYYWYEZYSXED(String in) {
		XYYWYEZYSXED = in;
	}

	@Column(name = "DKYEZYDKSXED", length = 20, nullable = true)
	@IColumn(description="贷款余额占用贷款授信额度")
	private String DKYEZYDKSXED;

	public String getDKYEZYDKSXED() {
		return DKYEZYDKSXED;
	}

	public void setDKYEZYDKSXED(String in) {
		DKYEZYDKSXED = in;
	}

	@Column(name = "SKSYSXED", length = 20, nullable = true)
	@IColumn(description="尚可使用授信额度")
	private String SKSYSXED;

	public String getSKSYSXED() {
		return SKSYSXED;
	}

	public void setSKSYSXED(String in) {
		SKSYSXED = in;
	}

	@Column(name = "SKSYDKSXED", length = 20, nullable = true)
	@IColumn(description="尚可使用贷款授信额度")
	private String SKSYDKSXED;

	public String getSKSYDKSXED() {
		return SKSYDKSXED;
	}

	public void setSKSYDKSXED(String in) {
		SKSYDKSXED = in;
	}

	@IColumn(tagMethodName="getGLFSXBSTag",description="关联方授信标识")
	@Column(name = "GLFSXBS", nullable =true)
	private String GLFSXBS;

	public static Map<String,String> getGLFSXBSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_RELA_CRDT_IDTFY");
	}

	public String getGLFSXBS() {
		return GLFSXBS;
	}

	public void setGLFSXBS(String in) {
		GLFSXBS = in;
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

