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
@Table(name = "NCR_3_2")
@IEntity(description= "法定代表人、高管及重要关联人信息")
public class AutoDTO_NCR_3_2 implements java.io.Serializable{

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

	@IColumn(tagMethodName="getATag",description="关系人类型")
	@Column(name = "A", nullable =true)
	private String A;

	public static Map<String,String> getATag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_RELA_PERSON_TYPE_3_2");
	}

	public String getA() {
		return A;
	}

	public void setA(String in) {
		A = in;
	}

	@Column(name = "B", length = 299, nullable = true)
	@IColumn(description="姓名")
	private String B;

	public String getB() {
		return B;
	}

	public void setB(String in) {
		B = in;
	}

	@IColumn(tagMethodName="getCTag",description="国别代码")
	@Column(name = "C", nullable =true)
	private String C;

	public static Map<String,String> getCTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getC() {
		return C;
	}

	public void setC(String in) {
		C = in;
	}

	@Column(name = "D", length = 299, nullable = true)
	@IColumn(description="身份证号码")
	private String D;

	public String getD() {
		return D;
	}

	public void setD(String in) {
		D = in;
	}

	@Column(name = "E_1", length = 299, nullable = true)
	@IColumn(description="护照1-护照号码")
	private String E_1;

	public String getE_1() {
		return E_1;
	}

	public void setE_1(String in) {
		E_1 = in;
	}

	@Column(name = "F_1", length = 8, nullable = true)
	@IColumn(description="护照1-签发日期")
	private String F_1;

	public String getF_1() {
		return F_1;
	}

	public void setF_1(String in) {
		F_1 = in;
	}

	@Column(name = "G_1", length = 8, nullable = true)
	@IColumn(description="护照1-到期日期")
	private String G_1;

	public String getG_1() {
		return G_1;
	}

	public void setG_1(String in) {
		G_1 = in;
	}

	@Column(name = "E_2", length = 299, nullable = true)
	@IColumn(description="护照2-护照号码")
	private String E_2;

	public String getE_2() {
		return E_2;
	}

	public void setE_2(String in) {
		E_2 = in;
	}

	@Column(name = "F_2", length = 8, nullable = true)
	@IColumn(description="护照2-签发日期")
	private String F_2;

	public String getF_2() {
		return F_2;
	}

	public void setF_2(String in) {
		F_2 = in;
	}

	@Column(name = "G_2", length = 8, nullable = true)
	@IColumn(description="护照2-到期日期")
	private String G_2;

	public String getG_2() {
		return G_2;
	}

	public void setG_2(String in) {
		G_2 = in;
	}

	@Column(name = "E_N", length = 299, nullable = true)
	@IColumn(description="护照N-护照号码")
	private String E_N;

	public String getE_N() {
		return E_N;
	}

	public void setE_N(String in) {
		E_N = in;
	}

	@Column(name = "F_N", length = 8, nullable = true)
	@IColumn(description="护照N-签发日期")
	private String F_N;

	public String getF_N() {
		return F_N;
	}

	public void setF_N(String in) {
		F_N = in;
	}

	@Column(name = "G_N", length = 8, nullable = true)
	@IColumn(description="护照N-到期日期")
	private String G_N;

	public String getG_N() {
		return G_N;
	}

	public void setG_N(String in) {
		G_N = in;
	}

	@IColumn(tagMethodName="getH_1Tag",description="其他证件1-证件类型")
	@Column(name = "H_1", nullable =true)
	private String H_1;

	public static Map<String,String> getH_1Tag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_OTHER_CERT_TYPE");
	}

	public String getH_1() {
		return H_1;
	}

	public void setH_1(String in) {
		H_1 = in;
	}

	@Column(name = "I_1", length = 299, nullable = true)
	@IColumn(description="其他证件1-证件号码")
	private String I_1;

	public String getI_1() {
		return I_1;
	}

	public void setI_1(String in) {
		I_1 = in;
	}

	@IColumn(tagMethodName="getH_NTag",description="其他证件N-证件类型")
	@Column(name = "H_N", nullable =true)
	private String H_N;

	public static Map<String,String> getH_NTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_OTHER_CERT_TYPE");
	}

	public String getH_N() {
		return H_N;
	}

	public void setH_N(String in) {
		H_N = in;
	}

	@Column(name = "I_N", length = 299, nullable = true)
	@IColumn(description="其他证件N-证件号码")
	private String I_N;

	public String getI_N() {
		return I_N;
	}

	public void setI_N(String in) {
		I_N = in;
	}

	@Column(name = "J", length = 8, nullable = true)
	@IColumn(description="更新信息日期")
	private String J;

	public String getJ() {
		return J;
	}

	public void setJ(String in) {
		J = in;
	}

	@IColumn(tagMethodName="getKTag",description="实际控制人标识")
	@Column(name = "K", nullable =true)
	private String K;

	public static Map<String,String> getKTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_ACTL_CTRL_PERSON_IDTFY");
	}

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@Column(name = "REL_CUST_NAME", nullable = true)
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