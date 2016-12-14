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
@Table(name = "QY_02XJLLB")
@IEntity(description= "2002版现金流量表")
public class AutoDTO_QY_02XJLLB implements java.io.Serializable{

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

	@Column(name = "XSSPHT9795", length = 50, nullable = true)
	@IColumn(description="销售商品和提供劳务收到的现金")
	private String XSSPHT9795;

	public String getXSSPHT9795() {
		return XSSPHT9795;
	}

	public void setXSSPHT9795(String in) {
		XSSPHT9795 = in;
	}

	@Column(name = "SDDSFFH", length = 50, nullable = true)
	@IColumn(description="收到的税费返还")
	private String SDDSFFH;

	public String getSDDSFFH() {
		return SDDSFFH;
	}

	public void setSDDSFFH(String in) {
		SDDSFFH = in;
	}

	@Column(name = "SDDQTY9799", length = 50, nullable = true)
	@IColumn(description="收到的其他与经营活动有关的现金")
	private String SDDQTY9799;

	public String getSDDQTY9799() {
		return SDDQTY9799;
	}

	public void setSDDQTY9799(String in) {
		SDDQTY9799 = in;
	}

	@Column(name = "JYHDXJLRXJ", length = 50, nullable = true)
	@IColumn(description="经营活动现金流入小计")
	private String JYHDXJLRXJ;

	public String getJYHDXJLRXJ() {
		return JYHDXJLRXJ;
	}

	public void setJYHDXJLRXJ(String in) {
		JYHDXJLRXJ = in;
	}

	@Column(name = "GMSPJS9803", length = 50, nullable = true)
	@IColumn(description="购买商品、接受劳务支付的现金")
	private String GMSPJS9803;

	public String getGMSPJS9803() {
		return GMSPJS9803;
	}

	public void setGMSPJS9803(String in) {
		GMSPJS9803 = in;
	}

	@Column(name = "ZFGZGY9805", length = 50, nullable = true)
	@IColumn(description="支付给职工以及为职工支付的现金")
	private String ZFGZGY9805;

	public String getZFGZGY9805() {
		return ZFGZGY9805;
	}

	public void setZFGZGY9805(String in) {
		ZFGZGY9805 = in;
	}

	@Column(name = "ZFDGXSF", length = 50, nullable = true)
	@IColumn(description="支付的各项税费")
	private String ZFDGXSF;

	public String getZFDGXSF() {
		return ZFDGXSF;
	}

	public void setZFDGXSF(String in) {
		ZFDGXSF = in;
	}

	@Column(name = "ZFDQTY9809", length = 50, nullable = true)
	@IColumn(description="支付的其他与经营活动有关的现金")
	private String ZFDQTY9809;

	public String getZFDQTY9809() {
		return ZFDQTY9809;
	}

	public void setZFDQTY9809(String in) {
		ZFDQTY9809 = in;
	}

	@Column(name = "JYHDXJLCXJ", length = 50, nullable = true)
	@IColumn(description="经营活动现金流出小计")
	private String JYHDXJLCXJ;

	public String getJYHDXJLCXJ() {
		return JYHDXJLCXJ;
	}

	public void setJYHDXJLCXJ(String in) {
		JYHDXJLCXJ = in;
	}

	@Column(name = "XJJE9813_1", length = 50, nullable = true)
	@IColumn(description="经营活动产生的现金流量净额")
	private String XJJE9813_1;

	public String getXJJE9813_1() {
		return XJJE9813_1;
	}

	public void setXJJE9813_1(String in) {
		XJJE9813_1 = in;
	}

	@Column(name = "SHTZSSDDXJ", length = 50, nullable = true)
	@IColumn(description="收回投资所收到的现金")
	private String SHTZSSDDXJ;

	public String getSHTZSSDDXJ() {
		return SHTZSSDDXJ;
	}

	public void setSHTZSSDDXJ(String in) {
		SHTZSSDDXJ = in;
	}

	@Column(name = "QDTZSYSSDDXJ", length = 50, nullable = true)
	@IColumn(description="取得投资收益所收到的现金")
	private String QDTZSYSSDDXJ;

	public String getQDTZSYSSDDXJ() {
		return QDTZSYSSDDXJ;
	}

	public void setQDTZSYSSDDXJ(String in) {
		QDTZSYSSDDXJ = in;
	}

	@Column(name = "CZGDZC9819", length = 50, nullable = true)
	@IColumn(description="处置固定资产无形资产和其他长期资产所收回的现金净额")
	private String CZGDZC9819;

