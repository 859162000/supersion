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
@Table(name = "EAS_ZZHJQKMB")
@IEntity(description= "总账会计全科目表")
public class AutoDTO_EAS_ZZHJQKMB implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "CJRQ", length = 8, nullable = true)
	@IColumn(description="采集日期")
	private String CJRQ;

	public String getCJRQ() {
		return CJRQ;
	}

	public void setCJRQ(String in) {
		CJRQ = in;
	}

	@IColumn(tagMethodName="getJDBZTag",description="借贷标志")
	@Column(name = "JDBZ", nullable =true)
	private String JDBZ;

	public static Map<String,String> getJDBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_DC_IND");
	}

	public String getJDBZ() {
		return JDBZ;
	}

	public void setJDBZ(String in) {
		JDBZ = in;
	}

	@Column(name = "TJKMMC", length = 299, nullable = true)
	@IColumn(description="统计科目名称")
	private String TJKMMC;

	public String getTJKMMC() {
		return TJKMMC;
	}

	public void setTJKMMC(String in) {
		TJKMMC = in;
	}

	@Column(name = "TJKMBH", length = 299, nullable = true)
	@IColumn(description="统计科目编号")
	private String TJKMBH;

	public String getTJKMBH() {
		return TJKMBH;
	}

	public void setTJKMBH(String in) {
		TJKMBH = in;
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

	@Column(name = "JRXKZH", length = 299, nullable = true)
	@IColumn(description="金融许可证号")
	private String JRXKZH;

	public String getJRXKZH() {
		return JRXKZH;
	}

	public void setJRXKZH(String in) {
		JRXKZH = in;
	}

	@Column(name = "YHJGDM", length = 299, nullable = true)
	@IColumn(description="银行机构代码")
	private String YHJGDM;

	public String getYHJGDM() {
		return YHJGDM;
	}

	public void setYHJGDM(String in) {
		YHJGDM = in;
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

	@Column(name = "YHJGMC", length = 299, nullable = true)
	@IColumn(description="银行机构名称")
	private String YHJGMC;

	public String getYHJGMC() {
		return YHJGMC;
	}

	public void setYHJGMC(String in) {
		YHJGMC = in;
	}

	@Column(name = "KMBH", length = 299, nullable = true)
	@IColumn(description="总账会计科目编号")
	private String KMBH;

	public String getKMBH() {
		return KMBH;
	}

	public void setKMBH(String in) {
		KMBH = in;
	}

	@Column(name = "KMMC", length = 299, nullable = true)
	@IColumn(description="总账会计科目名称")
	private String KMMC;

	public String getKMMC() {
		return KMMC;
	}

	public void setKMMC(String in) {
		KMMC = in;
	}

	@Column(name = "KMJC", nullable = true)
	@IColumn(description="总账会计科目级次")
	private Integer KMJC;

	public Integer getKMJC() {
		return KMJC;
	}

	public void setKMJC(String in) {
		KMJC = TypeParse.parseInt(in);
	}

	@IColumn(tagMethodName="getKMLXTag",description="总账会计科目类型")
	@Column(name = "KMLX", nullable =true)
	private String KMLX;

	public static Map<String,String> getKMLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_GL_GL_TYPE");
	}

	public String getKMLX() {
		return KMLX;
	}

	public void setKMLX(String in) {
		KMLX = in;
	}

	@Column(name = "QCJFYE", length = 20, nullable = true)
	@IColumn(description="期初借方余额")
	private BigDecimal QCJFYE;

	public BigDecimal getQCJFYE() {
		return QCJFYE;
	}

	public void setQCJFYE(String in) {
		QCJFYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QCDFYE", length = 20, nullable = true)
	@IColumn(description="期初贷方余额")
	private BigDecimal QCDFYE;

	public BigDecimal getQCDFYE() {
		return QCDFYE;
	}

	public void setQCDFYE(String in) {
		QCDFYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JFFSE", length = 20, nullable = true)
	@IColumn(description="本期借方发生额")
	private BigDecimal JFFSE;

	public BigDecimal getJFFSE() {
		return JFFSE;
	}

	public void setJFFSE(String in) {
		JFFSE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DFFSE", length = 20, nullable = true)
	@IColumn(description="本期贷方发生额")
	private BigDecimal DFFSE;

	public BigDecimal getDFFSE() {
		return DFFSE;
	}

	public void setDFFSE(String in) {
		DFFSE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QMJFYE", length = 20, nullable = true)
	@IColumn(description="期末借方余额")
	private BigDecimal QMJFYE;

	public BigDecimal getQMJFYE() {
		return QMJFYE;
	}

	public void setQMJFYE(String in) {
		QMJFYE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "QMDFYE", length = 20, nullable = true)
	@IColumn(description="期末贷方余额")
	private BigDecimal QMDFYE;

	public BigDecimal getQMDFYE() {
		return QMDFYE;
	}

	public void setQMDFYE(String in) {
		QMDFYE = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getBZTag",description="币种")
	@Column(name = "BZ", nullable =true)
	private String BZ;

	public static Map<String,String> getBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("GB_CURR_CD");
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String in) {
		BZ = in;
	}

	@Column(name = "KJRQ", length = 299, nullable = true)
	@IColumn(description="会计日期")
	private String KJRQ;

	public String getKJRQ() {
		return KJRQ;
	}

	public void setKJRQ(String in) {
		KJRQ = in;
	}

	@Column(name = "BSZQ", length = 299, nullable = true)
	@IColumn(description="报送周期")
	private String BSZQ;

	public String getBSZQ() {
		return BSZQ;
	}

	public void setBSZQ(String in) {
		BSZQ = in;
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

