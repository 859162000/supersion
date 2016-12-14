package report.service.expression.interfaces;

import java.util.Map;

public interface IExpressionLogFormatter {
     String format(Object obj,Map<Object,Object> params);
     String format(Object obj,Object[] params) throws Exception;
     
}
