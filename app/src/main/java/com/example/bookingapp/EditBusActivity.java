package com.example.bookingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Bus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditBusActivity extends AppCompatActivity {

    private EditText editTextNbSeats, editTextDestination, editTextTicketPrice;
    private Button btnSaveBus;
    private AppDatabase db;
    private int busId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bus);

        db = AppDatabase.getAppDatabase(this);

        editTextNbSeats = findViewById(R.id.editTextSeats);
        editTextDestination = findViewById(R.id.editTextDestination);
        editTextTicketPrice = findViewById(R.id.editTextPrice);
        btnSaveBus = findViewById(R.id.buttonSave);

        busId = getIntent().getIntExtra("bus_id", -1);

        if (busId != -1) {
            Bus bus = db.busDao().getBusById(busId);
            editTextNbSeats.setText(String.valueOf(bus.getNbSeats()));
            editTextDestination.setText(bus.getDestination());
            editTextTicketPrice.setText(String.valueOf(bus.getTicketprice()));
        }

        btnSaveBus.setOnClickListener(v -> saveBus());
    }

    private void saveBus() {
        try {
            int nbSeats = Integer.parseInt(editTextNbSeats.getText().toString());
            String destination = editTextDestination.getText().toString();
            double ticketPrice = Double.parseDouble(editTextTicketPrice.getText().toString());

            // Use SimpleDateFormat to get the current time
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String time = sdf.format(new Date());  // Get current time in the "HH:mm" format

            // Create Bus object using the correct constructor
            Bus updatedBus = new Bus(busId, nbSeats, destination, ticketPrice, time);

            // Update the bus in the database
            db.busDao().update(updatedBus);

            // Show success message and close the activity
            Toast.makeText(this, "Bus details updated", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
        }
    }

}
