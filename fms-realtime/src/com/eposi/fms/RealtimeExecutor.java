package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.msg.TrackMessage;
import com.eposi.fms.model.VehicleState;
import com.eposi.fms.services.HazelcastClientMapAkka;
import com.eposi.fms.track.FmsProto;
import net.openhft.affinity.AffinityLock;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class RealtimeExecutor extends AbstractBean {
    private static final long serialVersionUID = -1983945949728394L;
    private static Logger log = Logger.getLogger(RealtimeExecutor.class.getName());
    private int poolSize = 4048;
    private int maxMsgPerVehicle = 10;
    private int msxMsgProcessingCurrent = 512;

    private BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
    private ThreadPoolExecutor executor;
    private static ConcurrentHashMap<String, VehicleState> hashMapVehicleState = new ConcurrentHashMap<String, VehicleState>();
    private static ConcurrentHashMap<String, Object> lockedVehicles = new ConcurrentHashMap<String, Object>();

    private static HazelcastClientMapAkka beanHazelcastClientMapAkka;
    private static VehicleProcessor beanVehicleProcessor;


    public synchronized void execute(String vehicleId, String deviceId, Object message) {
        try {
            while (getTotalMsgInProcessing() > msxMsgProcessingCurrent) {
                try {
                    Thread.sleep(10);
                } catch (Exception eInterruptedException) {
                    eInterruptedException.printStackTrace();
                }
            }

            RunnableWorker runningWorker = (RunnableWorker) lockedVehicles.get(vehicleId);
            if (runningWorker != null) {
                while (true) {
                    if (runningWorker.getQueueSize() < maxMsgPerVehicle) {
                        runningWorker.addMessage(message);
                        break;
                    } else {
                        Thread.sleep(1);
                        runningWorker = (RunnableWorker) lockedVehicles.get(vehicleId);
                        if (runningWorker == null) {
                            while(executor.getActiveCount() >= poolSize) { // blockingQueue.size()
                                try {
                                    Thread.sleep(1);
                                } catch (Exception eInterruptedException) {
                                    eInterruptedException.printStackTrace();
                                }
                            }
                            RunnableWorker worker = new RunnableWorker();
                            worker.setVehicleId(vehicleId);
                            worker.setDeviceId(deviceId);
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
                        eInterruptedException.printStackTrace();
                        log.error("Exception", eInterruptedException );
                    }
                }
                RunnableWorker worker = new RunnableWorker();
                worker.setVehicleId(vehicleId);
                worker.setDeviceId(deviceId);
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
                for (Object obj : lockedVehicles.values()) {
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
        beanHazelcastClientMapAkka = (HazelcastClientMapAkka) this.getBean("beanHazelcastClientMapAkka");
        beanVehicleProcessor       = (VehicleProcessor) this.getBean("beanVehicleProcessor");
        executor = new ThreadPoolExecutor(poolSize, poolSize, 6000L, TimeUnit.SECONDS, blockingQueue);
    }

    public void shutdown() {

    }

    private static class RunnableWorker implements Runnable {
        private long lastExecute = 0;
        private String vehicleId;
        private String deviceId;
        private Queue<Object> queueMsg = new LinkedList<Object>();

        @Override
        public void run() {
            try {
                // lock vehicle
                lockedVehicles.put(vehicleId, this);
                ///////////////////////////////////
                // process
                VehicleState state = hashMapVehicleState.get(vehicleId);
                if (state == null) {
                    state = beanHazelcastClientMapAkka.get(vehicleId);
                    if (state == null) {
                        state = new VehicleState();
                        state.setVehicle(vehicleId);
                        state.setDevice(deviceId);

                        beanVehicleProcessor.updateOwnerFromDB(state);
                        Date now = new Date();
                        state.setLastSyncDb((int) (now.getTime()/1000));

                        if (state.getSpeedLimit() <= 0) {
                            state.setSpeedLimit(80);
                        }
                    }
                }

                Object message = null;
                while ((message = getMessage()) != null) {
                    if (state != null) {
                        try {
                            Date now = new Date();
                            if ((state.getCompanyAggregationId() == null) || (((now.getTime()/1000) - state.getLastSyncDb())) > 3600 ) {
                                beanVehicleProcessor.updateOwnerFromDB(state);
                                state.setLastSyncDb((int) (now.getTime() / 1000));
                            }

                           if (message instanceof TrackMessage) {
                                TrackMessage trackMessage = (TrackMessage) message;
                                if (trackMessage.getCommand() == TrackMessage.COMMAND_TRACKING_WAYPOINTBATCH) {
                                    FmsProto.WayPointBatch wayPointBatch =(FmsProto.WayPointBatch)trackMessage.getObj();
                                    beanVehicleProcessor.processWayPointBatch(state, wayPointBatch);
                                } else if (trackMessage.getCommand() == TrackMessage.COMMAND_TRACKING_WAYPOINT) {
                                    FmsProto.WayPoint wayPoint =(FmsProto.WayPoint) trackMessage.getObj();
                                    beanVehicleProcessor.processWayPoint(state, wayPoint);
                                }
                            }
                        } catch (Exception e99) {
                            e99.printStackTrace();
                            log.error("RunnableWorker.ex:" + e99.getMessage());
                        }
                    }
                }
                hashMapVehicleState.put(vehicleId, state);
            } catch (Exception eee) {
                eee.printStackTrace();
                log.error("RunnableWorker.ex:" + eee.getMessage());
            } finally {
                // unlock vehicle
                lockedVehicles.remove(vehicleId);
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

        public void addMessage(Object message) {
            try (AffinityLock al = AffinityLock.acquireCore()) {
                queueMsg.add(message);
            }
        }

        private Object getMessage() {
            Object msg = null;
            try (AffinityLock al = AffinityLock.acquireCore()) {
                msg = queueMsg.poll();
            }
            return msg;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public long getLastExecute() {
            return lastExecute;
        }

        public void setLastExecute(long lastExecute) {
            this.lastExecute = lastExecute;
        }
    }
}
