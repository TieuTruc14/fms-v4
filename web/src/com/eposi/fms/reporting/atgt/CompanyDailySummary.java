package com.eposi.fms.reporting.atgt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class CompanyDailySummary implements Serializable {
	private static final long serialVersionUID = 8259389895093240759L;
	private String id; // id is [date] + [vehicle]
	private String company;
    private String companyName;

	private Date date;

	private int vehicleLength; // tong so xe cua Doanh nghiep nay

	private int countVehicleOverSpeed; // dem so xe vi pham
	private int countOverSpeed;
	private int sumOverSpeed; // duration
	private int maxSpeed;
    private double sumDistanceToday = 0; // in km
    private double sumOverSpeedDistance = 0; // in km

    /////////////////////////////////////////////
    private int sumDayCountLT5 = 0; // Duoi 05 km/h
    private int sumDayCount5T10 = 0; // Từ 05 km/h đến dưới 10 km/h
    private int sumDayCount10T20 = 0; // Trên 10 km/h đến 20 km/h
    private int sumDayCount20T35 = 0; // Trên 20 km/h đến 35 km/h
    private int sumDayCountGT35 = 0; // Trên 35 km/h

    // 1:43 PM 3/4/2014
    private HashSet<String> overspeedVehicleList;

    public HashSet<String> getOverspeedVehicleList() {
        return overspeedVehicleList;
    }

    public void setOverspeedVehicleList(HashSet<String> overspeedVehicleList) {
        this.overspeedVehicleList = overspeedVehicleList;
    }

    public int getCountVehicleOverSpeed() {
		return countVehicleOverSpeed;
	}

	public void setCountVehicleOverSpeed(int countVehicleOverSpeed) {
		this.countVehicleOverSpeed = countVehicleOverSpeed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public int getCountOverSpeed() {
		return countOverSpeed;
	}

	public void setCountOverSpeed(int countOverSpeed) {
		this.countOverSpeed = countOverSpeed;
	}

	public int getVehicleLength() {
		return vehicleLength;
	}

	public void setVehicleLength(int vehicleLength) {
		this.vehicleLength = vehicleLength;
	}

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

//    public String getFormatedDuration() {
//		int seconds = (int) (sumOverSpeed) % 60;
//		int minutes = (int) ((sumOverSpeed / (60)) % 60);
//		int hours = (int) ((sumOverSpeed / (60 * 60)) % 24);
//
//		return hours + "h " + minutes + "m " + seconds + "s";
//	}

    public int getSumDayCountLT5() {
        return sumDayCountLT5;
    }

    public void setSumDayCountLT5(int sumDayCountLT5) {
        this.sumDayCountLT5 = sumDayCountLT5;
    }

    public int getSumDayCount5T10() {
        return sumDayCount5T10;
    }

    public void setSumDayCount5T10(int sumDayCount5T10) {
        this.sumDayCount5T10 = sumDayCount5T10;
    }

    public int getSumDayCount10T20() {
        return sumDayCount10T20;
    }

    public void setSumDayCount10T20(int sumDayCount10T20) {
        this.sumDayCount10T20 = sumDayCount10T20;
    }

    public int getSumDayCount20T35() {
        return sumDayCount20T35;
    }

    public void setSumDayCount20T35(int sumDayCount20T35) {
        this.sumDayCount20T35 = sumDayCount20T35;
    }

    public int getSumDayCountGT35() {
        return sumDayCountGT35;
    }

    public void setSumDayCountGT35(int sumDayCountGT35) {
        this.sumDayCountGT35 = sumDayCountGT35;
    }

    public double getSumOverSpeedDistance() {
        return sumOverSpeedDistance;
    }

    public void setSumOverSpeedDistance(double sumOverSpeedDistance) {
        this.sumOverSpeedDistance = sumOverSpeedDistance;
    }

    public double getSumDistanceToday() {
        return sumDistanceToday;
    }

    public void setSumDistanceToday(double sumDistanceToday) {
        this.sumDistanceToday = sumDistanceToday;
    }

    public double getPercentageVehicle() {
        double ratio = 0;

        try {
            ratio = countVehicleOverSpeed*100.0d/vehicleLength;
        }catch(Exception e) {

        }

        if (Double.isNaN(ratio)) {
            return 0;
        }

        return ratio;
    }

    public double getPercentageKm() {
        double ratio = 0;

        try {
            ratio = sumOverSpeedDistance*100.0d/sumDistanceToday;
        }catch(Exception e) {

        }

        if (Double.isNaN(ratio)) {
            return 0;
        }

        return ratio;
    }
}
