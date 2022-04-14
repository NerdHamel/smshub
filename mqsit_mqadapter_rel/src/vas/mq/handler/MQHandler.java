package vas.mq.handler;

import java.io.IOException;
import java.util.Properties;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

import vas.mq.adapter.MQStringMessage;
import vas.mq.adapter.StringMsgTransceiver;
import vas.mq.connection.MQConnectionManager;
import vas.mq.util.ListedHashMap;
import vas.mq.util.MQLogging;
import vas.mq.util.PoolSwitcher;

public class MQHandler {

	private static MQHandler mqHandler = null;
	protected MQConnectionManager mqConnectionManager;

	private static int poolSize = -1;
	private static String qmName = null;
	private static long timeOut = -1;

	private static String hostname = null;
	private static String channel = null;
	private static int port = -1;

	public static MQHandler instance() {
		if (mqHandler == null) {
			if (poolSize == -1 || qmName == null || timeOut == -1) {
				return null;
			} else {
				initializeInstance(qmName, poolSize, timeOut, hostname,
						channel, port);
			}
		}
		return mqHandler;
	}

	public static void initializeInstance(String qmName, int poolSize,
			long queueTimeOut, String hostname, String channel, int port) {
		mqHandler = new MQHandler();
		MQHandler.qmName = qmName;
		MQHandler.poolSize = poolSize;
		MQHandler.timeOut = queueTimeOut;
		MQHandler.hostname = hostname;
		MQHandler.channel = channel;
		MQHandler.port = port;
		mqHandler.initMQ(qmName, poolSize, queueTimeOut, hostname, channel,
				port);
	}

	public static void initializeInstance(String qmName, int poolSize,
			long queueTimeOut) {
		mqHandler = new MQHandler();
		MQHandler.qmName = qmName;
		MQHandler.poolSize = poolSize;
		MQHandler.timeOut = queueTimeOut;
		mqHandler.initMQ(qmName, poolSize, queueTimeOut);
	}

	private void initMQ(String qmName, int poolSize, long queueTimeOut,
			String hostname, String channel, int port) {
		MQLogging.log("Initializing MQ Connections  ");

		Properties props = new Properties();
		props.setProperty(MQConnectionManager.MQ_MANAGER_NAME, qmName);
		props.setProperty(MQConnectionManager.POOL_SIZE, "" + poolSize);
		props.setProperty(MQConnectionManager.WAIT_TIME_OUT_NAME, ""
				+ queueTimeOut);
		props.setProperty(MQConnectionManager.HOST_NAME, hostname);
		if (channel != null)
			props.setProperty(MQConnectionManager.CHANNEL, channel);
		props.setProperty(MQConnectionManager.PORT, port + "");

		boolean mqSetupSucess = false;
		while (!mqSetupSucess) {
			try {
				mqConnectionManager = new MQConnectionManager(props, qmName);
				new PoolSwitcher();
				// AlarmServiceImpl.clearAlarm(ALARM_MQ_EEROR);
				MQLogging.log("MQ Connection Initialized Successfully ");
				mqSetupSucess = true;
			} catch (Exception e) {
				MQLogging.logError(" Error While initilizing MQ \n", e);
				// AlarmServiceImpl.raiseAlarm(ALARM_MQ_EEROR);
				try {
					Thread.sleep(30000);
				} catch (Exception ex) {
					MQLogging.logError("Exception ", ex);
				}
				mqSetupSucess = false;
			}
			MQLogging
					.log(" Finished Initializing MQ Connections successfully ");
		}
	}

	private void initMQ(String qmName, int poolSize, long queueTimeOut) {
		MQLogging.logInfo("Initializing MQ Connections  ");

		Properties props = new Properties();
		props.setProperty(MQConnectionManager.MQ_MANAGER_NAME, qmName);
		props.setProperty(MQConnectionManager.POOL_SIZE, "" + poolSize);
		props.setProperty(MQConnectionManager.WAIT_TIME_OUT_NAME, ""
				+ queueTimeOut);
		boolean mqSetupSucess = false;
		while (!mqSetupSucess) {
			try {
				mqConnectionManager = new MQConnectionManager(props, qmName,
						true);
				new PoolSwitcher();
				MQLogging.logInfo("MQ Connection Initialized Successfully ");
				mqSetupSucess = true;
			} catch (Exception e) {
				MQLogging.logError(" Error While initilizing MQ \n", e);
				try {
					Thread.sleep(30000);
				} catch (Exception ex) {
					MQLogging.logError("Exception ", ex);
				}
				mqSetupSucess = false;
			}
		}
		MQLogging
				.logInfo(" Finished Initializing MQ Connections successfully ");
	}

