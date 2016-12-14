package framework.test;

import java.util.Map;

public interface TestDataProvider {
	Map<String,String> getData(Map<String,Object> context);
}
