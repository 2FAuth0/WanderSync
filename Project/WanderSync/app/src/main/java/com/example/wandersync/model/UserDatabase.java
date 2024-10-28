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
    public MutableLiveData<User> userLiveData = new MutableLiveData<>();
    public MutableLiveData<List<User>> allUserLiveData = new MutableLiveData<>();

    private UserDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public static synchronized UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public void addUser(String email,FirebaseUser newUser) {
        String userID = newUser.getUid();

        assert userID != null;
        User user = new User(email, userID);
        databaseReference.child(userID).setValue(user);
    }

    public void updateUser(User user) {
        String userID = user.getId();
        Log.d("UserDatabase", "updateUser:" + userID + ";vacaions" + user.getAllottedVacation().get(0).getEndDate());

        assert userID != null;
        databaseReference.child(userID).setValue(user);
    }

    public MutableLiveData<User> getUserData(String userID) {

        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("UserDatabase", "url:"+databaseReference.child(userID).toString()+"getUserData:" + userID);
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    Log.d("UserDatabase", "Retrieved user data for: " + userID + " his email is:" + user.getEmail());

                    userLiveData.setValue(user);
                } else {
                    Log.w("UserDatabase", "No user data found for: " + userID);
                    userLiveData.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
                Log.e("UserDatabase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return userLiveData;
    }



    public MutableLiveData<List<User>> getAllUserData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("UserDatabase", "url:"+databaseReference.toString()+"; getAllUserData");
                List<User> users = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }
                allUserLiveData.setValue(users);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
                Log.e("UserDatabase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return allUserLiveData;
    }
}
