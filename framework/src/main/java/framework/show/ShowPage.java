package framework.show;

public class ShowPage {
	
	private boolean showPage;
	private boolean showPrePage;
	private boolean showNextPage;
	private int totalPage;
	private int currentPage;
	private int pageSize;
	private int totalCount;
	
	public boolean isShowPage() {
		return showPage;
	}
	public void setShowPage(boolean showPage) {
		this.showPage = showPage;
	}
	public boolean isShowPrePage() {
		return showPrePage;
	}
	public void setShowPrePage(boolean showPrePage) {
		this.showPrePage = showPrePage;
	}
	public boolean isShowNextPage() {
		return showNextPage;
	}
	public void setShowNextPage(boolean showNextPage) {
		this.showNextPage = showNextPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	
}
