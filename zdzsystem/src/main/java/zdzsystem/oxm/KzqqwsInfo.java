package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @author veggieg
 * @since 2016-09-27 15:55
 */
@XStreamAlias("wsinfo")
public class KzqqwsInfo implements Serializable {
    @XStreamAsAttribute
    @XStreamAlias("BDHM")
    private String bdhmContent;

    @XStreamAsAttribute
    @XStreamAlias("XH")
    private String xh;

    @XStreamAsAttribute
    @XStreamAlias("WJMC")
    private String wjmc;

    @XStreamAsAttribute
    @XStreamAlias("WJLX")
    private String wjlx;

    @XStreamAsAttribute
    @XStreamAlias("WSLB")
    private String wslb;

    @XStreamAsAttribute
    @XStreamAlias("MD5")
    private String md5;

    @XStreamAsAttribute
    @XStreamAlias("WSNR")
    private String wsnrContent;

    public String getBdhmContent() {
        return bdhmContent;
    }

    public void setBdhmContent(String bdhmContent) {
        this.bdhmContent = bdhmContent;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public String getWjlx() {
        return wjlx;
    }

    public void setWjlx(String wjlx) {
        this.wjlx = wjlx;
    }

    public String getWslb() {
        return wslb;
    }

    public void setWslb(String wslb) {
        this.wslb = wslb;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getWsnrContent() {
        return wsnrContent;
    }

    public void setWsnrContent(String wsnrContent) {
        this.wsnrContent = wsnrContent;
    }
}
