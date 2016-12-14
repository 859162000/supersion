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
@Table(name = "DB_DCGKXX")
@IEntity(navigationName="代偿概况信息段",description="代偿概况信息段")
public class AutoDTO_DB_DCGKXX implements java.io.Serializable{

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
	
	@Column(name = "JZRQ", length = 50, nullable = true)
	@IColumn(description="记账日期")
	private String JZRQ;

	public String getJZRQ() {
		return JZRQ;
	}

	public void setJZRQ(String in) {
		JZRQ = in;
	}

	@IColumn(tagMethodName="getJXZCBZTag",description="继续追偿标志")
	@Column(name = "JXZCBZ", nullable =true)
	private String JXZCBZ;

	public static Map<String,String> getJXZCBZTag() {
		try {
			return HelpTool.getSystemConstVal("JXZCBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJXZCBZ() {
		return JXZCBZ;
	}

	public void setJXZCBZ(String in) {
		JXZCBZ = in;
	}

	@Column(name = "ZJYCDCRQ", length = 50, nullable = true)
	@IColumn(description="最近一次代偿日期")
	private String ZJYCDCRQ;

	public String getZJYCDCRQ() {
		return ZJYCDCRQ;
	}

	public void setZJYCDCRQ(String in) {
		ZJYCDCRQ = in;
	}

	@Column(name = "LJDCJE", length = 50, nullable = true)
	@IColumn(description="累计代偿金额")
	private String LJDCJE;

	public String getLJDCJE() {
		return LJDCJE;
	}

	public void setLJDCJE(String in) {
		LJDCJE = in;
	}

	@Column(name = "BJGCDDCJE", length = 50, nullable = true)
	@IColumn(description="本机构承担代偿金额")
	private String BJGCDDCJE;

	public String getBJGCDDCJE() {
		return BJGCDDCJE;
	}

	public void setBJGCDDCJE(String in) {
		BJGCDDCJE = in;
	}

	@Column(name = "ZJYCZCRQ", length = 50, nullable = true)
	@IColumn(description="最近一次追偿日期")
	private String ZJYCZCRQ;

	public String getZJYCZCRQ() {
		return ZJYCZCRQ;
	}

	public void setZJYCZCRQ(String in) {
		ZJYCZCRQ = in;
	}

	@Column(name = "DCYE", length = 50, nullable = true)
	@IColumn(description="代偿余额")
	private String DCYE;

	public String getDCYE() {
		return DCYE;
	}

	public void setDCYE(String in) {
		DCYE = in;
	}

	@Column(name = "BJGCDDCYE", length = 50, nullable = true)
	@IColumn(description="本机构承担代偿余额")
	private String BJGCDDCYE;

	public String getBJGCDDCYE() {
		return BJGCDDCYE;
	}

	public void setBJGCDDCYE(String in) {
		BJGCDDCYE = in;
	}

	@Column(name = "LJZCJE", length = 50, nullable = true)
	@IColumn(description="累计追偿金额")
	private String LJZCJE;

	public String getLJZCJE() {
		return LJZCJE;
	}

	public void setLJZCJE(String in) {
		LJZCJE = in;
	}

	@Column(name = "LJSSJE", length = 50, nullable = true)
	@IColumn(description="累计损失金额")
	private String LJSSJE;

	public String getLJSSJE() {
		return LJSSJE;
	}

	public void setLJSSJE(String in) {
		LJSSJE = in;
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

