package com.example.bookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.ReservationBus;
import java.util.Calendar;

public class ReservationBusActivity extends AppCompatActivity {

    private Spinner paymentMethodSpinner, statusSpinner;
    private Button datePickerButton, btnReserveBus, btnCancel;
    private TextView selectedDateTextView;
    private EditText nbSeatsEditText, prixEditText;

    private AppDatabase db;
    private int busId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_bus);

        // Initialize views
        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);
        statusSpinner = findViewById(R.id.statusSpinner);
        datePickerButton = findViewById(R.id.datePickerButton);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        nbSeatsEditText = findViewById(R.id.nbSeatsEditText);
        prixEditText = findViewById(R.id.prixEditText);
        btnReserveBus = findViewById(R.id.btnReserveBus);
        btnCancel = findViewById(R.id.btnCancel);

        // Setup Spinners with sample data
        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(this,
                R.array.payment_methods_array, android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(paymentAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        // Get bus details from intent
        Intent intent = getIntent();
        String busDetails = intent.getStringExtra("busDetails");
        busId = intent.getIntExtra("busId", -1);

        db = AppDatabase.getAppDatabase(this);

        // DatePicker setup
        datePickerButton.setOnClickListener(v -> showDatePickerDialog());

        // Reserve button click listener
        btnReserveBus.setOnClickListener(v -> {
            String paymentMethod = paymentMethodSpinner.getSelectedItem().toString();
            String status = statusSpinner.getSelectedItem().toString();
            String nbSeatsStr = nbSeatsEditText.getText().toString();
            String prixStr = prixEditText.getText().toString();
            String reservationDate = selectedDateTextView.getText().toString();

            // Validate inputs
            if (nbSeatsStr.isEmpty() || prixStr.isEmpty() || reservationDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int nbSeats = Integer.parseInt(nbSeatsStr);
            int prix = Integer.parseInt(prixStr);

            // Create a new ReservationBus entity
            ReservationBus reservationBus = new ReservationBus(
                    0, // id is auto-generated
                    reservationDate,
                    paymentMethod,
                    status,
                    nbSeats,
                    prix,
                    busId
            );

            // Insert the reservation into the database
            db.reservationBusDao().insert(reservationBus);

            // Show a toast message
            Toast.makeText(this, "Bus reservation successful!", Toast.LENGTH_SHORT).show();

            // Navigate to ThankYouActivity with reservation details
            Intent thankYouIntent = new Intent(ReservationBusActivity.this, ThankYouActivity.class);
            thankYouIntent.putExtra("reservationDate", reservationDate);
            thankYouIntent.putExtra("startDate", "Not Provided");  // Modify as per your logic
            thankYouIntent.putExtra("endDate", "Not Provided");    // Modify as per your logic
            thankYouIntent.putExtra("paymentMethod", paymentMethod);
            thankYouIntent.putExtra("status", status);
            startActivity(thankYouIntent);
            finish();
        });

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> {
            // Clear all fields and reset spinners
            paymentMethodSpinner.setSelection(0);
            statusSpinner.setSelection(0);
            nbSeatsEditText.setText("");
            prixEditText.setText("");
            selectedDateTextView.setText("");
            Toast.makeText(this, "Reservation Canceled", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showDatePickerDialog() {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format and display selected date
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    selectedDateTextView.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }
}
