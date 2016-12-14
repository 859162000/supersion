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
@Table(name = "NCR_1_6")
@IEntity(description= "表外业务明细")
public class AutoDTO_NCR_1_6 implements java.io.Serializable{

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

	@Column(name = "A", length = 299, nullable = true)
	@IColumn(description="表外业务客户名称")
	private String A;

	public String getA() {
		return A;
	}

	public void setA(String in) {
		A = in;
	}

	@Column(name = "B", length = 299, nullable = true)
	@IColumn(description="表外业务客户代码")
	private String B;

	public String getB() {
		return B;
	}

	public void setB(String in) {
		B = in;
	}

	@Column(name = "C", length = 299, nullable = true)
	@IColumn(description="承办银行机构代码")
	private String C;

	public String getC() {
		return C;
	}

	public void setC(String in) {
		C = in;
	}

	@IColumn(tagMethodName="getDTag",description="表外业务类型")
	@Column(name = "D", nullable =true)
	private String D;

	public static Map<String,String> getDTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_OFF_BIZ_TYPE");
	}

	public String getD() {
		return D;
	}

	public void setD(String in) {
		D = in;
	}

	@Column(name = "E", length = 299, nullable = true)
	@IColumn(description="授信号码")
	private String E;

	public String getE() {
		return E;
	}

	public void setE(String in) {
		E = in;
	}

	@Column(name = "F", length = 299, nullable = true)
	@IColumn(description="合同号")
	private String F;

	public String getF() {
		return F;
	}

	public void setF(String in) {
		F = in;
	}

	@Column(name = "G", length = 299, nullable = true)
	@IColumn(description="业务号码")
	private String G;

	public String getG() {
		return G;
	}

	public void setG(String in) {
		G = in;
	}

	@Column(name = "H", length = 20, nullable = true)
	@IColumn(description="业务余额(万元)")
	private BigDecimal H;

	public BigDecimal getH() {
		return H;
	}

	public void setH(String in) {
		H = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getITag",description="币种代码")
	@Column(name = "I", nullable =true)
	private String I;

	public static Map<String,String> getITag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_CURR_CD");
	}

	public String getI() {
		return I;
	}

	public void setI(String in) {
		I = in;
	}

	@Column(name = "J", length = 8, nullable = true)
	@IColumn(description="发生日期")
	private String J;

	public String getJ() {
		return J;
	}

	public void setJ(String in) {
		J = in;
	}

	@Column(name = "K", length = 8, nullable = true)
	@IColumn(description="到期日期")
	private String K;

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@Column(name = "L", length = 20, nullable = true)
	@IColumn(description="保证金金额(万元)")
	private BigDecimal L;

	public BigDecimal getL() {
		return L;
	}

	public void setL(String in) {
		L = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getMTag",description="产业结构调整类型")
	@Column(name = "M", nullable =true)
	private String M;

	public static Map<String,String> getMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_INDUSTRY_STRU_ADJ_TYPE");
	}

	public String getM() {
		return M;
	}

	public void setM(String in) {
		M = in;
	}

	@IColumn(tagMethodName="getNTag",description="工业转型升级标识")
	@Column(name = "N", nullable =true)
	private String N;

	public static Map<String,String> getNTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_INDUSTRY_UPGD_IDTFY");
	}

	public String getN() {
		return N;
	}

	public void setN(String in) {
		N = in;
	}

	@IColumn(tagMethodName="getOTag",description="战略新兴产业类型")
	@Column(name = "O", nullable =true)
	private String O;

	public static Map<String,String> getOTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_NEW_INDUSTRY_TYPE");
	}

	public String getO() {
		return O;
	}

	public void setO(String in) {
		O = in;
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
