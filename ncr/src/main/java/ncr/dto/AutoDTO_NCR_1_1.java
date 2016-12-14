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
@Table(name = "NCR_1_1")
@IEntity(description= "授信情况")
public class AutoDTO_NCR_1_1 implements java.io.Serializable{

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

	@IColumn(tagMethodName="getATag",description="客户类型")
	@Column(name = "A", nullable =true)
	private String A;

	public static Map<String,String> getATag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_CUST_TYPE_1_1");
	}

	public String getA() {
		return A;
	}

	public void setA(String in) {
		A = in;
	}

	@Column(name = "B", length = 299, nullable = true)
	@IColumn(description="客户名称")
	private String B;

	public String getB() {
		return B;
	}

	public void setB(String in) {
		B = in;
	}

	@Column(name = "C", length = 299, nullable = true)
	@IColumn(description="客户代码")
	private String C;

	public String getC() {
		return C;
	}

	public void setC(String in) {
		C = in;
	}

	@IColumn(tagMethodName="getDTag",description="客户国籍代码")
	@Column(name = "D", nullable =true)
	private String D;

	public static Map<String,String> getDTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getD() {
		return D;
	}

	public void setD(String in) {
		D = in;
	}

	@Column(name = "E", length = 299, nullable = true)
	@IColumn(description="授信银行代码")
	private String E;

	public String getE() {
		return E;
	}

	public void setE(String in) {
		E = in;
	}

	@Column(name = "F", length = 299, nullable = true)
	@IColumn(description="授信银行名称")
	private String F;

	public String getF() {
		return F;
	}

	public void setF(String in) {
		F = in;
	}

	@Column(name = "G", length = 299, nullable = true)
	@IColumn(description="授信号码")
	private String G;

	public String getG() {
		return G;
	}

	public void setG(String in) {
		G = in;
	}

	@Column(name = "H", length = 20, nullable = true)
	@IColumn(description="授信额度(万元)")
	private BigDecimal H;

	public BigDecimal getH() {
		return H;
	}

	public void setH(String in) {
		H = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "I", length = 20, nullable = true)
	@IColumn(description="贷款授信额度(万元)")
	private BigDecimal I;

	public BigDecimal getI() {
		return I;
	}

	public void setI(String in) {
		I = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "J", length = 8, nullable = true)
	@IColumn(description="授信起始日期")
	private String J;

	public String getJ() {
		return J;
	}

	public void setJ(String in) {
		J = in;
	}

	@Column(name = "K", length = 8, nullable = true)
	@IColumn(description="授信到期日期")
	private String K;

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@Column(name = "L", length = 20, nullable = true)
	@IColumn(description="贷款余额(万元)")
	private BigDecimal L;

	public BigDecimal getL() {
		return L;
	}

	public void setL(String in) {
		L = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "M", length = 20, nullable = true)
	@IColumn(description="持有债券余额(万元)")
	private BigDecimal M;

	public BigDecimal getM() {
		return M;
	}

	public void setM(String in) {
		M = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "N", length = 20, nullable = true)
	@IColumn(description="持有股权余额(万元)")
	private BigDecimal N;

	public BigDecimal getN() {
		return N;
	}

	public void setN(String in) {
		N = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "O", length = 20, nullable = true)
	@IColumn(description="其他表内信用风险资产余额(万元)")
	private BigDecimal O;

	public BigDecimal getO() {
		return O;
	}

	public void setO(String in) {
		O = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "P", length = 20, nullable = true)
	@IColumn(description="表外业务余额(万元)")
	private BigDecimal P;

	public BigDecimal getP() {
		return P;
	}

	public void setP(String in) {
		P = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "Q", length = 20, nullable = true)
	@IColumn(description="现有业务余额占用授信额度(万元)")
	private BigDecimal Q;

	public BigDecimal getQ() {
		return Q;
	}

	public void setQ(String in) {
		Q = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "R", length = 20, nullable = true)
	@IColumn(description="贷款余额占用贷款授信额度(万元)")
	private BigDecimal R;

	public BigDecimal getR() {
		return R;
	}

	public void setR(String in) {
		R = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "S", length = 20, nullable = true)
	@IColumn(description="尚可使用授信额度(万元)")
	private BigDecimal S;

	public BigDecimal getS() {
		return S;
	}

	public void setS(String in) {
		S = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "T", length = 20, nullable = true)
	@IColumn(description="尚可使用贷款授信额度(万元)")
	private BigDecimal T;

	public BigDecimal getT() {
		return T;
	}

	public void setT(String in) {
		T = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getUTag",description="关联方授信标识")
	@Column(name = "U", nullable =true)
	private String U;

	public static Map<String,String> getUTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_RELA_CRDT_IDTFY");
	}

	public String getU() {
		return U;
	}

	public void setU(String in) {
		U = in;
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


