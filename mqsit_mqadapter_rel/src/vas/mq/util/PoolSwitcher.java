package vas.mq.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import vas.mq.connection.MQConnectionManager;

public class PoolSwitcher {
	private static final long PERIOD = 60 * 60 * 1000;

	public PoolSwitcher() {
		Calendar startTime = Calendar.getInstance();
		startTime.add(Calendar.HOUR, 1);
		new Timer().schedule(new TimerTask() {
			public void run() {
				switchPool();
			}
		}, startTime.getTime(), PERIOD);
		MQLogging.log("PoolSwitcher initialized");
	}

	private void switchPool() {
		MQLogging.log("Switching used pools");
		MQConnectionManager.one = !MQConnectionManager.one;
		MQLogging.log("Now main connection manager is using map "
				+ (MQConnectionManager.one ? "one" : "two"));
	}
}
//لا اله الا الله 