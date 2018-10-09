package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.apache.log4j.Logger;
import org.nustaq.serialization.FSTConfiguration;

public class ReportMessageProcessor extends AbstractBean {
    private static final long serialVersionUID = -7718913104171311669L;

    private static Logger log = Logger.getLogger(ReportMessageProcessor.class.getName());
    private QueueingConsumerReportClient beanQueueingConsumerReportClient;
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public synchronized void processMessage(Object msg) {
        if(msg!=null) {
            byte[] bytes = conf.asByteArray(msg);
            if (bytes != null) {
                beanQueueingConsumerReportClient.put(bytes);
            }
        }
    }

    ////////////////////////////
    public void init() {
        beanQueueingConsumerReportClient =(QueueingConsumerReportClient)this.getBean("beanQueueingConsumerReportClient");
    }
}
