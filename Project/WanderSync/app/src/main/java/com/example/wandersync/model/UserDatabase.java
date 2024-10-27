package com.example.wandersync.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static UserDatabase instance;
    private DatabaseReference databaseReference;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    private UserDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public static synchronized UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public void addUser(FirebaseUser newUser) {
        String userID = newUser.getUid();
        Log.d("UserDatabase", "addUser:" + userID);
        assert userID != null;
        User user = new User(newUser.getEmail(), userID);
        databaseReference.child(userID).setValue(user);
    }

    public void updateUser(User user) {
        String userID = user.getId();
        Log.d("UserDatabase", "updateUser:" + userID);
        assert userID != null;
        databaseReference.child(userID).setValue(user);
    }

    public MutableLiveData<User> getUserData(String userID) {
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("UserDatabase", "getUserData:" + userID);
                userLiveData.setValue(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
                Log.e("UserDatabase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return userLiveData;
    }


}
