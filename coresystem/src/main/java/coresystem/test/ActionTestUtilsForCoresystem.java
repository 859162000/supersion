package coresystem.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

import sun.tools.tree.ThisExpression;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import extend.dto.Suit;
import framework.show.ShowTreeNode;
import framework.test.ActionTestUtils;

public class ActionTestUtilsForCoresystem {
	private static Class<?> clazz = null;
	private static String line = System.getProperty("line.separator");
	
	/********************单个DTO（与自身有外键关联）树形菜单数据操作start********************/
	
	/**
	 * 
	 * <P>根据指定的查询条件（与Action生成的树形菜单同等条件），获取单个DTO<b>（与自身有外键关联）</b>对应的树形菜单Map数据对象</P>
	 * @param dc 与Action生成的树形菜单同等条件的数据库查询条件
	 * @return Map<InstInfo, List<InstInfo>> 返回与Action生成的树形菜单同等条件的树形菜单Map数据对象
	 * @throws Exception <P>当传入的查询条件为空时，会抛出“获取数据库信息失败，当前传入的查询条件对象为空”的异常信息</P>
	 */
	private static Map<Object, List<Object>> getSingleDtoTreeMapFromDB(DetachedCriteria dc) throws Exception {
		if(dc != null) {
			List<Object> objList = ActionTestUtils.getDBData(dc);// 获取数据库中符合条件且机构状态为“营业”的机构信息
			
			// 生成机构树
			return getSingleDtoTreeMap(objList);
		} else {
			throw new Exception("获取数据库信息失败，当前传入的查询条件对象为空；");
		}
	}
	
	/**
	 * 
	 * <P>通过单个<b>（与自身有外键关联）</b>DTO数据获取对应的树形菜单Map数据对象</P>
	 * 
	 * @param objList 单个<b>（与自身有外键关联）</b>DTO数据
	 * @return Map<String,List<InstInfo>> 树形菜单数据Map
	 * 
	 * @author Liutao
	 * @throws Exception <P>如果传入的单个DTO<b>（与自身有外键关联）</b>数组数据为空，则会抛出"获取预期的树形菜单数据错误，传入的单个DTO<b>（与自身有外键关联）</b>数组数据为空"的异常信息</P>
	 *
	 */
	private static Map<Object, List<Object>> getSingleDtoTreeMap(List<Object> objList) throws Exception{
		if(objList != null && objList.size() > 0) {
			List<Object> tempInst = new ArrayList<Object>();// 临时数组
			Map<Object, List<Object>> treeMap = new HashMap<Object, List<Object>>();
			// 找出所有的最顶级的机构信息，即没有上级机构的所有机构信息
			for(Object obj : objList) {
				if((obj.getClass().equals(InstInfo.class) && ((InstInfo) obj).getHigherInst() == null) 
						|| (obj.getClass().equals(Suit.class) && ((Suit) obj).getHigerSuit() == null) ) {
					tempInst.add(obj);
					treeMap.put(obj, new ArrayList<Object>());
				}
			}
			
			// 将临时数组里面的数据从InstList数组中删除
			if(tempInst.size() > 0) {
				objList.removeAll(tempInst);
			}
			
			setgetSingleDtoChildList(objList, treeMap);
			return treeMap;
		} else {
			throw new Exception("获取预期的树形菜单数据错误，传入的单个DTO<b>（与自身有外键关联）</b>数组数据为空;"+line);
		}
	}
	
