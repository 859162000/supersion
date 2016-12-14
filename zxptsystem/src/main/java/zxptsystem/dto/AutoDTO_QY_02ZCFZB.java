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
@Table(name = "QY_02ZCFZB")
@IEntity(description= "2002版资产负债表")
public class AutoDTO_QY_02ZCFZB implements java.io.Serializable{

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

	@Column(name = "DQTZ", length = 50, nullable = true)
	@IColumn(description="短期投资")
	private String DQTZ;

	public String getDQTZ() {
		return DQTZ;
	}

	public void setDQTZ(String in) {
		DQTZ = in;
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

	@Column(name = "YSGL", length = 50, nullable = true)
	@IColumn(description="应收股利")
	private String YSGL;

	public String getYSGL() {
		return YSGL;
	}

	public void setYSGL(String in) {
		YSGL = in;
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

	@Column(name = "YSZK", length = 50, nullable = true)
	@IColumn(description="应收账款")
	private String YSZK;

	public String getYSZK() {
		return YSZK;
	}

	public void setYSZK(String in) {
		YSZK = in;
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

	@Column(name = "YFZK", length = 50, nullable = true)
	@IColumn(description="预付账款")
	private String YFZK;

	public String getYFZK() {
		return YFZK;
	}

	public void setYFZK(String in) {
		YFZK = in;
	}

	@Column(name = "QHBZJ", length = 50, nullable = true)
	@IColumn(description="期货保证金")
	private String QHBZJ;

	public String getQHBZJ() {
		return QHBZJ;
	}

	public void setQHBZJ(String in) {
		QHBZJ = in;
	}

	@Column(name = "YSBTK", length = 50, nullable = true)
	@IColumn(description="应收补贴款")
	private String YSBTK;

	public String getYSBTK() {
		return YSBTK;
	}

	public void setYSBTK(String in) {
		YSBTK = in;
	}

	@Column(name = "YSCKTS", length = 50, nullable = true)
	@IColumn(description="应收出口退税")
	private String YSCKTS;

	public String getYSCKTS() {
		return YSCKTS;
	}

	public void setYSCKTS(String in) {
		YSCKTS = in;
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

	@Column(name = "CHYCL", length = 50, nullable = true)
	@IColumn(description="存货原材料")
	private String CHYCL;

	public String getCHYCL() {
		return CHYCL;
	}

	public void setCHYCL(String in) {
		CHYCL = in;
	}

	@Column(name = "CHCCP", length = 50, nullable = true)
	@IColumn(description="存货产成品")
	private String CHCCP;

	public String getCHCCP() {
		return CHCCP;
	}

	public void setCHCCP(String in) {
		CHCCP = in;
	}

	@Column(name = "DTFY", length = 50, nullable = true)
	@IColumn(description="待摊费用")
	private String DTFY;

	public String getDTFY() {
		return DTFY;
	}

	public void setDTFY(String in) {
		DTFY = in;
	}

	@Column(name = "DCLLDZCJSS", length = 50, nullable = true)
	@IColumn(description="待处理流动资产净损失")
	private String DCLLDZCJSS;

	public String getDCLLDZCJSS() {
		return DCLLDZCJSS;
	}

	public void setDCLLDZCJSS(String in) {
		DCLLDZCJSS = in;
	}

	@Column(name = "YNNDQ9533", length = 50, nullable = true)
	@IColumn(description="一年内到期的长期债权投资")
	private String YNNDQ9533;

	public String getYNNDQ9533() {
		return YNNDQ9533;
	}

	public void setYNNDQ9533(String in) {
		YNNDQ9533 = in;
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

	@Column(name = "CQTZ", length = 50, nullable = true)
	@IColumn(description="长期投资")
	private String CQTZ;

	public String getCQTZ() {
		return CQTZ;
	}

	public void setCQTZ(String in) {
		CQTZ = in;
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

	@Column(name = "CQZQTZ", length = 50, nullable = true)
	@IColumn(description="长期债权投资")
	private String CQZQTZ;

	public String getCQZQTZ() {
		return CQZQTZ;
	}

	public void setCQZQTZ(String in) {
		CQZQTZ = in;
	}

	@Column(name = "HBJC", length = 50, nullable = true)
	@IColumn(description="合并价差")
	private String HBJC;

	public String getHBJC() {
		return HBJC;
	}

	public void setHBJC(String in) {
		HBJC = in;
	}

	@Column(name = "CQTZHJ", length = 50, nullable = true)
	@IColumn(description="长期投资合计")
	private String CQTZHJ;

	public String getCQTZHJ() {
		return CQTZHJ;
	}

	public void setCQTZHJ(String in) {
		CQTZHJ = in;
	}

	@Column(name = "GDZCYJ", length = 50, nullable = true)
	@IColumn(description="固定资产原价")
	private String GDZCYJ;

	public String getGDZCYJ() {
		return GDZCYJ;
	}

	public void setGDZCYJ(String in) {
		GDZCYJ = in;
	}

	@Column(name = "LJZJ", length = 50, nullable = true)
	@IColumn(description="累计折旧")
	private String LJZJ;

	public String getLJZJ() {
		return LJZJ;
	}

	public void setLJZJ(String in) {
		LJZJ = in;
	}

	@Column(name = "GDZCJZ", length = 50, nullable = true)
	@IColumn(description="固定资产净值")
	private String GDZCJZ;

	public String getGDZCJZ() {
		return GDZCJZ;
	}

	public void setGDZCJZ(String in) {
		GDZCJZ = in;
	}

	@Column(name = "GDZCZJZZB", length = 50, nullable = true)
	@IColumn(description="固定资产值减值准备")
	private String GDZCZJZZB;

	public String getGDZCZJZZB() {
		return GDZCZJZZB;
	}

	public void setGDZCZJZZB(String in) {
		GDZCZJZZB = in;
	}

	@Column(name = "GDZCJE", length = 50, nullable = true)
	@IColumn(description="固定资产净额")
	private String GDZCJE;

	public String getGDZCJE() {
		return GDZCJE;
	}

	public void setGDZCJE(String in) {
		GDZCJE = in;
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

	@Column(name = "GCWZ", length = 50, nullable = true)
	@IColumn(description="工程物资")
	private String GCWZ;

	public String getGCWZ() {
		return GCWZ;
	}

	public void setGCWZ(String in) {
		GCWZ = in;
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

	@Column(name = "DCLGDZCJSS", length = 50, nullable = true)
	@IColumn(description="待处理固定资产净损失")
	private String DCLGDZCJSS;

	public String getDCLGDZCJSS() {
		return DCLGDZCJSS;
	}

	public void setDCLGDZCJSS(String in) {
		DCLGDZCJSS = in;
	}

	@Column(name = "GDZCHJ", length = 50, nullable = true)
	@IColumn(description="固定资产合计")
	private String GDZCHJ;

	public String getGDZCHJ() {
		return GDZCHJ;
	}

	public void setGDZCHJ(String in) {
		GDZCHJ = in;
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

	@Column(name = "WXZCTDSYQ", length = 50, nullable = true)
	@IColumn(description="无形资产土地使用权")
	private String WXZCTDSYQ;

	public String getWXZCTDSYQ() {
		return WXZCTDSYQ;
	}

	public void setWXZCTDSYQ(String in) {
		WXZCTDSYQ = in;
	}

	@Column(name = "DYZC", length = 50, nullable = true)
	@IColumn(description="递延资产")
	private String DYZC;

	public String getDYZC() {
		return DYZC;
	}

	public void setDYZC(String in) {
		DYZC = in;
	}

	@Column(name = "DYZCGDZCXL", length = 50, nullable = true)
	@IColumn(description="递延资产固定资产修理")
	private String DYZCGDZCXL;

	public String getDYZCGDZCXL() {
		return DYZCGDZCXL;
	}

	public void setDYZCGDZCXL(String in) {
		DYZCGDZCXL = in;
	}

	@Column(name = "DYZCGD9577", length = 50, nullable = true)
	@IColumn(description="递延资产固定资产改良支出")
	private String DYZCGD9577;

	public String getDYZCGD9577() {
		return DYZCGD9577;
	}

	public void setDYZCGD9577(String in) {
		DYZCGD9577 = in;
	}

	@Column(name = "QTCQZC", length = 50, nullable = true)
	@IColumn(description="其他长期资产")
	private String QTCQZC;

	public String getQTCQZC() {
		return QTCQZC;
	}

	public void setQTCQZC(String in) {
		QTCQZC = in;
	}

	@Column(name = "QTCQZC9581", length = 50, nullable = true)
	@IColumn(description="其他长期资产特准储备物资")
	private String QTCQZC9581;

	public String getQTCQZC9581() {
		return QTCQZC9581;
	}

	public void setQTCQZC9581(String in) {
		QTCQZC9581 = in;
	}

	@Column(name = "WXJQTZCHJ", length = 50, nullable = true)
	@IColumn(description="无形及其他资产合计")
	private String WXJQTZCHJ;

	public String getWXJQTZCHJ() {
		return WXJQTZCHJ;
	}

	public void setWXJQTZCHJ(String in) {
		WXJQTZCHJ = in;
	}

	@Column(name = "DYSKJX", length = 50, nullable = true)
	@IColumn(description="递延税款借项")
	private String DYSKJX;

	public String getDYSKJX() {
		return DYSKJX;
	}

	public void setDYSKJX(String in) {
		DYSKJX = in;
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

	@Column(name = "YFPJ", length = 50, nullable = true)
	@IColumn(description="应付票据")
	private String YFPJ;

	public String getYFPJ() {
		return YFPJ;
	}

	public void setYFPJ(String in) {
		YFPJ = in;
	}

	@Column(name = "YIFZK", length = 50, nullable = true)
	@IColumn(description="应付账款")
	private String YIFZK;

	public String getYIFZK() {
		return YIFZK;
	}

	public void setYIFZK(String in) {
		YIFZK = in;
	}

	@Column(name = "YUSZK", length = 50, nullable = true)
	@IColumn(description="预收账款")
	private String YUSZK;

	public String getYUSZK() {
		return YUSZK;
	}

	public void setYUSZK(String in) {
		YUSZK = in;
	}

	@Column(name = "YFGZ", length = 50, nullable = true)
	@IColumn(description="应付工资")
	private String YFGZ;

	public String getYFGZ() {
		return YFGZ;
	}

	public void setYFGZ(String in) {
		YFGZ = in;
	}

	@Column(name = "YFFLF", length = 50, nullable = true)
	@IColumn(description="应付福利费")
	private String YFFLF;

	public String getYFFLF() {
		return YFFLF;
	}

	public void setYFFLF(String in) {
		YFFLF = in;
	}

	@Column(name = "YFLR", length = 50, nullable = true)
	@IColumn(description="应付利润")
	private String YFLR;

	public String getYFLR() {
		return YFLR;
	}

	public void setYFLR(String in) {
		YFLR = in;
	}

	@Column(name = "YFSJ", length = 50, nullable = true)
	@IColumn(description="应交税金")
	private String YFSJ;

	public String getYFSJ() {
		return YFSJ;
	}

	public void setYFSJ(String in) {
		YFSJ = in;
	}

	@Column(name = "QTYJK", length = 50, nullable = true)
	@IColumn(description="其他应交款")
	private String QTYJK;

	public String getQTYJK() {
		return QTYJK;
	}

	public void setQTYJK(String in) {
		QTYJK = in;
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

	@Column(name = "YTFY", length = 50, nullable = true)
	@IColumn(description="预提费用")
	private String YTFY;

	public String getYTFY() {
		return YTFY;
	}

	public void setYTFY(String in) {
		YTFY = in;
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

	@Column(name = "YNNDQDCQFZ", length = 50, nullable = true)
	@IColumn(description="一年内到期的长期负债")
	private String YNNDQDCQFZ;

	public String getYNNDQDCQFZ() {
		return YNNDQDCQFZ;
	}

	public void setYNNDQDCQFZ(String in) {
		YNNDQDCQFZ = in;
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

	@Column(name = "YFJJ", length = 50, nullable = true)
	@IColumn(description="应付债券")
	private String YFJJ;

	public String getYFJJ() {
		return YFJJ;
	}

	public void setYFJJ(String in) {
		YFJJ = in;
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

	@Column(name = "QTCQFZ", length = 50, nullable = true)
	@IColumn(description="其他长期负债")
	private String QTCQFZ;

	public String getQTCQFZ() {
		return QTCQFZ;
	}

	public void setQTCQFZ(String in) {
		QTCQFZ = in;
	}

	@Column(name = "QTCQFZ9629", length = 50, nullable = true)
	@IColumn(description="其他长期负债特准储备基金")
	private String QTCQFZ9629;

	public String getQTCQFZ9629() {
		return QTCQFZ9629;
	}

	public void setQTCQFZ9629(String in) {
		QTCQFZ9629 = in;
	}

	@Column(name = "CQFZHJ", length = 50, nullable = true)
	@IColumn(description="长期负债合计")
	private String CQFZHJ;

	public String getCQFZHJ() {
		return CQFZHJ;
	}

	public void setCQFZHJ(String in) {
		CQFZHJ = in;
	}

	@Column(name = "DYSKDX", length = 50, nullable = true)
	@IColumn(description="递延税款贷项")
	private String DYSKDX;

	public String getDYSKDX() {
		return DYSKDX;
	}

	public void setDYSKDX(String in) {
		DYSKDX = in;
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

	@Column(name = "SSGDQY", length = 50, nullable = true)
	@IColumn(description="少数股东权益")
	private String SSGDQY;

	public String getSSGDQY() {
		return SSGDQY;
	}

	public void setSSGDQY(String in) {
		SSGDQY = in;
	}

	@Column(name = "SSZB", length = 50, nullable = true)
	@IColumn(description="实收资本")
	private String SSZB;

	public String getSSZB() {
		return SSZB;
	}

	public void setSSZB(String in) {
		SSZB = in;
	}

	@Column(name = "GJZB", length = 50, nullable = true)
	@IColumn(description="国家资本")
	private String GJZB;

	public String getGJZB() {
		return GJZB;
	}

	public void setGJZB(String in) {
		GJZB = in;
	}

	@Column(name = "JTZB", length = 50, nullable = true)
	@IColumn(description="集体资本")
	private String JTZB;

	public String getJTZB() {
		return JTZB;
	}

	public void setJTZB(String in) {
		JTZB = in;
	}

	@Column(name = "FRZB", length = 50, nullable = true)
	@IColumn(description="法人资本")
	private String FRZB;

	public String getFRZB() {
		return FRZB;
	}

	public void setFRZB(String in) {
		FRZB = in;
	}

	@Column(name = "FRZBGJFRZB", length = 50, nullable = true)
	@IColumn(description="法人资本国有法人资本")
	private String FRZBGJFRZB;

	public String getFRZBGJFRZB() {
		return FRZBGJFRZB;
	}

	public void setFRZBGJFRZB(String in) {
		FRZBGJFRZB = in;
	}

	@Column(name = "FRZBJTFRZB", length = 50, nullable = true)
	@IColumn(description="法人资本集体法人资本")
	private String FRZBJTFRZB;

	public String getFRZBJTFRZB() {
		return FRZBJTFRZB;
	}

	public void setFRZBJTFRZB(String in) {
		FRZBJTFRZB = in;
	}

	@Column(name = "GRZB", length = 50, nullable = true)
	@IColumn(description="个人资本")
	private String GRZB;

	public String getGRZB() {
		return GRZB;
	}

	public void setGRZB(String in) {
		GRZB = in;
	}

	@Column(name = "WSZB", length = 50, nullable = true)
	@IColumn(description="外商资本")
	private String WSZB;

	public String getWSZB() {
		return WSZB;
	}

	public void setWSZB(String in) {
		WSZB = in;
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

	@Column(name = "YYGJ", length = 50, nullable = true)
	@IColumn(description="盈余公积")
	private String YYGJ;

	public String getYYGJ() {
		return YYGJ;
	}

	public void setYYGJ(String in) {
		YYGJ = in;
	}

	@Column(name = "YYGJFDYYGJ", length = 50, nullable = true)
	@IColumn(description="盈余公积法定盈余公积")
	private String YYGJFDYYGJ;

	public String getYYGJFDYYGJ() {
		return YYGJFDYYGJ;
	}

	public void setYYGJFDYYGJ(String in) {
		YYGJFDYYGJ = in;
	}

	@Column(name = "YYGJGJJ", length = 50, nullable = true)
	@IColumn(description="盈余公积公益金")
	private String YYGJGJJ;

	public String getYYGJGJJ() {
		return YYGJGJJ;
	}

	public void setYYGJGJJ(String in) {
		YYGJGJJ = in;
	}

	@Column(name = "YYGJBCLDZB", length = 50, nullable = true)
	@IColumn(description="盈余公积补充流动资本")
	private String YYGJBCLDZB;

	public String getYYGJBCLDZB() {
		return YYGJBCLDZB;
	}

	public void setYYGJBCLDZB(String in) {
		YYGJBCLDZB = in;
	}

	@Column(name = "WQRDTZSS", length = 50, nullable = true)
	@IColumn(description="未确认的投资损失")
	private String WQRDTZSS;

	public String getWQRDTZSS() {
		return WQRDTZSS;
	}

	public void setWQRDTZSS(String in) {
		WQRDTZSS = in;
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

	@Column(name = "WBBBZSCE", length = 50, nullable = true)
	@IColumn(description="外币报表折算差额")
	private String WBBBZSCE;

	public String getWBBBZSCE() {
		return WBBBZSCE;
	}

	public void setWBBBZSCE(String in) {
		WBBBZSCE = in;
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
	@IColumn(description="负债和所有者权益总计")
	private String FZHSYZQYHJ;

	public String getFZHSYZQYHJ() {
		return FZHSYZQYHJ;
	}

	public void setFZHSYZQYHJ(String in) {
		FZHSYZQYHJ = in;
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

