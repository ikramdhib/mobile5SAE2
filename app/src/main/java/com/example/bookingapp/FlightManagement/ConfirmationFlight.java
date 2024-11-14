package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import com.example.bookingapp.R;

import java.io.File;

public class ConfirmationFlight extends AppCompatActivity {
    private EditText emailField;
    private Button btnConfirmEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_flight);

        emailField = findViewById(R.id.emailField);
        btnConfirmEmail = findViewById(R.id.btnConfirmEmail);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
        // Récupérer les détails du vol depuis l'Intent
        Intent intent = getIntent();
        String flightDetails = intent.getStringExtra("flightDetails");

        btnConfirmEmail.setOnClickListener(v -> {
            String email = emailField.getText().toString();

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address!", Toast.LENGTH_SHORT).show();
            } else {
                // Envoyer un email via MailSender
                sendEmail(email, flightDetails);
            }
        });
    }

    public void sendEmail(String email, String flightDetails) {
        new Thread(() -> {
            try {
                // Générer le QR code
                Bitmap qrCodeBitmap = QRCodeGenerator.generateQRCode(flightDetails);

                // Enregistrer le QR code comme fichier
                QRCodeSaver qrCodeSaver = new QRCodeSaver(this);
                File qrCodeFile = qrCodeSaver.saveQRCodeImage(qrCodeBitmap, "booking_qr.png");

                // Utiliser MailSender pour envoyer l'email avec le fichier en pièce jointe
                MailSender mailSender = new MailSender();
                mailSender.sendEmailWithAttachment(
                        email,
                        "Booking Confirmation",
                        "Thank you for your booking!\n\n" + flightDetails,
                        qrCodeFile
                );

                // Montrer un message de succès sur l'interface utilisateur
                runOnUiThread(() -> Toast.makeText(this, "Email sent successfully!", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                // Montrer un message d'erreur sur l'interface utilisateur
                runOnUiThread(() -> Toast.makeText(this, "Failed to send email. Please try again later.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
