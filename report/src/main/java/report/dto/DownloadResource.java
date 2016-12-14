package report.dto;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import extend.dto.Suit;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "DownloadResource")
@IEntity(description="下载资源库")
public class DownloadResource implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -2438009268138038785L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@Column(name = "startTime", length = 50)
	@IColumn(description="生成开始时间")
	private Timestamp startTime;
	
	@Column(name = "endTime", length = 50)
	@IColumn(description="生成结束时间")
	private Timestamp endTime;
	
	@Column(name = "fileName", length = 4000)
	@IColumn(description="生成文件")
	private String fileName;
	
	@Column(name = "fileType", length = 50)
	@IColumn(tagMethodName="getDownLoadFileType", description="文件类型", isNullable = false)
	private String fileType;
	
	@Column(name = "proposer", length = 50)
	@IColumn(description="生成人员")
	private String proposer;
	
	@Column(name = "downloadOperator", length = 50)
	@IColumn(description="下载操作员")
	private String downloadOperator;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suit")
	@IColumn(description="主题")
	private Suit suit;

	@Column(name = "fileDiscription", length = 500)
	@IColumn(description="文件描述")
	private String fileDiscription;

	public static Map<String,String> getDownLoadFileType(){
		return ShowContext.getInstance().getShowEntityMap().get("downLoadFileType");
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getDownloadOperator() {
		return downloadOperator;
	}

	public void setDownloadOperator(String downloadOperator) {
		this.downloadOperator = downloadOperator;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public void setStartTime(String startTime) {
		this.startTime = TypeParse.parseTimestamp(startTime);
	}
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = TypeParse.parseTimestamp(endTime);
	}
	
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setFileDiscription(String fileDiscription) {
		this.fileDiscription = fileDiscription;
	}

	public String getFileDiscription() {
		return fileDiscription;
	}

}
