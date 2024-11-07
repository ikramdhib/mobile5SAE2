package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HotelDetailActivity extends AppCompatActivity {
    private TextView hotelName, hotelLocation, hotelPrice, hotelDescription, hotelRoomType;
    private ImageView hotelImage;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_detail);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Initialisation des vues
        hotelName = findViewById(R.id.hotelNameDetail);
        hotelLocation = findViewById(R.id.hotelLocationDetail);
        hotelPrice = findViewById(R.id.hotelPriceDetail);
        hotelDescription = findViewById(R.id.hotelDescriptionDetail);
        hotelImage = findViewById(R.id.hotelImageDetail);
        hotelRoomType = findViewById(R.id.hotelRoomTypeDetail);
        reserveButton = findViewById(R.id.reserveButton);

        // Récupérer les données passées via l'Intent
        String name = getIntent().getStringExtra("hotelName");
        String location = getIntent().getStringExtra("hotelLocation");
        double price = getIntent().getDoubleExtra("hotelPrice", 0);
        String description = getIntent().getStringExtra("hotelDescription");
        int imageResource = getIntent().getIntExtra("hotelImage", R.drawable.img1);
        String roomType = getIntent().getStringExtra("hotelRoomType");
        String checkInDate = getIntent().getStringExtra("checkInDate");
        String checkOutDate = getIntent().getStringExtra("checkOutDate");

        // Définir les données dans l'interface
        hotelName.setText(name);
        hotelLocation.setText(location);
        hotelPrice.setText("TND " + price);
        hotelDescription.setText(description);
        hotelImage.setImageResource(imageResource);
        if (roomType != null) {
            hotelRoomType.setText(roomType);  // Définir le type de chambre
        } else {
            hotelRoomType.setText("Type de chambre non spécifié");
        }


        reserveButton.setOnClickListener(v -> {
            Intent intent = new Intent(HotelDetailActivity.this, ReservationActivity.class);
            intent.putExtra("hotelName", name);
            intent.putExtra("hotelLocation", location);
            intent.putExtra("hotelPrice", price);
            intent.putExtra("checkInDate", checkInDate);  // Si vous avez des dates pré-remplies
            intent.putExtra("checkOutDate", checkOutDate);
            intent.putExtra("hotelRoomType", roomType);
            startActivity(intent);
        });
    }
}
