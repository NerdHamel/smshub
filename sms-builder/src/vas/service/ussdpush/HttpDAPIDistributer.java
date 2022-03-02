package vas.service.ussdpush;


import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.*;
import javax.net.ssl.*;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import vas.util.EncryptMsisdn;
import vas.util.TokenSessionEntity;

public class HttpDAPIDistributer
{

    HttpClient httpClient;

    public HttpDAPIDistributer()
    {
        httpClient = new DefaultHttpClient();
    }

    public static HttpDAPIDistributer getInstance()
    {
        System.out.println("In HttpApiDistributer getInstance");
        return new HttpDAPIDistributer();
    }

    public TokenSessionEntity getAuthorizetoken(String msisdn)
    {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("WdCbfwTibRGffliAoJtqhugqnhmQEc",
                        "=*N|&<6w$7J4JWL"));
        try
        {
            httpClient = HttpClients.custom().setSSLSocketFactory(
                    new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, 
                            new TrustSelfSignedStrategy()).build()))
                    .setDefaultCredentialsProvider(credentialsProvider).build();
        }
        catch(KeyManagementException e1)
        {
            e1.printStackTrace();
        }
        catch(NoSuchAlgorithmException e1)
        {
            e1.printStackTrace();
        }
        catch(KeyStoreException e1)
        {
            e1.printStackTrace();
        }
        EncryptMsisdn encryptMssin = new EncryptMsisdn();
        HttpPost httpPost = new HttpPost("https://api.mobily.com.sa//api/internal-oauth-server/oauth/token");
        System.out.println("httppostUrl..... http://ssta003:9080/api/internal-oauth-server/oauth/token");
        List paramsList = new ArrayList();
        paramsList.add(new BasicNameValuePair("grant_type", "client_credentials"));
        paramsList.add(new BasicNameValuePair("app_id", "USSD"));
        System.out.println("httppostapp_id..... USSD");
        paramsList.add(new BasicNameValuePair("app_version", "v1"));
        paramsList.add(new BasicNameValuePair("device_id", "USSD-DEVICE"));
        paramsList.add(new BasicNameValuePair("lang", "EN"));
        paramsList.add(new BasicNameValuePair("with_msisdn", "Y"));
        paramsList.add(new BasicNameValuePair("msisdn", "DIZsxy01ylZVm1U+D99tcubrlrCT8GFHBRswBPGVT2OlIlM1l342Lv3cvtToTRA0KVkwhXVrDAeIP9/F" +
"S0Vmsnp1qbE4zPqb2SHgEfC+geberpdp5t0QGVGvRl5d34WZ4i3H+OdEtRCkis7dAyPmBXgb7YrLBZov" +
"Rm4L1Dk21KX6wzSSwVStW0JrMzJsZnBelco3lyWrzHJcshOM/ePbf6ppnC2DAjhFLGIlucrLAPgeXcyo" +
"LVkrEYSQapPGGswoZOtQRHv2DKHl3iVc3qMo0zMKKRKfJAatkXrsgnWfO3uMPCeOFUancL96GYx/2jGZ" +
"uC8733xKYgDBaqOELtfXXA=="));

