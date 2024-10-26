package com.example.bookingapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.entity.HotelWithChambres;

import java.util.List;

public class ListeHotelActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_hotels);
        // Initialisation de la base de données
        database = AppDatabase.getAppDatabase(this);
        // Initialisation du RecyclerView
        recyclerView = findViewById(R.id.recyclerViewHotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Récupérer les critères de recherche de l'intent
        String location = getIntent().getStringExtra("location");
        int nbAdultes = getIntent().getIntExtra("nbAdultes", 2);
        int nbEnfants = getIntent().getIntExtra("nbEnfants", 0);

        // Charger les hôtels depuis la base de données
        loadHotels(location, nbAdultes, nbEnfants);
    }

    private void loadHotels(String location, int nbAdultes, int nbEnfants) {
        new Thread(() -> {
            // Récupérer les hôtels correspondants aux critères de recherche
            List<HotelWithChambres> hotels = database.hotelDao().searchHotels("%" + location + "%", nbAdultes, nbEnfants);

            // Mettre à jour l'interface utilisateur sur le thread principal
            runOnUiThread(() -> {
                hotelAdapter = new HotelAdapter(hotels, this);
                recyclerView.setAdapter(hotelAdapter);
            });
        }).start();
}}
