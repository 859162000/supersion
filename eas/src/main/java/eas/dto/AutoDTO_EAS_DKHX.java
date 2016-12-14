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
@Table(name = "EAS_DKHX")
@IEntity(description= "贷款核销")
public class AutoDTO_EAS_DKHX implements java.io.Serializable{

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

	@Column(name = "XDJJH", length = 299, nullable = true)
	@IColumn(description="信贷借据号")
	private String XDJJH;

	public String getXDJJH() {
		return XDJJH;
	}

	public void setXDJJH(String in) {
		XDJJH = in;
	}

	@Column(name = "XDHTH", length = 299, nullable = true)
	@IColumn(description="信贷合同号")
	private String XDHTH;

	public String getXDHTH() {
		return XDHTH;
	}

	public void setXDHTH(String in) {
		XDHTH = in;
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

	@Column(name = "KMGSJG", length = 299, nullable = true)
	@IColumn(description="科目归属机构")
	private String KMGSJG;

	public String getKMGSJG() {
		return KMGSJG;
	}

	public void setKMGSJG(String in) {
		KMGSJG = in;
	}

	@Column(name = "SHDKBJ", length = 20, nullable = true)
	@IColumn(description="实核贷款本金")
	private BigDecimal SHDKBJ;

	public BigDecimal getSHDKBJ() {
		return SHDKBJ;
	}

	public void setSHDKBJ(String in) {
		SHDKBJ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SHBNLX", length = 20, nullable = true)
	@IColumn(description="实核表内利息")
	private BigDecimal SHBNLX;

	public BigDecimal getSHBNLX() {
		return SHBNLX;
	}

	public void setSHBNLX(String in) {
		SHBNLX = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SHBWLX", length = 20, nullable = true)
	@IColumn(description="实核表外利息")
	private BigDecimal SHBWLX;

	public BigDecimal getSHBWLX() {
		return SHBWLX;
	}

	public void setSHBWLX(String in) {
		SHBWLX = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "HXRQ", length = 8, nullable = true)
	@IColumn(description="核销日期")
	private String HXRQ;

	public String getHXRQ() {
		return HXRQ;
	}

	public void setHXRQ(String in) {
		HXRQ = in;
	}

	@Column(name = "HXSHBJ", length = 20, nullable = true)
	@IColumn(description="核销收回本金")
	private BigDecimal HXSHBJ;

	public BigDecimal getHXSHBJ() {
		return HXSHBJ;
	}

	public void setHXSHBJ(String in) {
		HXSHBJ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "HXSHBNLX", length = 20, nullable = true)
	@IColumn(description="核销收回表内利息")
	private BigDecimal HXSHBNLX;

	public BigDecimal getHXSHBNLX() {
		return HXSHBNLX;
	}

	public void setHXSHBNLX(String in) {
		HXSHBNLX = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "HXSHBWLX", length = 20, nullable = true)
	@IColumn(description="核销收回表外利息")
	private BigDecimal HXSHBWLX;

	public BigDecimal getHXSHBWLX() {
		return HXSHBWLX;
	}

	public void setHXSHBWLX(String in) {
		HXSHBWLX = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "HXHXJLX", length = 20, nullable = true)
	@IColumn(description="核销后新结利息")
	private BigDecimal HXHXJLX;

	public BigDecimal getHXHXJLX() {
		return HXHXJLX;
	}

	public void setHXHXJLX(String in) {
		HXHXJLX = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "SHHXHLX", length = 20, nullable = true)
	@IColumn(description="收回核销后利息")
	private BigDecimal SHHXHLX;

	public BigDecimal getSHHXHLX() {
		return SHHXHLX;
	}

	public void setSHHXHLX(String in) {
		SHHXHLX = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getSHBZTag",description="收回标志")
	@Column(name = "SHBZ", nullable =true)
	private String SHBZ;

	public static Map<String,String> getSHBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_RETRA_IND");
	}

	public String getSHBZ() {
		return SHBZ;
	}

	public void setSHBZ(String in) {
		SHBZ = in;
	}

	@Column(name = "HXSHGYH", length = 299, nullable = true)
	@IColumn(description="核销收回柜员号")
	private String HXSHGYH;

	public String getHXSHGYH() {
		return HXSHGYH;
	}

	public void setHXSHGYH(String in) {
		HXSHGYH = in;
	}

	@Column(name = "HXSHRQ", length = 8, nullable = true)
	@IColumn(description="核销收回日期")
	private String HXSHRQ;

	public String getHXSHRQ() {
		return HXSHRQ;
	}

	public void setHXSHRQ(String in) {
		HXSHRQ = in;
	}

	@Column(name = "BZH", length = 299, nullable = true)
	@IColumn(description="备注")
	private String BZH;

	public String getBZH() {
		return BZH;
	}

	public void setBZH(String in) {
		BZH = in;
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