//paramsList.add(new BasicNameValuePair("msisdn","jHG2v6SJNKUIv/3gy8ol7TIYKRX0lPMnPIneui0wrHBp4h/voFC+BEcQ479k7iK0iaoHc+ndISZuZ5o3TJ5t29cx/zzEWSHYKVy2vImhCO10G/SpUfRChfc6UV32V6OiZRIydNluOTBa6kzZn5oj0mQgbyNvcolGRksb5Tux/imsoMHYggG1ybEtRyci5kXOEY4obvsTUxU5wlnSihBbaHgxykmmjyTjMRjdMgTb9S40F0LFYNU3lCWSnUUq7eJiX2H31ejrgYaIU9Nc8cWJcUJPXDiWLMmzGhBRPgSrcWBY92xg8bZ6JKCVdVYETidIwMp7kCQ3Ycb1XCXdP3O27Q=="));

        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        TokenSessionEntity access_token = new TokenSessionEntity();
        try
        {
            System.out.println("inside respEntity  httppost");
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            System.out.println((new StringBuilder()).append("inside respEntity  ").append(respEntity).toString());
            if(respEntity != null)
            {
                String content = EntityUtils.toString(respEntity);
                System.out.println((new StringBuilder()).append("CONTENT in every time").append(content).toString());
                JSONObject json = (JSONObject)(new JSONParser()).parse(content);
                access_token.setToken((String)json.get("access_token"));
                access_token.setCreatedOn(new Date());
                access_token.setMsisdn(msisdn);
                access_token.setExpiry("30 mints");
                System.out.println("right after");
                System.out.println((new StringBuilder()).append("token in method authorization").append(access_token.getToken()).toString());
                	X509TrustManager[] trustAllCerts = new X509TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}

				} };
                        
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HostnameVerifier hostnameverifier = new HostnameVerifier() {

                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }

            
           
                };
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return access_token;
    }

    public TokenSessionEntity getAuthorizetoken2(String msisdn)
    {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("WdCbfwTibRGffliAoJtqhugqnhmQEc", "=*N|&<6w$7J4JWL"));
        try
        {
            httpClient = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build())).setDefaultCredentialsProvider(credentialsProvider).build();
        }
        catch(KeyManagementException e1)
        {
            e1.printStackTrace();
        }
        catch(NoSuchAlgorithmException e1)
        {
            e1.printStackTrace();
        }
        catch(KeyStoreException e1)
        {
            e1.printStackTrace();
        }
        EncryptMsisdn encryptMssin = new EncryptMsisdn();
        HttpPost httpPost = new HttpPost("https://api.mobily.com.sa/apis/apigee-oauth/access-token");
        String user = "WdCbfwTibRGffliAoJtqhugqnhmQEc";
        String pwd = "=*N|&<6w$7J4JWL";
        String encoding = Base64.getEncoder().encodeToString((new StringBuilder()).append(user).append(":").append(pwd).toString().getBytes());
        httpPost.setHeader("Authorization", (new StringBuilder()).append("Basic ").append(encoding).toString());
        List paramsList = new ArrayList();
        paramsList.add(new BasicNameValuePair("grant_type", "client_credentials"));
        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(paramsList));
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        TokenSessionEntity access_token = new TokenSessionEntity();
        try
        {
            System.out.println("inside respEntity  httppost");
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            System.out.println((new StringBuilder()).append("inside respEntity  ").append(respEntity).toString());
            if(respEntity != null)
            {
                String content = EntityUtils.toString(respEntity);
                System.out.println((new StringBuilder()).append("CONTENT in every time").append(content).toString());
                JSONObject json = (JSONObject)(new JSONParser()).parse(content);
                access_token.setToken((String)json.get("access_token"));
                access_token.setCreatedOn(new Date());
                access_token.setMsisdn(msisdn);
                access_token.setExpiry("30 mints");
                System.out.println("right after");
                System.out.println((new StringBuilder()).append("token in method authorization").append(access_token.getToken()).toString());
               	X509TrustManager[] trustAllCerts = new X509TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}

				} };
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
              
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return access_token;
    }

    public static void main(String a[])
    {
        HttpDAPIDistributer aa = new HttpDAPIDistributer();
        aa.getAuthorizetoken("966545296279");
        
        String s="DIZsxy01ylZVm1U+D99tcubrlrCT8GFHBRswBPGVT2OlIlM1l342Lv3cvtToTRA0KVkwhXVrDAeIP9/F" +
"S0Vmsnp1qbE4zPqb2SHgEfC+geberpdp5t0QGVGvRl5d34WZ4i3H+OdEtRCkis7dAyPmBXgb7YrLBZov" +
"Rm4L1Dk21KX6wzSSwVStW0JrMzJsZnBelco3lyWrzHJcshOM/ePbf6ppnC2DAjhFLGIlucrLAPgeXcyo" +
"LVkrEYSQapPGGswoZOtQRHv2DKHl3iVc3qMo0zMKKRKfJAatkXrsgnWfO3uMPCeOFUancL96GYx/2jGZ" +
"uC8733xKYgDBaqOELtfXXA=="
;
        System.out.println(s);
    }

    private String postResponse(String msisdn, String _2, String _3, String s)
    {
        return null;
    }

    public String postResponse(String msisdn, String input, String legacyTargetCode)
    {
        String url = "";
        String responseInJson = "";
        HttpClient client = HttpClientBuilder.create().build();
        try
        {
            JSONObject object = new JSONObject();
            JSONObject dataJson = new JSONObject();
            url = "https://api.mobily.com.sa/apis/endpoint-app.cognigy.ai";
            System.out.println((new StringBuilder()).append("inside url---------").append(url).toString());
            HttpPost post = new HttpPost(url);
            object.put("sessionId", (msisdn));
            object.put("userId", "sms_test");
            object.put("data", dataJson);
            object.put("text", input != null ? ((Object) (input)) : "hi");
            TokenSessionEntity accessToken = getAuthorizetoken2(msisdn);
            String token = accessToken.getToken();
            System.out.println((new StringBuilder()).append("generated token").append(token).toString());
           // dataJson.put("isFromUSSD", "true");
            //dataJson.put("DAPIToken", getAuthorizetoken(msisdn).getToken());
            dataJson.put("sourceSystem", "SMS");
            dataJson.put("legacyTargetCode", legacyTargetCode);
            
            String message = object.toString();
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", (new StringBuilder()).append("Bearer ").append(token).toString());
            post.setHeader("Channel", "USSD");
            post.setEntity(new StringEntity(message, "UTF8"));
            HttpResponse response = client.execute(post);
            HttpEntity respEntity = response.getEntity();
            System.out.println((new StringBuilder()).append("Before respEntity1").append(respEntity.getContent()).toString());
            responseInJson = EntityUtils.toString(respEntity);
            System.out.println((new StringBuilder()).append("Before responseString").append(responseInJson).toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return responseInJson;
    }
}
