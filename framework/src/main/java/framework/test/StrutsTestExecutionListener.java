package framework.test;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class StrutsTestExecutionListener extends AbstractTestExecutionListener {

	private Object testInstance=null;
	public void beforeTestClass(TestContext testContext) throws Exception {
		
		
	}

	
	public void prepareTestInstance(TestContext testContext) throws Exception {
		/* no-op */
		testInstance=testContext.getTestInstance();
		if(testInstance instanceof TestResourceManager)
		{
			((TestResourceManager)testInstance).init();
		}
	}

	
	public void beforeTestMethod(TestContext testContext) throws Exception {
		/* no-op */
	}

	/**
	 * The default implementation is <em>empty</em>. Can be overridden by
	 * subclasses as necessary.
	 */
	public void afterTestMethod(TestContext testContext) throws Exception {
		/* no-op */
	}

	/**
	 * The default implementation is <em>empty</em>. Can be overridden by
	 * subclasses as necessary.
	 */
	public void afterTestClass(TestContext testContext) throws Exception {
		if(testInstance!=null)
		{
			if(testInstance instanceof TestResourceManager)
			{
				((TestResourceManager)testInstance).destroy();
			}
		}
		
	}

	
}
