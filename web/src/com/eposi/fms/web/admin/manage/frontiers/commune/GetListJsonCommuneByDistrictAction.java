package com.eposi.fms.web.admin.manage.frontiers.commune;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Commune;
import com.eposi.fms.model.District;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by TienManh on 7/20/2016.
 */
public class GetListJsonCommuneByDistrictAction extends AbstractAction {

    private static final long serialVersionUID = 5992751048093764964L;
    private String districtId;
    private List<Commune> communes;
    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        try {
            if(!districtId.isEmpty()) {
                District district=beanFmsDao.getDistrict(districtId);
                if(district!=null){
                    communes=beanFmsDao.searchCommuneByDistrict(district);
                }
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.manage.getCommuneByDistrict.json" + e.getMessage());
        }

        return SUCCESS;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }
}
