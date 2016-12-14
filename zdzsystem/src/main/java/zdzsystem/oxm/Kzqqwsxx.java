package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-27 15:54
 */
@XStreamAlias("wsinfoList")
public class Kzqqwsxx implements Serializable {
    @XStreamImplicit
    private List<KzqqwsInfo> wsinfoList;

    public List<KzqqwsInfo> getWsinfoList() {
        return wsinfoList;
    }

    public void setWsinfoList(List<KzqqwsInfo> wsinfoList) {
        this.wsinfoList = wsinfoList;
    }
}
