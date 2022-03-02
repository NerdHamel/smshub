package vas.mq.util;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;




public class MQConfigurations {

	private static Properties properties = null;
	private static boolean isPropertiesLoaded = false;
	private static String fileName = "mqConfiguration.properties";

	private static void loadProperties() throws Exception {
		properties = new Properties();
		

		
		try {
			LoggingClass
					.logInfo("**********Loading mqConfiguration.properties*********");
			properties.load(new FileInputStream("mqConfiguration.properties"));
			isPropertiesLoaded = true;
		} catch (Exception exp) {
			LoggingClass.logError("During Loading mqConfiguration.properties "
					+ exp.getMessage(), exp);
		}
		if (!isPropertiesLoaded)
			loadConfigValues();
	}

	public static void loadConfigValues() throws Exception {
		InputStream iostream = null;
		try {
			LoggingClass.logInfo("[Loading Configuration] " + fileName
					+ "... from conf folder!");
			MQConfigurations obj = new MQConfigurations();
			properties = new Properties();
			iostream = obj.createInputStream(fileName);
			properties.load(iostream);
			LoggingClass.logInfo("[Loading Configuration] " + fileName
					+ "... Done Successfully!");
			isPropertiesLoaded = true;
		} catch (Exception exp) {
			LoggingClass
					.logInfo("[Loading Configuration] There is error during MQConfigurations: "
							+ exp.getMessage());
			exp.printStackTrace();
		} finally {
			try {
				if (iostream != null)
					iostream.close();
			} catch (Exception exp) {
			}
		}
	}

	private InputStream createInputStream(String fileNme) {
		InputStream iostream = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(fileNme);
		if (iostream == null) {
			iostream = this.getClass().getResourceAsStream(fileNme);
		}
		if (iostream == null) {
			iostream = this.getClass().getClassLoader()
					.getResourceAsStream(fileNme);
		}
		return iostream;
	}

	public static String getConfiguValue(String key) {
		try {
			if (!isPropertiesLoaded)
				loadProperties();

			LoggingClass.logInfo("Reading From properties: key: " + key
					+ ", Value: " + properties.getProperty(key));

			return properties.getProperty(key);
		} catch (Exception exp) {
			LoggingClass.logError(exp.getMessage(), exp);
		}
		return null;
	}

}
