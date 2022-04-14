package vas.mq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingClass {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss.SSS a");
	
	public static void logInfo(String msg) {
		System.out.println(sdf.format(new Date()) + " [INFO] - [MDM Customer Profile] " + msg);
	}

	public static void logError(String msg) {
		System.out.println(sdf.format(new Date()) + " [ERROR] - [MDM Customer Profile] " + msg);
	}

	public static void logError(String msg, Exception exp) {
		System.out.println(sdf.format(new Date()) + " [ERROR] - [MDM Customer Profile] " + msg
				+ ", Error Message: " + exp.getMessage());
		exp.printStackTrace();
	}
}
