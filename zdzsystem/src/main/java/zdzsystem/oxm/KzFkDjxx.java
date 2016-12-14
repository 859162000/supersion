package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * 金融机构控制反馈信息 - 在先冻结信息（判断无后则不反馈）
 *
 * @author veggieg
 * @since 2016-10-18
 */
@XStreamAlias(value = "djxx")
public class KzFkDjxx implements Serializable {
    private static final long serialVersionUID = -8712938516999149362L;

    // @XStreamAsAttribute
    // @XStreamAlias("BDHM")
    // private String bdhm;
    
    // @XStreamAsAttribute
    // @XStreamAlias("CCXH")
    // private String ccxh;
    
    @XStreamAsAttribute
    @XStreamAlias("CSXH")
    private String csxh;
    
    @XStreamAsAttribute
    @XStreamAlias("DJJG")
    private String djjg;
    
    @XStreamAsAttribute
    @XStreamAlias("DJWH")
    private String djwh;
    
    @XStreamAsAttribute
    @XStreamAlias("DJJE")
    private String djje;
    
    @XStreamAsAttribute
    @XStreamAlias("DJJZRQ")
    private String djjzrq;
    
    @XStreamAsAttribute
    @XStreamAlias("BEIZ")
    private String beiz;
    
    @XStreamAsAttribute
    @XStreamAlias("YXSCQLX")
    private String yxscqlx;

    public String getCsxh() {
        return csxh;
    }

    public void setCsxh(String csxh) {
        this.csxh = csxh;
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

    public String getDjjzrq() {
        return djjzrq;
    }

    public void setDjjzrq(String djjzrq) {
        this.djjzrq = djjzrq;
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