	public String getCZGDZC9819() {
		return CZGDZC9819;
	}

	public void setCZGDZC9819(String in) {
		CZGDZC9819 = in;
	}

	@Column(name = "SDDQTY9821", length = 50, nullable = true)
	@IColumn(description="收到的其他与投资活动有关的现金")
	private String SDDQTY9821;

	public String getSDDQTY9821() {
		return SDDQTY9821;
	}

	public void setSDDQTY9821(String in) {
		SDDQTY9821 = in;
	}

	@Column(name = "TZHDXJLRXJ", length = 50, nullable = true)
	@IColumn(description="投资活动现金流入小计")
	private String TZHDXJLRXJ;

	public String getTZHDXJLRXJ() {
		return TZHDXJLRXJ;
	}

	public void setTZHDXJLRXJ(String in) {
		TZHDXJLRXJ = in;
	}

	@Column(name = "GJGDZC9825", length = 50, nullable = true)
	@IColumn(description="购建固定资产无形资产和其他长期资产所支付的现金")
	private String GJGDZC9825;

	public String getGJGDZC9825() {
		return GJGDZC9825;
	}

	public void setGJGDZC9825(String in) {
		GJGDZC9825 = in;
	}

	@Column(name = "TZSZFDXJ", length = 50, nullable = true)
	@IColumn(description="投资所支付的现金")
	private String TZSZFDXJ;

	public String getTZSZFDXJ() {
		return TZSZFDXJ;
	}

	public void setTZSZFDXJ(String in) {
		TZSZFDXJ = in;
	}

	@Column(name = "ZFDQTY9829", length = 50, nullable = true)
	@IColumn(description="支付的其他与投资活动有关的现金")
	private String ZFDQTY9829;

	public String getZFDQTY9829() {
		return ZFDQTY9829;
	}

	public void setZFDQTY9829(String in) {
		ZFDQTY9829 = in;
	}

	@Column(name = "TZHDXJLCXJ", length = 50, nullable = true)
	@IColumn(description="投资活动现金流出小计")
	private String TZHDXJLCXJ;

	public String getTZHDXJLCXJ() {
		return TZHDXJLCXJ;
	}

	public void setTZHDXJLCXJ(String in) {
		TZHDXJLCXJ = in;
	}

	@Column(name = "TZHDCS9833", length = 50, nullable = true)
	@IColumn(description="投资活动产生的现金流量净额")
	private String TZHDCS9833;

	public String getTZHDCS9833() {
		return TZHDCS9833;
	}

	public void setTZHDCS9833(String in) {
		TZHDCS9833 = in;
	}

	@Column(name = "XSTZSSDDXJ", length = 50, nullable = true)
	@IColumn(description="吸收投资所收到的现金")
	private String XSTZSSDDXJ;

	public String getXSTZSSDDXJ() {
		return XSTZSSDDXJ;
	}

	public void setXSTZSSDDXJ(String in) {
		XSTZSSDDXJ = in;
	}

	@Column(name = "JKSSDDXJ", length = 50, nullable = true)
	@IColumn(description="借款所收到的现金")
	private String JKSSDDXJ;

	public String getJKSSDDXJ() {
		return JKSSDDXJ;
	}

	public void setJKSSDDXJ(String in) {
		JKSSDDXJ = in;
	}

	@Column(name = "SDDQTY9839", length = 50, nullable = true)
	@IColumn(description="收到的其他与筹资活动有关的现金")
	private String SDDQTY9839;

	public String getSDDQTY9839() {
		return SDDQTY9839;
	}

	public void setSDDQTY9839(String in) {
		SDDQTY9839 = in;
	}

	@Column(name = "CZHDXJLRXJ", length = 50, nullable = true)
	@IColumn(description="筹资活动现金流入小计")
	private String CZHDXJLRXJ;

	public String getCZHDXJLRXJ() {
		return CZHDXJLRXJ;
	}

	public void setCZHDXJLRXJ(String in) {
		CZHDXJLRXJ = in;
	}

	@Column(name = "CHZWSZFDXJ", length = 50, nullable = true)
	@IColumn(description="偿还债务所支付的现金")
	private String CHZWSZFDXJ;

	public String getCHZWSZFDXJ() {
		return CHZWSZFDXJ;
	}

	public void setCHZWSZFDXJ(String in) {
		CHZWSZFDXJ = in;
	}

