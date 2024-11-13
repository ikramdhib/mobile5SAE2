package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingapp.R;

public class ThankYouFlight extends AppCompatActivity {

    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_flight);

        // Initialiser le bouton
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // Définir l'action pour revenir à l'écran d'accueil ou à une autre activité
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(ThankYouFlight.this, FindFlightActivity.class);  // ou l'activité d'accueil
            startActivity(intent);
            finish();  // Fermer l'activité actuelle
        });
    }
}