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
@Table(name = "BX_BFJNMXXX")
@IEntity(description= "保费缴纳明细信息")
public class AutoDTO_BX_BFJNMXXX implements java.io.Serializable{

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
	@IColumn(description="回执状态", isNullable = false)
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

	@Column(name = "YJRQ", length = 50, nullable = true)
	@IColumn(description="应缴日期")
	private String YJRQ;

	public String getYJRQ() {
		return YJRQ;
	}

	public void setYJRQ(String in) {
		YJRQ = in;
	}

	@Column(name = "YJBFJE", length = 50, nullable = true)
	@IColumn(description="应缴保费金额")
	private String YJBFJE;

	public String getYJBFJE() {
		return YJBFJE;
	}

	public void setYJBFJE(String in) {
		YJBFJE = in;
	}

	@Column(name = "SJRQ", length = 50, nullable = true)
	@IColumn(description="实缴日期")
	private String SJRQ;

	public String getSJRQ() {
		return SJRQ;
	}

	public void setSJRQ(String in) {
		SJRQ = in;
	}

	@Column(name = "QJJE", length = 50, nullable = true)
	@IColumn(description="欠缴金额")
	private String QJJE;

	public String getQJJE() {
		return QJJE;
	}

	public void setQJJE(String in) {
		QJJE = in;
	}

	@IColumn(tagMethodName="getBQBFJNZTTag",description="本期保费缴纳状态")
	@Column(name = "BQBFJNZT", nullable =true)
	private String BQBFJNZT;

	public static Map<String,String> getBQBFJNZTTag() {
		try {
			return HelpTool.getSystemConstVal("BQBFJNZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBQBFJNZT() {
		return BQBFJNZT;
	}

	public void setBQBFJNZT(String in) {
		BQBFJNZT = in;
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

