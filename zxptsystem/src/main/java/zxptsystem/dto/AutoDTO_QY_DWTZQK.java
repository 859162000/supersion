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

import zxptsystem.dto.AutoDTO_QY_JKRZBGC_JC;

@Entity
@Table(name = "QY_DWTZQK")
@IEntity(description= "对外投资情况")
public class AutoDTO_QY_DWTZQK implements java.io.Serializable{

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

	@Column(name = "BTZDWMC", length = 200, nullable = true)
	@IColumn(description="被投资单位名称")
	private String BTZDWMC;

	public String getBTZDWMC() {
		return BTZDWMC;
	}

	public void setBTZDWMC(String in) {
		BTZDWMC = in;
	}

	@Column(name = "BTZDWDKKBM", length = 50, nullable = true)
	@IColumn(description="贷款卡编码")
	private String BTZDWDKKBM;

	public String getBTZDWDKKBM() {
		return BTZDWDKKBM;
	}

	public void setBTZDWDKKBM(String in) {
		BTZDWDKKBM = in;
	}

	@Column(name = "ZZJGDM", length = 50, nullable = true)
	@IColumn(description="组织机构代码")
	private String ZZJGDM;

	public String getZZJGDM() {
		return ZZJGDM;
	}

	public void setZZJGDM(String in) {
		ZZJGDM = in;
	}

	@IColumn(tagMethodName="getBZ1Tag",description="币种1")
	@Column(name = "BZ1", nullable =true)
	private String BZ1;

	public static Map<String,String> getBZ1Tag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBZ1() {
		return BZ1;
	}

	public void setBZ1(String in) {
		BZ1 = in;
	}

	@Column(name = "TZJE1", length = 50, nullable = true)
	@IColumn(description="投资金额1")
	private String TZJE1;

	public String getTZJE1() {
		return TZJE1;
	}

	public void setTZJE1(String in) {
		TZJE1 = in;
	}

	@IColumn(tagMethodName="getBZ2Tag",description="币种2")
	@Column(name = "BZ2", nullable =true)
	private String BZ2;

	public static Map<String,String> getBZ2Tag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBZ2() {
		return BZ2;
	}

	public void setBZ2(String in) {
		BZ2 = in;
	}

	@Column(name = "TZJE2", length = 50, nullable = true)
	@IColumn(description="投资金额2")
	private String TZJE2;

	public String getTZJE2() {
		return TZJE2;
	}

	public void setTZJE2(String in) {
		TZJE2 = in;
	}

	@IColumn(tagMethodName="getBZ3Tag",description="币种3")
	@Column(name = "BZ3", nullable =true)
	private String BZ3;

	public static Map<String,String> getBZ3Tag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBZ3() {
		return BZ3;
	}

	public void setBZ3(String in) {
		BZ3 = in;
	}

	@Column(name = "TZJE3", length = 50, nullable = true)
	@IColumn(description="投资金额3")
	private String TZJE3;

	public String getTZJE3() {
		return TZJE3;
	}

	public void setTZJE3(String in) {
		TZJE3 = in;
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
	private zxptsystem.dto.AutoDTO_QY_JKRZBGC_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_JKRZBGC_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_JKRZBGC_JC in) {
		FOREIGNID = in;
	}

}

