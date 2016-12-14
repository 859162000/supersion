package ncr.dto;

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
import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import framework.interfaces.IEntity;
import java.util.Set;

import coresystem.dto.UserInfo;
import coresystem.dto.InstInfo;

@Entity
@Table(name = "NCR_3_3")
@IEntity(description= "重要股东及主要关联企业")
public class AutoDTO_NCR_3_3 implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

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

	@IColumn(tagMethodName="getATag",description="关联类型")
	@Column(name = "A", nullable =true)
	private String A;

	public static Map<String,String> getATag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_RELA_TYPE");
	}

	public String getA() {
		return A;
	}

	public void setA(String in) {
		A = in;
	}

	@Column(name = "B", length = 299, nullable = true)
	@IColumn(description="股东/关联企业名称")
	private String B;

	public String getB() {
		return B;
	}

	public void setB(String in) {
		B = in;
	}

	@IColumn(tagMethodName="getCTag",description="股东/关联企业类型")
	@Column(name = "C", nullable =true)
	private String C;

	public static Map<String,String> getCTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_SHAREHD_RELA_CORP_TYPE");
	}

	public String getC() {
		return C;
	}

	public void setC(String in) {
		C = in;
	}

	@IColumn(tagMethodName="getDTag",description="股东/关联企业证件类型")
	@Column(name = "D", nullable =true)
	private String D;

	public static Map<String,String> getDTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_SH_REL_CORP_CERT_TYPE");
	}

	public String getD() {
		return D;
	}

	public void setD(String in) {
		D = in;
	}

	@Column(name = "E", length = 299, nullable = true)
	@IColumn(description="股东/关联企业证件代码")
	private String E;

	public String getE() {
		return E;
	}

	public void setE(String in) {
		E = in;
	}

	@Column(name = "F", length = 299, nullable = true)
	@IColumn(description="登记注册代码")
	private String F;

	public String getF() {
		return F;
	}

	public void setF(String in) {
		F = in;
	}

	@Column(name = "G", length = 299, nullable = true)
	@IColumn(description="股东/关联企业客户代码")
	private String G;

	public String getG() {
		return G;
	}

	public void setG(String in) {
		G = in;
	}

	@IColumn(tagMethodName="getHTag",description="国别代码")
	@Column(name = "H", nullable =true)
	private String H;

	public static Map<String,String> getHTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getH() {
		return H;
	}

	public void setH(String in) {
		H = in;
	}

	@Column(name = "I", length = 20, nullable = true)
	@IColumn(description="持股比例(%)")
	private BigDecimal I;

	public BigDecimal getI() {
		return I;
	}

	public void setI(String in) {
		I = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "J", length = 8, nullable = true)
	@IColumn(description="股东结构对应日期")
	private String J;

	public String getJ() {
		return J;
	}

	public void setJ(String in) {
		J = in;
	}

	@Column(name = "K", length = 8, nullable = true)
	@IColumn(description="更新信息日期")
	private String K;

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@IColumn(tagMethodName="getLTag",description="实际控制人标识")
	@Column(name = "L", nullable =true)
	private String L;

	public static Map<String,String> getLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_ACTL_CTRL_PERSON_IDTFY");
	}

	public String getL() {
		return L;
	}

	public void setL(String in) {
		L = in;
	}

	@Column(name = "REL_CUST_NAME", length = 299, nullable = true)
	@IColumn(description="客户名称")
	private String REL_CUST_NAME;

	public String getREL_CUST_NAME() {
		return REL_CUST_NAME;
	}

	public void setREL_CUST_NAME(String in) {
		REL_CUST_NAME = in;
	}

	@Column(name = "REL_3_1_A", length = 299, nullable = true)
	@IColumn(description="客户编号")
	private String REL_3_1_A;

	public String getREL_3_1_A() {
		return REL_3_1_A;
	}

	public void setREL_3_1_A(String in) {
		REL_3_1_A = in;
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

