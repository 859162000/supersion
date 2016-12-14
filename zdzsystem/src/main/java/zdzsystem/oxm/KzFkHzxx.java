package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * 金融机构控制反馈信息 - 回执信息
 *
 * @author veggieg
 * @since 2016-10-18
 */
@XStreamAlias(value = "hzxx")
public class KzFkHzxx implements Serializable {
    private static final long serialVersionUID = -4359834708670345859L;

    @XStreamAsAttribute
    @XStreamAlias("BDHM")
    private String bdhm;

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
    @XStreamAlias("WSNR")
    private String wsnr;

    @XStreamAsAttribute
    @XStreamAlias("MD5")
    private String md5;

    public String getBdhm() {
        return bdhm;
    }

    public void setBdhm(String bdhm) {
        this.bdhm = bdhm;
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

    public String getWsnr() {
        return wsnr;
    }

    public void setWsnr(String wsnr) {
        this.wsnr = wsnr;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
