package zdzsystem.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.lang.Double;
import java.lang.Float;
import java.sql.Time;
import framework.interfaces.IColumn;
import framework.show.ShowContext;
import framework.helper.TypeParse;
import extend.helper.HelpTool;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;

import zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR;

import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import framework.interfaces.IEntity;
import java.util.Set;

import coresystem.dto.UserInfo;
import coresystem.dto.InstInfo;

@Entity
@Table(name = "ZDZ_ZXDJXX")
@IEntity(description= "在先冻结信息")
public class AutoDTO_ZDZ_ZXDJXX implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5", isNullable = false)
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4", isNullable = false)
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3", isNullable = false)
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2", isNullable = false)
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1", isNullable = false)
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
	private UserInfo operationUser;

	public UserInfo getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(UserInfo in) {
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
	@JoinColumn(name = "instInfo")
	private InstInfo instInfo;

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo in) {
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BDHM", nullable = true)
	@IColumn(isNullable = false,description="控制请求单号")
	private AutoDTO_ZDZ_KZQQJTNR BDHM;

	public AutoDTO_ZDZ_KZQQJTNR getBDHM() {
		return BDHM;
	}

	public void setBDHM(AutoDTO_ZDZ_KZQQJTNR bDHM) {
		BDHM = bDHM;
	}

	@Column(name = "CCXH", length = 255, nullable = true)
	@IColumn(description="控制序号")
	private String CCXH;

	public String getCCXH() {
		return CCXH;
	}

	public void setCCXH(String in) {
		CCXH = in;
	}

	@Column(name = "CSXH", length = 255, nullable = true)
	@IColumn(description="措施序号")
	private String CSXH;

	public String getCSXH() {
		return CSXH;
	}

	public void setCSXH(String in) {
		CSXH = in;
	}

	@Column(name = "DJJG", length = 100, nullable = true)
	@IColumn(description="在先冻结机关")
	private String DJJG;

	public String getDJJG() {
		return DJJG;
	}

	public void setDJJG(String in) {
		DJJG = in;
	}

	@Column(name = "DJWH", length = 100, nullable = true)
	@IColumn(description="在先冻结文号")
	private String DJWH;

	public String getDJWH() {
		return DJWH;
	}

	public void setDJWH(String in) {
		DJWH = in;
	}

	@Column(name = "DJJE", length = 50, nullable = true)
	@IColumn(description="在先冻结金额")
	private String DJJE;

	public String getDJJE() {
		return DJJE;
	}

	public void setDJJE(String in) {
		DJJE = in;
	}

	@Column(name = "DJJZRQ", length = 50, nullable = true)
	@IColumn(description="在先冻结到期日")
	private String DJJZRQ;

	public String getDJJZRQ() {
		return DJJZRQ;
	}

	public void setDJJZRQ(String in) {
		DJJZRQ = in;
	}

	@Column(name = "BEIZ", length = 2000, nullable = true)
	@IColumn(description="备注")
	private String BEIZ;

	public String getBEIZ() {
		return BEIZ;
	}

	public void setBEIZ(String in) {
		BEIZ = in;
	}
	
	@Column(name = "BatchNumber", length = 50, nullable = true)
	@IColumn(description="批次号")
	private String BatchNumber;

	public String getBatchNumber() {
		return BatchNumber;
	}

	public void setBatchNumber(String in) {
		BatchNumber = in;
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

