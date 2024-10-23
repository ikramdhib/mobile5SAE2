package com.example.bookingapp.FlightManagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapp.R;
import com.example.bookingapp.dao.FlightDao;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;

public class AddFlight extends AppCompatActivity {

    private EditText matriculeFlight, flightDate, from, to, seats, departureTime, arrivalTime, pointEscale;
    private TextView errorMatriculeFlight, errorFlightDate, errorFrom, errorTo, errorSeats, errorDepartureTime, errorArrivalTime;
    private RadioGroup typeFlightGroup;
    private RadioButton directFlight, escaleFlight;
    private Button btnAdd, btnCancel;

    private AppDatabase appDatabase;
    private FlightDao flightDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        // Initialiser la base de données
        appDatabase = AppDatabase.getAppDatabase(this); // Ajoutez cette ligne

        // Maintenant, vous pouvez accéder à flightDao
        flightDao = appDatabase.flightDao();

        // Initialiser les éléments UI
        matriculeFlight = findViewById(R.id.matriculeFlight);
        flightDate = findViewById(R.id.flightDate);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        seats = findViewById(R.id.seats);
        departureTime = findViewById(R.id.departureTime);
        arrivalTime = findViewById(R.id.arrivalTime);
        typeFlightGroup = findViewById(R.id.typeFlightGroup);
        directFlight = findViewById(R.id.directFlight);
        escaleFlight = findViewById(R.id.escaleFlight);
        pointEscale = findViewById(R.id.pointEscale);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        // Initialiser les TextViews pour les messages d'erreur
        errorMatriculeFlight = findViewById(R.id.errorMatriculeFlight);
        errorFlightDate = findViewById(R.id.errorFlightDate);
        errorFrom = findViewById(R.id.errorFrom);
        errorTo = findViewById(R.id.errorTo);
        errorSeats = findViewById(R.id.errorSeats);
        errorDepartureTime = findViewById(R.id.errorDepartureTime);
        errorArrivalTime = findViewById(R.id.errorArrivalTime);
        // Afficher ou masquer le champ 'Point Escale' en fonction du type de vol
        typeFlightGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.escaleFlight) {
                    pointEscale.setVisibility(View.VISIBLE);
                } else {
                    pointEscale.setVisibility(View.GONE);
                }
            }
        });

        // Action du bouton ajouter
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs
                String matricule = matriculeFlight.getText().toString();
                String date = flightDate.getText().toString();
                String fromLocation = from.getText().toString();
                String toLocation = to.getText().toString();
                String seatCount = seats.getText().toString();
                String depTime = departureTime.getText().toString();
                String arrTime = arrivalTime.getText().toString();
                String typeFlight = escaleFlight.isChecked() ? "Escale" : "Direct";
                String escalePoint = pointEscale.getText().toString();

                boolean isValid = true;
                // Vérification simple des champs
                if (matricule.isEmpty()) {
                    errorMatriculeFlight.setText("Matricule is required.");
                    errorMatriculeFlight.setVisibility(View.VISIBLE); // Affiche le message d'erreur
                    isValid = false;
                } else {
                    errorMatriculeFlight.setVisibility(View.GONE); // Masque le message d'erreur si valide
                }
                if (date.isEmpty()) {
                    errorFlightDate.setText("Flight date is required.");
                    errorFlightDate.setVisibility(View.VISIBLE);
                    isValid = false;
                } else {
                    errorFlightDate.setVisibility(View.GONE);
                }
                if (fromLocation.isEmpty()) {
                    errorFrom.setText("Departure location is required.");
                    errorFrom.setVisibility(View.VISIBLE);
                    isValid = false;
                } else {
                    errorFrom.setVisibility(View.GONE);
                }
                if (toLocation.isEmpty()) {
                    errorTo.setText("Destination location is required.");
                    errorTo.setVisibility(View.VISIBLE);
                    isValid = false;
                } else {
                    errorTo.setVisibility(View.GONE);
                }
                if (seatCount.isEmpty()) {
                    errorSeats.setText("Number of seats is required.");
                    errorSeats.setVisibility(View.VISIBLE);
                    isValid = false;
                } else {
                    try {
                        int seatsValue = Integer.parseInt(seatCount);
                        if (seatsValue <= 0) {
                            errorSeats.setText("Seats must be greater than 0.");
                            errorSeats.setVisibility(View.VISIBLE);
                            isValid = false;
                        } else {
                            errorSeats.setVisibility(View.GONE);
                        }
                    } catch (NumberFormatException e) {
                        errorSeats.setText("Invalid number of seats.");
                        errorSeats.setVisibility(View.VISIBLE);
                        isValid = false;
                    }
                }
                if (depTime.isEmpty()) {
                    errorDepartureTime.setText("Departure time is required.");
                    errorDepartureTime.setVisibility(View.VISIBLE);
                    isValid = false;
                } else {
                    errorDepartureTime.setVisibility(View.GONE);
                }
                if (arrTime.isEmpty()) {
                    errorArrivalTime.setText("Arrival time is required.");
                    errorArrivalTime.setVisibility(View.VISIBLE);
                    isValid = false;
                } else {
                    errorArrivalTime.setVisibility(View.GONE);
                }
                if (isValid) {
                // Si tout est valide, ajouter le vol
                    // Créer un objet Flight
                    Flight newFlight = new Flight();
                    newFlight.setFlightMatricule(matricule);
                    newFlight.setFlightDate(date);
                    newFlight.setAvailability(true);
                    newFlight.setNbSeats(Integer.parseInt(seatCount));
                    newFlight.setFrom(fromLocation);
                    newFlight.setTo(toLocation);
                    newFlight.setArrivalTime(arrTime);
                    newFlight.setDepartureTime(depTime);
                    newFlight.setType(typeFlight);
                    // Ajouter le vol dans la base de données Room
                    new AddFlightAsyncTask(flightDao).execute(newFlight);
                }else{
                    Toast.makeText(AddFlight.this, "There is something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // Action du bouton annuler
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Ferme l'activité
            }
        });
    }

    private void resetErrorMessages() {
        errorMatriculeFlight.setText("");
        errorFlightDate.setText("");
        errorFrom.setText("");
        errorTo.setText("");
        errorSeats.setText("");
        errorDepartureTime.setText("");
        errorArrivalTime.setText("");
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