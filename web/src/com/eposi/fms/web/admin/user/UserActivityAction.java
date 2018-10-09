package com.eposi.fms.web.admin.user;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

public class UserActivityAction extends AbstractAction {
    private static final long serialVersionUID = -7730973460897872912L;

    private String start; // start date
    private String end; // end date
    private String username;
    private List<Activity> activitys;

	public String execute() {

        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(currentUser, PermissionDefine.ROLE_USER_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }

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
            dateEnd = FormatUtil.parseDate(end+"-23:59:59", "dd/MM/yyyy-HH:mm:ss");
        } catch(Exception e)  {
        }

        User user = beanFmsDao.getUser(username);
        if(user!=null) {
            try {
                if(user.getKonexyId()!=null) {
                    activitys = beanReportReader.searchActivity(user.getKonexyId(), dateStart, dateEnd);
                }
            } catch (Exception e) {
                System.out.println("com.eposi.fms.web.admin.User.UserActivityAction: " + e.getMessage());
            }
        }

        return SUCCESS;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
