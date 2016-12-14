package zdzsystem.dto.condition;


public class CustInfo_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String custcode;
	
	private String custnum;

	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}

	public String getCustnum() {
		return custnum;
	}

	public void setCustnum(String custnum) {
		this.custnum = custnum;
	}
    
}
