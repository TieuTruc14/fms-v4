package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.ReportDefine;
import com.eposi.fms.logging.ElogParser;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.services.ReportFileStore;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Tuan on 7/11/2016.
 */
public class AggregationWindowProcessor extends AbstractBean {
    private static final long serialVersionUID = 3513716300562295795L;
    private static Logger log = Logger.getLogger(AggregationWindowProcessor.class.getName());
    private ReportFileStore beanReportFileStore;
    private FmsDao beanFmsDao;

    private HashMap<String, VehicleAggregationWindow> hashMapVehicleAggregationWindow= new HashMap<String, VehicleAggregationWindow>();
    private HashMap<String, DriverAggregationWindow>  hashMapDriverAggregationWindow = new HashMap<String, DriverAggregationWindow>();
    private HashMap<String, CompanyAggregationWindow> hashMapCompanyAggregationWindow= new HashMap<String, CompanyAggregationWindow>();
    private HashMap<String, XmitAggregationWindow>    hashMapXmitAggregationWindow= new HashMap<String, XmitAggregationWindow>();
    private long startTime = 0;

    public synchronized void processMessage(AbstractMessage msg){
        try {
            this.updateVehicleAggregation(msg);
        }catch (Exception e){
            log.error("processMessage.updateVehicleAggregation.ex:"+e.getMessage());
        }

        try {
            this.updateDriverAggregation(msg);
        }catch (Exception e){
            log.error("processMessage.updateDriverAggregation.ex:"+e.getMessage());
        }

        try {
            this.updateCompnanyAggregationWindow(msg);
        }catch (Exception e){
            log.error("processMessage.updateCompnanyAggregationWindow.ex:"+e.getMessage());
        }

        try {
            this.updateCompnanyActivity(msg);
        }catch (Exception e){
            log.error("processMessage.updateCompnanyActivity.ex:"+e.getMessage());
        }

        try {
            this.updateXmitAggregationWindow(msg);
        }catch (Exception e){
            log.error("processMessage.updateXmitAggregationWindow.ex:"+e.getMessage());
        }

        long now = System.currentTimeMillis();
        if (startTime == 0) {
            startTime = now;
        }
        long span = now - startTime;
        if ((span > 900000)) { // 15 *60*1000

            Iterator<Map.Entry<String, VehicleAggregationWindow>> iteratorVehicle = hashMapVehicleAggregationWindow.entrySet().iterator();
            while (iteratorVehicle.hasNext()) {
                try {
                    Map.Entry<String, VehicleAggregationWindow> entry = iteratorVehicle.next();
                    beanReportFileStore.updateVehicleAggregationWindow(entry.getValue());
                    iteratorVehicle.remove();
                } catch(Exception e) {
                    log.error("hashMapVehicleAggregationWindow.ex:"+e.getMessage());
                }
            }
            hashMapVehicleAggregationWindow.clear();


            Iterator<Map.Entry<String, DriverAggregationWindow>> iteratorDriver = hashMapDriverAggregationWindow.entrySet().iterator();
            while (iteratorDriver.hasNext()) {
                try {
                    Map.Entry<String, DriverAggregationWindow> entry = iteratorDriver.next();
                    beanReportFileStore.updateDriverAggregationWindow(entry.getValue());
                    iteratorDriver.remove();
                } catch(Exception e) {
                    log.error("hashMapDriverAggregationWindow.ex:"+e.getMessage());
                }
            }
            hashMapDriverAggregationWindow.clear();


            Iterator<Map.Entry<String, CompanyAggregationWindow>> iteratorCompany = hashMapCompanyAggregationWindow.entrySet().iterator();
            while (iteratorCompany.hasNext()) {
                try {
                    Map.Entry<String, CompanyAggregationWindow> entry = iteratorCompany.next();
                    beanReportFileStore.updateCompanyAggregationWindow(entry.getValue());
                    iteratorCompany.remove();
                } catch(Exception e) {
                    log.error("hashMapCompanyAggregationWindow.ex:"+e.getMessage());
                }
            }
            hashMapCompanyAggregationWindow.clear();

            Iterator<Map.Entry<String, XmitAggregationWindow>> iteratorXmit = hashMapXmitAggregationWindow.entrySet().iterator();
            while (iteratorXmit.hasNext()) {
                try {
                    Map.Entry<String, XmitAggregationWindow> entry = iteratorXmit.next();
                    beanReportFileStore.updateXmitAggregationWindow(entry.getValue());
                    iteratorXmit.remove();
                } catch(Exception e) {
                    log.error("hashMapXmitAggregationWindow.ex:"+e.getMessage());
                }
            }
            hashMapXmitAggregationWindow.clear();


            startTime = 0;
        }
    }

