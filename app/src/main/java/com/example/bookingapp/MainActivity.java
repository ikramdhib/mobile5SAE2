package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Chambre;
import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.entity.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getAppDatabase(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnShowHotels = findViewById(R.id.btnShowHotels);
        Button btnAdmin = findViewById(R.id.btnAdmin);

        btnShowHotels.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RechercheHotelActivity.class);
            startActivity(intent);
        });
        btnAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GestionChambreActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
