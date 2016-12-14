package report.service.imps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.dto.TaskModelInst;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.SingleObjectShowTreeService;
import framework.show.ShowContext;
import framework.show.ShowTree;
import framework.show.ShowTreeNode;

public class ReportTaskModelInstShowTreeService  extends SingleObjectShowTreeService {

	private String[] strSuitCode;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws Exception {
		super.init();
		((Map<String,Object>)ServletActionContext.getContext().get("request")).put("show_treeSuitCode", strSuitCode);
	}
	
    @Override
	public Object objectResultExecute() throws Exception {
	 
		  ShowTree treeList=(ShowTree)super.objectResultExecute();
		  List<ShowTreeNode> treeValue=treeList.getTreeValue();
		  List<ShowTreeNode> NewtreeValue=new ArrayList<ShowTreeNode>();
		  List<ShowTreeNode> NewtreeValue1=new ArrayList<ShowTreeNode>();
		  List<ShowTreeNode> NewtreeValue2=new ArrayList<ShowTreeNode>();
		  List<ShowTreeNode> NewtreeValue3=new ArrayList<ShowTreeNode>();
		  List<ShowTreeNode> NewtreeValue4=new ArrayList<ShowTreeNode>();
		  
		  for (ShowTreeNode showTreeNode : treeValue) {
			  if(showTreeNode.getParentID().equals("-1")){
				  NewtreeValue1.add(showTreeNode);
			  }else if(StringUtils.isBlank(showTreeNode.getURL())){
				  NewtreeValue2.add(showTreeNode);
			  }else{
				  NewtreeValue3.add(showTreeNode);
			  }
		  }
		  
		  if(null != strSuitCode && strSuitCode.length>0){
			  for (ShowTreeNode showTreeNode : NewtreeValue3) {
				    IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					String url=showTreeNode.getURL();
					String id=url.substring(url.indexOf("id=")+3, url.indexOf("&type"));
				    TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),id,null});
				    for (String  suitCode: strSuitCode) { 
					    if(taskModelInst !=null && taskModelInst.getTaskFlow().getSuit()!=null && taskModelInst.getTaskFlow().getSuit().getStrSuitCode().equals(suitCode)){
							 NewtreeValue.add(showTreeNode);
							 break;
					     }
				    }
				}
		  }
		 
		  else{ //如果主题为空，则只显示自定义主题  modifys by  xiajieli 2016-10-19
			  String strArr="";
			  Map<String,String> AllSystemSuit=ShowContext.getInstance().getShowEntityMap().get("AllSystemSuit");
			  for (Map.Entry<String, String> entry : AllSystemSuit.entrySet()) {
		    	strArr+=entry.getKey()+",";
			  }
			  strSuitCode=strArr.split(",");
			  for (ShowTreeNode showTreeNode : NewtreeValue3) {
				    IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					String url=showTreeNode.getURL();
					String id=url.substring(url.indexOf("id=")+3, url.indexOf("&type"));
				    TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),id,null});
				    
				    boolean isMarch=true; //默认为自定义主题
				    for (String  suitCode: strSuitCode) {
				    	if(taskModelInst !=null && taskModelInst.getTaskFlow().getSuit()!=null && taskModelInst.getTaskFlow().getSuit().getStrSuitCode().equals(suitCode)){
							 isMarch=false;
							 break;
					    }
				    }
				    if(isMarch){
				    	NewtreeValue.add(showTreeNode);
				    }
			  }
			 
		  }
		 
		 Set<String> setList=new HashSet<String>();
		 for (ShowTreeNode showTreeNode : NewtreeValue) {
			if(!setList.contains(showTreeNode.getParentID())){
				for (ShowTreeNode showTreeNode2 : NewtreeValue2) {
					if(showTreeNode2.getTreeNodeID().equals(showTreeNode.getParentID())){
						NewtreeValue4.add(showTreeNode2);
						setList.add(showTreeNode.getParentID());
						break;
					}
				}
			}
		 }
		 for (ShowTreeNode showTreeNode : NewtreeValue4) {
				NewtreeValue.add(showTreeNode);
			}
		 
		 for (ShowTreeNode showTreeNode : NewtreeValue1) {
			NewtreeValue.add(showTreeNode);
		}
		 
		 treeList.setTreeValue(NewtreeValue);
		 return treeList;
	  
	 
    }

	public void setStrSuitCode(String[] strSuitCode) {
		this.strSuitCode = strSuitCode;
	}

	public String[] getStrSuitCode() {
		return strSuitCode;
	}
	
	
	
}
