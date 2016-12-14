package dbgssystem.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
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
import com.sun.tools.internal.xjc.reader.RawTypeSet.Ref;
import com.sun.xml.internal.ws.api.PropertySet.Property;

import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import java.util.Set;

import coresystem.dto.UserInfo;
import coresystem.dto.InstInfo;
import dbgssystem.dto.AutoDTO_DB_DBHTXX;
import dbgssystem.dto.AutoDTO_DB_BDBRXX;
import dbgssystem.dto.AutoDTO_DB_ZQRJZHTXX;
import dbgssystem.dto.AutoDTO_DB_FDBRXX;
import dbgssystem.dto.AutoDTO_DB_SJZBZRXX;
import dbgssystem.dto.AutoDTO_DB_DCGKXX;
import dbgssystem.dto.AutoDTO_DB_DCMXXX;
import dbgssystem.dto.AutoDTO_DB_BFJNGKXX;
import dbgssystem.dto.AutoDTO_DB_BFJNMXXX;
import dbgssystem.dto.AutoDTO_DB_ZCMXXX;

@Entity
@Table(name = "DB_DBXX_JC")
@IEntity(navigationName="担保业务基础段",description="担保业务基础段")
public class AutoDTO_DB_DBXX_JC implements java.io.Serializable{

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
	@IColumn(description="担保业务编号")
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
	@Column(name = "autoID", length = 100)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	private String autoID;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String in) {
		autoID = in;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_DBHTXX> OneToMany_DB_HTXX= new HashSet<dbgssystem.dto.AutoDTO_DB_DBHTXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_DBHTXX> getOneToMany_DB_HTXX() {
		return OneToMany_DB_HTXX;
	}

	public void setOneToMany_DB_HTXX(Set<dbgssystem.dto.AutoDTO_DB_DBHTXX> in) {
		OneToMany_DB_HTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_BDBRXX> OneToMany_DB_BDBRXX= new HashSet<dbgssystem.dto.AutoDTO_DB_BDBRXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_BDBRXX> getOneToMany_DB_BDBRXX() {
		return OneToMany_DB_BDBRXX;
	}

	public void setOneToMany_DB_BDBRXX(Set<dbgssystem.dto.AutoDTO_DB_BDBRXX> in) {
		OneToMany_DB_BDBRXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_ZQRJZHTXX> OneToMany_DB_ZQRJZHTXX= new HashSet<dbgssystem.dto.AutoDTO_DB_ZQRJZHTXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_ZQRJZHTXX> getOneToMany_DB_ZQRJZHTXX() {
		return OneToMany_DB_ZQRJZHTXX;
	}

	public void setOneToMany_DB_ZQRJZHTXX(Set<dbgssystem.dto.AutoDTO_DB_ZQRJZHTXX> in) {
		OneToMany_DB_ZQRJZHTXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_FDBRXX> OneToMany_DB_FDBRXX= new HashSet<dbgssystem.dto.AutoDTO_DB_FDBRXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_FDBRXX> getOneToMany_DB_FDBRXX() {
		return OneToMany_DB_FDBRXX;
	}

	public void setOneToMany_DB_FDBRXX(Set<dbgssystem.dto.AutoDTO_DB_FDBRXX> in) {
		OneToMany_DB_FDBRXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_SJZBZRXX> OneToMany_DB_SJZBZRXX= new HashSet<dbgssystem.dto.AutoDTO_DB_SJZBZRXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_SJZBZRXX> getOneToMany_DB_SJZBZRXX() {
		return OneToMany_DB_SJZBZRXX;
	}

	public void setOneToMany_DB_SJZBZRXX(Set<dbgssystem.dto.AutoDTO_DB_SJZBZRXX> in) {
		OneToMany_DB_SJZBZRXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_DCGKXX> OneToMany_DB_DCGKXX= new HashSet<dbgssystem.dto.AutoDTO_DB_DCGKXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_DCGKXX> getOneToMany_DB_DCGKXX() {
		return OneToMany_DB_DCGKXX;
	}

	public void setOneToMany_DB_DCGKXX(Set<dbgssystem.dto.AutoDTO_DB_DCGKXX> in) {
		OneToMany_DB_DCGKXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_DCMXXX> OneToMany_DB_DCMXXX= new HashSet<dbgssystem.dto.AutoDTO_DB_DCMXXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_DCMXXX> getOneToMany_DB_DCMXXX() {
		return OneToMany_DB_DCMXXX;
	}

	public void setOneToMany_DB_DCMXXX(Set<dbgssystem.dto.AutoDTO_DB_DCMXXX> in) {
		OneToMany_DB_DCMXXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_BFJNGKXX> OneToMany_DB_BFJNGKXX= new HashSet<dbgssystem.dto.AutoDTO_DB_BFJNGKXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_BFJNGKXX> getOneToMany_DB_BFJNGKXX() {
		return OneToMany_DB_BFJNGKXX;
	}

	public void setOneToMany_DB_BFJNGKXX(Set<dbgssystem.dto.AutoDTO_DB_BFJNGKXX> in) {
		OneToMany_DB_BFJNGKXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_BFJNMXXX> OneToMany_DB_BFJNMXXX= new HashSet<dbgssystem.dto.AutoDTO_DB_BFJNMXXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_BFJNMXXX> getOneToMany_DB_BFJNMXXX() {
		return OneToMany_DB_BFJNMXXX;
	}

	public void setOneToMany_DB_BFJNMXXX(Set<dbgssystem.dto.AutoDTO_DB_BFJNMXXX> in) {
		OneToMany_DB_BFJNMXXX = in;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOREIGNID")
	private Set<dbgssystem.dto.AutoDTO_DB_ZCMXXX> OneToMany_DB_ZCMXXX= new HashSet<dbgssystem.dto.AutoDTO_DB_ZCMXXX>();

	public Set<dbgssystem.dto.AutoDTO_DB_ZCMXXX> getOneToMany_DB_ZCMXXX() {
		return OneToMany_DB_ZCMXXX;
	}

	public void setOneToMany_DB_ZCMXXX(Set<dbgssystem.dto.AutoDTO_DB_ZCMXXX> in) {
		OneToMany_DB_ZCMXXX = in;
	}

}

