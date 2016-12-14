package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-20
 */
@XStreamAlias(value="htxxList")
public class KzHt implements Serializable {
    private static final long serialVersionUID = 3183271909942058695L;

    @XStreamImplicit
    private List<KzHtxx> htxxList;

    public List<KzHtxx> getHtxxList() {
        return htxxList;
    }

    public void setHtxxList(List<KzHtxx> htxxList) {
        this.htxxList = htxxList;
    }
}
