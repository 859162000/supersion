package zdzsystem.dto;

import java.io.File;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "ZDZ_CXQQFGGWZZJRN")
@IEntity(description= "查询请求法官公务证证件内容")
public class AutoDTO_ZDZ_CXQQFGGWZZJRN implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "XH", length = 255, nullable = false)
	@IColumn(description="序号")
	private String XH;

	public String getXH() {
		return XH;
	}

	public void setXH(String in) {
		XH = in;
	}

	@Column(name = "ZJMC", length = 100, nullable = true)
	@IColumn(description="证件名称")
	private String ZJMC;

	public String getZJMC() {
		return ZJMC;
	}

	public void setZJMC(String in) {
		ZJMC = in;
	}

	@Column(name = "GWZBM", length = 50, nullable = true)
	@IColumn(description="公务证编号")
	private String GWZBM;

	public String getGWZBM() {
		return GWZBM;
	}

	public void setGWZBM(String in) {
		GWZBM = in;
	}

	@Column(name = "GWZWJGS", length = 50, nullable = true)
	@IColumn(description="公务证格式")
	private String GWZWJGS;

	public String getGWZWJGS() {
		return GWZWJGS;
	}

	public void setGWZWJGS(String in) {
		GWZWJGS = in;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "GWZ",length=1024*1024*5)
	@IColumn(description="公务证")
	private byte[] GWZ;
	
	public byte[] getGWZ() {
		if(null !=byteFile){
			this.GWZ = TypeParse.getByteData(GWZ, byteFile);
		}
		return GWZ;
	}
	public void setGWZ(byte[] GWZ) {
		this.GWZ = GWZ;
	}
	
	@IColumn(isFileField=true, target="GWZ")
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

