package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="qlxxList")
public class CxFkQlxxList implements Serializable {
    private static final long serialVersionUID = -901637719313458273L;

    @XStreamImplicit
    private List<CxFkQlxx> qlxxList;

    public List<CxFkQlxx> getQlxxList() {
        return qlxxList;
    }

    public void setQlxxList(List<CxFkQlxx> qlxxList) {
        this.qlxxList = qlxxList;
    }
}
