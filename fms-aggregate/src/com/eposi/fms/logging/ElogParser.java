package com.eposi.fms.logging;

import com.eposi.fms.common.FmsUtil;
import com.eposi.fms.common.RegexUtil;
import com.eposi.fms.common.ReportDefine;
import com.eposi.fms.model.*;
import java.util.Arrays;

public class ElogParser {

	public static String getJSONVehicleAggregation(VehicleAggregationWindow item) {
		if (item == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(item.getCompanyId()).append("\",");
		sb.append("\"").append(ReportDefine.VEHICLE).append("\":\"").append(item.getVehicle()).append("\",");
		sb.append("\"").append(ReportDefine.TRIP_COUNT).append("\":").append(item.getTripCount()).append(",");
		sb.append("\"").append(ReportDefine.TRIP_KM).append("\":").append(FmsUtil.getShortDouble(item.getTripKm())).append(",");
		sb.append("\"").append(ReportDefine.STOP_COUNT).append("\":").append(item.getStopCount()).append(",");
		sb.append("\"").append(ReportDefine.STOP_DURATION).append("\":").append(item.getStopDuration()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_COUNT).append("\":").append(item.getOverspeedCount()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_DURATION).append("\":").append(item.getOverspeedDuration()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_MAX).append("\":").append(item.getOverspeedMax()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_KM).append("\":").append(FmsUtil.getShortDouble(item.getOverspeedKm())).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_MSG_RANGE).append("\":\"").append(Arrays.toString(item.getOverspeedMsgRange())).append("\",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_RANGE).append("\":\"").append(Arrays.toString(item.getOverspeedRange())).append("\",");
		sb.append("\"").append(ReportDefine.OVER_4H_COUNT).append("\":").append(item.getOvertime4hCount()).append(",");
		sb.append("\"").append(ReportDefine.OVER_10H_COUNT).append("\":").append(item.getOvertime10Count()).append(",");
		sb.append("\"").append(ReportDefine.MSG_COUNT).append("\":").append(item.getMsgCount()).append(",");
		sb.append("\"").append(ReportDefine.GPS_NO_SIGNAL_COUNT).append("\":").append(item.getGpsNoSignalCount()).append(",");
		sb.append("\"").append(ReportDefine.GPS_NO_SIGNAL_DURATION).append("\":").append(item.getGpsNoSignalDuration()).append(",");
		sb.append("\"").append(ReportDefine.DATA_NO_SIGNAL_COUNT).append("\":").append(item.getDataNoSignalCount()).append(",");
		sb.append("\"").append(ReportDefine.DATA_NO_SIGNAL_DURATION).append("\":").append(item.getDataNoSignalDuration());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONDriverAggregation(DriverAggregationWindow item) {
		if (item == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.DRIVER_NAME).append("\":\"").append(item.getDriverName()).append("\",");
		sb.append("\"").append(ReportDefine.LIICENCE_KEY).append("\":\"").append(item.getLicenceKey()).append("\",");
		sb.append("\"").append(ReportDefine.TRIP_COUNT).append("\":").append(item.getTripCount()).append(",");
		sb.append("\"").append(ReportDefine.TRIP_KM).append("\":").append(item.getTripKm()).append(",");
		sb.append("\"").append(ReportDefine.STOP_COUNT).append("\":").append(item.getStopCount()).append(",");
		sb.append("\"").append(ReportDefine.STOP_DURATION).append("\":").append(item.getStopDuration()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_COUNT).append("\":").append(item.getOverspeedCount()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_DURATION).append("\":").append(item.getOverspeedDuration()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_MAX).append("\":").append(item.getOverspeedMax()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_KM).append("\":").append(item.getOverspeedKm()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_MSG_RANGE).append("\":\"").append(Arrays.toString(item.getOverspeedMsgRange())).append("\",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_RANGE).append("\":\"").append(Arrays.toString(item.getOverspeedRange())).append("\",");
		sb.append("\"").append(ReportDefine.OVER_4H_COUNT).append("\":").append(item.getOvertime4hCount()).append(",");
		sb.append("\"").append(ReportDefine.OVER_10H_COUNT).append("\":").append(item.getOvertime10Count());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONCompanyAggregation(CompanyAggregationWindow item) {
		if (item == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(item.getCompanyId()).append("\",");
		sb.append("\"").append(ReportDefine.TRIP_COUNT).append("\":").append(item.getTripCount()).append(",");
		sb.append("\"").append(ReportDefine.TRIP_KM).append("\":").append(FmsUtil.getShortDouble(item.getTripKm())).append(",");
		sb.append("\"").append(ReportDefine.STOP_COUNT).append("\":").append(item.getStopCount()).append(",");
		sb.append("\"").append(ReportDefine.STOP_DURATION).append("\":").append(item.getStopDuration()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_COUNT).append("\":").append(item.getOverspeedCount()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_DURATION).append("\":").append(item.getOverspeedDuration()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_MAX).append("\":").append(item.getOverspeedMax()).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_KM).append("\":").append(FmsUtil.getShortDouble(item.getOverspeedKm())).append(",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_MSG_RANGE).append("\":\"").append(Arrays.toString(item.getOverspeedMsgRange())).append("\",");
		sb.append("\"").append(ReportDefine.OVER_SPEED_RANGE).append("\":\"").append(Arrays.toString(item.getOverspeedRange())).append("\",");
		sb.append("\"").append(ReportDefine.OVER_4H_COUNT).append("\":").append(item.getOvertime4hCount()).append(",");
		sb.append("\"").append(ReportDefine.OVER_10H_COUNT).append("\":").append(item.getOvertime10Count()).append(",");
		sb.append("\"").append(ReportDefine.MSG_COUNT).append("\":").append(item.getMsgCount()).append(",");
		sb.append("\"").append(ReportDefine.DATA_NO_SIGNAL_COUNT).append("\":").append(item.getDataNoSignalCount()).append(",");
		sb.append("\"").append(ReportDefine.DATA_NO_SIGNAL_DURATION).append("\":").append(item.getDataNoSignalDuration()).append(",");
		sb.append("\"").append(ReportDefine.GPS_NO_SIGNAL_COUNT).append("\":").append(item.getGpsNoSignalCount()).append(",");
		sb.append("\"").append(ReportDefine.GPS_NO_SIGNAL_DURATION).append("\":").append(item.getGpsNoSignalDuration());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONXmitAggregationWindow(XmitAggregationWindow item) {
		if (item == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.DATA_NO_SIGNAL_COUNT).append("\":").append(item.getDataCount()).append(",");
		sb.append("\"").append(ReportDefine.DATA_NO_SIGNAL_DURATION).append("\":").append(item.getDataDuration()).append(",");
		sb.append("\"").append(ReportDefine.GPS_NO_SIGNAL_COUNT).append("\":").append(item.getGpsCount()).append(",");
		sb.append("\"").append(ReportDefine.GPS_NO_SIGNAL_DURATION).append("\":").append(item.getGpsDuration());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONXmit(DataXmit xmit) {
		if (xmit == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(xmit.getCompany()).append("\",");
		sb.append("\"").append(ReportDefine.VEHICLE).append("\":\"").append(xmit.getVehicle()).append("\",");
		sb.append("\"").append(ReportDefine.TYPE).append("\":").append(xmit.getType()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_TIME).append("\":").append(xmit.getBeginTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.END_TIME).append("\":").append(xmit.getEndTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.DURATION).append("\":").append(xmit.getDuration());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONGeoPoi(GeoPoi geoPoi) {
		if (geoPoi == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.VEHICLE).append("\":\"").append(geoPoi.getVehicle()).append("\",");
		sb.append("\"").append(ReportDefine.GEO_NAME).append("\":\"").append(geoPoi.getName().trim()).append("\",");
		sb.append("\"").append(ReportDefine.ADDRESS).append("\":\"").append(geoPoi.getAddress().trim()).append("\",");
		sb.append("\"").append(ReportDefine.DRIVER_NAME).append("\":\"").append(geoPoi.getDriverName().trim()).append("\",");
		sb.append("\"").append(ReportDefine.BEGIN_TIME).append("\":").append(geoPoi.getTimeIn().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.END_TIME).append("\":").append(geoPoi.getTimeOut().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.DURATION).append("\":").append(geoPoi.getDuration());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONTrip(Trip trip) {
		if (trip == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(trip.getCompany()).append("\",");
		sb.append("\"").append(ReportDefine.VEHICLE).append("\":\"").append(trip.getVehicle()).append("\",");
		String driver =	RegexUtil.validateDriver(trip.getDriver())? trip.getDriver().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER).append("\":\"").append(driver).append("\",");
		String driverName 	=	trip.getDriverName()!=null ? trip.getDriverName().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER_NAME).append("\":\"").append(driverName).append("\",");
		String licenceKey =	RegexUtil.validateDriver(trip.getLicenceKey())? trip.getLicenceKey().trim():"";
		sb.append("\"").append(ReportDefine.LIICENCE_KEY).append("\":\"").append(licenceKey).append("\",");
		sb.append("\"").append(ReportDefine.BEGIN_X).append("\":").append(trip.getBeginX()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_Y).append("\":").append(trip.getBeginY()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_TIME).append("\":").append(trip.getBeginTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_ADDRESS).append("\":\"").append(trip.getBeginAddress()).append("\",");
		sb.append("\"").append(ReportDefine.END_TIME).append("\":").append(trip.getEndTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.END_X).append("\":").append(trip.getEndX()).append(",");
		sb.append("\"").append(ReportDefine.END_Y).append("\":").append(trip.getEndY()).append(",");
		sb.append("\"").append(ReportDefine.END_ADDRESS).append("\":\"").append(trip.getEndAddress()).append("\",");
		sb.append("\"").append(ReportDefine.DURATION).append("\":").append(trip.getDuration()).append(",");
		sb.append("\"").append(ReportDefine.DISTANCE).append("\":").append(FmsUtil.getShortDouble(trip.getTripKm())).append(",");
		sb.append("\"").append(ReportDefine.MAX_SPEED).append("\":").append(trip.getMaxSpeedGPS());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONStop(Stop stop) {
		if (stop == null) {
			throw new NullPointerException("msg is null!");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(stop.getCompany()).append("\",");
		sb.append("\"").append(ReportDefine.VEHICLE).append("\":\"").append(stop.getVehicle()).append("\",");
		String driver =	RegexUtil.validateDriver(stop.getDriver())? stop.getDriver().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER).append("\":\"").append(driver).append("\",");
		String driverName 	=	stop.getDriverName()!=null? stop.getDriverName().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER_NAME).append("\":\"").append(driverName).append("\",");
		String licenceKey =	RegexUtil.validateDriver(stop.getLicenceKey())? stop.getLicenceKey().trim():"";
		sb.append("\"").append(ReportDefine.LIICENCE_KEY).append("\":\"").append(licenceKey).append("\",");
		sb.append("\"").append(ReportDefine.ADDRESS).append("\":\"").append(stop.getAddress()).append("\",");
		sb.append("\"").append(ReportDefine.X).append("\":").append(stop.getX()).append(",");
		sb.append("\"").append(ReportDefine.Y).append("\":").append(stop.getY()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_TIME).append("\":").append(stop.getBeginTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.END_TIME).append("\":").append(stop.getEndTime().getTime() / 1000L).append(",");
		sb.append("\"").append(ReportDefine.DURATION).append("\":").append(stop.getDuration());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONOver10h(DrivingOver10h over10h) {
		if (over10h == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(over10h.getCompany()).append("\",");
		String driver =	RegexUtil.validateDriver(over10h.getDriver())? over10h.getDriver().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER).append("\":\"").append(driver).append("\",");
		String driverName 	=	over10h.getDriverName()!=null? over10h.getDriverName().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER_NAME).append("\":\"").append(driverName).append("\",");
		String licenceKey =	RegexUtil.validateDriver(over10h.getLicenceKey())? over10h.getLicenceKey().trim():"";
		sb.append("\"").append(ReportDefine.LIICENCE_KEY).append("\":\"").append(licenceKey).append("\",");
		sb.append("\"").append(ReportDefine.BEGIN_X).append("\":").append(over10h.getBeginX()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_Y).append("\":").append(over10h.getBeginY()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_TIME).append("\":").append(over10h.getBeginTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_ADDRESS).append("\":\"").append(over10h.getBeginAddress()).append("\",");
		sb.append("\"").append(ReportDefine.END_TIME).append("\":").append(over10h.getEndTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.END_Y).append("\":").append(over10h.getEndX()).append(",");
		sb.append("\"").append(ReportDefine.END_Y).append("\":").append(over10h.getEndY()).append(",");
		sb.append("\"").append(ReportDefine.END_ADDRESS).append("\":\"").append(over10h.getEndAddress()).append("\",");
		sb.append("\"").append(ReportDefine.DURATION).append("\":").append(over10h.getDuration());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONOver4h(DrivingOver4h over4h) {
		if (over4h == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(over4h.getCompany()).append("\",");
		String driver =	RegexUtil.validateDriver(over4h.getDriver())? over4h.getDriver().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER).append("\":\"").append(driver).append("\",");
		String driverName 	=	over4h.getDriverName()!=null? over4h.getDriverName().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER_NAME).append("\":\"").append(driverName).append("\",");
		String licenceKey =	RegexUtil.validateDriver(over4h.getLicenceKey())? over4h.getLicenceKey().trim():"";
		sb.append("\"").append(ReportDefine.LIICENCE_KEY).append("\":\"").append(licenceKey).append("\",");
		sb.append("\"").append(ReportDefine.BEGIN_ADDRESS).append("\":\"").append(over4h.getBeginAddress()).append("\",");
		sb.append("\"").append(ReportDefine.BEGIN_X).append("\":").append(over4h.getBeginX()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_Y).append("\":").append(over4h.getBeginY()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_TIME).append("\":").append(over4h.getBeginTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.END_ADDRESS).append("\":\"").append(over4h.getEndAddress()).append("\",");
		sb.append("\"").append(ReportDefine.END_X).append("\":").append(over4h.getEndX()).append(",");
		sb.append("\"").append(ReportDefine.END_Y).append("\":").append(over4h.getEndY()).append(",");
		sb.append("\"").append(ReportDefine.END_TIME).append("\":").append(over4h.getEndTime().getTime() / 1000L).append(",");
		sb.append("\"").append(ReportDefine.DURATION).append("\":").append(over4h.getDuration());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONOverSpeed(OverSpeed os) {
		if (os == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(os.getCompany()).append("\",");
		sb.append("\"").append(ReportDefine.VEHICLE).append("\":\"").append(os.getVehicle()).append("\",");
		String driver =	RegexUtil.validateDriver(os.getDriver())? os.getDriver().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER).append("\":\"").append(driver).append("\",");
		String driverName 	=	os.getDriverName()!=null? os.getDriverName().trim():"";
		sb.append("\"").append(ReportDefine.DRIVER_NAME).append("\":\"").append(driverName).append("\",");
		String licenceKey =	RegexUtil.validateDriver(os.getLicenceKey())? os.getLicenceKey().trim():"";
		sb.append("\"").append(ReportDefine.LIICENCE_KEY).append("\":\"").append(licenceKey).append("\",");
		sb.append("\"").append(ReportDefine.BEGIN_ADDRESS).append("\":\"").append(os.getBeginAddress()).append("\",");
		sb.append("\"").append(ReportDefine.BEGIN_X).append("\":").append(os.getBeginX()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_Y).append("\":").append(os.getBeginY()).append(",");
		sb.append("\"").append(ReportDefine.BEGIN_TIME).append("\":").append(os.getBeginTime().getTime() / 1000L).append(",");
		sb.append("\"").append(ReportDefine.END_ADDRESS).append("\":\"").append(os.getEndAddress()).append("\",");
		sb.append("\"").append(ReportDefine.END_X).append("\":").append(os.getEndX()).append(",");
		sb.append("\"").append(ReportDefine.END_Y).append("\":").append(os.getEndY()).append(",");
		sb.append("\"").append(ReportDefine.END_TIME).append("\":").append(os.getEndTime().getTime()/1000L).append(",");
		sb.append("\"").append(ReportDefine.MAX_SPEED).append("\":").append(os.getSpeedLimit()).append(",");
		sb.append("\"").append(ReportDefine.DURATION).append("\":").append(os.getDuration()).append(",");
		sb.append("\"").append(ReportDefine.DISTANCE).append("\":").append(os.getOsKm());
		sb.append("}");
		return sb.toString();
	}

	public static String getJSONActivity(Activity item) {
		if (item == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"objectId\":\"").append(item.getObjectId()).append("\",");
		sb.append("\"objectName\":\"").append(item.getObjectName()).append("\",");
		sb.append("\"actionName\":\"").append(item.getActionName()).append("\",");
		sb.append("\"actorName\":\"").append(item.getActorName()).append("\",");
		sb.append("\"context\":\"").append(item.getContext()).append("\",");
		sb.append("\"date\":").append(item.getDate().getTime()/1000L).append(",");
		sb.append("\"icon\":\"").append("<i class=\\\"fa fa-stop time-icon bg-dark\\\"></i>").append("\",");
		sb.append("\"passive\":\"").append(item.isPassive()).append("\",");
		sb.append("\"iObjectName\":\"").append(item.getIndirectObjectName()).append("\"");
		sb.append("}");
		return sb.toString();
	}
}
