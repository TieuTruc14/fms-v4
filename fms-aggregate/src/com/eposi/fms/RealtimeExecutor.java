package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.AbstractMessage;
import net.openhft.affinity.AffinityLock;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class RealtimeExecutor extends AbstractBean {
    private static final long serialVersionUID = -1983945949728394L;
    private static Logger log = Logger.getLogger(RealtimeExecutor.class.getName());
    private int poolSize = 2048;
    private int maxMsgPerVehicle = 5;
    private int msxMsgProcessingCurrent = 512;

    private BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
    private ThreadPoolExecutor executor;
    private static ConcurrentHashMap<String, Object> lockedReports = new ConcurrentHashMap<String, Object>();
    private static ReportProcessor beanReportProcessor;

    public synchronized void execute(String konexyId, AbstractMessage message) {
        try {
            while (getTotalMsgInProcessing() > msxMsgProcessingCurrent) {
                try {
                    Thread.sleep(10);
                } catch (Exception eInterruptedException) {
                    eInterruptedException.printStackTrace();
                }
            }

            RunnableWorker runningWorker = (RunnableWorker) lockedReports.get(konexyId);
            if (runningWorker != null) {
                while (true) {
                    if (runningWorker.getQueueSize() < 1) {
                        runningWorker.addMessage(message);
                        break;
                    } else {
                        Thread.sleep(1);
                        runningWorker = (RunnableWorker) lockedReports.get(konexyId);
                        if (runningWorker == null) {
                            while(executor.getActiveCount() >= poolSize) { // blockingQueue.size()
                                try {
                                    Thread.sleep(1);
                                } catch (Exception eInterruptedException) {
                                }
                            }
                            RunnableWorker worker = new RunnableWorker();
                            worker.setKonexyId(konexyId);
                            worker.addMessage(message);
                            worker.setLastExecute(System.currentTimeMillis());
                            executor.execute(worker);
                            break;
                        }
                    }
                }
            } else {
                while(executor.getActiveCount() >= poolSize) { // blockingQueue.size()
                    try {
                        Thread.sleep(1);
                    } catch (Exception eInterruptedException) {
                        log.error("Exception", eInterruptedException );
                    }
                }
                RunnableWorker worker = new RunnableWorker();
                worker.setKonexyId(konexyId);
                worker.addMessage(message);
                worker.setLastExecute(System.currentTimeMillis());
                executor.execute(worker);
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    private static long lastCheckTotalMsgInProcessing = 0;
    private static long totalMsgInProcessing = 0;
    private synchronized long getTotalMsgInProcessing() {
        long timenow = System.currentTimeMillis();
        if (timenow - lastCheckTotalMsgInProcessing > 3000) {
            totalMsgInProcessing = 0;
            try {
                for (Object obj : lockedReports.values()) {
                    if (obj != null) {
                        RunnableWorker runningWorker = (RunnableWorker) obj;
                        totalMsgInProcessing += runningWorker.getQueueSize();
                    }
                }
            } catch (Exception e) {
                log.error("getTotalMsgInProcessing.ex:" + e.getMessage());
            } finally {
                lastCheckTotalMsgInProcessing = timenow;
            }
        }

        return totalMsgInProcessing;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getMaxMsgPerVehicle() {
        return maxMsgPerVehicle;
    }

    public int getMsxMsgProcessingCurrent() {
        return msxMsgProcessingCurrent;
    }

    public void setMsxMsgProcessingCurrent(int msxMsgProcessingCurrent) {
        this.msxMsgProcessingCurrent = msxMsgProcessingCurrent;
    }

    public void setMaxMsgPerVehicle(int maxMsgPerVehicle) {
        this.maxMsgPerVehicle = maxMsgPerVehicle;
    }

    public void init() {
        executor = new ThreadPoolExecutor(poolSize, poolSize, 6000L, TimeUnit.SECONDS, blockingQueue);
        beanReportProcessor =(ReportProcessor)this.getBean("beanReportProcessor");
    }

    public void shutdown() {

    }

    private static class RunnableWorker implements Runnable {
        private long lastExecute = 0;
        private String konexyId;
        private Queue<AbstractMessage> queueMsg = new LinkedList<AbstractMessage>();

        @Override
        public void run() {
            try {
                // lock vehicle
                lockedReports.put(konexyId, this);
                AbstractMessage message = null;
                while ((message = getMessage()) != null) {
                    beanReportProcessor.processMessage(message);
                }
            } catch (Exception eee) {
                log.error("RunnableWorker.ex:" + eee.getMessage());
                eee.printStackTrace();
            } finally {
                // unlock vehicle
                lockedReports.remove(konexyId);
            }
        }

        public int getQueueSize () {
            int queuesize = 0;

            try (AffinityLock al = AffinityLock.acquireCore()) {
                queuesize = queueMsg.size();
            }

            return queuesize;
        }

        public void purgeMessages() {

            try (AffinityLock al = AffinityLock.acquireCore()) {
                queueMsg.clear();
            }
        }

        public void addMessage(AbstractMessage message) {
            try (AffinityLock al = AffinityLock.acquireCore()) {
                queueMsg.add(message);
            }
        }

        private AbstractMessage getMessage() {
            AbstractMessage msg = null;
            try (AffinityLock al = AffinityLock.acquireCore()) {
                msg = queueMsg.poll();
            }
            return msg;
        }

        public String getKonexyId() {
            return konexyId;
        }

        public void setKonexyId(String konexyId) {
            this.konexyId = konexyId;
        }

        public long getLastExecute() {
            return lastExecute;
        }

        public void setLastExecute(long lastExecute) {
            this.lastExecute = lastExecute;
        }
    }
}
