package com.example.bookingapp.FlightManagement;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapp.R;
import com.example.bookingapp.dao.FlightDao;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;

import java.util.Calendar;

public class AddFlight extends AppCompatActivity {

    private EditText matriculeFlight, departureTime, flightDate, from, to, seats, arrivalTime, pointEscale;
    private EditText selectedFlightDate, selectedDepartureTime, selectedArrivalTime;
    private Button btnShowTimeDialog, btnShowDialog, btnAdd, btnCancel;

    private AppDatabase appDatabase;
    private FlightDao flightDao;

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
            selectedDate[0] = year + "-" + (month + 1) + "-" + dayOfMonth;
        });

        // Bouton "Confirmer"
        btnConfirm.setOnClickListener(v -> {
            if (selectedDate[0] != null) {
                selectedFlightDate.setText(selectedDate[0]);
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

    private void showTimePickerDialog(EditText timeField) {
        // Utilisation d'un TimePickerDialog natif pour sélectionner l'heure
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute1) -> {
                    String selectedTime = hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1);
                    timeField.setText(selectedTime);
                },
                hour, minute, true
        );

        timePickerDialog.show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        // Initialisation des champs de texte pour afficher les sélections
        selectedFlightDate = findViewById(R.id.selectedFlightDate);
        selectedDepartureTime = findViewById(R.id.selectedDepartureTime);
        selectedArrivalTime = findViewById(R.id.selectedArrivalTime);

        // Boutons pour ouvrir les dialogues
        btnShowTimeDialog = findViewById(R.id.btnShowTimeDialog);
        btnShowTimeDialog.setOnClickListener(v -> showTimePickerDialog(selectedDepartureTime));

        Button btnShowArrivalTimeDialog = findViewById(R.id.arrivalTimeButton);
        btnShowArrivalTimeDialog.setOnClickListener(v -> showTimePickerDialog(selectedArrivalTime));

        btnShowDialog = findViewById(R.id.btnShowDialog);
        btnShowDialog.setOnClickListener(v -> showCalendarDialog());

        // Initialiser la base de données
        appDatabase = AppDatabase.getAppDatabase(this);
        flightDao = appDatabase.flightDao();

        // Initialisation des champs de texte
        matriculeFlight = findViewById(R.id.matriculeFlight);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        seats = findViewById(R.id.seats);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        // Action du bouton ajouter
        btnAdd.setOnClickListener(v -> {
            String matricule = matriculeFlight.getText().toString();
            String date = selectedFlightDate.getText().toString();
            String fromLocation = from.getText().toString();
            String toLocation = to.getText().toString();
            String seatCount = seats.getText().toString();
            String depTime = selectedDepartureTime.getText().toString();
            String arrTime = selectedArrivalTime.getText().toString();

            boolean isValid = true;

            if (matricule.isEmpty() || date.isEmpty() || fromLocation.isEmpty() || toLocation.isEmpty() || seatCount.isEmpty() || depTime.isEmpty() || arrTime.isEmpty()) {
                Toast.makeText(AddFlight.this, "Veuillez remplir tous les champs avant de soumettre", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (isValid) {
                Flight newFlight = new Flight();
                newFlight.setFlightMatricule(matricule);
                newFlight.setFlightDate(date);
                newFlight.setAvailability(true);
                newFlight.setNbSeats(Integer.parseInt(seatCount));
                newFlight.setFrom(fromLocation);
                newFlight.setTo(toLocation);
                newFlight.setArrivalTime(arrTime);
                newFlight.setDepartureTime(depTime);

                new AddFlightAsyncTask(flightDao).execute(newFlight);
            }
        });

        // Action du bouton annuler
        btnCancel.setOnClickListener(v -> finish());
    }

    private class AddFlightAsyncTask extends AsyncTask<Flight, Void, Void> {
        private FlightDao flightDao;

        public AddFlightAsyncTask(FlightDao flightDao) {
            this.flightDao = flightDao;
        }

        @Override
        protected Void doInBackground(Flight... flights) {
            flightDao.insertFlight(flights[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(AddFlight.this, "Flight added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
