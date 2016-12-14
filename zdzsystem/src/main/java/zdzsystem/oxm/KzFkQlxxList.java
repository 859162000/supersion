package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-18
 */
@XStreamAlias(value="qlxxList")
public class KzFkQlxxList implements Serializable {
    private static final long serialVersionUID = 5541746169939420309L;

    @XStreamImplicit
    private List<KzFkQlxx> qlxxList;

    public List<KzFkQlxx> getQlxxList() {
        return qlxxList;
    }

    public void setQlxxList(List<KzFkQlxx> qlxxList) {
        this.qlxxList = qlxxList;
    }
}
