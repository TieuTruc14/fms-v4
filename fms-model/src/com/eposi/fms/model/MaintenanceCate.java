package com.eposi.fms.model;

import java.io.Serializable;

public class MaintenanceCate implements Serializable {

    private static final long serialVersionUID = -990520010152077190L;
    private Long id;
	private String name;
	private int period;
	private int normKm;
	private int normHour;
	private Company company;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getNormKm() {
        return normKm;
    }

    public void setNormKm(int normKm) {
        this.normKm = normKm;
    }

    public int getNormHour() {
        return normHour;
    }

    public void setNormHour(int normHour) {
        this.normHour = normHour;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
