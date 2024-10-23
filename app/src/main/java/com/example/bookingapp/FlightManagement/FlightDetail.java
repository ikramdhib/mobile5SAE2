package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapp.R;
import com.example.bookingapp.entity.Flight;
import com.example.bookingapp.R;
import com.example.bookingapp.entity.Flight;

public class FlightDetail extends AppCompatActivity {


    private TextView matriculeTextView, fromTextView, toTextView, dateTextView, seatsTextView, depTimeTextView, arrTimeTextView, typeTextView, escalePointTextView;
    private Button editButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);

        // Initialiser les TextViews et les boutons
        matriculeTextView = findViewById(R.id.matriculeFlightTextView);
        fromTextView = findViewById(R.id.fromTextView);
        toTextView = findViewById(R.id.toTextView);
        dateTextView = findViewById(R.id.dateTextView);
        seatsTextView = findViewById(R.id.seatsTextView);
        depTimeTextView = findViewById(R.id.departureTimeTextView);
        arrTimeTextView = findViewById(R.id.arrivalTimeTextView);
        typeTextView = findViewById(R.id.typeFlightTextView);
        escalePointTextView = findViewById(R.id.pointEscaleTextView);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

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
          //  escalePointTextView.setText(flight.getType().equals("Escale") ? flight.getEscalePoint() : "N/A");
        }

        // Gestion du clic sur le bouton de modification
        editButton.setOnClickListener(v -> {
            /*Intent intent = new Intent(FlightDetail.this, EditFlightActivity.class);
            intent.putExtra("flight", flight); // Passer l'objet Flight à l'activité de modification
            startActivity(intent);*/
        });

        // Gestion du clic sur le bouton de suppression
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog(flight));
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
        // Ici, ajoutez la logique pour supprimer le vol de votre base de données ou liste
        // Exemple : flightList.remove(flight);
        // Afficher un message de confirmation
        Toast.makeText(this, "Vol supprimé avec succès", Toast.LENGTH_SHORT).show();
        finish(); // Retourner à l'activité précédente
    }
}