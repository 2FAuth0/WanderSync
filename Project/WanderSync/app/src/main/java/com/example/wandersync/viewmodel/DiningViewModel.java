package com.example.wandersync.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.example.wandersync.model.DiningDatabase;
import com.example.wandersync.model.DiningReservation;
import com.example.wandersync.model.Trip;
import com.example.wandersync.model.TripDatabase;
import com.example.wandersync.model.User;
import com.example.wandersync.model.UserDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DiningViewModel extends ViewModel {
    private DiningDatabase diningDatabase;
    private UserDatabase userDatabase;
    private TripDatabase tripDatabase;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Trip>> allTripLiveData = new MutableLiveData<>();
    private LiveData<Trip> tripLiveData = new MutableLiveData<>();
    private LiveData<List<DiningReservation>> tripDiningLiveData = new MutableLiveData<>();

    private LiveData<List<DiningReservation>> diningReservationsLiveData;

    public DiningViewModel() {
        diningDatabase = DiningDatabase.getInstance();
        userDatabase = UserDatabase.getInstance();
        tripDatabase = TripDatabase.getInstance();

        diningReservationsLiveData = diningDatabase.getDiningReservationsLiveData();
        userLiveData =
                userDatabase.getUserData(FirebaseAuth.getInstance().getCurrentUser().getUid());
        allTripLiveData = tripDatabase.getTripData();


        tripLiveData = Transformations.switchMap(userLiveData, user -> {
            if (user != null && user.getTripID() != null) {
                return tripDatabase.getTripDataByID(user.getTripID());
            }
            return new MutableLiveData<>(null);
        });

        tripDiningLiveData = Transformations.switchMap(tripLiveData, trip ->
            Transformations.map(diningReservationsLiveData, diningReservations -> {
                List<DiningReservation> filteredReservations = new ArrayList<>();
                if (trip != null && trip.getDiningReservations() != null) {
                    for (DiningReservation reservation : diningReservations) {
                        if (trip.getDiningReservations().contains(reservation.getId())) {
                            filteredReservations.add(reservation);
                        }
                    }
                }
                return filteredReservations;
            }));
    }

    // Method to add a new dining reservation
    public void addDiningReservation(String location,
                                     String website,
                                     String timing) {
        Log.d("DiningViewModel", "addDiningReservation: method called");

        DiningReservation reservation = new DiningReservation(
                null, // Unique identifier
                location,
                timing,
                website
        );
        Log.d("DiningViewModel", "addDiningReservation: got here");


        String reservationId = diningDatabase.addDiningReservation(reservation);
        if (userLiveData.getValue() != null && userLiveData.getValue().getTripID() != null) {
            String tripId = userLiveData.getValue().getTripID();
            Observer<Trip> oneTimeObserver = new Observer<Trip>() {
                @Override
                public void onChanged(Trip trip) {
                    if (trip != null) {
                        trip.addDining(reservationId);
                        tripDatabase.updateTrip(trip);
                    }
                    // Remove itself after the first observation
                    tripDatabase.getTripDataByID(tripId).removeObserver(this);
                }
            };
            tripDatabase.getTripDataByID(tripId).observeForever(oneTimeObserver);
        }

    }

    // Method to retrieve all dining reservations as LiveData
    public LiveData<List<DiningReservation>> getDiningReservations() {
        return tripDiningLiveData;
    }
}