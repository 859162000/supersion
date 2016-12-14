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
@Table(name = "NCR_2_1")
@IEntity(description= "集团基本情况")
public class AutoDTO_NCR_2_1 implements java.io.Serializable{

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
	@IColumn(description="客户代码/供应链编码")
	private String B;

	public String getB() {
		return B;
	}

	public void setB(String in) {
		B = in;
	}

	@Column(name = "C", length = 299, nullable = true)
	@IColumn(description="工商注册编号")
	private String C;

	public String getC() {
		return C;
	}

	public void setC(String in) {
		C = in;
	}

	@Column(name = "D", nullable = true)
	@IColumn(description="集团成员数")
	private Integer D;

	public Integer getD() {
		return D;
	}

	public void setD(String in) {
		D = TypeParse.parseInt(in);
	}

	@IColumn(tagMethodName="getETag",description="授信类型")
	@Column(name = "E", nullable =true)
	private String E;

	public static Map<String,String> getETag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_CRDT_TYPE");
	}

	public String getE() {
		return E;
	}

	public void setE(String in) {
		E = in;
	}

	@Column(name = "F", length = 20, nullable = true)
	@IColumn(description="集团并表财务指标-资产总额(万元)")
	private BigDecimal F;

	public BigDecimal getF() {
		return F;
	}

	public void setF(String in) {
		F = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "G", length = 20, nullable = true)
	@IColumn(description="集团并表财务指标-负债总额(万元)")
	private BigDecimal G;

	public BigDecimal getG() {
		return G;
	}

	public void setG(String in) {
		G = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getHTag",description="集团并表财务指标-资产负债表类型")
	@Column(name = "H", nullable =true)
	private String H;

	public static Map<String,String> getHTag() {
		return ShowContext.getInstance().getShowEntityMap().get("NCR_ASSET_LIAB_TBL_TYPE");
	}

	public String getH() {
		return H;
	}

	public void setH(String in) {
		H = in;
	}

	@Column(name = "I", length = 8, nullable = true)
	@IColumn(description="集团并表财务指标-资产负债表日期")
	private String I;

	public String getI() {
		return I;
	}

	public void setI(String in) {
		I = in;
	}

	@IColumn(tagMethodName="getJTag",description="预警/评级-风险预警信号")
	@Column(name = "J", nullable =true)
	private String J;

	public static Map<String,String> getJTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_GROUP_RISK_WARN_CD");
	}

	public String getJ() {
		return J;
	}

	public void setJ(String in) {
		J = in;
	}

	@IColumn(tagMethodName="getKTag",description="预警/评级-关注事件")
	@Column(name = "K", nullable =true)
	private String K;

	public static Map<String,String> getKTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_GROUP_CONCERN_EVENT_CD");
	}

	public String getK() {
		return K;
	}

	public void setK(String in) {
		K = in;
	}

	@Column(name = "L", length = 299, nullable = true)
	@IColumn(description="预警/评级-信用评级结果")
	private String L;

	public String getL() {
		return L;
	}

	public void setL(String in) {
		L = in;
	}

	@IColumn(tagMethodName="getMTag",description="母公司注册信息-注册国家或地区")
	@Column(name = "M", nullable =true)
	private String M;

	public static Map<String,String> getMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getM() {
		return M;
	}

	public void setM(String in) {
		M = in;
	}

	@IColumn(tagMethodName="getNTag",description="母公司注册信息-国别代码")
	@Column(name = "N", nullable =true)
	private String N;

	public static Map<String,String> getNTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getN() {
		return N;
	}

	public void setN(String in) {
		N = in;
	}

	@Column(name = "O", length = 500, nullable = true)
	@IColumn(description="母公司注册信息-国内注册地址")
	private String O;

	public String getO() {
		return O;
	}

	public void setO(String in) {
		O = in;
	}

	@IColumn(tagMethodName="getPTag",description="母公司注册信息-行政区划代码")
	@Column(name = "P", nullable =true)
	private String P;

	public static Map<String,String> getPTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_ADMIN_REGION_CD");
	}

	public String getP() {
		return P;
	}

	public void setP(String in) {
		P = in;
	}

	@Column(name = "Q", length = 8, nullable = true)
	@IColumn(description="母公司注册信息-更新注册信息日期")
	private String Q;

	public String getQ() {
		return Q;
	}

	public void setQ(String in) {
		Q = in;
	}

	@Column(name = "R", length = 500, nullable = true)
	@IColumn(description="国内办公地址-办公地址")
	private String R;

	public String getR() {
		return R;
	}

	public void setR(String in) {
		R = in;
	}

	@Column(name = "S", length = 500, nullable = true)
	@IColumn(description="国内办公地址-行政区划代码")
	private String S;

	public String getS() {
		return S;
	}

	public void setS(String in) {
		S = in;
	}

	@Column(name = "T", length = 8, nullable = true)
	@IColumn(description="国内办公地址-更新办公地址日期")
	private String T;

	public String getT() {
		return T;
	}

	public void setT(String in) {
		T = in;
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