    private void updateVehicleAggregation(AbstractMessage msg) throws  Exception{
        if (msg == null) {
            log.error("updateVehicleAggregation: msg is null");
            return;
        }

        if (msg instanceof Stop) {
            Stop stop = (Stop) msg;
            String key = stop.getVehicleAggregationId() + "_" + FormatUtil.formatDate(stop.getBeginTime(), "yyyyMMdd");
            VehicleAggregationWindow vehicleAggregationWindow = hashMapVehicleAggregationWindow.get(key);
            if (vehicleAggregationWindow == null) {
                vehicleAggregationWindow = new VehicleAggregationWindow();
                vehicleAggregationWindow.setDate(stop.getBeginTime());
                vehicleAggregationWindow.setVehicle(stop.getVehicle());
                vehicleAggregationWindow.setVehicleAggregationId(stop.getVehicleAggregationId());
            }
            vehicleAggregationWindow.setCompanyId(stop.getCompany());
            vehicleAggregationWindow.setStopCount(vehicleAggregationWindow.getStopCount() + 1);
            vehicleAggregationWindow.setStopDuration(vehicleAggregationWindow.getStopDuration() + stop.getDuration());

            hashMapVehicleAggregationWindow.put(key, vehicleAggregationWindow);

        } else if (msg instanceof OverSpeed) {
            OverSpeed os = (OverSpeed) msg;
            String key = os.getVehicleAggregationId() + "_" + FormatUtil.formatDate(os.getBeginTime(), "yyyyMMdd");
            VehicleAggregationWindow vehicleAggregationWindow = hashMapVehicleAggregationWindow.get(key);

            if (vehicleAggregationWindow == null) {
                vehicleAggregationWindow = new VehicleAggregationWindow();
                vehicleAggregationWindow.setDate(os.getBeginTime());
                vehicleAggregationWindow.setVehicle(os.getVehicle());
                vehicleAggregationWindow.setVehicleAggregationId(os.getVehicleAggregationId());
            }
            vehicleAggregationWindow.setCompanyId(os.getCompany());
            vehicleAggregationWindow.setOverspeedCount(vehicleAggregationWindow.getOverspeedCount()+1);
            vehicleAggregationWindow.setOverspeedDuration(vehicleAggregationWindow.getOverspeedDuration() + os.getDuration());
            vehicleAggregationWindow.setOverspeedKm(vehicleAggregationWindow.getOverspeedKm() + os.getOsKm());

            int[] overspeedMsgRange = vehicleAggregationWindow.getOverspeedMsgRange();
            if (overspeedMsgRange == null) {
                overspeedMsgRange = new int[] {0, 0, 0, 0, 0};
            }

            List<Float> lstSpeed = os.getLstSpeed();
            for(float speed :lstSpeed){
                int speedOver = (int)speed - os.getSpeedLimit();
                if (speedOver < 5) {         overspeedMsgRange[0]++;
                } else if (speedOver < 10) { overspeedMsgRange[1]++;
                } else if (speedOver < 20) { overspeedMsgRange[2]++;
                } else if (speedOver < 35) { overspeedMsgRange[3]++;
                } else {                     overspeedMsgRange[4]++;
                }
            }
            vehicleAggregationWindow.setOverspeedMsgRange(overspeedMsgRange);


            int[] overspeedRange = vehicleAggregationWindow.getOverspeedRange();
            if (overspeedRange == null) {
                overspeedRange = new int[] {0, 0, 0, 0, 0};
            }
            int speedOver = os.getMaxSpeed() - os.getSpeedLimit();
            if (speedOver < 5) {         overspeedRange[0]++;
            } else if (speedOver < 10) { overspeedRange[1]++;
            } else if (speedOver < 20) { overspeedRange[2]++;
            } else if (speedOver < 35) { overspeedRange[3]++;
            } else {                     overspeedRange[4]++;
            }
            vehicleAggregationWindow.setOverspeedRange(overspeedRange);

            hashMapVehicleAggregationWindow.put(key, vehicleAggregationWindow);

        } else if (msg instanceof DrivingOver4h) {
            DrivingOver4h drivingOver4h = (DrivingOver4h) msg;
            String key = drivingOver4h.getVehicleAggregationId() + "_" + FormatUtil.formatDate(drivingOver4h.getBeginTime(), "yyyyMMdd");
            VehicleAggregationWindow vehicleAggregationWindow = hashMapVehicleAggregationWindow.get(key);

            if (vehicleAggregationWindow == null) {
                vehicleAggregationWindow = new VehicleAggregationWindow();
                vehicleAggregationWindow.setDate(drivingOver4h.getBeginTime());
                vehicleAggregationWindow.setVehicle(drivingOver4h.getVehicle());
                vehicleAggregationWindow.setVehicleAggregationId(drivingOver4h.getVehicleAggregationId());
            }
            vehicleAggregationWindow.setCompanyId(drivingOver4h.getCompany());
            vehicleAggregationWindow.setOvertime4hCount(vehicleAggregationWindow.getOvertime4hCount() + 1);

            hashMapVehicleAggregationWindow.put(key, vehicleAggregationWindow);

        } else if (msg instanceof DrivingOver10h) {
            DrivingOver10h drivingOver10h = (DrivingOver10h) msg;
            String key = drivingOver10h.getVehicleAggregationId() + "_" + FormatUtil.formatDate(drivingOver10h.getBeginTime(), "yyyyMMdd");
            VehicleAggregationWindow vehicleAggregationWindow = hashMapVehicleAggregationWindow.get(key);
            if (vehicleAggregationWindow == null) {
                vehicleAggregationWindow = new VehicleAggregationWindow();
                vehicleAggregationWindow.setDate(drivingOver10h.getEndTime());
                vehicleAggregationWindow.setVehicle(drivingOver10h.getVehicle());
                vehicleAggregationWindow.setVehicleAggregationId(drivingOver10h.getVehicleAggregationId());
            }
            vehicleAggregationWindow.setCompanyId(drivingOver10h.getCompany());
            vehicleAggregationWindow.setOvertime10Count(vehicleAggregationWindow.getOvertime10Count() + 1);

            hashMapVehicleAggregationWindow.put(key, vehicleAggregationWindow);

        }  else if (msg instanceof Trip) {
            Trip trip = (Trip) msg;
            String key = trip.getVehicleAggregationId() + "_" + FormatUtil.formatDate(trip.getBeginTime(), "yyyyMMdd");
            VehicleAggregationWindow vehicleAggregationWindow = hashMapVehicleAggregationWindow.get(key);
            if (vehicleAggregationWindow == null) {
                vehicleAggregationWindow = new VehicleAggregationWindow();
                vehicleAggregationWindow.setDate(trip.getBeginTime());
                vehicleAggregationWindow.setVehicle(trip.getVehicle());
                vehicleAggregationWindow.setVehicleAggregationId(trip.getVehicleAggregationId());
                vehicleAggregationWindow.setCompanyId(trip.getCompany());
            }

            vehicleAggregationWindow.setTripCount(vehicleAggregationWindow.getTripCount() + 1);
            vehicleAggregationWindow.setTripKm(vehicleAggregationWindow.getTripKm() + trip.getTripKm());
            hashMapVehicleAggregationWindow.put(key, vehicleAggregationWindow);

        } else if (msg instanceof VehicleXmitAggregationWindow) {
            VehicleXmitAggregationWindow vehicleXmitMsg = (VehicleXmitAggregationWindow) msg;
            String key = vehicleXmitMsg.getVehicleAggregationId() + "_" + FormatUtil.formatDate(vehicleXmitMsg.getDate(), "yyyyMMdd");
            VehicleAggregationWindow vehicleAggregationWindow = hashMapVehicleAggregationWindow.get(key);
            if(vehicleAggregationWindow==null){
                vehicleAggregationWindow = new VehicleAggregationWindow();
                vehicleAggregationWindow.setCompanyId(vehicleXmitMsg.getCompany());
                vehicleAggregationWindow.setDate(vehicleXmitMsg.getDate());
                vehicleAggregationWindow.setVehicle(vehicleXmitMsg.getVehicle());
                vehicleAggregationWindow.setVehicleAggregationId(vehicleXmitMsg.getVehicleAggregationId());
            }

            vehicleAggregationWindow.setCompanyId(vehicleXmitMsg.getCompany());
            vehicleAggregationWindow.setMsgCount(vehicleAggregationWindow.getMsgCount() + vehicleXmitMsg.getMsgCount());

            hashMapVehicleAggregationWindow.put(key, vehicleAggregationWindow);

        }  else  if (msg instanceof DataXmit) {
            DataXmit xmit  = (DataXmit)msg;
            String key = xmit.getVehicleAggregationId() + "_" + FormatUtil.formatDate(xmit.getBeginTime(), "yyyyMMdd");
            VehicleAggregationWindow vehicleAggregationWindow = hashMapVehicleAggregationWindow.get(key);
            if(vehicleAggregationWindow==null){
                vehicleAggregationWindow = new VehicleAggregationWindow();
                vehicleAggregationWindow.setCompanyId(xmit.getCompany());
                vehicleAggregationWindow.setDate(xmit.getBeginTime());
                vehicleAggregationWindow.setVehicle(xmit.getVehicle());
                vehicleAggregationWindow.setVehicleAggregationId(xmit.getVehicleAggregationId());
            }
            if(xmit.getType()==ReportDefine.DATA_XMIT_MSG_OFF){
                vehicleAggregationWindow.setDataNoSignalCount(vehicleAggregationWindow.getDataNoSignalCount()+1);
                vehicleAggregationWindow.setDataNoSignalDuration(vehicleAggregationWindow.getDataNoSignalDuration()+ xmit.getDuration());
            }else {
                vehicleAggregationWindow.setGpsNoSignalCount(vehicleAggregationWindow.getGpsNoSignalCount()+1);
                vehicleAggregationWindow.setGpsNoSignalDuration(vehicleAggregationWindow.getGpsNoSignalDuration() +xmit.getDuration());
            }
            vehicleAggregationWindow.setCompanyId(xmit.getCompany());

            hashMapVehicleAggregationWindow.put(key, vehicleAggregationWindow);
        }
    }

