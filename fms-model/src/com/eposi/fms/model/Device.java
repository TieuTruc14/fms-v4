package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable {
    private static final long serialVersionUID = -297264697118938385L;
    private String id;     // Ma TB làm primary key
    private Vehicle  vehicle;
    private Company  company;
    private Date     dateStart; //ngay sản xuất
    private Date     dateActive;//ngay kich hoạt
    private Date     dateEnd; //ngày hết hạn
    private int      unit;//trạng thái lắp đặt hay chưa.
    private boolean  stock;//da thuoc phieu xuat nao chua
    private Batch    batch;//lô sản phẩm
    private String   product_key;// mã số sản phẩm gồm tiền tố Batch(4 ký tự model va 3 ký tự batch)+ số series(5 ký tự)
    private boolean  disable;//trạng thái hủy thiết bị.
    private Firmware fwv;//firmware nap vao thiết bị
    private String   description;

    private Date    dateCreated;//ngày tạo
    private String  userCreated;//người tạo
    private String  userUpdated;//người cập nhập cuối
    private Date    dateUpdated;//thời gian cập nhập cuối
    private String  konexyId;


	public Device() {
		super();
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        if (this.unit == 0) {
            return "Chưa lắp đặt";
        }
        return "Đã lắp đặt";
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public Date getDateActive() {
        return dateActive;
    }

    public void setDateActive(Date dateActive) {
        this.dateActive = dateActive;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public Firmware getFwv() {
        return fwv;
    }

    public void setFwv(Firmware fwv) {
        this.fwv = fwv;
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

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKonexyId() {
        return konexyId;
    }

    public void setKonexyId(String konexyId) {
        this.konexyId = konexyId;
    }

}
