package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-27 14:58
 */
@XStreamAlias("kzqqList")
public class Kzqqxx implements Serializable {
    @XStreamImplicit
    private List<AutoDTO_ZDZ_KZQQJTNR> kzqqList;

    public List<AutoDTO_ZDZ_KZQQJTNR> getKzqqList() {
        return kzqqList;
    }

    public void setKzqqList(List<AutoDTO_ZDZ_KZQQJTNR> kzqqList) {
        this.kzqqList = kzqqList;
    }
}
