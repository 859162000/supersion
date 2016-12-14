package zdzsystem.oxm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import zdzsystem.dto.AutoDTO_ZDZ_BZXRZHXX;

import java.io.Serializable;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-27 16:38
 */
@XStreamAlias("kzzhlist")
public class Kzqqbzxrzhxx implements Serializable {
    @XStreamImplicit
    private List<AutoDTO_ZDZ_BZXRZHXX> kzzhlist;

    public List<AutoDTO_ZDZ_BZXRZHXX> getkzzhlist(){
        return kzzhlist;
    }

    public void setkkzzhlist(List<AutoDTO_ZDZ_BZXRZHXX> kzzhlist){
        this.kzzhlist = kzzhlist;
    }
}
