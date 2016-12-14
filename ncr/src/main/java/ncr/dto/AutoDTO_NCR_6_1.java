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
@Table(name = "NCR_6_1")
@IEntity(description= "个人违约贷款担保情况统计表")
public class AutoDTO_NCR_6_1 implements java.io.Serializable{

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
	@IColumn(description="借款人姓名")
	private String A;

	public String getA() {
		return A;
	}

	public void setA(String in) {
		A = in;
	}

	@IColumn(tagMethodName="getBTag",description="有效身份证件类型")
	@Column(name = "B", nullable =true)
	private String B;

	public static Map<String,String> getBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_VALID_IDTFY_CERT_TYPE");
	}

	public String getB() {
		return B;
	}

	public void setB(String in) {
		B = in;
	}

	@Column(name = "C", length = 299, nullable = true)
	@IColumn(description="证件号码")
	private String C;

	public String getC() {
		return C;
	}

	public void setC(String in) {
		C = in;
	}

	@Column(name = "D", length = 299, nullable = true)
	@IColumn(description="贷款合同号")
	private String D;

	public String getD() {
		return D;
	}

	public void setD(String in) {
		D = in;
	}

	@Column(name = "E", length = 299, nullable = true)
	@IColumn(description="担保合同号")
	private String E;

	public String getE() {
		return E;
	}

	public void setE(String in) {
		E = in;
	}

	@IColumn(tagMethodName="getFTag",description="担保合同类型")
	@Column(name = "F", nullable =true)
	private String F;

	public static Map<String,String> getFTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_GUAR_CONTR_TYPE");
	}

	public String getF() {
		return F;
	}

	public void setF(String in) {
		F = in;
	}

	@IColumn(tagMethodName="getGTag",description="押品类型")
	@Column(name = "G", nullable =true)
	private String G;

	public static Map<String,String> getGTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_NEGOT_CLASS_ENCODE");
	}

	public String getG() {
		return G;
	}

	public void setG(String in) {
		G = in;
	}

	@Column(name = "H", length = 299, nullable = true)
	@IColumn(description="押品名称")
	private String H;

	public String getH() {
		return H;
	}

	public void setH(String in) {
		H = in;
	}

	@Column(name = "I", length = 299, nullable = true)
	@IColumn(description="押品代码")
	private String I;

	public String getI() {
		return I;
	}

	public void setI(String in) {
		I = in;
	}

	@Column(name = "J", length = 299, nullable = true)
	@IColumn(description="押品权属人（或保证人）名称")
	private String J;

	public String getJ() {
		return J;
	}

	public void setJ(String in) {
		J = in;
	}

	@IColumn(tagMethodName="getKTag",description="押品权属人（或保证人）类型")
	@Column(name = "K", nullable =true)
	private String K;

	public static Map<String,String> getKTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_NEGOT_ATTRIBUTER_TYPE");
	}

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@IColumn(tagMethodName="getLTag",description="押品权属人（或保证人）证件类型")
	@Column(name = "L", nullable =true)
	private String L;

	public static Map<String,String> getLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_NEGOT_ATTR_CERT_TYPE");
	}

	public String getL() {
		return L;
	}

	public void setL(String in) {
		L = in;
	}

	@Column(name = "M", length = 299, nullable = true)
	@IColumn(description="押品权属人（或保证人）证件代码")
	private String M;

	public String getM() {
		return M;
	}

	public void setM(String in) {
		M = in;
	}

	@Column(name = "N", length = 20, nullable = true)
	@IColumn(description="押品评估价值（或保证金额）(万元)")
	private BigDecimal N;

	public BigDecimal getN() {
		return N;
	}

	public void setN(String in) {
		N = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getOTag",description="押品权属人是否第三方")
	@Column(name = "O", nullable =true)
	private String O;

	public static Map<String,String> getOTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_NEGOT_ATTR_TRDPTY");
	}

	public String getO() {
		return O;
	}

	public void setO(String in) {
		O = in;
	}

	@Column(name = "P", length = 8, nullable = true)
	@IColumn(description="首次估值日期")
	private String P;

	public String getP() {
		return P;
	}

	public void setP(String in) {
		P = in;
	}

	@Column(name = "Q", length = 8, nullable = true)
	@IColumn(description="最新估值日期")
	private String Q;

	public String getQ() {
		return Q;
	}

	public void setQ(String in) {
		Q = in;
	}

	@Column(name = "R", length = 8, nullable = true)
	@IColumn(description="估值到期日期")
	private String R;

	public String getR() {
		return R;
	}

	public void setR(String in) {
		R = in;
	}

	@Column(name = "S", length = 20, nullable = true)
	@IColumn(description="保证人保证能力上限")
	private BigDecimal S;

	public BigDecimal getS() {
		return S;
	}

	public void setS(String in) {
		S = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "T", length = 20, nullable = true)
	@IColumn(description="审批抵质押率(%)")
	private BigDecimal T;

	public BigDecimal getT() {
		return T;
	}

	public void setT(String in) {
		T = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "U", length = 20, nullable = true)
	@IColumn(description="实际抵质押率(%)")
	private BigDecimal U;

	public BigDecimal getU() {
		return U;
	}

	public void setU(String in) {
		U = TypeParse.parseBigDecimal(in);
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


