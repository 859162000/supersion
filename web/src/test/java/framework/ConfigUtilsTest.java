package framework;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import framework.helper.ConfigUtils;

public class ConfigUtilsTest {

	@Test
	public void testLoadConfig() throws Exception {
		ConfigUtils.loadConfig();
	}

}
