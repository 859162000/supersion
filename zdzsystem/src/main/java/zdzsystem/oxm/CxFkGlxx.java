package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 关联子类账户信息
 *
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="glxx")
public class CxFkGlxx implements Serializable {
    private static final long serialVersionUID = -5625348886716547812L;

    @XStreamOmitField
    private String autoID;

    // @XStreamAsAttribute
    // @XStreamAlias("BDHM")
    // private String bdhmContent;//查询请求单号

    // @XStreamAsAttribute
    // @XStreamAlias("CCXH")
    // private String ccxh;//账户序号

    @XStreamAsAttribute
    @XStreamAlias("GLXH")
    private String glxh = "";//子账户序号

    @XStreamAsAttribute
    @XStreamAlias("ZKHZH")
    private String zkhzh = "";//主账户账号

    @XStreamAsAttribute
    @XStreamAlias("GLZHLB")
    private String glzhlb = "";//子账户类别

    @XStreamAsAttribute
    @XStreamAlias("GLZHHM")
    private String glzhhm = "";//子账户账号

    @XStreamAsAttribute
    @XStreamAlias("BZ")
    private String bz = "";//计价币种

    @XStreamAsAttribute
    @XStreamAlias("YE")
    private String ye = "0.00";//资产数额

    @XStreamAsAttribute
    @XStreamAlias("KYYE")
    private String kyye = "0.00";//可用资产数额

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

    public String getGlxh() {
        return glxh;
    }

    public void setGlxh(String glxh) {
        this.glxh = glxh;
    }

    public String getZkhzh() {
        return zkhzh;
    }

    public void setZkhzh(String zkhzh) {
        this.zkhzh = zkhzh;
    }

    public String getGlzhlb() {
        return glzhlb;
    }

    public void setGlzhlb(String glzhlb) {
        this.glzhlb = glzhlb;
    }

    public String getGlzhhm() {
        return glzhhm;
    }

    public void setGlzhhm(String glzhhm) {
        this.glzhhm = glzhhm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getYe() {
        return ye;
    }

    public void setYe(String ye) {
        this.ye = ye;
    }

    public String getKyye() {
        return kyye;
    }

    public void setKyye(String kyye) {
        this.kyye = kyye;
    }

    public String getBeiz() {
        return beiz;
    }

    public void setBeiz(String beiz) {
        this.beiz = beiz;
    }
}