    private  void updateDriverAggregation(AbstractMessage msg) throws  Exception{
        if (msg == null) {
            return;
        }

        if (msg instanceof Stop) {
            Stop stop = (Stop) msg;
            if(stop.getDriverAggregationId()!=null) {
                String key = stop.getDriverAggregationId() + "_" + FormatUtil.formatDate(stop.getBeginTime(), "yyyyMMdd");
                DriverAggregationWindow driverAggregationWindow = hashMapDriverAggregationWindow.get(key);
                if (driverAggregationWindow == null) {
                    driverAggregationWindow = new DriverAggregationWindow();
                    driverAggregationWindow.setId(stop.getDriver());
                    driverAggregationWindow.setDriverAggregationId(stop.getDriverAggregationId());
                    driverAggregationWindow.setDriverName(stop.getDriverName());
                    driverAggregationWindow.setLicenceKey(stop.getLicenceKey());
                    driverAggregationWindow.setDate(stop.getBeginTime());
                }
                driverAggregationWindow.setStopCount(driverAggregationWindow.getStopCount() + 1);
                driverAggregationWindow.setStopDuration(driverAggregationWindow.getStopDuration() + stop.getDuration());
                hashMapDriverAggregationWindow.put(key, driverAggregationWindow);
            }

        } else if (msg instanceof OverSpeed) {
            OverSpeed os = (OverSpeed) msg;
            if(os.getDriverAggregationId()!=null) {
                String key = os.getDriverAggregationId() + "_" + FormatUtil.formatDate(os.getBeginTime(), "yyyyMMdd");
                DriverAggregationWindow driverAggregationWindow = hashMapDriverAggregationWindow.get(key);
                if (driverAggregationWindow == null) {
                    driverAggregationWindow = new DriverAggregationWindow();
                    driverAggregationWindow.setId(os.getDriver());
                    driverAggregationWindow.setDriverAggregationId(os.getDriverAggregationId());
                    driverAggregationWindow.setDriverName(os.getDriverName());
                    driverAggregationWindow.setLicenceKey(os.getLicenceKey());
                    driverAggregationWindow.setDate(os.getBeginTime());
                }
                driverAggregationWindow.setOverspeedCount(driverAggregationWindow.getOverspeedCount());
                driverAggregationWindow.setOverspeedDuration(driverAggregationWindow.getOverspeedDuration() + os.getDuration());
                driverAggregationWindow.setOverspeedKm(driverAggregationWindow.getOverspeedKm() + os.getOsKm());

                int[] overspeedMsgRange = driverAggregationWindow.getOverspeedMsgRange();
                if (overspeedMsgRange == null) {
                    overspeedMsgRange = new int[]{0, 0, 0, 0, 0};
                }
                List<Float> lstSpeed = os.getLstSpeed();
                for (float speed : lstSpeed) {
                    int speedOver = (int) speed - os.getSpeedLimit();
                    if (speedOver < 5) {
                        overspeedMsgRange[0]++;
                    } else if (speedOver < 10) {
                        overspeedMsgRange[1]++;
                    } else if (speedOver < 20) {
                        overspeedMsgRange[2]++;
                    } else if (speedOver < 35) {
                        overspeedMsgRange[3]++;
                    } else {
                        overspeedMsgRange[4]++;
                    }
                }
                driverAggregationWindow.setOverspeedMsgRange(overspeedMsgRange);

                int[] overspeedRange = driverAggregationWindow.getOverspeedRange();
                if (overspeedRange == null) {
                    overspeedRange = new int[]{0, 0, 0, 0, 0};
                }

                int speedOver = os.getMaxSpeed() - os.getSpeedLimit();

                if (speedOver < 5) {
                    overspeedRange[0]++;
                } else if (speedOver < 10) {
                    overspeedRange[1]++;
                } else if (speedOver < 20) {
                    overspeedRange[2]++;
                } else if (speedOver < 35) {
                    overspeedRange[3]++;
                } else {
                    overspeedRange[4]++;
                }
                driverAggregationWindow.setOverspeedRange(overspeedRange);
                hashMapDriverAggregationWindow.put(key, driverAggregationWindow);
            }
        } else if (msg instanceof DrivingOver4h) {
            DrivingOver4h drivingOver4h = (DrivingOver4h) msg;
            if(drivingOver4h.getDriverAggregationId()!=null) {
                String key = drivingOver4h.getDriverAggregationId() + "_" + FormatUtil.formatDate(drivingOver4h.getBeginTime(), "yyyyMMdd");
                DriverAggregationWindow driverAggregationWindow = hashMapDriverAggregationWindow.get(key);
                if (driverAggregationWindow == null) {
                    driverAggregationWindow = new DriverAggregationWindow();
                    driverAggregationWindow.setId(drivingOver4h.getDriver());
                    driverAggregationWindow.setDriverName(drivingOver4h.getDriverName());
                    driverAggregationWindow.setLicenceKey(drivingOver4h.getLicenceKey());
                    driverAggregationWindow.setDate(drivingOver4h.getBeginTime());
                    driverAggregationWindow.setDriverAggregationId(drivingOver4h.getDriverAggregationId());
                }
                driverAggregationWindow.setOvertime4hCount(driverAggregationWindow.getOvertime4hCount() + 1);
                hashMapDriverAggregationWindow.put(key, driverAggregationWindow);
            }
        } else if (msg instanceof DrivingOver10h) {
            DrivingOver10h drivingOver10h = (DrivingOver10h) msg;
            if(drivingOver10h.getDriverAggregationId()!=null) {
                String key = drivingOver10h.getDriverAggregationId() + "_" + FormatUtil.formatDate(drivingOver10h.getBeginTime(), "yyyyMMdd");
                DriverAggregationWindow driverAggregationWindow = hashMapDriverAggregationWindow.get(key);
                if (driverAggregationWindow == null) {
                    driverAggregationWindow = new DriverAggregationWindow();
                    driverAggregationWindow.setId(drivingOver10h.getDriver());
                    driverAggregationWindow.setDriverName(drivingOver10h.getDriverName());
                    driverAggregationWindow.setLicenceKey(drivingOver10h.getLicenceKey());
                    driverAggregationWindow.setDate(drivingOver10h.getEndTime());
                    driverAggregationWindow.setDriverAggregationId(drivingOver10h.getDriverAggregationId());
                }
                driverAggregationWindow.setOvertime10Count(driverAggregationWindow.getOvertime10Count() + 1);
                hashMapDriverAggregationWindow.put(key, driverAggregationWindow);
            }
        }  else if (msg instanceof Trip) {
            Trip trip = (Trip) msg;
            if(trip.getDriverAggregationId()!=null) {
                String key = trip.getDriverAggregationId() + "_" + FormatUtil.formatDate(trip.getBeginTime(), "yyyyMMdd");
                DriverAggregationWindow driverAggregationWindow = hashMapDriverAggregationWindow.get(key);
                if (driverAggregationWindow == null) {
                    driverAggregationWindow = new DriverAggregationWindow();
                    driverAggregationWindow.setId(trip.getDriver());
                    driverAggregationWindow.setDriverAggregationId(trip.getDriverAggregationId());
                    driverAggregationWindow.setDriverName(trip.getDriverName());
                    driverAggregationWindow.setLicenceKey(trip.getLicenceKey());
                    driverAggregationWindow.setDate(trip.getBeginTime());
                }
                driverAggregationWindow.setTripCount(driverAggregationWindow.getTripCount() + 1);
                driverAggregationWindow.setTripKm(driverAggregationWindow.getTripKm() + trip.getTripKm());
                hashMapDriverAggregationWindow.put(key, driverAggregationWindow);
            }
        }
    }

