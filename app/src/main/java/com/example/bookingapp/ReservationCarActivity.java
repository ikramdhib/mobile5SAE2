package com.example.bookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.ReservationCar;
import java.util.Calendar;

public class ReservationCarActivity extends AppCompatActivity {

    private AppDatabase db;
    private Spinner paymentMethodSpinner, statusSpinner;
    private Button reservationDateButton, startDateButton, endDateButton, submitReservationButton, cancelButton;
    private TextView carDetailsTextView, reservationDateTextView, startDateTextView, endDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reservation_car);

        // Initialize the database
        db = AppDatabase.getAppDatabase(this);

        // Initialize views
        carDetailsTextView = findViewById(R.id.carDetailsTextView);
        reservationDateButton = findViewById(R.id.reservationDateButton);
        reservationDateTextView = findViewById(R.id.reservationDateTextView);
        startDateButton = findViewById(R.id.startDateButton);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateButton = findViewById(R.id.endDateButton);
        endDateTextView = findViewById(R.id.endDateTextView);
        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);
        statusSpinner = findViewById(R.id.statusSpinner);
        submitReservationButton = findViewById(R.id.submitReservationButton);
        cancelButton = findViewById(R.id.cancelButton);  // Initialize the Cancel button

        // Setup Spinners with sample data
        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(this,
                R.array.payment_methods_array, android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(paymentAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        // Get car details from the Intent
        Intent intent = getIntent();
        String carDetails = intent.getStringExtra("carDetails");
        int carId = intent.getIntExtra("carId", -1);
        carDetailsTextView.setText(carDetails);

        // Setup DatePickers
        reservationDateButton.setOnClickListener(v -> showDatePickerDialog(reservationDateTextView));
        startDateButton.setOnClickListener(v -> showDatePickerDialog(startDateTextView));
        endDateButton.setOnClickListener(v -> showDatePickerDialog(endDateTextView));

        // Submit button click listener
        submitReservationButton.setOnClickListener(v -> {
            String reservationDate = reservationDateTextView.getText().toString();
            String startDate = startDateTextView.getText().toString();
            String endDate = endDateTextView.getText().toString();
            String paymentMethod = paymentMethodSpinner.getSelectedItem().toString();
            String status = statusSpinner.getSelectedItem().toString();

            // Validate input data
            if (reservationDate.isEmpty() || startDate.isEmpty() || endDate.isEmpty() ||
                    paymentMethod.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create and save the ReservationCar entity
            ReservationCar reservation = new ReservationCar(
                    reservationDate, startDate, endDate, paymentMethod, status, carId
            );

            // Save to database using a background thread
            new Thread(() -> {
                Log.d("ReservationCarActivity", "Attempting to insert reservation into database...");
                db.reservationCarDao().insertReservation(reservation);
                Log.d("ReservationCarActivity", "Insert operation completed.");
            }).start();

            // Show success message and navigate to Thank You activity
            Toast.makeText(this, "Reservation created successfully!", Toast.LENGTH_SHORT).show();
            Intent thankYouIntent = new Intent(ReservationCarActivity.this, ThankYouActivity.class);
            thankYouIntent.putExtra("reservationDate", reservationDate);
            thankYouIntent.putExtra("startDate", startDate);
            thankYouIntent.putExtra("endDate", endDate);
            thankYouIntent.putExtra("paymentMethod", paymentMethod);
            thankYouIntent.putExtra("status", status);
            startActivity(thankYouIntent);
            finish();
        });

        // Cancel button click listener
        cancelButton.setOnClickListener(v -> {
            // Navigate back to the previous activity
            finish();
        });
    }

    private void showDatePickerDialog(TextView dateTextView) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dateTextView.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }
}
