package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-27 13:09
 */
@XStreamAlias("zjinfoList")
public class Cxqqzjxx implements Serializable {
    @XStreamImplicit
    private List<CxqqzjInfo> zjinfoList;

    public List<CxqqzjInfo> getZjinfoList() {
        return zjinfoList;
    }

    public void setZjinfoList(List<CxqqzjInfo> zjinfoList) {
        this.zjinfoList = zjinfoList;
    }
}
