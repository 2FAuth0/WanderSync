package com.example.wandersync.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.wandersync.R;

public class MainActivity extends AppCompatActivity {
    // TODO: Abhi splash screen or welcome screen here
    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initializes Firebase Auth
        myAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = myAuth.getCurrentUser();
        if (currentUser == null) {
            // No user is signed in, redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity
        } else {
            // User is signed in, show a welcome message or set up the home UI
            Toast.makeText(MainActivity.this, "Welcome, " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        }

        // Initializes Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users"); // This is just an example reference

        // TODO: Add methods for reading/writing data in the future
    }

    // Signs out current user
    public void signOut() {
        myAuth.signOut();
        updateUI(null); // TODO: Handle sign out UI
    }
    // TODO: Update UI Method to handle UI changes once the user signs in or signs out (if needed)
    private void updateUI(FirebaseUser user) {
        // TODO: Implement UI updates
        if (user != null) {
            // User is signed in
        } else {
            // User is signed out
        }
    }
}