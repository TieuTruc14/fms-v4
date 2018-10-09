package com.eposi.fms.persitence;

import com.eposi.common.persitence.DaoUtil;
import com.eposi.common.util.AbstractBean;
import com.eposi.common.util.GeoUtil;
import com.eposi.fms.geofence.DataLoader;
import com.eposi.fms.model.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.index.SpatialIndex;
import com.vividsolutions.jts.io.WKTReader;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class FmsDao extends AbstractBean {
    private static final long serialVersionUID = -7155765819672985946L;
    private static Logger log = Logger.getLogger(FmsDao.class.getName());

    public Company getCompany(String id) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (Company) beanDaoUtil.get(Company.class, id);
    }

    public Driver getDriver(String driverId) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        Driver driver = (Driver) beanDaoUtil.get(Driver.class, driverId);
        return driver;
    }

    public Vehicle getVehicle(String id) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (Vehicle) beanDaoUtil.get(Vehicle.class, id);
    }

    public Device getDevice(String id) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (Device) beanDaoUtil.get(Device.class, id);
    }

    public Batch getBatch(String id) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (Batch) beanDaoUtil.get(Batch.class, id);
    }

    /******************************************************************/
    // ReportDao
    public Report getReport(String id) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (Report) beanDaoUtil.get(Report.class, id);
    }

    public List<Report> listReport() {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (List<Report>) beanDaoUtil.list(Report.class.getName());
    }

    /******************************************************************/
    //Position
    public List<Position> findPositionByCompany(Company company) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        List<Position> lstPosition = (List<Position>) beanDaoUtil.query(Position.class, criterions);
        if(lstPosition!=null){
            return lstPosition;
        }
        return new ArrayList<Position>();
    }

    public List<Company> listCompany() {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (List<Company>) beanDaoUtil.list(Company.class.getName());
    }

    public List<Vehicle> listVehicle() {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (List<Vehicle>) beanDaoUtil.list(Vehicle.class.getName());
    }

    /******************************************************************/
    //Charging
    public List<Charging> getAllChargings() {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (List<Charging>) beanDaoUtil.list("Charging");
    }

    public synchronized Charging findCharging(double x, double y) {
        DataLoader beanDataLoader      = (DataLoader) this.getBean("beanDataLoader");
        SpatialIndex spatialIndexChargingStation = beanDataLoader.getSpatialIndexChargings();

        double distanceInMeter = 250.0d;

        double distanceInDegree = (float)GeoUtil.convertDistanceToDegree(new Coordinate(x, y), distanceInMeter);
        double xmin = x - distanceInDegree;
        double xmax = x + distanceInDegree;
        double ymin = y - distanceInDegree;
        double ymax = y + distanceInDegree;

        Envelope envelope = new Envelope(xmin, xmax, ymin, ymax);

        List<?> lst = spatialIndexChargingStation.query(envelope);

        return findClosestCharging(x, y, lst);
    }


    private Charging findClosestCharging(double x, double y, List<?> list) {
        if (x <1 || y < 1 ||(list == null) || (list.isEmpty())) {
            return null;
        }

        try {
            double distance = 0.0d;
            double minDistance = 1.7976931348623157E+308D;
            int idx = 0;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Charging chargingStation =  (Charging)list.get(i);
                distance = GeoUtil.distanceInMeters(x, y, chargingStation.getX(), chargingStation.getY());
                if (distance < minDistance) {
                    minDistance = distance;
                    idx = i;
                }
            }

            if(minDistance<250) {
                return (Charging) list.get(idx);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }

        return null;
    }

    //ExpressWay
    public List<ExpressWay> getAllExpressWay() {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (List<ExpressWay>) beanDaoUtil.list("ExpressWay");
    }

    public ExpressWay findExpressWay(double x, double y) {
        DataLoader beanDataLoader      = (DataLoader) this.getBean("beanDataLoader");
        SpatialIndex spatialIndexExpressWay = beanDataLoader.getSpatialIndexExpressWay();

        double distanceInMeter = 150.0D;

        double distanceInDegree = GeoUtil.convertDistanceToDegree(new Coordinate(x, y), distanceInMeter);
        double xmin = x - distanceInDegree;
        double xmax = x + distanceInDegree;
        double ymin = y - distanceInDegree;
        double ymax = y + distanceInDegree;

        Envelope envelope = new Envelope(xmin, xmax, ymin, ymax);

        List<?> lst = spatialIndexExpressWay.query(envelope);

        return findClosestExpressWay(x, y, lst);
    }


    private ExpressWay findClosestExpressWay(double x, double y, List<?> list) {
        if (x <1 || y < 1 ||(list == null) || (list.isEmpty())) {
            return null;
        }

        try {
            double distance = 0.0D;
            WKTReader wkt = new WKTReader();
            Geometry g = wkt.read("POINT(" + x + " " + y + ")");
            double minDistance = 1.7976931348623157E+308D;
            int idx = 0;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ExpressWay expressWay =  (ExpressWay)list.get(i);
                distance = expressWay.getGeometry().distance(g);
                if (distance < minDistance) {
                    minDistance = distance;
                    idx = i;
                }
            }

            if(minDistance<150) {
                return (ExpressWay) list.get(idx);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }

        return null;
    }
}
