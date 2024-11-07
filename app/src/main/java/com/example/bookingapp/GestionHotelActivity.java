package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Hotel;

import java.util.ArrayList;
import java.util.List;

public class GestionHotelActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_HOTEL = 1;
    private static final int REQUEST_EDIT_HOTEL = 2;

    private RecyclerView recyclerViewHotels;
    private HotelAdminAdapter hotelAdapter;
    private Button btnAddHotel, btnEditHotel, btnDeleteHotel;
    private AppDatabase database;
    private List<Hotel> hotelsList = new ArrayList<>();
    private Hotel selectedHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_hotel);

        database = AppDatabase.getAppDatabase(this);

        recyclerViewHotels = findViewById(R.id.recyclerViewHotels);
        btnAddHotel = findViewById(R.id.btnAddHotel);
        btnEditHotel = findViewById(R.id.btnEditHotel);
        btnDeleteHotel = findViewById(R.id.btnDeleteHotel);

        recyclerViewHotels.setLayoutManager(new LinearLayoutManager(this));
        hotelAdapter = new HotelAdminAdapter(hotelsList, this);
        recyclerViewHotels.setAdapter(hotelAdapter);

        hotelAdapter.setOnItemClickListener(hotel -> selectedHotel = hotel);

        loadHotels();

        btnAddHotel.setOnClickListener(v -> {
            Intent intent = new Intent(GestionHotelActivity.this, AddEditHotelActivity.class);
            startActivityForResult(intent, REQUEST_ADD_HOTEL);
        });

        btnEditHotel.setOnClickListener(v -> {
            if (selectedHotel != null) {
                Intent intent = new Intent(GestionHotelActivity.this, AddEditHotelActivity.class);
                intent.putExtra("hotelId", selectedHotel.getId());
                startActivityForResult(intent, REQUEST_EDIT_HOTEL);
            } else {
                Toast.makeText(this, "Veuillez sélectionner un hôtel", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteHotel.setOnClickListener(v -> {
            if (selectedHotel != null) {
                deleteHotel(selectedHotel);
            } else {
                Toast.makeText(this, "Veuillez sélectionner un hôtel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == REQUEST_ADD_HOTEL || requestCode == REQUEST_EDIT_HOTEL)) {
            loadHotels(); // Recharger la liste des hôtels après ajout ou modification
        }
    }

    private void loadHotels() {
        new Thread(() -> {
            hotelsList = database.hotelDao().getAllHotels();
            runOnUiThread(() -> hotelAdapter.updateData(hotelsList));
        }).start();
    }

    private void deleteHotel(Hotel hotel) {
        new Thread(() -> {
            database.hotelDao().deleteHotel(hotel);
            runOnUiThread(() -> {
                loadHotels();
                Toast.makeText(this, "Hôtel supprimé", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
