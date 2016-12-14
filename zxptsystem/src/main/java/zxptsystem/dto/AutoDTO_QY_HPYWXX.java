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

import zxptsystem.dto.AutoDTO_QY_YHCDHP_JC;

@Entity
@Table(name = "QY_HPYWXX")
@IEntity(description= "汇票业务信息段")
public class AutoDTO_QY_HPYWXX implements java.io.Serializable{

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

	@Column(name = "CPRMC", length = 200, nullable = true)
	@IColumn(description="出票人名称")
	private String CPRMC;

	public String getCPRMC() {
		return CPRMC;
	}

	public void setCPRMC(String in) {
		CPRMC = in;
	}

	@Column(name = "DKKBM", length = 50, nullable = true)
	@IColumn(description="贷款卡编码")
	private String DKKBM;

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String in) {
		DKKBM = in;
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

	@Column(name = "HPJE", length = 50, nullable = true)
	@IColumn(description="汇票金额")
	private String HPJE;

	public String getHPJE() {
		return HPJE;
	}

	public void setHPJE(String in) {
		HPJE = in;
	}

	@Column(name = "HPCDR", length = 50, nullable = true)
	@IColumn(description="汇票承兑日")
	private String HPCDR;

	public String getHPCDR() {
		return HPCDR;
	}

	public void setHPCDR(String in) {
		HPCDR = in;
	}

	@Column(name = "HPDQR", length = 50, nullable = true)
	@IColumn(description="汇票到期日")
	private String HPDQR;

	public String getHPDQR() {
		return HPDQR;
	}

	public void setHPDQR(String in) {
		HPDQR = in;
	}

	@Column(name = "HPFKRQ", length = 50, nullable = true)
	@IColumn(description="汇票付款日期")
	private String HPFKRQ;

	public String getHPFKRQ() {
		return HPFKRQ;
	}

	public void setHPFKRQ(String in) {
		HPFKRQ = in;
	}

	@Column(name = "BZJBL", length = 50, nullable = true)
	@IColumn(description="保证金比例")
	private String BZJBL;

	public String getBZJBL() {
		return BZJBL;
	}

	public void setBZJBL(String in) {
		BZJBL = in;
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

	@IColumn(tagMethodName="getDKBZTag",description="垫款标志")
	@Column(name = "DKBZ", nullable =true)
	private String DKBZ;

	public static Map<String,String> getDKBZTag() {
		try {
			return HelpTool.getSystemConstVal("DKBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDKBZ() {
		return DKBZ;
	}

	public void setDKBZ(String in) {
		DKBZ = in;
	}

	@IColumn(tagMethodName="getHPZTTag",description="汇票状态")
	@Column(name = "HPZT", nullable =true)
	private String HPZT;

	public static Map<String,String> getHPZTTag() {
		try {
			return HelpTool.getSystemConstVal("HPZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getHPZT() {
		return HPZT;
	}

	public void setHPZT(String in) {
		HPZT = in;
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
	private zxptsystem.dto.AutoDTO_QY_YHCDHP_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_YHCDHP_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_YHCDHP_JC in) {
		FOREIGNID = in;
	}

}

