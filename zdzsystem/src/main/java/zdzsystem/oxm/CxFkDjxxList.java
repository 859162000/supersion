package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="djxxList")
public class CxFkDjxxList implements Serializable {
    private static final long serialVersionUID = 5441584747505593471L;

    @XStreamImplicit
    private List<CxFkDjxx> djxxList;

    public List<CxFkDjxx> getDjxxList() {
        return djxxList;
    }

    public void setDjxxList(List<CxFkDjxx> djxxList) {
        this.djxxList = djxxList;
    }
}
