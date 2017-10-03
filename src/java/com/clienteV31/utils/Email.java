/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.utils;

import java.util.List;
import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author MAURICIO
 */
@Stateless
public class Email {

    private static final String SENDER_EMAIL = "controlacceso.cager@gmail.com"; //Correo emisor 
    private static final String SENDER_PASSWORD = "Colombia2015*";  // PW emisor 
    private static final String EMAIL_SMTP_SERVER = "smtp.gmail.com"; //Servidor SMTP 
    private static final String EMAIL_SERVER_PORT = "465"; // SSL
    
    public Email() {
    }

    @Asynchronous
    public void sendEmail(String to, String subject, String body, List<String> ccEmails) {
        //TODO create ccEmails for sending copies
        Properties props = new Properties();
        props.put("mail.user", SENDER_EMAIL);
        props.put("mail.password", SENDER_PASSWORD);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", EMAIL_SMTP_SERVER);
        props.put("mail.smtp.port", EMAIL_SERVER_PORT);
        props.put("mail.smtp.socketFactory.port", EMAIL_SERVER_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.connectiontimeout", "7000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtps.ssl.checkserveridentity", "false");
        props.put("mail.smtps.ssl.trust", "*");

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(SENDER_EMAIL));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            if (ccEmails != null && !ccEmails.isEmpty()) {
                for(String ccEmail: ccEmails){
                    msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse(ccEmail));
                }
            }

            msg.setSubject(subject);

            BodyPart emailBody = new MimeBodyPart();
            emailBody.setText(body);
            Multipart multiparte = new MimeMultipart();
            multiparte.addBodyPart(emailBody);

            msg.setContent(multiparte);

            Transport tr = session.getTransport("smtps");
            tr.connect(EMAIL_SMTP_SERVER, 465, SENDER_EMAIL, SENDER_PASSWORD);
            msg.saveChanges();

            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (MessagingException mex) {
            System.out.println("CLIENTE EXCEPTION MAIL" + mex);
        }

    }

    public static class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
        }
    }

}
