package zdzsystem.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.lang.Double;
import java.lang.Float;
import java.sql.Time;
import framework.interfaces.IColumn;
import framework.show.ShowContext;
import framework.helper.TypeParse;
import extend.helper.HelpTool;
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
@Table(name = "ZDZ_ZJWLXX")
@IEntity(description= "资金往来(交易)信息")
public class AutoDTO_ZDZ_ZJWLXX implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5", isNullable = false)
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4", isNullable = false)
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3", isNullable = false)
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2", isNullable = false)
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1", isNullable = false)
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}

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
	private UserInfo operationUser;

	public UserInfo getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(UserInfo in) {
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
	@JoinColumn(name = "instInfo")
	private InstInfo instInfo;

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo in) {
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BDHM", nullable = true)
	@IColumn(isNullable = false,description="查询请求单号")
	private AutoDTO_ZDZ_CXQQNR BDHM;

	public AutoDTO_ZDZ_CXQQNR getBDHM() {
		return BDHM;
	}

	public void setBDHM(AutoDTO_ZDZ_CXQQNR bDHM) {
		BDHM = bDHM;
	}

	@Column(name = "CCXH", length = 255, nullable = true)
	@IColumn(description="账户序号")
	private String CCXH;

	public String getCCXH() {
		return CCXH;
	}

	public void setCCXH(String in) {
		CCXH = in;
	}

	@Column(name = "WLXH", length = 100, nullable = true)
	@IColumn(description="资金往来序号")
	private String WLXH;

	public String getWLXH() {
		return WLXH;
	}

	public void setWLXH(String in) {
		WLXH = in;
	}

	@Column(name = "JYZL", length = 100, nullable = true)
	@IColumn(description="交易种类")
	private String JYZL;

	public String getJYZL() {
		return JYZL;
	}

	public void setJYZL(String in) {
		JYZL = in;
	}

	@Column(name = "ZJLX", length = 50, nullable = true)
	@IColumn(description="借贷方向(资金流向)")
	private String ZJLX;

	public String getZJLX() {
		return ZJLX;
	}

	public void setZJLX(String in) {
		ZJLX = in;
	}

	@IColumn(tagMethodName="getBZTag",description="交易币种")
	@Column(name = "BZ", nullable =true)
	private String BZ;

	public static Map<String,String> getBZTag() {
		try {
			return HelpTool.getSystemConstVal("ZDZ_BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String in) {
		BZ = in;
	}

	@Column(name = "JE", length = 50, nullable = true)
	@IColumn(description="交易金额")
	private String JE;

	public String getJE() {
		return JE;
	}

	public void setJE(String in) {
		JE = in;
	}

	@Column(name = "JYSJ", length = 50, nullable = true)
	@IColumn(description="交易时间")
	private String JYSJ;

	public String getJYSJ() {
		return JYSJ;
	}

	public void setJYSJ(String in) {
		JYSJ = in;
	}

	@Column(name = "JYLSH", length = 100, nullable = true)
	@IColumn(description="交易流水号")
	private String JYLSH;

	public String getJYLSH() {
		return JYLSH;
	}

	public void setJYLSH(String in) {
		JYLSH = in;
	}

	@Column(name = "ZCKZXM", length = 150, nullable = true)
	@IColumn(description="交易对方姓名/名称")
	private String ZCKZXM;

	public String getZCKZXM() {
		return ZCKZXM;
	}

	public void setZCKZXM(String in) {
		ZCKZXM = in;
	}

	@Column(name = "ZCKZH", length = 100, nullable = true)
	@IColumn(description="交易对方账号")
	private String ZCKZH;

	public String getZCKZH() {
		return ZCKZH;
	}

	public void setZCKZH(String in) {
		ZCKZH = in;
	}

	@Column(name = "ZCKZHKHH", length = 200, nullable = true)
	@IColumn(description="交易对方账号开户行")
	private String ZCKZHKHH;

	public String getZCKZHKHH() {
		return ZCKZHKHH;
	}

	public void setZCKZHKHH(String in) {
		ZCKZHKHH = in;
	}

	@Column(name = "JYDSYHHH", length = 100, nullable = true)
	@IColumn(description="交易对方账号开户行行号")
	private String JYDSYHHH;

	public String getJYDSYHHH() {
		return JYDSYHHH;
	}

	public void setJYDSYHHH(String in) {
		JYDSYHHH = in;
	}

	@Column(name = "JYDSZJLX", length = 100, nullable = true)
	@IColumn(description="交易对方开户证件类型")
	private String JYDSZJLX;

	public String getJYDSZJLX() {
		return JYDSZJLX;
	}

	public void setJYDSZJLX(String in) {
		JYDSZJLX = in;
	}

	@Column(name = "JYDSZJHM", length = 100, nullable = true)
	@IColumn(description="交易对方开户证件号码")
	private String JYDSZJHM;

	public String getJYDSZJHM() {
		return JYDSZJHM;
	}

	public void setJYDSZJHM(String in) {
		JYDSZJHM = in;
	}

	@Column(name = "JYDSTXDZ", length = 200, nullable = true)
	@IColumn(description="交易对方通讯地址")
	private String JYDSTXDZ;

	public String getJYDSTXDZ() {
		return JYDSTXDZ;
	}

	public void setJYDSTXDZ(String in) {
		JYDSTXDZ = in;
	}

	@Column(name = "JYDSYZBM", length = 50, nullable = true)
	@IColumn(description="交易对方邮政编码")
	private String JYDSYZBM;

	public String getJYDSYZBM() {
		return JYDSYZBM;
	}

	public void setJYDSYZBM(String in) {
		JYDSYZBM = in;
	}

	@Column(name = "JYDSLXDH", length = 50, nullable = true)
	@IColumn(description="交易对方联系电话")
	private String JYDSLXDH;

	public String getJYDSLXDH() {
		return JYDSLXDH;
	}

	public void setJYDSLXDH(String in) {
		JYDSLXDH = in;
	}

	@Column(name = "BEIZ", length = 2000, nullable = true)
	@IColumn(description="备注")
	private String BEIZ;

	public String getBEIZ() {
		return BEIZ;
	}

	public void setBEIZ(String in) {
		BEIZ = in;
	}
	
	@Column(name = "BatchNumber", length = 50, nullable = true)
	@IColumn(description="批次号")
	private String BatchNumber;

	public String getBatchNumber() {
		return BatchNumber;
	}

	public void setBatchNumber(String in) {
		BatchNumber = in;
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

