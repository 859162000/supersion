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
@Table(name = "NCR_1_3")
@IEntity(description= "贷款明细")
public class AutoDTO_NCR_1_3 implements java.io.Serializable{

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
	@IColumn(description="贷款客户名称")
	private String A;

	public String getA() {
		return A;
	}

	public void setA(String in) {
		A = in;
	}

	@Column(name = "B", length = 299, nullable = true)
	@IColumn(description="贷款客户代码")
	private String B;

	public String getB() {
		return B;
	}

	public void setB(String in) {
		B = in;
	}

	@Column(name = "C", length = 299, nullable = true)
	@IColumn(description="贷款发放行代码")
	private String C;

	public String getC() {
		return C;
	}

	public void setC(String in) {
		C = in;
	}

	@Column(name = "D", length = 299, nullable = true)
	@IColumn(description="授信号码")
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

	@Column(name = "G", length = 8, nullable = true)
	@IColumn(description="发放日期")
	private String G;

	public String getG() {
		return G;
	}

	public void setG(String in) {
		G = in;
	}

	@Column(name = "H", length = 8, nullable = true)
	@IColumn(description="到期日期")
	private String H;

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

	@IColumn(tagMethodName="getKTag",description="五级分类")
	@Column(name = "K", nullable =true)
	private String K;

	public static Map<String,String> getKTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_FIVE_CLASS");
	}

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@IColumn(tagMethodName="getLTag",description="贷款类型")
	@Column(name = "L", nullable =true)
	private String L;

	public static Map<String,String> getLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_LOAN_TYPE");
	}

	public String getL() {
		return L;
	}

	public void setL(String in) {
		L = in;
	}

	@IColumn(tagMethodName="getMTag",description="贷款业务种类")
	@Column(name = "M", nullable =true)
	private String M;

	public static Map<String,String> getMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_LOAN_BIZ_TYPE");
	}

	public String getM() {
		return M;
	}

	public void setM(String in) {
		M = in;
	}

	@IColumn(tagMethodName="getNTag",description="投向行业")
	@Column(name = "N", nullable =true)
	private String N;

	public static Map<String,String> getNTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ECON_INDUSTRY_CLASS");
	}

	public String getN() {
		return N;
	}

	public void setN(String in) {
		N = in;
	}

	@IColumn(tagMethodName="getOTag",description="币种代码")
	@Column(name = "O", nullable =true)
	private String O;

	public static Map<String,String> getOTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_CURR_CD");
	}

	public String getO() {
		return O;
	}

	public void setO(String in) {
		O = in;
	}

	@IColumn(tagMethodName="getPTag",description="担保方式")
	@Column(name = "P", nullable =true)
	private String P;

	public static Map<String,String> getPTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_GUAR_MODE");
	}

	public String getP() {
		return P;
	}

	public void setP(String in) {
		P = in;
	}

	@Column(name = "Q", length = 20, nullable = true)
	@IColumn(description="欠本余额(万元)")
	private BigDecimal Q;

	public BigDecimal getQ() {
		return Q;
	}

	public void setQ(String in) {
		Q = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "R", nullable = true)
	@IColumn(description="欠本天数")
	private Integer R;

	public Integer getR() {
		return R;
	}

	public void setR(String in) {
		R = TypeParse.parseInt(in);
	}

	@Column(name = "S", length = 20, nullable = true)
	@IColumn(description="欠息余额(万元)")
	private BigDecimal S;

	public BigDecimal getS() {
		return S;
	}

	public void setS(String in) {
		S = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "T", nullable = true)
	@IColumn(description="欠息天数")
	private Integer T;

	public Integer getT() {
		return T;
	}

	public void setT(String in) {
		T = TypeParse.parseInt(in);
	}

	@Column(name = "U", length = 20, nullable = true)
	@IColumn(description="本期还款(万元)")
	private BigDecimal U;

	public BigDecimal getU() {
		return U;
	}

	public void setU(String in) {
		U = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getVTag",description="还本方式")
	@Column(name = "V", nullable =true)
	private String V;

	public static Map<String,String> getVTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_REPAY_CMBC_TYPE");
	}

	public String getV() {
		return V;
	}

	public void setV(String in) {
		V = in;
	}

	@IColumn(tagMethodName="getWTag",description="还息方式")
	@Column(name = "W", nullable =true)
	private String W;

	public static Map<String,String> getWTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_REPAY_INTRES_TYPE");
	}

	public String getW() {
		return W;
	}

	public void setW(String in) {
		W = in;
	}

	@Column(name = "X", length = 8, nullable = true)
	@IColumn(description="下期还本日期")
	private String X;

	public String getX() {
		return X;
	}

	public void setX(String in) {
		X = in;
	}

	@Column(name = "Y", length = 20, nullable = true)
	@IColumn(description="下期还本金额(万元)")
	private BigDecimal Y;

	public BigDecimal getY() {
		return Y;
	}

	public void setY(String in) {
		Y = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "Z", length = 8, nullable = true)
	@IColumn(description="下期还息日期")
	private String Z;

	public String getZ() {
		return Z;
	}

	public void setZ(String in) {
		Z = in;
	}

	@Column(name = "AA", length = 20, nullable = true)
	@IColumn(description="下期还息金额(万元)")
	private BigDecimal AA;

	public BigDecimal getAA() {
		return AA;
	}

	public void setAA(String in) {
		AA = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getABTag",description="贷款发放类型")
	@Column(name = "AB", nullable =true)
	private String AB;

	public static Map<String,String> getABTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_LOAN_DISTR_TYPE");
	}

	public String getAB() {
		return AB;
	}

	public void setAB(String in) {
		AB = in;
	}

	@Column(name = "AC", length = 20, nullable = true)
	@IColumn(description="减值准备(万元)")
	private BigDecimal AC;

	public BigDecimal getAC() {
		return AC;
	}

	public void setAC(String in) {
		AC = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getADTag",description="产业结构调整类型")
	@Column(name = "AD", nullable =true)
	private String AD;

	public static Map<String,String> getADTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_INDUSTRY_STRU_ADJ_TYPE");
	}

	public String getAD() {
		return AD;
	}

	public void setAD(String in) {
		AD = in;
	}

	@IColumn(tagMethodName="getAETag",description="工业转型升级标识")
	@Column(name = "AE", nullable =true)
	private String AE;

	public static Map<String,String> getAETag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_INDUSTRY_UPGD_IDTFY");
	}

	public String getAE() {
		return AE;
	}

	public void setAE(String in) {
		AE = in;
	}

	@IColumn(tagMethodName="getAFTag",description="战略新兴产业类型")
	@Column(name = "AF", nullable =true)
	private String AF;

	public static Map<String,String> getAFTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_NEW_INDUSTRY_TYPE");
	}

	public String getAF() {
		return AF;
	}

	public void setAF(String in) {
		AF = in;
	}

	@IColumn(tagMethodName="getAGTag",description="银团贷款标识")
	@Column(name = "AG", nullable =true)
	private String AG;

	public static Map<String,String> getAGTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_SYN_LOAN_TAG_ROLE");
	}

	public String getAG() {
		return AG;
	}

	public void setAG(String in) {
		AG = in;
	}

	@IColumn(tagMethodName="getAHTag",description="支付方式")
	@Column(name = "AH", nullable =true)
	private String AH;

	public static Map<String,String> getAHTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_PAY_MODE");
	}

	public String getAH() {
		return AH;
	}

	public void setAH(String in) {
		AH = in;
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
