package com.eposi.fms.persitence;

import com.eposi.common.persitence.DaoUtil;
import com.eposi.common.persitence.HibernateUtil;
import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.*;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FmsDao extends AbstractBean {
    private static final long serialVersionUID = -7155765819672985946L;
    private static Logger log = Logger.getLogger(FmsDao.class.getName());
    private HibernateUtil beanHibernateUtil;
    private DaoUtil beanDaoUtil;

    public void init() {
        beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        beanHibernateUtil = (HibernateUtil) getBean("beanHibernateUtil");
    }

    public Company getCompany(String id) {
        return (Company) beanDaoUtil.get(Company.class, id);
    }

    public Company editCompany(Company item) throws Exception {
        Company savedObject = (Company) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public void updateAggregation(Company company, Date date){
        if(date==null)return;
        if(company==null)return;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day =calendar.get(Calendar.DAY_OF_MONTH);
        int week =calendar.get(Calendar.WEEK_OF_YEAR);
        int month =calendar.get(Calendar.MONTH);
        int year =calendar.get(Calendar.YEAR);

        try {
            VehicleAggregation vehicleAggregation = getVehicleAggregationByDay(company.getId(), day, month, year);
            if (vehicleAggregation == null) {
                vehicleAggregation = new VehicleAggregation();
                vehicleAggregation.setCompanyId(company.getId());
                vehicleAggregation.setInDay(day);
                vehicleAggregation.setInWeek(week);
                vehicleAggregation.setInMonth(month);
                vehicleAggregation.setInYear(year);
                vehicleAggregation.setVehicleCount(1);
            } else {
                vehicleAggregation.setVehicleCount(vehicleAggregation.getVehicleCount() + 1);
            }
            editVehicleAggregation(vehicleAggregation);
        }catch (Exception e){

        }
    }

    public VehicleAggregation getVehicleAggregationByDay(String companyId, int day, int month, int year){

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("companyId", companyId));
        criterions.add(Restrictions.eq("inDay", day));
        criterions.add(Restrictions.eq("inMonth", month));
        criterions.add(Restrictions.eq("inYear", year));
        List<VehicleAggregation> lstVehicleAggregation = (List<VehicleAggregation>) beanDaoUtil.query(VehicleAggregation.class, criterions);

        if (lstVehicleAggregation != null) {
            if(lstVehicleAggregation.size()>0) {
                return lstVehicleAggregation.get(0);
            }
        }

        return  null;
    }

    public VehicleAggregation editVehicleAggregation(VehicleAggregation item) throws Exception {
        VehicleAggregation savedObject = (VehicleAggregation) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }
}
