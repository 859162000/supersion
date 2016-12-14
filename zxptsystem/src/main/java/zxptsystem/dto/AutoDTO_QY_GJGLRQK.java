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

import zxptsystem.dto.AutoDTO_QY_JKRZBGC_JC;

@Entity
@Table(name = "QY_GJGLRQK")
@IEntity(description= "高级管理人情况")
public class AutoDTO_QY_GJGLRQK implements java.io.Serializable{

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

	@IColumn(tagMethodName="getZJLXTag",description="证件类型")
	@Column(name = "ZJLX", nullable =true)
	private String ZJLX;

	public static Map<String,String> getZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZJLX() {
		return ZJLX;
	}

	public void setZJLX(String in) {
		ZJLX = in;
	}

	@Column(name = "ZJHM", length = 50, nullable = true)
	@IColumn(description="证件号码")
	private String ZJHM;

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String in) {
		ZJHM = in;
	}

	@Column(name = "GGRYXM", length = 100, nullable = true)
	@IColumn(description="高管人员姓名")
	private String GGRYXM;

	public String getGGRYXM() {
		return GGRYXM;
	}

	public void setGGRYXM(String in) {
		GGRYXM = in;
	}

	@IColumn(tagMethodName="getGGRYLBTag",description="高管人员类别")
	@Column(name = "GGRYLB", nullable =true)
	private String GGRYLB;

	public static Map<String,String> getGGRYLBTag() {
		try {
			return HelpTool.getSystemConstVal("GGRYLB");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getGGRYLB() {
		return GGRYLB;
	}

	public void setGGRYLB(String in) {
		GGRYLB = in;
	}

	@IColumn(tagMethodName="getXBTag",description="性别")
	@Column(name = "XB", nullable =true)
	private String XB;

	public static Map<String,String> getXBTag() {
		try {
			return HelpTool.getSystemConstVal("XB");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getXB() {
		return XB;
	}

	public void setXB(String in) {
		XB = in;
	}

	@Column(name = "GGRYCSRQ", length = 50, nullable = true)
	@IColumn(description="高管人员出生日期")
	private String GGRYCSRQ;

	public String getGGRYCSRQ() {
		return GGRYCSRQ;
	}

	public void setGGRYCSRQ(String in) {
		GGRYCSRQ = in;
	}

	@IColumn(tagMethodName="getGGRYZGXLTag",description="高管人员最高学历")
	@Column(name = "GGRYZGXL", nullable =true)
	private String GGRYZGXL;

	public static Map<String,String> getGGRYZGXLTag() {
		try {
			return HelpTool.getSystemConstVal("GGRYZGXL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getGGRYZGXL() {
		return GGRYZGXL;
	}

	public void setGGRYZGXL(String in) {
		GGRYZGXL = in;
	}

	@Column(name = "GGRYGZJL", length = 1000, nullable = true)
	@IColumn(description="高管人员工作简历")
	private String GGRYGZJL;

	public String getGGRYGZJL() {
		return GGRYGZJL;
	}

	public void setGGRYGZJL(String in) {
		GGRYGZJL = in;
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
	private zxptsystem.dto.AutoDTO_QY_JKRZBGC_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_JKRZBGC_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_JKRZBGC_JC in) {
		FOREIGNID = in;
	}

}

