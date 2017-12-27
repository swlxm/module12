package net.automation.mobile.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartAppiumServer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String rootPath = System.getProperty("user.dir");
		String file_separator = System.getProperty("file.separator");
		Desktop.getDesktop().open(new File(rootPath + file_separator + "cmd" + file_separator + "terminal1.bat"));
		Desktop.getDesktop().open(new File(rootPath + file_separator + "cmd" + file_separator + "terminal2.bat"));
		Desktop.getDesktop().open(new File(rootPath + file_separator + "cmd" + file_separator + "c1e43588.bat"));
	}

}
