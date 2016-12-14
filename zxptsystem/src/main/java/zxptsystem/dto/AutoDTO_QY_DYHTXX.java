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
@Table(name = "QY_DYHTXX")
@IEntity(description= "抵押合同信息段")
public class AutoDTO_QY_DYHTXX implements java.io.Serializable{

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

	@Column(name = "DYHTBM", length = 200, nullable = true)
	@IColumn(description="抵押合同编号")
	private String DYHTBM;

	public String getDYHTBM() {
		return DYHTBM;
	}

	public void setDYHTBM(String in) {
		DYHTBM = in;
	}

	@Column(name = "DYXH", length = 50, nullable = true)
	@IColumn(description="抵押序号")
	private String DYXH;

	public String getDYXH() {
		return DYXH;
	}

	public void setDYXH(String in) {
		DYXH = in;
	}

	@Column(name = "DYRMC", length = 200, nullable = true)
	@IColumn(description="抵押人名称")
	private String DYRMC;

	public String getDYRMC() {
		return DYRMC;
	}

	public void setDYRMC(String in) {
		DYRMC = in;
	}

	@Column(name = "DYRDKKBM", length = 50, nullable = true)
	@IColumn(description="贷款卡编码")
	private String DYRDKKBM;

	public String getDYRDKKBM() {
		return DYRDKKBM;
	}

	public void setDYRDKKBM(String in) {
		DYRDKKBM = in;
	}

	@IColumn(tagMethodName="getPGBZTag",description="币种")
	@Column(name = "PGBZ", nullable =true)
	private String PGBZ;

	public static Map<String,String> getPGBZTag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPGBZ() {
		return PGBZ;
	}

	public void setPGBZ(String in) {
		PGBZ = in;
	}

	@Column(name = "DYWPGJZ", length = 50, nullable = true)
	@IColumn(description="抵押物评估价值")
	private String DYWPGJZ;

	public String getDYWPGJZ() {
		return DYWPGJZ;
	}

	public void setDYWPGJZ(String in) {
		DYWPGJZ = in;
	}

	@Column(name = "PGRQ", length = 50, nullable = true)
	@IColumn(description="评估日期")
	private String PGRQ;

	public String getPGRQ() {
		return PGRQ;
	}

	public void setPGRQ(String in) {
		PGRQ = in;
	}

	@Column(name = "PGJGMC", length = 200, nullable = true)
	@IColumn(description="评估机构名称")
	private String PGJGMC;

	public String getPGJGMC() {
		return PGJGMC;
	}

	public void setPGJGMC(String in) {
		PGJGMC = in;
	}

	@Column(name = "PGJGZZJGDM", length = 50, nullable = true)
	@IColumn(description="评估机构组织机构代码")
	private String PGJGZZJGDM;

	public String getPGJGZZJGDM() {
		return PGJGZZJGDM;
	}

	public void setPGJGZZJGDM(String in) {
		PGJGZZJGDM = in;
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

	@IColumn(tagMethodName="getDYWZLTag",description="抵押物种类")
	@Column(name = "DYWZL", nullable =true)
	private String DYWZL;

	public static Map<String,String> getDYWZLTag() {
		try {
			return HelpTool.getSystemConstVal("DYWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDYWZL() {
		return DYWZL;
	}

	public void setDYWZL(String in) {
		DYWZL = in;
	}

	@IColumn(tagMethodName="getDYBZTag",description="币种")
	@Column(name = "DYBZ", nullable =true)
	private String DYBZ;

	public static Map<String,String> getDYBZTag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDYBZ() {
		return DYBZ;
	}

	public void setDYBZ(String in) {
		DYBZ = in;
	}

	@Column(name = "DYJE", length = 50, nullable = true)
	@IColumn(description="抵押金额")
	private String DYJE;

	public String getDYJE() {
		return DYJE;
	}

	public void setDYJE(String in) {
		DYJE = in;
	}

	@Column(name = "DJJG", length = 200, nullable = true)
	@IColumn(description="登记机关")
	private String DJJG;

	public String getDJJG() {
		return DJJG;
	}

	public void setDJJG(String in) {
		DJJG = in;
	}

	@Column(name = "DJRQ", length = 50, nullable = true)
	@IColumn(description="登记日期")
	private String DJRQ;

	public String getDJRQ() {
		return DJRQ;
	}

	public void setDJRQ(String in) {
		DJRQ = in;
	}

	@Column(name = "DYWSM", length = 800, nullable = true)
	@IColumn(description="抵押物说明")
	private String DYWSM;

	public String getDYWSM() {
		return DYWSM;
	}

	public void setDYWSM(String in) {
		DYWSM = in;
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

