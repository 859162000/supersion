package report.service.expression.interfaces;

public interface IExpressionCheck {
   boolean check(String exp) throws Exception;
   String getCheckMsg();
}
