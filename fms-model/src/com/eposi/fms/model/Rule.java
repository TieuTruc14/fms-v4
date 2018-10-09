package com.eposi.fms.model;

import java.io.Serializable;

public class Rule implements Serializable {
    private static final long serialVersionUID = -1637239525344614694L;

    private long    id;
	private String  name;
	private long    timeInside;
	private int     stop;
	private int     turnOff;
	private int     openDoor;
	private long    timeStop;
	private int     velocity;
	private long    timeOnVelocity;

    private Company company;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public long getTimeInside() {
		return timeInside;
	}

	public void setTimeInside(long timeInside) {
		this.timeInside = timeInside;
	}

	public int getStop() {
		return stop;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}

	public int getTurnOff() {
		return turnOff;
	}

	public void setTurnOff(int turnOff) {
		this.turnOff = turnOff;
	}

	public int getOpenDoor() {
		return openDoor;
	}

	public void setOpenDoor(int openDoor) {
		this.openDoor = openDoor;
	}

	public long getTimeStop() {
		return timeStop;
	}

	public void setTimeStop(long timeStop) {
		this.timeStop = timeStop;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public long getTimeOnVelocity() {
		return timeOnVelocity;
	}

	public void setTimeOnVelocity(long timeOnVelocity) {
		this.timeOnVelocity = timeOnVelocity;
	}

}