	/**
	 * 
	 * <P>获取单个DTO（有自身有外键关联）的整个树形菜单Map</P>
	 * 
	 * @param objList 待解析的去除第一层级菜单项数据的单个DTO（有自身有外键关联）数据数组
	 * @param treeMap 第一层级的菜单项数据
	 * 
	 * @author Liutao
	 *
	 */
	private static void setgetSingleDtoChildList(List<Object> objList, Map<Object, List<Object>> treeMap){
		if(objList != null && treeMap != null
				&& objList.size() > 0
				&& treeMap.size() > 0) {
			Set<Object> keySet = treeMap.keySet();
			List<Object> tempList = new ArrayList<Object>();
			
			Object higherObject = null;
			for(Object obj : objList) {
				if(obj.getClass().equals(InstInfo.class)) {
					higherObject = ((InstInfo) obj).getHigherInst();// 获取当前机构的上级机构
				}
				
				if(obj.getClass().equals(Suit.class)) {
					higherObject = ((Suit) obj).getHigerSuit();
				}
				
				if(keySet.contains(higherObject)) {// 当前机构的上级机构在map中
					treeMap.get(higherObject).add(obj);
					treeMap.put(higherObject, new ArrayList<Object>());// 将当前的机构编码放入到map中
					tempList.add(obj);// 将当前机构放入到临时数组中
				}
			}

			if(!tempList.isEmpty()) {
				objList.removeAll(tempList);
				tempList = null;// 清空临时数组
				if(!objList.isEmpty()) {// 判断待解析的机构数组是否为空
					setgetSingleDtoChildList(objList, treeMap);
				}
			}
		}
	}
	
	/**
	 * 
	 * <P>判断当前Action执行结果产生的单个DTO<b>（与自身有外键关联）</b>树形菜单的数据是否正确</P>
	 * 
	 * @param treeValue Action执行之后返回的树形菜单数据 
	 * @param dc 与Action产生树形菜单数据同等的数据查询条件
	 * @author Liutao
	 *
	 */
	public static void checkSingleDtoTree(List<ShowTreeNode> treeValue, DetachedCriteria dc, Class<?> clazz){
		String message = "";
		if(treeValue == null || treeValue.isEmpty()) {
			message += "Action执行完毕之后返回的机构树形菜单为空；"+line;
		} 
		if(dc == null) {
			message += "传入的机构信息查询条件参数为空；"+line;
		}
		
		if(clazz == null) {
			message += "传入的当前操作对应DTO的Class为空；"+line;
		}
		
		if(!StringUtils.isBlank(message)) {
			ActionTestUtils.setTestResultWhenException(null, message); 
		} else {
			try {
				ActionTestUtilsForCoresystem.clazz = clazz;
				message = checkSingleDtoTree(treeValue, getSingleDtoTreeMapFromDB(dc));
				
				if(!StringUtils.isBlank(message)) {
					ActionTestUtils.setTestResultWhenException(null, message); 
				}
			} catch (Exception e) {
				ActionTestUtils.setTestResultWhenException(e, null); 
			}
		}
	}
	
