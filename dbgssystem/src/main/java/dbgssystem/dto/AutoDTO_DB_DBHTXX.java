package dbgssystem.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
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
import java.util.Set;

import dbgssystem.dto.AutoDTO_DB_DBXX_JC;

@Entity
@Table(name = "DB_DBHTXX")
@IEntity(navigationName="担保合同信息段",description="担保合同信息段")
public class AutoDTO_DB_DBHTXX implements java.io.Serializable{

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
	
	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态",isSingleTagHidden=false)
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

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=false,isNullable=false)
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
	
	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden = false,isNullable=false)
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
	
	@IColumn(tagMethodName="getDBYWZLTag",description="担保业务种类")
	@Column(name = "DBYWZL", nullable =true)
	private String DBYWZL;

	public static Map<String,String> getDBYWZLTag() {
		try {
			return HelpTool.getSystemConstVal("DBYWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDBYWZL() {
		return DBYWZL;
	}

	public void setDBYWZL(String in) {
		DBYWZL = in;
	}

	@IColumn(tagMethodName="getDBFSTag",description="担保方式")
	@Column(name = "DBFS", nullable =true)
	private String DBFS;

	public static Map<String,String> getDBFSTag() {
		try {
			return HelpTool.getSystemConstVal("DBFS");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDBFS() {
		return DBFS;
	}

	public void setDBFS(String in) {
		DBFS = in;
	}

	@Column(name = "DBJE", length = 50, nullable = true)
	@IColumn(description="担保金额")
	private String DBJE;

	public String getDBJE() {
		return DBJE;
	}

	public void setDBJE(String in) {
		DBJE = in;
	}

	@Column(name = "DBQSRQ", length = 50, nullable = true)
	@IColumn(description="担保起始日期")
	private String DBQSRQ;

	public String getDBQSRQ() {
		return DBQSRQ;
	}

	public void setDBQSRQ(String in) {
		DBQSRQ = in;
	}

	@Column(name = "DBDQRQ", length = 50, nullable = true)
	@IColumn(description="担保到期日期")
	private String DBDQRQ;

	public String getDBDQRQ() {
		return DBDQRQ;
	}

	public void setDBDQRQ(String in) {
		DBDQRQ = in;
	}

	@Column(name = "CCBZJBL", length = 50, nullable = true)
	@IColumn(description="存出保证金比例")
	private String CCBZJBL;

	public String getCCBZJBL() {
		return CCBZJBL;
	}

	public void setCCBZJBL(String in) {
		CCBZJBL = in;
	}

	@IColumn(tagMethodName="getFDBFSTag",description="反担保方式")
	@Column(name = "FDBFS", nullable =true)
	private String FDBFS;

	public static Map<String,String> getFDBFSTag() {
		try {
			return HelpTool.getSystemConstVal("FDBFS");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getFDBFS() {
		return FDBFS;
	}

	public void setFDBFS(String in) {
		FDBFS = in;
	}

	@Column(name = "YDZDBBCBL", length = 50, nullable = true)
	@IColumn(description="约定再担保补偿比例")
	private String YDZDBBCBL;

	public String getYDZDBBCBL() {
		return YDZDBBCBL;
	}

	public void setYDZDBBCBL(String in) {
		YDZDBBCBL = in;
	}

	@Column(name = "FL", length = 50, nullable = true)
	@IColumn(description="费率")
	private String FL;

	public String getFL() {
		return FL;
	}

	public void setFL(String in) {
		FL = in;
	}

	@Column(name = "NHFL", length = 50, nullable = true)
	@IColumn(description="年化费率")
	private String NHFL;

	public String getNHFL() {
		return NHFL;
	}

	public void setNHFL(String in) {
		NHFL = in;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FOREIGNID", nullable = false)
	private dbgssystem.dto.AutoDTO_DB_DBXX_JC FOREIGNID;

	public dbgssystem.dto.AutoDTO_DB_DBXX_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(dbgssystem.dto.AutoDTO_DB_DBXX_JC in) {
		FOREIGNID = in;
	}
}

