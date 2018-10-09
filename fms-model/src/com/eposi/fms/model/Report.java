package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
	private static final long serialVersionUID = 4681012259877428844L;

	private String id;
	private String trip;
	private String stop;
	private String os;
	private String over10h;
	private String over4h;
	private String log;
	private String xmit;
	private String geoPoi;
	private String aggregation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrip() {
		return trip;
	}

	public void setTrip(String trip) {
		this.trip = trip;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOver10h() {
		return over10h;
	}

	public void setOver10h(String over10h) {
		this.over10h = over10h;
	}

	public String getOver4h() {
		return over4h;
	}

	public void setOver4h(String over4h) {
		this.over4h = over4h;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getXmit() {
		return xmit;
	}

	public void setXmit(String xmit) {
		this.xmit = xmit;
	}

	public String getAggregation() {
		return aggregation;
	}

	public void setAggregation(String aggregation) {
		this.aggregation = aggregation;
	}

	public String getGeoPoi() {
		return geoPoi;
	}

	public void setGeoPoi(String geoPoi) {
		this.geoPoi = geoPoi;
	}
}
