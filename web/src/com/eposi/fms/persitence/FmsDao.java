package com.eposi.fms.persitence;

import com.eposi.common.persitence.DaoUtil;
import com.eposi.common.persitence.HibernateUtil;
import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;


public class FmsDao extends AbstractBean {
    private static final long serialVersionUID = -7155765819672985946L;

    private HibernateUtil beanHibernateUtil;
    private DaoUtil beanDaoUtil;

    public void init() {
        beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
        beanHibernateUtil = (HibernateUtil) getBean("beanHibernateUtil");
    }

    //*********************************************************************/
    //Permision
    public List<GroupMember> searchGroupMemberByUserName(String username) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("username", username));

        List<GroupMember> items = (List<GroupMember>) beanDaoUtil.query(GroupMember.class, criterions);
        return items;
    }

    public List<GroupPermission> searchGroupPermissionByGroupId(String groupId) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("groupid", groupId));

        List<GroupPermission> items = (List<GroupPermission>) beanDaoUtil.query(GroupPermission.class, criterions);
        return items;
    }

    public List<Permission> searchPermissionByListId(List<String> lstId) {
        if(lstId==null || lstId.size()==0) return new ArrayList<>();
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("id", lstId));

        List<Permission> items = (List<Permission>) beanDaoUtil.query(Permission.class, criterions);
        if(items==null){
            return new ArrayList<>();
        }
        return items;
    }

    public boolean isPermision(User user, String permissionId) {
        List<GrantedAuthority> lstAuthor= user.getGrantedAuths();
        if(lstAuthor!=null){
            for(GrantedAuthority item:lstAuthor){
                if(item.getAuthority().equals(permissionId)){
                    return true;
                }
            }
        }
        return false;
    }

    public GroupMember addGroupMember(GroupMember item) throws Exception {
        GroupMember savedObject = (GroupMember) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public List<?> saveOrUpdateGroupMembers(List<GroupMember> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (GroupMember item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
        return items;
    }

    public Group getGroup(String id) {
        return (Group) beanDaoUtil.get(Group.class, id);
    }

    public List<Group> listGroup() {
        return (List<Group>) beanDaoUtil.list(Group.class.getName());
    }

    public void pageGroup(PagingResult page) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Group.class);
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public Group addGroup(Group item) throws Exception {
        if (beanDaoUtil.get(Group.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Group.class.getName());
        }

        Group savedObject = (Group) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Group editGroup(Group item) throws Exception {
        Group savedObject = (Group) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public List<Group> getGroupByListId(List<String> lstId) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("id", lstId));

        List<Group> items = (List<Group>) beanDaoUtil.query(Group.class, criterions);
        if(items!=null) return items;
        return new ArrayList<>();
    }

    public List<GroupGrant> listGroupGrant() {
        return (List<GroupGrant>) beanDaoUtil.list(GroupGrant.class.getName());
    }


    public void deleteGroupMemberByUsername(String username) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("username", username));

        List<GroupMember> lstGroupMember = (List<GroupMember>) beanDaoUtil.query(GroupMember.class, criterions);
        if(lstGroupMember!=null){
            if(lstGroupMember.size()>0){
                deleteGroupMembers(lstGroupMember);
            }
        }
    }
    public List<?> deleteGroupMembers(List<GroupMember> items) {
        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (GroupMember item : items) {
                session.delete(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public List<?> deleteGroupPermissions(List<GroupPermission> items) {
        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (GroupPermission item : items) {
                session.delete(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public void deleteGroupPermissionByGroupId(String groupId)  {
        List<Object> params = new ArrayList<Object>();

        // remove old records from tbl_users_devices
        String deleteQuery = "DELETE " +
                "FROM `tbl_group_permission` " +
                "WHERE groupId = ?";

        params.add(groupId);

        executeSQL(deleteQuery, params);

    }
    public int executeSQL(String sqlQuery, List<Object> params){
        int result = -1;
        HibernateUtil beanHibernateUtil = (HibernateUtil) getBean("beanHibernateUtil");

        Session session = beanHibernateUtil.getSession();
        try{
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(sqlQuery);

            if(params != null && params.size() > 0){
                for(int i = 0; i < params.size(); i++){
                    query.setParameter(i, params.get(i));
                }
            }
            result = query.executeUpdate();
            session.flush();
            session.getTransaction().commit();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
        return result;
    }

    public List<?> saveOrUpdateGroupPermissions(List<GroupPermission> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (GroupPermission item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
        return items;
    }


    //*********************************************************************/
    //Report
    public Report getReport(String id) {
        return (Report) beanDaoUtil.get(Report.class, id);
    }

    public Report addReport(Report item) throws Exception {
        if (beanDaoUtil.get(Report.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Report.class.getName());
        }

        Report savedObject = (Report) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Report editReport(Report item) throws Exception {
        Report savedObject = (Report) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public Report deleteReport(Report item) throws Exception {
        return (Report) beanDaoUtil.delete(item);
    }

    /**
     * Organization
     */
    public List<Company> getAllOrganizationByParent(Company parent){

        if(parent!=null){
            List<Company> lstParent=new ArrayList<Company>();
            lstParent.add(parent);
            List<Company> lstChildren = searchOrganizationByParent(parent);
            if(lstChildren!=null && lstChildren.size()>0){
                int size=0;
                do{
                    size=0;
                    for(Company item: lstChildren){
                        lstParent.add(item);
                    }

                    lstChildren = searchOrganizationByListParent(lstChildren);
                    if(lstChildren!=null && lstChildren.size()>0){
                        size = lstChildren.size();
                    }
                }while (size>0);
            }
            return lstParent;
        }

        return  new ArrayList<Company>();
    }

    public List<Company> searchOrganizationChildrentByParent(Company parent){
        if(parent!=null){
            List<Company> lstParent= getAllOrganizationByParent(parent);
            if(lstParent.size()>0){
                List<Criterion> criterions = new ArrayList<Criterion>();
                criterions.add(Restrictions.in("parent", lstParent));
                criterions.add(Restrictions.eq("orgazation", true));
                List<Company> items = (List<Company>) beanDaoUtil.query(Company.class, criterions);
                return items;
            }
        }
        return  new ArrayList<>();
    }

    public List<Company> searchOrganizationByParentAndIdName(Company parent, String fillterId, String fillterName){
        if(parent!=null){
            List<Company> lstParent= getAllOrganizationByParent(parent);
            if(lstParent.size()>0){
                List<Criterion> criterions = new ArrayList<Criterion>();
                criterions.add(Restrictions.in("parent", lstParent));
                criterions.add(Restrictions.eq("orgazation", true));
                if(StringUtils.isNotEmpty(fillterId)){
                    criterions.add(Restrictions.ilike("id","%"+fillterId+"%"));
                }
                if(StringUtils.isNotEmpty(fillterName)){
                    criterions.add(Restrictions.ilike("name","%"+fillterName+"%"));
                }
                List<Company> items = (List<Company>) beanDaoUtil.query(Company.class, criterions);
                return items;
            }
        }
        return  new ArrayList<>();
    }

    private List<Company> searchOrganizationByListParent(List<Company> parents) {
        if(parents ==null){
            return new ArrayList<>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("parent", parents));
        criterions.add(Restrictions.eq("orgazation", true));

        List<Company> items = (List<Company>) beanDaoUtil.query(Company.class, criterions);
        return items;
    }

    public List<Company> searchOrganizationByParent(Company item) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("parent", item));
        criterions.add(Restrictions.eq("orgazation", true));

        return (List<Company>) beanDaoUtil.query(Company.class, criterions);
    }

    /**
     * Company
     */

    public List<Company> searchCompanyByParent(Company item) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("parent", item));

        return (List<Company>) beanDaoUtil.query(Company.class, criterions);
    }

    private List<Company> searchAllCompanyByListParent(List<Company> parents) {
        if(parents ==null){
            return new ArrayList<>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("parent", parents));

        List<Company> items = (List<Company>) beanDaoUtil.query(Company.class, criterions);

        return items;
    }

    public List<Company> searchCompanyIsNotOrgazationByParent(Company parent) {
        if(parent!=null){
            List<Company> lstParent = getAllOrganizationByParent(parent);
            lstParent.add(parent);
            return searchCompanyIsNotOrgazationByListParent(lstParent);
        }

        return new ArrayList<Company>();
    }

    private List<Company> searchCompanyIsNotOrgazationByListParent(List<Company> items) {
        if(items ==null){
            return new ArrayList<>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("parent", items));
        criterions.add(Restrictions.eq("orgazation", false));
        List<Company> lstCompany = (List<Company>) beanDaoUtil.query(Company.class, criterions);

        return lstCompany;
    }

    public List<Company> getAllCompanyByParent(Company parent){

        if(parent!=null){
            List<Company> items= getAllOrganizationByParent(parent);
            List<Criterion> criterions = new ArrayList<Criterion>();
            criterions.add(Restrictions.in("parent", items));
            return (List<Company>) beanDaoUtil.query(Company.class, criterions);
        }
        return null;
    }

    public List<Company> getAllCompanyRelationShipByParent(Company item){

        List<Company>  lstAllCompany =null;
        if(item!=null){
            List<Company> items= getAllOrganizationByParent(item);
            List<Criterion> criterions = new ArrayList<Criterion>();
            criterions.add(Restrictions.in("parent", items));
            lstAllCompany =  (List<Company>) beanDaoUtil.query(Company.class, criterions);
        }
        Company parent = item.getParent();
        while (parent!=null){
            lstAllCompany.add(parent);
            parent = parent.getParent();
        }

        return lstAllCompany;
    }

    public List<Company> getAllCompanyAndOrganizationByParent(Company item){

        List<Company>  lstAllCompany =null;
        if(item!=null){
            List<Company> items= getAllOrganizationByParent(item);
            List<Criterion> criterions = new ArrayList<Criterion>();
            criterions.add(Restrictions.in("parent", items));
            lstAllCompany =  (List<Company>) beanDaoUtil.query(Company.class, criterions);
            if(items!=null){
                lstAllCompany.addAll(items);
            }
        }

        return lstAllCompany;
    }

    /******************************************************************/
    // CompanyDao
    public Company getCompany(String id) {
        return (Company) beanDaoUtil.get(Company.class, id);
    }


    public Company editCompany(Company item) throws Exception {
        Company savedObject = (Company) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public Company addCompany(Company item) throws Exception {
        if (beanDaoUtil.get(Company.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Company.class.getName());
        }

        Company savedObject = (Company) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public List<Company> searchCompanyByProvince(Province province) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("province", province));

        return (List<Company>) beanDaoUtil.query(Company.class, criterions);
    }

    public List<Company> searchCompanyByPhone(String phone) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("phone", phone));

        return (List<Company>) beanDaoUtil.query(Company.class, criterions);
    }

    public List<Company> searchCompanyById(String idFilter ) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.ilike("id", "%" + idFilter + "%"));

        return (List<Company>) beanDaoUtil.query(Company.class, criterions);
    }

    public List<?> saveOrUpdateCompanies(List<Company> items) {
        if(items ==null){
            return new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (Company item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public void pageCompany(PagingResult page, String id_filter, String name_filter, String owner_filter, Province province, Company companyParent) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Company.class);

            if (StringUtils.isNotEmpty(id_filter)) {
                criteria.add(Restrictions.ilike("id", "%" + id_filter + "%"));
            }
            if (StringUtils.isNotEmpty(name_filter)) {
                criteria.add(Restrictions.ilike("name", "%" + name_filter + "%"));
            }
            if (StringUtils.isNotEmpty(owner_filter)) {
                long owner=Long.parseLong(owner_filter);
                criteria.add(Restrictions.eq("owner", owner ));
            }
            if (province != null) {
                criteria.add(Restrictions.eq("province", province));
            }

            if(companyParent!=null){
                List<Company> lstParent= getAllOrganizationByParent(companyParent);
                if(lstParent!=null){
                    criteria.add(Restrictions.in("parent", lstParent));
                    criteria.add(Restrictions.eq("orgazation", false));
                }
            }

            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                List list = criteria.list();
                page.setItems(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public void pageCompanyAll(PagingResult page, String id_filter, String name_filter, String owner_filter, Province province, Company companyParent) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Company.class);

            if (StringUtils.isNotEmpty(id_filter)) {
                criteria.add(Restrictions.ilike("id", "%" + id_filter + "%"));
            }
            if (StringUtils.isNotEmpty(name_filter)) {
                criteria.add(Restrictions.ilike("name", "%" + name_filter + "%"));
            }
            if (StringUtils.isNotEmpty(owner_filter)) {
                long owner=Long.parseLong(owner_filter);
                criteria.add(Restrictions.eq("owner", owner ));
            }
            if (province != null) {
                criteria.add(Restrictions.eq("province", province));
            }

            if(companyParent!=null){
                List<Company> lstParent= getAllOrganizationByParent(companyParent);
                if(lstParent!=null){
                    criteria.add(Restrictions.in("parent", lstParent));
                }
            }

            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                List list = criteria.list();
                page.setItems(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public String randomCodeEmailCompany(){

        List<Character> symbols= new ArrayList<>();
        for (char ch = '0'; ch <= '9'; ++ch)
            symbols.add(ch);
        for (char ch = 'A'; ch <= 'Z'; ++ch)
            symbols.add(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            symbols.add(ch);

        Random random = new Random();
        String key="";
        int count=0;
        int lenghtKey=showRandomInteger(10,16);//random lenght key from 10 to 16 char
        do{
            count++;
            key="";
            for (int idx = 0; idx < lenghtKey; ++idx)
                key += symbols.get(random.nextInt(symbols.size()));
        }while (getCompanyByCodeEmail(key)!=null && count<10);
        if(count>9){
            return "";
        }

        return key;
    }

    private int showRandomInteger(int Start, int End){
        if (Start > End) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        Random random=new Random();
        //get the range, casting to long to avoid overflow problems
        long range = (long)End - (long)Start + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * random.nextDouble());
        int randomNumber =  (int)(fraction + Start);
        return randomNumber;
    }

    public Company getCompanyByCodeEmail(String key){
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("codeEmail", key));

        List<Company> companies=(List<Company>) beanDaoUtil.query(Company.class, criterions);
        if(companies!=null && companies.size()>0){
            return companies.get(0);
        }
        return null;
    }

    public List<Company> getCompanyInListId(Set<String> ids) {
        if(ids ==null){
            return new ArrayList<>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("id", ids));

        List<Company> lstCompanies = (List<Company>) beanDaoUtil.query(Company.class, criterions);

        return lstCompanies;
    }

    public Company deleteCompany(Company item) throws Exception {
        return (Company) beanDaoUtil.delete(item);
    }

    public Company getCompanyByOwner(Long owner) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("owner", owner));

        List<Company> items = (List<Company>) beanDaoUtil.query(Company.class, criterions);
        if(items!=null && items.size()>0) return items.get(0);

        return null;
    }

    public List<Company>  getCompanyByOwners(List<Long> owners) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("owner", owners));

        List<Company> items = (List<Company>) beanDaoUtil.query(Company.class, criterions);

        return items;
    }


    public String GeneratorCompanyId(Company company){

        //Get Prefix required generator  companyId
        String prefix = findPrefixCompanyCode(company);

        //Get Company have max suffix number
        Company lastCompany = getLastCompanyByPrefix(prefix);

        //Parser suffix to integer
        Integer suffix =0;
        if(lastCompany!=null) {
            suffix = getSuffixCompanyIdAndParserToInt(lastCompany.getId(), prefix);
        }

        //GeneratorCompanyId
        suffix+=1;
        String companyId="0000000000";
        int count=0;
        while (count<5){
            companyId = companyId.substring(0, (10 - suffix.toString().length()));
            companyId = companyId.concat(suffix.toString());
            companyId = prefix+companyId;
            Company companyCheck = getCompany(companyId);
            if(companyCheck==null){
                return companyId;
            }
            count++;
            suffix++;
        }
        return null;
    }

    private String findPrefixCompanyCode(Company company){
        Company parent = getCompany(company.getParent().getId());
        while (true){
            if(parent==null) return null;
            if(parent.getPrefix()!=null){
                if(!parent.getPrefix().isEmpty()){
                    return parent.getPrefix();
                }
            }
            parent = getCompany(parent.getParent().getId());
        }
    }

    private int getSuffixCompanyIdAndParserToInt(String companyId, String prefix){
        if(prefix==null) return 0;
        if(prefix.isEmpty()) return 0;

        String suffix= companyId.substring(prefix.length());
        if(suffix.length()>0){
            return Integer.parseInt(suffix);
        }

        return 0;
    }

    private Company getLastCompanyByPrefix(String prefix){
        List<Company> lstCompany = searchCompanyByPrefix(prefix);
        if(lstCompany!=null){
            if(lstCompany.size()>0){
                return lstCompany.get(lstCompany.size()-1);
            }
        }

        return null;
    }

    private List<Company> searchCompanyByPrefix(String prefix) {
        List<Company> items=null;
        Session session = beanHibernateUtil.getSession();

        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Company.class);
            criteria.add(Restrictions.ilike("id", prefix+"%"));
            criteria.addOrder(Order.asc("id"));
            items = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public boolean isParent(Company child, Company parent){
        int count=0;
        if(child==null || parent==null) return false;
        while (child!=null){
            if(child.getId().equals(parent.getId())) return true;
            child = child.getParent();
        }

        return false;
    }

    /******************************************************************/
    // DriverDao

    public List<Driver> listDriver() {
        return (List<Driver>) beanDaoUtil.list(Driver.class.getName());
    }

    public List<Driver> searchDriverInCompanies(List<Company> companies) {
        if (companies == null) {
            return new ArrayList<>();
        }
        if(companies.size()==0){
            return  new ArrayList<>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("company", companies));

        List<Driver> lstVehicles = (List<Driver>) beanDaoUtil.query(Driver.class, criterions);

        if (lstVehicles != null) {
            return lstVehicles;
        }

        return new ArrayList<>();
    }

    public void pageDriver(PagingResult page, String keyword, Company company) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Driver.class);
            if (StringUtils.isNotEmpty(keyword)) {
                criteria.add(Restrictions.disjunction().add(Restrictions.like("id", "%"+keyword+"%")).add(Restrictions.like("name", "%"+keyword+"%")));
            }

            if(company!=null){
                List<Company> lstParent= getAllOrganizationByParent(company);
                List<Company> lstCompany=searchAllCompanyByListParent(lstParent);
                lstCompany.add(company);
                criteria.add(Restrictions.in("company",lstCompany));
            }
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    // edit
    public Driver editDriver(Driver item) throws Exception {
        Driver savedObject = (Driver) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Driver addDriver(Driver item) throws Exception {
        // check duplicate
        if (beanDaoUtil.get(Driver.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Driver.class.getName());
        }

        Driver savedObject = (Driver) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Driver getDriver(String id) {
        Driver driver = new Driver();
        driver = (Driver) beanDaoUtil.get(Driver.class, id);
        return driver;
    }

    public int searchDriverMaxAutogenKey() {
        String sqlQuery = "SELECT  max(autogen_key) " +
                "FROM    tbl_driver ";

        List<Integer> lstDrivers = (List<Integer>) querySQL(sqlQuery, null);
        if (lstDrivers != null && lstDrivers.size()>0) {
            return lstDrivers.get(0);
        }

        return 0;
    }
    public List<?> querySQL(String sqlQuery, List<Object> params){
        List<?> items = null;
        HibernateUtil beanHibernateUtil = (HibernateUtil) getBean("beanHibernateUtil");

        Session session = beanHibernateUtil.getSession();
        try{
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(sqlQuery);
            if(params != null && params.size() > 0){
                for(int i = 0; i < params.size(); i++){
                    query.setParameter(i, params.get(i));
                }
            }
            items = query.list();
            session.flush();
            session.getTransaction().commit();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
        return items;
    }

    public Driver autoGenDriverKey(){
        Driver item=new Driver();
        Integer maxAutoKey=searchDriverMaxAutogenKey();
        maxAutoKey=maxAutoKey+1;

        String driverKey="DR000000";
        int count=0;
        while (count<10){
            driverKey= driverKey.substring(0, (8 - maxAutoKey.toString().length()));
            driverKey = driverKey.concat(maxAutoKey.toString());
            Driver checkDriver=getDriver(driverKey);
            if(checkDriver==null){
                item.setAutogen_key(maxAutoKey);
                item.setId(driverKey);
               return item;
            }
            maxAutoKey++;
            count++;
        }
        return null;
    }
    public Driver deleteDriver(Driver item) throws Exception {
        return (Driver) beanDaoUtil.delete(item);
    }

    public List<Driver> searchDriverByCompany(Company company) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        List<Driver> drivers =  (List<Driver>) beanDaoUtil.query(Driver.class, criterions);
        return  drivers;
    }

    public List<Driver> searchDriverByCompanyId(String companyId) {
        if(StringUtils.isNotEmpty(companyId)){
            Company company = getCompany(companyId);
            List<Criterion> criterions = new ArrayList<Criterion>();
            criterions.add(Restrictions.eq("company", company));
            return (List<Driver>) beanDaoUtil.query(Driver.class, criterions);
        }

        return new ArrayList<Driver>();
    }

    public List<Driver> searchDriverByDefaultVehicle(String vehicleId) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("defaultVehicle", vehicleId));

        return (List<Driver>) beanDaoUtil.query(Driver.class, criterions);
    }

    public List<?> saveOrUpdateDrivers(List<Driver> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (Driver item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    /****************************Contact**************************************/
    public Contact editContact(Contact item) throws Exception {
        Contact savedObject = (Contact) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Contact addContact(Contact item) throws Exception {
        // check duplicate
        if (beanDaoUtil.get(Contact.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Contact.class.getName());
        }

        Contact savedObject = (Contact) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Contact getContact(int id) {
        return (Contact) beanDaoUtil.get(Contact.class, id);
    }

    public Contact deleteContact(Contact item) throws Exception {
        return (Contact) beanDaoUtil.delete(item);
    }

    public List<Contact> searchContactByCompany(Company company) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));

        return (List<Contact>) beanDaoUtil.query(Contact.class, criterions);
    }

    /******************************************************************/
    // ProvinceDao

    public Province getProvince(String id) {
        return (Province) beanDaoUtil.get(Province.class, id);
    }

    public Province saveProvince(Province item) {
        return (Province) beanDaoUtil.saveOrUpdate(item);
    }

    public List<Province> listProvince() {
        List<Province> items=null;
        Session session = beanHibernateUtil.getSession();

        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Province.class);
            criteria.addOrder(Order.asc("name"));
            items = criteria.list();
            session.getTransaction().commit();
            /*return criteria.list();*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }
/*****************************************ADDRESS******************************************************************/
    //district --commune
    public List<?> saveOrUpdateDistricts(List<District> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (District item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public List<?> saveOrUpdateCommunes(List<Commune> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (Commune item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public District getDistrict(String id) {
        return (District) beanDaoUtil.get(District.class, id);
    }

    public Commune getCommune(String id) {
        return (Commune) beanDaoUtil.get(Commune.class, id);
    }

    public List<District> listDistrict() {
        return (List<District>) beanDaoUtil.list(District.class.getName());
    }

    public List<Commune> listCommune() {
        return (List<Commune>) beanDaoUtil.list(Commune.class.getName());
    }

    public List<District> searchDistrictByProvince(Province province) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("province", province));

        return (List<District>) beanDaoUtil.query(District.class, criterions);
    }

    public List<Commune> searchCommuneByDistrict(District district) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("district", district));

        return (List<Commune>) beanDaoUtil.query(Commune.class, criterions);
    }

    public Address addAddress(Address item) throws Exception {
        // check duplicate
        if (beanDaoUtil.get(Address.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Address.class.getName());
        }

        Address savedObject = (Address) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Address getAddress(int id) {
        Address address = new Address();
        address = (Address) beanDaoUtil.get(Address.class, id);
        return address;
    }

    public Address saveAddress(Address item) {
        return (Address) beanDaoUtil.saveOrUpdate(item);
    }


    /******************************************************************/
    // VehicleDao

    public List<Vehicle> listVehicle() {
        return (List<Vehicle>) beanDaoUtil.list(Vehicle.class.getName());
    }

    public Vehicle addVehicle(Vehicle item) throws Exception {
        // check duplicate
        if (beanDaoUtil.get(Vehicle.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Vehicle.class.getName());
        }

        Vehicle savedObject = (Vehicle) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public Vehicle deleteVehicle(Vehicle item) throws Exception {
        return (Vehicle) beanDaoUtil.delete(item);
    }

    // edit
    public Vehicle editVehicle(Vehicle item) throws Exception {
        Vehicle savedObject = (Vehicle) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public List<?> saveOrUpdateVehicles(List<Vehicle> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (Vehicle item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public void pageVehicle(PagingResult page, String id, int type, List<Company> companyList) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Vehicle.class);
            if (StringUtils.isNotEmpty(id)) {
                criteria.add(Restrictions.ilike("id", "%" + id + "%"));
            }
            if (type > 0) {
                criteria.add(Restrictions.eq("type", type));
            }
            if (companyList != null) {
                criteria.add(Restrictions.in("company", companyList));
            }
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);

                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public void pageVehicle(PagingResult page, Company company) {
        Session session = beanHibernateUtil.getSession();
        try {
            if (company != null) {
                Criteria criteria = session.createCriteria(Vehicle.class);
                criteria.add(Restrictions.eq("disable", false));
                criteria.add(Restrictions.eq("company", company));
                // record count
                int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                if (rowCount > 0) {
                    page.setRowCount(rowCount);
                    criteria.setProjection(null);
                    criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                    criteria.setMaxResults(page.getNumberPerPage());
                    page.setItems(criteria.list());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public void pageVehicle(PagingResult page, String vehicleId, Company companyParent) {
        Session session = beanHibernateUtil.getSession();
        try {
                Criteria criteria = session.createCriteria(Vehicle.class);
                if (StringUtils.isNotEmpty(vehicleId)) {
                    criteria.add(Restrictions.eq("id", vehicleId));
                }

                if(companyParent!=null){
                    List<Company> lstParent= getAllOrganizationByParent(companyParent);
                    List<Company> lstCompany=searchAllCompanyByListParent(lstParent);
                    lstCompany.add(companyParent);
                    criteria.add(Restrictions.in("company",lstCompany));
                }

                // record count
                int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                if (rowCount > 0) {
                    page.setRowCount(rowCount);
                    criteria.setProjection(null);
                    criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                    criteria.setMaxResults(page.getNumberPerPage());
                    page.setItems(criteria.list());
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public Vehicle getVehicle(String id) {
        return (Vehicle) beanDaoUtil.get(Vehicle.class, id);
    }

    public Vehicle getVehicleAble(String vehiceId) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("disable", false));
        criterions.add(Restrictions.eq("id", vehiceId));
        List<Vehicle> lstVehicles = (List<Vehicle>) beanDaoUtil.query(Vehicle.class, criterions);
        if(lstVehicles!=null){
            if(lstVehicles.size()>0){
                return  lstVehicles.get(0);
            }
        }

        return null;
    }

    public Vehicle getVehicle(String vehiceId, String companyId) {
        Company company = this.getCompany(companyId);
        if (company == null) {
            return null;
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("id", vehiceId));
        List<Vehicle> lstVehicles = (List<Vehicle>) beanDaoUtil.query(Vehicle.class, criterions);
        if(lstVehicles!=null){
            if(lstVehicles.size()>0){
                return  lstVehicles.get(0);
            }
        }

        return null;
    }

    public void changevehicleCompany(Vehicle item, Company company) throws Exception{
        changeDeviceCompany(item, company);
        cleanAllDefaultDriverOfDriver(item);
        changeContractCompany(item, company);
        item.setCompany(company);
        editVehicle(item);

    }

    private void changeDeviceCompany(Vehicle vehicle, Company company){
        List<Device> lstDevice=searchAllDeviceByVehicle(vehicle);
        for(int i=0;i<lstDevice.size();i++){
            lstDevice.get(i).setCompany(company);
        }
        saveOrUpdateDevices(lstDevice);
    }

    private void cleanAllDefaultDriverOfDriver(Vehicle item){
//        List<Driver> lstDriver = searchDriverByDefaultVehicle(item.getId());
//        if(lstDriver!=null && lstDriver.size()>0){
//            saveOrUpdateDrivers(lstDriver);
//        }
    }

    private void changeContractCompany(Vehicle vehicle, Company company){
        List<Contract> lstContract = searchContractByVehicle(vehicle);
        if(lstContract!=null && lstContract.size()>0){
            //Copy Contract to new Vehicle
            for(int i=0;i<lstContract.size();i++){
                lstContract.get(i).setCompany(company);
            }
            saveOrUpdateContracts(lstContract);
        }
    }

    public void editChangeVehicle(Vehicle item,Vehicle oldVehicle, String newVehicleId) throws Exception {
        item.setId(newVehicleId);
        addVehicle(item);//add vehicle mới
        changeNewVehicleForDevice(oldVehicle, item);//sửa all TB
        cloneAllDefaultDriverOfDriver(oldVehicle,newVehicleId);//sửa driverdefault
        editAllContractOfVehicle(oldVehicle, item);//chuyển hợp đồng

        deleteVehicle(oldVehicle);//xóa biển số cũ
        //Edit report
        Report report = getReport(oldVehicle.getId());
        if(report!=null){
            deleteReport(report);
            report.setId(newVehicleId);
            addReport(report);
        }
    }

    private void changeNewVehicleForDevice(Vehicle vehicle, Vehicle newVehicle){
        List<Device> lstDevice = searchAllDeviceByVehicle(vehicle);
        for(int i=0;i<lstDevice.size();i++){
            lstDevice.get(i).setVehicle(newVehicle);
        }
        saveOrUpdateDevices(lstDevice);
    }

    private void cloneAllDefaultDriverOfDriver(Vehicle item,String newVehicleId){
//        List<Driver> lstDriver = searchDriverByDefaultVehicle(item.getId());
//        if(lstDriver!=null && lstDriver.size()>0){
//            for(Driver dr:lstDriver){
//                dr.setDefaultVehicle(newVehicleId);
//            }
//            saveOrUpdateDrivers(lstDriver);
//        }
    }

    private void editAllContractOfVehicle(Vehicle vehicle,Vehicle newVehicle){
        List<Contract> lstContract = searchContractByVehicle(vehicle);
        if(lstContract!=null && lstContract.size()>0){
            //Copy Contract to new Vehicle
            for(int i=0;i<lstContract.size();i++){
                lstContract.get(i).setVehicle(newVehicle);
            }
            saveOrUpdateContracts(lstContract);
        }
    }


    public List<Vehicle> searchAllVehicleManagerByCompany(Company company) {
        if (company == null) {
            return new ArrayList<Vehicle>();
        }
        List<Company> companyList = searchCompanyIsNotOrgazationByParent(company);

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("company", companyList));
        criterions.add(Restrictions.eq("disable", false));

        List<Vehicle> lstVehicles = (List<Vehicle>) beanDaoUtil.query(Vehicle.class, criterions);

        if (lstVehicles != null) {
            return lstVehicles;
        }

        return new ArrayList<Vehicle>();
    }

    public List<Vehicle> searchVehicleInCompanies(List<Company> companyList) {
        if (companyList == null) {
            return new ArrayList<Vehicle>();
        }
        if(companyList.size()==0){
            return new ArrayList<Vehicle>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("company", companyList));

        List<Vehicle> lstVehicles = (List<Vehicle>) beanDaoUtil.query(Vehicle.class, criterions);

        if (lstVehicles != null) {
            return lstVehicles;
        }

        return new ArrayList<Vehicle>();
    }

    public List<Vehicle> searchVehicleByCompanyId(String companyId) {
        Company company = this.getCompany(companyId);
        return searchVehicleByCompany(company);
    }

    public List<Vehicle> searchVehicleByCompany(Company company) {
        if (company == null) {
            return new ArrayList<Vehicle>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));

        List<Vehicle> lstVehicles = (List<Vehicle>) beanDaoUtil.query(Vehicle.class, criterions);

        if (lstVehicles != null) {
            return lstVehicles;
        }

        return new ArrayList<Vehicle>();
    }

    public List<Vehicle> getVehiclesInList(Collection<String> ids) {
        if (ids == null) {
            return new ArrayList<Vehicle>();
        }
        if (ids.size() <= 0) {
            return new ArrayList<Vehicle>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("id", ids));

        List<Vehicle> lstVehicles = (List<Vehicle>) beanDaoUtil.query(Vehicle.class, criterions);

        return lstVehicles;
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


    public int getVehicleCountByDay(String companyId, int day, int month, int year){

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("companyId", companyId));
        criterions.add(Restrictions.eq("inDay", day));
        criterions.add(Restrictions.eq("inMonth", month));
        criterions.add(Restrictions.eq("inYear", year));
        List<VehicleAggregation> lstVehicleAggregation = (List<VehicleAggregation>) beanDaoUtil.query(VehicleAggregation.class, criterions);

        if (lstVehicleAggregation != null) {
            if(lstVehicleAggregation.size()>0) {
                return lstVehicleAggregation.get(0).getVehicleCount();
            }
        }

        return  0;
    }

    public int getVehicleCountByWeek(String companyId, int week,  int year){
        int vehicleCount = 0;
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("companyId", companyId));
        criterions.add(Restrictions.eq("inWeek", week));
        criterions.add(Restrictions.eq("inYear", year));
        List<VehicleAggregation> lstVehicleAggregation = (List<VehicleAggregation>) beanDaoUtil.query(VehicleAggregation.class, criterions);

        if (lstVehicleAggregation != null) {
            if(lstVehicleAggregation.size()>0) {
                for(VehicleAggregation item:lstVehicleAggregation){
                    vehicleCount+=item.getVehicleCount();
                }
            }
        }

        return  vehicleCount;
    }

    public int getVehicleCountByMonth(String companyId, int month, int year){
        int vehicleCount = 0;
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("companyId", companyId));
        criterions.add(Restrictions.eq("inMonth", month));
        criterions.add(Restrictions.eq("inYear", year));
        List<VehicleAggregation> lstVehicleAggregation = (List<VehicleAggregation>) beanDaoUtil.query(VehicleAggregation.class, criterions);

        if (lstVehicleAggregation != null) {
            if(lstVehicleAggregation.size()>0) {
                for(VehicleAggregation item:lstVehicleAggregation){
                    vehicleCount+=item.getVehicleCount();
                }
            }
        }

        return  vehicleCount;
    }

    public List<VehicleAggregation> getVehicleByMonth(String companyId, int month, int year){
        int vehicleCount = 0;
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("companyId", companyId));
        criterions.add(Restrictions.eq("inMonth", month));
        criterions.add(Restrictions.eq("inYear", year));
        List<VehicleAggregation> lstVehicleAggregation = (List<VehicleAggregation>) beanDaoUtil.query(VehicleAggregation.class, criterions);

        if (lstVehicleAggregation != null) {
            if(lstVehicleAggregation.size()>0) {
                return lstVehicleAggregation;
            }
        }

        return  null;
    }

    public int getVehicleCountByYear(String companyId, int year){
        int vehicleCount = 0;
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("companyId", companyId));
        criterions.add(Restrictions.eq("inYear", year));
        List<VehicleAggregation> lstVehicleAggregation = (List<VehicleAggregation>) beanDaoUtil.query(VehicleAggregation.class, criterions);

        if (lstVehicleAggregation != null) {
            if(lstVehicleAggregation.size()>0) {
                for(VehicleAggregation item:lstVehicleAggregation){
                    vehicleCount+=item.getVehicleCount();
                }
            }
        }

        return  vehicleCount;
    }

    /******************************************************************/
    public List<Device> searchDeviceCompany(Company company) {
        if (company == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("disable", false));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }

        return new ArrayList<Device>();
    }

    public List<Device> searchDeviceCompany(Company company, int unit) {
        if (company == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("unit", unit));
        criterions.add(Restrictions.eq("disable", false));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }

        return new ArrayList<Device>();
    }

    public List<Device> searchDeviceCompanyToAddStock(Company company, int unit, boolean stock) {
        if (company == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("unit", unit));
        criterions.add(Restrictions.eq("stock", stock));
        criterions.add(Restrictions.eq("disable", false));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }

        return new ArrayList<Device>();
    }

    public List<Device> searchDeviceCompanyToRecover(Company company, int unit, boolean stock) {
        if (company == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("unit", unit));
        criterions.add(Restrictions.eq("stock", stock));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }

        return new ArrayList<Device>();
    }

    public List<Device> searchDeviceCompanyToAddVehicle(Company company, int unit) {
        if (company == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("unit", unit));
//        criterions.add(Restrictions.eq("disable", false));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }

        return new ArrayList<Device>();
    }

    public List<Device> searchDeviceByVehicle(Vehicle vehicle, int unit) {
        if (vehicle == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("vehicle", vehicle));
        criterions.add(Restrictions.eq("unit", unit));
//        criterions.add(Restrictions.eq("disable", false));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }
        return new ArrayList<Device>();
    }

    public List<Device> searchAllDeviceByVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("vehicle", vehicle));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }
        return new ArrayList<Device>();
    }

    public List<Device> searchDeviceByBatch(Batch batch) {
        if (batch == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("batch", batch));
        criterions.add(Restrictions.eq("disable", false));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }

        return new ArrayList<Device>();
    }

    public List<Device> searchDevicesByListId(List<String> lstId) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("id", lstId));
        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);
        if(lstDevices!=null && lstDevices.size()>0){
            return  lstDevices;
        }
        return null;
    }

    public void pageDeviceByBatch(PagingResult page, Batch batch) {
        Session session = beanHibernateUtil.getSession();
        try {
            if(batch!=null) {
                Criteria criteria = session.createCriteria(Device.class);
                criteria.add(Restrictions.eq("batch", batch));
                criteria.add(Restrictions.eq("disable", false));
                criteria.addOrder(Order.desc("dateCreated"));
                // record count
                int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                if (rowCount > 0) {
                    page.setRowCount(rowCount);
                    criteria.setProjection(null);
                    criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                    criteria.setMaxResults(page.getNumberPerPage());
                    page.setItems(criteria.list());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public void pageDeviceByUnit(PagingResult page, Company company,int unit) {
        Session session = beanHibernateUtil.getSession();
        try {
            if(company!=null) {
                Criteria criteria = session.createCriteria(Device.class);
                criteria.add(Restrictions.eq("company", company));
                criteria.add(Restrictions.eq("unit", unit));
                criteria.add(Restrictions.eq("disable", false));
                // record count
                int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                if (rowCount > 0) {
                    page.setRowCount(rowCount);

                    criteria.setProjection(null);

                    criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                    criteria.setMaxResults(page.getNumberPerPage());
                    page.setItems(criteria.list());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }


    public List<Device> searchDeviceByVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return new ArrayList<Device>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("vehicle", vehicle));
        criterions.add(Restrictions.eq("disable", false));

        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);

        if (lstDevices != null) {
            return lstDevices;
        }

        return new ArrayList<Device>();
    }

    public void pageDevice(PagingResult page, String id_filter, int unit, Company company,String productTypeId) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Device.class);
            criteria.add(Restrictions.eq("disable", false));
            if (StringUtils.isNotEmpty(id_filter)) {
                criteria.add(Restrictions.ilike("id", "%" + id_filter + "%"));
            }
            if (StringUtils.isNotEmpty(productTypeId)) {
                    List<Model> lstModels=searchModelByProductType(productTypeId);
                    List<Batch> lstBatch=searchBatchByLstModel(lstModels);
                    criteria.add(Restrictions.in("batch", lstBatch));
            }
            if (unit==3) {
                criteria.add(Restrictions.eq("company", company));
                criteria.add(Restrictions.eq("unit", 0));
            }else{

                if(unit==0 || unit==1){
                    criteria.add(Restrictions.eq("unit", unit));
                }

                if (company != null) {
                    List<Company> lstCompany= getAllOrganizationByParent(company);
                    if(lstCompany!=null && lstCompany.size()>0){
                        List<Company> lstCompanyAll= searchAllCompanyByListParent(lstCompany);
                        lstCompanyAll.add(company);
                        criteria.add(Restrictions.in("company",lstCompanyAll));
                    }
                }
            }

            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);

                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public void pageDevice(PagingResult page, String id_filter, Company company) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Device.class);
            criteria.add(Restrictions.eq("disable", false));
            if (StringUtils.isNotEmpty(id_filter)) {
                criteria.add(Restrictions.ilike("id", "%" + id_filter + "%"));
            }

            criteria.add(Restrictions.eq("company", company));
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);

                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public void pageDeviceInventory(PagingResult page, String id_filter, Company company) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Device.class);
            criteria.add(Restrictions.eq("unit", 0));
            if (StringUtils.isNotEmpty(id_filter)) {
                criteria.add(Restrictions.ilike("id", "%" + id_filter + "%"));
            }

            criteria.add(Restrictions.eq("company", company));
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);

                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public Device getDevice(String id) {
        return (Device) beanDaoUtil.get(Device.class, id);
    }

    public List<Device> getDeviceByCompany(Company company) {
        if (company == null) {
            return null;
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("disable", false));
        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);
        if(lstDevices!=null){
                return  lstDevices;
        }

        return null;
    }
    public Device getDeviceByProductKey(String productKey) {
        if (productKey.isEmpty()) {
            return null;
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("product_key", productKey));
        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);
        if(lstDevices!=null && lstDevices.size()>0){
            return  lstDevices.get(0);
        }
        return null;
    }

    public List<Device> getDeviceByCompany(Company company, int unit) {
        if (company == null) {
            return null;
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("unit", unit));
        criterions.add(Restrictions.eq("disable", false));
        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);
        if(lstDevices!=null){
            return  lstDevices;
        }

        return null;
    }

    public List<Device> getDeviceByUnit(int unit) {

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("unit", unit));
        criterions.add(Restrictions.eq("disable", false));
        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);
        if(lstDevices!=null){
            return  lstDevices;
        }

        return null;
    }

    public List<Device> getDeviceInOrganizationAndUnit(Company item, int unit) {

        List<Criterion> criterions = new ArrayList<Criterion>();
        List<Company> lstCompany = getAllCompanyAndOrganizationByParent(item);
        lstCompany.add(item);
        criterions.add(Restrictions.in("company", lstCompany));
        criterions.add(Restrictions.eq("unit", unit));
        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);
        if(lstDevices!=null){
            return  lstDevices;
        }

        return null;
    }

    public Device getDevice(String id, String companyId) {
        Company company = this.getCompany(companyId);
        if (company == null) {
            return null;
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));
        criterions.add(Restrictions.eq("id", id));
        List<Device> lstDevices = (List<Device>) beanDaoUtil.query(Device.class, criterions);
        if(lstDevices!=null){
            if(lstDevices.size()>0){
                return  lstDevices.get(0);
            }
        }

        return null;
    }


    public Device addDevice(Device item) throws Exception {
        // check duplicate
        if (beanDaoUtil.get(Device.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Device.class.getName());
        }

        Device savedObject = (Device) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Device deleteDevice(Device item) throws Exception {
        return (Device) beanDaoUtil.delete(item);
    }

    // edit
    public Device editDevice(Device item) throws Exception {
        Device savedObject = (Device) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public List<?> saveOrUpdateDevices(List<Device> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (Device item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    /*
        Batch
    */
    public List<Batch> listBatch() {
        return (List<Batch>) beanDaoUtil.list(Batch.class.getName());
    }

    /*
       Product type
    */
    public List<CustomerType> listCustomerType() {
        return (List<CustomerType>) beanDaoUtil.list(CustomerType.class.getName());
    }

    /*
        Product type
     */
    public List<ProductType> listProductType() {
        return (List<ProductType>) beanDaoUtil.list(ProductType.class.getName());
    }

    public List<ProductType> getProductTypeByCompany(Company item) {

        List<Criterion> criterions = new ArrayList<Criterion>();
        Restrictions.or(Restrictions.eq("companyId", item.getId()), Restrictions.eq("global", true));
        List<ProductType> lstProductType = (List<ProductType>) beanDaoUtil.query(ProductType.class, criterions);
        if(lstProductType!=null){
            return  lstProductType;
        }

        return null;
    }

    /*
        Batch
     */
    public Batch getBatch(String id) {
        return (Batch) beanDaoUtil.get(Batch.class, id);
    }

    public String GeneratorCodeNewBatch(Model model){
        if(model==null) return null;

        //Get last Batch by Model
        Batch batch = getLastBatchByModel(model);
        //GeneratorCompanyId
        Integer suffix =0;
        if(batch!=null) {
            suffix = Integer.parseInt(batch.getCode());
        }
        suffix+=1;
        String code="000";
        int count=0;
        while (count<5){
            code = code.substring(0, (3 - suffix.toString().length()));
            code = code.concat(suffix.toString());
            Batch BatchCheck = searchBatchByCodeAndModel(code, model);
            if(BatchCheck==null){
                return code;
            }
            count++;
            suffix++;
        }
        return null;
    }

    public Batch searchBatchByCodeAndModel(String code, Model model) {
        if (code == null || model ==null) {
            return null;
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("code", code));
        criterions.add(Restrictions.eq("model", model));

        List<Batch> lstBatch = (List<Batch>) beanDaoUtil.query(Batch.class, criterions);
        if (lstBatch != null) {
            if(lstBatch.size()>0) {
                return lstBatch.get(0);
            }
        }

        return null;
    }

    private Batch getLastBatchByModel(Model model){
        List<Batch> lstBatch = searchBatchByModel(model);
        if(lstBatch!=null){
            if(lstBatch.size()>0){
                return lstBatch.get(lstBatch.size()-1);
            }
        }

        return null;
    }

    private List<Batch> searchBatchByModel(Model model) {
        List<Batch> items=null;
        Session session = beanHibernateUtil.getSession();

        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Batch.class);
            criteria.add(Restrictions.eq("model", model));
            criteria.addOrder(Order.asc("code"));
            items = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    private List<Batch> searchBatchByLstModel(List<Model> lstModel) {
        List<Batch> items=null;
        Session session = beanHibernateUtil.getSession();

        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Batch.class);
            criteria.add(Restrictions.in("model", lstModel));
            criteria.addOrder(Order.asc("code"));
            items = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public Batch saveBatch(Batch item) throws Exception {
        Batch savedObject = (Batch) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Batch deleteBatch(Batch item) throws Exception {
        return (Batch) beanDaoUtil.delete(item);
    }

    // edit
    public Batch editBatch(Batch item) throws Exception {
        Batch savedObject = (Batch) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public void pageBatch(PagingResult page,Company company, String fillterId,String fillterName) {
        Session session = beanHibernateUtil.getSession();
        try {
            // session.beginTransaction();
            Criteria criteria = session.createCriteria(Batch.class);
            if(StringUtils.isNotEmpty(fillterId)){
                criteria.add(Restrictions.ilike("id","%"+fillterId+"%"));
            }
            if(StringUtils.isNotEmpty(fillterName)){
                criteria.add(Restrictions.ilike("name","%"+fillterName+"%"));
            }
            if(company!=null){
                criteria.add(Restrictions.eq("companyId",company.getId()));
            }
            criteria.addOrder(Order.desc("dateCreated"));

            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    /*Model*/
    public List<Model> getModelByCompany(Company item) {

        List<Criterion> criterions = new ArrayList<Criterion>();
        Restrictions.or(Restrictions.eq("companyId", item.getId()), Restrictions.eq("global", true));
        List<Model> lstModels = (List<Model>) beanDaoUtil.query(Model.class, criterions);
        if(lstModels!=null){
            return  lstModels;
        }

        return null;
    }

    public Model addModel(Model item) throws Exception {
        if (beanDaoUtil.get(Model.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Model.class.getName());
        }

        Model savedObject = (Model) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    // edit
    public Model editModel(Model item) throws Exception {
        Model savedObject = (Model) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public Model getModel(String id) {
        return (Model) beanDaoUtil.get(Model.class, id);
    }

    public void pageModel(PagingResult page) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Model.class);
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    /*Stock*/
    public Stock getStock(long id) {
        return (Stock) beanDaoUtil.get(Stock.class, id);
    }

    public Stock addStock(Stock item) throws Exception {
        Stock savedObject = (Stock) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }
    public Stock editStock(Stock item) throws Exception {

        Stock savedObject = (Stock) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public void pageStockOut(PagingResult page, Company company, Company companyDes,Date start,Date end) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Stock.class);

            if (company != null) {
                criteria.add(Restrictions.eq("companySource", company));
            }
            if(companyDes!=null){
                criteria.add(Restrictions.eq("companyDes", companyDes));
            }
            if(start!=null){
                criteria.add(Restrictions.gt("dateCreated", start));
            }
            if(end!=null){
                criteria.add(Restrictions.lt("dateCreated", end));
            }
            criteria.addOrder(Order.desc("dateCreated"));

            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);

                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public void pageStockIn(PagingResult page, Company companySource, Company companyDes, Date start,Date end) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Stock.class);

            if (companySource != null) {
                criteria.add(Restrictions.eq("companySource", companySource));
            }
            if(companyDes!=null){
                criteria.add(Restrictions.eq("companyDes", companyDes));
            }
            if(start!=null){
                criteria.add(Restrictions.gt("dateCreated", start));
            }
            if(end!=null){
                criteria.add(Restrictions.lt("dateCreated", end));
            }
            criteria.addOrder(Order.desc("dateCreated"));

            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);

                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public List<StockDetail> searchStockDetail(Stock stock) {
        if (stock == null) {
            return new ArrayList<StockDetail>();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("stock", stock));

        List<StockDetail> lstDevices = (List<StockDetail>) beanDaoUtil.query(StockDetail.class, criterions);
        if (lstDevices != null) {
            return lstDevices;
        }

        return new ArrayList<StockDetail>();
    }

    public StockDetail searchStockDetailById(Stock stock,Device device) {
        if (stock == null || device ==null) {
            return new StockDetail();
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("stock", stock));
        criterions.add(Restrictions.eq("device", device));

        List<StockDetail> lstDevices = (List<StockDetail>) beanDaoUtil.query(StockDetail.class, criterions);
        if (lstDevices != null) {
            if(lstDevices.size()>0) {
                return lstDevices.get(0);
            }
        }
        return new StockDetail();
    }

    public List<?> saveOrUpdateStockDetal(List<StockDetail> items) {
        if(items == null){
            return new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (StockDetail item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public StockDetail deleteStockDetail(StockDetail item) throws Exception {
        return (StockDetail) beanDaoUtil.delete(item);
    }

    /******************************************************************/

    /*FeedBack*/

    public FeedBack getFeedBack(long id) {
        return (FeedBack) beanDaoUtil.get(FeedBack.class, id);
    }

    public FeedBack addFeedBack(FeedBack item) throws Exception {
        FeedBack savedObject = (FeedBack) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    // edit
    public FeedBack editFeedBack(FeedBack item) throws Exception {
        FeedBack savedObject = (FeedBack) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public FeedBack deleteFeedBack(FeedBack item) throws Exception {
        FeedBack savedObject = (FeedBack) beanDaoUtil.delete(item);
        return savedObject;
    }

    public List<FeedBack> searchFeedBackByCompany(Company item){
        if(item!=null){
            List<Company> lstCompany= getAllCompanyAndOrganizationByParent(item);
            if(lstCompany.size()>0){
                List<Criterion> criterions = new ArrayList<Criterion>();
                criterions.add(Restrictions.in("company", lstCompany));
                List<FeedBack> items = (List<FeedBack>) beanDaoUtil.query(FeedBack.class, criterions);
                return items;
            }
        }
        return  new ArrayList<>();
    }

    /******************************************************************/
    // UserDao

    public void pageUser(PagingResult page, String filterUserName, Company item) {
        Session session = beanHibernateUtil.getSession();
        if(item==null){
            return;
        }

        try {
            Criteria criteria = session.createCriteria(User.class);

            if (StringUtils.isNotEmpty(filterUserName)) {
                criteria.add(Restrictions.like("username", "%" + filterUserName + "%"));
            }

            List<Company> lstAllCompany = getAllCompanyByParent(item);
            lstAllCompany.add(item);
            if(lstAllCompany.size()>0){
                criteria.add(Restrictions.in("company", lstAllCompany));
            }
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public List<User> searchAllUserManagerByCompany(Company item){
        if(item!=null){
            List<Company> lstCompany= getAllCompanyAndOrganizationByParent(item);
            if(lstCompany.size()>0){
                List<Criterion> criterions = new ArrayList<Criterion>();
                criterions.add(Restrictions.in("company", lstCompany));
                List<User> items = (List<User>) beanDaoUtil.query(User.class, criterions);
                return items;
            }
        }
        return  new ArrayList<>();
    }

    public User getUser(String id) {
        return (User) beanDaoUtil.get(User.class, id);
    }

    public List<User> searchUserByCompany(Company company) {
        if (company == null) {
            return new ArrayList<User>();
        }

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("company", company));

        List<User> lstUser = (List<User>) beanDaoUtil.query(User.class, criterions);

        if (lstUser != null) {
            return lstUser;
        }

        return new ArrayList<User>();
    }

    public User editUser(User item) throws Exception {
        User savedObject = (User) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public User addUser(User item) throws Exception {
        // check duplicate
        if (beanDaoUtil.get(User.class, item.getUsername()) != null) {
            // not unique - return error
            throw new NonUniqueObjectException(item.getUsername(), User.class.getName());
        }
        User savedObject = (User) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public User updateUser(User item) throws Exception {
        User savedObject = (User) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public User deleteUser(User item) throws Exception {
        return (User) beanDaoUtil.delete(item);
    }

    public List<?> saveOrUpdateUsers(List<User> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (User item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }
    /******************************************************************/
    // ContractDao

    public Contract editContract(Contract item) throws Exception {
        Contract savedObject = (Contract) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public Contract addContract(Contract item) throws Exception {
        // check dupplicate
        if (beanDaoUtil.get(Contract.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Contract.class.getName());
        }

        Contract savedObject = (Contract) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public List<?> saveOrUpdateContracts(List<Contract> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (Contract item : items) {
                session.saveOrUpdate(item);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }

    public boolean isConflictContract(Contract item) throws Exception {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("vehicle", item.getVehicle()));
        Criterion criterionStart = Restrictions.between("dateStart", item.getDateStart(), item.getDateEnd());
        Criterion criterionEnd   = Restrictions.between("dateEnd", item.getDateStart(), item.getDateEnd());
        criterions.add(Restrictions.or(criterionStart, criterionEnd));
        List<Contract> lstContract = (List<Contract>) beanDaoUtil.query(Contract.class, criterions);
        if(lstContract!=null){
            if(lstContract.size()>0) {
                if (lstContract.size() > 1) {
                    return true;
                } else {
                    if (lstContract.size() == 1) {
                        Contract newItem = lstContract.get(0);
                        if (newItem.getId() != item.getId()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public void pageContract(PagingResult page, Company item, String vehicleId) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Contract.class);
            if (vehicleId!=null && org.apache.commons.lang3.StringUtils.isNotEmpty(vehicleId)) {
                Vehicle vehicle =getVehicle(vehicleId);
                criteria.add(Restrictions.eq("vehicle", vehicle));
            }

            if (item!=null) {
                List<Company> lstCompany = getAllCompanyAndOrganizationByParent(item);
                criteria.add(Restrictions.in("company", lstCompany));
            }

            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);

                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public void pageContractCensored(PagingResult page,String vehicleId) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Contract.class);
            criteria.add(Restrictions.eq("censored", false));
            if (vehicleId!=null && org.apache.commons.lang3.StringUtils.isNotEmpty(vehicleId)) {
                Vehicle vehicle =getVehicle(vehicleId);
                criteria.add(Restrictions.eq("vehicle", vehicle));
            }

            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);

                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public Contract getContract(long id) {
        return (Contract) beanDaoUtil.get(Contract.class, id);
    }

    public List<Contract> getContractByCode(String code) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("code", code));
        List<Contract> lstContract = (List<Contract>) beanDaoUtil.query(Contract.class, criterions);

        return  lstContract;
    }

    public List<Contract> searchContractByVehicle(Vehicle vehicle) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("vehicle", vehicle));
        List<Contract> lstContract = (List<Contract>) beanDaoUtil.query(Contract.class, criterions);

        return  lstContract;
    }

    public List<Contract> searchContractCensored(boolean censored) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("censored", censored));
        List<Contract> lstContract = (List<Contract>) beanDaoUtil.query(Contract.class, criterions);

        return  lstContract;
    }
    public Contract deleteContract(Contract item) throws Exception {
        return (Contract) beanDaoUtil.delete(item);
    }


    /******************************************************************/
    // ContractTypeDao
    public List<ContractType> listContractType() {
        return (List<ContractType>) beanDaoUtil.list(ContractType.class.getName());
    }

    public ContractType addContractType(ContractType item) throws Exception {
        if (beanDaoUtil.get(ContractType.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), ContractType.class.getName());
        }

        ContractType savedObject = (ContractType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    // edit
    public ContractType editContractType(ContractType item) throws Exception {
        ContractType savedObject = (ContractType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public ContractType getContractType(String id) {
        return (ContractType) beanDaoUtil.get(ContractType.class, id);
    }

    public void pageContractType(PagingResult page) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(ContractType.class);
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    /******************************************************************/
    // CustomerTypeDao
    public void pageCustomerType(PagingResult page) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(CustomerType.class);
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }
    public CustomerType addCustomerType(CustomerType item) throws Exception {
        if (beanDaoUtil.get(CustomerType.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), CustomerType.class.getName());
        }

        CustomerType savedObject = (CustomerType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    // edit
    public CustomerType editCustomerType(CustomerType item) throws Exception {
        CustomerType savedObject = (CustomerType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public CustomerType getCustomerType(String id) {
        return (CustomerType) beanDaoUtil.get(CustomerType.class, id);
    }
    /******************************************************************/
    // FuelTypeDao
    public List<FuelType> listFuelType() {
        return (List<FuelType>) beanDaoUtil.list(FuelType.class.getName());
    }

    public FuelType addFuelType(FuelType item) throws Exception {
        if (beanDaoUtil.get(FuelType.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), FuelType.class.getName());
        }

        FuelType savedObject = (FuelType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    // edit
    public FuelType editFuelType(FuelType item) throws Exception {
        FuelType savedObject = (FuelType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public FuelType getFuelType(Integer id) {
        return (FuelType) beanDaoUtil.get(FuelType.class, id);
    }

    public void pageFuelType(PagingResult page) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(FuelType.class);
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    //******************************************************************/
    // TransportTypeDao
    public TransportType addTransportType(TransportType item) throws Exception {
        if (beanDaoUtil.get(TransportType.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), TransportType.class.getName());
        }

        TransportType savedObject = (TransportType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    // edit
    public TransportType editTransportType(TransportType item) throws Exception {
        TransportType savedObject = (TransportType) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public TransportType getTransportType(int id) {
        return (TransportType) beanDaoUtil.get(TransportType.class, id);
    }

    public List<TransportType> listTransportType() {
        return (List<TransportType>) beanDaoUtil.list(TransportType.class.getName());
    }

    public void pageTransportType(PagingResult page) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(TransportType.class);
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    /******************************************************************/
    // FirmwareDao
    public List<Firmware> listFirmware() {
        return (List<Firmware>) beanDaoUtil.list(Firmware.class.getName());
    }

    public Firmware addFirmware(Firmware item) throws Exception {
        if (beanDaoUtil.get(Firmware.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), Firmware.class.getName());
        }

        Firmware savedObject = (Firmware) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    // edit
    public Firmware editFirmware(Firmware item) throws Exception {
        Firmware savedObject = (Firmware) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public Firmware getFirmware(String id) {
        return (Firmware) beanDaoUtil.get(Firmware.class, id);
    }

    public void pageFirmware(PagingResult page) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Firmware.class);
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    /**
     * ProductType
     */
    public void pageProductType(PagingResult page) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(ProductType.class);
            // record count
            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);
                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                page.setItems(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }

    public ProductType addProductType(ProductType item) throws Exception {
        if (beanDaoUtil.get(ProductType.class, item.getId()) != null) {
            throw new NonUniqueObjectException(item.getId(), ProductType.class.getName());
        }

        ProductType savedObject = (ProductType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public ProductType editProductType(ProductType item) throws Exception {
        ProductType savedObject = (ProductType) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public ProductType getProductType(String id) {
        return (ProductType) beanDaoUtil.get(ProductType.class, id);
    }

    public List<Model> searchModelByProductType(String productTypeId){
        List<Criterion> criterions = new ArrayList<Criterion>();
        ProductType item= getProductType(productTypeId);
        criterions.add(Restrictions.eq("productType", item));
        List<Model> lstModel = (List<Model>) beanDaoUtil.query(Model.class, criterions);
        return lstModel;
    }

    public void pageModelByProductType(PagingResult page, String productTypeId) {
        Session session = beanHibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(Model.class);

            if (StringUtils.isNotEmpty(productTypeId)) {
                ProductType item= getProductType(productTypeId);
                criteria.add(Restrictions.eq("productType", item));
                criteria.addOrder(Order.desc("dateCreated"));
            }

            int rowCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (rowCount > 0) {
                page.setRowCount(rowCount);

                criteria.setProjection(null);
                criteria.setFirstResult(page.getPageNumber() * page.getNumberPerPage());
                criteria.setMaxResults(page.getNumberPerPage());
                List list = criteria.list();
                page.setItems(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }
    }
    /*
     Text
     */
    public Text editText(Text item) throws Exception {
        Text savedObject = (Text) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }

    public  Text getText(String key){
        return (Text) beanDaoUtil.get(Text.class, key);
    }

    public List<Text> listText() {
        List<Text> items=null;
        Session session = beanHibernateUtil.getSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Text.class);
            criteria.addOrder(Order.asc("id"));
            items = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            beanHibernateUtil.closeSession(session);
        }

        return items;
    }
}
