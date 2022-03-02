package vas.mq.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.extras.DOMConfigurator;
import org.apache.log4j.Logger;

public class MQLog4jLogging_forSimpleProcess {
	public static Logger log = Logger.getLogger("vas.mq.logging.MQLog4jLogging");
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm:ss:SS a");

	private static boolean isConfigLodded = false;

	public MQLog4jLogging_forSimpleProcess() {
		DOMConfigurator.configure("log4j-babyvas-server.xml");
	}

	public static void loadConfig() {
		DOMConfigurator.configure("log4j-babyvas-server.xml");
		isConfigLodded = true;
	}

	public static void logInfo(String msg) {
		if (!isConfigLodded)
			loadConfig();
		log.info(msg);
	}

	public static void logError(String msg) {
		if (!isConfigLodded)
			loadConfig();
		log.error(msg);
	}

	public static void logWarn(String msg) {
		if (!isConfigLodded)
			loadConfig();
		log.warn(msg);
	}

	public static void printInfo(String msg) {
		System.out.println(sdf.format(new Date()) + " [INFO] " + msg);
	}

	public static void printError(String msg) {
		System.out.println(sdf.format(new Date()) + " [Error] " + msg);
	}
}
