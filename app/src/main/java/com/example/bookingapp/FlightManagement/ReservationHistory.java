package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.ReservationFlight;

import java.util.List;

public class ReservationHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private List<ReservationFlight> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_history);

        recyclerView = findViewById(R.id.recyclerViewReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the user ID (for example: from Intent or user model)
        int userId = 1;  // Replace this with the actual user ID from your authentication system

        // Fetch the reservations from the database in a separate thread
        new Thread(() -> {
            AppDatabase db = AppDatabase.getAppDatabase(ReservationHistory.this);
            reservations = db.reservationFlightDao().getReservationsByUserId(userId);
            Log.d("MyActivity", "les vols: " + reservations);
            Button btnBookNow = findViewById(R.id.btnBookNow);

            btnBookNow.setOnClickListener(v -> {
                        // Start the BookFlight activity when the button is clicked
                        Intent intent = new Intent(ReservationHistory.this, FindFlightActivity.class);
                        startActivity(intent);
                    });
            // Update the UI with the reservation list
            runOnUiThread(() -> {
                if (reservations.isEmpty()) {
                    Toast.makeText(ReservationHistory.this, "No reservations found", Toast.LENGTH_SHORT).show();
                } else {
                    // Pass the context and reservation list to the adapter
                    adapter = new ReservationAdapter(reservations, ReservationHistory.this);
                    recyclerView.setAdapter(adapter);
                }
            });
        }).start();
    }
}
