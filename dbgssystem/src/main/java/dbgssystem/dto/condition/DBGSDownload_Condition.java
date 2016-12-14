package dbgssystem.dto.condition;

import java.text.SimpleDateFormat;
import java.util.Date;

import framework.helper.TypeParse;
import framework.interfaces.ICondition;

public class DBGSDownload_Condition implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

		private String strReportType;
		
		@ICondition(order=1,isVisible=false)
		private String strReportCode;
		
		private String strJRJGCode;
		
		private Date SJBGRQ;

		public String getStrReportType() {
			return strReportType;
		}

		public void setStrReportType(String strReportType) {
			this.strReportType = strReportType;
		}

		public String getStrReportCode() {
			return strReportCode;
		}

		public void setStrReportCode(String strReportCode) {
			this.strReportCode = strReportCode;
		}

		public String getStrJRJGCode() {
			return strJRJGCode;
		}

		public void setStrJRJGCode(String strJRJGCode) {
			this.strJRJGCode = strJRJGCode;
		}

		public void setSJBGRQ(String sJBGRQ) {
			SJBGRQ =TypeParse.parseDate(sJBGRQ);
		}

		public String getSJBGRQ() {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			return SJBGRQ==null?null:format.format(SJBGRQ);
		}

}
