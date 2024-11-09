package com.example.bookingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Car;

public class AddCarActivity extends AppCompatActivity {

    private EditText edtModel, edtPrice, edtRegistration;
    private Spinner spinnerBrand, spinnerCarType, spinnerAvailabilityStatus, spinnerModel;
    private NumberPicker numberPickerSeats;
    private Button btnSave, cancelButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // Initialize database
        db = AppDatabase.getAppDatabase(this);

        // Initialize views
        edtPrice = findViewById(R.id.editTextPricePerHour);
        edtRegistration = findViewById(R.id.editTextRegistrationNumber);
        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerCarType = findViewById(R.id.spinnerCarType);
        spinnerAvailabilityStatus = findViewById(R.id.spinnerAvailabilityStatus);
        numberPickerSeats = findViewById(R.id.numberPickerSeats);
        spinnerModel = findViewById(R.id.spinnerModel);
        btnSave = findViewById(R.id.addTransportButton);
        cancelButton = findViewById(R.id.cancelButton);  // Initialize the Cancel button

        // Set the range for the NumberPicker (number of seats between 1 and 50)
        numberPickerSeats.setMinValue(1);
        numberPickerSeats.setMaxValue(50);
        numberPickerSeats.setValue(1);  // Set default value to 1 seat

        // Setup the spinners with mock data
        setupSpinner(spinnerBrand, new String[] {
                "Toyota", "Honda", "BMW", "Ford", "Chevrolet", "Mercedes-Benz", "Audi",
                "Volkswagen", "Nissan", "Hyundai", "Kia", "Porsche", "Tesla", "Jaguar",
                "Land Rover", "Lexus", "Mazda", "Subaru", "Chrysler", "Jeep", "Fiat",
                "Dodge", "Buick", "GMC", "Cadillac", "Acura", "Infiniti", "Mitsubishi",
                "Lincoln", "Ram", "Alfa Romeo", "Mini", "Ferrari", "Lamborghini",
                "Aston Martin", "Bentley", "Rolls-Royce", "Maserati", "McLaren", "Pagani",
                "Bugatti", "Peugeot", "Renault", "Citroën", "Opel", "Skoda", "SEAT",
                "Volvo", "Peugeot", "Suzuki", "Saab", "Honda"
        });
        setupSpinner(spinnerCarType, new String[] {
                "Sedan", "SUV", "Truck", "Coupe", "Convertible", "Hatchback", "Minivan",
                "Wagon", "Sports Car", "Crossover", "Luxury", "Pickup", "Roadster",
                "Hybrid", "Electric", "Van", "MPV", "Subcompact", "Compact", "Full-size",
                "Off-road", "Touring", "Cabriolet", "Grand Tourer", "Cross coupe",
                "All-terrain"
        });
        setupSpinner(spinnerAvailabilityStatus, new String[] {
                "Available", "Not Available"
        });

        // Set up listener for brand selection to change models dynamically
        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateModelSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        // Save button logic
        btnSave.setOnClickListener(v -> saveCar());

        cancelButton.setOnClickListener(v -> {
            // Navigate back to the previous activity
            finish();
        });
    }

    private void setupSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    // Dynamically update model spinner based on selected brand
    private void updateModelSpinner(int brandPosition) {
        String[] models;
        switch (brandPosition) {
            case 0: // Toyota
                models = new String[] {"Camry", "Corolla", "Hilux"};
                break;
            case 1: // Honda
                models = new String[] {"Civic", "Accord", "CR-V"};
                break;
            case 2: // BMW
                models = new String[] {"X5", "3 Series", "M4"};
                break;
            case 3: // Ford
                models = new String[] {"Focus", "Fiesta", "Mustang"};
                break;
            case 4: // Chevrolet
                models = new String[] {"Malibu", "Impala", "Cruze"};
                break;
            case 5: // Mercedes-Benz
                models = new String[] {"A-Class", "C-Class", "E-Class"};
                break;
            case 6: // Audi
                models = new String[] {"A4", "Q5", "A6"};
                break;
            case 7: // Volkswagen
                models = new String[] {"Golf", "Passat", "Tiguan"};
                break;
            case 8: // Nissan
                models = new String[] {"Altima", "Sentra", "Maxima"};
                break;
            case 9: // Hyundai
                models = new String[] {"Elantra", "Sonata", "Tucson"};
                break;
            // Add more cases for other brands as needed
            default:
                models = new String[] {}; // Empty array for unhandled brands
        }

        // Set models in the spinner
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, models);
        spinnerModel.setAdapter(modelAdapter);
    }

    // Save the car to the database
    private void saveCar() {
        // Get input values
        String model = (String) spinnerModel.getSelectedItem(); // Get selected model from spinner
        if (model == null || model.isEmpty()) {
            Toast.makeText(this, "Please select a car model", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(edtPrice.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
            return;
        }

        String registration = edtRegistration.getText().toString();
        if (registration.isEmpty()) {
            Toast.makeText(this, "Please enter a registration number", Toast.LENGTH_SHORT).show();
            return;
        }

        String brand = (String) spinnerBrand.getSelectedItem();
        if (brand == null || brand.isEmpty()) {
            Toast.makeText(this, "Please select a brand", Toast.LENGTH_SHORT).show();
            return;
        }

        String carType = (String) spinnerCarType.getSelectedItem();
        String availabilityStatus = (String) spinnerAvailabilityStatus.getSelectedItem();
        int numberOfSeats = numberPickerSeats.getValue();

        // Ensure the number of seats is valid
        if (numberOfSeats < 1 || numberOfSeats > 50) {
            Toast.makeText(this, "Please select a valid number of seats", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Car object
        Car car = new Car(brand, model, price, registration, carType, availabilityStatus, numberOfSeats);

        // Save the car to the database
        db.carDao().insert(car);

        // Show a toast message
        Toast.makeText(this, "Car Added Successfully", Toast.LENGTH_SHORT).show();

        // Close the activity
        finish();
    }
}
