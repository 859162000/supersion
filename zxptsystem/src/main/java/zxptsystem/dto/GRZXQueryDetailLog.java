package zxptsystem.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "GRZXQueryDetailLog")
@IEntity(description="个人征信查询日志详情")
public class GRZXQueryDetailLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8303127717611500751L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	
	@IColumn(isNullable = false,description="日志信息")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gRZXQueryLogId")
	private GRZXQueryLog gRZXQueryLogId;
	
	@Column(name = "strRHActNo", length = 50)
	@IColumn(description="人行账户")
	private String strRHActNo;
	
	@Column(name = "strRHActNoPwd", length = 50)
	@IColumn(description="人行账户密码")
	private String strRHActNoPwd;
	
	@Column(name="strUrl", length = 500)
	@IColumn(description="URL")
	private String strUrl;
	
	@Column(name="dtBeginDate")
	@IColumn(description="查询开始时间")
	private Timestamp dtBeginDate;
	
	@Column(name="dtEndDate")
	@IColumn(description="查询结束时间")
	private Timestamp dtEndDate;
	
	@Column(name = "strQueryStatus", length = 10)
	@IColumn(description="查询状态")
	private String strQueryStatus;
	
	@Column(name = "strException", length = 100)
	@IColumn(description="异常原因")
	private String strException;
	
	@Column(name = "strRemark", length = 100)
	@IColumn(description="备注")
	private String strRemark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStrRHActNo() {
		return strRHActNo;
	}

	public void setStrRHActNo(String strRHActNo) {
		this.strRHActNo = strRHActNo;
	}

	public String getStrRHActNoPwd() {
		return strRHActNoPwd;
	}

	public void setStrRHActNoPwd(String strRHActNoPwd) {
		this.strRHActNoPwd = strRHActNoPwd;
	}

	public String getStrUrl() {
		return strUrl;
	}

	public void setStrUrl(String strUrl) {
		if(strUrl.length()>500){
			strUrl=strUrl.substring(0,99);
		}
		this.strUrl = strUrl;
	}

	public Timestamp getDtBeginDate() {
		return dtBeginDate;
	}

	public void setDtBeginDate(Timestamp dtBeginDate) {
		this.dtBeginDate = dtBeginDate;
	}

	public Timestamp getDtEndDate() {
		return dtEndDate;
	}

	public void setDtEndDate(Timestamp dtEndDate) {
		this.dtEndDate = dtEndDate;
	}

	public String getStrQueryStatus() {
		return strQueryStatus;
	}

	public void setStrQueryStatus(String strQueryStatus) {
		this.strQueryStatus = strQueryStatus;
	}

	public String getStrException() {
		return strException;
	}

	public void setStrException(String strException) {
		if(null!=strException && strException.length()>100){
			strException=strException.substring(0,99);
		}
		this.strException = strException;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		if(null!=strRemark && strRemark.length()>100){
			strRemark=strRemark.substring(0,99);
		}
		this.strRemark = strRemark;
	}

	public void setGRZXQueryLogId(GRZXQueryLog gRZXQueryLogId) {
		this.gRZXQueryLogId = gRZXQueryLogId;
	}

	public GRZXQueryLog getGRZXQueryLogId() {
		return gRZXQueryLogId;
	}	
}
