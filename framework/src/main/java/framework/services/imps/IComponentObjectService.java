package framework.services.imps;

import java.util.List;
import java.util.Map;

public interface IComponentObjectService {

	String defaultAddClass = "framework.services.add.ComponentAllAdd";
	String defaultCheckClass = "framework.services.check.ComponentAllCheck";
	String defaultTranslateClass = "framework.services.translate.ComponentAllTranslate";
	String defaultProceseClass = "framework.services.procese.ComponentAllProcese";

	void setDtoAddMap(Map<String, List<String>> dtoAddMap);

	Map<String, List<String>> getDtoAddMap();

	void setDtoCheckMap(Map<String, List<String>> dtoCheckMap);

	Map<String, List<String>> getDtoCheckMap();

	void setDtoTranslateMap(Map<String, List<String>> dtoTranslateMap);

	Map<String, List<String>> getDtoTranslateMap();

	void setDtoProceseMap(Map<String, List<String>> dtoProceseMap);

	Map<String, List<String>> getDtoProceseMap();

	List<String> getDefaultComponentAddClassList();

	void setDefaultComponentAddClassList(List<String> defaultComponentAddClassList);

	List<String> getDefaultComponentCheckClassList();

	void setDefaultComponentCheckClassList(List<String> defaultComponentCheckClassList);

	List<String> getDefaultComponentTranslateClassList();

	void setDefaultComponentTranslateClassList(List<String> defaultComponentTranslateClassList);

	List<String> getDefaultComponentProceseClassList();

	void setDefaultComponentProceseClassList(List<String> defaultComponentProceseClassList);

}