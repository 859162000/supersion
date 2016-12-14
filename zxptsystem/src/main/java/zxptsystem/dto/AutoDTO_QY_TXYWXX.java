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

import zxptsystem.dto.AutoDTO_QY_PJTX_JC;

@Entity
@Table(name = "QY_TXYWXX")
@IEntity(description= "贴现业务信息段")
public class AutoDTO_QY_TXYWXX implements java.io.Serializable{

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

	@IColumn(tagMethodName="getPJZLTag",description="票据种类")
	@Column(name = "PJZL", nullable =true)
	private String PJZL;

	public static Map<String,String> getPJZLTag() {
		try {
			return HelpTool.getSystemConstVal("PJZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPJZL() {
		return PJZL;
	}

	public void setPJZL(String in) {
		PJZL = in;
	}

	@Column(name = "TXSQRMC", length = 200, nullable = true)
	@IColumn(description="贴现申请人名称")
	private String TXSQRMC;

	public String getTXSQRMC() {
		return TXSQRMC;
	}

	public void setTXSQRMC(String in) {
		TXSQRMC = in;
	}

	@Column(name = "DKKBM", length = 50, nullable = true)
	@IColumn(description="贴现申请人贷款卡编码")
	private String DKKBM;

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String in) {
		DKKBM = in;
	}

	@Column(name = "CDRHMC", length = 200, nullable = true)
	@IColumn(description="承兑人/行名称")
	private String CDRHMC;

	public String getCDRHMC() {
		return CDRHMC;
	}

	public void setCDRHMC(String in) {
		CDRHMC = in;
	}

	@Column(name = "CDRHDDKKBM", length = 50, nullable = true)
	@IColumn(description="承兑人/行贷款卡编码")
	private String CDRHDDKKBM;

	public String getCDRHDDKKBM() {
		return CDRHDDKKBM;
	}

	public void setCDRHDDKKBM(String in) {
		CDRHDDKKBM = in;
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

	@Column(name = "TXJE", length = 50, nullable = true)
	@IColumn(description="贴现金额")
	private String TXJE;

	public String getTXJE() {
		return TXJE;
	}

	public void setTXJE(String in) {
		TXJE = in;
	}

	@Column(name = "PMJE", length = 50, nullable = true)
	@IColumn(description="票面金额")
	private String PMJE;

	public String getPMJE() {
		return PMJE;
	}

	public void setPMJE(String in) {
		PMJE = in;
	}

	@Column(name = "TXR", length = 50, nullable = true)
	@IColumn(description="贴现日")
	private String TXR;

	public String getTXR() {
		return TXR;
	}

	public void setTXR(String in) {
		TXR = in;
	}

	@Column(name = "CDDQR", length = 50, nullable = true)
	@IColumn(description="承兑到期日")
	private String CDDQR;

	public String getCDDQR() {
		return CDDQR;
	}

	public void setCDDQR(String in) {
		CDDQR = in;
	}

	@IColumn(tagMethodName="getPJZTTag",description="票据状态")
	@Column(name = "PJZT", nullable =true)
	private String PJZT;

	public static Map<String,String> getPJZTTag() {
		try {
			return HelpTool.getSystemConstVal("PJZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPJZT() {
		return PJZT;
	}

	public void setPJZT(String in) {
		PJZT = in;
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
	private zxptsystem.dto.AutoDTO_QY_PJTX_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_PJTX_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_PJTX_JC in) {
		FOREIGNID = in;
	}

}

