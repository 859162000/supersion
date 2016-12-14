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
@Table(name = "EAS_XDYWZHDYW")
@IEntity(description= "信贷业务质或抵押物")
public class AutoDTO_EAS_XDYWZHDYW implements java.io.Serializable{

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

	@Column(name = "ZHDYWBH", length = 299, nullable = true)
	@IColumn(description="质或抵押物编号")
	private String ZHDYWBH;

	public String getZHDYWBH() {
		return ZHDYWBH;
	}

	public void setZHDYWBH(String in) {
		ZHDYWBH = in;
	}

	@Column(name = "DBHTH", length = 299, nullable = true)
	@IColumn(description="担保合同号")
	private String DBHTH;

	public String getDBHTH() {
		return DBHTH;
	}

	public void setDBHTH(String in) {
		DBHTH = in;
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

	@Column(name = "ZHDYWMC", length = 299, nullable = true)
	@IColumn(description="质或抵押物名称")
	private String ZHDYWMC;

	public String getZHDYWMC() {
		return ZHDYWMC;
	}

	public void setZHDYWMC(String in) {
		ZHDYWMC = in;
	}

	@IColumn(tagMethodName="getZHDYWLXTag",description="质或抵押物类型")
	@Column(name = "ZHDYWLX", nullable =true)
	private String ZHDYWLX;

	public static Map<String,String> getZHDYWLXTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_PLEDGE_TYPE");
	}

	public String getZHDYWLX() {
		return ZHDYWLX;
	}

	public void setZHDYWLX(String in) {
		ZHDYWLX = in;
	}

	@Column(name = "ZHDYWZMJZ", length = 20, nullable = true)
	@IColumn(description="质或抵押物账面价值")
	private BigDecimal ZHDYWZMJZ;

	public BigDecimal getZHDYWZMJZ() {
		return ZHDYWZMJZ;
	}