    private void updateCompnanyAggregationWindow(AbstractMessage msg) throws Exception{

         if (msg instanceof Stop) {
            Stop stop = (Stop) msg;
            if (stop.getCompanyAggregationId() != null) {
                String key = stop.getCompanyAggregationId() + "_" + FormatUtil.formatDate(stop.getBeginTime(), "yyyyMMdd");
                CompanyAggregationWindow companyAggregationWindow = hashMapCompanyAggregationWindow.get(key);
                if (companyAggregationWindow == null) {
                    companyAggregationWindow = new CompanyAggregationWindow();
                    companyAggregationWindow.setDate(stop.getBeginTime());
                    companyAggregationWindow.setCompanyId(stop.getCompany());
                    companyAggregationWindow.setCompanyAggregationId(stop.getCompanyAggregationId());
                }
                companyAggregationWindow.setStopCount(companyAggregationWindow.getStopCount() + 1);
                companyAggregationWindow.setStopDuration(companyAggregationWindow.getStopDuration() + stop.getDuration());
                hashMapCompanyAggregationWindow.put(key, companyAggregationWindow);
            }else {
                log.error("updateCompnanyAggregationWindow.Stop: getCompanyAggregationId is null");
            }
        } else if (msg instanceof OverSpeed) {
            OverSpeed overSpeed = (OverSpeed) msg;
            if (overSpeed.getCompanyAggregationId() != null) {
                String key = overSpeed.getCompanyAggregationId() + "_" + FormatUtil.formatDate(overSpeed.getBeginTime(), "yyyyMMdd");
                CompanyAggregationWindow companyAggregationWindow = hashMapCompanyAggregationWindow.get(key);

                if (companyAggregationWindow == null) {
                    companyAggregationWindow = new CompanyAggregationWindow();
                    companyAggregationWindow.setDate(overSpeed.getBeginTime());
                    companyAggregationWindow.setCompanyId(overSpeed.getCompany());
                    companyAggregationWindow.setCompanyAggregationId(overSpeed.getCompanyAggregationId());
                }
                companyAggregationWindow.setOverspeedDuration(companyAggregationWindow.getOverspeedDuration() + overSpeed.getDuration());
                companyAggregationWindow.setOverspeedKm(companyAggregationWindow.getOverspeedKm() + overSpeed.getOsKm());
                companyAggregationWindow.setOverspeedCount(companyAggregationWindow.getOverspeedCount() + 1);

                int speedOver = overSpeed.getMaxSpeed() - overSpeed.getSpeedLimit();
                int[] overspeedRange = companyAggregationWindow.getOverspeedRange();
                if (speedOver < 5) {         overspeedRange[0]++;
                } else if (speedOver < 10) { overspeedRange[1]++;
                } else if (speedOver < 20) { overspeedRange[2]++;
                } else if (speedOver < 35) { overspeedRange[3]++;
                } else {                     overspeedRange[4]++;
                }

                companyAggregationWindow.setOverspeedRange(overspeedRange);
                hashMapCompanyAggregationWindow.put(key, companyAggregationWindow);
            }else {
                log.error("updateCompnanyAggregationWindow.OverSpeed: getCompanyAggregationId is null");
            }
        } else if (msg instanceof DrivingOver4h) {
            DrivingOver4h drivingOver4h = (DrivingOver4h) msg;

            // company
            if (drivingOver4h.getCompanyAggregationId() != null) {
                String key = drivingOver4h.getCompanyAggregationId() + "_" + FormatUtil.formatDate(drivingOver4h.getBeginTime(), "yyyyMMdd");
                CompanyAggregationWindow companyAggregationWindow = hashMapCompanyAggregationWindow.get(key);

                if (companyAggregationWindow == null) {
                    companyAggregationWindow = new CompanyAggregationWindow();
                    companyAggregationWindow.setDate(drivingOver4h.getBeginTime());
                    companyAggregationWindow.setCompanyId(drivingOver4h.getCompany());
                    companyAggregationWindow.setCompanyAggregationId(drivingOver4h.getCompanyAggregationId());
                }
                companyAggregationWindow.setOvertime4hCount(companyAggregationWindow.getOvertime4hCount() + 1);
                hashMapCompanyAggregationWindow.put(key, companyAggregationWindow);
            }else {
                log.error("updateCompnanyAggregationWindow.DrivingOver4h: getCompanyAggregationId is null");
            }
        } else if (msg instanceof DrivingOver10h) {
            DrivingOver10h drivingOver10h = (DrivingOver10h) msg;

            // company
            if (drivingOver10h.getCompanyAggregationId() != null) {
                String key = drivingOver10h.getCompanyAggregationId() + "_" + FormatUtil.formatDate(drivingOver10h.getBeginTime(), "yyyyMMdd");
                CompanyAggregationWindow companyAggregationWindow = hashMapCompanyAggregationWindow.get(key);
                if (companyAggregationWindow == null) {
                    companyAggregationWindow = new CompanyAggregationWindow();
                    companyAggregationWindow.setDate(drivingOver10h.getEndTime());
                    companyAggregationWindow.setCompanyId(drivingOver10h.getCompany());
                    companyAggregationWindow.setCompanyAggregationId(drivingOver10h.getCompanyAggregationId());
                }
                companyAggregationWindow.setOvertime10Count(companyAggregationWindow.getOvertime10Count() + 1);
                hashMapCompanyAggregationWindow.put(key, companyAggregationWindow);
            }else {
                log.error("updateCompnanyAggregationWindow.DrivingOver10h: getCompanyAggregationId is null");
            }
        } else if (msg instanceof Trip) {
            Trip trip = (Trip) msg;

            // company
            if (trip.getCompanyAggregationId() != null) {
                String key = trip.getCompanyAggregationId() + "_" + FormatUtil.formatDate(trip.getBeginTime(), "yyyyMMdd");
                CompanyAggregationWindow companyAggregationWindow = hashMapCompanyAggregationWindow.get(key);

                if (companyAggregationWindow == null) {
                    companyAggregationWindow = new CompanyAggregationWindow();
                    companyAggregationWindow.setDate(trip.getBeginTime());
                    companyAggregationWindow.setCompanyId(trip.getCompany());
                    companyAggregationWindow.setTripCount(0);
                    companyAggregationWindow.setCompanyAggregationId(trip.getCompanyAggregationId());
                }
                companyAggregationWindow.setTripCount(companyAggregationWindow.getTripCount() + 1);
                companyAggregationWindow.setTripKm(companyAggregationWindow.getTripKm() + trip.getTripKm());
                hashMapCompanyAggregationWindow.put(key, companyAggregationWindow);
            }else {
                log.error("updateCompnanyAggregationWindow.Trip: getCompanyAggregationId is null");
            }
        } else if (msg instanceof VehicleXmitAggregationWindow) {
            VehicleXmitAggregationWindow vehicleXmitAggregationWindow = (VehicleXmitAggregationWindow) msg;
            // company
            if (vehicleXmitAggregationWindow.getCompanyAggregationId() != null) {
                String key = vehicleXmitAggregationWindow.getCompanyAggregationId() + "_" + FormatUtil.formatDate(vehicleXmitAggregationWindow.getDate(), "yyyyMMdd");
                CompanyAggregationWindow companyAggregationWindow = hashMapCompanyAggregationWindow.get(key);
                if (companyAggregationWindow == null) {
                    companyAggregationWindow = new CompanyAggregationWindow();
                    companyAggregationWindow.setDate(vehicleXmitAggregationWindow.getDate());
                    companyAggregationWindow.setCompanyId(vehicleXmitAggregationWindow.getCompany());
                    companyAggregationWindow.setCompanyAggregationId(vehicleXmitAggregationWindow.getCompanyAggregationId());
                }
                companyAggregationWindow.setMsgCount(companyAggregationWindow.getMsgCount() + vehicleXmitAggregationWindow.getMsgCount());
                hashMapCompanyAggregationWindow.put(key, companyAggregationWindow);
            }else {
                log.error("updateCompnanyAggregationWindow.VehicleXmitAggregationWindow: getCompanyAggregationId is null");
            }

        } else if (msg instanceof DataXmit) {
            DataXmit dataXmit = (DataXmit) msg;
            if (dataXmit.getCompanyAggregationId() != null) {
                String key = dataXmit.getCompanyAggregationId() + "_" + FormatUtil.formatDate(dataXmit.getBeginTime(), "yyyyMMdd");
                CompanyAggregationWindow companyAggregationWindow = hashMapCompanyAggregationWindow.get(key);

                if (companyAggregationWindow == null) {
                    companyAggregationWindow = new CompanyAggregationWindow();
                    companyAggregationWindow.setDate(dataXmit.getBeginTime());
                    companyAggregationWindow.setCompanyId(dataXmit.getCompany());
                    companyAggregationWindow.setCompanyAggregationId(dataXmit.getCompanyAggregationId());
                }

                if(dataXmit.getType()== ReportDefine.DATA_XMIT_GPS_OFF){
                    companyAggregationWindow.setGpsNoSignalCount(companyAggregationWindow.getGpsNoSignalCount()+1);
                    companyAggregationWindow.setGpsNoSignalDuration(companyAggregationWindow.getGpsNoSignalDuration()+dataXmit.getDuration());
                }else{
                    companyAggregationWindow.setDataNoSignalCount(companyAggregationWindow.getDataNoSignalCount()+1);
                    companyAggregationWindow.setDataNoSignalDuration(companyAggregationWindow.getDataNoSignalDuration()+dataXmit.getDuration());
                }
                hashMapCompanyAggregationWindow.put(key, companyAggregationWindow);
            }else {
                log.error("updateCompnanyAggregationWindow.DataXmit: getCompanyAggregationId is null");
            }
        }
    }

