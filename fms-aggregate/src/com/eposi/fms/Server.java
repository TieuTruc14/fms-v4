package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.AbstractMessage;
import com.eposi.fms.services.QueueingConsumerClient;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import org.apache.log4j.Logger;
import org.nustaq.serialization.FSTConfiguration;

import java.util.concurrent.*;


public class Server extends AbstractBean {
	private static final long serialVersionUID = -210861315150811372L;
    private static Logger log = Logger.getLogger(Server.class.getName());
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
    private QueueingConsumerClient beanQueueingConsumerClient;
    private RealtimeExecutor beanRealtimeExecutor;

    // store unix time of the last message for each vehicle to fix the duplicate message issue
    private static ConcurrentHashMap<String, Integer> hashMapRemoveFixDupplicate = new ConcurrentHashMap<String, Integer>();

    /*************************************************************************
	 * THE VERY MAIN METHOD <== SHOULD BE CALLED ON STARTUP.
	 */
	public void run() {
        System.out.println("runWithoutUsingAkka:");
        runWithoutUsingAkka();
	}

    private void runWithoutUsingAkka() {
        beanQueueingConsumerClient  = (QueueingConsumerClient) applicationContext.getBean("beanQueueingConsumerClient");
        beanRealtimeExecutor = (RealtimeExecutor)this.applicationContext.getBean("beanRealtimeExecutor");

        while (true) {
            try {
                Delivery delivery = beanQueueingConsumerClient.next();
                if (delivery == null) {
                    continue;
                }
                long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                beanQueueingConsumerClient.getChannel().basicAck(deliveryTag, false);
                byte[] data = delivery.getBody();
                if (data == null) {
                    System.out.println("runWithoutUsingAkka: data is null");
                    continue;
                }

                Object msg = conf.asObject(data);
                if (msg == null) {
                    System.out.println("runWithoutUsingAkka: parse msg is null");
                    continue;
                }

                AbstractMessage baseMessage =(AbstractMessage)msg;
                beanRealtimeExecutor.execute(baseMessage.getId(), baseMessage);

            } catch (Exception e) {
                log.error("runWithoutUsingAkka.ex:" + e.getMessage());
            }
        }
    }

}
