package ncr.service.imps;

import java.util.Map;
import java.util.Set;

import framework.services.imps.BaseService;
import framework.show.ShowContext;

public class FeedbackReportViewService extends BaseService{

	private String feedbackReportTableEntity;

	@Override
	public void initSuccessResult() throws Exception {
		Map<String, String> feedbackReportTableMap = ShowContext.getInstance().getShowEntityMap().get(feedbackReportTableEntity);
		this.setServiceResult(feedbackReportTableMap);
	}
	
	public String getFeedbackReportTableEntity() {
		return feedbackReportTableEntity;
	}

	public void setFeedbackReportTableEntity(String feedbackReportTableEntity) {
		this.feedbackReportTableEntity = feedbackReportTableEntity;
	}
}