    private void updateCompnanyActivity(AbstractMessage msg) throws Exception{
        if (msg instanceof Activity) {
            Activity activity = (Activity) msg;
            if(activity.getId()!=null){
                Company company = beanFmsDao.getCompany(activity.getId());
                updateDriverOrVehicleCount(activity, company);
                if(company.getKonexyId()!=null) {
                    beanReportFileStore.saveActivity(company.getKonexyId(), activity);
                }
                while (company.getParent()!=null){
                    company = company.getParent();
                    updateDriverOrVehicleCount(activity, company);
                    updateCompanyCount(activity, company);
                    if(company.getKonexyId()!=null){
                        beanReportFileStore.saveActivity(company.getKonexyId(), activity);
                    }
                }
            }
        }
    }

    private void updateDriverOrVehicleCount(Activity activity, Company company) throws Exception{
        log.error("Activity.ObjectName:"+activity.getObjectName());
        if (activity.getActionName().equals("Addnew")) {
            if(activity.getObjectName().equals(Vehicle.class.getName())){
                company.setVehicleCount(company.getVehicleCount()+1);
                beanFmsDao.editCompany(company);
                beanFmsDao.updateAggregation(company, activity.getDate());
            }else if(activity.getObjectName().equals(Driver.class.getName())){
                company.setDriverCount(company.getDriverCount()+1);
                beanFmsDao.editCompany(company);
            }
        }
    }

