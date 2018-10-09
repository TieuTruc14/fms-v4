package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Model sản phẩm
 */
public class Model implements Serializable {

    private static final long serialVersionUID = 187847402013187147L;
    private String  id;//gom 4 ky tự là ký hiệu dòng model sản phẩm
    private String  name;
    private String  note;
    private ProductType productType;//thuộc hàng hóa nào
    private String  companyId;
    private Date    dateCreated;
    private String  UserCreated;
    private Date    dateUpdate;
    private String  UserUpdate;
    private boolean global;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserCreated() {
        return UserCreated;
    }

    public void setUserCreated(String userCreated) {
        UserCreated = userCreated;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getUserUpdate() {
        return UserUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        UserUpdate = userUpdate;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
