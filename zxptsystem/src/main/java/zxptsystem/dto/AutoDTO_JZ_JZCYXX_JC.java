package zxptsystem.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
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
@Table(name = "JZ_JZCYXX_JC")
@IEntity(description= "家族成员信息基础段")
public class AutoDTO_JZ_JZCYXX_JC implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5")
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4")
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3")
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2")
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1")
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden=true)
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

	@Column(name = "XZXH", length = 100, nullable = true)
	@IColumn(description="下载序号",isSingleTagHidden=true)
	private String XZXH;

	public String getXZXH() {
		return XZXH;
	}

	public void setXZXH(String in) {
		XZXH = in;
	}

	@IColumn(tagMethodName="getRPTSubmitStatusTag",description="提交状态",isSingleTagHidden=true)
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

	@IColumn(tagMethodName="getRPTVerifyTypeTag",description="审核状态",isSingleTagHidden=true)
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
	@IColumn(description="最后修改时间", isNullable = false,isSingleTagHidden=true)
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

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=true)
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

	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态",isSingleTagHidden=true)
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
	@IColumn(description="数据时间", isNullable = false,isSingleTagHidden=true)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	@Column(name = "ZYGXRXM", length = 200, nullable = true)
	@IColumn(description="主要关系人姓名")
	private String ZYGXRXM;

	public String getZYGXRXM() {
		return ZYGXRXM;
	}

	public void setZYGXRXM(String in) {
		ZYGXRXM = in;
	}
	
	@IColumn(tagMethodName="getZYGXRZJLXTag",description="主要关系人证件类型")
	@Column(name = "ZYGXRZJLX", nullable =true)
	private String ZYGXRZJLX;

	public static Map<String,String> getZYGXRZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZYGXRZJLX() {
		return ZYGXRZJLX;
	}

	public void setZYGXRZJLX(String in) {
		ZYGXRZJLX = in;
	}

	@Column(name = "ZJHM_1", length = 50, nullable = true)
	@IColumn(description="主要关系人证件号码")
	private String ZJHM_1;

	public String getZJHM_1() {
		return ZJHM_1;
	}

	public void setZJHM_1(String in) {
		ZJHM_1 = in;
	}

	@IColumn(tagMethodName="getJZGXTag",description="家族关系")
	@Column(name = "JZGX", nullable =true)
	private String JZGX;

	public static Map<String,String> getJZGXTag() {
		try {
			return HelpTool.getSystemConstVal("JZGX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJZGX() {
		return JZGX;
	}

	public void setJZGX(String in) {
		JZGX = in;
	}
	
	@Column(name = "JZCYXM", length = 200, nullable = true)
	@IColumn(description="家族成员姓名")
	private String JZCYXM;

	public String getJZCYXM() {
		return JZCYXM;
	}

	public void setJZCYXM(String in) {
		JZCYXM = in;
	}

	@IColumn(tagMethodName="getJZCYZJLXTag",description="家族成员证件类型")
	@Column(name = "JZCYZJLX", nullable =true)
	private String JZCYZJLX;

	public static Map<String,String> getJZCYZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJZCYZJLX() {
		return JZCYZJLX;
	}

	public void setJZCYZJLX(String in) {
		JZCYZJLX = in;
	}

	@Column(name = "ZJHM_2", length = 50, nullable = true)
	@IColumn(description="家族成员证件号码")
	private String ZJHM_2;

	public String getZJHM_2() {
		return ZJHM_2;
	}

	public void setZJHM_2(String in) {
		ZJHM_2 = in;
	}

	@Column(name = "XXGXRQ", length = 50, nullable = true)
	@IColumn(description="信息更新日期")
	private String XXGXRQ;

	public String getXXGXRQ() {
		return XXGXRQ;
	}

	public void setXXGXRQ(String in) {
		XXGXRQ = in;
	}

	@Column(name = "YLZD", length = 100, nullable = true)
	@IColumn(description="预留字段")
	private String YLZD;

	public String getYLZD() {
		return YLZD;
	}

	public void setYLZD(String in) {
		YLZD = in;
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

