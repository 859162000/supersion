package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-18
 */
@XStreamAlias(value = "hzxxList")
public class KzFkHz implements Serializable {
    private static final long serialVersionUID = -3624284046647976488L;

    @XStreamImplicit
    private List<KzFkHzxx> hzxxList;

    public List<KzFkHzxx> getHzxxList() {
        return hzxxList;
    }

    public void setHzxxList(List<KzFkHzxx> hzxxList) {
        this.hzxxList = hzxxList;
    }
}
