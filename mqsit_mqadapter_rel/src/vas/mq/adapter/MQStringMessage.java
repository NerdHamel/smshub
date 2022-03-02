package vas.mq.adapter;

import java.io.IOException;
import java.io.Serializable;

import vas.mq.util.MQLogging;

import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueueManager;

public class MQStringMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	protected MQMessage mqMessage;
	protected String requestQueueName;
	protected MQQueueManager conn;
	protected String content;
	protected String backoutQueue;
	protected int backoutThreShold;

	public MQStringMessage() {

	}

	public MQStringMessage(MQMessage mqMsg) {
		this.mqMessage = mqMsg;
	}

	public byte[] getMessageID() {
		return mqMessage.messageId;
	}

	public void setMessageID(byte[] messageId) {
		mqMessage.messageId = messageId;
	}

	public String getMesseageContent() {
		try {
			if (content == null) {
				content = mqMessage.readStringOfByteLength(mqMessage.getMessageLength());
				//content = mqMessage.readString(mqMessage.getDataLength());
			}
		} catch (IOException ioe) {
			MQLogging.logWarn("Unable to read message content or fetch length",
					ioe);
		}
		return content;
	}

	public String getUTF8MesseageContent() {
		try {
			byte[] b = new byte[mqMessage.getDataLength()];
			if (content == null)
				mqMessage.readFully(b);
			content = new String(b, "UTF-8");
		} catch (IOException ioe) {
			MQLogging.logWarn("Unable to read message content or fetch length",
					ioe);
		}
		return content;
	}

	public void setMesseageContent(String messeageContent) {
		content = messeageContent;
	}

	public String getReplyToQueueName() {
		return mqMessage.replyToQueueName;
	}

	public void setReplyToQueueName(String replyToQueueName) {
		mqMessage.replyToQueueName = replyToQueueName;
	}

	public String getReplyToQueueManager() {
		return mqMessage.replyToQueueManagerName;
	}

	public void setReplyToQueueManager(String replyToQueueManager) {
		mqMessage.replyToQueueManagerName = replyToQueueManager;
	}

	public byte[] getCorrelationID() {
		return mqMessage.correlationId;
	}

	public void setCorrelationID(byte[] correlationId) {
		mqMessage.correlationId = correlationId;
	}

	public String getRequestQueueName() {
		return requestQueueName;
	}

	public void setRequestQueueName(String requestQueueName) {
		this.requestQueueName = requestQueueName;
	}

	public MQMessage getMQMessage() {
		return mqMessage;
	}

	public String getBackoutQueue() {
		return backoutQueue;
	}

	public void setBackoutQueue(String backoutQueue) {
		this.backoutQueue = backoutQueue;
	}

	public int getBackoutThreShold() {
		return backoutThreShold;
	}

	public void setBackoutThreShold(int backoutThreShold) {
		this.backoutThreShold = backoutThreShold;
	}

	public MQQueueManager getConn() {
		return conn;
	}

	public void setConn(MQQueueManager conn) {
		this.conn = conn;
	}

	public void setMQMessage(MQMessage message) {
		mqMessage = message;
	}

	public int getBackoutCount() {
		return mqMessage.backoutCount;
	}

	public void setBackoutCount(int backoutCount) {
		mqMessage.backoutCount = backoutCount;
	}

	public String toString() {
		return new StringBuilder("messageId [").append(mqMessage.messageId)
				.append("] replyToQueueName: ")
				.append(mqMessage.replyToQueueName)
				.append("replyToQueueManager: ")
				.append(mqMessage.replyToQueueManagerName)
				.append("correlationID: ").append(mqMessage.correlationId)
				.append("requestQueueName: ").append(requestQueueName)
				.append("backoutQueue: ").append(backoutQueue)
				.append("backoutThreShold: ").append(backoutThreShold)
				.toString();

	}
}
//لا اله الا الله 