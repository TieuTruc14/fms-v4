package com.eposi.fms.web.admin.manage.frontiers.province;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.PagingResult;
import com.eposi.fms.model.Province;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class ProvinceDetailAction extends AbstractAction {
    private static final long serialVersionUID = -8966289171883389764L;
    private PagingResult page = new PagingResult();

    private String id;
	private Province item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_PROVINCE_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        try {
            if (StringUtils.isNotEmpty(id)) {
                item = beanFmsDao.getProvince(id);
            }
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }
            beanFmsDao.pageCompany(page,null,null,null,item,null);

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.province.detail.execute: "+e.getMessage());
        }

		return SUCCESS;
	}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Province getItem() {
        return item;
    }

    public void setItem(Province item) {
        this.item = item;
    }

    public PagingResult getPage() {
        return page;
    }

}
