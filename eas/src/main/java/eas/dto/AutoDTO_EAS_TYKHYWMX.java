package eas.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import framework.interfaces.IColumn;
import framework.show.ShowContext;
import framework.helper.TypeParse;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.TemporalType;

import framework.interfaces.IEntity;

@Entity
@Table(name = "EAS_TYKHYWMX")
@IEntity(description= "授信情况-同业客户业务明细")
public class AutoDTO_EAS_TYKHYWMX implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

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

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态")
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

	@IColumn(tagMethodName="getRPTSubmitStatusTag",description="提交状态")
	@Column(name = "RPTSubmitStatus", nullable =true)
	private String RPTSubmitStatus;

	public static Map<String,String> getRPTSubmitStatusTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSubmitStatus");
	}

	public String getRPTSubmitStatus() {
		return RPTSubmitStatus;
	}

	public void setRPTSubmitStatus(String in) {
		RPTSubmitStatus = in;
	}

	@IColumn(tagMethodName="getRPTVerifyTypeTag",description="审核状态")
	@Column(name = "RPTVerifyType", nullable =true)
	private String RPTVerifyType;

	public static Map<String,String> getRPTVerifyTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTVerifyType");
	}

	public String getRPTVerifyType() {
		return RPTVerifyType;
	}

	public void setRPTVerifyType(String in) {
		RPTVerifyType = in;
	}

	@Column(name = "lastUpdateDate", nullable = true)
	@IColumn(description="最后修改时间", isNullable = false)
	private Timestamp lastUpdateDate;

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String in) {
		lastUpdateDate = TypeParse.parseTimestamp(in);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operationUser")
	private coresystem.dto.UserInfo operationUser;

	public coresystem.dto.UserInfo getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(coresystem.dto.UserInfo in) {
		operationUser = in;
	}

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态")
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

	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态")
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instInfo", nullable = false)
	private coresystem.dto.InstInfo instInfo;

	public coresystem.dto.InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(coresystem.dto.InstInfo in) {
		instInfo = in;
	}

	@Column(name = "dtDate", nullable = true)
	@IColumn(description="数据时间", isNullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	@Column(name = "YXJGDM", length = 299, nullable = true)
	@IColumn(description="银行机构代码")
	private String YXJGDM;

	public String getYXJGDM() {
		return YXJGDM;
	}

	public void setYXJGDM(String in) {
		YXJGDM = in;
	}

	@Column(name = "JRXKZH", length = 299, nullable = true)
	@IColumn(description="金融许可证号")
	private String JRXKZH;

	public String getJRXKZH() {
		return JRXKZH;
	}

	public void setJRXKZH(String in) {
		JRXKZH = in;
	}

	@Column(name = "NBJGH", length = 299, nullable = true)
	@IColumn(description="内部机构号")
	private String NBJGH;

	public String getNBJGH() {
		return NBJGH;
	}

	public void setNBJGH(String in) {
		NBJGH = in;
	}

	@Column(name = "SBJGDM", length = 299, nullable = true)
	@IColumn(description="上报机构代码")
	private String SBJGDM;

	public String getSBJGDM() {
		return SBJGDM;
	}

	public void setSBJGDM(String in) {
		SBJGDM = in;
	}

	@Column(name = "KHMC", length = 299, nullable = true)
	@IColumn(description="客户名称")
	private String KHMC;

	public String getKHMC() {
		return KHMC;
	}

	public void setKHMC(String in) {
		KHMC = in;
	}

	@Column(name = "KHDM", length = 299, nullable = true)
	@IColumn(description="客户代码")
	private String KHDM;

	public String getKHDM() {
		return KHDM;
	}

	public void setKHDM(String in) {
		KHDM = in;
	}

	@IColumn(tagMethodName="getGBDMTag",description="国别代码")
	@Column(name = "GBDM", nullable =true)
	private String GBDM;

	public static Map<String,String> getGBDMTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_COUNT_ZONE_CD");
	}

	public String getGBDM() {
		return GBDM;
	}

	public void setGBDM(String in) {
		GBDM = in;
	}

	@Column(name = "FXCJGTJJGBM", length = 299, nullable = true)
	@IColumn(description="非现场监管统计机构编码")
	private String FXCJGTJJGBM;

	public String getFXCJGTJJGBM() {
		return FXCJGTJJGBM;
	}

	public void setFXCJGTJJGBM(String in) {
		FXCJGTJJGBM = in;
	}

	@Column(name = "ZZJGDM", length = 299, nullable = true)
	@IColumn(description="组织机构代码")
	private String ZZJGDM;

	public String getZZJGDM() {
		return ZZJGDM;
	}

	public void setZZJGDM(String in) {
		ZZJGDM = in;
	}

	@IColumn(tagMethodName="getKHLBTag",description="客户类别")
	@Column(name = "KHLB", nullable =true)
	private String KHLB;

	public static Map<String,String> getKHLBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CUST_CATE");
	}

	public String getKHLB() {
		return KHLB;
	}

	public void setKHLB(String in) {
		KHLB = in;
	}

	@Column(name = "NBPJ", length = 299, nullable = true)
	@IColumn(description="内部评级")
	private String NBPJ;

	public String getNBPJ() {
		return NBPJ;
	}

	public void setNBPJ(String in) {
		NBPJ = in;
	}

	@Column(name = "WBPJ", length = 299, nullable = true)
	@IColumn(description="外部评级")
	private String WBPJ;

	public String getWBPJ() {
		return WBPJ;
	}

	public void setWBPJ(String in) {
		WBPJ = in;
	}

	@Column(name = "CHFTY", length = 20, nullable = true)
	@IColumn(description="拆放同业")
	private BigDecimal CHFTY;

	public BigDecimal getCHFTY() {
		return CHFTY;
	}

	public void setCHFTY(String in) {
		CHFTY = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CFTY", length = 20, nullable = true)
	@IColumn(description="存放同业")
	private BigDecimal CFTY;

	public BigDecimal getCFTY() {
		return CFTY;
	}

	public void setCFTY(String in) {
		CFTY = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZQHG", length = 20, nullable = true)
	@IColumn(description="债券回购")
	private BigDecimal ZQHG;

	public BigDecimal getZQHG() {
		return ZQHG;
	}

	public void setZQHG(String in) {
		ZQHG = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "GPZYDK", length = 20, nullable = true)
	@IColumn(description="股票质押贷款")
	private BigDecimal GPZYDK;

	public BigDecimal getGPZYDK() {
		return GPZYDK;
	}

	public void setGPZYDK(String in) {
		GPZYDK = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "MRFSZC", length = 20, nullable = true)
	@IColumn(description="买入返售资产")
	private BigDecimal MRFSZC;

	public BigDecimal getMRFSZC() {
		return MRFSZC;
	}

	public void setMRFSZC(String in) {
		MRFSZC = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "MDSZTX", length = 20, nullable = true)
	@IColumn(description="买断式转贴现")
	private BigDecimal MDSZTX;

	public BigDecimal getMDSZTX() {
		return MDSZTX;
	}

	public void setMDSZTX(String in) {
		MDSZTX = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CYZQ", length = 20, nullable = true)
	@IColumn(description="持有债券")
	private BigDecimal CYZQ;

	public BigDecimal getCYZQ() {
		return CYZQ;
	}

	public void setCYZQ(String in) {
		CYZQ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "GQTZ", length = 20, nullable = true)
	@IColumn(description="股权投资")
	private BigDecimal GQTZ;

	public BigDecimal getGQTZ() {
		return GQTZ;
	}

	public void setGQTZ(String in) {
		GQTZ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "TYDF", length = 20, nullable = true)
	@IColumn(description="同业代付")
	private BigDecimal TYDF;

	public BigDecimal getTYDF() {
		return TYDF;
	}

	public void setTYDF(String in) {
		TYDF = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QTBNYW", length = 20, nullable = true)
	@IColumn(description="其他表内业务")
	private BigDecimal QTBNYW;

	public BigDecimal getQTBNYW() {
		return QTBNYW;
	}

	public void setQTBNYW(String in) {
		QTBNYW = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "MCHGZC", length = 20, nullable = true)
	@IColumn(description="卖出回购资产")
	private BigDecimal MCHGZC;

	public BigDecimal getMCHGZC() {
		return MCHGZC;
	}

	public void setMCHGZC(String in) {
		MCHGZC = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "BKCXDCNJHYFZ", length = 20, nullable = true)
	@IColumn(description="不可撤销的承诺及或有负债")
	private BigDecimal BKCXDCNJHYFZ;

	public BigDecimal getBKCXDCNJHYFZ() {
		return BKCXDCNJHYFZ;
	}

	public void setBKCXDCNJHYFZ(String in) {
		BKCXDCNJHYFZ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QTBWYW", length = 20, nullable = true)
	@IColumn(description="其他表外业务")
	private BigDecimal QTBWYW;

	public BigDecimal getQTBWYW() {
		return QTBWYW;
	}

	public void setQTBWYW(String in) {
		QTBWYW = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "CJRQ", length = 299, nullable = true)
	@IColumn(description="采集日期")
	private BigDecimal CJRQ;

	public BigDecimal getCJRQ() {
		return CJRQ;
	}

	public void setCJRQ(String in) {
		CJRQ = TypeParse.parseBigDecimal(in);
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

