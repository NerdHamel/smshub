package vas.util;

import com.mobily.customerEncryption.MobilyEncryption;

import vas.mq.util.MQConfigurations;

import java.io.PrintStream;


public class EncryptMsisdn
{
    public String getEncryptionMSISDN(String anumber) {
        String originalString = anumber;
        MobilyEncryption mobilyEncryption = new MobilyEncryption();
        String encryptedString = null;
        try {
            encryptedString = mobilyEncryption.encrypt(originalString,
            		MQConfigurations.getConfiguValue("USSD.SECRET_KEY"));
            //System.out.println(originalString);
            System.out.println(encryptedString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
}