	/**
	 * 
	 * <P>判断当前Action执行结果产生的单个DTO<b>（与自身有外键关联）</b>树形菜单的数据是否正确</P>
	 * 
	 * @param treeValue Action执行之后返回的树形菜单数据 
	 * @param objList 单个<b>（与自身有外键关联）</b>DTO数据数组
	 * @author Liutao
	 *
	 */
	public static void checkSingleDtoTree(List<ShowTreeNode> treeValue, List<Object> objList, Class<?> clazz){
		String message = "";
		
		if(treeValue == null || treeValue.isEmpty()) {
			message += "Action执行完毕之后返回的机构树形菜单为空；"+line;
		}
		if(objList == null || objList.isEmpty()) {
			message += "传入的机构信息数组参数为空；"+line;
		}
		
		if(clazz == null) {
			message += "传入的当前操作对应DTO的Class为空；"+line;
		}
		
		if(!StringUtils.isBlank(message)) {
			ActionTestUtils.setTestResultWhenException(null, message); 
		} else {
			try {
				ActionTestUtilsForCoresystem.clazz = clazz;
				message = checkSingleDtoTree(treeValue, getSingleDtoTreeMap(objList));
				
				if(!StringUtils.isBlank(message)) {
					ActionTestUtils.setTestResultWhenException(null, message); 
				}
			} catch (Exception e) {
				ActionTestUtils.setTestResultWhenException(e, null); 
			}
		}
	}
	
	
	/**
	 * 
	 * <P>判断当前机构信息树形菜单显示是否正确</P>
	 * <P>1、检测机构树数据长度是否正确</P>
	 * <P>2、检测每一层机构信息下的直接子节点的长度是否正确</P>
	 * <P>3、检测每一层机构信息下的直接子节点的内容是否正确</P>
	 * <P>4、检测每一个机构子菜单所对应的URL参数是否正确</P>
	 * 
	 * @param treeValue action执行之后返回的机构树形菜单数据 
	 * @param treeMap 预期的树形菜单数据Map
	 * @return String 判断之后的错误信息 
	 * @author Liutao
	 * @throws Exception <P>获取的当前操作的单个<b>（与自身有外键关联）</b>）DTO的Class为空时，则会抛出“获取预期的树形菜单中的菜单项Class出错”异常</P>
	 *
	 */
	private static String checkSingleDtoTree(List<ShowTreeNode> treeValue, Map<Object, List<Object>> treeMap) throws Exception {
		String message = "";
		
		if(clazz == null) {
			throw new Exception("获取预期的树形菜单中的菜单项Class出错；"+line);
		}
		
		// 找出最顶层（即：ParentID为-1）的节点，验证其信息是否显示正常
		for(ShowTreeNode treeNode : treeValue) {
			if(treeNode.getParentID().equals("-1")) {
				message += checkTopestTreeItemInfo(treeNode, clazz);
				break;
			} 
		}
		
		if(!StringUtils.isBlank(message)) {
			throw new Exception(message);
		}
		
		// 将Action执行结果产生的树形菜单转换成Map
		Map<ShowTreeNode, List<ShowTreeNode>> resultTreeMap = parseSingleDtoTreeValueToMap(treeValue);
		
		// 是否为空判断
		if(resultTreeMap != null && !resultTreeMap.isEmpty()
				&& treeMap != null && !treeMap.isEmpty()) {
			// 长度比较开始
			int resultSize = resultTreeMap.size();
			int instSize = treeMap.size();
			if(resultSize > instSize) {
				message += "Action执行之后所产生的机构树的长度大于系统（DB或者配置文件）中生成的机构树的长度；"+line; 
			} else if(resultSize < instSize) {
				message += "Action执行之后所产生的机构树的长度小于系统（DB或者配置文件）中生成的机构树的长度；"+line; 
			} else {// 当前两者长度相同，则比较内部的每一个子菜单的数据
				// 比较菜单内的每一个菜单项是否正确
				message += compareSingleDtoTreeMenueItem(resultTreeMap, treeMap);
			}// 长度比较结束
		}// 是否为空判断结束
		
		return message;
	}
	
	/********************单个DTO（与自身有外键关联）树形菜单数据操作end********************/
	
	
	
	
	/********************Action执行结果产生的单个DTO（与自身有外键关联）树形菜单数据转换成Mapstart********************/
	
	/**
	 * <P>将Action执行之后返回的单个DTO<b>（与自身有外键关联）</b>的树形菜单数据转换成Map</P>
	 * <P>此Map中封装了每一个菜单项及其子菜单项的数据</P>
	 * 
	 * @param treeValue Action执行之后产生的树形菜单数据
	 * @return Map<String, List<String>> 封装了每一个菜单项及其子菜单项的数据的Map对象
	 * @throws Exception <P>如果Action执行之后返回的树形菜单结果中不存在parentID为-1的节点，则会抛出“最顶层节点（即：parentID为-1）不存在的异常”</P>
	 */
	private static Map<ShowTreeNode, List<ShowTreeNode>> parseSingleDtoTreeValueToMap(List<ShowTreeNode> treeValue) throws Exception {
		Map<ShowTreeNode, List<ShowTreeNode>> treeManueMap = new HashMap<ShowTreeNode, List<ShowTreeNode>>();
		List<ShowTreeNode> tempList = new ArrayList<ShowTreeNode>();
		ShowTreeNode topNode = null;
		
		// 找出最顶层（即：ParentID为-1）的节点，以及其直接子节点
		for(ShowTreeNode treeNode : treeValue) {
			if(treeNode.getParentID().equals("-1")) {
				topNode = treeNode;
				tempList.add(treeNode);
				break;
			} 
		}
		
		if(topNode == null) {
			throw new Exception("Action执行之后，生成的树形菜单有误，没有最顶层（即：parentID为-1）的菜单项");
		} else {
			if(!tempList.isEmpty()) {
				treeValue.removeAll(tempList);// 删除最顶层（即：parentID为-1）的菜单项
			}
			if(!treeValue.isEmpty()) {
				for(ShowTreeNode treeNode : treeValue) {
					if(treeNode.getParentID().equals(topNode.getTreeNodeID())) {
						tempList.add(treeNode);
						treeManueMap.put(treeNode, new ArrayList<ShowTreeNode>());
					} 
				}
				
				if(!tempList.isEmpty()) {
					treeValue.removeAll(tempList);
				}
				
				if(!treeValue.isEmpty()) {
					setSingleDtoChildTreeNode(treeValue, treeManueMap);
				}
			}
		}
		return treeManueMap;
	}
	
