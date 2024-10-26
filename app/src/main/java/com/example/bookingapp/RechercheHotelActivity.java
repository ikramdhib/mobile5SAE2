package com.example.bookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.database.AppDatabase;

import java.util.Calendar;

public class RechercheHotelActivity extends AppCompatActivity {
    private AppDatabase database;
    private EditText locationInput;
    private Button checkInDateButton;
    private Button checkOutDateButton;
    private Button peopleInputButton;
    private Button searchButton;

    private String checkInDate;
    private String checkOutDate;
    private int nbAdultes = 2; // Valeur par défaut
    private int nbEnfants = 0; // Valeur par défaut


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_hotels);

        // Initialisation de la base de données
        database = AppDatabase.getAppDatabase(this);

        // Initialiser les éléments de l'interface
        locationInput = findViewById(R.id.locationInput);
        checkInDateButton = findViewById(R.id.checkInDate);
        checkOutDateButton = findViewById(R.id.checkOutDate);
        peopleInputButton = findViewById(R.id.peopleInput);
        searchButton = findViewById(R.id.searchButton);

        // Sélection de la date d'arrivée
        checkInDateButton.setOnClickListener(v -> showDatePicker(checkInDateButton));

        // Sélection de la date de départ
        checkOutDateButton.setOnClickListener(v -> showDatePicker(checkOutDateButton));

        // Sélection du nombre d'adultes, enfants et chambres
        peopleInputButton.setOnClickListener(v -> showPeoplePickerDialog());

        // Action sur le bouton de recherche
        searchButton.setOnClickListener(v -> performSearch());
    }

    // Méthode pour afficher un DatePickerDialog
    private void showDatePicker(final Button button) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            button.setText(date);
            if (button.getId() == R.id.checkInDate) {
                checkInDate = date;
            } else {
                checkOutDate = date;
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    // Méthode pour afficher un AlertDialog pour choisir le nombre d'adultes, d'enfants et de chambres
    private void showPeoplePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_people_picker, null);
        builder.setView(dialogView);

        // Initialiser les NumberPickers
        NumberPicker npAdultes = dialogView.findViewById(R.id.npAdultes);
        NumberPicker npEnfants = dialogView.findViewById(R.id.npEnfants);

        // Configurer les limites des NumberPickers
        npAdultes.setMinValue(1);
        npAdultes.setMaxValue(10);
        npAdultes.setValue(nbAdultes);

        npEnfants.setMinValue(0);
        npEnfants.setMaxValue(10);
        npEnfants.setValue(nbEnfants);


        builder.setPositiveButton("OK", (dialog, which) -> {
            // Récupérer les valeurs sélectionnées
            nbAdultes = npAdultes.getValue();
            nbEnfants = npEnfants.getValue();

            // Mettre à jour le texte du bouton
            peopleInputButton.setText(nbAdultes + " Adultes, " + nbEnfants + " Enfants, ");
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    // Méthode pour effectuer la recherche
    private void performSearch() {
        String location = locationInput.getText().toString();
        Log.d("Recherche", "Location: " + location);
        Log.d("Recherche", "Check-in: " + checkInDate);
        Log.d("Recherche", "Check-out: " + checkOutDate);
        Log.d("Recherche", "Adultes: " + nbAdultes + ", Enfants: " + nbEnfants);

        // Créer un intent pour lancer l'activité de résultats de recherche
        Intent intent = new Intent(RechercheHotelActivity.this, ListeHotelActivity.class);
        intent.putExtra("location", location);
        intent.putExtra("checkInDate", checkInDate);
        intent.putExtra("checkOutDate", checkOutDate);
        intent.putExtra("nbAdultes", nbAdultes);
        intent.putExtra("nbEnfants", nbEnfants);
        startActivity(intent);
    }
}
