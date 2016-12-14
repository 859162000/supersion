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
import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import java.util.Set;

import dbgssystem.dto.AutoDTO_DB_DBXX_JC;

@Entity
@Table(name = "DB_BFJNGKXX")
@IEntity(navigationName="保费缴纳概况信息段",description="保费缴纳概况信息段")
public class AutoDTO_DB_BFJNGKXX implements java.io.Serializable{

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
	
	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态",isSingleTagHidden=false)
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

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=false,isNullable=false)
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
	
	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden = false,isNullable=false)
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
	
	@IColumn(tagMethodName="getJFLBTag",description="缴费类别")
	@Column(name = "JFLB", nullable =true)
	private String JFLB;

	public static Map<String,String> getJFLBTag() {
		try {
			return HelpTool.getSystemConstVal("JFLB");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJFLB() {
		return JFLB;
	}

	public void setJFLB(String in) {
		JFLB = in;
	}

	@Column(name = "JZRQ", length = 50, nullable = true)
	@IColumn(description="记账日期")
	private String JZRQ;

	public String getJZRQ() {
		return JZRQ;
	}

	public void setJZRQ(String in) {
		JZRQ = in;
	}

	@IColumn(tagMethodName="getJNFSTag",description="缴纳方式")
	@Column(name = "JNFS", nullable =true)
	private String JNFS;

	public static Map<String,String> getJNFSTag() {
		try {
			return HelpTool.getSystemConstVal("JNFS");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJNFS() {
		return JNFS;
	}

	public void setJNFS(String in) {
		JNFS = in;
	}

	@Column(name = "JE", length = 50, nullable = true)
	@IColumn(description="金额")
	private String JE;

	public String getJE() {
		return JE;
	}

	public void setJE(String in) {
		JE = in;
	}

	@IColumn(tagMethodName="getJNPLTag",description="缴纳频率")
	@Column(name = "JNPL", nullable =true)
	private String JNPL;

	public static Map<String,String> getJNPLTag() {
		try {
			return HelpTool.getSystemConstVal("BFJNPL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJNPL() {
		return JNPL;
	}

	public void setJNPL(String in) {
		JNPL = in;
	}

	@Column(name = "JFQSRQ", length = 50, nullable = true)
	@IColumn(description="计费起始日期")
	private String JFQSRQ;

	public String getJFQSRQ() {
		return JFQSRQ;
	}

	public void setJFQSRQ(String in) {
		JFQSRQ = in;
	}

	@IColumn(tagMethodName="getBFJNZTTag",description="保费缴纳状态")
	@Column(name = "BFJNZT", nullable =true)
	private String BFJNZT;

	public static Map<String,String> getBFJNZTTag() {
		try {
			return HelpTool.getSystemConstVal("BFJNZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBFJNZT() {
		return BFJNZT;
	}

	public void setBFJNZT(String in) {
		BFJNZT = in;
	}

	@Column(name = "JNJSRQ", length = 50, nullable = true)
	@IColumn(description="缴纳结束日期")
	private String JNJSRQ;

	public String getJNJSRQ() {
		return JNJSRQ;
	}

	public void setJNJSRQ(String in) {
		JNJSRQ = in;
	}

	@Column(name = "YE", length = 50, nullable = true)
	@IColumn(description="余额")
	private String YE;

	public String getYE() {
		return YE;
	}

	public void setYE(String in) {
		YE = in;
	}

	@Column(name = "LJQJJE", length = 50, nullable = true)
	@IColumn(description="累计欠缴金额")
	private String LJQJJE;

	public String getLJQJJE() {
		return LJQJJE;
	}

	public void setLJQJJE(String in) {
		LJQJJE = in;
	}

	@Column(name = "YLZD", length = 200, nullable = true)
	@IColumn(description="预留字段")
	private String YLZD;

	public String getYLZD() {
		return YLZD;
	}

	public void setYLZD(String in) {
		YLZD = in;
	}

	@Id
	@Column(name = "autoID", length = 100)
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
	private dbgssystem.dto.AutoDTO_DB_DBXX_JC FOREIGNID;

	public dbgssystem.dto.AutoDTO_DB_DBXX_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(dbgssystem.dto.AutoDTO_DB_DBXX_JC in) {
		FOREIGNID = in;
	}

}

