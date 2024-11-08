package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.R;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;
import com.example.bookingapp.entity.ReservationFlight;

public class BookFlight extends AppCompatActivity {

    private TextView flightDate, flightDepartureTime, flightArrivalTime, passengerCount, totalPrice;
    private Button btnDecreasePassengers, btnIncreasePassengers, btnFinalBook, btnCancel;

    private int id, passengers = 1;
    private double pricePerPassenger = 100.00; // Valeur par défaut, peut être mise à jour avec les données passées
    private double totalCost;

    private Flight flight;  // Objet Flight pour récupérer les informations sur le vol

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_flight);

        // Initialisation des vues
        flightDate = findViewById(R.id.flightDate);
        flightDepartureTime = findViewById(R.id.flightDepartureTime);
        flightArrivalTime = findViewById(R.id.flightArrivalTime);
        passengerCount = findViewById(R.id.passengerCount);
        totalPrice = findViewById(R.id.totalPrice);
        btnDecreasePassengers = findViewById(R.id.btnDecreasePassengers);
        btnIncreasePassengers = findViewById(R.id.btnIncreasePassengers);
        btnFinalBook = findViewById(R.id.btnFinalBook);
        btnCancel = findViewById(R.id.btnAnnuler); // Initialisation du bouton Cancel

        // Récupérer les informations depuis l'Intent
        Intent intent = getIntent();
        id = intent.getIntExtra("flightId", -1);
        String date = intent.getStringExtra("flightDate");
        String departureTime = intent.getStringExtra("departureTime");
        String arrivalTime = intent.getStringExtra("arrivalTime");
        pricePerPassenger = intent.getDoubleExtra("pricePerPassenger", 100.00); // Prix par passager par défaut si non spécifié

        // Obtenez l'objet Flight pour vérifier le nombre de sièges disponibles
        AppDatabase db = AppDatabase.getAppDatabase(BookFlight.this);
        flight = db.flightDao().getFlightById(id);  // Obtenez le vol par son ID

        // Mettre à jour l'interface utilisateur
        flightDate.setText("Date: " + date);
        flightDepartureTime.setText("Departure Time: " + departureTime);
        flightArrivalTime.setText("Arrival Time: " + arrivalTime);
        updateTotalPrice();

        // Configuration des boutons
        btnDecreasePassengers.setOnClickListener(v -> {
            if (passengers > 1) {
                passengers--;
                passengerCount.setText(String.valueOf(passengers));
                updateTotalPrice();
            }
        });

        btnIncreasePassengers.setOnClickListener(v -> {
            if (passengers < flight.getNbSeats()) {
                passengers++;
                passengerCount.setText(String.valueOf(passengers));
                updateTotalPrice();
            } else {
                Toast.makeText(BookFlight.this, "Not enough available seats!", Toast.LENGTH_SHORT).show();
            }
        });

        btnFinalBook.setOnClickListener(v -> {
            // Vérification si le nombre de passagers dépasse le nombre de sièges disponibles
            if (passengers <= flight.getNbSeats()) {
                // Logique de réservation pour chaque passager
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = AppDatabase.getAppDatabase(BookFlight.this);
                        for (int i = 0; i < passengers; i++) {
                            // Créer une réservation par passager
                            ReservationFlight reservation = new ReservationFlight();
                            reservation.setCreatedAt(date);
                            reservation.setFlightId(id);
                            reservation.setUserId(1); // Utilisateur par défaut ou vous pouvez modifier selon le cas
                            reservation.setStatus("CONFIRMED");
                            reservation.setPrice(pricePerPassenger); // Prix pour chaque passager

                            // Insérer chaque réservation dans la base de données
                            db.reservationFlightDao().insert(reservation);
                        }

                        // Mettre à jour le nombre de sièges disponibles après la réservation
                        flight.setNbSeats(flight.getNbSeats() - passengers);
                        db.flightDao().updateFlight(flight); // Mise à jour de l'objet Flight dans la base de données

                        runOnUiThread(() -> {
                            Toast.makeText(BookFlight.this, "Booking confirmed for all passengers!", Toast.LENGTH_SHORT).show();
                            // Retour à l'écran de liste des vols
                            Intent backToList = new Intent(BookFlight.this, FindFlightActivity.class);
                            startActivity(backToList);
                            finish();
                        });
                    }
                }).start();
            } else {
                Toast.makeText(BookFlight.this, "Not enough seats available for the number of passengers!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuration du bouton Cancel
        btnCancel.setOnClickListener(v -> {
            // Retourner à l'activité de liste des vols (SortedFilightList)
            Intent backToList = new Intent(BookFlight.this, FindFlightActivity.class);
            startActivity(backToList);
            finish();  // Facultatif : pour fermer l'activité BookFlight et ne pas la laisser dans la pile d'activités
        });
    }

    private void updateTotalPrice() {
        totalCost = passengers * pricePerPassenger;
        totalPrice.setText(String.format("$%.2f", totalCost));
    }
}
