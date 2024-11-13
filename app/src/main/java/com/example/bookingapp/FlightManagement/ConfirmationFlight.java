package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.R;

public class ConfirmationFlight extends AppCompatActivity {
    private EditText emailField;
    private Button btnConfirmEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_flight);

        emailField = findViewById(R.id.emailField);
        btnConfirmEmail = findViewById(R.id.btnConfirmEmail);

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

    private void sendEmail(String email, String flightDetails) {
        new Thread(() -> {
            try {
                // Utiliser MailSender pour envoyer l'email
                MailSender mailSender = new MailSender();
                mailSender.sendEmail(
                        email,
                        "Booking Confirmation",
                        "Thank you for your booking!\n\n" + flightDetails
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
