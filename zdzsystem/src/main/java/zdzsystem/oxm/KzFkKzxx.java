package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;
import java.util.List;

/**
 * 金融机构控制反馈信息 - 控制处理结果
 *
 * @author veggieg
 * @since 2016-10-17
 */
@XStreamAlias(value = "kzxx")
public class KzFkKzxx implements Serializable {
    private static final long serialVersionUID = -2706282930047228836L;

    @XStreamOmitField
    private String kzlx;// 控制类型 AutoDTO_ZDZ_BZXRZHXX#KZLX

    @XStreamOmitField
    private String kzcs;// 控制措施 AutoDTO_ZDZ_BZXRZHXX#KZCS

    @XStreamOmitField
    private String zcmc;// 金融资产名称 AutoDTO_ZDZ_BZXRZHXX#ZCMC

    @XStreamAsAttribute
    @XStreamAlias("BDHM")
    private String bdhmContent;

    @XStreamAsAttribute
    @XStreamAlias("CCXH")
    private String ccxh;

    @XStreamAsAttribute
    @XStreamAlias("KHZH")
    private String khzh;

    @XStreamAsAttribute
    @XStreamAlias("KZNR")
    private String kznr;

    @XStreamAsAttribute
    @XStreamAlias("KZZT")
    private String kzzt;

    @XStreamAsAttribute
    @XStreamAlias("YE")
    private String ye;

    @XStreamAsAttribute
    @XStreamAlias("KYYE")
    private String kyye;

    @XStreamAsAttribute
    @XStreamAlias("CSKSRQ")
    private String csksrq;

    @XStreamAsAttribute
    @XStreamAlias("CSJSRQ")
    private String csjsrq;

    @XStreamAsAttribute
    @XStreamAlias("DJXE")
    private String djxe;

    @XStreamAsAttribute
    @XStreamAlias("SKJE")
    private String skje;

    @XStreamAsAttribute
    @XStreamAlias("CESKJE")
    private String ceskje;

    @XStreamAsAttribute
    @XStreamAlias("WNKZYY")
    private String wnkzyy;

    @XStreamAsAttribute
    @XStreamAlias("BEIZ")
    private String beiz;

    @XStreamImplicit
    private List<KzFkDjxxList> djxxLists;

    @XStreamImplicit
    private List<KzFkQlxxList> qlxxLists;

    public String getKzlx() {
        return kzlx;
    }

    public void setKzlx(String kzlx) {
        this.kzlx = kzlx;
    }

    public String getKzcs() {
        return kzcs;
    }

    public void setKzcs(String kzcs) {
        this.kzcs = kzcs;
    }

    public String getZcmc() {
        return zcmc;
    }

    public void setZcmc(String zcmc) {
        this.zcmc = zcmc;
    }

    public String getBdhmContent() {
        return bdhmContent;
    }

    public void setBdhmContent(String bdhmContent) {
        this.bdhmContent = bdhmContent;
    }

    public String getCcxh() {
        return ccxh;
    }

    public void setCcxh(String ccxh) {
        this.ccxh = ccxh;
    }

    public String getKhzh() {
        return khzh;
    }

    public void setKhzh(String khzh) {
        this.khzh = khzh;
    }

    public String getKznr() {
        return kznr;
    }

    public void setKznr(String kznr) {
        this.kznr = kznr;
    }

    public String getKzzt() {
        return kzzt;
    }

    public void setKzzt(String kzzt) {
        this.kzzt = kzzt;
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

    public String getCsksrq() {
        return csksrq;
    }

    public void setCsksrq(String csksrq) {
        this.csksrq = csksrq;
    }

    public String getCsjsrq() {
        return csjsrq;
    }

    public void setCsjsrq(String csjsrq) {
        this.csjsrq = csjsrq;
    }

    public String getDjxe() {
        return djxe;
    }

    public void setDjxe(String djxe) {
        this.djxe = djxe;
    }

    public String getSkje() {
        return skje;
    }

    public void setSkje(String skje) {
        this.skje = skje;
    }

    public String getCeskje() {
        return ceskje;
    }

    public void setCeskje(String ceskje) {
        this.ceskje = ceskje;
    }

    public String getWnkzyy() {
        return wnkzyy;
    }

    public void setWnkzyy(String wnkzyy) {
        this.wnkzyy = wnkzyy;
    }

    public String getBeiz() {
        return beiz;
    }

    public void setBeiz(String beiz) {
        this.beiz = beiz;
    }

    public List<KzFkDjxxList> getDjxxLists() {
        return djxxLists;
    }

    public void setDjxxLists(List<KzFkDjxxList> djxxLists) {
        this.djxxLists = djxxLists;
    }

    public List<KzFkQlxxList> getQlxxLists() {
        return qlxxLists;
    }

    public void setQlxxLists(List<KzFkQlxxList> qlxxLists) {
        this.qlxxLists = qlxxLists;
    }
}
