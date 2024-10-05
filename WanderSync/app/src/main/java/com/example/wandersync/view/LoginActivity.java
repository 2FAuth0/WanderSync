package com.example.wandersync.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wandersync.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText userNameInput = findViewById(R.id.userNameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);

        Button loginButton = findViewById(R.id.buttonLogin);
        Button createAccountButton = findViewById(R.id.buttonCreateAccount);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!userName.isEmpty() && !password.isEmpty()) {
                    // Replace `true` with Firebase Authentication logic
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(userName, password)
                            .addOnCompleteListener(LoginActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Login success, go to HomeActivity
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish(); // Close the LoginActivity
                                } else {
                                    // Login failed, show error
                                    TextView errorMessage = findViewById(R.id.errorMessage);
                                    errorMessage.setText("Authentication failed: " + task.getException().getMessage());
                                }
                            });
                } else {
                    TextView errorMessage = findViewById(R.id.errorMessage);
                    errorMessage.setText("Please enter both username and password");
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
