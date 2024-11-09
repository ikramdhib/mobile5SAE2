package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.User;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText etEmail; // Email input field
    private AppDatabase db; // Database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize the EditText field for email
        etEmail = findViewById(R.id.etEmail);

        // Initialize the database instance
        db = AppDatabase.getAppDatabase(this);

        // Set the OnClickListener for the "Next" button
        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNext(view);
            }
        });
    }

    // Method to handle the "Next" button click
    public void onNext(View view) {
        String email = etEmail.getText().toString();

        // Check if the email field is empty
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use a separate thread to access the database
        new Thread(() -> {
            User user = db.userDao().getUserByEmail(email);

            runOnUiThread(() -> {
                if (user != null) {
                    // If user is found, proceed to the verification code activity
                    Intent intent = new Intent(ForgotPasswordActivity.this, EnterVerificationCodeActivity.class);
                    intent.putExtra("email", email); // Pass email to the next activity
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent returning back here
                } else {
                    // If user is not found, show an error message
                    Toast.makeText(ForgotPasswordActivity.this, "No user found with this email", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}