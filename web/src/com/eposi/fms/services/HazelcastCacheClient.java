package com.eposi.fms.services;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.common.FmsUtil;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HazelcastCacheClient extends AbstractBean {
    private static final long serialVersionUID = -7134002218510782289L;

    private final Logger log = Logger.getLogger(this.getClass());
    public static final String MAP_AKKA          = "fms"; // realtime

	//
	private List<String> addresses = new ArrayList<String>();
	private String username = null;
	private String password = null;

	private ClientConfig clientConfig;

	private HazelcastInstance client = null;

    private Thread threadMaintainConnection = null;

    public void start() {
        try {
            if (clientConfig == null) {
                clientConfig = new ClientConfig();
                clientConfig.getNetworkConfig().setConnectionTimeout(600000);
                clientConfig.getNetworkConfig().setConnectionAttemptLimit(4);
                clientConfig.getNetworkConfig().setSmartRouting(true);
                clientConfig.getGroupConfig().setName(username).setPassword(password);
                if (addresses != null) {
                    for (String address : addresses) {
                        clientConfig.getNetworkConfig().addAddress(address);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            client = (HazelcastInstance) HazelcastClient.newHazelcastClient(clientConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (threadMaintainConnection == null) {
                threadMaintainConnection = new Thread(new ConnectionMaintainThread());
                threadMaintainConnection.start(); // start the monitoring Thread
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	public void shutdown() {
		try {
			client.getLifecycleService().shutdown();
			client = null;
			client.shutdown();
		} catch (Exception e) {
			// do nothing
		}
	}

	public HazelcastInstance getClient() {
		return client;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public class ConnectionMaintainThread implements Runnable {
        @Override
        public void run() {
            String randomKey = "test_" + FmsUtil.getToken(8);
            IMap<String, Object> imap = client.getMap("maptest");

            while (true) {
                try {
                    try {
                        Thread.sleep(180000);
                    } catch (InterruptedException eInterruptedException) {
                        eInterruptedException.printStackTrace();
                    }

                    if (client == null) {
                        System.out.println("ConnectionMaintainThread: client == null => reconnect..." );
                        reconnect();
						continue;
                    } else {
                        if (imap == null) {
                            System.out.println("ConnectionMaintainThread: imap == null => reconnect..." );
                            reconnect();
							continue;
                        } else {
                            try {
                                imap.put(randomKey, "test");
                            } catch (Exception ePut) {
                                System.out.println("ConnectionMaintainThread: put Failed! => reconnect..." );
                                ePut.printStackTrace();
                                reconnect();

								continue;
                            }

                            try {
                                String strTest = (String) imap.get(randomKey);
                                if (strTest == null) {
                                    System.out.println("ConnectionMaintainThread: strTest == null => reconnect..." );
                                    reconnect();
									continue;
                                } else {
                                    if (!strTest.equals("test")) {
                                        System.out.println("ConnectionMaintainThread: strTest # expected => reconnect..." );
                                        reconnect();
										continue;
                                    }
                                }
                            } catch (Exception ePut) {
                                System.out.println("ConnectionMaintainThread: get Failed! => reconnect..." );
                                ePut.printStackTrace();
                                reconnect();
								continue;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void reconnect() {
            shutdown();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException eInterruptedException) {
                eInterruptedException.printStackTrace();
            }

            start();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException eInterruptedException) {
                eInterruptedException.printStackTrace();
            }
        }
    }

}
