package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @author veggieg
 * @since 2016-09-27 16:11
 */
@XStreamAlias("zjinfo")
public class KzqqzjInfo implements Serializable {
    @XStreamAlias("BDHM")
    @XStreamAsAttribute
    private String bdhmContent;

    @XStreamAlias("XH")
    @XStreamAsAttribute
    private String xh;

    @XStreamAlias("GZZBM")
    @XStreamAsAttribute
    private String gzzbm;

    @XStreamAlias("GZZWJGS")
    @XStreamAsAttribute
    private String gzzwjgs;

    @XStreamAlias("GZZ")
    @XStreamAsAttribute
    private String gzzContent;

    @XStreamAlias("GWZBM")
    @XStreamAsAttribute
    private String gwzbm;

    @XStreamAlias("GWZWJGS")
    @XStreamAsAttribute
    private String gwzwjgs;

    @XStreamAlias("GWZ")
    @XStreamAsAttribute
    private String gwzContent;

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

    public String getGzzbm() {
        return gzzbm;
    }

    public void setGzzbm(String gzzbm) {
        this.gzzbm = gzzbm;
    }

    public String getGzzwjgs() {
        return gzzwjgs;
    }

    public void setGzzwjgs(String gzzwjgs) {
        this.gzzwjgs = gzzwjgs;
    }

    public String getGzzContent() {
        return gzzContent;
    }

    public void setGzzContent(String gzzContent) {
        this.gzzContent = gzzContent;
    }

    public String getGwzbm() {
        return gwzbm;
    }

    public void setGwzbm(String gwzbm) {
        this.gwzbm = gwzbm;
    }

    public String getGwzwjgs() {
        return gwzwjgs;
    }

    public void setGwzwjgs(String gwzwjgs) {
        this.gwzwjgs = gwzwjgs;
    }

    public String getGwzContent() {
        return gwzContent;
    }

    public void setGwzContent(String gwzContent) {
        this.gwzContent = gwzContent;
    }
}
