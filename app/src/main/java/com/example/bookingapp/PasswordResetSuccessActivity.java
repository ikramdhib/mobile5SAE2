package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PasswordResetSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_success);
    }

    // Handle the button click to go back to the Login Activity
    public void goToLogin(View view) {
        Intent intent = new Intent(PasswordResetSuccessActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();  // Close the current activity to prevent going back to it
    }
}