package itemreport;


import org.junit.Test;


import framework.test.ActionTestCase;




public class ItemReportActionTest extends ActionTestCase {
	
	
	
	@Test
	public void roleSaveTest() throws Exception
	{
		testAction("/framework/Save-report.dto.RptInfo","rolesave.xls");
	}
	
}
