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

import zxptsystem.dto.AutoDTO_JG_JGJBXX_JC;

@Entity
@Table(name = "JG_ZYGD")
@IEntity(description= "重要股东段")
public class AutoDTO_JG_ZYGD implements java.io.Serializable{

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

	@IColumn(tagMethodName="getGDLXTag",description="股东类型")
	@Column(name = "GDLX", nullable =true)
	private String GDLX;

	public static Map<String,String> getGDLXTag() {
		try {
			return HelpTool.getSystemConstVal("GDLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getGDLX() {
		return GDLX;
	}

	public void setGDLX(String in) {
		GDLX = in;
	}

	@IColumn(tagMethodName="getGDZJLXTag",description="证件类型/登记注册号类型")
	@Column(name = "GDZJLX", nullable =true)
	private String GDZJLX;

	public static Map<String,String> getGDZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJ_DJ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getGDZJLX() {
		return GDZJLX;
	}

	public void setGDZJLX(String in) {
		GDZJLX = in;
	}

	@Column(name = "GDZJHM", length = 50, nullable = true)
	@IColumn(description="证件号码/登记注册号码")
	private String GDZJHM;

	public String getGDZJHM() {
		return GDZJHM;
	}

	public void setGDZJHM(String in) {
		GDZJHM = in;
	}

	@Column(name = "GDMC", length = 100, nullable = true)
	@IColumn(description="股东名称")
	private String GDMC;

	public String getGDMC() {
		return GDMC;
	}

	public void setGDMC(String in) {
		GDMC = in;
	}

	@Column(name = "GDZZJGDM", length = 50, nullable = true)
	@IColumn(description="组织机构代码")
	private String GDZZJGDM;

	public String getGDZZJGDM() {
		return GDZZJGDM;
	}

	public void setGDZZJGDM(String in) {
		GDZZJGDM = in;
	}

	@Column(name = "CURRENCY", length = 50, nullable = true)
	@IColumn(description="机构信用代码")
	private String CURRENCY;

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String in) {
		CURRENCY = in;
	}

	@Column(name = "GDCZJE", length = 50, nullable = true)
	@IColumn(description="持股比例")
	private String GDCZJE;

	public String getGDCZJE() {
		return GDCZJE;
	}

	public void setGDCZJE(String in) {
		GDCZJE = in;
	}

	@Column(name = "GDXXGXRQ", length = 50, nullable = true)
	@IColumn(description="信息更新日期")
	private String GDXXGXRQ;

	public String getGDXXGXRQ() {
		return GDXXGXRQ;
	}

	public void setGDXXGXRQ(String in) {
		GDXXGXRQ = in;
	}

	@Column(name = "YLZD", length = 100, nullable = true)
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
	private zxptsystem.dto.AutoDTO_JG_JGJBXX_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_JG_JGJBXX_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_JG_JGJBXX_JC in) {
		FOREIGNID = in;
	}

}

