package com.eposi.fms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * For Notification Processing
 */
public class App {
    private ApplicationContext applicationContext;

    private void start() throws Exception {
        applicationContext = new FileSystemXmlApplicationContext("src/applicationContext.xml");//Server
        // start the server
        new Thread() {
            public void run() {
                Server server = (Server) applicationContext.getBean("beanServer");
                server.run();
            }
        }.start();
    }

	public static void main(String[] args) throws Exception {
        (new App()).start();
	}
}
