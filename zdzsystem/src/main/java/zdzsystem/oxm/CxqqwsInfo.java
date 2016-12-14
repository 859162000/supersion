package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @author veggieg
 * @since 2016-09-27 14:29
 */
@XStreamAlias("wsinfo")
public class CxqqwsInfo implements Serializable {
    @XStreamAsAttribute
    @XStreamAlias("WSBH")
    private String wsbh;

    @XStreamAsAttribute
    @XStreamAlias("XH")
    private String xh;

    @XStreamAsAttribute
    @XStreamAlias("WSLB")
    private String wslb;

    @XStreamAsAttribute
    @XStreamAlias("WJMC")
    private String wjmc;

    @XStreamAsAttribute
    @XStreamAlias("WJLX")
    private String wjlx;

    @XStreamAsAttribute
    @XStreamAlias("WSNR")
    private String wsnrContent;

    @XStreamAsAttribute
    @XStreamAlias("MD5")
    private String md5;

    public String getWsbh() {
        return wsbh;
    }

    public void setWsbh(String wsbh) {
        this.wsbh = wsbh;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getWslb() {
        return wslb;
    }

    public void setWslb(String wslb) {
        this.wslb = wslb;
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

    public String getWsnrContent() {
        return wsnrContent;
    }

    public void setWsnrContent(String wsnrContent) {
        this.wsnrContent = wsnrContent;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
