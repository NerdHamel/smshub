package vas.mq.adapter;

import java.io.EOFException;
import java.io.IOException;

import vas.mq.connection.MQConnectionManager;
import vas.mq.util.MQLogging;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQManagedObject;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.CMQC;

public class MQAdapter {

	private static final int[] selectors = { CMQC.MQIA_BACKOUT_THRESHOLD,
			CMQC.MQCA_BACKOUT_REQ_Q_NAME };
	private static final byte[] charAttrs = new byte[MQC.MQ_Q_NAME_LENGTH];

	public static MQStringMessage putMQMessageLocal(MQQueueManager connection,
			String qName, String msg, String replyQName, String replyQMgrName)
			throws Exception {
		MQQueue queue = null;
		try {
			int openOptions = MQC.MQOO_OUTPUT;
			MQLogging.log("Trying to access queue " + qName);
			queue = connection
					.accessQueue(qName, openOptions, null, null, null);
			MQLogging.log("Accessed queue " + qName + " successfully");

			MQMessage outMessage = new MQMessage();
			outMessage.characterSet = 1208;
			outMessage.format = MQC.MQFMT_STRING;
			if (replyQName != null)
				outMessage.replyToQueueName = replyQName;
			if (replyQMgrName != null)
				outMessage.replyToQueueManagerName = replyQMgrName;

			outMessage.writeString(msg);

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			pmo.options = MQC.MQPMO_NO_SYNCPOINT | MQC.MQPMO_FAIL_IF_QUIESCING
					| MQC.MQRO_COPY_MSG_ID_TO_CORREL_ID;
			// MQC.

			queue.put(outMessage, pmo);
			MQLogging.log("Message put in queue " + qName + " successfully");

			MQStringMessage mqStrMsg = new MQStringMessage();
			mqStrMsg.setMQMessage(outMessage);
			mqStrMsg.setMesseageContent(msg);
			mqStrMsg.setRequestQueueName(qName);

			MQLogging.log("Message ID: " + mqStrMsg.getMessageID());

			return mqStrMsg;
		} catch (MQException mqe) {
			switch (mqe.reasonCode) {
			case 2162:
				MQLogging
						.logError("Queue manager " + connection.name
								+ " is stopped with reason code "
								+ mqe.reasonCode, mqe);
				break;
			case 2009:
				MQLogging.logError("Connection to queue manager "
						+ connection.name + " is broken with reason code "
						+ mqe.reasonCode, mqe);
				break;
			case 2053:
				MQLogging.logError("Queue is full as reason code "
						+ mqe.reasonCode, mqe);
				break;
			default:
				MQLogging.logError(
						"An MQSeries error occurred on put message with completion code: "
								+ mqe.completionCode + ", and reason code: "
								+ mqe.reasonCode, mqe);
			}
			throw mqe;
		} catch (IOException ioe) {
			MQLogging.logError(
					"An error occurred while writing to the message buffer",
					ioe);
			throw ioe;
		} catch (Exception ioe) {
			MQLogging.logError(
					"An error occurred while writing to the message buffer",
					ioe);
			throw ioe;
		} finally {
			if (queue != null) {
				queue.close();
				MQLogging.log("Closed queue " + qName + " successfully.");
			}
		}
	}

