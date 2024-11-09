package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.adapters.BookingListBusesAdapter;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Bus;

import java.util.List;

public class BookingListBusesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingListBusesAdapter busAdapter;
    private AppDatabase db;
    private Button btnBackToTransportManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bus_to_book);

        btnBackToTransportManagement = findViewById(R.id.btnBackToTransportManagement);

        db = AppDatabase.getAppDatabase(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the list of buses
        List<Bus> busList = db.busDao().getAllBuses();
        busAdapter = new BookingListBusesAdapter(this, busList);
        recyclerView.setAdapter(busAdapter);

        btnBackToTransportManagement.setOnClickListener(v -> {
            Intent intent = new Intent(BookingListBusesActivity.this, TransportManagementActivity.class);
            startActivity(intent);
        });
    }
}