	/**
	 * 
	 * <P>将Action执行结果产生的单个DTO<b>（与自身有外键关联）</b>对应的树形菜单数据全部全换成Map对象</P>
	 * 
	 * @param treeValue Action执行之后产生的树形菜单数据
	 * @param treeManueMap 第一层的菜单项Map，包括了其所有的子菜单项
	 * @author Liutao
	 *
	 */
	private static void setSingleDtoChildTreeNode(List<ShowTreeNode> treeValue, Map<ShowTreeNode, List<ShowTreeNode>> treeManueMap) {
		Set<ShowTreeNode> keySet = treeManueMap.keySet();
		List<ShowTreeNode> tempList = new ArrayList<ShowTreeNode>();
		
		for(ShowTreeNode treeNode : treeValue) {
			for(ShowTreeNode key : keySet) {
				if(treeNode.getParentID().equals(key.getTreeNodeID())) {
					tempList.add(treeNode);
					treeManueMap.get(key).add(treeNode);
					treeManueMap.put(treeNode, new ArrayList<ShowTreeNode>());
				}
			}
		}
		
		if(!tempList.isEmpty()) {
			treeValue.removeAll(tempList);
		}
		
		if(!treeValue.isEmpty()) {
			setSingleDtoChildTreeNode(treeValue, treeManueMap);
		}
	}
	
	/********************Action执行结果产生的单个DTO（与自身有外键关联）树形菜单数据转换成Mapend********************/
	
	
	/********************解析树形菜单中每个菜单项的信息start********************/
	
	/**
	 * <P>检测单个DTO<b>（与自身有外键关联）</b>的菜单项中的最顶级节点是否显示正确显示是否正确</P>
	 * 
	 * @param topNode 当前树结构的最顶级菜单项（即：paranNodeId属性值为-1的菜单节点）菜单项对象
	 * @param clazz 当前操作的DTO对应的Class，clazz为空时说明URL可以为空
	 */
	private static String checkTopestTreeItemInfo(ShowTreeNode topNode, Class<?> clazz) {
		String message = "";
		String URL = topNode.getURL();
		if(clazz != null) {
			if(StringUtils.isBlank(topNode.getExpandImageURL()) || StringUtils.isBlank(topNode.getNormalImageURL())) {
				message += "当前树结构的最顶级菜单项（即：paranNodeId属性值为-1的菜单节点）菜单项的显示图片（折叠或者展开）存在空值；"+line;
			}
			
			if(StringUtils.isBlank(topNode.getShowName())) {
				message += "当前树结构的最顶级菜单项（即：paranNodeId属性值为-1的菜单节点）菜单项的名称不能为空；"+line;
			}
			
			if(StringUtils.isBlank(topNode.getParentID())) {
				message += "当前树结构的最顶级菜单项（即：paranNodeId属性值为-1的菜单节点）菜单项的上级节点ID不能为空；"+line;
			}
			
			if(StringUtils.isBlank(topNode.getTreeNodeID())) {
				message += "当前树结构的最顶级菜单项（即：paranNodeId属性值为-1的菜单节点）菜单项本身的ID不能为空；"+line;
			}
			
			if(StringUtils.isBlank(URL)) {
				message += "当前树结构的最顶级菜单项（即：paranNodeId属性值为-1的菜单节点）的URL属性值不能为空；"+line;
			} else {
				Map<String, String> urlParamMap = ActionTestUtils.parseURLRequestParam(URL);
				if(urlParamMap == null || urlParamMap.size() <= 0) {
					message += "当前树结构的最顶级菜单项（即：paranNodeId属性值为-1的菜单节点）的URL属性值中的查询字符串为空；"+line;
				} else {
					if(clazz.equals(UserInfo.class)) {
						message = checkTreeMenueItemURL(URL, "coresystem.dto.InstInfo", clazz.getSimpleName(), true);
					} else {
						message = checkTreeMenueItemURL(URL, clazz.getName(), clazz.getSimpleName(), true);
					}
				}
			}
		} else {
			if(StringUtils.isNotBlank(URL)) {
				message += "当前树结构的最顶级菜单项（即：paranNodeId属性值为-1的菜单节点）的URL属性值不为空；";
			}
		}
		
		return message;
	}
	
