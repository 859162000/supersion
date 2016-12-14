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

import zxptsystem.dto.AutoDTO_QY_DBXX_JC;

@Entity
@Table(name = "QY_ZYHTXX")
@IEntity(description= "质押合同信息段")
public class AutoDTO_QY_ZYHTXX implements java.io.Serializable{

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

	@Column(name = "ZYHTBH", length = 200, nullable = true)
	@IColumn(description="质押合同编号")
	private String ZYHTBH;

	public String getZYHTBH() {
		return ZYHTBH;
	}

	public void setZYHTBH(String in) {
		ZYHTBH = in;
	}

	@Column(name = "ZYXH", length = 50, nullable = true)
	@IColumn(description="质押序号")
	private String ZYXH;

	public String getZYXH() {
		return ZYXH;
	}

	public void setZYXH(String in) {
		ZYXH = in;
	}

	@Column(name = "CZRMC", length = 200, nullable = true)
	@IColumn(description="出质人名称")
	private String CZRMC;

	public String getCZRMC() {
		return CZRMC;
	}

	public void setCZRMC(String in) {
		CZRMC = in;
	}

	@Column(name = "CZRDDKKBM", length = 50, nullable = true)
	@IColumn(description="贷款卡编码")
	private String CZRDDKKBM;

	public String getCZRDDKKBM() {
		return CZRDDKKBM;
	}

	public void setCZRDDKKBM(String in) {
		CZRDDKKBM = in;
	}

	@IColumn(tagMethodName="getZYBZTag",description="币种")
	@Column(name = "ZYBZ", nullable =true)
	private String ZYBZ;

	public static Map<String,String> getZYBZTag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZYBZ() {
		return ZYBZ;
	}

	public void setZYBZ(String in) {
		ZYBZ = in;
	}

	@Column(name = "ZYWJZ", length = 50, nullable = true)
	@IColumn(description="质押物价值")
	private String ZYWJZ;

	public String getZYWJZ() {
		return ZYWJZ;
	}

	public void setZYWJZ(String in) {
		ZYWJZ = in;
	}

	@Column(name = "HTQDRQ", length = 50, nullable = true)
	@IColumn(description="合同签订日期")
	private String HTQDRQ;

	public String getHTQDRQ() {
		return HTQDRQ;
	}

	public void setHTQDRQ(String in) {
		HTQDRQ = in;
	}

	@IColumn(tagMethodName="getZYWZLTag",description="质押物种类")
	@Column(name = "ZYWZL", nullable =true)
	private String ZYWZL;

	public static Map<String,String> getZYWZLTag() {
		try {
			return HelpTool.getSystemConstVal("ZYWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZYWZL() {
		return ZYWZL;
	}

	public void setZYWZL(String in) {
		ZYWZL = in;
	}

	@IColumn(tagMethodName="getZYWJZBZTag",description="币种")
	@Column(name = "ZYWJZBZ", nullable =true)
	private String ZYWJZBZ;

	public static Map<String,String> getZYWJZBZTag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZYWJZBZ() {
		return ZYWJZBZ;
	}

	public void setZYWJZBZ(String in) {
		ZYWJZBZ = in;
	}

	@Column(name = "ZYJE", length = 50, nullable = true)
	@IColumn(description="质押金额")
	private String ZYJE;

	public String getZYJE() {
		return ZYJE;
	}

	public void setZYJE(String in) {
		ZYJE = in;
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
	private zxptsystem.dto.AutoDTO_QY_DBXX_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_DBXX_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_DBXX_JC in) {
		FOREIGNID = in;
	}

}

