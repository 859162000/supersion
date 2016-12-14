package zxptsystem.dto;

import java.io.Serializable;

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
@Table(name="GR_F1")
@IEntity(description="声明信息-本人声明")
public class GR_F1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8501435929999690449L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="StaContent", length=255,nullable=true)
	@IColumn(description="声明内容",isNullable=true)
	private String StaContent;

	@Column(name="AddDate1", length=20,nullable=true)
	@IColumn(description="添加日期",isNullable=true)
	private String AddDate;

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public GR_A1 getReportNo() {
		return ReportNo;
	}

	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}

	public String getStaContent() {
		return StaContent;
	}

	public void setStaContent(String staContent) {
		StaContent = staContent;
	}

	public String getAddDate() {
		return AddDate;
	}

	public void setAddDate(String addDate) {
		AddDate = addDate;
	}



}
