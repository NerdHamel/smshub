package vas.service.main;

import org.apache.log4j.xml.DOMConfigurator;

import vas.mq.util.ConstantsUtil;
import vas.mq.util.LoggingClass;
import vas.service.ussdpush.UssdPushRequestService;
import vas.util.InitMQ;

public class StartServices
{

    public StartServices()
    {
    }

    public static void main(String args[])
    {
        try
        {
            LoggingClass.logInfo("*********** Starting Vas Services *****************");
            try
            {
                LoggingClass.logInfo("Going to Configure Log4J Logging ...");
                DOMConfigurator.configure("log4j-babyvas-server.xml");
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
            initMQ();
            int numberOfThreads = ConstantsUtil.VAS_USSD_PUSH_SERVICE;
            UssdPushRequestService lstService[] = new UssdPushRequestService[numberOfThreads];
            LoggingClass.logInfo(" Number of threads for VAS USSD PUSH SERVICE will be started is :" + numberOfThreads);
            for(int i = 0; i < numberOfThreads; i++)
            {
                LoggingClass.logInfo("Starting VAS USSD PUSH SERVICE Thread number: " + i);
                lstService[i] = new UssdPushRequestService();
                lstService[i].start();
                try
                {
                    Thread.sleep(1000L);
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }

           
            LoggingClass.logInfo("**************** VAS USSD PUSH SERVICE started successfully *****************");
            do
            {
                try
                {
                    for(int i = 0; i < lstService.length; i++)
                    {
                        LoggingClass.logInfo("Thread Status: at location " + i + " Name: " + lstService[i].toString() + " Status: " + lstService[i].getState() + " IsAlive: " + lstService[i].isAlive());
                        if(!lstService[i].isAlive())
                        {
                            LoggingClass.logInfo("Going to Start... Thread Status: at location " + i + " Name: " + lstService[i].toString() + " Status: " + lstService[i].getState() + " IsAlive: " + lstService[i].isAlive());
                            lstService[i] = new UssdPushRequestService();
                            lstService[i].start();
                            try
                            {
                                Thread.sleep(1000L);
                            }
                            catch(InterruptedException ex)
                            {
                                ex.printStackTrace();
                            }
                            LoggingClass.logInfo("Started... Thread Status: at location " + i + " Name: " + lstService[i].toString() + " Status: " + lstService[i].getState() + " IsAlive: " + lstService[i].isAlive());
                        }
                    }

                }
                catch(Exception exp)
                {
                    LoggingClass.logError("Error During checking Thread Status: " + exp.getMessage());
                }
                try
                {
                    Thread.sleep(60000L);
                }
                catch(Exception exp)
                {
                    exp.printStackTrace();
                }
            } while(true);
        }
        catch(Exception ex)
        {
            LoggingClass.logInfo("Exception ..." + ex.getMessage());
        }
    }

    private static void initMQ()
    {
        LoggingClass.logInfo("Initializing MQ Connections for VAS Services ");
        InitMQ.initMQ();
        LoggingClass.logInfo(" Finished Initializing MQ Connections for VAS Services");
    }
			
			
			
			
}
