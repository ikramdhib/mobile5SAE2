package com.example.bookingapp.FlightManagement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingapp.R;
import com.example.bookingapp.dao.FlightDao;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;

import java.util.ArrayList;
import java.util.List;

public class SortedFilightList extends AppCompatActivity {


    private List<Flight> filteredFlights; // Liste filtrée des vols
    private FlightDao flightDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_filight_list);
        flightDao = AppDatabase.getAppDatabase(this).flightDao();

        // Récupérer les paramètres de recherche depuis l'intent
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String date = getIntent().getStringExtra("date");

        // Obtenir l'instance de la base de données
        AppDatabase db = AppDatabase.getAppDatabase(this);

        // Exécuter la recherche en utilisant FlightDao
        new Thread(new Runnable() {
            @Override
            public void run() {

                filteredFlights = flightDao.searchFlights(from, to, date);
                Log.d("MyActivity", "flightss" + from);
                Log.d("MyActivity", "flightss" + to);
                Log.d("MyActivity", "flightss" + date);

                // Mettre à jour l'interface utilisateur avec les vols filtrés
                runOnUiThread(() -> displayFlights(filteredFlights));
                Log.d("MyActivity", "flightss" + filteredFlights.size());
            }

        }).start();
    }

    // Méthode pour afficher la liste des vols
    private void displayFlights(List<Flight> flights) {
        LinearLayout flightListLayout = findViewById(R.id.flightListLayout);
        flightListLayout.removeAllViews(); // Clear existing views

        for (Flight flight : flights) {
            // Inflate the flight_item layout
            View flightItemView = getLayoutInflater().inflate(R.layout.flight_sort_item, flightListLayout, false);

            // Find views in the inflated layout
            ImageView flightImage = flightItemView.findViewById(R.id.flightImage);
            TextView flightInfo = flightItemView.findViewById(R.id.flightInfo);
            Button btnBookNow = flightItemView.findViewById(R.id.btnBookNow);

            // Set the flight information text
            flightInfo.setText(
                    "Flight: " + flight.getFlightMatricule() + "\n" +
                            "From: " + flight.getFrom() + "\n" +
                            "To: " + flight.getTo() + "\n" +
                            "Date: " + flight.getFlightDate() + "\n" +
                            "Seats: " + flight.getNbSeats()
            );

            // Set the image resource if applicable (assuming ic_flight is a placeholder)
            flightImage.setImageResource(R.drawable.flightsort);

            // Set up the "Book Now" button click listener
            btnBookNow.setOnClickListener(v -> {
                // Handle booking logic here, e.g., start a new activity or show a confirmation dialog
                Toast.makeText(this, "Booking " + flight.getFlightMatricule(), Toast.LENGTH_SHORT).show();
            });

            // Add the populated item view to the flight list layout
            flightListLayout.addView(flightItemView);
        }
    }
}