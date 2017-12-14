package net.automation.android.scripts.wechat;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import net.automation.android.scripts.AppiumTestCase;

public class WechatTestCase extends AppiumTestCase {

	@BeforeTest
	@Parameters({"port"})
	public void setUp(String port) throws Exception {
		super.setUp(port, "com.tencent.mm", ".ui.LauncherUI");
	}
	
	@AfterTest
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
