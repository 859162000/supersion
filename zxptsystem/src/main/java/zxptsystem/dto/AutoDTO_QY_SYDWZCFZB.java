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
@Table(name = "QY_SYDWZCFZB")
@IEntity(description= "事业单位资产负债表")
public class AutoDTO_QY_SYDWZCFZB implements java.io.Serializable{

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

	@Column(name = "XJ", length = 50, nullable = true)
	@IColumn(description="现金")
	private String XJ;

	public String getXJ() {
		return XJ;
	}

	public void setXJ(String in) {
		XJ = in;
	}

	@Column(name = "YHCK", length = 50, nullable = true)
	@IColumn(description="银行存款")
	private String YHCK;

	public String getYHCK() {
		return YHCK;
	}

	public void setYHCK(String in) {
		YHCK = in;
	}

	@Column(name = "YSPJ", length = 50, nullable = true)
	@IColumn(description="应收票据")
	private String YSPJ;

	public String getYSPJ() {
		return YSPJ;
	}

	public void setYSPJ(String in) {
		YSPJ = in;
	}

	@Column(name = "YSZK", length = 50, nullable = true)
	@IColumn(description="应收账款")
	private String YSZK;

	public String getYSZK() {
		return YSZK;
	}

	public void setYSZK(String in) {
		YSZK = in;
	}

	@Column(name = "YFZK", length = 50, nullable = true)
	@IColumn(description="预付账款")
	private String YFZK;

	public String getYFZK() {
		return YFZK;
	}

	public void setYFZK(String in) {
		YFZK = in;
	}

	@Column(name = "QTYSK", length = 50, nullable = true)
	@IColumn(description="其他应收款")
	private String QTYSK;

	public String getQTYSK() {
		return QTYSK;
	}

	public void setQTYSK(String in) {
		QTYSK = in;
	}

	@Column(name = "CL", length = 50, nullable = true)
	@IColumn(description="材料")
	private String CL;

	public String getCL() {
		return CL;
	}

	public void setCL(String in) {
		CL = in;
	}

	@Column(name = "CCP", length = 50, nullable = true)
	@IColumn(description="产成品")
	private String CCP;

	public String getCCP() {
		return CCP;
	}

	public void setCCP(String in) {
		CCP = in;
	}

	@Column(name = "DWTZ", length = 50, nullable = true)
	@IColumn(description="对外投资")
	private String DWTZ;

	public String getDWTZ() {
		return DWTZ;
	}

	public void setDWTZ(String in) {
		DWTZ = in;
	}

	@Column(name = "GDZC", length = 50, nullable = true)
	@IColumn(description="固定资产")
	private String GDZC;

	public String getGDZC() {
		return GDZC;
	}

	public void setGDZC(String in) {
		GDZC = in;
	}

	@Column(name = "WXZC", length = 50, nullable = true)
	@IColumn(description="无形资产")
	private String WXZC;

	public String getWXZC() {
		return WXZC;
	}

	public void setWXZC(String in) {
		WXZC = in;
	}

	@Column(name = "ZCHJ", length = 50, nullable = true)
	@IColumn(description="资产合计")
	private String ZCHJ;

	public String getZCHJ() {
		return ZCHJ;
	}

	public void setZCHJ(String in) {
		ZCHJ = in;
	}

	@Column(name = "BCJF", length = 50, nullable = true)
	@IColumn(description="拨出经费")
	private String BCJF;

	public String getBCJF() {
		return BCJF;
	}

	public void setBCJF(String in) {
		BCJF = in;
	}

	@Column(name = "BCZK", length = 50, nullable = true)
	@IColumn(description="拨出专款")
	private String BCZK;

	public String getBCZK() {
		return BCZK;
	}

	public void setBCZK(String in) {
		BCZK = in;
	}

	@Column(name = "ZKZC", length = 50, nullable = true)
	@IColumn(description="专款支出")
	private String ZKZC;

	public String getZKZC() {
		return ZKZC;
	}

	public void setZKZC(String in) {
		ZKZC = in;
	}

	@Column(name = "SYZC", length = 50, nullable = true)
	@IColumn(description="事业支出")
	private String SYZC;

	public String getSYZC() {
		return SYZC;
	}

	public void setSYZC(String in) {
		SYZC = in;
	}

	@Column(name = "JYZC", length = 50, nullable = true)
	@IColumn(description="经营支出")
	private String JYZC;

	public String getJYZC() {
		return JYZC;
	}

