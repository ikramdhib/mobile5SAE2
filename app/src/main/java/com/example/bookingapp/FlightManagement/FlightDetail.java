package com.example.bookingapp.FlightManagement;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import com.example.bookingapp.R;
import com.example.bookingapp.dao.FlightDao;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;

import java.util.Calendar;

public class FlightDetail extends AppCompatActivity {
    private static final int REQUEST_CODE_EDIT_FLIGHT = 1;
    private AppDatabase appDatabase;
    private FlightDao flightDao;

    private TextView matriculeTextView, priveView, fromTextView, toTextView, dateTextView, seatsTextView, depTimeTextView, arrTimeTextView, typeTextView, escalePointTextView;
    private Button editButton, deleteButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        // Initialize the AppDatabase instance
        appDatabase = AppDatabase.getAppDatabase(this);

        // Initialize the FlightDao instance
        flightDao = appDatabase.flightDao();

        // Initialiser les TextViews et les boutons
        matriculeTextView = findViewById(R.id.matriculeFlightTextView);
        fromTextView = findViewById(R.id.fromTextView);
        toTextView = findViewById(R.id.toTextView);
        dateTextView = findViewById(R.id.dateTextView);
        seatsTextView = findViewById(R.id.seatsTextView);
        depTimeTextView = findViewById(R.id.departureTimeTextView);
        arrTimeTextView = findViewById(R.id.arrivalTimeTextView);
        typeTextView = findViewById(R.id.typeFlightTextView);
       // escalePointTextView = findViewById(R.id.pointEscaleTextView);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        priveView = findViewById(R.id.price);

        // Obtenir l'objet Flight à partir de l'intent
        Flight flight = (Flight) getIntent().getSerializableExtra("flight");

        // Remplir les TextViews avec les détails du vol
        if (flight != null) {
            matriculeTextView.setText(flight.getFlightMatricule());
            fromTextView.setText(flight.getFrom());
            toTextView.setText(flight.getTo());
            dateTextView.setText(flight.getFlightDate());
            seatsTextView.setText(String.valueOf(flight.getNbSeats()));
            depTimeTextView.setText(flight.getDepartureTime());
            arrTimeTextView.setText(flight.getArrivalTime());
            typeTextView.setText(flight.getType());
            priveView.setText(flight.getPrice()+ "DT");
            Log.d("MyActivity", "User name is: " + flight.getId());
          //  escalePointTextView.setText(flight.getType().equals("Escale") ? flight.getEscalePoint() : "N/A");
        }

        // Gestion du clic sur le bouton de modification
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateFlight.class);
            intent.putExtra("flightId",flight.getId());
            intent.putExtra("matriculeFlight", flight.getFlightMatricule());
            intent.putExtra("flightDate", flight.getFlightDate());
            intent.putExtra("from", flight.getFrom());
            intent.putExtra("to", flight.getTo());
            intent.putExtra("seats", String.valueOf( flight.getNbSeats()));
            intent.putExtra("departureTime", flight.getDepartureTime());
            intent.putExtra("arrivalTime", flight.getArrivalTime());
            intent.putExtra("flightType", flight.getType());
            intent.putExtra("price",flight.getPrice());
            startActivityForResult(intent, REQUEST_CODE_EDIT_FLIGHT);
        });

        // Gestion du clic sur le bouton de suppression
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog(flight));
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

    private void showDeleteConfirmationDialog(Flight flight) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation de suppression")
                .setMessage("Êtes-vous sûr de vouloir supprimer ce vol ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Logique pour supprimer le vol ici
                        deleteFlight(flight);
                    }
                })
                .setNegativeButton("Non", null)
                .show();
    }

    private void deleteFlight(Flight flight) {
        new Thread(() -> {
            flightDao.deleteFlight(flight);
            runOnUiThread(() -> {
                Toast.makeText(this, "Vol supprimé avec succès", Toast.LENGTH_SHORT).show();
                // Return to the previous activity with a result
                setResult(RESULT_OK);  // Notify that the deletion was successful
                finish();  // Close this activity and go back to the list
            });
        }).start();
    }
}