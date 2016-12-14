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
@Table(name = "NCR_3_1")
@IEntity(description= "单一法人基本情况")
public class AutoDTO_NCR_3_1 implements java.io.Serializable{

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

	@Column(name = "C", length = 299, nullable = true)
	@IColumn(description="组织机构代码")
	private String C;

	public String getC() {
		return C;
	}

	public void setC(String in) {
		C = in;
	}

	@Column(name = "D", length = 8, nullable = true)
	@IColumn(description="组织机构登记/年检/更新日期")
	private String D;

	public String getD() {
		return D;
	}

	public void setD(String in) {
		D = in;
	}

	@Column(name = "E", length = 299, nullable = true)
	@IColumn(description="登记注册代码")
	private String E;

	public String getE() {
		return E;
	}

	public void setE(String in) {
		E = in;
	}

	@Column(name = "F", length = 8, nullable = true)
	@IColumn(description="登记注册/年检/更新日期")
	private String F;

	public String getF() {
		return F;
	}

	public void setF(String in) {
		F = in;
	}

	@IColumn(tagMethodName="getGTag",description="注册国家或地区")
	@Column(name = "G", nullable =true)
	private String G;

	public static Map<String,String> getGTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_NA");
	}

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

	@Column(name = "I", length = 500, nullable = true)
	@IColumn(description="注册地址")
	private String I;

	public String getI() {
		return I;
	}

	public void setI(String in) {
		I = in;
	}

	@IColumn(tagMethodName="getJTag",description="行政区划代码")
	@Column(name = "J", nullable =true)
	private String J;

	public static Map<String,String> getJTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getJ() {
		return J;
	}

	public void setJ(String in) {
		J = in;
	}

	@Column(name = "K", length = 299, nullable = true)
	@IColumn(description="上市公司标志-国别代码-上市公司代码")
	private String K;

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@IColumn(tagMethodName="getLTag",description="风险预警信号")
	@Column(name = "L", nullable =true)
	private String L;

	public static Map<String,String> getLTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_LP_RISK_WARN_CD");
	}

	public String getL() {
		return L;
	}

	public void setL(String in) {
		L = in;
	}

	@IColumn(tagMethodName="getMTag",description="关注事件")
	@Column(name = "M", nullable =true)
	private String M;

	public static Map<String,String> getMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_LP_CONCERN_EVENT_CD");
	}

	public String getM() {
		return M;
	}

	public void setM(String in) {
		M = in;
	}

	@Column(name = "N", length = 20, nullable = true)
	@IColumn(description="违约概率")
	private BigDecimal N;

	public BigDecimal getN() {
		return N;
	}

	public void setN(String in) {
		N = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "O", length = 299, nullable = true)
	@IColumn(description="信用评级结果")
	private String O;

	public String getO() {
		return O;
	}

	public void setO(String in) {
		O = in;
	}

	@Column(name = "P", length = 20, nullable = true)
	@IColumn(description="资产总额(万元)")
	private BigDecimal P;

	public BigDecimal getP() {
		return P;
	}

	public void setP(String in) {
		P = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "Q", length = 20, nullable = true)
	@IColumn(description="负债总额(万元)")
	private BigDecimal Q;

	public BigDecimal getQ() {
		return Q;
	}

	public void setQ(String in) {
		Q = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "R", length = 20, nullable = true)
	@IColumn(description="税前利润(万元)")
	private BigDecimal R;

	public BigDecimal getR() {
		return R;
	}

	public void setR(String in) {
		R = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "S", length = 20, nullable = true)
	@IColumn(description="主营业务收入(万元)")
	private BigDecimal S;

	public BigDecimal getS() {
		return S;
	}

	public void setS(String in) {
		S = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "T", length = 20, nullable = true)
	@IColumn(description="存货(万元)")
	private BigDecimal T;

	public BigDecimal getT() {
		return T;
	}

	public void setT(String in) {
		T = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "U", length = 20, nullable = true)
	@IColumn(description="应收账款(万元)")
	private BigDecimal U;

	public BigDecimal getU() {
		return U;
	}

	public void setU(String in) {
		U = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "V", length = 20, nullable = true)
	@IColumn(description="其他应收款(万元)")
	private BigDecimal V;

	public BigDecimal getV() {
		return V;
	}

	public void setV(String in) {
		V = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "W", length = 20, nullable = true)
	@IColumn(description="流动资产合计(万元)")
	private BigDecimal W;

	public BigDecimal getW() {
		return W;
	}

	public void setW(String in) {
		W = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "X", length = 20, nullable = true)
	@IColumn(description="流动负债合计(万元)")
	private BigDecimal X;

	public BigDecimal getX() {
		return X;
	}

	public void setX(String in) {
		X = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getYTag",description="财务报表类型")
	@Column(name = "Y", nullable =true)
	private String Y;

	public static Map<String,String> getYTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_FIN_REPT_TYPE");
	}

	public String getY() {
		return Y;
	}

	public void setY(String in) {
		Y = in;
	}

	@Column(name = "Z", length = 8, nullable = true)
	@IColumn(description="财务报表日期")
	private String Z;

	public String getZ() {
		return Z;
	}

	public void setZ(String in) {
		Z = in;
	}

	@IColumn(tagMethodName="getAATag",description="客户类型")
	@Column(name = "AA", nullable =true)
	private String AA;

	public static Map<String,String> getAATag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_CUST_TYPE_3_1");
	}

	public String getAA() {
		return AA;
	}

	public void setAA(String in) {
		AA = in;
	}

	@IColumn(tagMethodName="getABTag",description="客户所属行业代码")
	@Column(name = "AB", nullable =true)
	private String AB;

	public static Map<String,String> getABTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ECON_INDUSTRY_CLASS");
	}

	public String getAB() {
		return AB;
	}

	public void setAB(String in) {
		AB = in;
	}

	@Column(name = "AC", length = 299, nullable = true)
	@IColumn(description="贷款卡号")
	private String AC;

	public String getAC() {
		return AC;
	}

	public void setAC(String in) {
		AC = in;
	}

	@Column(name = "AD", length = 299, nullable = true)
	@IColumn(description="环境和社会风险分类")
	private String AD;

	public String getAD() {
		return AD;
	}

	public void setAD(String in) {
		AD = in;
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
