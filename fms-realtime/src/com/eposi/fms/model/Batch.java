package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Quản lý lô sản phẩm
 */
public class Batch implements Serializable {
    private static final long serialVersionUID = -2678235221074950460L;
    private String id;
    private String code;
    private String name;
    private String konexyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKonexyId() {
        return konexyId;
    }

    public void setKonexyId(String konexyId) {
        this.konexyId = konexyId;
    }
}
