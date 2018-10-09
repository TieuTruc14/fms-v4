package com.eposi.fms.persitence;

import com.eposi.common.util.AbstractBean;
import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.ReportDefine;
import com.eposi.fms.logging.ElogParser;
import com.eposi.fms.model.*;
import com.eposi.fms.reporting.atgt.BatchXmit;
import com.eposi.fms.reporting.atgt.VehicleDailySummary;
import com.eposi.fms.reporting.company.SummaryProvinceAction;
import com.eposi.fms.services.RWsClient;
import com.eposi.fms.reporting.company.SummaryXmitVehicleAction;
import com.eposi.fms.reporting.company.SummaryXmitNationAction;
import com.eposi.fms.web.model.*;
import com.eposi.fms.web.model.Point;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.time.DateUtils;
import org.nustaq.serialization.FSTConfiguration;

import java.util.*;

public class ReportReader extends AbstractBean {
	private static final long serialVersionUID = 7349211370549940545L;
    private static ObjectMapper objectMapper = new ObjectMapper();

    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
    private String quorum;
    private RWsClient beanRWsClient;

    public String newKonexyId(String description){
        int count =0;
        String response =null;
        try {
            while (true) {
                count++;
                response = beanRWsClient.postObject("/thing", description);
                if(response!=null) {
                    JsonNode node = objectMapper.readValue(response, JsonNode.class);
                    JsonNode statusNode = node.get("status");
                    if(statusNode!=null) {
                        String status = statusNode.asText();
                        if (status.equals("success")) {
                            JsonNode dataNode = node.get("data");
                            if(dataNode!=null) {
                                JsonNode thingIdNode = dataNode.get("id");
                                if(thingIdNode!=null) {
                                    String thingId = thingIdNode.asText();
                                    return thingId;
                                }
                            }
                        }
                    }
                }

                if(count>3){
                    return null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void saveActivity(String konexyId, Activity activity) {
        if (activity == null) {
            return;
        }

        if (konexyId == null) {
            return;
        }

        Date date = activity.getDate();
        if (date == null) {
            return;
        }

        int unixTime     = (int) (System.currentTimeMillis()/1000L);
        StringBuffer sb = new StringBuffer("/log/");
        sb.append(konexyId).append("/").append(unixTime);
        try {
            String jsonXmit = ElogParser.getJSONActivity(activity);
            int count =0;
            while (true && count<2){
                count++;
                String response = beanRWsClient.postObject(sb.toString(), jsonXmit);
                if(response!=null) {
                    JsonNode node = objectMapper.readValue(response, JsonNode.class);
                    //Json paser
                    if(node!=null) {
                        JsonNode statusNode = node.get("status");
                        if(statusNode!=null) {
                            String status = statusNode.asText();
                            if (status.equals("success")) {
                                return;
                            }
                            if (count > 3) {
                                return;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Activity> searchActivity(String konexyId, Date dateStart, Date dateEnd) {
        if(konexyId==null){
            return null;
        }

        List<Activity> items = new ArrayList<Activity>();
        try {

            if(dateStart==null){
                dateStart = FormatUtil.parseDate("2015-06-01","yyyy-MM-dd");
            }

            if(dateEnd ==null){
                dateEnd = new Date();
            }

            try {
                ////////////////////////////////
                //Build path
                String path ="/log/"+konexyId+"/range?from="+(dateStart.getTime()/1000L)+"&to="+(dateEnd.getTime()/1000L);

                //Get Json from Konexy
                String response =null;
                JsonNode node = null;
                int count =0;
                while (true) {
                    response = beanRWsClient.getObject(path);
                    if(response!=null) {
                        node = objectMapper.readValue(response, JsonNode.class);
                        if(node!=null) {
                            JsonNode statusNode = node.get("status");
                            if(statusNode!=null) {
                                String status = statusNode.asText();
                                if (status.equals("success")) {
                                    break;
                                }
                                if (count > 3) {
                                    break;
                                }
                            }
                        }
                    }
                    count++;
                }

                //Json paser
                if(node!=null) {
                    JsonNode dataNode = node.get("data");
                    if(dataNode!=null) {
                        Iterator<JsonNode> iterator = dataNode.iterator();
                        JsonNode activityNode = null;
                        Activity activity = null;
                        while (iterator.hasNext()) {
                            activityNode = iterator.next();
                            activity = new Activity();
                            if (activityNode.has("actionName")) {
                                activity.setActionName(activityNode.get("actionName").asText());
                            }
                            if (activityNode.has("actorName")) {
                                activity.setActorName(activityNode.get("actorName").asText());
                            }

                            if (activityNode.has("date")) {
                                activity.setDate(new Date(activityNode.get("date").asInt() * 1000L));
                            }
                            if (activityNode.has("context")) {
                                activity.setContext(activityNode.get("context").asText());
                            }
                            if (activityNode.has("icon")) {
                                activity.setIcon(activityNode.get("icon").asText());
                            }

                            if (activityNode.has("objectId")) {
                                activity.setObjectId(activityNode.get("objectId").asText());
                            }

                            if (activityNode.has("objectName")) {
                                activity.setObjectName(activityNode.get("objectName").asText());
                            }
                            if (activityNode.has("iObjectName")) {
                                activity.setIndirectObjectName(activityNode.get("iObjectName").asText());
                            }
                            if (activityNode.has("passive")) {
                                activity.setPassive(activityNode.get("passive").asBoolean());
                            }

                            items.add(activity);
                        }
                    }
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    //-----------------------------BEGIN XMIT ---------------------------------------------------------
    public SummaryXmitNationAction.CompanyXmitSummary getCompanyXmitSummary(String companyId, Date date) {
        if (companyId == null) { return null; }

        try {
            companyId = FormatUtil.removeSpecialCharacters(companyId);
            byte[] companyBytes = companyId.getBytes();
            if (companyBytes.length > 15) {
                return null;
            }

        }  catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public SummaryXmitVehicleAction.VehicleXmitSummary getVehicleXmitSummary(String companyId, String vehicleId, Date date) {
        if (vehicleId == null) { return null; }

        try {
            vehicleId = FormatUtil.removeSpecialCharacters(vehicleId);
            byte[] vehicleBytes = vehicleId.getBytes();
            if (vehicleBytes.length > 15) {
                return null;
            }

        }  catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public SummaryProvinceAction.CompanySummary getCompanySummary(String companyId, Date date) {
        if (companyId == null) { return null; }

        try {
            companyId = FormatUtil.removeSpecialCharacters(companyId);

            byte[] vehicleBytes = companyId.getBytes();
            if (vehicleBytes.length > 15) { return null; }


        }  catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<BatchXmit> getBatchXmit(Batch batch, Date date) {
        if (batch == null) { return null; }
        String konexyId = batch.getKonexyId();
        if (konexyId == null) { return null; }
        if (date == null) { return null; }

        try {
            date = DateUtils.truncate(date, Calendar.DATE);
            //Build path
            String path ="/log/"+konexyId+"/range?from="+(date.getTime()/1000L)+"&to="+(date.getTime()/1000L);
            JsonNode jsonNode = getJsonNode(path);
            if(jsonNode!=null) {
                List<BatchXmit> lstBatchXmit = new ArrayList<>();

                Iterator<JsonNode> iterator = jsonNode.iterator();
                JsonNode xmitAggregationNode = null;
                BatchXmit item = null;
                while (iterator.hasNext()) {
                    xmitAggregationNode = iterator.next();
                    item = new BatchXmit();
                    item.setName(batch.getName());
                    item.setDeviceCount(batch.getDeviceCount());
                    item.setDate(date);

                    if (xmitAggregationNode.has(ReportDefine.DATA_NO_SIGNAL_COUNT)) {
                        item.setDataCount(xmitAggregationNode.get(ReportDefine.DATA_NO_SIGNAL_COUNT).asInt());
                    }
                    if (xmitAggregationNode.has(ReportDefine.DATA_NO_SIGNAL_DURATION)) {
                        item.setDataDuration(xmitAggregationNode.get(ReportDefine.DATA_NO_SIGNAL_DURATION).asInt());
                    }
                    if (xmitAggregationNode.has(ReportDefine.GPS_NO_SIGNAL_COUNT)) {
                        item.setGpsCount(xmitAggregationNode.get(ReportDefine.GPS_NO_SIGNAL_COUNT).asInt());
                    }
                    if (xmitAggregationNode.has(ReportDefine.GPS_NO_SIGNAL_DURATION)) {
                        item.setGpsDuration(xmitAggregationNode.get(ReportDefine.GPS_NO_SIGNAL_DURATION).asInt());
                    }

                    lstBatchXmit.add(item);
                }
                return lstBatchXmit;
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Report  newReportVehicleInKonexy(String vehicleId){
        Report report = new Report();
        report.setId(vehicleId);

        //Create konexyId for Trip
        String descriptionTrip = "{ \"name\":\"Trip\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingTrip = newKonexyId(descriptionTrip);
        if(thingTrip!=null) {
            report.setTrip(thingTrip);
        }

        //Create konexyId for Stop
        String descriptionStop = "{ \"name\":\"Stop\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingStop = newKonexyId(descriptionStop);
        if(thingStop!=null) {
            report.setStop(thingStop);
        }

        //Create konexyId for Os
        String descriptionOs = "{ \"name\":\"Os\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingOs = newKonexyId(descriptionOs);
        if(thingOs!=null) {
            report.setOs(thingOs);
        }

        //Create konexyId for Over10h
        String descriptionOver10h = "{ \"name\":\"Over10h\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingOver10h = newKonexyId(descriptionOver10h);
        if(thingOver10h!=null) {
            report.setOver10h(thingOver10h);
        }

        //Create konexyId for Over4h
        String descriptionOver4h = "{ \"name\":\"Over4h\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingOver4h = newKonexyId(descriptionOver4h);
        if(thingOver4h!=null) {
            report.setOver4h(thingOver4h);
        }

        //Create konexyId for Log
        String descriptionLog = "{ \"name\":\"Log\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingLog = newKonexyId(descriptionLog);
        if(thingLog!=null) {
            report.setLog(thingLog);
        }

        //Create konexyId for Xmit
        String descriptionXmit = "{ \"name\":\"Xmit\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingXmit = newKonexyId(descriptionXmit);
        if(thingXmit!=null) {
            report.setXmit(thingXmit);
        }

        //Create konexyId for Geo POI
        String descriptionPOI = "{ \"name\":\"Poi\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingPoi = newKonexyId(descriptionPOI);
        if(thingPoi!=null) {
            report.setGeoPoi(thingPoi);
        }

        //Create konexyId for Aggregation
        String descriptionAggregation = "{ \"name\":\"Aggregation\",\"Vehicle\":\""+vehicleId+"\"}";
        String thingAggregation = newKonexyId(descriptionAggregation);
        if(thingAggregation!=null) {
            report.setAggregation(thingAggregation);
        }

        return report;
    }

    public VehicleDailySummary getOverSpeedVehicle(String companyId, String vehicle, Date date) {
        vehicle = FormatUtil.removeSpecialCharacters(vehicle);

        byte[] vehicleBytes = vehicle.getBytes();
        if (vehicleBytes.length > 15) { return null; }

        try {

        } catch(Exception ex) {
            // do nothing
        }

        return null;
    }

    public List<TrackItem> loadTrackItem(String konexyId, Date dateStart, Date dateEnd) {
        if (konexyId == null) {
            return null;
        }

        List<TrackItem> items = new ArrayList<TrackItem>();
        try {
            //Build path
            String path ="/log/"+konexyId+"/range?from="+(dateStart.getTime()/1000L)+"&to="+(dateEnd.getTime()/1000L);
            //Get Json from Konexy
            String response = beanRWsClient.getObject(path);
            if(response!=null) {
                JsonNode node = objectMapper.readValue(response, JsonNode.class);
                //Json paser
                if(node!=null) {
                    JsonNode statusNode = node.get("status");
                    if(statusNode!=null) {
                        String status = statusNode.asText();
                        if (status.equals("success")) {
                            JsonNode dataNode = node.get("data");
                            Iterator<JsonNode> iterator = dataNode.iterator();
                            JsonNode wp = null;
                            TrackItem trackItem = null;
                            while (iterator.hasNext()) {
                                wp = iterator.next();
                                if (wp.has("Points")) {
                                    JsonNode PointsNode = wp.get("Points");
                                    Iterator<JsonNode> points = PointsNode.iterator();
                                    JsonNode pointNode = null;
                                    while (points.hasNext()) {
                                        trackItem = new TrackItem();
                                        pointNode = points.next();
                                        if (pointNode.has("t")) {
                                            int unixTime = pointNode.get("t").asInt();
                                            trackItem.setTime(new Date(unixTime * 1000L));
                                        }
                                        if (pointNode.has("x")) {
                                            double x = pointNode.get("x").asDouble();
                                            trackItem.setX(x);
                                        }
                                        if (pointNode.has("y")) {
                                            double y = pointNode.get("y").asDouble();
                                            trackItem.setY(y);
                                        }
                                        if (pointNode.has("s")) {
                                            int speed = pointNode.get("s").asInt();
                                            trackItem.setSpeed(speed);
                                        }
                                        items.add(trackItem);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<ItemBatch> loadItemBatch(String companyId, String vehicle, Date dateStart, Date dateEnd) {
        List<ItemBatch> items = new ArrayList<ItemBatch>();

        try {
            byte[] vehicleBytes = vehicle.getBytes();
            if (vehicleBytes.length > 15) { return null; }
            //Build path
            String path ="/log/tree/"+companyId+"/vehicle/"+vehicle+"/log/range?from="+(dateStart.getTime()/1000L)+"&to="+(dateEnd.getTime()/1000L);
            String response = beanRWsClient.getObject(path);
            if(response!=null) {
                JsonNode node = objectMapper.readValue(response, JsonNode.class);
                if(node!=null) {
                    JsonNode statusNode = node.get("status");
                    if(statusNode!=null) {
                        String status = statusNode.asText();
                        if (status.equals("success")) {
                            JsonNode dataNode = node.get("data");
                            Iterator<JsonNode> iterator = dataNode.iterator();
                            JsonNode wp = null;
                            ItemBatch itemBatch = null;
                            while (iterator.hasNext()) {
                                itemBatch = new ItemBatch();
                                wp = iterator.next();
                                if (wp.has("m")) {
                                    String device = wp.get("m").asText();
                                    itemBatch.setDevive(device);
                                }

                                if (wp.has("k")) {
                                    String driverKey = wp.get("k").asText();
                                    itemBatch.setDriver(driverKey);
                                }
                                if (wp.has("io")) {
                                    String io = wp.get("io").asText();
                                    itemBatch.setI0(io.charAt(0));
                                    itemBatch.setI1(io.charAt(1));
                                    itemBatch.setI2(io.charAt(2));
                                    itemBatch.setI3(io.charAt(3));
                                    itemBatch.setI4(io.charAt(4));
                                    itemBatch.setI5(io.charAt(5));
                                }
                                if (wp.has("a0")) {
                                    int a0 = wp.get("a0").asInt();
                                    itemBatch.setA0(a0);
                                }
                                if (wp.has("a1")) {
                                    int a1 = wp.get("a1").asInt();
                                    itemBatch.setA1(a1);
                                }

                                if (wp.has("unixtime")) {
                                    int unixtime = wp.get("unixtime").asInt();
                                    itemBatch.setUnixTime(unixtime);
                                }

                                if (wp.has("Points")) {
                                    JsonNode PointsNode = wp.get("Points");
                                    Iterator<JsonNode> points = PointsNode.iterator();
                                    JsonNode pointNode = null;
                                    com.eposi.fms.web.model.Point point = null;
                                    while (points.hasNext()) {
                                        point = new Point();
                                        pointNode = points.next();
                                        if (pointNode.has("t")) {
                                            int unixTime = pointNode.get("t").asInt();
                                            point.setUnixTime(unixTime);
                                        }
                                        if (pointNode.has("x")) {
                                            double x = pointNode.get("x").asDouble();
                                            point.setX(x);
                                        }
                                        if (pointNode.has("y")) {
                                            double y = pointNode.get("y").asDouble();
                                            point.setY(y);
                                        }
                                        if (pointNode.has("s")) {
                                            int speed = pointNode.get("s").asInt();
                                            point.setSpeed(speed);
                                        }
                                        itemBatch.getPoints().add(point);
                                    }
                                }
                                items.add(itemBatch);
                            }
                        }
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    private  String getThingId(String responedescriptionStop){
        try {

            JsonNode node = objectMapper.readValue(responedescriptionStop, JsonNode.class);
            JsonNode statusNode = node.get("status");
            String status = statusNode.asText();
            if(status.equals("success")){
                JsonNode dataNode = node.get("data");
                JsonNode thingIdNode = dataNode.get("id");
                String thingId = thingIdNode.asText();
                return thingId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private JsonNode getJsonNode(String path){
        int loop =0;
        String respone =null;
        while (true) {
            loop++;
            try {
                respone = beanRWsClient.getObject(path);
                if(respone!=null) {
                    JsonNode node = objectMapper.readValue(respone, JsonNode.class);
                    //Json paser
                    JsonNode statusNode = node.get("status");
                    String status = statusNode.asText();
                    if (status.equals("success")) {
                        return node.get("data");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(loop > 3){
                return null;
            }
        }
    }


    public void init() {
        beanRWsClient = (RWsClient)getBean("beanRWsClient");
    }

    public String getQuorum() {
        return quorum;
    }

    public void setQuorum(String quorum) {
        this.quorum = quorum;
    }



}
