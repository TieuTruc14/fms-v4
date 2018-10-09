package com.eposi.fms.web.admin.manage.feedback;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.FeedBack;
import com.eposi.fms.persitence.FmsDao;


public class FeedBackDeleteAction extends AbstractAction {
    private static final long serialVersionUID = -1993047817762423771L;
    private long id;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        if(id>0) {
            FeedBack feedBack = beanFmsDao.getFeedBack(id);
            if(feedBack!=null){
                try {
                    beanFmsDao.deleteFeedBack(feedBack);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return SUCCESS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
