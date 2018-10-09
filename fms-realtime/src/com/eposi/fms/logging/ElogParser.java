package com.eposi.fms.logging;

import com.eposi.fms.common.FmsUtil;
import com.eposi.fms.common.RegexUtil;
import com.eposi.fms.common.ReportDefine;
import com.eposi.fms.model.*;
import com.eposi.fms.track.FmsProto;
import java.util.Arrays;

public class ElogParser {

	public static String getJSONWayPointBatch(FmsProto.WayPointBatch wPBatch) {
		if (wPBatch == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
			sb.append("\"m\":\"" + wPBatch.getDevice() + "\",");
			int unixTime = wPBatch.getPointList().get(0).getDatetime();
			sb.append("\"t\":" + unixTime+",");
			String driver =	RegexUtil.validateDriver(wPBatch.getDriver().trim())? wPBatch.getDriver().trim():"";
			sb.append("\"k\":\"" +driver+"\", "); // driver

			// inputs
			StringBuffer sbIO = new StringBuffer();
			sbIO.append(wPBatch.getIgnition()? 1:0).append((wPBatch.getDoor() ? 1:0)).append((wPBatch.getAircon() ? 1 : 0));
			sbIO.append((wPBatch.getEx1() ? 1:0)).append((wPBatch.getEx2() ? 1 : 0)).append((wPBatch.getEx3() ? 1 : 0));
			sb.append("\"io\":\"" + sbIO.toString()+"\",");

			// adcs
			sb.append("\"a0\":" + wPBatch.getAdc0() + ",");
			sb.append("\"a1\":" + wPBatch.getAdc1() + ",");
			//Point
			sb.append("\"Pois\":[");
				int size =wPBatch.getPointList().size();
				for(int i=0;i<size;i++){
					FmsProto.WayPointBatch.TrackPoint tp= wPBatch.getPoint(i);
					sb.append("{");
						sb.append("\"t\":" + tp.getDatetime()+ ",");
						sb.append("\"x\":" +tp.getX() + ",");
						sb.append("\"y\":" +tp.getY()+ ",");
						sb.append("\"s\":" + tp.getSpeed()+ "");
					if(i<size-1) {
						sb.append("},");
					}else {
						sb.append("}");
					}
				}
			sb.append("]");
		sb.append("}");

		return sb.toString();
	}

	public static String getJSONWayPoint(FmsProto.WayPoint wp) {
		if (wp == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
			sb.append("\"m\":\"" + wp.getDevice() + "\",");
			sb.append("\"t\":" + wp.getDatetime()+",");
			String driver =	RegexUtil.validateDriver(wp.getDriver().trim())? wp.getDriver().trim():"";
			sb.append("\"k\":\"" + driver + "\", "); // driver

			// inputs
			StringBuffer sbIO = new StringBuffer();
			sbIO.append(wp.getIgnition()? 1:0).append((wp.getDoor() ? 1:0)).append((wp.getAircon() ? 1 : 0));
			sbIO.append((wp.getEx1() ? 1:0)).append((wp.getEx2() ? 1 : 0)).append((wp.getEx3() ? 1 : 0));
			sb.append("\"io\":\"" + sbIO.toString() + "\",");
			// adcs
			sb.append("\"a0\":" + wp.getAdc0() + ",");
			sb.append("\"a1\":" + wp.getAdc1() + ",");
			//Point
			sb.append("\"Pois\":[{");
				sb.append("\"t\":" + wp.getDatetime() + ",");
				sb.append("\"x\":" +wp.getX() + ",");
				sb.append("\"y\":" +wp.getY() + ",");
				sb.append("\"s\":" + wp.getSpeed()+ "");
			sb.append("}]");
		sb.append(" }");
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

	public static String getJSONVehicleSum(VehicleAggregationWindow item) {
		if (item == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"").append(ReportDefine.COMPANY).append("\":\"").append(item.getCompanyId()).append("\",");
		sb.append("\"").append(ReportDefine.VEHICLE).append("\":\"").append(item.getId()).append("\",");
		sb.append("\"").append(ReportDefine.TRIP_COUNT).append("\":").append(item.getTripCount()).append(",");
		sb.append("\"").append(ReportDefine.TRIP_KM).append("\":").append(FmsUtil.getShortDouble(item.getTripKm())).append(",");
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
			sb.append("\"").append(ReportDefine.DISTANCE).append("\":").append(FmsUtil.getShortDouble(trip.getDistanceGPS())).append(",");
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
			sb.append("\"").append(ReportDefine.DURATION).append("\":").append(os.getDuration());
		sb.append("}");
		return sb.toString();
	}

}
