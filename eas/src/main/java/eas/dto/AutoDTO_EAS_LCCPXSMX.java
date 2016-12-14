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
@Table(name = "EAS_LCCPXSMX")
@IEntity(description= "理财产品销售明细")
public class AutoDTO_EAS_LCCPXSMX implements java.io.Serializable{

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

	@Column(name = "JYH", length = 299, nullable = true)
	@IColumn(description="交易号")
	private String JYH;

	public String getJYH() {
		return JYH;
	}

	public void setJYH(String in) {
		JYH = in;
	}

	@Column(name = "HXJYLSH", length = 299, nullable = true)
	@IColumn(description="核心交易流水号")
	private String HXJYLSH;

	public String getHXJYLSH() {
		return HXJYLSH;
	}

	public void setHXJYLSH(String in) {
		HXJYLSH = in;
	}

	@Column(name = "ZJYLSH", length = 299, nullable = true)
	@IColumn(description="子交易流水号")
	private String ZJYLSH;

	public String getZJYLSH() {
		return ZJYLSH;
	}

	public void setZJYLSH(String in) {
		ZJYLSH = in;
	}

	@Column(name = "BCXH", length = 299, nullable = true)
	@IColumn(description="笔次序号")
	private String BCXH;

	public String getBCXH() {
		return BCXH;
	}

	public void setBCXH(String in) {
		BCXH = in;
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

	@Column(name = "MXKMBH", length = 299, nullable = true)
	@IColumn(description="明细科目编号")
	private String MXKMBH;

	public String getMXKMBH() {
		return MXKMBH;
	}

	public void setMXKMBH(String in) {
		MXKMBH = in;
	}

	@Column(name = "YXJGMC", length = 299, nullable = true)
	@IColumn(description="银行机构名称")
	private String YXJGMC;

	public String getYXJGMC() {
		return YXJGMC;
	}

	public void setYXJGMC(String in) {
		YXJGMC = in;
	}

	@Column(name = "MXKMMC", length = 299, nullable = true)
	@IColumn(description="明细科目名称")
	private String MXKMMC;

	public String getMXKMMC() {
		return MXKMMC;
	}

	public void setMXKMMC(String in) {
		MXKMMC = in;
	}

	@Column(name = "LCZH", length = 299, nullable = true)
	@IColumn(description="理财帐号")
	private String LCZH;

	public String getLCZH() {
		return LCZH;
	}

	public void setLCZH(String in) {
		LCZH = in;
	}

	@Column(name = "KHTYBH", length = 299, nullable = true)
	@IColumn(description="客户统一编号")
	private String KHTYBH;

	public String getKHTYBH() {
		return KHTYBH;
	}

	public void setKHTYBH(String in) {
		KHTYBH = in;
	}

	@Column(name = "KHXM", length = 299, nullable = true)
	@IColumn(description="客户姓名")
	private String KHXM;

	public String getKHXM() {
		return KHXM;
	}

	public void setKHXM(String in) {
		KHXM = in;
	}

	@Column(name = "GLHQCKZH", length = 299, nullable = true)
	@IColumn(description="关联活期存款账号")
	private String GLHQCKZH;

	public String getGLHQCKZH() {
		return GLHQCKZH;
	}

	public void setGLHQCKZH(String in) {
		GLHQCKZH = in;
	}

	@Column(name = "LCCPMC", length = 299, nullable = true)
	@IColumn(description="理财产品名称")
	private String LCCPMC;

	public String getLCCPMC() {
		return LCCPMC;
	}

	public void setLCCPMC(String in) {
		LCCPMC = in;
	}

	@Column(name = "HNBSM", length = 299, nullable = true)
	@IColumn(description="行内标识码")
	private String HNBSM;

	public String getHNBSM() {
		return HNBSM;
	}

	public void setHNBSM(String in) {
		HNBSM = in;
	}

	@Column(name = "SGSHBZ", length = 299, nullable = true)
	@IColumn(description="申购赎回标志")
	private String SGSHBZ;

	public String getSGSHBZ() {
		return SGSHBZ;
	}

	public void setSGSHBZ(String in) {
		SGSHBZ = in;
	}

	@Column(name = "SFJESH", length = 299, nullable = true)
	@IColumn(description="是否巨额赎回")
	private String SFJESH;

	public String getSFJESH() {
		return SFJESH;
	}

	public void setSFJESH(String in) {
		SFJESH = in;
	}

	@Column(name = "HXJYRQ", length = 8, nullable = true)
	@IColumn(description="交易日期")
	private String HXJYRQ;

	public String getHXJYRQ() {
		return HXJYRQ;
	}

	public void setHXJYRQ(String in) {
		HXJYRQ = in;
	}

	@Column(name = "JYJE", length = 20, nullable = true)
	@IColumn(description="交易金额")
	private BigDecimal JYJE;

	public BigDecimal getJYJE() {
		return JYJE;
	}

	public void setJYJE(String in) {
		JYJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JYFE", length = 299, nullable = true)
	@IColumn(description="交易份额")
	private String JYFE;

	public String getJYFE() {
		return JYFE;
	}

	public void setJYFE(String in) {
		JYFE = in;
	}

	@Column(name = "JYFY", length = 20, nullable = true)
	@IColumn(description="交易费用")
	private BigDecimal JYFY;

	public BigDecimal getJYFY() {
		return JYFY;
	}

	public void setJYFY(String in) {
		JYFY = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JYQD", length = 299, nullable = true)
	@IColumn(description="交易渠道")
	private String JYQD;

	public String getJYQD() {
		return JYQD;
	}

	public void setJYQD(String in) {
		JYQD = in;
	}

	@Column(name = "KHJLGH", length = 299, nullable = true)
	@IColumn(description="客户经理工号")
	private String KHJLGH;

	public String getKHJLGH() {
		return KHJLGH;
	}

	public void setKHJLGH(String in) {
		KHJLGH = in;
	}

	@Column(name = "KHJLXM", length = 299, nullable = true)
	@IColumn(description="客户经理姓名")
	private String KHJLXM;

	public String getKHJLXM() {
		return KHJLXM;
	}

	public void setKHJLXM(String in) {
		KHJLXM = in;
	}

	@Column(name = "JYGYH", length = 299, nullable = true)
	@IColumn(description="交易柜员号")
	private String JYGYH;

	public String getJYGYH() {
		return JYGYH;
	}

	public void setJYGYH(String in) {
		JYGYH = in;
	}

	@Column(name = "CJRQ", length = 8, nullable = true)
	@IColumn(description="采集日期")
	private String CJRQ;

	public String getCJRQ() {
		return CJRQ;
	}

	public void setCJRQ(String in) {
		CJRQ = in;
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

