package vas.mq.connection;

import java.util.Hashtable;
import java.util.Properties;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

import vas.mq.util.ListedHashMap;
import vas.mq.util.MQLogging;

public class MQConnectionManager {

	public static final String HOST_NAME = "HOST_NAME";
	public static final String CHANNEL = "CHANNEL";
	public static final String PORT = "PORT";

	public static final String POOL_SIZE = "POOL_SIZE";
	public static final String MQ_MANAGER_NAME = "MQ_MANAGER_NAME";
	public static final String WAIT_TIME_OUT_NAME = "WAIT_TIME_OUT_NAME";
	public static final String MQ_TRACE = "MQ_TRACE";
	public static final String MQ_TRACE_FILE = "MQ_TRACE_FILE";
	public static final String MQ_TRACE_LEVEL = "MQ_TRACE_LEVEL";

	public static int QUEUE_WAIT_TIME_OUT = 30000;

	private int _initialPoolSize = 2;
	private String _hostName = null;
	private String _channel;
	private String _mqmName;
	private int _port = -1;
	private ListedHashMap connectionsMap1 = new ListedHashMap();
	private ListedHashMap connectionsMap2 = new ListedHashMap();

	public static boolean one = true;
	private String _appName = "";

	public MQConnectionManager(Properties props) throws Exception {
		this(props, "");
	}

	public MQConnectionManager(Properties props, String appName)
			throws Exception {
		_appName = appName;
		if (props != null) {
			_hostName = (String) props.get(HOST_NAME);
			_channel = (String) props.get(CHANNEL);
			String portStr = props.getProperty(PORT);
			if (portStr != null)
				_port = Integer.parseInt(portStr);

			_mqmName = (String) props.get(MQ_MANAGER_NAME);
			if (_mqmName == null)
				throw new Exception("MQManager Name is not defined");

			if (props.getProperty(POOL_SIZE) != null)
				_initialPoolSize = Integer.parseInt(props
						.getProperty(POOL_SIZE));

			String timeOutStr = props.getProperty(WAIT_TIME_OUT_NAME);
			if (timeOutStr != null)
				QUEUE_WAIT_TIME_OUT = Integer.parseInt(timeOutStr);

			// initialize the MQ connections
			initConnections();

		} else {
			throw new Exception(
					"No properties has been supplied to connect to MQ");
		}
	}

	public MQConnectionManager(Properties props, String appName, boolean local)
			throws Exception {
		_appName = appName;
		if (props != null) {
			// _hostName = (String) props.get(HOST_NAME);
			// _channel = (String) props.get(CHANNEL);
//			String portStr = props.getProperty(PORT);
			// if (portStr != null)
			// _port = Integer.parseInt(portStr);

			_mqmName = (String) props.get(MQ_MANAGER_NAME);
			if (_mqmName == null)
				throw new Exception("MQManager Name is not defined");

			if (props.getProperty(POOL_SIZE) != null)
				_initialPoolSize = Integer.parseInt(props
						.getProperty(POOL_SIZE));

			String timeOutStr = props.getProperty(WAIT_TIME_OUT_NAME);
			if (timeOutStr != null)
				QUEUE_WAIT_TIME_OUT = Integer.parseInt(timeOutStr);

			// initialize the MQ connections
			initConnectionsLocal();

		} else {
			throw new Exception(
					"No properties has been supplied to connect to MQ");
		}
	}

	private void initConnections() throws Exception {
		MQLogging.log("[" + _appName + "] Going to connect to MQM: " + _mqmName
				+ " Host : [" + _hostName + "] , channel : [" + _channel
				+ "] , port [" + _port + "]");
		if (_hostName != null)
			MQEnvironment.hostname = _hostName;
		if (_channel != null)
			MQEnvironment.channel = _channel;
		if (_port != -1)
			MQEnvironment.port = _port;
		for (int i = 0; i < _initialPoolSize; i++) {
			try {
				createNewConnection(connectionsMap1, false);
			} catch (Exception e) {
				e.printStackTrace();
				MQLogging.logError("[" + _appName
						+ "] Unable to create connection to MQM: " + _mqmName
						+ " Host : [" + _hostName + "] , channel : ["
						+ _channel + "] , port [" + _port + "]", e);
				throw new Exception("Unable to create connection to MQM: "
						+ _mqmName + " Host : [" + _hostName
						+ "] , channel : [" + _channel + "] , port [" + _port
						+ "]", e);
			}
		}
		MQLogging.log("[" + _appName
				+ "] MQ has been initialized with connection Pool size ["
				+ _initialPoolSize + "]");
	}

