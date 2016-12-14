package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-27 16:10
 */
@XStreamAlias("zjinfoList")
public class Kzqqzjxx implements Serializable {
    @XStreamImplicit
    private List<KzqqzjInfo> zjinfoList;

    public List<KzqqzjInfo> getZjinfoList() {
        return zjinfoList;
    }

    public void setZjinfoList(List<KzqqzjInfo> zjinfoList) {
        this.zjinfoList = zjinfoList;
    }
}
