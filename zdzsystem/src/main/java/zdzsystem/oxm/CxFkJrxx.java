package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 金融资产信息
 *
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="jrxx")
public class CxFkJrxx implements Serializable {
    private static final long serialVersionUID = 1569562476178924850L;

    @XStreamOmitField
    private String autoID;

    // @XStreamAsAttribute
    // @XStreamAlias("BDHM")
    // private String bdhmContent;//查询请求单号

    // @XStreamAsAttribute
    // @XStreamAlias("CCXH")
    // private String ccxh;//账户序号

    @XStreamAsAttribute
    @XStreamAlias("ZCXH")
    private String zcxh = "";//金融资产序号

    @XStreamAsAttribute
    @XStreamAlias("ZCMC")
    private String zcmc = "";//金融资产名称

    @XStreamAsAttribute
    @XStreamAlias("ZCLX")
    private String zclx = "";//金融资产类型

    @XStreamAsAttribute
    @XStreamAlias("ZCGLR")
    private String zcglr = "";//资产管理人

    @XStreamAsAttribute
    @XStreamAlias("ZCKFJY")
    private String zckfjy = "";//资产可否通过银行交易

    @XStreamAsAttribute
    @XStreamAlias("ZCJYXZ")
    private String zcjyxz = "";//资产交易限制类型

    @XStreamAsAttribute
    @XStreamAlias("XZXCRQ")
    private String xzxcrq = "";//资产交易限制消除时间

    @XStreamAsAttribute
    @XStreamAlias("JLDW")
    private String jldw = "";//计量单位

    @XStreamAsAttribute
    @XStreamAlias("BZ")
    private String bz = "";//计价币种

    @XStreamAsAttribute
    @XStreamAlias("ZCDWJG")
    private String zcdwjg = "";//资产单位价格

    @XStreamAsAttribute
    @XStreamAlias("SE")
    private String se = "0.00";//资产数额

    @XStreamAsAttribute
    @XStreamAlias("KYSE")
    private String kyse = "0.00";//可用资产数额

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

    // public void setBdhmContent(String bdhmContent) {
    //     this.bdhmContent = bdhmContent;
    // }

    // public String getCcxh() {
    //     return ccxh;
    // }

    // public void setCcxh(String ccxh) {
    //     this.ccxh = ccxh;
    // }

    public String getZcxh() {
        return zcxh;
    }

    public void setZcxh(String zcxh) {
        this.zcxh = zcxh;
    }

    public String getZcmc() {
        return zcmc;
    }

    public void setZcmc(String zcmc) {
        this.zcmc = zcmc;
    }

    public String getZclx() {
        return zclx;
    }

    public void setZclx(String zclx) {
        this.zclx = zclx;
    }

    public String getZcglr() {
        return zcglr;
    }

    public void setZcglr(String zcglr) {
        this.zcglr = zcglr;
    }

    public String getZckfjy() {
        return zckfjy;
    }

    public void setZckfjy(String zckfjy) {
        this.zckfjy = zckfjy;
    }

    public String getZcjyxz() {
        return zcjyxz;
    }

    public void setZcjyxz(String zcjyxz) {
        this.zcjyxz = zcjyxz;
    }

    public String getXzxcrq() {
        return xzxcrq;
    }

    public void setXzxcrq(String xzxcrq) {
        this.xzxcrq = xzxcrq;
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getZcdwjg() {
        return zcdwjg;
    }

    public void setZcdwjg(String zcdwjg) {
        this.zcdwjg = zcdwjg;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public String getKyse() {
        return kyse;
    }

    public void setKyse(String kyse) {
        this.kyse = kyse;
    }

    public String getBeiz() {
        return beiz;
    }

    public void setBeiz(String beiz) {
        this.beiz = beiz;
    }
}
