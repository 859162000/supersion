package zxptsystem.dto;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
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
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "EIS_CertificateInfo")
@IEntity(description= "客户证照附件信息")
public class EIS_CertificateInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	
	@Column(name = "profileName", length = 50, nullable = false)
	@IColumn(description="证件号码")
	private String profileName;
	
	@Column(name = "certificateType", length = 1, nullable = false)
	@IColumn(tagMethodName="getCertificateTypeTag",description="证照类别")
	private String certificateType;
	
	@Column(name = "recordTime",nullable = false)
	@IColumn(description="记录时间",isSingleTagHidden=true)
	private Date recordTime;
	
	@Column(name = "attachmentName", length = 50, nullable = true)
	@IColumn(description="文件名",isSingleTagHidden=true)
	private String strFileName;
	
	@Column(name = "strCustomerID", length = 50,nullable = false)
	@IColumn(description="客户代码",isSingleTagHidden=true)
	private String strCustomerID;	
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "byteData",length=1024*1024*5)
	@IColumn(description="证照资料附件")
	private byte[] byteData;
	
	@IColumn(isFileField=true, target="byteData")
	@Transient
	private File byteFile;
	
	@Column(name = "remark", length = 255)
	@IColumn(description="备注")
	private String remark;
	
	@Transient
	private String byteFileFileName;
	@Transient
	private String byteFileContentType;
	
	public byte[] getByteData() {
		if(null !=byteFile){
			this.byteData = TypeParse.getByteData(byteData, byteFile);
		}
		return byteData;
	}
	public void setByteData(byte[] byteData) {
		this.byteData = byteData;
	}
	public File getByteFile() {
		return byteFile;
	}
	public void setByteFile(File byteFile) {
		this.byteFile = byteFile;
	}
	
	public static Map<String,String> getCertificateTypeTag() {
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("E", "E-(新)营业证照（三证合一）");map.put("F", "F-(旧)营业执照");map.put("G", "G-(旧)组织机构代码证");map.put("H", "H-(旧)税务登记证");map.put("Z", "Z-其他证件");
		
		map.put("0", "0-身份证");map.put("1", "1-户口簿");map.put("2", "2-护照");map.put("3", "3-军官证");map.put("4", "4-士兵证");
		map.put("5", "5-港澳居民来往内地通行证");	map.put("6", "6-台湾同胞来往内地通行证");	map.put("7", "7-临时身份证");map.put("8", "8-外国人居留证");
		map.put("9", "9-警官证");map.put("A", "A-香港身份证");map.put("B", "B-澳门身份证");map.put("C", "C-台湾身份证");map.put("X", "X-其他证件");
		return map;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStrCustomerID(String strCustomerID) {
		this.strCustomerID = strCustomerID;
	}

	public String getStrCustomerID() {
		return strCustomerID;
	}
	public void setByteFileFileName(String byteFileFileName) {
		this.byteFileFileName = byteFileFileName;
	}
	public String getByteFileFileName() {
		return byteFileFileName;
	}
	public void setByteFileContentType(String byteFileContentType) {
		this.byteFileContentType = byteFileContentType;
	}
	public String getByteFileContentType() {
		return byteFileContentType;
	}
	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}
	public String getStrFileName() {
		if(null != this.getByteFileFileName())
			this.strFileName=this.getByteFileFileName();
		return strFileName;
	}
}
