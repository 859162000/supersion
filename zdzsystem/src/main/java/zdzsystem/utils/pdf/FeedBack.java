package zdzsystem.utils.pdf;

import zdzsystem.oxm.KzFk;

/**
 *
 * @author veggieg
 * @since 2016-10-19
 */
public class FeedBack {
    private KzFk kzFk;

    private String fymc;// 法院名称
    private String ckwh;// 裁定书文号 e.g. （20xx）x法执x字第xx号执行裁定书
    private String cktz;// 查控通知文号，e.g（20xx）x法执x字第xx号协助执行通知书
    private String yhmc;// 银行名称
    private String wssj;// 反馈文书生成时间，yyyy/MM/dd hh:mm:ss
    private String bzxrxm;// 被查询人姓名

    public KzFk getKzFk() {
        return kzFk;
    }

    public void setKzFk(KzFk kzFk) {
        this.kzFk = kzFk;
    }

    public String getFymc() {
        return fymc;
    }

    public void setFymc(String fymc) {
        this.fymc = fymc;
    }

    public String getCkwh() {
        return ckwh;
    }

    public void setCkwh(String ckwh) {
        this.ckwh = ckwh;
    }

    public String getCktz() {
        return cktz;
    }

    public void setCktz(String cktz) {
        this.cktz = cktz;
    }

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public String getWssj() {
        return wssj;
    }

    public void setWssj(String wssj) {
        this.wssj = wssj;
    }

    public String getBzxrxm() {
        return bzxrxm;
    }

    public void setBzxrxm(String bzxrxm) {
        this.bzxrxm = bzxrxm;
    }
}
