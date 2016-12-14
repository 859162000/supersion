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

import zxptsystem.dto.AutoDTO_QY_MYRZ_JC;

@Entity
@Table(name = "QY_RZYWXX")
@IEntity(description= "融资业务信息段")
public class AutoDTO_QY_RZYWXX implements java.io.Serializable{

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

	@Column(name = "RZYWBH", length = 200, nullable = true)
	@IColumn(description="融资业务编号")
	private String RZYWBH;

	public String getRZYWBH() {
		return RZYWBH;
	}

	public void setRZYWBH(String in) {
		RZYWBH = in;
	}

	@IColumn(tagMethodName="getRZYWZLTag",description="融资业务种类")
	@Column(name = "RZYWZL", nullable =true)
	private String RZYWZL;

	public static Map<String,String> getRZYWZLTag() {
		try {
			return HelpTool.getSystemConstVal("RZYWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getRZYWZL() {
		return RZYWZL;
	}

	public void setRZYWZL(String in) {
		RZYWZL = in;
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

	@Column(name = "RZJE", length = 50, nullable = true)
	@IColumn(description="融资金额")
	private String RZJE;

	public String getRZJE() {
		return RZJE;
	}

	public void setRZJE(String in) {
		RZJE = in;
	}

	@Column(name = "RZYE", length = 50, nullable = true)
	@IColumn(description="融资余额")
	private String RZYE;

	public String getRZYE() {
		return RZYE;
	}

	public void setRZYE(String in) {
		RZYE = in;
	}

	@Column(name = "RZYWFSRQ", length = 50, nullable = true)
	@IColumn(description="融资业务发放日期")
	private String RZYWFSRQ;

	public String getRZYWFSRQ() {
		return RZYWFSRQ;
	}

	public void setRZYWFSRQ(String in) {
		RZYWFSRQ = in;
	}

	@Column(name = "RZYWJSRQ", length = 50, nullable = true)
	@IColumn(description="融资业务结束日期")
	private String RZYWJSRQ;

	public String getRZYWJSRQ() {
		return RZYWJSRQ;
	}

	public void setRZYWJSRQ(String in) {
		RZYWJSRQ = in;
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
	private zxptsystem.dto.AutoDTO_QY_MYRZ_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_MYRZ_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_MYRZ_JC in) {
		FOREIGNID = in;
	}

}

