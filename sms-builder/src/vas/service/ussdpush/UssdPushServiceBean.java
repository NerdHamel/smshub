package vas.service.ussdpush;

public class UssdPushServiceBean {
	/*
	 * <MOBILY_VAS_REQ> <REQ_HEADER> <funcId>USSD_PUSH_SERVICE</funcId>
	 * <RequestorChannelId></RequestorChannelId>
	 * <RequestorTransId></RequestorTransId> <reqDate></reqDate> </REQ_HEADER>
	 * 
	 * <msisdn>9665XXXXXXXX</msisdn> <!-- Required --> <pushtype></pushtype>
	 * <!-- Required --> <srid></srid> <agent></agent>
	 * <referenceid></referenceid> <surveyid></surveyid>
	 * <surveyrefid></surveyrefid> </MOBILY_VAS_REQ>
	 */

	/*
	 * <MOBILY_VAS_REQ_REPLY>
	 * 
	 * <REQ_HEADER_REPLY> <funcId>USSD_PUSH_SERVICE</funcId>
	 * <RequestorChannelId></RequestorChannelId>
	 * <RequestorTransId></RequestorTransId> <reqDate></reqDate>
	 * <resDate></resDate> </REQ_HEADER_REPLY> <ErrorCode>0</ErrorCode> <!-- 0
	 * in case of success --> <ErrorMSG></ErrorMSG> </MOBILY_VAS_REQ_REPLY>
	 */
	public String funcId = null;
	public String requestorChannelId = null;
	public String requestorTransId = null;
	public String reqDate = null;
	public String resDate = null;

	public byte[] messageId = null;

	public String requesterQueue = null;
	public String requesterQueueManager = null;
	public String language=null;
	public String msisdn = null;
	public String pushtype = null;
	public String srId = null;
	public String agent = null;
	public String referenceId = null;
	public String surveyId = null;
	public String surveyRefId = null;

	public String lastUpdatedDate = null;

	public String errorCode = null;
	public String errorMsg = null;
}
