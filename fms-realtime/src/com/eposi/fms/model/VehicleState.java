package com.eposi.fms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleState implements Serializable {
	private static final long serialVersionUID = -6296528481590052181L;

	private String vehicle;
    private String device;
    private String vehicleAggregation  = null;//Using for summary vehicle report
    private String transportType;
    private String capacity;

    // Hold the owner to avoid request to DB too much!
    private String companyId  = null; //using for revergeocode
    private String companyAggregationId  = null;//Using for summary company report
    private String companyName   = null;
    private String provinceName;

    private String batchAggregationId  = null;

    private int lastSyncDb = 0; // unix time

	private Date time = null; // last status of message received
	private double x; // last longitude
	private double y; // last latitude
	private float  speed;
	private boolean ignition = false;
	private boolean door = false;
	private String address = "";
    private List<TrackPoint> trackPointList = new ArrayList<>();

    private Date lastServerReceivedMessage = new Date(); // server time
    private int lastPutToCache = 0; // unix time

    /************************************************************/
    private String driver = ""; // last driver
    private String driverName  = null;
    private String licenceKey  = null;
    private String driverPhone = null;
    private String driverAggregation = null;

    private DrivingOver4h  drivingOver4h  = null;
    private DrivingOver10h drivingOver10h = null;
    private int totalTimeDriveFinish     = 0;  // tong thoi gian da lai trong ngay
    private int totalTimeDrivingToday    = 0; // in seconds, to check over 10h, tong thoi gian lai xe trong ngay = totalTimeDriveFinish + drivingBegin4h
	/************************************************************/
	private int status = 0; // STATUS_UNKNOW
	private int prevStatus = 0;// STATUS_UNKNOW

	/************************************************************/
	private Stop stop;
	private Trip trip;
	private OverSpeed overspeed;
    private int speedLimit = 80;
    /*************************************************************/
    private Report   report;
    /*************************************************************/
    private GeoPoi geoPoi;
    /*************************************************************/
    private DataXmit xmit;
    private int      xmitCount;  // count number of msg since xmitUpdate
    private Date     xmitUpdate; // last time update to the storage

    private VehicleAggregationWindow vehicleAggregationWindow = new VehicleAggregationWindow(); // for reporting

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public int getXmitCount() {
        return xmitCount;
    }

    public void setXmitCount(int xmitCount) {
        this.xmitCount = xmitCount;
    }

    public Date getXmitUpdate() {
        return xmitUpdate;
    }

    public void setXmitUpdate(Date xmitUpdate) {
        this.xmitUpdate = xmitUpdate;
    }

    public DataXmit getXmit() {
        return xmit;
    }

    public void setXmit(DataXmit xmit) {
        this.xmit = xmit;
    }

    public VehicleAggregationWindow getVehicleAggregationWindow() {
        return vehicleAggregationWindow;
    }

    public void setVehicleAggregationWindow(VehicleAggregationWindow vehicleAggregationWindow) {
        this.vehicleAggregationWindow = vehicleAggregationWindow;
    }

    public int getTotalTimeDriveFinish() {
        return totalTimeDriveFinish;
    }

    public void setTotalTimeDriveFinish(int totalTimeDriveFinish) {
        this.totalTimeDriveFinish = totalTimeDriveFinish;
    }

    public DrivingOver4h getDrivingOver4h() {
        return drivingOver4h;
    }

    public void setDrivingOver4h(DrivingOver4h drivingOver4h) {
        this.drivingOver4h = drivingOver4h;
    }

    public DrivingOver10h getDrivingOver10h() {
        return drivingOver10h;
    }

    public void setDrivingOver10h(DrivingOver10h drivingOver10h) {
        this.drivingOver10h = drivingOver10h;
    }

    public int getTotalTimeDrivingToday() {
        return totalTimeDrivingToday;
    }

    public void setTotalTimeDrivingToday(int totalTimeDrivingToday) {
        this.totalTimeDrivingToday = totalTimeDrivingToday;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverAggregation() {
        return driverAggregation;
    }

    public void setDriverAggregation(String driverAggregation) {
        this.driverAggregation = driverAggregation;
    }

    public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

    public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

    public List<TrackPoint> getTrackPointList() {
        return trackPointList;
    }

    public void setTrackPointList(List<TrackPoint> trackPointList) {
        this.trackPointList = trackPointList;
    }

    public int getPrevStatus() {
		return prevStatus;
	}

	public void setPrevStatus(int prevStatus) {
		this.prevStatus = prevStatus;
	}

	public boolean isIgnition() {
		return ignition;
	}

	public void setIgnition(boolean ignition) {
		this.ignition = ignition;
	}

	public boolean isDoor() {
		return door;
	}

	public void setDoor(boolean door) {
		this.door = door;
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public OverSpeed getOverspeed() {
		return overspeed;
	}

	public void setOverspeed(OverSpeed overspeed) {
		this.overspeed = overspeed;
	}

    public GeoPoi getGeoPoi() {
        return geoPoi;
    }

    public void setGeoPoi(GeoPoi geoPoi) {
        this.geoPoi = geoPoi;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVehicleAggregation() {
        return vehicleAggregation;
    }

    public void setVehicleAggregation(String vehicleAggregation) {
        this.vehicleAggregation = vehicleAggregation;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }


    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public Date getLastServerReceivedMessage() {
        return lastServerReceivedMessage;
    }

    public void setLastServerReceivedMessage(Date lastServerReceivedMessage) {
        this.lastServerReceivedMessage = lastServerReceivedMessage;
    }

    public int getLastPutToCache() {
        return lastPutToCache;
    }

    public void setLastPutToCache(int lastPutToCache) {
        this.lastPutToCache = lastPutToCache;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyAggregationId() {
        return companyAggregationId;
    }

    public void setCompanyAggregationId(String companyAggregationId) {
        this.companyAggregationId = companyAggregationId;
    }

    public String getBatchAggregationId() {
        return batchAggregationId;
    }

    public void setBatchAggregationId(String batchAggregationId) {
        this.batchAggregationId = batchAggregationId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getLastSyncDb() {
        return lastSyncDb;
    }

    public void setLastSyncDb(int lastSyncDb) {
        this.lastSyncDb = lastSyncDb;
    }
}
