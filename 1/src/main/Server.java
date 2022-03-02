package main;

import java.io.*;
import java.net.InetSocketAddress;

import javax.xml.ws.*;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;

import com.sun.net.httpserver.*;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

import vas.mq.util.ConstantsUtil;
import vas.service.main.StartServices;
import vas.util.InitMQ;

@WebServiceProvider
@ServiceMode(value = Service.Mode.MESSAGE)
public class Server {

	public static void main2(String[] args) {

		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(8080), 0);
			server.createContext("/sendSMS", new SendSMSHandler());
			server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
			server.start();
			
			
			StartServices receiver=new StartServices();
			receiver.main(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main (String [] a) {
		
		String result="{"
				+ "\"msisdn\": \"2222\","
				+ "\"messageBody\": \"في\""
				+ "}";
	     final JsonReader reader = Json.createReader((Reader)new StringReader(result));
         final JsonObject object = reader.readObject();
         JsonString msisdn = object.getJsonString("msisdn");
         JsonString msg=object.getJsonString("messageBody");
         
         String s= msg + "";
        for (int i=0; i<s.length();i++) {
        	char c=s.charAt(i);
        	
        	if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
        		  //en glish letter
        		break;
        		}
        	} //end for
        
     
         StringBuilder contents=new StringBuilder( msisdn + ";824861; " + msg);
         
     	try {
			InitMQ.getMQHandler().sendToMQWithoutReply(contents.toString(), 
					ConstantsUtil.VC_SMS_SENDER, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}

}



class SendSMSHandler implements HttpHandler {

	public void handle(HttpExchange exchange) throws IOException {
	byte[]bytes = 	exchange.getRequestBody().readAllBytes();
	String s = new String(bytes);
		int x = 0;
		
		
	}

}