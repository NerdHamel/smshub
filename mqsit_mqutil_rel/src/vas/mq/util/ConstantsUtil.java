package vas.mq.util;

public class ConstantsUtil {
	
	// Loading from Properties file
	// public static String QUEUE_MANAGER_NAME = "EESO008D";//IDVE
	// public static String QUEUE_MANAGER_NAME = "EESO801I";//SIT
	// public static String QUEUE_MANAGER_NAME = "TESTVAS";//TESTVAS
	// public static String QUEUE_MANAGER_NAME = "EESO1004P";//EESO1004P
	public static String GCCROW_BALANCE_SMS =  MQConfigurations
			.getConfiguValue("SMS.INCOMING.REQUEST");
	public static String QUEUE_MANAGER_NAME = MQConfigurations
			.getConfiguValue("MQ.QUEUE_MANAGER_NAME");

	public static int POOL_SIZE_hardcoded = 2;
	public static int POOL_SIZE = Integer.parseInt(MQConfigurations
			.getConfiguValue("MQ.POOL_SIZE"));
	public static int QUEUE_READ_TIMEOUT_hardcoded = 30000;
	public static int QUEUE_READ_TIMEOUT = Integer.parseInt(MQConfigurations
			.getConfiguValue("MQ.QUEUE_READ_TIMEOUT"));

	public static int POOL_SIZE_VAS_SERVICE = Integer.parseInt(MQConfigurations
			.getConfiguValue("MQ.VAS_SERVICE_POOL_SIZE"));
	public static int VAS_SERVICE_TIMEOUT = Integer.parseInt(MQConfigurations
			.getConfiguValue("MQ.VAS_SERVICE_TIMEOUT"));

	public static int VAS_USSD_PUSH_SERVICE = Integer.parseInt(MQConfigurations
			.getConfiguValue("MQ.VAS_USSD_PUSH_SERVICE"));

	public static String QUEUE_MANAGER_NAME_hardcoded = "EESO008D";// IDVE
	// public static String QUEUE_MANAGER_NAME_hardcoded = "EESO801I";// SIT
	public static String IP_hardcoded = "10.14.11.65";
	public static String CHANNEL_NAME_hardcoded = "SYSTEM.ADMIN.SVRCONN";
	public static int PORT_hardcoded = 9020;// IDEV
	// public static int PORT_hardcoded = 1416;// SIT

	// public static String MDM_CUSTOMER_PROFILE_REQUEST_QUEUE =
	// "MDM.MESSAGING.REQUEST";
	// public static String VAS_CUSTOMER_PROFILE_RESPONSE_QUEUE =
	// "VAS.CUSTOMERPROFILE.RESPONSE";
	public static String MDM_CUSTOMER_PROFILE_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.MDM_CUSTOMER_PROFILE_REQUEST_QUEUE");
	public static String VAS_CUSTOMER_PROFILE_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_CUSTOMER_PROFILE_RESPONSE_QUEUE");
	

	public static String MOBILY_FUNC_WSGATEWAY_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_WSGATEWAY_REQUEST");

	// public static String MOBILY_SF_GSM_SERVICE_SBU_REQ_QUEUE =
	// "MOBILY.SF.GSM.SERVICE.SBU.REQ";
	// public static String MOBILY_SF_GSM_SERVICE_SBU_RESPONSE_QUEUE =
	// "VAS.CUSTOMERPROFILE.RESPONSE";

	// public static String MOBILY_FUNC_CR_INQUIRY_REQUEST =
	// "MOBILY.FUNC.CR.INQUIRY.REQUEST";
	// public static String VAS_CR_INQUIRY_RESPONSE = "VAS.CR.INQUIRY.RESPONSE";
	// -----------------------MCR General Inquiry--------------------------
	public static String MOBILY_FUNC_CR_INQUIRY_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY.FUNC.CR.INQUIRY.REQUEST");
	public static String VAS_CR_INQUIRY_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.VAS.CR.INQUIRY.RESPONSE");

	public static String MOBILY_FUNC_MCR_PROMO_INQUIRY_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_MCR_PROMO_INQUIRY_REQUEST");

