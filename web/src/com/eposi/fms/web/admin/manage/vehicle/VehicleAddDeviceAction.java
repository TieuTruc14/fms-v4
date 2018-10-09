package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by TienManh on 6/13/2016.
 */
public class VehicleAddDeviceAction extends AbstractAction {

    private static final long serialVersionUID = -3015214798197146573L;
    private String id;
    private List<DeviceItem> devices=new ArrayList<>();
    private String messeage="";

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_DEVICE_ADD)){
           messeage=getTitleText("fms.failed.authority");
            return INPUT;
        }
        try{
            if(StringUtils.isNotEmpty(id)){
                Vehicle vehicle= beanFmsDao.getVehicle(id);
                List<Device> deviceList= new ArrayList<Device>();
                if(devices!=null && devices.size()>0 && vehicle!=null && user!=null){
                    for ( DeviceItem item : devices) {
                        if (StringUtils.equals("on", item.getCheckbox())) {
                            Device device=beanFmsDao.getDevice(item.getId());
                            device.setVehicle(vehicle);
                            device.setUnit(1);
                            if(device.getDateActive()==null && device.getDateEnd()==null){
                                device.setDateActive(new Date());
                                Calendar cal=Calendar.getInstance();
                                cal.add(Calendar.YEAR, 1);
                                Date nextYear= cal.getTime();
                                device.setDateEnd(nextYear);
                            }
                            deviceList.add(device);
                        }
                    }
                    if(deviceList.size()>0){
                        //kiem tra ko cho add 2 TB GSHT, hoac sim (trợ giúp update với v2)
                        if(checkDuplicate(deviceList)){
                            messeage="Không thể thêm 2 thiết bị cùng loại sản phẩm vào 1 xe!";
                            return INPUT;
                        }
                        if(checkErrorDuplicate(vehicle,deviceList)){
                           messeage="Hiện xe này đã có thiết bị cùng loại sản phẩm với thiết bị bạn thêm!";
                            return INPUT;
                        }
                        //add xong v3 moi co cai cho v2 search ma add
                        beanFmsDao.saveOrUpdateDevices(deviceList);

                        //khi add TB GSHT và sim mới xét add Hợp đồng và V2
                        if(checkAddGSHTSim(deviceList)){
                            //add contract, and set dateEnd vehicle 2 week if active vehicle
                            //kiem tra xem co phai add xe moi ko.Neu phai thi them hop dong
                            if(vehicle.isVehicleNew()){
                                addContract(vehicle);
                            }

                            //add v2
                            beanFmsV2Dao.saveOrUpdateVehicle(vehicle);
                        }


                        //update vehicle Activity
                        if(vehicle.getKonexyId()!=null) {
                            Activity activity = new Activity();
                            activity.setActionName("AddDevice");
                            activity.setActorName(user.getUsername());
                            activity.setObjectId(vehicle.getId());
                            activity.setObjectName(Vehicle.class.getName());
                            activity.setIndirectObjectName("/manage/vehicle/vehicle.detail.action?vehicleId=" + vehicle.getId());
                            activity.setDate(new Date());
                            activity.setContext(devicesToString(deviceList));
                            activity.setPassive(true);

                            //Update Activity Vehicle
                            beanReportReader.saveActivity(vehicle.getKonexyId(), activity);
                        }

                    }
                }
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.vehicle.add.devcie.execute: "+e.getMessage());
        }
        return SUCCESS;
    }

    private void addContract(Vehicle item){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Device> devices = beanFmsDao.searchDeviceByVehicle(item);
        String deviceId="",sim="";
        for(Device d:devices){
            if(d.getBatch().getModel().getProductType().getType()==0){
                deviceId = d.getId();
            }else if(d.getBatch().getModel().getProductType().getType()==1){
                sim = d.getId();
            }
        }
        if(deviceId.length()>0 && sim.length()>0){
            List<Contract> lstCotnract=beanFmsDao.searchContractByVehicle(item);
            if(lstCotnract==null || lstCotnract.size()==0){
                try{
                    Contract contract=new Contract();
                    contract.setVehicle(item);
                    contract.setCompany(item.getCompany());
                    contract.setDateCreated(new Date());
                    contract.setDateUpdate(new Date());
                    contract.setUserCreated(user.getUsername());
                    contract.setUserUpdated(user.getUsername());
                    contract.setDateStart(new Date());

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, 1);
                    Date nextYear = cal.getTime();
                    contract.setDateEnd(nextYear);
                    contract.setCensored(false);

                    item.setDateStart(new Date());
                    Calendar cal1 = Calendar.getInstance();
                    cal1.add(Calendar.DATE, 15);
                    Date nextDate= cal1.getTime();
                    item.setDateEnd(nextDate);

                    item.setVehicleNew(false);//xac nhan xe cũ roi

                    beanFmsDao.editVehicle(item);
                    beanFmsDao.addContract(contract);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DeviceItem> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceItem> devices) {
        this.devices = devices;
    }

    public String getMesseage() {
        return messeage;
    }

    public void setMesseage(String messeage) {
        this.messeage = messeage;
    }

    private boolean checkDuplicate(List<Device> devices){
        if(devices==null || devices.size()==0) return false;
        for(int i=0;i<devices.size();i++){
            int typeId=devices.get(i).getBatch().getModel().getProductType().getType();
            for(int j=i+1;j<devices.size();j++){
                if(typeId==(devices.get(j).getBatch().getModel().getProductType().getType())) return true;
            }
        }
        return false;
    }

    private boolean checkErrorDuplicate(Vehicle vehicle, List<Device> devices){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        if(vehicle==null || devices==null || devices.size()==0) return false;
        List<Device> lstDevicesCurent=beanFmsDao.searchAllDeviceByVehicle(vehicle);
        for(Device device:lstDevicesCurent){
            String productId=device.getBatch().getModel().getProductType().getId();
            for(Device deviceCheck:devices){
                if(productId.equals(deviceCheck.getBatch().getModel().getProductType().getId())) return true;
            }
        }
        return false;
    }

    private boolean checkAddGSHTSim(List<Device> devices){
        if(devices==null || devices.size()==0) return false;
        for(Device device:devices){
            if(device.getBatch().getModel().getProductType().getType()==0 || device.getBatch().getModel().getProductType().getType()==1) return true;
        }
        return false;
    }

    private String devicesToString(List<Device> devices){
        StringBuffer sb = new StringBuffer("");
        for(Device device:devices){
            sb.append(device.getId()).append(",");
        }
        sb.append("");

        return sb.toString();
    }

    public static class DeviceItem{
        private String id;
        private String checkbox;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCheckbox() {
            return checkbox;
        }

        public void setCheckbox(String checkbox) {
            this.checkbox = checkbox;
        }
    }
}
