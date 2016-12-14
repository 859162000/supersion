package framework.test;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.Dispatcher;
import org.junit.After;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Log4jConfigurer;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;

import framework.actions.imps.BaseSAction;
import framework.helper.ConfigUtils;
import framework.helper.DomXMLOperation;
import framework.helper.ReflectOperation;
import framework.reportCheck.CheckContext;
import framework.security.SecurityContext;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	StrutsTestExecutionListener.class})
@Transactional()
public class ActionTestCase extends StrutsTestCase implements
		ApplicationContextAware,TestResourceManager {
	
	private static Boolean isLogin=false;
	private static Boolean isConfigLoaded=false;
	private static Map<String,Object> session=null;
	protected final Log logger = LogFactory.getLog(getClass());
	protected ApplicationContext applicationContext;
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

	protected void setupBeforeInitDispatcher() throws Exception {
		// init context

		servletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				applicationContext);
	}
	
	
	
	
	
	public void setUp() throws Exception {
		servletContext = new MockServletContext(resourceLoader);
		super.setUp();
		login();
		
	}
	public void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	@Before
	public void initServletMockObjects() {
	      
	        response = new MockHttpServletResponse();
	        request = new MockHttpServletRequest();
	        pageContext = new MockPageContext(servletContext, request, response);
	}
	
	
	
	protected String getLoginUserName()
	{
		return "admin";
	}
	protected String getLoginUserPwd()
	{
		return "123";
	}

	protected String testAction(String uri, String dataFileName, int sheetIndex)
			throws Exception {
		return testAction(new CheckResult() {
			public void check(String resultCode, Object actionInstance) {
				if(actionInstance instanceof BaseSAction)
				{
					
					Object serviceResult=((BaseSAction)actionInstance).getServiceResult();
					if(serviceResult instanceof MessageResult)
					{
						MessageResult mr=(MessageResult)serviceResult;
						
						assertEquals(mr.getMessage(),"success", resultCode);
						return;
					}
				}
				assertEquals("success", resultCode);
			}
		}, uri, dataFileName, sheetIndex);
	}
	protected String testAction(String uri,String dataFileName) throws Exception
	{
		return testAction(uri, dataFileName, 0);
	}
	protected String testAction(CheckResult checkResult,String uri,TestDataProvider dataProvider,Map<String,Object> context) throws Exception
	{
		File uploadFile = null;// 需要上传的文件
		if(dataProvider!=null)
		{
		  Map<String,String> datas=dataProvider.getData(context);
		  if(datas!=null)
		  {
			  
			  for(Entry<String,String> entry: datas.entrySet())
			  {
				  if(entry.getKey().equals("uploadFile")){
					  uploadFile = new File(entry.getValue());
				  }
				  else
				  {
					  request.addParameter(entry.getKey(),entry.getValue());
				  }
			  }
		  }
		}
		
		
		request.setMethod("POST");
		ActionProxy proxy = getActionProxy(uri); 
		if(uploadFile != null){
			Object action = proxy.getAction();
			Method setFileMethod=ReflectOperation.getReflectMethod(action.getClass(), "setUploadFile",File.class);
			if(setFileMethod!=null)
			{
				setFileMethod.invoke(action, uploadFile);
			}
			
		}
		
        createContext();
        request.setPathInfo(uri);
        
        String result = proxy.execute(); 
        if(checkResult!=null)
        {
        	checkResult.check(result, proxy.getAction());
        }
        else
        {
        	assertEquals("success", result);
        }
         
        
        return result;
	}
	protected String testAction(String uri,TestDataProvider dataProvider,Map<String,Object> context) throws Exception
	{
		return testAction(new CheckResult() {
			public void check(String resultCode, Object actionInstance) {
				if(actionInstance instanceof BaseSAction)
				{
					Object serviceResult=((BaseSAction)actionInstance).getServiceResult();
					if(serviceResult instanceof MessageResult)
					{
						MessageResult mr=(MessageResult)serviceResult;
						
						assertEquals(mr.getMessage(),"success", resultCode);
						return;
					}
				}
				assertEquals("success", resultCode);
				
			}
		},uri,dataProvider,context);
	}
	
	
	protected String testAction(CheckResult checkResult,String uri,String dataFileName,int sheetIndex) throws Exception
	{
        Map<String,Object> context=new HashMap<String,Object>();
        if(dataFileName==null)
        {
        	dataFileName="";
        }
        
        context.put("dataFileName", dataFileName);
        context.put("sheetIndex", sheetIndex);
        
        return testAction(checkResult,uri,new TestDataProvider()
        {
        	public Map<String,String> getData(Map<String,Object> context)
        	{
        		String dataFileName=context.get("dataFileName").toString();
        		int sheetIndex=(Integer)context.get("sheetIndex");
        		return ExcelUtils.getSheetData(dataFileName, sheetIndex);
        	}
        },context);
	}
	
	private String testAction(CheckResult checkResult,String uri,Sheet sheet) throws Exception
	{
		 Map<String,Object> context=new HashMap<String,Object>();
	        context.put("sheet", sheet);
	        
	        
	        return testAction(checkResult,uri,new TestDataProvider()
	        {
	        	public Map<String,String> getData(Map<String,Object> context)
	        	{
	        		Sheet sheet=(Sheet)context.get("sheet");
	        		
	        		return ExcelUtils.getSheetData(sheet);
	        	}
	        },context);
	}
	
	protected String testAction(CheckResult checkResult,String uri,String dataFileName) throws Exception
	{
		return testAction(checkResult,uri,dataFileName,0);
	}
	protected String testActions(String uritemplate, String dataFileName) throws Exception{
		InputStream  is=this.getClass().getClassLoader().getResourceAsStream(dataFileName);
		POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wwb = new HSSFWorkbook(fs);
		List<String> dtoList = getTestDto(wwb);
		logger.info("========批量测试============");
		for(String dtoName:dtoList)
		{
			int lastIndex=dtoName.lastIndexOf(".");
			String sheetName=dtoName.substring(lastIndex+1);
			Sheet sheet=wwb.getSheet(sheetName);
			String url=String.format(uritemplate, dtoName);
			logger.info(String.format("========测试%s============",url));
			initServletMockObjects();
			testAction(new CheckResult() {
				public void check(String resultCode, Object actionInstance) {
					if(actionInstance instanceof BaseSAction)
					{
						Object serviceResult=((BaseSAction)actionInstance).getServiceResult();
						if(serviceResult instanceof MessageResult)
						{
							MessageResult mr=(MessageResult)serviceResult;
							
							assertEquals(mr.getMessage(),"success", resultCode);
							return;
						}
					}
					assertEquals("success", resultCode);
					
				}
			},url,sheet);
			
		}
		fs.close();
		return "";
		//return testAction(uri, dataFileName, getSheetIndex(dataFileName, dtoName));
	}
	/**author:liutao
	 * 获取需要测试的DTO列表
	 * @param dataFileName
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	protected List<String> getTestDto(HSSFWorkbook wwb) throws IOException {
		List<String> dtoList = null;
		
		Sheet sheet = wwb.getSheetAt(0);
		if(sheet != null){
			dtoList = new ArrayList<String>();
			
			int rowNum = 0;
			String val = null;
			while(rowNum <= sheet.getLastRowNum()){
				HSSFRow row = (HSSFRow) sheet.getRow(rowNum);
				if(row != null){
					HSSFCell cell = row.getCell(0);
					if(cell != null){
						val = cell.getStringCellValue();
						if(!StringUtils.isBlank(val)){
							dtoList.add(val);
						}
					}
				}
				rowNum++;
			}
		}
		return dtoList;
	}
	
	private  void login() throws Exception{
		if(!isLogin)
			{
				request.addParameter("serviceParam.strUserCode",getLoginUserName());
		        request.addParameter("serviceParam.strPassword", getLoginUserPwd());
		        ActionProxy proxy = getActionProxy("/coresystem/Login-coresystem.dto.UserInfo.action"); 
		        
		        createContext();
		        request.setPathInfo("/coresystem/Login-coresystem.dto.UserInfo.action");
		        
		        String result = proxy.execute(); 
		        assertEquals("success", result); 
		        session = ServletActionContext.getContext().getSession();
		        isLogin=true;
		        logger.debug("====================login================");
			}
		
	}
	private void createContext() {
		ActionContext context = ServletActionContext.getContext();
		Map<String, Object> applicatioMap = new HashMap<String, Object>();
		context.setApplication(applicatioMap);
		if(session==null)
		{
			session = new HashMap<String, Object>();
		}
		
		context.setSession(session);
		context.put("request", new HashMap<String, Object>());
		// ServletActionContext.setContext(context);
	}
	protected Dispatcher initDispatcher(Map<String, String> params) {
    	 params = new HashMap<String, String>();
         params.put("config", "struts-default.xml,struts-plugin.xml,struts.xml");
         return super.initDispatcher(params);
	}

	@BeforeClass
	public static void initConfig() throws Exception {
		if (!isConfigLoaded) {
			Log4jConfigurer.initLogging("classpath:log4j.properties", 6000);
			ShowContext.Init();
			SecurityContext.Init();
			CheckContext.Init();

			ConfigUtils.loadConfig();

			isConfigLoaded = true;

		}

	}
	


	@Override
	public void destroy() throws Exception {
		super.tearDown();
		
	}

	@Override
	public void init() throws Exception  {
		
		servletContext = new MockServletContext(resourceLoader);
		super.setUp();
		login();
		
	}

	public static Map<String, Object> getSession(){
		return session;
	}
}
