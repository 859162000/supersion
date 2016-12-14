package report.service.imps;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.AutoTaskFlow;
import report.dto.Reportmodel_tableSet;
import report.dto.RptInfo;
import report.dto.TableTableSet;
import report.dto.TaskFlow;
import extend.dto.ReportModel_Table;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;

public class  OperateSuitObject{

    public boolean equals(Object o, Suit suit)
	{
		if(o!=null && o instanceof Suit)
		{
			return suit.getStrSuitCode().equals(((Suit)o).getStrSuitCode());
		}
			
		return false;
	}

	/**
	 * 
	 * 获取当前主题下的所有子节点（包括节点本身）
	 * 思路：先遍历源主题数组所有数据，将和当前任务所拥有的主题相同的主题放入一个新的数组当中保存，并将该主题从原有的数组（即：源数组）当中删除
	 * 
	 * @param suits
	 *            :所有的主题数组(即：源数组)
	 * @author liutao
	 */
	public List<Suit> getChildSuits(List<Suit> suits, Suit suit) {
		List<Suit> childSuits=new ArrayList<Suit>();
		if (suits.size() != 0) {
			// 判断任务对应的主题是否在源数组中
			boolean isFind = false;// isFind为true表明已经将任务对应的主题放入了childSuits中
			if (suits.contains(suit) || checkSuitIsSame(suits, suit)) {
				childSuits.add(suit);
				suits.remove(suit);
				isFind = true;
			}
			if (isFind) {
				this.getSubSuitsOfTaskFlowSuit(suits, childSuits);
			}
		}
		return childSuits;
	}

	/**
	 * 找出当前任务对应主题的所有子孙主题
	 * 思路：由于在方法setChildSuits中，已经将当前任务的主题信息放入了childSuits中，因此只需要遍历所有的源主题数组中的信息
	 * ，当源主题数组中主题的上级主题信息在childSuits中存在
	 * 则将该主题放入一个临时数组中，且将的那个钱主题信息放入childSuits中，如果在遍历的过程中
	 * ，源主题数组中的主题信息的伤及主题信息为空时，就将当前的主题信息放入临时数组中，当 本次遍历结束之后将临时数组从源数组中删除，最后递归调用
	 * 
	 * @param suits
	 *            :所有的主题数组(即：源数组)
	 * @param childSuits
	 *            ：存放当前任务对应主题的所有子孙主题的List
	 */
	private void getSubSuitsOfTaskFlowSuit(List<Suit> suits, List<Suit> childSuits) {
		if (suits.size() != 0) {
			// 定义临时数组用于存放要进行删除的主题信息
			List<Suit> tempSuits = new ArrayList<Suit>();
			// 遍历源主题数组
			boolean isHasSubSuits = false;
			for (Suit suit : suits) {
				// 判断每一个源主题的上级主题是否在childSuits中
				if (null != suit.getHigerSuit()) {
					if (childSuits.contains(suit.getHigerSuit()) || checkSuitIsSame(childSuits, suit.getHigerSuit())) {
						childSuits.add(suit);
						tempSuits.add(suit);
						isHasSubSuits = true;
					}
				} else {
					tempSuits.add(suit);
				}
			}
			// 删除不用的主体信息
			if (tempSuits.size() > 0) {
				suits.removeAll(tempSuits);
			}
			// 递归调用
			if (isHasSubSuits) {
				getSubSuitsOfTaskFlowSuit(suits, childSuits);
			}
			if (!isHasSubSuits) {
				suits.clear();
			}
		}
	}
	
	/**
	 * 判断当前报表下的主题信息是否和当前任务对应主题的所有子主题（包括自身主题）的的信息相同
	 * @param childSuits 当前任务对应主题的所有子孙主题信息（包括自身主题信息）
	 * @param rptInfos 报表信息的List数组
	 * @return
	 */
	public LinkedHashMap<String, String> compareSuit(List<Suit> childSuits, List<RptInfo> rptInfos){
		LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
		if(null != childSuits && null != rptInfos
				&& childSuits.size() > 0 && rptInfos.size() > 0){
			for(Suit s : childSuits){
				for(RptInfo r : rptInfos){
					if(null != r.getSuit() && this.equals(s, r.getSuit())){
						m.put(r.getStrRptCode(), r.getDescName());
					}
				}
			}
		}
		return m;
	}
	
	/**
	 * 比较当前任务对应的主题及其子孙主题中是否包含有某一个特定的主题信息
	 * @param childSuits 当前任务对应主题的所有子孙主题信息（包括自身主题信息）
	 * @param suit 主题信息
	 * @return
	 */
	public boolean compareSuit(List<Suit> childSuits, Suit suit) {
		boolean res = false;
		if(null != childSuits && null != suit
				&& childSuits.size() > 0){
			for(Suit s : childSuits){
				if(this.equals(s, suit)){
					res = this.equals(s, suit);
					break;
				}
			}
		}
		return res;
	}
	
	/**
	 * 
	 * 比较当前的主体数组中是否存在特定的主题对象
	 * @param suitList 主题数组
	 * @param suit 需要判断的主体对象
	 * @return 是否包含特定主体对象（包含返回true）
	 */
	private boolean checkSuitIsSame(List<Suit> suitList, Suit suit){
		boolean isSameFlag = false;
		for(Suit s : suitList){
			if(this.equals(s, suit)){
				isSameFlag = true;
				break;
			}
		}
		return isSameFlag;
	}
	
	
}
