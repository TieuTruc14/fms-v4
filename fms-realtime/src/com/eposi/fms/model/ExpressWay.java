package com.eposi.fms.model;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTWriter;
import org.codehaus.jettison.json.JSONObject;

import java.io.Serializable;

public class ExpressWay implements Serializable {
	private static final long serialVersionUID = -8629528949662011268L;
	
	private int    id;
	private String  name;
	private Geometry geometry;
	private Double distance;
	private double buffer;
	private int maxSpeed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getWktString() {
		String strWKT = "";
		WKTWriter wkt = new WKTWriter();
		if (geometry != null) {
			strWKT = wkt.write(geometry);
		}
		
		return strWKT;
	}

	public double getBuffer() {
		return buffer;
	}

	public void setBuffer(double buffer) {
		this.buffer = buffer;
	}
	
	public String getGeometryJson(){
		return JSONObject.quote(getWktString());
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
}