	// public static String MOBILY_FUNC_NSA_CALLER_REQUEST_QUEUE =
	// "MOBILY.FUNC.NSA.CALLER.REQUEST";
	// public static String MOBILY_FUNC_NSA_CALLER_RESPONSE_QUEUE =
	// "VAS.CUSTOMERPROFILE.RESPONSE";

	public static String MOBILY_FUNC_DSA_HANDLER_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY.FUNC.DSA.HANDLER.REQUEST");

	// --------------------Forced Auto-renewal - Interface Document -
	// Mediation---------------------------
	public static String MOBILY_FUNC_MED_ARRENEWAL_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_MED_ARRENEWAL_REQUEST_QUEUE");
	public static String VAS_FUNC_MED_ARRENEWAL_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.VAS_FUNC_MED_ARRENEWAL_RESPONSE_QUEUE");

	// --------------------Roaming Activation Service -
	// EAI---------------------------
	public static String COM_MOBILY_CC_ROAMING_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.COM_MOBILY_CC_ROAMING_REQUEST_QUEUE");
	public static String COM_MOBILY_CC_ROAMING_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.COM_MOBILY_CC_ROAMING_RESPONSE_QUEUE");

	// -----------------------------MDM - 4 G LINES count
	// ------------------------
	public static String MDM_CONTRACT_COMPOSITE_4GLINES_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.MDM_CONTRACT_COMPOSITE_4GLINES_REQUEST_QUEUE");
	public static String VAS_CONTRACT_COMPOSITE_4GLINES_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_CONTRACT_COMPOSITE_4GLINES_RESPONSE_QUEUE");

	// -----------------------------MDM - get contact Email
	// ------------------------
	public static String MDM_CONTRACT_COMPOSITE_EMAIL_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.MDM_CONTRACT_COMPOSITE_EMAIL_REQUEST_QUEUE");
	public static String VAS_CONTRACT_COMPOSITE_EMAIL_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_CONTRACT_COMPOSITE_EMAIL_RESPONSE_QUEUE");
	// ----------------------------- MDM SIM Inqiry -----------------
	public static String MDM_SIM_INQUIRY_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.MDM_SIM_INQUIRY_REQUEST_QUEUE");
	public static String VAS_SIM_INQUIRY_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_SIM_INQUIRY_RESPONSE_QUEUE");

	
	
	//------------------------------------------
	
	public static String MARCHENT_INQUIRY_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MOBILY.FUNC.MERCHANTIDAVBLE.REQ");
	
	
	// ----------------------------- MDM Ten Line Validation -----------------
	public static String MDM_LINE_VALIDATION_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.MDM_LINE_VALIDATION_REQUEST_QUEUE");
	public static String VAS_LINE_VALIDATION_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_LINE_VALIDATION_RESPONSE_QUEUE");
	// ----------------------------- EAI - Get ELM ID Inquiry -----------------
	public static String EAI_ELM_ID_INQUIRY_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.EAI_ELM_ID_INQUIRY_REQUEST_QUEUE");
	public static String VAS_ELM_ID_INQUIRY_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_ELM_ID_INQUIRY_RESPONSE_QUEUE");

	// ----------------------------- EAI - Set SIM Activation -----------------
	public static String EAI_SIM_ACTIVATION_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.EAI_SIM_ACTIVATION_REQUEST_QUEUE");
	public static String VAS_SIM_ACTIVATION_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_SIM_ACTIVATION_RESPONSE_QUEUE");
	// --------------------VAS Services Queues----------------------------
	public static String VAS_SERVICE_USSD_PUSH_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_SERVICE_USSD_PUSH_REQUEST_QUEUE");
	// ----------------------------- EAI - Set SIM Activation -----------------
	public static String VAS_USSD_PUSH_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_USSD_PUSH_REQUEST_QUEUE");
	public static String VAS_USSD_PUSH_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_USSD_PUSH_RESPONSE_QUEUE");

	// ----------------------------- EAI - IS AP -----------------
	public static String EAI_IS_AP_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.EAI_IS_AP_REQUEST_QUEUE");
	public static String VAS_IS_AP_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_IS_AP_RESPONSE_QUEUE");

