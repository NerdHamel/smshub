package vas.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import vas.mq.util.LoggingClass;
import vas.service.ussdpush.UssdPushServiceBean;

public class ParserUtil {
	public static SimpleDateFormat sdfUpdate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");
	public static SimpleDateFormat sdfSrDate = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static String setUssdPushServiceResponse(UssdPushServiceBean bean)
			throws Exception {
		String retValue = "<MOBILY_VAS_REQ_REPLY> " + "<REQ_HEADER_REPLY> "
				+ "<funcId>" + bean.funcId + "</funcId> "
				+ "<RequestorChannelId>" + bean.requestorChannelId
				+ "</RequestorChannelId> " + "<RequestorTransId>"
				+ bean.requestorTransId + "</RequestorTransId> " + "<reqDate>"
				+ bean.reqDate + "</reqDate> " + "<resDate>"
				+ sdfSrDate.format(new Date()) + "</resDate> "
				+ "</REQ_HEADER_REPLY> ";
		if (bean.lastUpdatedDate != null)
			retValue += "<lastupdatetimestamp>" + bean.lastUpdatedDate
					+ "</lastupdatetimestamp>";
		retValue += "<ErrorCode>" + bean.errorCode + "</ErrorCode> "
				+ "<ErrorMSG>" + bean.errorMsg + "</ErrorMSG> "
				+ "</MOBILY_VAS_REQ_REPLY>";
		return retValue;
	}

	public static UssdPushServiceBean getUssdPushServiceBean(String request)
			throws Exception {
		UssdPushServiceBean bean = new UssdPushServiceBean();
		Document doc = parseXMLString(request, null, null);

		/*
		 * <MOBILY_VAS_REQ> <REQ_HEADER> <funcId>USSD_PUSH_SERVICE</funcId>
		 * <RequestorChannelId></RequestorChannelId>
		 * <RequestorTransId></RequestorTransId> <reqDate></reqDate>
		 * </REQ_HEADER>
		 * 
		 * <msisdn>9665XXXXXXXX</msisdn> <!-- Required --> <pushtype></pushtype>
		 * <!-- Required --> <srid></srid> <agent></agent>
		 * <referenceid></referenceid> <surveyid></surveyid>
		 * <surveyrefid></surveyrefid> </MOBILY_VAS_REQ>
		 */
		Node rootNode = null;
		NodeList nl = null;
		try {
			rootNode = doc.getElementsByTagName("REQ_HEADER").item(0);
			nl = rootNode.getChildNodes();
			for (int j = 0; j < nl.getLength(); j++) {
				if (nl.item(j) instanceof Element) {
					Element l_Node = (Element) nl.item(j);
					String p_szTagName = l_Node.getTagName();
					String l_szNodeValue = null;
					l_szNodeValue = null;
					if (l_Node.getFirstChild() != null)
						l_szNodeValue = l_Node.getFirstChild().getNodeValue();
					if (p_szTagName.equalsIgnoreCase("funcId")) {
						bean.funcId = l_szNodeValue;
					} else if (p_szTagName
							.equalsIgnoreCase("RequestorChannelId")) {
						bean.requestorChannelId = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("RequestorTransId")) {
						bean.requestorTransId = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("reqDate")) {
						bean.reqDate = l_szNodeValue;
					}
				}
			}
		} catch (Exception exp) {
		}

		try {
			rootNode = doc.getElementsByTagName("MOBILY_VAS_REQ").item(0);
			nl = rootNode.getChildNodes();
			for (int j = 0; j < nl.getLength(); j++) {
				if (nl.item(j) instanceof Element) {
					Element l_Node = (Element) nl.item(j);
					String p_szTagName = l_Node.getTagName();
					String l_szNodeValue = null;
					l_szNodeValue = null;
					if (l_Node.getFirstChild() != null)
						l_szNodeValue = l_Node.getFirstChild().getNodeValue();
					if (p_szTagName.equalsIgnoreCase("msisdn")) {
						bean.msisdn = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("pushtype")) {
						bean.pushtype = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("srid")) {
						bean.srId = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("agent")) {
						bean.agent = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("referenceid")) {
						bean.referenceId = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("surveyid")) {
						bean.surveyId = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("surveyrefid")) {
						bean.surveyRefId = l_szNodeValue;
					}else if (p_szTagName.equalsIgnoreCase("language")) {
						bean.language = l_szNodeValue;
					}
				}
			}
		} catch (Exception exp) {
		}

		return bean;
	}
	
	
	public static UssdPushServiceBean getUssdPushServiceBean2(String request)
			throws Exception {
		UssdPushServiceBean bean = new UssdPushServiceBean();
		Document doc = parseXMLString(request, null, null);

		/*
		 * <MOBILY_VAS_REQ> <REQ_HEADER> <funcId>USSD_PUSH_SERVICE</funcId>
		 * <RequestorChannelId></RequestorChannelId>
		 * <RequestorTransId></RequestorTransId> <reqDate></reqDate>
		 * </REQ_HEADER>
		 * 
		 * <msisdn>9665XXXXXXXX</msisdn> <!-- Required --> <pushtype></pushtype>
		 * <!-- Required --> <srid></srid> <agent></agent>
		 * <referenceid></referenceid> <surveyid></surveyid>
		 * <surveyrefid></surveyrefid> </MOBILY_VAS_REQ>
		 */
		Node rootNode = null;
		NodeList nl = null;
		try {
			rootNode = doc.getElementsByTagName("REQ_HEADER").item(0);
			nl = rootNode.getChildNodes();
			for (int j = 0; j < nl.getLength(); j++) {
				if (nl.item(j) instanceof Element) {
					Element l_Node = (Element) nl.item(j);
					String p_szTagName = l_Node.getTagName();
					String l_szNodeValue = null;
					l_szNodeValue = null;
					if (l_Node.getFirstChild() != null)
						l_szNodeValue = l_Node.getFirstChild().getNodeValue();
					if (p_szTagName.equalsIgnoreCase("funcId")) {
						bean.funcId = l_szNodeValue;
					} else if (p_szTagName
							.equalsIgnoreCase("RequestorChannelId")) {
						bean.requestorChannelId = l_szNodeValue;
						
						bean.requestorChannelId =nodeToString(l_Node);
						
						if (bean.requestorChannelId!= null) {
							bean.requestorChannelId=bean.requestorChannelId.replace("<RequestorChannelId>", "");
							bean.requestorChannelId=bean.requestorChannelId.replace("</RequestorChannelId>", "");
						}
					} else if (p_szTagName.equalsIgnoreCase("RequestorTransId")) {
						bean.requestorTransId = l_szNodeValue;
						
					
					} else if (p_szTagName.equalsIgnoreCase("reqDate")) {
						bean.reqDate = l_szNodeValue;
					}
				}
			}
		} catch (Exception exp) {
		}

		try {
			rootNode = doc.getElementsByTagName("MOBILY_VAS_REQ").item(0);
			nl = rootNode.getChildNodes();
			for (int j = 0; j < nl.getLength(); j++) {
				if (nl.item(j) instanceof Element) {
					Element l_Node = (Element) nl.item(j);
					String p_szTagName = l_Node.getTagName();
					String l_szNodeValue = null;
					l_szNodeValue = null;
					if (l_Node.getFirstChild() != null)
						l_szNodeValue = l_Node.getFirstChild().getNodeValue();
					if (p_szTagName.equalsIgnoreCase("msisdn")) {
						bean.msisdn = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("pushtype")) {
						bean.pushtype = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("srid")) {
						bean.srId = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("agent")) {
						bean.agent = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("referenceid")) {
						String str = nodeToString(l_Node);
						if (str!=null) {
							str=str.replace("<referenceid>", "");
							str=str.replace("</referenceid>", "");
						}
						
						bean.referenceId=str;
					} else if (p_szTagName.equalsIgnoreCase("surveyid")) {
						bean.surveyId = l_szNodeValue;
					} else if (p_szTagName.equalsIgnoreCase("surveyrefid")) {
						bean.surveyRefId = l_szNodeValue;
					}else if (p_szTagName.equalsIgnoreCase("language")) {
						bean.language = l_szNodeValue;
					}
				}
			}
		} catch (Exception exp) {
		}

		return bean;
	}
	
