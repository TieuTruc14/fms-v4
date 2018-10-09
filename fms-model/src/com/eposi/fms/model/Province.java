package com.eposi.fms.model;

import java.io.Serializable;

//import com.vividsolutions.jts.geom.Geometry;

public class Province implements Serializable {
	private static final long serialVersionUID = -2191252964526706146L;

	private String id;
	private String name;
	private double x;
	private double y;
	private String maBienSo;
    private String fullName;

    /**********************************************************/
    // Should be update interval eg., daily
    private int companyCount;
    private int vehicleCount;
    private int driverCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaBienSo() {
		return maBienSo;
	}

	public void setMaBienSo(String maBienSo) {
		this.maBienSo = maBienSo;
	}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public int getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(int companyCount) {
        this.companyCount = companyCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public int getDriverCount() {
        return driverCount;
    }

    public void setDriverCount(int driverCount) {
        this.driverCount = driverCount;
    }
}
