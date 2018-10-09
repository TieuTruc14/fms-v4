package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.common.util.GeoUtil;
import com.eposi.fms.common.FmsUtil;
import com.eposi.fms.common.RegexUtil;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.services.ReportFileStore;
import com.eposi.fms.services.HazelcastClientMapAkka;
import com.eposi.fms.services.XlsClient;
import com.eposi.fms.track.FmsProto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleProcessor extends AbstractBean {
	private static final long serialVersionUID = 2637343177904002744L;
    private static Logger log = Logger.getLogger(VehicleProcessor.class.getName());
    /*************************************************************/
    private static final int    MIN_TIME_PUT_TO_CACHE_VEHICLE_STATUS = 15; // in seconds
    private static final int    MIN_TIME_UPDATE_ADDRESS              = 180; // in seconds
    public static final int     MIN_TIME_PARK_DURATION               = 900; // 15 * 60 in seconds
    /**********************************************************/
    public static final int     DATA_XMIT_GPS_OFF           = 1;
    public static final int     DATA_XMIT_MSG_OFF           = 2;
	/**********************************************************
	 * status
	 */
	public static final int     STATUS_UNKNOW               = 0;
	public static final int     STATUS_STOP_POSSIBLE        = 11;
	public static final int     STATUS_STOP                 = 10;
	public static final int     STATUS_MOVING_POSSIBLE      = 21;
	public static final int     STATUS_MOVING               = 20;
    /**********************************************************/
	public static final int     MIN_STOP_DURATION           = 180; // 3 * 60 in seconds
	public static final int     MAX_STOP_RADIUS             = 100; // meters
	public static final int     TRIP_POSSIBLE_RADIUS        = 150; // meters
    public static final int     TRIP_RADIUS                 = 250; // meters
	//
	public static final int     MIN_DURATION_OVERSPEED_TT08 = 20; // in seconds
    /**********************************************************/
    public static final String  UNKNOW_DEFAULT_DRIVER       = "UNKNOWN";
    public static final String  DEFAULT_DRIVER_NAME         = "UNKNOWN";
    /////////////////////////////////////////////
    private HazelcastClientMapAkka beanHazelcastClientMapAkka;
    private ReportFileStore             beanReportFileStore;
    private FmsDao beanFmsDao;

    private ReportMessageProcessor beanReportMessageProcessor;
    private XlsClient beanXlsClient;

    public void init() {
        beanReportFileStore             = (ReportFileStore) this.getBean("beanReportFileStore");
        beanFmsDao                      = (FmsDao) this.getBean("beanFmsDao");
        beanReportMessageProcessor      = (ReportMessageProcessor) this.getBean("beanReportMessageProcessor");
        beanHazelcastClientMapAkka      = (HazelcastClientMapAkka) this.getBean("beanHazelcastClientMapAkka");
        beanXlsClient                   = (XlsClient) this.getBean("beanXlsClient");
    }

    public void processWayPointBatch(VehicleState state, FmsProto.WayPointBatch wayPointBatch) {
        if (wayPointBatch == null) { return; }

        if(state.getCompanyAggregationId()==null){
            return;
        }

        if(state.getReport()==null){ return; }

        FmsProto.WayPoint[] wayPoints = null;
        try {
            wayPoints       = convertWayPointBatch2WayPoints(wayPointBatch);
        } catch (Exception e){
            log.error("convertWayPointBatch2WayPoints.ex:"+e.getMessage() );
        }

        if (wayPointBatch == null)  { return; }
        if (wayPoints == null)      { return; }
        if (wayPoints.length <= 0)  { return; }

        Date now = new Date();
        if (state != null) {
            // validate duplicate message OR not in order or future
            try {
                Date msgDate = new Date(1000L * wayPoints[0].getDatetime());
                if(state.getTime()==null){
                    state.setTime(msgDate);
                }

                if (msgDate.getTime() <= state.getTime().getTime()) {
                    return;
                }
                if (msgDate.getTime() > now.getTime()+180000){ //3*60*1000 3 minute
                    return;
                }
            } catch(Exception e) {
                log.error("processWayPointBatch.ex:"+e.getMessage() );
            }
            state.setLastServerReceivedMessage(now);
        }

        /**********************************************************/
        try {
            beanReportFileStore.logWayPointBatch(state.getReport().getLog(), wayPointBatch);
        } catch(Exception e) {
            log.error("logWayPointBatch.ex:" + e.getMessage());
        }

        /**********************************************************/
        List<TrackPoint> lstTrackPoint = convertWayPointBatch2TrackPoints(wayPointBatch);
        state.setTrackPointList(lstTrackPoint);


        /**********************************************************/
        // data transmition
        processXmit(state, wayPoints[0]);
		// /////////////////////////////////
		for (FmsProto.WayPoint msg : wayPoints) {
			if (validateWayPoint(msg)) {
				this.processReports(state, msg);
			}
		}
        /**********************************************************/
		// update address before put to cache
		int nowUnixTime = (int) (now.getTime() / 1000L);
        if (nowUnixTime - state.getLastPutToCache() > MIN_TIME_PUT_TO_CACHE_VEHICLE_STATUS) {
            beanHazelcastClientMapAkka.put(state.getVehicle(), state);
            state.setLastPutToCache(nowUnixTime);
        }
	}

    public void processWayPoint(VehicleState state, FmsProto.WayPoint wayPoint) {

        if (wayPoint == null) { return; }
        if(state.getReport() == null){ return; }

        if (state != null) {
            Date now = new Date();
            // validate dupplicate message OR not in order or future
            try {
                Date msgDate = new Date(1000L * wayPoint.getDatetime());
                if(state.getTime()==null){
                    state.setTime(msgDate);
                }

                if (msgDate.getTime() <= state.getTime().getTime()) {
                    return;
                }

                if (msgDate.getTime() > now.getTime()+180000){ //3*60*1000 3 minute
                    return;
                }
            } catch(Exception e) {
                log.error("logWayPointBatch.ex:" + e.getMessage());
            }

            state.setLastServerReceivedMessage(now);
        }

        if(state.getCompanyAggregationId()==null){
            return;
        }
        /**********************************************************/
        try {
            beanReportFileStore.logWayPoint(state.getReport().getLog(), wayPoint);
        } catch(Exception e) {
            log.error("beanLogProcessor.logWayPoint:" + e.getMessage());
        }

        /**********************************************************/
        List<TrackPoint> lstTrackPoint = convertWayPoint2TrackPoints(wayPoint);
        state.setTrackPointList(lstTrackPoint);


        /**********************************************************/
        // data transmition
        processXmit(state, wayPoint);
        /**********************************************************/
        /**********************************************************/
        if (validateWayPoint(wayPoint)) { // validate
            this.processReports(state, wayPoint);
        }
        /**********************************************************/
		// update address before put to cache
		int nowUnixTime = (int) (System.currentTimeMillis() / 1000l);
        if (nowUnixTime - state.getLastPutToCache() > MIN_TIME_PUT_TO_CACHE_VEHICLE_STATUS) {
            beanHazelcastClientMapAkka.put(state.getVehicle(),state);
            state.setLastPutToCache(nowUnixTime);
        }
	}

    private void processReports(VehicleState state, FmsProto.WayPoint msg) {
        /**********************************************************/
        // Report
        try {
            switch (state.getStatus()) {
                case STATUS_STOP_POSSIBLE:
                    this.processStopPossible(state, msg);
                    break;
                case STATUS_STOP:
                    this.processStop(state, msg);
                    break;
                case STATUS_MOVING_POSSIBLE:
                    this.processMovingPossible(state, msg);
                    break;
                case STATUS_MOVING:
                    this.processMoving(state, msg);
                    break;
                default:
                    this.processStatusUnknown(state, msg);
                    break;
            }
        } catch (Exception e) {
            log.error("processReports:" + e.getMessage());
        }

        /**********************************************************/
        // updateCurrentDriver
        updateCurrentDriver(state, msg);

        /**********************************************************/
        // Charging
        geofenceProcess(state, msg);

    }

	private void processStatusUnknown(VehicleState state, FmsProto.WayPoint msg) {
        state.setX(msg.getX());
        state.setY(msg.getY());

        if (msg.hasDatetime()) {
            state.setTime(new Date(1000L * msg.getDatetime()));
        }

        if (msg.hasSpeed()) {
            state.setSpeed(msg.getSpeed());

            if (msg.getSpeed() >= 3) {
                this.changeStatus(state, STATUS_MOVING_POSSIBLE);
            } else {
                this.changeStatus(state, STATUS_STOP_POSSIBLE);
            }
        }

        if (msg.hasIgnition()) {
            state.setIgnition(msg.getIgnition());
        }
	}

	private void processStopPossible(VehicleState state, FmsProto.WayPoint msg) {
		state.setX(msg.getX());
		state.setY(msg.getY());

		if (msg.hasDatetime()) {
			state.setTime(new Date(1000L * msg.getDatetime()));
		}

		if (msg.hasSpeed()) {
			state.setSpeed(msg.getSpeed());
		}

		if (msg.hasIgnition()) {
			state.setIgnition(msg.getIgnition());
		}

		switch (state.getPrevStatus()) {
		case STATUS_UNKNOW:
			if (msg.hasDatetime()) {
                if (GeoUtil.distanceInMeters(msg.getX(), msg.getY(), state.getStop().getX(), state.getStop().getY()) < MAX_STOP_RADIUS) {
					if ((msg.getDatetime() - (state.getStop().getBeginTime().getTime() / 1000l)) > MIN_STOP_DURATION) { // check time over 3 minutes
						this.changeStatus(state, STATUS_STOP);
					}
				} else {
					this.changeStatus(state, STATUS_UNKNOW);
				}
			}

			break;
		case STATUS_MOVING:
			if (msg.hasDatetime()) {
                if (GeoUtil.distanceInMeters(msg.getX(), msg.getY(), state.getStop().getX(), state.getStop().getY()) < MAX_STOP_RADIUS) {
					if ((msg.getDatetime() - (state.getStop().getBeginTime().getTime() / 1000l)) > MIN_STOP_DURATION) { // check time over 3 minutes or not
						this.changeStatus(state, STATUS_STOP);
					}
				} else {
					this.changeStatus(state, STATUS_MOVING);
				}
			}

			break;
		default:
            this.changeStatus(state, STATUS_UNKNOW);
		}
	}

	private void processStop(VehicleState state, FmsProto.WayPoint msg) {
        ///////////////////////////////////////////////
        // break on new date
        Stop stop = state.getStop();
        if (stop != null) {
            Date stopEndTime = new Date(1000L * msg.getDatetime());
            state.getStop().setEndTime(stopEndTime);

            //Reset begin time Driver over 4h if timeStop >=15minute
            DrivingOver4h drivingOver4h =state.getDrivingOver4h();
            if(drivingOver4h!=null) {
                if ((stopEndTime.getTime() - stop.getBeginTime().getTime())/1000 > MIN_TIME_PARK_DURATION) {
                    //Check DrivingOver4h
                    if(drivingOver4h.getEndTime()!=null){
                        drivingOver4h.setId(state.getReport().getOver4h());
                        drivingOver4h.setVehicleAggregationId(state.getVehicleAggregation());
                        drivingOver4h.setVehicle(state.getVehicle());
                        drivingOver4h.setLicenceKey(state.getDriver());
                        drivingOver4h.setDriverAggregationId(state.getDriverAggregation());
                        drivingOver4h.setDriverName(state.getDriverName());
                        drivingOver4h.setCompany(state.getCompanyId());
                        drivingOver4h.setCompanyAggregationId(state.getCompanyAggregationId());
                        String strAddress = this.beanXlsClient.reverseGeocode(drivingOver4h.getEndX(), drivingOver4h.getEndY(),state.getCompanyId());
                        if(strAddress!=null) {
                            if (strAddress.length() > 10) {
                                drivingOver4h.setEndAddress(strAddress);
                            }
                        }

                        if(state.getVehicleAggregationWindow()==null){
                            VehicleAggregationWindow vehicleAggregationWindow = new VehicleAggregationWindow();
                            state.setVehicleAggregationWindow(vehicleAggregationWindow);
                        }
                        state.getVehicleAggregationWindow().setOvertime4hCount( state.getVehicleAggregationWindow().getOvertime4hCount()+1);
                        beanReportMessageProcessor.processMessage(drivingOver4h);
                    }
                    //Reset DrivingOver4h
                    state.setDrivingOver4h(null);
                }
            }

            if (!DateUtils.isSameDay(stop.getBeginTime(), stopEndTime)) {
                state.getStop().setEndTime(stopEndTime);
                if ((stop.getBeginTime() != null)&&(stop.getEndTime() != null)){
                    try {
                        this.doNewStop(state);
                        // new Stop after break;
                        Stop newStop = new Stop();
                        newStop.setVehicle(state.getVehicle());
                        newStop.setBeginTime(stopEndTime);
                        newStop.setX(stop.getX());
                        newStop.setY(stop.getY());
                        newStop.setEndTime(stopEndTime);
                        state.setStop(newStop);

                    } catch (Exception e) {
                        log.error("processStop:" + e.getMessage());
                    }
                }
            }
        }


        switch (state.getPrevStatus()) {
		case STATUS_STOP_POSSIBLE:
			state.setX(msg.getX());
			state.setY(msg.getY());

            if (msg.hasDatetime()) {
                state.setTime(new Date(1000L * msg.getDatetime()));
            }

			if (msg.hasSpeed()) {
				state.setSpeed(msg.getSpeed());
				if (msg.getSpeed() >= 3) {
                    if (GeoUtil.distanceInMeters(msg.getX(), msg.getY(), state.getStop().getX(), state.getStop().getY()) > MAX_STOP_RADIUS) {
						this.changeStatus(state, STATUS_MOVING_POSSIBLE);
					}
				}
			}
			if (msg.hasIgnition()) {
				state.setIgnition(msg.getIgnition());
			}

			break;
		default:
            if (msg.hasDatetime()) {
                state.setTime(new Date(1000L * msg.getDatetime()));
            }
			//throw new NotImplementedException();
            this.changeStatus(state, STATUS_UNKNOW);
		}
	}

	private void processMovingPossible(VehicleState state, FmsProto.WayPoint msg) {
		state.setX(msg.getX());
		state.setY(msg.getY());

		if (msg.hasDatetime()) {
			state.setTime(new Date(1000L * msg.getDatetime()));
		}

		if (msg.hasSpeed()) {
			state.setSpeed(msg.getSpeed());
		}

		if (msg.hasIgnition()) {
			state.setIgnition(msg.getIgnition());
		}


        // STATUS_UNKNOW, STATUS_STOP_POSSIBLE, STATUS_STOP, STATUS_MOVING_POSSIBLE, STATUS_MOVING
		switch (state.getPrevStatus()) {
        case STATUS_UNKNOW:
                this.changeStatus(state, STATUS_MOVING);

                break;
		case STATUS_STOP:
        case STATUS_MOVING_POSSIBLE:
            if (msg.hasDatetime() && msg.hasSpeed()) {
                if ((msg.getSpeed() >= 3) && (GeoUtil.distanceInMeters(msg.getX(), msg.getY(), state.getStop().getX(), state.getStop().getY()) > TRIP_POSSIBLE_RADIUS)) {
                    this.changeStatus(state, STATUS_MOVING);
                }
            }
            break;
		default:
            this.changeStatus(state, STATUS_UNKNOW);
		}
	}

	private void processMoving(VehicleState state, FmsProto.WayPoint msg) {
        try {
            Trip trip = state.getTrip();
            if (trip != null) {
                ///////////////////////////////////////////////
                // break on new date
                Date endTime = new Date(1000L * msg.getDatetime());
                if (!DateUtils.isSameDay(trip.getBeginTime(), endTime)) {
                    // break it
                    trip.setEndTime(endTime);
                    trip.setEndX(msg.getX());
                    trip.setEndY(msg.getY());
                    String strBeginAddress = this.beanXlsClient.reverseGeocode(trip.getBeginX(), trip.getBeginY(),state.getCompanyId());
                    if(strBeginAddress!=null){
                        if(strBeginAddress.length()>10){
                            trip.setBeginAddress(strBeginAddress);
                        }
                    }

                    String strEndAddress = this.beanXlsClient.reverseGeocode(trip.getEndX(), trip.getEndY(),state.getCompanyId());
                    if(strEndAddress!=null){
                        if(strEndAddress.length()>10){
                            trip.setEndAddress(strEndAddress);
                        }
                    }
                    this.doNewTrip(state);

                    // new Trip after break on new date
                    Trip newTrip =new Trip();
                    newTrip.setVehicle(state.getVehicle());
                    newTrip.setBeginTime(endTime);
                    newTrip.setBeginX(state.getX());
                    newTrip.setBeginY(state.getY());
                    newTrip.setEndX(state.getX());
                    newTrip.setEndY(state.getY());
                    newTrip.setEndTime(endTime);

                    state.setTrip(newTrip);
                }

                // update distance for the trip
                double distance = GeoUtil.distanceInMeters(msg.getX(), msg.getY(), trip.getEndX(), trip.getEndY());
                if ((distance > 40)) {
                    trip.setDistanceGPS(trip.getDistanceGPS()+(float)distance);
                    trip.setEndX(msg.getX());
                    trip.setEndY(msg.getY());
                    trip.setEndTime(endTime);
                }

                /**********************************************************/
                // update max speed for the trip, (not for overspeed)
                if (msg.hasSpeed()) {
                    if (msg.getSpeed() < 150) {
                        if (msg.getSpeed() > trip.getMaxSpeedGPS()) {
                            trip.setMaxSpeedGPS((int) msg.getSpeed());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("processMoving:" + e.getMessage());
        }

        /**********************************************************/
		// OVERSPEED
        try {
            if (msg.hasSpeed()) {
                // find the speedLimit
                int speedLimit = state.getSpeedLimit();
                /////////////////////////////////////////////////////////////////
                // khong tinh toan neu truyen khong lien tuc (60s)
                if (msg.hasDatetime() && (1000L * msg.getDatetime() - state.getTime().getTime() > 60000)) {
                    state.setOverspeed(null);
                }
                OverSpeed os = state.getOverspeed();

                if (msg.getSpeed() > speedLimit) {
                    ExpressWay expressWay = beanFmsDao.findExpressWay(msg.getX(), msg.getY());
                    if(expressWay!=null){
                        speedLimit = expressWay.getMaxSpeed();
                    }

                    if(msg.getSpeed() > speedLimit) {
                        if (os == null) { // start OverSpeed
                            os = new OverSpeed();
                            os.setVehicle(state.getVehicle());
                            os.setBeginTime(new Date(1000L * msg.getDatetime()));
                            os.setBeginX(msg.getX());
                            os.setBeginY(msg.getY());
                            os.setSpeedLimit(speedLimit);
                            os.addSpeed(msg.getSpeed());
                            os.setMaxSpeed((int) msg.getSpeed());
                            state.setOverspeed(os);
                        } else {
                            //Add speed to list
                            os.addSpeed(msg.getSpeed());
                            // caculate max speed during overspeed
                            if (msg.getSpeed() > os.getMaxSpeed()) {
                                if (msg.getSpeed() < 150) {
                                    os.setMaxSpeed((int) msg.getSpeed());
                                }
                            }
                            // caculate distance for the overspeed
                            os.setDistance(os.getDistance() +(float)GeoUtil.distanceInMeters(msg.getX(), msg.getY(), state.getX(), state.getY()));
                            if (Double.isNaN(os.getDistance())) {
                                os.setDistance(0);
                            }
                            os.setEndTime(new Date(1000L * msg.getDatetime()));
                        }
                    }else {
                        if (os != null) { // stop OverSpeed
                            processOS(msg, os, state);
                        }
                    }
                } else {
                    if (os != null) { // stop OverSpeed
                        processOS(msg, os, state);
                    }
                }
            }
        } catch (Exception e) {
            log.error("beanReportFileStore.addOverSpeed:" + e.getMessage());
        }

        /**********************************************************/
        // PHAT HIEN VI PHAM THOI GIAN LAI XE 4h va 10h
        String currentDriver = state.getDriver();
        if ((currentDriver!= null) && (!UNKNOW_DEFAULT_DRIVER.equals(currentDriver))) {
            // 4h
            try {
                /////////////////////////////////////////////////////////////////
                // Reset neu truyen khong lien tuc (15p)
                if (msg.hasDatetime() && (1000L * msg.getDatetime() - state.getTime().getTime() > 900000L)) {
                    DrivingOver4h drivingOver4h = state.getDrivingOver4h();
                    if(drivingOver4h!=null) {
                        if (drivingOver4h.getEndTime() != null) {
                            try {
                                drivingOver4h.setLicenceKey(state.getDriver());
                                drivingOver4h.setDriverName(state.getDriverName());
                                drivingOver4h.setDriverAggregationId(state.getDriverAggregation());
                                drivingOver4h.setId(state.getReport().getOver4h());
                                drivingOver4h.setVehicle(state.getVehicle());
                                drivingOver4h.setVehicleAggregationId(state.getVehicleAggregation());
                                drivingOver4h.setCompany(state.getCompanyId());
                                drivingOver4h.setCompanyAggregationId(state.getCompanyAggregationId());
                                String strEndAddress = this.beanXlsClient.reverseGeocode(drivingOver4h.getEndX(), drivingOver4h.getEndY(),state.getCompanyId());
                                if(strEndAddress!=null) {
                                    if (strEndAddress.length() > 10) {
                                        drivingOver4h.setEndAddress(strEndAddress);
                                    }
                                }
                                if(state.getVehicleAggregationWindow()==null){
                                    VehicleAggregationWindow vehicleAggregationWindow = new VehicleAggregationWindow();
                                    state.setVehicleAggregationWindow(vehicleAggregationWindow);
                                }
                                state.getVehicleAggregationWindow().setOvertime4hCount(state.getVehicleAggregationWindow().getOvertime4hCount() +1);
                                beanReportMessageProcessor.processMessage(drivingOver4h);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    state.setDrivingOver4h(null);
                }

                DrivingOver4h drivingOver4h = state.getDrivingOver4h();
                if (drivingOver4h == null) {
                    // TODO: chu y can thay doi - drivingBegin4h = state.getTime();
                    drivingOver4h = new DrivingOver4h();
                    drivingOver4h.setBeginTime(state.getTime());
                    drivingOver4h.setBeginX(state.getX());
                    drivingOver4h.setBeginY(state.getY());
                    drivingOver4h.setBeginAddress(state.getAddress());
                    state.setDrivingOver4h(drivingOver4h);
                }

                if ((1000L * msg.getDatetime() - drivingOver4h.getBeginTime().getTime()) > 14400000L) { // > 4h
                    //Update end time driver over
                    drivingOver4h.setEndTime(new Date(1000L * msg.getDatetime())); // state.getTime()
                    drivingOver4h.setEndX(msg.getX());
                    drivingOver4h.setEndY(msg.getY());
                    state.setDrivingOver4h(drivingOver4h);
                }
            } catch (Exception e) {
                log.error("drivingOver4h:" + e.getMessage());
            }

            // 10h
            try {
                if(state.getDrivingOver4h()!=null){
                    int drivingTime = (int) ((1000L * msg.getDatetime() - state.getDrivingOver4h().getBeginTime().getTime())/1000); // dang lai
                    state.setTotalTimeDrivingToday(state.getTotalTimeDriveFinish() + drivingTime);

                    if (state.getTotalTimeDrivingToday() > 36000) { // qua 10h ~ 10*60*60s
                        DrivingOver10h drivingOver10h = state.getDrivingOver10h();
                        if (drivingOver10h == null) {
                            // TODO: chu y can thay doi - drivingBegin4h = state.getTime();
                            drivingOver10h = new DrivingOver10h();
                            drivingOver10h.setBeginTime(state.getTime());
                            drivingOver10h.setBeginX(state.getX());
                            drivingOver10h.setBeginY(state.getY());
                            drivingOver10h.setBeginAddress(state.getAddress());
                            state.setDrivingOver10h(drivingOver10h);
                        }
                        //Update end time driver over 10h
                        drivingOver10h.setEndTime(new Date(1000L * msg.getDatetime())); // state.getTime()
                        drivingOver10h.setEndX(msg.getX());
                        drivingOver10h.setEndY(msg.getY());
                    }
                }
            } catch (Exception e) {
                log.error("getDrivingOver4h:" + e.getMessage());
            }
        }
        /**********************************************************/
		// update status
		state.setX(msg.getX());
		state.setY(msg.getY());

		if (msg.hasDatetime()) {
			state.setTime(new Date(1000L * msg.getDatetime()));
		}

		if (msg.hasIgnition()) {
			state.setIgnition(msg.getIgnition());
		}

		if (msg.hasSpeed()) {
			state.setSpeed(msg.getSpeed());
		}

		switch (state.getPrevStatus()) {
		case STATUS_MOVING_POSSIBLE:
		case STATUS_STOP_POSSIBLE:
			if (msg.hasSpeed()) {
				if (msg.getSpeed() < 3) {
					this.changeStatus(state, STATUS_STOP_POSSIBLE);
				}
			}

			break;
		default:
            this.changeStatus(state, STATUS_UNKNOW);
		}
	}

    private void processOS(FmsProto.WayPoint msg, OverSpeed os, VehicleState state){
        int duration = (int) (msg.getDatetime() - (os.getBeginTime().getTime() / 1000L));
        os.setEndTime(new Date(1000L * msg.getDatetime()));
        os.setEndX(msg.getX());
        os.setEndY(msg.getY());
        os.setDuration(duration);

        // caculate distance for the overspeed
        os.setDistance(os.getDistance() +(float)GeoUtil.distanceInMeters(msg.getX(), msg.getY(), state.getX(), state.getY()));
        if (Double.isNaN(os.getDistance())) {
            os.setDistance(0);
        }

        if(state.getReport()!=null){
            os.setId(state.getReport().getOs());
            os.setVehicleAggregationId(state.getVehicleAggregation());
        }

        if (state.getCompanyAggregationId() != null) {
            os.setCompany(state.getCompanyId());
            os.setCompanyAggregationId(state.getCompanyAggregationId());
        }

        os.setLicenceKey(state.getDriver());
        os.setDriverName(state.getDriverName());
        os.setLicenceKey(state.getLicenceKey());
        os.setDriverAggregationId(state.getDriverAggregation());

        /**********************************************************/
        ////////////////////////////////////////
        // GPS Noise Detection
        ////////////////////////////////////////
        boolean isGPSNoised = false;

        if (os.getMaxSpeed() > 150) {
            isGPSNoised = true;
        } else if ((os.getDuration() <= 3) && ((os.getMaxSpeed() - msg.getSpeed()) > 35)) {
            isGPSNoised = true;
        } else if ((os.getOsKm()/(os.getDuration()/3600d)) > 150) {
            isGPSNoised = true;
        } else if ((((state.getTime().getTime()/1000) - msg.getDatetime()) < 3) && (msg.getSpeed() < 3)) {
            isGPSNoised = true;
        } else if (os.getSpeedLimit() < 50) {
            isGPSNoised = true;
        }

        /**********************************************************/
        if (!isGPSNoised) {
            if (os.getDuration() >= MIN_DURATION_OVERSPEED_TT08) {
                String strBeginAddress = this.beanXlsClient.reverseGeocode(os.getBeginX(), os.getBeginY(),state.getCompanyId());
                if(strBeginAddress!=null){
                    if(strBeginAddress.length()>10){
                        os.setBeginAddress(strBeginAddress);
                    }
                }

                String strEndAddress = this.beanXlsClient.reverseGeocode(os.getEndX(), os.getEndY(),state.getCompanyId());
                if(strEndAddress!=null){
                    if(strEndAddress.length()>10){
                        os.setEndAddress(strEndAddress);
                    }
                }

                beanReportMessageProcessor.processMessage(os);
            }
        }

        state.setOverspeed(null);
    }

    public void changeStatus(VehicleState state, int newStatus) {
        state.setPrevStatus(state.getStatus());
        state.setStatus(newStatus);

        if (newStatus == STATUS_STOP_POSSIBLE) {
            state.setStop(new Stop());
            state.getStop().setVehicle(state.getVehicle());
            state.getStop().setBeginTime(state.getTime());
            state.getStop().setEndTime(state.getTime());
            state.getStop().setX(state.getX());
            state.getStop().setY(state.getY());
        } else if (newStatus == STATUS_STOP) {
            Trip trip = state.getTrip();
            if (trip != null) {
                if(trip.getDistanceGPS()>TRIP_RADIUS) {
                    trip.setEndTime(state.getStop().getBeginTime());
                    trip.setEndX(state.getStop().getX());
                    trip.setEndY(state.getStop().getY());

                    String strBeginAddress = this.beanXlsClient.reverseGeocode(trip.getBeginX(), trip.getBeginY(), state.getCompanyId());
                    if (strBeginAddress != null) {
                        if (strBeginAddress.length() > 10) {
                            trip.setBeginAddress(strBeginAddress);
                        }
                    }

                    String strEndAddress = this.beanXlsClient.reverseGeocode(trip.getEndX(), trip.getEndY(), state.getCompanyId());
                    if (strEndAddress != null) {
                        if (strEndAddress.length() > 10) {
                            trip.setEndAddress(strEndAddress);
                        }
                    }
                    doNewTrip(state);
                }
            } else {
                log.debug("VehicleState.changeStatus: (debug - newStatus STATUS_STOP) trip is null?");
            }
        } else if (newStatus == STATUS_MOVING_POSSIBLE) {
            if (state.getPrevStatus() == STATUS_STOP) {
                Trip trip = new Trip();
                trip.setVehicle(state.getVehicle());
                trip.setBeginTime(state.getTime());
                trip.setBeginX(state.getX());
                trip.setBeginY(state.getY());
                trip.setEndX(state.getX());
                trip.setEndY(state.getY());
                trip.setEndTime(state.getTime());
                state.setTrip(trip);

                //Danh dau thoi gian bat dau lai xe 4h
                if(state.getDrivingOver4h()==null){
                    DrivingOver4h drivingOver4h = new DrivingOver4h();
                    drivingOver4h.setBeginTime(state.getTime());
                    drivingOver4h.setBeginX(state.getX());
                    drivingOver4h.setBeginY(state.getY());
                    drivingOver4h.setBeginAddress(state.getAddress());
                    state.setDrivingOver4h(drivingOver4h);
                }

                if(state.getDrivingOver10h()==null){
                    DrivingOver10h drivingOver10h = new DrivingOver10h();
                    drivingOver10h.setBeginTime(state.getTime());
                    drivingOver10h.setBeginX(state.getX());
                    drivingOver10h.setBeginY(state.getY());
                    drivingOver10h.setVehicle(state.getVehicle());
                    drivingOver10h.setBeginAddress(state.getAddress());
                    state.setDrivingOver10h(drivingOver10h);
                }
            }
        } else if (newStatus == STATUS_MOVING) {
            // xac dinh thoi gian bat dau lai xe cho 4h
            if ((state.getPrevStatus() != STATUS_STOP_POSSIBLE)) {
                if (state.getStop() == null) {
                    Stop newStop = new Stop();
                    state.setStop(newStop);
                }

                Trip trip = state.getTrip();
                if (trip != null) {
                    state.getStop().setEndTime(trip.getBeginTime());
                    state.getStop().setX(trip.getBeginX());
                    state.getStop().setY(trip.getBeginY());

                    if ((state.getStop().getBeginTime() != null)&&(state.getStop().getEndTime() != null)){
                        try {
                            String strAddress = this.beanXlsClient.reverseGeocode(state.getStop().getX(), state.getStop().getY(),state.getCompanyId());
                            if (strAddress != null) {
                                if (strAddress.length() > 10) {
                                    state.getStop().setAddress(strAddress);
                                }
                            }
                            doNewStop(state);
                        } catch (Exception e) {
                            log.error("Exception", e);
                        }
                    }
                }
            }
        }
    }

    private void processXmit(VehicleState state, FmsProto.WayPoint wayPoint) {
        Date msgDate = new Date(1000L * wayPoint.getDatetime());
        if ((state.getTime() != null) && (msgDate != null)) {

            if(msgDate.getTime() - state.getTime().getTime()>120000){// 2*60*1000 = 120000
                DataXmit xmit = new DataXmit();
                xmit.setId(state.getReport().getXmit());
                xmit.setVehicle(state.getVehicle());
                xmit.setBeginTime(state.getTime());
                xmit.setEndTime(msgDate);
                xmit.setType(DATA_XMIT_MSG_OFF);
                xmit.setCompany(state.getCompanyId());
                xmit.setVehicleAggregationId(state.getVehicleAggregation());
                xmit.setCompanyAggregationId(state.getCompanyAggregationId());
                xmit.setBatchAggregationId(state.getBatchAggregationId());

                this.beanReportMessageProcessor.processMessage(xmit);
            }

            //Mat tin hieu GPS
            if(!validateWayPoint(wayPoint)){
                DataXmit xmit = state.getXmit();
                if(xmit==null){
                    xmit = new DataXmit();
                    xmit.setId(state.getReport().getXmit());
                    xmit.setVehicle(state.getVehicle());
                    xmit.setCompany(state.getCompanyId());
                    xmit.setBeginTime(msgDate);
                    xmit.setEndTime(msgDate);
                    xmit.setVehicleAggregationId(state.getVehicleAggregation());
                    xmit.setCompanyAggregationId(state.getCompanyAggregationId());
                    xmit.setBatchAggregationId(state.getBatchAggregationId());
                    xmit.setType(DATA_XMIT_GPS_OFF);
                }else{
                    xmit.setEndTime(msgDate);
                }

                state.setXmit(xmit);
                state.setTime(msgDate);
            }else{
                DataXmit xmit = state.getXmit();
                if(xmit!=null){
                    xmit.setEndTime(msgDate);
                    if((xmit.getEndTime().getTime() - xmit.getBeginTime().getTime())>=120000){//2*60*1000
                        this.beanReportMessageProcessor.processMessage(xmit);
                    }
                    //Reset Xmit
                    state.setXmit(null);
                }
            }

            // FOR VEHICLE
            // processing and saving local without sending messages to aggregating server
            if (DateUtils.isSameDay(msgDate, state.getTime())) {
                state.setXmitCount(state.getXmitCount() + 1);

                Date xmitDate = state.getXmitUpdate();
                if (xmitDate == null) {
                    xmitDate = new Date(msgDate.getTime());
                    state.setXmitUpdate(xmitDate);
                }

                Date now = new Date();
                if (now.getTime() - xmitDate.getTime() > 1800000) { // 30minute
                    //[XMIT]
                    if (state.getXmitCount() > 0) {
                        VehicleXmitAggregationWindow vehicleXmitAggregationWindow = new VehicleXmitAggregationWindow();
                        vehicleXmitAggregationWindow.setId(state.getVehicleAggregation());
                        vehicleXmitAggregationWindow.setVehicle(state.getVehicle());
                        vehicleXmitAggregationWindow.setDate(state.getTime());
                        vehicleXmitAggregationWindow.setCompany(state.getCompanyId());
                        vehicleXmitAggregationWindow.setMsgCount(state.getXmitCount());
                        vehicleXmitAggregationWindow.setCompanyAggregationId(state.getCompanyAggregationId());
                        vehicleXmitAggregationWindow.setVehicleAggregationId(state.getVehicleAggregation());
                        this.beanReportMessageProcessor.processMessage(vehicleXmitAggregationWindow);
                    }

                    state.setXmitUpdate(now);
                    state.setXmitCount(0);
                }
            } else {
                //[XMIT]
                if (state.getXmitCount() > 0) {
                    VehicleXmitAggregationWindow vehicleXmitAggregationWindow = new VehicleXmitAggregationWindow();
                    vehicleXmitAggregationWindow.setId(state.getVehicleAggregation());
                    vehicleXmitAggregationWindow.setVehicle(state.getVehicle());
                    vehicleXmitAggregationWindow.setDate(state.getTime());
                    vehicleXmitAggregationWindow.setMsgCount(state.getXmitCount());
                    vehicleXmitAggregationWindow.setCompanyAggregationId(state.getCompanyAggregationId());
                    vehicleXmitAggregationWindow.setVehicleAggregationId(state.getVehicleAggregation());
                    this.beanReportMessageProcessor.processMessage(vehicleXmitAggregationWindow);
                }
                //Check DrivingOver4h
                DrivingOver4h drivingOver4h = state.getDrivingOver4h();
                if(drivingOver4h!=null) {
                    if (drivingOver4h.getEndTime() != null) {
                        drivingOver4h.setLicenceKey(state.getDriver());
                        drivingOver4h.setDriverName(state.getDriverName());
                        drivingOver4h.setDriverAggregationId(state.getDriverAggregation());
                        drivingOver4h.setId(state.getReport().getOver4h());
                        drivingOver4h.setVehicle(state.getVehicle());
                        drivingOver4h.setVehicleAggregationId(state.getVehicleAggregation());
                        drivingOver4h.setCompany(state.getCompanyId());
                        drivingOver4h.setCompanyAggregationId(state.getCompanyAggregationId());

                        String strAddress = this.beanXlsClient.reverseGeocode(drivingOver4h.getEndX(), drivingOver4h.getEndY(),state.getCompanyId());
                        if(strAddress!=null) {
                            if (strAddress.length() > 10) {
                                drivingOver4h.setEndAddress(strAddress);
                            }
                        }

                        if(state.getVehicleAggregationWindow()==null){
                            VehicleAggregationWindow vehicleAggregationWindow = new VehicleAggregationWindow();
                            state.setVehicleAggregationWindow(vehicleAggregationWindow);
                        }
                        state.getVehicleAggregationWindow().setOvertime4hCount(state.getVehicleAggregationWindow().getOvertime4hCount() +1);
                        beanReportMessageProcessor.processMessage(drivingOver4h);
                    }
                    //Reset DrivingOver4h
                    state.setDrivingOver4h(null);
                }

                //Check DrivingOver10h
                DrivingOver10h drivingOver10h =state.getDrivingOver10h();
                if(drivingOver10h!=null) {
                    drivingOver10h.setId(state.getReport().getOver10h());
                    drivingOver10h.setVehicle(state.getVehicle());
                    drivingOver10h.setVehicleAggregationId(state.getVehicleAggregation());
                    drivingOver10h.setCompanyAggregationId(state.getCompanyAggregationId());
                    drivingOver10h.setLicenceKey(state.getDriver());
                    drivingOver10h.setDriverName(state.getDriverName());
                    drivingOver10h.setDriverAggregationId(state.getDriverAggregation());
                    drivingOver10h.setCompany(state.getCompanyId());
                    if (drivingOver10h.getEndTime() != null) {
                        String strAddress = this.beanXlsClient.reverseGeocode(drivingOver10h.getEndX(), drivingOver10h.getEndY(),state.getCompanyId());
                        if(strAddress!=null) {
                            if (strAddress.length() > 10) {
                                drivingOver10h.setEndAddress(strAddress);
                            }
                        }
                        beanReportMessageProcessor.processMessage(drivingOver10h);
                    }
                    //Reset DrivingOver10h
                    state.setDrivingOver10h(null);
                }

                // sang ngay moi thi reset lai thoi gian lai xe
                state.setTotalTimeDriveFinish(0);
                state.setTotalTimeDrivingToday(0);
                Date now = new Date();
                state.setXmitUpdate(now);
                state.setTime(msgDate);
                state.setXmitCount(1);
                state.setVehicleAggregationWindow(null);
            }
        }
    }

    private void  geofenceProcess(VehicleState state, FmsProto.WayPoint msg){
        double x = msg.getX();
        double y = msg.getY();
        Charging charging = beanFmsDao.findCharging(x, y);
        if(charging!=null){
            GeoPoi  geoPoi = state.getGeoPoi();
            if(geoPoi==null){
                geoPoi = new GeoPoi();
                geoPoi.setName(charging.getName().trim());
                geoPoi.setAddress(charging.getAddress().trim());
                geoPoi.setTimeIn(new Date(msg.getDatetime() * 1000L));
                geoPoi.setDriverName(state.getDriverName().trim());
                geoPoi.setVehicle(state.getVehicle());
                geoPoi.setX(charging.getX());
                geoPoi.setY(charging.getY());
            }
            geoPoi.setTimeOut(new Date(msg.getDatetime() * 1000L));
            state.setGeoPoi(geoPoi);
        }else {
            GeoPoi  geoPoi = state.getGeoPoi();
            if(geoPoi!=null){
                geoPoi.setId(state.getReport().getGeoPoi());
                beanReportMessageProcessor.processMessage(geoPoi);
                state.setGeoPoi(null);
            }
        }
    }

    private void updateCurrentDriver(VehicleState state, FmsProto.WayPoint msg) {
        if (msg.hasDriver()) {
            String newDriverId = msg.getDriver();
            if(RegexUtil.validateDriver(newDriverId)){
                String prevDriver = state.getDriver();
                if (StringUtils.isNotEmpty(prevDriver)){
                    if (!prevDriver.equals(newDriverId)) {
                        state.setDriver(newDriverId);
                        String driverName    = "Chưa cập nhật";
                        String driverPhone   = "Chưa cập nhật";
                        String licenceKey    = "Chưa cập nhật";
                        String driverAggregationId = null;
                        Driver driver = beanFmsDao.getDriver(newDriverId);
                        if(driver==null){
                            Vehicle vehicle = beanFmsDao.getVehicle(state.getVehicle());
                            if(vehicle!=null){
                                driver = vehicle.getDriver();
                            }
                        }

                        if (driver != null) {
                            driverName  = driver.getName();
                            driverPhone = driver.getPhone();
                            licenceKey  = driver.getLicenceKey();
                            driverAggregationId = driver.getAggregation();
                        }

                        state.setDriver(newDriverId);
                        state.setDriverName(driverName);
                        state.setDriverPhone(driverPhone);
                        state.setLicenceKey(licenceKey);
                        state.setDriverAggregation(driverAggregationId);
                        // RESET phat hien vi pham thoi gian lai xe cho lai xe hien tai vi thay doi lai xe moi
                        //Check DrivingOver4h
                        DrivingOver4h drivingOver4h = state.getDrivingOver4h();
                        if(drivingOver4h!=null) {
                            if (drivingOver4h.getEndTime() != null) {
                                drivingOver4h.setDriver(state.getDriver());
                                drivingOver4h.setLicenceKey(state.getDriver());
                                drivingOver4h.setDriverName(state.getDriverName());
                                drivingOver4h.setDriverAggregationId(state.getDriverAggregation());

                                drivingOver4h.setId(state.getReport().getOver4h());
                                drivingOver4h.setVehicle(state.getVehicle());
                                drivingOver4h.setVehicleAggregationId(state.getVehicleAggregation());
                                drivingOver4h.setCompany(state.getCompanyId());
                                drivingOver4h.setCompanyAggregationId(state.getCompanyAggregationId());
                                String strEndAddress =this.beanXlsClient.reverseGeocode(drivingOver4h.getEndX(), drivingOver4h.getEndY(),state.getCompanyId());
                                if(strEndAddress!=null) {
                                    if (strEndAddress.length() > 10) {
                                        drivingOver4h.setEndAddress(strEndAddress);
                                    }
                                }

                                if(state.getVehicleAggregationWindow()==null){
                                    VehicleAggregationWindow vehicleAggregationWindow = new VehicleAggregationWindow();
                                    state.setVehicleAggregationWindow(vehicleAggregationWindow);
                                }
                                state.getVehicleAggregationWindow().setOvertime4hCount( state.getVehicleAggregationWindow().getOvertime4hCount()+1);
                                beanReportMessageProcessor.processMessage(drivingOver4h);
                            }
                            //Reset DrivingOver4h
                            state.setDrivingOver4h(null);
                        }

                        state.setTotalTimeDriveFinish(0);
                        state.setTotalTimeDrivingToday(0);
                    }
                } else {
                    state.setDriver(newDriverId);
                    Driver driver = beanFmsDao.getDriver(newDriverId);
                    if(driver==null) {
                        Vehicle vehicle = beanFmsDao.getVehicle(state.getVehicle());
                        if(vehicle!=null){
                            driver = vehicle.getDriver();
                        }
                    }
                    if(driver!=null){
                        if(RegexUtil.validateDriver(driver.getId())) {
                            state.setDriver(driver.getId());
                            state.setDriverName(driver.getName());
                            state.setLicenceKey(driver.getLicenceKey());
                            state.setDriverAggregation(driver.getAggregation());
                        }
                    }
                }
            }else{
                String current = state.getDriver();
                if(StringUtils.isEmpty(current)){
                    Driver driver = null;
                    Vehicle vehicle = beanFmsDao.getVehicle(state.getVehicle());
                    if(vehicle!=null){
                        driver = vehicle.getDriver();
                    }

                    if(driver!=null){
                        if(RegexUtil.validateDriver(driver.getId())) {
                            state.setDriver(driver.getId());
                            state.setDriverName(driver.getName());
                            state.setLicenceKey(driver.getLicenceKey());
                            state.setDriverAggregation(driver.getAggregation());
                        }
                    }
                }
            }
        }
    }

    private void doNewStop(VehicleState state) {
        Stop stop = state.getStop();
        try {
            if(stop.getDuration()>180) {//3*60
                String strAddress = this.beanXlsClient.reverseGeocode(state.getStop().getX(), state.getStop().getY(), state.getCompanyId());
                if (strAddress != null) {
                    if (strAddress.length() > 5) {
                        stop.setAddress(strAddress);
                    }
                }

                if (state.getReport() != null) {
                    stop.setId(state.getReport().getStop());
                    stop.setVehicle(state.getVehicle());
                    stop.setCompany(state.getCompanyId());
                    stop.setVehicleAggregationId(state.getVehicleAggregation());
                    stop.setCompanyAggregationId(state.getCompanyAggregationId());
                }

                if (state.getDriver() != null) {
                    stop.setDriver(state.getDriver());
                    stop.setDriverName(state.getDriverName());
                    stop.setLicenceKey(state.getLicenceKey());
                    stop.setDriverAggregationId(state.getDriverAggregation());
                }

                if (state.getVehicleAggregationWindow() == null) {
                    VehicleAggregationWindow vehicleAggregationWindow = new VehicleAggregationWindow();
                    state.setVehicleAggregationWindow(vehicleAggregationWindow);
                }
                state.getVehicleAggregationWindow().setStopCount(state.getVehicleAggregationWindow().getStopCount() + 1);
                state.getVehicleAggregationWindow().setStopDuration(state.getVehicleAggregationWindow().getStopDuration() + stop.getDuration());
                beanReportMessageProcessor.processMessage(stop);
            }
        } catch (Exception e) {
            log.error("doNewStop.ex:" + e.getMessage());
        }
    }

    private void doNewTrip(VehicleState state) {
        Trip trip = state.getTrip();
        if(trip.getDistanceGPS()>TRIP_RADIUS) {
            if (state.getReport() != null) {
                trip.setId(state.getReport().getTrip());
                trip.setVehicle(state.getVehicle());
                trip.setVehicleAggregationId(state.getVehicleAggregation());
                trip.setCompany(state.getCompanyId());
                trip.setCompanyAggregationId(state.getCompanyAggregationId());
                trip.setDriverAggregationId(state.getDriverAggregation());
            }

            trip.setDriver(state.getDriver());
            trip.setDriverName(state.getDriverName());
            trip.setLicenceKey(state.getLicenceKey());
            trip.setDriverAggregationId(state.getDriverAggregation());

            if (state.getVehicleAggregationWindow() == null) {
                VehicleAggregationWindow vehicleAggregationWindow = new VehicleAggregationWindow();
                state.setVehicleAggregationWindow(vehicleAggregationWindow);
            }
            state.getVehicleAggregationWindow().setTripCount(state.getVehicleAggregationWindow().getTripCount() + 1);
            state.getVehicleAggregationWindow().setTripKm(FmsUtil.getShortDouble(state.getVehicleAggregationWindow().getTripKm()) + FmsUtil.getShortDouble(trip.getTripKm()));

            beanReportMessageProcessor.processMessage(trip);
        }
    }

    public void updateOwnerFromDB(VehicleState state) {

        Vehicle vehicle = beanFmsDao.getVehicle(state.getVehicle());
        if (vehicle != null) {
            Report report = beanFmsDao.getReport(vehicle.getId());
            if(report!=null){
                state.setReport(report);
                state.setVehicleAggregation(report.getAggregation());
            }
            if(vehicle.getType()!=null){
                state.setTransportType(vehicle.getType().getName());
            }

            if(vehicle.getCapacity()!=null){
                state.setCapacity(vehicle.getCapacity().toString());
            }

            Device device = beanFmsDao.getDevice(state.getDevice());
            if(device!=null){
                Batch batch = device.getBatch();
                if(batch!=null){
                    batch = beanFmsDao.getBatch(batch.getId());
                    if(batch!=null) {
                        state.setBatchAggregationId(batch.getKonexyId());
                    }
                }

            }
            if(vehicle.getSpeedLimit()>0) {
                state.setSpeedLimit(vehicle.getSpeedLimit());
            }else {
                state.setSpeedLimit(80);//default
            }

            Company company = vehicle.getCompany();
            if (company != null) {
                state.setCompanyName(company.getName());
                state.setCompanyId(company.getId());
                state.setCompanyAggregationId(company.getAggregation());
                if(company.getProvince()!=null) {
                    state.setProvinceName(company.getProvince().getName());
                }
            }
            /////////////////////////////////////
            // update Default Driver
            String currentDriver = state.getDriver();
            Driver driver = beanFmsDao.getDriver(currentDriver);
            if(driver==null){
               driver = vehicle.getDriver();
            }

            if (driver != null) {
                if(RegexUtil.validateDriver(driver.getId())) {
                    state.setDriver(driver.getId());
                    state.setDriverName(driver.getName());
                    state.setDriverPhone(driver.getPhone());
                    state.setLicenceKey(driver.getLicenceKey());
                    state.setDriverAggregation(driver.getAggregation());
                }
            } else {
                state.setDriver(UNKNOW_DEFAULT_DRIVER);
                state.setDriverName(DEFAULT_DRIVER_NAME);
                state.setDriverPhone(DEFAULT_DRIVER_NAME);
            }
        }
    }

    private FmsProto.WayPoint[] convertWayPointBatch2WayPoints(FmsProto.WayPointBatch wpb) {
        FmsProto.WayPoint[] wayPoints = null;

        try {
            //Neu xe dung chi lay 1 ban tin trong WayPointBatch de su ly
            FmsProto.WayPointBatch.TrackPoint firstTrackPoint = wpb.getPoint(0);
            int size = wpb.getPointCount();
            wayPoints = new FmsProto.WayPoint[size];
            for (int i = 0; i < size; i++) {
                FmsProto.WayPointBatch.TrackPoint trackPoint = wpb.getPoint(i);
                FmsProto.WayPoint.Builder wpBuilder = FmsProto.WayPoint.newBuilder();

                wpBuilder.setDatetime(trackPoint.getDatetime());
                wpBuilder.setDriver(wpb.getDriver());
                wpBuilder.setVehicle(wpb.getVehicle());
                wpBuilder.setDevice(wpb.getDevice());
                wpBuilder.setAdc0(wpb.getAdc0());
                wpBuilder.setAdc1(wpb.getAdc1());
                wpBuilder.setDoc(wpb.getDoc());
                wpBuilder.setIgnition(wpb.getIgnition());
                wpBuilder.setDoor(wpb.getDoor());
                wpBuilder.setAircon(wpb.getAircon());
                wpBuilder.setEx1(wpb.getEx1());
                wpBuilder.setEx2(wpb.getEx2());
                wpBuilder.setEx3(wpb.getEx3());

                wpBuilder.setX(trackPoint.getX());
                wpBuilder.setY(trackPoint.getY());
                wpBuilder.setSpeed(trackPoint.getSpeed());
                wayPoints[i] = wpBuilder.build();
            }

        } catch (Exception e) {
            log.error("convertWayPointBatch2WayPoints.ex:" + e.getMessage());
        }

        return wayPoints;
    }

    private List<TrackPoint> convertWayPointBatch2TrackPoints(FmsProto.WayPointBatch wpb) {
        List<TrackPoint> lstTrackPoint = new ArrayList<>();

        try {
            FmsProto.WayPointBatch.TrackPoint firstTrackPoint = wpb.getPoint(0);
            int size = wpb.getPointCount();
            for (int i = 0; i < size; i++) {
                FmsProto.WayPointBatch.TrackPoint trackPoint = wpb.getPoint(i);
                TrackPoint tp = new TrackPoint();
                tp.setUnixTime(trackPoint.getDatetime());
                tp.setX(trackPoint.getX());
                tp.setY(trackPoint.getY());
                tp.setSpeed((int)trackPoint.getSpeed());
                lstTrackPoint.add(tp);
            }

        } catch (Exception e) {
            log.error("convertWayPointBatch2WayPoints.ex:" + e.getMessage());
        }

        return lstTrackPoint;
    }

    private List<TrackPoint> convertWayPoint2TrackPoints(FmsProto.WayPoint wpb) {
        List<TrackPoint> lstTrackPoint = new ArrayList<>();
        TrackPoint tp = new TrackPoint();
        tp.setUnixTime(wpb.getDatetime());
        tp.setX(wpb.getX());
        tp.setY(wpb.getY());
        tp.setSpeed((int)wpb.getSpeed());
        lstTrackPoint.add(tp);

        return lstTrackPoint;
    }

    private boolean validateWayPoint(FmsProto.WayPoint msg) {
        if (msg.hasX() && msg.hasY()) {
            if ((msg.getX() < 1) && (msg.getY() < 1)) {
                return false;
            }
        }

        return true;
    }

}
