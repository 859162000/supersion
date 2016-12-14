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
@Table(name = "QY_SYDWSRZCB")
@IEntity(description= "事业单位收入支出表")
public class AutoDTO_QY_SYDWSRZCB implements java.io.Serializable{

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

	@Column(name = "FSDWJK", length = 50, nullable = true)
	@IColumn(description="附属单位缴款")
	private String FSDWJK;

	public String getFSDWJK() {
		return FSDWJK;
	}

	public void setFSDWJK(String in) {
		FSDWJK = in;
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

	@Column(name = "YSWZJSR", length = 50, nullable = true)
	@IColumn(description="预算外资金收入")
	private String YSWZJSR;

	public String getYSWZJSR() {
		return YSWZJSR;
	}

	public void setYSWZJSR(String in) {
		YSWZJSR = in;
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

	@Column(name = "SYSRXJ", length = 50, nullable = true)
	@IColumn(description="事业收入小计")
	private String SYSRXJ;

	public String getSYSRXJ() {
		return SYSRXJ;
	}

	public void setSYSRXJ(String in) {
		SYSRXJ = in;
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

	@Column(name = "JYSRXJ", length = 50, nullable = true)
	@IColumn(description="经营收入小计")
	private String JYSRXJ;

	public String getJYSRXJ() {
		return JYSRXJ;
	}

	public void setJYSRXJ(String in) {
		JYSRXJ = in;
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

	@Column(name = "BRZKXJ", length = 50, nullable = true)
	@IColumn(description="拨入专款小计")
	private String BRZKXJ;

	public String getBRZKXJ() {
		return BRZKXJ;
	}

	public void setBRZKXJ(String in) {
		BRZKXJ = in;
	}

	@Column(name = "SRZJ", length = 50, nullable = true)
	@IColumn(description="收入总计")
	private String SRZJ;

	public String getSRZJ() {
		return SRZJ;
	}

	public void setSRZJ(String in) {
		SRZJ = in;
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

	@Column(name = "SYZC", length = 50, nullable = true)
	@IColumn(description="事业支出")
	private String SYZC;

	public String getSYZC() {
		return SYZC;
	}

	public void setSYZC(String in) {
		SYZC = in;
	}

	@Column(name = "CZBZZC", length = 50, nullable = true)
	@IColumn(description="财政补助支出")
	private String CZBZZC;

	public String getCZBZZC() {
		return CZBZZC;
	}

	public void setCZBZZC(String in) {
		CZBZZC = in;
	}

	@Column(name = "YSWZJZC", length = 50, nullable = true)
	@IColumn(description="预算外资金支出")
	private String YSWZJZC;

	public String getYSWZJZC() {
		return YSWZJZC;
	}

	public void setYSWZJZC(String in) {
		YSWZJZC = in;
	}

	@Column(name = "XSSJ_1", length = 50, nullable = true)
	@IColumn(description="销售税金")
	private String XSSJ_1;

	public String getXSSJ_1() {
		return XSSJ_1;
	}

	public void setXSSJ_1(String in) {
		XSSJ_1 = in;
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

	@Column(name = "SYZCXJ", length = 50, nullable = true)
	@IColumn(description="事业支出小计")
	private String SYZCXJ;

	public String getSYZCXJ() {
		return SYZCXJ;
	}

	public void setSYZCXJ(String in) {
		SYZCXJ = in;
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

	@Column(name = "XSSJ_2", length = 50, nullable = true)
	@IColumn(description="销售税金")
	private String XSSJ_2;

	public String getXSSJ_2() {
		return XSSJ_2;
	}

	public void setXSSJ_2(String in) {
		XSSJ_2 = in;
	}

	@Column(name = "JYZCXJ", length = 50, nullable = true)
	@IColumn(description="经营支出小计")
	private String JYZCXJ;

	public String getJYZCXJ() {
		return JYZCXJ;
	}

	public void setJYZCXJ(String in) {
		JYZCXJ = in;
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

	@Column(name = "ZKXJ", length = 50, nullable = true)
	@IColumn(description="专款小计")
	private String ZKXJ;

	public String getZKXJ() {
		return ZKXJ;
	}

	public void setZKXJ(String in) {
		ZKXJ = in;
	}

	@Column(name = "ZCZJ", length = 50, nullable = true)
	@IColumn(description="支出总计")
	private String ZCZJ;

	public String getZCZJ() {
		return ZCZJ;
	}

	public void setZCZJ(String in) {
		ZCZJ = in;
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

	@Column(name = "ZCSRJY", length = 50, nullable = true)
	@IColumn(description="正常收入结余")
	private String ZCSRJY;

	public String getZCSRJY() {
		return ZCSRJY;
	}

	public void setZCSRJY(String in) {
		ZCSRJY = in;
	}

	@Column(name = "SHYQNDSYZC", length = 50, nullable = true)
	@IColumn(description="收回以前年度事业支出")
	private String SHYQNDSYZC;

	public String getSHYQNDSYZC() {
		return SHYQNDSYZC;
	}

	public void setSHYQNDSYZC(String in) {
		SHYQNDSYZC = in;
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

	@Column(name = "YQNDJYKS", length = 50, nullable = true)
	@IColumn(description="以前年度经营亏损")
	private String YQNDJYKS;

	public String getYQNDJYKS() {
		return YQNDJYKS;
	}

	public void setYQNDJYKS(String in) {
		YQNDJYKS = in;
	}

	@Column(name = "JYFP", length = 50, nullable = true)
	@IColumn(description="结余分配")
	private String JYFP;

	public String getJYFP() {
		return JYFP;
	}

	public void setJYFP(String in) {
		JYFP = in;
	}

	@Column(name = "YJSDS", length = 50, nullable = true)
	@IColumn(description="应交所得税")
	private String YJSDS;

	public String getYJSDS() {
		return YJSDS;
	}

	public void setYJSDS(String in) {
		YJSDS = in;
	}

	@Column(name = "TQZYJJ", length = 50, nullable = true)
	@IColumn(description="提取专用基金")
	private String TQZYJJ;

	public String getTQZYJJ() {
		return TQZYJJ;
	}

	public void setTQZYJJ(String in) {
		TQZYJJ = in;
	}

	@Column(name = "ZRSYJJ", length = 50, nullable = true)
	@IColumn(description="转入事业基金")
	private String ZRSYJJ;

	public String getZRSYJJ() {
		return ZRSYJJ;
	}

	public void setZRSYJJ(String in) {
		ZRSYJJ = in;
	}

	@Column(name = "QTJYFP", length = 50, nullable = true)
	@IColumn(description="其他结余分配")
	private String QTJYFP;

	public String getQTJYFP() {
		return QTJYFP;
	}

	public void setQTJYFP(String in) {
		QTJYFP = in;
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

