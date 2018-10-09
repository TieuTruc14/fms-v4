package com.eposi.fms.services;

import com.eposi.common.util.AbstractBean;
import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.FmsUtil;
import com.eposi.fms.common.ReportDefine;
import com.eposi.fms.logging.ElogParser;
import com.eposi.fms.model.*;
import com.eposi.fms.rest.client.RWsClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.*;

public class ReportFileStore extends AbstractBean {
	private static final long serialVersionUID = 6842538627617988085L;
    private static Logger log = Logger.getLogger(ReportFileStore.class.getName());
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static final String  UNKNOW       = "UNKNOWN";
    private RWsClient beanRWsClient;

    public void updateCompanyAggregationWindow(CompanyAggregationWindow companyAggregationWindow) throws Exception {
        if (companyAggregationWindow == null) {
            return;
        }

        String konexyId = companyAggregationWindow.getCompanyAggregationId();
        if (konexyId == null) {
            log.error("updateCompanyAggregationWindow: konexyId is null");
            return;
        }

        Date date = companyAggregationWindow.getDate();
        if (date == null) {
            throw new NullPointerException("date is null!");
        }
        date = DateUtils.truncate(date, Calendar.DATE);

        CompanyAggregationWindow item = this.getCompanyAggregationWindow(konexyId, date);
        if (item == null) {
            item = new CompanyAggregationWindow();
            item.setCompanyAggregationId(konexyId);
            item.setDate(date);
        }

        // trip
        companyAggregationWindow.setTripKm(companyAggregationWindow.getTripKm() + item.getTripKm());
        companyAggregationWindow.setTripCount(companyAggregationWindow.getTripCount() + item.getTripCount());

        // stop
        companyAggregationWindow.setStopDuration(companyAggregationWindow.getStopDuration() + item.getStopDuration());
        companyAggregationWindow.setStopCount(companyAggregationWindow.getStopCount() + item.getStopCount());

        companyAggregationWindow.setOverspeedCount(companyAggregationWindow.getOverspeedCount() + item.getOverspeedCount());
        companyAggregationWindow.setOverspeedDuration(companyAggregationWindow.getOverspeedDuration() + item.getOverspeedDuration());
        companyAggregationWindow.setOverspeedMax((companyAggregationWindow.getOverspeedMax() > item.getOverspeedMax()) ? companyAggregationWindow.getOverspeedMax() : item.getOverspeedMax());
        companyAggregationWindow.setOverspeedKm(companyAggregationWindow.getOverspeedKm() + item.getOverspeedKm());

        int[] overspeedRange1 = companyAggregationWindow.getOverspeedRange();
        int[] overspeedRange2 = item.getOverspeedRange();
        if (overspeedRange2 != null) {
            for (int i=0; i<overspeedRange1.length; i++) {
                overspeedRange1[i] += overspeedRange2[i];
            }
        }
        companyAggregationWindow.setOverspeedRange(overspeedRange1);

        companyAggregationWindow.setOvertime4hCount(companyAggregationWindow.getOvertime4hCount() + item.getOvertime4hCount());
        companyAggregationWindow.setOvertime10Count(companyAggregationWindow.getOvertime10Count() + item.getOvertime10Count());

        companyAggregationWindow.setMsgCount(companyAggregationWindow.getMsgCount() + item.getMsgCount());
        companyAggregationWindow.setGpsNoSignalCount(companyAggregationWindow.getGpsNoSignalCount() + item.getGpsNoSignalCount());
        companyAggregationWindow.setGpsNoSignalDuration(companyAggregationWindow.getGpsNoSignalDuration()+item.getGpsNoSignalDuration());
        companyAggregationWindow.setDataNoSignalCount(companyAggregationWindow.getDataNoSignalCount()+item.getDataNoSignalCount());
        companyAggregationWindow.setDataNoSignalDuration(companyAggregationWindow.getDataNoSignalDuration()+item.getDataNoSignalDuration());

        this.saveCompanyAggregationWindow(companyAggregationWindow);
    }

