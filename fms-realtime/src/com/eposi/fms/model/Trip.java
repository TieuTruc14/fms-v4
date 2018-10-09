package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Trip extends AbstractMessage implements IMessage,Serializable{
	private static final long serialVersionUID = -7395169875836304578L;

	private String vehicle;
	private Date beginTime;
	private double beginX;
	private double beginY;
	private String beginAddress;

	private Date endTime;
	private double endX;
	private double endY;
	private String endAddress;

	private float distanceGPS;
	private int maxSpeedGPS;

    private String driver;
    private String licenceKey;
	private String driverName;

    private String company;


	public int getMaxSpeedGPS() {
		return maxSpeedGPS;
	}

	public void setMaxSpeedGPS(int maxSpeedGPS) {
		this.maxSpeedGPS = maxSpeedGPS;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public double getBeginX() {
		return beginX;
	}

	public void setBeginX(double beginX) {
		this.beginX = beginX;
	}

	public double getBeginY() {
		return beginY;
	}

	public void setBeginY(double beginY) {
		this.beginY = beginY;
	}

	public String getBeginAddress() {
		return beginAddress;
	}

	public void setBeginAddress(String beginAddress) {
		this.beginAddress = beginAddress;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public double getEndX() {
		return endX;
	}

	public void setEndX(double endX) {
		this.endX = endX;
	}

	public double getEndY() {
		return endY;
	}

	public void setEndY(double endY) {
		this.endY = endY;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public float getDistanceGPS() {
		return distanceGPS;
	}

	public void setDistanceGPS(float distanceGPS) {
		this.distanceGPS = distanceGPS;
	}

	public float getTripKm(){
		return distanceGPS/1000.0f;
	}

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }


	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

	// /////////
	public long getDuration() {
		long d = 0;
		try {
			d = (endTime.getTime() - beginTime.getTime());
			d = d / 1000;
		} catch (Exception e) {
			e.printStackTrace();
			if(beginTime==null){
				System.out.println("Trip.getDuration.ex:beginTime is null");
			}
			if(endTime==null){
				System.out.println("Trip.getDuration.ex:endTime is null");
			}
		}
		return d;
	}

	public int getMessageType() {
		return MESSAGE_TYPE_TRIP;
	}
}
