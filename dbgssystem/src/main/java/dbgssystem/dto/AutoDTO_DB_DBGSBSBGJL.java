package dbgssystem.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "DB_DBGSBSBGJL")
@IEntity(navigationName="标识变更记录",description="标识变更记录")
public class AutoDTO_DB_DBGSBSBGJL implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5", isNullable = true,isSingleTagHidden=true)
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4", isNullable = true,isSingleTagHidden=true)
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3", isNullable = true,isSingleTagHidden=true)
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2", isNullable = true,isSingleTagHidden=true)
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1", isNullable = true,isSingleTagHidden=true)
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}

	@IColumn(tagMethodName="getRPTSubmitStatusTag",description="提交状态",isSingleTagHidden=true)
	@Column(name = "RPTSubmitStatus", nullable =false)
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

	@IColumn(tagMethodName="getRPTVerifyTypeTag",description="审核状态",isSingleTagHidden=true)
	@Column(name = "RPTVerifyType", nullable =false)
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

	@Column(name = "lastUpdateDate", nullable = false)
	@IColumn(description="最后修改时间", isNullable = false,isSingleTagHidden=true)
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

	public void setOperationUser(coresystem.dto.UserInfo in) throws Exception{
		operationUser = in;
	}

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=true)
	@Column(name = "RPTSendType", nullable =false)
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

	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态",isSingleTagHidden=true)
	@Column(name = "RPTCheckType", nullable =false)
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

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden = true,isNullable=false)
	@Column(name = "RPTFeedbackType",length=1)
	private String RPTFeedbackType;
	
	public static Map<String,String> getRPTFeedbackTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
	}
	
	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}


	public String getRPTFeedbackType() {
		return RPTFeedbackType;
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

	@Column(name = "dtDate", nullable = false)
	@IColumn(description="数据时间", isNullable = false,isSingleTagHidden=true)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}
	
	@Id
	@Column(name = "autoBSBGJLID", length = 100)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoBSBGJLID;
	
	@Column(name = "DBJGDM", length = 50, nullable = false)
	@IColumn(description="担保机构代码", isNullable = false)
	private String DBJGDM;
	
	@Column(name = "YDBYWBH", length = 200, nullable = false)
	@IColumn(description="原担保业务编号", isNullable = false)
	private String YDBYWBH;
	
	@Column(name = "BGHDDBYWBH", length = 200, nullable = false)
	@IColumn(description="变更后的担保业务编号", isNullable = false)
	private String BGHDDBYWBH;
	
	@Column(name = "SJBGRQ", length = 50, nullable = false)
	@IColumn(description="数据报告日期", isNullable = false)
	private String SJBGRQ;

	public String getDBJGDM() {
		return DBJGDM;
	}

	public void setDBJGDM(String dBJGDM) {
		DBJGDM = dBJGDM;
	}

	public String getYDBYWBH() {
		return YDBYWBH;
	}

	public void setYDBYWBH(String yDBYWBH) {
		YDBYWBH = yDBYWBH;
	}

	public String getBGHDDBYWBH() {
		return BGHDDBYWBH;
	}

	public void setBGHDDBYWBH(String bGHDDBYWBH) {
		BGHDDBYWBH = bGHDDBYWBH;
	}

	

	public void setAutoBSBGJLID(String autoBSBGJLID) {
		this.autoBSBGJLID = autoBSBGJLID;
	}

	public String getAutoBSBGJLID() {
		return autoBSBGJLID;
	}

	public void setSJBGRQ(String sJBGRQ) {
		SJBGRQ = sJBGRQ;
	}

	public String getSJBGRQ() {
		return SJBGRQ;
	}

}
