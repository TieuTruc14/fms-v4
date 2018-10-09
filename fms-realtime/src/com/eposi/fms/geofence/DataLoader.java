package com.eposi.fms.geofence;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.index.strtree.STRtree;
import com.vividsolutions.jts.io.WKTReader;

public class DataLoader extends AbstractBean {
	private static final long serialVersionUID = -1453858985684206785L;

	private ConcurrentHashMap<Integer, ExpressWay> expressWays = new ConcurrentHashMap<Integer, ExpressWay>();
	private STRtree spatialIndexExpressWay = new STRtree();

    private ConcurrentHashMap<Long, Charging> chargings = new ConcurrentHashMap<Long, Charging>();
    private STRtree spatialIndexChargings = new STRtree();

	public void load() throws Exception {
        loadExpressWay();
        System.out.println("loadExpressWay.complete");
        loadCharging();
        System.out.println("loadCharging.complete");
	}

	private void  loadExpressWay() throws Exception {
		long start = System.currentTimeMillis();
        FmsDao beanFmsDao =(FmsDao)this.getBean("beanFmsDao");
		List<ExpressWay> lstExpressWay =  beanFmsDao.getAllExpressWay();
		for(ExpressWay item: lstExpressWay){
            expressWays.put(item.getId(), item);
            spatialIndexExpressWay.insert(item.getGeometry().getEnvelopeInternal(), item);
		}
        spatialIndexExpressWay.build();
	}

    private void  loadCharging() throws Exception {
        FmsDao beanFmsDao =(FmsDao)this.getBean("beanFmsDao");
        List<Charging> lstChargings =  beanFmsDao.getAllChargings();
        for(Charging item: lstChargings){
            chargings.put(item.getId(), item);
            WKTReader wkt = new WKTReader();
            Geometry geometry = wkt.read("POINT(" + item.getX() + " " + item.getY() + ")");
            spatialIndexChargings.insert(geometry.getEnvelopeInternal(), item);
        }
        spatialIndexChargings.build();
    }

    public ConcurrentHashMap<Integer, ExpressWay> getExpressWays() {
        return expressWays;
    }

    public void setExpressWays(ConcurrentHashMap<Integer, ExpressWay> expressWays) {
        this.expressWays = expressWays;
    }

    public STRtree getSpatialIndexExpressWay() {
        return spatialIndexExpressWay;
    }

    public void setSpatialIndexExpressWay(STRtree spatialIndexExpressWay) {
        this.spatialIndexExpressWay = spatialIndexExpressWay;
    }

    public ConcurrentHashMap<Long, Charging> getChargings() {
        return chargings;
    }

    public void setChargings(ConcurrentHashMap<Long, Charging> chargings) {
        this.chargings = chargings;
    }

    public STRtree getSpatialIndexChargings() {
        return spatialIndexChargings;
    }

    public void setSpatialIndexChargings(STRtree spatialIndexChargings) {
        this.spatialIndexChargings = spatialIndexChargings;
    }
}
