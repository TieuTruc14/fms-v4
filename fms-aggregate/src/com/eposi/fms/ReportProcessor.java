package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.common.ReportDefine;
import com.eposi.fms.model.*;
import com.eposi.fms.services.ReportFileStore;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Tuan on 7/11/2016.
 */
public class ReportProcessor extends AbstractBean {
    private static final long serialVersionUID = 4132417325214060619L;

    private static Logger log = Logger.getLogger(ReportProcessor.class.getName());
    private ReportFileStore beanReportFileStore;
    private AggregationWindowProcessor beanAggregationWindowProcessor;


    public void processMessage(AbstractMessage msg) throws  Exception{
        if (msg == null) {
            return;
        }

        if (msg instanceof Stop) {
            Stop stop = (Stop) msg;
            beanReportFileStore.addStop(stop.getId(), stop);
        } else if (msg instanceof OverSpeed) {
            OverSpeed os = (OverSpeed) msg;
            beanReportFileStore.addOverSpeed(os.getId(), os);

        } else if (msg instanceof DrivingOver4h) {
            DrivingOver4h drivingOver4h = (DrivingOver4h) msg;
            beanReportFileStore.addOvertime4h(drivingOver4h.getId(), drivingOver4h);

        } else if (msg instanceof DrivingOver10h) {
            DrivingOver10h drivingOver10h = (DrivingOver10h) msg;
            beanReportFileStore.addOvertime10h(drivingOver10h.getId(), drivingOver10h);

        }  else if (msg instanceof Trip) {
            Trip trip = (Trip) msg;
            beanReportFileStore.addTrip(trip.getId(), trip);

        }   else  if (msg instanceof DataXmit) {
            DataXmit xmit  = (DataXmit)msg;
            beanReportFileStore.addXmitVehicle(xmit.getId(), xmit);
        }else if (msg instanceof GeoPoi) {
            GeoPoi geoPoi =(GeoPoi) msg;
            beanReportFileStore.addGeoPoiVehicle(geoPoi.getId(), geoPoi);
        }

        beanAggregationWindowProcessor.processMessage(msg);
    }

    public void init() {
        beanReportFileStore = (ReportFileStore) this.getBean("beanReportFileStore");
        beanAggregationWindowProcessor = (AggregationWindowProcessor)this.getBean("beanAggregationWindowProcessor");
    }
}
