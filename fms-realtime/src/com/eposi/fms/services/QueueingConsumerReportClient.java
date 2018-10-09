package com.eposi.fms.services;

import com.eposi.common.util.AbstractBean;
import com.rabbitmq.client.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class QueueingConsumerReportClient extends AbstractBean {
	private static final long serialVersionUID = -1575816816071771335L;
	private static Logger log = Logger.getLogger(QueueingConsumerReportClient.class.getName());

	private List<String> addresses = new ArrayList<String>();
	private Address[] addrs;
	private String username = null;
	private String password = null;
	private String exchangename =null;

	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Channel channel = null;

    private int qos = 256; //256

	public void start() {
		try {
			if (factory == null) {
				factory = new ConnectionFactory();

				List<Address> lstAddr = new ArrayList<Address>();
				for (String address : addresses) {
					if (address.contains(":")) {
						String[] strs = address.split(":");
						lstAddr.add(new Address(strs[0], Integer.parseInt(strs[1])));
					} else {
						lstAddr.add(new Address(address, 5672));
					}
				}

				addrs = lstAddr.toArray(new Address[0]);

				factory.setUsername(username);
				factory.setPassword(password);
				factory.setConnectionTimeout(60000);
			}

			connection = factory.newConnection(addrs);
			channel = connection.createChannel();

		} catch (Exception e) {
            log.error("Exception", e);
		}
	}


    public void shutdown() {
		try {
			channel.close();
		} catch (Exception e) {

		}

		try {
			connection.close();
		} catch (Exception e) {

		}
		
		channel = null;
		connection = null;
	}

	public void restart() {
		if (restarting) {
			return;
		}

		try {
			log.info("RabbitMQ Client Restarting in 10 seconds....");
			Thread.sleep(10000);

			restarting = true;
			shutdown();

			start();
		} catch (Exception e) {
            log.error("QueueingConsumerReportClient.Exception:"+e.getMessage());
		} finally {
			restarting = false;
		}
	}

	private static boolean restarting = false;

	public void put(byte[] data) {
		if (restarting) {
			return;
		}

		try {
			channel.exchangeDeclare(exchangename, "direct", true);
			channel.basicPublish(exchangename, "report", null, data);
		} catch (Exception e) {
			log.error("Eposi.ems.put:" + exchangename + " " + addresses + " " + e.getMessage());
			try {
				channel.close();
			} catch (Exception ex1) {
			}

			try {
				connection.close();
			} catch (Exception ex1) {

			}
			this.start();
		}

	}

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ConnectionFactory getFactory() {
		return factory;
	}

	public void setFactory(ConnectionFactory factory) {
		this.factory = factory;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getExchangename() {
		return exchangename;
	}

	public void setExchangename(String exchangename) {
		this.exchangename = exchangename;
	}
}
