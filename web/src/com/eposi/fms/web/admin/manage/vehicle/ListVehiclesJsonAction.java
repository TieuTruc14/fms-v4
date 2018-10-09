package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class ListVehiclesJsonAction extends AbstractAction {
    private static final long serialVersionUID = -1012682897834504959L;

    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
    private User user;
    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null) {
            Company company = user.getCompany();
            if (company != null) {
                vehicles = beanFmsDao.searchVehicleByCompany(company);

                for (Vehicle vehicle : vehicles) {
                    vehicle.setId(vehicle.getId());
                }
            }
        }

        return SUCCESS;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
