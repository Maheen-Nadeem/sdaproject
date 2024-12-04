package com.sdaproject.frugalflowapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySignUp extends AppCompatActivity {

    // Declare UI elements
    private Button signUpButton, backButton;
    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private TextView loginRedirectTextView;

    // SharedPreferences for storing user data locally
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Initialize UI elements
        signUpButton = findViewById(R.id.signup_button);
        backButton = findViewById(R.id.back_button);
        firstNameEditText = findViewById(R.id.multi_line_text_box);
        lastNameEditText = findViewById(R.id.last_name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        loginRedirectTextView = findViewById(R.id.login_redirect_text);

        // Set up button click listeners
        signUpButton.setOnClickListener(v -> handleSignUp());
        backButton.setOnClickListener(v -> finish());
        loginRedirectTextView.setOnClickListener(v -> {
            Intent intent = new Intent(ActivitySignUp.this, ActivitySignIn.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Handles the sign-up logic.
     */
    private void handleSignUp() {
        // Retrieve input values
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        // Validate inputs
        if (TextUtils.isEmpty(firstName)) {
            showToast("Please enter your First Name");
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            showToast("Please enter your Last Name");
            return;
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid Email");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            showToast("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match");
            return;
        }

        // Store user data locally
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FirstName", firstName);
        editor.putString("LastName", lastName);
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.apply();

        showToast("Sign-Up Successful!");
        // Navigate to the Sign-In page
        startActivity(new Intent(ActivitySignUp.this, ActivitySetCurrency.class));
        finish();
    }

    /**
     * Displays a toast message.
     */
    private void showToast(String message) {
        Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_SHORT).show();
    }
}
