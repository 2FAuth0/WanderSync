package com.example.sprintproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initializes Firebase Auth
        myAuth = FirebaseAuth.getInstance();

        // TODO: Set up UI elements for sign-in, sign-up, and sign-out buttons

        // TODO: Add listeners for the sign-in, sign-up, and sign-out buttons
        // You will call signIn(email, password), createAccount(email, password), and signOut() from these listeners

        // Initializes Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users"); // This is just an example reference

        // TODO: Add methods for reading/writing data in the future
    }
    // Create a new user (Sign up) using Email
    public void createAccount(String email, String password) {
        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, update UI
                        FirebaseUser user = myAuth.getCurrentUser();
                        updateUI(user); // TODO: Handle successful sign-up UI
                    } else {
                        // If sign up fails, display a message
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null); // TODO: Handle failed sign-up UI
                    }
                });
    }

    // Login (For existing users)
    public void signIn(String email, String password) {
        myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in was successful
                        FirebaseUser user = myAuth.getCurrentUser();
                        updateUI(user); // TODO: Hangle successful sign-in UI
                } else {
                        // Sign in Auth. failed
                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null); // TODO: Handle failed sign-in UI
                    }
                    });
    }
    // Signs out current user
    public void signOut() {
        myAuth.signOut();
        updateUI(null); // TODO: Handle sign out UI
    }
    // TODO: Update UI Method to handle UI changes once the user signs in or signs out
    private void updateUI(FirebaseUser user) {
        // TODO: Implement UI updates
        if (user != null) {
            // User is signed in
        } else {
            // User is signed out
        }
    }

}