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
@Table(name = "QY_07ZCFZB")
@IEntity(description= "2007版资产负债表")
public class AutoDTO_QY_07ZCFZB implements java.io.Serializable{

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

	@Column(name = "HBZJ", length = 50, nullable = true)
	@IColumn(description="货币资金")
	private String HBZJ;

	public String getHBZJ() {
		return HBZJ;
	}

	public void setHBZJ(String in) {
		HBZJ = in;
	}

	@Column(name = "JYXJRZC", length = 50, nullable = true)
	@IColumn(description="交易性金融资产")
	private String JYXJRZC;

	public String getJYXJRZC() {
		return JYXJRZC;
	}

	public void setJYXJRZC(String in) {
		JYXJRZC = in;
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

	@Column(name = "YFZK_1", length = 50, nullable = true)
	@IColumn(description="预付账款")
	private String YFZK_1;

	public String getYFZK_1() {
		return YFZK_1;
	}

	public void setYFZK_1(String in) {
		YFZK_1 = in;
	}

	@Column(name = "YSLX", length = 50, nullable = true)
	@IColumn(description="应收利息")
	private String YSLX;

	public String getYSLX() {
		return YSLX;
	}

	public void setYSLX(String in) {
		YSLX = in;
	}

	@Column(name = "YSGL", length = 50, nullable = true)
	@IColumn(description="应收股利")
	private String YSGL;

	public String getYSGL() {
		return YSGL;
	}

	public void setYSGL(String in) {
		YSGL = in;
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

	@Column(name = "CH", length = 50, nullable = true)
	@IColumn(description="存货")
	private String CH;

	public String getCH() {
		return CH;
	}

	public void setCH(String in) {
		CH = in;
	}

	@Column(name = "YNNDQDFLDZC", length = 50, nullable = true)
	@IColumn(description="一年内到期的非流动资产")
	private String YNNDQDFLDZC;

	public String getYNNDQDFLDZC() {
		return YNNDQDFLDZC;
	}

	public void setYNNDQDFLDZC(String in) {
		YNNDQDFLDZC = in;
	}

	@Column(name = "QTLDZC", length = 50, nullable = true)
	@IColumn(description="其他流动资产")
	private String QTLDZC;

	public String getQTLDZC() {
		return QTLDZC;
	}

	public void setQTLDZC(String in) {
		QTLDZC = in;
	}

	@Column(name = "LDZCHJ", length = 50, nullable = true)
	@IColumn(description="流动资产合计")
	private String LDZCHJ;

	public String getLDZCHJ() {
		return LDZCHJ;
	}

	public void setLDZCHJ(String in) {
		LDZCHJ = in;
	}

	@Column(name = "KGCSDJRZC", length = 50, nullable = true)
	@IColumn(description="可供出售的金融资产")
	private String KGCSDJRZC;

	public String getKGCSDJRZC() {
		return KGCSDJRZC;
	}

	public void setKGCSDJRZC(String in) {
		KGCSDJRZC = in;
	}

	@Column(name = "CYZDQTZ", length = 50, nullable = true)
	@IColumn(description="持有至到期投资")
	private String CYZDQTZ;

	public String getCYZDQTZ() {
		return CYZDQTZ;
	}

	public void setCYZDQTZ(String in) {
		CYZDQTZ = in;
	}

	@Column(name = "CQGQTZ", length = 50, nullable = true)
	@IColumn(description="长期股权投资")
	private String CQGQTZ;

	public String getCQGQTZ() {
		return CQGQTZ;
	}

	public void setCQGQTZ(String in) {
		CQGQTZ = in;
	}

	@Column(name = "CQYSK", length = 50, nullable = true)
	@IColumn(description="长期应收款")
	private String CQYSK;

	public String getCQYSK() {
		return CQYSK;
	}

	public void setCQYSK(String in) {
		CQYSK = in;
	}

	@Column(name = "TZXFDC", length = 50, nullable = true)
	@IColumn(description="投资性房地产")
	private String TZXFDC;

	public String getTZXFDC() {
		return TZXFDC;
	}

	public void setTZXFDC(String in) {
		TZXFDC = in;
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

	@Column(name = "ZJGC", length = 50, nullable = true)
	@IColumn(description="在建工程")
	private String ZJGC;

	public String getZJGC() {
		return ZJGC;
	}

	public void setZJGC(String in) {
		ZJGC = in;
	}

	@Column(name = "GCWZ", length = 50, nullable = true)
	@IColumn(description="工程物资")
	private String GCWZ;

	public String getGCWZ() {
		return GCWZ;
	}

	public void setGCWZ(String in) {
		GCWZ = in;
	}

	@Column(name = "GDZCQL", length = 50, nullable = true)
	@IColumn(description="固定资产清理")
	private String GDZCQL;

	public String getGDZCQL() {
		return GDZCQL;
	}

	public void setGDZCQL(String in) {
		GDZCQL = in;
	}

	@Column(name = "SCXSWZC", length = 50, nullable = true)
	@IColumn(description="生产性生物资产")
	private String SCXSWZC;

	public String getSCXSWZC() {
		return SCXSWZC;
	}

	public void setSCXSWZC(String in) {
		SCXSWZC = in;
	}

	@Column(name = "YQZC", length = 50, nullable = true)
	@IColumn(description="油气资产")
	private String YQZC;

	public String getYQZC() {
		return YQZC;
	}

	public void setYQZC(String in) {
		YQZC = in;
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

	@Column(name = "KFZC", length = 50, nullable = true)
	@IColumn(description="开发支出")
	private String KFZC;

	public String getKFZC() {
		return KFZC;
	}

	public void setKFZC(String in) {
		KFZC = in;
	}

	@Column(name = "SY", length = 50, nullable = true)
	@IColumn(description="商誉")
	private String SY;

	public String getSY() {
		return SY;
	}

	public void setSY(String in) {
		SY = in;
	}

	@Column(name = "CQDTFY", length = 50, nullable = true)
	@IColumn(description="长期待摊费用")
	private String CQDTFY;

	public String getCQDTFY() {
		return CQDTFY;
	}

	public void setCQDTFY(String in) {
		CQDTFY = in;
	}

	@Column(name = "DYSDSZC", length = 50, nullable = true)
	@IColumn(description="递延所得税资产")
	private String DYSDSZC;

	public String getDYSDSZC() {
		return DYSDSZC;
	}

	public void setDYSDSZC(String in) {
		DYSDSZC = in;
	}

	@Column(name = "QTFLDZC", length = 50, nullable = true)
	@IColumn(description="其他非流动资产")
	private String QTFLDZC;

	public String getQTFLDZC() {
		return QTFLDZC;
	}

	public void setQTFLDZC(String in) {
		QTFLDZC = in;
	}

	@Column(name = "FLDZCHJ", length = 50, nullable = true)
	@IColumn(description="非流动资产合计")
	private String FLDZCHJ;

	public String getFLDZCHJ() {
		return FLDZCHJ;
	}

	public void setFLDZCHJ(String in) {
		FLDZCHJ = in;
	}

	@Column(name = "ZCZJ", length = 50, nullable = true)
	@IColumn(description="资产总计")
	private String ZCZJ;

	public String getZCZJ() {
		return ZCZJ;
	}

	public void setZCZJ(String in) {
		ZCZJ = in;
	}

	@Column(name = "DQJK", length = 50, nullable = true)
	@IColumn(description="短期借款")
	private String DQJK;

	public String getDQJK() {
		return DQJK;
	}

	public void setDQJK(String in) {
		DQJK = in;
	}

	@Column(name = "JYXJRFZ", length = 50, nullable = true)
	@IColumn(description="交易性金融负债")
	private String JYXJRFZ;

	public String getJYXJRFZ() {
		return JYXJRFZ;
	}

	public void setJYXJRFZ(String in) {
		JYXJRFZ = in;
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

	@Column(name = "YFZK_2", length = 50, nullable = true)
	@IColumn(description="应付账款")
	private String YFZK_2;

	public String getYFZK_2() {
		return YFZK_2;
	}

	public void setYFZK_2(String in) {
		YFZK_2 = in;
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

	@Column(name = "YFLX", length = 50, nullable = true)
	@IColumn(description="应付利息")
	private String YFLX;

	public String getYFLX() {
		return YFLX;
	}

	public void setYFLX(String in) {
		YFLX = in;
	}

	@Column(name = "YFZGXZ", length = 50, nullable = true)
	@IColumn(description="应付职工薪酬")
	private String YFZGXZ;

	public String getYFZGXZ() {
		return YFZGXZ;
	}

	public void setYFZGXZ(String in) {
		YFZGXZ = in;
	}

	@Column(name = "YJSF", length = 50, nullable = true)
	@IColumn(description="应交税费")
	private String YJSF;

	public String getYJSF() {
		return YJSF;
	}

	public void setYJSF(String in) {
		YJSF = in;
	}

	@Column(name = "YFGL", length = 50, nullable = true)
	@IColumn(description="应付股利")
	private String YFGL;

	public String getYFGL() {
		return YFGL;
	}

	public void setYFGL(String in) {
		YFGL = in;
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

	@Column(name = "YNNDQDFLDFZ", length = 50, nullable = true)
	@IColumn(description="一年内到期的非流动负债")
	private String YNNDQDFLDFZ;

	public String getYNNDQDFLDFZ() {
		return YNNDQDFLDFZ;
	}

	public void setYNNDQDFLDFZ(String in) {
		YNNDQDFLDFZ = in;
	}

	@Column(name = "QTLDFZ", length = 50, nullable = true)
	@IColumn(description="其他流动负债")
	private String QTLDFZ;

	public String getQTLDFZ() {
		return QTLDFZ;
	}

	public void setQTLDFZ(String in) {
		QTLDFZ = in;
	}

	@Column(name = "LDFZHJ", length = 50, nullable = true)
	@IColumn(description="流动负债合计")
	private String LDFZHJ;

	public String getLDFZHJ() {
		return LDFZHJ;
	}

	public void setLDFZHJ(String in) {
		LDFZHJ = in;
	}

	@Column(name = "CQJK", length = 50, nullable = true)
	@IColumn(description="长期借款")
	private String CQJK;

	public String getCQJK() {
		return CQJK;
	}

	public void setCQJK(String in) {
		CQJK = in;
	}

	@Column(name = "YFZQ", length = 50, nullable = true)
	@IColumn(description="应付债券")
	private String YFZQ;

	public String getYFZQ() {
		return YFZQ;
	}

	public void setYFZQ(String in) {
		YFZQ = in;
	}

	@Column(name = "CQYFK", length = 50, nullable = true)
	@IColumn(description="长期应付款")
	private String CQYFK;

	public String getCQYFK() {
		return CQYFK;
	}

	public void setCQYFK(String in) {
		CQYFK = in;
	}

	@Column(name = "ZXYFK", length = 50, nullable = true)
	@IColumn(description="专项应付款")
	private String ZXYFK;

	public String getZXYFK() {
		return ZXYFK;
	}

	public void setZXYFK(String in) {
		ZXYFK = in;
	}

	@Column(name = "YJFZ", length = 50, nullable = true)
	@IColumn(description="预计负债")
	private String YJFZ;

	public String getYJFZ() {
		return YJFZ;
	}

	public void setYJFZ(String in) {
		YJFZ = in;
	}

	@Column(name = "DYSDSFZ", length = 50, nullable = true)
	@IColumn(description="递延所得税负债")
	private String DYSDSFZ;

	public String getDYSDSFZ() {
		return DYSDSFZ;
	}

	public void setDYSDSFZ(String in) {
		DYSDSFZ = in;
	}

	@Column(name = "QTFLDFZ", length = 50, nullable = true)
	@IColumn(description="其他非流动负债")
	private String QTFLDFZ;

	public String getQTFLDFZ() {
		return QTFLDFZ;
	}

	public void setQTFLDFZ(String in) {
		QTFLDFZ = in;
	}

	@Column(name = "FLDFZHJ", length = 50, nullable = true)
	@IColumn(description="非流动负债合计")
	private String FLDFZHJ;

	public String getFLDFZHJ() {
		return FLDFZHJ;
	}

	public void setFLDFZHJ(String in) {
		FLDFZHJ = in;
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

	@Column(name = "SSZBHGB", length = 50, nullable = true)
	@IColumn(description="实收资本（或股本）")
	private String SSZBHGB;

	public String getSSZBHGB() {
		return SSZBHGB;
	}

	public void setSSZBHGB(String in) {
		SSZBHGB = in;
	}

	@Column(name = "ZBGJ", length = 50, nullable = true)
	@IColumn(description="资本公积")
	private String ZBGJ;

	public String getZBGJ() {
		return ZBGJ;
	}

	public void setZBGJ(String in) {
		ZBGJ = in;
	}

	@Column(name = "JKCG", length = 50, nullable = true)
	@IColumn(description="减：库存股")
	private String JKCG;

	public String getJKCG() {
		return JKCG;
	}

	public void setJKCG(String in) {
		JKCG = in;
	}

	@Column(name = "YYGJ", length = 50, nullable = true)
	@IColumn(description="盈余公积")
	private String YYGJ;

	public String getYYGJ() {
		return YYGJ;
	}

	public void setYYGJ(String in) {
		YYGJ = in;
	}

	@Column(name = "WFPLR", length = 50, nullable = true)
	@IColumn(description="未分配利润")
	private String WFPLR;

	public String getWFPLR() {
		return WFPLR;
	}

	public void setWFPLR(String in) {
		WFPLR = in;
	}

	@Column(name = "SYZQYHJ", length = 50, nullable = true)
	@IColumn(description="所有者权益合计")
	private String SYZQYHJ;

	public String getSYZQYHJ() {
		return SYZQYHJ;
	}

	public void setSYZQYHJ(String in) {
		SYZQYHJ = in;
	}

	@Column(name = "FZHSYZQYHJ", length = 50, nullable = true)
	@IColumn(description="负债和所有者权益合计")
	private String FZHSYZQYHJ;

	public String getFZHSYZQYHJ() {
		return FZHSYZQYHJ;
	}

	public void setFZHSYZQYHJ(String in) {
		FZHSYZQYHJ = in;
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

}

