package net.automation.selenium.jenkins.util;

/**
 * Defined some costants.
 * @author Samuel
 *
 */
public abstract class Constants {

	public static final boolean DISTRIBUTION;
	public static final int DEFAULT_WAITING_TIME;
	public static final int LONG_WAITING_TIME;
	public static final String FILE_SEPARATOR;
	public static final String PROJECT_ROOT_PATH;
	public static final String TEMP_FOLDER_PATH;
	public static final String USER_PROFILE_PATH;
	
	static {
		PropertyParser parser = new PropertyParser("config.properties");
		DISTRIBUTION = parser.getPropertyAsBoolean("distribution");
		DEFAULT_WAITING_TIME = parser.getPropertyAsInt("defaultWaitingTime");
		LONG_WAITING_TIME = parser.getPropertyAsInt("longWaitingTime");
		
		USER_PROFILE_PATH = System.getenv("USERPROFILE");
		TEMP_FOLDER_PATH = System.getProperty("java.io.tmpdir");
		FILE_SEPARATOR = System.getProperty("file.separator");
		PROJECT_ROOT_PATH = System.getProperty("user.dir");
		
	}
}
