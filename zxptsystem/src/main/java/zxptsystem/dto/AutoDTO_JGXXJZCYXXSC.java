package zxptsystem.dto;

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

import extend.helper.HelpTool;import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "JGXXJZCYXXSC")
@IEntity(navigationName="机构信息家族成员信息删除",description="机构信息家族成员信息删除")
public class AutoDTO_JGXXJZCYXXSC  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(tagMethodName="getZYGXRZJLXTag",description="主要关系人证件类型",isNullable=false)
	@Column(name = "strZYGXRZJLX", length = 50)
	private String strZYGXRZJLX;

	public static Map<String,String> getZYGXRZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@IColumn(description="主要关系人证件号码",isNullable=false)
	@Column(name = "strZYGXRZJHM", length = 200)
	private String strZYGXRZJHM;
	
	@IColumn(tagMethodName="getJZGXTag",description="家族关系",isNullable=false)
	@Column(name = "strJZGX", length = 50)
	private String strJZGX;

	public static Map<String,String> getJZGXTag() {
		try {
			return HelpTool.getSystemConstVal("JZGX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@IColumn(tagMethodName="getJZCYZJLXTag",description="家族成员证件类型",isNullable=false)
	@Column(name = "strJZCYZJLX", length = 50)
	private String strJZCYZJLX;

	public static Map<String,String> getJZCYZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@IColumn(description="家族成员证件号码",isNullable=false)
	@Column(name = "strJZCYZJHM", length = 200)
	private String strJZCYZJHM;
	
	@IColumn(description="信息更新日期",isNullable=false)
	@Column(name = "strXXGXRQ", length = 50)
	private String strXXGXRQ;
	
	@IColumn(description="预留字段")
	@Column(name = "strYLZD", length = 200)
	private String strYLZD;
	
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
	
	@IColumn(tagMethodName="getRPTSubmitStatusTag",description="提交状态",isSingleTagHidden=true)
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

	@IColumn(tagMethodName="getRPTVerifyTypeTag",description="审核状态",isSingleTagHidden=true)
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
	
	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=true)
	@Column(name = "RPTSendType", nullable =false)
	private String RPTSendType;

	public static Map<String,String> getRPTSendTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSendType");
	}
	
	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden = true,isNullable=false)
	@Column(name = "RPTFeedbackType",length=1)
	private String RPTFeedbackType;
	
	public static Map<String,String> getRPTFeedbackTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
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
	@IColumn(description="扩展字段1", isNullable = true)
	private String extend1;
	
	@Column(name = "lastUpdateDate", nullable = true)
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

	public void setOperationUser(coresystem.dto.UserInfo in) {
		operationUser = in;
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
	@IColumn(description="数据时间", isNullable = false,isSingleTagHidden=true)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}
	
	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}
	
	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTSendType(String in) {
		RPTSendType = in;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStrZYGXRZJLX() {
		return strZYGXRZJLX;
	}

	public void setStrZYGXRZJLX(String strZYGXRZJLX) {
		this.strZYGXRZJLX = strZYGXRZJLX;
	}

	public String getStrZYGXRZJHM() {
		return strZYGXRZJHM;
	}

	public void setStrZYGXRZJHM(String strZYGXRZJHM) {
		this.strZYGXRZJHM = strZYGXRZJHM;
	}

	public String getStrJZGX() {
		return strJZGX;
	}

	public void setStrJZGX(String strJZGX) {
		this.strJZGX = strJZGX;
	}

	public String getStrJZCYZJLX() {
		return strJZCYZJLX;
	}

	public void setStrJZCYZJLX(String strJZCYZJLX) {
		this.strJZCYZJLX = strJZCYZJLX;
	}

	public String getStrJZCYZJHM() {
		return strJZCYZJHM;
	}

	public void setStrJZCYZJHM(String strJZCYZJHM) {
		this.strJZCYZJHM = strJZCYZJHM;
	}

	public String getStrXXGXRQ() {
		return strXXGXRQ;
	}

	public void setStrXXGXRQ(String strXXGXRQ) {
		this.strXXGXRQ = strXXGXRQ;
	}

	public String getStrYLZD() {
		return strYLZD;
	}

	public void setStrYLZD(String strYLZD) {
		this.strYLZD = strYLZD;
	}

	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

}
