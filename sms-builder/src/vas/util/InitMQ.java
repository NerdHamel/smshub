package vas.util;
import com.mobily.mq.util.MQHelper;

import vas.mq.util.ConstantsUtil;
import vas.mq.util.LoggingClass;


public class InitMQ
{

    private static volatile MQHelper instance = MQHelper.instance();

    public InitMQ()
    {
    } 

    public static void initMQ()
    {
        try
        {
            String hostname = ConstantsUtil.hostname;
            String qmName = ConstantsUtil.qmName;
            int port = Integer.parseInt(ConstantsUtil.port);
            String userId = ConstantsUtil.userId;
            String password = ConstantsUtil.password;
            String channel = ConstantsUtil.channel;
            int poolSize = Integer.parseInt(ConstantsUtil.poolSize);
            long queueTimeOut = Long.parseLong(ConstantsUtil.queueTimeOut);
            String qName = ConstantsUtil.qName;
            String sampleRequest = ConstantsUtil.sampleRequest;
            LoggingClass.logInfo("hostname=" + hostname);
            LoggingClass.logInfo("qmName=" + qmName);
            LoggingClass.logInfo("port=" + port);
            LoggingClass.logInfo("userId=" + userId);
            LoggingClass.logInfo("password=" + password);
            LoggingClass.logInfo("channel=" + channel);
            LoggingClass.logInfo("poolSize=" + poolSize);
            LoggingClass.logInfo("queueTimeOut=" + queueTimeOut);
            LoggingClass.logInfo("queueName=" + qName);
            LoggingClass.logInfo("sampleRequest=" + sampleRequest);
            MQHelper.initializeInstance(qmName, poolSize, queueTimeOut, hostname, port, channel, userId, password);
        }
        catch(NumberFormatException e)
        {
            LoggingClass.logError("an error occurred in InitMQ", e);
        }
    }

    public static MQHelper getMQHandler()
    {
        if(instance == null)
        {
            synchronized(vas.util.InitMQ.class)
            {
                if(instance == null)
                {
                    initMQ();
                    instance = MQHelper.instance();
                }
            }
        }
        return instance;
    }

}



