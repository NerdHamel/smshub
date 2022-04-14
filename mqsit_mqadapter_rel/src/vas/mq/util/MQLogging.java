package vas.mq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import vas.mq.logging.MQLog4jLogging;
//import vas.mq.logging.MQLog4jLogging_forSimpleProcess;

public class MQLogging {
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"MM-dd-yyyy hh:mm:ss.SSS a");

	public static void log(String _log) {
		MQLog4jLogging.logInfo(sdf.format(new Date()) + " [DEBUG] " + _log);
	}

	public static void logWarn(String _log, Exception exp) {
		MQLog4jLogging.logWarn(sdf.format(new Date()) + " [WARN] " + _log);
	}

	public static void logInfo(String _log) {
		MQLog4jLogging.logInfo(sdf.format(new Date()) + " [INFO] " + _log);
	}

	public static void logError(String _log, Exception exp) {
		MQLog4jLogging.logError(sdf.format(new Date()) + " [ERROR] " + _log
				+ ", Error Message: " + exp.getMessage());
	}
}
//لا اله الا الله 