package com.eposi.fms.services;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.HttpClientConnectionManager;

public class IdleConnectionMonitorThread extends Thread {
//    private static Logger log = Logger.getLogger(ReportFileStore.class.getName());
    private final HttpClientConnectionManager connMgr;
    private volatile boolean shutdown;
    private long idleTimeoutInMS;
    
    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr, long idleTimeoutInMS) {
        super();
        this.connMgr = connMgr;
        this.idleTimeoutInMS = idleTimeoutInMS;
    }

    @Override
    public void run() {
    	
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // Close expired connections
//                    System.out.println("IdleConnectionMonitorThread:");
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    connMgr.closeIdleConnections(idleTimeoutInMS, TimeUnit.MILLISECONDS);
                }
            }
        } catch (InterruptedException ex) {
//            log.error(ex.getMessage(), ex);
        }
    }
    
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
    
}