package dbgssystem.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUitl {
	
	
	/***
	 * 获取系统当前时间 日期格式为 yyyy-MM-dd HH:mm:ss
	 * @version guandongxu
	 * @return
	 */
	public static String dateToString(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String systemDate=sdf.format(new Date()).toString();
		return systemDate;
	}
}
