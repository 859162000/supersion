package zdzsystem.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.lang.Double;
import java.lang.Float;
import java.sql.Time;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import framework.interfaces.IEntity;
import java.util.Set;

import coresystem.dto.UserInfo;
import coresystem.dto.InstInfo;

@Entity
@Table(name = "ZDZ_JTZHXX")
@IEntity(description= "具体账户信息")
public class AutoDTO_ZDZ_JTZHXX implements java.io.Serializable{

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
	@IColumn(description="账号序号")
	private String CCXH;

	public String getCCXH() {
		return CCXH;
	}

	public void setCCXH(String in) {
		CCXH = in;
	}

	@Column(name = "KHZH", length = 100, nullable = true)
	@IColumn(description="开户账号")
	private String KHZH;

	public String getKHZH() {
		return KHZH;
	}

	public void setKHZH(String in) {
		KHZH = in;
	}

	@Column(name = "CCLB", length = 50, nullable = true)
	@IColumn(description="账户类别")
	private String CCLB;

	public String getCCLB() {
		return CCLB;
	}

	public void setCCLB(String in) {
		CCLB = in;
	}

	@Column(name = "ZHZT", length = 50, nullable = true)
	@IColumn(description="账户状态")
	private String ZHZT;

	public String getZHZT() {
		return ZHZT;
	}

	public void setZHZT(String in) {
		ZHZT = in;
	}

	@Column(name = "KHWD", length = 200, nullable = true)
	@IColumn(description="开户网点")
	private String KHWD;

	public String getKHWD() {
		return KHWD;
	}

	public void setKHWD(String in) {
		KHWD = in;
	}

	@Column(name = "KHWDDM", length = 50, nullable = true)
	@IColumn(description="开户网点代码")
	private String KHWDDM;

	public String getKHWDDM() {
		return KHWDDM;
	}

	public void setKHWDDM(String in) {
		KHWDDM = in;
	}

	@Column(name = "KHRQ", length = 50, nullable = true)
	@IColumn(description="开户日期")
	private String KHRQ;

	public String getKHRQ() {
		return KHRQ;
	}

	public void setKHRQ(String in) {
		KHRQ = in;
	}

	@Column(name = "XHRQ", length = 50, nullable = true)
	@IColumn(description="销户日期")
	private String XHRQ;

	public String getXHRQ() {
		return XHRQ;
	}

	public void setXHRQ(String in) {
		XHRQ = in;
	}

	@IColumn(tagMethodName="getBZTag",description="计价币种")
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

	@Column(name = "YE", length = 50, nullable = true)
	@IColumn(description="资产数额")
	private String YE;

	public String getYE() {
		return YE;
	}

	public void setYE(String in) {
		YE = in;
	}

	@Column(name = "KYYE", length = 50, nullable = true)
	@IColumn(description="可用资产数额")
	private String KYYE;

	public String getKYYE() {
		return KYYE;
	}

	public void setKYYE(String in) {
		KYYE = in;
	}

	@Column(name = "GLZJZH", length = 50, nullable = true)
	@IColumn(description="关联资金账户",isSingleTagHidden=true)
	private String GLZJZH;

	public String getGLZJZH() {
		return GLZJZH;
	}

	public void setGLZJZH(String in) {
		GLZJZH = in;
	}

	@Column(name = "FKSJ", length = 50, nullable = true)
	@IColumn(description="反馈结果时间")
	private String FKSJ;

	public String getFKSJ() {
		return FKSJ;
	}

	public void setFKSJ(String in) {
		FKSJ = in;
	}

	@Column(name = "TXDZ", length = 200, nullable = true)
	@IColumn(description="通讯地址")
	private String TXDZ;

	public String getTXDZ() {
		return TXDZ;
	}

	public void setTXDZ(String in) {
		TXDZ = in;
	}

	@Column(name = "YZBM", length = 50, nullable = true)
	@IColumn(description="邮政编码")
	private String YZBM;

	public String getYZBM() {
		return YZBM;
	}

	public void setYZBM(String in) {
		YZBM = in;
	}

	@Column(name = "LXDH", length = 50, nullable = true)
	@IColumn(description="联系电话")
	private String LXDH;

	public String getLXDH() {
		return LXDH;
	}

	public void setLXDH(String in) {
		LXDH = in;
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

