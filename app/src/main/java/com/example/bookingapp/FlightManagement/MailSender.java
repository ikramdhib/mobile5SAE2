package com.example.bookingapp.FlightManagement;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private final String smtpHost = "smtp.gmail.com"; // Hôte SMTP (exemple : Gmail)
    private final String smtpPort = "587";           // Port SMTP
    private final String emailSender = "dhibikram50@gmail.com"; // Adresse e-mail de l'expéditeur
    private final String emailPassword = "zexg ijjq dast tuee";      // Mot de passe de l'e-mail (ou mot de passe spécifique à l'application)

    public void sendEmail(String recipient, String subject, String body) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        // Session avec authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSender, emailPassword);
            }
        });

        // Créer le message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailSender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(body);

        // Envoyer le message
        Transport.send(message);
    }
}
