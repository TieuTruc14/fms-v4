package com.eposi.fms.msg;

import java.io.Serializable;

public class BaseSystemCommand implements Serializable {
    private static final long serialVersionUID = 3687051862676779698L;

    // system messages
    public static final int COMMAND_PING = 0;

    // actor manipulate messages
    public static final int COMMAND_ACTOR_CREATE_VEHICLE = 1000;
    public static final int COMMAND_REMOVE_TIMEOUT_ACTOR = 1;

    //
    public static final int COMMAND_RESET_AGGRESSION_ACTOR = 2;
    public static final int COMMAND_OVERSPEED = 60;
    public static final int COMMAND_OVERSPEED_BY_PROVINCE = 61;
    public static final int COMMAND_OVERSPEED_BY_LOCATION_PROVINCE = 4;
    public static final int COMMAND_TRIP_BY_PROVINCE = 5;

    public static final int COMMAND_GEOFENCE_PROVINCE_LOCATION = 20; // should be send at ending
    public static final int COMMAND_GEOFENCE_PROVINCE_LOCATION_BEGIN = 21;
//    public static final int COMMAND_GEOFENCE_PROVINCE_LOCATION_END = 22;
//    public static final int COMMAND_GEOFENCE_PROVINCE_NOTIFY_STOP_IN_PROVINCE = 21;

    public static final int COMMAND_XMIT = 30;
    public static final int COMMAND_XMIT_WINDOW = 31;


    public static final int COMMAND_REG_SYNCDB = 40; // tell actor to synch/update DB

    public static final int COMMAND_AGGRESSION_ACTIVITY_IN_PROGRESS = 50;

//    public static final int COMMAND_EVENT_START_MOVING = 51;
//    public static final int COMMAND_EVENT_START_STOPING = 61;


//    public static final int COMMAND_STOP = 70;

    public static final int COMMAND_DOOR = 80;
    public static final int COMMAND_DOOR_OPEN_WHILE_MOVING = 81;


    //public static final int COMMAND_DRIVER_CHANGE = 90;
    public static final int COMMAND_DRIVER_CHANGE_PREV = 91;
    public static final int COMMAND_DRIVER_CHANGE_NEW = 92;
    public static final int COMMAND_DRIVER_OVER4H = 93;
    public static final int COMMAND_DRIVER_OVER10H = 94;

    private Object obj;

    //
    private int command = 0;

    public BaseSystemCommand(int cmd) {
        command = cmd;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
