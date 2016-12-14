package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 金融机构控制反馈信息 - 优先权信息（判断无后则不反馈）
 *
 * @author veggieg
 * @since 2016-10-18
 */
@XStreamAlias(value = "qlxx")
public class KzFkQlxx implements Serializable {
    private static final long serialVersionUID = 2667821109493046385L;

    @XStreamOmitField
    private String yxscqlx;//TODO 优先受偿权类型  怎么取?

    // @XStreamAsAttribute
    // @XStreamAlias("BDHM")
    // private String BDHM;

    // @XStreamAsAttribute
    // @XStreamAlias("CCXH")
    // private String ccxh;

    @XStreamAsAttribute
    @XStreamAlias("XH")
    private String xh;

    @XStreamAsAttribute
    @XStreamAlias("QLLX")
    private String qllx;

    @XStreamAsAttribute
    @XStreamAlias("QLR")
    private String qlr;

    @XStreamAsAttribute
    @XStreamAlias("QLJE")
    private String qlje;

    @XStreamAsAttribute
    @XStreamAlias("QLRDZ")
    private String qlrdz;

    @XStreamAsAttribute
    @XStreamAlias("QLRLXFS")
    private String qlrlxfs;

    @XStreamAsAttribute
    @XStreamAlias("BEIZ")
    private String beiz;

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

    public String getYxscqlx() {
        return yxscqlx;
    }

    public void setYxscqlx(String yxscqlx) {
        this.yxscqlx = yxscqlx;
    }
}
