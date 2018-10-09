package com.eposi.fms.services;

import com.eposi.common.util.AbstractBean;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.*;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Properties;

public class FmsMailService extends AbstractBean {
    private static final long serialVersionUID = -8307759019911462919L;
    private final Logger log = Logger.getLogger(FmsMailService.class);

    private static final String KONEXY_MAIL_HOST = "mail.eposi.vn";           // Assuming you are sending email from localhost
    private static final String KONEXY_MAIL_FROM_ADDRESS = "service";  // Sender's email ID needs to be mentioned
    private static final String KONEXY_MAIL_USERNAME = "service";  // Sender's email ID needs to be mentioned
    private static final String KONEXY_MAIL_PASSWORD = "eposi@2016";           // password of admin@eposi.com
//    private static final String KONEXY_MAIL_HOST = "smtp.gmail.com";           // Assuming you are sending email from localhost

    private Properties properties = null;

    public synchronized void sendMail(String to, String title, String htmlContent) throws Exception {

        if (properties == null) {
            properties = System.getProperties();             // Get system properties
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.host", KONEXY_MAIL_HOST); // Setup mail server
            properties.setProperty("mail.smtp.port", "465"); // TLS/STARTTLS: 587, SSL: 465 . TLS/STARTTLS is gmail example.
            properties.setProperty("mail.host", KONEXY_MAIL_HOST);
            properties.setProperty("mail.user", KONEXY_MAIL_USERNAME);
            properties.setProperty("mail.password", KONEXY_MAIL_PASSWORD);
            properties.setProperty("mail.smtp.auth", "true"); // use SSL with security if true.
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.socketFactory.fallback", "false");
//        //Bypass the SSL authentication
            properties.setProperty("mail.smtp.starttls.enable", "true");
        }

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication  getPasswordAuthentication() {
                return new PasswordAuthentication(KONEXY_MAIL_FROM_ADDRESS, KONEXY_MAIL_PASSWORD);
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);              // Create a default MimeMessage object.
            message.setFrom(new InternetAddress(KONEXY_MAIL_FROM_ADDRESS)); // Set From: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // Set To: header field of the header.
            message.setSubject(title, StandardCharsets.UTF_8.name());             // Set Subject: header field
            message.setContent(htmlContent, "text/html; charset=UTF-8");          // Send the actual HTML message, as big as you like

            Transport.send(message);                                // Send message
            log.info("Sent message successfully....");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void sendMailToMany(List<String> to, String title, String htmlContent) throws Exception {
        if (properties == null) {
            properties = System.getProperties();             // Get system properties
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.host", KONEXY_MAIL_HOST); // Setup mail server
            properties.setProperty("mail.smtp.port", "587"); // TLS/STARTTLS: 587, SSL: 465
            properties.setProperty("mail.host", KONEXY_MAIL_HOST);
            properties.setProperty("mail.user", KONEXY_MAIL_USERNAME);
            properties.setProperty("mail.password", KONEXY_MAIL_PASSWORD);
            properties.setProperty("mail.smtp.auth", "true");
//
//        //Bypass the SSL authentication
            properties.setProperty("mail.smtp.starttls.enable", "true");
        }

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication  getPasswordAuthentication() {
                return new PasswordAuthentication(KONEXY_MAIL_FROM_ADDRESS, KONEXY_MAIL_PASSWORD);
            }
        });

        try{
            Message message = new MimeMessage(session);              // Create a default MimeMessage object.
            message.setFrom(new InternetAddress(KONEXY_MAIL_FROM_ADDRESS)); // Set From: header field of the header.
            for (int i=0;i<to.size();i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to.get(i))); // Set To: header field of the header.
            }
            message.setSubject(title);                              // Set Subject: header field
            message.setContent(htmlContent, "text/html");          // Send the actual HTML message, as big as you like

            Transport.send(message);                                // Send message
            log.info("Sent message successfully....");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }



}
