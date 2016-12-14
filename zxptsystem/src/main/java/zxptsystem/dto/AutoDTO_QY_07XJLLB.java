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
@Table(name = "QY_07XJLLB")
@IEntity(description= "2007版现金流量表")
public class AutoDTO_QY_07XJLLB implements java.io.Serializable{

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

	@Column(name = "XSCPHTGLWSDDXJ", length = 50, nullable = true)
	@IColumn(description="销售商品和提供劳务收到的现金")
	private String XSCPHTGLWSDDXJ;

	public String getXSCPHTGLWSDDXJ() {
		return XSCPHTGLWSDDXJ;
	}

	public void setXSCPHTGLWSDDXJ(String in) {
		XSCPHTGLWSDDXJ = in;
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

	@Column(name = "SDQTYJYHDYGDXJ", length = 50, nullable = true)
	@IColumn(description="收到其他与经营活动有关的现金")
	private String SDQTYJYHDYGDXJ;

	public String getSDQTYJYHDYGDXJ() {
		return SDQTYJYHDYGDXJ;
	}

	public void setSDQTYJYHDYGDXJ(String in) {
		SDQTYJYHDYGDXJ = in;
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

	@Column(name = "GMSPJSLWZFDXJ", length = 50, nullable = true)
	@IColumn(description="购买商品、接受劳务支付的现金")
	private String GMSPJSLWZFDXJ;

	public String getGMSPJSLWZFDXJ() {
		return GMSPJSLWZFDXJ;
	}

	public void setGMSPJSLWZFDXJ(String in) {
		GMSPJSLWZFDXJ = in;
	}

	@Column(name = "ZFGZGYJWZGZFDXJ", length = 50, nullable = true)
	@IColumn(description="支付给职工以及为职工支付的现金")
	private String ZFGZGYJWZGZFDXJ;

	public String getZFGZGYJWZGZFDXJ() {
		return ZFGZGYJWZGZFDXJ;
	}

	public void setZFGZGYJWZGZFDXJ(String in) {
		ZFGZGYJWZGZFDXJ = in;
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

	@Column(name = "ZFQTYJYHDYGDXJ", length = 50, nullable = true)
	@IColumn(description="支付其他与经营活动有关的现金")
	private String ZFQTYJYHDYGDXJ;

	public String getZFQTYJYHDYGDXJ() {
		return ZFQTYJYHDYGDXJ;
	}

	public void setZFQTYJYHDYGDXJ(String in) {
		ZFQTYJYHDYGDXJ = in;
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

	@Column(name = "JYHDCSDXJLLJE", length = 50, nullable = true)
	@IColumn(description="经营活动产生的现金流量净额")
	private String JYHDCSDXJLLJE;

	public String getJYHDCSDXJLLJE() {
		return JYHDCSDXJLLJE;
	}

	public void setJYHDCSDXJLLJE(String in) {
		JYHDCSDXJLLJE = in;
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

	@Column(name = "CZGDZCWXZCHQTCQZCSSHDXJJE", length = 50, nullable = true)
	@IColumn(description="处置固定资产无形资产和其他长期资产所收回的现金净额")
	private String CZGDZCWXZCHQTCQZCSSHDXJJE;

	public String getCZGDZCWXZCHQTCQZCSSHDXJJE() {
		return CZGDZCWXZCHQTCQZCSSHDXJJE;
	}

	public void setCZGDZCWXZCHQTCQZCSSHDXJJE(String in) {
		CZGDZCWXZCHQTCQZCSSHDXJJE = in;
	}

	@Column(name = "CZZGSJQTYYDWSDDXJJE", length = 50, nullable = true)
	@IColumn(description="处置子公司及其他营业单位收到的现金净额")
	private String CZZGSJQTYYDWSDDXJJE;

	public String getCZZGSJQTYYDWSDDXJJE() {
		return CZZGSJQTYYDWSDDXJJE;
	}

	public void setCZZGSJQTYYDWSDDXJJE(String in) {
		CZZGSJQTYYDWSDDXJJE = in;
	}

	@Column(name = "SDQTYTZHDYGDXJ", length = 50, nullable = true)
	@IColumn(description="收到其他与投资活动有关的现金")
	private String SDQTYTZHDYGDXJ;

	public String getSDQTYTZHDYGDXJ() {
		return SDQTYTZHDYGDXJ;
	}

	public void setSDQTYTZHDYGDXJ(String in) {
		SDQTYTZHDYGDXJ = in;
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

	@Column(name = "GMGDZCWXZCHQTCQZCSZFDXJ", length = 50, nullable = true)
	@IColumn(description="购建固定资产无形资产和其他长期资产所支付的现金")
	private String GMGDZCWXZCHQTCQZCSZFDXJ;

	public String getGMGDZCWXZCHQTCQZCSZFDXJ() {
		return GMGDZCWXZCHQTCQZCSZFDXJ;
	}

	public void setGMGDZCWXZCHQTCQZCSZFDXJ(String in) {
		GMGDZCWXZCHQTCQZCSZFDXJ = in;
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

	@Column(name = "QDZGSJQTSYDWZFDXJJE", length = 50, nullable = true)
	@IColumn(description="取得子公司及其他营业单位支付的现金净额")
	private String QDZGSJQTSYDWZFDXJJE;

	public String getQDZGSJQTSYDWZFDXJJE() {
		return QDZGSJQTSYDWZFDXJJE;
	}

	public void setQDZGSJQTSYDWZFDXJJE(String in) {
		QDZGSJQTSYDWZFDXJJE = in;
	}

	@Column(name = "ZFQTYTZHDYGDXJ", length = 50, nullable = true)
	@IColumn(description="支付其他与投资活动有关的现金")
	private String ZFQTYTZHDYGDXJ;

	public String getZFQTYTZHDYGDXJ() {
		return ZFQTYTZHDYGDXJ;
	}

	public void setZFQTYTZHDYGDXJ(String in) {
		ZFQTYTZHDYGDXJ = in;
	}

	@Column(name = "TZHDXJLRXJ_1", length = 50, nullable = true)
	@IColumn(description="投资活动现金流出小计")
	private String TZHDXJLRXJ_1;

	public String getTZHDXJLRXJ_1() {
		return TZHDXJLRXJ_1;
	}

	public void setTZHDXJLRXJ_1(String in) {
		TZHDXJLRXJ_1 = in;
	}

	@Column(name = "TZHDCSDXJLLJE", length = 50, nullable = true)
	@IColumn(description="投资活动产生的现金流量净额")
	private String TZHDCSDXJLLJE;

	public String getTZHDCSDXJLLJE() {
		return TZHDCSDXJLLJE;
	}

	public void setTZHDCSDXJLLJE(String in) {
		TZHDCSDXJLLJE = in;
	}

	@Column(name = "XSTZSDDXJ", length = 50, nullable = true)
	@IColumn(description="吸收投资收到的现金")
	private String XSTZSDDXJ;

	public String getXSTZSDDXJ() {
		return XSTZSDDXJ;
	}

	public void setXSTZSDDXJ(String in) {
		XSTZSDDXJ = in;
	}

	@Column(name = "QDJKSDDXJ", length = 50, nullable = true)
	@IColumn(description="取得借款收到的现金")
	private String QDJKSDDXJ;

	public String getQDJKSDDXJ() {
		return QDJKSDDXJ;
	}

	public void setQDJKSDDXJ(String in) {
		QDJKSDDXJ = in;
	}

	@Column(name = "SDQTYCZHDYGDXJ", length = 50, nullable = true)
	@IColumn(description="收到其他与筹资活动有关的现金")
	private String SDQTYCZHDYGDXJ;

	public String getSDQTYCZHDYGDXJ() {
		return SDQTYCZHDYGDXJ;
	}

	public void setSDQTYCZHDYGDXJ(String in) {
		SDQTYCZHDYGDXJ = in;
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

	@Column(name = "FPGLLRHCFLXSZFDXJ", length = 50, nullable = true)
	@IColumn(description="分配股利、利润或偿付利息所支付的现金")
	private String FPGLLRHCFLXSZFDXJ;

	public String getFPGLLRHCFLXSZFDXJ() {
		return FPGLLRHCFLXSZFDXJ;
	}

	public void setFPGLLRHCFLXSZFDXJ(String in) {
		FPGLLRHCFLXSZFDXJ = in;
	}

	@Column(name = "ZFQTYCZHDYGDXJ", length = 50, nullable = true)
	@IColumn(description="支付其他与筹资活动有关的现金")
	private String ZFQTYCZHDYGDXJ;

	public String getZFQTYCZHDYGDXJ() {
		return ZFQTYCZHDYGDXJ;
	}

	public void setZFQTYCZHDYGDXJ(String in) {
		ZFQTYCZHDYGDXJ = in;
	}

	@Column(name = "CZHDXJLRXJ_1", length = 50, nullable = true)
	@IColumn(description="筹资活动现金流出小计")
	private String CZHDXJLRXJ_1;

	public String getCZHDXJLRXJ_1() {
		return CZHDXJLRXJ_1;
	}

	public void setCZHDXJLRXJ_1(String in) {
		CZHDXJLRXJ_1 = in;
	}

	@Column(name = "CZHDCSDXJLLJE", length = 50, nullable = true)
	@IColumn(description="筹集活动产生的现金流量净额")
	private String CZHDCSDXJLLJE;

	public String getCZHDCSDXJLLJE() {
		return CZHDCSDXJLLJE;
	}

	public void setCZHDCSDXJLLJE(String in) {
		CZHDCSDXJLLJE = in;
	}

	@Column(name = "SLBDDXJJXJDJWDYX", length = 50, nullable = true)
	@IColumn(description="汇率变动对现金及现金等价物的影响")
	private String SLBDDXJJXJDJWDYX;

	public String getSLBDDXJJXJDJWDYX() {
		return SLBDDXJJXJDJWDYX;
	}

	public void setSLBDDXJJXJDJWDYX(String in) {
		SLBDDXJJXJDJWDYX = in;
	}

	@Column(name = "XJJXJDJWJZEW", length = 50, nullable = true)
	@IColumn(description="现金及现金等价物净增加额(五)")
	private String XJJXJDJWJZEW;

	public String getXJJXJDJWJZEW() {
		return XJJXJDJWJZEW;
	}

	public void setXJJXJDJWJZEW(String in) {
		XJJXJDJWJZEW = in;
	}

	@Column(name = "QCXJJXJDJWYE", length = 50, nullable = true)
	@IColumn(description="期初现金及现金等价物余额")
	private String QCXJJXJDJWYE;

	public String getQCXJJXJDJWYE() {
		return QCXJJXJDJWYE;
	}

	public void setQCXJJXJDJWYE(String in) {
		QCXJJXJDJWYE = in;
	}

	@Column(name = "QMXJJXJDJWYEL", length = 50, nullable = true)
	@IColumn(description="期末现金及现金等价物余额(六)")
	private String QMXJJXJDJWYEL;

	public String getQMXJJXJDJWYEL() {
		return QMXJJXJDJWYEL;
	}

	public void setQMXJJXJDJWYEL(String in) {
		QMXJJXJDJWYEL = in;
	}

	@Column(name = "JLR_1", length = 50, nullable = true)
	@IColumn(description="净利润")
	private String JLR_1;

	public String getJLR_1() {
		return JLR_1;
	}

	public void setJLR_1(String in) {
		JLR_1 = in;
	}

	@Column(name = "ZCJZZB", length = 50, nullable = true)
	@IColumn(description="资产减值准备")
	private String ZCJZZB;

	public String getZCJZZB() {
		return ZCJZZB;
	}

	public void setZCJZZB(String in) {
		ZCJZZB = in;
	}

	@Column(name = "GDZCZJYQZCZHSCXSWZCZJ", length = 50, nullable = true)
	@IColumn(description="固定资产折旧、油气资产折耗、生产性生物资产折旧")
	private String GDZCZJYQZCZHSCXSWZCZJ;

	public String getGDZCZJYQZCZHSCXSWZCZJ() {
		return GDZCZJYQZCZHSCXSWZCZJ;
	}

	public void setGDZCZJYQZCZHSCXSWZCZJ(String in) {
		GDZCZJYQZCZHSCXSWZCZJ = in;
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

	@Column(name = "CZGDZCWXZCHQTCQZCDSS", length = 50, nullable = true)
	@IColumn(description="处置固定资产无形资产和其他长期资产的损失")
	private String CZGDZCWXZCHQTCQZCDSS;

	public String getCZGDZCWXZCHQTCQZCDSS() {
		return CZGDZCWXZCHQTCQZCDSS;
	}

	public void setCZGDZCWXZCHQTCQZCDSS(String in) {
		CZGDZCWXZCHQTCQZCDSS = in;
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

	@Column(name = "GYJZBDSS", length = 50, nullable = true)
	@IColumn(description="公允价值变动损失")
	private String GYJZBDSS;

	public String getGYJZBDSS() {
		return GYJZBDSS;
	}

	public void setGYJZBDSS(String in) {
		GYJZBDSS = in;
	}

	@Column(name = "CWFY_1", length = 50, nullable = true)
	@IColumn(description="财务费用")
	private String CWFY_1;

	public String getCWFY_1() {
		return CWFY_1;
	}

	public void setCWFY_1(String in) {
		CWFY_1 = in;
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

	@Column(name = "DYSDSZCJS", length = 50, nullable = true)
	@IColumn(description="递延所得税资产减少")
	private String DYSDSZCJS;

	public String getDYSDSZCJS() {
		return DYSDSZCJS;
	}

	public void setDYSDSZCJS(String in) {
		DYSDSZCJS = in;
	}

	@Column(name = "DYSDSFZZJ", length = 50, nullable = true)
	@IColumn(description="递延所得税负债增加")
	private String DYSDSFZZJ;

	public String getDYSDSFZZJ() {
		return DYSDSFZZJ;
	}

	public void setDYSDSFZZJ(String in) {
		DYSDSFZZJ = in;
	}

	@Column(name = "ZHDJS", length = 50, nullable = true)
	@IColumn(description="存货的减少")
	private String ZHDJS;

	public String getZHDJS() {
		return ZHDJS;
	}

	public void setZHDJS(String in) {
		ZHDJS = in;
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

	@Column(name = "JYXYFXMDZJ", length = 50, nullable = true)
	@IColumn(description="经营性应付项目的增加")
	private String JYXYFXMDZJ;

	public String getJYXYFXMDZJ() {
		return JYXYFXMDZJ;
	}

	public void setJYXYFXMDZJ(String in) {
		JYXYFXMDZJ = in;
	}

	@Column(name = "QT", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT;

	public String getQT() {
		return QT;
	}

	public void setQT(String in) {
		QT = in;
	}

	@Column(name = "JYHDCSDXJLLJE_1", length = 50, nullable = true)
	@IColumn(description="经营活动产生的现金流量净额")
	private String JYHDCSDXJLLJE_1;

	public String getJYHDCSDXJLLJE_1() {
		return JYHDCSDXJLLJE_1;
	}

	public void setJYHDCSDXJLLJE_1(String in) {
		JYHDCSDXJLLJE_1 = in;
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

	@Column(name = "YNNDQDKZHGSZQ", length = 50, nullable = true)
	@IColumn(description="一年内到期的可转换公司债券")
	private String YNNDQDKZHGSZQ;

	public String getYNNDQDKZHGSZQ() {
		return YNNDQDKZHGSZQ;
	}

	public void setYNNDQDKZHGSZQ(String in) {
		YNNDQDKZHGSZQ = in;
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

	@Column(name = "QT_1", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT_1;

	public String getQT_1() {
		return QT_1;
	}

	public void setQT_1(String in) {
		QT_1 = in;
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

	@Column(name = "XJJXJDJWJZZJE", length = 50, nullable = true)
	@IColumn(description="现金及现金等价物净增加额")
	private String XJJXJDJWJZZJE;

	public String getXJJXJDJWJZZJE() {
		return XJJXJDJWJZZJE;
	}

	public void setXJJXJDJWJZZJE(String in) {
		XJJXJDJWJZZJE = in;
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

