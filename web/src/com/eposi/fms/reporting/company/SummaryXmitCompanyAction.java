package com.eposi.fms.reporting.company;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SummaryXmitCompanyAction extends AbstractAction {
    private static final long serialVersionUID = 176123459016672307L;
    private String format = "html";
    private String start;
    private String end;
    private String companyId;
    private Company company;
    private List<SummaryXmitVehicleAction.VehicleXmitSummary> items = new ArrayList<SummaryXmitVehicleAction.VehicleXmitSummary>();

	@Override
	public String execute() throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        Date dateStart = null;
        Date dateEnd = null;
        if (StringUtils.isEmpty(companyId)) {
            return SUCCESS;
        }

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

        int days = Days.daysBetween(LocalDate.fromDateFields(dateStart), LocalDate.fromDateFields(dateEnd)).getDays();

        if (days >= 32) {
            this.addActionError("Khoảng thời gian xem báo cáo (từ ngày - đến ngày) phải ngắn hơn 1 tháng (31 ngày).");
            return SUCCESS;
        }
        company = beanFmsDao.getCompany(companyId);
        if (company==null) {
            return SUCCESS;
        }

        List<Vehicle> lstVehicle = new ArrayList<Vehicle>();
        lstVehicle = beanFmsDao.searchVehicleByCompanyId(company.getId());
        for (Vehicle vehicle : lstVehicle) {
            SummaryXmitVehicleAction.VehicleXmitSummary vehicleXmitSummary = null;
            for (int i=0; i <= days; i++) {
                LocalDate d = LocalDate.fromDateFields(dateStart).withFieldAdded(DurationFieldType.days(), i);
                Date date = d.toDate();
                SummaryXmitVehicleAction.VehicleXmitSummary item = beanReportReader.getVehicleXmitSummary(vehicle.getCompany().getId(), vehicle.getId(), date);
                if (vehicleXmitSummary == null) {
                    vehicleXmitSummary = item;
                } else {
                    vehicleXmitSummary.setMsgCount(vehicleXmitSummary.getMsgCount()+item.getMsgCount());
                    vehicleXmitSummary.setCountGPSNoSignal(vehicleXmitSummary.getCountGPSNoSignal()+item.getCountGPSNoSignal());
                    vehicleXmitSummary.setGpsNoSignalDuration(vehicleXmitSummary.getGpsNoSignalDuration()+item.getGpsNoSignalDuration());
                    vehicleXmitSummary.setCountDataNoSignal(vehicleXmitSummary.getCountDataNoSignal()+item.getCountDataNoSignal());
                    vehicleXmitSummary.setDataNoSignalDuration(vehicleXmitSummary.getDataNoSignalDuration()+item.getDataNoSignalDuration());
                }
            }

            if (vehicleXmitSummary != null) {
                items.add(vehicleXmitSummary);
            }
        }

        if (!format.equals("html")) {
            if (format.equals("xlsx")) {
                return "xlsx";
            }
        }

		return SUCCESS;
	}

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public List<SummaryXmitVehicleAction.VehicleXmitSummary> getItems() {
        return items;
    }

    public void setItems(List<SummaryXmitVehicleAction.VehicleXmitSummary> items) {
        this.items = items;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
