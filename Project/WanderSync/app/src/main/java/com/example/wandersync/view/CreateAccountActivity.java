package com.example.wandersync.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wandersync.R;
import com.example.wandersync.model.User;
import com.example.wandersync.model.UserDatabase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        TextInputEditText emailInput = findViewById(R.id.emailInput);
        TextInputEditText passwordInput = findViewById(R.id.passwordInput);
        Button submitButton = findViewById(R.id.buttonSubmit);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    // Replace `true` with Firebase Authentication logic
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateAccountActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Account created, get Firebase UID
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                if (firebaseUser != null) {
                                    String uid = firebaseUser.getUid();
                                    User user = new User (email, password);
                                    user.setID(uid);
                                    // Saving user to UserDatabase with user's uid
                                    UserDatabase.getInstance().addUser(user);
                                }
                                // Login success, go to HomeActivity
                                Toast.makeText(CreateAccountActivity.this,
                                        "Welcome, " + email, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateAccountActivity.this,
                                        HomeActivity.class);
                                startActivity(intent);
                                finish(); // Close the LoginActivity
                            } else {
                                // Login failed, show error
                                TextView errorMessage = findViewById(R.id.errorMessage);
                                errorMessage.setText("Authentication failed: "
                                        + task.getException().getMessage());
                            }
                        });
                } else {
                    TextView errorMessage = findViewById(R.id.errorMessage);
                    errorMessage.setText("Please enter both username and password");
                }
            }
        });
    }
}