	public void closePoolConnections() {
		MQLogging.log("Going to close the MQ pool connections");
		ListedHashMap[] connectionMaps = mqConnectionManager
				.getConnectionMaps();
		MQQueueManager connection;
		ListedHashMap connectionMap;
		for (int i = 0; i < connectionMaps.length; i++) {
			connectionMap = connectionMaps[i];
			while (connectionMap.size() > 0) {
				connection = (MQQueueManager) connectionMap.getKeyAt(0);
				if (!((Boolean) connectionMap.getValueAt(0)).booleanValue())
					try {
						connection.disconnect();
					} catch (Exception e) {
						MQLogging.logError("Unable to close a connection", e);
					}
				else
					MQLogging.log("Caught a connection that's still in use");
				connectionMap.remove(connection);
			}
			MQLogging
					.log("Connections has been removed from the Connection pool "
							+ i);
		}
		mqConnectionManager = null;
	}

	public void commit(MQStringMessage mqMessage) {
		if (mqMessage == null)
			return;
		MQQueueManager conn = mqMessage.getConn();
		try {
			if (conn != null)
				conn.commit();
			mqConnectionManager.returnConnection(conn);
			MQLogging.log("Message committed successfully");
		} catch (MQException e) {
			MQLogging
					.logError(
							"Unable to commit queue operation, going to remove connection ...",
							e);
			mqConnectionManager.removeConnection(conn);
		}
	}

	public void backout(MQStringMessage mqMessage) {
		if (mqMessage == null)
			return;
		MQQueueManager conn = mqMessage.getConn();
		try {
			if (conn != null)
				conn.backout();
			mqConnectionManager.returnConnection(conn);
			MQLogging.log("Message backedout successfully");
		} catch (MQException e) {
			MQLogging
					.logError(
							"Unable to backout queue operation, going to remove connection ...",
							e);
			mqConnectionManager.removeConnection(conn);
		}
	}

	public void sendToMQ(String msg, String qName) throws Exception {
		StringMsgTransceiver sender = new StringMsgTransceiver(qName);
		MQQueueManager conn = null;
		int c = 1;
		do {
			try {
				conn = mqConnectionManager.getConnection();
			} catch (Exception e) {
				try {
					MQLogging.log("Sleeping in loop #" + c
							+ " before trying with the connection again");
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					MQLogging.logError("Sleeping interrupted", ie);
				}
			}
		} while (conn == null && c++ < 6);
		if (conn == null)
			throw new Exception("Can Not Create Connection!");
		try {
			sender.sendMessage(conn, qName, msg);
		} catch (MQException e) {
			mqConnectionManager.removeConnection(conn);
			throw new Exception("Queue Operation Failed!");
		} catch (IOException e) {
			mqConnectionManager.returnConnection(conn);
			throw new Exception("IO Operation Failed!");
		} finally {
			mqConnectionManager.returnConnection(conn);
		}
	}

	public MQStringMessage listenToMQ(String queueName) throws Exception {
		MQLogging.log("Will start listenting to MQ on queue [" + queueName
				+ "]");
		StringMsgTransceiver msgSender = new StringMsgTransceiver(queueName);
		MQQueueManager conn = null;
		MQStringMessage msg = null;
		int c = 1;
		do {
			try {
				conn = mqConnectionManager.getConnection();
			} catch (Exception e) {
				try {
					MQLogging.log("Sleeping in loop #" + c
							+ " before trying with the connection again");
					Thread.sleep(30000);
				} catch (InterruptedException ie) {
					MQLogging.log("Sleeping interrupted");
				}
			}
		} while (conn == null && c++ > 10);
		if (conn == null)
			throw new Exception("Could not create a new connection");
		try {
			msg = msgSender.getMessage(conn, queueName);
			MQLogging.log("New message received\n" + msg.getMesseageContent());
			return msg;
		} catch (MQException mqe) {
			throw new Exception("Queue operation exception " + mqe.getMessage());
		} finally {
			mqConnectionManager.returnConnection(conn);
		}
	}

	public void sendToMQWithCorrel(String msg, byte correlId[], String qName,
			String qMName) throws Exception {
		StringMsgTransceiver sender = new StringMsgTransceiver();
		MQQueueManager conn = null;
		int c = 1;
		do {
			try {
				conn = mqConnectionManager.getConnection();
			} catch (Exception e) {
				try {
					MQLogging.log("Sleeping in loop #" + c
							+ " before trying with the connection again");
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					MQLogging.log("Sleeping interrupted");
				}
			}
		} while (conn == null && c++ > 6);
		if (conn == null)
			throw new Exception("Could not create a new connection");
		try {
			sender.putStringMessageRemote(conn, qName, msg, correlId, qMName);
		} catch (MQException e) {
			mqConnectionManager.removeConnection(conn);
			throw new Exception("Operation Failed! " + e.getMessage());
		} catch (Exception e) {
			mqConnectionManager.returnConnection(conn);
			throw new Exception("Operation Failed! " + e.getMessage());
		}
		mqConnectionManager.returnConnection(conn);
	}

