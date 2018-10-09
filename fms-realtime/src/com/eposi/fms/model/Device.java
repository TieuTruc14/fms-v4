package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable {
    private static final long serialVersionUID = -297264697118938385L;
    private String id;     // Ma TB làm primary key
    private Batch    batch;//lô sản phẩm
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

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public String getKonexyId() {
        return konexyId;
    }

    public void setKonexyId(String konexyId) {
        this.konexyId = konexyId;
    }

}