    public void updateVehicleAggregationWindow(VehicleAggregationWindow vehicleAggregationWindow) throws Exception {
        if (vehicleAggregationWindow == null) {
            return;
        }

        String konexyId = vehicleAggregationWindow.getVehicleAggregationId();
        if (konexyId == null) {
            log.error("updateVehicleAggregationWindow: konexyId is null");
            return;
        }

        Date date = vehicleAggregationWindow.getDate();
        if (date == null) {
            throw new NullPointerException("date is null!");
        }
        date = DateUtils.truncate(date, Calendar.DATE);

        VehicleAggregationWindow item = this.getVehicleAggregationWindow(konexyId, date);
        if (item == null) {
            item = new VehicleAggregationWindow();
            item.setId(konexyId);
            item.setVehicleAggregationId(konexyId);
            item.setDate(date);
        }

        // trip
        vehicleAggregationWindow.setTripKm(vehicleAggregationWindow.getTripKm() + item.getTripKm());
        vehicleAggregationWindow.setTripCount(vehicleAggregationWindow.getTripCount() + item.getTripCount());

        // stop
        vehicleAggregationWindow.setStopDuration(vehicleAggregationWindow.getStopDuration() + item.getStopDuration());
        vehicleAggregationWindow.setStopCount(vehicleAggregationWindow.getStopCount() + item.getStopCount());

        vehicleAggregationWindow.setOverspeedCount(vehicleAggregationWindow.getOverspeedCount() + item.getOverspeedCount());
        vehicleAggregationWindow.setOverspeedDuration(vehicleAggregationWindow.getOverspeedDuration() + item.getOverspeedDuration());
        vehicleAggregationWindow.setOverspeedMax((vehicleAggregationWindow.getOverspeedMax() > item.getOverspeedMax()) ? vehicleAggregationWindow.getOverspeedMax() : item.getOverspeedMax());
        vehicleAggregationWindow.setOverspeedKm(vehicleAggregationWindow.getOverspeedKm() + item.getOverspeedKm());

        int[] overspeedRange1 = vehicleAggregationWindow.getOverspeedRange();
        int[] overspeedRange2 = item.getOverspeedRange();
        if (overspeedRange2 != null) {
            for (int i=0; i<overspeedRange1.length; i++) {
                overspeedRange1[i] += overspeedRange2[i];
            }
        }
        vehicleAggregationWindow.setOverspeedRange(overspeedRange1);

        int[] overspeedMsgRange1 = vehicleAggregationWindow.getOverspeedMsgRange();
        int[] overspeedMsgRange2 = item.getOverspeedMsgRange();
        if (overspeedMsgRange2 != null) {
            for (int i=0; i<overspeedMsgRange1.length; i++) {
                overspeedMsgRange1[i] += overspeedMsgRange2[i];
            }
        }
        vehicleAggregationWindow.setOverspeedMsgRange(overspeedMsgRange1);

        vehicleAggregationWindow.setOvertime4hCount(vehicleAggregationWindow.getOvertime4hCount() + item.getOvertime4hCount());
        vehicleAggregationWindow.setOvertime10Count(vehicleAggregationWindow.getOvertime10Count() + item.getOvertime10Count());

        vehicleAggregationWindow.setMsgCount(vehicleAggregationWindow.getMsgCount() + item.getMsgCount());
        vehicleAggregationWindow.setGpsNoSignalCount(vehicleAggregationWindow.getGpsNoSignalCount() + item.getGpsNoSignalCount());
        vehicleAggregationWindow.setGpsNoSignalDuration(vehicleAggregationWindow.getGpsNoSignalDuration()+item.getGpsNoSignalDuration());
        vehicleAggregationWindow.setDataNoSignalCount(vehicleAggregationWindow.getDataNoSignalCount()+item.getDataNoSignalCount());
        vehicleAggregationWindow.setDataNoSignalDuration(vehicleAggregationWindow.getDataNoSignalDuration()+item.getDataNoSignalDuration());

        this.saveVehicleAggregation(vehicleAggregationWindow);
    }

