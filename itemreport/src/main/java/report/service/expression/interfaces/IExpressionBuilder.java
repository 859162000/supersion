package report.service.expression.interfaces;

import java.util.List;
import java.util.Map;

public interface IExpressionBuilder {
      String build() throws Exception;
      void setContext(Map<String,Object> context);
      void setConverters(List<IExpressionConverter> converts);
}