	private void initConnectionsLocal() throws Exception {
		MQLogging.log("[" + _appName + "] Going to connect to MQM: " + _mqmName
				+ " Host : [" + _hostName + "] , channel : [" + _channel
				+ "] , port [" + _port + "]");
		// if (_hostName != null)
		// MQEnvironment.hostname = _hostName;
		// if (_channel != null)
		// MQEnvironment.channel = _channel;
		// if (_port != -1)
		// MQEnvironment.port = _port;
		for (int i = 0; i < _initialPoolSize; i++) {
			try {
				createNewConnectionLocal(connectionsMap1, false);
			} catch (Exception e) {
				e.printStackTrace();
				MQLogging.logError("[" + _appName
						+ "] Unable to create connection to MQM: " + _mqmName
						+ " Host : [" + _hostName + "] , channel : ["
						+ _channel + "] , port [" + _port + "]", e);
				throw new Exception("Unable to create connection to MQM: "
						+ _mqmName + " Host : [" + _hostName
						+ "] , channel : [" + _channel + "] , port [" + _port
						+ "]", e);
			}
		}
		MQLogging.log("[" + _appName
				+ "] MQ has been initialized with connection Pool size ["
				+ _initialPoolSize + "]");
	}

	private MQQueueManager createNewConnection(ListedHashMap connections,
			boolean use) throws Exception {
		MQQueueManager mqM = null;
		try {
			mqM = new MQQueueManager(_mqmName);
			connections.put(mqM, new Boolean(use));

			MQLogging
					.log("["
							+ _appName
							+ "] New Connection has been added to the Connection pool, new pool size ["
							+ connections.size()
							+ "], connectionsMap1 size is ["
							+ connectionsMap1.size()
							+ "] and connectionsMap2 size is ["
							+ connectionsMap2.size() + "]");
		} catch (MQException mqe) {
			mqe.printStackTrace();
			MQLogging.logError(
					"[" + _appName + "] unable to create connection", mqe);
			throw new Exception("[" + _appName
					+ "] unable to create connection");
		}
		return mqM;
	}