	/**
	 * <P>检测每个菜单项数据是否显示正确</P>
	 * 
	 * @param node Action执行结果产生的每一个菜单项对象
	 * @param type URL中的type参数值
	 * @param windowId URL中的windowId参数值
	 */
	private static String checkTreeMenueItemInfo(ShowTreeNode node, String type, String windowId) {
		String message = "";
		if(StringUtils.isBlank(node.getExpandImageURL()) || StringUtils.isBlank(node.getNormalImageURL())) {
			message += "菜单项的显示图片（折叠或者展开）存在空值；"+line;
		}
		
		if(StringUtils.isBlank(node.getParentID())) {
			message += "菜单项的上级节点ID不能为空"+line;
		}
		
		if(StringUtils.isBlank(node.getTreeNodeID())) {
			message += "菜单项本身的ID不能为空"+line;
		}
		
		if(StringUtils.isBlank(node.getShowName())) {
			message += "菜单项本身的显示名称不能为空"+line;
		}
		
		message += checkTreeMenueItemURL(node.getURL(), type, windowId, false);
		
		return message;
	}
	
	/**
	 * <P>检测每个菜单项的URL参数是否正确</P>
	 * @param URL 最顶级树形菜单项的URL参数值
	 * @param type URL中查询字符串对应的type参数的值
	 * @param windowId URL中查询字符串对应的windowId参数的值
	 * @param inoreIgFlag 是否检测URL中查询字符串中的id属性的值标志
	 */
	private static String checkTreeMenueItemURL(String URL, String type, String windowId, boolean inoreIgFlag) {
		String message = "";
		if(StringUtils.isBlank(URL)) {
			message += "当前传入的URL为空；"+line;
		} else {
			int index = URL.indexOf("?")+1;
			if(index == -1) {// URL中是否存在问号
				if(!URL.endsWith(".action")) {
					message += "当前URL中不存在？时，其值（"+URL+"）未以.action结尾；"+line;
				}
			} else {// 有问号
				String[] URLArr = URL.split("?");
				if(StringUtils.isBlank(URLArr[0]) || StringUtils.isBlank(URLArr[0])) {// ？左右有空值
					message += "URL的值（"+URL+"）有误，其中，真实的请求路径（即：第一个？前的部分）为空，或者？之后未跟任何的查询字符串；"+line;
				} else {// ？左右无空值
					if(!URLArr[0].endsWith(".action")) {// 真实请求路径
						message += "URL（"+URL+"）中，真实的请求路径（即：第一个？前的部分）未以.action结尾；"+line;
					}
					
					URL = URLArr[1];
					String[] strArr = URL.split("&");
					String[] temArr = null;
					
					if(strArr.length != 3) {
						message += "URL（"+URLArr[0]+URL+"）中查询字符串的参数个数有误，未将ID、type、windowId全部写入；"+line;
					}
					
					for(String str : strArr) {
						temArr = str.split("=");
						if(temArr[0].equals("id")) {
							if(!inoreIgFlag && StringUtils.isBlank(temArr[1])) {
								message += "URL（"+temArr[0]+URL+"）中查询字符串中id参数的值不能为空；"+line;
							}
						}
						
						if(temArr[0].equals("type") || temArr[0].equals("windowId")) {
							if(StringUtils.isBlank(temArr[1])) {
								if(temArr[0].equals("type") && StringUtils.isNotBlank(type)) {
									message += "URL（"+temArr[0]+URL+"）中查询字符串中type参数的值与预期值（"+type+"）不一致；"+line;
								}
								
								if(temArr[0].equals("windowId") && StringUtils.isNotBlank(windowId)) {
									message += "URL（"+temArr[0]+URL+"）中查询字符串中windowId参数的值与预期值（"+windowId+"）不一致；"+line;
								}
							} else {
								if(temArr[0].equals("type")) {
									if(StringUtils.isBlank(type) || !temArr[1].equals(type)) {
										message += "URL（"+temArr[0]+URL+"）中查询字符串中type参数的值与预期值（"+type+"）不一致；"+line;
									}
								}
								
								if(temArr[0].equals("windowId")) {
									if(StringUtils.isBlank(windowId) || !temArr[1].equals(windowId)) {
										message += "URL（"+temArr[0]+URL+"）中查询字符串中windowId参数的值与预期值（"+windowId+"）不一致；"+line;
									}
								}
							}
						}
					}
				}
			}
		}
		return message;
	}
	
