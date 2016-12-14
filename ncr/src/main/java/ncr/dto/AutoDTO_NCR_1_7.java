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
@Table(name = "NCR_1_7")
@IEntity(description= "同业客户业务明细")
public class AutoDTO_NCR_1_7 implements java.io.Serializable{

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
	@IColumn(description="客户名称")
	private String A;

	public String getA() {
		return A;
	}

	public void setA(String in) {
		A = in;
	}

	@Column(name = "B", length = 299, nullable = true)
	@IColumn(description="客户代码")
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
	@IColumn(description="非现场监管统计机构编码")
	private String D;

	public String getD() {
		return D;
	}

	public void setD(String in) {
		D = in;
	}

	@Column(name = "E", length = 299, nullable = true)
	@IColumn(description="组织机构代码")
	private String E;

	public String getE() {
		return E;
	}

	public void setE(String in) {
		E = in;
	}

	@IColumn(tagMethodName="getFTag",description="客户类别")
	@Column(name = "F", nullable =true)
	private String F;

	public static Map<String,String> getFTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_CUST_CATE");
	}

	public String getF() {
		return F;
	}

	public void setF(String in) {
		F = in;
	}

	@Column(name = "G", length = 299, nullable = true)
	@IColumn(description="内部评级")
	private String G;

	public String getG() {
		return G;
	}

	public void setG(String in) {
		G = in;
	}

	@Column(name = "H", length = 299, nullable = true)
	@IColumn(description="外部评级")
	private String H;

	public String getH() {
		return H;
	}

	public void setH(String in) {
		H = in;
	}

	@Column(name = "I", length = 20, nullable = true)
	@IColumn(description="拆放同业(万元)")
	private BigDecimal I;

	public BigDecimal getI() {
		return I;
	}

	public void setI(String in) {
		I = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "J", length = 20, nullable = true)
	@IColumn(description="存放同业(万元)")
	private BigDecimal J;

	public BigDecimal getJ() {
		return J;
	}

	public void setJ(String in) {
		J = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "K", length = 20, nullable = true)
	@IColumn(description="债券回购(万元)")
	private BigDecimal K;

	public BigDecimal getK() {
		return K;
	}

	public void setK(String in) {
		K = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "L", length = 20, nullable = true)
	@IColumn(description="股票质押贷款(万元)")
	private BigDecimal L;

	public BigDecimal getL() {
		return L;
	}

	public void setL(String in) {
		L = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "M", length = 20, nullable = true)
	@IColumn(description="买入返售资产(万元)")
	private BigDecimal M;

	public BigDecimal getM() {
		return M;
	}

	public void setM(String in) {
		M = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "N", length = 20, nullable = true)
	@IColumn(description="买断式转贴现(万元)")
	private BigDecimal N;

	public BigDecimal getN() {
		return N;
	}

	public void setN(String in) {
		N = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "O", length = 20, nullable = true)
	@IColumn(description="持有债券(万元)")
	private BigDecimal O;

	public BigDecimal getO() {
		return O;
	}

	public void setO(String in) {
		O = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "P", length = 20, nullable = true)
	@IColumn(description="股权投资(万元)")
	private BigDecimal P;

	public BigDecimal getP() {
		return P;
	}

	public void setP(String in) {
		P = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "Q", length = 20, nullable = true)
	@IColumn(description="同业代付(万元)")
	private BigDecimal Q;

	public BigDecimal getQ() {
		return Q;
	}

	public void setQ(String in) {
		Q = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "R", length = 20, nullable = true)
	@IColumn(description="其他表内业务(万元)")
	private BigDecimal R;

	public BigDecimal getR() {
		return R;
	}

	public void setR(String in) {
		R = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "S", length = 20, nullable = true)
	@IColumn(description="卖出回购资产(万元)")
	private BigDecimal S;

	public BigDecimal getS() {
		return S;
	}

	public void setS(String in) {
		S = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "T", length = 20, nullable = true)
	@IColumn(description="不可撤销的承诺及或有负债(万元)")
	private BigDecimal T;

	public BigDecimal getT() {
		return T;
	}

	public void setT(String in) {
		T = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "U", length = 20, nullable = true)
	@IColumn(description="其他表外业务(万元)")
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

