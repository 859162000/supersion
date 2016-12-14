package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * 金融机构反馈查询结果信息
 *
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="zhxxList")
public class CxFk implements Serializable {
    private static final long serialVersionUID = -2296525202293381912L;

    @XStreamImplicit
    private List<CxFkZhxx> zhxxList;

    public List<CxFkZhxx> getZhxxList() {
        return zhxxList;
    }

    public void setZhxxList(List<CxFkZhxx> zhxxList) {
        this.zhxxList = zhxxList;
    }
}