    public void updateDriverAggregationWindow(DriverAggregationWindow driverAggregationWindow) throws Exception {
        if (driverAggregationWindow == null) {
            return;
        }

        String konexyId = driverAggregationWindow.getDriverAggregationId();
        if (konexyId == null) {
            log.error("updateDriverAggregationWindow: konexyId is null");
            return;
        }

        Date date = driverAggregationWindow.getDate();
        if (date == null) {
            throw new NullPointerException("date is null!");
        }
        date = DateUtils.truncate(date, Calendar.DATE);

        DriverAggregationWindow item = this.getDriverAggregationWindow(konexyId, date);
        if (item == null) {
            item = new DriverAggregationWindow();
            item.setDriverAggregationId(konexyId);
            item.setDate(date);
        }

        // trip
        driverAggregationWindow.setTripKm(driverAggregationWindow.getTripKm() + item.getTripKm());
        driverAggregationWindow.setTripCount(driverAggregationWindow.getTripCount() + item.getTripCount());

        // stop
        driverAggregationWindow.setStopDuration(driverAggregationWindow.getStopDuration() + item.getStopDuration());
        driverAggregationWindow.setStopCount(driverAggregationWindow.getStopCount() + item.getStopCount());

        driverAggregationWindow.setOverspeedCount(driverAggregationWindow.getOverspeedCount() + item.getOverspeedCount());
        driverAggregationWindow.setOverspeedDuration(driverAggregationWindow.getOverspeedDuration() + item.getOverspeedDuration());
        driverAggregationWindow.setOverspeedMax((driverAggregationWindow.getOverspeedMax() > item.getOverspeedMax()) ? driverAggregationWindow.getOverspeedMax() : item.getOverspeedMax());
        driverAggregationWindow.setOverspeedKm(driverAggregationWindow.getOverspeedKm() + item.getOverspeedKm());

        int[] overspeedRange1 = driverAggregationWindow.getOverspeedRange();
        int[] overspeedRange2 = item.getOverspeedRange();
        if (overspeedRange2 != null) {
            for (int i=0; i<overspeedRange1.length; i++) {
                overspeedRange1[i] += overspeedRange2[i];
            }
        }
        driverAggregationWindow.setOverspeedRange(overspeedRange1);

        int[] overspeedMsgRange1 = driverAggregationWindow.getOverspeedMsgRange();
        int[] overspeedMsgRange2 = item.getOverspeedMsgRange();
        if (overspeedMsgRange2 != null) {
            for (int i=0; i<overspeedMsgRange1.length; i++) {
                overspeedMsgRange1[i] += overspeedMsgRange2[i];
            }
        }
        driverAggregationWindow.setOverspeedMsgRange(overspeedMsgRange1);

        driverAggregationWindow.setOvertime4hCount(driverAggregationWindow.getOvertime4hCount() + item.getOvertime4hCount());
        driverAggregationWindow.setOvertime10Count(driverAggregationWindow.getOvertime10Count() + item.getOvertime10Count());

        this.saveDriverAggregation(driverAggregationWindow);
    }

    public void updateXmitAggregationWindow(XmitAggregationWindow xmitAggregationWindow) throws Exception{
        if (xmitAggregationWindow == null) {
            return;
        }
        String konexyId = xmitAggregationWindow.getId();
        if (konexyId == null) {
            log.error("updateXmitAggregationWindow: konexyId is null");
            return;
        }


        Date date = xmitAggregationWindow.getDate();
        if (date == null) {
            throw new NullPointerException("date is null!");
        }
        date = DateUtils.truncate(date, Calendar.DATE);

        System.out.println("updateXmitAggregationWindow:"+xmitAggregationWindow.getId()+" "+ FormatUtil.formatDate(date,"yyyy/MM/dd"));

        XmitAggregationWindow item = this.getXmitAggregationWindow(konexyId, date);
        if (item == null) {
            item = new XmitAggregationWindow();
            item.setId(konexyId);
            item.setDate(date);
        }
        xmitAggregationWindow.setGpsCount(xmitAggregationWindow.getGpsCount() + item.getGpsCount());
        xmitAggregationWindow.setGpsDuration(xmitAggregationWindow.getGpsDuration()+item.getGpsDuration());
        xmitAggregationWindow.setDataCount(xmitAggregationWindow.getDataCount()+item.getDataCount());
        xmitAggregationWindow.setDataDuration(xmitAggregationWindow.getDataDuration()+item.getDataDuration());
        System.out.println("saveXmitAggregationWindow:"+xmitAggregationWindow.getId()+" "+xmitAggregationWindow.getDataCount()+" "+xmitAggregationWindow.getGpsCount());
        this.saveXmitAggregationWindow(xmitAggregationWindow);


    }

    private XmitAggregationWindow getXmitAggregationWindow(String konexyId, Date date) {
        if (konexyId == null) {
            return null;
        }

        if (date == null) {
            return null;
        }
        date = DateUtils.truncate(date, Calendar.DATE);

        try {
            int unixDate = (int) (date.getTime()/1000L);

            XmitAggregationWindow item = null;
            //Build path
            String path ="/log/"+konexyId+"/range?from="+(date.getTime()/1000L)+"&to="+(date.getTime()/1000L);
            //Get Json from Konexy
            JsonNode jsonNode = getJsonNode(path);
            if(jsonNode!=null) {
                Iterator<JsonNode> iterator = jsonNode.iterator();
                JsonNode xmitAggregationNode = null;
                while (iterator.hasNext()) {
                    xmitAggregationNode = iterator.next();
                    item = new XmitAggregationWindow();

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
                }
            }

            return item;
        }  catch (Exception e) {
            log.error("getCompanyAggregationWindow.ex:" + e.getMessage());
        }

        return null;
    }

