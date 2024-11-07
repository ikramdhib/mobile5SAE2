package com.example.bookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ReservationActivity extends AppCompatActivity {

    private TextView hotelName, hotelLocation, hotelPrice, hotelRoomType;
    private Button checkInDateButton, checkOutDateButton, confirmReservationButton;
    private Calendar checkInCalendar, checkOutCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_hotel);

        // Initialisation des vues
        hotelName = findViewById(R.id.hotelNameReservation);
        hotelLocation = findViewById(R.id.hotelLocationReservation);
        hotelPrice = findViewById(R.id.hotelPriceReservation);
        hotelRoomType = findViewById(R.id.hotelRoomTypeReservation);
        checkInDateButton = findViewById(R.id.checkInDateButton);
        checkOutDateButton = findViewById(R.id.checkOutDateButton);
        confirmReservationButton = findViewById(R.id.confirmReservationButton);

        // Récupérer les données passées via l'Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("hotelName");
        String location = intent.getStringExtra("hotelLocation");
        double price = intent.getDoubleExtra("hotelPrice", 0);
        String checkInDate = intent.getStringExtra("checkInDate");
        String checkOutDate = intent.getStringExtra("checkOutDate");
        String roomType = intent.getStringExtra("hotelRoomType");

        // Afficher les données dans les vues
        hotelName.setText(name);
        hotelLocation.setText(location);
        hotelPrice.setText("TND " + price + " / nuit");
        hotelRoomType.setText(roomType != null ?"Type de chambre: " + roomType : "Type de chambre non spécifié");
        checkInDateButton.setText(checkInDate != null ? checkInDate : "Date d'arrivée");
        checkOutDateButton.setText(checkOutDate != null ? checkOutDate : "Date de départ");

        // Gérer la sélection de la date d'arrivée
        checkInDateButton.setOnClickListener(v -> showDatePicker(checkInDateButton, true));

        // Gérer la sélection de la date de départ
        checkOutDateButton.setOnClickListener(v -> showDatePicker(checkOutDateButton, false));

        // Confirmer la réservation
        confirmReservationButton.setOnClickListener(v -> {
            Toast.makeText(this, "Réservation confirmée pour " + name, Toast.LENGTH_LONG).show();
            // Vous pouvez ajouter ici le code pour sauvegarder la réservation dans une base de données ou une API
        });
    }

    // Méthode pour afficher un DatePickerDialog
    private void showDatePicker(final Button button, boolean isCheckIn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            button.setText(date);

            // Mettre à jour les dates dans les variables appropriées
            if (isCheckIn) {
                checkInCalendar = Calendar.getInstance();
                checkInCalendar.set(year1, month1, dayOfMonth);
            } else {
                checkOutCalendar = Calendar.getInstance();
                checkOutCalendar.set(year1, month1, dayOfMonth);
            }
        }, year, month, day);

        // Si on choisit la date d'arrivée, on empêche la sélection de dates antérieures à aujourd'hui
        if (isCheckIn) {
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }

        // Si on choisit la date de départ, on empêche la sélection de dates antérieures à la date d'arrivée
        if (!isCheckIn && checkInCalendar != null) {
            datePickerDialog.getDatePicker().setMinDate(checkInCalendar.getTimeInMillis());
        }
        datePickerDialog.show();
    }
}
