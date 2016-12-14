package testsystem.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import framework.interfaces.IColumn;
import framework.security.SecurityContext;
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

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.opensymphony.xwork2.util.KeyProperty;

import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import java.util.Set;

import coresystem.dto.UserInfo;
import coresystem.dto.InstInfo;



public class View_AutoDTO_DB_DBXX_JC implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5", isNullable = true,isSingleTagHidden=true)
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4", isNullable = true,isSingleTagHidden=true)
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3", isNullable = true,isSingleTagHidden=true)
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2", isNullable = true,isSingleTagHidden=true)
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1", isNullable = true,isSingleTagHidden=true)
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
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
	@Column(name = "RPTSubmitStatus", nullable =false)
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
	@Column(name = "RPTVerifyType", nullable =false)
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

	@Column(name = "lastUpdateDate", nullable = false)
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

	public void setOperationUser(coresystem.dto.UserInfo in) throws Exception{
		operationUser = in;
	}

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=true)
	@Column(name = "RPTSendType", nullable =false)
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
	@Column(name = "RPTCheckType", nullable =false)
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

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden = true,isNullable=false)
	@Column(name = "RPTFeedbackType",length=1)
	private String RPTFeedbackType;
	
	public static Map<String,String> getRPTFeedbackTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
	}
	
	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}


	public String getRPTFeedbackType() {
		return RPTFeedbackType;
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

	@Column(name = "dtDate", nullable = false)
	@IColumn(description="数据时间", isNullable = false,isSingleTagHidden=true)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	@Column(name = "DBJGDM", length = 50, nullable = true)
	@IColumn(description="担保机构代码")
	private String DBJGDM;

	public String getDBJGDM() {
		return DBJGDM;
	}

	public void setDBJGDM(String in) {
		DBJGDM = in;
	}

	@Column(name = "DBYWBH", length = 200, nullable = true)
	@IColumn(description="担保业务编号",isKeyName=true)
	private String DBYWBH;

	public String getDBYWBH() {
		return DBYWBH;
	}

	public void setDBYWBH(String in) {
		DBYWBH = in;
	}

	@Column(name = "DBHTHM", length = 200, nullable = true)
	@IColumn(description="担保合同号码")
	private String DBHTHM;

	public String getDBHTHM() {
		return DBHTHM;
	}

	public void setDBHTHM(String in) {
		DBHTHM = in;
	}

	@IColumn(tagMethodName="getBDBRLXTag",description="被担保人类型")
	@Column(name = "BDBRLX", nullable =true)
	private String BDBRLX;

	public static Map<String,String> getBDBRLXTag() {
		try {
			return HelpTool.getSystemConstVal("BDBRLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBDBRLX() {
		return BDBRLX;
	}

	public void setBDBRLX(String in) {
		BDBRLX = in;
	}

	@Column(name = "BDBRMC", length = 200, nullable = true)
	@IColumn(description="被担保人名称")
	private String BDBRMC;

	public String getBDBRMC() {
		return BDBRMC;
	}

	public void setBDBRMC(String in) {
		BDBRMC = in;
	}

	@IColumn(tagMethodName="getBDBRZJLXTag",description="被担保人证件类型")
	@Column(name = "BDBRZJLX", nullable =true)
	private String BDBRZJLX;

	public static Map<String,String> getBDBRZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBDBRZJLX() {
		return BDBRZJLX;
	}

	public void setBDBRZJLX(String in) {
		BDBRZJLX = in;
	}

	@Column(name = "BDBRZJHM", length = 50, nullable = true)
	@IColumn(description="被担保人证件号码")
	private String BDBRZJHM;

	public String getBDBRZJHM() {
		return BDBRZJHM;
	}

	public void setBDBRZJHM(String in) {
		BDBRZJHM = in;
	}

	@Column(name = "SJBGRQ", length = 50, nullable = true)
	@IColumn(description="数据报告日期",isSingleTagHidden=true)
	private String SJBGRQ;

	public String getSJBGRQ() {
		return SJBGRQ;
	}

	public void setSJBGRQ(String in) {
		SJBGRQ = in;
	}

	@Column(name = "YLZD", length = 200, nullable = true)
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

