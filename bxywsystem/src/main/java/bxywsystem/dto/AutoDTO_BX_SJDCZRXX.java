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
@Table(name = "BX_SJDCZRXX")
@IEntity(description= "实际代偿责任信息")
public class AutoDTO_BX_SJDCZRXX implements java.io.Serializable{

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

	@IColumn(tagMethodName="getBXHTYXZTTag",description="保险合同有效状态")
	@Column(name = "BXHTYXZT", nullable =true)
	private String BXHTYXZT;

	public static Map<String,String> getBXHTYXZTTag() {
		try {
			return HelpTool.getSystemConstVal("BXHTYXZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBXHTYXZT() {
		return BXHTYXZT;
	}

	public void setBXHTYXZT(String in) {
		BXHTYXZT = in;
	}

	@Column(name = "DCZRJCRQ", length = 50, nullable = true)
	@IColumn(description="代偿责任解除日期")
	private String DCZRJCRQ;

	public String getDCZRJCRQ() {
		return DCZRJCRQ;
	}

	public void setDCZRJCRQ(String in) {
		DCZRJCRQ = in;
	}

	@Column(name = "ZBYE", length = 50, nullable = true)
	@IColumn(description="在保余额")
	private String ZBYE;

	public String getZBYE() {
		return ZBYE;
	}

	public void setZBYE(String in) {
		ZBYE = in;
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

	@Column(name = "YEBHRQ", length = 50, nullable = true)
	@IColumn(description="余额变化日期")
	private String YEBHRQ;

	public String getYEBHRQ() {
		return YEBHRQ;
	}

	public void setYEBHRQ(String in) {
		YEBHRQ = in;
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

