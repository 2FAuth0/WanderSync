package com.example.wandersync.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.wandersync.model.TripDatabase;
import com.example.wandersync.model.UserDatabase;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends ViewModel {
    private final UserDatabase userDatabase;
    private final TripDatabase tripDatabase;

    public UserViewModel() {
        userDatabase = UserDatabase.getInstance();
        tripDatabase = TripDatabase.getInstance();
    }

    public void addUser(String email, FirebaseUser firebaseUser) {
        String tripID = tripDatabase.addTrip(email);
        userDatabase.addUser(email, firebaseUser.getUid(), tripID);
    }
}
