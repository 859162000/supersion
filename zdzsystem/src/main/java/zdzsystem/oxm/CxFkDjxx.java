package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 司法强制措施信息
 *
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="djxx")
public class CxFkDjxx implements Serializable {
    private static final long serialVersionUID = -1877895799372341997L;

    @XStreamOmitField
    private String autoID;

    // @XStreamAsAttribute
    // @XStreamAlias("BDHM")
    // private String bdhmContent;//查询请求单号

    // @XStreamAsAttribute
    // @XStreamAlias("CCXH")
    // private String ccxh;//账户序号

    @XStreamAsAttribute
    @XStreamAlias("CSXH")
    private String csxh = "";//措施序号

    @XStreamAsAttribute
    @XStreamAlias("DJJZRQ")
    private String djjzrq = "";//冻结截止日

    @XStreamAsAttribute
    @XStreamAlias("DJJG")
    private String djjg = "";//冻结机关

    @XStreamAsAttribute
    @XStreamAlias("DJWH")
    private String djwh = "";//冻结文号

    @XStreamAsAttribute
    @XStreamAlias("DJJE")
    private String djje = "";//冻结金额/数额

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

    public String getCsxh() {
        return csxh;
    }

    public void setCsxh(String csxh) {
        this.csxh = csxh;
    }

    public String getDjjzrq() {
        return djjzrq;
    }

    public void setDjjzrq(String djjzrq) {
        this.djjzrq = djjzrq;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getDjwh() {
        return djwh;
    }

    public void setDjwh(String djwh) {
        this.djwh = djwh;
    }

    public String getDjje() {
        return djje;
    }

    public void setDjje(String djje) {
        this.djje = djje;
    }

    public String getBeiz() {
        return beiz;
    }

    public void setBeiz(String beiz) {
        this.beiz = beiz;
    }
}
