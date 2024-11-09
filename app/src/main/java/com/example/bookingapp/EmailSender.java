package com.example.bookingapp;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailSender {
    // Method to send the verification email

    // Method to send the verification email
    public static void sendVerificationEmail(String recipientEmail, String verificationCode) {
        String senderEmail = "maryem.sebeii@gmail.com"; // Replace with your sender email
        String senderPassword = "Rouma"; // Replace with your email password or app-specific password

        // Set up properties for Gmail's SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        // Create a session with the correct properties and authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));  // Sender's email address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));  // Recipient's email address
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + verificationCode);  // Email content

            // Send the message
            Transport.send(message);

            System.out.println("Verification email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
