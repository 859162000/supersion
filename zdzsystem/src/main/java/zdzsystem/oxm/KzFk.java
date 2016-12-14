package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author veggieg
 * @since 2016-10-17
 */
@XStreamAlias(value="kzxxList")
public class KzFk implements Serializable {
    private static final long serialVersionUID = 4683569545575769768L;

    @XStreamImplicit
    private List<KzFkKzxx> kzxxList;

    public List<KzFkKzxx> getKzxxList() {
        return kzxxList;
    }

    public void setKzxxList(List<KzFkKzxx> kzxxList) {
        this.kzxxList = kzxxList;
    }
}
