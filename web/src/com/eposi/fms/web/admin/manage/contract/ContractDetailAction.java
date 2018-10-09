package com.eposi.fms.web.admin.manage.contract;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Contract;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContractDetailAction extends AbstractAction {
    private static final long serialVersionUID = 6227468801177645669L;

    private String id;
    private Contract item;
    private String strDateStart;
    private String strDateEnd;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CONTRACT_VIEW)) return SUCCESS;
        try {
            if (StringUtils.isNotEmpty(id)){
                item = beanFmsDao.getContract(Long.parseLong(id));
                setStrDateStart(FormatUtil.formatDate(item.getDateStart(), "dd/MM/yyyy"));
                setStrDateEnd(FormatUtil.formatDate(item.getDateEnd(), "dd/MM/yyyy"));
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.contract.ContractDetailAction.details: " + e.getMessage());
        }

        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contract getItem() {
        return item;
    }

    public void setItem(Contract item) {
        this.item = item;
    }

    public String getStrDateStart() {
        return strDateStart;
    }

    public void setStrDateStart(String strDateStart) {
        this.strDateStart = strDateStart;
    }

    public String getStrDateEnd() {
        return strDateEnd;
    }

    public void setStrDateEnd(String strDateEnd) {
        this.strDateEnd = strDateEnd;
    }
}