	public static MQStringMessage getMQMessageLocal(MQQueueManager connection,
			String qName) throws Exception {
		MQMessage inMessage = null;
		MQQueue queue = null;
		try {
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF;
			MQLogging.log("Trying to access queue: " + qName);
			queue = connection
					.accessQueue(qName, openOptions, null, null, null);

			MQLogging.log("Accessed queue: " + qName + " successfully.");

			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = MQC.MQGMO_NO_SYNCPOINT | MQC.MQGMO_FAIL_IF_QUIESCING
					| MQC.MQGMO_WAIT;
			gmo.matchOptions = MQC.MQMO_NONE;
			gmo.waitInterval = MQC.MQWI_UNLIMITED;

			inMessage = new MQMessage();
			queue.get(inMessage, gmo);

			MQLogging.log("Got a message from queue: " + qName
					+ " successfully.");

			MQStringMessage mqMsg = new MQStringMessage();
			mqMsg.setMQMessage(inMessage);
			// mqMsg.setConn(connection);

			MQLogging.log("Built an MQ message object with this information:\n"
					+ mqMsg);

			return mqMsg;
		} catch (MQException mqe) {
			if (mqe.reasonCode == 2162) {
				MQLogging.logError("Queue manager " + connection.name
						+ " is stopped: " + mqe.reasonCode, mqe);
			} else if (mqe.reasonCode == 2009) {
				MQLogging.logError("Connection to queue manager "
						+ connection.name + " is broken: " + mqe.reasonCode,
						mqe);
			} else
				MQLogging.logError("MQException with reason code "
						+ mqe.reasonCode + " occurred", mqe);
			throw mqe;
		} finally {
			if (queue != null)
				queue.close();
			MQLogging.log("Closed queue " + qName + " successfully.");
			connection.commit();
		}
	}

	public static String getMQMessageDepth(MQQueueManager connection,
			String qName) throws Exception {
		// MQMessage inMessage = null;
		MQQueue queue = null;
		try {
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT
					| MQC.MQOO_INQUIRE;
			MQLogging.log("Trying to access queue: " + qName);
			queue = connection
					.accessQueue(qName, openOptions, null, null, null);

			return "QUEUEDEPTH:" + queue.getCurrentDepth();
			// MQLogging.log("Accessed queue: " + qName + " successfully.");
			//
			// MQGetMessageOptions gmo = new MQGetMessageOptions();
			// gmo.options = MQC.MQGMO_NO_SYNCPOINT |
			// MQC.MQGMO_FAIL_IF_QUIESCING
			// | MQC.MQGMO_WAIT;
			// gmo.matchOptions = MQC.MQMO_NONE;
			// gmo.waitInterval = MQC.MQWI_UNLIMITED;
			//
			// inMessage = new MQMessage();
			// queue.get(inMessage, gmo);
			//
			// MQLogging.log("Got a message from queue: " + qName
			// + " successfully.");
			//
			// MQStringMessage mqMsg = new MQStringMessage();
			// mqMsg.setMQMessage(inMessage);
			// //mqMsg.setConn(connection);
			//
			// MQLogging.log("Built an MQ message object with this information:\n"
			// + mqMsg);
			//
			// return mqMsg;
		} catch (MQException mqe) {
			if (mqe.reasonCode == 2162) {
				MQLogging.logError("Queue manager " + connection.name
						+ " is stopped: " + mqe.reasonCode, mqe);
			} else if (mqe.reasonCode == 2009) {
				MQLogging.logError("Connection to queue manager "
						+ connection.name + " is broken: " + mqe.reasonCode,
						mqe);
			} else
				MQLogging.logError("MQException with reason code "
						+ mqe.reasonCode + " occurred", mqe);
			throw mqe;
		} finally {
			if (queue != null)
				queue.close();
			MQLogging.log("Closed queue " + qName + " successfully.");
			connection.commit();
		}
	}

