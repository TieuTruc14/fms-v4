package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Text implements Serializable {
    private static final long serialVersionUID = -2995698223946459416L;

    private String  id;
	private String  nameE;
    private String  nameVN;
    private String  nameTaxi;
    private String  nameShip;
    private String  nameBike;
    private String  nameFerry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameE() {
        return nameE;
    }

    public void setNameE(String nameE) {
        this.nameE = nameE;
    }

    public String getNameVN() {
        return nameVN;
    }

    public void setNameVN(String nameVN) {
        this.nameVN = nameVN;
    }

    public String getNameTaxi() {
        return nameTaxi;
    }

    public void setNameTaxi(String nameTaxi) {
        this.nameTaxi = nameTaxi;
    }

    public String getNameShip() {
        return nameShip;
    }

    public void setNameShip(String nameShip) {
        this.nameShip = nameShip;
    }

    public String getNameBike() {
        return nameBike;
    }

    public void setNameBike(String nameBike) {
        this.nameBike = nameBike;
    }

    public String getNameFerry() {
        return nameFerry;
    }

    public void setNameFerry(String nameFerry) {
        this.nameFerry = nameFerry;
    }
}
