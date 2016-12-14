package zxptsystem.helper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class EnterpriseQueryTemplate {

	//private String _hosturl;
	//private HashMap<String,String> _areaIps;
	private String _loginurl;
	private String _loginParam;
	private List<QueryAction> QueryActions;
		
//	public void set_hosturl(String hosturl) {
//		_hosturl = hosturl;
//	}
//	public String get_hosturl() {
//		return _hosturl;
//	}
	public String get_loginurl() {
		return _loginurl;
	}

	public void set_loginurl(String loginurl) {
		_loginurl = loginurl;
	}

	public List<QueryAction> getQueryActions() {
		return QueryActions;
	}

	public void setQueryActions(List<QueryAction> queryActions) {
		QueryActions = queryActions;
	}

	public List<Field> get_fields() {
		return _fields;
	}

	public void set_fields(List<Field> fields) {
		_fields = fields;
	}

	/**
	 * @param _loginParam the _loginParam to set
	 */
	public void set_loginParam(String _loginParam) {
		this._loginParam = _loginParam;
	}

	/**
	 * @return the _loginParam
	 */
	public String get_loginParam() {
		return _loginParam;
	}

//	public void set_areaIps(HashMap<String,String> _areaIps) {
//		this._areaIps = _areaIps;
//	}
//
//	public HashMap<String,String> get_areaIps() {
//		return _areaIps;
//	}

	private List<Field> _fields;

}
