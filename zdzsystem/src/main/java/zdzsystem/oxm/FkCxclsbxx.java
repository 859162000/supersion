package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import zdzsystem.dto.AutoDTO_ZDZ_FYDFKCXCLSBNR;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-27 14:40
 */
@XStreamAlias("htxxList")
public class FkCxclsbxx implements Serializable {
    @XStreamImplicit
    private List<Cxclsbxx> htxxList;

    public List<Cxclsbxx> getHtxxList() {
        return htxxList;
    }

    public void setHtxxList(List<Cxclsbxx> htxxList) {
        this.htxxList = htxxList;
    }
}
