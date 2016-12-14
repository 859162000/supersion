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

import zxptsystem.dto.AutoDTO_QY_DKYW_JC;

@Entity
@Table(name = "QY_HTXX")
@IEntity(description= "合同信息")
public class AutoDTO_QY_HTXX implements java.io.Serializable{

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

	@Column(name = "JKRMC", length = 200, nullable = true)
	@IColumn(description="借款人名称")
	private String JKRMC;

	public String getJKRMC() {
		return JKRMC;
	}

	public void setJKRMC(String in) {
		JKRMC = in;
	}

	@Column(name = "SXXYHM", length = 200, nullable = true)
	@IColumn(description="授信协议号码")
	private String SXXYHM;

	public String getSXXYHM() {
		return SXXYHM;
	}

	public void setSXXYHM(String in) {
		SXXYHM = in;
	}

	@Column(name = "HTSXRQ", length = 50, nullable = true)
	@IColumn(description="贷款合同生效日期")
	private String HTSXRQ;

	public String getHTSXRQ() {
		return HTSXRQ;
	}

	public void setHTSXRQ(String in) {
		HTSXRQ = in;
	}

	@Column(name = "HTZZRQ", length = 50, nullable = true)
	@IColumn(description="贷款合同终止日期")
	private String HTZZRQ;

	public String getHTZZRQ() {
		return HTZZRQ;
	}

	public void setHTZZRQ(String in) {
		HTZZRQ = in;
	}

	@IColumn(tagMethodName="getYTBZTag",description="银团标志")
	@Column(name = "YTBZ", nullable =true)
	private String YTBZ;

	public static Map<String,String> getYTBZTag() {
		try {
			return HelpTool.getSystemConstVal("YTBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getYTBZ() {
		return YTBZ;
	}

	public void setYTBZ(String in) {
		YTBZ = in;
	}

	@IColumn(tagMethodName="getDBBZTag",description="担保标志")
	@Column(name = "DBBZ", nullable =true)
	private String DBBZ;

	public static Map<String,String> getDBBZTag() {
		try {
			return HelpTool.getSystemConstVal("DBBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDBBZ() {
		return DBBZ;
	}

	public void setDBBZ(String in) {
		DBBZ = in;
	}

	@IColumn(tagMethodName="getHTYXZTTag",description="合同有效状态")
	@Column(name = "HTYXZT", nullable =true)
	private String HTYXZT;

	public static Map<String,String> getHTYXZTTag() {
		try {
			return HelpTool.getSystemConstVal("HTYXZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getHTYXZT() {
		return HTYXZT;
	}

	public void setHTYXZT(String in) {
		HTYXZT = in;
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
	private zxptsystem.dto.AutoDTO_QY_DKYW_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_DKYW_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_DKYW_JC in) {
		FOREIGNID = in;
	}

}

