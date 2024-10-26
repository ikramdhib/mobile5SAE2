package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Chambre;
import com.example.bookingapp.entity.Hotel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getAppDatabase(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnShowHotels = findViewById(R.id.btnShowHotels);
        insertSampleHotels();

        // Définir l'action lors du clic sur le bouton
        btnShowHotels.setOnClickListener(v -> {
            // Démarrer l'activité ListeHotelActivity
            Intent intent = new Intent(MainActivity.this, RechercheHotelActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void insertSampleHotels() {
        new Thread(() -> {
            Log.d("MainActivity", "Inserting sample hotels...");
            // Vérifier si la table des hôtels est vide
            List<Hotel> hotels = database.hotelDao().getAllHotels();
            Log.d("MainActivity", "Found " + hotels.size() + " hotels in the database");
            if (hotels.isEmpty()) {
                // Insertion de l'hôtel 1
                Hotel hotel1 = new Hotel();
                hotel1.setName("Hôtel de Paris");
                hotel1.setLocation("Paris");
                hotel1.setDescription("Un hôtel élégant au cœur de Paris.");
                hotel1.setAvailable(true);
                hotel1.setPricePerNight(150.0);
                hotel1.setEvaluation(4.5);
                hotel1.setImageResource(R.drawable.img1);
                long hotel1Id = database.hotelDao().insertHotel(hotel1);

                // Insertion des chambres pour l'hôtel 1
                insertSampleChambres((int) hotel1Id);

                // Insertion de l'hôtel 2
                Hotel hotel2 = new Hotel();
                hotel2.setName("Hôtel de New York");
                hotel2.setLocation("New York");
                hotel2.setDescription("Un hôtel moderne avec vue sur la ville.");
                hotel2.setAvailable(true);
                hotel2.setPricePerNight(200.0);
                hotel2.setEvaluation(4.7);
                hotel2.setImageResource(R.drawable.img4);
                long hotel2Id = database.hotelDao().insertHotel(hotel2);

                // Insertion des chambres pour l'hôtel 2
                insertSampleChambres((int) hotel2Id);

                // Insertion de l'hôtel 3
                Hotel hotel3 = new Hotel();
                hotel3.setName("Hôtel de Londres");
                hotel3.setLocation("Londres");
                hotel3.setDescription("Un hôtel luxueux au cœur de Londres, avec une vue magnifique sur la ville.");
                hotel3.setAvailable(true);
                hotel3.setPricePerNight(250.0);
                hotel3.setEvaluation(4.9);
                hotel3.setImageResource(R.drawable.img5);
                long hotel3Id = database.hotelDao().insertHotel(hotel3);

                // Insertion des chambres pour l'hôtel 3
                insertSampleChambres((int) hotel3Id);

                Log.d("MainActivity", "Sample hotels inserted.");
            }
        }).start();
    }

    private void insertSampleChambres(int hotelId) {
        Chambre chambre1 = new Chambre();
        chambre1.setHotelId(hotelId); // Lier la chambre à l'hôtel
        chambre1.setType("Double");
        chambre1.setNbAdultes(2);
        chambre1.setNbEnfants(1);
        chambre1.setAvailable(true);
        chambre1.setPricePerNight(100.0);

        Chambre chambre2 = new Chambre();
        chambre2.setHotelId(hotelId); // Lier la chambre à l'hôtel
        chambre2.setType("Suite");
        chambre2.setNbAdultes(3);
        chambre2.setNbEnfants(2);
        chambre2.setAvailable(true);
        chambre2.setPricePerNight(200.0);

        // Insérer les chambres dans la base de données
        database.chambreDao().insertChambre(chambre1);
        database.chambreDao().insertChambre(chambre2);
    }
}
