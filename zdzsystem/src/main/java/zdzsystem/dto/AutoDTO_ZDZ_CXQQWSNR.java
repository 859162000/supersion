package zdzsystem.dto;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

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

import java.io.File;


@Entity
@Table(name = "ZDZ_CXQQWSNR")
@IEntity(description = "查询请求文书内容")
public class AutoDTO_ZDZ_CXQQWSNR implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "WSBH", length = 100, nullable = true)
    @IColumn(description = "文书编号")
    private String WSBH;

    public String getWSBH() {
        return WSBH;
    }

    public void setWSBH(String in) {
        WSBH = in;
    }

    @Column(name = "XH", length = 255, nullable = false)
    @IColumn(description = "序号")
    private String XH;

    public String getXH() {
        return XH;
    }

    public void setXH(String in) {
        XH = in;
    }

    @Column(name = "WJMC", length = 200, nullable = true)
    @IColumn(description = "文件名称")
    private String WJMC;

    public String getWJMC() {
        return WJMC;
    }

    public void setWJMC(String in) {
        WJMC = in;
    }

    @Column(name = "WJLX", length = 50, nullable = true)
    @IColumn(description = "文件类型")
    private String WJLX;

    public String getWJLX() {
        return WJLX;
    }

    public void setWJLX(String in) {
        WJLX = in;
    }

    @Column(name = "WSLB", length = 50, nullable = true)
    @IColumn(description = "文书类别")
    private String WSLB;

    public String getWSLB() {
        return WSLB;
    }

    public void setWSLB(String in) {
        WSLB = in;
    }

    @Column(name = "MD5", length = 100, nullable = true)
    @IColumn(description = "文书MD5")
    private String MD5;

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String in) {
        MD5 = in;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "WSNR", length = 1024 * 1024 * 5)
    @IColumn(description = "文书内容")
    private byte[] WSNR;

    public byte[] getWSNR() {
        if (null != byteFile) {
            this.WSNR = TypeParse.getByteData(WSNR, byteFile);
        }
        return WSNR;
    }

    public void setWSNR(byte[] WSNR) {
        this.WSNR = WSNR;
    }

    @IColumn(isFileField = true, target = "WSNR")
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
    @IColumn(description = "批次号")
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

