package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-18
 */
@XStreamAlias(value="djxxList")
public class KzFkDjxxList implements Serializable {
    @XStreamImplicit
    private List<KzFkDjxx> djxxList;

    public List<KzFkDjxx> getDjxxList() {
        return djxxList;
    }

    public void setDjxxList(List<KzFkDjxx> djxxList) {
        this.djxxList = djxxList;
    }
}
