package com.example.bookingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingapp.dao.UserDao;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.User;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;  // Use etUsername for fullName field
    private Button btnSignIn;
    private UserDao userDao;
    private TextView tvCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);  // This field will represent fullName
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);

        AppDatabase db = AppDatabase.getAppDatabase(this);
        userDao = db.userDao();

        btnSignIn.setOnClickListener(v -> loginUser());

        new Thread(() -> {
            User testUser = userDao.getUserByEmail("mariem.sebei@gmail.com");
            if (testUser == null) {
                User newUser = new User("Mariem Sebei", "mariem.sebei@gmail.com", "password123", "23456789", "user");
                userDao.insertUser(newUser);
            }
        }).start();

        // Set up the "Create Account" link to navigate to RegisterActivity
        tvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Set up the "Forgot Password?" link to navigate to ForgotPasswordActivity
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });


    }

    private void loginUser() {
        String fullName = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (fullName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            User user = userDao.loginByFullName(fullName, password);
            runOnUiThread(() -> {
                if (user != null) {
                    // Login successful
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                    // Navigate to HomeActivity
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();  // Close the LoginActivity
                } else {
                    // Login failed
                    Toast.makeText(this, "Invalid Full Name or Password", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
