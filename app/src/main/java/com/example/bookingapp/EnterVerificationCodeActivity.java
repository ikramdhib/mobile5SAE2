package com.example.bookingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.User;

public class EnterVerificationCodeActivity extends AppCompatActivity {
    private EditText etVerificationCode, etNewPassword, etConfirmPassword;
    private AppDatabase db;
    private String enteredVerificationCode = "123456"; // Example, replace with dynamic code if needed

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etVerificationCode = findViewById(R.id.etVerificationCode);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnResetPassword = findViewById(R.id.btnResetPassword);

        db = AppDatabase.getAppDatabase(this);

        btnResetPassword.setOnClickListener(view -> resetPassword());
    }

    private void resetPassword() {
        String verificationCode = etVerificationCode.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // Validate input fields
        if (verificationCode.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the entered verification code matches the sent code
        if (!verificationCode.equals(enteredVerificationCode)) {
            Toast.makeText(this, "Incorrect verification code", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the user by email
        User user = db.userDao().getUserByEmail("user@example.com");  // Replace with the correct email
        if (user != null) {
            user.setPassword(newPassword);  // Set the new password
            db.userDao().updateUser(user);  // Update user in the database
            Toast.makeText(this, "Password reset successfully", Toast.LENGTH_SHORT).show();

            // Redirect to the success page
            Intent intent = new Intent(EnterVerificationCodeActivity.this, PasswordResetSuccessActivity.class);
            startActivity(intent);
            finish();  // Finish this activity and go back to the login screen
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }
}