	public void setJYZC(String in) {
		JYZC = in;
	}

	@Column(name = "CBJF", length = 50, nullable = true)
	@IColumn(description="成本费用")
	private String CBJF;

	public String getCBJF() {
		return CBJF;
	}

	public void setCBJF(String in) {
		CBJF = in;
	}

	@Column(name = "XSSJ", length = 50, nullable = true)
	@IColumn(description="销售税金")
	private String XSSJ;

	public String getXSSJ() {
		return XSSJ;
	}

	public void setXSSJ(String in) {
		XSSJ = in;
	}

	@Column(name = "SJSJZC", length = 50, nullable = true)
	@IColumn(description="上缴上级支出")
	private String SJSJZC;

	public String getSJSJZC() {
		return SJSJZC;
	}

	public void setSJSJZC(String in) {
		SJSJZC = in;
	}

	@Column(name = "DFSDWBZ", length = 50, nullable = true)
	@IColumn(description="对附属单位补助")
	private String DFSDWBZ;

	public String getDFSDWBZ() {
		return DFSDWBZ;
	}

	public void setDFSDWBZ(String in) {
		DFSDWBZ = in;
	}

	@Column(name = "JZZCJJ", length = 50, nullable = true)
	@IColumn(description="结转自筹基建")
	private String JZZCJJ;

	public String getJZZCJJ() {
		return JZZCJJ;
	}

	public void setJZZCJJ(String in) {
		JZZCJJ = in;
	}

	@Column(name = "ZCHJ_1", length = 50, nullable = true)
	@IColumn(description="支出合计")
	private String ZCHJ_1;

	public String getZCHJ_1() {
		return ZCHJ_1;
	}

	public void setZCHJ_1(String in) {
		ZCHJ_1 = in;
	}

	@Column(name = "ZCBLZJ", length = 50, nullable = true)
	@IColumn(description="资产部类总计")
	private String ZCBLZJ;

	public String getZCBLZJ() {
		return ZCBLZJ;
	}

	public void setZCBLZJ(String in) {
		ZCBLZJ = in;
	}

	@Column(name = "JJKX", length = 50, nullable = true)
	@IColumn(description="借记款项")
	private String JJKX;

	public String getJJKX() {
		return JJKX;
	}

	public void setJJKX(String in) {
		JJKX = in;
	}

	@Column(name = "YFPJ", length = 50, nullable = true)
	@IColumn(description="应付票据")
	private String YFPJ;

	public String getYFPJ() {
		return YFPJ;
	}

	public void setYFPJ(String in) {
		YFPJ = in;
	}

	@Column(name = "YFZK_1", length = 50, nullable = true)
	@IColumn(description="应付账款")
	private String YFZK_1;

	public String getYFZK_1() {
		return YFZK_1;
	}

	public void setYFZK_1(String in) {
		YFZK_1 = in;
	}

	@Column(name = "YSZK_1", length = 50, nullable = true)
	@IColumn(description="预收账款")
	private String YSZK_1;

	public String getYSZK_1() {
		return YSZK_1;
	}

	public void setYSZK_1(String in) {
		YSZK_1 = in;
	}

	@Column(name = "QTYFK", length = 50, nullable = true)
	@IColumn(description="其他应付款")
	private String QTYFK;

	public String getQTYFK() {
		return QTYFK;
	}

	public void setQTYFK(String in) {
		QTYFK = in;
	}

	@Column(name = "YJYFK", length = 50, nullable = true)
	@IColumn(description="应缴预算款")
	private String YJYFK;

	public String getYJYFK() {
		return YJYFK;
	}

	public void setYJYFK(String in) {
		YJYFK = in;
	}

	@Column(name = "YJCZZHK", length = 50, nullable = true)
	@IColumn(description="应缴财政专户款")
	private String YJCZZHK;

	public String getYJCZZHK() {
		return YJCZZHK;
	}

	public void setYJCZZHK(String in) {
		YJCZZHK = in;
	}

	@Column(name = "YJSJ", length = 50, nullable = true)
	@IColumn(description="应交税金")
	private String YJSJ;

	public String getYJSJ() {
		return YJSJ;
	}

	public void setYJSJ(String in) {
		YJSJ = in;
	}

	@Column(name = "FZHJ", length = 50, nullable = true)
	@IColumn(description="负债合计")
	private String FZHJ;

	public String getFZHJ() {
		return FZHJ;
	}

	public void setFZHJ(String in) {
		FZHJ = in;
	}

