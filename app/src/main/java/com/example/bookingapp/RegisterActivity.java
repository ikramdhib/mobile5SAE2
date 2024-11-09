package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername1, etEmail, etPassword;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        etUsername1 = findViewById(R.id.etUsername1);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Initialize the database
        db = AppDatabase.getAppDatabase(getApplicationContext());
    }

    // Method to handle Sign Up button click
    public void signUpUser(View view) {
        // Retrieve input from EditText fields
        String fullName = etUsername1.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Simple validation: Check if fields are empty
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email format is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the user already exists
        new Thread(() -> {
            User existingUser = db.userDao().getUserByEmail(email);
            runOnUiThread(() -> {
                if (existingUser != null) {
                    Toast.makeText(this, "User with this email already exists", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Create a new User entity
                    User newUser = new User(fullName, email, password, "123456789", "user");
                    db.userDao().insertUser(newUser);

                    // Show a confirmation message
                    Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();

                    // Clear input fields
                    etUsername1.setText("");
                    etEmail.setText("");
                    etPassword.setText("");

                    // Navigate back to LoginActivity after registration
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }).start();
    }
}