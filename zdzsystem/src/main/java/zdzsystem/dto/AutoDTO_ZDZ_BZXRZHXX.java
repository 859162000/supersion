package zdzsystem.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import extend.helper.HelpTool;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "ZDZ_BZXRZHXX")
@IEntity(description= "被执行人账户信息")
@XStreamAlias("kzzh")
public class AutoDTO_ZDZ_BZXRZHXX implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5", isNullable = false)
	@XStreamOmitField
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4", isNullable = false)
	@XStreamOmitField
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3", isNullable = false)
	@XStreamOmitField
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2", isNullable = false)
	@XStreamOmitField
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1", isNullable = false)
	@XStreamOmitField
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态")
	@Column(name = "RPTFeedbackType", nullable =true)
	@XStreamOmitField
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
	@XStreamOmitField
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
	@XStreamOmitField
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
	@XStreamOmitField
	private Timestamp lastUpdateDate;

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String in) {
		lastUpdateDate = TypeParse.parseTimestamp(in);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operationUser")
	@XStreamOmitField
	private UserInfo operationUser;

	public UserInfo getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(UserInfo in) {
		operationUser = in;
	}

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态")
	@Column(name = "RPTSendType", nullable =true)
	@XStreamOmitField
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
	@XStreamOmitField
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
	@XStreamOmitField
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
	@XStreamOmitField
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BDHM", nullable = true)
	@IColumn(isNullable = false,description="控制请求单号")
	@XStreamOmitField
	private AutoDTO_ZDZ_KZQQJTNR BDHM;

	public AutoDTO_ZDZ_KZQQJTNR getBDHM() {
		return BDHM;
	}

	public void setBDHM(AutoDTO_ZDZ_KZQQJTNR bDHM) {
		BDHM = bDHM;
	}

	@Column(name = "CCXH", length = 255, nullable = true)
	@IColumn(description="序号")
	@XStreamAsAttribute
	private String CCXH;

	public String getCCXH() {
		return CCXH;
	}

	public void setCCXH(String in) {
		CCXH = in;
	}

	@IColumn(tagMethodName="getKZLXTag",description="控制类型")
	@Column(name = "KZLX", nullable =true)
	@XStreamAsAttribute
	private String KZLX;

	public static Map<String,String> getKZLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZDZ_KZLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getKZLX() {
		return KZLX;
	}

	public void setKZLX(String in) {
		KZLX = in;
	}

	@IColumn(tagMethodName="getKZCSTag",description="控制措施")
	@Column(name = "KZCS", nullable =true)
	@XStreamAsAttribute
	private String KZCS;

	public static Map<String,String> getKZCSTag() {
		try {
			return HelpTool.getSystemConstVal("ZDZ_KZCS");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getKZCS() {
		return KZCS;
	}

	public void setKZCS(String in) {
		KZCS = in;
	}

	@Column(name = "KHZH", length = 100, nullable = true)
	@IColumn(description="开户账号")
	@XStreamAsAttribute
	private String KHZH;

	public String getKHZH() {
		return KHZH;
	}

	public void setKHZH(String in) {
		KHZH = in;
	}

	@Column(name = "GLZHHM", length = 100, nullable = true)
	@IColumn(description="开户账号子账号")
	@XStreamAsAttribute
	private String GLZHHM;

	public String getGLZHHM() {
		return GLZHHM;
	}

	public void setGLZHHM(String in) {
		GLZHHM = in;
	}

	@Column(name = "CCLB", length = 50, nullable = true)
	@IColumn(description="账户类别")
	@XStreamAsAttribute
	private String CCLB;

	public String getCCLB() {
		return CCLB;
	}

	public void setCCLB(String in) {
		CCLB = in;
	}

	@Column(name = "KHWD", length = 200, nullable = true)
	@IColumn(description="开户网点")
	@XStreamAsAttribute
	private String KHWD;

	public String getKHWD() {
		return KHWD;
	}

	public void setKHWD(String in) {
		KHWD = in;
	}

	@Column(name = "KHWDDM", length = 50, nullable = true)
	@IColumn(description="开户网点代码")
	@XStreamAsAttribute
	private String KHWDDM;

	public String getKHWDDM() {
		return KHWDDM;
	}

	public void setKHWDDM(String in) {
		KHWDDM = in;
	}

	@Column(name = "ZCMC", length = 100, nullable = true)
	@IColumn(description="金融资产名称")
	@XStreamAsAttribute
	private String ZCMC;

	public String getZCMC() {
		return ZCMC;
	}

	public void setZCMC(String in) {
		ZCMC = in;
	}

	@Column(name = "ZCLX", length = 50, nullable = true)
	@IColumn(description="金融资产类型")
	@XStreamAsAttribute
	private String ZCLX;

	public String getZCLX() {
		return ZCLX;
	}

	public void setZCLX(String in) {
		ZCLX = in;
	}

	@Column(name = "JLDW", length = 50, nullable = true)
	@IColumn(description="计量单位")
	@XStreamAsAttribute
	private String JLDW;

	public String getJLDW() {
		return JLDW;
	}

	public void setJLDW(String in) {
		JLDW = in;
	}

	@IColumn(tagMethodName="getKZNRTag",description="申请控制内容")
	@Column(name = "KZNR", nullable =true)
	@XStreamAsAttribute
	private String KZNR;

	public static Map<String,String> getKZNRTag() {
		try {
			return HelpTool.getSystemConstVal("ZDZ_KZNR");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getKZNR() {
		return KZNR;
	}

	public void setKZNR(String in) {
		KZNR = in;
	}

	@Column(name = "KSRQ", length = 50, nullable = true)
	@IColumn(description="申请控制开始时间")
	@XStreamAsAttribute
	private String KSRQ;

	public String getKSRQ() {
		return KSRQ;
	}

	public void setKSRQ(String in) {
		KSRQ = in;
	}

	@Column(name = "JSRQ", length = 50, nullable = true)
	@IColumn(description="申请控制结束时间")
	@XStreamAsAttribute
	private String JSRQ;

	public String getJSRQ() {
		return JSRQ;
	}

	public void setJSRQ(String in) {
		JSRQ = in;
	}

	@IColumn(tagMethodName="getBZTag",description="申请控制金额币种")
	@Column(name = "BZ", nullable =true)
	@XStreamAsAttribute
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

	@Column(name = "JE", length = 255, nullable = true)
	@IColumn(description="申请控制金额（数额）")
	@XStreamAsAttribute
	private String JE;

	public String getJE() {
		return JE;
	}

	public void setJE(String in) {
		JE = in;
	}

	@Column(name = "SFJH", length = 50, nullable = true)
	@IColumn(description="是否结汇")
	@XStreamAsAttribute
	private String SFJH;

	public String getSFJH() {
		return SFJH;
	}

	public void setSFJH(String in) {
		SFJH = in;
	}

	@Column(name = "CKWH", length = 100, nullable = true)
	@IColumn(description="裁定书文号")
	@XStreamAsAttribute
	private String CKWH;

	public String getCKWH() {
		return CKWH;
	}

	public void setCKWH(String in) {
		CKWH = in;
	}

	@Column(name = "ZXKZHHM", length = 200, nullable = true)
	@IColumn(description="执行款专户户名")
	@XStreamAsAttribute
	private String ZXKZHHM;

	public String getZXKZHHM() {
		return ZXKZHHM;
	}

	public void setZXKZHHM(String in) {
		ZXKZHHM = in;
	}

	@Column(name = "ZXKZHKHH", length = 200, nullable = true)
	@IColumn(description="执行款专户开户行")
	@XStreamAsAttribute
	private String ZXKZHKHH;

	public String getZXKZHKHH() {
		return ZXKZHKHH;
	}

	public void setZXKZHKHH(String in) {
		ZXKZHKHH = in;
	}

	@Column(name = "ZXKZHKHHHH", length = 100, nullable = true)
	@IColumn(description="执行款专户开户行行号")
	@XStreamAsAttribute
	private String ZXKZHKHHHH;

	public String getZXKZHKHHHH() {
		return ZXKZHKHHHH;
	}

	public void setZXKZHKHHHH(String in) {
		ZXKZHKHHHH = in;
	}

	@Column(name = "ZXKZHZH", length = 100, nullable = true)
	@IColumn(description="执行款专户账号")
	@XStreamAsAttribute
	private String ZXKZHZH;

	public String getZXKZHZH() {
		return ZXKZHZH;
	}

	public void setZXKZHZH(String in) {
		ZXKZHZH = in;
	}

	@Column(name = "ZXKZHLX", length = 50, nullable = true)
	@IColumn(description="执行款专户类型")
	@XStreamAsAttribute
	private String ZXKZHLX;

	public String getZXKZHLX() {
		return ZXKZHLX;
	}

	public void setZXKZHLX(String in) {
		ZXKZHLX = in;
	}

	@Column(name = "YDJAH", length = 100, nullable = true)
	@IColumn(description="原冻结案号")
	@XStreamAsAttribute
	private String YDJAH;

	public String getYDJAH() {
		return YDJAH;
	}

	public void setYDJAH(String in) {
		YDJAH = in;
	}

	@Column(name = "YDJDH", length = 100, nullable = true)
	@IColumn(description="原冻结请求单号（任务流水号）")
	@XStreamAsAttribute
	private String YDJDH;

	public String getYDJDH() {
		return YDJDH;
	}

	public void setYDJDH(String in) {
		YDJDH = in;
	}

	@Column(name = "BatchNumber", length = 50, nullable = true)
	@IColumn(description="批次号")
	@XStreamOmitField
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
	@XStreamOmitField
	private String autoID;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String in) {
		autoID = in;
	}

}

