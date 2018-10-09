package com.eposi.fms.v2.persitence;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.model.*;
import com.eposi.fms.v2.model.Device;
import com.eposi.fms.v2.model.Province;
import com.eposi.fms.v2.model.TransportType;
import com.eposi.fms.v2.model.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FmsV2Dao extends AbstractBean {
    private HibernateUtil beanHibernateUtil;
    private DaoUtil beanDaoUtil;
    private FmsDao  beanFmsDao;

    public void init() {
        beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtilV2");
        beanHibernateUtil = (HibernateUtil) getBean("beanHibernateUtilV2");
        beanFmsDao        =(FmsDao) getBean("beanFmsDao");
    }

    public List<Owner> getOwnerLikeCode(String code) {

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.like("code", "%" + code + "%"));
        List<Owner> lstDevices = (List<Owner>) beanDaoUtil.query(Owner.class, criterions);
        if(lstDevices!=null){
            return  lstDevices;
        }

        return null;
    }

    public List<?> saveOrUpdateOwners(List<Owner> items) {
        if(items==null){
            return  new ArrayList<>();
        }

        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (Owner item : items) {
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


    public Owner editCompany(Company item){
        Owner owner = getOwner(item.getOwner());
        if(owner!=null) {
            owner.setName(item.getName());
            Province province = getProvince(Integer.parseInt(item.getProvince().getId()));
            owner.setProvince(province);
            if (item.getParent() != null) {
                Company parent = beanFmsDao.getCompany(item.getParent().getId());
                if (parent.getOwner() > 0) {
                    OrganizationUnit organizationUnit = getOrganizationUnit(parent.getOwner());
                    if (organizationUnit != null) {
                        owner.setParent(organizationUnit);
                    }
                }
            }
            Owner save = (Owner) saveOwner(owner);
            return save;
        }
        return null;
    }

//    public Owner addCompany(Company item){
//        Owner owner = new Owner();
//        owner.setName(item.getName());
//        Province province = getProvince(Integer.parseInt(item.getProvince().getId()));
//        owner.setProvince(province);
//        if(item.getParent()!=null) {
//            Company parent = beanFmsDao.getCompany(item.getParent().getId());
//            if(parent.getOwner()>0) {
//                OrganizationUnit organizationUnit = getOrganizationUnit(parent.getOwner());
//                if(organizationUnit!=null) {
//                    owner.setParent(organizationUnit);
//                }
//            }
//        }
//        owner.setTypeOwner(3);
//        owner.setCode(item.getId());
//        Owner save =(Owner)saveOwner(owner);
//        return save;
//    }
    public Customer addCompany(Company item){
        Customer owner = new Customer();
        owner.setName(item.getName());
        Province province = getProvince(Integer.parseInt(item.getProvince().getId()));
        owner.setProvince(province);
        if(item.getParent()!=null) {
            Company parent = beanFmsDao.getCompany(item.getParent().getId());
            if(parent.getOwner()>0) {
                OrganizationUnit organizationUnit = getOrganizationUnit(parent.getOwner());
                if(organizationUnit!=null) {
                    owner.setParent(organizationUnit);
                }
            }
        }
        owner.setTypeOwner(3);
        owner.setCode(item.getId());
        Customer save =(Customer)saveOwner(owner);
        return save;
    }

    public Owner addOrganization(Company item){
        Owner owner = new Owner();
        owner.setName(item.getName());
        if(item.getParent()!=null) {
            Company parent = beanFmsDao.getCompany(item.getParent().getId());
            if(parent.getOwner()>0) {
                OrganizationUnit organizationUnit = getOrganizationUnit(parent.getOwner());
                if(organizationUnit!=null) {
                    owner.setParent(organizationUnit);
                }
            }
        }
        owner.setTypeOwner(1);
        owner.setCode(item.getId());
        Owner save =(Owner)saveOwner(owner);
        return save;
    }

    public Owner editOrganization(Company item){
        Owner owner = getOwner(item.getOwner());
        if(owner!=null) {
            owner.setName(item.getName());
            if (item.getParent() != null) {
                Company parent = beanFmsDao.getCompany(item.getParent().getId());
                if (parent.getOwner() > 0) {
                    OrganizationUnit organizationUnit = getOrganizationUnit(parent.getOwner());
                    if (organizationUnit != null) {
                        owner.setParent(organizationUnit);
                    }
                }
            }
            Owner save = (Owner) saveOwner(owner);
            return save;
        }
        return null;
    }

    public Device deleteVehicle(Vehicle item){
        Device device=getDevice(item.getId());
        if(device!=null){
            try{
                List<DeviceContract> deviceContractList= getDeviceContractByDevice(device);
                if(deviceContractList!=null && deviceContractList.size()>0) deleteDeviceContracts(deviceContractList);
                deleteDevice(device);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return device;
    }

    public Device editVehicle(Vehicle item){
        Device device = getDevice(item.getId());
        if(device==null) return null;
        Device save = null;
        List<com.eposi.fms.model.Device> devices = beanFmsDao.searchDeviceByVehicle(item);
        if(devices!=null) {
            if(devices.size()>1) {
                //Find device GSHT && Find SIM
                String deviceId ="";
                String sim ="";
                for(com.eposi.fms.model.Device d:devices){
                    if(d.getBatch().getModel().getProductType().getType()==0){
                        deviceId = d.getId();
                    }else if(d.getBatch().getModel().getProductType().getType()==1){
                        sim = d.getId();
                    }
                }

                if((deviceId.length()>0)&&(sim.length()>0)) {
                    try {
                        device.setDeviceId(deviceId);
                        device.setSim(sim);
                        device.setDateActive(item.getDateStart());
                        device.setDateEnd(item.getDateEnd());
                        save = editDevice(device);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return save;
    }

    public Device addVehicle(Vehicle item){
        Device device = new Device();
        device.setVehicleId(item.getId());
        Device save = null;
        List<com.eposi.fms.model.Device> devices = beanFmsDao.searchAllDeviceByVehicle(item);
        if(devices!=null) {
            if(devices.size()>1) {
                //Find device GSHT & Find SIM
                String deviceId ="";
                String sim ="";
                for(com.eposi.fms.model.Device d:devices){
                    if(d.getBatch().getModel().getProductType().getType()==0){
                        deviceId = d.getId();
                    }else if(d.getBatch().getModel().getProductType().getType()==1){
                        sim = d.getId();
                    }
                }

                if((deviceId.length()>0)&&(sim.length()>0)) {
                    try {
                        device.setDeviceId(deviceId);
                        device.setSim(sim);
                        if(item.getCompany().getProvince()!=null){
                            Province province = getProvince(Integer.parseInt(item.getCompany().getProvince().getId()));
                            device.setProvince(province);
                        }
                        if(item.getType().getId()!=null && item.getType().getId()>0){
                            TransportType transportType = getTransportType(item.getType().getId());
                            device.setTransportType(transportType);
                        }

                        VehicleType vehicleType=getVehicleType(3);//mac dinh xe con
                        if(vehicleType!=null) {
                            device.setVehicleType(vehicleType);
                            device.setMaxSpeed(vehicleType.getMaxSpeed());
                        }

                        device.setTimeOverSpeed(20);//20s theo quy chuan 31
                        Owner owner=getOwner(item.getCompany().getOwner());
                        if(owner!=null){
                            device.setOwner(owner);
                        }

                        //thời hạn xe theo đúng vehicle v3 dù thay hợp đồng hay chưa.
                        device.setDateActive(item.getDateStart());
                        device.setDateEnd(item.getDateEnd());

                        device.setBgt(item.isBgt());
                        device.setConfigI0(item.isConfigI0());
                        device.setConfigI1(item.isConfigI1());
                        device.setConfigI2(item.isConfigI2());
                        device.setOnFilter(item.isOnFilter());
                        if(item.getCapacity()!=null ){
                            device.setSeatOrWeight(item.getCapacity().toString());
                        }
                        device.setSensor(item.isSensor());
                        device.setNote(item.getNote());
                        if(item.isConfigI5()){
                            device.setWorkVehicle(true);
                            device.setWorkVehicleMapping(item.getWorkVehicleMapping());
                        }

                        save = addDevice(device);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return save;
    }

    public void saveOrUpdateVehicle(Vehicle item){
        Device device = getDevice(item.getId());
        if(device==null){
            addVehicle(item);
        }else{
            editVehicle(item);
        }
    }

    public Device editDateDeviceFromVehicleV3(Vehicle item){
        if(item==null) return null;
        try{
            Device device = getDevice(item.getId());
            if(device!=null){
                if(item.getDateEnd()!=null)  device.setDateEnd(item.getDateEnd());
                if(item.getDateStart()!=null)   device.setDateActive(item.getDateStart());
                if(item.getDateEnd()!=null && item.getDateEnd().after(new Date())) device.setDisable(false);
                editDevice(device);
                return device;
            }
        }catch (Exception e){
            System.out.println("Error: fms.v2.persitence.FmsV2Dao.editDateDeviceFromVehicleV3");
            e.printStackTrace();
        }

        return null;
    }
    public Device mergeDeviceFromVehicleV3(Vehicle item,Device device){
        try{
            if(item.getType().getId()!=null && item.getType().getId()>0){
                TransportType transportType = getTransportType(item.getType().getId());
                device.setTransportType(transportType);
            }

            VehicleType vehicleType=getVehicleType(3);//mac dinh xe con
            if(vehicleType!=null) {
                device.setVehicleType(vehicleType);
                device.setMaxSpeed(vehicleType.getMaxSpeed());
            }

            device.setTimeOverSpeed(20);//20s theo quy chuan 31
//            Owner owner=getOwner(item.getCompany().getOwner());
//            if(owner!=null) device.setOwner(owner);
            device.setDisable(item.isDisable());

            device.setBgt(item.isBgt());
            device.setConfigI0(item.isConfigI0());
            device.setConfigI1(item.isConfigI1());
            device.setConfigI2(item.isConfigI2());
            device.setOnFilter(item.isOnFilter());
            if(item.getCapacity()!=null ){
                device.setSeatOrWeight(item.getCapacity().toString());
            }
            device.setSensor(item.isSensor());
            device.setNote(item.getNote());
            if(item.isConfigI5()){
                device.setWorkVehicle(true);
                device.setWorkVehicleMapping(item.getWorkVehicleMapping());
            }

            device = editDevice(device);
        }catch (Exception e){
            e.printStackTrace();
        }

        return device;
    }
    public User editUser(com.eposi.fms.model.User item){
        User user = getUser(item.getUsername());
        if(user!=null){
            user.setName(item.getName());
            user.setIsSuperUser(item.isSupperUser());
            user.setPassword(item.getPassword());
            return saveUser(user);
        }
        return null;
    }

    public User addUser(com.eposi.fms.model.User item){
        User user = getUser(item.getUsername());
        if(user==null){
            user = new User();
            user.setName(item.getName());
            user.setUsername(item.getUsername());
            user.setPassword(item.getPassword());
            Owner owner = getOwner(item.getCompany().getOwner());
            user.setOwner(owner);
            user.setIsSuperUser(item.isSupperUser());
            user.setRealtionVehicle(false);
            user.setSoType(0);
            user.setEnabled(true);
            user.setView(0);
            return saveUser(user);
        }

        return null;
    }



    public Device getDevice(String id) {
        return (Device) beanDaoUtil.get(Device.class, id);
    }

    public List<Device> listDevice() {
        return (List<Device>) beanDaoUtil.list(Device.class.getName());
    }

    public Device editDevice(Device item) throws Exception {
        Device savedObject = (Device) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public Device addDevice(Device item) throws Exception {
        if (beanDaoUtil.get(Device.class, item.getDeviceId()) != null) {
            throw new NonUniqueObjectException(item.getDeviceId(), Device.class.getName());
        }

        Device savedObject = (Device) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }
    public void vehicleChangeCompany(Vehicle vehicle,Company company) throws Exception{
        Device device=getDevice(vehicle.getId());
        Owner owner=getOwner(company.getOwner());
        if(device!=null && owner!=null){
            device.setOwner(owner);
            editDevice(device);
            //xoa het users_device tranh xem xe
            List<UserDevice> lstUserDevices=lstUserDeviceByVehicleId(device.getVehicleId());
            if(lstUserDevices!=null && lstUserDevices.size()>0) deleteUserDevices(lstUserDevices);
        }

    }

    public void editChangeVehicle(Vehicle item, String newVehicleId,String newNote)throws Exception{
        Device oldDevice=deleteVehicle(item);//xóa biển cũ
        oldDevice.setNote(newNote);
        oldDevice.setVehicleId(newVehicleId);
        addDevice(oldDevice);
    }

    public Device deleteDevice(Device item) throws Exception {
        return (Device) beanDaoUtil.delete(item);
    }

    /*DeviceContract*/
    public List<DeviceContract> getDeviceContractByDevice(Device device) {
        if (device == null) {
            return null;
        }
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("device", device));
        List<DeviceContract> lstDevices = (List<DeviceContract>) beanDaoUtil.query(DeviceContract.class, criterions);
        if(lstDevices!=null){
            return  lstDevices;
        }

        return null;
    }

    public List<?> deleteDeviceContracts(List<DeviceContract> items) {
        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (DeviceContract item : items) {
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

    /*UserDevice*/
    private List<UserDevice> lstUserDeviceByVehicleId(String vehicleId) {
        List<Criterion> criterions = new ArrayList<>();
        criterions.add(Restrictions.eq("vehicleId", vehicleId));

        List<UserDevice> lstUserDevice = (List<UserDevice>) beanDaoUtil.query(UserDevice.class, criterions);
        if(lstUserDevice != null && lstUserDevice.size() > 0) {
            return lstUserDevice;
        }
        return null;
    }

    public List<?> deleteUserDevices(List<UserDevice> items) {
        Session session = beanHibernateUtil.getSession();
        session.getTransaction().begin();
        try {
            for (UserDevice item : items) {
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

    /*OWNER*/
    public Owner getOwner(Long id) {
        DaoUtil beanDaoUtilV2 = (DaoUtil) this.getBean("beanDaoUtilV2");
        return (Owner) beanDaoUtilV2.get(Owner.class, id);
    }

    public Owner saveOwner(Owner item) {
        DaoUtil beanDaoUtil = (DaoUtil)this.getBean("beanDaoUtilV2");
        return (Owner) beanDaoUtil.saveOrUpdate(item);
    }

    public List<Owner> lstOwner(int type) {
        DaoUtil beanDaoUtilV2 = (DaoUtil) this.getBean("beanDaoUtilV2");

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("typeOwner", type));

        List<Owner> lst = (List<Owner>) beanDaoUtilV2.query(Owner.class, criterions);

        return lst;
    }
    /*Driver*/

    public DriverCard addDriverCard(Driver item){
        if(getDriver(item.getId())!=null) return null;
        try{
            DriverCard driverCard=new DriverCard();
            driverCard.setDriverKey(item.getId());
            driverCard.setName(item.getName());
            driverCard.setLicenseNumber(item.getLicenceKey());
            driverCard.setDateOfGrant(item.getLicenceDay());
            driverCard.setDateExper(item.getLicenceExp());
            driverCard.setPhone(item.getPhone());
            Owner owner=getOwner(item.getCompany().getOwner());
            if(owner!=null) driverCard.setOwner(owner);

            return addDriver(driverCard);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public DriverCard editDriverCard(Driver item){
        DriverCard driverCard=getDriver(item.getId());
        if(driverCard==null) return null;
        try{
            driverCard.setName(item.getName());
            driverCard.setLicenseNumber(item.getLicenceKey());
            driverCard.setDateOfGrant(item.getLicenceDay());
            driverCard.setDateExper(item.getLicenceExp());
            driverCard.setPhone(item.getPhone());

            return editDriver(driverCard);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public DriverCard getDriver(String id) {
        return (DriverCard) beanDaoUtil.get(DriverCard.class, id);
    }

    public DriverCard editDriver(DriverCard item) throws Exception {
        DriverCard savedObject = (DriverCard) beanDaoUtil.saveOrUpdate(item);

        return savedObject;
    }

    public DriverCard addDriver(DriverCard item) throws Exception {
        if (beanDaoUtil.get(DriverCard.class, item.getDriverKey()) != null) {
            throw new NonUniqueObjectException(item.getDriverKey(), DriverCard.class.getName());
        }

        DriverCard savedObject = (DriverCard) beanDaoUtil.saveOrUpdate(item);
        return savedObject;
    }
    public DriverCard deleteDriver(DriverCard item) throws Exception {
        return (DriverCard) beanDaoUtil.delete(item);
    }

    /*TRansport*/
    private TransportType getTransportType(int id) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtilV2");
        return (TransportType) beanDaoUtil.get(TransportType.class, id);
    }

    private VehicleType getVehicleType(int id) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtilV2");
        return (VehicleType) beanDaoUtil.get(VehicleType.class, id);
    }

    private User getUser(String strUsername) {
        List<Criterion> criterions = new ArrayList<>();
        criterions.add(Restrictions.eq("username", strUsername));

        List<User> lstUser = (List<User>) beanDaoUtil.query(User.class, criterions);
        if(lstUser != null && lstUser.size() > 0) {
            return lstUser.get(0);
        }

        return null;
    }

    private User saveUser(User item) {
        return (User) beanDaoUtil.saveOrUpdate(item);
    }



    private OrganizationUnit getOrganizationUnit(long id) {
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtilV2");
        return (OrganizationUnit) beanDaoUtil.get(OrganizationUnit.class, id);
    }

    private Province getProvince(int pId) {
        DaoUtil beanDaoUtilV2 = (DaoUtil) this.getBean("beanDaoUtilV2");

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("id", pId));

        List<Province> lst = (List<Province>) beanDaoUtilV2.query(Province.class, criterions);

        if (lst != null ) {
            if (lst.size() == 1) {
                return lst.get(0);
            }
        }

        return null;
    }

}