    private void saveXmitAggregationWindow(XmitAggregationWindow item) throws Exception{
        if (item == null) {
            log.error("saveXmitAggregationWindow:item is null");
            return;
        }
        String konexyId = item.getId();
        if (konexyId == null) {
            log.error("saveXmitAggregationWindow:konexyId is null");
            return;
        }


        Date date = item.getDate();
        if (date == null) {
            log.error("saveXmitAggregationWindow:date is null");
            return;
        }

        date = DateUtils.truncate(date, Calendar.DATE);
        int unixDate = (int) (date.getTime()/1000L);
        StringBuffer sb = new StringBuffer("/log/");
        sb.append(konexyId).append("/").append(unixDate);

        String jsonSum = ElogParser.getJSONXmitAggregationWindow(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonSum);
    }


    private void saveCompanyAggregationWindow(CompanyAggregationWindow item) throws Exception{
        if (item == null) {
            log.error("saveCompanyAggregationWindow:item is null");
            return;
        }
        String konexyId = item.getCompanyAggregationId();
        if (konexyId == null) {
            log.error("saveCompanyAggregationWindow:konexyId is null");
            return;
        }


        Date date = item.getDate();
        if (date == null) {
            log.error("saveCompanyAggregationWindow:date is null");
            return;
        }

        date = DateUtils.truncate(date, Calendar.DATE);
        int unixDate = (int) (date.getTime()/1000L);
        StringBuffer sb = new StringBuffer("/log/");
        sb.append(konexyId).append("/").append(unixDate);

        String jsonSum = ElogParser.getJSONCompanyAggregation(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonSum);
    }

    private CompanyAggregationWindow getCompanyAggregationWindow(String konexyId, Date date) {
        if (konexyId == null) {
            return null;
        }

        if (date == null) {
            return null;
        }
        date = DateUtils.truncate(date, Calendar.DATE);
        try {
            int unixDate = (int) (date.getTime()/1000L);

            CompanyAggregationWindow item = null;
            //Build path
            String path ="/log/"+konexyId+"/range?from="+(date.getTime()/1000L)+"&to="+(date.getTime()/1000L);
            //Get Json from Konexy
            JsonNode jsonNode = getJsonNode(path);
            if(jsonNode!=null) {
                Iterator<JsonNode> iterator = jsonNode.iterator();
                JsonNode companyAggregationNode = null;
                while (iterator.hasNext()) {
                    companyAggregationNode = iterator.next();
                    item = new CompanyAggregationWindow();

                    item.setDate(date);
                    if (companyAggregationNode.has(ReportDefine.COMPANY)) {
                        item.setCompanyId(companyAggregationNode.get(ReportDefine.COMPANY).asText());
                    }
                    if (companyAggregationNode.has(ReportDefine.TRIP_COUNT)) {
                        item.setTripCount(companyAggregationNode.get(ReportDefine.TRIP_COUNT).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.TRIP_KM)) {
                        item.setTripKm(companyAggregationNode.get(ReportDefine.TRIP_KM).asDouble());
                    }
                    if (companyAggregationNode.has(ReportDefine.STOP_COUNT)) {
                        item.setStopCount(companyAggregationNode.get(ReportDefine.STOP_COUNT).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.STOP_DURATION)) {
                        item.setStopDuration(companyAggregationNode.get(ReportDefine.STOP_DURATION).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.OVER_SPEED_COUNT)) {
                        item.setOverspeedCount(companyAggregationNode.get(ReportDefine.OVER_SPEED_COUNT).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.OVER_SPEED_DURATION)) {
                        item.setOverspeedDuration(companyAggregationNode.get(ReportDefine.OVER_SPEED_DURATION).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.OVER_SPEED_MAX)) {
                        item.setOverspeedMax(companyAggregationNode.get(ReportDefine.OVER_SPEED_MAX).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.OVER_SPEED_KM)) {
                        item.setOverspeedKm(companyAggregationNode.get(ReportDefine.OVER_SPEED_KM).asDouble());
                    }
                    if (companyAggregationNode.has(ReportDefine.OVER_SPEED_MSG_RANGE)) {
                        item.setOverspeedMsgRange(FmsUtil.getArray(companyAggregationNode.get(ReportDefine.OVER_SPEED_MSG_RANGE).asText()));
                    }
                    if (companyAggregationNode.has(ReportDefine.OVER_SPEED_RANGE)) {
                        item.setOverspeedRange(FmsUtil.getArray(companyAggregationNode.get(ReportDefine.OVER_SPEED_RANGE).asText()));
                    }
                    if (companyAggregationNode.has(ReportDefine.OVER_4H_COUNT)) {
                        item.setOvertime4hCount(companyAggregationNode.get(ReportDefine.OVER_4H_COUNT).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.OVER_10H_COUNT)) {
                        item.setOvertime10Count(companyAggregationNode.get(ReportDefine.OVER_10H_COUNT).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.MSG_COUNT)) {
                        item.setMsgCount(companyAggregationNode.get(ReportDefine.MSG_COUNT).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.DATA_NO_SIGNAL_COUNT)) {
                        item.setDataNoSignalCount(companyAggregationNode.get(ReportDefine.DATA_NO_SIGNAL_COUNT).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.DATA_NO_SIGNAL_DURATION)) {
                        item.setDataNoSignalDuration(companyAggregationNode.get(ReportDefine.DATA_NO_SIGNAL_DURATION).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.GPS_NO_SIGNAL_COUNT)) {
                        item.setGpsNoSignalCount(companyAggregationNode.get(ReportDefine.GPS_NO_SIGNAL_COUNT).asInt());
                    }
                    if (companyAggregationNode.has(ReportDefine.GPS_NO_SIGNAL_DURATION)) {
                        item.setGpsNoSignalDuration(companyAggregationNode.get(ReportDefine.GPS_NO_SIGNAL_DURATION).asInt());
                    }
                }
            }

            return item;
        }  catch (Exception e) {
            log.error("getCompanyAggregationWindow.ex:" + e.getMessage());
        }

        return null;
    }

