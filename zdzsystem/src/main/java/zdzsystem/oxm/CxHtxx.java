package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * @author veggieg
 * @since 2016-10-09
 */
@XStreamAlias("htxx")
public class CxHtxx implements Serializable {
    private static final long serialVersionUID = -3636336555562851423L;

    @XStreamOmitField
    private String autoID;

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

    public String getAutoID() {
        return autoID;
    }

    public void setAutoID(String autoID) {
        this.autoID = autoID;
    }

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
