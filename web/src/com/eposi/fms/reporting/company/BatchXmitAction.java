package com.eposi.fms.reporting.company;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.reporting.atgt.BatchXmit;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BatchXmitAction extends AbstractAction {
    private static final long serialVersionUID = -3650766446153765754L;

    private String format = "html"; // pdf, cvs
    private String start; // start date
    private String end; // end date
    private User user;
    private String batchId; // end date

    private List<BatchXmit> items = new ArrayList<BatchXmit>();
	
	@Override
	public String execute() throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        Date dateStart = null;
        Date dateEnd = null;

        // date
        if (StringUtils.isEmpty(start)) {
            start = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }
        if (StringUtils.isEmpty(end)) {
            end = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }

        try {
            dateStart = FormatUtil.parseDate(start, "dd/MM/yyyy");
        } catch(Exception e)  {
        }
        try {
            dateEnd = FormatUtil.parseDate(end, "dd/MM/yyyy");
        } catch(Exception e)  {
        }

        int days = Days.daysBetween(LocalDate.fromDateFields(dateStart), LocalDate.fromDateFields(dateEnd)).getDays();

        if (days >= 32) {
            this.addActionError("Khoảng thời gian xem báo cáo (từ ngày - đến ngày) phải ngắn hơn 1 tháng (31 ngày).");
            return SUCCESS;
        }


        if (StringUtils.isNotEmpty(batchId)) {
            Batch batch = beanFmsDao.getBatch(batchId);
            if(batch!=null){
                if(batch.getKonexyId()!=null) {
                    for (int i = 0; i <= days; i++) {
                        LocalDate d = LocalDate.fromDateFields(dateStart).withFieldAdded(DurationFieldType.days(), i);
                        Date date = d.toDate();
                        List<BatchXmit> lstBatchXmit = beanReportReader.getBatchXmit(batch, date);
                        if (lstBatchXmit != null) {
                            items.addAll(lstBatchXmit);
                        }
                    }
                }
            }
        }else {
            List<Batch> lstBatch = beanFmsDao.listBatch();
            for(Batch batch : lstBatch){
                if(batch.getKonexyId()!=null) {
                    for (int i = 0; i <= days; i++) {
                        LocalDate d = LocalDate.fromDateFields(dateStart).withFieldAdded(DurationFieldType.days(), i);
                        Date date = d.toDate();
                        List<BatchXmit> lstBatchXmit = beanReportReader.getBatchXmit(batch, date);
                        if (lstBatchXmit != null) {
                            items.addAll(lstBatchXmit);
                        }
                    }
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

    public List<BatchXmit> getItems() {
        return items;
    }

    public void setItems(List<BatchXmit> items) {
        this.items = items;
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

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