	public static void putStringMessageRemote(MQQueueManager connection,
			String qName, String message, byte[] correlationId, String qMName)
			throws MQException, IOException {
		MQQueue queue = null;
		try {
			int openOptions = MQC.MQOO_OUTPUT;
			MQLogging.logInfo("Trying to access queue " + qName
					+ ", on queue manager " + qMName);
			queue = connection.accessQueue(qName, openOptions, qMName, null,
					null);
			MQLogging.logInfo("Accessed queue " + qName + " successfully");

			MQMessage outMessage = new MQMessage();
			outMessage.format = MQC.MQFMT_STRING;

			outMessage.writeString(message);
			outMessage.correlationId = correlationId;

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			pmo.options = MQC.MQPMO_NO_SYNCPOINT | MQC.MQPMO_FAIL_IF_QUIESCING;

			queue.put(outMessage, pmo);

			MQLogging
					.logInfo("Message put in queue " + qName + " successfully");
		} catch (MQException mqe) {
			switch (mqe.reasonCode) {
			case 2162:
				MQLogging
						.logError("Queue manager " + connection.name
								+ " is stopped with reason code "
								+ mqe.reasonCode, mqe);
				break;
			case 2009:
				MQLogging.logError("Connection to queue manager "
						+ connection.name + " is broken with reason code "
						+ mqe.reasonCode, mqe);
				break;
			case 2053:
				MQLogging.logError("Queue is full as reason code "
						+ mqe.reasonCode, mqe);
				break;
			default:
				MQLogging.logError(
						"An MQSeries error occurred on put message with completion code: "
								+ mqe.completionCode + ", and reason code: "
								+ mqe.reasonCode, mqe);
			}
			throw mqe;
		} catch (IOException ioe) {
			MQLogging.logError(
					"An error occurred while writing to the message buffer",
					ioe);
			throw ioe;
		} finally {
			if (queue != null) {
				queue.close();
			}
		}
	}

	public static MQStringMessage getMQMessageLocalWithoutCommit(
			MQQueueManager connection, String qName) throws Exception {
		MQMessage inMessage = null;
		MQQueue queue = null;
		MQManagedObject managedObject = null;
		try {
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_INQUIRE;
			MQLogging.log("Trying to access queue: " + qName);
			queue = connection
					.accessQueue(qName, openOptions, null, null, null);

			MQLogging.log("Accessed queue: " + qName + " successfully.");

			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = MQC.MQGMO_SYNCPOINT | MQC.MQGMO_FAIL_IF_QUIESCING
					| MQC.MQGMO_WAIT;
			gmo.matchOptions = MQC.MQMO_NONE;
			gmo.waitInterval = MQC.MQWI_UNLIMITED;

			int[] intAttrs = new int[1];
			managedObject = (MQManagedObject) queue;
			managedObject.inquire(selectors, intAttrs, charAttrs);
			int boThresh = 0;
			boThresh = intAttrs[0];

			inMessage = new MQMessage();
			queue.get(inMessage, gmo);
			MQLogging.log("Got a message from queue: " + qName
					+ " successfully.");
			if (inMessage.feedback == MQC.MQFB_QUIT) {
				connection.commit();
				throw new Exception();
			}
			MQStringMessage mqMsg = new MQStringMessage();
			String myPutQname = new String(charAttrs);
			mqMsg.setMQMessage(inMessage);
			mqMsg.setBackoutQueue(myPutQname);
			mqMsg.setBackoutThreShold(boThresh);
			mqMsg.setConn(connection);

			MQLogging.log("Built an MQ message object with this information:\n"
					+ mqMsg);

			return mqMsg;
		} catch (MQException mqe) {
			if (mqe.reasonCode == 2162) {
				MQLogging.logError("Queue manager " + connection.name
						+ " is stopped: " + mqe.reasonCode, mqe);
			} else if (mqe.reasonCode == 2009) {
				MQLogging.logError("Connection to queue manager "
						+ connection.name + " is broken: " + mqe.reasonCode,
						mqe);
			} else
				MQLogging.logError("MQException with reason code "
						+ mqe.reasonCode + " occurred", mqe);
			throw mqe;
		} finally {
			if (queue != null)
				queue.close();

			MQLogging.log("Closed queue " + qName + " successfully.");
		}
	}

