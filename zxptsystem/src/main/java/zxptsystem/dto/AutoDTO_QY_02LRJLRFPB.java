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
@Table(name = "QY_02LRJLRFPB")
@IEntity(description= "2002版利润及利润分配表")
public class AutoDTO_QY_02LRJLRFPB implements java.io.Serializable{

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

	@Column(name = "ZYYWSR", length = 50, nullable = true)
	@IColumn(description="主营业务收入")
	private String ZYYWSR;

	public String getZYYWSR() {
		return ZYYWSR;
	}

	public void setZYYWSR(String in) {
		ZYYWSR = in;
	}

	@Column(name = "ZYYWSR9677", length = 50, nullable = true)
	@IColumn(description="主营业务收入出口产品销售收入")
	private String ZYYWSR9677;

	public String getZYYWSR9677() {
		return ZYYWSR9677;
	}

	public void setZYYWSR9677(String in) {
		ZYYWSR9677 = in;
	}

	@Column(name = "ZYYWSR9679", length = 50, nullable = true)
	@IColumn(description="主营业务收入进口产品销售收入")
	private String ZYYWSR9679;

	public String getZYYWSR9679() {
		return ZYYWSR9679;
	}

	public void setZYYWSR9679(String in) {
		ZYYWSR9679 = in;
	}

	@Column(name = "ZKYCR", length = 50, nullable = true)
	@IColumn(description="折扣与拆让")
	private String ZKYCR;

	public String getZKYCR() {
		return ZKYCR;
	}

	public void setZKYCR(String in) {
		ZKYCR = in;
	}

	@Column(name = "ZYYWSRJE", length = 50, nullable = true)
	@IColumn(description="主营业务收入净额")
	private String ZYYWSRJE;

	public String getZYYWSRJE() {
		return ZYYWSRJE;
	}

	public void setZYYWSRJE(String in) {
		ZYYWSRJE = in;
	}

	@Column(name = "ZYYWCB", length = 50, nullable = true)
	@IColumn(description="主营业务成本")
	private String ZYYWCB;

	public String getZYYWCB() {
		return ZYYWCB;
	}

	public void setZYYWCB(String in) {
		ZYYWCB = in;
	}

	@Column(name = "ZYYWCB9687", length = 50, nullable = true)
	@IColumn(description="主营业务成本出口产品销售成本")
	private String ZYYWCB9687;

	public String getZYYWCB9687() {
		return ZYYWCB9687;
	}

	public void setZYYWCB9687(String in) {
		ZYYWCB9687 = in;
	}

	@Column(name = "ZYYWSJJFJ", length = 50, nullable = true)
	@IColumn(description="主营业务税金及附加")
	private String ZYYWSJJFJ;

	public String getZYYWSJJFJ() {
		return ZYYWSJJFJ;
	}

	public void setZYYWSJJFJ(String in) {
		ZYYWSJJFJ = in;
	}

	@Column(name = "JYFY", length = 50, nullable = true)
	@IColumn(description="经营费用")
	private String JYFY;

	public String getJYFY() {
		return JYFY;
	}

	public void setJYFY(String in) {
		JYFY = in;
	}

