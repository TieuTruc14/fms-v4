package com.eposi.fms;

import com.eposi.fms.rest.client.RWsClient;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * For Basic Report Processing
 */
public class App {
    private static Logger log = Logger.getLogger(App.class.getName()); /* Get actual class name to be printed on */

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
