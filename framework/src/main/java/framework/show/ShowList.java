package framework.show;

import java.util.ArrayList;
import java.util.List;

// 返回给ShowList.jsp页面用于显示的对象
public class ShowList {
	
	private List<ShowHeaderName> showNameList;
	
    private List<List<ShowValue>> valueTable;
    
    private ShowPage showPage;
    
    private List<ShowFieldCondition> showCondition;
    
    private String namespace;
    
    private String selectActionName;
    
    private List<ShowOperation> operationList;
    
    private List<List<ShowOperation>> linkedList;
    
    private int startLine;
    
    private int startColumn;
    
    private String navigationName;
    
    private List<ShowFieldValue> showLevelFieldValueList;
    
	private boolean showView;
	
	private boolean showModal;
	
	private List<ShowHeaderName> showLinkNameList;

	public ShowList(){
		this.setShowLevelFieldValueList(new ArrayList<ShowFieldValue>());
		this.setValueTable(new ArrayList<List<ShowValue>>());
		this.setShowNameList(new ArrayList<ShowHeaderName>());
		this.showPage = new ShowPage();
		this.setShowCondition(new ArrayList<ShowFieldCondition>());
		this.setShowLinkNameList(new ArrayList<ShowHeaderName>());
	}

	public void setShowPage(ShowPage showPage) {
		this.showPage = showPage;
	}

	public ShowPage getShowPage() {
		return showPage;
	}

	public void setSelectActionName(String selectActionName) {
		this.selectActionName = selectActionName;
	}

	public String getSelectActionName() {
		return selectActionName;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setShowCondition(List<ShowFieldCondition> showCondition) {
		this.showCondition = showCondition;
	}

	public List<ShowFieldCondition> getShowCondition() {
		return showCondition;
	}

	public void setOperationList(List<ShowOperation> operationList) {
		this.operationList = operationList;
	}

	public List<ShowOperation> getOperationList() {
		return operationList;
	}



	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	public int getStartLine() {
		return startLine;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}

	public String getNavigationName() {
		return navigationName;
	}

	public void setShowLevelFieldValueList(List<ShowFieldValue> showLevelFieldValueList) {
		this.showLevelFieldValueList = showLevelFieldValueList;
	}

	public List<ShowFieldValue> getShowLevelFieldValueList() {
		return showLevelFieldValueList;
	}

	public void setShowNameList(List<ShowHeaderName> showNameList) {
		this.showNameList = showNameList;
	}

	public List<ShowHeaderName> getShowNameList() {
		return showNameList;
	}

	public void setValueTable(List<List<ShowValue>> valueTable) {
		this.valueTable = valueTable;
	}

	public List<List<ShowValue>> getValueTable() {
		return valueTable;
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

	public void setLinkedList(List<List<ShowOperation>> linkedList) {
		this.linkedList = linkedList;
	}

	public List<List<ShowOperation>> getLinkedList() {
		return linkedList;
	}

	public List<ShowHeaderName> getShowLinkNameList() {
		return showLinkNameList;
	}

	public void setShowLinkNameList(List<ShowHeaderName> showLinkNameList) {
		this.showLinkNameList = showLinkNameList;
	}

}