	/**
	 * 
	 * <P>比较Action执行结果产生的单个DTO<b>（与自身有外键关联）</b>对应的树形菜单是否与同等条件下的预期结果的数据一致</P>
	 * 
	 * @param resultTreeMap Action执行结果产生的单个DTO对应的树形菜单
	 * @param treeMap 同等条件下的预期树形菜单结果
	 * @return String 比较结果
	 * 
	 * @author Liutao
	 *
	 */
	private static String compareSingleDtoTreeMenueItem(Map<ShowTreeNode, List<ShowTreeNode>> resultTreeMap, Map<Object, List<Object>> treeMap) {
		String actualId = "";// 树形菜单项URL属性中的查询字符串中的id属性值
		String expectId = "";
		
		String message = "";
		String temMessage = "";// 存放临时消息的对象
		
		String actualShowName = "";// 菜单项实际的显示名称
		String expectShowName = "";// 菜单项预期的显示名称
		
		String actualChildMenueShowName = "";// 菜单项实际直接子菜单的显示名称
		String expectChildMenueShowName = "";// 菜单项预期直接子菜单的显示名称
		
		Set<Object> expectKeys = treeMap.keySet();
		Set<ShowTreeNode> actualKeys = resultTreeMap.keySet();
		
		Map<String, String> urlParamMap = null;// 菜单项中，URL属性中的查询字符串中的参数信息
		
		List<ShowTreeNode> actualChildNodeList = null;// 实际的子菜单项数组
		List<Object> expectChildNodeList = null;// 预期的子菜单项数组
		
		boolean findNodeFlag = false;// Action执行结果产生的树形菜单的菜单项是否在预期树形菜单中找到与之对应的菜单项标志
		// 循环取出由Action运行之后产生的树形菜单对应的Map的key
		for(ShowTreeNode actualkey : actualKeys) {
			findNodeFlag = false;// 还原是否找到标志
			actualShowName = actualkey.getShowName();
			actualChildNodeList = resultTreeMap.get(actualkey);
			// 检测树形节点本身显示是否有问题（未与预期结果比较）
			temMessage = checkTreeMenueItemInfo(actualkey, clazz.getName(), clazz.getSimpleName());
			if(StringUtils.isNotBlank(temMessage)) {// 有问题
				message += temMessage;
				break;
			} else {// 无问题
				urlParamMap = ActionTestUtils.parseURLRequestParam(actualkey.getURL());
				if(urlParamMap == null || urlParamMap.isEmpty()) {
					message += "获取菜单项"+actualShowName+"中URL属性中的查询字符串的值出现错误；"+line;
					break;
				} else {
					actualId = urlParamMap.get("id");
					if(StringUtils.isBlank(actualId)) {
						message += "菜单项"+actualShowName+"的URL属性中的查询字符串不存在id属性；"+line;
						break;
					} else {
						// 遍历预期的树形菜单数据Map
						for(Object expectKey : expectKeys) {
							if(expectId.equals(actualId)) {
								// 是机构树形菜单
								if(clazz.equals(InstInfo.class)) {
									expectId = ((InstInfo) expectKey).getStrInstCode();
									expectShowName = ((InstInfo) expectKey).getStrInstName();
								}
								
								// 是主题树形菜单
								if(clazz.equals(Suit.class)) {
									expectId = ((Suit) expectKey).getStrSuitCode();
									expectShowName = ((Suit) expectKey).getStrSuitName();
								}
								
								expectChildNodeList = treeMap.get(expectKey);
								
								findNodeFlag = true;// 找到对应的菜单项
								break;
							}
						}// 循环预期的树形菜单数据Map结束
						
						// 判断是否找到对应的菜单项
						if(findNodeFlag) {// 已找到，比较显示名称是否相同
							if(actualShowName.equals(expectShowName)) {// 显示名称相同，判断其直接子菜单项的个数是否相同，以及显示名称是否相同
								if(actualChildNodeList != null && !actualChildNodeList.isEmpty() &&
										expectChildNodeList != null && !expectChildNodeList.isEmpty()) {// 当直接子菜单项都不空时
									if(actualChildNodeList.size() != expectChildNodeList.size()) {
										message += "菜单项"+actualShowName+"显示的直接子菜单项长度与该菜单项对应预期结果长度不符；"+line;
									} else {// 长度相同比较显示名称
										for(ShowTreeNode actualItemNode : actualChildNodeList) {
											findNodeFlag = false;// 还原是否找到标志
											urlParamMap = ActionTestUtils.parseURLRequestParam(actualItemNode.getURL());
											
											actualId = urlParamMap.get("id");
											actualChildMenueShowName = actualItemNode.getShowName();
												
											for(Object expectItemNode : expectChildNodeList) {
												if(expectId.equals(actualId)) {
													if(clazz.equals(InstInfo.class)) {
														expectId = ((InstInfo) expectItemNode).getStrInstCode();
														expectShowName = ((InstInfo) expectItemNode).getStrInstName();
													}
													
													// 是主题树形菜单
													if(clazz.equals(Suit.class)) {
														expectId = ((Suit) expectItemNode).getStrSuitCode();
														expectShowName = ((Suit) expectItemNode).getStrSuitName();
													} 
													break;
												}
											}// 实际子菜单项循环结束
											
											if(findNodeFlag) {// 找到
												if(!expectChildMenueShowName.equals(actualChildMenueShowName)) {
													message += "菜单项"+actualShowName+"的直接子菜单项"+actualChildMenueShowName+
														"与该菜单项对应的预期直接子菜单项中相应的子菜单显示名称不符；"+line;
												}
											} else {
												message += "菜单项"+actualShowName+"的直接子菜单项"+actualChildMenueShowName+"在该菜单项对应的预期直接子菜单项中不存在；"+line;
											}
										}// 实际子菜单项循环结束
									}
								}
							} else {
								message += "菜单项"+actualShowName+"的显示名称与预期名称（"+expectShowName+"）不符；"+line;
							}
						} else {// 未找到
							message += "菜单项"+actualShowName+"所对应的数据在预期结果中不存在；"+line;
						}
						
					}// 查询字符串id属性值判断结束
				}
			}// 树形节点本身有无问题判断结束
		}// 循环取出由Action运行之后产生的树形菜单对应的Map的key结束
		
		return message;
	}
	
	/********************解析树形菜单中每个菜单项的信息end********************/
	
	
	
	
	
	
	
	
	
	
	
}
