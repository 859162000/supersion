package framework.show;

import java.util.ArrayList;
import java.util.List;

public class ShowInstanceValue {
	
	public ShowInstanceValue(ShowInstance showInstance){
		this.showInstance = showInstance;
		showFieldValueList = new ArrayList<ShowFieldValue>();
		for(ShowField showField : this.showInstance.getShowFieldList()){
			ShowFieldValue showFieldValue = new ShowFieldValue();
			showFieldValue.setShowField(showField);
			showFieldValueList.add(showFieldValue);
		}
	}
	
	private ShowInstance showInstance;

	public void setShowInstance(ShowInstance showInstance) {
		this.showInstance = showInstance;
	}

	public ShowInstance getShowInstance() {
		return showInstance;
	}
	
	public void setShowFieldValueList(List<ShowFieldValue> showFieldValueList) {
		this.showFieldValueList = showFieldValueList;
	}

	public List<ShowFieldValue> getShowFieldValueList() {
		return showFieldValueList;
	}

	private List<ShowFieldValue> showFieldValueList;
}
