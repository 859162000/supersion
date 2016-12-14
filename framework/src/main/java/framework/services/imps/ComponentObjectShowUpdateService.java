package framework.services.imps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import framework.services.interfaces.LogicParamManager;

public class ComponentObjectShowUpdateService extends BaseObjectDaoResultService implements IComponentObjectService {

	Map<String,List<String>> dtoAddMap;
	Map<String,List<String>> dtoCheckMap;
	Map<String,List<String>> dtoTranslateMap;
	Map<String,List<String>> dtoProceseMap;
	
	List<String> defaultComponentAddClassList;
	
	List<String> defaultComponentCheckClassList;
	
	List<String> defaultComponentTranslateClassList;
	
	List<String> defaultComponentProceseClassList;
	
	private Map<String,Map<String,String>> dtoDefaultValue;
	private Map<String,String> dtoShowInstanceMap;
	private Map<String,String> dtoListServiceMap;

	@Override
    public void init() throws Exception{
		super.init();
		this.setDefaultCheckClassList(Arrays.asList(new String[]{defaultCheckClass}));
		this.setDefaultAddClassList(Arrays.asList(new String[]{defaultAddClass}));
		this.setDefaultTranslateClassList(Arrays.asList(new String[]{defaultTranslateClass}));
		this.setDefaultProceseClassList(Arrays.asList(new String[]{defaultProceseClass}));
	 	LogicParamManager.setDtoAddMap(dtoAddMap);
	 	LogicParamManager.setDtoCheckMap(dtoCheckMap);
	 	LogicParamManager.setDtoTranslateMap(dtoTranslateMap);
	 	LogicParamManager.setDtoProceseMap(dtoProceseMap);
	 	LogicParamManager.setDtoDefaultValue(dtoDefaultValue);
	 	LogicParamManager.setDtoShowInstanceMap(dtoShowInstanceMap);
	 	LogicParamManager.setDtoListServiceMap(dtoListServiceMap);
	 	LogicParamManager.setDefaultComponentAddClassList(defaultComponentAddClassList);
	 	LogicParamManager.setDefaultComponentCheckClassList(defaultComponentCheckClassList);
	 	LogicParamManager.setDefaultComponentProceseClassList(defaultComponentProceseClassList);
	 	LogicParamManager.setDefaultComponentTranslateClassList(defaultComponentTranslateClassList);
	}

	public Map<String,Map<String,String>> getDtoDefaultValue()
	{
		return dtoDefaultValue;
	}
	public void setDtoDefaultValue(Map<String,Map<String,String>> dtoDefaultValue)
	{
		this.dtoDefaultValue=dtoDefaultValue;
	}
	
	public void setDtoAddMap(Map<String,List<String>> dtoAddMap) {
		this.dtoAddMap = dtoAddMap;
	}

	public Map<String,List<String>> getDtoAddMap() {
		return dtoAddMap;
	}
	public void setDtoCheckMap(Map<String,List<String>> dtoCheckMap) {
		this.dtoCheckMap = dtoCheckMap;
	}

	public Map<String,List<String>> getDtoCheckMap() {
		return dtoCheckMap;
	}
	public void setDtoTranslateMap(Map<String,List<String>> dtoTranslateMap) {
		this.dtoTranslateMap = dtoTranslateMap;
	}

	public Map<String,List<String>> getDtoTranslateMap() {
		return dtoTranslateMap;
	}
	public void setDtoProceseMap(Map<String,List<String>> dtoProceseMap) {
		this.dtoProceseMap = dtoProceseMap;
	}

	public Map<String,List<String>> getDtoProceseMap() {
		return dtoProceseMap;
	}

	public List<String> getDefaultComponentAddClassList() {
		return defaultComponentAddClassList;
	}

	public void setDefaultComponentAddClassList(
			List<String> defaultComponentAddClassList) {
		this.defaultComponentAddClassList = defaultComponentAddClassList;
	}

	public List<String> getDefaultComponentCheckClassList() {
		return defaultComponentCheckClassList;
	}

	public void setDefaultComponentCheckClassList(
			List<String> defaultComponentCheckClassList) {
		this.defaultComponentCheckClassList = defaultComponentCheckClassList;
	}

	public List<String> getDefaultComponentTranslateClassList() {
		return defaultComponentTranslateClassList;
	}

	public void setDefaultComponentTranslateClassList(
			List<String> defaultComponentTranslateClassList) {
		this.defaultComponentTranslateClassList = defaultComponentTranslateClassList;
	}

	public List<String> getDefaultComponentProceseClassList() {
		return defaultComponentProceseClassList;
	}

	public void setDefaultComponentProceseClassList(
			List<String> defaultComponentProceseClassList) {
		this.defaultComponentProceseClassList = defaultComponentProceseClassList;
	}
	public Map<String,String> getDtoShowInstanceMap()
	{
		return dtoShowInstanceMap;
	}
	public void setDtoShowInstanceMap(Map<String,String> dtoShowInstanceMap)
	{
		this.dtoShowInstanceMap=dtoShowInstanceMap;
	}
	public Map<String,String> getDtoListServiceMap()
	{
		return dtoListServiceMap;
	}
	public void setDtoListServiceMap(Map<String,String> dtoListServiceMap)
	{
		this.dtoListServiceMap=dtoListServiceMap;
	}
	
	
}
