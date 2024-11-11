package com.example.wandersync.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripDatabase {
    private static TripDatabase instance;
    private DatabaseReference databaseReference;
    private MutableLiveData<List<Trip>> allTripLiveData = new MutableLiveData<>();
    private MutableLiveData<Trip> tripLiveData = new MutableLiveData<>();

    private TripDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("trips");
    }

    public static synchronized TripDatabase getInstance() {
        if (instance == null) {
            instance = new TripDatabase();
        }
        return instance;
    }

    public String addTrip(String email) {
        String tripID = databaseReference.push().getKey();
        Log.d("TripDatabase", "updateTrip:" + tripID);
        databaseReference.child(tripID).setValue(new Trip(tripID, email));
        return tripID;
    }

    public void updateTrip(Trip trip) {
        String tripID = trip.getId();
        Log.d("TripDatabase", "updateTrip:" + tripID);

        assert tripID != null;
        databaseReference.child(tripID).setValue(trip);
    }

    public MutableLiveData<List<Trip>> getTripData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TripDatabase", "url:"
                        + databaseReference.toString());
                if (dataSnapshot != null) {
                    List<Trip> trips = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Trip trip = snapshot.getValue(Trip.class);
                        trips.add(trip);
                    }
                    Log.d("TripDatabase", "Retrieved trip data");
                    allTripLiveData.setValue(trips);
                } else {
                    Log.w("TripDatabase", "No trip data found");
                    allTripLiveData.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
                Log.e("TripDatabase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return allTripLiveData;
    }
    public MutableLiveData<Trip> getTripDataByID(String tripID) {

        databaseReference.child(tripID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TripDatabase", "url:"
                        + databaseReference.child(tripID).toString() + "getTripData:" + tripID);
                com.example.wandersync.model.Trip trip =
                        dataSnapshot.getValue(com.example.wandersync.model.Trip.class);
                if (trip != null) {
                    Log.d("TripDatabase", "Retrieved trip data for: "
                            + tripID);

                    tripLiveData.setValue(trip);
                } else {
                    Log.w("TripDatabase", "No trip data found for: " + tripID);
                    tripLiveData.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
                Log.e("TripDatabase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return tripLiveData;
    }
}
