package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="wlxxList")
public class CxFkWlxxList implements Serializable {
    private static final long serialVersionUID = -4976002009268653719L;

    @XStreamImplicit
    private List<CxFkWlxx> wlxxList;

    public List<CxFkWlxx> getWlxxList() {
        return wlxxList;
    }

    public void setWlxxList(List<CxFkWlxx> wlxxList) {
        this.wlxxList = wlxxList;
    }
}