	// ----------------------------- EAI - NBA -----------------
	public static String EAI_NBA_CAMPAIGN_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.EAI_NBA_CAMPAIGN_REQUEST_QUEUE");

	// ----------------------------- EAI - Reseller Addon -----------------
	public static String EAI_ADDONS_SUBSCRIPTION_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.EAI_ADDONS_SUBSCRIPTION_REQUEST_QUEUE");
	// public static String VAS_ADDONS_SUBSCRIPTION_RESPONSE_QUEUE =
	// MQConfigurations
	// .getConfiguValue("MQ.VAS_ADDONS_SUBSCRIPTION_RESPONSE_QUEUE");

	// ----------------------------- EAI - Shared Account - WAJID PLUS/EXTRA
	// -----------------
	public static String MOBILY_FUNC_SO_MAIN_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_SO_MAIN_REQUEST_QUEUE");
	public static String VAS_SHARING_ACCOUNT_MAIN_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.VAS_SHARING_ACCOUNT_MAIN_RESPONSE_QUEUE");

	public static String MOBILY_INQ_MSISDN_RESOURCERELATION_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_INQ_MSISDN_RESOURCERELATION_REQUEST_QUEUE");
	public static String VAS_INQ_MSISDN_RESOURCERELATION_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_INQ_MSISDN_RESOURCERELATION_RESPONSE_QUEUE");

	public static String CONSUMED_BALANCE_INQUIRY_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.CONSUMED_BALANCE_INQUIRY_REQUEST_QUEUE");
	public static String VAS_CONSUMED_BALANCE_INQUIRY_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.VAS_CONSUMED_BALANCE_INQUIRY_RESPONSE_QUEUE");

	public static String RUSBS_RQ_SHARED_SMS_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.RUSBS_RQ_SHARED_SMS_REQUEST_QUEUE");

	// ----------------------------- Queue to purge -----------------
	public static String QUEUE_TO_PURGE = MQConfigurations
			.getConfiguValue("MQ.QUEUE_TO_PURGE");

	// ----------------------------- EAI - SMS GW -----------------
	public static String MOBILY_FUNC_SMSGW_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_SMSGW_REQUEST_QUEUE");

	// ----------------------------- EAI - BALANCE Inquiry -----------------
	public static String MOBILY_INQ_BALANCEINQ_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_INQ_BALANCEINQ_REQUEST");
	public static String VAS_INQ_BALANCEINQ_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.VAS_INQ_BALANCEINQ_RESPONSE");

	// ----------------------------- EAI - UNSuspense -----------------
	public static String MOBILY_FUNC_UNSUS_BSL_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_UNSUS_BSL_REQUEST");

	// ----------------------------- EAI - Full Un Barring -----------------
	public static String MOBILY_SIM_FULL_UNBAR_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_SIM_FULL_UNBAR_REQUEST");
	public static String MOBILY_SIM_FULL_BAR_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_SIM_FULL_BAR_REQUEST");
	public static String MOBILY_SIM_FULL_UNBAR_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_SIM_FULL_UNBAR_RESPONSE");

	// ----------------------------- EAI - Prepaid Package Conversion
	// -----------------
	public static String MOBILY_FUNC_CONVERSION_WITH_SUPP_SYNC_REQ = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_CONVERSION_WITH_SUPP_SYNC_REQ");

	// ----------------------------- EAI - NBA Start Session -----------------
	public static String MOBILY_FUNC_NBA_START_SESSION_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.EAI_NBA_START_SESSION_REQUEST_QUEUE");
	public static String MOBILY_FUNC_NBA_START_SESSION_RESPONSE_QUEUE = MQConfigurations
			.getConfiguValue("MQ.EAI_NBA_START_SESSION_RESPONSE_QUEUE");

	// ----------------------------- EAI - NBA Update Session -----------------
	public static String EAI_NBA_UPDATE_SESSION_REQUEST_QUEUE = MQConfigurations
			.getConfiguValue("MQ.EAI_NBA_UPDATE_SESSION_REQUEST_QUEUE");

	// ----------------------------- EAI - Force Renewal -----------------
	public static String MOBILY_FUNC_GENERIC_ROUTER_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_GENERIC_ROUTER_REQUEST");

