import static org.junit.Assert.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import report.service.expression.ExpressionKey;
import report.service.expression.parser.SqlCal;
import report.service.expression.parser.Word;

import framework.test.SpringBeanTest;


public class SqlCalTest extends SpringBeanTest{

	@Test
	public void testCal() throws Exception {
		Word w=new Word(null, 0);
		Map<String,Object> m=new HashMap<String,Object>();
		m.put(ExpressionKey.SQL, "select COUNT(*) from ActionExcute where actionName=@actionName and strUserCode in(@strUserCode) and actionTime>@actionTime");
		w.setExpParam(m);
		w.wordType=ExpressionKey.SQL;
		Map<String,Object> context=new HashMap<String,Object>();
		context.put("actionName","Update-extend.dto.SystemParam");
		context.put("instCode","0001");
		
		context.put("strUserCode","select strUserCode from UserInfo where strInstCode=@instCode");
		context.put("actionTime",Date.valueOf("2016-05-01"));
		SqlCal cal=new SqlCal();
		cal.setContext(context);
		cal.cal(w);
		
	}

}
