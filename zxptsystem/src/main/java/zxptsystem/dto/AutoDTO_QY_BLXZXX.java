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

import zxptsystem.dto.AutoDTO_QY_BLYW_JC;

@Entity
@Table(name = "QY_BLXZXX")
@IEntity(description= "保理叙做信息段")
public class AutoDTO_QY_BLXZXX implements java.io.Serializable{

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

	@IColumn(tagMethodName="getBLCPLXTag",description="保理产品类型")
	@Column(name = "BLCPLX", nullable =true)
	private String BLCPLX;

	public static Map<String,String> getBLCPLXTag() {
		try {
			return HelpTool.getSystemConstVal("BLCPLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBLCPLX() {
		return BLCPLX;
	}

	public void setBLCPLX(String in) {
		BLCPLX = in;
	}

	@IColumn(tagMethodName="getBLYWZTTag",description="保理业务状态")
	@Column(name = "BLYWZT", nullable =true)
	private String BLYWZT;

	public static Map<String,String> getBLYWZTTag() {
		try {
			return HelpTool.getSystemConstVal("BLYWZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBLYWZT() {
		return BLYWZT;
	}

	public void setBLYWZT(String in) {
		BLYWZT = in;
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

	@Column(name = "XZJE", length = 50, nullable = true)
	@IColumn(description="叙做金额")
	private String XZJE;

	public String getXZJE() {
		return XZJE;
	}

	public void setXZJE(String in) {
		XZJE = in;
	}

	@Column(name = "XZRQ", length = 50, nullable = true)
	@IColumn(description="叙做日期")
	private String XZRQ;

	public String getXZRQ() {
		return XZRQ;
	}

	public void setXZRQ(String in) {
		XZRQ = in;
	}

	@Column(name = "XZYE", length = 50, nullable = true)
	@IColumn(description="叙做余额")
	private String XZYE;

	public String getXZYE() {
		return XZYE;
	}

	public void setXZYE(String in) {
		XZYE = in;
	}

	@Column(name = "YEBHRQ", length = 50, nullable = true)
	@IColumn(description="余额变化日期")
	private String YEBHRQ;

	public String getYEBHRQ() {
		return YEBHRQ;
	}

	public void setYEBHRQ(String in) {
		YEBHRQ = in;
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
	private zxptsystem.dto.AutoDTO_QY_BLYW_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_BLYW_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_BLYW_JC in) {
		FOREIGNID = in;
	}

}

