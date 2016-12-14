package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
public class VIEW_GRZX_DKXXSJCX implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String ID;
	
	@IColumn(description="���ڻ�������")
	@Column(name = "JRJGDM", length = 50)
	private String JRJGDM;
	
	@IColumn(description="ҵ���")
	@Column(name = "YWH", length = 50)
	private String YWH;
	
	@IColumn(description="����")
	@Column(name = "XM", length = 50)
	private String XM;
	
	@IColumn(description="֤������")
	@Column(name = "ZJLX", length = 50)
	private String ZJLX;
	
	@IColumn(description="���㡢Ӧ��������")
	@Column(name = "JSYHKRQ", length = 50)
	private String JSYHKRQ;
	
	@IColumn(description="���»���״̬")
	@Column(name = "DYHKZT", length = 50)
	private String DYHKZT;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getJRJGDM() {
		return JRJGDM;
	}

	public void setJRJGDM(String jRJGDM) {
		JRJGDM = jRJGDM;
	}

	public String getYWH() {
		return YWH;
	}

	public void setYWH(String yWH) {
		YWH = yWH;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String xM) {
		XM = xM;
	}

	public String getZJLX() {
		return ZJLX;
	}

	public void setZJLX(String zJLX) {
		ZJLX = zJLX;
	}

	public String getJSYHKRQ() {
		return JSYHKRQ;
	}

	public void setJSYHKRQ(String jSYHKRQ) {
		JSYHKRQ = jSYHKRQ;
	}

	public String getDYHKZT() {
		return DYHKZT;
	}

	public void setDYHKZT(String dYHKZT) {
		DYHKZT = dYHKZT;
	}
	
	
}
