package com.example.wandersync.view;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wandersync.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.wandersync.R;
import com.example.wandersync.viewmodel.LoginViewModel;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    // TODO: Abhi splash screen or welcome screen here
    private final String TAG = "MainActivity";
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//        binding.setVariable(BR.viewModel, viewModel);
//        binding.setLifecycleOwner(this);
         // Initializes Firebase Auth
//        myAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser currentUser = myAuth.getCurrentUser();
//        if (currentUser == null) {
//            // No user is signed in, redirect to LoginActivity
//            Intent intent = new Intent(MainActivity.this, LoginActivity2.class);
//            startActivity(intent);
//            finish(); // Close MainActivity
//        } else {
//            // User is signed in, show a welcome message or set up the home UI
//            Toast.makeText(MainActivity.this, "Welcome, " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
//        }
//
//        // Initializes Firebase Database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users"); // This is just an example reference


        // TODO: Add methods for reading/writing data in the future

        Button signInButton = findViewById(R.id.btn_signIn);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //find button by id
        Button exitButton = findViewById(R.id.btn_exit);
        exitButton.setPaintFlags(exitButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //set an on clock listened to the button
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //create intent
                finish();
                System.exit(0);
            }
        });

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

    // Method to read data from Firebase Database
    public void readUserData(String userId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    // Update UI with user data
                    Toast.makeText(MainActivity.this, "Name: " + user.getName() + ", Email: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read user data.", databaseError.toException());
            }
        });
    }

    // Method to write data to Firebase Database
    public void writeUserData(String userId, String name, String email) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        // Create a user object (You can use your own model class here)
        User user = new User(name, email);

        // Write to the 'users/userId' node
        usersRef.child(userId).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "User data saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}