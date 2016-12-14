package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-10-08
 */
@XStreamAlias(value="jrxxList")
public class CxFkJrxxList implements Serializable {
    private static final long serialVersionUID = 148237176975332607L;

    @XStreamImplicit
    private List<CxFkJrxx> jrxxList;

    public List<CxFkJrxx> getJrxxList() {
        return jrxxList;
    }

    public void setJrxxList(List<CxFkJrxx> jrxxList) {
        this.jrxxList = jrxxList;
    }
}
