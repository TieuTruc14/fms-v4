package com.eposi.fms.model;


public class DriverAggregationWindow extends AbstractAggregationWindow {
    private static final long serialVersionUID = 6263055565525866525L;

    private String driverName;
    private String licenceKey;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
    }

    @Override
    public int getType() {
        return TYPE_COMPANY;
    }
}
