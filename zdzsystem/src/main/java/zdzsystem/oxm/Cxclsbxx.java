package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @author veggieg
 * @since 2016-09-27 17:20
 */
@XStreamAlias("htxx")
public class Cxclsbxx implements Serializable {
    @XStreamAsAttribute
    @XStreamAlias("BDHM")
    private String bdhmContent;

    @XStreamAsAttribute
    @XStreamAlias("XCHTYY")
    private String xchtyy;

    @XStreamAsAttribute
    @XStreamAlias("XCHTBZ")
    private String xchtbz;

    @XStreamAsAttribute
    @XStreamAlias("XCHTR")
    private String xchtr;

    @XStreamAsAttribute
    @XStreamAlias("XCHTDH")
    private String xchtdh;

    public String getBdhmContent() {
        return bdhmContent;
    }

    public void setBdhmContent(String bdhmContent) {
        this.bdhmContent = bdhmContent;
    }

    public String getXchtyy() {
        return xchtyy;
    }

    public void setXchtyy(String xchtyy) {
        this.xchtyy = xchtyy;
    }

    public String getXchtbz() {
        return xchtbz;
    }

    public void setXchtbz(String xchtbz) {
        this.xchtbz = xchtbz;
    }

    public String getXchtr() {
        return xchtr;
    }

    public void setXchtr(String xchtr) {
        this.xchtr = xchtr;
    }

    public String getXchtdh() {
        return xchtdh;
    }

    public void setXchtdh(String xchtdh) {
        this.xchtdh = xchtdh;
    }
}
