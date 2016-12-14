package framework.show;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

@SuppressWarnings("serial")
public class ShowComponentSaveOrUpdate implements Serializable{
	
	private ShowSaveOrUpdate showSaveOrUpdate;
	private List<ShowSaveOrUpdate> showCardList;
	private List<ShowList> showListList;
	private String listHeadJsons;
	public void setShowSaveOrUpdate(ShowSaveOrUpdate showSaveOrUpdate) {
		this.showSaveOrUpdate = showSaveOrUpdate;
	}
	public ShowSaveOrUpdate getShowSaveOrUpdate() {
		return showSaveOrUpdate;
	}
	
	public void setShowCardList(List<ShowSaveOrUpdate> showCardList)
	{
		this.showCardList=showCardList;
	}
	public List<ShowSaveOrUpdate> getShowCardList()
	{
		return this.showCardList;
	}
	
	public void setShowListList(List<ShowList> showListList) {
		this.showListList = showListList;
		List<List<ShowHeaderName>> head=new ArrayList<List<ShowHeaderName>>();
		for(ShowList list:showListList)
		{
			head.add(list.getShowNameList());
		}
		JSONArray jo =JSONArray.fromObject(head);  
		listHeadJsons=jo.toString();
	}
	public List<ShowList> getShowListList() {
		return showListList;
	}
	public String getListHeadJsons()
	{
		return listHeadJsons;
	}
	
	
	
}
