package com.eposi.fms.msg;

import java.io.Serializable;

public class TrackMessage implements Serializable {
    private static final long serialVersionUID = -2291447582954780143L;

    public static final int COMMAND_TRACKING_WAYPOINTBATCH = 100;
    public static final int COMMAND_TRACKING_WAYPOINT = 101;

    private Object obj;
    private byte[] raw;

    private int command = 0;

    public TrackMessage(int cmd) {
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

    public byte[] getRaw() {
        return raw;
    }

    public void setRaw(byte[] raw) {
        this.raw = raw;
    }
}
