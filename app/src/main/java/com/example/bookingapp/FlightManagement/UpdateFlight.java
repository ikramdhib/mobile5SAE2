package com.example.bookingapp.FlightManagement;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.R;
import com.example.bookingapp.dao.FlightDao;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;

import java.util.Calendar;

public class UpdateFlight extends AppCompatActivity {

    private EditText matriculeFlight, selectedFlightDate, from, to, seats, selectedDepartureTime, selectedArrivalTime, pointEscale;
    private RadioGroup typeFlightGroup;
    private Button arrivalTimeButton,btnShowTimeDialog,btnShowDialog, btnAdd, btnCancel;
    private AppDatabase database;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_flight);

        // Boutons pour ouvrir les dialogues
        btnShowTimeDialog = findViewById(R.id.btnShowTimeDialog);
        btnShowTimeDialog.setOnClickListener(v -> showTimePickerDialog(selectedDepartureTime));

        Button btnShowArrivalTimeDialog = findViewById(R.id.arrivalTimeButton);
        btnShowArrivalTimeDialog.setOnClickListener(v -> showTimePickerDialog(selectedArrivalTime));

        btnShowDialog = findViewById(R.id.btnShowDialog);
        btnShowDialog.setOnClickListener(v -> showCalendarDialog());

        // Liaison des vues avec les éléments UI
        matriculeFlight = findViewById(R.id.matriculeFlight);
        selectedFlightDate = findViewById(R.id.selectedFlightDate);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        seats = findViewById(R.id.seats);
        selectedDepartureTime = findViewById(R.id.selectedDepartureTime);
        selectedArrivalTime = findViewById(R.id.selectedArrivalTime);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        // Récupération des données du vol via Intent
        Intent intent = getIntent();
        matriculeFlight.setText(intent.getStringExtra("matriculeFlight"));
        selectedFlightDate.setText(intent.getStringExtra("flightDate"));
        from.setText(intent.getStringExtra("from"));
        to.setText(intent.getStringExtra("to"));
        seats.setText(intent.getStringExtra("seats"));
        selectedDepartureTime.setText(intent.getStringExtra("departureTime"));
        selectedArrivalTime.setText(intent.getStringExtra("arrivalTime"));


        // Gestion du clic sur le bouton Ajouter
        btnAdd.setOnClickListener(v -> {
            if (validateInputs()) {
                // Récupérer les valeurs modifiées
                String updatedMatricule = matriculeFlight.getText().toString();
                String updatedFlightDate = selectedFlightDate.getText().toString();
                String updatedFrom = from.getText().toString();
                String updatedTo = to.getText().toString();
                String updatedSeats = seats.getText().toString();
                String updatedDepartureTime = selectedDepartureTime.getText().toString();
                String updatedArrivalTime = selectedArrivalTime.getText().toString();

                // Mise à jour du vol dans la base de données
                Flight updatedFlight = new Flight();
                updatedFlight.setFlightMatricule(updatedMatricule);
                updatedFlight.setFlightDate(updatedFlightDate);
                updatedFlight.setNbSeats(Integer.parseInt(updatedSeats));
                updatedFlight.setTo(updatedTo);
                updatedFlight.setFrom(updatedFrom);
                updatedFlight.setDepartureTime(updatedDepartureTime);
                updatedFlight.setArrivalTime(updatedArrivalTime);

                // Exécution de la mise à jour du vol dans la base de données via AsyncTask
                new UpdateFlightTask(flightDao, updatedFlight).execute();
            }
        });

        // Gestion du clic sur le bouton Annuler
        btnCancel.setOnClickListener(v -> finish());
    }


    private class UpdateFlightTask extends AsyncTask<Void, Void, Void> {
        private FlightDao flightDao;
        private Flight updatedFlight;

        public UpdateFlightTask(FlightDao flightDao, Flight updatedFlight) {
            this.flightDao = flightDao;
            this.updatedFlight = updatedFlight;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            flightDao.updateFlight(updatedFlight); // Mise à jour du vol dans la base de données
            return null;
        }
    }

    // Validation des champs
    private boolean validateInputs() {
        boolean isValid = true;
        if (TextUtils.isEmpty(matriculeFlight.getText())) {
           // showError(R.id.errorMatriculeFlight, "Matricule est requis");
            isValid = false;
        } else {
          //  hideError(R.id.errorMatriculeFlight);
        }

        // Ajoutez des validations similaires pour les autres champs

        return isValid;
    }

    // Affichage des erreurs
    private void showError(int errorViewId, String message) {
        TextView errorView = findViewById(errorViewId);
        errorView.setText(message);
        errorView.setVisibility(View.VISIBLE);
    }

    // Masquer les erreurs
    private void hideError(int errorViewId) {
        TextView errorView = findViewById(errorViewId);
        errorView.setVisibility(View.GONE);
    }
}

