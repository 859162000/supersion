package report.actions.imps;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.sun.corba.se.spi.orb.OperationFactory;

import framework.actions.imps.BaseSAction;
import framework.actions.imps.BaseSTAction;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;

public class UpdateFieldAction extends BaseSTAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4508299534930343059L;
	
	// 修改对象的ID
	private String dataId;
	// 报表备注信息
	private String updateObjectName;
	// 需要修改的字段名称
	private String fieldName;
	// 修改的字段值
	private String fieldvalue;
	
	public String getFieldvalue() {
		return fieldvalue;
	}
	public void setFieldvalue(String fieldvalue) throws UnsupportedEncodingException {
		this.fieldvalue =fieldvalue;// new String(fieldvalue.getBytes(), "UTF-8");
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getUpdateObjectName() {
		return updateObjectName;
	}
	public void setUpdateObjectName(String updateObjectName) {
		this.updateObjectName = updateObjectName;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Override
	public String execute() throws Exception {
		
		Map<String,String> checkParam = new LinkedHashMap<String,String>();
		checkParam.put("fieldvalue", fieldvalue);
		RequestManager.setReportCheckParam(checkParam);
		
		Object updateObj = Class.forName(updateObjectName).newInstance();
		// 获取修改对象的主键
		Field keyField = ReflectOperation.getPrimaryKeyField(updateObjectName);
		// 设置修改对象的主键值
		ReflectOperation.setFieldValue(updateObj, keyField.getName().toString(), dataId);
		// 设置要修改的字段的值
		ReflectOperation.setFieldValue(updateObj, fieldName, fieldvalue);
		
		RequestManager.setTOject(updateObj);
		
		super.execute();
		
		// 判断执行service直接结果serviceResult是否为空
		if(this.getServiceResult() != null){
			if(((MessageResult) this.getServiceResult()).getErrorFieldList() != null){
				// 定义response对象
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("UTF-8");
				// 定义执行结果对象
				String result = "保存失败\r\n";
				for(ErrorField msg : ((MessageResult) this.getServiceResult()).getErrorFieldList()){
					result = result + msg.getMessage() + "\r\n";
				}
				result = result + ";" + false;
				response.getWriter().write(result.toString());
			}
		}
		
		
		
		return null;
	}
	
	
	
}
