package ncr.helper;

import java.util.Map;
/**
 * Xml配置文件
 * @author ctx101
 *
 */
public class InterfaceDownBean {
	/**	新客户风险	*/
	private Map<String, String> DGJTYKH;			//表1、对公及同业客户授信和表内外业务统计表
	private Map<String, String> JTKH;				//表2、集团客户、供应链融资基本信息统计表
	private Map<String, String> DYFRKH;				//表3、单一法人客户基本信息统计表
	private Map<String, String> DGKHDB;				//表4、对公客户担保情况统计表
	private Map<String, String> GRDKWY;				//表5、个人贷款违约情况统计表
	private Map<String, String> GRWYDKDB;			//表6、个人违约贷款担保情况统计表
	private Map<String, String> NewAssociativeField;		//表与表之间的配置字段，在表中的排列位置的配置信息
	
	public Map<String, String> getNewAssociativeField() {
		return NewAssociativeField;
	}
	public void setNewAssociativeField(Map<String, String> newAssociativeField) {
		NewAssociativeField = newAssociativeField;
	}
	public Map<String, String> getDGJTYKH() {
		return DGJTYKH;
	}
	public void setDGJTYKH(Map<String, String> dGJTYKH) {
		DGJTYKH = dGJTYKH;
	}
	public Map<String, String> getJTKH() {
		return JTKH;
	}
	public void setJTKH(Map<String, String> jTKH) {
		JTKH = jTKH;
	}
	public Map<String, String> getDYFRKH() {
		return DYFRKH;
	}
	public void setDYFRKH(Map<String, String> dYFRKH) {
		DYFRKH = dYFRKH;
	}
	public Map<String, String> getDGKHDB() {
		return DGKHDB;
	}
	public void setDGKHDB(Map<String, String> dGKHDB) {
		DGKHDB = dGKHDB;
	}
	public Map<String, String> getGRDKWY() {
		return GRDKWY;
	}
	public void setGRDKWY(Map<String, String> gRDKWY) {
		GRDKWY = gRDKWY;
	}
	public Map<String, String> getGRWYDKDB() {
		return GRWYDKDB;
	}
	public void setGRWYDKDB(Map<String, String> gRWYDKDB) {
		GRWYDKDB = gRWYDKDB;
	}

}
