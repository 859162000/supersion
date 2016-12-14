package report.service.imps;

import javax.persistence.Table;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;



public class CheckDTOAndTableExistService{
	
	public String check(String DTO) {
		try {
			Class<?> sourceType = Class.forName(DTO);
			
			String strTable = sourceType.getAnnotation(Table.class).name();
			IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("createSqlQueryListMapTableDao");	// 调用DAO,执行SQL,取得数据
			try {
				dao.paramObjectResultExecute(new Object[]{"select '存在表' from "+strTable, null});
			} catch (Exception e) {
				return "表："+strTable+" 不存在!";
			}
		} catch (ClassNotFoundException e) {
			return "DTO："+DTO+" 不存在!";
		}
		return "true";
	}
}
