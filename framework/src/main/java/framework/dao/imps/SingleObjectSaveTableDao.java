package framework.dao.imps;

import java.lang.reflect.Field;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;

public class SingleObjectSaveTableDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		//RequestManager.getTOject()中保存着要新增的对象
		if(RequestManager.getTOject() == null)
			return;
		
		Class<?> type = Class.forName(RequestManager.getTName());
		String strSQL = "insert into " + RequestManager.getTName();
		String strFields = "";
		String strVals =  "";
		Field[] fieldList = ReflectOperation.getReflectFields(type);
		for(int i=0;i<fieldList.length;i++){
			if(fieldList[i].getName() == "serialVersionUID") continue;
			
			if(!strFields.equals(""))
				strFields += ",";
			if(!strFields.equals(""))
				strVals += ",";
			
			strFields += fieldList[i].getName();
			strVals += ReflectOperation.getFieldValue(RequestManager.getTOject(), fieldList[i].getName()).toString();
		}
		
		strSQL += " (" + strFields + ") values(" + strVals + ")";
		this.getSession().createSQLQuery(strSQL).executeUpdate();
        
		return ;
	}

}
