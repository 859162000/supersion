package framework.show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.w3c.dom.Element;

import framework.helper.DomXMLOperation;
import framework.helper.ExceptionLog;
import framework.helper.ReflectOperation;

//解析显示相关配置信息保存到ShowInstance
public class ShowContext {
private static ShowContext showContext = null;  
	
    

    private ShowContext() {  
        if(showInstanceLocation != null){
        	initShowInstance();
        }
        if(showNavigationLocation != null){
        	initShowNavigation();
        }
        if(showEntityLocation != null){
        	initShowEntity();
        }
    }  
  
    synchronized  public static ShowContext getInstance(){  
        if (showContext == null)  {
        	showContext = new ShowContext();  
        }
        return showContext;  
    }  
    
	public static void Init() throws Exception{  
		ShowContext.showContext = null;
    }
    
    // 读取xml配置,生成对象
    private void initShowInstance(){
    	
    	showInstanceMap = new HashMap<String, List<ShowInstance>>();
    	for(int f=0;f<showInstanceLocation.length;f++){ // 每个show xml配置文件
    		if(showInstanceLocation[f].equals("")) continue;
    		
    		Element fieldShowsRootElement = DomXMLOperation.getElementByName(showInstanceLocation[f],"fieldShowsRoot");
           	Element[] fieldShowsEntityElements = DomXMLOperation.getElementsByName(fieldShowsRootElement,"fieldShowsEntity");
           	for(int i=0;i<fieldShowsEntityElements.length;i++){ // 文件中的每个dto
           		
           		List<ShowInstance> showInstanceList = new ArrayList<ShowInstance>();

           		Element classNameElement = DomXMLOperation.getElementByName(fieldShowsEntityElements[i],"className");
           		Element fieldShowListElement = DomXMLOperation.getElementByName(fieldShowsEntityElements[i],"fieldShowList");
           		Element[] fieldShowsElement = DomXMLOperation.getElementsByName(fieldShowListElement,"fieldShow");
           		
           		Element[] showInstancesElement = DomXMLOperation.getElementsByName(fieldShowsEntityElements[i],"showInstance");
           		
           		String className = DomXMLOperation.getElementValue(classNameElement);
           	
           		
           		List<ShowField> showFieldList = new ArrayList<ShowField>();
           		ShowInstance xmlInstance = new ShowInstance();
           		
           		if(fieldShowsElement != null){
           			for(int j=0;j<fieldShowsElement.length;j++){
               			
               			ShowField showField = new ShowField();
               			
               			Element fieldNameElement =DomXMLOperation.getElementByName(fieldShowsElement[j],"fieldName");
               			Element fieldShowNameElement =DomXMLOperation.getElementByName(fieldShowsElement[j],"fieldShowName");
               			Element fieldTargetPrimaryKeyElement =DomXMLOperation.getElementByName(fieldShowsElement[j],"fieldTargetPrimaryKey");
               			Element fieldTargetPrimaryKeyNameElement =DomXMLOperation.getElementByName(fieldShowsElement[j],"fieldTargetPrimaryKeyName");
               			Element listVisibleElement =DomXMLOperation.getElementByName(fieldShowsElement[j],"listVisible");
               			Element updateReadOnlyElement =DomXMLOperation.getElementByName(fieldShowsElement[j],"updateReadOnly");
               			Element paramNameElement =DomXMLOperation.getElementByName(fieldShowsElement[j],"paramName");
               			Element singleTagElement =DomXMLOperation.getElementByName(fieldShowsElement[j],"singleTag");
               			
               			if(fieldNameElement != null){
                        	showField.setFieldName(DomXMLOperation.getElementValue(fieldNameElement));
               			}
						if(fieldShowNameElement != null){
							showField.setFieldShowName(DomXMLOperation.getElementValue(fieldShowNameElement));			
						}
						if(fieldTargetPrimaryKeyElement != null){
							showField.setFieldTargetPrimaryKey(DomXMLOperation.getElementValue(fieldTargetPrimaryKeyElement));
						}
						if(fieldTargetPrimaryKeyNameElement != null){
							showField.setFieldTargetPrimaryKeyName(DomXMLOperation.getElementValue(fieldTargetPrimaryKeyNameElement));
						}
						if(paramNameElement != null){
	               			showField.setParamName(DomXMLOperation.getElementValue(paramNameElement));	
						}
						if(singleTagElement != null){
	               			showField.setSingleTag(DomXMLOperation.getElementValue(singleTagElement));	
						}
						if(listVisibleElement != null){
	               			showField.setListVisible(DomXMLOperation.getElementBooleanValue(listVisibleElement));
						}
						if(updateReadOnlyElement != null){
							showField.setUpdateReadOnly(DomXMLOperation.getElementBooleanValue(updateReadOnlyElement));
						}

               			showFieldList.add(showField);
               		}
           		}

           		xmlInstance.setShowFieldList(showFieldList);
           		
           		ShowInstance defaultInstance = null;
           		try {
					defaultInstance = ReflectOperation.getInitDefaultShowInstance(className);
				} catch (Exception ex) {
					ExceptionLog.CreateLog(ex);
				}
           		
           		for(int j=0;j<showInstancesElement.length;j++){

           			ShowInstance showInstance = new ShowInstance();
           			
           			Element isLoadDefaultInstanceElement =DomXMLOperation.getElementByName(showInstancesElement[j],"isLoadDefaultInstance");
           			Boolean isLoadDefaultInstance = DomXMLOperation.getElementBooleanValue(isLoadDefaultInstanceElement);
           			if(isLoadDefaultInstance == null || isLoadDefaultInstance){
						showInstance.setShowFieldList(defaultInstance.CopyShowFieldList());
						showInstance.setShowEntityName(defaultInstance.getShowEntityName());
						showInstance.setNavigationName(defaultInstance.getNavigationName());
           			}
           			else{
           				showInstance.setShowFieldList(xmlInstance.CopyShowFieldList());
           			}
           			
       				Element showInstanceNameElement =DomXMLOperation.getElementByName(showInstancesElement[j],"showInstanceName");
       				showInstance.setShowInstanceName(DomXMLOperation.getElementValue(showInstanceNameElement));
       				if(!StringUtils.isBlank(DomXMLOperation.getElementAttr(showInstanceNameElement, "windowHeight"))){
       					showInstance.setWindowHeight(Integer.parseInt(DomXMLOperation.getElementAttr(showInstanceNameElement, "windowHeight")));
       				}
       				if(!StringUtils.isBlank(DomXMLOperation.getElementAttr(showInstanceNameElement, "windowWidth"))){
       					showInstance.setWindowWidth(Integer.parseInt(DomXMLOperation.getElementAttr(showInstanceNameElement, "windowWidth")));
       				}
       				if(DomXMLOperation.getElementBooleanAttr(showInstanceNameElement, "showView") != null){
       					showInstance.setShowView(DomXMLOperation.getElementBooleanAttr(showInstanceNameElement, "showView"));
       				}
       				if(DomXMLOperation.getElementBooleanAttr(showInstanceNameElement, "showModal") != null){
       					showInstance.setShowModal(DomXMLOperation.getElementBooleanAttr(showInstanceNameElement, "showModal"));
       				}
       				
       				Element listCond =DomXMLOperation.getElementByName(showInstancesElement[j],"listConditionShow");
       				if(listCond!=null)
       					showInstance.setListConditionShow(DomXMLOperation.getElementValue(listCond));
       				
       				
       				
       				Element entityNameElement= DomXMLOperation.getElementByName(fieldShowsEntityElements[i],"showEntityName");
       				if(entityNameElement != null){
       					showInstance.setShowEntityName(DomXMLOperation.getElementValue(entityNameElement));
       				}
       				
           			Element[] fieldNameElements =DomXMLOperation.getElementsByName(showInstancesElement[j],"fieldName");

					List<ShowField> xmlField = new ArrayList<ShowField>();
					ShowField showField = null;
					for (int k = 0; k < fieldNameElements.length; k++) {
						showField = null;
						for (ShowField showField1 : showInstance.getShowFieldList()) {
							if (showField1.getFieldName().equals(
									DomXMLOperation.getElementAttr(
											fieldNameElements[k], "fieldName"))) {
								showField = showField1;
								break;
							}
						}
						
						String fieldName = DomXMLOperation.getElementAttr(
								fieldNameElements[k], "fieldName");
						String fieldShowName = DomXMLOperation.getElementAttr(
								fieldNameElements[k], "fieldShowName");
						String fieldTargetPrimaryKey = DomXMLOperation
								.getElementAttr(fieldNameElements[k],
										"fieldTargetPrimaryKey");
						String fieldTargetPrimaryKeyName = DomXMLOperation
								.getElementAttr(fieldNameElements[k],
										"fieldTargetPrimaryKeyName");
						String paramName = DomXMLOperation.getElementAttr(
								fieldNameElements[k], "paramName");
						String singleTag = DomXMLOperation.getElementAttr(
								fieldNameElements[k], "singleTag");
						Boolean listVisible = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"listVisible");
						Boolean updateReadOnly = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"updateReadOnly");
						Integer width = DomXMLOperation.getElementIntegerAttr(
								fieldNameElements[k], "width");
						Integer height = DomXMLOperation.getElementIntegerAttr(
								fieldNameElements[k], "height");
						Boolean errorSpace = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"errorSpace");
						Boolean titleField = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"titleField");
						Boolean imageField = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"imageField");
						Boolean splitField = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"splitField");
						Integer intOrder = DomXMLOperation
								.getElementIntegerAttr(fieldNameElements[k],
										"intOrder");
						Boolean thousandth = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"thousandth");
						Integer reportUnitCode = DomXMLOperation
								.getElementIntegerAttr(fieldNameElements[k],
										"reportUnitCode");
						Boolean encrypt = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"encrypt");
						Integer autoFill = DomXMLOperation
								.getElementIntegerAttr(fieldNameElements[k],
										"autoFill");
						Boolean multipleSelect = DomXMLOperation
								.getElementBooleanAttr(fieldNameElements[k],
										"multipleSelect");
						String fieldShowNamePrefix = DomXMLOperation.getElementAttr(fieldNameElements[k], "fieldShowNamePrefix");
						
						if (showField == null  &&!StringUtils.isBlank(singleTag)) {
							showField = new ShowField();
							xmlField.add(showField);
						}
						
						if(showField==null)
						{
							continue;
						}
							
						if (!StringUtils.isBlank(fieldName)) {
							showField.setFieldName(fieldName);
						}
						if (!StringUtils.isBlank(fieldShowName)) {
							showField.setFieldShowName(fieldShowName);
						}
						if (!StringUtils.isBlank(fieldTargetPrimaryKey)) {
							showField.setFieldTargetPrimaryKey(fieldTargetPrimaryKey);
						}
						if (!StringUtils.isBlank(fieldTargetPrimaryKeyName)) {
							showField.setFieldTargetPrimaryKeyName(fieldTargetPrimaryKeyName);
						}
						if (!StringUtils.isBlank(paramName)) {
							showField.setParamName(paramName);
						}
						if (!StringUtils.isBlank(singleTag)) {
							showField.setSingleTag(singleTag);
						}
						if (listVisible != null) {
							showField.setListVisible(listVisible);
						}
						if (updateReadOnly != null) {
							showField.setUpdateReadOnly(updateReadOnly);
						}
						if (width != null) {
							showField.setWidth(width);
						}
						if (height != null) {
							showField.setHeight(height);
						}
						if (errorSpace != null) {
							showField.setErrorSpace(errorSpace);
						}
						if (titleField != null) {
							showField.setTitleField(titleField);
						}
						if (imageField != null) {
							showField.setImageField(imageField);
						}
						if (splitField != null) {
							showField.setSplitField(splitField);
						}
						if (intOrder != null) {
							showField.setIntOrder(intOrder);
						}
						if (thousandth != null) {
							showField.setThousandth(thousandth);
						}
						if (reportUnitCode != null) {
							showField.setReportUnitCode(reportUnitCode);
						}
						if (encrypt != null) {
							showField.setEncrypt(encrypt);
						}
						if (autoFill != null) {
							showField.setAutoFill(autoFill);
						}
						if (multipleSelect != null) {
							showField.setMultipleSelect(multipleSelect);
						}
						if(fieldShowNamePrefix!=null){
							showField.setFieldShowNamePrefix(fieldShowNamePrefix);
						}

					}
					showInstance.getShowFieldList().addAll(xmlField);
           			for(int k=0;k<showInstance.getShowFieldList().size() - 1;k++){
           				for(int l=k+1;l<showInstance.getShowFieldList().size();l++){
           					if(showInstance.getShowFieldList().get(k).getIntOrder() > showInstance.getShowFieldList().get(l).getIntOrder()){
           						ShowField tempShowField = showInstance.getShowFieldList().get(k);
           						showInstance.getShowFieldList().set(k, showInstance.getShowFieldList().get(l));
           						showInstance.getShowFieldList().set(l, tempShowField);
           					}
           				}
           			}
           			
           			showInstanceList.add(showInstance);
           		}
           		
           		showInstanceMap.put(className, showInstanceList);
       		}
    	}

    }
    
    private void addNavigationComponentListElements(Element navigationComponentElement,ShowNavigationComponent showNavigationComponent,int level){

    	Element[] navigationComponentListElements = DomXMLOperation.getElementsByName(navigationComponentElement,"navigationComponent");
		if(navigationComponentListElements != null){
			
			String parentId = DomXMLOperation.getElementAttr(navigationComponentElement, "id");
			if(StringUtils.isBlank(parentId)){
				parentId = "-1";
			}
			
			for(int i=0;i<navigationComponentListElements.length;i++){

	       		
	       		String id=DomXMLOperation.getElementAttr(navigationComponentListElements[i], "id");
	       		if(!loadedNavComponentSet.contains(id))
	       		{
	       			ShowNavigationComponent newNavigationComponent = new ShowNavigationComponent();
		       		newNavigationComponent.setId(id);
		       		newNavigationComponent.setName(DomXMLOperation.getElementAttr(navigationComponentListElements[i], "name"));
		       		newNavigationComponent.setUrl(DomXMLOperation.getElementAttr(navigationComponentListElements[i], "url"));
		       		newNavigationComponent.setIco(DomXMLOperation.getElementAttr(navigationComponentListElements[i], "ico"));
		       		newNavigationComponent.setDes(DomXMLOperation.getElementAttr(navigationComponentListElements[i], "des"));
		       		newNavigationComponent.setTreeIco(DomXMLOperation.getElementAttr(navigationComponentListElements[i], "treeIco"));
		       		newNavigationComponent.setTreeExpandIco(DomXMLOperation.getElementAttr(navigationComponentListElements[i], "treeExpandIco"));
		       		newNavigationComponent.setParentId(parentId);
		       		newNavigationComponent.setLevel(level);
		       		
		       		/*if(showNavigationComponent == null){
		        		showNavigationComponentList.add(newNavigationComponent);
		        	}
		       		else{
		       			showNavigationComponent.getShowNavigationComponentList().add(newNavigationComponent);
		       		}*/
		       		showNavigationComponentList.add(newNavigationComponent);
		       		loadedNavComponentSet.add(id);
		       		
	       		}
	       		

	       		addNavigationComponentListElements(navigationComponentListElements[i],null,level + 1);
			}
		}
    	
    }
    private Set<String> loadedNavComponentSet;
	private void initShowNavigation(){
    	showNavigationList = new ArrayList<ShowNavigation>();
    	showNavigationComponentList = new ArrayList<ShowNavigationComponent>();
    	loadedNavComponentSet=new HashSet<String>();
    	for(int f=0;f<showNavigationLocation.length;f++){
    		if(showNavigationLocation[f].equals("")) continue;
    		
    		Element navigationRootElement = DomXMLOperation.getElementByName(showNavigationLocation[f],"navigationRoot");
          
    		Element[] navigationElements = DomXMLOperation.getElementsByName(navigationRootElement,"navigation");
    		if(navigationElements != null){
    			for(int i=0;i<navigationElements.length;i++){
            		Element levelElement = DomXMLOperation.getElementByName(navigationElements[i],"level");
               		Element navigationNameElement = DomXMLOperation.getElementByName(navigationElements[i],"navigationName");
               		Element navigationUrlElement = DomXMLOperation.getElementByName(navigationElements[i],"navigationUrl");
               		ShowNavigation showNavigation = new ShowNavigation();
               		showNavigation.setLevel(DomXMLOperation.getElementValue(levelElement));
               		showNavigation.setNavigationName(DomXMLOperation.getElementValue(navigationNameElement));
               		showNavigation.setNavigationUrl(DomXMLOperation.getElementValue(navigationUrlElement));
               		showNavigationList.add(showNavigation);
            	}
    		}

        	Element navigationComponentElement = DomXMLOperation.getElementByName(navigationRootElement,"navigationComponentList");
        	if(navigationComponentElement != null){
        		addNavigationComponentListElements(navigationComponentElement,null,1);
        	}
    	}
    	loadedNavComponentSet=null;
    	
    }

	private void initShowEntity(){
		showEntityMap = new HashMap<String, Map<String,String>>();
		for(int f=0;f<showEntityLocation.length;f++){ // 每个常量文件
			if(showEntityLocation[f].equals("")) continue;
			
    		Element entityRootElement = DomXMLOperation.getElementByName(showEntityLocation[f],"entityRoot");
           	Element[] entityMapElements = DomXMLOperation.getElementsByName(entityRootElement,"entityMap");
        	for(int i=0;i<entityMapElements.length;i++){
        		
        		Element entityNameElement = DomXMLOperation.getElementByName(entityMapElements[i],"entityName");
        		Element[] entityElements = DomXMLOperation.getElementsByName(entityMapElements[i],"entity");
        		String entityName=DomXMLOperation.getElementValue(entityNameElement);
        		Map<String,String> entitys =null;
        		if(showEntityMap.containsKey(entityName))
        		{
        			entitys =showEntityMap.get(entityName);
        		}
        		else
        		{
        			entitys = new LinkedHashMap<String,String>();
        			showEntityMap.put(entityName, entitys);
        		}
        		 
        		
        		for(int j=0;j<entityElements.length;j++){
        			Element codeElement = DomXMLOperation.getElementByName(entityElements[j],"code");
               		Element nameElement = DomXMLOperation.getElementByName(entityElements[j],"name");
               		entitys.put(DomXMLOperation.getElementValue(codeElement), DomXMLOperation.getElementValue(nameElement));
               		
        		}
        	}
    	}
	}
	
    private Map<String, List<ShowInstance>> showInstanceMap;
    
	public Map<String, List<ShowInstance>> getShowInstanceMap() {
		return showInstanceMap;
	}
	
	private List<ShowNavigation> showNavigationList;
	
	public List<ShowNavigation> getShowNavigationList() {
		return showNavigationList;
	}
	
	private List<ShowNavigationComponent> showNavigationComponentList;
	
	public List<ShowNavigationComponent> getShowNavigationComponentList() {
		return showNavigationComponentList;
	}
	
	private Map<String, Map<String,String>> showEntityMap;
	
	public Map<String, Map<String,String>> getShowEntityMap() {
		return showEntityMap;
	}

	private static String[] showInstanceLocation; 

	public static void setShowInstanceLocation(String[] showInstanceLocation) {
		ShowContext.showInstanceLocation = showInstanceLocation;
	}
	
    private static String[] showNavigationLocation; 

    public static void setShowNavigationLocation(
			String[] showNavigationLocation) {
		ShowContext.showNavigationLocation = showNavigationLocation;
	}
    
    private static String[] showEntityLocation; 
    
    public static void setShowEntityLocation(String[] showEntityLocation) {
		ShowContext.showEntityLocation = showEntityLocation;
	}
}