	@Column(name = "SYJJ", length = 50, nullable = true)
	@IColumn(description="事业基金")
	private String SYJJ;

	public String getSYJJ() {
		return SYJJ;
	}

	public void setSYJJ(String in) {
		SYJJ = in;
	}

	@Column(name = "YBJJ", length = 50, nullable = true)
	@IColumn(description="一般基金")
	private String YBJJ;

	public String getYBJJ() {
		return YBJJ;
	}

	public void setYBJJ(String in) {
		YBJJ = in;
	}

	@Column(name = "TZJJ", length = 50, nullable = true)
	@IColumn(description="投资基金")
	private String TZJJ;

	public String getTZJJ() {
		return TZJJ;
	}

	public void setTZJJ(String in) {
		TZJJ = in;
	}

	@Column(name = "GDJJ", length = 50, nullable = true)
	@IColumn(description="固定基金")
	private String GDJJ;

	public String getGDJJ() {
		return GDJJ;
	}

	public void setGDJJ(String in) {
		GDJJ = in;
	}

	@Column(name = "ZYJJ", length = 50, nullable = true)
	@IColumn(description="专用基金")
	private String ZYJJ;

	public String getZYJJ() {
		return ZYJJ;
	}

	public void setZYJJ(String in) {
		ZYJJ = in;
	}

	@Column(name = "SYJY", length = 50, nullable = true)
	@IColumn(description="事业结余")
	private String SYJY;

	public String getSYJY() {
		return SYJY;
	}

	public void setSYJY(String in) {
		SYJY = in;
	}

	@Column(name = "JYJY", length = 50, nullable = true)
	@IColumn(description="经营结余")
	private String JYJY;

	public String getJYJY() {
		return JYJY;
	}

	public void setJYJY(String in) {
		JYJY = in;
	}

	@Column(name = "JZCHJ", length = 50, nullable = true)
	@IColumn(description="净资产合计")
	private String JZCHJ;

	public String getJZCHJ() {
		return JZCHJ;
	}

	public void setJZCHJ(String in) {
		JZCHJ = in;
	}

	@Column(name = "CZBZSR", length = 50, nullable = true)
	@IColumn(description="财政补助收入")
	private String CZBZSR;

	public String getCZBZSR() {
		return CZBZSR;
	}

	public void setCZBZSR(String in) {
		CZBZSR = in;
	}

	@Column(name = "SJBZSR", length = 50, nullable = true)
	@IColumn(description="上级补助收入")
	private String SJBZSR;

	public String getSJBZSR() {
		return SJBZSR;
	}

	public void setSJBZSR(String in) {
		SJBZSR = in;
	}

	@Column(name = "BRZK", length = 50, nullable = true)
	@IColumn(description="拨入专款")
	private String BRZK;

	public String getBRZK() {
		return BRZK;
	}

	public void setBRZK(String in) {
		BRZK = in;
	}

	@Column(name = "SYSR", length = 50, nullable = true)
	@IColumn(description="事业收入")
	private String SYSR;

	public String getSYSR() {
		return SYSR;
	}

	public void setSYSR(String in) {
		SYSR = in;
	}

	@Column(name = "JYSR", length = 50, nullable = true)
	@IColumn(description="经营收入")
	private String JYSR;

	public String getJYSR() {
		return JYSR;
	}

	public void setJYSR(String in) {
		JYSR = in;
	}

	@Column(name = "FSDWJK", length = 50, nullable = true)
	@IColumn(description="附属单位缴款")
	private String FSDWJK;

	public String getFSDWJK() {
		return FSDWJK;
	}

	public void setFSDWJK(String in) {
		FSDWJK = in;
	}

	@Column(name = "QTSR", length = 50, nullable = true)
	@IColumn(description="其他收入")
	private String QTSR;

	public String getQTSR() {
		return QTSR;
	}

	public void setQTSR(String in) {
		QTSR = in;
	}

	@Column(name = "SRHJ", length = 50, nullable = true)
	@IColumn(description="收入合计")
	private String SRHJ;

	public String getSRHJ() {
		return SRHJ;
	}

	public void setSRHJ(String in) {
		SRHJ = in;
	}

	@Column(name = "FZBLZJ", length = 50, nullable = true)
	@IColumn(description="负债部类总计")
	private String FZBLZJ;

	public String getFZBLZJ() {
		return FZBLZJ;
	}

	public void setFZBLZJ(String in) {
		FZBLZJ = in;
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

