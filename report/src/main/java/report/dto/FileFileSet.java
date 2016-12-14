package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.FileInfo;
import extend.dto.Suit;
import framework.interfaces.IColumn;

@Entity
@Table(name = "FileFileSet")
public class FileFileSet implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -8745126630520852722L;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strFileCode", nullable = false)
	private FileInfo fileInfo;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strFileSetCode", nullable = false)
	private FileInfoSet strFileSetCode;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suit")
	@IColumn(description="主题")
	private Suit suit;
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setStrFileSetCode(FileInfoSet strFileSetCode) {
		this.strFileSetCode = strFileSetCode;
	}

	public FileInfoSet getStrFileSetCode() {
		return strFileSetCode;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

}

