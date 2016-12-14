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
@Table(name = "JG_ZYGLQY")
@IEntity(description= "主要关联企业段")
public class AutoDTO_JG_ZYGLQY implements java.io.Serializable{

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

	@IColumn(tagMethodName="getGLLXTag",description="关联类型")
	@Column(name = "GLLX", nullable =true)
	private String GLLX;

	public static Map<String,String> getGLLXTag() {
		try {
			return HelpTool.getSystemConstVal("GLLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getGLLX() {
		return GLLX;
	}

	public void setGLLX(String in) {
		GLLX = in;
	}

	@Column(name = "GLQYMC", length = 100, nullable = true)
	@IColumn(description="关联企业名称")
	private String GLQYMC;

	public String getGLQYMC() {
		return GLQYMC;
	}

	public void setGLQYMC(String in) {
		GLQYMC = in;
	}

	@IColumn(tagMethodName="getDJZCHMLXTag",description="登记注册号类型")
	@Column(name = "DJZCHMLX", nullable =true)
	private String DJZCHMLX;

	public static Map<String,String> getDJZCHMLXTag() {
		try {
			return HelpTool.getSystemConstVal("DJZCHLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDJZCHMLX() {
		return DJZCHMLX;
	}

	public void setDJZCHMLX(String in) {
		DJZCHMLX = in;
	}

	@Column(name = "DJZCHM", length = 50, nullable = true)
	@IColumn(description="登记注册号码")
	private String DJZCHM;

	public String getDJZCHM() {
		return DJZCHM;
	}

	public void setDJZCHM(String in) {
		DJZCHM = in;
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

	@Column(name = "JGXYDM", length = 50, nullable = true)
	@IColumn(description="机构信用代码")
	private String JGXYDM;

	public String getJGXYDM() {
		return JGXYDM;
	}

	public void setJGXYDM(String in) {
		JGXYDM = in;
	}

	@Column(name = "XXGXRQ", length = 50, nullable = true)
	@IColumn(description="信息更新日期")
	private String XXGXRQ;

	public String getXXGXRQ() {
		return XXGXRQ;
	}

	public void setXXGXRQ(String in) {
		XXGXRQ = in;
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

