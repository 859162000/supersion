package framework.interfaces;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;

public interface IGetScrollableResultsExecute {
	public ScrollableResults getScrollableResults(String param,Session session) throws Exception;
}
