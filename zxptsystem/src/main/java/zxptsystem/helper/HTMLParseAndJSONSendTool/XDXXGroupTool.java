package zxptsystem.helper.HTMLParseAndJSONSendTool;

import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;

public class XDXXGroupTool {
	
	public static void getXDXXGroupDataList(LinkedList<Element> dkList, LinkedList<Element> djkList, 
			LinkedList<Element> zdjkList, LinkedList<Element> dbxxList, Element table) {
		
		if(dkList != null && djkList != null && zdjkList != null && table != null && dbxxList != null &&
				table.html().indexOf("tr") > -1 && table.html().indexOf("td") > -1 &&
				table.children() != null && table.children().size() > 0) {
			
			boolean findFlag = false;// 是否找到信贷交易信息明细的相关数据
			LinkedList<Element> xdxxTrList = new LinkedList<Element>();
			for(Element tr : table.children().get(0).children()) {
				if(tr == null || StringUtils.isBlank(tr.text()) || 
						StringUtils.isBlank(tr.html()) || !tr.tagName().equals("tr") || 
						tr.html().indexOf("nbsp") > -1) {
					continue;
				}
				
				// 如果当前的信息是信贷交易信息明细的信息
				if(!findFlag) {
					if(tr.text().endsWith("信贷交易信息明细")) {
						findFlag = true;
					}// 信贷交易信息明细
				} else {
					if(tr.text().endsWith("公共信息明细") || tr.text().endsWith("声明信息")
							|| tr.text().endsWith("查询记录")) {
						break;
					} else {
						xdxxTrList.add(tr);
					}
				}
			}// table的所有TR循环
			
			if(!xdxxTrList.isEmpty()) {
				int dkIndex = -1;
				int djkIndex = -1;
				int zdjkIndex = -1;
				int dbxxIndex = -1;
				boolean dbxxFindFlag = false;
				for(Element tr : xdxxTrList) {
					if(tr.text().endsWith("贷款")) {
						dkIndex = xdxxTrList.indexOf(tr);
					}
					
					if(tr.text().endsWith("贷记卡") && !tr.text().endsWith("准贷记卡")) {
						djkIndex = xdxxTrList.indexOf(tr);
					}
					
					if(tr.text().endsWith("准贷记卡")) {
						zdjkIndex = xdxxTrList.indexOf(tr);
					}
					
					if(!dbxxFindFlag && tr.text().endsWith("担保信息")) {
						dbxxFindFlag = true;
						dbxxIndex = xdxxTrList.indexOf(tr);
					}
				}
				
				if(!(dkIndex == -1 && djkIndex == -1 && zdjkIndex == -1) && xdxxTrList.size() >= 1) {
					if(dkIndex > -1 && djkIndex == -1 && zdjkIndex == -1) {// 只有贷款数据
						if(dbxxIndex > -1) {//有担保信息
							dkList.addAll(xdxxTrList.subList(dkIndex+1, dbxxIndex));
							dbxxList.addAll(xdxxTrList.subList(dbxxIndex+1, xdxxTrList.size()));
						} else {
							dkList.addAll(xdxxTrList.subList(dkIndex+1, xdxxTrList.size()));
						}
					}
					
					if(dkIndex == -1 && djkIndex > -1 && zdjkIndex == -1) {// 只有贷记卡数据
						if(dbxxIndex > -1) {//有担保信息
							djkList.addAll(xdxxTrList.subList(djkIndex+1, dbxxIndex));
							dbxxList.addAll(xdxxTrList.subList(dbxxIndex+1, xdxxTrList.size()));
						} else {
							djkList.addAll(xdxxTrList.subList(djkIndex+1, xdxxTrList.size()));
						}
					}
					
					if(dkIndex == -1 && djkIndex == -1 && zdjkIndex > -1) {// 只有准贷记卡数据
						if(dbxxIndex > -1) {//有担保信息
							zdjkList.addAll(xdxxTrList.subList(zdjkIndex+1, dbxxIndex));
							dbxxList.addAll(xdxxTrList.subList(dbxxIndex+1, xdxxTrList.size()));
						} else {
							zdjkList.addAll(xdxxTrList.subList(zdjkIndex+1, xdxxTrList.size()));
						}
					}
					
					if(dkIndex > -1 && djkIndex > -1 && zdjkIndex == -1) {// 无准贷记卡数据
						if(dbxxIndex > -1) {//有担保信息
							dkList.addAll(xdxxTrList.subList(dkIndex+1, djkIndex));
							djkList.addAll(xdxxTrList.subList(djkIndex+1, dbxxIndex));
							dbxxList.addAll(xdxxTrList.subList(dbxxIndex+1, xdxxTrList.size()));
						} else {
							dkList.addAll(xdxxTrList.subList(dkIndex+1, djkIndex));
							djkList.addAll(xdxxTrList.subList(djkIndex+1, xdxxTrList.size()));
						}
					}
					
					if(dkIndex > -1 && djkIndex == -1 && zdjkIndex > -1) {// 无贷记卡数据
						if(dbxxIndex > -1) {//有担保信息
							dkList.addAll(xdxxTrList.subList(dkIndex+1, zdjkIndex));
							zdjkList.addAll(xdxxTrList.subList(zdjkIndex+1, dbxxIndex));
							dbxxList.addAll(xdxxTrList.subList(dbxxIndex+1, xdxxTrList.size()));
						} else {
							dkList.addAll(xdxxTrList.subList(dkIndex+1, zdjkIndex));
							zdjkList.addAll(xdxxTrList.subList(zdjkIndex+1, xdxxTrList.size()));
						}
					}
					
					if(dkIndex == -1 && djkIndex > -1 && zdjkIndex > -1) {// 无贷款数据
						if(dbxxIndex > -1) {//有担保信息
							djkList.addAll(xdxxTrList.subList(djkIndex+1, zdjkIndex));
							zdjkList.addAll(xdxxTrList.subList(zdjkIndex+1, dbxxIndex));
							dbxxList.addAll(xdxxTrList.subList(dbxxIndex+1, xdxxTrList.size()));
						} else {
							djkList.addAll(xdxxTrList.subList(djkIndex+1, zdjkIndex));
							zdjkList.addAll(xdxxTrList.subList(zdjkIndex+1, xdxxTrList.size()));
						}
					}
					
					if(dkIndex > -1 && djkIndex > -1 && zdjkIndex > -1) {// 全部都有
						if(dbxxIndex > -1) {//有担保信息
							dkList.addAll(xdxxTrList.subList(dkIndex+1, djkIndex));
							djkList.addAll(xdxxTrList.subList(djkIndex+1, zdjkIndex));
							zdjkList.addAll(xdxxTrList.subList(zdjkIndex+1, dbxxIndex));
							dbxxList.addAll(xdxxTrList.subList(dbxxIndex+1, xdxxTrList.size()));
						} else {
							dkList.addAll(xdxxTrList.subList(dkIndex+1, djkIndex));
							djkList.addAll(xdxxTrList.subList(djkIndex+1, zdjkIndex));
							zdjkList.addAll(xdxxTrList.subList(zdjkIndex+1, xdxxTrList.size()));
						}
					}
				}
			}
		}
	}
	
	
	
	
	
}
