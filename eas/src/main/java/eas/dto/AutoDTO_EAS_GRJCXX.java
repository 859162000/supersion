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
@Table(name = "EAS_GRJCXX")
@IEntity(description= "个人基础信息")
public class AutoDTO_EAS_GRJCXX implements java.io.Serializable{

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

	@Column(name = "KHTYBH", length = 299, nullable = true)
	@IColumn(description="客户统一编号")
	private String KHTYBH;

	public String getKHTYBH() {
		return KHTYBH;
	}

	public void setKHTYBH(String in) {
		KHTYBH = in;
	}

	@Column(name = "ZJHM", length = 299, nullable = true)
	@IColumn(description="证件号码")
	private String ZJHM;

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String in) {
		ZJHM = in;
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

	@Column(name = "LYXT", length = 299, nullable = true)
	@IColumn(description="来源系统")
	private String LYXT;

	public String getLYXT() {
		return LYXT;
	}

	public void setLYXT(String in) {
		LYXT = in;
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

	@Column(name = "KHYWXM", length = 299, nullable = true)
	@IColumn(description="客户英文姓名")
	private String KHYWXM;

	public String getKHYWXM() {
		return KHYWXM;
	}

	public void setKHYWXM(String in) {
		KHYWXM = in;
	}

	@IColumn(tagMethodName="getZJLBTag",description="证件类别")
	@Column(name = "ZJLB", nullable =true)
	private String ZJLB;

	public static Map<String,String> getZJLBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CERT_CATE");
	}

	public String getZJLB() {
		return ZJLB;
	}

	public void setZJLB(String in) {
		ZJLB = in;
	}

	@Column(name = "GJ", length = 299, nullable = true)
	@IColumn(description="国籍")
	private String GJ;

	public String getGJ() {
		return GJ;
	}

	public void setGJ(String in) {
		GJ = in;
	}

	@Column(name = "MZ", length = 299, nullable = true)
	@IColumn(description="民族")
	private String MZ;

	public String getMZ() {
		return MZ;
	}

	public void setMZ(String in) {
		MZ = in;
	}

	@IColumn(tagMethodName="getXBTag",description="性别")
	@Column(name = "XB", nullable =true)
	private String XB;

	public static Map<String,String> getXBTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_GENDER");
	}

	public String getXB() {
		return XB;
	}

	public void setXB(String in) {
		XB = in;
	}

	@Column(name = "XL", length = 299, nullable = true)
	@IColumn(description="学历")
	private String XL;

	public String getXL() {
		return XL;
	}

	public void setXL(String in) {
		XL = in;
	}

	@Column(name = "CSRQ", length = 8, nullable = true)
	@IColumn(description="出生日期")
	private String CSRQ;

	public String getCSRQ() {
		return CSRQ;
	}

	public void setCSRQ(String in) {
		CSRQ = in;
	}

	@Column(name = "GZDWMC", length = 299, nullable = true)
	@IColumn(description="工作单位名称")
	private String GZDWMC;

	public String getGZDWMC() {
		return GZDWMC;
	}

	public void setGZDWMC(String in) {
		GZDWMC = in;
	}

	@Column(name = "GZDWDZ", length = 500, nullable = true)
	@IColumn(description="工作单位地址")
	private String GZDWDZ;

	public String getGZDWDZ() {
		return GZDWDZ;
	}

	public void setGZDWDZ(String in) {
		GZDWDZ = in;
	}

	@Column(name = "GZDWDH", length = 299, nullable = true)
	@IColumn(description="工作单位电话")
	private String GZDWDH;

	public String getGZDWDH() {
		return GZDWDH;
	}

	public void setGZDWDH(String in) {
		GZDWDH = in;
	}

	@Column(name = "ZY", length = 299, nullable = true)
	@IColumn(description="职业")
	private String ZY;

	public String getZY() {
		return ZY;
	}

	public void setZY(String in) {
		ZY = in;
	}

	@Column(name = "JTZZ", length = 500, nullable = true)
	@IColumn(description="家庭住址")
	private String JTZZ;

	public String getJTZZ() {
		return JTZZ;
	}

	public void setJTZZ(String in) {
		JTZZ = in;
	}

	@Column(name = "TXDZ", length = 500, nullable = true)
	@IColumn(description="通讯地址")
	private String TXDZ;

	public String getTXDZ() {
		return TXDZ;
	}

	public void setTXDZ(String in) {
		TXDZ = in;
	}

	@Column(name = "JTDH", length = 299, nullable = true)
	@IColumn(description="家庭电话")
	private String JTDH;

	public String getJTDH() {
		return JTDH;
	}

	public void setJTDH(String in) {
		JTDH = in;
	}

	@Column(name = "YDDH", length = 299, nullable = true)
	@IColumn(description="移动电话")
	private String YDDH;

	public String getYDDH() {
		return YDDH;
	}

	public void setYDDH(String in) {
		YDDH = in;
	}

	@Column(name = "GRYSR", length = 20, nullable = true)
	@IColumn(description="个人月收入")
	private BigDecimal GRYSR;

	public BigDecimal getGRYSR() {
		return GRYSR;
	}

	public void setGRYSR(String in) {
		GRYSR = TypeParse.parseBigDecimal(in);
	}

	@Column(name = "JTYSR", length = 20, nullable = true)
	@IColumn(description="家庭月收入")
	private BigDecimal JTYSR;

	public BigDecimal getJTYSR() {
		return JTYSR;
	}

	public void setJTYSR(String in) {
		JTYSR = TypeParse.parseBigDecimal(in);
	}

	@IColumn(tagMethodName="getHYQKTag",description="婚姻情况")
	@Column(name = "HYQK", nullable =true)
	private String HYQK;

	public static Map<String,String> getHYQKTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_MARRIAGE_SITU");
	}

	public String getHYQK() {
		return HYQK;
	}

	public void setHYQK(String in) {
		HYQK = in;
	}

	@Column(name = "POXM", length = 299, nullable = true)
	@IColumn(description="配偶姓名")
	private String POXM;

	public String getPOXM() {
		return POXM;
	}

	public void setPOXM(String in) {
		POXM = in;
	}

	@Column(name = "POLXDH", length = 299, nullable = true)
	@IColumn(description="配偶联系电话")
	private String POLXDH;

	public String getPOLXDH() {
		return POLXDH;
	}

	public void setPOLXDH(String in) {
		POLXDH = in;
	}

	@Column(name = "POYDDH", length = 299, nullable = true)
	@IColumn(description="配偶移动电话")
	private String POYDDH;

	public String getPOYDDH() {
		return POYDDH;
	}

	public void setPOYDDH(String in) {
		POYDDH = in;
	}

	@Column(name = "PODYKHH", length = 299, nullable = true)
	@IColumn(description="配偶对应客户号")
	private String PODYKHH;

	public String getPODYKHH() {
		return PODYKHH;
	}

	public void setPODYKHH(String in) {
		PODYKHH = in;
	}

	@IColumn(tagMethodName="getBXYGBZTag",description="本行员工标志")
	@Column(name = "BXYGBZ", nullable =true)
	private String BXYGBZ;

	public static Map<String,String> getBXYGBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CMBC_EMPLY_IND");
	}

	public String getBXYGBZ() {
		return BXYGBZ;
	}

	public void setBXYGBZ(String in) {
		BXYGBZ = in;
	}

	@IColumn(tagMethodName="getSBXHMDBZTag",description="上本行黑名单标志")
	@Column(name = "SBXHMDBZ", nullable =true)
	private String SBXHMDBZ;

	public static Map<String,String> getSBXHMDBZTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_CMBC_BLKLIST_IND");
	}

	public String getSBXHMDBZ() {
		return SBXHMDBZ;
	}

	public void setSBXHMDBZ(String in) {
		SBXHMDBZ = in;
	}

	@Column(name = "SHMDRQ", length = 8, nullable = true)
	@IColumn(description="上黑名单日期")
	private String SHMDRQ;

	public String getSHMDRQ() {
		return SHMDRQ;
	}

	public void setSHMDRQ(String in) {
		SHMDRQ = in;
	}

	@Column(name = "SHMDYY", length = 299, nullable = true)
	@IColumn(description="上黑名单原因")
	private String SHMDYY;

	public String getSHMDYY() {
		return SHMDYY;
	}

	public void setSHMDYY(String in) {
		SHMDYY = in;
	}

	@Column(name = "YZBM", length = 299, nullable = true)
	@IColumn(description="邮政编码")
	private String YZBM;

	public String getYZBM() {
		return YZBM;
	}

	public void setYZBM(String in) {
		YZBM = in;
	}

	@Column(name = "DWXZ", length = 299, nullable = true)
	@IColumn(description="单位性质")
	private String DWXZ;

	public String getDWXZ() {
		return DWXZ;
	}

	public void setDWXZ(String in) {
		DWXZ = in;
	}

	@Column(name = "ZC", length = 299, nullable = true)
	@IColumn(description="职称")
	private String ZC;

	public String getZC() {
		return ZC;
	}

	public void setZC(String in) {
		ZC = in;
	}

	@IColumn(tagMethodName="getSFNHTag",description="是否农户")
	@Column(name = "SFNH", nullable =true)
	private String SFNH;

	public static Map<String,String> getSFNHTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_AGRICUT");
	}

	public String getSFNH() {
		return SFNH;
	}

	public void setSFNH(String in) {
		SFNH = in;
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

