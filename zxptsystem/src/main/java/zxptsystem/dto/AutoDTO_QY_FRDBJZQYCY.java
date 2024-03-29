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
@Table(name = "QY_FRDBJZQYCY")
@IEntity(description= "法人代表家族企业成员情况")
public class AutoDTO_QY_FRDBJZQYCY implements java.io.Serializable{

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

	@IColumn(tagMethodName="getZJLXTag",description="证件类型")
	@Column(name = "ZJLX", nullable =true)
	private String ZJLX;

	public static Map<String,String> getZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZJLX() {
		return ZJLX;
	}

	public void setZJLX(String in) {
		ZJLX = in;
	}

	@Column(name = "ZJHM", length = 50, nullable = true)
	@IColumn(description="证件号码")
	private String ZJHM;

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String in) {
		ZJHM = in;
	}

	@Column(name = "JZCYXM", length = 100, nullable = true)
	@IColumn(description="家族成员姓名")
	private String JZCYXM;

	public String getJZCYXM() {
		return JZCYXM;
	}

	public void setJZCYXM(String in) {
		JZCYXM = in;
	}

	@IColumn(tagMethodName="getJZGXTag",description="家族关系")
	@Column(name = "JZGX", nullable =true)
	private String JZGX;

	public static Map<String,String> getJZGXTag() {
		try {
			return HelpTool.getSystemConstVal("JZGX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJZGX() {
		return JZGX;
	}

	public void setJZGX(String in) {
		JZGX = in;
	}

	@Column(name = "JZCYSZQYMC", length = 200, nullable = true)
	@IColumn(description="家族成员所在企业名称")
	private String JZCYSZQYMC;

	public String getJZCYSZQYMC() {
		return JZCYSZQYMC;
	}

	public void setJZCYSZQYMC(String in) {
		JZCYSZQYMC = in;
	}

	@Column(name = "SZQYDDKKBM", length = 50, nullable = true)
	@IColumn(description="贷款卡编码")
	private String SZQYDDKKBM;

	public String getSZQYDDKKBM() {
		return SZQYDDKKBM;
	}

	public void setSZQYDDKKBM(String in) {
		SZQYDDKKBM = in;
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

