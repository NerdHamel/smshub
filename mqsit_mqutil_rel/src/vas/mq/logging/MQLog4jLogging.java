package vas.mq.logging;

import java.text.SimpleDateFormat;
import java.util.Date;


//import org.apache.log4j.extras.DOMConfigurator;
import org.apache.log4j.Logger;

public class MQLog4jLogging {
	// public static Logger log =
	// Logger.getLogger("vas.mq.logging.MQLog4jLogging");
	public static Logger log = Logger.getLogger(MQLog4jLogging.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm:ss:SS a");

	/*
	 * private static boolean isConfigLodded = false;
	 * 
	 * public MQLog4jLogging() {
	 * DOMConfigurator.configure("log4j-babyvas-server.xml"); }
	 * 
	 * public static void loadConfig() {
	 * DOMConfigurator.configure("log4j-babyvas-server.xml"); isConfigLodded =
	 * true; }
	 */

	public static void logInfo(String msg) {
		log.info(msg);
	}

	public static void logError(String msg) {
		log.error(msg);
	}

	public static void logWarn(String msg) {
		log.warn(msg);
	}

	public static void debug(String msg) {
		log.debug(msg);
	}
	public static void printInfo(String msg) {
		System.out.println(sdf.format(new Date()) + " [INFO] " + msg);
	}

	public static void printError(String msg) {
		System.out.println(sdf.format(new Date()) + " [Error] " + msg);
	}

	public static void error(String msg) {
		log.error(msg);
		
	}
}
