package framework.services.interfaces;

import java.util.Map;

public interface ICheckWithParam {
	MessageResult Check(Map<String, Object> mapObject) throws Exception;
}