	// ----------------------------- EAI - Promotion Framework Inquiry
	// -----------------
	public static String MOBILY_PROMO_INQUIRY_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_PROMO_INQUIRY_REQUEST");
	public static String MOBILY_PROMO_INQUIRY_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_PROMO_INQUIRY_RESPONSE");

	// ----------------------------- EAI - Free BALANCE Inquiry
	// -----------------
	public static String FREE_BALANCE_INQUIRY_REQUEST = MQConfigurations
			.getConfiguValue("MQ.FREE_BALANCE_INQUIRY_REQUEST");
	public static String VAS_FREE_BALANCE_INQUIRY_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.VAS_FREE_BALANCE_INQUIRY_RESPONSE");

	// ----------------------------- EAI - ICS2_MGMT Replace
	// -------------------------
	public static String MOBILY_FUNC_SS_MGMT_SERVICE_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_SS_MGMT_SERVICE_REQUEST");
	
	public static String MOBILY_FUNC_SS_SUB_MONTHLY_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_SS_SUB_MONTHLY_REQUEST");
	 
	
	
	
	// ----------------------------- EAI - CustomerInfo - for Wajid Exp.
	// -------------------------

	public static String MOBILY_CUSTOMERINFO_INQ_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_CUSTOMERINFO_INQ_REQUEST");
	public static String MOBILY_CUSTOMERINFO_INQ_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_CUSTOMERINFO_INQ_RESPONSE");

	// ----------------------------- EAI - Prepaid Voucher Recharge
	// -------------------------
	public static String MOBILY_FUNC_PREPAID_VOUCHER_RECHARGE_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_PREPAID_VOUCHER_RECHARGE_REQUEST");
	public static String MOBILY_FUNC_PREPAID_VOUCHER_RECHARGE_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_PREPAID_VOUCHER_RECHARGE_RESPONSE");

	// ----------------------------- EAI - Favorite Number Settings
	// -------------------------
	public static String MOBILY_FUNC_FAVNUMBERSET_BSL_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_FAVNUMBERSET_BSL_REQUEST");

	// ----------------------------- EAI - Favorite Number Inquiry
	// -------------------------

	public static String MOBILY_INQ_FAVORITE_NUMBER_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_INQ_FAVORITE_NUMBER_REQUEST");
	public static String MOBILY_INQ_FAVORITE_NUMBER_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_INQ_FAVORITE_NUMBER_RESPONSE");

	// #----------------------------- EAI - Neqaty Loyalty Redemption
	// ----------------
	public static String MOBILY_INQ_RTRV_AVLBL_REWARDS_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_INQ_RTRV_AVLBL_REWARDS_REQUEST");
	public static String MOBILY_INQ_RTRV_AVLBL_REWARDS_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_INQ_RTRV_AVLBL_REWARDS_RESPONSE");
	public static String MOBILY_REWARDS_REDEPMTION_SLR_RQ_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_REWARDS_REDEPMTION_SLR_RQ_RESPONSE");

	// ----------------------------- EAI - Promotion Framework --- Connect PAYG
	// Enhancement
	// -----------------
	public static String MOBILY_PROMO_SERVER_PRIORITY_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_PROMO_SERVER_PRIORITY_REQUEST");

	// ----------------------------- USSD Push Survey and RESPONSE to
	// UAD----------------
	public static String MOBILY_FUNC_WSGATEWAY_REQ_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_WSGATEWAY_REQ_REQUEST");
	public static String MOBILY_FUNC_WSGATEWAY_REQ_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_WSGATEWAY_REQ_RESPONSE");

	// -------------- starts PRJ005045 � Enterprise Neqaty 360
	// -----------------------
	// --------------- for Set/Change AP Active pool
	public static String MOBILY_FUNC_CORP_SERVICES_MAIN_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_CORP_SERVICES_MAIN_REQUEST");
	public static String MOBILY_FUNC_CORP_SERVICES_MAIN_REQ_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_CORP_SERVICES_MAIN_REQ_RESPONSE");
	// --------------AP Inquiry for Loyalty Item Matrix-----------------------
	public static String MOBILY_FUNC_LOYALTY_INQUIRY_SERVICES_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_LOYALTY_INQUIRY_SERVICES_REQUEST");
	public static String MOBILY_FUNC_LOYALTY_INQUIRY_SERVICES_RESPONSE = MQConfigurations
			.getConfiguValue("MQ.MOBILY_FUNC_LOYALTY_INQUIRY_SERVICES_RESPONSE");

