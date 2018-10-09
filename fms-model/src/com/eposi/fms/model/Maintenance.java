package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Maintenance implements Serializable {
    private static final long serialVersionUID = -7094564784435581562L;
    private Long id;
	private Vehicle vehicle;
    private Company company;
	private MaintenanceCate cate;
	private String place;
	private Date maintenanceDay;
	private Date nextMaintenanceDay;
	private Double fee;
	private Double km;
	private int dayWaning;
	private int kmWarning;
	private int status = 1;
	private String note;
	private long pulseNumber = 0; //= so xung cua ngay bao duong neu ngay bao duong >= ngay kich hoat, = so xung cua ngay them bao duong neu nguoc lai
	private double kmMaintenance = 0.0f; //so km tren dong ho luc bao duong
	private double kmAdd = 0.0f; //so km luc them du lieu
	private double kmRemain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public MaintenanceCate getCate() {
        return cate;
    }

    public void setCate(MaintenanceCate cate) {
        this.cate = cate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getMaintenanceDay() {
        return maintenanceDay;
    }

    public void setMaintenanceDay(Date maintenanceDay) {
        this.maintenanceDay = maintenanceDay;
    }

    public Date getNextMaintenanceDay() {
        return nextMaintenanceDay;
    }

    public void setNextMaintenanceDay(Date nextMaintenanceDay) {
        this.nextMaintenanceDay = nextMaintenanceDay;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public int getDayWaning() {
        return dayWaning;
    }

    public void setDayWaning(int dayWaning) {
        this.dayWaning = dayWaning;
    }

    public int getKmWarning() {
        return kmWarning;
    }

    public void setKmWarning(int kmWarning) {
        this.kmWarning = kmWarning;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getPulseNumber() {
        return pulseNumber;
    }

    public void setPulseNumber(long pulseNumber) {
        this.pulseNumber = pulseNumber;
    }

    public double getKmMaintenance() {
        return kmMaintenance;
    }

    public void setKmMaintenance(double kmMaintenance) {
        this.kmMaintenance = kmMaintenance;
    }

    public double getKmAdd() {
        return kmAdd;
    }

    public void setKmAdd(double kmAdd) {
        this.kmAdd = kmAdd;
    }

    public double getKmRemain() {
        return kmRemain;
    }

    public void setKmRemain(double kmRemain) {
        this.kmRemain = kmRemain;
    }
}
