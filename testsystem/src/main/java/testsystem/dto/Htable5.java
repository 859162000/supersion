package testsystem.dto;

import java.io.File;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;

/**
 * Htable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HTABLE5")
public class Htable5 implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "field1", length = 50)
	private String field1;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "byteData")
	@IColumn(description="文件1")
	private byte[] byteData;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name="REMARK", columnDefinition="CLOB") //text
	private String longString;
	
	@IColumn(isFileField=true, target="byteData")
	@Transient
	private File byteFile;
	
	@Column(name = "strFileName", length = 100)
	@IColumn(description="文件1名称", isSingleTagHidden = true)
	private String strFileName;

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField1() {
		return field1;
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

	public void setLongString(String longString) {
		this.longString = longString;
	}

	public String getLongString() {
		return longString;
	}
}