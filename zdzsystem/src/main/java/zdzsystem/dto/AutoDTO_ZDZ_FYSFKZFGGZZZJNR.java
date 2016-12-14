package zdzsystem.dto;

import java.io.File;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "ZDZ_FYSFKZFGGZZZJNR")
@IEntity(description= "法院司法控制法官工作证证件内容")
public class AutoDTO_ZDZ_FYSFKZFGGZZZJNR implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BDHM", nullable = true)
	@IColumn(isNullable = false,description="控制请求单号")
	private AutoDTO_ZDZ_KZQQJTNR BDHM;

	public AutoDTO_ZDZ_KZQQJTNR getBDHM() {
		return BDHM;
	}

	public void setBDHM(AutoDTO_ZDZ_KZQQJTNR bDHM) {
		BDHM = bDHM;
	}

	@Column(name = "XH", length = 255, nullable = true)
	@IColumn(description="序号")
	private String XH;

	public String getXH() {
		return XH;
	}

	public void setXH(String in) {
		XH = in;
	}

	@Column(name = "GZZBM", length = 50, nullable = true)
	@IColumn(description="工作证编号")
	private String GZZBM;

	public String getGZZBM() {
		return GZZBM;
	}

	public void setGZZBM(String in) {
		GZZBM = in;
	}

	@Column(name = "GZZWJGS", length = 50, nullable = true)
	@IColumn(description="工作证格式")
	private String GZZWJGS;

	public String getGZZWJGS() {
		return GZZWJGS;
	}

	public void setGZZWJGS(String in) {
		GZZWJGS = in;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "GZZ",length=1024*1024*5)
	@IColumn(description="工作证")
	private byte[] GZZ;
	
	public byte[] getGZZ() {
		if(null !=byteFile){
			this.GZZ = TypeParse.getByteData(GZZ, byteFile);
		}
		return GZZ;
	}
	public void setGZZ(byte[] GZZ) {
		this.GZZ = GZZ;
	}
	
	@IColumn(isFileField=true, target="GZZ")
	@Transient
	private File byteFile;
	
	public File getByteFile() {
		return byteFile;
	}
	public void setByteFile(File byteFile) {
		this.byteFile = byteFile;
	}
	
	@Transient
	private String byteFileFileName;
	
	public void setByteFileFileName(String byteFileFileName) {
		this.byteFileFileName = byteFileFileName;
	}
	public String getByteFileFileName() {
		return byteFileFileName;
	}
	
	@Transient
	private String byteFileContentType;
	
	public void setByteFileContentType(String byteFileContentType) {
		this.byteFileContentType = byteFileContentType;
	}
	public String getByteFileContentType() {
		return byteFileContentType;
	}
	
	@Column(name = "BatchNumber", length = 50, nullable = true)
	@IColumn(description="批次号")
	private String BatchNumber;

	public String getBatchNumber() {
		return BatchNumber;
	}

	public void setBatchNumber(String in) {
		BatchNumber = in;
	}

	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	private String autoID;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String in) {
		autoID = in;
	}

}

