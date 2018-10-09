package com.eposi.fms.web.admin.report;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.ReportReader;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

public class SystemActivityAction extends AbstractAction {
    private static final long serialVersionUID = -7730973460897872912L;

    private String start; // start date
    private String end; // end date
    private List<Activity> activitys;

	public String execute() {
        Date dateStart = null;
        Date dateEnd   = null;

        // date
        if (org.apache.commons.lang3.StringUtils.isEmpty(start)) {
            start = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }

        if (org.apache.commons.lang3.StringUtils.isEmpty(end)) {
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

        try {
            User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Company company = user.getCompany();
            ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
            if(company.getKonexyId()!=null) {
                activitys = beanReportReader.searchActivity(company.getKonexyId(), dateStart, dateEnd);
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.SystemActivityAction: "+ e.getMessage());
        }

        return SUCCESS;
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

    public List<Activity> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<Activity> activitys) {
        this.activitys = activitys;
    }
}
