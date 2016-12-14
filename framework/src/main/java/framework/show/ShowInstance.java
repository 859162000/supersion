package framework.show;

import java.util.ArrayList;
import java.util.List;

import framework.interfaces.ApplicationManager;


public class ShowInstance {
	
    private String showInstanceName;
    private String listConditionShow;
    
    public void setListConditionShow(String listCondShow)
    {
    	this.listConditionShow=listCondShow;
    }
    public String getListConditionShow()
    {
    	return this.listConditionShow;
    }
    
    
	public void setShowInstanceName(String showInstanceName) {
		this.showInstanceName = showInstanceName;
	}

	public String getShowInstanceName() {
		return showInstanceName;
	}
	
    private String showEntityName;
	
    public void setShowEntityName(String showEntityName) {
		this.showEntityName = showEntityName;
	}

	public String getShowEntityName() {
		return showEntityName;
	}
	
	private String navigationName;
	
	public void setShowFieldList(List<ShowField> showFieldList) {
		this.showFieldList = showFieldList;
	}
	
	private int windowHeight;
	
	private int windowWidth;
	
	private boolean showView;
	
	private boolean showModal;

	public List<ShowField> getShowFieldList() {
		return showFieldList;
	}

	private List<ShowField> showFieldList;
	
	public List<ShowField> CopyShowFieldList(){
		List<ShowField> showFieldList = new ArrayList<ShowField>();
		for(ShowField showField : this.showFieldList){
			ShowField tempShowField = showField.Copy();
			showFieldList.add(tempShowField);
		}
		return showFieldList;
	}
	
	public ShowInstance(){
		this.showFieldList = new ArrayList<ShowField>();
	}

	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}

	public String getNavigationName() {
		return navigationName;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setShowView(boolean showView) {
		this.showView = showView;
	}

	public boolean isShowView() {
		return showView;
	}

	public void setShowModal(boolean showModal) {
		this.showModal = showModal;
	}

	public boolean isShowModal() {
		return showModal;
	}
	public List<ShowField> getFieldListWithShowCard()
	{
		return getFieldListWithSingleTag(ApplicationManager.getCardSingleTag());
	}
	public List<ShowField> getFieldListWithShowList()
	{

		return getFieldListWithSingleTag(ApplicationManager.getListSingleTag());
		
	}
	
	private List<ShowField> getFieldListWithSingleTag(String singleTag)
	{
		List<ShowField> singleTagFieldList=new ArrayList<ShowField>();
		
			
		for(ShowField showField:showFieldList)
		{
			if(singleTag.equals(showField.getSingleTag()))
			{
				singleTagFieldList.add(showField.Copy());
			}
					
		}
		return singleTagFieldList;
	}
}
