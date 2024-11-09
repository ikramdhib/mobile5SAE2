package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.adapters.ReservationListAdapter;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.ReservationBus;
import com.example.bookingapp.entity.ReservationCar;
import java.util.List;

public class ReservationListActivity extends AppCompatActivity implements ReservationListAdapter.OnDeleteClickListener {

    private RecyclerView recyclerView;
    private ReservationListAdapter reservationListAdapter;
    private AppDatabase appDatabase;
    private List<ReservationCar> reservationCars;
    private List<ReservationBus> reservationBuses;
    private RadioGroup radioGroup;
    private RadioButton radioCar, radioBus;
    private Button clearAllButton,btnBackToTransportManagement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        radioGroup = findViewById(R.id.radioGroup);
        radioCar = findViewById(R.id.radioCar);
        radioBus = findViewById(R.id.radioBus);
        clearAllButton = findViewById(R.id.clearAllButton);
        btnBackToTransportManagement = findViewById(R.id.btnBackToTransportManagement);
        // Initialize the database
        appDatabase = AppDatabase.getAppDatabase(this);

        // Load reservations from the database
        reservationCars = appDatabase.reservationCarDao().getAllReservations();
        reservationBuses = appDatabase.reservationBusDao().getAllReservations();

        // Set up initial data
        loadCarReservations();

        // Set up listener for radio button changes
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioCar) {
                loadCarReservations();
            } else if (checkedId == R.id.radioBus) {
                loadBusReservations();
            }
        });
        // Set up listener for the Clear All button
        clearAllButton.setOnClickListener(v -> {
            // Clear all reservations from the database
            clearAllReservations();
        });
        btnBackToTransportManagement.setOnClickListener(v -> {
            Intent intent = new Intent(ReservationListActivity.this, TransportManagementActivity.class); // Replace with your actual Transport Management Activity class
            startActivity(intent);
        });
    }

    private void loadCarReservations() {
        reservationListAdapter = new ReservationListAdapter(reservationCars, null, this);
        recyclerView.setAdapter(reservationListAdapter);
    }

    private void loadBusReservations() {
        reservationListAdapter = new ReservationListAdapter(null, reservationBuses, this);
        recyclerView.setAdapter(reservationListAdapter);
    }

    @Override
    public void onDeleteReservationCar(int position) {
        ReservationCar car = reservationCars.get(position);
        appDatabase.reservationCarDao().delete(car);
        reservationCars.remove(position);
        reservationListAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onDeleteReservationBus(int position) {
        ReservationBus bus = reservationBuses.get(position);
        appDatabase.reservationBusDao().delete(bus);
        reservationBuses.remove(position);
        reservationListAdapter.notifyItemRemoved(position);
    }
    private void clearAllReservations() {
        // Clear reservations from the database
        appDatabase.reservationCarDao().deleteAll();
        appDatabase.reservationBusDao().deleteAllReservations();

        // Update the lists to empty
        reservationCars.clear();
        reservationBuses.clear();

        // Notify the adapter that the data has changed
        reservationListAdapter.notifyDataSetChanged();
    }
}
