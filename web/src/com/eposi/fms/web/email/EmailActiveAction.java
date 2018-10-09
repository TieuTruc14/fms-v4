package com.eposi.fms.web.email;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;

/**
 * Created by TienManh on 8/5/2016.
 */
public class EmailActiveAction extends AbstractAction {
    private static final long serialVersionUID = -4609151423880589372L;
    private String id;
    private boolean check=false;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        try {
            if(id!=null && StringUtils.isNotEmpty(id)){
                Company item= beanFmsDao.getCompanyByCodeEmail(id);
                if(item!=null){
//                    item.setStatusEmail((byte)10);//da active
                    beanFmsDao.editCompany(item);
                    check=true;
                }else{return INPUT;}

            }else{
                return INPUT;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