	public void setZHDYWZMJZ(String in) {
		ZHDYWZMJZ = TypeParse.parseBigDecimal(in);
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

	@Column(name = "YXRDJZ", length = 20, nullable = true)
	@IColumn(description="银行认定价值")
	private BigDecimal YXRDJZ;

	public BigDecimal getYXRDJZ() {
		return YXRDJZ;
	}

	public void setYXRDJZ(String in) {
		YXRDJZ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "PGJZ", length = 20, nullable = true)
	@IColumn(description="评估价值")
	private BigDecimal PGJZ;

	public BigDecimal getPGJZ() {
		return PGJZ;
	}

	public void setPGJZ(String in) {
		PGJZ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "PGRQ", length = 8, nullable = true)
	@IColumn(description="评估日期")
	private String PGRQ;

	public String getPGRQ() {
		return PGRQ;
	}

	public void setPGRQ(String in) {
		PGRQ = in;
	}

	@Column(name = "PGJGMC", length = 299, nullable = true)
	@IColumn(description="评估机构名称")
	private String PGJGMC;

	public String getPGJGMC() {
		return PGJGMC;
	}

	public void setPGJGMC(String in) {
		PGJGMC = in;
	}

	@Column(name = "ZHDYL", nullable = true)
	@IColumn(description="质或抵押率(万分之一)")
	private BigDecimal ZHDYL;

	public BigDecimal getZHDYL() {
		return ZHDYL;
	}

	public void setZHDYL(String in) {
		ZHDYL = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DYWSYQRMC", length = 299, nullable = true)
	@IColumn(description="抵押物所有权人名称")
	private String DYWSYQRMC;

	public String getDYWSYQRMC() {
		return DYWSYQRMC;
	}

	public void setDYWSYQRMC(String in) {
		DYWSYQRMC = in;
	}

	@Column(name = "YDYJZ", length = 20, nullable = true)
	@IColumn(description="已抵押价值")
	private BigDecimal YDYJZ;

	public BigDecimal getYDYJZ() {
		return YDYJZ;
	}

	public void setYDYJZ(String in) {
		YDYJZ = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "DJRQ", length = 8, nullable = true)
	@IColumn(description="登记日期")
	private String DJRQ;

	public String getDJRQ() {
		return DJRQ;
	}

	public void setDJRQ(String in) {
		DJRQ = in;
	}

	@Column(name = "SWSQRQ", length = 299, nullable = true)
	@IColumn(description="实物收取日期")
	private String SWSQRQ;

	public String getSWSQRQ() {
		return SWSQRQ;
	}

	public void setSWSQRQ(String in) {
		SWSQRQ = in;
	}

	@Column(name = "DJJG", length = 299, nullable = true)
	@IColumn(description="登记机构")
	private String DJJG;

	public String getDJJG() {
		return DJJG;
	}

	public void setDJJG(String in) {
		DJJG = in;
	}

	@Column(name = "CZJG", length = 299, nullable = true)
	@IColumn(description="操作机构")
	private String CZJG;

	public String getCZJG() {
		return CZJG;
	}

	public void setCZJG(String in) {
		CZJG = in;
	}

	@Column(name = "DBQSRQ", length = 8, nullable = true)
	@IColumn(description="担保起始日期")
	private String DBQSRQ;

	public String getDBQSRQ() {
		return DBQSRQ;
	}

	public void setDBQSRQ(String in) {
		DBQSRQ = in;
	}

	@Column(name = "DBDQRQ", length = 8, nullable = true)
	@IColumn(description="担保到期日期")
	private String DBDQRQ;

	public String getDBDQRQ() {
		return DBDQRQ;
	}

	public void setDBDQRQ(String in) {
		DBDQRQ = in;
	}

	@Column(name = "ZYPZZH", length = 299, nullable = true)
	@IColumn(description="质押票证账号")
	private String ZYPZZH;

	public String getZYPZZH() {
		return ZYPZZH;
	}

	public void setZYPZZH(String in) {
		ZYPZZH = in;
	}

	@Column(name = "ZYPZLX", length = 299, nullable = true)
	@IColumn(description="质押票证类型")
	private String ZYPZLX;

	public String getZYPZLX() {
		return ZYPZLX;
	}

	public void setZYPZLX(String in) {
		ZYPZLX = in;
	}

	@Column(name = "ZYPZHM", length = 299, nullable = true)
	@IColumn(description="质押票证号码")
	private String ZYPZHM;

	public String getZYPZHM() {
		return ZYPZHM;
	}

	public void setZYPZHM(String in) {
		ZYPZHM = in;
	}

	@Column(name = "ZYPZJE", length = 20, nullable = true)
	@IColumn(description="质押票证金额")
	private BigDecimal ZYPZJE;

	public BigDecimal getZYPZJE() {
		return ZYPZJE;
	}

	public void setZYPZJE(String in) {
		ZYPZJE = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "ZYPZQFJG", length = 299, nullable = true)
	@IColumn(description="质押票证签发机构")
	private String ZYPZQFJG;

	public String getZYPZQFJG() {
		return ZYPZQFJG;
	}

	public void setZYPZQFJG(String in) {
		ZYPZQFJG = in;
	}

	@Column(name = "ZYPZKLRQ", length = 299, nullable = true)
	@IColumn(description="质押票证开立日期")
	private String ZYPZKLRQ;

	public String getZYPZKLRQ() {
		return ZYPZKLRQ;
	}

	public void setZYPZKLRQ(String in) {
		ZYPZKLRQ = in;
	}

	@Column(name = "BXDH", length = 299, nullable = true)
	@IColumn(description="保险单号")
	private String BXDH;

	public String getBXDH() {
		return BXDH;
	}

	public void setBXDH(String in) {
		BXDH = in;
	}

	@Column(name = "HBRQ", length = 8, nullable = true)
	@IColumn(description="核保日期")
	private String HBRQ;

	public String getHBRQ() {
		return HBRQ;
	}

	public void setHBRQ(String in) {
		HBRQ = in;
	}

	@Column(name = "HBRYXM", length = 299, nullable = true)
	@IColumn(description="核保人一姓名")
	private String HBRYXM;

	public String getHBRYXM() {
		return HBRYXM;
	}

	public void setHBRYXM(String in) {
		HBRYXM = in;
	}

	@Column(name = "HBREXM", length = 299, nullable = true)
	@IColumn(description="核保人二姓名")
	private String HBREXM;

	public String getHBREXM() {
		return HBREXM;
	}

	public void setHBREXM(String in) {
		HBREXM = in;
	}

	@Column(name = "QZDJHM", length = 299, nullable = true)
	@IColumn(description="权证登记号码")
	private String QZDJHM;

	public String getQZDJHM() {
		return QZDJHM;
	}

	public void setQZDJHM(String in) {
		QZDJHM = in;
	}

	@Column(name = "QZMC", length = 299, nullable = true)
	@IColumn(description="权证名称")
	private String QZMC;

	public String getQZMC() {
		return QZMC;
	}

	public void setQZMC(String in) {
		QZMC = in;
	}

	@Column(name = "QZYXDQRQ", length = 8, nullable = true)
	@IColumn(description="权证有效到期日期")
	private String QZYXDQRQ;

	public String getQZYXDQRQ() {
		return QZYXDQRQ;
	}

	public void setQZYXDQRQ(String in) {
		QZYXDQRQ = in;
	}

	@Column(name = "DJYXZZRQ", length = 8, nullable = true)
	@IColumn(description="登记有效终止日期")
	private String DJYXZZRQ;

	public String getDJYXZZRQ() {
		return DJYXZZRQ;
	}

	public void setDJYXZZRQ(String in) {
		DJYXZZRQ = in;
	}

	@IColumn(tagMethodName="getSFNRBWHSTag",description="是否纳入表外核算")
	@Column(name = "SFNRBWHS", nullable =true)
	private String SFNRBWHS;

	public static Map<String,String> getSFNRBWHSTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_IN_OFF_ACCTING");
	}

	public String getSFNRBWHS() {
		return SFNRBWHS;
	}

	public void setSFNRBWHS(String in) {
		SFNRBWHS = in;
	}

	@Column(name = "BWHSKSRQ", length = 8, nullable = true)
	@IColumn(description="表外核算开始日期")
	private String BWHSKSRQ;

	public String getBWHSKSRQ() {
		return BWHSKSRQ;
	}

	public void setBWHSKSRQ(String in) {
		BWHSKSRQ = in;
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

