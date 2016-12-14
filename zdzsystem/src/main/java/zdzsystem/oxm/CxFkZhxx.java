package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;
import java.util.List;

/**
 * 具体账户信息
 *
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="zhxx")
public class CxFkZhxx implements Serializable {
    private static final long serialVersionUID = -2196225627089169296L;

    @XStreamOmitField
    private String autoID;

    @XStreamAsAttribute
    @XStreamAlias("BDHM")
    private String bdhmContent;//查询请求单号

    @XStreamAsAttribute
    @XStreamAlias("CCXH")
    private String ccxh;//账户序号

    @XStreamAsAttribute
    @XStreamAlias("KHZH")
    private String khzh;//开户账号

    @XStreamAsAttribute
    @XStreamAlias("CCLB")
    private String cclb;//账户类别

    @XStreamAsAttribute
    @XStreamAlias("ZHZT")
    private String zhzt;//账户状态

    @XStreamAsAttribute
    @XStreamAlias("KHWD")
    private String khwd;//开户网点

    @XStreamAsAttribute
    @XStreamAlias("KHWDDM")
    private String khwddm;//开户网点代码

    @XStreamAsAttribute
    @XStreamAlias("KHRQ")
    private String khrq;//开户日期

    @XStreamAsAttribute
    @XStreamAlias("XHRQ")
    private String xhrq;//销户日期

    @XStreamAsAttribute
    @XStreamAlias("BZ")
    private String bz;//计价币种

    @XStreamAsAttribute
    @XStreamAlias("YE")
    private String ye;//资产数额

    @XStreamAsAttribute
    @XStreamAlias("KYYE")
    private String kyye;//可用资产数额

    @XStreamAsAttribute
    @XStreamAlias("GLZJZH")
    private String glzjzh;//关联资金账户

    @XStreamAsAttribute
    @XStreamAlias("FKSJ")
    private String fksj;//反馈结果时间

    @XStreamAsAttribute
    @XStreamAlias("TXDZ")
    private String txdz;//通讯地址

    @XStreamAsAttribute
    @XStreamAlias("YZBM")
    private String yzbm;//邮政编码

    @XStreamAsAttribute
    @XStreamAlias("LXDH")
    private String lxdh;//联系电话

    @XStreamAsAttribute
    @XStreamAlias("BEIZ")
    private String beiz;//备注

    @XStreamImplicit
    private List<CxFkJrxxList> jrxxList;

    @XStreamImplicit
    private List<CxFkDjxxList> djxxList;

    @XStreamImplicit
    private List<CxFkWlxxList> wlxxList;

    @XStreamImplicit
    private List<CxFkQlxxList> qlxxList;

    @XStreamImplicit
    private List<CxFkGlxxList> glxxList;

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

    public String getCclb() {
        return cclb;
    }

    public void setCclb(String cclb) {
        this.cclb = cclb;
    }

    public String getZhzt() {
        return zhzt;
    }

    public void setZhzt(String zhzt) {
        this.zhzt = zhzt;
    }

    public String getKhwd() {
        return khwd;
    }

    public void setKhwd(String khwd) {
        this.khwd = khwd;
    }

    public String getKhwddm() {
        return khwddm;
    }

    public void setKhwddm(String khwddm) {
        this.khwddm = khwddm;
    }

    public String getKhrq() {
        return khrq;
    }

    public void setKhrq(String khrq) {
        this.khrq = khrq;
    }

    public String getXhrq() {
        return xhrq;
    }

    public void setXhrq(String xhrq) {
        this.xhrq = xhrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

    public String getGlzjzh() {
        return glzjzh;
    }

    public void setGlzjzh(String glzjzh) {
        this.glzjzh = glzjzh;
    }

    public String getFksj() {
        return fksj;
    }

    public void setFksj(String fksj) {
        this.fksj = fksj;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getBeiz() {
        return beiz;
    }

    public void setBeiz(String beiz) {
        this.beiz = beiz;
    }

    public List<CxFkJrxxList> getJrxxList() {
        return jrxxList;
    }

    public void setJrxxList(List<CxFkJrxxList> jrxxList) {
        this.jrxxList = jrxxList;
    }

    public List<CxFkDjxxList> getDjxxList() {
        return djxxList;
    }

    public void setDjxxList(List<CxFkDjxxList> djxxList) {
        this.djxxList = djxxList;
    }

    public List<CxFkWlxxList> getWlxxList() {
        return wlxxList;
    }

    public void setWlxxList(List<CxFkWlxxList> wlxxList) {
        this.wlxxList = wlxxList;
    }

    public List<CxFkQlxxList> getQlxxList() {
        return qlxxList;
    }

    public void setQlxxList(List<CxFkQlxxList> qlxxList) {
        this.qlxxList = qlxxList;
    }

    public List<CxFkGlxxList> getGlxxList() {
        return glxxList;
    }

    public void setGlxxList(List<CxFkGlxxList> glxxList) {
        this.glxxList = glxxList;
    }
}
