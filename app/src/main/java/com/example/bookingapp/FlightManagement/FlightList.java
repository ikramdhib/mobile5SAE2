package com.example.bookingapp.FlightManagement;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingapp.R;
import com.example.bookingapp.dao.FlightDao;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;

import java.util.List;

public class FlightList extends AppCompatActivity {

    private ListView flightListView;
    private FlightDao flightDao;

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
}