	@Column(name = "FPGLLR9845", length = 50, nullable = true)
	@IColumn(description="分配股利、利润或偿付利息所支付的现金")
	private String FPGLLR9845;

	public String getFPGLLR9845() {
		return FPGLLR9845;
	}

	public void setFPGLLR9845(String in) {
		FPGLLR9845 = in;
	}

	@Column(name = "ZFDQTY9847", length = 50, nullable = true)
	@IColumn(description="支付的其他与筹资活动有关的现金")
	private String ZFDQTY9847;

	public String getZFDQTY9847() {
		return ZFDQTY9847;
	}

	public void setZFDQTY9847(String in) {
		ZFDQTY9847 = in;
	}

	@Column(name = "CZHDXJLCXJ", length = 50, nullable = true)
	@IColumn(description="筹资活动现金流出小计")
	private String CZHDXJLCXJ;

	public String getCZHDXJLCXJ() {
		return CZHDXJLCXJ;
	}

	public void setCZHDXJLCXJ(String in) {
		CZHDXJLCXJ = in;
	}

	@Column(name = "CZHDCS9851", length = 50, nullable = true)
	@IColumn(description="筹集活动产生的现金流量净额")
	private String CZHDCS9851;

	public String getCZHDCS9851() {
		return CZHDCS9851;
	}

	public void setCZHDCS9851(String in) {
		CZHDCS9851 = in;
	}

	@Column(name = "HLBDDXJDYX", length = 50, nullable = true)
	@IColumn(description="汇率变动对现金的影响")
	private String HLBDDXJDYX;

	public String getHLBDDXJDYX() {
		return HLBDDXJDYX;
	}

	public void setHLBDDXJDYX(String in) {
		HLBDDXJDYX = in;
	}

	@Column(name = "ZJE9855_1", length = 50, nullable = true)
	@IColumn(description="现金及现金等价物净增加额")
	private String ZJE9855_1;

	public String getZJE9855_1() {
		return ZJE9855_1;
	}