    private void saveVehicleAggregation(VehicleAggregationWindow item) throws  Exception {
        if (item == null) {
            log.error("saveVehicleAggregation.ex:item is null");
            return;
        }

        String konexyId = item.getVehicleAggregationId();
        if (konexyId == null) {
            log.error("saveVehicleAggregation.ex1:konexyId is null");
            return;
        }

        Date date = item.getDate();
        if (date == null) {
            log.error("saveVehicleAggregation.ex:date is null");
            return;
        }

        date = DateUtils.truncate(date, Calendar.DATE);

        //Build path
        int unixTime     = (int) (date.getTime()/1000L);
        StringBuffer sb = new StringBuffer();
        sb.append("/log/").append(konexyId).append("/").append(unixTime);

        //parser Object VehicleSum to Json String
        String jsonSum = ElogParser.getJSONVehicleAggregation(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonSum);

    }

    private VehicleAggregationWindow getVehicleAggregationWindow(String konexyId, Date date) {
        if (konexyId == null) {
            return null;
        }

        if (date == null) {
            return null;
        }

        date = DateUtils.truncate(date, Calendar.DATE);

        try {

            int unixDate = (int) (date.getTime()/1000L);
            VehicleAggregationWindow item = null;
            //Build path
            String path ="/log/"+konexyId+"/range?from="+(date.getTime()/1000L)+"&to="+(date.getTime()/1000L);

            //Get Json from Konexy
            JsonNode jsonNode = getJsonNode(path);
            //Json paser
            if(jsonNode!=null) {
                Iterator<JsonNode> iterator = jsonNode.iterator();
                JsonNode vehicleAggregationNode=null;
                while (iterator.hasNext()){
                    vehicleAggregationNode =iterator.next();
                    item = new VehicleAggregationWindow();
                    item.setDate(date);
                    if(vehicleAggregationNode.has(ReportDefine.VEHICLE)){
                        item.setVehicle(vehicleAggregationNode.get(ReportDefine.VEHICLE).asText());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.COMPANY)){
                        item.setCompanyId(vehicleAggregationNode.get(ReportDefine.COMPANY).asText());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.TRIP_COUNT)){
                        item.setTripCount(vehicleAggregationNode.get(ReportDefine.TRIP_COUNT).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.TRIP_KM)){
                        item.setTripKm(vehicleAggregationNode.get(ReportDefine.TRIP_KM).asDouble());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.STOP_COUNT)){
                        item.setStopCount(vehicleAggregationNode.get(ReportDefine.STOP_COUNT).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.STOP_DURATION)){
                        item.setStopDuration(vehicleAggregationNode.get(ReportDefine.STOP_DURATION).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.OVER_SPEED_COUNT)){
                        item.setOverspeedCount(vehicleAggregationNode.get(ReportDefine.OVER_SPEED_COUNT).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.OVER_SPEED_DURATION)){
                        item.setOverspeedDuration(vehicleAggregationNode.get(ReportDefine.OVER_SPEED_DURATION).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.OVER_SPEED_MAX)){
                        item.setOverspeedMax(vehicleAggregationNode.get(ReportDefine.OVER_SPEED_MAX).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.OVER_SPEED_KM)){
                        item.setOverspeedKm(vehicleAggregationNode.get(ReportDefine.OVER_SPEED_KM).asDouble());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.OVER_SPEED_MSG_RANGE)){
                        item.setOverspeedMsgRange(FmsUtil.getArray(vehicleAggregationNode.get(ReportDefine.OVER_SPEED_MSG_RANGE).asText()));
                    }
                    if(vehicleAggregationNode.has(ReportDefine.OVER_SPEED_RANGE)){
                        item.setOverspeedRange(FmsUtil.getArray(vehicleAggregationNode.get(ReportDefine.OVER_SPEED_RANGE).asText()));
                    }
                    if(vehicleAggregationNode.has(ReportDefine.OVER_4H_COUNT)){
                        item.setOvertime4hCount(vehicleAggregationNode.get(ReportDefine.OVER_4H_COUNT).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.OVER_10H_COUNT)){
                        item.setOvertime10Count(vehicleAggregationNode.get(ReportDefine.OVER_10H_COUNT).asInt());
                    }

