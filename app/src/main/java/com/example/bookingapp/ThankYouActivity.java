package com.example.bookingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.Manifest;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ThankYouActivity extends AppCompatActivity {

    private TextView reservationDetailsTextView;
    private Button confirmRideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_thank_you);


        // Initialize views
        reservationDetailsTextView = findViewById(R.id.reservationDetailsTextView);
        confirmRideButton = findViewById(R.id.confirmRideButton);

        // Get reservation details from the Intent
        Intent intent = getIntent();
        String reservationDate = intent.getStringExtra("reservationDate");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");
        String paymentMethod = intent.getStringExtra("paymentMethod");
        String status = intent.getStringExtra("status");

        // Display reservation details in the TextView
        String reservationDetails = "Reservation Date: " + reservationDate + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate + "\n" +
                "Payment Method: " + paymentMethod + "\n" +
                "Status: " + status;
        reservationDetailsTextView.setText(reservationDetails);

        // Confirm Ride button click listener
        confirmRideButton.setOnClickListener(v -> {
            // Handle the confirm ride action
            downloadReservationDetails(reservationDate, startDate, endDate, paymentMethod, status);

            Toast.makeText(this, "Ride confirmed!", Toast.LENGTH_SHORT).show();

            // Simulate downloading reservation details or trigger download logic here
        });
    }

    // Simulate the downloading of reservation details
    private void downloadReservationDetails(String reservationDate, String startDate, String endDate, String paymentMethod, String status) {
        // Check for write permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        // Create the file name and content
        String fileName = "ReservationDetails.txt";
        String fileContent = "Reservation Date: " + reservationDate + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate + "\n" +
                "Payment Method: " + paymentMethod + "\n" +
                "Status: " + status;

        // Define the file path in the Documents directory
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            // Write the content to the file
            fos.write(fileContent.getBytes());
            fos.flush();

            // Notify the user of success
            Toast.makeText(this, "Reservation details saved to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Notify the user of the error
            Toast.makeText(this, "Failed to save reservation details.", Toast.LENGTH_SHORT).show();
        }
    }



}
