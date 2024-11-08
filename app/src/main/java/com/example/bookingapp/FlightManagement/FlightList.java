package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.R;
import com.example.bookingapp.dao.FlightDao;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;
import java.util.List;

public class FlightList extends AppCompatActivity {

    private ListView flightListView;
    private FlightDao flightDao;
    private FlightAdapter adapter;
    // Create an ActivityResultLauncher to receive the result from FlightDetail
    private final ActivityResultLauncher<Intent> flightDetailLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Reload the flight list after returning from FlightDetail
                    loadFlightList();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        flightListView = findViewById(R.id.flightListView);
        flightDao = AppDatabase.getAppDatabase(this).flightDao();

        // Récupérer la liste des vols dans un thread séparé
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Flight> flights = flightDao.getAllFlights(); // Assurez-vous que cette méthode existe
                runOnUiThread(() -> {
                    // Mettez à jour l'interface utilisateur avec la liste des vols
                    // Vous devrez créer un adaptateur pour le ListView
                    FlightAdapter adapter = new FlightAdapter(FlightList.this, flights);
                    flightListView.setAdapter(adapter);
                });
            }
        }).start();
    }
    // Ensure the flight list is refreshed whenever the activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        // Reload the flight list every time the activity resumes
        loadFlightList();
    }

    private void loadFlightList() {
        new Thread(() -> {
            List<Flight> flights = flightDao.getAllFlights(); // Fetch all flights from the database
            runOnUiThread(() -> {
                // Update the ListView with the new list of flights
                if (adapter == null) {
                    adapter = new FlightAdapter(FlightList.this, flights);
                    flightListView.setAdapter(adapter);
                } else {
                    adapter.setFlights(flights);  // Assuming FlightAdapter has a setFlights method
                    adapter.notifyDataSetChanged(); // Notify the adapter to refresh the list view
                }
            });
        }).start();
    }
}