package com.eposi.fms.web.admin.manage.feedback;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.FeedBack;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class FeedBackListingAction extends AbstractAction {
    private static final long serialVersionUID = -4306568519846106129L;

    private List<FeedBack> items = new ArrayList<FeedBack>();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null) {
            items = beanFmsDao.searchFeedBackByCompany(user.getCompany());
        }

        return SUCCESS;
    }

    public List<FeedBack> getItems() {
        return items;
    }
}
