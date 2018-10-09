package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class CompanyState implements Serializable {
    private static final long serialVersionUID = 871284361763422984L;

	private String companyCode;
    private Date date; // summary date, default is today

    // hold list of vehicle managed by this company
    private HashSet<String> vehicles = null; //new HashSet<String>();

//    private HashMap<String, CompanyStateDghcProvince> mapDghc = new HashMap<String, CompanyStateDghcProvince>();

//    private HashMap<String, CompanyStateGeofenceProvinceInProgress> geofenceProvinceInProgressHashMap = new HashMap<String, CompanyStateGeofenceProvinceInProgress>();

    private short speedMax = 0;

    private int sumDuration = 0; // overspeed duration, need to change the variable name later
    private int sumOverSpeedDurationTT08 = 0;
//    private int sumTripDuration = 0;

    private int countDoorOpenWhileMoving = 0;
    private int countDoor = 0;

    private long sumStopDuration = 0; // thoi gian dung - don vi SECOND
    private int stopCount = 0; // so lan dung do

    private int tripCount = 0; // dem tong so hanh trinh trong ngay

    /////////////////////////////////////////////////////////////
    // DRIVING
    private int count4h = 0;
    private int count10h = 0;

    private double sumTripDistance = 0; // in km
    private double sumDistanceToday = 0; // in km
    private double sumOverSpeedDistance = 0; // in km
    private double sumOverSpeedDistanceTT08 = 0; // in km
    //////////////////////////////
    //    ALL
    private int totalOverSpeedToday = 0;
    private int totalOverSpeedTodayTT08 = 0; // by managing, TT08
    private HashSet<String> vehiclesOverSpeedToday = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province
    private HashSet<String> vehiclesOverSpeedTodayTT08 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province
    /////////////////////////////////////////////
    //    Duoi 05 km/h
    private int totalOverSpeedTodayLT5 = 0; // by managing
    private int totalOverSpeedTodayLT5TT08 = 0; // by managing, TT08
//    private HashSet<String> vehiclesOverSpeedTodayLT5 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province
//    private HashSet<String> vehiclesOverSpeedTodayLT5TT08 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province

    //////////////////////////////
    //   Từ 05 km/h đến dưới 10 km/h
    private int totalOverSpeedToday5T10 = 0; // by managing
    private int totalOverSpeedToday5T10TT08 = 0; // by managing, TT08
//    private HashSet<String> vehiclesOverSpeedToday5T10 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province
//    private HashSet<String> vehiclesOverSpeedToday5T10TT08 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province

    //////////////////////////////
    //   Trên 10 km/h đến 20 km/h
    private int totalOverSpeedToday10T20 = 0; // by managing
    private int totalOverSpeedToday10T20TT08 = 0; // by managing, TT08
//    private HashSet<String> vehiclesOverSpeedToday10T20 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province
//    private HashSet<String> vehiclesOverSpeedToday10T20TT08 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province

    //////////////////////////////
    //   Trên 20 km/h đến 35 km/h
    private int totalOverSpeedToday20T35 = 0; // by managing
    private int totalOverSpeedToday20T35TT08 = 0; // by managing, TT08
//    private HashSet<String> vehiclesOverSpeedToday20T35 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province
//    private HashSet<String> vehiclesOverSpeedToday20T35TT08 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province

    //////////////////////////////
    //   Trên 35 km/h
    private int totalOverSpeedTodayGT35 = 0; // by managing
    private int totalOverSpeedTodayGT35TT08 = 0; // by managing, TT08
//    private HashSet<String> vehiclesOverSpeedTodayGT35 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province
//    private HashSet<String> vehiclesOverSpeedTodayGT35TT08 = new HashSet<String>(); // hold today all overspeed vehicles managed by this Province

	private long lastUpdateToCache = 0;

    //
//    private CompanyXmitDaySum xmit;

    public int getTripCount() {
        return tripCount;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }

    public long getSumStopDuration() {
        return sumStopDuration;
    }

    public void setSumStopDuration(long sumStopDuration) {
        this.sumStopDuration = sumStopDuration;
    }

    public int getStopCount() {
        return stopCount;
    }

    public void setStopCount(int stopCount) {
        this.stopCount = stopCount;
    }

    public int getCountDoor() {
        return countDoor;
    }

    public void setCountDoor(int countDoor) {
        this.countDoor = countDoor;
    }

    public int getCountDoorOpenWhileMoving() {
        return countDoorOpenWhileMoving;
    }

    public void setCountDoorOpenWhileMoving(int countDoorOpenWhileMoving) {
        this.countDoorOpenWhileMoving = countDoorOpenWhileMoving;
    }

    public int getCount4h() {
        return count4h;
    }

    public void setCount4h(int count4h) {
        this.count4h = count4h;
    }

    public int getCount10h() {
        return count10h;
    }

    public void setCount10h(int count10h) {
        this.count10h = count10h;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getTotalOverSpeedToday() {
		return totalOverSpeedToday;
	}

	public void setTotalOverSpeedToday(int totalOverSpeedToday) {
		this.totalOverSpeedToday = totalOverSpeedToday;
	}

	public long getLastUpdateToCache() {
		return lastUpdateToCache;
	}

	public void setLastUpdateToCache(long lastUpdateToCache) {
		this.lastUpdateToCache = lastUpdateToCache;
	}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashSet<String> getVehiclesOverSpeedToday() {
        return vehiclesOverSpeedToday;
    }

    public void setVehiclesOverSpeedToday(HashSet<String> vehiclesOverSpeedToday) {
        this.vehiclesOverSpeedToday = vehiclesOverSpeedToday;
    }

    public int getTotalOverSpeedTodayTT08() {
        return totalOverSpeedTodayTT08;
    }

    public void setTotalOverSpeedTodayTT08(int totalOverSpeedTodayTT08) {
        this.totalOverSpeedTodayTT08 = totalOverSpeedTodayTT08;
    }

    public HashSet<String> getVehiclesOverSpeedTodayTT08() {
        return vehiclesOverSpeedTodayTT08;
    }

    public void setVehiclesOverSpeedTodayTT08(HashSet<String> vehiclesOverSpeedTodayTT08) {
        this.vehiclesOverSpeedTodayTT08 = vehiclesOverSpeedTodayTT08;
    }

    public int getTotalOverSpeedTodayLT5() {
        return totalOverSpeedTodayLT5;
    }

//    public HashMap<String, CompanyStateGeofenceProvinceInProgress> getGeofenceProvinceInProgressHashMap() {
//        return geofenceProvinceInProgressHashMap;
//    }
//
//    public void setGeofenceProvinceInProgressHashMap(HashMap<String, CompanyStateGeofenceProvinceInProgress> geofenceProvinceInProgressHashMap) {
//        this.geofenceProvinceInProgressHashMap = geofenceProvinceInProgressHashMap;
//    }

    public void setTotalOverSpeedTodayLT5(int totalOverSpeedTodayLT5) {
        this.totalOverSpeedTodayLT5 = totalOverSpeedTodayLT5;
    }

    public int getTotalOverSpeedTodayLT5TT08() {
        return totalOverSpeedTodayLT5TT08;
    }

    public void setTotalOverSpeedTodayLT5TT08(int totalOverSpeedTodayLT5TT08) {
        this.totalOverSpeedTodayLT5TT08 = totalOverSpeedTodayLT5TT08;
    }

    public double getSumTripDistance() {
        return sumTripDistance;
    }

    public void setSumTripDistance(double sumTripDistance) {
        this.sumTripDistance = sumTripDistance;
    }

    //    public HashSet<String> getVehiclesOverSpeedTodayLT5() {
//        return vehiclesOverSpeedTodayLT5;
//    }
//
//    public void setVehiclesOverSpeedTodayLT5(HashSet<String> vehiclesOverSpeedTodayLT5) {
//        this.vehiclesOverSpeedTodayLT5 = vehiclesOverSpeedTodayLT5;
//    }
//
//    public HashSet<String> getVehiclesOverSpeedTodayLT5TT08() {
//        return vehiclesOverSpeedTodayLT5TT08;
//    }
//
//    public void setVehiclesOverSpeedTodayLT5TT08(HashSet<String> vehiclesOverSpeedTodayLT5TT08) {
//        this.vehiclesOverSpeedTodayLT5TT08 = vehiclesOverSpeedTodayLT5TT08;
//    }

    public int getTotalOverSpeedToday5T10() {
        return totalOverSpeedToday5T10;
    }

    public void setTotalOverSpeedToday5T10(int totalOverSpeedToday5T10) {
        this.totalOverSpeedToday5T10 = totalOverSpeedToday5T10;
    }

    public int getTotalOverSpeedToday5T10TT08() {
        return totalOverSpeedToday5T10TT08;
    }

    public void setTotalOverSpeedToday5T10TT08(int totalOverSpeedToday5T10TT08) {
        this.totalOverSpeedToday5T10TT08 = totalOverSpeedToday5T10TT08;
    }

    public HashSet<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(HashSet<String> vehicles) {
        this.vehicles = vehicles;
    }

    //    public HashSet<String> getVehiclesOverSpeedToday5T10() {
//        return vehiclesOverSpeedToday5T10;
//    }
//
//    public void setVehiclesOverSpeedToday5T10(HashSet<String> vehiclesOverSpeedToday5T10) {
//        this.vehiclesOverSpeedToday5T10 = vehiclesOverSpeedToday5T10;
//    }
//
//    public HashSet<String> getVehiclesOverSpeedToday5T10TT08() {
//        return vehiclesOverSpeedToday5T10TT08;
//    }
//
//    public void setVehiclesOverSpeedToday5T10TT08(HashSet<String> vehiclesOverSpeedToday5T10TT08) {
//        this.vehiclesOverSpeedToday5T10TT08 = vehiclesOverSpeedToday5T10TT08;
//    }

    public int getTotalOverSpeedToday10T20() {
        return totalOverSpeedToday10T20;
    }

    public void setTotalOverSpeedToday10T20(int totalOverSpeedToday10T20) {
        this.totalOverSpeedToday10T20 = totalOverSpeedToday10T20;
    }

    public int getTotalOverSpeedToday10T20TT08() {
        return totalOverSpeedToday10T20TT08;
    }

    public void setTotalOverSpeedToday10T20TT08(int totalOverSpeedToday10T20TT08) {
        this.totalOverSpeedToday10T20TT08 = totalOverSpeedToday10T20TT08;
    }

//    public HashSet<String> getVehiclesOverSpeedToday10T20() {
//        return vehiclesOverSpeedToday10T20;
//    }
//
//    public void setVehiclesOverSpeedToday10T20(HashSet<String> vehiclesOverSpeedToday10T20) {
//        this.vehiclesOverSpeedToday10T20 = vehiclesOverSpeedToday10T20;
//    }
//
//    public HashSet<String> getVehiclesOverSpeedToday10T20TT08() {
//        return vehiclesOverSpeedToday10T20TT08;
//    }
//
//    public void setVehiclesOverSpeedToday10T20TT08(HashSet<String> vehiclesOverSpeedToday10T20TT08) {
//        this.vehiclesOverSpeedToday10T20TT08 = vehiclesOverSpeedToday10T20TT08;
//    }

    public int getTotalOverSpeedToday20T35() {
        return totalOverSpeedToday20T35;
    }

    public void setTotalOverSpeedToday20T35(int totalOverSpeedToday20T35) {
        this.totalOverSpeedToday20T35 = totalOverSpeedToday20T35;
    }

    public int getTotalOverSpeedToday20T35TT08() {
        return totalOverSpeedToday20T35TT08;
    }

    public void setTotalOverSpeedToday20T35TT08(int totalOverSpeedToday20T35TT08) {
        this.totalOverSpeedToday20T35TT08 = totalOverSpeedToday20T35TT08;
    }

//    public HashSet<String> getVehiclesOverSpeedToday20T35() {
//        return vehiclesOverSpeedToday20T35;
//    }
//
//    public void setVehiclesOverSpeedToday20T35(HashSet<String> vehiclesOverSpeedToday20T35) {
//        this.vehiclesOverSpeedToday20T35 = vehiclesOverSpeedToday20T35;
//    }
//
//    public HashSet<String> getVehiclesOverSpeedToday20T35TT08() {
//        return vehiclesOverSpeedToday20T35TT08;
//    }
//
//    public void setVehiclesOverSpeedToday20T35TT08(HashSet<String> vehiclesOverSpeedToday20T35TT08) {
//        this.vehiclesOverSpeedToday20T35TT08 = vehiclesOverSpeedToday20T35TT08;
//    }

    public int getTotalOverSpeedTodayGT35() {
        return totalOverSpeedTodayGT35;
    }

    public void setTotalOverSpeedTodayGT35(int totalOverSpeedTodayGT35) {
        this.totalOverSpeedTodayGT35 = totalOverSpeedTodayGT35;
    }

    public int getTotalOverSpeedTodayGT35TT08() {
        return totalOverSpeedTodayGT35TT08;
    }

    public void setTotalOverSpeedTodayGT35TT08(int totalOverSpeedTodayGT35TT08) {
        this.totalOverSpeedTodayGT35TT08 = totalOverSpeedTodayGT35TT08;
    }

//    public HashSet<String> getVehiclesOverSpeedTodayGT35() {
//        return vehiclesOverSpeedTodayGT35;
//    }
//
//    public void setVehiclesOverSpeedTodayGT35(HashSet<String> vehiclesOverSpeedTodayGT35) {
//        this.vehiclesOverSpeedTodayGT35 = vehiclesOverSpeedTodayGT35;
//    }
//
//    public HashSet<String> getVehiclesOverSpeedTodayGT35TT08() {
//        return vehiclesOverSpeedTodayGT35TT08;
//    }
//
//    public void setVehiclesOverSpeedTodayGT35TT08(HashSet<String> vehiclesOverSpeedTodayGT35TT08) {
//        this.vehiclesOverSpeedTodayGT35TT08 = vehiclesOverSpeedTodayGT35TT08;
//    }

    public short getSpeedMax() {
        return speedMax;
    }

    public void setSpeedMax(short speedMax) {
        this.speedMax = speedMax;
    }

    public int getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(int sumDuration) {
        this.sumDuration = sumDuration;
    }

    public double getSumDistanceToday() {
        return sumDistanceToday;
    }

    public void setSumDistanceToday(double sumDistanceToday) {
        this.sumDistanceToday = sumDistanceToday;
    }

    public double getSumOverSpeedDistance() {
        return sumOverSpeedDistance;
    }

    public void setSumOverSpeedDistance(double sumOverSpeedDistance) {
        this.sumOverSpeedDistance = sumOverSpeedDistance;
    }

    public int getSumOverSpeedDurationTT08() {
        return sumOverSpeedDurationTT08;
    }

    public void setSumOverSpeedDurationTT08(int sumOverSpeedDurationTT08) {
        this.sumOverSpeedDurationTT08 = sumOverSpeedDurationTT08;
    }

    public double getSumOverSpeedDistanceTT08() {
        return sumOverSpeedDistanceTT08;
    }

    public void setSumOverSpeedDistanceTT08(double sumOverSpeedDistanceTT08) {
        this.sumOverSpeedDistanceTT08 = sumOverSpeedDistanceTT08;
    }

//    public HashMap<String, CompanyStateDghcProvince> getMapDghc() {
//        if (mapDghc == null) {
//            mapDghc = new HashMap<String, CompanyStateDghcProvince>();
//        }
//        return mapDghc;
//    }
//
//    public void setMapDghc(HashMap<String, CompanyStateDghcProvince> mapDghc) {
//        this.mapDghc = mapDghc;
//    }

//    public CompanyXmitDaySum getXmit() {
//        return xmit;
//    }
//
//    public void setXmit(CompanyXmitDaySum xmit) {
//        this.xmit = xmit;
//    }

    public void resetDirtyFields() {
//         if (mapDghc != null) {
//             for (CompanyState.CompanyStateDghcProvince dghcItem : mapDghc.values()) {
//                 dghcItem.setDirty(false);
//             }
//         }
    }

//    public static class CompanyStateGeofenceProvinceInProgress implements Serializable {
//        private static final long serialVersionUID = 3141116942674806257L;
//
//        private String dghcProvince = null;
//        private double dghcDistance = 0;
//
//        public double getDghcDistance() {
//            return dghcDistance;
//        }
//
//        public void setDghcDistance(double dghcDistance) {
//            this.dghcDistance = dghcDistance;
//        }
//
//        public String getDghcProvince() {
//            return dghcProvince;
//        }
//
//        public void setDghcProvince(String dghcProvince) {
//            this.dghcProvince = dghcProvince;
//        }
//    }

//    public static class CompanyStateDghcProvince implements Serializable {
//        private static final long serialVersionUID = -7086179700069356722L;
//
//        private transient boolean dirty = false; // indidate that this object has been modified
//
//        private String dghcProvince = null;
//
//        /*********************************************************/
//        /*********************************************************/
//        /** CAC THAM SO CAN TINH TOAN CHO MOI TINH               */
//        /*********************************************************/
//        /*********************************************************/
//        /** so xe vi pham trong tinh                             */
//        /** so xe hoat dong trong tinh                           */
//        /** km vi pham trong tinh                                */
//        /** tong km hoat dong trong tinh                         */
//        /** so lan LT5 vi pham trong tinh                        */
//        /** so lan 5T10 vi pham trong tinh                       */
//        /** so lan 10T20 vi pham trong tinh                      */
//        /** so lan 20T35 vi pham trong tinh                      */
//        /** so lan GT35 vi pham trong tinh                       */
//        /** tong so lan vi pham trong tinh                       */
//        /** thong thoi gian vi pham trong tinh                   */
//        /** van toc cao nhat xe vi pham trong tinh               */
//        /*********************************************************/
//
//        private HashSet<String> dghcVehiclesOverSpeed = new HashSet<String>();
//        private HashSet<String> dghcVehiclesOverSpeedTT08 = new HashSet<String>();
//
//        private HashSet<String> dghcVehicles = new HashSet<String>();
//        private double dghcDistanceOverSpeed = 0;
//        private double dghcDistanceOverSpeedTT08 = 0;
//        private double dghcDistance = 0;
////        private double dghcDistanceInProgress = 0;
////        private double dghcDistanceTrip = 0;
//
//        /////
//        // OVERSPEED
//        private int dghcCount = 0;
//        private int dghcCountLT5 = 0;
//        private int dghcCount5T10 = 0;
//        private int dghcCount10T20 = 0;
//        private int dghcCount20T35 = 0;
//        private int dghcCountGT35 = 0;
//        private int dghcDurationOverSpeed = 0; // unix time
//        private short dghcMaxSpeed = 0;
//        //
//        private int dghcCountTT08 = 0;
//        private int dghcCountLT5TT08 = 0;
//        private int dghcCount5T10TT08 = 0;
//        private int dghcCount10T20TT08 = 0;
//        private int dghcCount20T35TT08 = 0;
//        private int dghcCountGT35TT08 = 0;
//        private int dghcDurationOverSpeedTT08 = 0; // unix time
//        private short dghcMaxSpeedTT08 = 0;
//
//        public String getDghcProvince() {
//            return dghcProvince;
//        }
//
//        public void setDghcProvince(String dghcProvince) {
//            this.dghcProvince = dghcProvince;
//        }
//
//        public double getDghcDistanceOverSpeed() {
//            return dghcDistanceOverSpeed;
//        }
//
//        public void setDghcDistanceOverSpeed(double dghcDistanceOverSpeed) {
//            this.dghcDistanceOverSpeed = dghcDistanceOverSpeed;
//        }
//
////        public double getDghcDistance() {
////            return dghcDistance;
////        }
////
////        public void setDghcDistance(double dghcDistance) {
////            this.dghcDistance = dghcDistance;
////        }
//
//
////        public double getDghcDistanceInProgress() {
////            return dghcDistanceInProgress;
////        }
////
////        public void setDghcDistanceInProgress(double dghcDistanceInProgress) {
////            this.dghcDistanceInProgress = dghcDistanceInProgress;
////        }
////
////        public double getDghcDistanceTrip() {
////            return dghcDistanceTrip;
////        }
////
////        public void setDghcDistanceTrip(double dghcDistanceTrip) {
////            this.dghcDistanceTrip = dghcDistanceTrip;
////        }
//
//        public int getDghcCount() {
//            return dghcCount;
//        }
//
//        public void setDghcCount(int dghcCount) {
//            this.dghcCount = dghcCount;
//        }
//
//        public int getDghcCountLT5() {
//            return dghcCountLT5;
//        }
//
//        public void setDghcCountLT5(int dghcCountLT5) {
//            this.dghcCountLT5 = dghcCountLT5;
//        }
//
//        public double getDghcDistance() {
//            return dghcDistance;
//        }
//
//        public void setDghcDistance(double dghcDistance) {
//            this.dghcDistance = dghcDistance;
//        }
//
//        public int getDghcCount5T10() {
//            return dghcCount5T10;
//        }
//
//        public void setDghcCount5T10(int dghcCount5T10) {
//            this.dghcCount5T10 = dghcCount5T10;
//        }
//
//        public int getDghcCount10T20() {
//            return dghcCount10T20;
//        }
//
//        public void setDghcCount10T20(int dghcCount10T20) {
//            this.dghcCount10T20 = dghcCount10T20;
//        }
//
//        public int getDghcCount20T35() {
//            return dghcCount20T35;
//        }
//
//        public void setDghcCount20T35(int dghcCount20T35) {
//            this.dghcCount20T35 = dghcCount20T35;
//        }
//
//        public int getDghcCountGT35() {
//            return dghcCountGT35;
//        }
//
//        public void setDghcCountGT35(int dghcCountGT35) {
//            this.dghcCountGT35 = dghcCountGT35;
//        }
//
//        public int getDghcDurationOverSpeed() {
//            return dghcDurationOverSpeed;
//        }
//
//        public void setDghcDurationOverSpeed(int dghcDurationOverSpeed) {
//            this.dghcDurationOverSpeed = dghcDurationOverSpeed;
//        }
//
//        public short getDghcMaxSpeed() {
//            return dghcMaxSpeed;
//        }
//
//        public void setDghcMaxSpeed(short dghcMaxSpeed) {
//            this.dghcMaxSpeed = dghcMaxSpeed;
//        }
//
//        public HashSet<String> getDghcVehicles() {
//            return dghcVehicles;
//        }
//
//        public void setDghcVehicles(HashSet<String> dghcVehicles) {
//            this.dghcVehicles = dghcVehicles;
//        }
//
////        public double getDghcDistanceInProgress() {
////            return dghcDistanceInProgress;
////        }
////
////        public void setDghcDistanceInProgress(double dghcDistanceInProgress) {
////            this.dghcDistanceInProgress = dghcDistanceInProgress;
////        }
//
//        public HashSet<String> getDghcVehiclesOverSpeed() {
//            return dghcVehiclesOverSpeed;
//        }
//
//        public void setDghcVehiclesOverSpeed(HashSet<String> dghcVehiclesOverSpeed) {
//            this.dghcVehiclesOverSpeed = dghcVehiclesOverSpeed;
//        }
//
//        public HashSet<String> getDghcVehiclesOverSpeedTT08() {
//            if (dghcVehiclesOverSpeedTT08 == null) {
//                dghcVehiclesOverSpeedTT08 = new HashSet<String>();
//            }
//            return dghcVehiclesOverSpeedTT08;
//        }
//
//        public void setDghcVehiclesOverSpeedTT08(HashSet<String> dghcVehiclesOverSpeedTT08) {
//            this.dghcVehiclesOverSpeedTT08 = dghcVehiclesOverSpeedTT08;
//        }
//
//        public double getDghcDistanceOverSpeedTT08() {
//            return dghcDistanceOverSpeedTT08;
//        }
//
//        public void setDghcDistanceOverSpeedTT08(double dghcDistanceOverSpeedTT08) {
//            this.dghcDistanceOverSpeedTT08 = dghcDistanceOverSpeedTT08;
//        }
//
//        public int getDghcCountTT08() {
//            return dghcCountTT08;
//        }
//
//        public void setDghcCountTT08(int dghcCountTT08) {
//            this.dghcCountTT08 = dghcCountTT08;
//        }
//
//        public int getDghcCountLT5TT08() {
//            return dghcCountLT5TT08;
//        }
//
//        public void setDghcCountLT5TT08(int dghcCountLT5TT08) {
//            this.dghcCountLT5TT08 = dghcCountLT5TT08;
//        }
//
//        public int getDghcCount5T10TT08() {
//            return dghcCount5T10TT08;
//        }
//
//        public void setDghcCount5T10TT08(int dghcCount5T10TT08) {
//            this.dghcCount5T10TT08 = dghcCount5T10TT08;
//        }
//
//        public int getDghcCount10T20TT08() {
//            return dghcCount10T20TT08;
//        }
//
//        public void setDghcCount10T20TT08(int dghcCount10T20TT08) {
//            this.dghcCount10T20TT08 = dghcCount10T20TT08;
//        }
//
//        public int getDghcCount20T35TT08() {
//            return dghcCount20T35TT08;
//        }
//
//        public void setDghcCount20T35TT08(int dghcCount20T35TT08) {
//            this.dghcCount20T35TT08 = dghcCount20T35TT08;
//        }
//
//        public int getDghcCountGT35TT08() {
//            return dghcCountGT35TT08;
//        }
//
//        public void setDghcCountGT35TT08(int dghcCountGT35TT08) {
//            this.dghcCountGT35TT08 = dghcCountGT35TT08;
//        }
//
//        public int getDghcDurationOverSpeedTT08() {
//            return dghcDurationOverSpeedTT08;
//        }
//
//        public void setDghcDurationOverSpeedTT08(int dghcDurationOverSpeedTT08) {
//            this.dghcDurationOverSpeedTT08 = dghcDurationOverSpeedTT08;
//        }
//
//        public short getDghcMaxSpeedTT08() {
//            return dghcMaxSpeedTT08;
//        }
//
//        public void setDghcMaxSpeedTT08(short dghcMaxSpeedTT08) {
//            this.dghcMaxSpeedTT08 = dghcMaxSpeedTT08;
//        }
//
//        public boolean isDirty() {
//            return dirty;
//        }
//
//        public void setDirty(boolean dirty) {
//            this.dirty = dirty;
//        }
//    }
}
