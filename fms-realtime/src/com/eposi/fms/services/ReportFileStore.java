package com.eposi.fms.services;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.logging.ElogParser;
import com.eposi.fms.rest.client.RWsClient;
import com.eposi.fms.track.FmsProto;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class ReportFileStore extends AbstractBean {
	private static final long serialVersionUID = 6842538627617988085L;
    private static Logger log = Logger.getLogger(ReportFileStore.class.getName());
    public static final String  UNKNOW       = "UNKNOWN";
    private RWsClient beanRWsClient;

    public void logWayPointBatch(String konexyId, FmsProto.WayPointBatch wayPointBatch) {

        if (wayPointBatch == null) {
            return;
        }

        if(konexyId==null){
            return;
        }

        if (wayPointBatch.getPointList().size() <= 0){
            return;
        }

        int unixTime = wayPointBatch.getPointList().get(0).getDatetime();

        // validate time
        int unixTimeNow = (int) (System.currentTimeMillis() / 1000L);

        // store last 30 days only
        if ((unixTime < unixTimeNow - 2592000) || (unixTime > unixTimeNow)) {
            return;
        }

        try {
            StringBuffer sb = new StringBuffer("/log/");
            sb.append(konexyId).append("/").append(unixTime);
            String json = ElogParser.getJSONWayPointBatch(wayPointBatch);
            int loop =0;
            while (true) {
                loop++;
                String respone = beanRWsClient.postObject(sb.toString(), json);
                if(respone!=null) {
                    JSONObject jsonObj = new JSONObject(respone);
                    String status = (String) jsonObj.get("status");
                    if (status.equals("success")||(loop>2)) {
                        return;
                    }
                }else {
                    if(loop>2){
                        return;
                    }
                    Thread.sleep(10);
                }
            }
        }catch (Exception ex){
            log.error("logWayPointBatch:"+ex.getMessage());
        }
    }

    public  void logWayPoint(String konexyId, FmsProto.WayPoint wayPoint) {

        if (wayPoint == null) {
            return;
        }

        if(konexyId==null){
            return;
        }

        if (wayPoint == null) {
            return;
        }

        int unixTime = wayPoint.getDatetime();
        int unixTimeNow = (int) (System.currentTimeMillis() / 1000L);
        if ((unixTime < unixTimeNow - 2592000) || (unixTime > unixTimeNow)) {
            return;
        }

        try {
            StringBuffer sb = new StringBuffer("/log/");
            sb.append(konexyId).append("/").append(unixTime);
            String json = ElogParser.getJSONWayPoint(wayPoint);
            int loop =0;
            while (true) {
                loop++;
                String respone = beanRWsClient.postObject(sb.toString(), json);
                if(respone!=null) {
                    JSONObject jsonObj = new JSONObject(respone);
                    String status = (String) jsonObj.get("status");
                    if (status.equals("success")||(loop>2)) {
                        return;
                    }
                }else {
                    if(loop>2){
                        return;
                    }
                    Thread.sleep(10);
                }
            }
        }catch (Exception  ex){
            log.error("logWayPoint:"+ex.getMessage());
        }
    }

    public  void  init(){
        beanRWsClient = (RWsClient)getBean("beanRWsClient");
    }

}
