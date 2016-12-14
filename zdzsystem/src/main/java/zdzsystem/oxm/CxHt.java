package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-09
 */
@XStreamAlias(value="htxxList")
public class CxHt implements Serializable {
    private static final long serialVersionUID = 71647860944936012L;

    @XStreamImplicit
    private List<CxHtxx> htxxList;

    public List<CxHtxx> getHtxxList() {
        return htxxList;
    }

    public void setHtxxList(List<CxHtxx> htxxList) {
        this.htxxList = htxxList;
    }
}
