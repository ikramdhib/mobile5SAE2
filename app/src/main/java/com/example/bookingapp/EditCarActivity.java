package com.example.bookingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Car;

public class EditCarActivity extends AppCompatActivity {

    private EditText etCarType, etBrand, etModel, etSeats, etPrice, etRegistration, etAvailabilityStatus;
    private Button btnSaveChanges, btnCancelEdit;
    private AppDatabase db;
    private int carId;  // Change carId to int

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        // Initialize views
        etCarType = findViewById(R.id.etCarType);
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etSeats = findViewById(R.id.etSeats);
        etPrice = findViewById(R.id.etPrice);
        etRegistration = findViewById(R.id.etRegistration);
        etAvailabilityStatus = findViewById(R.id.etAvailabilityStatus);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnCancelEdit = findViewById(R.id.btnCancelEdit);

        // Get the car ID passed from the CarListActivity
        carId = getIntent().getIntExtra("car_id", -1);  // Change to getIntExtra

        // Log the received car ID
        Log.d("EditCarActivity", "Received car ID: " + carId);

        // Check if carId is valid
        if (carId == -1) {
            Toast.makeText(this, "Invalid car ID", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity if the car ID is invalid
            return;
        }

        // Initialize the database
        db = AppDatabase.getAppDatabase(this);

        // Fetch the car from the database using the car_id
        Car car = db.carDao().getById(carId);

        if (car != null) {
            // Pre-fill the fields with the existing car data
            etCarType.setText(car.getCarType());
            etBrand.setText(car.getBrand());
            etModel.setText(car.getModel());
            etSeats.setText(String.valueOf(car.getNbSeats()));
            etPrice.setText(String.valueOf(car.getPrice()));
            etRegistration.setText(car.getRegistration());
            etAvailabilityStatus.setText(car.getAvailabilityStatus());
        } else {
            // If the car is not found, display an error message
            Toast.makeText(this, "Car not found!", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity if car is not found
        }

        // Save changes logic
        btnSaveChanges.setOnClickListener(v -> {
            // Get updated values from the fields
            String carType = etCarType.getText().toString();
            String brand = etBrand.getText().toString();
            String model = etModel.getText().toString();
            int seats = Integer.parseInt(etSeats.getText().toString());
            double price = Double.parseDouble(etPrice.getText().toString());
            String registration = etRegistration.getText().toString();
            String availabilityStatus = etAvailabilityStatus.getText().toString();

            // Create a new car object with updated values
            Car updatedCar = new Car(carId, carType, brand, model, seats, price, registration, availabilityStatus);

            // Update the car details in the database
            db.carDao().update(updatedCar);

            // Show a confirmation message
            Toast.makeText(EditCarActivity.this, "Car updated successfully", Toast.LENGTH_SHORT).show();

            // Close the activity
            finish();
        });

        // Cancel button logic (just finish the activity)
        btnCancelEdit.setOnClickListener(v -> finish());
    }
}
