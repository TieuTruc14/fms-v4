package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.msg.TrackMessage;
import com.eposi.fms.services.QueueingConsumerClient;
import com.eposi.fms.track.FmsProto;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import org.apache.log4j.Logger;

import java.util.concurrent.*;


public class Server extends AbstractBean {
	private static final long serialVersionUID = -210861315150811372L;
    private static Logger log = Logger.getLogger(Server.class.getName());

    private QueueingConsumerClient beanQueueingConsumerClient;
    private LogParserService beanLogParserService;
    private RealtimeExecutor beanRealtimeExecutor;

	public void run() {
        System.out.println("runWithoutUsingAkka:");
        runWithoutUsingAkka();
	}

    private void runWithoutUsingAkka() {
        beanLogParserService        = (LogParserService)applicationContext.getBean("beanLogParserService");
        beanQueueingConsumerClient  = (QueueingConsumerClient) applicationContext.getBean("beanQueueingConsumerClient");
        beanRealtimeExecutor        = (RealtimeExecutor)this.applicationContext.getBean("beanRealtimeExecutor");

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
                    continue;
                }

                FmsProto.BaseMessage msg = null;
                try {
                    msg = beanLogParserService.Parser(data);
                } catch(Exception pbE) {
                    continue;
                }

                if (msg == null) {
                    continue;
                }

                String strVehicleId = null;
                String strDeviceId = null;
                TrackMessage trackMessage =null;
                switch (msg.getMsgType()) {
                    case WayPoint:
                        FmsProto.WayPoint wayPoint = msg.getExtension(FmsProto.WayPoint.msg);
                        strVehicleId = wayPoint.getVehicle();
                        strDeviceId  = wayPoint.getDevice();
                        trackMessage = new TrackMessage(TrackMessage.COMMAND_TRACKING_WAYPOINT);
                        trackMessage.setObj(wayPoint);
                        beanRealtimeExecutor.execute(strVehicleId, strDeviceId, trackMessage);
                        break;

                    case WayPointBatch:
                        FmsProto.WayPointBatch wayPointBatch = msg.getExtension(FmsProto.WayPointBatch.msg);
                        strVehicleId = wayPointBatch.getVehicle();
                        strDeviceId  = wayPointBatch.getDevice();
                        trackMessage = new TrackMessage(TrackMessage.COMMAND_TRACKING_WAYPOINTBATCH);
                        trackMessage.setObj(wayPointBatch);
                        beanRealtimeExecutor.execute(strVehicleId, strDeviceId, trackMessage);
                        break;

                    default:
                        System.out.println("getMsgType:"+msg.getMsgType().toString());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("runWithoutUsingAkka.ex:" + e.getMessage());
            }
        }
    }

}
