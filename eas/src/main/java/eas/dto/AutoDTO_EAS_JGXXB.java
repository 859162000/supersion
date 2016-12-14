package eas.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
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
@Table(name = "EAS_JGXXB")
@IEntity(description= "机构信息表")
public class AutoDTO_EAS_JGXXB implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "SJGLJGMC", length = 299, nullable = true)
	@IColumn(description="上级管理机构名称")
	private String SJGLJGMC;

	public String getSJGLJGMC() {
		return SJGLJGMC;
	}

	public void setSJGLJGMC(String in) {
		SJGLJGMC = in;
	}

	@IColumn(tagMethodName="getSFZMDTag",description="是否最末端")
	@Column(name = "SFZMD", nullable =true)
	private String SFZMD;

	public static Map<String,String> getSFZMDTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_IS_END");
	}

	public String getSFZMD() {
		return SFZMD;
	}

	public void setSFZMD(String in) {
		SFZMD = in;
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

	@Column(name = "YXJGDM", length = 299, nullable = true)
	@IColumn(description="银行机构代码")
	private String YXJGDM;

	public String getYXJGDM() {
		return YXJGDM;
	}

	public void setYXJGDM(String in) {
		YXJGDM = in;
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

	@Column(name = "JRXKZH", length = 299, nullable = true)
	@IColumn(description="金融许可证号")
	private String JRXKZH;

	public String getJRXKZH() {
		return JRXKZH;
	}

	public void setJRXKZH(String in) {
		JRXKZH = in;
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

	@Column(name = "JGLB", length = 299, nullable = true)
	@IColumn(description="机构类别")
	private String JGLB;

	public String getJGLB() {
		return JGLB;
	}

	public void setJGLB(String in) {
		JGLB = in;
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

	@Column(name = "WDH", length = 299, nullable = true)
	@IColumn(description="网点号")
	private String WDH;

	public String getWDH() {
		return WDH;
	}

	public void setWDH(String in) {
		WDH = in;
	}

	@IColumn(tagMethodName="getYYZTTag",description="营业状态")
	@Column(name = "YYZT", nullable =true)
	private String YYZT;

	public static Map<String,String> getYYZTTag() {
		return ShowContext.getInstance().getShowEntityMap().get("EAS_BIZ_STATUS");
	}

	public String getYYZT() {
		return YYZT;
	}

	public void setYYZT(String in) {
		YYZT = in;
	}

	@Column(name = "CLSJ", length = 8, nullable = true)
	@IColumn(description="成立时间")
	private String CLSJ;

	public String getCLSJ() {
		return CLSJ;
	}

	public void setCLSJ(String in) {
		CLSJ = in;
	}

	@Column(name = "JGGZKSSJ", length = 299, nullable = true)
	@IColumn(description="机构工作开始时间")
	private String JGGZKSSJ;

	public String getJGGZKSSJ() {
		return JGGZKSSJ;
	}

	public void setJGGZKSSJ(String in) {
		JGGZKSSJ = in;
	}

	@Column(name = "JGGZZZSJ", length = 299, nullable = true)
	@IColumn(description="机构工作终止时间")
	private String JGGZZZSJ;

	public String getJGGZZZSJ() {
		return JGGZZZSJ;
	}

	public void setJGGZZZSJ(String in) {
		JGGZZZSJ = in;
	}

	@Column(name = "JGDZ", length = 500, nullable = true)
	@IColumn(description="机构地址")
	private String JGDZ;

	public String getJGDZ() {
		return JGDZ;
	}

	public void setJGDZ(String in) {
		JGDZ = in;
	}

	@Column(name = "FZRXM", length = 299, nullable = true)
	@IColumn(description="负责人姓名")
	private String FZRXM;

	public String getFZRXM() {
		return FZRXM;
	}

	public void setFZRXM(String in) {
		FZRXM = in;
	}

	@Column(name = "FZRZW", length = 299, nullable = true)
	@IColumn(description="负责人职务")
	private String FZRZW;

	public String getFZRZW() {
		return FZRZW;
	}

	public void setFZRZW(String in) {
		FZRZW = in;
	}

	@Column(name = "FZRLXDH", length = 299, nullable = true)
	@IColumn(description="负责人联系电话")
	private String FZRLXDH;

	public String getFZRLXDH() {
		return FZRLXDH;
	}

	public void setFZRLXDH(String in) {
		FZRLXDH = in;
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

