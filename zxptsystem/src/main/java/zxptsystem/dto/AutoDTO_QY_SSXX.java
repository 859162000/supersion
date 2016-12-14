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

import zxptsystem.dto.AutoDTO_QY_JKRGZ_JC;

@Entity
@Table(name = "QY_SSXX")
@IEntity(description= "诉讼信息")
public class AutoDTO_QY_SSXX implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

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

	@Column(name = "BQSJLLSH", length = 200, nullable = true)
	@IColumn(description="被起诉记录流水号")
	private String BQSJLLSH;

	public String getBQSJLLSH() {
		return BQSJLLSH;
	}

	public void setBQSJLLSH(String in) {
		BQSJLLSH = in;
	}

	@Column(name = "QSRMC", length = 200, nullable = true)
	@IColumn(description="起诉人名称")
	private String QSRMC;

	public String getQSRMC() {
		return QSRMC;
	}

	public void setQSRMC(String in) {
		QSRMC = in;
	}

	@IColumn(tagMethodName="getBZTag",description="币种")
	@Column(name = "BZ", nullable =true)
	private String BZ;

	public static Map<String,String> getBZTag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
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

	@Column(name = "PJZXJE", length = 50, nullable = true)
	@IColumn(description="判决执行金额")
	private String PJZXJE;

	public String getPJZXJE() {
		return PJZXJE;
	}

	public void setPJZXJE(String in) {
		PJZXJE = in;
	}

	@Column(name = "PJZXRQ", length = 50, nullable = true)
	@IColumn(description="判决执行日期")
	private String PJZXRQ;

	public String getPJZXRQ() {
		return PJZXRQ;
	}

	public void setPJZXRQ(String in) {
		PJZXRQ = in;
	}

	@Column(name = "ZXJG", length = 200, nullable = true)
	@IColumn(description="执行结果")
	private String ZXJG;

	public String getZXJG() {
		return ZXJG;
	}

	public void setZXJG(String in) {
		ZXJG = in;
	}

	@Column(name = "BQSYY", length = 600, nullable = true)
	@IColumn(description="被起诉原因")
	private String BQSYY;

	public String getBQSYY() {
		return BQSYY;
	}

	public void setBQSYY(String in) {
		BQSYY = in;
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
	private zxptsystem.dto.AutoDTO_QY_JKRGZ_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_JKRGZ_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_JKRGZ_JC in) {
		FOREIGNID = in;
	}

}

