package com.eposi.fms.services;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.VehicleState;
import com.eposi.fms.services.HazelcastCacheClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceNotActiveException;
import com.hazelcast.core.IMap;
import org.nustaq.serialization.FSTConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HazelcastClientMapAkka extends AbstractBean {
	private static final long serialVersionUID = 7465339645595075482L;

	static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
	private HazelcastInstance hazelcastClientLive = null;
	private IMap<String, Object> imap = null;

	public void put(String key, VehicleState value) {
		try {
            this.getMap().set(key, conf.asByteArray(value), 86400, TimeUnit.SECONDS);
		} catch (Exception e) {
		}
	}

	public VehicleState get(String key) {
		VehicleState value = null;

		try {
            Object obj = this.getMap().get(key);
            value = (VehicleState) conf.asObject((byte[]) obj);
        } catch (Exception e) {

		}

		return value;
	}

	public List<Object> getAll() {
		List<Object> items = null;
		try {
			items = new ArrayList<Object>(this.getMap().values());
		} catch (Exception e) {
		}

		return items;
	}

	public IMap<String, Object> getMap() {
		if (imap == null) {
			try {
				imap = hazelcastClientLive.getMap(HazelcastCacheClient.MAP_AKKA);
			} catch (Exception e) {
			}
		}

		return imap;
	}

	public void init() {
		if (hazelcastClientLive == null) {
			try {
				HazelcastCacheClient hazelcastCacheClient = (HazelcastCacheClient) this.getBean("beanHazelcastCacheClient");
				hazelcastClientLive = hazelcastCacheClient.getClient();
			} catch (Exception e) {
			}
		}
	}

	public void shutdown() {
		if (hazelcastClientLive != null) {
			try {
				hazelcastClientLive.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				hazelcastClientLive = null;
			}
		}

	}
}
