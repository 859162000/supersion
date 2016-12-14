package report.service.imps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.xwork.StringUtils;

import report.dto.TaskFlow;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.SingleObjectShowTreeService;
import framework.show.ShowTree;
import framework.show.ShowTreeNode;
/**
 * 汇总数据-任务按主题分类
 * @author xiajieli
 * @since 2016-10-18
 */
public class ReportTaskFlowShowTreeService extends SingleObjectShowTreeService {

	private String[] strSuitCode;

	@Override
	public Object objectResultExecute() throws Exception {
		ShowTree treeList = (ShowTree) super.objectResultExecute();
		List<ShowTreeNode> treeValue = treeList.getTreeValue();
		List<ShowTreeNode> NewtreeValue = new ArrayList<ShowTreeNode>();
		List<ShowTreeNode> NewtreeValue1 = new ArrayList<ShowTreeNode>();
		List<ShowTreeNode> NewtreeValue2 = new ArrayList<ShowTreeNode>();
		List<ShowTreeNode> NewtreeValue3 = new ArrayList<ShowTreeNode>();
		List<ShowTreeNode> NewtreeValue4 = new ArrayList<ShowTreeNode>();

		for (ShowTreeNode showTreeNode : treeValue) {
			if (showTreeNode.getParentID().equals("-1")) {
				NewtreeValue1.add(showTreeNode);
			} else if (StringUtils.isBlank(showTreeNode.getURL())) {
				NewtreeValue2.add(showTreeNode);
			} else {
				NewtreeValue3.add(showTreeNode);
			}
		}
		if(null != strSuitCode && strSuitCode.length>0){
			for (ShowTreeNode showTreeNode : NewtreeValue3) {
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				String url = showTreeNode.getURL();
				String id = url.substring(url.indexOf("id=") + 3, url.indexOf("&windowId"));
				TaskFlow taskFlow = (TaskFlow) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { TaskFlow.class.getName(), id, null });
				for (String suitCode : strSuitCode) {
					if (taskFlow != null && taskFlow.getSuit() != null && taskFlow.getSuit().getStrSuitCode().equals(suitCode)) {
						NewtreeValue.add(showTreeNode);
						break;
					}
				}
			}
		}
		else{
			strSuitCode=new String[]{"01","03","14","07"};
			for (ShowTreeNode showTreeNode : NewtreeValue3) {
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				String url = showTreeNode.getURL();
				String id = url.substring(url.indexOf("id=") + 3, url.indexOf("&windowId"));
				TaskFlow taskFlow = (TaskFlow) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { TaskFlow.class.getName(), id, null });
				
				boolean isMarch=true; //默认为自定义主题
				for (String suitCode : strSuitCode) {
					if (taskFlow != null && taskFlow.getSuit()!=null && taskFlow.getSuit().getStrSuitCode().equals(suitCode)) {
						isMarch=false;
						break;
					}
				}
				if(isMarch){
					NewtreeValue.add(showTreeNode);
				}
			}
		}
		
		Set<String> setList = new HashSet<String>();
		
		for (ShowTreeNode showTreeNode : NewtreeValue) {
			if (!setList.contains(showTreeNode.getParentID())) {
				for (ShowTreeNode showTreeNode2 : NewtreeValue2) {
					if (showTreeNode2.getTreeNodeID().equals(showTreeNode.getParentID())) {
						NewtreeValue4.add(showTreeNode2);
						setList.add(showTreeNode.getParentID());
						break;
					}
				}
			}
		}
		
		//任务节点层按任务名称进行排序（其实是按任务数据日期排序，因为程序里经过处理后的任务名中以任务数据日期开头）
		Collections.sort(NewtreeValue4,new Comparator<ShowTreeNode>(){
			public int compare(ShowTreeNode stn1, ShowTreeNode stn2) {
				return stn1.getShowName().compareTo(stn2.getShowName());
			}	
		});
		
		
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
