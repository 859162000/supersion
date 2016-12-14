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
@Table(name = "NCR_5_1")
@IEntity(description= "个人贷款违约情况统计表")
public class AutoDTO_NCR_5_1 implements java.io.Serializable{

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
	@IColumn(description="贷款发放行代码")
	private String D;

	public String getD() {
		return D;
	}

	public void setD(String in) {
		D = in;
	}

	@Column(name = "E", length = 299, nullable = true)
	@IColumn(description="贷款合同号")
	private String E;

	public String getE() {
		return E;
	}

	public void setE(String in) {
		E = in;
	}

	@Column(name = "F", length = 299, nullable = true)
	@IColumn(description="借据号")
	private String F;

	public String getF() {
		return F;
	}

	public void setF(String in) {
		F = in;
	}

	@IColumn(tagMethodName="getGTag",description="贷款品种")
	@Column(name = "G", nullable =true)
	private String G;

	public static Map<String,String> getGTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_LOAN_BREED");
	}

	public String getG() {
		return G;
	}

	public void setG(String in) {
		G = in;
	}

	@IColumn(tagMethodName="getHTag",description="担保方式")
	@Column(name = "H", nullable =true)
	private String H;

	public static Map<String,String> getHTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_GUAR_MODE");
	}

	public String getH() {
		return H;
	}

	public void setH(String in) {
		H = in;
	}

	@Column(name = "I", length = 20, nullable = true)
	@IColumn(description="发放金额(万元)")
	private BigDecimal I;

	public BigDecimal getI() {
		return I;
	}

	public void setI(String in) {
		I = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "J", length = 20, nullable = true)
	@IColumn(description="贷款余额(万元)")
	private BigDecimal J;

	public BigDecimal getJ() {
		return J;
	}

	public void setJ(String in) {
		J = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "K", length = 8, nullable = true)
	@IColumn(description="发放日期")
	private String K;

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@Column(name = "L", length = 8, nullable = true)
	@IColumn(description="到期日期")
	private String L;

	public String getL() {
		return L;
	}

	public void setL(String in) {
		L = in;
	}

	@Column(name = "M", length = 20, nullable = true)
	@IColumn(description="违约金额(万元)")
	private BigDecimal M;

	public BigDecimal getM() {
		return M;
	}

	public void setM(String in) {
		M = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "N", nullable = true)
	@IColumn(description="违约天数")
	private Integer N;

	public Integer getN() {
		return N;
	}

	public void setN(String in) {
		N = TypeParse.parseInt(in);
	}

	@IColumn(tagMethodName="getOTag",description="五级分类")
	@Column(name = "O", nullable =true)
	private String O;

	public static Map<String,String> getOTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_FIVE_CLASS");
	}

	public String getO() {
		return O;
	}

	public void setO(String in) {
		O = in;
	}

	@IColumn(tagMethodName="getPTag",description="还款方式")
	@Column(name = "P", nullable =true)
	private String P;

	public static Map<String,String> getPTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_REPAY_MODE");
	}

	public String getP() {
		return P;
	}

	public void setP(String in) {
		P = in;
	}

	@Column(name = "Q", length = 20, nullable = true)
	@IColumn(description="最近一次还款金额(万元)")
	private BigDecimal Q;

	public BigDecimal getQ() {
		return Q;
	}

	public void setQ(String in) {
		Q = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "R", length = 8, nullable = true)
	@IColumn(description="最近一次还款日期")
	private String R;

	public String getR() {
		return R;
	}

	public void setR(String in) {
		R = in;
	}

	@Column(name = "S", length = 500, nullable = true)
	@IColumn(description="客户住址")
	private String S;

	public String getS() {
		return S;
	}

	public void setS(String in) {
		S = in;
	}

	@IColumn(tagMethodName="getTTag",description="住址行政区划代码")
	@Column(name = "T", nullable =true)
	private String T;

	public static Map<String,String> getTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getT() {
		return T;
	}

	public void setT(String in) {
		T = in;
	}

	@IColumn(tagMethodName="getUTag",description="客户其他证件类型")
	@Column(name = "U", nullable =true)
	private String U;

	public static Map<String,String> getUTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_CUST_OTHER_CERT_TYPE");
	}

	public String getU() {
		return U;
	}

	public void setU(String in) {
		U = in;
	}

	@Column(name = "V", length = 299, nullable = true)
	@IColumn(description="客户其他证件号码")
	private String V;

	public String getV() {
		return V;
	}

	public void setV(String in) {
		V = in;
	}

	@Column(name = "W_1", length = 299, nullable = true)
	@IColumn(description="共同债务人1-姓名")
	private String W_1;

	public String getW_1() {
		return W_1;
	}

	public void setW_1(String in) {
		W_1 = in;
	}

	@IColumn(tagMethodName="getX_1Tag",description="共同债务人1-证件类型")
	@Column(name = "X_1", nullable =true)
	private String X_1;

	public static Map<String,String> getX_1Tag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_CO_DEBT_PER_CERT_TYPE");
	}

	public String getX_1() {
		return X_1;
	}

	public void setX_1(String in) {
		X_1 = in;
	}

	@Column(name = "Y_1", length = 299, nullable = true)
	@IColumn(description="共同债务人1-证件号码")
	private String Y_1;

	public String getY_1() {
		return Y_1;
	}

	public void setY_1(String in) {
		Y_1 = in;
	}

	@Column(name = "W_N", length = 299, nullable = true)
	@IColumn(description="共同债务人N-姓名")
	private String W_N;

	public String getW_N() {
		return W_N;
	}

	public void setW_N(String in) {
		W_N = in;
	}

	@IColumn(tagMethodName="getX_NTag",description="共同债务人N-证件类型")
	@Column(name = "X_N", nullable =true)
	private String X_N;

	public static Map<String,String> getX_NTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_CO_DEBT_PER_CERT_TYPE");
	}

	public String getX_N() {
		return X_N;
	}

	public void setX_N(String in) {
		X_N = in;
	}

	@Column(name = "Y_N", length = 299, nullable = true)
	@IColumn(description="共同债务人N-证件号码")
	private String Y_N;

	public String getY_N() {
		return Y_N;
	}

	public void setY_N(String in) {
		Y_N = in;
	}

	@Column(name = "Z", length = 299, nullable = true)
	@IColumn(description="学校名称")
	private String Z;

	public String getZ() {
		return Z;
	}

	public void setZ(String in) {
		Z = in;
	}

	@Column(name = "AA", length = 500, nullable = true)
	@IColumn(description="学校地址")
	private String AA;

	public String getAA() {
		return AA;
	}

	public void setAA(String in) {
		AA = in;
	}

	@IColumn(tagMethodName="getABTag",description="学校行政区划代码")
	@Column(name = "AB", nullable =true)
	private String AB;

	public static Map<String,String> getABTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getAB() {
		return AB;
	}

	public void setAB(String in) {
		AB = in;
	}

	@Column(name = "AC", length = 299, nullable = true)
	@IColumn(description="学生证号")
	private String AC;

	public String getAC() {
		return AC;
	}

	public void setAC(String in) {
		AC = in;
	}

	@Column(name = "AD", length = 500, nullable = true)
	@IColumn(description="贷款时家庭住址")
	private String AD;

	public String getAD() {
		return AD;
	}

	public void setAD(String in) {
		AD = in;
	}

	@IColumn(tagMethodName="getAETag",description="贷款时家庭住址行政区划代码")
	@Column(name = "AE", nullable =true)
	private String AE;

	public static Map<String,String> getAETag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getAE() {
		return AE;
	}

	public void setAE(String in) {
		AE = in;
	}

	@IColumn(tagMethodName="getAFTag",description="助学贷款类型")
	@Column(name = "AF", nullable =true)
	private String AF;

	public static Map<String,String> getAFTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_EDU_LOAN_TYPE");
	}

	public String getAF() {
		return AF;
	}

	public void setAF(String in) {
		AF = in;
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