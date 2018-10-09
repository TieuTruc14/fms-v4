package com.eposi.fms.services;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.html.HtmlBuilder;
import com.eposi.fms.model.Xmit;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class FmsMailService extends AbstractBean {
    private static final long serialVersionUID = -8307759019911462919L;
    private final Logger log = Logger.getLogger(FmsMailService.class);

    private static final String FMS_MAIL_HOST = "mail.eposi.vn";           // Assuming you are sending email from localhost
    private static final String FMS_MAIL_FROM_ADDRESS = "service@eposi.vn";  // Sender's email ID needs to be mentioned
    private static final String FMS_MAIL_USERNAME = "service";  // Sender's email ID needs to be mentioned
    private static final String FMS_MAIL_PASSWORD = "eposi@2016";           // password of admin@konexy.com

    private Properties properties = null;

    public synchronized void sendMail(String to, String title, String htmlContent) throws Exception {
        if (properties == null) {
            properties = System.getProperties();             // Get system properties
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.host", FMS_MAIL_HOST); // Setup mail server
            properties.setProperty("mail.smtp.port", "587"); // TLS/STARTTLS: 587, SSL: 465
            properties.setProperty("mail.host", FMS_MAIL_HOST);
            properties.setProperty("mail.user", FMS_MAIL_USERNAME);
            properties.setProperty("mail.password", FMS_MAIL_PASSWORD);
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.ssl.trust", FMS_MAIL_HOST);
//
//        //Bypass the SSL authentication
            properties.setProperty("mail.smtp.starttls.enable", "true");
        }

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication  getPasswordAuthentication() {
                return new PasswordAuthentication(FMS_MAIL_FROM_ADDRESS, FMS_MAIL_PASSWORD);
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);              // Create a default MimeMessage object.
            message.setFrom(new InternetAddress(FMS_MAIL_FROM_ADDRESS)); // Set From: header field of the header.
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
            properties.setProperty("mail.smtp.host", FMS_MAIL_HOST); // Setup mail server
            properties.setProperty("mail.smtp.port", "587"); // TLS/STARTTLS: 587, SSL: 465
            properties.setProperty("mail.host", FMS_MAIL_HOST);
            properties.setProperty("mail.user", FMS_MAIL_USERNAME);
            properties.setProperty("mail.password", FMS_MAIL_PASSWORD);
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.ssl.trust", FMS_MAIL_HOST);
//
//        //Bypass the SSL authentication
            properties.setProperty("mail.smtp.starttls.enable", "true");
        }

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication  getPasswordAuthentication() {
                return new PasswordAuthentication(FMS_MAIL_FROM_ADDRESS, FMS_MAIL_PASSWORD);
            }
        });

        try{
            Message message = new MimeMessage(session);              // Create a default MimeMessage object.
            message.setFrom(new InternetAddress(FMS_MAIL_FROM_ADDRESS)); // Set From: header field of the header.
            for (int i=0;i<to.size();i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to.get(i))); // Set To: header field of the header.
            }
            message.setSubject(title);                              // Set Subject: header field
            message.setContent(htmlContent, "text/html; charset=UTF-8");          // Send the actual HTML message, as big as you like

            Transport.send(message);                                // Send message
            log.info("Sent message successfully....");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        FmsMailService fmsMailService =  new FmsMailService();
        Xmit xmit = new Xmit();
        xmit.setVehicle("29X1234");
        xmit.setBeginTime(new Date());
        xmit.setAddress("Ngô quang tuấn, Hà Nội");
        List<Xmit> lstXmit  = new ArrayList<>();
        lstXmit.add(xmit);
        String htmlContent = HtmlBuilder.toHtml(lstXmit);
        fmsMailService.sendMail("tuannq@eposi.vn","test ",htmlContent);
    }
}