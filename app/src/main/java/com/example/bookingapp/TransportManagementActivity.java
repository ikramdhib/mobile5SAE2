package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TransportManagementActivity extends AppCompatActivity {

    private Button btnCarManagement, btnBusManagement,see_all_cars,see_all_buses,btnReservationManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_management);

        btnCarManagement = findViewById(R.id.btnCarManagement);
        btnBusManagement = findViewById(R.id.btnBusManagement);
        see_all_cars = findViewById(R.id.see_all_cars);
        see_all_buses = findViewById(R.id.see_all_buses);
        btnReservationManagement = findViewById(R.id.btnReservationManagement);
        // Open Car Management Activity when clicked
        btnCarManagement.setOnClickListener(v -> {
            Intent intent = new Intent(TransportManagementActivity.this, CarListActivity.class);
            startActivity(intent);
        });

        // Open Bus Management Activity when clicked (You can implement BusListActivity in the future)
        btnBusManagement.setOnClickListener(v -> {
            Intent intent = new Intent(TransportManagementActivity.this, BusListActivity.class);
            startActivity(intent);
        });

        // Open Bus Management Activity when clicked (You can implement BusListActivity in the future)
        see_all_cars.setOnClickListener(v -> {
            Intent intent = new Intent(TransportManagementActivity.this, BookingListCarsActivity.class);
            startActivity(intent);
        });

        // Open Bus Management Activity when clicked (You can implement BusListActivity in the future)
        see_all_buses.setOnClickListener(v -> {
            Intent intent = new Intent(TransportManagementActivity.this, BookingListBusesActivity.class);
            startActivity(intent);
        });

        btnReservationManagement.setOnClickListener(v -> {
            Intent intent = new Intent(TransportManagementActivity.this, ReservationListActivity.class);
            startActivity(intent);
        });
    }
}
