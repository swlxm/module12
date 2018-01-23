package net.automation.mobile.testscripts.shadowsocks;

import net.automation.mobile.util.AppiumConstants;

public interface ShadowsocksConstants extends AppiumConstants {

	public static final String APP_PACKAGE = "com.github.shadowsocks";
	public static final String APP_ACTIVITY = ".MainActivity";
	
	public static final String content_desc_add_profile = "Add Profile";
	public static final String content_desc_apply = "Apply";
	public static final String content_desc_remove = "Remove";
	
	public static final String xpath_textview_manual_settings = "//android.widget.TextView[@text='Manual Settings']";
	public static final String xpath_textview_profile_name = "//android.widget.TextView[@text='Profile Name']";
	public static final String xpath_textedit_edit = "//android.widget.EditText[@resource-id='android:id/edit']";
	public static final String xpath_button_ok = "//android.widget.Button[@text='OK']";
	public static final String xpath_textview_server = "//android.widget.TextView[@text='Server']";
	public static final String xpath_textview_remote_port = "//android.widget.TextView[@text='Remote Port']";
	public static final String xpath_textview_password = "//android.widget.TextView[@text='Password']";
	public static final String xpath_textview_encrypt_method = "//android.widget.TextView[@text='Encrypt Method']";
	public static final String xpath_button_yes = "//android.widget.Button[@text='YES']";
}
