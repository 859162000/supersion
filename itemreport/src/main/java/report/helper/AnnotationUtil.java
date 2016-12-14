package report.helper;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：AnnotationUtil</P>
 * *********************************<br>
 * <P>类描述：vo类 注释解析器</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-19 下午2:41:52<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-19 下午2:41:52<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AnnotationUtil {
	public static StringBuffer getTSelectSqlStr(Class clazz) {
		StringBuffer sb = new StringBuffer("");
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Column cName = field.getAnnotation(Column.class);
			if (null != cName)
				sb.append(", " + cName.name() + " " + field.getName());
		}
		sb.append(" ");
		sb.replace(0, 1, "select");
		sb.append(" from ").append(getTableName(clazz));
		return sb;
	}
	
	public static String getTableName(Class clazz){
		Table table = (Table) clazz.getAnnotation(Table.class);
		return table.name(); 
	}

	public static StringBuffer getTInsertSqlStr(Class clazz) {
		StringBuffer sb = new StringBuffer("insert into ");
		sb.append(getTableName(clazz));
		Field[] fields = clazz.getDeclaredFields();
		String cols = "";
		String vals = "";
		for (Field field : fields) {
			Column cName = field.getAnnotation(Column.class);
			if (null != cName) {
				if (StringUtils.isNotBlank(cName.name())) {
					cols += "," + cName.name();
				} else {
					cols += "," + field.getName();
				}
				vals += ",?";
			}
		}
		sb.append(cols.replaceFirst(",", "(")).append(") values");
		sb.append(vals.replaceFirst(",", "(")).append(")");
		return sb;
	}
	
}
