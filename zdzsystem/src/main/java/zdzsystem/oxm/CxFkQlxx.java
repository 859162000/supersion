package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 共有权/优先权信息
 *
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="qlxx")
public class CxFkQlxx implements Serializable {
    private static final long serialVersionUID = 2489029262688253287L;

    @XStreamOmitField
    private String autoID;

    // @XStreamAsAttribute
    // @XStreamAlias("BDHM")
    // private String bdhmContent;//查询请求单号

    // @XStreamAsAttribute
    // @XStreamAlias("CCXH")
    // private String ccxh;//账户序号

    @XStreamAsAttribute
    @XStreamAlias("XH")
    private String xh = "";//序号

    @XStreamAsAttribute
    @XStreamAlias("QLLX")
    private String qllx = "";//权利类型

    @XStreamAsAttribute
    @XStreamAlias("QLR")
    private String qlr = "";//权利人

    @XStreamAsAttribute
    @XStreamAlias("QLJE")
    private String qlje = "";//权利金额/数额

    @XStreamAsAttribute
    @XStreamAlias("QLRDZ")
    private String qlrdz = "";//权利人通讯地址

    @XStreamAsAttribute
    @XStreamAlias("QLRLXFS")
    private String qlrlxfs = "";//权利人联系方式

    @XStreamAsAttribute
    @XStreamAlias("BEIZ")
    private String beiz = "";//备注

    public String getAutoID() {
        return autoID;
    }

    public void setAutoID(String autoID) {
        this.autoID = autoID;
    }

    // public String getBdhmContent() {
    //     return bdhmContent;
    // }
    //
    // public void setBdhmContent(String bdhmContent) {
    //     this.bdhmContent = bdhmContent;
    // }

    // public String getCcxh() {
    //     return ccxh;
    // }
    //
    // public void setCcxh(String ccxh) {
    //     this.ccxh = ccxh;
    // }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlje() {
        return qlje;
    }

    public void setQlje(String qlje) {
        this.qlje = qlje;
    }

    public String getQlrdz() {
        return qlrdz;
    }

    public void setQlrdz(String qlrdz) {
        this.qlrdz = qlrdz;
    }

    public String getQlrlxfs() {
        return qlrlxfs;
    }

    public void setQlrlxfs(String qlrlxfs) {
        this.qlrlxfs = qlrlxfs;
    }

    public String getBeiz() {
        return beiz;
    }

    public void setBeiz(String beiz) {
        this.beiz = beiz;
    }
}
