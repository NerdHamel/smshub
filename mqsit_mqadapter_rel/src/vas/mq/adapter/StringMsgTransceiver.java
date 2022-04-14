package vas.mq.adapter;

import vas.mq.util.MQLogging;

import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueueManager;

public class StringMsgTransceiver {
	private String _appName = "";

	public StringMsgTransceiver() {
	}

	public StringMsgTransceiver(String appName) {
		this._appName = appName;
	}

	public void sendMessage(MQQueueManager mqMgr, String requestQname,
			String msg) throws Exception {
		MQLogging.log("[" + _appName + "] sending MQ Message : [" + msg
				+ "] to queue [" + requestQname + "]");
		MQAdapter.putMQMessageLocal(mqMgr, requestQname, msg, null, null);
	}

	public String sendMessage(MQQueueManager mqMgr, String requestQname,
			String replyQName, String replyQMgrName, String msg)
			throws Exception {
		MQLogging.log("[" + _appName + "] MQ Request : [" + msg
				+ "] on queue [" + requestQname + "]");
		MQStringMessage mqMessage = MQAdapter.putMQMessageLocal(mqMgr,
				requestQname, msg, replyQName, replyQMgrName);

		// MQMessage responseMQMessage = MQAdapter.getMQMessage(mqMgr,
		// replyQName,
		// mqMessage.getCorrelationID());
		// String responseMsg = null;
		//
		// if (responseMQMessage != null) {
		// responseMsg = responseMQMessage.readString(responseMQMessage
		// .getDataLength());
		// }

		MQLogging.log("[" + _appName + "] MQ Request : ["
				+ mqMessage.getMessageID() + "] from queue [" + replyQName
				+ "]");

		// String responseMsg = MQAdapter.getMQMessageString(mqMgr, replyQName,
		// mqMessage.getCorrelationID());
		String responseMsg = MQAdapter.getMQMessageString(mqMgr, replyQName,
				mqMessage.getMessageID());

		MQLogging.log("[" + _appName + "] MQ Response : [" + responseMsg
				+ "] from queue [" + replyQName + "]");

		return responseMsg;
	}

	public MQStringMessage sendMessageOnly(MQQueueManager mqMgr,
			String requestQname, String replyQName, String replyQMgrName,
			String msg) throws Exception {
		MQLogging.log("[" + _appName + "] MQ Request : [" + msg
				+ "] on queue [" + requestQname + "]");

		MQStringMessage mqMessage = MQAdapter.putMQMessageLocal(mqMgr,
				requestQname, msg, replyQName, replyQMgrName);

		MQLogging.log("[" + _appName + "] MQ Request : ["
				+ mqMessage.getMessageID() + "] from queue [" + replyQName
				+ "]");
		//
		// String responseMsg = MQAdapter.getMQMessageString(mqMgr, replyQName,
		// mqMessage.getMessageID());
		//
		// MQLogging.log("[" + _appName + "] MQ Response : [" + responseMsg
		// + "] from queue [" + replyQName + "]");

		return mqMessage;
	}

	public String recieveMessageOnly(MQQueueManager mqMgr,
			MQStringMessage mqMessage, String requestQname, String replyQName,
			String replyQMgrName, String msg) throws Exception {
		// MQLogging.log("[" + _appName + "] MQ Request : [" + msg
		// + "] on queue [" + requestQname + "]");
		//
		// MQStringMessage mqMessage = MQAdapter.putMQMessageLocal(mqMgr,
		// requestQname, msg, replyQName, replyQMgrName);

		MQLogging.log("[" + _appName + "] MQ Request : ["
				+ mqMessage.getMessageID() + "] from queue [" + replyQName
				+ "]");

		String responseMsg = MQAdapter.getMQMessageString(mqMgr, replyQName,
				mqMessage.getMessageID());

		MQLogging.log("[" + _appName + "] MQ Response : [" + responseMsg
				+ "] from queue [" + replyQName + "]");

		return responseMsg;
	}

	public MQStringMessage getMessage(MQQueueManager mqMgr, String qName)
			throws Exception {
		MQLogging.log("[" + _appName + "] getting message from queue [" + qName
				+ "]");
		return MQAdapter.getMQMessageLocal(mqMgr, qName);
	}

	public MQStringMessage getMessageWithoutCommit(MQQueueManager mqMgr,
			String qName) throws Exception {
		MQLogging.log("[" + _appName + "] getting message from queue [" + qName
				+ "]");
		return MQAdapter.getMQMessageLocalWithoutCommit(mqMgr, qName);
	}

	public void putStringMessageRemote(MQQueueManager connection, String qName,
			String message, byte[] correlationId, String qMName)
			throws Exception {
		MQLogging.log("[" + _appName + "] putting message on queue [" + qName
				+ "]");
		MQAdapter.putStringMessageRemote(connection, qName, message,
				correlationId, qMName);
	}

	public String getQueueDepth(MQQueueManager mqMgr, String qName)
			throws Exception {
		MQLogging.log("[" + _appName + "] getting message from queue [" + qName
				+ "]");
		return MQAdapter.getMQMessageDepth(mqMgr, qName);
	}
}
// لا اله الا الله 