	// -------------- Ends PRJ005045 � Enterprise Neqaty 360
	// -----------------------

	// --------------Update MDM Language to Sync through
	// EAI-----------------------
	public static String MA_RQ_REQUEST = MQConfigurations
			.getConfiguValue("MQ.MA_RQ_REQUEST");

	// ----- USF Postpaid -- USSD ----

	public static String USF_MANATEQ_REQ = MQConfigurations
			.getConfiguValue("MQ.MOBILY_USF_POSTPAID_MANATEQ_ELIGIBILITY_INQUIRY_REQUEST");

	public static String USF_MANATEQ_RES = MQConfigurations
			.getConfiguValue("MQ.MOBILY_USF_POSTPAID_MANATEQ_ELIGIBILITY_INQUIRY_RESPONSE");

	
	
	
	//---------------------------- postPaid Revamp ----------------------------
	
	
	
	public static String SERVICE_SMS_NOTIFICATION_REQ=MQConfigurations
	.getConfiguValue("MQ.MOBILY_SERVICE_SMS_REQUEST");

	public static String SEIBEL_ASSET_REQUEST=MQConfigurations
			.getConfiguValue("MQ.SEIBEL_ASSET_REQUEST");

			public static String SEIBEL_ASSET_RESPONSE=MQConfigurations
			.getConfiguValue("MQ.SEIBEL_ASSET_RESPONSE");

			
			
	
	

			public static String SS_SUBSCRIBE_PP_REQUEST=MQConfigurations
					.getConfiguValue("MQ.MOBILY_SS_SUBSCRIBE_PP_REQUEST");

					public static String SS_SUBSCRIBE_PP_RESPONSE=MQConfigurations
					.getConfiguValue("MQ.MOBILY_SS_SUBSCRIBE_PP_RESPONSE");

						
			
					public static String MDM_PRODUCT_REQUEST_QUEUE=MQConfigurations
							.getConfiguValue("MQ.MDM_PRODUCT_REQ");
	
					
					public static String SUB_HISTORY_REQ=MQConfigurations
							.getConfiguValue("MQ.SUB_HISTORY_REQ");
	
					
					public static String MOBILY_PROMO_SERVER_REQUEST = MQConfigurations
							.getConfiguValue("MQ.MOBILY_PROMO_SERVER_REQUEST");
					public static String DEVICE_INSTALLMENT_INQUIRY_REQUEST= MQConfigurations
							.getConfiguValue("MQ.DEVICE_INSTALLMENT_INQUIRY");
					
					public static String SFBAAMBAP_REQUEST = MQConfigurations
							.getConfiguValue("VAD_SFBAAMBAP_REQ");
					
					public static String GRACE_INQ_REQUEST = MQConfigurations
							.getConfiguValue("GRACE_INQ_REQ");
					
					public static String CUSTOMER_PRODUCT_RQ= MQConfigurations
							.getConfiguValue("RCPC_RQ");
					
					//TODO 
					
					public static String UNICA_REQUEST_QUEUE = MQConfigurations
							.getConfiguValue("NBA_BATCH.RQ");
					
					public static String UNICA_ACCEPT_OFFER_Q = MQConfigurations
							.getConfiguValue("SCC.RQ");
					public static String VAS_CUSTOMERPROFILE_RESPONSE = MQConfigurations
							.getConfiguValue("VAS.CUSTOMERPROFILE.RESPONSE");
					
					public static String GET_ADD_ONS_QUEUE = MQConfigurations
							.getConfiguValue("MOBILY.INQ.RETRIEVE.AVAILABLE.OFFERING.REQ");
					
					public static String BTL_MAIN_REQUEST =  
							MQConfigurations.getConfiguValue("MOBILY.BTL.MAIN.REQUEST");
					