	private MQQueueManager createNewConnectionLocal(ListedHashMap connections,
			boolean use) throws Exception {
		MQQueueManager mqM = null;
		try {
			Hashtable properties = new Hashtable();
			
			//properties.put("transport", "MQSeries Bindings"); // MQSeries Client
			properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_BINDINGS);
			mqM = new MQQueueManager(_mqmName, properties);
			connections.put(mqM, new Boolean(use));

			MQLogging
					.log("["
							+ _appName
							+ "] New Connection has been added to the Connection pool, new pool size ["
							+ connections.size()
							+ "], connectionsMap1 size is ["
							+ connectionsMap1.size()
							+ "] and connectionsMap2 size is ["
							+ connectionsMap2.size() + "]");
		} catch (MQException mqe) {
			mqe.printStackTrace();
			MQLogging.logError(
					"[" + _appName + "] unable to create connection", mqe);
			throw new Exception("[" + _appName
					+ "] unable to create connection");
		}
		return mqM;
	}

	public synchronized MQQueueManager getConnection() throws Exception {
		if (one) {
			if (!connectionsMap2.isEmpty()) {
				for (int i = 0; i < connectionsMap2.size(); i++) {
					if (i == 0 || i == (connectionsMap2.size() - 1))
						MQLogging
								.log("["
										+ _appName
										+ "] within connections 2, connections size is:"
										+ connectionsMap2.size() + " and i is "
										+ i);
					boolean inUse = ((Boolean) connectionsMap2.getValueAt(i))
							.booleanValue();
					if (!inUse) {

						MQLogging.log("[" + _appName
								+ "] removing one idle connection from 2");
						MQQueueManager connection = (MQQueueManager) connectionsMap2
								.getKeyAt(i);
						try {
							connection.disconnect();
						} catch (MQException mqe) {
							MQLogging.logWarn("[" + _appName
									+ "] unable to close connection", mqe);
						}
						connectionsMap2.remove(connection);
						i--;
					}
				}
			}
			for (int i = 0; i < connectionsMap1.size(); i++) {
				boolean inUse = ((Boolean) connectionsMap1.getValueAt(i))
						.booleanValue();
				if (!inUse) {

					MQLogging
							.log("["
									+ _appName
									+ "] taking connection from connections 1 at location "
									+ i);
					connectionsMap1.setValueAt(i, new Boolean(true));
					return (MQQueueManager) connectionsMap1.getKeyAt(i);
				}
			}

			MQLogging
					.log("["
							+ _appName
							+ "] "
							+ "no connections are available with in connections 1 so going to create new one");
			return createNewConnection(connectionsMap1, true);
		} else {
			if (!connectionsMap1.isEmpty()) {
				for (int i = 0; i < connectionsMap1.size(); i++) {
					if (i == 0 || i == (connectionsMap1.size() - 1))
						MQLogging
								.log("["
										+ _appName
										+ "] within connections 1, connections size is: "
										+ connectionsMap1.size() + " and i is "
										+ i);
					boolean inUse = ((Boolean) connectionsMap1.getValueAt(i))
							.booleanValue();
					if (!inUse) {

						MQLogging.log("[" + _appName
								+ "] removing one idle connection from 1");
						MQQueueManager connection = (MQQueueManager) connectionsMap1
								.getKeyAt(i);
						try {
							connection.disconnect();
						} catch (MQException mqe) {
							MQLogging.logWarn("[" + _appName
									+ "] unable to close connection", mqe);
						}
						connectionsMap1.remove(connection);
						i--;
					}
				}
			}
			for (int i = 0; i < connectionsMap2.size(); i++) {
				boolean inUse = ((Boolean) connectionsMap2.getValueAt(i))
						.booleanValue();
				if (!inUse) {

					MQLogging
							.log("["
									+ _appName
									+ "] taking connection from connections 2 at location "
									+ i);
					connectionsMap2.setValueAt(i, new Boolean(true));
					return (MQQueueManager) connectionsMap2.getKeyAt(i);
				}
			}
			MQLogging
					.log("["
							+ _appName
							+ "] "
							+ "no connections are available with in connections 2 so going to create new one");
			return createNewConnection(connectionsMap2, true);
		}
	}

	public synchronized void returnConnection(MQQueueManager conn) {
		if (conn == null) {

			MQLogging.log("[" + _appName
					+ "] connection to be returned is null");
			return;
		}
		if (one) {
			if (!connectionsMap2.isEmpty() && connectionsMap2.containsKey(conn)) {

				MQLogging.log("[" + _appName
						+ "] removing one used connection from connections 2");
				try {
					conn.disconnect();
				} catch (MQException mqe) {
					MQLogging.logWarn("[" + _appName
							+ "] unable to close connection", mqe);
				}
				connectionsMap2.remove(conn);
			} else if (conn.isConnected()) {
				MQLogging.log("[" + _appName
						+ "] returning connections to connections 1");
				connectionsMap1.put(conn, new Boolean(false));
			} else
				removeConnection(conn);
		} else {
			if (!connectionsMap1.isEmpty() && connectionsMap1.containsKey(conn)) {

				MQLogging
						.log("["
								+ _appName
								+ "] "
								+ "removing one used connection from connections 1 which has size: "
								+ connectionsMap1.size());
				try {
					conn.disconnect();
				} catch (MQException mqe) {
					MQLogging.logWarn("[" + _appName
							+ "] unable to close connection", mqe);
				}
				connectionsMap1.remove(conn);
			} else if (conn.isConnected()) {
				MQLogging.log("[" + _appName
						+ "] returning connections to connections 2");
				connectionsMap2.put(conn, new Boolean(false));
			} else
				removeConnection(conn);
		}
	}

	public synchronized void removeConnection(MQQueueManager conn) {
		if (conn == null) {

			MQLogging.log("[" + _appName
					+ "] connection signaled for remove is null");
			return;
		}
		try {
			conn.disconnect();
		} catch (MQException mqe) {
			MQLogging.logWarn("[" + _appName + "] unable to close connection",
					mqe);
		}
		if (one) {
			connectionsMap1.remove(conn);
		} else {
			connectionsMap2.remove(conn);
		}
		MQLogging
				.log("["
						+ _appName
						+ "] connection has been removed from the connection pool, connectionsMap1 size is ["
						+ connectionsMap1.size()
						+ "] and connectionsMap2 size is ["
						+ connectionsMap2.size() + "]");
	}

	public ListedHashMap[] getConnectionMaps() {
		return new ListedHashMap[] { connectionsMap1, connectionsMap2 };
	}
}
//لا اله الا الله 