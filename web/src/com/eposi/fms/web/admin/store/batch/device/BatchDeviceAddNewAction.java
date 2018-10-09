package com.eposi.fms.web.admin.store.batch.device;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Device;


public class BatchDeviceAddNewAction extends AbstractAction {

    private static final long serialVersionUID = 7511573663313179041L;
    private Device item;
    private String strDateStart;
    private String modelId;
    private String batch;

    public String execute() {

        return SUCCESS;
    }

    public Device getItem() {
        return item;
    }

    public void setItem(Device item) {
        this.item = item;
    }

    public String getStrDateStart() {
        return strDateStart;
    }

    public void setStrDateStart(String strDateStart) {
        this.strDateStart = strDateStart;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