					public static String LINES_UNDER_ID_QUEUE = 
							MQConfigurations.getConfiguValue("inquiry_accounts");
					public static String CREDIT_REFIL_RQ  = MQConfigurations.getConfiguValue("CreditRefil.RQ");
					
					public static String USSDDATAMENU_SUPPSVC_SOCIAL_MEDIA_RQ  = MQConfigurations.getConfiguValue("MQ.MOBILY_SUPPLIMSERVICE_SOCIALMEDIA_REQ");

					public static String SRO_SET_PAYASYGO=MQConfigurations
							.getConfiguValue("MQ.MOBILY_PAYASYOUGO_REQUEST");
					
					public static String POSTPAID_PKG_CONVERSISON_RQ=
							MQConfigurations.getConfiguValue("MQ.MOBILY_POSTPAID_PKG_CONVERSION_REQ");
					
					public static String MOBILY_CREDITCARD_PAYMENT_RQ=
							MQConfigurations.getConfiguValue("MQ.MOBILY_CREDITCARD_PAYMENT_REQ");
					public static String MOBILY_promo =
							MQConfigurations.getConfiguValue("mobily_promo");
					
					public static String TELEPHONY_INQUIRY_QUEUE =
							MQConfigurations.getConfiguValue("telephony_inquiry");
					public static String TELEPHONY_UPDATE_QUEUE =
							MQConfigurations.getConfiguValue("telephony_update");
					
					public static String OTP_ORDER_QUEUE =MQConfigurations.getConfiguValue("otp_order");
					public static String OTP_VALIDATE_QUEUE =MQConfigurations.getConfiguValue("otp_validate");
					
					public static String EMA_ADAPTER = MQConfigurations.getConfiguValue("check_roaming");
					public static String ROAMING = MQConfigurations.getConfiguValue("ROAMING");
					public static String CORP_BAL_INQ =  MQConfigurations.getConfiguValue("CORP_BAL_INQ");
					public static String SS_INQUIRY_Q = MQConfigurations.getConfiguValue("SS_INQUIRY_Q");
					public static String HOT_STATEMNT_VERIFICATION_QUE = MQConfigurations.getConfiguValue("MQ.HOT_STATEMNT_VERIFICATION_QUE");
					public static String VOUCHER_RECHARGE=MQConfigurations.getConfiguValue("MOBILY.PROMO.SERVER.REQUEST");
					public static String SMS_BALANCE_INQUIRY=MQConfigurations.getConfiguValue("SMS.INCOMING.REQUEST");
					
					public static String IVR_REPORT_ENHANCEMENT=MQConfigurations.getConfiguValue("ivr.report.enhancement");
					public static String CHECK_OUTAGE = MQConfigurations.getConfiguValue("checkoutage");
					public static String DOD_NUMOFOFFERS_POSTPAID= MQConfigurations.getConfiguValue("NUMOFOFFERS_POSTPAID");
					public static String DOD_NUMOFOFFERS_PREPAID= MQConfigurations.getConfiguValue("NUMOFOFFERS_PREPAID");
					public static String SR_TREE_Q = MQConfigurations.getConfiguValue("SRTREE");
					
					
					// New config (Reduce IBM MQ footprint)
					public static String hostname = MQConfigurations.getConfiguValue("mq.host.name");
					public static String qmName = MQConfigurations.getConfiguValue("mq.queue.manager.name");
					public static String port = MQConfigurations.getConfiguValue("mq.port");
					public static String userId = MQConfigurations.getConfiguValue("mq.user.id");
					public static String password = MQConfigurations.getConfiguValue("mq.password");
					public static String channel = MQConfigurations.getConfiguValue("mq.channel");
					
					public static String poolSize = MQConfigurations.getConfiguValue("mq.pool.size");
					public static String queueTimeOut = MQConfigurations.getConfiguValue("mq.timeout");
					public static String qName = MQConfigurations.getConfiguValue("mq.queue.name");
					public static String sampleRequest = MQConfigurations.getConfiguValue("mq.sample.request");
					
