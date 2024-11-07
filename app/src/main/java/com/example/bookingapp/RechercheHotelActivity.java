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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.database.AppDatabase;

import java.util.Calendar;
import java.util.Date;

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
    private Calendar checkInCalendar = Calendar.getInstance();
    private Calendar checkOutCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_hotels);

        database = AppDatabase.getAppDatabase(this);

        locationInput = findViewById(R.id.locationInput);
        checkInDateButton = findViewById(R.id.checkInDate);
        checkOutDateButton = findViewById(R.id.checkOutDate);
        peopleInputButton = findViewById(R.id.peopleInput);
        searchButton = findViewById(R.id.searchButton);

        checkInDateButton.setOnClickListener(v -> showDatePicker(checkInDateButton, true));

        checkOutDateButton.setOnClickListener(v -> showDatePicker(checkOutDateButton, false));

        peopleInputButton.setOnClickListener(v -> showPeoplePickerDialog());

        searchButton.setOnClickListener(v -> {
            if (validateDates()) {
                performSearch();
            }
        });
    }

    private void showDatePicker(final Button button, boolean isCheckIn) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            button.setText(date);
            if (isCheckIn) {
                checkInCalendar.set(year1, month1, dayOfMonth);
                checkInDate = date;
            } else {
                checkOutCalendar.set(year1, month1, dayOfMonth);
                checkOutDate = date;
            }
        }, year, month, day);

        if (isCheckIn) {
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }

        if (!isCheckIn && checkInDate != null) {
            datePickerDialog.getDatePicker().setMinDate(checkInCalendar.getTimeInMillis());
        }
        datePickerDialog.show();
    }

    private void showPeoplePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_people_picker, null);
        builder.setView(dialogView);

        NumberPicker npAdultes = dialogView.findViewById(R.id.npAdultes);
        NumberPicker npEnfants = dialogView.findViewById(R.id.npEnfants);

        npAdultes.setMinValue(1);
        npAdultes.setMaxValue(10);
        npAdultes.setValue(nbAdultes);

        npEnfants.setMinValue(0);
        npEnfants.setMaxValue(10);
        npEnfants.setValue(nbEnfants);


        builder.setPositiveButton("OK", (dialog, which) -> {
            nbAdultes = npAdultes.getValue();
            nbEnfants = npEnfants.getValue();

            peopleInputButton.setText(nbAdultes + " Adultes, " + nbEnfants + " Enfants, ");
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
    private boolean validateDates() {
        Date today = Calendar.getInstance().getTime();

        if (checkInCalendar.getTime().before(today)) {
            Toast.makeText(this, "La date d'arrivée ne peut pas être antérieure à aujourd'hui.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (checkOutCalendar.getTime().before(checkInCalendar.getTime())) {
            Toast.makeText(this, "La date de départ doit être après la date d'arrivée.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void performSearch() {

        String location = locationInput.getText().toString().trim();

        // Vérifier si le champ location est vide
        if (location.isEmpty()) {
            locationInput.setError("La localisation est obligatoire");
            locationInput.requestFocus();
            return;
        }
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
