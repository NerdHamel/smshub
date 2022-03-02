package vas.util;

import vas.mq.util.ConstantsUtil;
import vas.mq.handler.MQHandler;

public class InitMQ
{
    public static synchronized void initMQ() {
        if (MQHandler.instance() == null) {
            MQHandler.initializeInstance(ConstantsUtil.QUEUE_MANAGER_NAME, ConstantsUtil.POOL_SIZE_VAS_SERVICE, (long)ConstantsUtil.VAS_SERVICE_TIMEOUT);
        }
    }
    
    public static MQHandler getMQHandler() {
        if (MQHandler.instance() == null) {
            initMQ();
        }
        return MQHandler.instance();
    }
}