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
@Table(name = "EAS_GYB")
@IEntity(description= "柜员表")
public class AutoDTO_EAS_GYB implements java.io.Serializable{

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

	@Column(name = "GYH", length = 299, nullable = true)
	@IColumn(description="柜员号")
	private String GYH;

	public String getGYH() {
		return GYH;
	}

	public void setGYH(String in) {
		GYH = in;
	}

	@Column(name = "GH", length = 299, nullable = true)
	@IColumn(description="工号")
	private String GH;

	public String getGH() {
		return GH;
	}

	public void setGH(String in) {
		GH = in;
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

	@Column(name = "NBJGH", length = 299, nullable = true)
	@IColumn(description="内部机构号")
	private String NBJGH;

	public String getNBJGH() {
		return NBJGH;
	}

	public void setNBJGH(String in) {
		NBJGH = in;
	}

	@Column(name = "ZXJGDM", length = 299, nullable = true)
	@IColumn(description="总行机构代码")
	private String ZXJGDM;

	public String getZXJGDM() {
		return ZXJGDM;
	}

	public void setZXJGDM(String in) {
		ZXJGDM = in;
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

	@Column(name = "YXJGMC", length = 299, nullable = true)
	@IColumn(description="银行机构名称")
	private String YXJGMC;

	public String getYXJGMC() {
		return YXJGMC;
	}

	public void setYXJGMC(String in) {
		YXJGMC = in;
	}

	@Column(name = "GYLX", length = 299, nullable = true)
	@IColumn(description="柜员类型")
	private String GYLX;

	public String getGYLX() {
		return GYLX;
	}

	public void setGYLX(String in) {
		GYLX = in;
	}

	@IColumn(tagMethodName="getSFSTGYTag",description="是否实体柜员")
	@Column(name = "SFSTGY", nullable =true)
	private String SFSTGY;

	public static Map<String,String> getSFSTGYTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_ENTITY_TELLER");
	}

	public String getSFSTGY() {
		return SFSTGY;
	}

	public void setSFSTGY(String in) {
		SFSTGY = in;
	}

	@IColumn(tagMethodName="getKHJLBZTag",description="客户经理标志")
	@Column(name = "KHJLBZ", nullable =true)
	private String KHJLBZ;

	public static Map<String,String> getKHJLBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CUST_MGER_IND");
	}

	public String getKHJLBZ() {
		return KHJLBZ;
	}

	public void setKHJLBZ(String in) {
		KHJLBZ = in;
	}

	@IColumn(tagMethodName="getJBZWBZTag",description="经办账务标志")
	@Column(name = "JBZWBZ", nullable =true)
	private String JBZWBZ;

	public static Map<String,String> getJBZWBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_OPER_FIN_IND");
	}

	public String getJBZWBZ() {
		return JBZWBZ;
	}

	public void setJBZWBZ(String in) {
		JBZWBZ = in;
	}

	@IColumn(tagMethodName="getQXGLBZTag",description="权限管理标志")
	@Column(name = "QXGLBZ", nullable =true)
	private String QXGLBZ;

	public static Map<String,String> getQXGLBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_PRVLG_MGMT_IND");
	}

	public String getQXGLBZ() {
		return QXGLBZ;
	}

	public void setQXGLBZ(String in) {
		QXGLBZ = in;
	}

	@IColumn(tagMethodName="getYBGLBZTag",description="一般管理标志")
	@Column(name = "YBGLBZ", nullable =true)
	private String YBGLBZ;

	public static Map<String,String> getYBGLBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_COMN_MGMT_IND");
	}

	public String getYBGLBZ() {
		return YBGLBZ;
	}

	public void setYBGLBZ(String in) {
		YBGLBZ = in;
	}

	@IColumn(tagMethodName="getXDYBZTag",description="信贷员标志")
	@Column(name = "XDYBZ", nullable =true)
	private String XDYBZ;

	public static Map<String,String> getXDYBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CRDT_IND");
	}

	public String getXDYBZ() {
		return XDYBZ;
	}

	public void setXDYBZ(String in) {
		XDYBZ = in;
	}

	@IColumn(tagMethodName="getKGYBZTag",description="库管员标志")
	@Column(name = "KGYBZ", nullable =true)
	private String KGYBZ;

	public static Map<String,String> getKGYBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_WHS_IND");
	}

	public String getKGYBZ() {
		return KGYBZ;
	}

	public void setKGYBZ(String in) {
		KGYBZ = in;
	}

	@IColumn(tagMethodName="getZHGYBZTag",description="综合柜员标志")
	@Column(name = "ZHGYBZ", nullable =true)
	private String ZHGYBZ;

	public static Map<String,String> getZHGYBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_SYN_TELLER_IND");
	}

	public String getZHGYBZ() {
		return ZHGYBZ;
	}

	public void setZHGYBZ(String in) {
		ZHGYBZ = in;
	}

	@IColumn(tagMethodName="getSQBZTag",description="授权标志")
	@Column(name = "SQBZ", nullable =true)
	private String SQBZ;

	public static Map<String,String> getSQBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_AUTH_IND");
	}

	public String getSQBZ() {
		return SQBZ;
	}

	public void setSQBZ(String in) {
		SQBZ = in;
	}

	@Column(name = "SQFW", length = 299, nullable = true)
	@IColumn(description="授权范围")
	private String SQFW;

	public String getSQFW() {
		return SQFW;
	}

	public void setSQFW(String in) {
		SQFW = in;
	}

	@Column(name = "GWBH", length = 299, nullable = true)
	@IColumn(description="岗位编号")
	private String GWBH;

	public String getGWBH() {
		return GWBH;
	}

	public void setGWBH(String in) {
		GWBH = in;
	}

	@Column(name = "GYYHJB", length = 299, nullable = true)
	@IColumn(description="柜员用户级别")
	private String GYYHJB;

	public String getGYYHJB() {
		return GYYHJB;
	}

	public void setGYYHJB(String in) {
		GYYHJB = in;
	}

	@Column(name = "GYQXJB", length = 299, nullable = true)
	@IColumn(description="柜员权限级别")
	private String GYQXJB;

	public String getGYQXJB() {
		return GYQXJB;
	}

	public void setGYQXJB(String in) {
		GYQXJB = in;
	}

	@Column(name = "SGRQ", length = 8, nullable = true)
	@IColumn(description="上岗日期")
	private String SGRQ;

	public String getSGRQ() {
		return SGRQ;
	}

	public void setSGRQ(String in) {
		SGRQ = in;
	}

	@Column(name = "GWZT", length = 299, nullable = true)
	@IColumn(description="柜员状态")
	private String GWZT;

	public String getGWZT() {
		return GWZT;
	}

	public void setGWZT(String in) {
		GWZT = in;
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