					public static String TEST = MQConfigurations.getConfiguValue("test");
										
										
					//customer
					public static String MOBILY_CUSTOMER_PREFERREDLANGUAGE_REQUEST = MQConfigurations
					.getConfiguValue("MQ.MOBILY_CUSTOMER_PREFERREDLANGUAGE_REQUEST");
					//practice
					public static String MOBILY_PUSH_SERVICE_REQUEST = MQConfigurations
							.getConfiguValue("MQ.MOBILY_PUSH_SERVICE_REQUEST");
					
					public static String MOBILY_SMSSURVEY_LASTUPDATE_INQUIRY_REQUEST = MQConfigurations
							.getConfiguValue("MQ.MOBILY_SMSSURVEY_LASTUPDATE_INQUIRY_REQUEST");
					
					public static String MOBILY_LANGUAGE_UPDATE_REQUEST = MQConfigurations
							.getConfiguValue("MQ.MOBILY_LANGUAGE_UPDATE_REQUEST");
					
					
					public static String OTP_VALIDATE_REQ = MQConfigurations.getConfiguValue("MOBILY.FUNC.OTP.VALIDATE.REQ");
					public static String OTP_GENERATION_REQ = MQConfigurations.getConfiguValue("MOBILY.FUNC.OTP.ORDER.REQ");
					public static String OTP_INQUIRY_REQ = MQConfigurations.getConfiguValue("MOBILY.FUNC.OTP.INQUIRY.REQ");
					public static String OTP_REGENERATE = MQConfigurations.getConfiguValue("MQ.MOBILY.FUNC.OTP.REGENERATE");
					public static String GET_Online_Activation_Order = MQConfigurations.getConfiguValue("MOBILY.FUNC.WSGATEWAY.REQ");
					public static String POST2POST_SM_RQ_Q = MQConfigurations.getConfiguValue("MOBILY.FUNC.POST2POSTSM.REQ");
					public static String UPDATE_SERVICE_GROUP = MQConfigurations.getConfiguValue("MOBILY.FUNC.SEND.NOTIFICATIONS.REQ");
					public static String MULTI_SIM_INQUIRY = MQConfigurations.getConfiguValue("MOBILY.MULTISIM.INQ.REQUEST");
					public static String MOBILY_FUNC_SMSGW_USSD_REQUEST_QUEUE = MQConfigurations.getConfiguValue("MQ.MOBILY_FUNC_SMSGW_USSD_REQUEST_QUEUE");
					public static String MOBILY_FTTHINQ_REQUEST = MQConfigurations.getConfiguValue("MOBILY.FUNC.WSGATEWAY.REQ");
					public static String INQUIRY_LAST_BILL = MQConfigurations.getConfiguValue("MOBILY.INQ.LAST.BILLS.REQ");
					public static String MOBILY_UPDATE_VAS_LANGUAGE = MQConfigurations.getConfiguValue("MQ.MOBILY_UPDATE_VAS_LANGUAGE");
					public static String FTTH_STATUS_REQUEST = MQConfigurations.getConfiguValue("MOBILY.FUNC.FTTH.NETWORK.INQ.REQUEST");
					public static String FTTH_Retention_offers = MQConfigurations.getConfiguValue("MOBILY.FUNC.WSGATEWAY.REQ");
					public static String FTTH_ADD_TO_GRACE = MQConfigurations.getConfiguValue("MOBILY.FUNC.ELIFE.SUB.MGMT.REQUEST.NEW");
					
					public static String GSM_LD_IVR= MQConfigurations.getConfiguValue("LDIVR");
					public static String MNP= MQConfigurations.getConfiguValue("MOBILY.FUNC.SEND.NOTIFICATIONS.REQ");
					
					//sms as a channel
					public static String VC_SMS_RECEIVER = MQConfigurations.getConfiguValue("vc_sms_receiver");
					public static String VC_SMS_SENDER=MQConfigurations.getConfiguValue("MOBILY.FUNC.SMSGW.REQUEST");
					//mobily pay
					public static String MOBILY_PAY= MQConfigurations.getConfiguValue("MOBILY.FUNC.BENFCRY.VERIFICATION.DP.REQ");
					
					// Unmanaged AP segregation
					public static String CUST_AND_LINE = MQConfigurations.getConfiguValue("MOBILY.INQ.CUST.LINE.INFO.REQ");
					
}	
