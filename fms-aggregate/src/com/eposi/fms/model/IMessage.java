package com.eposi.fms.model;

/**
 * Created by Tuan on 8/8/2016.
 */
public interface IMessage {
    public static final byte MESSAGE_TYPE_STOP    = 0;
    public static final byte MESSAGE_TYPE_TRIP    = 1;
    public static final byte MESSAGE_TYPE_OS      = 2;
    public static final byte MESSAGE_TYPE_OVER4H  = 3;
    public static final byte MESSAGE_TYPE_OVER10H = 4;
    public static final byte MESSAGE_TYPE_XMIT    = 5;
    public static final byte MESSAGE_TYPE_VEHICLE_XMIT    = 6;
    public static final byte MESSAGE_TYPE_VEHICLE_AGGREGATION    = 7;
    public static final byte MESSAGE_TYPE_GEO_POI    = 8;
    public static final byte MESSAGE_TYPE_ACTIVITY   = 9;
    public static final byte MESSAGE_TYPE_ACTIVE_VEHICLE   = 10;

    public int getMessageType();

    public String getId();

    public String getVehicleAggregationId();

    public String getDriverAggregationId();

    public String getCompanyAggregationId();
}
