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
@Table(name = "QY_JJXX")
@IEntity(description= "借据信息")
public class AutoDTO_QY_JJXX implements java.io.Serializable{

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

	@Column(name = "JJBH", length = 200, nullable = true)
	@IColumn(description="借据编号")
	private String JJBH;

	public String getJJBH() {
		return JJBH;
	}

	public void setJJBH(String in) {
		JJBH = in;
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

	@Column(name = "DKJJJE", length = 50, nullable = true)
	@IColumn(description="贷款借据金额")
	private String DKJJJE;

	public String getDKJJJE() {
		return DKJJJE;
	}

	public void setDKJJJE(String in) {
		DKJJJE = in;
	}

	@Column(name = "DKJJYE", length = 50, nullable = true)
	@IColumn(description="贷款借据余额")
	private String DKJJYE;

	public String getDKJJYE() {
		return DKJJYE;
	}

	public void setDKJJYE(String in) {
		DKJJYE = in;
	}

	@Column(name = "JJFKRQ", length = 50, nullable = true)
	@IColumn(description="借据放款日期")
	private String JJFKRQ;

	public String getJJFKRQ() {
		return JJFKRQ;
	}

	public void setJJFKRQ(String in) {
		JJFKRQ = in;
	}

	@Column(name = "JJFKDQR", length = 50, nullable = true)
	@IColumn(description="借据放款到期日")
	private String JJFKDQR;

	public String getJJFKDQR() {
		return JJFKDQR;
	}

	public void setJJFKDQR(String in) {
		JJFKDQR = in;
	}

	@IColumn(tagMethodName="getDKYWZLTag",description="贷款业务种类")
	@Column(name = "DKYWZL", nullable =true)
	private String DKYWZL;

	public static Map<String,String> getDKYWZLTag() {
		try {
			return HelpTool.getSystemConstVal("DKYWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDKYWZL() {
		return DKYWZL;
	}

	public void setDKYWZL(String in) {
		DKYWZL = in;
	}

	@IColumn(tagMethodName="getDKXSTag",description="贷款形式")
	@Column(name = "DKXS", nullable =true)
	private String DKXS;

	public static Map<String,String> getDKXSTag() {
		try {
			return HelpTool.getSystemConstVal("DKXS");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDKXS() {
		return DKXS;
	}

	public void setDKXS(String in) {
		DKXS = in;
	}

	@IColumn(tagMethodName="getDKXZTag",description="贷款性质")
	@Column(name = "DKXZ", nullable =true)
	private String DKXZ;

	public static Map<String,String> getDKXZTag() {
		try {
			return HelpTool.getSystemConstVal("DKXZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDKXZ() {
		return DKXZ;
	}

	public void setDKXZ(String in) {
		DKXZ = in;
	}

	@IColumn(tagMethodName="getDKTXTag",description="贷款投向")
	@Column(name = "DKTX", nullable =true)
	private String DKTX;

	public static Map<String,String> getDKTXTag() {
		try {
			return HelpTool.getSystemConstVal("HYFL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDKTX() {
		return DKTX;
	}

	public void setDKTX(String in) {
		DKTX = in;
	}

	@IColumn(tagMethodName="getDKZLTag",description="贷款种类")
	@Column(name = "DKZL", nullable =true)
	private String DKZL;

	public static Map<String,String> getDKZLTag() {
		try {
			return HelpTool.getSystemConstVal("DKZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDKZL() {
		return DKZL;
	}

	public void setDKZL(String in) {
		DKZL = in;
	}

	@IColumn(tagMethodName="getZQBZTag",description="展期标志")
	@Column(name = "ZQBZ", nullable =true)
	private String ZQBZ;

	public static Map<String,String> getZQBZTag() {
		try {
			return HelpTool.getSystemConstVal("ZQBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZQBZ() {
		return ZQBZ;
	}

	public void setZQBZ(String in) {
		ZQBZ = in;
	}

	@IColumn(tagMethodName="getSJFLTag",description="四级分类")
	@Column(name = "SJFL", nullable =true)
	private String SJFL;

	public static Map<String,String> getSJFLTag() {
		try {
			return HelpTool.getSystemConstVal("SJFL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getSJFL() {
		return SJFL;
	}

	public void setSJFL(String in) {
		SJFL = in;
	}

	@IColumn(tagMethodName="getWJFLTag",description="五级分类")
	@Column(name = "WJFL", nullable =true)
	private String WJFL;

	public static Map<String,String> getWJFLTag() {
		try {
			return HelpTool.getSystemConstVal("WJFL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getWJFL() {
		return WJFL;
	}

	public void setWJFL(String in) {
		WJFL = in;
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

