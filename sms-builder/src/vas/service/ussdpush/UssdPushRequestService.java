package vas.service.ussdpush;

import vas.mq.adapter.MQStringMessage;
import vas.mq.logging.MQLog4jLogging;
import vas.mq.util.ConstantsUtil;
import vas.util.DBUtil;
import vas.util.InitMQ;
import vas.util.ParserUtil;

import com.ibm.mq.MQException;

public class UssdPushRequestService extends Thread {
	public void run() {
		MQStringMessage requestMSG = null;
		UssdPushServiceBean bean = null;
		String strMsg="";
		while (true) {
			requestMSG = null;
			bean = null;
			try {
				System.out.println("Going to get the message! "
						+ this.toString());
				requestMSG = InitMQ.getMQHandler().listenToMQ(
						ConstantsUtil.VC_SMS_RECEIVER);
				System.out.println("Got the message! " + requestMSG.toString());

				// byte[] b = new byte[requestMSG.getMQMessage()
				// .getMessageLength()];
				// requestMSG.getMQMessage().readFully(b, 0,
				// requestMSG.getMQMessage().getMessageLength());
				// String requestMsg = new String(b, "utf-8");

				// String strMsg = requestMSG.getUTF8MesseageContent();

				 strMsg = requestMSG.getMesseageContent();

				System.out.println("Parsing bean....................... msg: "
						+ strMsg);
				/*bean = ParserUtil.getUssdPushServiceBean(strMsg);
				requestMSG.setCorrelationID(requestMSG.getMessageID());
				bean.messageId = requestMSG.getMessageID();

				System.out
						.println("Parsing bean....................... getting other info");
				MQLog4jLogging.logInfo("[UssdPushRequestService] Reply Queue: "
						+ requestMSG.getReplyToQueueName());
				MQLog4jLogging
						.logInfo("[UssdPushRequestService] Reply Queue Manager:"
								+ requestMSG.getReplyToQueueManager());
				bean.requesterQueue = requestMSG.getReplyToQueueName();
				bean.requesterQueueManager = requestMSG
						.getReplyToQueueManager();

				if (bean.funcId != null
						&& bean.funcId.trim().equalsIgnoreCase(
								"USSD_PUSH_SERVICE")) {
					if (bean.msisdn != null && !bean.msisdn.trim().equals("")
							&& bean.pushtype != null
							&& !bean.pushtype.trim().equals("")) {
						DBUtil.insertUssdPushRequest(bean);
						bean.errorCode = "0";
					} else {
						bean.errorCode = "1";
						bean.errorMsg = "MSISDN and PushType are required!";
					}
				} else {
					if (bean.funcId != null
							&& bean.funcId.trim().equalsIgnoreCase(
									"USSD_PUSH_SMS_SURVEY_LASTUPDATE")) {
						if (bean.msisdn != null
								&& !bean.msisdn.trim().equals("")) {
							bean = DBUtil.getLatestSMSSurveyDate(bean);
						} else {
							bean.errorCode = "1";
							bean.errorMsg = "MSISDN is required!";
						}

					}
					if (bean.funcId != null
							&& bean.funcId.trim().equalsIgnoreCase(
									"UPDATE_VAS_LANGUAGE")) {
						if (bean.msisdn != null
								&& !bean.msisdn.trim().equals("")) {

							if (bean.language.equals("1")
									|| bean.language.equals("2")) {
								//bean = DBUtil.updateLanguage(bean);
							} else {
								bean.errorCode = "4";
								bean.errorMsg = "Language Not Supported!";

							}
						} else {
							bean.errorCode = "1";
							bean.errorMsg = "MSISDN is required!";
						}

					} else {
						bean.errorCode = "2";
						bean.errorMsg = "Function Id is not correct!";
					}
				}

			} catch (MQException e) {
				e.printStackTrace();
				System.out
						.println("Exception **************************************** e: "
								+ e.getMessage());
				bean.errorCode = "3";
				bean.errorMsg = "Internal Exception! " + e.getMessage();
			*/} catch (Exception exp) {
				exp.printStackTrace();
				System.out
						.println("Exception **************************************** exp: "
								+ exp.getMessage());
				bean.errorCode = "3";
				bean.errorMsg = "Internal Exception! " + exp.getMessage();
			} finally {

			}

			
			HttpDAPIDistributer dapi = HttpDAPIDistributer.getInstance();
			
			String msisdn="",legacyTargetCode="";
			try {
				//Sample: source=966562342626,largeAccount=8020,shortCode=8020,content=Block 966562342626,contentHex=0042006c006f0063006b0020003900360036003500360032003300340032003600320036,dataCoding=0,timeReceived=20171211113010
			 msisdn= strMsg.substring(6, strMsg.indexOf(",largeAccount"));
			 legacyTargetCode=strMsg.substring(strMsg.indexOf("shortCode="), strMsg.indexOf(",content"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			MQLog4jLogging.logInfo("msisdn=" + msisdn + " , short code=" + legacyTargetCode);
			String s = dapi.postResponse(msisdn,
					strMsg.toString(),
legacyTargetCode);
			
			System.out.println("dapi response=" + s
					);
			/*try {
				System.out.println("Going to Put the message! "
						+ this.toString());
				String responseMsg = ParserUtil
						.setUssdPushServiceResponse(bean);
				System.out.println("Going to Put the message! "
						+ this.toString() + responseMsg);
				InitMQ.getMQHandler().sendToMQWithCorrel(responseMsg,
						bean.messageId, bean.requesterQueue,
						bean.requesterQueueManager);
			} catch (MQException e) {
				e.printStackTrace();
			} catch (Exception exp) {
				exp.printStackTrace();
			}*/
		}
	}
	
	public static void main (String a []) {
		HttpDAPIDistributer dapi = HttpDAPIDistributer.getInstance();
		String strMsg="source=966562342626,largeAccount=8020,shortCode=8020,content=Block966562342626,contentHex=0042006c006f0063006b0020003900360036003500360032003300340032003600320036,dataCoding=0,timeReceived=20171211113010";
		String msisdn="",legacyTargetCode="";
		try {
			
			//Sample: source=966562342626,largeAccount=8020,shortCode=8020,content=Block 966562342626,contentHex=0042006c006f0063006b0020003900360036003500360032003300340032003600320036,dataCoding=0,timeReceived=20171211113010
		 msisdn= strMsg.substring(6, strMsg.indexOf(",largeAccount"));
		 legacyTargetCode=strMsg.substring(strMsg.indexOf("shortCode="), strMsg.indexOf(",content"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MQLog4jLogging.logInfo("msisdn=" + msisdn + " , short code=" + legacyTargetCode);
		String s = dapi.postResponse(msisdn,
				strMsg.toString(),
legacyTargetCode);
	}
}
