package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 资金往来（交易）信息
 *
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="wlxx")
public class CxFkWlxx implements Serializable {
    private static final long serialVersionUID = -8642724338988877431L;

    @XStreamOmitField
    private String autoID;

    // @XStreamAsAttribute
    // @XStreamAlias("BDHM")
    // private String bdhmContent;//查询请求单号

    // @XStreamAsAttribute
    // @XStreamAlias("CCXH")
    // private String ccxh;//账户序号

    @XStreamAsAttribute
    @XStreamAlias("WLXH")
    private String wlxh = "";//资金往来序号

    @XStreamAsAttribute
    @XStreamAlias("JYZL")
    private String jyzl = "";//交易种类

    @XStreamAsAttribute
    @XStreamAlias("ZJLX")
    private String zjlx = "";//借贷方向（资金流向）

    @XStreamAsAttribute
    @XStreamAlias("BZ")
    private String bz = "";//交易币种

    @XStreamAsAttribute
    @XStreamAlias("JE")
    private String je = "";//交易金额

    @XStreamAsAttribute
    @XStreamAlias("JYSJ")
    private String jysj = "";//交易日期

    @XStreamAsAttribute
    @XStreamAlias("JYLSH")
    private String jylsh = "";//交易流水号

    @XStreamAsAttribute
    @XStreamAlias("ZCKZXM")
    private String zckzxm = "";//交易对方姓名/名称

    @XStreamAsAttribute
    @XStreamAlias("ZCKZH")
    private String zckzh = "";//交易对方账号

    @XStreamAsAttribute
    @XStreamAlias("ZCKZHKHH")
    private String zckzhkhh = "";//交易对方账号开户行

    @XStreamAsAttribute
    @XStreamAlias("JYDSYHHH")
    private String jydsyhhh = "";//交易对方账号开户行行号

    @XStreamAsAttribute
    @XStreamAlias("JYDSZJLX")
    private String jydszjlx = "";//交易对方开户证件类型

    @XStreamAsAttribute
    @XStreamAlias("JYDSZJHM")
    private String jydszjhm = "";//交易对方开户证件号码

    @XStreamAsAttribute
    @XStreamAlias("JYDSTXDZ")
    private String jydstxdz = "";//交易对方通讯地址

    @XStreamAsAttribute
    @XStreamAlias("JYDSYZBM")
    private String jydsyzbm = "";//交易对方邮政编码

    @XStreamAsAttribute
    @XStreamAlias("JYDSLXDH")
    private String jydslxdh = "";//交易对方联系电话

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
    //
    // public void setBdhmContent(String bdhmContent) {
    //     this.bdhmContent = bdhmContent;
    // }

    // public String getCcxh() {
    //     return ccxh;
    // }
    //
    // public void setCcxh(String ccxh) {
    //     this.ccxh = ccxh;
    // }

    public String getWlxh() {
        return wlxh;
    }

    public void setWlxh(String wlxh) {
        this.wlxh = wlxh;
    }

    public String getJyzl() {
        return jyzl;
    }

    public void setJyzl(String jyzl) {
        this.jyzl = jyzl;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }

    public String getJysj() {
        return jysj;
    }

    public void setJysj(String jysj) {
        this.jysj = jysj;
    }

    public String getJylsh() {
        return jylsh;
    }

    public void setJylsh(String jylsh) {
        this.jylsh = jylsh;
    }

    public String getZckzxm() {
        return zckzxm;
    }

    public void setZckzxm(String zckzxm) {
        this.zckzxm = zckzxm;
    }

    public String getZckzh() {
        return zckzh;
    }

    public void setZckzh(String zckzh) {
        this.zckzh = zckzh;
    }

    public String getZckzhkhh() {
        return zckzhkhh;
    }

    public void setZckzhkhh(String zckzhkhh) {
        this.zckzhkhh = zckzhkhh;
    }

    public String getJydsyhhh() {
        return jydsyhhh;
    }

    public void setJydsyhhh(String jydsyhhh) {
        this.jydsyhhh = jydsyhhh;
    }

    public String getJydszjlx() {
        return jydszjlx;
    }

    public void setJydszjlx(String jydszjlx) {
        this.jydszjlx = jydszjlx;
    }

    public String getJydszjhm() {
        return jydszjhm;
    }

    public void setJydszjhm(String jydszjhm) {
        this.jydszjhm = jydszjhm;
    }

    public String getJydstxdz() {
        return jydstxdz;
    }

    public void setJydstxdz(String jydstxdz) {
        this.jydstxdz = jydstxdz;
    }

    public String getJydsyzbm() {
        return jydsyzbm;
    }

    public void setJydsyzbm(String jydsyzbm) {
        this.jydsyzbm = jydsyzbm;
    }

    public String getJydslxdh() {
        return jydslxdh;
    }

    public void setJydslxdh(String jydslxdh) {
        this.jydslxdh = jydslxdh;
    }

    public String getBeiz() {
        return beiz;
    }

    public void setBeiz(String beiz) {
        this.beiz = beiz;
    }
}
