package report.dto;

import java.io.File;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import coresystem.dto.UserInfo;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "Notice")
@IEntity(description = "公告信息表")
public class Notice implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = 8640226438772013685L;

	@Column(name="strNoticeBoardTitle",length = 50,nullable=false)
	@IColumn(description="公告标题",isKeyName=true, isSpecialCharCheck=true)
	private String strNoticeBoardTitle;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode", nullable = true)
	@IColumn(isLoginTag=true)
	private UserInfo userInfo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="strNoticeBoardDate",length = 50,nullable=false)
	@IColumn(description="公告日期",isSystemDate=true)
	private Date strNoticeBoardDate;
	
	
	@Column(name="strNoticeBoardContent",length = 2000,nullable=false)
	@IColumn(description="公告内容", isSpecialCharCheck=true)
	private String strNoticeBoardContent;

	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "byteData")
	@IColumn(description="添加附件")
	private byte[] byteData;
	
	@IColumn(isFileField=true, target="byteData")
	@Transient
	private File byteFile;
	
	@Column(name = "strFileName", length = 100)
	@IColumn(description="附件名称")
	private String strFileName;
	
	
	@Id
	@Column(name = "noticeBoardInfoID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String noticeBoardInfoID;
	
	public String getStrNoticeBoardTitle() {
		return strNoticeBoardTitle;
	}

	public void setStrNoticeBoardTitle(String strNoticeBoardTitle) {
		this.strNoticeBoardTitle = strNoticeBoardTitle;
	}

	
	public void setStrNoticeBoardDate(String strNoticeBoardDate) {
		this.strNoticeBoardDate = TypeParse.parseDate(strNoticeBoardDate);
	}

	public Date getStrNoticeBoardDate() {
		return strNoticeBoardDate;
	}

	public void setStrNoticeBoardContent(String strNoticeBoardContent) {
		this.strNoticeBoardContent = strNoticeBoardContent;
	}

	public String getStrNoticeBoardContent() {
		return strNoticeBoardContent;
	}

	public void setByteData(byte[] byteData) {
		this.byteData = byteData;
	}

	public byte[] getByteData(){
		this.byteData = TypeParse.getByteData(byteData, byteFile);
		return byteData;
	}

	public void setByteFile(File byteFile) {
		this.byteFile = byteFile;
	}

	public File getByteFile() {
		return byteFile;
	}

	public void setStrFileName(String strFileName) {
	}

	public String getStrFileName() {
		return strFileName;
	}
	
	public void byteFileContentType(String[] contentType){	
	}
	
	public void byteFileFileName(String[] fileName){
		this.strFileName = fileName[0];
	} 
	
	public void setNoticeBoardInfoID(String noticeBoardInfoID) {
		this.noticeBoardInfoID = noticeBoardInfoID;
	}

	public String getNoticeBoardInfoID() {
		return noticeBoardInfoID;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

}



