package com.example.bookingapp.FlightManagement;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapp.R;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;

import java.util.ArrayList;

public class FindFlightActivity extends AppCompatActivity {

    private EditText editTextFrom, editTextTo, editTextDate;
    private Button btnShowCalender,searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_flight);
        btnShowCalender = findViewById(R.id.btnShowCalender);
        btnShowCalender.setOnClickListener(v -> showCalendarDialog());
        // Initialiser les champs de recherche
        editTextFrom = findViewById(R.id.etFrom);
        editTextTo = findViewById(R.id.etTo);
        editTextDate = findViewById(R.id.etDate);
        searchButton = findViewById(R.id.btnSearchFlights);

        searchButton.setOnClickListener(v -> {
            // Récupérer les valeurs de recherche
            String from = editTextFrom.getText().toString();
            String to = editTextTo.getText().toString();
            String date = editTextDate.getText().toString();

            // Créer l'intent pour FlightListActivity et y ajouter les valeurs
            Intent intent = new Intent(FindFlightActivity.this, SortedFilightList.class);
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            intent.putExtra("date", date);

            // Lancer FlightListActivity
            startActivity(intent);
        });
    }


    private void showCalendarDialog() {
        // Crée un nouveau dialogue
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_calendar);
        dialog.setCancelable(true);

        // Récupère les vues du dialogue
        CalendarView calendarView = dialog.findViewById(R.id.calendarView);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        // Variable pour stocker la date sélectionnée
        String[] selectedDate = {null};

        // Écouteur pour le calendrier
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Formater la date en jour/mois/année
            selectedDate[0] = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
        });

        // Bouton "Confirmer"
        btnConfirm.setOnClickListener(v -> {
            if (selectedDate[0] != null) {
                editTextDate.setText(selectedDate[0]);
            } else {
                Toast.makeText(this, "Veuillez sélectionner une date", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        // Bouton "Annuler"
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Affiche le dialogue
        dialog.show();
    }

}
