package framework.services.procese;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowTree;
import framework.show.ShowTreeNode;


public class ShowTreeProcese implements IProcese {

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		if(ShowParamManager.getTreeServiceMap() == null){
			
			Class<?> type = Class.forName(RequestManager.getTName());
			
			String typeName ="";
			for(Map.Entry<String, String> entry : ShowParamManager.getForeignFieldMap().entrySet()){
				typeName += ReflectOperation.getReflectField(type,entry.getKey()).getType().getName() + ",";
			}
			typeName = typeName.substring(0,typeName.length()-1);

			IParamObjectResultExecute defaultLogicDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean(LogicParamManager.getDefaultLogicDaoBeanId());

			ShowTree treeList = new ShowTree();
	    	
			ShowTreeNode showTreeNode = new ShowTreeNode();
			showTreeNode.setParentID("-1");
			showTreeNode.setShowName(ShowParamManager.getShowTreeRootName());
			showTreeNode.setTreeNodeID(UUID.randomUUID().toString());
			String normalImageURL = "";
			String expandImageURL = "";
			String[] images = ShowParamManager.getShowTreeImage().get(0).split(",");
			normalImageURL = images[0];
			if(images.length == 1){
				expandImageURL = images[0];
			}
			if(images.length == 2){
				expandImageURL = images[1];
			}
			showTreeNode.setNormalImageURL(normalImageURL);
			showTreeNode.setExpandImageURL(expandImageURL);
			
			treeList.getTreeValue().add(showTreeNode);

			if(ShowParamManager.getConstructType() == null || ShowParamManager.getConstructType().equals("1")){
				String actionTName = null;
				if(StringUtils.isBlank(RequestManager.getTypeName())){
					actionTName = RequestManager.getTName();
				}
				else{
					actionTName = RequestManager.getTypeName();
				}
				if(ShowParamManager.getOperationMap() != null){
					String operationValue = ShowParamManager.getOperationMap().get("");
					String namespace = TActionRule.getNamespace(operationValue);
					String url = namespace + "/" + TActionRule.getCurrentLevelOperationActionName(operationValue, actionTName) + ".action";
					url += "?id=" + "&type=" + typeName;
					String windowId = RequestManager.getWindowId();
					if(!StringUtils.isBlank(windowId)){
						url += "&windowId=" + windowId;
					}
					showTreeNode.setURL(url);
				}
				ConstructTreeNode(type,defaultLogicDao,(List<Object>)serviceResult,showTreeNode.getTreeNodeID(),treeList.getTreeValue(),-1,typeName,actionTName);
			}
			else if(ShowParamManager.getConstructType().equals("2")){

				List<Object> objectList = (List<Object>)serviceResult;
				
				List<Object> needObjectList = new ArrayList<Object>();
				Set<String> primaryKeySet = new HashSet<String>();
				for(Object object:objectList){
					String primaryKeyString = "";
					for(Map.Entry<String, String> entry : ShowParamManager.getForeignFieldMap().entrySet()){
						Object foreignObject = ReflectOperation.getFieldValue(object, entry.getKey());
						String foreignPrimaryKeyValue = ReflectOperation.getPrimaryKeyValue(foreignObject).toString();
						primaryKeyString += foreignPrimaryKeyValue + "*";
					}
					if(!primaryKeySet.contains(primaryKeyString)){
						primaryKeySet.add(primaryKeyString);
						needObjectList.add(object);
					}
				}
				ConstructTreeNode(needObjectList,showTreeNode.getTreeNodeID(),treeList.getTreeValue(),-1,typeName);
			}
			else if(ShowParamManager.getConstructType().equals("3")){
				
				typeName="";
				for(Map.Entry<String, String> entry : ShowParamManager.getForeignFieldMap().entrySet()){
					typeName += ReflectOperation.getReflectField(type,entry.getKey()).getType().getName() + ",";
					typeName += ReflectOperation.getReflectField(type,entry.getValue()).getType().getName();
					break;
				}
				if(ShowParamManager.getOperationMap() != null){
					String operationValue = ShowParamManager.getOperationMap().get("");
					String namespace = TActionRule.getNamespace(operationValue);//.substring(1);;
					if(!("_blank").equals(ShowParamManager.getShowTreeRootUrl())){
						String url="";
						if(ShowParamManager.getConstructType().equals("3"))
						{
							String action=TActionRule.getCurrentLevelOperationActionName(operationValue,  RequestManager.getTName());
							String op=TActionRule.getAction(action);
							url = namespace + "/" +op+"-"+typeName.split(",")[1]+ ".action";
							url += "?id=&type=" +  RequestManager.getTName()+","+typeName.split(",")[0];
						}
						
						
						String windowId = RequestManager.getWindowId();
						if(!StringUtils.isBlank(windowId)){
							url += "&windowId=" + windowId;
						}
						showTreeNode.setURL(url);
					}
				}

				List<Object> objectList = (List<Object>)serviceResult;
				
				List<Object> needObjectList = new ArrayList<Object>();
				Set<String> primaryKeySet = new HashSet<String>();
				for(Object object:objectList){
					String primaryKeyString = "";
					for(Map.Entry<String, String> entry : ShowParamManager.getForeignFieldMap().entrySet()){
						Object foreignObject = ReflectOperation.getFieldValue(object, entry.getKey());
						String foreignPrimaryKeyValue = ReflectOperation.getPrimaryKeyValue(foreignObject).toString();
						primaryKeyString += foreignPrimaryKeyValue + "*";
					}
					if(!primaryKeySet.contains(primaryKeyString)){
						primaryKeySet.add(primaryKeyString);
						needObjectList.add(object);
					}
				}
				
				//typeName = typeName.substring(0,typeName.length()-1);
				
				ConstructTreeNode(needObjectList,showTreeNode.getTreeNodeID(),treeList.getTreeValue(),-1,typeName);

			}
			return treeList;
		}
		else{
			ShowTree treeList = new ShowTree();
			
			for(Map.Entry<String, String> entry : ShowParamManager.getTreeServiceMap().entrySet()){
				String[] types = entry.getValue().split(",");
				RequestManager.setTName(types[0]);
				if(types.length == 2){
					RequestManager.setTypeName(types[1]);
				}
				IObjectResultExecute objectResultExecute = (IObjectResultExecute)FrameworkFactory.CreateBean(entry.getKey());
				ShowTree temptTreeList = (ShowTree)objectResultExecute.objectResultExecute();
				for(ShowTreeNode showTreeNode : temptTreeList.getTreeValue()){
					treeList.getTreeValue().add(showTreeNode);
				}
			}
			
			return treeList;
		}
		
	}
	
	private void ConstructTreeNode(List<Object> objectList,String parentID,List<ShowTreeNode> showTreeNodeList,int level,String typeName) throws Exception{
		level++;

		int i = 0;
		String entryKey = null;
		for(Map.Entry<String, String> entry : ShowParamManager.getForeignFieldMap().entrySet()){
			if(i == level){
				entryKey = entry.getKey();
			}
			i++;
		}
		if(entryKey == null){
			return;
		}
		
		int imageLevel = level + 1;
		if(imageLevel > ShowParamManager.getShowTreeImage().size() - 1){
			imageLevel = ShowParamManager.getShowTreeImage().size() - 1;
		}
		
		String normalImageURL = "";
		String expandImageURL = "";
		String[] images = ShowParamManager.getShowTreeImage().get(imageLevel).split(",");
		normalImageURL = images[0];
		if(images.length == 1){
			expandImageURL = images[0];
		}
		if(images.length == 2){
			expandImageURL = images[1];
		}
		
		boolean isNeedUrl = false;
		if(level == ShowParamManager.getForeignFieldMap().size() -1){
			isNeedUrl = true;
		}
		
		final String enKey = entryKey;

		Collections.sort(objectList, new Comparator<Object>() {
		      public int compare(Object o1, Object o2) {
		    	  
		    	  String str0 = "";
		    	  String str1 = "";
		    	  try {
					Object foreignObject0 = ReflectOperation.getFieldValue(o1, enKey);
					Object foreignObject1 = ReflectOperation.getFieldValue(o2, enKey);
					
					str0 = ReflectOperation.getPrimaryKeyValue(foreignObject0).toString();
					str1 = ReflectOperation.getPrimaryKeyValue(foreignObject1).toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		    	return str0.compareTo(str1);
		      }
		    });
		    
		
		Set<Object> primaryKeyValueSet = new HashSet<Object>();
		for(Object object : objectList){
			Object foreignObject = ReflectOperation.getFieldValue(object, entryKey);
			if(!primaryKeyValueSet.contains(foreignObject)){
				primaryKeyValueSet.add(foreignObject);
				List<Object> tempObjectList = new ArrayList<Object>();
				for(Object object1 : objectList){
					Object foreignObject1 = ReflectOperation.getFieldValue(object1, entryKey);
			
					if(foreignObject == foreignObject1){
						tempObjectList.add(object1);
					}
				}

				String primaryKeyValue = ReflectOperation.getPrimaryKeyValue(object).toString();
				String treeNodeId = UUID.randomUUID().toString();
				String foreignPrimaryKeyNameValue = ReflectOperation.getFieldValue(foreignObject, ReflectOperation.getKeyNameField(foreignObject.getClass()).getName()).toString();
				
				ShowTreeNode showTreeNode = new ShowTreeNode();
				showTreeNode.setExpandImageURL(expandImageURL);
				showTreeNode.setNormalImageURL(normalImageURL);
				showTreeNode.setParentID(parentID);
				showTreeNode.setShowName(foreignPrimaryKeyNameValue);
				showTreeNode.setTreeNodeID(treeNodeId);
				if(isNeedUrl){
					if(ShowParamManager.getOperationMap() != null){
						String operationValue = ShowParamManager.getOperationMap().get("");
						String namespace = TActionRule.getNamespace(operationValue);//.substring(1);;
						
						String url="";
						if(ShowParamManager.getConstructType().equals("3"))
						{
							String action=TActionRule.getCurrentLevelOperationActionName(operationValue,  RequestManager.getTName());
							String op=TActionRule.getAction(action);
							url = namespace + "/" +op+"-"+typeName.split(",")[1]+ ".action";
							url += "?id=" + foreignPrimaryKeyNameValue+ "&type=" +  RequestManager.getTName()+","+typeName.split(",")[0];
						}
						else
						{
							url = namespace + "/" + TActionRule.getCurrentLevelOperationActionName(operationValue, RequestManager.getTName()) + ".action";
							url += "?id=" + primaryKeyValue+ "&type=" + typeName;
						}
						
						
						
						String windowId = RequestManager.getWindowId();
						if(!StringUtils.isBlank(windowId)){
							url += "&windowId=" + windowId;
						}
						showTreeNode.setURL(url);
					}
				}
				
				showTreeNodeList.add(showTreeNode);
				
				if(tempObjectList.size()>0){
					ConstructTreeNode(tempObjectList,treeNodeId,showTreeNodeList,level,typeName);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void ConstructTreeNode(Class<?> type, IParamObjectResultExecute defaultLogicDao, List<Object> objectList,String parentID,List<ShowTreeNode> showTreeNodeList,int level,String typeName,String actionTName) throws Exception{
		level++;
		int imageLevel = level + 1;
		if(imageLevel > ShowParamManager.getShowTreeImage().size() - 1){
			imageLevel = ShowParamManager.getShowTreeImage().size() - 1;
		}
		
		String normalImageURL = "";
		String expandImageURL = "";
		String[] images = ShowParamManager.getShowTreeImage().get(imageLevel).split(",");
		normalImageURL = images[0];
		if(images.length == 1){
			expandImageURL = images[0];
		}
		if(images.length == 2){
			expandImageURL = images[1];
		}
		
		for(Object object : objectList){

			String primaryKeyValue = ReflectOperation.getPrimaryKeyValue(object).toString();
			String treeNodeId = UUID.randomUUID().toString();
			
			ShowTreeNode showTreeNode = new ShowTreeNode();
			showTreeNode.setExpandImageURL(expandImageURL);
			showTreeNode.setNormalImageURL(normalImageURL);
			showTreeNode.setParentID(parentID);
			showTreeNode.setShowName(ReflectOperation.getFieldValue(object, ReflectOperation.getKeyNameField(type).getName()).toString());
			showTreeNode.setTreeNodeID(treeNodeId);
			if(ShowParamManager.getOperationMap() != null){
				String operationValue = ShowParamManager.getOperationMap().get("");
				String namespace = TActionRule.getNamespace(operationValue);
				String url = namespace + "/" + TActionRule.getCurrentLevelOperationActionName(operationValue, actionTName) + ".action";
				url += "?id=" + primaryKeyValue+ "&type=" + typeName;
				String windowId = RequestManager.getWindowId();
				if(!StringUtils.isBlank(windowId)){
					url += "&windowId=" + windowId;
				}
				showTreeNode.setURL(url);
			}
			showTreeNodeList.add(showTreeNode);
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);
			
			for(Map.Entry<String, String> entry : ShowParamManager.getForeignFieldMap().entrySet()){
				if(StringUtils.isBlank(entry.getValue())){
					detachedCriteria.add(Restrictions.eq(entry.getKey(), object));
				}
				else{
					Object foreigObject = ReflectOperation.getFieldValue(object, entry.getValue());
					detachedCriteria.add(Restrictions.eq(entry.getKey(), foreigObject));
				}
			}
			
			Field primaryKey = ReflectOperation.getPrimaryKeyField(type);
			Set<Object> valueSet = LogicParamManager.getTreeDataSecurity();
			if(valueSet != null){
				if(valueSet.size() == 0){
					detachedCriteria.add(Restrictions.isNull(primaryKey.getName()));
				}
				else{
					detachedCriteria.add(Restrictions.in(primaryKey.getName(),valueSet));
				}
			}

			if(SessionManager.getCurrentLevel() != null){
				String currentLevelLevel = SessionManager.getCurrentLevel();
				if(SessionManager.getLevelTName(currentLevelLevel) != null && SessionManager.getLevelIdValue(currentLevelLevel) != null){
					String previousLevelLevelTName  = SessionManager.getPreviousLevelTName();
					Object foreignObject = FrameworkFactory.CreateClass(previousLevelLevelTName);
					Field primaryField = ReflectOperation.getPrimaryKeyField(previousLevelLevelTName);
					ReflectOperation.setFieldValue(foreignObject, primaryField.getName(), SessionManager.getLevelIdValue(currentLevelLevel));
					
					Field foreignField = null;
					Field[] fields = ReflectOperation.getReflectFields(Class.forName(RequestManager.getTName()));
					for(int i=0;i<fields.length;i++){
						if(fields[i].getType().equals(Class.forName(previousLevelLevelTName))){ 
							foreignField = fields[i];
							break;
						}
					}
					detachedCriteria.add(Restrictions.eq(foreignField.getName(), foreignObject));
				}
			}
			
			
			LogicParamManager.setDetachedCriteria(detachedCriteria);
			ConstructTreeNode(type,defaultLogicDao,(List<Object>)defaultLogicDao.paramObjectResultExecute(null),treeNodeId, showTreeNodeList,level,typeName,actionTName);
		}
	}
}