	@Column(name = "QT9691", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT9691;

	public String getQT9691() {
		return QT9691;
	}

	public void setQT9691(String in) {
		QT9691 = in;
	}

	@Column(name = "DYSY", length = 50, nullable = true)
	@IColumn(description="递延收益")
	private String DYSY;

	public String getDYSY() {
		return DYSY;
	}

	public void setDYSY(String in) {
		DYSY = in;
	}

	@Column(name = "DGDXSR", length = 50, nullable = true)
	@IColumn(description="代购代销收入")
	private String DGDXSR;

	public String getDGDXSR() {
		return DGDXSR;
	}

	public void setDGDXSR(String in) {
		DGDXSR = in;
	}

	@Column(name = "QT9699", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT9699;

	public String getQT9699() {
		return QT9699;
	}

	public void setQT9699(String in) {
		QT9699 = in;
	}

	@Column(name = "ZYYWLR", length = 50, nullable = true)
	@IColumn(description="主营业务利润")
	private String ZYYWLR;

	public String getZYYWLR() {
		return ZYYWLR;
	}

	public void setZYYWLR(String in) {
		ZYYWLR = in;
	}

	@Column(name = "QTYWLR", length = 50, nullable = true)
	@IColumn(description="其他业务利润")
	private String QTYWLR;

	public String getQTYWLR() {
		return QTYWLR;
	}

	public void setQTYWLR(String in) {
		QTYWLR = in;
	}

	@Column(name = "YYFY", length = 50, nullable = true)
	@IColumn(description="营业费用")
	private String YYFY;

	public String getYYFY() {
		return YYFY;
	}

	public void setYYFY(String in) {
		YYFY = in;
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

	@Column(name = "QT9907", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT9907;

	public String getQT9907() {
		return QT9907;
	}

	public void setQT9907(String in) {
		QT9907 = in;
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

	@Column(name = "TZSY", length = 50, nullable = true)
	@IColumn(description="投资收益")
	private String TZSY;

	public String getTZSY() {
		return TZSY;
	}

	public void setTZSY(String in) {
		TZSY = in;
	}

	@Column(name = "QHSY", length = 50, nullable = true)
	@IColumn(description="期货收益")
	private String QHSY;

	public String getQHSY() {
		return QHSY;
	}

	public void setQHSY(String in) {
		QHSY = in;
	}

	@Column(name = "BTSR", length = 50, nullable = true)
	@IColumn(description="补贴收入")
	private String BTSR;

	public String getBTSR() {
		return BTSR;
	}

	public void setBTSR(String in) {
		BTSR = in;
	}

	@Column(name = "BTSRBT9719", length = 50, nullable = true)
	@IColumn(description="补贴收入补贴前亏损的企业补贴收入")
	private String BTSRBT9719;

	public String getBTSRBT9719() {
		return BTSRBT9719;
	}

	public void setBTSRBT9719(String in) {
		BTSRBT9719 = in;
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

	@Column(name = "YYWSRC9723", length = 50, nullable = true)
	@IColumn(description="营业外收入处置固定资产净收益")
	private String YYWSRC9723;

	public String getYYWSRC9723() {
		return YYWSRC9723;
	}

	public void setYYWSRC9723(String in) {
		YYWSRC9723 = in;
	}

	@Column(name = "YYWSRF9725", length = 50, nullable = true)
	@IColumn(description="营业外收入非货币性交易收益")
	private String YYWSRF9725;

	public String getYYWSRF9725() {
		return YYWSRF9725;
	}

	public void setYYWSRF9725(String in) {
		YYWSRF9725 = in;
	}

	@Column(name = "YYWSRC9727", length = 50, nullable = true)
	@IColumn(description="营业外收入出售无形资产收益")
	private String YYWSRC9727;

	public String getYYWSRC9727() {
		return YYWSRC9727;
	}

	public void setYYWSRC9727(String in) {
		YYWSRC9727 = in;
	}

	@Column(name = "YYWSRFKJSR", length = 50, nullable = true)
	@IColumn(description="营业外收入罚款净收入")
	private String YYWSRFKJSR;

	public String getYYWSRFKJSR() {
		return YYWSRFKJSR;
	}

	public void setYYWSRFKJSR(String in) {
		YYWSRFKJSR = in;
	}

	@Column(name = "QT9909", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT9909;

	public String getQT9909() {
		return QT9909;
	}

	public void setQT9909(String in) {
		QT9909 = in;
	}

	@Column(name = "QTYYQN9731", length = 50, nullable = true)
	@IColumn(description="其他用以前年度含量工资节余弥补利润")
	private String QTYYQN9731;

	public String getQTYYQN9731() {
		return QTYYQN9731;
	}

	public void setQTYYQN9731(String in) {
		QTYYQN9731 = in;
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

	@Column(name = "YYWZCC9735", length = 50, nullable = true)
	@IColumn(description="营业外支出处置固定资产净损失")
	private String YYWZCC9735;

	public String getYYWZCC9735() {
		return YYWZCC9735;
	}

	public void setYYWZCC9735(String in) {
		YYWZCC9735 = in;
	}

	@Column(name = "YYWZCZ9737", length = 50, nullable = true)
	@IColumn(description="营业外支出债务重组损失")
	private String YYWZCZ9737;

	public String getYYWZCZ9737() {
		return YYWZCZ9737;
	}

	public void setYYWZCZ9737(String in) {
		YYWZCZ9737 = in;
	}

	@Column(name = "YYWZCFKZC", length = 50, nullable = true)
	@IColumn(description="营业外支出罚款支出")
	private String YYWZCFKZC;

	public String getYYWZCFKZC() {
		return YYWZCFKZC;
	}

	public void setYYWZCFKZC(String in) {
		YYWZCFKZC = in;
	}

	@Column(name = "YYWZCJZZC", length = 50, nullable = true)
	@IColumn(description="营业外支出捐赠支出")
	private String YYWZCJZZC;

	public String getYYWZCJZZC() {
		return YYWZCJZZC;
	}

	public void setYYWZCJZZC(String in) {
		YYWZCJZZC = in;
	}

	@Column(name = "QTZC", length = 50, nullable = true)
	@IColumn(description="其他支出")
	private String QTZC;

	public String getQTZC() {
		return QTZC;
	}

	public void setQTZC(String in) {
		QTZC = in;
	}

	@Column(name = "QTZCJZ9745", length = 50, nullable = true)
	@IColumn(description="其他支出结转的含量工资包干节余")
	private String QTZCJZ9745;

	public String getQTZCJZ9745() {
		return QTZCJZ9745;
	}

	public void setQTZCJZ9745(String in) {
		QTZCJZ9745 = in;
	}

	@Column(name = "LRZR", length = 50, nullable = true)
	@IColumn(description="利润总额")
	private String LRZR;

	public String getLRZR() {
		return LRZR;
	}

	public void setLRZR(String in) {
		LRZR = in;
	}

	@Column(name = "SDS", length = 50, nullable = true)
	@IColumn(description="所得税")
	private String SDS;

	public String getSDS() {
		return SDS;
	}

	public void setSDS(String in) {
		SDS = in;
	}

	@Column(name = "SSGDSY", length = 50, nullable = true)
	@IColumn(description="少数股东损益")
	private String SSGDSY;

	public String getSSGDSY() {
		return SSGDSY;
	}

	public void setSSGDSY(String in) {
		SSGDSY = in;
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

	@Column(name = "JLR", length = 50, nullable = true)
	@IColumn(description="净利润")
	private String JLR;

	public String getJLR() {
		return JLR;
	}

	public void setJLR(String in) {
		JLR = in;
	}

	@Column(name = "NCWFPLR", length = 50, nullable = true)
	@IColumn(description="年初未分配利润")
	private String NCWFPLR;

	public String getNCWFPLR() {
		return NCWFPLR;
	}

	public void setNCWFPLR(String in) {
		NCWFPLR = in;
	}

	@Column(name = "YYGJBK", length = 50, nullable = true)
	@IColumn(description="盈余公积补亏")
	private String YYGJBK;

	public String getYYGJBK() {
		return YYGJBK;
	}

	public void setYYGJBK(String in) {
		YYGJBK = in;
	}

	@Column(name = "QTTZYS", length = 50, nullable = true)
	@IColumn(description="其他调整因素")
	private String QTTZYS;

	public String getQTTZYS() {
		return QTTZYS;
	}

	public void setQTTZYS(String in) {
		QTTZYS = in;
	}

	@Column(name = "KGFPDLR", length = 50, nullable = true)
	@IColumn(description="可供分配的利润")
	private String KGFPDLR;

	public String getKGFPDLR() {
		return KGFPDLR;
	}

	public void setKGFPDLR(String in) {
		KGFPDLR = in;
	}

	@Column(name = "DXLYDLR", length = 50, nullable = true)
	@IColumn(description="单项留用的利润")
	private String DXLYDLR;

	public String getDXLYDLR() {
		return DXLYDLR;
	}

	public void setDXLYDLR(String in) {
		DXLYDLR = in;
	}

	@Column(name = "BCLDZB", length = 50, nullable = true)
	@IColumn(description="补充流动资本")
	private String BCLDZB;

	public String getBCLDZB() {
		return BCLDZB;
	}

	public void setBCLDZB(String in) {
		BCLDZB = in;
	}

	@Column(name = "TQFDYYGJ", length = 50, nullable = true)
	@IColumn(description="提取法定盈余公积")
	private String TQFDYYGJ;

	public String getTQFDYYGJ() {
		return TQFDYYGJ;
	}

	public void setTQFDYYGJ(String in) {
		TQFDYYGJ = in;
	}

	@Column(name = "TQFDGYJ", length = 50, nullable = true)
	@IColumn(description="提取法定公益金")
	private String TQFDGYJ;

	public String getTQFDGYJ() {
		return TQFDGYJ;
	}

	public void setTQFDGYJ(String in) {
		TQFDGYJ = in;
	}

	@Column(name = "TQZGJJJFLJJ", length = 50, nullable = true)
	@IColumn(description="提取职工奖励及福利基金")
	private String TQZGJJJFLJJ;

	public String getTQZGJJJFLJJ() {
		return TQZGJJJFLJJ;
	}

	public void setTQZGJJJFLJJ(String in) {
		TQZGJJJFLJJ = in;
	}

	@Column(name = "TQCBJJ", length = 50, nullable = true)
	@IColumn(description="提取储备基金")
	private String TQCBJJ;

	public String getTQCBJJ() {
		return TQCBJJ;
	}

	public void setTQCBJJ(String in) {
		TQCBJJ = in;
	}

	@Column(name = "TQQYFZJJ", length = 50, nullable = true)
	@IColumn(description="提取企业发展基金")
	private String TQQYFZJJ;

	public String getTQQYFZJJ() {
		return TQQYFZJJ;
	}

	public void setTQQYFZJJ(String in) {
		TQQYFZJJ = in;
	}

	@Column(name = "LRGHTZ", length = 50, nullable = true)
	@IColumn(description="利润归还投资")
	private String LRGHTZ;

	public String getLRGHTZ() {
		return LRGHTZ;
	}

	public void setLRGHTZ(String in) {
		LRGHTZ = in;
	}

	@Column(name = "QT9911", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT9911;

	public String getQT9911() {
		return QT9911;
	}

	public void setQT9911(String in) {
		QT9911 = in;
	}

	@Column(name = "KGTZZFPDLR", length = 50, nullable = true)
	@IColumn(description="可供投资者分配的利润")
	private String KGTZZFPDLR;

	public String getKGTZZFPDLR() {
		return KGTZZFPDLR;
	}

	public void setKGTZZFPDLR(String in) {
		KGTZZFPDLR = in;
	}

	@Column(name = "YFYXGGL", length = 50, nullable = true)
	@IColumn(description="应付优先股股利")
	private String YFYXGGL;

	public String getYFYXGGL() {
		return YFYXGGL;
	}

	public void setYFYXGGL(String in) {
		YFYXGGL = in;
	}

	@Column(name = "TQRYYYGJ", length = 50, nullable = true)
	@IColumn(description="提取任意盈余公积")
	private String TQRYYYGJ;

	public String getTQRYYYGJ() {
		return TQRYYYGJ;
	}

	public void setTQRYYYGJ(String in) {
		TQRYYYGJ = in;
	}

	@Column(name = "YFPTGGL", length = 50, nullable = true)
	@IColumn(description="应付普通股股利")
	private String YFPTGGL;

	public String getYFPTGGL() {
		return YFPTGGL;
	}

	public void setYFPTGGL(String in) {
		YFPTGGL = in;
	}

	@Column(name = "ZZZBDPTGGL", length = 50, nullable = true)
	@IColumn(description="转作资本的普通股股利")
	private String ZZZBDPTGGL;

	public String getZZZBDPTGGL() {
		return ZZZBDPTGGL;
	}

	public void setZZZBDPTGGL(String in) {
		ZZZBDPTGGL = in;
	}

	@Column(name = "QT9913", length = 50, nullable = true)
	@IColumn(description="其他")
	private String QT9913;

	public String getQT9913() {
		return QT9913;
	}

	public void setQT9913(String in) {
		QT9913 = in;
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

	@Column(name = "WFPLR9793", length = 50, nullable = true)
	@IColumn(description="未分配利润应由以后年度税前利润弥补的亏损")
	private String WFPLR9793;

	public String getWFPLR9793() {
		return WFPLR9793;
	}

	public void setWFPLR9793(String in) {
		WFPLR9793 = in;
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

