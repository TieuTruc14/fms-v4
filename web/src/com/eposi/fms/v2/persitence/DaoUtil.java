package com.eposi.fms.v2.persitence;

import com.eposi.common.util.AbstractBean;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TienManh on 4/10/2016.
 */
public class DaoUtil extends AbstractBean {

    private static final long serialVersionUID = -2996624995552188233L;

    public DaoUtil() {
    }

    public Object saveOrUpdate(Object item) {
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil)this.getBean("beanHibernateUtilV2");
        Session session = beanHibernateUtilV2.getSession();

        try {
            session.saveOrUpdate(item);
            session.flush();
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }

        return item;
    }

    public List<?> list(String model) {
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil)this.getBean("beanHibernateUtilV2");
        List items = null;
        Session session = beanHibernateUtilV2.getSession();

        try {
            session.beginTransaction();
            items = session.createQuery("from " + model).list();
            session.getTransaction().commit();
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }

        return items;
    }

    public Object add(Object item) throws Exception {
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil)this.getBean("beanHibernateUtilV2");
        Session session = beanHibernateUtilV2.getSession();

        try {
            session.beginTransaction();
            session.save(item);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception var8) {
            ;
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }

        return item;
    }

    public Object edit(Object item) throws Exception {
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil)this.getBean("beanHibernateUtilV2");
        Session session = beanHibernateUtilV2.getSession();

        try {
            session.beginTransaction();
            session.update(item);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception var8) {
            ;
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }

        return item;
    }

    public Object delete(Object item) throws Exception {
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil)this.getBean("beanHibernateUtilV2");
        Session session = beanHibernateUtilV2.getSession();

        try {
            session.beginTransaction();
            session.delete(item);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception var8) {
            ;
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }

        return item;
    }

    public void deleteAll(String model) throws Exception {
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil)this.getBean("beanHibernateUtilV2");
        Session session = beanHibernateUtilV2.getSession();

        try {
            session.beginTransaction();
            String hql = "delete from " + model;
            Query query = session.createQuery(hql);
            query.executeUpdate();
            session.flush();
            session.getTransaction().commit();
        } catch (Exception var9) {
            ;
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }

    }

    public List<?> query(Class modelClass, List<Criterion> criterions) {
        List items = null;
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil)this.getBean("beanHibernateUtilV2");
        Session session = beanHibernateUtilV2.getSession();

        try {
            session.beginTransaction();
            Criteria e = session.createCriteria(modelClass);
            if(criterions != null) {
                for(int i = 0; i < criterions.size(); ++i) {
                    Criterion criterion = (Criterion)criterions.get(i);
                    e.add(criterion);
                }
            }

            items = e.list();
            session.getTransaction().commit();
        } catch (Exception var12) {
            var12.printStackTrace();
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }

        return items;
    }

    public Object get(Class cls, Serializable id) {
        Object obj = null;
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil)this.getBean("beanHibernateUtilV2");
        Session session = beanHibernateUtilV2.getSession();

        try {
            session.beginTransaction();
            obj = session.get(cls, id);
            session.getTransaction().commit();
        } catch (Exception var10) {
            System.out.println(var10.getMessage());
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }

        return obj;
    }

    public List<?> query(Class modelClass, List<Criterion> criterions, List<Order> orderes) {
        List items = null;
        HibernateUtil beanHibernateUtilV2 = (HibernateUtil) getBean("beanHibernateUtilV2");
        Session session = beanHibernateUtilV2.getSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(modelClass);
            if (criterions != null) {
                for (int i = 0; i < criterions.size(); i++) {
                    Criterion criterion = (Criterion) criterions.get(i);
                    criteria.add(criterion);
                }
            }
            if (orderes != null) {
                for (int i = 0; i < orderes.size(); i++) {
                    Order order = (Order) orderes.get(i);
                    criteria.addOrder(order);
                }
            }
            items = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtilV2.closeSession(session);
        }
        return items;
    }
}