	public static MQMessage getMQMessage(MQQueueManager connection,
			String qName, byte[] l_szCorrelationID) throws MQException {
		MQMessage inMessage = null;
		MQQueue queue = null;
		try {
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF;
			MQLogging.log("Trying to access queue: " + qName);
			queue = connection
					.accessQueue(qName, openOptions, null, null, null);

			MQLogging.log("Accessed queue: " + qName + " successfully.");

			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = MQC.MQGMO_NO_SYNCPOINT | MQC.MQGMO_FAIL_IF_QUIESCING
					| MQC.MQGMO_WAIT;
			gmo.matchOptions = MQC.MQMO_MATCH_CORREL_ID;
			gmo.waitInterval = MQConnectionManager.QUEUE_WAIT_TIME_OUT;

			inMessage = new MQMessage();
			inMessage.correlationId = l_szCorrelationID;
			queue.get(inMessage, gmo);

			MQLogging.log("Got a message from queue: " + qName
					+ " successfully.");

			return inMessage;
		} catch (MQException mqe) {
			if (mqe.reasonCode == 2162) {
				MQLogging.logError("Queue manager " + connection.name
						+ " is stopped: " + mqe.reasonCode, mqe);
			} else if (mqe.reasonCode == 2009) {
				MQLogging.logError("Connection to queue manager "
						+ connection.name + " is broken: " + mqe.reasonCode,
						mqe);
			} else
				MQLogging.logError("MQException with reason code "
						+ mqe.reasonCode + " occurred", mqe);
			throw mqe;
		} finally {
			if (queue != null)
				queue.close();
			MQLogging.log("Closed queue " + qName + " successfully.");
			MQLogging.log("@@@@@@@@@@@ Commiting...");
			connection.commit();
		}
	}

	public static String getMQMessageString(MQQueueManager connection,
			String qName, byte[] l_szCorrelationID) throws Exception {
		MQMessage inMessage = null;
		MQQueue queue = null;
		try {
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF;
			MQLogging.log("Trying to access queue: " + qName);
			queue = connection
					.accessQueue(qName, openOptions, null, null, null);

			MQLogging.log("Accessed queue: " + qName + " successfully.");

			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = MQC.MQGMO_NO_SYNCPOINT | MQC.MQGMO_FAIL_IF_QUIESCING
					| MQC.MQGMO_WAIT;
			gmo.matchOptions = MQC.MQMO_MATCH_CORREL_ID;
			gmo.waitInterval = MQConnectionManager.QUEUE_WAIT_TIME_OUT;

			inMessage = new MQMessage();
			// inMessage.characterSet = 1208;
			inMessage.correlationId = l_szCorrelationID;
			queue.get(inMessage, gmo);

			MQLogging.log("UPDATED111 - Got a message from queue: " + qName
					+ " successfully.");

			String responseMsg = null;

			if (inMessage != null) {

				byte[] b = new byte[inMessage.getMessageLength()];
				inMessage.readFully(b, 0, inMessage.getMessageLength());
				// message.clearMessage();
				responseMsg = new String(b, "utf-8");
				// responseMsg = msgString;
				// //responseMsg =
				// inMessage.readString(inMessage.getDataLength());
				// responseMsg = inMessage.readStringOfByteLength(inMessage
				// .getMessageLength());

			}

			// MQLogging.log("@@@@@@@@@@@ Message: " + responseMsg);
			// MQLogging.log("@@@@@@@@@@@ Commiting...");
			// connection.commit();
			return responseMsg;
		} catch (MQException mqe) {
			if (mqe.reasonCode == 2162) {
				MQLogging.logError("Queue manager " + connection.name
						+ " is stopped: " + mqe.reasonCode, mqe);
			} else if (mqe.reasonCode == 2009) {
				MQLogging.logError("Connection to queue manager "
						+ connection.name + " is broken: " + mqe.reasonCode,
						mqe);
			} else
				MQLogging.logError("MQException with reason code "
						+ mqe.reasonCode + " occurred", mqe);
			throw mqe;
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		} finally {
			if (queue != null)
				queue.close();
			MQLogging.log("Closed queue " + qName + " successfully.");
			MQLogging.log("@@@@@@@@@@@ Commiting...");
			connection.commit();
		}
	}
}

// لا اله الا الله 