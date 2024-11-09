package com.example.bookingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Bus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddBusActivity extends AppCompatActivity {

    private NumberPicker numberPickerSeats;
    private Spinner spinnerDestination;
    private EditText editTextTicketPrice;
    private Button btnAddBus,cancelButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bus_activity);

        db = AppDatabase.getAppDatabase(this);

        // Initialize and configure NumberPicker in Java code
        numberPickerSeats = findViewById(R.id.numberPickerSeats);
        numberPickerSeats.setMinValue(1);  // Set minimum value
        numberPickerSeats.setMaxValue(50); // Set maximum value

        // Initialize Spinner
        spinnerDestination = findViewById(R.id.spinnerDestination);
        ArrayList<String> destinations = new ArrayList<>();
        destinations.add("New York");
        destinations.add("Los Angeles");
        destinations.add("Chicago");
        destinations.add("Miami");
        destinations.add("San Francisco");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, destinations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestination.setAdapter(adapter);

        // Initialize EditText and Button
        editTextTicketPrice = findViewById(R.id.editTextTicketPrice);
        btnAddBus = findViewById(R.id.btnAddBus);
        cancelButton = findViewById(R.id.cancelButton);  // Initialize the Cancel button

        // Set button click listener
        btnAddBus.setOnClickListener(v -> addBus());
        // Cancel button click listener
        cancelButton.setOnClickListener(v -> {
            // Navigate back to the previous activity
            finish();
        });
    }

    private void addBus() {
        try {
            // Get selected number of seats from NumberPicker
            int nbSeats = numberPickerSeats.getValue();

            // Get selected destination from Spinner
            String destination = spinnerDestination.getSelectedItem().toString();

            // Parse ticket price from EditText
            double ticketPrice = Double.parseDouble(editTextTicketPrice.getText().toString());

            // Use SimpleDateFormat to get current time
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String timeString = sdf.format(new Date());

            // Create a new Bus object and insert it into the database
            Bus newBus = new Bus(nbSeats, destination, ticketPrice, timeString);
            db.busDao().insert(newBus);

            Toast.makeText(this, "Bus Added", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
        }
    }
}
