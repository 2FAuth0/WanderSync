package com.example.wandersync.model;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDatabase {
    private static UserDatabase instance;
    private DatabaseReference databaseReference;

    private UserDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public static synchronized UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public void addUser(User user) {
        String id = databaseReference.push().getKey();
        user.setID(id);
        Log.d("UserDatabase", "addUser:" + id);
        assert id != null;
        databaseReference.child(id).setValue(user);
    }

    public void getUserData(String userID, ValueEventListener listener) {
        databaseReference.child(userID).addListenerForSingleValueEvent(listener);
    }

}
