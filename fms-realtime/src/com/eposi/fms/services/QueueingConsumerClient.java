package com.eposi.fms.services;

import java.util.ArrayList;
import java.util.List;

import com.eposi.common.util.AbstractBean;
import com.rabbitmq.client.*;
import org.apache.log4j.Logger;

public class QueueingConsumerClient extends AbstractBean {
	private static final long serialVersionUID = -1498891724252585280L;
    private static Logger log = Logger.getLogger(QueueingConsumerClient.class.getName());

	private List<String> addresses = new ArrayList<String>();
	private Address[] addrs;
	private String username = null;
	private String password = null;
	private String queuename = null;

	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Channel channel = null;
	private QueueingConsumer consumer = null;

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

            log.debug("addrs: " + addrs.length);

			connection = factory.newConnection(addrs);
			channel = connection.createChannel();
			consumer = new QueueingConsumer(channel);

			// not autoAck
			if(queuename!=null) {
				channel.basicConsume(queuename, false, consumer);
				channel.basicQos(qos);
			}

		} catch (Exception e) {
            log.error("Exception", e);
		}
	}

	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}

    public int getQos() {
        return qos;
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
            log.error("Exception", e);
		} finally {
			restarting = false;
		}
	}

	private static boolean restarting = false;

	public QueueingConsumer.Delivery next() throws Exception {
		QueueingConsumer.Delivery delivery = null;
		if (restarting) {
			return null;
		}

		try {
            if(consumer!=null) {
                delivery = consumer.nextDelivery(1000);
            }
		} catch (Exception e) {
            log.error("QueueingConsumer.Delivery.next.Exception:", e);
			restart();
		}
		return delivery;
	}

    public void setQos(int qos) {
        this.qos = qos;
    }

    public QueueingConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(QueueingConsumer consumer) {
		this.consumer = consumer;
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

}
