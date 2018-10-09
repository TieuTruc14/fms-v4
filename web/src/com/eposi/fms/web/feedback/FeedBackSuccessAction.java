package com.eposi.fms.web.feedback;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.FeedBack;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by TieuTruc on 5/21/2015.
 */
public class FeedBackSuccessAction extends AbstractAction {
    private String contentText;
    private FeedBack item= new FeedBack();

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user!=null){
                if(StringUtils.isNotEmpty(contentText)){
                    item.setContent(contentText);
                    item.setUsername(user.getUsername());
                    item.setDateCreated(new Date());
                    item.setCompany(user.getCompany());

                    beanFmsDao.addFeedBack(item);
                }
            }
        }catch (Exception e){
            System.out.println("Error Com.eposi.fms.web.feedback.sendFeedBack.execute():"+e.getMessage());
        }
        return SUCCESS;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public FeedBack getItem() {
        return item;
    }

    public void setItem(FeedBack item) {
        this.item = item;
    }
}
