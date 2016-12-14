package dbgssystem.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
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

import com.opensymphony.xwork2.util.KeyProperty;

import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import java.util.Set;

import dbgssystem.dto.AutoDTO_DB_DBXX_JC;

@Entity
@Table(name = "DB_BDBRXX")
@IEntity(navigationName="被担保人信息段",description="被担保人信息段")
public class AutoDTO_DB_BDBRXX implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5", isNullable = true,isSingleTagHidden=true)
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4", isNullable = true,isSingleTagHidden=true)
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3", isNullable = true,isSingleTagHidden=true)
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2", isNullable = true,isSingleTagHidden=true)
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1", isNullable = true,isSingleTagHidden=true)
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}
	
	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态",isSingleTagHidden=true)
	@Column(name = "RPTCheckType", nullable =false)
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

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=true,isNullable=false)
	@Column(name = "RPTSendType", nullable =false)
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
	
	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden = true,isNullable=false)
	@Column(name = "RPTFeedbackType",length=1)
	private String RPTFeedbackType;
	
	public static Map<String,String> getRPTFeedbackTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
	}
	
	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}


	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}
	
	@IColumn(tagMethodName="getBDBRLXTag",description="被担保人类型")
	@Column(name = "BDBRLX", nullable =true)
	private String BDBRLX;

	public static Map<String,String> getBDBRLXTag() {
		try {
			return HelpTool.getSystemConstVal("BDBRLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBDBRLX() {
		return BDBRLX;
	}

	public void setBDBRLX(String in) {
		BDBRLX = in;
	}

	@Column(name = "BDBRMC", length = 200, nullable = true)
	@IColumn(description="被担保人名称")
	private String BDBRMC;

	public String getBDBRMC() {
		return BDBRMC;
	}

	public void setBDBRMC(String in) {
		BDBRMC = in;
	}

	@IColumn(tagMethodName="getBDBRZJLXTag",description="被担保人证件类型")
	@Column(name = "BDBRZJLX", nullable =true)
	private String BDBRZJLX;

	public static Map<String,String> getBDBRZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBDBRZJLX() {
		return BDBRZJLX;
	}

	public void setBDBRZJLX(String in) {
		BDBRZJLX = in;
	}

	@Column(name = "BDBRZJHM", length = 50, nullable = true)
	@IColumn(description="被担保人证件号码")
	private String BDBRZJHM;

	public String getBDBRZJHM() {
		return BDBRZJHM;
	}

	public void setBDBRZJHM(String in) {
		BDBRZJHM = in;
	}

	@IColumn(tagMethodName="getZTWTag",description="状态位")
	@Column(name = "ZTW", nullable =true)
	private String ZTW;

	public static Map<String,String> getZTWTag() {
		try {
			return HelpTool.getSystemConstVal("ZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZTW() {
		return ZTW;
	}

	public void setZTW(String in) {
		ZTW = in;
	}

	@Id
	@Column(name = "autoID", length = 100)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	@KeyProperty
	private String autoID;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String in) {
		autoID = in;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FOREIGNID", nullable = false)
	private dbgssystem.dto.AutoDTO_DB_DBXX_JC FOREIGNID;

	public dbgssystem.dto.AutoDTO_DB_DBXX_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(dbgssystem.dto.AutoDTO_DB_DBXX_JC in) {
		FOREIGNID = in;
	}

}