	public MQStringMessage listenToMQWithoutCommit(String queueName)
			throws Exception {
		MQLogging.log("Will start listenting to MQ on queue [" + queueName
				+ "]");
		StringMsgTransceiver msgSender = new StringMsgTransceiver();
		MQQueueManager conn = null;
		MQStringMessage msg = null;
		int c = 1;
		do {
			try {
				conn = mqConnectionManager.getConnection();
			} catch (Exception e) {
				try {
					MQLogging.log("Sleeping in loop #" + c
							+ " before trying with the connection again");
					Thread.sleep(30000);
				} catch (InterruptedException ie) {
					MQLogging.log("Sleeping interrupted");
				}
			}
		} while (conn == null && c++ > 10);
		if (conn == null)
			throw new Exception("Could not create a new connection");
		try {
			msg = msgSender.getMessageWithoutCommit(conn, queueName);
			MQLogging.log("New message received\n" + msg.getMesseageContent());
			return msg;
		} catch (MQException mqe) {
			throw new Exception("Queue operation exception" + mqe.getMessage());
		} catch (Exception te) {
			mqConnectionManager.returnConnection(conn);
			throw te;
		}
	}

	public String sendToMQWithReply(String msg, String qName, String replyQN,
			String replyToQMName) throws Exception {
		StringMsgTransceiver sender = new StringMsgTransceiver();
		MQQueueManager conn = null;
		String responseMsg = null;
		int c = 1;
		do {
			try {
				conn = mqConnectionManager.getConnection();
			} catch (Exception e) {
				try {
					MQLogging.log("Sleeping in loop #" + c
							+ " before trying with the connection again");
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					MQLogging.log("Sleeping interrupted");
				}
			}
		} while (conn == null && c++ > 6);
		if (conn == null)
			throw new Exception("Can Not created MQ Connection!");
		try {
			responseMsg = sender.sendMessage(conn, qName, replyQN,
					replyToQMName, msg);
		} catch (MQException e) {
			mqConnectionManager.removeConnection(conn);
			throw new Exception("Queue operation Failed");
		} catch (IOException e) {
			mqConnectionManager.returnConnection(conn);
			throw new Exception("IO operation failed.");
		}
		mqConnectionManager.returnConnection(conn);
		return responseMsg;
	}

	public MQStringMessage sendToMQWithoutReply(String msg, String qName,
			String replyQN, String replyToQMName) throws Exception {
		StringMsgTransceiver sender = new StringMsgTransceiver();
		MQQueueManager conn = null;
		MQStringMessage responseMsg = null;
		int c = 1;
		do {
			try {
				conn = mqConnectionManager.getConnection();
			} catch (Exception e) {
				try {
					MQLogging.log("Sleeping in loop #" + c
							+ " before trying with the connection again");
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					MQLogging.log("Sleeping interrupted");
				}
			}
		} while (conn == null && c++ > 6);
		if (conn == null)
			throw new Exception("Can Not created MQ Connection!");
		try {
			responseMsg = sender.sendMessageOnly(conn, qName, replyQN,
					replyToQMName, msg);
		} catch (MQException e) {
			mqConnectionManager.removeConnection(conn);
			throw new Exception("Queue operation Failed");
		} catch (IOException e) {
			mqConnectionManager.returnConnection(conn);
			throw new Exception("IO operation failed.");
		}
		mqConnectionManager.returnConnection(conn);
		return responseMsg;
	}

	public String recieveFromMQ(String msg, String qName,
			MQStringMessage mqMessage, String replyQN, String replyToQMName)
			throws Exception {
		StringMsgTransceiver sender = new StringMsgTransceiver();
		MQQueueManager conn = null;
		String responseMsg = null;
		int c = 1;
		do {
			try {
				conn = mqConnectionManager.getConnection();
			} catch (Exception e) {
				try {
					MQLogging.log("Sleeping in loop #" + c
							+ " before trying with the connection again");
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					MQLogging.log("Sleeping interrupted");
				}
			}
		} while (conn == null && c++ > 6);
		if (conn == null)
			throw new Exception("Can Not created MQ Connection!");
		try {
			responseMsg = sender.recieveMessageOnly(conn, mqMessage, qName,
					replyQN, replyToQMName, msg);
		} catch (MQException e) {
			mqConnectionManager.removeConnection(conn);
			throw new Exception("Queue operation Failed");
		} catch (IOException e) {
			mqConnectionManager.returnConnection(conn);
			throw new Exception("IO operation failed.");
		}
		mqConnectionManager.returnConnection(conn);
		return responseMsg;
	}

	public String getQueueDepth(String qName) throws Exception {
		StringMsgTransceiver sender = new StringMsgTransceiver();
		MQQueueManager conn = null;
		String responseMsg = null;
		int c = 1;
		do {
			try {
				conn = mqConnectionManager.getConnection();
			} catch (Exception e) {
				try {
					MQLogging.log("Sleeping in loop #" + c
							+ " before trying with the connection again");
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					MQLogging.log("Sleeping interrupted");
				}
			}
		} while (conn == null && c++ > 6);
		if (conn == null)
			throw new Exception("Can Not created MQ Connection!");
		try {
			responseMsg = sender.getQueueDepth(conn, qName);
		} catch (MQException e) {
			mqConnectionManager.removeConnection(conn);
			throw new Exception("Queue operation Failed");
		} catch (IOException e) {
			mqConnectionManager.returnConnection(conn);
			throw new Exception("IO operation failed.");
		}
		mqConnectionManager.returnConnection(conn);
		return responseMsg;
	}

}
// لا اله الا الله 