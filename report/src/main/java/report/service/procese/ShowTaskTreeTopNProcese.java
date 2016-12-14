package report.service.procese;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import report.dto.TaskFlow;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowTree;
import framework.show.ShowTreeNode;

/**
 * 项目名称：report<br>
 * *********************************<br>
 * <P>类名称：ShowTaskTreeProcese</P>
 * *********************************<br>
 * <P>类描述：任务树构建</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-4 上午10:30:05<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-4 上午10:30:05<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class ShowTaskTreeTopNProcese implements IProcese {

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		String rootNodeId = UUID.randomUUID().toString();
		ShowTree treeList = initTree(rootNodeId, "-1", ShowParamManager.getShowTreeRootName());
		List<Object> objectList = (List<Object>) serviceResult;
		for (Object object : objectList) {
			TaskFlow taskFlow = (TaskFlow) object;
			if (!taskFlow.getStrTaskState().equals("1") || (taskFlow.getTaskStartData().getTime() > new Date().getTime()) || ((taskFlow.getTaskEndData().getTime() + 86400000) < new Date().getTime())) {
				continue;
			} else {
				ShowTreeNode node = newTreeNode(UUID.randomUUID().toString(), rootNodeId, taskFlow.getDtTaskDate()+""+taskFlow.getStrTaskName());
				node.setURL("report/ShowTaskRptInstToTopN-report.dto.TaskRptInst.action?id="+taskFlow.getId()+"&windowId="+RequestManager.getWindowId());
				treeList.getTreeValue().add(node);
			}
		}
		return treeList;
	}

	/**
	 * <p>方法描述: 初始化任务树</p>
	 * 
	 * <p>方法备注: </p>
	 * 
	 * @return
	 * 
	 * <p>创建人：王川</p>
	 * 
	 * <p>创建时间：2016-7-1 上午10:38:16</p>
	 * 
	 */
	private ShowTree initTree(String nodeId, String parentId, String nodeName) {
		ShowTree treeList = new ShowTree();
		ShowTreeNode showTreeNode = newTreeNode(nodeId, parentId, nodeName);
		treeList.getTreeValue().add(showTreeNode);
		return treeList;
	}

	/**
	 * <p>方法描述:新建树节点 </p>
	 * 
	 * <p>方法备注: </p>
	 * 
	 * @return
	 * 
	 * <p>创建人：王川</p>
	 * 
	 * <p>创建时间：2016-7-1 上午10:37:54</p>
	 * 
	 */
	private ShowTreeNode newTreeNode(String nodeId, String parentId, String nodeName) {
		ShowTreeNode showTreeNode = new ShowTreeNode();
		showTreeNode.setParentID(parentId);
		showTreeNode.setShowName(nodeName);
		showTreeNode.setTreeNodeID(nodeId);
		String normalImageURL = "";
		String expandImageURL = "";
		String[] images = ShowParamManager.getShowTreeImage().get(0).split(",");
		normalImageURL = images[0];
		if (images.length == 1) {
			expandImageURL = images[0];
		}
		if (images.length == 2) {
			expandImageURL = images[1];
		}
		showTreeNode.setNormalImageURL(normalImageURL);
		showTreeNode.setExpandImageURL(expandImageURL);
		return showTreeNode;
	}

}
