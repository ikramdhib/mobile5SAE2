package com.example.bookingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HotelDetailActivity extends AppCompatActivity {
    private TextView hotelName, hotelLocation, hotelPrice, hotelDescription;
    private ImageView hotelImage;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_detail);

        // Initialisation des vues
        hotelName = findViewById(R.id.hotelNameDetail);
        hotelLocation = findViewById(R.id.hotelLocationDetail);
        hotelPrice = findViewById(R.id.hotelPriceDetail);
        hotelDescription = findViewById(R.id.hotelDescriptionDetail);
        hotelImage = findViewById(R.id.hotelImageDetail);
        reserveButton = findViewById(R.id.reserveButton);

        // Récupérer les données passées via l'Intent
        String name = getIntent().getStringExtra("hotelName");
        String location = getIntent().getStringExtra("hotelLocation");
        double price = getIntent().getDoubleExtra("hotelPrice", 0);
        String description = getIntent().getStringExtra("hotelDescription");
        int imageResource = getIntent().getIntExtra("hotelImage", R.drawable.img1);

        // Définir les données dans l'interface
        hotelName.setText(name);
        hotelLocation.setText(location);
        hotelPrice.setText("TND " + price);
        hotelDescription.setText(description);
        hotelImage.setImageResource(imageResource);

        // Action à effectuer lors du clic sur le bouton de réservation (à définir)
        reserveButton.setOnClickListener(v -> {
            // Logique de réservation à ajouter ici
        });
    }
}
