package com.eposi.fms.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 7698361452270327622L;

    private long id;
    private String no;
    private Commune commune;
    private District district;
    private Province province;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotEmpty(no)){
            sb.append(no).append(", ");
        }

        if(commune!=null){
            sb.append(commune.getName()).append(", ");
        }

        if(district!=null){
            sb.append(district.getName()).append(", ");
        }

        if(province!=null){
            sb.append(province.getName());
        }

        return sb.toString();
    }
}
