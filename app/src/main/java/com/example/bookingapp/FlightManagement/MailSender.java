package com.example.bookingapp.FlightManagement;

import java.io.File;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
    private final String smtpHost = "smtp.gmail.com"; // Hôte SMTP (exemple : Gmail)
    private final String smtpPort = "587";           // Port SMTP
    private final String emailSender = "dhibikram50@gmail.com"; // Adresse e-mail de l'expéditeur
    private final String emailPassword = "zexg ijjq dast tuee";      // Mot de passe de l'e-mail (ou mot de passe spécifique à l'application)

    public void sendEmailWithAttachment(String toEmail, String subject, String body, File attachment) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSender, emailPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailSender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);

        // Ajouter le texte
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);

        // Ajouter la pièce jointe
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachment);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(attachment.getName());

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
