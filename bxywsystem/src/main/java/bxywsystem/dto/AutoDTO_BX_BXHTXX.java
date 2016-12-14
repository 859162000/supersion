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

import bxywsystem.dto.AutoDTO_BX_BXYW_JC;

@Entity
@Table(name = "BX_BXHTXX")
@IEntity(description= "保险合同信息")
public class AutoDTO_BX_BXHTXX implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1")
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
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

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3")
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
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

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5")
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "RPTFeedbackType", length = 50, nullable = true)
	@IColumn(description="回执状态", isNullable = false,isSingleTagHidden=true)
	private String RPTFeedbackType;

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	public void setRPTFeedbackType(String in) {
		RPTFeedbackType = in;
	}

	@Column(name = "RPTSendType", length = 50, nullable = true)
	@IColumn(description="报送状态", isNullable = false)
	private String RPTSendType;

	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTSendType(String in) {
		RPTSendType = in;
	}

	@Column(name = "RPTCheckType", length = 50, nullable = true)
	@IColumn(description="校验状态", isNullable = false)
	private String RPTCheckType;

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String in) {
		RPTCheckType = in;
	}

	@IColumn(tagMethodName="getBXYWZLTag",description="保险业务种类")
	@Column(name = "BXYWZL", nullable =true)
	private String BXYWZL;

	public static Map<String,String> getBXYWZLTag() {
		try {
			return HelpTool.getSystemConstVal("BXYWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBXYWZL() {
		return BXYWZL;
	}

	public void setBXYWZL(String in) {
		BXYWZL = in;
	}

	@IColumn(tagMethodName="getBXFSTag",description="保险方式")
	@Column(name = "BXFS", nullable =true)
	private String BXFS;

	public static Map<String,String> getBXFSTag() {
		try {
			return HelpTool.getSystemConstVal("BXFS");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBXFS() {
		return BXFS;
	}

	public void setBXFS(String in) {
		BXFS = in;
	}

	@Column(name = "BXJE", length = 50, nullable = true)
	@IColumn(description="保险金额")
	private String BXJE;

	public String getBXJE() {
		return BXJE;
	}

	public void setBXJE(String in) {
		BXJE = in;
	}

	@Column(name = "BXQSRQ", length = 50, nullable = true)
	@IColumn(description="保险起始日期")
	private String BXQSRQ;

	public String getBXQSRQ() {
		return BXQSRQ;
	}

	public void setBXQSRQ(String in) {
		BXQSRQ = in;
	}

	@Column(name = "BXDQRQ", length = 50, nullable = true)
	@IColumn(description="保险到期日期")
	private String BXDQRQ;

	public String getBXDQRQ() {
		return BXDQRQ;
	}

	public void setBXDQRQ(String in) {
		BXDQRQ = in;
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

	@Column(name = "YLZD", length = 150, nullable = true)
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FOREIGNID", nullable = false)
	private bxywsystem.dto.AutoDTO_BX_BXYW_JC FOREIGNID;

	public bxywsystem.dto.AutoDTO_BX_BXYW_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(bxywsystem.dto.AutoDTO_BX_BXYW_JC in) {
		FOREIGNID = in;
	}

}