                    if(vehicleAggregationNode.has(ReportDefine.DATA_NO_SIGNAL_DURATION)){
                        item.setDataNoSignalDuration(vehicleAggregationNode.get(ReportDefine.DATA_NO_SIGNAL_DURATION).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.DATA_NO_SIGNAL_COUNT)){
                        item.setDataNoSignalCount(vehicleAggregationNode.get(ReportDefine.DATA_NO_SIGNAL_COUNT).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.GPS_NO_SIGNAL_DURATION)){
                        item.setGpsNoSignalDuration(vehicleAggregationNode.get(ReportDefine.GPS_NO_SIGNAL_DURATION).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.GPS_NO_SIGNAL_COUNT)){
                        item.setGpsNoSignalCount(vehicleAggregationNode.get(ReportDefine.GPS_NO_SIGNAL_COUNT).asInt());
                    }
                    if(vehicleAggregationNode.has(ReportDefine.MSG_COUNT)){
                        item.setMsgCount(vehicleAggregationNode.get(ReportDefine.MSG_COUNT).asInt());
                    }
                }
            }

            return item;
        }  catch (Exception e) {
            log.error("Exception", e);
        }

        return null;
    }

    public void saveDriverAggregation(DriverAggregationWindow item) throws Exception {
        if (item == null) {
            return;
        }

        String konexyId = item.getDriverAggregationId();
        if (konexyId == null) {
            System.out.println("saveDriverAggregation: konexyId is null");
            return;
        }

        Date date = item.getDate();
        if (date == null) {
            System.out.println("saveDriverAggregation: date is null");
            return;
        }

        date = DateUtils.truncate(date, Calendar.DATE);

        int unixTime = (int) (date.getTime()/1000L);
        StringBuffer sb = new StringBuffer();
        sb.append("/log/").append(konexyId).append("/").append(unixTime);

        String jsonDriverAggregation= ElogParser.getJSONDriverAggregation(item);
        saveReport(sb.toString(), jsonDriverAggregation);
    }

    public DriverAggregationWindow getDriverAggregationWindow(String konexyId, Date date) {
        if (konexyId == null) {
            return null;
        }

        if (date == null) {
            return null;
        }
        date = DateUtils.truncate(date, Calendar.DATE);

        try {
            int unixDate = (int) (date.getTime()/1000L);
            DriverAggregationWindow item = null;
            //Build path
            String path ="/log/"+konexyId+"/range?from="+(date.getTime()/1000L)+"&to="+(date.getTime()/1000L);
            //Get Json from Konexy
            JsonNode jsonNode = getJsonNode(path);
            //Json paser
            if(jsonNode!=null) {
                Iterator<JsonNode> iterator = jsonNode.iterator();
                JsonNode driverAggregationNode=null;
                while (iterator.hasNext()){
                    driverAggregationNode =iterator.next();
                    item = new DriverAggregationWindow();
                    item.setDate(date);

                    if(driverAggregationNode.has(ReportDefine.ID)){
                        item.setId(driverAggregationNode.get(ReportDefine.ID).asText());
                    }
                    if(driverAggregationNode.has(ReportDefine.LIICENCE_KEY)) {
                        item.setLicenceKey(driverAggregationNode.get(ReportDefine.LIICENCE_KEY).asText());
                    }
                    if(driverAggregationNode.has(ReportDefine.DRIVER_NAME)) {
                        item.setDriverName(driverAggregationNode.get(ReportDefine.DRIVER_NAME).asText());
                    }
                    if(driverAggregationNode.has(ReportDefine.COMPANY)){
                        item.setCompanyId(driverAggregationNode.get(ReportDefine.COMPANY).asText());
                    }
                    if(driverAggregationNode.has(ReportDefine.TRIP_COUNT)){
                        item.setTripCount(driverAggregationNode.get(ReportDefine.TRIP_COUNT).asInt());
                    }
                    if(driverAggregationNode.has(ReportDefine.TRIP_KM)){
                        item.setTripKm(driverAggregationNode.get(ReportDefine.TRIP_KM).asDouble());
                    }
                    if(driverAggregationNode.has(ReportDefine.STOP_COUNT)){
                        item.setStopCount(driverAggregationNode.get(ReportDefine.STOP_COUNT).asInt());
                    }
                    if(driverAggregationNode.has(ReportDefine.STOP_DURATION)){
                        item.setStopDuration(driverAggregationNode.get(ReportDefine.STOP_DURATION).asInt());
                    }
                    if(driverAggregationNode.has(ReportDefine.OVER_SPEED_COUNT)){
                        item.setOverspeedCount(driverAggregationNode.get(ReportDefine.OVER_SPEED_COUNT).asInt());
                    }
                    if(driverAggregationNode.has(ReportDefine.OVER_SPEED_DURATION)){
                        item.setOverspeedDuration(driverAggregationNode.get(ReportDefine.OVER_SPEED_DURATION).asInt());
                    }
                    if(driverAggregationNode.has(ReportDefine.OVER_SPEED_MAX)){
                        item.setOverspeedMax(driverAggregationNode.get(ReportDefine.OVER_SPEED_MAX).asInt());
                    }
                    if(driverAggregationNode.has(ReportDefine.OVER_SPEED_KM)){
                        item.setOverspeedKm(driverAggregationNode.get(ReportDefine.OVER_SPEED_KM).asDouble());
                    }
                    if(driverAggregationNode.has(ReportDefine.OVER_SPEED_MSG_RANGE)){
                        item.setOverspeedMsgRange(FmsUtil.getArray(driverAggregationNode.get(ReportDefine.OVER_SPEED_MSG_RANGE).asText()));
                    }
                    if(driverAggregationNode.has(ReportDefine.OVER_SPEED_RANGE)){
                        item.setOverspeedRange(FmsUtil.getArray(driverAggregationNode.get(ReportDefine.OVER_SPEED_RANGE).asText()));
                    }
                    if(driverAggregationNode.has(ReportDefine.OVER_4H_COUNT)){
                        item.setOvertime4hCount(driverAggregationNode.get(ReportDefine.OVER_4H_COUNT).asInt());
                    }
                    if(driverAggregationNode.has(ReportDefine.OVER_10H_COUNT)){
                        item.setOvertime10Count(driverAggregationNode.get(ReportDefine.OVER_10H_COUNT).asInt());
                    }
                }
            }

            return item;
        }  catch (Exception e) {
            log.error("Exception", e);
        }

        return null;
    }

    public void addXmitVehicle(String konexyId, DataXmit item) throws Exception {
        if (item == null) {
            throw new NullPointerException("addXmit: item is null!");
        }

        if (konexyId == null) {
            return;
        }

        if (item.getBeginTime() == null) {
            throw new NullPointerException("addXmit: item.getBeginTime is null!");
        }

        int unixTime     = (int) (item.getBeginTime().getTime()/1000L);
        StringBuffer sb = new StringBuffer("/log/");
        sb.append(konexyId).append("/").append(unixTime);

        String jsonXmit = ElogParser.getJSONXmit(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonXmit);
    }

    public void addGeoPoiVehicle(String konexyId, GeoPoi item) throws Exception {
        if (item == null) {
            throw new NullPointerException("GeoPoi: item is null!");
        }

        if (konexyId == null) {
            return;
        }

        if (item.getTimeIn() == null) {
            throw new NullPointerException("GeoPoi: item.getBeginTime is null!");
        }

        int unixTime     = (int) (item.getTimeIn().getTime()/1000L);
        StringBuffer sb = new StringBuffer("/log/");
        sb.append(konexyId).append("/").append(unixTime);

        String jsonXmit = ElogParser.getJSONGeoPoi(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonXmit);
    }

	public void addTrip(String konexyId, Trip item) throws Exception {
        if (item == null) {
            throw new NullPointerException("addTrip: item is null!");
        }

        if (item.getBeginTime() == null) {
            throw new NullPointerException("addTrip: item.getBeginTime is null!");
        }
        //Build path
        int unixTime     = (int) (item.getBeginTime().getTime()/1000L);
        StringBuffer sb = new StringBuffer();
        sb.append("/log/").append(konexyId).append("/").append(unixTime);

        //parser Object Trip to Json String
        String jsonTrip = ElogParser.getJSONTrip(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonTrip);

	}

    public void addStop(String konexyId, Stop item) throws Exception {
        if (item == null) {
            throw new NullPointerException("addStop: item is null!");
        }

        if (konexyId == null) {
            throw new NullPointerException("addStop: konexyId is null!");
        }

        if (item.getBeginTime() == null) {
            throw new NullPointerException("addStop: item.getBeginTime is null!");
        }

        //Build path
        int unixTime     = (int) (item.getBeginTime().getTime()/1000L);
        StringBuffer sb = new StringBuffer();
        sb.append("/log/").append(konexyId).append("/").append(unixTime);

        //parser Object Stop to Json String
        String jsonStop = ElogParser.getJSONStop(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonStop);
    }

    public void addOvertime4h(String konexyId, DrivingOver4h item) throws Exception {
        if (item == null) {
            throw new NullPointerException("addOvertime4h: item is null!");
        }

        if (konexyId == null) {
            throw new NullPointerException("addStop: konexyId is null!");
        }

        if (item.getBeginTime() == null) {
            throw new NullPointerException("addOvertime4h: item.getBeginTime is null!");
        }

        //Build path
        int unixTime     = (int) (item.getBeginTime().getTime()/1000L);
        StringBuffer sb = new StringBuffer();
        sb.append("/log/").append(konexyId).append("/").append(unixTime);

        //parser Object Over4h to Json String
        String jsonOver4h = ElogParser.getJSONOver4h(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonOver4h);
    }

    public void addOvertime10h(String konexyId, DrivingOver10h item) throws Exception {
        if (item == null) {
            throw new NullPointerException("addOvertime10h: item is null!");
        }

        if (konexyId == null) {
            throw new NullPointerException("addStop: konexyId is null!");
        }

        if (item.getBeginTime() == null) { // begintime
            throw new NullPointerException("addOvertime10h: item.getBeginTime is null!");
        }

        //Build path
        int unixTime     = (int) (item.getBeginTime().getTime()/1000L);
        StringBuffer sb = new StringBuffer();
        sb.append("/log/").append(konexyId).append("/").append(unixTime);

        //parser Object Over10h to Json String
        String jsonOver10h = ElogParser.getJSONOver10h(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonOver10h);
    }

    public void addOverSpeed(String konexyId, OverSpeed item) throws Exception {
        if (item == null) {
            throw new NullPointerException("os is null!");
        }

        if (konexyId == null) {
            throw new NullPointerException("addStop: konexyId is null!");
        }

        if (item.getBeginTime() == null) { // begintime
            throw new NullPointerException("addOvertime10h: item.getBeginTime is null!");
        }

        //Build path
        int unixTime     = (int) (item.getBeginTime().getTime()/1000L);
        StringBuffer sb = new StringBuffer();
        sb.append("/log/").append(konexyId).append("/").append(unixTime);

        //parser Object Os to Json String
        String jsonOverSpeed = ElogParser.getJSONOverSpeed(item);

        //Save Report to Konexy
        saveReport(sb.toString(), jsonOverSpeed);
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

    private void saveReport(String path, String jsonString) throws Exception {

        int loop =0;
        String respone =null;
        while (true) {
            loop++;
            respone = beanRWsClient.postObject(path, jsonString);
            if(respone!=null) {
                JSONObject jsonObj = new JSONObject(respone);
                String status = (String) jsonObj.get("status");
                if (status.equals("success")) {
                    return;
                }
            }else {
                Thread.sleep(10);
            }

            if(loop > 5){
                return;
            }
        }
    }

    public  void  init(){
        beanRWsClient = (RWsClient)getBean("beanRWsClient");
    }

}