	public void setZJE9855_1(String in) {
		ZJE9855_1 = in;
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

	@Column(name = "JTDZCJZZB", length = 50, nullable = true)
	@IColumn(description="计提的资产减值准备")
	private String JTDZCJZZB;

	public String getJTDZCJZZB() {
		return JTDZCJZZB;
	}

	public void setJTDZCJZZB(String in) {
		JTDZCJZZB = in;
	}

	@Column(name = "GDZCZJ", length = 50, nullable = true)
	@IColumn(description="固定资产拆旧")
	private String GDZCZJ;

	public String getGDZCZJ() {
		return GDZCZJ;
	}

	public void setGDZCZJ(String in) {
		GDZCZJ = in;
	}

	@Column(name = "WXZCTX", length = 50, nullable = true)
	@IColumn(description="无形资产摊销")
	private String WXZCTX;

	public String getWXZCTX() {
		return WXZCTX;
	}

	public void setWXZCTX(String in) {
		WXZCTX = in;
	}

	@Column(name = "CQDTFYTX", length = 50, nullable = true)
	@IColumn(description="长期待摊费用摊销")
	private String CQDTFYTX;

	public String getCQDTFYTX() {
		return CQDTFYTX;
	}

	public void setCQDTFYTX(String in) {
		CQDTFYTX = in;
	}

	@Column(name = "DTFYJS", length = 50, nullable = true)
	@IColumn(description="待摊费用减少")
	private String DTFYJS;

	public String getDTFYJS() {
		return DTFYJS;
	}

	public void setDTFYJS(String in) {
		DTFYJS = in;
	}

	@Column(name = "YTFYZJ", length = 50, nullable = true)
	@IColumn(description="预提费用增加")
	private String YTFYZJ;

	public String getYTFYZJ() {
		return YTFYZJ;
	}

	public void setYTFYZJ(String in) {
		YTFYZJ = in;
	}

	@Column(name = "CZGDZC9871", length = 50, nullable = true)
	@IColumn(description="处置固定资产无形资产和其他长期资产的损失")
	private String CZGDZC9871;

	public String getCZGDZC9871() {
		return CZGDZC9871;
	}

	public void setCZGDZC9871(String in) {
		CZGDZC9871 = in;
	}

	@Column(name = "GDZCBFSS", length = 50, nullable = true)
	@IColumn(description="固定资产报废损失")
	private String GDZCBFSS;

	public String getGDZCBFSS() {
		return GDZCBFSS;
	}

	public void setGDZCBFSS(String in) {
		GDZCBFSS = in;
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

	@Column(name = "TZSS", length = 50, nullable = true)
	@IColumn(description="投资损失")
	private String TZSS;

	public String getTZSS() {
		return TZSS;
	}

	public void setTZSS(String in) {
		TZSS = in;
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

	@Column(name = "CHDJS", length = 50, nullable = true)
	@IColumn(description="存货的减少")
	private String CHDJS;

	public String getCHDJS() {
		return CHDJS;
	}

	public void setCHDJS(String in) {
		CHDJS = in;
	}

	@Column(name = "JYXYSXMDJS", length = 50, nullable = true)
	@IColumn(description="经营性应收项目的减少")
	private String JYXYSXMDJS;

	public String getJYXYSXMDJS() {
		return JYXYSXMDJS;
	}

	public void setJYXYSXMDJS(String in) {
		JYXYSXMDJS = in;
	}

	@Column(name = "JYXYSXMDZJ", length = 50, nullable = true)
	@IColumn(description="经营性应付项目的增加")
	private String JYXYSXMDZJ;

	public String getJYXYSXMDZJ() {
		return JYXYSXMDZJ;
	}

	public void setJYXYSXMDZJ(String in) {
		JYXYSXMDZJ = in;
	}

	@Column(name = "QT9915", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT9915;

	public String getQT9915() {
		return QT9915;
	}

	public void setQT9915(String in) {
		QT9915 = in;
	}

	@Column(name = "XJJE9813_2", length = 50, nullable = true)
	@IColumn(description="经营活动产生的现金流量净额")
	private String XJJE9813_2;

	public String getXJJE9813_2() {
		return XJJE9813_2;
	}

	public void setXJJE9813_2(String in) {
		XJJE9813_2 = in;
	}

	@Column(name = "ZWZWZB", length = 50, nullable = true)
	@IColumn(description="债务转为资本")
	private String ZWZWZB;

	public String getZWZWZB() {
		return ZWZWZB;
	}

	public void setZWZWZB(String in) {
		ZWZWZB = in;
	}

	@Column(name = "YNNDQD9893", length = 50, nullable = true)
	@IColumn(description="一年内到期的可转换公司债券")
	private String YNNDQD9893;

	public String getYNNDQD9893() {
		return YNNDQD9893;
	}

	public void setYNNDQD9893(String in) {
		YNNDQD9893 = in;
	}

	@Column(name = "RZZRGDZC", length = 50, nullable = true)
	@IColumn(description="融资租入固定资产")
	private String RZZRGDZC;

	public String getRZZRGDZC() {
		return RZZRGDZC;
	}

	public void setRZZRGDZC(String in) {
		RZZRGDZC = in;
	}

	@Column(name = "QT9897", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT9897;

	public String getQT9897() {
		return QT9897;
	}

	public void setQT9897(String in) {
		QT9897 = in;
	}

	@Column(name = "XJDQMYE", length = 50, nullable = true)
	@IColumn(description="现金的期末余额")
	private String XJDQMYE;

	public String getXJDQMYE() {
		return XJDQMYE;
	}

	public void setXJDQMYE(String in) {
		XJDQMYE = in;
	}

	@Column(name = "XJDQCYE", length = 50, nullable = true)
	@IColumn(description="现金的期初余额")
	private String XJDQCYE;

	public String getXJDQCYE() {
		return XJDQCYE;
	}

	public void setXJDQCYE(String in) {
		XJDQCYE = in;
	}

	@Column(name = "XJDJWDQMYE", length = 50, nullable = true)
	@IColumn(description="现金等价物的期末余额")
	private String XJDJWDQMYE;

	public String getXJDJWDQMYE() {
		return XJDJWDQMYE;
	}

	public void setXJDJWDQMYE(String in) {
		XJDJWDQMYE = in;
	}

	@Column(name = "XJDJWDQCYE", length = 50, nullable = true)
	@IColumn(description="现金等价物的期初余额")
	private String XJDJWDQCYE;

	public String getXJDJWDQCYE() {
		return XJDJWDQCYE;
	}

	public void setXJDJWDQCYE(String in) {
		XJDJWDQCYE = in;
	}

	@Column(name = "ZJE9885_2", length = 50, nullable = true)
	@IColumn(description="现金及现金等价物净增加额")
	private String ZJE9885_2;

	public String getZJE9885_2() {
		return ZJE9885_2;
	}

	public void setZJE9885_2(String in) {
		ZJE9885_2 = in;
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

