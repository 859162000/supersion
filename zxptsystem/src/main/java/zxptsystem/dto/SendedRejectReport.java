package zxptsystem.dto;

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


import extend.dto.Suit;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "SendedRejectReport")
@IEntity(description= "已报送报文驳回")
public class SendedRejectReport  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strSuitCode", nullable = false)
	private Suit suit;
	
	@Column(name="strReportType",length = 250, nullable = false)
	@IColumn(description="报文类别")
	private String strReportType;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtDate", nullable = true)
	@IColumn(description="生成日期")
	private Date dtDate;
	
	@Column(name="operationUser",length = 50, nullable = false)
	@IColumn(description="生成人员")
	private String operationUser;
	
	@Column(name="strFileName",length = 250, nullable = false)
	@IColumn(description="报文名")
	private String strFileName;
	
	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态")
	@Column(name = "RPTFeedbackType", nullable =true)
	private String RPTFeedbackType;

	public static Map<String,String> getRPTFeedbackTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "rejectDate", nullable = true)
	@IColumn(description="驳回日期")
	private Date rejectDate;
	
	@Column(name="rejectUser",length = 50, nullable = true)
	@IColumn(description="驳回人员")
	private String rejectUser;
	

	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoID;

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public String getStrReportType() {
		return strReportType;
	}

	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(Date dtDate) {
		this.dtDate = dtDate;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public String getStrFileName() {
		return strFileName;
	}

	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}

	public String getAutoID() {
		return autoID;
	}

	public Date getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(Date rejectDate) {
		this.rejectDate = rejectDate;
	}

	public String getRejectUser() {
		return rejectUser;
	}

	public void setRejectUser(String rejectUser) {
		this.rejectUser = rejectUser;
	}
}
