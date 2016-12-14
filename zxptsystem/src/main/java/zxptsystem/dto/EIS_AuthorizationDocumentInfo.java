package zxptsystem.dto;

import java.io.File;
import java.io.Serializable;
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
@Table(name = "EIS_AuthorizationDocumentInfo")
@IEntity(description= "客户授权书附件信息")
public class EIS_AuthorizationDocumentInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid") 
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	
	@Column(name = "profileName", length = 50, nullable = false)
	@IColumn(description="授权资料名称")
	private String profileName;
	
	@Column(name = "profileType", length = 1, nullable = false)
	@IColumn(tagMethodName="getProfileTypeTag",description="授权人身份类型",isSingleTagHidden=true)
	private String profileType;
		
	@Column(name = "attachmentName", length = 50, nullable = true)
	@IColumn(description="文件名",isSingleTagHidden=true)
	private String strFileName;
	
	@Column(name = "authorizationDate",length = 10, nullable = false)
	@IColumn(description="授权开始日期",isNullable=false)
	private String authorizationDate;
	
	@Column(name = "authorizationEndDate",length = 10)
	@IColumn(description="授权结束日期")
	private String authorizationEndDate;
	
	@Column(name = "strCustomerID",length = 50,nullable = false)
	@IColumn(description="授权客户代码",isSingleTagHidden=true)
	private String strCustomerID;
	
	@Column(name = "remark", length = 255)
	@IColumn(description="备注")
	private String remark;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "byteData",length=1024*1024*5)
	@IColumn(description="授权资料附件")
	private byte[] byteData;
	
	@IColumn(isFileField=true, target="byteData")
	@Transient
	private File byteFile;
	
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
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	
	public String getStrCustomerID() {
		return strCustomerID;
	}
	public void setStrCustomerID(String strCustomerID) {
		this.strCustomerID = strCustomerID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public static Map<String,String> getProfileTypeTag() {
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("1", "1-企业");
		map.put("2", "2-个人");
		return map;
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
	public void setAuthorizationDate(String authorizationDate) {
		this.authorizationDate = authorizationDate;
	}
	public String getAuthorizationDate() {
		return authorizationDate;
	}
	public void setAuthorizationEndDate(String authorizationEndDate) {
		this.authorizationEndDate = authorizationEndDate;
	}
	public String getAuthorizationEndDate() {
		return authorizationEndDate;
	}
}