	private static String nodeToString(Node node) {
		 StringWriter sw = new StringWriter();
		try {
		 
		  try {
		    Transformer t = TransformerFactory.newInstance().newTransformer();
		    t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    t.transform(new DOMSource(node), new StreamResult(sw));
		  } catch (TransformerException te) {
		    System.out.println("nodeToString Transformer Exception");
		  }
		  
		}catch(Exception e){e.printStackTrace();}
		return sw.toString();
		}

	public static Document parseXMLString(String aXMLStr, String msisdn,
			String requestId) throws Exception {
		// log.debug("XML to be parsed is : " + aXMLStr);
		DOMParser parser = new DOMParser();
		try {
			// log.debug("Started parsing");
			StringReader strReader = new StringReader(aXMLStr);
			InputSource inputSource = new InputSource(strReader);
			parser.parse(inputSource);
			// log.debug("Parsing finished");
			strReader.close();
		} catch (IOException ex) {
			LoggingClass.logError("parseXMLString - MSISDN: " + msisdn
					+ ", RequestId: " + requestId, ex);
			throw ex;
		} catch (SAXException saxEX) {
			LoggingClass.logError("parseXMLString - MSISDN: " + msisdn
					+ ", RequestId: " + requestId, saxEX);
			throw saxEX;
		} catch (Exception exp) {
			LoggingClass.logError("parseXMLString - MSISDN: " + msisdn
					+ ", RequestId: " + requestId, exp);
			throw exp;
		}
		return parser.getDocument();
	}

	public static String getLanguageValue(String lang) {
		if (lang != null) {
			if (lang.trim().equals("100"))
				return "2";
			else if (lang.trim().equals("1100"))
				return "1";
			else if (lang.trim().equals("4300"))
				return "3";
			else if (lang.trim().equals("4400"))
				return "4";
			else if (lang.trim().equals("4500"))
				return "5";
			else
				return "2";
		} else {
			return "2";
		}
	}

	public static String getLanguageMappingCode(String lang) {
		if (lang.trim().equals("2"))
			return "100";
		else if (lang.trim().equals("1"))
			return "1100";
		else if (lang.trim().equals("3"))
			return "4300";
		else if (lang.trim().equals("4"))
			return "4400";
		else if (lang.trim().equals("5"))
			return "4500";
		else
			return "";
	}
}
