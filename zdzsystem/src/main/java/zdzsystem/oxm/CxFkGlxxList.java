package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="glxxList")
public class CxFkGlxxList implements Serializable {
    private static final long serialVersionUID = -4870973901042501457L;

    @XStreamImplicit
    private List<CxFkGlxx> glxxList;

    public List<CxFkGlxx> getGlxxList() {
        return glxxList;
    }

    public void setGlxxList(List<CxFkGlxx> glxxList) {
        this.glxxList = glxxList;
    }
}
