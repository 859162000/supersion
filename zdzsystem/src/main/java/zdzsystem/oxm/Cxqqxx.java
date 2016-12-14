package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import zdzsystem.dto.AutoDTO_ZDZ_CXQQNR;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-26 17:45
 */
@XStreamAlias("cxqqList")
public class Cxqqxx implements Serializable {
    @XStreamImplicit
    private List<AutoDTO_ZDZ_CXQQNR> cxqqList;

    public List<AutoDTO_ZDZ_CXQQNR> getCxqqList() {
        return cxqqList;
    }

    public void setCxqqList(List<AutoDTO_ZDZ_CXQQNR> cxqqList) {
        this.cxqqList = cxqqList;
    }
}
