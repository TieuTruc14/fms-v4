package com.eposi.fms.reporting.company;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
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


public class SummaryXmitNationAction extends AbstractAction {
    private static final long serialVersionUID = 176123459016672307L;
    private String format = "html";
    private String start;
    private String end;
    private User   user;
    private List<CompanyXmitSummary> items = new ArrayList<CompanyXmitSummary>();

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

        int days = Days.daysBetween(LocalDate.fromDateFields(dateStart), LocalDate.fromDateFields(dateEnd)).getDays();

        if (days >= 32) {
            this.addActionError("Khoảng thời gian xem báo cáo (từ ngày - đến ngày) phải ngắn hơn 1 tháng (31 ngày).");
            return SUCCESS;
        }
        List<Company> lstCompany = new ArrayList<Company>();
//        lstCompany = beanFmsDao.listCompany();
//        for (Company company : lstCompany) {
//            CompanyXmitSummary companyXmitSummary = null;
//
//            for (int i=0; i <= days; i++) {
//                LocalDate d = LocalDate.fromDateFields(dateStart).withFieldAdded(DurationFieldType.days(), i);
//                Date date = d.toDate();
//                CompanyXmitSummary item = beanReportReader.getCompanyXmitSummary(company.getId(), date);
//                if (companyXmitSummary == null) {
//                    companyXmitSummary = item;
//                } else {
//                    companyXmitSummary.setMsgCount(companyXmitSummary.getMsgCount()+item.getMsgCount());
//                    companyXmitSummary.setCountGPSNoSignal(companyXmitSummary.getCountGPSNoSignal()+item.getCountGPSNoSignal());
//                    companyXmitSummary.setGpsNoSignalDuration(companyXmitSummary.getGpsNoSignalDuration()+item.getGpsNoSignalDuration());
//                    companyXmitSummary.setCountDataNoSignal(companyXmitSummary.getCountDataNoSignal()+item.getCountDataNoSignal());
//                    companyXmitSummary.setDataNoSignalDuration(companyXmitSummary.getDataNoSignalDuration()+item.getDataNoSignalDuration());
//                }
//            }
//
//            if (companyXmitSummary != null) {
//                items.add(companyXmitSummary);
//            }
//        }

        if (!format.equals("html")) {
            if (format.equals("xlsx")) {
                return "xlsx";
            }
        }

		return SUCCESS;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<CompanyXmitSummary> getItems() {
        return items;
    }

    public void setItems(List<CompanyXmitSummary> items) {
        this.items = items;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public static class CompanyXmitSummary implements Serializable {
        private static final long serialVersionUID = -1168682175884298800L;

        private Company company;
        private Date    date;
        private long msgCount;

        private double countDataNoSignal = 0;
        private long   dataNoSignalDuration;

        private int  countGPSNoSignal = 0;
        private long gpsNoSignalDuration    = 0;

        public CompanyXmitSummary() {

        }

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
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
