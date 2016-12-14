package bxywsystem.dto;

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
import bxywsystem.dto.AutoDTO_BX_BXHTXX;
import bxywsystem.dto.AutoDTO_BX_ZQRJZHTXX;
import bxywsystem.dto.AutoDTO_BX_SJDCZRXX;
import bxywsystem.dto.AutoDTO_BX_DCGKXX;
import bxywsystem.dto.AutoDTO_BX_DCMXXX;
import bxywsystem.dto.AutoDTO_BX_BFJNGKXX;
import bxywsystem.dto.AutoDTO_BX_ZCMXXX;
import bxywsystem.dto.AutoDTO_BX_BFJNMXXX;

@Entity
@Table(name = "BX_BXYW_JC")
@IEntity(description= "保险业务基础段")
public class AutoDTO_BX_BXYW_JC implements java.io.Serializable{

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

	@Column(name = "lastUpdateDate", length = 50, nullable = true)
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

	@Column(name = "dtDate", length = 50, nullable = true)
	@IColumn(description="数据时间", isNullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	@Column(name = "BXGSJGDM", length = 50, nullable = true)
	@IColumn(description="保险公司机构代码")
	private String BXGSJGDM;

	public String getBXGSJGDM() {
		return BXGSJGDM;
	}

	public void setBXGSJGDM(String in) {
		BXGSJGDM = in;
	}

	@Column(name = "BDBH", length = 150, nullable = true)
	@IColumn(description="保单编号")
	private String BDBH;

	public String getBDBH() {
		return BDBH;
	}

	public void setBDBH(String in) {
		BDBH = in;
	}

	@Column(name = "BXHTHM", length = 150, nullable = true)
	@IColumn(description="保险合同号码")
	private String BXHTHM;

	public String getBXHTHM() {
		return BXHTHM;
	}

	public void setBXHTHM(String in) {
		BXHTHM = in;
	}

	@IColumn(tagMethodName="getBBZRLXTag",description="被保证人类型")
	@Column(name = "BBZRLX", nullable =true)
	private String BBZRLX;

	public static Map<String,String> getBBZRLXTag() {
		try {
			return HelpTool.getSystemConstVal("BBZRLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBBZRLX() {
		return BBZRLX;
	}

	public void setBBZRLX(String in) {
		BBZRLX = in;
	}

	@Column(name = "BBZRMC", length = 200, nullable = true)
	@IColumn(description="被保证人名称")
	private String BBZRMC;

	public String getBBZRMC() {
		return BBZRMC;
	}

	public void setBBZRMC(String in) {
		BBZRMC = in;
	}

	@IColumn(tagMethodName="getBBZRZJLXTag",description="被保证人证件类型")
	@Column(name = "BBZRZJLX", nullable =true)
	private String BBZRZJLX;

	public static Map<String,String> getBBZRZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("BBZRZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBBZRZJLX() {
		return BBZRZJLX;
	}

	public void setBBZRZJLX(String in) {
		BBZRZJLX = in;
	}

	@Column(name = "BBZRZJHM", length = 50, nullable = true)
	@IColumn(description="被保证人证件号码")
	private String BBZRZJHM;

	public String getBBZRZJHM() {
		return BBZRZJHM;
	}

	public void setBBZRZJHM(String in) {
		BBZRZJHM = in;
	}

	@Column(name = "SJBGRQ", length = 50, nullable = true)
	@IColumn(description="数据报告日期")
	private String SJBGRQ;

	public String getSJBGRQ() {
		return SJBGRQ;
	}

	public void setSJBGRQ(String in) {
		SJBGRQ = in;
	}

	@Column(name = "YLZD", length = 150, nullable = true)
	@IColumn(description="预留字段")
	private String YLZD;

	public String getYLZD() {
		return YLZD;
	}

	public void setYLZD(String in) {
		YLZD = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<bxywsystem.dto.AutoDTO_BX_BXHTXX> OneToMany_BX_BXHTXX= new HashSet<bxywsystem.dto.AutoDTO_BX_BXHTXX>(0);

	public Set<bxywsystem.dto.AutoDTO_BX_BXHTXX> getOneToMany_BX_BXHTXX() {
		return OneToMany_BX_BXHTXX;
	}

	public void setOneToMany_BX_BXHTXX(Set<bxywsystem.dto.AutoDTO_BX_BXHTXX> in) {
		OneToMany_BX_BXHTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<bxywsystem.dto.AutoDTO_BX_ZQRJZHTXX> OneToMany_BX_ZQRJZHTXX= new HashSet<bxywsystem.dto.AutoDTO_BX_ZQRJZHTXX>(0);

	public Set<bxywsystem.dto.AutoDTO_BX_ZQRJZHTXX> getOneToMany_BX_ZQRJZHTXX() {
		return OneToMany_BX_ZQRJZHTXX;
	}

	public void setOneToMany_BX_ZQRJZHTXX(Set<bxywsystem.dto.AutoDTO_BX_ZQRJZHTXX> in) {
		OneToMany_BX_ZQRJZHTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<bxywsystem.dto.AutoDTO_BX_SJDCZRXX> OneToMany_BX_SJDCZRXX= new HashSet<bxywsystem.dto.AutoDTO_BX_SJDCZRXX>(0);

	public Set<bxywsystem.dto.AutoDTO_BX_SJDCZRXX> getOneToMany_BX_SJDCZRXX() {
		return OneToMany_BX_SJDCZRXX;
	}

	public void setOneToMany_BX_SJDCZRXX(Set<bxywsystem.dto.AutoDTO_BX_SJDCZRXX> in) {
		OneToMany_BX_SJDCZRXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<bxywsystem.dto.AutoDTO_BX_DCGKXX> OneToMany_BX_DCGKXX= new HashSet<bxywsystem.dto.AutoDTO_BX_DCGKXX>(0);

	public Set<bxywsystem.dto.AutoDTO_BX_DCGKXX> getOneToMany_BX_DCGKXX() {
		return OneToMany_BX_DCGKXX;
	}

	public void setOneToMany_BX_DCGKXX(Set<bxywsystem.dto.AutoDTO_BX_DCGKXX> in) {
		OneToMany_BX_DCGKXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<bxywsystem.dto.AutoDTO_BX_DCMXXX> OneToMany_BX_DCMXXX= new HashSet<bxywsystem.dto.AutoDTO_BX_DCMXXX>(0);

	public Set<bxywsystem.dto.AutoDTO_BX_DCMXXX> getOneToMany_BX_DCMXXX() {
		return OneToMany_BX_DCMXXX;
	}

	public void setOneToMany_BX_DCMXXX(Set<bxywsystem.dto.AutoDTO_BX_DCMXXX> in) {
		OneToMany_BX_DCMXXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<bxywsystem.dto.AutoDTO_BX_BFJNGKXX> OneToMany_BX_BFJNGKXX= new HashSet<bxywsystem.dto.AutoDTO_BX_BFJNGKXX>(0);

	public Set<bxywsystem.dto.AutoDTO_BX_BFJNGKXX> getOneToMany_BX_BFJNGKXX() {
		return OneToMany_BX_BFJNGKXX;
	}

	public void setOneToMany_BX_BFJNGKXX(Set<bxywsystem.dto.AutoDTO_BX_BFJNGKXX> in) {
		OneToMany_BX_BFJNGKXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<bxywsystem.dto.AutoDTO_BX_ZCMXXX> OneToMany_BX_ZCMXXX= new HashSet<bxywsystem.dto.AutoDTO_BX_ZCMXXX>(0);

	public Set<bxywsystem.dto.AutoDTO_BX_ZCMXXX> getOneToMany_BX_ZCMXXX() {
		return OneToMany_BX_ZCMXXX;
	}

	public void setOneToMany_BX_ZCMXXX(Set<bxywsystem.dto.AutoDTO_BX_ZCMXXX> in) {
		OneToMany_BX_ZCMXXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<bxywsystem.dto.AutoDTO_BX_BFJNMXXX> OneToMany_BX_BFJNMXXX= new HashSet<bxywsystem.dto.AutoDTO_BX_BFJNMXXX>(0);

	public Set<bxywsystem.dto.AutoDTO_BX_BFJNMXXX> getOneToMany_BX_BFJNMXXX() {
		return OneToMany_BX_BFJNMXXX;
	}

	public void setOneToMany_BX_BFJNMXXX(Set<bxywsystem.dto.AutoDTO_BX_BFJNMXXX> in) {
		OneToMany_BX_BFJNMXXX = in;
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

