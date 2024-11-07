package com.example.bookingapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.ReservationHotel;

import java.util.Calendar;

public class ReservationActivity extends AppCompatActivity {

    private TextView hotelName, hotelLocation, hotelPrice, hotelRoomType;
    private Button checkInDateButton, checkOutDateButton, confirmReservationButton;
    private Calendar checkInCalendar, checkOutCalendar;
    private AppDatabase database;

    private int userId = 1; // Exemple, remplacez par l'utilisateur connecté
    private int chambreId = 1; // Exemple, remplacez par l'ID de la chambre réservée

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_hotel);

        database = AppDatabase.getAppDatabase(this);

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
        String roomType = intent.getStringExtra("hotelRoomType");

        hotelName.setText(name);
        hotelLocation.setText(location);
        hotelPrice.setText("TND " + price + " / nuit");
        hotelRoomType.setText(roomType != null ? "Type de chambre: " + roomType : "Type de chambre non spécifié");

        checkInDateButton.setOnClickListener(v -> showDatePicker(checkInDateButton, true));
        checkOutDateButton.setOnClickListener(v -> showDatePicker(checkOutDateButton, false));

        // Confirmer la réservation avec une alerte
        confirmReservationButton.setOnClickListener(v -> showConfirmationDialog(name));
    }

    private void showConfirmationDialog(String hotelName) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmer la réservation")
                .setMessage("Voulez-vous vraiment confirmer la réservation pour " + hotelName + "?")
                .setPositiveButton("Confirmer", (dialog, which) -> {
                    saveReservation();
                    Toast.makeText(this, "Réservation effectuée avec succès", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void saveReservation() {
        // Créer la réservation
        String startDate = checkInDateButton.getText().toString();
        String endDate = checkOutDateButton.getText().toString();
        int nbPerson = 2; // Exemple : Nombre de personnes, à définir selon votre logique

        ReservationHotel reservation = new ReservationHotel(
                chambreId, userId, "Confirmée", nbPerson, endDate, startDate
        );

        new Thread(() -> {
            database.reservationHotelDao().insertReservation(reservation);
        }).start();
    }

    private void showDatePicker(final Button button, boolean isCheckIn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            button.setText(date);

            if (isCheckIn) {
                checkInCalendar = Calendar.getInstance();
                checkInCalendar.set(year1, month1, dayOfMonth);
            } else {
                checkOutCalendar = Calendar.getInstance();
                checkOutCalendar.set(year1, month1, dayOfMonth);
            }
        }, year, month, day);

        if (isCheckIn) {
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }

        if (!isCheckIn && checkInCalendar != null) {
            datePickerDialog.getDatePicker().setMinDate(checkInCalendar.getTimeInMillis());
        }
        datePickerDialog.show();
    }
}
