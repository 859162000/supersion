package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-27 17:02
 */
@XStreamAlias("htxxList")
public class FkKzclsbxx implements Serializable {
    @XStreamImplicit
    private List<Kzclsbxx> htxxList;

    public List<Kzclsbxx> getHtxxList() {
        return htxxList;
    }

    public void setHtxxList(List<Kzclsbxx> htxxList) {
        this.htxxList = htxxList;
    }
}
