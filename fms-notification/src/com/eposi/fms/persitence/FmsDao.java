package com.eposi.fms.persitence;

import com.eposi.common.persitence.DaoUtil;
import com.eposi.common.persitence.HibernateUtil;
import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;


public class FmsDao extends AbstractBean {
    private static final long serialVersionUID = -7155765819672985946L;

    private HibernateUtil beanHibernateUtil;
    private DaoUtil beanDaoUtil;

    public void init() {
        beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        beanHibernateUtil = (HibernateUtil) getBean("beanHibernateUtil");
    }

    /**
     * Company
     */
    public Company getCompany(String id) {
        return (Company) beanDaoUtil.get(Company.class, id);
    }

    public List<Company> listCompany() {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (List<Company>) beanDaoUtil.list(Company.class.getName());
    }

    /******************************************************************/
    // VehicleDao
    public Vehicle getVehicle(String id) {
        return (Vehicle) beanDaoUtil.get(Vehicle.class, id);
    }

    public List<Vehicle> listVehicle() {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        return (List<Vehicle>) beanDaoUtil.list(Vehicle.class.getName());
    }

    //Contact
    public List<String> searchMailNotificationByCompany(String companyId) {
        Company company = getCompany(companyId);
        if(company!=null) {
            List<Criterion> criterions = new ArrayList<>();
            criterions.add(Restrictions.eq("company", company));
            criterions.add(Restrictions.eq("notify", true));
            List<Contact> lstContact = (List<Contact>) beanDaoUtil.query(Contact.class, criterions);
            if (lstContact != null) {
                if (lstContact.size() > 0) {
                    List<String> mails = new ArrayList<>();
                    for (Contact contact : lstContact) {
                        if (contact.getEmail() != null) {
                            mails.add(contact.getEmail());
                        }
                    }
                    return mails;
                }
            }
        }
        return null;
    }
}
