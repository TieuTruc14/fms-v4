package com.eposi.fms.web.admin.manage.frontiers.district;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.District;
import com.eposi.fms.model.Province;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by TienManh on 7/20/2016.
 */
public class GetListJsonDistrictByProvinceAction extends AbstractAction {

    private static final long serialVersionUID = 2947019431924441787L;
    private List<District> districts;
    private String provinceId;
    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        try {
            if(!provinceId.isEmpty()) {
                Province province=beanFmsDao.getProvince(provinceId);
                if(province!=null){
                    districts=beanFmsDao.searchDistrictByProvince(province);
                }
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.manage.getDistrictByProvince.json" + e.getMessage());
        }

        return SUCCESS;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
