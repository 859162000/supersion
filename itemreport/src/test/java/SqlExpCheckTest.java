import static org.junit.Assert.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import report.service.expression.ExpressionKey;
import report.service.expression.check.SqlExpCheck;
import report.service.expression.interfaces.IWordCheck;

import report.service.expression.parser.Word;


public class SqlExpCheckTest {

	@Test
	public void testCheck() throws Exception {
		Word w=new Word(null, 0);
		Map<String,Object> m=new HashMap<String,Object>();
		m.put(ExpressionKey.SQL, "select COUNT(*) from ActionExcute where actionName=@actionName and strUserCode in(@strUserCode) and actionTime>@actionTime");
		w.setExpParam(m);
		w.wordType=ExpressionKey.SQL;
		IWordCheck check=new SqlExpCheck();
		boolean result=check.check(w);
		assertTrue(result);
	}

}
