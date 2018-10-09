package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Vehicle implements Serializable {
	private static final long serialVersionUID = -5498092093615856631L;

	private String id;     // Bien so xe làm primary key
	private Company company;
    private FuelType fuelType;
	private TransportType type;
    private Driver driver; // default driver
    private Integer capacity;
    private String note;
    private boolean onFilter;
    private Date dateStart;
    private Date dateEnd;
    private Date dateCreated;
    private Date dateUpdated;

    //Config
    private boolean configI0;//Bat tat may
    private boolean configI1;//Dong mo cua
    private boolean configI2;// Bat tat dieu hoa
    private boolean configI5;// Che do cong trinh
    private boolean bgt;//gửi dữ liệu lên bộ giao thông
    private boolean sensor;//lắp sensor không
    private String  workVehicleMapping="i5";//Bổ trợ ver2 về giá trị config xe công trình. V3 ko cần
    private float   rateGPS = 1.05f;

    private String  userCreated;//người tạo
    private String  userUpdated;//người cập nhập cuối
    private boolean disable;
    private String konexyId;
    private boolean vehicleNew;//có phải là xe mới ko.Nếu có true. Nhằm tránh tạo hợp đồng xe khi thay TB/sim vào xe

	public Vehicle() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getTypeName() {
		return type.getName();
	}

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public boolean isOnFilter() {
        return onFilter;
    }

    public void setOnFilter(boolean onFilter) {
        this.onFilter = onFilter;
    }

    public boolean isConfigI0() {
        return configI0;
    }

    public void setConfigI0(boolean configI0) {
        this.configI0 = configI0;
    }

    public boolean isConfigI1() {
        return configI1;
    }

    public void setConfigI1(boolean configI1) {
        this.configI1 = configI1;
    }

    public boolean isConfigI2() {
        return configI2;
    }

    public void setConfigI2(boolean configI2) {
        this.configI2 = configI2;
    }

    public boolean isConfigI5() {
        return configI5;
    }

    public void setConfigI5(boolean configI5) {
        this.configI5 = configI5;
    }

    public float getRateGPS() {
        return rateGPS;
    }

    public void setRateGPS(float rateGPS) {
        this.rateGPS = rateGPS;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public boolean isBgt() {
        return bgt;
    }

    public void setBgt(boolean bgt) {
        this.bgt = bgt;
    }

    public boolean isSensor() {
        return sensor;
    }

    public void setSensor(boolean sensor) {
        this.sensor = sensor;
    }

    public String getWorkVehicleMapping() {
        return workVehicleMapping;
    }

    public void setWorkVehicleMapping(String workVehicleMapping) {
        this.workVehicleMapping = workVehicleMapping;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getKonexyId() {
        return konexyId;
    }

    public void setKonexyId(String konexyId) {
        this.konexyId = konexyId;
    }

    public boolean isVehicleNew() {
        return vehicleNew;
    }

    public void setVehicleNew(boolean vehicleNew) {
        this.vehicleNew = vehicleNew;
    }
}
