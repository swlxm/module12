package net.automation.mobile.android.wechat;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import net.automation.mobile.android.AndroidTestCase;

public class WechatTestCase extends AndroidTestCase {

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
