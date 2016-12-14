package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-27 10:10
 */
@XStreamAlias("wsinfoList")
public class Cxqqwsxx implements Serializable {
    @XStreamImplicit
    private List<CxqqwsInfo> wsinfoList;

    public List<CxqqwsInfo> getWsinfoList() {
        return wsinfoList;
    }

    public void setWsinfoList(List<CxqqwsInfo> wsinfoList) {
        this.wsinfoList = wsinfoList;
    }
}
