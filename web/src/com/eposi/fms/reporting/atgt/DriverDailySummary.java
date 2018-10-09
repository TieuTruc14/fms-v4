package com.eposi.fms.reporting.atgt;

import java.io.Serializable;
import java.util.Date;

public class DriverDailySummary implements Serializable {
    private static final long serialVersionUID = 4022091550352357580L;

    private String id;
	private String driver;
    private String driverName;
    private String licenceKey;

	private Date date;

	private int sumOverSpeed;  // duration
    private int sumTripDuration; // in km
	private int maxSpeed;

    private double sumDistanceToday = 0; // in km
    private double sumOverSpeedDistance = 0; // in km

    private double sumDayPercentLT5   = 0; // Duoi 05 km/h
    private double sumDayPercent5T10  = 0; // Từ 05 km/h đến dưới 10 km/h
    private double sumDayPercent10T20 = 0; // Trên 10 km/h đến 20 km/h
    private double sumDayPercent20T35 = 0; // Trên 20 km/h đến 35 km/h
    private double sumDayPercentGT35  = 0; // Trên 35 km/h

    private short sumDayCountLT5 = 0; // Duoi 05 km/h
    private short sumDayCount5T10 = 0; //   Từ 05 km/h đến dưới 10 km/h
    private short sumDayCount10T20 = 0; // Trên 10 km/h đến 20 km/h
    private short sumDayCount20T35 = 0; // Trên 20 km/h đến 35 km/h
    private short sumDayCountGT35  = 0; // Trên 35 km/h

    private int sumDayCountStop    = 0;
    private int count4h;
    private int count10h;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
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

    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSumOverSpeed() {
		return sumOverSpeed;
	}

	public void setSumOverSpeed(int sumOverSpeed) {
		this.sumOverSpeed = sumOverSpeed;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

    public short getSumDayCount() {
        return (short) (sumDayCountLT5 + sumDayCount5T10 + sumDayCount10T20 + sumDayCount20T35 + sumDayCountGT35);
    }

    public double getSumDayPercentLT5() {
        return sumDayPercentLT5;
    }

    public void setSumDayPercentLT5(double sumDayPercentLT5) {
        this.sumDayPercentLT5 = sumDayPercentLT5;
    }

    public double getSumDayPercent5T10() {
        return sumDayPercent5T10;
    }

    public void setSumDayPercent5T10(double sumDayPercent5T10) {
        this.sumDayPercent5T10 = sumDayPercent5T10;
    }

    public double getSumDayPercent10T20() {
        return sumDayPercent10T20;
    }

    public void setSumDayPercent10T20(double sumDayPercent10T20) {
        this.sumDayPercent10T20 = sumDayPercent10T20;
    }

    public double getSumDayPercent20T35() {
        return sumDayPercent20T35;
    }

    public void setSumDayPercent20T35(double sumDayPercent20T35) {
        this.sumDayPercent20T35 = sumDayPercent20T35;
    }

    public double getSumDayPercentGT35() {
        return sumDayPercentGT35;
    }

    public void setSumDayPercentGT35(double sumDayPercentGT35) {
        this.sumDayPercentGT35 = sumDayPercentGT35;
    }

    public void setSumDayPercentGT35(float sumDayPercentGT35) {
        this.sumDayPercentGT35 = sumDayPercentGT35;
    }

    public void setSumDayPercentGT35(short sumDayPercentGT35) {
        this.sumDayPercentGT35 = sumDayPercentGT35;
    }

    public short getSumDayCountLT5() {
        return sumDayCountLT5;
    }

    public void setSumDayCountLT5(short sumDayCountLT5) {
        this.sumDayCountLT5 = sumDayCountLT5;
    }

    public short getSumDayCount5T10() {
        return sumDayCount5T10;
    }

    public void setSumDayCount5T10(short sumDayCount5T10) {
        this.sumDayCount5T10 = sumDayCount5T10;
    }

    public short getSumDayCount10T20() {
        return sumDayCount10T20;
    }

    public void setSumDayCount10T20(short sumDayCount10T20) {
        this.sumDayCount10T20 = sumDayCount10T20;
    }

    public short getSumDayCount20T35() {
        return sumDayCount20T35;
    }

    public void setSumDayCount20T35(short sumDayCount20T35) {
        this.sumDayCount20T35 = sumDayCount20T35;
    }

    public short getSumDayCountGT35() {
        return sumDayCountGT35;
    }

    public void setSumDayCountGT35(short sumDayCountGT35) {
        this.sumDayCountGT35 = sumDayCountGT35;
    }

    public int getSumDayCountStop() {
        return sumDayCountStop;
    }

    public void setSumDayCountStop(int sumDayCountStop) {
        this.sumDayCountStop = sumDayCountStop;
    }

    public double getSumDistanceToday() {
        return sumDistanceToday;
    }

    public void setSumDistanceToday(double sumDistanceToday) {
        this.sumDistanceToday = sumDistanceToday;
    }

    public double getSumOverSpeedDistance() {
        return sumOverSpeedDistance;
    }

    public void setSumOverSpeedDistance(double sumOverSpeedDistance) {
        this.sumOverSpeedDistance = sumOverSpeedDistance;
    }

    public int getSumTripDuration() {
        return sumTripDuration;
    }

    public void setSumTripDuration(int sumTripDuration) {
        this.sumTripDuration = sumTripDuration;
    }

    public int getCount4h() {
        return count4h;
    }

    public void setCount4h(int count4h) {
        this.count4h = count4h;
    }

    public int getCount10h() {
        return count10h;
    }

    public void setCount10h(int count10h) {
        this.count10h = count10h;
    }
}
