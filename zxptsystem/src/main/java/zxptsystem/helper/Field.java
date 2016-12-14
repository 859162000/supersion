package zxptsystem.helper;

public class Field {
	private String _fieldName;
	private String _fieldCHName;
	private String _fieldType;
	private String _fieldDataSource;
	private boolean _readOnly;
	private String _dataField;
	private String _keyValue;
	private boolean _isHidden;
	private String _value;
	
	public String get_fieldName() {
		return _fieldName;
	}
	public void set_fieldName(String fieldName) {
		_fieldName = fieldName;
	}
	public String get_fieldCHName() {
		return _fieldCHName;
	}
	public void set_fieldCHName(String fieldCHName) {
		_fieldCHName = fieldCHName;
	}
	public String get_fieldType() {
		return _fieldType;
	}
	public void set_fieldType(String fieldType) {
		_fieldType = fieldType;
	}
	public String get_fieldDataSource() {
		return _fieldDataSource;
	}
	public void set_fieldDataSource(String fieldDataSource) {
		_fieldDataSource = fieldDataSource;
	}
	public boolean get_readOnly() {
		return _readOnly;
	}
	public void set_readOnly(boolean readOnly) {
		_readOnly = readOnly;
	}
	public String get_dataField() {
		return _dataField;
	}
	public void set_dataField(String dataField) {
		_dataField = dataField;
	}
	public String get_interfaceField() {
		return _interfaceField;
	}
	public void set_interfaceField(String interfaceField) {
		_interfaceField = interfaceField;
	}
	public String get_fieldDesc() {
		return _fieldDesc;
	}
	public void set_fieldDesc(String fieldDesc) {
		_fieldDesc = fieldDesc;
	}
	public void set_keyValue(String _keyValue) {
		this._keyValue = _keyValue;
	}
	public String get_keyValue() {
		return _keyValue;
	}
	public void set_isHidden(boolean _isHidden) {
		this._isHidden = _isHidden;
	}
	public boolean is_isHidden() {
		return _isHidden;
	}
	public void set_value(String _value) {
		this._value = _value;
	}
	public String get_value() {
		return _value;
	}
	private String _interfaceField;
	private String _fieldDesc;
}
