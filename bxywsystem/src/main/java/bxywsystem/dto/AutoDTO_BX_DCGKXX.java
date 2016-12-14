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
@Table(name = "BX_DCGKXX")
@IEntity(description= "代偿概况信息")
public class AutoDTO_BX_DCGKXX implements java.io.Serializable{

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

	@Column(name = "JZRQ", length = 50, nullable = true)
	@IColumn(description="记账日期")
	private String JZRQ;

	public String getJZRQ() {
		return JZRQ;
	}

	public void setJZRQ(String in) {
		JZRQ = in;
	}

	@IColumn(tagMethodName="getZCZTTag",description="追偿状态")
	@Column(name = "ZCZT", nullable =true)
	private String ZCZT;

	public static Map<String,String> getZCZTTag() {
		try {
			return HelpTool.getSystemConstVal("ZCZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZCZT() {
		return ZCZT;
	}

	public void setZCZT(String in) {
		ZCZT = in;
	}

	@Column(name = "ZJYCDCRQ", length = 50, nullable = true)
	@IColumn(description="最近一次代偿日期")
	private String ZJYCDCRQ;

	public String getZJYCDCRQ() {
		return ZJYCDCRQ;
	}

	public void setZJYCDCRQ(String in) {
		ZJYCDCRQ = in;
	}

	@Column(name = "LJDCJE", length = 50, nullable = true)
	@IColumn(description="累计代偿金额")
	private String LJDCJE;

	public String getLJDCJE() {
		return LJDCJE;
	}

	public void setLJDCJE(String in) {
		LJDCJE = in;
	}

	@Column(name = "BJGCDDCJE", length = 50, nullable = true)
	@IColumn(description="本机构承担代偿金额")
	private String BJGCDDCJE;

	public String getBJGCDDCJE() {
		return BJGCDDCJE;
	}

	public void setBJGCDDCJE(String in) {
		BJGCDDCJE = in;
	}

	@Column(name = "ZJYCZCRQ", length = 50, nullable = true)
	@IColumn(description="最近一次追偿日期")
	private String ZJYCZCRQ;

	public String getZJYCZCRQ() {
		return ZJYCZCRQ;
	}

	public void setZJYCZCRQ(String in) {
		ZJYCZCRQ = in;
	}

	@Column(name = "DZCJE", length = 50, nullable = true)
	@IColumn(description="待追偿金额")
	private String DZCJE;

	public String getDZCJE() {
		return DZCJE;
	}

	public void setDZCJE(String in) {
		DZCJE = in;
	}

	@Column(name = "BJGDZCJE", length = 50, nullable = true)
	@IColumn(description="本机构待追偿金额")
	private String BJGDZCJE;

	public String getBJGDZCJE() {
		return BJGDZCJE;
	}

	public void setBJGDZCJE(String in) {
		BJGDZCJE = in;
	}

	@Column(name = "LJZCJE", length = 50, nullable = true)
	@IColumn(description="累计追偿金额")
	private String LJZCJE;

	public String getLJZCJE() {
		return LJZCJE;
	}

	public void setLJZCJE(String in) {
		LJZCJE = in;
	}

	@Column(name = "LJSSJE", length = 50, nullable = true)
	@IColumn(description="累计损失金额")
	private String LJSSJE;

	public String getLJSSJE() {
		return LJSSJE;
	}

	public void setLJSSJE(String in) {
		LJSSJE = in;
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

