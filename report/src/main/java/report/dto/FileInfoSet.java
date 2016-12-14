package report.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import framework.interfaces.IColumn;

@Entity
@Table(name = "FileInfoSet")
public class FileInfoSet implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -7811201783135614940L;

	@Id
	@Column(name = "strFileSetCode", length = 50)
	@IColumn(description="文件集代码")
	private String strFileSetCode;
	
	@Column(name = "strFileSetName", length = 50)
	@IColumn(description="文件集名称")
	private String strFileSetName;
	

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "strFileSetCode")
	private Set<FileFileSet> fileInfoAndList = new HashSet<FileFileSet>(0);
	
	
	
	// 用于指定显示列表相关参数,显示列表为userRoles属性,显示关联表的roleInfo列
	@Transient
	@IColumn(isIdListField = true, target="fileInfoAndList", mappedBy = "fileInfo")
	private String[] strFileCodeIdList;



	public void setStrFileCodeIdList(String[] strFileCodeIdList) {
		this.strFileCodeIdList = strFileCodeIdList;
	}



	public String[] getStrFileCodeIdList() {
		return strFileCodeIdList;
	}





	public void setStrFileSetName(String strFileSetName) {
		this.strFileSetName = strFileSetName;
	}



	public String getStrFileSetName() {
		return strFileSetName;
	}



	public void setStrFileSetCode(String strFileSetCode) {
		this.strFileSetCode = strFileSetCode;
	}



	public String getStrFileSetCode() {
		return strFileSetCode;
	}



	public void setFileInfoAndList(Set<FileFileSet> fileInfoAndList) {
		this.fileInfoAndList = fileInfoAndList;
	}



	public Set<FileFileSet> getFileInfoAndList() {
		return fileInfoAndList;
	}



}

