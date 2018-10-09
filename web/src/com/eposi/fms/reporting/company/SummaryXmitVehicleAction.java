package com.eposi.fms.reporting.company;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SummaryXmitVehicleAction extends AbstractAction {
    private static final long serialVersionUID = 176123459016672307L;
    private String format = "html";
    private String start;
    private String end;
    private String vehicleId;
    private List<VehicleXmitSummary> items = new ArrayList<VehicleXmitSummary>();

	@Override
	public String execute() throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        Date dateStart = null;
        Date dateEnd = null;

        if (StringUtils.isEmpty(start)) {
            start = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }
        if (StringUtils.isEmpty(end)) {
            end = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }
        try {
            dateStart = FormatUtil.parseDate(start, "dd/MM/yyyy");
            dateEnd = FormatUtil.parseDate(end, "dd/MM/yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(StringUtils.isEmpty(vehicleId)){
            return SUCCESS;
        }
        int days = Days.daysBetween(LocalDate.fromDateFields(dateStart), LocalDate.fromDateFields(dateEnd)).getDays();

        if (days >= 32) {
            this.addActionError("Khoảng thời gian xem báo cáo (từ ngày - đến ngày) phải ngắn hơn 1 tháng (31 ngày).");
            return SUCCESS;
        }

        Vehicle vehicle = null;
        VehicleXmitSummary vehicleXmitSummary;
        for (int i = 0; i <= days; i++) {
            LocalDate d = LocalDate.fromDateFields(dateStart).withFieldAdded(DurationFieldType.days(), i);
            Date date = d.toDate();
            if(vehicleId.isEmpty()){continue;}
            vehicle = beanFmsDao.getVehicle(vehicleId);
            if(vehicle != null) {
                vehicleXmitSummary = null;//beanReportReader.getVehicleXmitSummary(vehicleId, date);
                if(vehicleXmitSummary!=null) {
                    items.add(vehicleXmitSummary);
                }
            }
        }

        if (!format.equals("html")) {
            if (format.equals("xlsx")) {
                return "xlsx";
            }
        }

		return SUCCESS;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<VehicleXmitSummary> getItems() {
        return items;
    }

    public void setItems(List<VehicleXmitSummary> items) {
        this.items = items;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public static class VehicleXmitSummary implements Serializable {
        private static final long serialVersionUID = -1168682175884298800L;

        private String vehicleId;
        private Date date;
        private long msgCount;

        private double countDataNoSignal = 0;
        private long   dataNoSignalDuration;

        private int  countGPSNoSignal = 0;
        private long gpsNoSignalDuration    = 0;

        public VehicleXmitSummary() {

        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public long getMsgCount() {
            return msgCount;
        }

        public void setMsgCount(long msgCount) {
            this.msgCount = msgCount;
        }

        public double getCountDataNoSignal() {
            return countDataNoSignal;
        }

        public void setCountDataNoSignal(double countDataNoSignal) {
            this.countDataNoSignal = countDataNoSignal;
        }

        public long getDataNoSignalDuration() {
            return dataNoSignalDuration;
        }

        public void setDataNoSignalDuration(long dataNoSignalDuration) {
            this.dataNoSignalDuration = dataNoSignalDuration;
        }

        public int getCountGPSNoSignal() {
            return countGPSNoSignal;
        }

        public void setCountGPSNoSignal(int countGPSNoSignal) {
            this.countGPSNoSignal = countGPSNoSignal;
        }

        public long getGpsNoSignalDuration() {
            return gpsNoSignalDuration;
        }

        public void setGpsNoSignalDuration(long gpsNoSignalDuration) {
            this.gpsNoSignalDuration = gpsNoSignalDuration;
        }
    }
}
