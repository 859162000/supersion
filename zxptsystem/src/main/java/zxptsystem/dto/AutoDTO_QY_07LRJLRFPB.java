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

import zxptsystem.dto.AutoDTO_QY_JKRCWBB_JC;

@Entity
@Table(name = "QY_07LRJLRFPB")
@IEntity(description= "2007版利润及利润分配表")
public class AutoDTO_QY_07LRJLRFPB implements java.io.Serializable{

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

	@Column(name = "YYSR", length = 50, nullable = true)
	@IColumn(description="营业收入")
	private String YYSR;

	public String getYYSR() {
		return YYSR;
	}

	public void setYYSR(String in) {
		YYSR = in;
	}

	@Column(name = "YYCB", length = 50, nullable = true)
	@IColumn(description="营业成本")
	private String YYCB;

	public String getYYCB() {
		return YYCB;
	}

	public void setYYCB(String in) {
		YYCB = in;
	}

	@Column(name = "YYSJJFJ", length = 50, nullable = true)
	@IColumn(description="营业税金及附加")
	private String YYSJJFJ;

	public String getYYSJJFJ() {
		return YYSJJFJ;
	}

	public void setYYSJJFJ(String in) {
		YYSJJFJ = in;
	}

	@Column(name = "XSFY", length = 50, nullable = true)
	@IColumn(description="销售费用")
	private String XSFY;

	public String getXSFY() {
		return XSFY;
	}

	public void setXSFY(String in) {
		XSFY = in;
	}

	@Column(name = "GLFY", length = 50, nullable = true)
	@IColumn(description="管理费用")
	private String GLFY;

	public String getGLFY() {
		return GLFY;
	}

	public void setGLFY(String in) {
		GLFY = in;
	}

	@Column(name = "CWFY", length = 50, nullable = true)
	@IColumn(description="财务费用")
	private String CWFY;

	public String getCWFY() {
		return CWFY;
	}

	public void setCWFY(String in) {
		CWFY = in;
	}

	@Column(name = "ZCJZSS", length = 50, nullable = true)
	@IColumn(description="资产减值损失")
	private String ZCJZSS;

	public String getZCJZSS() {
		return ZCJZSS;
	}

	public void setZCJZSS(String in) {
		ZCJZSS = in;
	}

	@Column(name = "GYJZBDJSY", length = 50, nullable = true)
	@IColumn(description="公允价值变动净收益")
	private String GYJZBDJSY;

	public String getGYJZBDJSY() {
		return GYJZBDJSY;
	}

	public void setGYJZBDJSY(String in) {
		GYJZBDJSY = in;
	}

	@Column(name = "TZJSY", length = 50, nullable = true)
	@IColumn(description="投资净收益")
	private String TZJSY;

	public String getTZJSY() {
		return TZJSY;
	}

	public void setTZJSY(String in) {
		TZJSY = in;
	}

	@Column(name = "DLYQYHHYQYDTZSY", length = 50, nullable = true)
	@IColumn(description="对联营企业和合营企业的投资收益")
	private String DLYQYHHYQYDTZSY;

	public String getDLYQYHHYQYDTZSY() {
		return DLYQYHHYQYDTZSY;
	}

	public void setDLYQYHHYQYDTZSY(String in) {
		DLYQYHHYQYDTZSY = in;
	}

	@Column(name = "YYLR", length = 50, nullable = true)
	@IColumn(description="营业利润")
	private String YYLR;

	public String getYYLR() {
		return YYLR;
	}

	public void setYYLR(String in) {
		YYLR = in;
	}

	@Column(name = "YYWSR", length = 50, nullable = true)
	@IColumn(description="营业外收入")
	private String YYWSR;

	public String getYYWSR() {
		return YYWSR;
	}

	public void setYYWSR(String in) {
		YYWSR = in;
	}

	@Column(name = "YYWZC", length = 50, nullable = true)
	@IColumn(description="营业外支出")
	private String YYWZC;

	public String getYYWZC() {
		return YYWZC;
	}

	public void setYYWZC(String in) {
		YYWZC = in;
	}

	@Column(name = "FLDZCSS", length = 50, nullable = true)
	@IColumn(description="非流动资产损失")
	private String FLDZCSS;

	public String getFLDZCSS() {
		return FLDZCSS;
	}

	public void setFLDZCSS(String in) {
		FLDZCSS = in;
	}

	@Column(name = "LRZE", length = 50, nullable = true)
	@IColumn(description="利润总额")
	private String LRZE;

	public String getLRZE() {
		return LRZE;
	}

	public void setLRZE(String in) {
		LRZE = in;
	}

	@Column(name = "SDSFY", length = 50, nullable = true)
	@IColumn(description="所得税费用")
	private String SDSFY;

	public String getSDSFY() {
		return SDSFY;
	}

	public void setSDSFY(String in) {
		SDSFY = in;
	}

	@Column(name = "JLR", length = 50, nullable = true)
	@IColumn(description="净利润")
	private String JLR;

	public String getJLR() {
		return JLR;
	}

	public void setJLR(String in) {
		JLR = in;
	}

	@Column(name = "JBMGSY", length = 50, nullable = true)
	@IColumn(description="基本每股收益")
	private String JBMGSY;

	public String getJBMGSY() {
		return JBMGSY;
	}

	public void setJBMGSY(String in) {
		JBMGSY = in;
	}

	@Column(name = "XSMGSY", length = 50, nullable = true)
	@IColumn(description="稀释每股收益")
	private String XSMGSY;

	public String getXSMGSY() {
		return XSMGSY;
	}

	public void setXSMGSY(String in) {
		XSMGSY = in;
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
	private zxptsystem.dto.AutoDTO_QY_JKRCWBB_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_JKRCWBB_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_JKRCWBB_JC in) {
		FOREIGNID = in;
	}

}

