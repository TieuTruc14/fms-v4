package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.common.ReportDefine;
import com.eposi.fms.html.HtmlBuilder;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.services.FmsMailService;
import com.eposi.fms.services.HazelcastClientMapAkka;
import com.eposi.fms.services.XlsClient;
import org.apache.log4j.Logger;

import java.util.*;


public class Server extends AbstractBean {
    private static final long serialVersionUID = 9145356443596682289L;

    private static Logger log = Logger.getLogger(Server.class.getName());
    private long startTime = 0;
    private FmsDao beanFmsDao;
    private FmsMailService beanFmsMailService;
    private HazelcastClientMapAkka beanHazelcastClientMapAkka;
    private XlsClient beanXlsClient;

    private HashMap<String, Xmit> hashMapVehicleXmit      = new HashMap<String, Xmit>();
    private HashMap<String, Notification> hashMapNotification = new HashMap<String, Notification>();

	public void run() {
        System.out.println("Server.runNotification:");
        runNotification();
	}

    private void runNotification() {
        beanFmsDao = (FmsDao)applicationContext.getBean("beanFmsDao");
        beanFmsMailService = (FmsMailService) applicationContext.getBean("beanFmsMailService");
        beanHazelcastClientMapAkka = (HazelcastClientMapAkka) applicationContext.getBean("beanHazelcastClientMapAkka");
        beanXlsClient              =(XlsClient)applicationContext.getBean("beanXlsClient");

        while (true) {
            long now = System.currentTimeMillis();
            if (startTime == 0) {
                startTime = now;
            }
            long span = now - startTime;
            if ((span > 1800000)) {//30*60*1000
                List<Vehicle> lstVehicle =beanFmsDao.listVehicle();
                for(Vehicle vehicle :lstVehicle){
                    VehicleState state = beanHazelcastClientMapAkka.get(vehicle.getId());
                    if(state!=null){
                        //MSG OFF
                        if(now - state.getTime().getTime()>3600000){//60*60*1000
                            if(hashMapVehicleXmit.get(vehicle.getId()) == null){
                                Xmit xmit = new Xmit();
                                xmit.setVehicle(state.getVehicle());
                                xmit.setCompany(state.getCompanyId());
                                xmit.setBeginTime(state.getTime());
                                xmit.setType(ReportDefine.DATA_XMIT_MSG_OFF);
                                xmit.setCounter(0);
                                xmit.setX(state.getX());
                                xmit.setY(state.getY());
                                String address =beanXlsClient.doreverseGeocode(xmit.getX(), xmit.getY());
                                if(address!=null){
                                    xmit.setAddress(address);
                                }
                                hashMapVehicleXmit.put(vehicle.getId(), xmit);
                            }
                        }else {
                            if(hashMapVehicleXmit.get(vehicle.getId()) != null){
                                hashMapVehicleXmit.remove(vehicle.getId());
                            }
                        }

                        //GPS OFF
                        DataXmit dataXmit = state.getXmit();
                        if (dataXmit != null) {
                            if (dataXmit.getType() == ReportDefine.DATA_XMIT_GPS_OFF) {
                                if (dataXmit.getDuration() > 3600) {//1*60*60
                                    if(now - dataXmit.getEndTime().getTime()<900000) {//15*60*1000
                                        if (hashMapVehicleXmit.get(vehicle.getId()) == null) {
                                            Xmit xmit = new Xmit();
                                            xmit.setVehicle(state.getVehicle());
                                            xmit.setCompany(state.getCompanyId());
                                            xmit.setBeginTime(state.getXmit().getBeginTime());
                                            xmit.setType(ReportDefine.DATA_XMIT_GPS_OFF);
                                            xmit.setX(state.getX());
                                            xmit.setY(state.getY());
                                            xmit.setCounter(0);
                                            String address = beanXlsClient.doreverseGeocode(xmit.getX(), xmit.getY());
                                            if (address != null) {
                                                xmit.setAddress(address);
                                            }
                                            hashMapVehicleXmit.put(vehicle.getId(), xmit);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                hashMapNotification.clear();

                //Check notification for company
                Iterator<Map.Entry<String, Xmit>> iteratorXmit = hashMapVehicleXmit.entrySet().iterator();
                while (iteratorXmit.hasNext()) {
                    try {
                        Map.Entry<String, Xmit> entry = iteratorXmit.next();
                        Xmit item =  entry.getValue();
                        Notification notification = hashMapNotification.get(item.getCompany());
                        if(notification==null){
                            notification = new Notification();
                            notification.setCompany(item.getCompany());
                        }
                        notification.getLstXmit().add(item);
                        hashMapNotification.put(item.getCompany(), notification);

                    }catch(Exception e) {
                        log.error("iteratorDataXmit.ex:"+e.getMessage());
                    }
                }
                log.error("hashMapNotification.size:"+hashMapNotification.size());
                //send mail notification
                Iterator<Map.Entry<String, Notification>> iteratorNotification = hashMapNotification.entrySet().iterator();
                while (iteratorNotification.hasNext()) {
                    try {
                        Map.Entry<String, Notification> entry = iteratorNotification.next();
                        Notification item =  entry.getValue();
                        //Build html sender
                        if(hasNotificationSender(item)) {
                            List<String> mails = beanFmsDao.searchMailNotificationByCompany(item.getCompany());
                            if(mails!=null) {
                                List<Xmit> lsXmit = item.getLstXmit();
                                String htmlContent = HtmlBuilder.toHtml(lsXmit);
                                log.error("beanFmsMailService.sendMailToMany:"+mails.toString());
                                beanFmsMailService.sendMailToMany(mails, "EPOSI - Cảnh báo mất tín hiệu !", htmlContent);

                                //Update DataXmit sender
                                for (Xmit xmit : lsXmit) {
                                    xmit.setCounter(xmit.getCounter() + 1);
                                    xmit.setSendTime(new Date());
                                    hashMapVehicleXmit.put(xmit.getVehicle(), xmit);
                                }
                            }
                        }

                        iteratorNotification.remove();
                    }catch(Exception e) {
                        log.error("iteratorNotification.ex:"+e.getMessage());
                    }
                }

                hashMapNotification.clear();
                startTime = 0;
            }
        }
    }

    private boolean hasNotificationSender(Notification notification){
        List<Xmit> lstXmit = notification.getLstXmit();
        if(lstXmit!=null){
            long now = System.currentTimeMillis();
            for(Xmit xmit :lstXmit){
                if(xmit.getSendTime()==null)return true;
                long span = now - xmit.getSendTime().getTime();
                double rate = Math.pow(2, xmit.getCounter());
                if(span > rate*7200000L){
                    return true;
                }
            }
        }
        return false;
    }
}
