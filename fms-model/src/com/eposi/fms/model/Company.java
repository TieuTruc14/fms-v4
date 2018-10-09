package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Company implements Serializable {
	private static final long serialVersionUID = -3001762772506182392L;

	private String id;
	private String name;
	private String email;
	private String url;
	private String phone;
	private Province province;// cho so GTVT quản lý
	private Address address ;//địa chỉ
	private CustomerType customerType;//Loại đối tượng(NCC, KHCN, KHNN,...)
	private String code;//mã số thuế hoặc chứng minh thư nếu là cá nhân
    private String note;

    // should be update daily
	private int companyCount;
    private int vehicleCount;
    private int driverCount;
	private int status;

    private Date dateCreated;
    private Date dateUpdated;
	private String userCreated;
	private String userUpdated;
	private String konexyId; //activity
	private String aggregation;//Summary report


    private Company parent;/*Xem truc thuoc chi nhanh hay dai ly' nao*/
    private boolean orgazation;/*co phai dai ly ko*/
	private String  prefix;
	private boolean inforLocked;//khoa thong tin hay chua

	//mapping V2
	private long owner;

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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Company getParent() {
        return parent;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }

	public boolean isOrgazation() {
		return orgazation;
	}

	public void setOrgazation(boolean orgazation) {
		this.orgazation = orgazation;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getKonexyId() {
		return konexyId;
	}

	public void setKonexyId(String konexyId) {
		this.konexyId = konexyId;
	}

	public String getAggregation() {
		return aggregation;
	}

	public void setAggregation(String aggregation) {
		this.aggregation = aggregation;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isInforLocked() {
		return inforLocked;
	}

	public void setInforLocked(boolean inforLocked) {
		this.inforLocked = inforLocked;
	}
}