    private void updateCompanyCount(Activity activity, Company company) throws Exception{
        if (activity.getActionName().equals("Addnew")) {
            if(activity.getObjectName().equals(Company.class.getName())){
                company.setCompanyCount(company.getCompanyCount()+1);
                beanFmsDao.editCompany(company);
            }
        }
    }

    private void updateXmitAggregationWindow(AbstractMessage msg) throws Exception{
        if (msg instanceof DataXmit) {
            DataXmit dataXmit = (DataXmit) msg;
            if (dataXmit.getBatchAggregationId() != null) {
                String key = dataXmit.getBatchAggregationId()+ "_" + FormatUtil.formatDate(dataXmit.getBeginTime(), "yyyyMMdd");
                XmitAggregationWindow xmitAggregationWindow = hashMapXmitAggregationWindow.get(key);

                if (xmitAggregationWindow == null) {
                    xmitAggregationWindow = new XmitAggregationWindow();
                    xmitAggregationWindow.setDate(dataXmit.getBeginTime());
                    xmitAggregationWindow.setId(dataXmit.getBatchAggregationId());
                }
                if(dataXmit.getType()== ReportDefine.DATA_XMIT_GPS_OFF){
                    xmitAggregationWindow.setGpsCount(xmitAggregationWindow.getGpsCount()+1);
                    xmitAggregationWindow.setGpsDuration(xmitAggregationWindow.getGpsDuration()+dataXmit.getDuration());
                }else{
                    xmitAggregationWindow.setDataCount(xmitAggregationWindow.getDataCount()+1);
                    xmitAggregationWindow.setDataDuration(xmitAggregationWindow.getDataDuration()+dataXmit.getDuration());
                }
                hashMapXmitAggregationWindow.put(key, xmitAggregationWindow);
            }
        }
    }

    public void init() {
        beanReportFileStore = (ReportFileStore) this.getBean("beanReportFileStore");
        beanFmsDao          = (FmsDao)this.getBean("beanFmsDao");
    }
}
