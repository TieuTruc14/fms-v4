package com.eposi.fms.v2.persitence;

import com.eposi.common.util.AbstractBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by TienManh on 4/10/2016.
 */
public class HibernateUtil extends AbstractBean {


    private static final long serialVersionUID = 6857488420415619041L;

    public HibernateUtil() {
    }

    public void closeSession(Session session) {
        if(session != null) {
            try {
                session.close();
            } catch (HibernateException var3) {
                var3.printStackTrace();
            }
        }

    }

    public Session getSession() {
        SessionFactory sessionFactory = (SessionFactory)this.getBean("sessionFactoryV2");
        return sessionFactory.openSession();
    }
}
