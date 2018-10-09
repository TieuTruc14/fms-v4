package com.eposi.fms.v2.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Device implements Serializable {
	private static final long serialVersionUID = -6082326400523892390L;

	private String      vehicleId = "";
	private String      deviceId = "";
	private Owner       owner;
    private Province    province;
	private VehicleType	  vehicleType;
	private TransportType transportType;
	private String        sim = "";
	private Date        dateActive = new Date();
    private Date        dateEnd;
	private String      note = "";
	private String      fuelRange;
	private boolean     sensor;
	private int			maxSpeed;		//Vận tốc tối đa cho phép của xe. Xóa sau khi có giải pháp tốc độ theo khhung đường
	private int         timeOverSpeed;
	private double      rateGPS = 1.05;
	private double      fuelLimit;
    private int         notify;

	//quan ly hop dong
	private int status;
	private int isCharge;
	
	/**
	 * http://jira.eposi.vn:8888/jira/browse/ST-688 <br>
	 * Phần này bổ sung tham số để chỉ định thiết bị có gắn bật tắt máy hay ko?  
	 */	
	private boolean configI0; // engine
	private boolean configI1; // door
	private boolean configI2; // air-con
	private Date    nsTime; // no signal time
	private Date    timeGSM;
	private Date    timeGPS;
	
	private boolean camera;
	private boolean onFilter;
	private boolean disable;
	private boolean bgt; //Config send data to UFMS
	private int     maxFuel;

	private String automaker;			// nhà sản xuất
	private String seatOrWeight;		// Trọng tải hoặc số ghế
	private String vin;					// Số khung
	private boolean turnOnI5 = false;	// Cấu hình hiển thị chân mở rộng
	//them 2 thuộc tính bổ sung trợ giúp phía thiết bị xem là xe công trình không do thiết bị đang bị lỗi nếu lắp nhiều hơn 3 chân (i0,i1,i2) thì nhiễu tín hiệu
	private boolean workVehicle;		//Xem có phải xe công trình không
	private String workVehicleMapping;					// Giá trị từ 0-2 để gán tính toán báo cáo theo chân nào trên thiết bị(tương ứng i0-i2)

	private Integer position;
	
	public TransportType getTransportType() {
		return transportType;
	}

	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}

    public Date getDateActive() {
        return dateActive;
    }

    public void setDateActive(Date dateActive) {
        this.dateActive = dateActive;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }


	public int getMaxFuel() {
        return maxFuel;
    }

    public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getSim() {
		return this.sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		
		this.vehicleId = vehicleId;
	}

	public double getRateGPS() {
		return rateGPS;
	}

	public void setRateGPS(double rateGPS) {
		this.rateGPS = rateGPS;
	}

	public String getNote() {
		return note;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFuelRange() {
		return fuelRange;
	}

	public void setFuelRange(String fuelRange) {
		this.fuelRange = fuelRange;
	}

	public String getTrimFuelRange() {
		if (fuelRange != null && fuelRange.length() > 100) {
			return fuelRange.substring(1, 100) + "...";
		}
		return fuelRange;
	}

	public String[] getFuleRangeArray() {
		String[] arr = null;
		if (fuelRange != null) {
			fuelRange = StringUtils.replace(fuelRange, "[", "");
			fuelRange = StringUtils.replace(fuelRange, "]", "");
			arr = StringUtils.split(fuelRange, ',');
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].isEmpty()) {
					arr[i] = "null";
				}
			}
		}
		ArrayUtils.reverse(arr);
		return arr;
	}
	
	public boolean isSensor() {
		return sensor;
	}

	public void setSensor(boolean sensor) {
		this.sensor = sensor;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
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

	public Date getTimeGPS() {
		return timeGPS;
	}

	public void setTimeGPS(Date timeGPS) {
		this.timeGPS = timeGPS;
	}

	public Date getTimeGSM() {
		return timeGSM;
	}

	public void setTimeGSM(Date timeGSM) {
		this.timeGSM = timeGSM;
	}

	/**
	 * @return the nsTime
	 */
	public Date getNsTime() {
		return nsTime;
	}

	/**
	 * @param nsTime
	 *            the nsTime to set
	 */
	public void setNsTime(Date nsTime) {
		this.nsTime = nsTime;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public boolean isCamera() {
		return camera;
	}

	public void setCamera(boolean camera) {
		this.camera = camera;
	}

	public boolean isOnFilter() {
		return onFilter;
	}

	public void setOnFilter(boolean onFilter) {
		this.onFilter = onFilter;
	}

	public boolean isBgt() {
		return bgt;
	}

	public void setBgt(boolean bgt) {
		this.bgt = bgt;
	}

	public int isMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(int maxFuel) {
		this.maxFuel = maxFuel;
	}

	/**
	 * @return the maxSpeed
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(int isCharge) {
		this.isCharge = isCharge;
	}

	/**
	 * @return the seatOrWeight
	 */
	public String getSeatOrWeight() {
		return seatOrWeight;
	}

	/**
	 * @return the String of seatOrWeight
	 */
	public String getSeatOrWeightInText() {
		return  seatOrWeight;
	}

	/**
	 * @param seatOrWeight the seatOrWeight to set
	 */
	public void setSeatOrWeight(String seatOrWeight) {
		this.seatOrWeight = seatOrWeight;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public boolean isTurnOnI5() {
		return turnOnI5;
	}

	/**
	 * @param turnOnI5 the turnOnI5 to set
	 */
	public void setTurnOnI5(boolean turnOnI5) {
		this.turnOnI5 = turnOnI5;
	}

	/**
	 * @return the automaker
	 */
	public String getAutomaker() {
		return automaker;
	}

	/**
	 * @param automaker the automaker to set
	 */
	public void setAutomaker(String automaker) {
		this.automaker = automaker;
	}
	
	/**
	 * @return The time that vehicle has over speed
	 */
	public int getTimeOverSpeed() {
		return this.timeOverSpeed;
	}
	
	/**
	 * @param timeOverSpeed the time that vehicle has over speed (seconds)
	 */
	public void setTimeOverSpeed(int timeOverSpeed) {
		this.timeOverSpeed = timeOverSpeed;
	}

	public double getFuelLimit() {
		return fuelLimit;
	}

	public void setFuelLimit(double fuelLimit) {
		this.fuelLimit = fuelLimit;
	}

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public String getStatusName() {
        if(disable){
            return "Khóa";
        }
        return "Mở";
    }

	public boolean isWorkVehicle() {
		return workVehicle;
	}

	public void setWorkVehicle(boolean workVehicle) {
		this.workVehicle = workVehicle;
	}

	public String getWorkVehicleMapping() {
		return workVehicleMapping;
	}

	public void setWorkVehicleMapping(String workVehicleMapping) {
		this.workVehicleMapping = workVehicleMapping;
	}
}