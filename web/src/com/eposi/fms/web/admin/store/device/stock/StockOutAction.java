package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Created by TieuTruc on 5/6/2015.
 */
public class StockOutAction extends AbstractAction {
    private String companyId;
    private String start;
    private String end;
    private PagingResult page = new PagingResult();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_OUT_VIEW)){
                addActionError("action.error.permission");
                return SUCCESS;
            }
            if (user != null) {
                Company companyDes=null;
                if(companyId!=null) {
                   companyDes = beanFmsDao.getCompany(companyId);
                }
                Date dateStart = null;
                Date dateEnd = null;
                if (StringUtils.isNotEmpty(start)) {
                    dateStart = FormatUtil.parseDate(start, "dd/MM/yyyy");
                }
                if (StringUtils.isNotEmpty(end)) {
                    dateEnd = FormatUtil.parseDate(end+"-23:59:59", "dd/MM/yyyy-HH:mm:ss");
                }
                Company company= user.getCompany();

                if (page.getPageNumber() > 0) {
                    page.setPageNumber(page.getPageNumber() - 1);
                }
                beanFmsDao.pageStockOut(page, company, companyDes, dateStart, dateEnd);
            }

        } catch (Exception e){
            System.out.println("com.eposi.fms.web.addmin.device.stock.excute: "+e.getMessage());
        }
        return SUCCESS;
    }

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
}
