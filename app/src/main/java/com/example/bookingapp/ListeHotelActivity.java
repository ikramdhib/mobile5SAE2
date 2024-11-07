package com.example.bookingapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.database.AppDatabase;
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
        database = AppDatabase.getAppDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewHotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String location = getIntent().getStringExtra("location");
        int nbAdultes = getIntent().getIntExtra("nbAdultes", 2);
        int nbEnfants = getIntent().getIntExtra("nbEnfants", 0);
        String checkInDate = getIntent().getStringExtra("checkInDate");
        String checkOutDate = getIntent().getStringExtra("checkOutDate");


        loadHotels(location, nbAdultes, nbEnfants, checkInDate,checkOutDate);
    }

    private void loadHotels(String location, int nbAdultes, int nbEnfants,String checkInDate, String checkOutDate) {
        new Thread(() -> {
            List<HotelWithChambres> hotels = database.hotelDao().searchHotels(
                    "%" + location + "%", nbAdultes, nbEnfants, checkInDate, checkOutDate);

            runOnUiThread(() -> {
                hotelAdapter = new HotelAdapter(hotels, this, checkInDate, checkOutDate);
                recyclerView.setAdapter(hotelAdapter);
            });
        }).start();
}}
